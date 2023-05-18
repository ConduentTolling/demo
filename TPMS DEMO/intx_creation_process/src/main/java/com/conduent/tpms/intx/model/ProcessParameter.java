package com.conduent.tpms.intx.model;

/**
 * Process Parameter Model
 * 
 * @author deepeshb
 *
 */

public class ProcessParameter {

	String paramValue;

	String paramName;

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

}
