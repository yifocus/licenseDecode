package com.ejiahe.eim.lisence.entity;

import java.util.Date;

/**
 * mac 地址
 *	
 *
 * @author focus
 * @date 2015年10月28日
 * @time 下午5:46:46
 */
public class LicenseMacAddress {

	private int id;
	private String macAddress;
	private String ipAddress;
	private String uniqueSerialNumber;
	private Date createDate;
	
	public LicenseMacAddress(String uniqueSerialNumber,String macAddress){
		this.uniqueSerialNumber = uniqueSerialNumber;
		this.macAddress = macAddress;
	}
	
	public LicenseMacAddress(){}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getUniqueSerialNumber() {
		return uniqueSerialNumber;
	}
	public void setUniqueSerialNumber(String uniqueSerialNumber) {
		this.uniqueSerialNumber = uniqueSerialNumber;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((macAddress == null) ? 0 : macAddress.hashCode());
		result = prime
				* result
				+ ((uniqueSerialNumber == null) ? 0 : uniqueSerialNumber
						.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LicenseMacAddress other = (LicenseMacAddress) obj;
		if (macAddress == null) {
			if (other.macAddress != null)
				return false;
		} else if (!macAddress.equals(other.macAddress))
			return false;
		if (uniqueSerialNumber == null) {
			if (other.uniqueSerialNumber != null)
				return false;
		} else if (!uniqueSerialNumber.equals(other.uniqueSerialNumber))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "LicenseMacAddress [macAddress=" + macAddress
				+ ", uniqueSerialNumber=" + uniqueSerialNumber
				+ ", createDate=" + createDate + "]";
	}
	
}
