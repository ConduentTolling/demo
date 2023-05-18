package com.conduent.transactionSearch.model;

public class Lane 
{
	  private Long laneId;
	  private String externLaneId;
	  private Long plazaId;
	  private String lprEnabled;
	  private String calculateTollAmount;
	  private Long hovOffSet;
	  private Long hovMinDeltaTime;
	  private Long hovMaxDeltaTime;
	  
	public Long getLaneId() {
		return laneId;
	}
	public void setLaneId(Long laneId) {
		this.laneId = laneId;
	}
	public String getExternLaneId() {
		return externLaneId;
	}
	public void setExternLaneId(String externLaneId) {
		this.externLaneId = externLaneId;
	}
	public Long getPlazaId() {
		return plazaId;
	}
	public void setPlazaId(Long plazaId) {
		this.plazaId = plazaId;
	}
	public String getLprEnabled() {
		return lprEnabled;
	}
	public void setLprEnabled(String lprEnabled) {
		this.lprEnabled = lprEnabled;
	}
	public String getCalculateTollAmount() {
		return calculateTollAmount;
	}
	public void setCalculateTollAmount(String calculateTollAmount) {
		this.calculateTollAmount = calculateTollAmount;
	}
	public Long getHovOffSet() {
		return hovOffSet;
	}
	public void setHovOffSet(Long hovOffSet) {
		this.hovOffSet = hovOffSet;
	}
	public Long getHovMinDeltaTime() {
		return hovMinDeltaTime;
	}
	public void setHovMinDeltaTime(Long hovMinDeltaTime) {
		this.hovMinDeltaTime = hovMinDeltaTime;
	}
	public Long getHovMaxDeltaTime() {
		return hovMaxDeltaTime;
	}
	public void setHovMaxDeltaTime(Long hovMaxDeltaTime) {
		this.hovMaxDeltaTime = hovMaxDeltaTime;
	}
}
