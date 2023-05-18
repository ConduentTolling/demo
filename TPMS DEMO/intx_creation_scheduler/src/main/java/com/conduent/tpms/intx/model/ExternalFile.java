package com.conduent.tpms.intx.model;

public class ExternalFile {
	
	private String fileId;

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	@Override
	public String toString() {
		return "ExternalFile [fileId=" + fileId + "]";
	}
	
}
