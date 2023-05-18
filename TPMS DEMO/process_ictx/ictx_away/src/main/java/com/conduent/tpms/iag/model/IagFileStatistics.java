package com.conduent.tpms.iag.model;

import java.time.LocalDateTime;

/**
 * Iag File Statistics Info
 * 
 * @author Urvashic
 *
 */
public class IagFileStatistics {

	private String inputFileName;

	private String outputFileName;

	private Long xferControlId;

	private Long atpFileId;

	private Long inputRecCount;

	private Long outputRecCount;

	private String fromAgency;

	private String toAgency;
	
	private String fileType;
	
	private String fileDate;
	
	private String fileNumber;

	public String getFileDate() {
		return fileDate;
	}

	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	private LocalDateTime updateTs;

	public IagFileStatistics() {
		super();
	}

	public String getInputFileName() {
		return inputFileName;
	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
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

	public String getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}

}
