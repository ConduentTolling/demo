package com.conduent.tollposting.model;

public class ProcessParameters 
{
	private Integer agencyId;
    private Integer plazaId;
    private String paramName;
    private String paramCode;
    private String paramValue;
    private String paramDesc;
    
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
	
}
