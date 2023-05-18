package com.conduent.tpms.inrx.dto;

import java.io.Serializable;

public class INRXDetailInfoVO implements Serializable {

	/**
	 * ICRX file details record fields
	 */
	private static final long serialVersionUID = 2798677367041309955L;

	private String etcTrxSerialNum;
	private String etcPostStatus;
	private String etcPostPlan;
	private String etcDebitCard;
	private String etcOwedAmount;
	public String getEtcTrxSerialNum() {
		return etcTrxSerialNum;
	}
	public void setEtcTrxSerialNum(String etcTrxSerialNum) {
		this.etcTrxSerialNum = etcTrxSerialNum;
	}
	public String getEtcPostStatus() {
		return etcPostStatus;
	}
	public void setEtcPostStatus(String etcPostStatus) {
		this.etcPostStatus = etcPostStatus;
	}
	public String getEtcPostPlan() {
		return etcPostPlan;
	}
	public void setEtcPostPlan(String etcPostPlan) {
		this.etcPostPlan = etcPostPlan;
	}
	public String getEtcDebitCard() {
		return etcDebitCard;
	}
	public void setEtcDebitCard(String etcDebitCard) {
		this.etcDebitCard = etcDebitCard;
	}
	public String getEtcOwedAmount() {
		return etcOwedAmount;
	}
	public void setEtcOwedAmount(String etcOwedAmount) {
		this.etcOwedAmount = etcOwedAmount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
