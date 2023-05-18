package com.conduent.tpms.qeval.model;



public class Customer {
	  private String lastName;
	  private String firstName;
	  private Long posetdFareAmtsum;
	  
	public Customer(String firstName, String lastName) {
	    this.firstName = firstName;
	    this.lastName = lastName;
	  }
	public Customer() {
		// TODO Auto-generated constructor stub
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public Long getPosetdFareAmtsum() {
		return posetdFareAmtsum;
	}
	public void setPosetdFareAmtsum(Long posetdFareAmtsum) {
		this.posetdFareAmtsum = posetdFareAmtsum;
	}
	@Override
	public String toString() {
		return "Customer [lastName=" + lastName + ", firstName=" + firstName + "]";
	}
	  

}
