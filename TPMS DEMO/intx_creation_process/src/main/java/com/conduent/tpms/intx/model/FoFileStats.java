package com.conduent.tpms.intx.model;

import java.time.LocalDateTime;

public class FoFileStats {

	private Long fileControlId;

	private String trxFileName;
	
	private String distribFileName;
	
	private String processedFlag;
	
	private Long inputCount;

	private Long outputCount;
	
	private String fromAgency;

	private String toAgency;
	
	private String fileDate;
	
	private String fileSeqNum;
	
	private String fileType;

	private LocalDateTime updateTs;

	public Long getFileControlId() {
		return fileControlId;
	}

	public void setFileControlId(Long fileControlId) {
		this.fileControlId = fileControlId;
	}

	public String getTrxFileName() {
		return trxFileName;
	}

	public void setTrxFileName(String trxFileName) {
		this.trxFileName = trxFileName;
	}

	public String getDistribFileName() {
		return distribFileName;
	}

	public void setDistribFileName(String distribFileName) {
		this.distribFileName = distribFileName;
	}

	public String getProcessedFlag() {
		return processedFlag;
	}

	public void setProcessedFlag(String processedFlag) {
		this.processedFlag = processedFlag;
	}

	public Long getInputCount() {
		return inputCount;
	}

	public void setInputCount(Long inputCount) {
		this.inputCount = inputCount;
	}

	public Long getOutputCount() {
		return outputCount;
	}

	public void setOutputCount(Long outputCount) {
		this.outputCount = outputCount;
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

	public String getFileDate() {
		return fileDate;
	}

	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}

	public String getFileSeqNum() {
		return fileSeqNum;
	}

	public void setFileSeqNum(String fileSeqNum) {
		this.fileSeqNum = fileSeqNum;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public LocalDateTime getUpdateTs() {
		return updateTs;
	}

	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}

	@Override
	public String toString() {
		return "FoFileStats [fileControlId=" + fileControlId + ", trxFileName=" + trxFileName + ", distribFileName="
				+ distribFileName + ", processedFlag=" + processedFlag + ", inputCount=" + inputCount + ", outputCount="
				+ outputCount + ", fromAgency=" + fromAgency + ", toAgency=" + toAgency + ", fileDate=" + fileDate
				+ ", fileSeqNum=" + fileSeqNum + ", fileType=" + fileType + ", updateTs=" + updateTs + "]";
	}

	public FoFileStats() {
		super();
	}
	
}
