package com.conduent.tpms.intx.model;

import java.time.LocalDateTime;

public class FoReconFileStats {

	private Long fileControlId;
	
	private String trxEpsFileName;

	private Long trxEpsCount;
	
	private Double trxEpsAmount;
	
	private String processedFlag;

	private String epsReconFileName;
	
	private Long epsReconCount;
	
	private String reconProcessedFlag;
	
	private String foReconFileName;

	private String fromAgency;

	private String toAgency;
	
	private Long fileSeqNum;

	private LocalDateTime updateTs;

	public FoReconFileStats() {
		super();
	}

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

	public Long getTrxEpsCount() {
		return trxEpsCount;
	}

	public void setTrxEpsCount(Long trxEpsCount) {
		this.trxEpsCount = trxEpsCount;
	}

	public Double getTrxEpsAmount() {
		return trxEpsAmount;
	}

	public void setTrxEpsAmount(Double trxEpsAmount) {
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

	public Long getEpsReconCount() {
		return epsReconCount;
	}

	public void setEpsReconCount(Long epsReconCount) {
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

	public Long getFileSeqNum() {
		return fileSeqNum;
	}

	public void setFileSeqNum(Long fileSeqNum) {
		this.fileSeqNum = fileSeqNum;
	}

	public LocalDateTime getUpdateTs() {
		return updateTs;
	}

	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}

	@Override
	public String toString() {
		return "FoReconFileStatistics [fileControlId=" + fileControlId + ", trxEpsFileName=" + trxEpsFileName
				+ ", trxEpsCount=" + trxEpsCount + ", trxEpsAmount=" + trxEpsAmount + ", processedFlag=" + processedFlag
				+ ", epsReconFileName=" + epsReconFileName + ", epsReconCount=" + epsReconCount
				+ ", reconProcessedFlag=" + reconProcessedFlag + ", foReconFileName=" + foReconFileName
				+ ", fromAgency=" + fromAgency + ", toAgency=" + toAgency + ", fileSeqNum=" + fileSeqNum + ", updateTs="
				+ updateTs + "]";
	}
	
}
