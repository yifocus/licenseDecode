package com.ejiahe.eim.lisence.manager.sign.source;

import com.ejiahe.eim.lisence.manager.sign.AbstractSign;



/**
 * sha1 签名加密
 * 
 * @author focus
 * @date 2015年10月13日
 * @time 下午3:16:04
 */
public class SignSHA1 extends AbstractSign {
	
	@Override
	public String createMsgSign(String msg) {
		try {
			return encryptBASE64(encryptSHA(msg.getBytes(CHARSET)));
		} catch (Exception e) {
			error("SHA1 加密失败", e);
		}
		return null;
	}

	public static void main(String[] args) throws Throwable {
		SignSHA1 sha = new SignSHA1();
		System.out.println(sha.createMsgSign("asdfe"));
		for(int i = 0; i < 5; i ++){
			System.out.println(initMacKey());
		}
	}
}
