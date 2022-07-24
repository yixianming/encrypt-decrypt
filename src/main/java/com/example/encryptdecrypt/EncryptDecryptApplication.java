package com.example.encryptdecrypt;

import javassist.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;

@SpringBootApplication
public class EncryptDecryptApplication {

    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException {
       /* ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.get("com.example.encryptdecrypt.controller.IndexController");
        CtMethod index = ctClass.getDeclaredMethod("index");
        index.insertBefore("log.info(\"进来了\");");
        ctClass.writeFile();
        ctClass.toClass();
        ctClass.detach();*/
        SpringApplication.run(EncryptDecryptApplication.class, args);
    }

}
