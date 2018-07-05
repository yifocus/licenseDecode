package com.ejiahe.eim.focus.lisence;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ejiahe.eim.focus.lisence.entity.CipherLicense;
import com.ejiahe.eim.focus.lisence.entity.LicenseCipherText;
import com.ejiahe.eim.focus.lisence.entity.LicenseMacAddress;
import com.ejiahe.eim.focus.lisence.entity.LicensePlainText;

public class DecodeLicense {

	private LicenseService licenseServ;
	public DecodeLicense (DecodeLicenseParam param){
		licenseServ = new LicenseService(param);
	}
	
	
	public LicensePlainText getLicense(){
		return licenseServ.getLicensePlainText();
	}
	
	public LicenseCipherText createLicenseCipher(CipherLicense cipher){
		return licenseServ.getCipherLicenseText(JSONObject.toJSONString(cipher));
	}
	
	
	public static void main(String[] args) {
		String license = "bgbaUOwhMbHhinUqqvhIorzKnCEfeTefGFiGw3+jub41BNAxZ5Cac/2yzTW8OteV9QPsvnKUtqUsjWoQ8AbrKUJxI8AKyab9qkkjP17pMqHTdipSLMKxvOGsssCJkUt3RXkWDx227ObvtndPysM4fvGRqiWlB9DShrBbdJlH1qZwuaZPm1XjrKwdiFWl+baLaZqZvbc3wShb5KrXftgFIKIsU28WWEHNn3d5o4uhYtLMb3qRYQ7jl17qWhSII0oL5D5nq5SSpkB0ftygBczzpvYYbjWAKhHSg+6IXe3R50cx4vk8kbRwfpaFKn+T1XIHXKHKPNefrGQQksYyzZM9sGYVbcUaD2TZA63X5DJwJ8HQk3+RRf29f+6q1CZNdhr+JkAmHjcJ3gxrUajXJDkioa+x+ku9fzgpnselpZdStoaDXuoBSiPkPz66d6GUWEikSyC1/G6MIHSEVcqhAUrko2agLp3G7VyhBY4iwCROI2w76z1ywEyqzQcqbhMoTx0/Wdws4du45GNI6IYVq/1c4ZFu/Vwx+qn+9Lyitb+b3LZhkDx+cb65HwSM4QV2JMtqTIfC40R1UONILvTnEYCbKrbsWBTP8ddUtxTUwheXv06LNe5va+AWCwDsEiNDK2lTiTGJzEIVectBdD5msIsqWIIkVmlpQuJI9ZCDY9Z56afkkoJdxxC9k0wZXLa1G8THiqi3oKBIJGh+kfT7JYOQSWSFOwb9z+sU+mVFYMnNJdYe51f4O/F0hrslCBgcchAr8lzuVCFZpQBIFYQULMAgtg==";
		String nonce  = "C+hBT5DJc5d8";
		String strategy = "ESA&MD5,UU52QTJSUlNSdzMxTTRQcw==";
		String msgSign = "PqCQDGOZIvInvUT3/YQ3Xg==";
		
		LicenseMacAddress address  = new LicenseMacAddress();
		address.setMacAddress("94de805dc5d8");
		address.setUniqueSerialNumber("5JMlqhHs");
		
		CipherLicense cipher = new CipherLicense();
		cipher.setLisence(license);
		cipher.setNonce(nonce);
		cipher.setStrategy(strategy);
		cipher.setMsgSign(msgSign);
		
		DecodeLicense decode = new DecodeLicense(null);
		LicenseCipherText text = decode.createLicenseCipher(cipher);
		System.out.println(text);
		List<LicenseMacAddress> addresses = new ArrayList<LicenseMacAddress>();
		addresses.add(address);
		DecodeLicenseParam param = new DecodeLicenseParam(text, addresses, 30);
		decode = new DecodeLicense(param);
		System.out.println(JSONObject.toJSONString(decode.getLicense()));
		
	}
	
}
