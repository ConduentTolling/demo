package com.conduent.tpms.unmatched.model;

/**
 * Process Parameter Model
 * 
 * @author deepeshb
 *
 */
public class ProcessParameter {

	private String paramValue;
	private String paramName;
	private Long agencyId;

	public ProcessParameter() {
		super();
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public Long getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	@Override
	public String toString() {
		return "ProcessParameter [paramValue=" + paramValue + ", paramName=" + paramName + ", agencyId=" + agencyId
				+ "]";
	}

	
	
}
