package com.conduent.tpms.iag.model;

import java.sql.Date;
import java.sql.Timestamp;

public class QatpStatistics {
	
	private Long atpFileId;
	private String fileType;
	private String  fileName ;
	private Date insertDate;
	private String insertTime;
	private Long recordCount;
	private Long amount;
	private String iSProcessed;
	private Date processDate;
	private String processTime;
	private Long processRecCount;
	private Timestamp updateTs;
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
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
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
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getiSProcessed() {
		return iSProcessed;
	}
	public void setiSProcessed(String iSProcessed) {
		this.iSProcessed = iSProcessed;
	}
	public Date getProcessDate() {
		return processDate;
	}
	public void setProcessDate(Date processDate) {
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
	public Timestamp getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(Timestamp updateTs) {
		this.updateTs = updateTs;
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
				+ ", insertDate=" + insertDate + ", insertTime=" + insertTime + ", recordCount=" + recordCount
				+ ", amount=" + amount + ", iSProcessed=" + iSProcessed + ", processDate=" + processDate
				+ ", processTime=" + processTime + ", processRecCount=" + processRecCount + ", updateTs=" + updateTs
				+ ", xferControlId=" + xferControlId + "]";
	}

}