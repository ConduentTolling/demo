package com.conduent.tpms.unmatched.dto;

/**
 * Common Classification Dto
 * 
 * 
 *
 */
public class UpdateTranDetailDto {
	private Long laneTxId;
	private String txExternRefNo;
	private Integer txStatus;
	private String matchedTxExternRefNo;
	private String txType;
	private Double etcFareAmount;
	
	
	public Long getLaneTxId() {
		return laneTxId;
	}
	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
	}
	public String getTxExternRefNo() {
		return txExternRefNo;
	}
	public void setTxExternRefNo(String txExternRefNo) {
		this.txExternRefNo = txExternRefNo;
	}
	public Integer getTxStatus() {
		return txStatus;
	}
	public void setTxStatus(Integer txStatus) {
		this.txStatus = txStatus;
	}
	public String getMatchedTxExternRefNo() {
		return matchedTxExternRefNo;
	}
	public void setMatchedTxExternRefNo(String matchedTxExternRefNo) {
		this.matchedTxExternRefNo = matchedTxExternRefNo;
	}
	public String getTxType() {
		return txType;
	}
	public void setTxType(String txType) {
		this.txType = txType;
	}
	public Double getEtcFareAmount() {
		return etcFareAmount;
	}
	public void setEtcFareAmount(Double etcFareAmount) {
		this.etcFareAmount = etcFareAmount;
	}
	@Override
	public String toString() {
		return "UpdateTranDetailDto [laneTxId=" + laneTxId + ", txExternRefNo=" + txExternRefNo + ", txStatus="
				+ txStatus + ", matchedTxExternRefNo=" + matchedTxExternRefNo + ", txType=" + txType
				+ ", etcFareAmount=" + etcFareAmount + "]";
	}


	
	
	
	
	
	
	
	
	
}
