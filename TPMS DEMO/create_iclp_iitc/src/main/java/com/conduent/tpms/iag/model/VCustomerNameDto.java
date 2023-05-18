package com.conduent.tpms.iag.model;

import java.io.Serializable;

public class VCustomerNameDto implements Serializable {
	private static final long serialVersionUID = -1636617059934829355L;
	
	private String firstName;
	private String lastName;
	private String middleInitial;
	
	
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
	
	@Override
	public String toString() {
		return "VCustomerNameDto [firstName=" + firstName + ", lastName=" + lastName + ", middleInitial="
				+ middleInitial + "]";
	}

}
