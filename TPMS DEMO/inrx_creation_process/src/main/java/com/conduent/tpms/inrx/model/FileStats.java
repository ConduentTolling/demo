package com.conduent.tpms.inrx.model;

import java.sql.Timestamp;

/**
 * 
 * @author petetid
 *
 */
public class FileStats {

	private Long fileControlId;
    private String trxEpsFileName;
    private Integer trxEpsCount;
    private Integer trxEpsAmount;
    private String processedFlag;
    private String epsReconFileName;
    private Integer epsReconCount;
    private String reconProcessedFlag;
    private String foReconFileName;
    private String fromAgency;
    private String toAgency;
    private Integer fileSeqNumber;
    private Timestamp updateTs;
	public Long getFileControlId() {
		return fileControlId;
	}
	public void setFileControlId(Long fileControlId) {
		this.fileControlId = fileControlId;
	}
	public String getTrxEpsFileName() {
		return trxEpsFileName;
	}
	public void setTrxEpsFileName(String trxEpsFileName) {
		this.trxEpsFileName = trxEpsFileName;
	}
	public Integer getTrxEpsCount() {
		return trxEpsCount;
	}
	public void setTrxEpsCount(Integer trxEpsCount) {
		this.trxEpsCount = trxEpsCount;
	}
	public Integer getTrxEpsAmount() {
		return trxEpsAmount;
	}
	public void setTrxEpsAmount(Integer trxEpsAmount) {
		this.trxEpsAmount = trxEpsAmount;
	}
	public String getProcessedFlag() {
		return processedFlag;
	}
	public void setProcessedFlag(String processedFlag) {
		this.processedFlag = processedFlag;
	}
	public String getEpsReconFileName() {
		return epsReconFileName;
	}
	public void setEpsReconFileName(String epsReconFileName) {
		this.epsReconFileName = epsReconFileName;
	}
	public Integer getEpsReconCount() {
		return epsReconCount;
	}
	public void setEpsReconCount(Integer epsReconCount) {
		this.epsReconCount = epsReconCount;
	}
	public String getReconProcessedFlag() {
		return reconProcessedFlag;
	}
	public void setReconProcessedFlag(String reconProcessedFlag) {
		this.reconProcessedFlag = reconProcessedFlag;
	}
	public String getFoReconFileName() {
		return foReconFileName;
	}
	public void setFoReconFileName(String foReconFileName) {
		this.foReconFileName = foReconFileName;
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
	public Integer getFileSeqNumber() {
		return fileSeqNumber;
	}
	public void setFileSeqNumber(Integer fileSeqNumber) {
		this.fileSeqNumber = fileSeqNumber;
	}
	public Timestamp getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(Timestamp updateTs) {
		this.updateTs = updateTs;
	}
	@Override
	public String toString() {
		return "FileStats [fileControlId=" + fileControlId + ", trxEpsFileName=" + trxEpsFileName + ", trxEpsCount="
				+ trxEpsCount + ", trxEpsAmount=" + trxEpsAmount + ", processedFlag=" + processedFlag
				+ ", epsReconFileName=" + epsReconFileName + ", epsReconCount=" + epsReconCount
				+ ", reconProcessedFlag=" + reconProcessedFlag + ", foReconFileName=" + foReconFileName
				+ ", fromAgency=" + fromAgency + ", toAgency=" + toAgency + ", fileSeqNumber=" + fileSeqNumber + "]";
	}

	
}
