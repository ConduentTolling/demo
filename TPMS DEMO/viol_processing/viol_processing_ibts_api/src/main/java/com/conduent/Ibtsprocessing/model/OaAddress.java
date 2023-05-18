package com.conduent.Ibtsprocessing.model;

import java.sql.Timestamp;

public class OaAddress {
	
	private Integer invalidAddressId;
	private String firstName;
	private String lastName;
	private String middleInitial;
	private String companyName;
	private String street1;
	private String street2;
	private String city;
	private String state;
	private String zipCode;
	private String zipPlus4;
	private Timestamp updateTs;
	public Timestamp getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(Timestamp updateTs) {
		this.updateTs = updateTs;
	}
	public OaAddress() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getInvalidAddressId() {
		return invalidAddressId;
	}
	public void setInvalidAddressId(Integer invalidAddressId) {
		this.invalidAddressId = invalidAddressId;
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getZipPlus4() {
		return zipPlus4;
	}
	public void setZipPlus4(String zipPlus4) {
		this.zipPlus4 = zipPlus4;
	}
	@Override
	public String toString() {
		return "OaAddress [invalidAddressId=" + invalidAddressId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", middleInitial=" + middleInitial + ", companyName=" + companyName + ", street1=" + street1
				+ ", street2=" + street2 + ", city=" + city + ", state=" + state + ", zipCode=" + zipCode
				+ ", zipPlus4=" + zipPlus4 + ", updateTs=" + updateTs + "]";
	}
	
	

}
