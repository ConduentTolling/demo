package com.conduent.tpms.qatp.dto;

import java.io.Serializable;

import com.conduent.tpms.qatp.validation.BaseVO;

public class MtaHeaderinfoVO extends BaseVO implements Serializable {

	/**
	 * 
	 */

	private static final long serialVersionUID = 4489240691078090844L;
	private String recordType;
	private String agencyId;
	private String serviceCenter;
	private String plazaId;
	private String lastUpload;
	private String currentTs;
	private String total;
	private String recordCount;
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}
	public String getServiceCenter() {
		return serviceCenter;
	}
	public void setServiceCenter(String serviceCenter) {
		this.serviceCenter = serviceCenter;
	}
	public String getPlazaId() {
		return plazaId;
	}
	public void setPlazaId(String plazaId) {
		this.plazaId = plazaId;
	}
	public String getLastUpload() {
		return lastUpload;
	}
	public void setLastUpload(String lastUpload) {
		this.lastUpload = lastUpload;
	}
	public String getCurrentTs() {
		return currentTs;
	}
	public void setCurrentTs(String currentTs) {
		this.currentTs = currentTs;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(String recordCount) {
		this.recordCount = recordCount;
	}
	
}
	
	