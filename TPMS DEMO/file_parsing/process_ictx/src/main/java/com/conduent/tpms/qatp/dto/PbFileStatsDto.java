package com.conduent.tpms.qatp.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class PbFileStatsDto implements Serializable
{
	private Long atpFileId;
	private Long depositId;
	private LocalDate fileDate;
	private Long fileSeqNumber;
	private String fileType;
	private String ictxFileName;
	private String fromAgency;
	private String toAgency;
	private Long inputCount;
	private Long outputCount;
	private String processedFlag;
	private LocalDateTime updateTs;
	private Long xferControlId;
	public Long getAtpFileId() {
		return atpFileId;
	}
	public void setAtpFileId(Long atpFileId) {
		this.atpFileId = atpFileId;
	}
	public Long getDepositId() {
		return depositId;
	}
	public void setDepositId(Long depositId) {
		this.depositId = depositId;
	}
	public LocalDate getFileDate() {
		return fileDate;
	}
	public void setFileDate(LocalDate fileDate) {
		this.fileDate = fileDate;
	}
	public Long getFileSeqNumber() {
		return fileSeqNumber;
	}
	public void setFileSeqNumber(Long fileSeqNumber) {
		this.fileSeqNumber = fileSeqNumber;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getIctxFileName() {
		return ictxFileName;
	}
	public void setIctxFileName(String ictxFileName) {
		this.ictxFileName = ictxFileName;
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
	public String getProcessedFlag() {
		return processedFlag;
	}
	public void setProcessedFlag(String processedFlag) {
		this.processedFlag = processedFlag;
	}
	public LocalDateTime getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}
	public Long getXferControlId() {
		return xferControlId;
	}
	public void setXferControlId(Long xferControlId) {
		this.xferControlId = xferControlId;
	}
	
	
	

}
