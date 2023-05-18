package com.conduent.tpms.unmatched.dto;

public class DeviceList {
	
	private String deviceNo;

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	@Override
	public String toString() {
		return "DeviceList [deviceNo=" + deviceNo + "]";
	}
	
	
}
