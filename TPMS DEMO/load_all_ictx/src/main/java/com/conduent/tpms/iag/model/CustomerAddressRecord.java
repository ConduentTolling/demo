package com.conduent.tpms.iag.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.relational.core.mapping.Column;

public class CustomerAddressRecord  implements Serializable {

	private static final long serialVersionUID = 1L;

	private int invalidAddressId;
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
	private LocalDateTime updateTs;
	private String zipCodeNew;

	public String getZipCodeNew() {
		return zipCodeNew;
	}

	public void setZipCodeNew(String zipCodeNew) {
		this.zipCodeNew = zipCodeNew;
	}
	
	public String getCustTagAgencyId() {
		return custTagAgencyId;
	}
	public void setCustTagAgencyId(String custTagAgencyId) {
		this.custTagAgencyId = custTagAgencyId;
	}
	public String getCustTagSerial() {
		return custTagSerial;
	}
	public void setCustTagSerial(String custTagSerial) {
		this.custTagSerial = custTagSerial;
	}
	private String custTagAgencyId; 
	private String custTagSerial; 
	
	
	public int getInvalidAddressId() {
		return invalidAddressId;
	}
	public void setInvalidAddressId(int invalidAddressId) {
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
	public LocalDateTime getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}
	
	
	@Override
	public String toString() {
		return "CustomerAddressRecord [invalidAddressId=" + invalidAddressId + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", middleInitial=" + middleInitial + ", companyName=" + companyName
				+ ", street1=" + street1 + ", street2=" + street2 + ", city=" + city + ", state=" + state + ", zipCode="
				+ zipCode + ", zipPlus4=" + zipPlus4 + ", updateTs=" + updateTs + ", custTagAgencyId=" + custTagAgencyId
				+ ", custTagSerial=" + custTagSerial + "]";
	}
	public CustomerAddressRecord(int invalidAddressId, String firstName, String lastName, String middleInitial,
			String companyName, String street1, String street2, String city, String state, String zipCode,
			String zipPlus4) {
		super();
		this.invalidAddressId = invalidAddressId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleInitial = middleInitial;
		this.companyName = companyName;
		this.street1 = street1;
		this.street2 = street2;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.zipPlus4 = zipPlus4;
	}
	public CustomerAddressRecord() {
		super();
	}
}
