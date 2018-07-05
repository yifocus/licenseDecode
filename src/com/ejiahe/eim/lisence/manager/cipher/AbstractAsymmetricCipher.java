package com.ejiahe.eim.lisence.manager.cipher;

import java.nio.charset.Charset;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import com.ejiahe.eim.lisence.util.BaseCoder;

public abstract class AbstractAsymmetricCipher extends BaseCoder implements ICipher{
	
	public static Charset CHARSET = Charset.forName("UTF-8");
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	public static  String public_key = "RASPublicKey";
	public static  String private_key = "RSAPrivateKey";
	
	/**
	 * 用私钥对信息生成数字签名
	 *	@param data 加密数据
	 *  @param privateKey 私钥
	 * @author focus
	 * @date 2015年10月19日
	 * @time 下午3:26:50
	 */
	public  String sign(byte[]data,String privateKey) throws Exception{
		// 解密有base64 编码的私钥
		byte[] keyBytes = decryptBASE64(privateKey);
		// 构造PKCS8EncodedKeySpec对象
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		// 指定加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(getAlgorithm());
		// 取得私钥对象
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		
		// 用私钥对信息生成数字签名
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data);
		return encryptBASE64(signature.sign());
	}

	/**
	 * 校验数字签名 
	 *
	 * @author focus
	 * @throws Exception 
	 * @date 2015年10月19日
	 * @time 下午3:27:48
	 */
	public  boolean verify(byte[]data,String publicKey,String sign) throws Exception{
		
		// 解密有base64编码的公钥
		byte[] keyBytes = decryptBASE64(publicKey);
		
		// 构造X509EncodeKeySpec 对象
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		
		KeyFactory keyFactory = KeyFactory.getInstance(getAlgorithm());
		// getAlgorithm() 指定加密算法
		PublicKey pubKey = keyFactory.generatePublic(keySpec);
		
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(data);
		
		// 验证签名是否正常
		return signature.verify(decryptBASE64(sign));
		
	}
	
	/**
	 * 私钥解密
	 * @author focus
	 * @date 2015年10月19日
	 * @time 下午3:45:39
	 */
	public  byte[] decryptByPrivateKey(byte[]data,String key) throws Exception{
		
		byte[] keyBytes = decryptBASE64(key);
		
		// 取得私钥
		PKCS8EncodedKeySpec kpcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(getAlgorithm());
		Key privateKey = keyFactory.generatePrivate(kpcs8KeySpec);
		
		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * 解密<br>
	 * 公钥解密
	 * @author focus
	 * @throws Exception 
	 * @date 2015年10月19日
	 * @time 下午3:45:56
	 */
	public  byte[] decryptByPublicKey(byte[] data,String key) throws Exception{
		
		byte[] keyBytes = decryptBASE64(key);
		
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(getAlgorithm());
		Key publicKey = keyFactory.generatePublic(keySpec);
		
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}
	
	public  byte[] encryptByPublicKey(byte[] data,String key) throws Exception{
		
		byte[] keyBytes = decryptBASE64(key);
		
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(getAlgorithm());
		Key publicKey = keyFactory.generatePublic(keySpec);
		
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}
	
	public  byte[] encryptByPrivateKey(byte[] data,String key) throws Exception{
		
		byte[] keyBytes = decryptBASE64(key);
		
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(getAlgorithm());
		Key privateKey = keyFactory.generatePrivate(keySpec);
		
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}
	
	public  String getPrivateKey(Map<String,Object> keyMap) throws Exception{
		Key key = (Key)keyMap.get(private_key);
		return encryptBASE64(key.getEncoded());
	}
	public  String getPublicKey(Map<String,Object> keyMap) throws Exception{
		Key key = (Key)keyMap.get(public_key);
		return encryptBASE64(key.getEncoded());
	}
	
	/**
	 * 初始化秘钥
	 *
	 * @author focus
	 * @date 2015年10月19日
	 * @time 下午3:26:29
	 */
	public  Map<String,Object> initKey()throws Exception{
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(getAlgorithm());
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		// 公钥
		Key  publicKey = keyPair.getPublic();
		// 私钥
		Key privateKey = keyPair.getPrivate();
		
		Map<String,Object> keyMap = new HashMap<String, Object>();
		keyMap.put(public_key, publicKey);
		keyMap.put(private_key, privateKey);
		return keyMap;
	}
}
