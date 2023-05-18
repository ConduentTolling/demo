package com.conduent.tpms.process25a.dto;

import java.io.Serializable;

	public class TProcessParameter implements Serializable{
	private static final long serialVersionUID = -3724514409417122576L;
	
 	private String  agencyId; 
 	private String  plazaId; 
	private String  paramName;
	private String  paramCode;
	private String  paramValue;
	private String  paramDesc; 
	
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "TProcessParameter [agencyId=" + agencyId + ", plazaId=" + plazaId + ", paramName=" + paramName
				+ ", paramCode=" + paramCode + ", paramValue=" + paramValue + ", paramDesc=" + paramDesc
				+ ", getAgencyId()=" + getAgencyId() + ", getPlazaId()=" + getPlazaId() + ", getParamName()="
				+ getParamName() + ", getParamCode()=" + getParamCode() + ", getParamValue()=" + getParamValue()
				+ ", getParamDesc()=" + getParamDesc() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	

}
