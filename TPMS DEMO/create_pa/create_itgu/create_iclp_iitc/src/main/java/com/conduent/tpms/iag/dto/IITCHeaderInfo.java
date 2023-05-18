package com.conduent.tpms.iag.dto;

import java.io.Serializable;

public class IITCHeaderInfo implements Serializable {
	
	private static final long serialVersionUID = 4489240691078090844L;
	
	private String recordCount;

	public String getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(String recordCount) {
		this.recordCount = recordCount;
	}
	
	

}
