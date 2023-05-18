package com.conduent.tpms.iag.model;

import java.io.Serializable;
import java.util.Date;

public class VVehicle implements Serializable 
{
	private static final long serialVersionUID = -1636617059934829355L;
	
	private String plateNumber;
	private String plateState;
	private String plateType;
	private String deviceNo;
	private String devicePrefix;
	private String serialNo;
	private String accountNo;
	private String licHomeAgency;
	private String vin;
	private String licGuaranteed;
	private Date licEffectiveFrom;
	private Date licEffectiveTo;
	
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
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getDevicePrefix() {
		return devicePrefix;
	}
	public void setDevicePrefix(String devicePrefix) {
		this.devicePrefix = devicePrefix;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getLicGuaranteed() {
		return licGuaranteed;
	}
	public void setLicGuaranteed(String licGuaranteed) {
		this.licGuaranteed = licGuaranteed;
	}
	public Date getLicEffectiveFrom() {
		return licEffectiveFrom;
	}
	public void setLicEffectiveFrom(Date licEffectiveFrom) {
		this.licEffectiveFrom = licEffectiveFrom;
	}
	public Date getLicEffectiveTo() {
		return licEffectiveTo;
	}
	public void setLicEffectiveTo(Date licEffectiveTo) {
		this.licEffectiveTo = licEffectiveTo;
	}
	public String getLicHomeAgency() {
		return licHomeAgency;
	}
	public void setLicHomeAgency(String licHomeAgency) {
		this.licHomeAgency = licHomeAgency;
	}
	@Override
	public String toString() {
		return "VVehicle [plateNumber=" + plateNumber + ", plateState=" + plateState + ", plateType=" + plateType
				+ ", deviceNo=" + deviceNo + ", devicePrefix=" + devicePrefix + ", serialNo=" + serialNo
				+ ", accountNo=" + accountNo + ", licHomeAgency=" + licHomeAgency + ", vin=" + vin + ", licGuaranteed="
				+ licGuaranteed + ", licEffectiveFrom=" + licEffectiveFrom + ", licEffectiveTo=" + licEffectiveTo + "]";
	}

}
