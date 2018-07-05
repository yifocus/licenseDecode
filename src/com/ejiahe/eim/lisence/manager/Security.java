package com.ejiahe.eim.lisence.manager;

import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.ejiahe.eim.lisence.DecodeLicenseParam;
import com.ejiahe.eim.lisence.conf.SignStrategy;
import com.ejiahe.eim.lisence.constant.LicenseConstant;
import com.ejiahe.eim.lisence.constant.TimeConstant;
import com.ejiahe.eim.lisence.entity.LicenseCipherText;
import com.ejiahe.eim.lisence.entity.LicensePlainText;
import com.ejiahe.eim.lisence.manager.cipher.ICipher;
import com.ejiahe.eim.lisence.manager.nonce.NonceManager1;
import com.ejiahe.eim.lisence.manager.sign.ISign;
import com.ejiahe.eim.lisence.manager.sign.source.SignMD5;
import com.ejiahe.eim.lisence.util.LicenseUtil;

/**
 * 用户提供外部的接口， 验证接口
 * 
 * @author focus
 * @date 2015年10月8日
 * @time 下午3:23:32
 */

public class Security implements ISecurity {

	/*
	 * <li> 701          签名验证失败
	 * <li> 0     License验证成功
	 * <li> 711         解析密文失败
	 * <li> 712   License过期
	 * <li> 713         无效的日期
	 * <li> 714   License快到期了
	 * <li> 715   License快Nonce 错误
	 * <li> 716        无license
	 * 
	 */
	private static final int SIGN_FAIL = LicenseConstant.SIGN_FAIL;
	private static final int SUCCESS = LicenseConstant.SUCCESS;
	private static final int DECODE_FAIL = LicenseConstant.DECODE_FAIL;
	private static final int LISENCE_EXPIRE = LicenseConstant.LISENCE_EXPIRE;
	private static final int INVALID_DATE = LicenseConstant.INVALID_DATE;
	private static final int NEAR_EXPIREDATE = LicenseConstant.NEAR_EXPIREDATE;
	private static final int NONCE_ERR = LicenseConstant.NONCE_ERR;
	private static final int NO_LICENSE = LicenseConstant.NO_LICENSE;
	
	private static final String NEAR_EXPIREDATE_KEY = "license.near.expiredate.key";
	private Logger log = LoggerFactory.getLogger(getClass());
	private NonceManager1 nonceManager;
	private DecodeLicenseParam param;
	public Security(DecodeLicenseParam param){
		this.param = param;
		nonceManager = new  NonceManager1(param);
	}
	
	
	
	
	private boolean isValidSign(LicenseCipherText cipherText){
		
		// 1  先取出密文
		// 2  取得策略
		// 3  重新生成签名
		// 4   验证签名
		if(cipherText == null){
			cipherText = param.getCipher();
		}
		if(cipherText == null){
			log.debug("密文数据加载出错， 请检测数据库中有数据");
			return false;
		}
		// 签名策略
		SignStrategy signStrategy = cipherText.signStrategy();
		// 签名原始消息字符串
		StringBuilder signSource = new StringBuilder();
		signSource.append(cipherText.getNonce());
		signSource.append(cipherText.getLisence());
		signSource.append(cipherText.getStrategy());
		signSource.append(",");
		signSource.append(cipherText.getPrivateKey());
		signSource.append(cipherText.getNonce());
		signSource.append(LicenseConstant.LICENSE_CONTROL_VERSION_SIGN_SUFFIX);
		
		String originalMsgSign = new SignMD5().createMsgSign(signSource.toString());
		
		// new SignMD5().createMsgSign(nonce + lisence + strategyTemp + nonce + LicenseConstant.LICENSE_CONTROL_VERSION_SIGN_SUFFIX);
		if(LicenseUtil.isEmpty(signStrategy,originalMsgSign)){
			log.info("数据解析错误， 请检查数据库中的数据是否正常 : {}",cipherText);
			return false;
		}
		// 签名
		ISign sign = signStrategy.getSignManager();
		// 签名验证
		boolean isValid = sign.createMsgSign(originalMsgSign).equals(cipherText.getMsgSign());
		if(isValid){
			log.debug("签名验证成功");
		}else{
			log.debug("签名验证失败， 请检查数据库中的数据是否是正常的{}",cipherText);
		}
		
		log.debug("签名加密策略： " + signStrategy.name());
		return isValid;
	}
	

