package com.conduent.tpms.iag.model;

import java.io.Serializable;

public class VAddressDto implements Serializable{
	
	private static final long serialVersionUID = -1636617059934829355L;
	
	private String street1;
	private String street2;
	private String city;
	private String state;
	private String zipCode;
	private String zipCodePlus;
	private String firstName;
	private String lastName;
	private String middleInitial;
	private String deviceNo;
	private String companyName;
	private int etcAcctId;
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public int getEtcAcctId() {
		return etcAcctId;
	}
	public void setEtcAcctId(int etcAcctId) {
		this.etcAcctId = etcAcctId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleInitial() {
		return middleInitial;
	}
	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}
	
	public String getStreet1() {
		return street1;
	}
	public void setStreet1(String street1) {
		this.street1 = street1;
	}
	public String getStreet2() {
		return street2;
	}
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipcode() {
		return zipCode;
	}
	public void setZipcode(String zipcode) {
		this.zipCode = zipcode;
	}
	public String getZipcodeplus() {
		return zipCodePlus;
	}
	public void setZipcodeplus(String zipcodeplus) {
		this.zipCodePlus = zipcodeplus;
	}
	
	@Override
	public String toString() {
		return "VAddressDto [street1=" + street1 + ", street2=" + street2 + ", city=" + city + ", state=" + state
				+ ", zipcode=" + zipCode + ", zipcodeplus=" + zipCodePlus + ", firstName=" + firstName + ", lastName="
				+ lastName + ", middleInitial=" + middleInitial + ", deviceNo=" + deviceNo + ", companyName="
				+ companyName + ", etcAcctId=" + etcAcctId + "]";
	}
	
	
}
