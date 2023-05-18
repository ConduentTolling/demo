package com.conduent.tpms.iag.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.conduent.tpms.iag.constants.FileProcessStatus;

/**
 * This class is used for file details
 * 
 * @author MAYURIG1
 *
 */
public class FileDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String fileName;
	private String fileType;
	private String processName;
	private long processId;
	private FileProcessStatus processStatus;
	private LocalDate txDate;
	private long laneTxId;
	private long serialNumber;
	private long fileCount;
	private Integer processedCount;
	private Integer successCount;
	private long exceptionCount;
	private LocalDateTime updateTs;
	private LocalDateTime fileDateTime;
	
	public LocalDateTime getFileDateTime() {
		return fileDateTime;
	}

	public void setFileDateTime(LocalDateTime fileDateTime) {
		this.fileDateTime = fileDateTime;
	}

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

	public long getProcessId() {
		return processId;
	}

	public void setProcessId(long processId) {
		this.processId = processId;
	}

	public FileProcessStatus getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(FileProcessStatus processStatus) {
		this.processStatus = processStatus;
	}

	public LocalDate getTxDate() {
		return txDate;
	}

	public void setTxDate(LocalDate txDate) {
		this.txDate = txDate;
	}

	public long getLaneTxId() {
		return laneTxId;
	}

	public void setLaneTxId(long laneTxId) {
		this.laneTxId = laneTxId;
	}

	public long getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(long serialNumber) {
		this.serialNumber = serialNumber;
	}

	public long getFileCount() {
		return fileCount;
	}

	public void setFileCount(long fileCount) {
		this.fileCount = fileCount;
	}

	public Integer getProcessedCount() {
		return processedCount;
	}

	public void setProcessedCount(Integer processedCount) {
		this.processedCount = processedCount;
	}

	public Integer getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}

	public long getExceptionCount() {
		return exceptionCount;
	}

	public void setExceptionCount(long exceptionCount) {
		this.exceptionCount = exceptionCount;
	}

	public LocalDateTime getUpdateTs() {
		return updateTs;
	}

	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}

	@Override
	public String toString() {
		return "FileDetails [fileName=" + fileName + ", fileType=" + fileType + ", processName=" + processName
				+ ", processId=" + processId + ", processStatus=" + processStatus + ", txDate=" + txDate + ", laneTxId="
				+ laneTxId + ", serialNumber=" + serialNumber + ", fileCount=" + fileCount + ", processedCount="
				+ processedCount + ", successCount=" + successCount + ", exceptionCount=" + exceptionCount
				+ ", updateTs=" + updateTs + ", fileDateTime=" + fileDateTime + "]";
	}

	
}
