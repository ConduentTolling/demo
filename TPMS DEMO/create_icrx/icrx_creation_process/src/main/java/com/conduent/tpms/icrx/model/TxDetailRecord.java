package com.conduent.tpms.icrx.model;

/**
 * 
 * @author urvashic
 *
 */
public class TxDetailRecord {

	private String txExternRefNo;
	private long postedFareAmount;
	private String txStatus;
	private String planTypeId;
	private String depositId;
	private Integer txModSeq;
	private String transactionRowId;

	public String getTransactionRowId() {
		return transactionRowId;
	}

	public void setTransactionRowId(String transactionRowId) {
		this.transactionRowId = transactionRowId;
	}

	public Integer getTxModSeq() {
		return txModSeq;
	}

	public void setTxModSeq(Integer txModSeq) {
		this.txModSeq = txModSeq;
	}

	public String getTxExternRefNo() {
		return txExternRefNo;
	}

	public void setTxExternRefNo(String txExternRefNo) {
		this.txExternRefNo = txExternRefNo;
	}

	public long getPostedFareAmount() {
		return postedFareAmount;
	}

	public void setPostedFareAmount(long postedFareAmount) {
		this.postedFareAmount = postedFareAmount;
	}

	public String getTxStatus() {
		return txStatus;
	}

	public void setTxStatus(String txStatus) {
		this.txStatus = txStatus;
	}

	public String getPlanTypeId() {
		return planTypeId;
	}

	public void setPlanTypeId(String planTypeId) {
		this.planTypeId = planTypeId;
	}

	public String getDepositId() {
		return depositId;
	}

	public void setDepositId(String depositId) {
		this.depositId = depositId;
	}

	@Override
	public String toString() {
		return "TxDetailRecord [txExternRefNo=" + txExternRefNo + ", postedFareAmount=" + postedFareAmount
				+ ", txStatus=" + txStatus + ", planTypeId=" + planTypeId + ", depositId=" + depositId + ", txModSeq="
				+ txModSeq + ", transactionRowId=" + transactionRowId + "]";
	}

}
