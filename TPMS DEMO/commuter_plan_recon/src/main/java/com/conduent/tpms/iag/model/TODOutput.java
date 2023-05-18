package com.conduent.tpms.iag.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author taniyan
 * 
 * 
 */
@JsonIgnoreProperties( ignoreUnknown = true)

public class TODOutput {
	
	private String todId;
	private String todSegmentId;
	@JsonProperty("todStatus")
	private String todStatus; 
	private String todSegmentStatus;
	
	
	public TODOutput() {}
	
	public String getTodId() {
		return todId;
	}
	public void setTodId(String todId) {
		this.todId = todId;
	}
	public String getTodSegmentId() {
		return todSegmentId;
	}
	public void setTodSegmentId(String todSegmentId) {
		this.todSegmentId = todSegmentId;
	}
	public String getTodStatus() {
		return todStatus;
	}
	public void setTodStatus(String todStatus) {
		this.todStatus = todStatus;
	}
	public String getTodSegmentStatus() {
		return todSegmentStatus;
	}
	public void setTodSegmentStatus(String todSegmentStatus) {
		this.todSegmentStatus = todSegmentStatus;
	}

}
