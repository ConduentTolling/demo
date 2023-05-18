package com.conduent.Ibtsprocessing.model;

public class Agency {
	
	private Integer agencyId;
	private String stmtDescription;
	public Agency() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}
	public String getStmtDescription() {
		return stmtDescription;
	}
	public void setStmtDescription(String stmtDescription) {
		this.stmtDescription = stmtDescription;
	}
	@Override
	public String toString() {
		return "Agency [agencyId=" + agencyId + ", stmtDescription=" + stmtDescription + "]";
	}
	
	
	

}
