package com.conduent.tpms.qatp.model;

/**
 * Process Parameter Model
 * 
 * @author deepeshb
 *
 */
public class ProcessParameter {

	private Integer paramValue;
	private String paramName;
	private Long agencyId;

	public ProcessParameter() {
		super();
	}

	public Integer getParamValue() {
		return paramValue;
	}

	public void setParamValue(Integer paramValue) {
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
