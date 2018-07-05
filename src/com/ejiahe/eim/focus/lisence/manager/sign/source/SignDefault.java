package com.ejiahe.eim.focus.lisence.manager.sign.source;

import com.ejiahe.eim.focus.lisence.manager.sign.AbstractSign;


/**
 * 如果都没有指定加密策略的时候使用默认的加密方式
 * 
 * @author focus
 * @date 2015年10月13日
 * @time 下午3:40:36
 */
public class SignDefault extends AbstractSign {

	@Override
	public String createMsgSign(String msg) {
		// md5(sha1(msg))
		if(msg == null) return null;
		 try {
			return encryptBASE64(encryptMD5(encryptSHA(msg.getBytes(CHARSET))));
		} catch (Exception e) {
			error("默认加密方式失败", e);
		}
		return null;
	}

}
