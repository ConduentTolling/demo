package com.conduent.tpms.recon.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class ReconciliationDto {
	

	
	private Long laneTxId;
	private String accountAgencyId;
	private Long atpFileId;
	private Integer citationLevel;
	private String codeType;
	private Integer codeId;
	private Integer compositeTransactionId;
	private Integer currentTxnState;
	private String deleteFlag;
	private Double discountedAmount;
	private Long externFileId;
	private String hovFlag;
	private Integer laneId;
	private String matchedTxExternRefNo;
	private String planTypeId;
	private Integer plazaAgencyId;
	private Integer plazaId;
	private LocalDate postedDate;
	private LocalDate revenueDate;
	private String trxSource;
	private String txExternRefNo;
	private Integer txModSeq;
	private String txTypeInd;
	private String txSubTypeInd;
	private String tollSystemType;
	private LocalDateTime updateTs;
	
	// reconciliation
	
	private Long accountNumber;
	private Integer actualClass;
	private String additionalTag;
	private String aetFlag;
	private Integer agencyId;
	private Integer avcAxles;
	private Integer avcClass;
	private String avcProfile;
	private Integer avcReverseAxles;
	private Integer aviClass;
	private String classMisMatch;
	private Long collectorId;
	private Integer cscViolStatus;
	private String debitCredit;
	private Integer feeCollected;
	private  Double feeNoticeAmount;
	private  Double fullFareAmount;
	private String imageUsed;
	private Integer laneMode;
	private String nonRevenue;
	private String ocrConfidence;
	private String ocrImageIndex;
	private String plateCountry;
	private String plateState;
	private String plateNumber;
	private Integer plateType;
	private Double postedFareAmount;
	private String primaryPaymentTag;
	private Integer reconCode;
	private Integer reconSubCode;
	private String reconType;
	private String recordType;
	private Integer revisedClass;
	private Integer revisedExtraAxles;
	private Integer speedViolation;
	private Integer tagClass;
	private Integer tagIagClass;
	private String tagRead;
	private Integer tagSerialNumber;
	private String tagStatus;
	private Integer tollAmount;
	private Integer tollCollected;
	private String txnSerialNumber;
	private String txStatus;
	private OffsetDateTime txTimeStamp;
	private Integer vehicleSpeed;
	private Double videoFareAmount;
	private String txType;
	private String processStatus;
	private Integer reconTxStatus;
	private String isFinalState;
	private String createRt;
	
	private Integer reconStatusInd;
	private Integer reconSubCodeInd;
	
	
	
	public Long getLaneTxId() {
		return laneTxId;
	}
	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
	}
	public String getAccountAgencyId() {
		return accountAgencyId;
	}
	public void setAccountAgencyId(String accountAgencyId) {
		this.accountAgencyId = accountAgencyId;
	}
	public Long getAtpFileId() {
		return atpFileId;
	}
	public void setAtpFileId(Long atpFileId) {
		this.atpFileId = atpFileId;
	}
	public Integer getCitationLevel() {
		return citationLevel;
	}
	public void setCitationLevel(Integer citationLevel) {
		this.citationLevel = citationLevel;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public Integer getCodeId() {
		return codeId;
	}
	public void setCodeId(Integer codeId) {
		this.codeId = codeId;
	}
	public Integer getCompositeTransactionId() {
		return compositeTransactionId;
	}
	public void setCompositeTransactionId(Integer compositeTransactionId) {
		this.compositeTransactionId = compositeTransactionId;
	}
	public Integer getCurrentTxnState() {
		return currentTxnState;
	}
	public void setCurrentTxnState(Integer currentTxnState) {
		this.currentTxnState = currentTxnState;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public Double getDiscountedAmount() {
		return discountedAmount;
	}
	public void setDiscountedAmount(Double discountedAmount) {
		this.discountedAmount = discountedAmount;
	}
	public Long getExternFileId() {
		return externFileId;
	}
	public void setExternFileId(Long externFileId) {
		this.externFileId = externFileId;
	}
	public String getHovFlag() {
		return hovFlag;
	}
	public void setHovFlag(String hovFlag) {
		this.hovFlag = hovFlag;
	}
	public Integer getLaneId() {
		return laneId;
	}
	public void setLaneId(Integer laneId) {
		this.laneId = laneId;
	}
	public String getMatchedTxExternRefNo() {
		return matchedTxExternRefNo;
	}
	public void setMatchedTxExternRefNo(String matchedTxExternRefNo) {
		this.matchedTxExternRefNo = matchedTxExternRefNo;
	}
	
	public Integer getPlazaAgencyId() {
		return plazaAgencyId;
	}
	public void setPlazaAgencyId(Integer plazaAgencyId) {
		this.plazaAgencyId = plazaAgencyId;
	}
	public Integer getPlazaId() {
		return plazaId;
	}
	public void setPlazaId(Integer plazaId) {
		this.plazaId = plazaId;
	}
	public LocalDate getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(LocalDate postedDate) {
		this.postedDate = postedDate;
	}
	public LocalDate getRevenueDate() {
		return revenueDate;
	}
	public void setRevenueDate(LocalDate revenueDate) {
		this.revenueDate = revenueDate;
	}
	public String getTrxSource() {
		return trxSource;
	}
	public void setTrxSource(String trxSource) {
		this.trxSource = trxSource;
	}
	public String getTxExternRefNo() {
		return txExternRefNo;
	}
	public void setTxExternRefNo(String txExternRefNo) {
		this.txExternRefNo = txExternRefNo;
	}
	public Integer getTxModSeq() {
		return txModSeq;
	}
	public void setTxModSeq(Integer txModSeq) {
		this.txModSeq = txModSeq;
	}
	public String getTxTypeInd() {
		return txTypeInd;
	}
	public void setTxTypeInd(String txTypeInd) {
		this.txTypeInd = txTypeInd;
	}
	public LocalDateTime getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}
	public Long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Integer getActualClass() {
		return actualClass;
	}
	public void setActualClass(Integer actualClass) {
		this.actualClass = actualClass;
	}
	public String getAdditionalTag() {
		return additionalTag;
	}
	public void setAdditionalTag(String additionalTag) {
		this.additionalTag = additionalTag;
	}
	public String getAetFlag() {
		return aetFlag;
	}
	public void setAetFlag(String aetFlag) {
		this.aetFlag = aetFlag;
	}
	public Integer getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}
	public Integer getAvcAxles() {
		return avcAxles;
	}
	public void setAvcAxles(Integer avcAxles) {
		this.avcAxles = avcAxles;
	}
	public Integer getAvcClass() {
		return avcClass;
	}
	public void setAvcClass(Integer avcClass) {
		this.avcClass = avcClass;
	}
	public String getAvcProfile() {
		return avcProfile;
	}
	public void setAvcProfile(String avcProfile) {
		this.avcProfile = avcProfile;
	}
	public Integer getAvcReverseAxles() {
		return avcReverseAxles;
	}
	public void setAvcReverseAxles(Integer avcReverseAxles) {
		this.avcReverseAxles = avcReverseAxles;
	}
	public Integer getAviClass() {
		return aviClass;
	}
	public void setAviClass(Integer aviClass) {
		this.aviClass = aviClass;
	}
	public String getClassMisMatch() {
		return classMisMatch;
	}
	public void setClassMisMatch(String classMisMatch) {
		this.classMisMatch = classMisMatch;
	}
	public Long getCollectorId() {
		return collectorId;
	}
	public void setCollectorId(Long collectorId) {
		this.collectorId = collectorId;
	}
	public Integer getCscViolStatus() {
		return cscViolStatus;
	}
	public void setCscViolStatus(Integer cscViolStatus) {
		this.cscViolStatus = cscViolStatus;
	}
	public String getDebitCredit() {
		return debitCredit;
	}
	public void setDebitCredit(String debitCredit) {
		this.debitCredit = debitCredit;
	}
	public Integer getFeeCollected() {
		return feeCollected;
	}
	public void setFeeCollected(Integer feeCollected) {
		this.feeCollected = feeCollected;
	}
	public Double getFeeNoticeAmount() {
		return feeNoticeAmount;
	}
	public void setFeeNoticeAmount(Double feeNoticeAmount) {
		this.feeNoticeAmount = feeNoticeAmount;
	}
	public Double getFullFareAmount() {
		return fullFareAmount;
	}
	public void setFullFareAmount(Double fullFareAmount) {
		this.fullFareAmount = fullFareAmount;
	}
	public String getImageUsed() {
		return imageUsed;
	}
	public void setImageUsed(String imageUsed) {
		this.imageUsed = imageUsed;
	}
	public Integer getLaneMode() {
		return laneMode;
	}
	public void setLaneMode(Integer laneMode) {
		this.laneMode = laneMode;
	}
	public String getNonRevenue() {
		return nonRevenue;
	}
	public void setNonRevenue(String nonRevenue) {
		this.nonRevenue = nonRevenue;
	}
	public String getOcrConfidence() {
		return ocrConfidence;
	}
	public void setOcrConfidence(String ocrConfidence) {
		this.ocrConfidence = ocrConfidence;
	}
	public String getOcrImageIndex() {
		return ocrImageIndex;
	}
	public void setOcrImageIndex(String ocrImageIndex) {
		this.ocrImageIndex = ocrImageIndex;
	}
	public String getPlateCountry() {
		return plateCountry;
	}
	public void setPlateCountry(String plateCountry) {
		this.plateCountry = plateCountry;
	}
	public String getPlateState() {
		return plateState;
	}
	public void setPlateState(String plateState) {
		this.plateState = plateState;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public Integer getPlateType() {
		return plateType;
	}
	public void setPlateType(Integer plateType) {
		this.plateType = plateType;
	}
	public Double getPostedFareAmount() {
		return postedFareAmount;
	}
	public void setPostedFareAmount(Double postedFareAmount) {
		this.postedFareAmount = postedFareAmount;
	}
	public String getPrimaryPaymentTag() {
		return primaryPaymentTag;
	}
	public void setPrimaryPaymentTag(String primaryPaymentTag) {
		this.primaryPaymentTag = primaryPaymentTag;
	}
	public Integer getReconCode() {
		return reconCode;
	}
	public void setReconCode(Integer reconCode) {
		this.reconCode = reconCode;
	}
	public Integer getReconSubCode() {
		return reconSubCode;
	}
	public void setReconSubCode(Integer reconSubCode) {
		this.reconSubCode = reconSubCode;
	}
	public String getReconType() {
		return reconType;
	}
	public void setReconType(String reconType) {
		this.reconType = reconType;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public Integer getRevisedClass() {
		return revisedClass;
	}
	public void setRevisedClass(Integer revisedClass) {
		this.revisedClass = revisedClass;
	}
	public Integer getRevisedExtraAxles() {
		return revisedExtraAxles;
	}
	public void setRevisedExtraAxles(Integer revisedExtraAxles) {
		this.revisedExtraAxles = revisedExtraAxles;
	}
	public Integer getSpeedViolation() {
		return speedViolation;
	}
	public void setSpeedViolation(Integer speedViolation) {
		this.speedViolation = speedViolation;
	}
	public Integer getTagClass() {
		return tagClass;
	}
	public void setTagClass(Integer tagClass) {
		this.tagClass = tagClass;
	}
	public Integer getTagIagClass() {
		return tagIagClass;
	}
	public void setTagIagClass(Integer tagIagClass) {
		this.tagIagClass = tagIagClass;
	}
	public String getTagRead() {
		return tagRead;
	}
	public void setTagRead(String tagRead) {
		this.tagRead = tagRead;
	}
	public Integer getTagSerialNumber() {
		return tagSerialNumber;
	}
	public void setTagSerialNumber(Integer tagSerialNumber) {
		this.tagSerialNumber = tagSerialNumber;
	}
	public String getTagStatus() {
		return tagStatus;
	}
	public void setTagStatus(String tagStatus) {
		this.tagStatus = tagStatus;
	}
	public Integer getTollAmount() {
		return tollAmount;
	}
	public void setTollAmount(Integer tollAmount) {
		this.tollAmount = tollAmount;
	}
	public Integer getTollCollected() {
		return tollCollected;
	}
	public void setTollCollected(Integer tollCollected) {
		this.tollCollected = tollCollected;
	}
	public String getTxnSerialNumber() {
		return txnSerialNumber;
	}
	public void setTxnSerialNumber(String txnSerialNumber) {
		this.txnSerialNumber = txnSerialNumber;
	}
	public OffsetDateTime getTxTimeStamp() {
		return txTimeStamp;
	}
	public void setTxTimeStamp(OffsetDateTime txTimeStamp) {
		this.txTimeStamp = txTimeStamp;
	}
	public Integer getVehicleSpeed() {
		return vehicleSpeed;
	}
	public void setVehicleSpeed(Integer vehicleSpeed) {
		this.vehicleSpeed = vehicleSpeed;
	}
	public Double getVideoFareAmount() {
		return videoFareAmount;
	}
	public void setVideoFareAmount(Double videoFareAmount) {
		this.videoFareAmount = videoFareAmount;
	}
	public String getTxType() {
		return txType;
	}
	public void setTxType(String txType) {
		this.txType = txType;
	}
	public String getPlanTypeId() {
		return planTypeId;
	}
	public void setPlanTypeId(String planTypeId) {
		this.planTypeId = planTypeId;
	}
	public String getTxStatus() {
		return txStatus;
	}
	public void setTxStatus(String txStatus) {
		this.txStatus = txStatus;
	}
	
	public String getTxSubTypeInd() {
		return txSubTypeInd;
	}
	public void setTxSubTypeInd(String txSubTypeInd) {
		this.txSubTypeInd = txSubTypeInd;
	}
	public String getTollSystemType() {
		return tollSystemType;
	}
	public void setTollSystemType(String tollSystemType) {
		this.tollSystemType = tollSystemType;
	}
	public String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	public Integer getReconTxStatus() {
		return reconTxStatus;
	}
	public void setReconTxStatus(Integer reconTxStatus) {
		this.reconTxStatus = reconTxStatus;
	}
	public String getIsFinalState() {
		return isFinalState;
	}
	public void setIsFinalState(String isFinalState) {
		this.isFinalState = isFinalState;
	}
	public String getCreateRt() {
		return createRt;
	}
	public void setCreateRt(String createRt) {
		this.createRt = createRt;
	}
	public Integer getReconStatusInd() {
		return reconStatusInd;
	}
	public void setReconStatusInd(Integer reconStatusInd) {
		this.reconStatusInd = reconStatusInd;
	}
	public Integer getReconSubCodeInd() {
		return reconSubCodeInd;
	}
	public void setReconSubCodeInd(Integer reconSubCodeInd) {
		this.reconSubCodeInd = reconSubCodeInd;
	}
	@Override
	public String toString() {
		return "ReconciliationDto [laneTxId=" + laneTxId + ", accountAgencyId=" + accountAgencyId + ", atpFileId="
				+ atpFileId + ", citationLevel=" + citationLevel + ", codeType=" + codeType + ", codeId=" + codeId
				+ ", compositeTransactionId=" + compositeTransactionId + ", currentTxnState=" + currentTxnState
				+ ", deleteFlag=" + deleteFlag + ", discountedAmount=" + discountedAmount + ", externFileId="
				+ externFileId + ", hovFlag=" + hovFlag + ", laneId=" + laneId + ", matchedTxExternRefNo="
				+ matchedTxExternRefNo + ", planTypeId=" + planTypeId + ", plazaAgencyId=" + plazaAgencyId
				+ ", plazaId=" + plazaId + ", postedDate=" + postedDate + ", revenueDate=" + revenueDate
				+ ", trxSource=" + trxSource + ", txExternRefNo=" + txExternRefNo + ", txModSeq=" + txModSeq
				+ ", txTypeInd=" + txTypeInd + ", txSubTypeInd=" + txSubTypeInd + ", tollSystemType=" + tollSystemType
				+ ", updateTs=" + updateTs + ", accountNumber=" + accountNumber + ", actualClass=" + actualClass
				+ ", additionalTag=" + additionalTag + ", aetFlag=" + aetFlag + ", agencyId=" + agencyId + ", avcAxles="
				+ avcAxles + ", avcClass=" + avcClass + ", avcProfile=" + avcProfile + ", avcReverseAxles="
				+ avcReverseAxles + ", aviClass=" + aviClass + ", classMisMatch=" + classMisMatch + ", collectorId="
				+ collectorId + ", cscViolStatus=" + cscViolStatus + ", debitCredit=" + debitCredit + ", feeCollected="
				+ feeCollected + ", feeNoticeAmount=" + feeNoticeAmount + ", fullFareAmount=" + fullFareAmount
				+ ", imageUsed=" + imageUsed + ", laneMode=" + laneMode + ", nonRevenue=" + nonRevenue
				+ ", ocrConfidence=" + ocrConfidence + ", ocrImageIndex=" + ocrImageIndex + ", plateCountry="
				+ plateCountry + ", plateState=" + plateState + ", plateNumber=" + plateNumber + ", plateType="
				+ plateType + ", postedFareAmount=" + postedFareAmount + ", primaryPaymentTag=" + primaryPaymentTag
				+ ", reconCode=" + reconCode + ", reconSubCode=" + reconSubCode + ", reconType=" + reconType
				+ ", recordType=" + recordType + ", revisedClass=" + revisedClass + ", revisedExtraAxles="
				+ revisedExtraAxles + ", speedViolation=" + speedViolation + ", tagClass=" + tagClass + ", tagIagClass="
				+ tagIagClass + ", tagRead=" + tagRead + ", tagSerialNumber=" + tagSerialNumber + ", tagStatus="
				+ tagStatus + ", tollAmount=" + tollAmount + ", tollCollected=" + tollCollected + ", txnSerialNumber="
				+ txnSerialNumber + ", txStatus=" + txStatus + ", txTimeStamp=" + txTimeStamp + ", vehicleSpeed="
				+ vehicleSpeed + ", videoFareAmount=" + videoFareAmount + ", txType=" + txType + ", processStatus="
				+ processStatus + ", reconTxStatus=" + reconTxStatus + ", isFinalState=" + isFinalState + ", createRt="
				+ createRt + ", reconStatusInd=" + reconStatusInd + ", reconSubCodeInd=" + reconSubCodeInd + "]";
	}

}
