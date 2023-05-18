package com.conduent.tpms.qatp.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import com.conduent.tpms.qatp.constants.FileStatusInd;

public class ReconciliationCheckPoint
{
    private String fileName;
    private Date processDate;
    private Integer recordCount;
    private FileStatusInd fileStatusInd;
    private Long lastLaneTxTd;
    private LocalDateTime lastTxTimeStatmp;
    private String lastExternLaneId;
    private String lastExternPlazaId;
    private Long xferControlId;
    
    
	public Long getXferControlId() {
		return xferControlId;
	}
	public void setXferControlId(Long xferControlId) {
		this.xferControlId = xferControlId;
	}
    
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Date getProcessDate() {
		return processDate;
	}
	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}
	public Integer getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(Integer recordCount) {
		this.recordCount = recordCount;
	}
	public FileStatusInd getFileStatusInd() {
		return fileStatusInd;
	}
	public void setFileStatusInd(FileStatusInd fileStatusInd) {
		this.fileStatusInd = fileStatusInd;
	}
	public Long getLastLaneTxTd() {
		return lastLaneTxTd;
	}
	public void setLastLaneTxTd(Long lastLaneTxTd) {
		this.lastLaneTxTd = lastLaneTxTd;
	}

	
	public LocalDateTime getLastTxTimeStatmp() {
		return lastTxTimeStatmp;
	}
	public void setLastTxTimeStatmp(LocalDateTime lastTxTimeStatmp) {
		this.lastTxTimeStatmp = lastTxTimeStatmp;
	}
	public String getLastExternLaneId() {
		return lastExternLaneId;
	}
	public void setLastExternLaneId(String lastExternLaneId) {
		this.lastExternLaneId = lastExternLaneId;
	}
	public String getLastExternPlazaId() {
		return lastExternPlazaId;
	}
	public void setLastExternPlazaId(String lastExternPlazaId) {
		this.lastExternPlazaId = lastExternPlazaId;
	}
	
}