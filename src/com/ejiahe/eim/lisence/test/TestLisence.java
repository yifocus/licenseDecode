package com.ejiahe.eim.lisence.test;

import java.util.Calendar;
import java.util.Date;

import com.ejiahe.eim.lisence.LicenseService;
import com.ejiahe.eim.lisence.conf.CipherStrategy;
import com.ejiahe.eim.lisence.conf.SignStrategy;
import com.ejiahe.eim.lisence.entity.LicenceCreateText;
import com.ejiahe.eim.lisence.entity.LicensePlainText;
import com.ejiahe.eim.lisence.entity.LicenseCipherText.LicenseStatus;
import com.ejiahe.eim.lisence.util.LicenseUtil;

public class TestLisence {

	
	public String createLisenceText(){
		
		LicenseService service = new LicenseService(null);
		LicenceCreateText lisence = new LicenceCreateText();
		lisence.setAuthor("focus1123");
		lisence.setCreateDate(new Date());
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.MONTH, 1);
		lisence.setExpireDate(ca.getTime());
		
		lisence.setMaxLoginEnableUserCount(15000);
		lisence.setMaxUserCount(10001);
		lisence.setNonce("Vdu2u6P8gy4ajGhW23E/UQ==");
		lisence.setStrategy( CipherStrategy.RSA.name() + "&" + SignStrategy.DEFAULT.name());
		
		String cipherText = service.createLisence(lisence);
		System.out.println(cipherText);
		return cipherText;
	}
	
	
	public LicensePlainText testGetLisence(){
		LicenseService service = new LicenseService(null);
		return service.getLicensePlainText();
	}
	public static void main(String[] args) throws Throwable {
		TestLisence test = new TestLisence();
//		System.out.println(test.testGetLisence());
		
		System.out.println(test.createLisenceText());
		
		String str = "";//LicenseUtil.getLocalMac();
		str += str;
		str += str;
		System.out.println(str.length());
		System.out.println(LicenseUtil.encryptBASE64(str.getBytes()));
		System.out.println(LicenseUtil.encryptBASE64(str.getBytes()).length());

	}
}
