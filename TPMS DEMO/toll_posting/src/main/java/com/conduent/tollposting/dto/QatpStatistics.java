package com.conduent.tollposting.dto;

/**
 * QatpStatistics Info
 * 
 * @author urvashic
 *
 */
public class QatpStatistics {

	private Long atpFileId;

	private String fileType;

	private String fileName;

	private Long recordCount;

	private Long processRecCount;

	private Long xferControlId;

	public Long getAtpFileId() {
		return atpFileId;
	}

	public void setAtpFileId(Long atpFileId) {
		this.atpFileId = atpFileId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	public Long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}

	public Long getProcessRecCount() {
		return processRecCount;
	}

	public void setProcessRecCount(Long processRecCount) {
		this.processRecCount = processRecCount;
	}

	public Long getXferControlId() {
		return xferControlId;
	}

	public void setXferControlId(Long xferControlId) {
		this.xferControlId = xferControlId;
	}

	@Override
	public String toString() {
		return "QatpStatistics [atpFileId=" + atpFileId + ", fileType=" + fileType + ", fileName=" + fileName
				+ ", recordCount=" + recordCount + ", processRecCount=" + processRecCount + ", xferControlId="
				+ xferControlId + "]";
	}

}
