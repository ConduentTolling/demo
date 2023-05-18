package com.conduent.tpms.icrx.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 
 * @author urvashic
 *
 */
public class FileStats {

	private Long xferControlId;
	private String ictxFileName;
	private String icrxFileName;
	private String processedFlag;
	private Long inputCount;
	private Long outputCount;
	private String fromAgency;
	private String toAgency;
	private LocalDate fileDate;
	private LocalDateTime updateTs;
	private Integer atpFileId;
	private Integer fileSeqNumber;
	private String fileType;
	private Integer depositId;
	private LocalDateTime icrxFileDateTime;

	public Long getXferControlId() {
		return xferControlId;
	}

	public void setXferControlId(Long xferControlId) {
		this.xferControlId = xferControlId;
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

	public LocalDate getFileDate() {
		return fileDate;
	}

	public void setFileDate(LocalDate fileDate) {
		this.fileDate = fileDate;
	}

	public LocalDateTime getUpdateTs() {
		return updateTs;
	}

	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}

	public Integer getAtpFileId() {
		return atpFileId;
	}

	public void setAtpFileId(Integer atpFileId) {
		this.atpFileId = atpFileId;
	}

	public Integer getFileSeqNumber() {
		return fileSeqNumber;
	}

	public void setFileSeqNumber(Integer fileSeqNumber) {
		this.fileSeqNumber = fileSeqNumber;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Integer getDepositId() {
		return depositId;
	}

	public void setDepositId(Integer depositId) {
		this.depositId = depositId;
	}

	public LocalDateTime getIcrxFileDateTime() {
		return icrxFileDateTime;
	}

	public void setIcrxFileDateTime(LocalDateTime icrxFileDateTime) {
		this.icrxFileDateTime = icrxFileDateTime;
	}
	
	@Override
	public String toString() {
		return "FileStats [xferControlId=" + xferControlId + ", ictxFileName=" + ictxFileName + ", icrxFileName="
				+ icrxFileName + ", processedFlag=" + processedFlag + ", inputCount=" + inputCount + ", outputCount="
				+ outputCount + ", fromAgency=" + fromAgency + ", toAgency=" + toAgency + ", fileDate=" + fileDate
				+ ", updateTs=" + updateTs + ", atpFileId=" + atpFileId + ", fileSeqNumber=" + fileSeqNumber
				+ ", fileType=" + fileType + ", depositId=" + depositId + ", icrxFileDateTime=" + icrxFileDateTime + "]";
	}

}
