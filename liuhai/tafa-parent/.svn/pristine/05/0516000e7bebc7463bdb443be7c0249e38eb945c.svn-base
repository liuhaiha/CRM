package com.tydic.traffic.tafa.utils.codec;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * ClassName:DESedeCodec <br/>
 * Function: DES对称加密算法 . <br/>
 */
public class DESedeCodec {

	public static final String KEY_ALGORITHM = "DESede";

	public static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";

	// 最小密钥长度
	private static final int KEY_LENGTH = 32;

	/**
	 * initkey:生成密钥. <br/>
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String initkey() throws NoSuchAlgorithmException {
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		kg.init(168);
		SecretKey secretKey = kg.generateKey();
		return Base64.encodeBase64String(secretKey.getEncoded());
	}

	/**
	 * toKey: 转换密钥. <br/>
	 *
	 * @param key
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws Exception
	 * @since 1.0
	 */
	private static Key toKey(byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
		DESedeKeySpec dks = new DESedeKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(dks);
		return secretKey;
	}

	/**
	 * encrypt：加密数据
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return 加密后的数据
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws NoSuchPaddingException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public static String encrypt(String data, String key) throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		if(null == data){
			throw new IllegalArgumentException("非法的传入参数，加密内容不能为空");
		}	
		if(null == key || key.length() < KEY_LENGTH){
			throw new IllegalArgumentException("非法的传入参数，加密密钥不能为空并且长度不能少于" + KEY_LENGTH);
		}
		Key k = toKey(Base64.decodeBase64(key));
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, k);
		return Base64.encodeBase64String(cipher.doFinal(data.getBytes()));
	}

	/**
	 * decrypt：解密数据
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return 解密后的数据
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws NoSuchPaddingException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public static String decrypt(String data, String key) throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		if(null == data){
			throw new IllegalArgumentException("非法的传入参数，解密内容不能为空");
		}	
		if(null == key || key.length() < KEY_LENGTH){
			throw new IllegalArgumentException("非法的传入参数，密钥不能为空并且长度不能少于" + KEY_LENGTH);
		}
		Key k = toKey(Base64.decodeBase64(key));
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k);
		return new String(cipher.doFinal(Base64.decodeBase64(data)));
	}

}