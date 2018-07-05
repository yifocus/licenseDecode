package com.ejiahe.eim.focus.lisence.entity;

import java.util.Date;
/**
 * 许可创建的时候对应的实体
 * 
 * @author focus
 * @date 2015年10月10日
 * @time 上午10:56:52
 */
public class LicenceCreateText {

	private int id;							// id
	private String entName;					// 企业名称
	private Date createDate;				// 创建时间
	private String nonce;					// 许可对应的企业唯一表示码
	private String author;					// 创建许可的人
	private int maxUserCount;				// 最大用户数
	private int maxLoginEnableUserCount;	// 最大可登陆数
	private String strategy;				// 证书生成策略 ： 解密策略&签名策略
	private Date expireDate;				// 失效日期
	private String remark;					// 备注
	private String extFields;
	
	
	public String getExtFields() {
		return extFields;
	}
	public void setExtFields(String extFields) {
		this.extFields = extFields;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStrategy() {
		return strategy;
	}
	/**
	 *  证书生成策略 ： 解密策略&签名策略
	 * @param strategy
	 * @author focus
	 * @date 2015年10月13日
	 * @time 上午10:22:03
	 */
	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}
	public String getEntName() {
		return entName;
	}
	public void setEntName(String entName) {
		this.entName = entName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
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
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
