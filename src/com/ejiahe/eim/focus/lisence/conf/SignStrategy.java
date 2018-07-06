package com.ejiahe.eim.focus.lisence.conf;

import com.ejiahe.eim.focus.lisence.manager.sign.ISign;
import com.ejiahe.eim.focus.lisence.manager.sign.source.SignDefault;
import com.ejiahe.eim.focus.lisence.manager.sign.source.SignMD5;
import com.ejiahe.eim.focus.lisence.manager.sign.source.SignSHA1;


/**
 * 签名的策略
 *
 * @author focus
 * @date 2015年10月8日
 * @time 下午2:40:10
 */
public enum SignStrategy {

	MD5,
	SHA1,
	DEFAULT;
	
	public ISign getSignManager(){
		switch (this) {
		case MD5:
			return new SignMD5();
		case SHA1:
			return new SignSHA1();
		case DEFAULT:
			return new SignDefault();
		default:
			break;
		}
		
		return new SignDefault();
	}
	
	public static SignStrategy parse(String name){
		
		if(name == null || "".equals(name)) return DEFAULT;
		
		for(SignStrategy signStrategy : values()){
			if(signStrategy.name().equals(name)){
				return signStrategy;
			}
		}
		
		return DEFAULT;
	}
}
