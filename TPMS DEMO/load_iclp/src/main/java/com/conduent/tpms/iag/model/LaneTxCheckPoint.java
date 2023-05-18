package com.conduent.tpms.iag.model;

import java.sql.Timestamp;
import java.util.Date;

import com.conduent.tpms.iag.constants.FileProcessStatus;


public class LaneTxCheckPoint
{
    private String fileName;
    private String fileType;
    private String processName;
    private Long processId;
    private FileProcessStatus processStatus;
	private Date txDate;
    private Long laneTxTd;
    private Long serailsNumber;
    private Long fileCount;
    private Long processedCount;
    private Long successCount;
    private Long exceptionCount;
    private Timestamp updateTs;
    
    
    
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public Long getProcessId() {
		return processId;
	}
	public void setProcessId(Long processId) {
		this.processId = processId;
	}
    public FileProcessStatus getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(FileProcessStatus processStatus) {
		this.processStatus = processStatus;
	}
	public Date getTxDate() {
		return txDate;
	}
	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}
	public Long getLaneTxTd() {
		return laneTxTd;
	}
	public void setLaneTxTd(Long laneTxTd) {
		this.laneTxTd = laneTxTd;
	}
	public Long getSerailsNumber() {
		return serailsNumber;
	}
	public void setSerailsNumber(Long serailsNumber) {
		this.serailsNumber = serailsNumber;
	}
	public Long getFileCount() {
		return fileCount;
	}
	public void setFileCount(Long fileCount) {
		this.fileCount = fileCount;
	}
	public Long getProcessedCount() {
		return processedCount;
	}
	public void setProcessedCount(Long processedCount) {
		this.processedCount = processedCount;
	}
	public Long getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(Long successCount) {
		this.successCount = successCount;
	}
	public Long getExceptionCount() {
		return exceptionCount;
	}
	public void setExceptionCount(Long exceptionCount) {
		this.exceptionCount = exceptionCount;
	}
	public Timestamp getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(Timestamp updateTs) {
		this.updateTs = updateTs;
	}
    
   

}