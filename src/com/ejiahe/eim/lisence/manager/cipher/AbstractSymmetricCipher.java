package com.ejiahe.eim.lisence.manager.cipher;

import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ejiahe.eim.lisence.util.AesException;
import com.ejiahe.eim.lisence.util.LicenseUtil;

/**
 * 加密解密抽象类 对称算法
 * @author focus
 * @date 2015年10月13日
 * @time 下午4:23:25
 */
public abstract class AbstractSymmetricCipher implements ICipher{

	private static Charset CHARSET = Charset.forName("UTF-8");
//	private static final int KeySize = 128;
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	public abstract String getAlgorithmCipher();
	/**
	 * 加密
	 * @param msg 原始消息
	 * @param encodingAesKey 密钥
	 * @return 输出base64编码后的加密消息体
	 * @throws AesException
	 *
	 */
	public String encrypt(String msg, String encodingAesKey){
		try {
			SecretKey secretKey = createSecretKey(encodingAesKey);
			
			Cipher c = Cipher.getInstance(getAlgorithmCipher());
			c.init(Cipher.ENCRYPT_MODE, secretKey);

			byte[] resultBytes = c.doFinal(msg.getBytes(CHARSET));
			
			String encode_encrypt_msg = LicenseUtil.encryptBASE64(resultBytes);
			
			return encode_encrypt_msg;
		} catch (Exception e) {
			logger.error( getAlgorithm()+"加密失败", e);
		}
		return null; 
	}
	
	/**
	 * 解密
	 * @param encode_encrypt_msg base64编码的加密消息体
	 * @param encodingAesKey 密钥
	 * @return 返回原始消息体
	 * @throws AesException
	 *
	 */
	public  String decrypt(String encode_encrypt_msg, String encodingAesKey) {
		
		try {
			SecretKey secretKey = createSecretKey(encodingAesKey);
			
			Cipher c = Cipher.getInstance(getAlgorithmCipher());
			c.init(Cipher.DECRYPT_MODE, secretKey);

			
			byte[] decode_encrypt_msg = LicenseUtil.decryptBASE64(encode_encrypt_msg);
			byte[] resultBytes = c.doFinal(decode_encrypt_msg);
			
			return new String(resultBytes, CHARSET);
		} catch (Exception e) {
			logger.error( getAlgorithm() +" 解密失败", e);
		} 
		return null;
	}
	
	private  SecretKey createSecretKey(String encodingAesKey) {
		SecretKey secretKey = null;
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(getAlgorithm());  
//          kgen.init(KeySize, new SecureRandom(LisenceUtil.decryptBASE64(encodingAesKey)));
			kgen.init(new SecureRandom(LicenseUtil.decryptBASE64(encodingAesKey)));
            secretKey = kgen.generateKey();
		} catch(Exception e){
			logger.error( getAlgorithm() + " 创建秘钥失败", e);
		}
		
		return secretKey;
	}
	
	@Override
	public  String createPrivateKey() {
		
		SecureRandom secureRandom = new SecureRandom();
		try {
			KeyGenerator kg = KeyGenerator.getInstance(getAlgorithm());
			kg.init(secureRandom);
			SecretKey secretKey = kg.generateKey();
			return LicenseUtil.encryptBASE64(secretKey.getEncoded());
		}catch (NoSuchAlgorithmException e) {
			logger.error(" 算法类型不正确" ,e);
		} catch(Exception e){
			logger.error(" 创建私钥失败", e);
		}
		return null;
		
	}

}
