package com.conduent.tpms.intx.model;

/**
 * Toll Post limit
 * 
 * @author deepeshb
 *
 */

public class TollPostLimit {

	Integer plazaAgencyId;

	Integer etcTxStatus;

	Integer allowedDays;

	String poachingLimit;

	public Integer getPlazaAgencyId() {
		return plazaAgencyId;
	}

	public void setPlazaAgencyId(Integer plazaAgencyId) {
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

	public String getPoachingLimit() {
		return poachingLimit;
	}

	public void setPoachingLimit(String poachingLimit) {
		this.poachingLimit = poachingLimit;
	}

}
