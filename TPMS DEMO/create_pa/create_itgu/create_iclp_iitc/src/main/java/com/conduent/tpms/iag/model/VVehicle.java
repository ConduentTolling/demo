package com.conduent.tpms.iag.model;

import java.io.Serializable;

public class VVehicle implements Serializable 
{
	private static final long serialVersionUID = -1636617059934829355L;
	
	private String plateNumber;
	private String plateState;
	private String plateType;
	private String deviceNo;
	
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceno(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public String getPlate_number() {
		return plateNumber;
	}
	public void setPlate_number(String plate_number) {
		this.plateNumber = plate_number;
	}
	public String getPlate_state() {
		return plateState;
	}
	public void setPlate_state(String plate_state) {
		this.plateState = plate_state;
	}
	
	public String getPlate_type() {
		return plateType;
	}
	public void setPlate_type(String plate_type) {
		this.plateType = plate_type;
	}
	@Override
	public String toString() {
		return "VVehicle [plate_number=" + plateNumber + ", plate_state=" + plateState + ", plate_type=" + plateType
				+ ", deviceNo=" + deviceNo + "]";
	}

}
