package com.conduent.tpms.process25a.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class AgencyTxPendingDto {

	
	private Long laneTxId;
	private String txExternRefNo;
	private Long txSeqNumber;
	private Long externFileId;
	//private LocalDateTime txTimestamp;
	private OffsetDateTime txTimestamp;
	private LocalDate txDate;
	private Integer txModSeq;
	private String txType;
	private String txSubtype;
	private String tollSystemType;
	private Integer laneMode;
	private Integer laneType;
	private Integer laneState;
	private Integer laneHealth;
	private Integer plazaAgencyId;
	private Integer plazaId;
	private Integer laneId;
	private Integer collectorId;
	private Integer tourSegmentId;
	private String entryDataSource;
	private Integer entryLaneId;
	private Integer entryPlazaId;
	//private LocalDateTime entryTimestamp;
	private OffsetDateTime entryTimestamp;
	private Integer entryTxSeqNumber;
	private Integer entryVehicleSpeed;
	private Integer laneTxStatus;
	private Integer lanetxType;
	private Integer tollRevenueType;
	private Integer actualClass;
	private Integer actualAxles;
	private Integer actualExtraAxles;
	private Integer collectorClass;
	private Integer collectorAxles;
	private Integer preclassClass;
	private Integer preclassAxles;
	private Integer postclassClass;
	private Integer postclassAxles;
	private Integer forwardAxles;
	private Integer reverseAxles;
	private Integer fullFareAmount;
	private Integer discountedAmount;
	private Double collectedAmount;
	private Double unrealizedAmount;
	private String isDiscountable;
	private String isMedianFare;
	private String isPeak;
	private Integer priceScheduleId;
	private Integer vehicleSpeed;
	private Integer receiptIssued;
	private String deviceNo;
	private Integer accountType;
	private Integer deviceCodedClass;
	private Integer deviceAgencyClass;
	private Integer deviceIagClass;
	private Integer deviceAxles;
	private Long etcAccountId;
	private Integer accountAgencyId;
	private Integer readAviClass;
	private Integer readAviAxles;
	private String deviceProgramStatus;
	private String bufferReadFlag;
	private String laneDeviceStatus;
	private Integer postDeviceStatus;
	private Double preTxnBalance;
	private Integer planTypeId;
	private Integer etcTxStatus;
	private String speedViolFlag;
	private String imageTaken;
	private String plateCountry;
	private String plateState;
	private String plateNumber;
	private LocalDate revenueDate;
	private LocalDate postedDate;
	private String atpFileId;
	private LocalDateTime updateTs;
	private String isReversed;
	private Integer corrReasonId;
	private LocalDate reconDate;
	private Integer reconStatusInd;
	private Integer reconSubCodeInd;
	private LocalDate externFileDate;
	private Integer mileage;
	private Integer deviceReadCount;
	private Integer deviceWriteCount;
	private Integer entryDeviceReadCount;
	private Integer entryDeviceWriteCount;
	private String depositeId;
	private String cscLookupKey;
	private String reciprocityTrx;
	private Double tollAmount;
	private Long dstAtpFileId;
	private Integer sourceSystem;
	private Double etcFareAmount;
	private Double postedFareAmount;
	private Double expectedRevenueAmount;
	private Double videoFareAmount;
	private Double cashFareAmount;
	private Integer txStatus;
	
	
	
	public Long getLaneTxId() {
		return laneTxId;
	}
	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
	}
	public String getTxExternRefNo() {
		return txExternRefNo;
	}
	public void setTxExternRefNo(String txExternRefNo) {
		this.txExternRefNo = txExternRefNo;
	}
	public Long getTxSeqNumber() {
		return txSeqNumber;
	}
	public void setTxSeqNumber(Long txSeqNumber) {
		this.txSeqNumber = txSeqNumber;
	}
	public Long getExternFileId() {
		return externFileId;
	}
	public void setExternFileId(Long externFileId) {
		this.externFileId = externFileId;
	}

	public LocalDate getTxDate() {
		return txDate;
	}
	public void setTxDate(LocalDate txDate) {
		this.txDate = txDate;
	}
	public Integer getTxModSeq() {
		return txModSeq;
	}
	public void setTxModSeq(Integer txModSeq) {
		this.txModSeq = txModSeq;
	}
	public String getTxType() {
		return txType;
	}
	public void setTxType(String txType) {
		this.txType = txType;
	}
	public String getTxSubtype() {
		return txSubtype;
	}
	public void setTxSubtype(String txSubtype) {
		this.txSubtype = txSubtype;
	}
	public String getTollSystemType() {
		return tollSystemType;
	}
	public void setTollSystemType(String tollSystemType) {
		this.tollSystemType = tollSystemType;
	}
	public Integer getLaneMode() {
		return laneMode;
	}
	public void setLaneMode(Integer laneMode) {
		this.laneMode = laneMode;
	}
	public Integer getLaneType() {
		return laneType;
	}
	public void setLaneType(Integer laneType) {
		this.laneType = laneType;
	}
	public Integer getLaneState() {
		return laneState;
	}
	public void setLaneState(Integer laneState) {
		this.laneState = laneState;
	}
	public Integer getLaneHealth() {
		return laneHealth;
	}
	public void setLaneHealth(Integer laneHealth) {
		this.laneHealth = laneHealth;
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
	public Integer getLaneId() {
		return laneId;
	}
	public void setLaneId(Integer laneId) {
		this.laneId = laneId;
	}
	public Integer getCollectorId() {
		return collectorId;
	}
	public void setCollectorId(Integer collectorId) {
		this.collectorId = collectorId;
	}
	public Integer getTourSegmentId() {
		return tourSegmentId;
	}
	public void setTourSegmentId(Integer tourSegmentId) {
		this.tourSegmentId = tourSegmentId;
	}
	public String getEntryDataSource() {
		return entryDataSource;
	}
	public void setEntryDataSource(String entryDataSource) {
		this.entryDataSource = entryDataSource;
	}
	public Integer getEntryLaneId() {
		return entryLaneId;
	}
	public void setEntryLaneId(Integer entryLaneId) {
		this.entryLaneId = entryLaneId;
	}
	public Integer getEntryPlazaId() {
		return entryPlazaId;
	}
	public void setEntryPlazaId(Integer entryPlazaId) {
		this.entryPlazaId = entryPlazaId;
	}

	public OffsetDateTime getTxTimestamp() {
		return txTimestamp;
	}
	public void setTxTimestamp(OffsetDateTime txTimestamp) {
		this.txTimestamp = txTimestamp;
	}
	public OffsetDateTime getEntryTimestamp() {
		return entryTimestamp;
	}
	public void setEntryTimestamp(OffsetDateTime entryTimestamp) {
		this.entryTimestamp = entryTimestamp;
	}
	public Integer getEntryTxSeqNumber() {
		return entryTxSeqNumber;
	}
	public void setEntryTxSeqNumber(Integer entryTxSeqNumber) {
		this.entryTxSeqNumber = entryTxSeqNumber;
	}
	public Integer getEntryVehicleSpeed() {
		return entryVehicleSpeed;
	}
	public void setEntryVehicleSpeed(Integer entryVehicleSpeed) {
		this.entryVehicleSpeed = entryVehicleSpeed;
	}
	public Integer getLaneTxStatus() {
		return laneTxStatus;
	}
	public void setLaneTxStatus(Integer laneTxStatus) {
		this.laneTxStatus = laneTxStatus;
	}
	public Integer getLanetxType() {
		return lanetxType;
	}
	public void setLanetxType(Integer lanetxType) {
		this.lanetxType = lanetxType;
	}
	public Integer getTollRevenueType() {
		return tollRevenueType;
	}
	public void setTollRevenueType(Integer tollRevenueType) {
		this.tollRevenueType = tollRevenueType;
	}
	public Integer getActualClass() {
		return actualClass;
	}
	public void setActualClass(Integer actualClass) {
		this.actualClass = actualClass;
	}
	public Integer getActualAxles() {
		return actualAxles;
	}
	public void setActualAxles(Integer actualAxles) {
		this.actualAxles = actualAxles;
	}
	public Integer getActualExtraAxles() {
		return actualExtraAxles;
	}
	public void setActualExtraAxles(Integer actualExtraAxles) {
		this.actualExtraAxles = actualExtraAxles;
	}
	public Integer getCollectorClass() {
		return collectorClass;
	}
	public void setCollectorClass(Integer collectorClass) {
		this.collectorClass = collectorClass;
	}
	public Integer getCollectorAxles() {
		return collectorAxles;
	}
	public void setCollectorAxles(Integer collectorAxles) {
		this.collectorAxles = collectorAxles;
	}
	public Integer getPreclassClass() {
		return preclassClass;
	}
	public void setPreclassClass(Integer preclassClass) {
		this.preclassClass = preclassClass;
	}
	public Integer getPreclassAxles() {
		return preclassAxles;
	}
	public void setPreclassAxles(Integer preclassAxles) {
		this.preclassAxles = preclassAxles;
	}
	public Integer getPostclassClass() {
		return postclassClass;
	}
	public void setPostclassClass(Integer postclassClass) {
		this.postclassClass = postclassClass;
	}
	public Integer getPostclassAxles() {
		return postclassAxles;
	}
	public void setPostclassAxles(Integer postclassAxles) {
		this.postclassAxles = postclassAxles;
	}
	public Integer getForwardAxles() {
		return forwardAxles;
	}
	public void setForwardAxles(Integer forwardAxles) {
		this.forwardAxles = forwardAxles;
	}
	public Integer getReverseAxles() {
		return reverseAxles;
	}
	public void setReverseAxles(Integer reverseAxles) {
		this.reverseAxles = reverseAxles;
	}
	public Integer getFullFareAmount() {
		return fullFareAmount;
	}
	public void setFullFareAmount(Integer fullFareAmount) {
		this.fullFareAmount = fullFareAmount;
	}
	public Integer getDiscountedAmount() {
		return discountedAmount;
	}
	public void setDiscountedAmount(Integer discountedAmount) {
		this.discountedAmount = discountedAmount;
	}
	public Double getCollectedAmount() {
		return collectedAmount;
	}
	public void setCollectedAmount(Double collectedAmount) {
		this.collectedAmount = collectedAmount;
	}
	public Double getUnrealizedAmount() {
		return unrealizedAmount;
	}
	public void setUnrealizedAmount(Double unrealizedAmount) {
		this.unrealizedAmount = unrealizedAmount;
	}
	public String getIsDiscountable() {
		return isDiscountable;
	}
	public void setIsDiscountable(String isDiscountable) {
		this.isDiscountable = isDiscountable;
	}
	public String getIsMedianFare() {
		return isMedianFare;
	}
	public void setIsMedianFare(String isMedianFare) {
		this.isMedianFare = isMedianFare;
	}
	public String getIsPeak() {
		return isPeak;
	}
	public void setIsPeak(String isPeak) {
		this.isPeak = isPeak;
	}
	public Integer getPriceScheduleId() {
		return priceScheduleId;
	}
	public void setPriceScheduleId(Integer priceScheduleId) {
		this.priceScheduleId = priceScheduleId;
	}
	public Integer getVehicleSpeed() {
		return vehicleSpeed;
	}
	public void setVehicleSpeed(Integer vehicleSpeed) {
		this.vehicleSpeed = vehicleSpeed;
	}
	public Integer getReceiptIssued() {
		return receiptIssued;
	}
	public void setReceiptIssued(Integer receiptIssued) {
		this.receiptIssued = receiptIssued;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public Integer getDeviceCodedClass() {
		return deviceCodedClass;
	}
	public void setDeviceCodedClass(Integer deviceCodedClass) {
		this.deviceCodedClass = deviceCodedClass;
	}
	public Integer getDeviceAgencyClass() {
		return deviceAgencyClass;
	}
	public void setDeviceAgencyClass(Integer deviceAgencyClass) {
		this.deviceAgencyClass = deviceAgencyClass;
	}
	public Integer getDeviceIagClass() {
		return deviceIagClass;
	}
	public void setDeviceIagClass(Integer deviceIagClass) {
		this.deviceIagClass = deviceIagClass;
	}
	public Integer getDeviceAxles() {
		return deviceAxles;
	}
	public void setDeviceAxles(Integer deviceAxles) {
		this.deviceAxles = deviceAxles;
	}
	public Long getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(Long etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	public Integer getAccountAgencyId() {
		return accountAgencyId;
	}
	public void setAccountAgencyId(Integer accountAgencyId) {
		this.accountAgencyId = accountAgencyId;
	}
	public Integer getReadAviClass() {
		return readAviClass;
	}
	public void setReadAviClass(Integer readAviClass) {
		this.readAviClass = readAviClass;
	}
	public Integer getReadAviAxles() {
		return readAviAxles;
	}
	public void setReadAviAxles(Integer readAviAxles) {
		this.readAviAxles = readAviAxles;
	}
	public String getDeviceProgramStatus() {
		return deviceProgramStatus;
	}
	public void setDeviceProgramStatus(String deviceProgramStatus) {
		this.deviceProgramStatus = deviceProgramStatus;
	}
	public String getBufferReadFlag() {
		return bufferReadFlag;
	}
	public void setBufferReadFlag(String bufferReadFlag) {
		this.bufferReadFlag = bufferReadFlag;
	}
	public String getLaneDeviceStatus() {
		return laneDeviceStatus;
	}
	public void setLaneDeviceStatus(String laneDeviceStatus) {
		this.laneDeviceStatus = laneDeviceStatus;
	}
	public Integer getPostDeviceStatus() {
		return postDeviceStatus;
	}
	public void setPostDeviceStatus(Integer postDeviceStatus) {
		this.postDeviceStatus = postDeviceStatus;
	}
	public Double getPreTxnBalance() {
		return preTxnBalance;
	}
	public void setPreTxnBalance(Double preTxnBalance) {
		this.preTxnBalance = preTxnBalance;
	}
	public Integer getPlanTypeId() {
		return planTypeId;
	}
	public void setPlanTypeId(Integer planTypeId) {
		this.planTypeId = planTypeId;
	}
	public Integer getEtcTxStatus() {
		return etcTxStatus;
	}
	public void setEtcTxStatus(Integer etcTxStatus) {
		this.etcTxStatus = etcTxStatus;
	}
	public String getSpeedViolFlag() {
		return speedViolFlag;
	}
	public void setSpeedViolFlag(String speedViolFlag) {
		this.speedViolFlag = speedViolFlag;
	}
	public String getImageTaken() {
		return imageTaken;
	}
	public void setImageTaken(String imageTaken) {
		this.imageTaken = imageTaken;
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
	public LocalDate getRevenueDate() {
		return revenueDate;
	}
	public void setRevenueDate(LocalDate revenueDate) {
		this.revenueDate = revenueDate;
	}
	public LocalDate getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(LocalDate postedDate) {
		this.postedDate = postedDate;
	}
	public String getAtpFileId() {
		return atpFileId;
	}
	public void setAtpFileId(String atpFileId) {
		this.atpFileId = atpFileId;
	}
	public LocalDateTime getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}
	public String getIsReversed() {
		return isReversed;
	}
	public void setIsReversed(String isReversed) {
		this.isReversed = isReversed;
	}
	public Integer getCorrReasonId() {
		return corrReasonId;
	}
	public void setCorrReasonId(Integer corrReasonId) {
		this.corrReasonId = corrReasonId;
	}
	public LocalDate getReconDate() {
		return reconDate;
	}
	public void setReconDate(LocalDate reconDate) {
		this.reconDate = reconDate;
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
	public LocalDate getExternFileDate() {
		return externFileDate;
	}
	public void setExternFileDate(LocalDate externFileDate) {
		this.externFileDate = externFileDate;
	}
	public Integer getMileage() {
		return mileage;
	}
	public void setMileage(Integer mileage) {
		this.mileage = mileage;
	}
	public Integer getDeviceReadCount() {
		return deviceReadCount;
	}
	public void setDeviceReadCount(Integer deviceReadCount) {
		this.deviceReadCount = deviceReadCount;
	}
	public Integer getDeviceWriteCount() {
		return deviceWriteCount;
	}
	public void setDeviceWriteCount(Integer deviceWriteCount) {
		this.deviceWriteCount = deviceWriteCount;
	}
	public Integer getEntryDeviceReadCount() {
		return entryDeviceReadCount;
	}
	public void setEntryDeviceReadCount(Integer entryDeviceReadCount) {
		this.entryDeviceReadCount = entryDeviceReadCount;
	}
	public Integer getEntryDeviceWriteCount() {
		return entryDeviceWriteCount;
	}
	public void setEntryDeviceWriteCount(Integer entryDeviceWriteCount) {
		this.entryDeviceWriteCount = entryDeviceWriteCount;
	}
	public String getDepositeId() {
		return depositeId;
	}
	public void setDepositeId(String depositeId) {
		this.depositeId = depositeId;
	}
	public String getCscLookupKey() {
		return cscLookupKey;
	}
	public void setCscLookupKey(String cscLookupKey) {
		this.cscLookupKey = cscLookupKey;
	}
	public String getReciprocityTrx() {
		return reciprocityTrx;
	}
	public void setReciprocityTrx(String reciprocityTrx) {
		this.reciprocityTrx = reciprocityTrx;
	}
	public Double getTollAmount() {
		return tollAmount;
	}
	public void setTollAmount(Double tollAmount) {
		this.tollAmount = tollAmount;
	}
	public Long getDstAtpFileId() {
		return dstAtpFileId;
	}
	public void setDstAtpFileId(Long dstAtpFileId) {
		this.dstAtpFileId = dstAtpFileId;
	}
	public Integer getSourceSystem() {
		return sourceSystem;
	}
	public void setSourceSystem(Integer sourceSystem) {
		this.sourceSystem = sourceSystem;
	}
	public Double getEtcFareAmount() {
		return etcFareAmount;
	}
	public void setEtcFareAmount(Double etcFareAmount) {
		this.etcFareAmount = etcFareAmount;
	}
	public Double getPostedFareAmount() {
		return postedFareAmount;
	}
	public void setPostedFareAmount(Double postedFareAmount) {
		this.postedFareAmount = postedFareAmount;
	}
	public Double getExpectedRevenueAmount() {
		return expectedRevenueAmount;
	}
	public void setExpectedRevenueAmount(Double expectedRevenueAmount) {
		this.expectedRevenueAmount = expectedRevenueAmount;
	}
	public Double getVideoFareAmount() {
		return videoFareAmount;
	}
	public void setVideoFareAmount(Double videoFareAmount) {
		this.videoFareAmount = videoFareAmount;
	}
	public Double getCashFareAmount() {
		return cashFareAmount;
	}
	public void setCashFareAmount(Double cashFareAmount) {
		this.cashFareAmount = cashFareAmount;
	}
	public Integer getTxStatus() {
		return txStatus;
	}
	public void setTxStatus(Integer txStatus) {
		this.txStatus = txStatus;
	}
	@Override
	public String toString() {
		return "AgencyTxPendingDto [laneTxId=" + laneTxId + ", txExternRefNo=" + txExternRefNo + ", txSeqNumber="
				+ txSeqNumber + ", externFileId=" + externFileId + ", txTimestamp=" + txTimestamp + ", txDate=" + txDate
				+ ", txModSeq=" + txModSeq + ", txType=" + txType + ", txSubtype=" + txSubtype + ", tollSystemType="
				+ tollSystemType + ", laneMode=" + laneMode + ", laneType=" + laneType + ", laneState=" + laneState
				+ ", laneHealth=" + laneHealth + ", plazaAgencyId=" + plazaAgencyId + ", plazaId=" + plazaId
				+ ", laneId=" + laneId + ", collectorId=" + collectorId + ", tourSegmentId=" + tourSegmentId
				+ ", entryDataSource=" + entryDataSource + ", entryLaneId=" + entryLaneId + ", entryPlazaId="
				+ entryPlazaId + ", entryTimestamp=" + entryTimestamp + ", entryTxSeqNumber=" + entryTxSeqNumber
				+ ", entryVehicleSpeed=" + entryVehicleSpeed + ", laneTxStatus=" + laneTxStatus + ", lanetxType="
				+ lanetxType + ", tollRevenueType=" + tollRevenueType + ", actualClass=" + actualClass
				+ ", actualAxles=" + actualAxles + ", actualExtraAxles=" + actualExtraAxles + ", collectorClass="
				+ collectorClass + ", collectorAxles=" + collectorAxles + ", preclassClass=" + preclassClass
				+ ", preclassAxles=" + preclassAxles + ", postclassClass=" + postclassClass + ", postclassAxles="
				+ postclassAxles + ", forwardAxles=" + forwardAxles + ", reverseAxles=" + reverseAxles
				+ ", fullFareAmount=" + fullFareAmount + ", discountedAmount=" + discountedAmount + ", collectedAmount="
				+ collectedAmount + ", unrealizedAmount=" + unrealizedAmount + ", isDiscountable=" + isDiscountable
				+ ", isMedianFare=" + isMedianFare + ", isPeak=" + isPeak + ", priceScheduleId=" + priceScheduleId
				+ ", vehicleSpeed=" + vehicleSpeed + ", receiptIssued=" + receiptIssued + ", deviceNo=" + deviceNo
				+ ", accountType=" + accountType + ", deviceCodedClass=" + deviceCodedClass + ", deviceAgencyClass="
				+ deviceAgencyClass + ", deviceIagClass=" + deviceIagClass + ", deviceAxles=" + deviceAxles
				+ ", etcAccountId=" + etcAccountId + ", accountAgencyId=" + accountAgencyId + ", readAviClass="
				+ readAviClass + ", readAviAxles=" + readAviAxles + ", deviceProgramStatus=" + deviceProgramStatus
				+ ", bufferReadFlag=" + bufferReadFlag + ", laneDeviceStatus=" + laneDeviceStatus
				+ ", postDeviceStatus=" + postDeviceStatus + ", preTxnBalance=" + preTxnBalance + ", planTypeId="
				+ planTypeId + ", etcTxStatus=" + etcTxStatus + ", speedViolFlag=" + speedViolFlag + ", imageTaken="
				+ imageTaken + ", plateCountry=" + plateCountry + ", plateState=" + plateState + ", plateNumber="
				+ plateNumber + ", revenueDate=" + revenueDate + ", postedDate=" + postedDate + ", atpFileId="
				+ atpFileId + ", updateTs=" + updateTs + ", isReversed=" + isReversed + ", corrReasonId=" + corrReasonId
				+ ", reconDate=" + reconDate + ", reconStatusInd=" + reconStatusInd + ", reconSubCodeInd="
				+ reconSubCodeInd + ", externFileDate=" + externFileDate + ", mileage=" + mileage + ", deviceReadCount="
				+ deviceReadCount + ", deviceWriteCount=" + deviceWriteCount + ", entryDeviceReadCount="
				+ entryDeviceReadCount + ", entryDeviceWriteCount=" + entryDeviceWriteCount + ", depositeId="
				+ depositeId + ", cscLookupKey=" + cscLookupKey + ", reciprocityTrx=" + reciprocityTrx + ", tollAmount="
				+ tollAmount + ", dstAtpFileId=" + dstAtpFileId + ", sourceSystem=" + sourceSystem + ", etcFareAmount="
				+ etcFareAmount + ", postedFareAmount=" + postedFareAmount + ", expectedRevenueAmount="
				+ expectedRevenueAmount + ", videoFareAmount=" + videoFareAmount + ", cashFareAmount=" + cashFareAmount
				+ ", txStatus=" + txStatus + "]";
	}
	
	
}
