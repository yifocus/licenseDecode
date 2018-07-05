package com.ejiahe.eim.lisence.manager;

import com.alibaba.fastjson.JSONObject;
import com.ejiahe.eim.lisence.entity.LicenseCipherText;
import com.ejiahe.eim.lisence.entity.LicensePlainText;

public interface ISecurity {

	
	public LicensePlainText getLicensePlainText();
	
	
	/**
	 * 直接读取明文，当是不保存到数据库
	 *
	 * @author focus
	 * @date 2015年10月22日
	 * @time 下午2:41:15
	 */
	public JSONObject getLisencePlianText(String cipherJson);
	
	/**
	 * 解密之后封装到 明文对象中
	 * @author focus
	 * @date 2016年1月28日
	 * @time 上午10:48:22
	 */
	public void parseToPlainLicense(LicenseCipherText cipherText,
			LicensePlainText licensePlainText);
}
