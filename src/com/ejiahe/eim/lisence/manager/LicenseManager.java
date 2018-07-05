package com.ejiahe.eim.lisence.manager;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ejiahe.eim.lisence.conf.CipherStrategy;
import com.ejiahe.eim.lisence.conf.SignStrategy;
import com.ejiahe.eim.lisence.constant.LicenseConstant;
import com.ejiahe.eim.lisence.entity.LicenceCreateText;
import com.ejiahe.eim.lisence.entity.License;
import com.ejiahe.eim.lisence.entity.LicenseCipherText;
import com.ejiahe.eim.lisence.manager.cipher.ICipher;
import com.ejiahe.eim.lisence.manager.sign.ISign;
import com.ejiahe.eim.lisence.manager.sign.source.SignMD5;
import com.ejiahe.eim.lisence.util.LicenseUtil;

/**
 * 
 * Lisence 许可管理
 * @author focus
 * @date 2015年10月9日
 * @time 下午1:46:07
 */
public class LicenseManager {

	// 策略的分隔符， 分割  内容加密解密策略&签名加密解密策略
	private static final String STRATEGY_SPLIT = "&";
	Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * 解析Lisence的json字符串:
	 * <br> 用户保存证书到数据的时候使用的
	 * @param licenseJson
	 * @return	如果徐如果是正常的 {@link LicenseCipherText} 则返回null
	 * @author focus
	 * @date 2015年10月9日
	 * @time 下午1:51:04
	 */
	public LicenseCipherText getLisenceCipherTextByLiceseJson(String licenseJson){
		
		// 1  解析cipherJson 字符串，
		// 2 通过对于特殊字段进行处理
		
		Map<String,String> cipherMap = LicenseUtil.parseJsonStrToMap(licenseJson);
		if(cipherMap == null){
			log.info("解析json 字符串错误 licenseJson:{}",licenseJson);
			return null;
		}
		
		// 许可对应某企业的服务器设备的唯一标识
		String nonce = cipherMap.get("nonce");
		
		// 策略  格式如：内容解密策略&签名策略,秘钥
		//			签名策略,秘钥
		//			内容解密策略,秘钥
		//			内容解密策略
		//			内容签名策略
		//			内容解密策略&签名策略
		// 策略可以为空： 如果为空则表示使用默认的策略进行加密解密，和签名验证等
		// 如果只有一种则说明，内容和签名都是用的同一个加密策略
		String strategy = "";
		// 秘钥，可以为空 与策略一起
		String privateKey = "";
		
		String strategyTemp = cipherMap.get("strategy");
		if(strategyTemp.contains(",")){
			// 如果存在秘钥， 则分割的第一个元素为 策略， 
			// 第二个为秘钥， 否则直接只有策略,没有秘钥
			strategy = strategyTemp.split(",")[0];
			privateKey = strategyTemp.split(",")[1];
		}else{
			strategy = strategyTemp;
		}
		
		// 签名： 签名不可以为空，为空表示程序错误
		String msgSign = cipherMap.get("msgSign");
		// 许可的内容
		String lisence = cipherMap.get("lisence");;

		// 原始签名的数据 ："nonce + lisence + strategy + nonce"
		String originalMsgSign = new SignMD5().createMsgSign(nonce + lisence + strategyTemp + nonce + LicenseConstant.LICENSE_CONTROL_VERSION_SIGN_SUFFIX);
		
		// 数据验证：
		// lisence,msgSign,nonce 必须不能为空
		if(LicenseUtil.isEmpty(lisence,msgSign,nonce)){
			log.info(" 解析许可的json字符串出错， 或者许可本身的错误，" +
					 " lisence,msgSign,nonce 字段不能为空 请检查许可是否存在问题 licenseJson:{}",licenseJson);
			return null;
		}
		
		// 其他扩展字段
		Map<String,String> ext = new HashMap<String,String>();
		try {
			
			Class<?> clzz = LicenseCipherText.class;
			Field fields[] = clzz.getDeclaredFields();
			
			for(Field field : fields){
				field.setAccessible(true);
				String fieldName = field.getName();
				cipherMap.remove(fieldName);
			}
			
			ext = cipherMap;
		} catch (Exception e) {
			log.error("处理扩展字段出错", e);
		}
		
		LicenseCipherText cipherText = new LicenseCipherText();
		cipherText.setExt(ext);
		cipherText.setNonce(nonce);
		cipherText.setMsgSign(msgSign);
		cipherText.setLisence(lisence);
		cipherText.setStrategy(strategy);
		cipherText.setCreateDate(new Date());
		cipherText.setPrivateKey(privateKey);
		cipherText.setOriginalMsgSign(originalMsgSign);
		
		return cipherText;
	}
	
	
	/**
	 * 创建许可
	 * @param lisenceCreateText
	 * @return
	 * @author focus
	 * @date 2015年10月10日
	 * @time 上午11:16:08
	 */
	public String createLisence(LicenceCreateText lisenceCreateText){
		
		// 1  对数据进行处理，合成
		// 2 通过策略对数据进行加密
		
		// 策略  格式如：内容解密策略&签名策略,秘钥
		//			签名策略,秘钥
		//			内容解密策略,秘钥
		//			内容解密策略
		//			内容签名策略
		//			内容解密策略&签名策略
		// 策略可以为空： 如果为空则表示使用默认的策略进行加密解密，和签名验证等
		// 如果只有一种则说明，内容和签名都是用的同一个加密策略
		String strategy = lisenceCreateText.getStrategy();
		String cipherStrategyName = "";
		String signStrategyName = "";
		if(strategy != null && strategy.contains(STRATEGY_SPLIT)){
			// 内容解密策略&签名策略， 
			cipherStrategyName = strategy.split(STRATEGY_SPLIT)[0];
			signStrategyName = strategy.split(STRATEGY_SPLIT)[1];
		}else{
			cipherStrategyName = strategy;
			signStrategyName = strategy;
		}
		
		
		// 取得策略
		CipherStrategy cipherStrategy = CipherStrategy.parse(cipherStrategyName);
		SignStrategy signStrategy = SignStrategy.parse(signStrategyName);
		
		// 通过策略取得 对应的生成逻辑
		ICipher cipher = cipherStrategy.getChiperManager();
		ISign sign = signStrategy.getSignManager();
		
 		// 秘钥 ,根据策略生成
		String privateKey = cipher.createPrivateKey();
		
		
		// 许可中的文本内容， 包括过期时间， 最大用户数量， 最大用户可登陆的数量
		Map<String,Object> lisenceTextMap = new HashMap<String,Object>();
		lisenceTextMap.put("maxLoginEnableUserCount", lisenceCreateText.getMaxLoginEnableUserCount());
		lisenceTextMap.put("maxUserCount", lisenceCreateText.getMaxUserCount());
		lisenceTextMap.put("expireDate", lisenceCreateText.getExpireDate().getTime());
		lisenceTextMap.put("extFields", lisenceCreateText.getExtFields());
		
		// 许可对已企业的唯一标识
		String nonce = lisenceCreateText.getNonce();
		// 策略
		if(privateKey != null && !privateKey.equals("")){
			strategy = strategy + "," + privateKey;
		}
		
		// 生成许可中的许可明细的内容
		String lisenceText = cipher.encrypt(LicenseUtil.parseMapToJson(lisenceTextMap), privateKey);
		
//		// 取得签名里面原始内容的内容： "nonce + lisence + strategy + nonce"
//		String signSourceContent = new SignMD5().createMsgSign( nonce + lisenceText + strategy + nonce);
//		// 生成签名
//		String msgSign = sign.createMsgSign(signSourceContent);
//		
		String msgSign = createSign(sign, nonce + lisenceText + strategy + nonce);
		License lisence = new License();
		lisence.setLisence(lisenceText);
		lisence.setNonce(nonce);
		lisence.setStrategy(strategy);
		lisence.setMsgSign(msgSign);
		
		return LicenseUtil.parseBeanToJosnStr(lisence);
	}
	
	public String createSign(ISign sign,String signSrc){
		
		signSrc += LicenseConstant.LICENSE_CONTROL_VERSION_SIGN_SUFFIX;
		String cipherSource = new SignMD5().createMsgSign(signSrc);
		return sign.createMsgSign(cipherSource);
	}
	
}
