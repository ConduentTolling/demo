package com.conduent.tpms.iag.model;

/**
 * @author taniyan
 * 
 */

public class APIResponse {
	
 	private String paymentReferenceID;
    private String status;
    
	public String getPaymentReferenceID() {
		return paymentReferenceID;
	}
	public void setPaymentReferenceID(String paymentReferenceID) {
		this.paymentReferenceID = paymentReferenceID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    


}
