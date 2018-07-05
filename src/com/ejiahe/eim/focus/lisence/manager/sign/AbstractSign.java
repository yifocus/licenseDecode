package com.ejiahe.eim.focus.lisence.manager.sign;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ejiahe.eim.focus.lisence.util.BaseCoder;

public abstract class AbstractSign extends BaseCoder implements ISign{

	protected Logger logger = LoggerFactory.getLogger(getClass());
	protected void info(String msg){
		logger.info(msg);
	}
	protected void error(String err,Throwable e){
		logger.error(err, e);
	}
	
	public static Charset CHARSET = Charset.forName("UTF-8");
	// 默认私钥
	public static String default_private_key = ""; 
	
	static {
		try {
			default_private_key = encryptBASE64(encryptMD5("diyiqixin".getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
