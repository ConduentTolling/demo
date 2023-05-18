package com.conduent.tpms.intx.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * QatpStatistics Info
 * 
 * @author deepeshb
 *
 */

public class QatpStatistics {

	public QatpStatistics() {
		super();

	}

	private Long atpFileId;

	private String fileType;

	private String fileName;

	private LocalDate insertDate;

	private String insertTime;

	private Long recordCount;

	private Double amount;

	private String isProcessed;

	private LocalDate processDate;

	private String processTime;

	private Long processRecCount;

	private LocalDateTime updateTs;

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

	public LocalDate getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(LocalDate insertDate) {
		this.insertDate = insertDate;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public Long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getIsProcessed() {
		return isProcessed;
	}

	public void setIsProcessed(String isProcessed) {
		this.isProcessed = isProcessed;
	}

	public LocalDate getProcessDate() {
		return processDate;
	}

	public void setProcessDate(LocalDate processDate) {
		this.processDate = processDate;
	}

	public String getProcessTime() {
		return processTime;
	}

	public void setProcessTime(String processTime) {
		this.processTime = processTime;
	}

	public Long getProcessRecCount() {
		return processRecCount;
	}

	public void setProcessRecCount(Long processRecCount) {
		this.processRecCount = processRecCount;
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
