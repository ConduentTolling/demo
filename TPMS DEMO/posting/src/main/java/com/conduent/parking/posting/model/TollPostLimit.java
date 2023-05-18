package com.conduent.parking.posting.model;

import java.time.LocalDateTime;

public class TollPostLimit
{
	private Long plazaAgencyId;
	private Integer etcTxStatus;
	private Integer allowedDays;
	private LocalDateTime updateTs;
	private String poachingLimit;
	
	public Long getPlazaAgencyId() {
		return plazaAgencyId;
	}
	public void setPlazaAgencyId(Long plazaAgencyId) {
		this.plazaAgencyId = plazaAgencyId;
	}
	public Integer getEtcTxStatus() {
		return etcTxStatus;
	}
	public void setEtcTxStatus(Integer etcTxStatus) {
		this.etcTxStatus = etcTxStatus;
	}
	public Integer getAllowedDays() {
		return allowedDays;
	}
	public void setAllowedDays(Integer allowedDays) {
		this.allowedDays = allowedDays;
	}
	public LocalDateTime getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}
	public String getPoachingLimit() {
		return poachingLimit;
	}
	public void setPoachingLimit(String poachingLimit) {
		this.poachingLimit = poachingLimit;
	}
}