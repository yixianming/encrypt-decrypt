package com.example.encryptdecrypt.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.crypto.symmetric.AES;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.encryptdecrypt.annotation.ApiEncAndDec;
import com.example.encryptdecrypt.dto.EncAndDecReqDTO;
import com.example.encryptdecrypt.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.security.SecurityUtil;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.smartcardio.ATR;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Map;
import java.util.UUID;

@Controller
@Slf4j
public class IndexController {

    //@ApiEncAndDec
    @RequestMapping("/dispatcher")
    public void dispatcher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getHeader("uri");
        request.getRequestDispatcher(uri).forward(request,response);
    }

    @RequestMapping("/index")
    public String index(ModelMap map){
        return "index";
    }

    @PostMapping("/submit")
    @ResponseBody
    @ApiEncAndDec
    public String submit(User user){
        return JSON.toJSONString(user);
    }

    @ResponseBody
    @RequestMapping("/generateRsaKeyPair")
    public String generateRsaKeyPair() throws NoSuchAlgorithmException {
        JSONObject jsonObject = new JSONObject();
        RSA rsa = SecureUtil.rsa();
        jsonObject.put("publicKey",rsa.getPublicKeyBase64());
        jsonObject.put("privateKey",rsa.getPrivateKeyBase64());
        return JSON.toJSONString(jsonObject);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        //ipgs端
        /*StringBuffer stringBuffer = new StringBuffer("12345678900000001234567890000000");
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJccOdYyOLf69H9zQ11xR1gLUAf7PYVF1FOKmL1koF1F3O3bBkNQiKlwH+Q2H8aGtRX+AJx6j3ztuT5an9QTCbHi59U8F4w0g+nBoUlJG79AW/OAszdNnUlr7D7WwBnJeLBgn2d9QfA0+nQ+KpPZGrmUcIUnDk/CA+CeeUl2v+sRAgMBAAECgYB2tAuTAZygmhjazZbLnqLmvOw/eNNZEAO9JJDbpmJ7IthsWLPQyUCxqYzD3uiTMVqrTf747SYugygTnWkUVWg1BQ5JQh/Frf7ESRN6wwR06fp0Rk78LkI8LRyc6PfGayiXqz8NtdbEy1Pg/J2889jv2Zgzc647m40EUCo/ltjPqQJBAOst5uAez7E+xVdlU/bX3DaOgzR/3zEvJbNy8Fu9VfKatjwu07XLDlBduL8BvnGe6WKNhwFrxk3PztyVs7AOk58CQQCkfPw1/uItLphTzIhmFwOtJV+BnrrzUIpWssy/RXsJIA8bkKVWhie1P+6VNuNrSxAzHqEKWMbYy9WLCc5HioNPAkEAnOgyKoPEFECKD4Y2X/GjJe8tUMCj27/WCoT8ImkPR967CSpA7AB/G1V8Zku2kT3x/mPomCUc2Ft2a6uhiCwhhwJAB4/rdHwMX/FldWzQ1Ii4VYyDUI1AoRER2xyLRzvlhSzhJO5Ie6rdRnry+A8282bXDtKYqsYcFjmAzsybnDRlBwJBAOqj68+xfXiTOvYN7jAAhSt7xFRQYgWspaH81d3zjxY8FH/Pi7RyGb1tEw+XdnjryanHxcQRJXnBA8Xgpsz0m34=";
        String publicLsy = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCXHDnWMji3+vR/c0NdcUdYC1AH+z2FRdRTipi9ZKBdRdzt2wZDUIipcB/kNh/GhrUV/gCceo987bk+Wp/UEwmx4ufVPBeMNIPpwaFJSRu/QFvzgLM3TZ1Ja+w+1sAZyXiwYJ9nfUHwNPp0PiqT2Rq5lHCFJw5PwgPgnnlJdr/rEQIDAQAB";
        RSA rsa = SecureUtil.rsa(null, publicLsy);
        RSA rsa1 = SecureUtil.rsa(privateKey, null);
           String encrypt = rsa.encryptBase64(stringBuffer.toString().getBytes(StandardCharsets.UTF_8), KeyType.PublicKey);
        System.out.println(encrypt);
            byte[] decrypt = rsa1.decrypt(encrypt, KeyType.PrivateKey);
        System.out.println(new String(decrypt));
        AES aes = new AES(Mode.ECB, Padding.ISO10126Padding,"12345678900000001234567890000000".getBytes(StandardCharsets.UTF_8));

        System.out.println(aes.encryptBase64("{\"name\":\"仪贤明\",\"age\":11}"));
        byte[] decrypt1 = aes.decrypt(aes.encryptBase64("{\"name\":1,\"age\":11}"));
        System.out.println(new String(decrypt1));*/

       //pc端
        StringBuffer stringBuffer = new StringBuffer("12345678900000001234567890000000");
        String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAIFruyergV+OvAYRe9sKKn6rBZHmy5UM9d04Ag88c4Upz0ogoClhs7HsYnJByrc+m9ty3yCwiS/Vh9FZLj5o35QQSq62+9m6jOD9wJNlfEBz7QA+nuyQP/Jeup61D7ySVHgdLzsh3E87REgdUFFT5eaFSMPzycCUOPQmU7sRe2pVAgMBAAECgYB74Kxxmj3pQ2xBurZ8wMxQFk7siqdO8ercZmHCR7CjQ5PAdl4XfgdMROEuUD9DCtCQSvG8Dn4hpRetkNksr6FgcWQLui77C8FgwrNfU4pk7jZmwdEC9HhrpcGYwmev7iHrD1uwpujtfEfiH+jCsmxyjJx6CVvmpeMiOinQhNZygQJBALt7vvZfAgLKhKCKuoASzo8gSKcc350Xc+8HZyrayBdQHz3U/idPTgwtHJxzGuxJSG9Twlh47XqK09W3LKak9AUCQQCwt93DMnZ3CpFNYj10up0b3wU+beVCpFW7XVOVmWDhXEd/pGoBfXyzwfUWoe4A1v7/Icvm0oKRLBbf1mfRFj4RAkEAs+WQnk0Z293Bl16miCADrhh3PksZUl5fsxtyZg+acaxC3gzTdprFNC/RRVm5msLSy2gektAuZyhxDZ//K0QalQJBAKn8MxTQNaPMRnhpRzRBTV7B6AllLGIuMUlJPF1qbAZa/WuKe21zh8BhkZ9zo7yrEh3YJIqjc3SxNE+brcEx5AECQQCzLnWZNl6YMJRGj4DrvSH4hS8aTaa3dzmzbm/bHmbw13mOnPa0b/CDlFsS7djcKiWGJQ28VDmRwe0ocPxwjHci";
        String publickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCBa7snq4FfjrwGEXvbCip+qwWR5suVDPXdOAIPPHOFKc9KIKApYbOx7GJyQcq3Ppvbct8gsIkv1YfRWS4+aN+UEEqutvvZuozg/cCTZXxAc+0APp7skD/yXrqetQ+8klR4HS87IdxPO0RIHVBRU+XmhUjD88nAlDj0JlO7EXtqVQIDAQAB";
        RSA rsa = SecureUtil.rsa(null, publickey);
        RSA rsa1 = SecureUtil.rsa(privateKey, null);
        String encrypt = rsa.encryptBase64(stringBuffer.toString().getBytes(StandardCharsets.UTF_8), KeyType.PublicKey);
        System.out.println(encrypt);
        byte[] decrypt = rsa1.decrypt("QYnhcnxDJMFRmxWNS6TkMoobvAMsDmW6JUmTyCqA40MzHJte/YTqYxEsTKKM1vWmlNhlN6D1W/un9oGmcRAx/A9bfLOsiu4xvdup3ORAirDS08b+WFDneFB9K8DIAL2lZPhwo9nkad+un/gwMBn7xDYY9fpmcB0tt781E/gJwwg=", KeyType.PrivateKey);
        System.out.println(new String(decrypt));
        AES aes = new AES(Mode.ECB, Padding.ISO10126Padding,"0cc33716d6bc44248210b0a34568ecc3".getBytes(StandardCharsets.UTF_8));

        System.out.println(aes.encryptBase64("{\"name\":\"仪贤明\",\"age\":11}"));
        byte[] decrypt1 = aes.decrypt(aes.encryptBase64("{\"name\":1,\"age\":11}"));
        System.out.println(new String(decrypt1));
        byte[] decrypt2 = aes.decrypt("2paT+zJjM6OtQl/pIqfb3OzMK6SNn5/5rn2JDZoBG3AnuW7/dQBpnmq5WQWtE4H6");
        System.out.println(new String(decrypt2));
    }
}

