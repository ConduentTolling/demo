package com.conduent.Ibtsprocessing.model;

public class TxTypeMapping {
	
	private String txTypeInd;
	private String txSubtypeInd;
	private String typeDescription;
	private Integer violationType;
	
	public TxTypeMapping() {
		super();
	}
	public String getTxTypeInd() {
		return txTypeInd;
	}
	public void setTxTypeInd(String txTypeInd) {
		this.txTypeInd = txTypeInd;
	}
	public String getTxSubtypeInd() {
		return txSubtypeInd;
	}
	public void setTxSubtypeInd(String txSubtypeInd) {
		this.txSubtypeInd = txSubtypeInd;
	}
	public String getTypeDescription() {
		return typeDescription;
	}
	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}
	public Integer getViolationType() {
		return violationType;
	}
	public void setViolationType(Integer violationType) {
		this.violationType = violationType;
	}
	@Override
	public String toString() {
		return "TxTypeMapping [txTypeInd=" + txTypeInd + ", txSubtypeIn=" + txSubtypeInd + ", typeDescription="
				+ typeDescription + ", voilationType=" + violationType + ", getTxTypeInd()=" + getTxTypeInd()
				+ ", getTxSubtypeIn()=" + getTxSubtypeInd() + ", getTypeDescription()=" + getTypeDescription()
				+ ", getVoilationType()=" + getViolationType() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
}
