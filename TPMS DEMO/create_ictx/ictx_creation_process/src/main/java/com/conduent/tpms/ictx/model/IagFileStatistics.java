package com.conduent.tpms.ictx.model;

import java.time.LocalDateTime;

/**
 * Iag File Statistics Info
 * 
 * @author deepeshb
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

}
