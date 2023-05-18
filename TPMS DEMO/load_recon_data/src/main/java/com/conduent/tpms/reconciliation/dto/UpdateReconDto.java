package com.conduent.tpms.reconciliation.dto;

import java.time.LocalDate;

public class UpdateReconDto {
	
	private Long laneTxId;
	private Integer reconTxStatus;
	private String isFinalState;
	private String createRt;
	private LocalDate postedDate;
	public Long getLaneTxId() {
		return laneTxId;
	}
	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
	}
	public Integer getReconTxStatus() {
		return reconTxStatus;
	}
	public void setReconTxStatus(Integer reconTxStatus) {
		this.reconTxStatus = reconTxStatus;
	}
	public String getIsFinalState() {
		return isFinalState;
	}
	public void setIsFinalState(String isFinalState) {
		this.isFinalState = isFinalState;
	}
	public String getCreateRt() {
		return createRt;
	}
	public void setCreateRt(String createRt) {
		this.createRt = createRt;
	}
	public LocalDate getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(LocalDate postedDate) {
		this.postedDate = postedDate;
	}
	@Override
	public String toString() {
		return "UpdateReconDto [laneTxId=" + laneTxId + ", reconTxStatus=" + reconTxStatus + ", isFinalState="
				+ isFinalState + ", createRt=" + createRt + ", postedDate=" + postedDate + "]";
	}
	

}
