package com.conduent.tpms.ictx.model;

/**
 * Reconciliation Checkpoint info
 * 
 * @author deepeshb
 *
 */
public class ReconciliationCheckPoint {

	public ReconciliationCheckPoint() {
		super();
	}

	private String fileName;

	private String fileStatusInd;

	private Long recordCount;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileStatusInd() {
		return fileStatusInd;
	}

	public void setFileStatusInd(String fileStatusInd) {
		this.fileStatusInd = fileStatusInd;
	}

	public Long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}

}
