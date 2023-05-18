package com.conduent.tpms.iag.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class IncrTagStatusRecord implements Serializable {

	private static final long serialVersionUID = 974376802303349683L;

	String deviceNo;
	Long agencyId;
	Long plazaId;
	Long oldTagStatus;
	String oldCtrlString;
	LocalDateTime updateTs;
	String tagProtocol;
	String tagType;
	String tagMount;
	String tagClass;

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public Long getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	public Long getPlazaId() {
		return plazaId;
	}

	public void setPlazaId(Long plazaId) {
		this.plazaId = plazaId;
	}

	public Long getOldTagStatus() {
		return oldTagStatus;
	}

	public void setOldTagStatus(Long oldTagStatus) {
		this.oldTagStatus = oldTagStatus;
	}

	public String getOldCtrlString() {
		return oldCtrlString;
	}

	public void setOldCtrlString(String oldCtrlString) {
		this.oldCtrlString = oldCtrlString;
	}

	public LocalDateTime getUpdateTs() {
		return updateTs;
	}

	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}

	public String getTagProtocol() {
		return tagProtocol;
	}

	public void setTagProtocol(String tagProtocol) {
		this.tagProtocol = tagProtocol;
	}

	public String getTagType() {
		return tagType;
	}

	public void setTagType(String tagType) {
		this.tagType = tagType;
	}

	public String getTagMount() {
		return tagMount;
	}

	public void setTagMount(String tagMount) {
		this.tagMount = tagMount;
	}

	public String getTagClass() {
		return tagClass;
	}

	public void setTagClass(String tagClass) {
		this.tagClass = tagClass;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "IncrTagStatusRecord [deviceNo=" + deviceNo + ", agencyId=" + agencyId + ", plazaId=" + plazaId
				+ ", oldTagStatus=" + oldTagStatus + ", oldCtrlString=" + oldCtrlString + ", updateTs=" + updateTs
				+ ", tagProtocol=" + tagProtocol + ", tagType=" + tagType + ", tagMount=" + tagMount + ", tagClass="
				+ tagClass + "]";
	}

}
