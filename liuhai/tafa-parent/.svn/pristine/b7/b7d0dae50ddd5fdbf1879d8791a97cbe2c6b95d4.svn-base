package com.tydic.test.utils.codec;

import com.tydic.traffic.tafa.utils.codec.AESCodec;
import com.tydic.traffic.tafa.utils.codec.DESCodec;
import com.tydic.traffic.tafa.utils.codec.DESedeCodec;
import com.tydic.traffic.tafa.utils.codec.RSACodec;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

/**
 * Created by Think on 2017/6/26.
 */
public class CodeTest {
    private static Logger logger = LoggerFactory.getLogger(CodeTest.class);

    @Test
    public void testDESCodec(){
        String str = "hello,world";
        try {
            logger.debug("源数据 " + str);
            String key = DESCodec.initkey();
            logger.debug("初始化密钥 " + key);
            String enc_str = DESCodec.encrypt(str,key);
            logger.debug("加密后的数据 " + enc_str);
            String des_str = DESCodec.decrypt(enc_str,key);
            logger.debug("解密后数据 " + des_str);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testAESCodec(){
        String str = "hello,world";
        logger.debug("元数据 " + str );
        try {
            byte[] key = AESCodec.initkey();
            logger.debug("加密的密钥 " + key);

            byte[] enc_str = AESCodec.encrypt(str,key);
            logger.debug("加密后的数据 " + enc_str);

            byte[] des_str = AESCodec.decrypt(enc_str,key);

            String dec_str = new String(des_str);
            logger.debug("解密后的数据 " + new String(dec_str));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDESedeCodec(){
        String str = "hello,world";
        logger.debug("源数据 " + str);

        try {
            String initkey =  DESedeCodec.initkey();
            logger.debug("加密的密钥 " + initkey);
            String enc_str = DESedeCodec.encrypt(str,initkey);
            logger.debug("加密后的内容" + enc_str);
            String dec_str = DESedeCodec.decrypt(enc_str,initkey);
            logger.debug("解密后的内容 " + dec_str);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testRSACodec(){
        String str = "hello,world";
        logger.debug("源数据 " + str);
        try {
            Map<String,Object> map = RSACodec.genKeyPair();
            String public_key = RSACodec.getPublicKey(map);
            String private_key = RSACodec.getPrivateKey(map);

            byte[] enc_pri_str = RSACodec.encryptByPrivateKey(str.getBytes(),private_key);
            logger.debug("加密后的内容 " + new String(enc_pri_str));
            byte[] dec_pri_str = RSACodec.decryptByPublicKey(enc_pri_str,public_key);
            logger.debug("解密后的内容 " + new String(dec_pri_str));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testRSACodec2(){
        String str = "hello,world";
        logger.debug("源数据 " + str);
        try {
            Map<String,Object> map = RSACodec.genKeyPair();
            String public_key = RSACodec.getPublicKey(map);
            String private_key = RSACodec.getPrivateKey(map);

            byte[] enc_pri_str = RSACodec.encryptByPublicKey(str.getBytes(),public_key);
            logger.debug("加密后的内容 " + new String(enc_pri_str));
            byte[] dec_pri_str = RSACodec.decryptByPrivateKey(enc_pri_str,private_key);
            logger.debug("解密后的内容 " + new String(dec_pri_str));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
