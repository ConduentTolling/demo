package com.conduent.tpms.iag.model;

public class LaneIdExtLaneInfo {
private Integer laneId;
private String externLaneId;
private Integer plazaId;
private String externPlazaId;
private Integer agencyId;
               
private Integer defaultPlanId;
private String revenueTime;
private String plazaTypeInd;
private String endPlazaInd;
	
private String sectionId;

public Integer getLaneId() {
	return laneId;
}

public void setLaneId(Integer laneId) {
	this.laneId = laneId;
}

public String getExternLaneId() {
	return externLaneId;
}

public void setExternLaneId(String externLaneId) {
	this.externLaneId = externLaneId;
}

public Integer getPlazaId() {
	return plazaId;
}

public void setPlazaId(Integer plazaId) {
	this.plazaId = plazaId;
}

public String getExternPlazaId() {
	return externPlazaId;
}

public void setExternPlazaId(String externPlazaId) {
	this.externPlazaId = externPlazaId;
}

public Integer getAgencyId() {
	return agencyId;
}

public void setAgencyId(Integer agencyId) {
	this.agencyId = agencyId;
}

public Integer getDefaultPlanId() {
	return defaultPlanId;
}

public void setDefaultPlanId(Integer defaultPlanId) {
	this.defaultPlanId = defaultPlanId;
}

public String getRevenueTime() {
	return revenueTime;
}

public void setRevenueTime(String revenueTime) {
	this.revenueTime = revenueTime;
}

public String getPlazaTypeInd() {
	return plazaTypeInd;
}

public void setPlazaTypeInd(String plazaTypeInd) {
	this.plazaTypeInd = plazaTypeInd;
}

public String getEndPlazaInd() {
	return endPlazaInd;
}

public void setEndPlazaInd(String endPlazaInd) {
	this.endPlazaInd = endPlazaInd;
}

public String getSectionId() {
	return sectionId;
}

public void setSectionId(String sectionId) {
	this.sectionId = sectionId;
}

@Override
public String toString() {
	return "LaneIdExtLaneInfo [laneId=" + laneId + ", externLaneId=" + externLaneId + ", plazaId=" + plazaId
			+ ", externPlazaId=" + externPlazaId + ", agencyId=" + agencyId + ", defaultPlanId=" + defaultPlanId
			+ ", revenueTime=" + revenueTime + ", plazaTypeInd=" + plazaTypeInd + ", endPlazaInd=" + endPlazaInd
			+ ", sectionId=" + sectionId + "]";
}



}
