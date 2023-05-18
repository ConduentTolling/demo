package com.conduent.tpms.qeval.model;

import java.io.Serializable;

public class TProcessParam implements Serializable{

	private static final long serialVersionUID = 1L;
	private String paramName;  
	private String paramGroup;                                 
	private String paramValue;                                 
	private String paramDesc; 
	
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamGroup() {
		return paramGroup;
	}
	public void setParamGroup(String paramGroup) {
		this.paramGroup = paramGroup;
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
