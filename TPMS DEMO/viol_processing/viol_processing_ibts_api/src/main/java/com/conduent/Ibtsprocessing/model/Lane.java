package com.conduent.Ibtsprocessing.model;

public class Lane {
	
	private Integer laneId;
	private String lprEnabled;
	public Integer getLaneId() {
		return laneId;
	}
	public void setLaneId(Integer laneId) {
		this.laneId = laneId;
	}
	public String getLprEnabled() {
		return lprEnabled;
	}
	public void setLprEnabled(String lprEnabled) {
		this.lprEnabled = lprEnabled;
	}
	@Override
	public String toString() {
		return "Lane [laneId=" + laneId + ", lprEnabled=" + lprEnabled + "]";
	}
	
	

}
