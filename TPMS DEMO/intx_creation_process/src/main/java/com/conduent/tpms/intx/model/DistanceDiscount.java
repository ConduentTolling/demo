package com.conduent.tpms.intx.model;

/**
 * Distance Discount Model
 * 
 * @author deepeshb
 *
 */
public class DistanceDiscount {

	private Integer agencyId;
	private Integer plazaId;
	private String externPlazaId;
	private Integer laneId;
	private String externLaneId;
	private Long groupId;
	private String timeInd;
	private Long timeOutValue;
	private String entryExitInd;

	public Integer getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
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

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getTimeInd() {
		return timeInd;
	}

	public void setTimeInd(String timeInd) {
		this.timeInd = timeInd;
	}

	public Long getTimeOutValue() {
		return timeOutValue;
	}

	public void setTimeOutValue(Long timeOutValue) {
		this.timeOutValue = timeOutValue;
	}

	public String getEntryExitInd() {
		return entryExitInd;
	}

	public void setEntryExitInd(String entryExitInd) {
		this.entryExitInd = entryExitInd;
	}

}
