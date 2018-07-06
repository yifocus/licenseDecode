package com.ejiahe.eim.focus.lisence.conf;


public enum LicenseStatus {
	disable(0),normal(1),standby(2);
	
	private Integer status;
	private LicenseStatus( int status){
		this.status = status;
	}
	
	public int status(){
		if(status == null) return this.ordinal();
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
