package com.conduent.tpms.intx.dto;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * Message Conversion Dto
 * 
 * @author deepeshb
 *
 */
public class MessageConversionDto {

	private String txMessage;

	private boolean isValidLengthTx = true;

	private List<String> errorMsg = new LinkedList<String>();

	private Double expectedRevenueAmount;

	private LocalDate revenueDate;

	public String getTxMessage() {
		return txMessage;
	}

	public void setTxMessage(String txMessage) {
		this.txMessage = txMessage;
	}

	public boolean getIsValidLengthTx() {
		return isValidLengthTx;
	}

	public void setIsValidLengthTx(boolean isValidLengthTx) {
		this.isValidLengthTx = isValidLengthTx;
	}

	public List<String> getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(List<String> errorMsg) {
		this.errorMsg = errorMsg;
	}

	public void addErrorMsg(String errorMsg) {
		this.errorMsg.add(errorMsg);
	}

	public Double getExpectedRevenueAmount() {
		return expectedRevenueAmount;
	}

	public void setExpectedRevenueAmount(Double expectedRevenueAmount) {
		this.expectedRevenueAmount = expectedRevenueAmount;
	}

	public LocalDate getRevenueDate() {
		return revenueDate;
	}

	public void setRevenueDate(LocalDate revenueDate) {
		this.revenueDate = revenueDate;
	}

	public void setValidLengthTx(boolean isValidLengthTx) {
		this.isValidLengthTx = isValidLengthTx;
	}

}
