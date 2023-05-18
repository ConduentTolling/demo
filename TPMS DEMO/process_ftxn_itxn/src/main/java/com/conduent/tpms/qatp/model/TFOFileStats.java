package com.conduent.tpms.qatp.model;

import java.sql.Date;
import java.sql.Timestamp;

public class TFOFileStats {
	
	private Long fileControlId;
	private String fileName;
	private String  distribFileName ;
	private String  processedFlag ;
	private Integer inputCount;
	private Integer outputCount;
	private String fromAgency;
	private String toAgency;
	private Date fileDate;
	private String fileSeqNum;
	private String fileType;
	private Timestamp updateTs;
	
	public Long getFileControlId() {
		return fileControlId;
	}
	public void setFileControlId(Long fileControlId) {
		this.fileControlId = fileControlId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	public Integer getInputCount() {
		return inputCount;
	}
	public void setInputCount(Integer inputCount) {
		this.inputCount = inputCount;
	}
	public Integer getOutputCount() {
		return outputCount;
	}
	public void setOutputCount(Integer outputCount) {
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
	public Date getFileDate() {
		return fileDate;
	}
	public void setFileDate(Date fileDate) {
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
	public Timestamp getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(Timestamp updateTs) {
		this.updateTs = updateTs;
	}
	
	
	@Override
	public String toString() {
		return "TFOFileStats [fileControlId=" + fileControlId + ", fileName=" + fileName + ", distribFileName="
				+ distribFileName + ", processedFlag=" + processedFlag + ", inputCount=" + inputCount + ", outputCount="
				+ outputCount + ", fromAgency=" + fromAgency + ", toAgency=" + toAgency + ", fileDate=" + fileDate
				+ ", fileSeqNum=" + fileSeqNum + ", fileType=" + fileType + ", updateTs=" + updateTs + "]";
	}

	

}
