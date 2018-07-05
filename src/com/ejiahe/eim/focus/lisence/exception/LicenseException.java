package com.ejiahe.eim.focus.lisence.exception;

public class LicenseException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	private String errorMsg;
	
	public LicenseException(String errorCode,String message){
		super(message);
		this.errorCode = errorCode;
		this.errorMsg = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
