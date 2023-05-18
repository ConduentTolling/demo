package com.conduent.Ibtsprocessing.model;

public class ProcessParameters {
	
	private Integer  agencyId; 
 	private String  plazaId; 
	private String  paramName;
	
	
	
	public ProcessParameters() {
		super();
	}
	private String  paramCode;
	private String  paramValue;
	private String  paramDesc; 
	
	public Integer getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}
	public String getPlazaId() {
		return plazaId;
	}
	public void setPlazaId(String plazaId) {
		this.plazaId = plazaId;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamCode() {
		return paramCode;
	}
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public String getParamDesc() {
		return paramDesc;
	}
	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}
	@Override
	public String toString() {
		return "ProcessParameter [agencyId=" + agencyId + ", plazaId=" + plazaId + ", paramName=" + paramName
				+ ", paramCode=" + paramCode + ", paramValue=" + paramValue + ", paramDesc=" + paramDesc
				+ ", getAgencyId()=" + getAgencyId() + ", getPlazaId()=" + getPlazaId() + ", getParamName()="
				+ getParamName() + ", getParamCode()=" + getParamCode() + ", getParamValue()=" + getParamValue()
				+ ", getParamDesc()=" + getParamDesc() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	

}
