package com.ejiahe.eim.focus.lisence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.ejiahe.eim.focus.lisence.entity.LicenceCreateText;
import com.ejiahe.eim.focus.lisence.entity.LicenseCipherText;
import com.ejiahe.eim.focus.lisence.entity.LicensePlainText;
import com.ejiahe.eim.focus.lisence.manager.ISecurity;
import com.ejiahe.eim.focus.lisence.manager.LicenseManager;
import com.ejiahe.eim.focus.lisence.manager.Security;
import com.ejiahe.eim.focus.lisence.manager.nonce.NonceManager1;

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

public class LicenseService implements ILicenseService{

	private ISecurity security;
	private LicenseManager manager;
	private NonceManager1 nonceManager;
	private DecodeLicenseParam param;
	
	private static final String LICENSE_SWITCH = "eim.plugins.license.isDefaultGenerate";
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	
	/**
	 * 获取nonce管理的逻辑类
	 *
	 * @author focus
	 * @date 2015年11月2日
	 * @time 下午3:23:39
	 */
	private NonceManager1 getNonceManager(){
		return nonceManager;
	}
	
	/**
	 * 初始化
	 */
	public LicenseService(DecodeLicenseParam param){
		this.param = param;
		security = new Security(param);
		manager = new LicenseManager();
		nonceManager = new NonceManager1(param);
	}
	
	/**
	 * 生成许可
	 * @param lisence   许可输入信息
	 * @param strategy  策略
	 * @return
	 * @author focus
	 * @date 2015年10月10日
	 * @time 上午10:26:31
	 */
	public String createLisence(LicenceCreateText lisence){
		return manager.createLisence(lisence);
	}
	
	/**
	 * 	{
	 *		"lisence": "gaithiRGCVTqWR750F+9lPLORY1IWpTw6SEBvnz3eWAaptDwfTE+HYoHpxsLDMGZQL7iwk8H64tZlCqSUpcfbPKN+jkb48C0KnBClXRRqtjG6LgVnae7gg4B4Q1WPfUxnMjwsrwphw3Wz2HTnkoD0WIJha8FuCcof9h8osqKkBQ=",
	 *		"nonce": "Vdu2u6P8gy4ajGhW23E/UQ==",
	 *		"strategy": "RSA&DEFAULT,MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCNIJ6SqRF6HXLbY8P/U9W5QzPcaf6IpTdzw6zIpm1HITbrV2+e57EEHj2//Qdbjqk1Oe9/IFuK79+5hFTHgYHNsIfke4gB3blQ8EwKPUY7KdLVdm23x2HB7Csb68BRC3oUV59KuGJtzc1nSa1yV/qHufuJ6CbRNheUItsHJwKzRQIDAQAB",
	 *		"msgSign": "msI08wuWEuygymAsy64oLw=="
	 *	}
	 * 加载许可
	 * @param cipherJson 许可文件中的内容： 加密信息
	 * @author focus
	 * @date 2015年10月10日
	 * @time 上午10:44:39
	 */
	public LicenseCipherText getCipherLicenseText(String cipherJson){
		return manager.getLisenceCipherTextByLiceseJson(cipherJson);
	}
	
	/**
	 * 取得许可的明文
	 * @return
	 * @author focus
	 * @date 2015年10月10日
	 * @time 上午10:43:42
	 */
	public LicensePlainText getLicensePlainText(){
		return security.getLicensePlainText();
	}
	
	/**
	 * 根据密文信息获取许可明文
	 *
	 * @author focus
	 * @date 2015年11月2日
	 * @time 下午3:28:09
	 */
	public JSONObject getLisencePlianText(String cipherJson){
		return security.getLisencePlianText(cipherJson);
	}
	
	
	public String getNonce(){
		return getNonceManager().getLocalNonce(); 
	}


}
