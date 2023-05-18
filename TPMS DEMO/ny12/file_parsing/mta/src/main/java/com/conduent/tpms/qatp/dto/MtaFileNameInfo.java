package com.conduent.tpms.qatp.dto;

import java.io.Serializable;

import com.conduent.tpms.qatp.validation.BaseVO;

public class MtaFileNameInfo extends BaseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3724514409417122576L;

	private String plazaId;
	private String fileDate;
	private String SerialNumber;

		
	public String getPlazaId() {
		return plazaId;
	}
	public void setPlazaId(String plazaId) {
		this.plazaId = plazaId;
	}
	public String getFileDate() {
		return fileDate;
	}
	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}
	public String getSerialNumber() {
		return SerialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		SerialNumber = serialNumber;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}