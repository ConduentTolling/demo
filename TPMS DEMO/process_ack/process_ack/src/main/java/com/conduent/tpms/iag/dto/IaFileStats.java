package com.conduent.tpms.iag.dto;

import java.io.Serializable;

public class IaFileStats implements Serializable {

	private static final long serialVersionUID = -2987860971989977763L;
	
	private String ictxFileName;
	private String icrxFileName;
	private String xferControlId;
	private String atpFileId;
	public String getAtpFileId() {
		return atpFileId;
	}
	public void setAtpFileId(String atpFileId) {
		this.atpFileId = atpFileId;
	}
	public String getIctxFileName() {
		return ictxFileName;
	}
	public void setIctxFileName(String ictxFileName) {
		this.ictxFileName = ictxFileName;
	}
	public String getIcrxFileName() {
		return icrxFileName;
	}
	public void setIcrxFileName(String icrxFileName) {
		this.icrxFileName = icrxFileName;
	}
	public String getXferControlId() {
		return xferControlId;
	}
	public void setXferControlId(String xferControlId) {
		this.xferControlId = xferControlId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
