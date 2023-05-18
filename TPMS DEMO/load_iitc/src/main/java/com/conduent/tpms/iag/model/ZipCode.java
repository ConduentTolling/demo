package com.conduent.tpms.iag.model;

import java.io.Serializable;

public class ZipCode implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String guId;
	private String zipcode;
	private String city;
	private String stateProv;
	private String country;
	
	
	public ZipCode() {
		
	}

	public ZipCode(String guId, String zipcode, String city, String stateProv, String country) {
		super();
		this.guId = guId;
		this.zipcode = zipcode;
		this.city = city;
		this.stateProv = stateProv;
		this.country = country;
	}

	public String getGuId() {
		return guId;
	}

	public void setGuId(String guId) {
		this.guId = guId;
	}

	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStateProv() {
		return stateProv;
	}
	public void setStateProv(String stateProv) {
		this.stateProv = stateProv;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "ZipCode [guId=" + guId + ", zipcode=" + zipcode + ", city=" + city + ", stateProv=" + stateProv
				+ ", country=" + country + "]";
	}
	

	
	
	
}
