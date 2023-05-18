package com.conduent.Ibtsprocessing.model;

import java.time.LocalDate;

public class OADevices {
	
	private String deviceNo;
	private LocalDate startDate;
	private LocalDate endDate;
	private Integer iagTagStatus;
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public Integer getIagTagStatus() {
		return iagTagStatus;
	}
	public void setIagTagStatus(Integer iagTagStatus) {
		this.iagTagStatus = iagTagStatus;
	}
	public OADevices() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "OADevices [deviceNo=" + deviceNo + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", iagTagStatus=" + iagTagStatus + "]";
	}
	
	
	
	

}
