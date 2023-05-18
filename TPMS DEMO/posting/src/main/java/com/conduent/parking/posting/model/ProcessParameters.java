package com.conduent.parking.posting.model;

public class ProcessParameters 
{
    private Long plazaId;
    private String paramName;
    private String paramCode;
    private String paramValue;
    private String paramDesc;
    private Integer agencyId;
    
    
	public Long getPlazaId() {
		return plazaId;
	}
	public void setPlazaId(Long plazaId) {
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
	public Integer getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}
}
