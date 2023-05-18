package com.conduent.tpms.qatp.config;

public class RequestObject 
{
	String startDeviceNo;
	String endDeviceNo;
	String loadType;
	String enableHistory;
	
	public String getLoadType() {
		return loadType;
	}
	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}
	public String getStartDeviceNo() {
		return startDeviceNo;
	}
	public void setStartDeviceNo(String startDeviceNo) {
		this.startDeviceNo = startDeviceNo;
	}
	public String getEndDeviceNo() {
		return endDeviceNo;
	}
	public void setEndDeviceNo(String endDeviceNo) {
		this.endDeviceNo = endDeviceNo;
	}
	public String getEnableHistory() {
		return enableHistory;
	}
	public void setEnableHistory(String enableHistory) {
		this.enableHistory = enableHistory;
	}
	
	
	@Override
	public String toString() {
		return "RequestObject [startDeviceNo=" + startDeviceNo + ", endDeviceNo=" + endDeviceNo + ", loadType="
				+ loadType + ", enableHistory=" + enableHistory + "]";
	}
	
	
	
}
