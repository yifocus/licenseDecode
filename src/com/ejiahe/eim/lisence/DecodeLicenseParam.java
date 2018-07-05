package com.ejiahe.eim.lisence;

import java.util.List;

import com.ejiahe.eim.lisence.entity.LicenseCipherText;
import com.ejiahe.eim.lisence.entity.LicenseMacAddress;

public class DecodeLicenseParam {

	private LicenseCipherText cipher;
	private List<LicenseMacAddress> macAddress;
	private int nearExpireDateKey = 30;
	
	
	public DecodeLicenseParam(LicenseCipherText cipher,
			List<LicenseMacAddress> macAddress, int nearExpireDateKey) {
		this.cipher = cipher;
		this.macAddress = macAddress;
		this.nearExpireDateKey = nearExpireDateKey;
	}
	
	
	public LicenseCipherText getCipher() {
		return cipher;
	}
	public List<LicenseMacAddress> getMacAddress() {
		return macAddress;
	}
	public int getNearExpireDateKey() {
		return nearExpireDateKey;
	}
	
	
}
