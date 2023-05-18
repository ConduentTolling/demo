package com.conduent.tpms.qatp.model;

public class TPlaza {

	private Integer plazaId;

	private Integer agencyId;
	
	
	public Integer getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}
	public Integer getPlazaId() {
		return plazaId;
	}
	public void setPlazaId(Integer plazaId) {
		this.plazaId = plazaId;
	}

	@Override
	public String toString() {
		return "TPlaza [plazaId=" + plazaId + ", agencyId=" + agencyId + "]";
	}

}
