package com.conduent.tpms.iag.dto;

import java.io.Serializable;

public class TLaneDto implements Serializable{

	private static final long serialVersionUID = 1657025664325894764L;
	
	private Integer laneId;
	private String  externLaneId;
	private Integer  plazaId;
	private String  avi;
	private String  operationalMode;
	private String  status;
	private String  laneIdx;
	private String  laneMask;;
	private String  updateTs;
	private String  direction;
	private String  laneIp;
	private String  portNo;
	private String  hostqueueName;
	private String  localPortNO;
	private Long laneType;
	private String lprEnabled;
	private Integer sectionId;
	private String calculateTollAmount;
	
	public String getLprEnabled() {
		return lprEnabled;
	}
	public void setLprEnabled(String lprEnabled) {
		this.lprEnabled = lprEnabled;
	}
	public Integer getSectionId() {
		return sectionId;
	}
	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}
	public String getCalculateTollAmount() {
		return calculateTollAmount;
	}
	public void setCalculateTollAmount(String calculateTollAmount) {
		this.calculateTollAmount = calculateTollAmount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public String getAvi() {
		return avi;
	}
	public void setAvi(String avi) {
		this.avi = avi;
	}
	public String getOperationalMode() {
		return operationalMode;
	}
	public void setOperationalMode(String operationalMode) {
		this.operationalMode = operationalMode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Integer getPlazaId() {
		return plazaId;
	}
	public void setPlazaId(Integer plazaId) {
		this.plazaId = plazaId;
	}
	public String getLaneIdx() {
		return laneIdx;
	}
	public void setLaneIdx(String laneIdx) {
		this.laneIdx = laneIdx;
	}
	public String getLaneMask() {
		return laneMask;
	}
	public void setLaneMask(String laneMask) {
		this.laneMask = laneMask;
	}
	public String getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(String updateTs) {
		this.updateTs = updateTs;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getLaneIp() {
		return laneIp;
	}
	public void setLaneIp(String laneIp) {
		this.laneIp = laneIp;
	}
	public String getPortNo() {
		return portNo;
	}
	public void setPortNo(String portNo) {
		this.portNo = portNo;
	}
	public String getHostqueueName() {
		return hostqueueName;
	}
	public void setHostqueueName(String hostqueueName) {
		this.hostqueueName = hostqueueName;
	}
	public String getLocalPortNO() {
		return localPortNO;
	}
	public void setLocalPortNO(String localPortNO) {
		this.localPortNO = localPortNO;
	}
	public Long getLaneType() {
		return laneType;
	}
	public void setLaneType(Long laneType) {
		this.laneType = laneType;
	}
	
	@Override
	public String toString() {
		return "TLaneDto [laneId=" + laneId + ", externLaneId=" + externLaneId + ", plazaId=" + plazaId + ", avi=" + avi
				+ ", operationalMode=" + operationalMode + ", status=" + status + ", laneIdx=" + laneIdx + ", laneMask="
				+ laneMask + ", updateTs=" + updateTs + ", direction=" + direction + ", laneIp=" + laneIp + ", portNo="
				+ portNo + ", hostqueueName=" + hostqueueName + ", localPortNO=" + localPortNO + ", laneType="
				+ laneType + ", lprEnabled=" + lprEnabled + ", sectionId=" + sectionId + ", calculateTollAmount="
				+ calculateTollAmount + "]";
	}
	
}
