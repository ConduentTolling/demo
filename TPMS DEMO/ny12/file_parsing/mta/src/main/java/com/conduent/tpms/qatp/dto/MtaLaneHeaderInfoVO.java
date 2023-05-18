package com.conduent.tpms.qatp.dto;
import java.io.Serializable;

import com.conduent.tpms.qatp.validation.BaseVO;


public class MtaLaneHeaderInfoVO extends BaseVO implements Serializable {

	private static final long serialVersionUID = -3724514409417122576L;
	
	
	private String recordStart;
	private String laneNum;
	private String laneConfig;
	private String onTime;
	private String vaultId;
	private String readerid;
	public String getRecordStart() {
		return recordStart;
	}
	public void setRecordStart(String recordStart) {
		this.recordStart = recordStart;
	}
	public String getLaneNum() {
		return laneNum;
	}
	public void setLaneNum(String laneNum) {
		this.laneNum = laneNum;
	}
	public String getLaneConfig() {
		return laneConfig;
	}
	public void setLaneConfig(String laneConfig) {
		this.laneConfig = laneConfig;
	}
	public String getOnTime() {
		return onTime;
	}
	public void setOnTime(String onTime) {
		this.onTime = onTime;
	}
	public String getVaultId() {
		return vaultId;
	}
	public void setVaultId(String vaultId) {
		this.vaultId = vaultId;
	}
	public String getReaderid() {
		return readerid;
	}
	public void setReaderid(String readerid) {
		this.readerid = readerid;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
}










	