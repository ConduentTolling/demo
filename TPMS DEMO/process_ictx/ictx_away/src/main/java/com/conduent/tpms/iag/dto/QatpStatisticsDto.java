package com.conduent.tpms.iag.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class QatpStatisticsDto {
	
	private Long atpFileId; 
	private String fileType;
	private String fileName;
	private LocalDate insertLocalDate; 
	private String timeCreated;
	private LocalDateTime insertTime;
	private Long recordCount; 
	private Long amount; 
	private Long isProcessed;
	private LocalDate processLocalDate;
	private LocalDate getProcessLocalDate; 
	private LocalDateTime processTime;
	private String processRecCount; 
	private LocalDateTime upLocalDateTs;
	public LocalDateTime getUpLocalDateTs() {
		return upLocalDateTs;
	}
	public void setUpLocalDateTs(LocalDateTime upLocalDateTs) {
		this.upLocalDateTs = upLocalDateTs;
	}
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
	public LocalDate getInsertLocalDate() {
		return insertLocalDate;
	}
	public void setInsertLocalDate(LocalDate insertLocalDate) {
		this.insertLocalDate = insertLocalDate;
	}
	public String getTimeCreated() {
		return timeCreated;
	}
	public void setTimeCreated(String timeCreated) {
		this.timeCreated = timeCreated;
	}
	public LocalDateTime getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(LocalDateTime insertTime) {
		this.insertTime = insertTime;
	}
	public Long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Long getIsProcessed() {
		return isProcessed;
	}
	public void setIsProcessed(Long isProcessed) {
		this.isProcessed = isProcessed;
	}
	public LocalDate getProcessLocalDate() {
		return processLocalDate;
	}
	public void setProcessLocalDate(LocalDate processLocalDate) {
		this.processLocalDate = processLocalDate;
	}
	public LocalDate getGetProcessLocalDate() {
		return getProcessLocalDate;
	}
	public void setGetProcessLocalDate(LocalDate getProcessLocalDate) {
		this.getProcessLocalDate = getProcessLocalDate;
	}
	public LocalDateTime getProcessTime() {
		return processTime;
	}
	public void setProcessTime(LocalDateTime processTime) {
		this.processTime = processTime;
	}
	public String getProcessRecCount() {
		return processRecCount;
	}
	public void setProcessRecCount(String processRecCount) {
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
				+ ", insertLocalDate=" + insertLocalDate + ", timeCreated=" + timeCreated + ", insertTime=" + insertTime
				+ ", recordCount=" + recordCount + ", amount=" + amount + ", isProcessed=" + isProcessed
				+ ", processLocalDate=" + processLocalDate + ", getProcessLocalDate=" + getProcessLocalDate + ", processTime=" + processTime
				+ ", processRecCount=" + processRecCount + ", xferControlId=" + xferControlId + "]";
	}
	
		
}
