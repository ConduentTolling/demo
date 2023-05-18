package com.conduent.tpms.qatp.dto;

import java.io.Serializable;

import com.conduent.tpms.qatp.validation.BaseVO;

public class MtxFileNameDetailVO extends BaseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3724514409417122576L;

	private String plazaId;
	private String fileDateTime;
	private String serialNumber;

	public void setPlazaId(String plazaId) {
		plazaId = plazaId;
	}

	public String getFileDateTime() {
		return fileDateTime;
	}

	public void setFileDateTime(String fileDateTime) {
		this.fileDateTime = fileDateTime;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
