package com.tydic.traffic.tafa.utils.codec;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * ClassName:DESCodec <br/>
 * Function: DES对称加密算法<br/>
 */
public class DESCodec {

	// 算法名称
	public static final String KEY_ALGORITHM = "DES";

	// 算法名称/加密模式/填充方式
	// DES共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
	public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";
	
	// 最小密钥长度
	private static final int KEY_LENGTH = 12;

	/**
	 * 
	 * initkey:生成密钥. <br/>
	 *
	 */
	public static String initkey() throws NoSuchAlgorithmException {
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM); // 实例化密钥生成器
		kg.init(56); // 初始化密钥生成器
		SecretKey secretKey = kg.generateKey(); // 生成密钥
		return Base64.encodeBase64String(secretKey.getEncoded()); // 获取二进制密钥编码形式
	}

	/**
	 * 
	 * toKey:转换密钥. <br/>
	 *
	 */
	private static Key toKey(byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
		DESKeySpec dks = new DESKeySpec(key); // 实例化Des密钥
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM); // 实例化密钥工厂
		SecretKey secretKey = keyFactory.generateSecret(dks); // 生成密钥
		return secretKey;
	}


	/**
	 * encrypt：加密数据
	 *
	 */
	public static String encrypt(String data, String key) throws Exception {
		if(null == data){
			throw new IllegalArgumentException("非法的参数,加密字符串不能为空");
		}	
		if(null == key || key.length() < KEY_LENGTH){
			throw new IllegalArgumentException("非法的参数,密钥长度不能小于" + DESCodec.KEY_LENGTH);
		}
		Key k = toKey(Base64.decodeBase64(key)); // 还原密钥
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM); // 实例化Cipher对象，它用于完成实际的加密操作
		cipher.init(Cipher.ENCRYPT_MODE, k); // 初始化Cipher对象，设置为加密模式
		return Base64.encodeBase64String(cipher.doFinal(data.getBytes())); // 执行加密操作。加密后的结果通常都会用Base64编码进行传输
	}

	/**
	 * decrypt：解密数据
	 *
	 * @throws IllegalBlockSizeException
	 */
	public static String decrypt(String data, String key) throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		if(null == data){
			throw new IllegalArgumentException("非法参数，解密的数据不能为空");
		}	
		if(null == key || key.length() < KEY_LENGTH){
			throw new IllegalArgumentException("非法参数，解密的KEY不能为空并且长度不能少于" + KEY_LENGTH);
		}
		Key k = toKey(Base64.decodeBase64(key));
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k); // 初始化Cipher对象，设置为解密模式
		return new String(cipher.doFinal(Base64.decodeBase64(data))); // 执行解密操作
	}

}