	private LicensePlainText getLisencePlainText(LicenseCipherText cipherText) {
		
		// 1  判断签名是否正确
		// 2  取得密文
		// 3  通过策略解密
		// 4  验证License是否过期
		
		LicensePlainText licensePlainText = new LicensePlainText();
		
		if(isValidSign(cipherText) == false){
			log.info("License签名验证失败！");
			licensePlainText.setLicenseStatus(SIGN_FAIL);
			return licensePlainText;
		}
		
		if(cipherText == null){
			cipherText = param.getCipher();
		}
		
		licensePlainText.setId(cipherText.getId());

		// 判断是否是license 的类型通过扩展字段来判断
		String fieldExtStr = cipherText.getFieldExtStr();
		if(fieldExtStr != null){
			try {
				
				JSONObject jsonExt = JSONObject.parseObject(fieldExtStr);
				if(jsonExt.containsKey(LicenseConstant.LICENSE_TYPE)){
					licensePlainText.setLicenseType(jsonExt.getInteger(LicenseConstant.LICENSE_TYPE));
				}
			} catch (Exception e) {
				log.error("License 的扩展字段处理出错！",e);
			}
		}
		
		// 验证NonceID
		String nonce = cipherText.getNonce();
		if(nonceManager.compareNonce(nonce) == false){ // TODO
			log.info("nonce 与本服务器不对应，nonceID 错误");
			licensePlainText.setLicenseStatus(NONCE_ERR);
			return licensePlainText;
		}
		
		// 添加明文的nonce
		licensePlainText.setNonce(nonce);
		boolean isNearExpireDate = false;
		try {
			parseToPlainLicense(cipherText, licensePlainText);
			// 解析过期时间， 同时判断 是否快到过期时间: 默认过期时间是 30 天
			if(isNearExpire(licensePlainText.getExpireDate())){
				isNearExpireDate = true;
			}
		} catch (Exception e) {
			log.error("License解析   Lisence密文错误",e);
			licensePlainText.setLicenseStatus(DECODE_FAIL);
			return licensePlainText;
		}
		
		Date expireDate = licensePlainText.getExpireDate();
		if(expireDate == null){
			log.debug("License日期格式不正确！");
			licensePlainText.setLicenseStatus(INVALID_DATE);
			return licensePlainText;
		}
		
		if(isNearExpireDate){
			licensePlainText.setLicenseStatus(NEAR_EXPIREDATE);
		}else{
			licensePlainText.setLicenseStatus(SUCCESS);
		}
		
		log.debug("License解密成功，status：" + licensePlainText.getLicenseStatus());
		return licensePlainText;
	}

	/**
	 * 解密之后封装到 明文对象中
	 * @author focus
	 * @date 2016年1月28日
	 * @time 上午10:48:22
	 */
	public void parseToPlainLicense(LicenseCipherText cipherText,
			LicensePlainText licensePlainText) {
		
		String jsonLisence = decryptLicense(cipherText);
		
		JSONObject json = JSONObject.parseObject(jsonLisence);
		
		String expireDateStr = json.getString(LicenseConstant.LICENSE_EXPIRE_DATE); // "expireDate" 
		String maxUserCount = json.getString(LicenseConstant.LICENSE_MAX_USER_COUNT); // "maxUserCount"
		String maxLoginEnableUserCount = json.getString(LicenseConstant.LICENSE_MAX_LOGIN_ENABLE_USER_COUNT); // "maxLoginEnableUserCount"
		String extFields = json.getString(LicenseConstant.LICENSE_EXT_FIELDS);
		if(LicenseUtil.isEmpty(expireDateStr,
				maxUserCount,
				maxLoginEnableUserCount)){
			// License解析错误
			log.info("License解析   Lisence密文错误");
			licensePlainText.setLicenseStatus(DECODE_FAIL);
			throw new RuntimeException("License 密文解析错误");
		}
		
		licensePlainText.setExtFields(extFields);
		licensePlainText.setMaxUserCount(Integer.valueOf(maxUserCount));
		licensePlainText.setMaxLoginEnableUserCount(Integer.valueOf(maxLoginEnableUserCount));
		licensePlainText.setExpireDate(new Date(Long.valueOf(expireDateStr)));
	}

	/**
	 * License 是否快要过期了。 
	 * @author focus
	 * @date 2016年1月28日
	 * @time 上午10:42:00
	 */
	private boolean isNearExpire(Date expireDate) {
		
		int nearExpireDateKey = param.getNearExpireDateKey();

		long now = toDay(System.currentTimeMillis());
		long expire = toDay(expireDate.getTime());
		
		if(now <= expire && (expire - now) < nearExpireDateKey){
			
			long difDay = expire - now ; 
			
			log.info("解析License 成功，还有：{} 天 过期",difDay);
			return true;
		}
		return false;
	}

	// 解密信息
	private String decryptLicense(LicenseCipherText cipherText) {
		ICipher cipher = cipherText.cipherStrategy().getChiperManager();
		return cipher.decrypt(cipherText.getLisence(), cipherText.getPrivateKey());
	}
	
	
	
	
	private static long toDay(long millis) {
	        return (millis + TimeZone.getDefault().getOffset(millis)) / TimeConstant.DAY;
	}



	@Override
	public JSONObject getLisencePlianText(String cipherJson) {
		
		LicenseManager manager = new LicenseManager();
		LicenseCipherText cipher = manager.getLisenceCipherTextByLiceseJson(cipherJson);
		if(cipher == null){
			// 当json 解析错误的时候的处理
			log.debug("许可文件解析 错误， 请检查许可中的文件是否是正确的" + cipherJson);
			throw new RuntimeException("许可文件错误");
		}
		LicensePlainText plain = getLisencePlainText(cipher);
		JSONObject json =  JSONObject.parseObject(JSONObject.toJSONString(plain));
		if(plain.getExpireDate() != null)
		json.put("expireDate", plain.getExpireDate().getTime());
		
		return json;
	}

	@Override
	public LicensePlainText getLicensePlainText() {
		return getLisencePlainText(param.getCipher());
	}
	
	
}
