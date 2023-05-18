package com.conduent.tpms.reconciliation.model;

public class RTMapping {

	private Integer reconStatusInd;
	private String reconSubCodeInd;
	
	public Integer getReconStatusInd() {
		return reconStatusInd;
	}
	public void setReconStatusInd(Integer reconStatusInd) {
		this.reconStatusInd = reconStatusInd;
	}
	public String getReconSubCodeInd() {
		return reconSubCodeInd;
	}
	public void setReconSubCodeInd(String reconSubCodeInd) {
		this.reconSubCodeInd = reconSubCodeInd;
	}
	
	@Override
	public String toString() {
		return "RTMapping [reconStatusInd=" + reconStatusInd + ", reconSubCodeInd=" + reconSubCodeInd + "]";
	}

	
	
}
