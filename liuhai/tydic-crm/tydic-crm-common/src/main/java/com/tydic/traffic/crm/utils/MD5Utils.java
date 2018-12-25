package com.tydic.traffic.crm.utils;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @USER:Think
 * @DATE:2017/7/26
 * md5 加盐 加密算法
 */
public class MD5Utils {
    private static final String CODENAME="MD5";
    /**
     *  获取十六进制字符串形式的MD5摘要
    */
    public static String md5Hex(String src){
        try {
            MessageDigest md5 = MessageDigest.getInstance(CODENAME);
            byte[] bytes = md5.digest(src.getBytes());
            return new String(new Hex().encode(bytes));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    * 自定义生成盐
    * */
    public static String randomSalt(){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append(random.nextInt(99999999)).append(random.nextInt(99999999));
        int len = sb.length();
        if(len < 16){
            for(int i = 0;i< 16 - len;i++){
                sb.append(random.nextInt(9));
            }
        }
        return sb.toString();
    }
    /*
    *
    * */
    public static String generate(String salt,String password){
        if(salt == null || salt == "" || salt.length() < 16){
            throw new IllegalArgumentException("参数盐错误，请输入正确的16位盐设置");
        }
        password = md5Hex(password + salt);
        char[]  chars = new char[48];
        for (int i = 0; i < 48; i += 3){
            chars[i] = password.charAt(i/3 * 2);
            char c = salt.charAt(i/3);
            chars[i + 1] = c;
            chars[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return new String(chars);
    }


}
