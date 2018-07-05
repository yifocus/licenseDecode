package com.ejiahe.eim.lisence;

import com.alibaba.fastjson.JSONObject;
import com.ejiahe.eim.lisence.entity.LicenceCreateText;
import com.ejiahe.eim.lisence.entity.LicenseCipherText;
import com.ejiahe.eim.lisence.entity.LicenseCipherText.LicenseStatus;
import com.ejiahe.eim.lisence.entity.LicensePlainText;

/**
 * 许可对外提供的服务的接口：
 * 1 创建许可
 * 2 加载许可
 * 3 获取许可明文信息
 * 4 获取许可密文信息
 * 5 获取许可Id ： nonce
 * 6 获取许可的状态
 * 
 * @author focus
 * @date 2015年10月10日
 * @time 上午10:46:03
 */

public interface ILicenseService {

	
	
	
	/**
	 * 生成许可
	 * @param lisence   许可输入信息
	 * @param strategy  策略
	 * @return
	 * @author focus
	 * @date 2015年10月10日
	 * @time 上午10:26:31
	 */
	public String createLisence(LicenceCreateText lisence);
	/**
	 * 加载许可
	 * @param cipherJson 许可文件中的内容： 加密信息
	 * @author focus
	 * @date 2015年10月10日
	 * @time 上午10:44:39
	 */
	public LicenseCipherText getCipherLicenseText(String cipherJson);
	
	/**
	 * 取得许可的明文
	 * @return
	 * @author focus
	 * @date 2015年10月10日
	 * @time 上午10:43:42
	 */
	public LicensePlainText getLicensePlainText();
	
	/**
	 * 获取当前状态license 唯一标识
	 * @author focus
	 * @date 2018年7月4日 下午3:46:40
	 * @return
	 */
	public String getNonce();
	
}
