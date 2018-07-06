package com.ejiahe.eim.focus.lisence.conf;

import com.ejiahe.eim.focus.lisence.manager.cipher.ICipher;
import com.ejiahe.eim.focus.lisence.manager.cipher.source.Cipher3DES;
import com.ejiahe.eim.focus.lisence.manager.cipher.source.CipherAES;
import com.ejiahe.eim.focus.lisence.manager.cipher.source.CipherDES;
import com.ejiahe.eim.focus.lisence.manager.cipher.source.CipherRSA;


/**
 *	加密策略
 * 
 * @author focus
 * @date 2015年9月29日
 * @time 下午5:17:21
 */
public enum CipherStrategy {
	_3DES,
	DES,
	ESA,
	DEFAULT,
	RSA;
	/**
	 * 获取对称算法加密解密的逻辑逻辑服务实例
	 *
	 * @author focus
	 * @date 2015年10月8日
	 * @time 下午2:09:15
	 */
	public ICipher getChiperManager(){
		switch (this) {
		case _3DES:
			return new Cipher3DES();
		case DES:
			return new CipherDES();
		case ESA:
			return new CipherAES();
		case DEFAULT:
			return new CipherAES();
		case RSA:
			return new CipherRSA();
		default:
			break;
		}
		
		return new CipherAES();
	}
	
	public static CipherStrategy parse(String name){
	
		if(name == null || 
				"".equals(name)) 
			return DEFAULT;
		
		for(CipherStrategy cipherStrategy : values()){
			if(name.equals(cipherStrategy.name())){
				return cipherStrategy;
			}
		}
		return DEFAULT;
	}
}
