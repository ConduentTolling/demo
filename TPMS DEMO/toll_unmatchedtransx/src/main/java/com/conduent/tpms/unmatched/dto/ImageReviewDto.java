package com.conduent.tpms.unmatched.dto;

import java.time.LocalDate;

public class ImageReviewDto {
	
	private String sectionId;
	private String agencyId;
	private String plazaId;
	private LocalDate txDate;
	
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	public String getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}
	public String getPlazaId() {
		return plazaId;
	}
	public void setPlazaId(String plazaId) {
		this.plazaId = plazaId;
	}
	public LocalDate getTxDate() {
		return txDate;
	}
	public void setTxDate(LocalDate txDate) {
		this.txDate = txDate;
	}
	
	@Override
	public String toString() {
		return "ImageReviewDto [sectionId=" + sectionId + ", agencyId=" + agencyId + ", plazaId=" + plazaId
				+ ", txDate=" + txDate + "]";
	}

	
}
