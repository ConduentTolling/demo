package com.conduent.tpms.recon.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class IncrTagStatusRecord implements Serializable {
	
	private static final long serialVersionUID = 974376802303349683L;
	
	String deviceNo;
	int agencyid;
	int plazaid;
	int tagstatus;
	String ctrlstring;
	LocalDateTime updateTs;
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public int getAgencyid() {
		return agencyid;
	}
	public void setAgencyid(int agencyid) {
		this.agencyid = agencyid;
	}
	public int getPlazaid() {
		return plazaid;
	}
	public void setPlazaid(int plazaid) {
		this.plazaid = plazaid;
	}
	public int getTagstatus() {
		return tagstatus;
	}
	public void setTagstatus(int tagstatus) {
		this.tagstatus = tagstatus;
	}
	public String getCtrlstring() {
		return ctrlstring;
	}
	public void setCtrlstring(String ctrlstring) {
		this.ctrlstring = ctrlstring;
	}
	public LocalDateTime getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}
	
	@Override
	public String toString() {
		return "IncrTagStatusRecord [deviceNo=" + deviceNo + ", agencyid=" + agencyid + ", plazaid=" + plazaid
				+ ", tagstatus=" + tagstatus + ", ctrlstring=" + ctrlstring + ", updateTs=" + updateTs + "]";
	}
	
	

}
