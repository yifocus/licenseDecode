package com.ejiahe.eim.lisence.manager.cipher.source;


import com.ejiahe.eim.lisence.manager.cipher.ICipher;
import com.ejiahe.eim.lisence.util.AesBase64Cipher;
import com.ejiahe.eim.lisence.util.AesException;

/**
 * AES加密/解密器;
 * <p>
 * <ul>
 * <li>字符串AES加密后经base64转码输出;
 * <li>密钥为base64编码密钥;
 * 
 */
public class CipherAES implements ICipher{


	@Override
	public String encrypt(String msg, String privateKey) {
		try{
			return AesBase64Cipher.encrypt(msg,privateKey);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}


	}

	@Override
	public String decrypt(String encode_encrypt_msg, String privateKey) {
		try {
			return AesBase64Cipher.decrypt(encode_encrypt_msg,privateKey);
		} catch (AesException e) {
			e.printStackTrace();
			return   null;
		}
	}

	@Override
	public String createPrivateKey() {
		return AesBase64Cipher.getRandomEncodingAesKey();
	}

	@Override
	public String getAlgorithm() {
		return null;
	}
}
