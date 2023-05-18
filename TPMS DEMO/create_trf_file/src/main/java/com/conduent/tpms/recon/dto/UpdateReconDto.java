package com.conduent.tpms.recon.dto;

public class UpdateReconDto {
	
	private Long laneTxId;
	private String deleteFlag;
	public Long getLaneTxId() {
		return laneTxId;
	}
	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	@Override
	public String toString() {
		return "UpdateReconDto [laneTxId=" + laneTxId + ", deleteFlag=" + deleteFlag + "]";
	}
	
	
}