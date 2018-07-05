package com.ejiahe.eim.focus.lisence.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;


/**
 *	
 * 许可明文
 * <br>当前属性中,解密之后的
 * @author focus
 * @date 2015年9月29日
 * @time 下午3:25:14
 */
public class LicensePlainText implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int id =-1;
	private String nonce;				// eim 部署服务器的唯一标识
	private Date expireDate;			// 正式的失效日期
	private int maxUserCount;			// 最大许可用户数
	private int maxLoginEnableUserCount;// 最大用户可登陆的数
	/**
	 *  0  标识正式许可
	 *  1  标识试用许可
	 *  2 标识特殊许可
	 */
	private int licenseType = 0;
	/**
	 *  <li>701     签名验证失败
	 * <li> 0          许可验证成功
	 * <li> 711    解析密文失败
	 * <li> 712    许可过期
	 * <li> 713    无效的日期
	 * <li> 714    许可快到期了
	 * <li> 715    许可快NonceId 错误
	 */
	private int licenseStatus;
	
	/**
	 * license 的扩展功能字段
	 */
	private String extFields;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getExtFields() {
		return extFields;
	}
	public void setExtFields(String extFields) {
		this.extFields = extFields;
	}
	/**
	 *  <li>701     签名验证失败
	 * <li> 0          许可验证成功
	 * <li> 711    解析密文失败
	 * <li> 712    许可过期
	 * <li> 713    无效的日期
	 * <li> 714    许可快到期了
	 * <li> 715    许可快NonceId 错误
	 * @return
	 * @author focus
	 * @date 2015年10月13日
	 * @time 下午4:08:55
	 */
	public Integer getLicenseStatus() {
		return licenseStatus;
	}
	/**
	 *  <li>-701     签名验证失败
	 * <li> 0          许可验证成功
	 * <li> 711    解析密文失败
	 * <li> 712    许可过期
	 * <li> 713    无效的日期
	 * <li> 714    许可快到期了
	 * <li> 715    许可快NonceId 错误
	 * @param lisenceStatus
	 * @author focus
	 * @date 2015年10月13日
	 * @time 下午4:09:01
	 */
	public void setLicenseStatus(int lisenceStatus) {
		this.licenseStatus = lisenceStatus;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public int getMaxUserCount() {
		return maxUserCount;
	}
	public void setMaxUserCount(int maxUserCount) {
		this.maxUserCount = maxUserCount;
	}
	public int getMaxLoginEnableUserCount() {
		return maxLoginEnableUserCount;
	}
	public void setMaxLoginEnableUserCount(int maxLoginEnableUserCount) {
		this.maxLoginEnableUserCount = maxLoginEnableUserCount;
	}
	
	public int getLicenseType() {
		return licenseType;
	}
	public void setLicenseType(int licenseType) {
		this.licenseType = licenseType;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	
	
}
