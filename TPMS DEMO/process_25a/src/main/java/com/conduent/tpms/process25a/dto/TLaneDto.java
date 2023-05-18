package com.conduent.tpms.process25a.dto;

import java.io.Serializable;

public class TLaneDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer laneId;
	private String  externLaneId;
	private Integer  plazaId;
	
	private String  avi;
	private String  operationMode;
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
	public String getOperationMode() {
		return operationMode;
	}
	public void setOperationMode(String operationMode) {
		this.operationMode = operationMode;
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
}
