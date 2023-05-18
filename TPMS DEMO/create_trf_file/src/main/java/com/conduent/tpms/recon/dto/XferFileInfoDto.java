package com.conduent.tpms.recon.dto;

public class XferFileInfoDto {
	
	private Long xferControlId;
	private String xferFileName;
	private String fileType;
	
	
	
	public Long getXferControlId() {
		return xferControlId;
	}
	public void setXferControlId(Long xferControlId) {
		this.xferControlId = xferControlId;
	}
	public String getXferFileName() {
		return xferFileName;
	}
	public void setXferFileName(String xferFileName) {
		this.xferFileName = xferFileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	@Override
	public String toString() {
		return "XferFileInfoDto [xferControlId=" + xferControlId + ", xferFileName=" + xferFileName + ", fileType="
				+ fileType + "]";
	}
	
	
}
