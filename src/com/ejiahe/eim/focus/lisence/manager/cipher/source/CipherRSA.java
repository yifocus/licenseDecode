package com.ejiahe.eim.focus.lisence.manager.cipher.source;

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

import com.ejiahe.eim.focus.lisence.manager.cipher.ICipher;
import com.ejiahe.eim.focus.lisence.util.BaseCoder;
import com.ejiahe.eim.focus.lisence.util.LicenseUtil;

/**
 * <strong>RSA 算法</strong> 
 * 	<p>
 *     <tt>这种算法1978年就出现了，它是第一个既能用于数据加密也能用于数字签名的算法。</tt>
 *     <tt>它易于理解和操作，也很流行。算法的名字以发明者的名字命名：</tt>
 *     <code><strong>Ron Rivest, AdiShamir</strong></code> 和
 *     <code><strong>Leonard Adleman</strong></code>。<p> 
 *      <tt>这种加密算法的特点主要是密钥的变化，上文我们看到DES只有一个密钥。</tt>
 *     <tt>相当于只有一把钥匙，如果这把钥匙丢了，数据也就不安全了。</tt>
 *    <tt>RSA同时有两把钥 匙，公钥与私钥。同时支持数字签名。数字签名的意义在于，对传输过来的数据进行校验。确保数据在传输工程中不被修改</tt>
 *
 *	<p>
 *  <strong>流程分析：</strong> 
 *  <ul>
 *   <li>甲方构建密钥对儿，将公钥公布给乙方，将私钥保留。</li>
 *   <li>甲方使用私钥加密数据，然后用私钥对加密后的数据签名，发送给乙方签名以及加密后的数据；乙方使用公钥、签名来验证待解密数据是否有效，如果有效使用公钥对数据解密。</li>
 *   <li>乙方使用公钥加密数据，向甲方发送经过加密后的数据；甲方获得加密数据，通过私钥解密。</li>
 *  </ul>
 * @author focus
 * @date 2015年10月21日
 * @time 下午1:57:31
 */
public class CipherRSA implements ICipher{

	private Map<String,String> keys;
	public CipherRSA() {
		try {
			Map<String,Object> keyMap =  RSA.initKey();
			keys = new HashMap<String, String>();
			keys.put(RSA.private_key, RSA.getPrivateKey(keyMap));
			keys.put(RSA.public_key, RSA.getPublicKey(keyMap));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public String encrypt(String msg, String privateKey) {

		if(msg == null) return null;
		try {
			return LicenseUtil.encryptBASE64(RSA.encryptByPrivateKey(msg.getBytes(), keys.get(RSA.private_key)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String decrypt(String encode_encrypt_msg, String privateKey) {

		if(encode_encrypt_msg == null) return null;
		try {
			return new String(RSA.decryptByPublicKey(LicenseUtil.decryptBASE64(encode_encrypt_msg), privateKey),RSA.CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String createPrivateKey() {
		return keys.get(RSA.public_key);
	}

	@Override
	public String getAlgorithm() {
		return RSA.KEY_ALGORITHM;
	}
	
	static class RSA extends BaseCoder{
		public static Charset CHARSET = Charset.forName("UTF-8");
		public static final String KEY_ALGORITHM = "RSA";
		public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
		public static final String public_key = "RASPublicKey";
		public static final String private_key = "RSAPrivateKey";
		
		/**
		 * 用私钥对信息生成数字签名
		 *	@param data 加密数据
		 *  @param privateKey 私钥
		 * @author focus
		 * @date 2015年10月19日
		 * @time 下午3:26:50
		 */
		public static String sign(byte[]data,String privateKey) throws Exception{
			// 解密有base64 编码的私钥
			byte[] keyBytes = decryptBASE64(privateKey);
			// 构造PKCS8EncodedKeySpec对象
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			// 指定加密算法
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
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
		public static boolean verify(byte[]data,String publicKey,String sign) throws Exception{
			
			// 解密有base64编码的公钥
			byte[] keyBytes = decryptBASE64(publicKey);
			
			// 构造X509EncodeKeySpec 对象
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			// KEY_ALGORITHM 指定加密算法
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
		public static byte[] decryptByPrivateKey(byte[]data,String key) throws Exception{
			
			byte[] keyBytes = decryptBASE64(key);
			
			// 取得私钥
			PKCS8EncodedKeySpec kpcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
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
		public static byte[] decryptByPublicKey(byte[] data,String key) throws Exception{
			
			byte[] keyBytes = decryptBASE64(key);
			
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key publicKey = keyFactory.generatePublic(keySpec);
			
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			return cipher.doFinal(data);
		}
		
		public static byte[] encryptByPublicKey(byte[] data,String key) throws Exception{
			
			byte[] keyBytes = decryptBASE64(key);
			
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key publicKey = keyFactory.generatePublic(keySpec);
			
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return cipher.doFinal(data);
		}
		
		public static byte[] encryptByPrivateKey(byte[] data,String key) throws Exception{
			
			byte[] keyBytes = decryptBASE64(key);
			
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key privateKey = keyFactory.generatePrivate(keySpec);
			
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			return cipher.doFinal(data);
		}
		
		public static String getPrivateKey(Map<String,Object> keyMap) throws Exception{
			Key key = (Key)keyMap.get(private_key);
			return encryptBASE64(key.getEncoded());
		}
		public static String getPublicKey(Map<String,Object> keyMap) throws Exception{
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
		public static Map<String,Object> initKey()throws Exception{
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
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

}
