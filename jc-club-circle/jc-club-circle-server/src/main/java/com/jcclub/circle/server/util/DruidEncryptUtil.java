package com.jcclub.circle.server.util;

import com.alibaba.druid.filter.config.ConfigTools;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/*
* 数据库加密
* */
public class DruidEncryptUtil {


    private static String publicKey;

    private static String privateKey;

    static {
        try {
            String[] strings = ConfigTools.genKeyPair(512);
            publicKey = strings[1];
            System.out.println("publicKey = " + publicKey);
            privateKey = strings[0];
            System.out.println("privateKey = " + privateKey);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String plainText) throws Exception {
        String encrypt = ConfigTools.encrypt(privateKey, plainText);
        System.out.println("encrypt = " + encrypt);
        return encrypt;
    }

    public static String decrypt(String encryptText) throws Exception {
        String decrypt = ConfigTools.decrypt(publicKey, encryptText);
        System.out.println("decrypt = " + decrypt);
        return decrypt;
    }

    public static void main(String[] args) throws Exception {
        String encrypt = encrypt("123456");
        System.out.println("encrypt = " + encrypt);
    }
}
