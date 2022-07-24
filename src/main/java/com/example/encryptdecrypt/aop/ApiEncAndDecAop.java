package com.example.encryptdecrypt.aop;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.util.UriEncoder;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Aspect
@Component
@Slf4j
public class ApiEncAndDecAop {
    //用于解密pc端发来的数据中的iv
    @Value("${ipgs.rsa.privateKey}")
    private String ipgsRSAPrivateKey;
    //向PC端返回数据时用于加密iv
    @Value("${ipgsPC.rsa.publicKey}")
    private String ipgsPCRSAPublicKey;

    @Pointcut("@annotation(com.example.encryptdecrypt.annotation.ApiEncAndDec)")
    public void pointcut(){}

    @Around("pointcut()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attr.getRequest();
        HttpServletResponse response = attr.getResponse();

        //方案1 请求方式application/json
        JSONObject paramFromRequest = getParamFromRequest(request);
        String ipgsPcIv = paramFromRequest.getString("iv");
        String encData = paramFromRequest.getString("encData");


        //方案2 请求方式改为form        
        /*String  ipgsPcIv = request.getParameter("iv");
        String encData = request.getParameter("encData");*/
        if(!ObjectUtils.isEmpty(ipgsPcIv) && !ObjectUtils.isEmpty(encData)){
            RSA rsa = SecureUtil.rsa(ipgsRSAPrivateKey, null);
            byte[] ivReq = rsa.decrypt(ipgsPcIv, KeyType.PrivateKey);
            AES aes = new AES(Mode.ECB, Padding.ISO10126Padding,ivReq);
            byte[]  reqDecParam = aes.decrypt(encData);
            Object[] args = joinPoint.getArgs();
            if(args.length>0 && !(args[0] instanceof HttpServletRequest) && !(args[0] instanceof HttpServletResponse)
            && !(args[0] instanceof MultipartFile)){
                Object o = JSON.parseObject(new String(reqDecParam,"utf-8"), args[0].getClass());
                BeanUtils.copyProperties(o,args[0]);
            }
        }else {
            Map<String,String> map = new HashMap<>();
            map.put("masCode","999999");
            map.put("msg","iv和encData有误");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSONObject.toJSONString(map));
            return ;
        }

        Object proceed = joinPoint.proceed();
        //加密处理返回
        String ipgsIv = UUID.randomUUID().toString().replaceAll("-","");
        AES aes = new AES(Mode.ECB, Padding.ISO10126Padding,ipgsIv.getBytes(StandardCharsets.UTF_8));
        String encryptResp = aes.encryptBase64(JSON.toJSONString(proceed));

        RSA rsa = SecureUtil.rsa(null, ipgsPCRSAPublicKey);
        String ivResp = rsa.encryptBase64(ipgsIv, KeyType.PublicKey);
        Map<String,String> map = new HashMap<>();
        map.put("iv",ivResp);
        map.put("encData",encryptResp);
        response.setContentType("application/json");
        response.getWriter().write(JSONObject.toJSONString(map));

    }

    private JSONObject getParamFromRequest(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            StringBuilder stringBuilderResp = new StringBuilder();
            String inputStr;
            while ((inputStr = bufferedReader.readLine()) != null){
                stringBuilderResp.append(inputStr);
            }
            jsonObject = JSONObject.parseObject(stringBuilderResp.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

        return jsonObject;
    }
}
