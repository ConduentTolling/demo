package com.conduent.tpms.qeval.model;

import java.sql.Date;
import java.sql.Timestamp;

public class XferControl 
{
	private Long xferControlId; 
	private Long xferFileId;
	private String xferFileName;
	private Date dateCreated; 
	private String timeCreated;
	private Long fileSize;
	private Long fileCheckSum; 
	private Long numRecs; 
	private Long hashTotal;
	private String xferPath;
	private String xferXmitStatus;
	private String xferRetry; 
	private Timestamp updateTs;
	private String fileType;
	public Long getXferControlId() {
		return xferControlId;
	}
	public void setXferControlId(Long xferControlId) {
		this.xferControlId = xferControlId;
	}
	public Long getXferFileId() {
		return xferFileId;
	}
	public void setXferFileId(Long xferFileId) {
		this.xferFileId = xferFileId;
	}
	public String getXferFileName() {
		return xferFileName;
	}
	public void setXferFileName(String xferFileName) {
		this.xferFileName = xferFileName;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getTimeCreated() {
		return timeCreated;
	}
	public void setTimeCreated(String timeCreated) {
		this.timeCreated = timeCreated;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public Long getFileCheckSum() {
		return fileCheckSum;
	}
	public void setFileCheckSum(Long fileCheckSum) {
		this.fileCheckSum = fileCheckSum;
	}
	public Long getNumRecs() {
		return numRecs;
	}
	public void setNumRecs(Long numRecs) {
		this.numRecs = numRecs;
	}
	public Long getHashTotal() {
		return hashTotal;
	}
	public void setHashTotal(Long hashTotal) {
		this.hashTotal = hashTotal;
	}
	public String getXferPath() {
		return xferPath;
	}
	public void setXferPath(String xferPath) {
		this.xferPath = xferPath;
	}
	public String getXferXmitStatus() {
		return xferXmitStatus;
	}
	public void setXferXmitStatus(String xferXmitStatus) {
		this.xferXmitStatus = xferXmitStatus;
	}
	public String getXferRetry() {
		return xferRetry;
	}
	public void setXferRetry(String xferRetry) {
		this.xferRetry = xferRetry;
	}
	public Timestamp getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(Timestamp updateTs) {
		this.updateTs = updateTs;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}
