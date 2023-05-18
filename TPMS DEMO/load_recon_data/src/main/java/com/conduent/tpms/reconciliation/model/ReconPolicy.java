package com.conduent.tpms.reconciliation.model;

public class ReconPolicy {

	private Integer reconTxStatus;
	private String isFinalState;
	private String createRt;
	
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
	@Override
	public String toString() {
		return "ReconPolicy [reconTxStatus=" + reconTxStatus + ", isFinalState=" + isFinalState + ", createRt="
				+ createRt + "]";
	}
	
}
