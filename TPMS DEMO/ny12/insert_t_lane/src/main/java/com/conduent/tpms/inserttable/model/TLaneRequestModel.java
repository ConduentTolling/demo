package com.conduent.tpms.inserttable.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;


public class TLaneRequestModel implements Serializable{
	

	private static final long serialVersionUID = -6683213270280748046L;

	@ApiModelProperty(value = "LaneId", dataType = "Long", required = true, example = "20036")
	private  long    laneId;
	@ApiModelProperty(value = "ExternLaneId", dataType = "String", required = true, example = "ABC")
 	private  String  externLaneId;
	@ApiModelProperty(value = "Avi", dataType = "String", required = true, example = "Y")
	private  String  avi;
	@ApiModelProperty(value = "Avi", dataType = "String", required = true, example = "1")
	private  String    operationalMode;
	@ApiModelProperty(value = "Status", dataType = "String", required = true, example = "Active")
	private  String    status;
	@ApiModelProperty(value = "PlazaId", dataType = "Long", required = true, example = "008")
	private  long    plazaId;
	@ApiModelProperty(value = "LaneIDX", dataType = "Long", required = true, example = "2022873709")
	private  long    laneIDX;
	@ApiModelProperty(value = "LaneMask", dataType = "Long", required = true, example = "2344")
	private  long    laneMask;
	private  String  direction;
	private  String  laneIp;
	private  String  portNo;
	private  String  hostQueueName;
	private  String  localPortNo;
	private  long    laneType;
	private  String  calculateTollAmount;
	private  long    hovMaxDeltaTime;
	private  long    hovMinDeltaTime;
	private  long    hovOffset;
	private  String  imageResolution;
	private  String  isShoulder;
	private  String  lprEnabled;
	private  String    sectionId;
	private String updateTs;
	public long getLaneId() {
		return laneId;
	}
	public void setLaneId(long laneId) {
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
	public long getPlazaId() {
		return plazaId;
	}
	public void setPlazaId(long plazaId) {
		this.plazaId = plazaId;
	}
	public long getLaneIDX() {
		return laneIDX;
	}
	public void setLaneIDX(long laneIDX) {
		this.laneIDX = laneIDX;
	}
	public long getLaneMask() {
		return laneMask;
	}
	public void setLaneMask(long laneMask) {
		this.laneMask = laneMask;
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
	public String getHostQueueName() {
		return hostQueueName;
	}
	public void setHostQueueName(String hostQueueName) {
		this.hostQueueName = hostQueueName;
	}
	public String getLocalPortNo() {
		return localPortNo;
	}
	public void setLocalPortNo(String localPortNo) {
		this.localPortNo = localPortNo;
	}
	public long getLaneType() {
		return laneType;
	}
	public void setLaneType(long laneType) {
		this.laneType = laneType;
	}
	public String getCalculateTollAmount() {
		return calculateTollAmount;
	}
	public void setCalculateTollAmount(String calculateTollAmount) {
		this.calculateTollAmount = calculateTollAmount;
	}
	public long getHovMaxDeltaTime() {
		return hovMaxDeltaTime;
	}
	public void setHovMaxDeltaTime(long hovMaxDeltaTime) {
		this.hovMaxDeltaTime = hovMaxDeltaTime;
	}
	public long getHovMinDeltaTime() {
		return hovMinDeltaTime;
	}
	public void setHovMinDeltaTime(long hovMinDeltaTime) {
		this.hovMinDeltaTime = hovMinDeltaTime;
	}
	public long getHovOffset() {
		return hovOffset;
	}
	public void setHovOffset(long hovOffset) {
		this.hovOffset = hovOffset;
	}
	public String getImageResolution() {
		return imageResolution;
	}
	public void setImageResolution(String imageResolution) {
		this.imageResolution = imageResolution;
	}
	public String getIsShoulder() {
		return isShoulder;
	}
	public void setIsShoulder(String isShoulder) {
		this.isShoulder = isShoulder;
	}
	public String getLprEnabled() {
		return lprEnabled;
	}
	public void setLprEnabled(String lprEnabled) {
		this.lprEnabled = lprEnabled;
	}
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	public String getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(String updateTs) {
		this.updateTs = updateTs;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "TLaneRequestModel [laneId=" + laneId + ", externLaneId=" + externLaneId + ", avi=" + avi
				+ ", operationalMode=" + operationalMode + ", status=" + status + ", plazaId=" + plazaId + ", laneIDX="
				+ laneIDX + ", laneMask=" + laneMask + ", direction=" + direction + ", laneIp=" + laneIp + ", portNo="
				+ portNo + ", hostQueueName=" + hostQueueName + ", localPortNo=" + localPortNo + ", laneType="
				+ laneType + ", calculateTollAmount=" + calculateTollAmount + ", hovMaxDeltaTime=" + hovMaxDeltaTime
				+ ", hovMinDeltaTime=" + hovMinDeltaTime + ", hovOffset=" + hovOffset + ", imageResolution="
				+ imageResolution + ", isShoulder=" + isShoulder + ", lprEnabled=" + lprEnabled + ", sectionId="
				+ sectionId + ", updateTs=" + updateTs + "]";
	}
	

	
	
		
}
