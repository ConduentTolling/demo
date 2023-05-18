package com.conduent.tpms.iag.model;

import java.io.Serializable;



public class VDeviceDto implements Serializable{
	
	private static final long serialVersionUID = -1636617059934829355L;
	
	private String deviceNo;       
	private long etcAccountId; 
	private int iagVehicleClass;
	private int deviceStatus; 
	private int deviceTypeId; 
	private String isPrevalidated;
	private int retailTagStatus;
	
	public int getRetailTagStatus() {
		return retailTagStatus;
	}
	public void setRetailTagStatus(int retailTagStatus) {
		this.retailTagStatus = retailTagStatus;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public long getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(long etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	public int getIagVehicleClass() {
		return iagVehicleClass;
	}
	public void setIagVehicleClass(int iagVehicleClass) {
		this.iagVehicleClass = iagVehicleClass;
	}
	public int getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(int deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public int getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setDeviceTypeId(int deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
	public String getIsPrevalidated() {
		return isPrevalidated;
	}
	public void setIsPrevalidated(String isPrevalidated) {
		this.isPrevalidated = isPrevalidated;
	}
	
	@Override
	public String toString() {
		return "VDeviceDto [deviceNo=" + deviceNo + ", etcAccountId=" + etcAccountId + ", iagVehicleClass="
				+ iagVehicleClass + ", deviceStatus=" + deviceStatus + ", deviceTypeId=" + deviceTypeId
				+ ", isPrevalidated=" + isPrevalidated + ", retailTagStatus=" + retailTagStatus + "]";
	}

}
