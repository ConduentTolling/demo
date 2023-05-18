package com.conduent.tpms.reconciliation.dto;

public class RtQueueDto {

	private Long laneTxId;

	public Long getLaneTxId() {
		return laneTxId;
	}

	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
	}

	@Override
	public String toString() {
		return "RtQueueDto [laneTxId=" + laneTxId + "]";
	}
	
	
	
}
