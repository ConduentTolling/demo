package com.conduent.tpms.iag.model;

public class TollPostLimitInfo {
	
	private int plazaAgencyId;
	private int etcTxStatus;
	private int allowedDays;
	private String updateTs;
	private String poachingLimit;
	
	public int getPlazaAgencyId() {
		return plazaAgencyId;
	}
	
	public void setPlazaAgencyId(int plazaAgencyId) {
		this.plazaAgencyId = plazaAgencyId;
	}
	
	public int getEtcTxStatus() {
		return etcTxStatus;
	}
	
	public void setEtcTxStatus(int etcTxStatus) {
		this.etcTxStatus = etcTxStatus;
	}
	
	public int getAllowedDays() {
		return allowedDays;
	}
	
	public void setAllowedDays(int allowedDays) {
		this.allowedDays = allowedDays;
	}
	
	public String getUpdateTs() {
		return updateTs;
	}
	
	public void setUpdateTs(String updateTs) {
		this.updateTs = updateTs;
	}
	
	public String getPoachingLimit() {
		return poachingLimit;
	}
	
	public void setPoachingLimit(String poachingLimit) {
		this.poachingLimit = poachingLimit;
	}

	@Override
	public String toString() {
		return "TollPostLimitInfo [plazaAgencyId=" + plazaAgencyId + ", etcTxStatus=" + etcTxStatus + ", allowedDays="
				+ allowedDays + ", updateTs=" + updateTs + ", poachingLimit=" + poachingLimit + "]";
	}
	
}
