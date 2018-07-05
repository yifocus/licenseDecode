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

	MD5(SignMD5.class),
	SHA1(SignSHA1.class),
	DEFAULT(SignDefault.class);
	private ISign signManager;
	private SignStrategy(Class<? extends ISign> clzz){
		try {
			signManager = clzz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public ISign getSignManager(){
		return signManager;
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
