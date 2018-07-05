package com.ejiahe.eim.focus.lisence.entity;

import java.util.Date;
import java.util.Map;

import com.ejiahe.eim.focus.lisence.conf.CipherStrategy;
import com.ejiahe.eim.focus.lisence.conf.SignStrategy;
import com.ejiahe.eim.focus.lisence.util.LicenseUtil;

/**
 * 许可密文 读取许可,许可证实体， 
 * <br>当前属性中,msgSign,lisence  为加密字段
 * @author focus
 * @date 2015年10月8日
 * @time 下午2:27:08
 */
public class LicenseCipherText {


	
	private int id;				// 编号	
	private String nonce;			// 许可对应某一企业的唯一标识
//	private Integer tenementId;			// 企业id
	private String strategy;		// 策略：解密策略&签名策略，,秘钥
	private String msgSign;			// 签名
	private String lisence; 		// 内容
	private String privateKey;  	// 秘钥 
	private String originalMsgSign; // 原始签名的数据 ："nonce + lisense + strategy + deviceId"
	private Map<String,String> ext; // 扩展
	private Date createDate;		// 创建日期
	private String fieldExtStr;	    // 扩展字段的json 字符串
//	public Integer getTenementId() {
//		if(tenementId == null || tenementId == 0 ){
//			try {
//				String deNonce = new String(LicenseUtil.decryptBASE64(nonce));
//				if(deNonce.contains("&")){
//					String tenementTemp = deNonce.split("&")[1];
//					if(tenementTemp.matches("^[0-9]+$")){
//						tenementId = Integer.valueOf(tenementTemp);
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return tenementId;
//	}
//	public void setTenementId(int tenementId) {
//		this.tenementId = tenementId;
//	}
	private int status = 1;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getFieldExtStr() {
		if(LicenseUtil.isEmpty(fieldExtStr)){
			fieldExtStr = LicenseUtil.parseMapToJson(ext);
		}
		return fieldExtStr;
	}

	public void setFieldExtStr(String fieldExtStr) {
		this.fieldExtStr = fieldExtStr;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	private static final String SPLIT = "&";
	
	/**
	 * 加密解密策略：如果没或者策略转换出错则使用默认策略
	 * @return
	 * @author focus
	 * @date 2015年10月8日
	 * @time 下午3:56:24
	 */
	public CipherStrategy cipherStrategy(){
		
		// 以下原因要是用默认的策略
		// 1 策略为空时 
		// 2 秘钥为空时
		if(strategy == null || 
				strategy.trim().length() == 0 ||
				privateKey == null||
				privateKey.trim().length() == 0){
			return CipherStrategy.DEFAULT;
		}
		
		String cipherStrategyStr = "";
		if(strategy.contains(SPLIT)){
			// 如果存在分隔符 & 则取第一个策略为解密策略
			cipherStrategyStr = strategy.split(SPLIT)[0].trim();
		}else{
			// 如果不存在分隔符，则说明只有一个策略，默认为当前策略 既是签名策略也是解密策略
			cipherStrategyStr = strategy.trim();
		}

		for(CipherStrategy cipherStrategy : CipherStrategy.values()){
			if(cipherStrategy.name().equalsIgnoreCase(cipherStrategyStr)){
				return cipherStrategy;
			}
		}
		return CipherStrategy.DEFAULT;
	}
	
	/**
	 * 签名策略： 如果没或者策略转换出错则使用默认策略
	 * @return
	 * @author focus
	 * @date 2015年10月8日
	 * @time 下午3:56:45
	 */
	public SignStrategy signStrategy(){
		
		// 以下原因要是用默认的策略
		// 1 策略为空时 
		// 2 秘钥为空时
		if(strategy == null || 
				strategy.trim().length() == 0 ||
				privateKey == null||
				privateKey.trim().length() == 0){
			return SignStrategy.DEFAULT;
		}
		
		String signStrategyStr = "";
		if(strategy.contains(SPLIT)){
			// 如果存在分隔符  & 则取第二个策略为解密策略
			signStrategyStr = strategy.split(SPLIT)[1].trim();
		}else{
			// 如果不存在分隔符，则说明只有一个策略，默认为当前策略 既是签名策略也是解密策略
			signStrategyStr = strategy.trim();
		}
		
		for(SignStrategy signStrategy : SignStrategy.values()){
			if(signStrategy.name().equalsIgnoreCase(signStrategyStr)){
				return signStrategy;
			}
		}
		
		return SignStrategy.DEFAULT;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	public String getStrategy() {
		return strategy;
	}
	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}
	public String getMsgSign() {
		return msgSign;
	}
	public void setMsgSign(String msgSign) {
		this.msgSign = msgSign;
	}
	public String getLisence() {
		return lisence;
	}
	public void setLisence(String lisence) {
		this.lisence = lisence;
	}
	public Map<String, String> getExt() {
		return ext;
	}
	public void setExt(Map<String, String> ext) {
		this.ext = ext;
	}

	public String getOriginalMsgSign() {
		return originalMsgSign;
	}
	public void setOriginalMsgSign(String originalMsgSign) {
		this.originalMsgSign = originalMsgSign;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	
	
	@Override
	public String toString() {
		return "LicenseCipherText [id=" + id + ", nonce=" + nonce
				+ ", strategy=" + strategy + ", msgSign=" + msgSign
				+ ", lisence=" + lisence + ", privateKey=" + privateKey
				+ ", originalMsgSign=" + originalMsgSign + ", ext=" + ext
				+ ", createDate=" + createDate + ", fieldExtStr=" + fieldExtStr
				+ ", status=" + status + "]";
	}
	public static void main(String[] args) throws Exception {
		LicenseCipherText cipher = new LicenseCipherText();
		cipher.nonce = "ddfasdf&12356";
		cipher.nonce = LicenseUtil.encryptBASE64(cipher.nonce.getBytes());
	}
	
	
	public enum LicenseStatus{
		normal(1),standby(2),disable(0);
		
		private int status;
		private LicenseStatus( int status){
			this.status = status;
		}
		
		public int status(){
			return status;
		}

		public static LicenseStatus parse(String statusStr) {
			for(LicenseStatus status : values()){
				if(status.name().equals(statusStr)){
					return status;
				}
			}
			return null;
		}

	}
}
