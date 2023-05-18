package com.conduent.tpms.iag.model;


/**
 * @author Yugandhar Peddi
 * @Created on Jun 3, 2021 12:08:12 PM
 * 
 */

public class AdjustmentResponseVO {
	
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
