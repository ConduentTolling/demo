package com.conduent.tpms.intx.model;

import java.time.LocalDateTime;

public class IagFileStats {

	public IagFileStats() {
		super();
	}

	private String inputFileName;

	private String ictxFileName;
	
	private String icrxFileName;
	
	private String processedFlag;
	
	private String fileType;
	
	private String fileDate;
	
	private Long xferControlId;

	private Long atpFileId;

	private Long inputRecCount;

	private Long outputRecCount;

	private String fromAgency;

	private String toAgency;

	private LocalDateTime updateTs;

	public String getInputFileName() {
		return inputFileName;
	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
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

	public String getProcessedFlag() {
		return processedFlag;
	}

	public void setProcessedFlag(String processedFlag) {
		this.processedFlag = processedFlag;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileDate() {
		return fileDate;
	}

	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}

	public Long getXferControlId() {
		return xferControlId;
	}

	public void setXferControlId(Long xferControlId) {
		this.xferControlId = xferControlId;
	}

	public Long getAtpFileId() {
		return atpFileId;
	}

	public void setAtpFileId(Long atpFileId) {
		this.atpFileId = atpFileId;
	}

	public Long getInputRecCount() {
		return inputRecCount;
	}

	public void setInputRecCount(Long inputRecCount) {
		this.inputRecCount = inputRecCount;
	}

	public Long getOutputRecCount() {
		return outputRecCount;
	}

	public void setOutputRecCount(Long outputRecCount) {
		this.outputRecCount = outputRecCount;
	}

	public String getFromAgency() {
		return fromAgency;
	}

	public void setFromAgency(String fromAgency) {
		this.fromAgency = fromAgency;
	}

	public String getToAgency() {
		return toAgency;
	}

	public void setToAgency(String toAgency) {
		this.toAgency = toAgency;
	}

	public LocalDateTime getUpdateTs() {
		return updateTs;
	}

	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}

//	XFER_CONTROL_ID
//	ICTX_FILE_NAME
//	ICRX_FILE_NAME
//	PROCESSED_FLAG
//	INPUT_COUNT
//	OUTPUT_COUNT
//	FROM_AGENCY
//	TO_AGENCY
//	FILE_DATE
//	UPDATE_TS
//	ATP_FILE_ID
//	FILE_SEQ_NUMBER
//	FILE_TYPE
//	DEPOSIT_ID


}
