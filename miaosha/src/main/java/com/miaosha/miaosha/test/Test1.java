package com.miaosha.miaosha.test;

import com.xiaoleilu.hutool.date.DateUtil;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: LinXueRui
 * @Date: 2019/9/3 18:08
 * @Desc:
 */
public class Test1 {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        System.out.println(encodeByMD5("123456"));
        System.out.println(decodeByMD5("4QrcOUm6Wau+VuBX8g+IPg=="));
        String a = "UmsPermission".toLowerCase();
        System.out.println(a);
    }

    public static String encodeByMD5(String str) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        //加密字符串
        return base64Encoder.encode(md5.digest(str.getBytes(StandardCharsets.UTF_8)));
    }

    public static String decodeByMD5(String encodePWD) throws NoSuchAlgorithmException, IOException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Decoder base64Decoder = new BASE64Decoder();
        //加密字符串
        String string = new String(base64Decoder.decodeBuffer(encodePWD), StandardCharsets.UTF_16LE);
        System.out.println(string);
        return "";
    }

}
