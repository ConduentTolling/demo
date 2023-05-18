package com.conduent.tpms.iag.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TodResponseUtill {
	
	private TODOutput result;

	public TODOutput getResult() {
		return result;
	}

	public void setResult(TODOutput result) {
		this.result = result;
	}
	
	

}
