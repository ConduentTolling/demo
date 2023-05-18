package com.conduent.tpms.unmatched.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class ViolTxDto {
	
	private OffsetDateTime txTimestamp;
	private Long laneTxId;
	private String txModSeq;
	private Long eventType;
	private Integer prevEventType;
	private LocalDateTime eventTimestamp;
	private LocalDateTime updateTs;
	private Long violTxStatus;
	private Double fullFareAmount;
	private Integer plazaAgencyId;
	private Long violType;
	private Integer actualClass;
	private Long reviewedClass;
	private Integer actualExtraAxles;
	private Integer entryPlazaId;
	private String plateNumber;
	private String plateState;
	private String dmvPlateType;
	private Integer plazaId;
	private String aetFlag;
	private Integer accountType;
	private String txExternRefNo;
	private Long externFileId;
	private Integer laneId;
	private Long txSeqNumber;
	private Integer laneHealth;
	private Long collectorId;
	private String entryDataSource;
	private Integer entryLaneId;
	private Integer laneTxStatus;
	private Integer lanetxType;
	private Integer actualAxles;
	private Integer collectorClass;
	private Integer collectorAxles;
	private Integer preclassClass;
	private Integer preclassAxles;
	private Integer postclassClass;
	private Integer postclassAxles;
	private Integer forwardAxles;
	private Integer reverseAxles;
	private Double discountedAmount;
	private Double collectedAmount;
	private Double unrealizedAmount;
	private String isDiscountable;
	private String isMedianFare;
	private String isPeak;
	private Integer priceScheduleId;
	private Integer receiptIssued;
	private String deviceNo;
	private Integer deviceAxles;
	private Integer accountAgencyId;
	private Integer readAviAxles;
	private String deviceProgramStatus;
	private String bufferedReadFlag;
	private Integer postDeviceStatus;
	private Integer preTxnBalance;
	private Integer planTypeId;
	private Integer etcTxStatus;
	private String imageTaken;
	private String plateCountry;
	private String revenueDate;
	private Long atpFileId;
	private String isReversed;
	private Integer corrReasonId;
	private Integer reconStatusInd;
	private Integer reconSubCodeInd;
	private Integer mileage;
	private Integer deviceReadCount;
	private Integer deviceWriteCount;
	private Integer entryDeviceReadCount;
	private Integer entryDeviceWriteCount;
	private String depositId;
	private LocalDate txDate;
	private String tollSystemType;
	private Long prevViolTxStatus;
	private Integer laneMode;
	private Integer tollRevenueType;
	private Integer exclusionStage;
	private Long etcAccountId;
	private Integer vehicleSpeed;
	private String txTypeInd;
	private String txSubtypeInd;
	private Integer entryVehicleSpeed;
	private Integer laneState;
	private String speedViolFlag;
	private String accountTypeCd;
	private LocalDateTime currentTimestamp;
	private Integer deviceAgencyClass;
	private OffsetDateTime entryTimestamp;
	private Integer entryTxSeqNumber;
	private Integer event;
	private LocalDateTime externFileDate;
	private String laneDeviceStatus;
	private Integer laneType;
	private Integer deviceCodedClass;
	private Integer deviceIagClass;
	private LocalDateTime postedDate;
	private Integer readAviClass;
	private LocalDateTime reconDate;
	private Integer tourSegmentId;
	private LocalDate cDmvDate;
	private Double noticeTollAmount;
	private Long frontImgRejReason;
	private Long trailerPlate;
	private String isReciprocityTxn;
	private Double expectedRevenueAmount;
	private Double videoFareAmount;
	private Double cashFareAmount;
	private Double etcFareAmount;
	private Double postedFareAmount;
	private Integer txStatus;
	private Long prevTxStatus;
	private Double discountedAmount2;
	public OffsetDateTime getTxTimestamp() {
		return txTimestamp;
	}
	public void setTxTimestamp(OffsetDateTime txTimestamp) {
		this.txTimestamp = txTimestamp;
	}
	public Long getLaneTxId() {
		return laneTxId;
	}
	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
	}
	public String getTxModSeq() {
		return txModSeq;
	}
	public void setTxModSeq(String txModSeq) {
		this.txModSeq = txModSeq;
	}
	public Long getEventType() {
		return eventType;
	}
	public void setEventType(Long eventType) {
		this.eventType = eventType;
	}
	public Integer getPrevEventType() {
		return prevEventType;
	}
	public void setPrevEventType(Integer prevEventType) {
		this.prevEventType = prevEventType;
	}
	public LocalDateTime getEventTimestamp() {
		return eventTimestamp;
	}
	public void setEventTimestamp(LocalDateTime eventTimestamp) {
		this.eventTimestamp = eventTimestamp;
	}
	public LocalDateTime getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}
	public Long getViolTxStatus() {
		return violTxStatus;
	}
	public void setViolTxStatus(Long violTxStatus) {
		this.violTxStatus = violTxStatus;
	}
	public Double getFullFareAmount() {
		return fullFareAmount;
	}
	public void setFullFareAmount(Double fullFareAmount) {
		this.fullFareAmount = fullFareAmount;
	}
	public Integer getPlazaAgencyId() {
		return plazaAgencyId;
	}
	public void setPlazaAgencyId(Integer plazaAgencyId) {
		this.plazaAgencyId = plazaAgencyId;
	}
	public Long getViolType() {
		return violType;
	}
	public void setViolType(Long violType) {
		this.violType = violType;
	}
	public Integer getActualClass() {
		return actualClass;
	}
	public void setActualClass(Integer actualClass) {
		this.actualClass = actualClass;
	}
	public Long getReviewedClass() {
		return reviewedClass;
	}
	public void setReviewedClass(Long reviewedClass) {
		this.reviewedClass = reviewedClass;
	}
	public Integer getActualExtraAxles() {
		return actualExtraAxles;
	}
	public void setActualExtraAxles(Integer actualExtraAxles) {
		this.actualExtraAxles = actualExtraAxles;
	}
	public Integer getEntryPlazaId() {
		return entryPlazaId;
	}
	public void setEntryPlazaId(Integer entryPlazaId) {
		this.entryPlazaId = entryPlazaId;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getPlateState() {
		return plateState;
	}
	public void setPlateState(String plateState) {
		this.plateState = plateState;
	}
	public String getDmvPlateType() {
		return dmvPlateType;
	}
	public void setDmvPlateType(String dmvPlateType) {
		this.dmvPlateType = dmvPlateType;
	}
	public Integer getPlazaId() {
		return plazaId;
	}
	public void setPlazaId(Integer plazaId) {
		this.plazaId = plazaId;
	}
	public String getAetFlag() {
		return aetFlag;
	}
	public void setAetFlag(String aetFlag) {
		this.aetFlag = aetFlag;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public String getTxExternRefNo() {
		return txExternRefNo;
	}
	public void setTxExternRefNo(String txExternRefNo) {
		this.txExternRefNo = txExternRefNo;
	}
	public Long getExternFileId() {
		return externFileId;
	}
	public void setExternFileId(Long externFileId) {
		this.externFileId = externFileId;
	}
	public Integer getLaneId() {
		return laneId;
	}
	public void setLaneId(Integer laneId) {
		this.laneId = laneId;
	}
	public Long getTxSeqNumber() {
		return txSeqNumber;
	}
	public void setTxSeqNumber(Long txSeqNumber) {
		this.txSeqNumber = txSeqNumber;
	}
	public Integer getLaneHealth() {
		return laneHealth;
	}
	public void setLaneHealth(Integer laneHealth) {
		this.laneHealth = laneHealth;
	}
	public Long getCollectorId() {
		return collectorId;
	}
	public void setCollectorId(Long collectorId) {
		this.collectorId = collectorId;
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
	public Integer getActualAxles() {
		return actualAxles;
	}
	public void setActualAxles(Integer actualAxles) {
		this.actualAxles = actualAxles;
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
	public Double getDiscountedAmount() {
		return discountedAmount;
	}
	public void setDiscountedAmount(Double discountedAmount) {
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
	public Integer getDeviceAxles() {
		return deviceAxles;
	}
	public void setDeviceAxles(Integer deviceAxles) {
		this.deviceAxles = deviceAxles;
	}
	public Integer getAccountAgencyId() {
		return accountAgencyId;
	}
	public void setAccountAgencyId(Integer accountAgencyId) {
		this.accountAgencyId = accountAgencyId;
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
	public String getBufferedReadFlag() {
		return bufferedReadFlag;
	}
	public void setBufferedReadFlag(String bufferedReadFlag) {
		this.bufferedReadFlag = bufferedReadFlag;
	}
	public Integer getPostDeviceStatus() {
		return postDeviceStatus;
	}
	public void setPostDeviceStatus(Integer postDeviceStatus) {
		this.postDeviceStatus = postDeviceStatus;
	}
	public Integer getPreTxnBalance() {
		return preTxnBalance;
	}
	public void setPreTxnBalance(Integer preTxnBalance) {
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
	public String getRevenueDate() {
		return revenueDate;
	}
	public void setRevenueDate(String revenueDate) {
		this.revenueDate = revenueDate;
	}
	public Long getAtpFileId() {
		return atpFileId;
	}
	public void setAtpFileId(Long atpFileId) {
		this.atpFileId = atpFileId;
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
	public String getDepositId() {
		return depositId;
	}
	public void setDepositId(String depositId) {
		this.depositId = depositId;
	}
	public LocalDate getTxDate() {
		return txDate;
	}
	public void setTxDate(LocalDate txDate) {
		this.txDate = txDate;
	}
	public String getTollSystemType() {
		return tollSystemType;
	}
	public void setTollSystemType(String tollSystemType) {
		this.tollSystemType = tollSystemType;
	}
	public Long getPrevViolTxStatus() {
		return prevViolTxStatus;
	}
	public void setPrevViolTxStatus(Long prevViolTxStatus) {
		this.prevViolTxStatus = prevViolTxStatus;
	}
	public Integer getLaneMode() {
		return laneMode;
	}
	public void setLaneMode(Integer laneMode) {
		this.laneMode = laneMode;
	}
	public Integer getTollRevenueType() {
		return tollRevenueType;
	}
	public void setTollRevenueType(Integer tollRevenueType) {
		this.tollRevenueType = tollRevenueType;
	}
	public Integer getExclusionStage() {
		return exclusionStage;
	}
	public void setExclusionStage(Integer exclusionStage) {
		this.exclusionStage = exclusionStage;
	}
	public Long getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(Long etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	public Integer getVehicleSpeed() {
		return vehicleSpeed;
	}
	public void setVehicleSpeed(Integer vehicleSpeed) {
		this.vehicleSpeed = vehicleSpeed;
	}
	public String getTxTypeInd() {
		return txTypeInd;
	}
	public void setTxTypeInd(String txTypeInd) {
		this.txTypeInd = txTypeInd;
	}
	public String getTxSubtypeInd() {
		return txSubtypeInd;
	}
	public void setTxSubtypeInd(String txSubtypeInd) {
		this.txSubtypeInd = txSubtypeInd;
	}
	public Integer getEntryVehicleSpeed() {
		return entryVehicleSpeed;
	}
	public void setEntryVehicleSpeed(Integer entryVehicleSpeed) {
		this.entryVehicleSpeed = entryVehicleSpeed;
	}
	public Integer getLaneState() {
		return laneState;
	}
	public void setLaneState(Integer laneState) {
		this.laneState = laneState;
	}
	public String getSpeedViolFlag() {
		return speedViolFlag;
	}
	public void setSpeedViolFlag(String speedViolFlag) {
		this.speedViolFlag = speedViolFlag;
	}
	public String getAccountTypeCd() {
		return accountTypeCd;
	}
	public void setAccountTypeCd(String accountTypeCd) {
		this.accountTypeCd = accountTypeCd;
	}
	public LocalDateTime getCurrentTimestamp() {
		return currentTimestamp;
	}
	public void setCurrentTimestamp(LocalDateTime currentTimestamp) {
		this.currentTimestamp = currentTimestamp;
	}
	public Integer getDeviceAgencyClass() {
		return deviceAgencyClass;
	}
	public void setDeviceAgencyClass(Integer deviceAgencyClass) {
		this.deviceAgencyClass = deviceAgencyClass;
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
	public Integer getEvent() {
		return event;
	}
	public void setEvent(Integer event) {
		this.event = event;
	}
	public LocalDateTime getExternFileDate() {
		return externFileDate;
	}
	public void setExternFileDate(LocalDateTime externFileDate) {
		this.externFileDate = externFileDate;
	}
	public String getLaneDeviceStatus() {
		return laneDeviceStatus;
	}
	public void setLaneDeviceStatus(String laneDeviceStatus) {
		this.laneDeviceStatus = laneDeviceStatus;
	}
	public Integer getLaneType() {
		return laneType;
	}
	public void setLaneType(Integer laneType) {
		this.laneType = laneType;
	}
	public Integer getDeviceCodedClass() {
		return deviceCodedClass;
	}
	public void setDeviceCodedClass(Integer deviceCodedClass) {
		this.deviceCodedClass = deviceCodedClass;
	}
	public Integer getDeviceIagClass() {
		return deviceIagClass;
	}
	public void setDeviceIagClass(Integer deviceIagClass) {
		this.deviceIagClass = deviceIagClass;
	}
	public LocalDateTime getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(LocalDateTime postedDate) {
		this.postedDate = postedDate;
	}
	public Integer getReadAviClass() {
		return readAviClass;
	}
	public void setReadAviClass(Integer readAviClass) {
		this.readAviClass = readAviClass;
	}
	public LocalDateTime getReconDate() {
		return reconDate;
	}
	public void setReconDate(LocalDateTime reconDate) {
		this.reconDate = reconDate;
	}
	public Integer getTourSegmentId() {
		return tourSegmentId;
	}
	public void setTourSegmentId(Integer tourSegmentId) {
		this.tourSegmentId = tourSegmentId;
	}
	public LocalDate getcDmvDate() {
		return cDmvDate;
	}
	public void setcDmvDate(LocalDate cDmvDate) {
		this.cDmvDate = cDmvDate;
	}
	public Double getNoticeTollAmount() {
		return noticeTollAmount;
	}
	public void setNoticeTollAmount(Double noticeTollAmount) {
		this.noticeTollAmount = noticeTollAmount;
	}
	public Long getFrontImgRejReason() {
		return frontImgRejReason;
	}
	public void setFrontImgRejReason(Long frontImgRejReason) {
		this.frontImgRejReason = frontImgRejReason;
	}
	public Long getTrailerPlate() {
		return trailerPlate;
	}
	public void setTrailerPlate(Long trailerPlate) {
		this.trailerPlate = trailerPlate;
	}
	public String getIsReciprocityTxn() {
		return isReciprocityTxn;
	}
	public void setIsReciprocityTxn(String isReciprocityTxn) {
		this.isReciprocityTxn = isReciprocityTxn;
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
	public Integer getTxStatus() {
		return txStatus;
	}
	public void setTxStatus(Integer txStatus) {
		this.txStatus = txStatus;
	}
	public Long getPrevTxStatus() {
		return prevTxStatus;
	}
	public void setPrevTxStatus(Long prevTxStatus) {
		this.prevTxStatus = prevTxStatus;
	}
	public Double getDiscountedAmount2() {
		return discountedAmount2;
	}
	public void setDiscountedAmount2(Double discountedAmount2) {
		this.discountedAmount2 = discountedAmount2;
	}
	@Override
	public String toString() {
		return "ViolTxDto [txTimestamp=" + txTimestamp + ", laneTxId=" + laneTxId + ", txModSeq=" + txModSeq
				+ ", eventType=" + eventType + ", prevEventType=" + prevEventType + ", eventTimestamp=" + eventTimestamp
				+ ", updateTs=" + updateTs + ", violTxStatus=" + violTxStatus + ", fullFareAmount=" + fullFareAmount
				+ ", plazaAgencyId=" + plazaAgencyId + ", violType=" + violType + ", actualClass=" + actualClass
				+ ", reviewedClass=" + reviewedClass + ", actualExtraAxles=" + actualExtraAxles + ", entryPlazaId="
				+ entryPlazaId + ", plateNumber=" + plateNumber + ", plateState=" + plateState + ", dmvPlateType="
				+ dmvPlateType + ", plazaId=" + plazaId + ", aetFlag=" + aetFlag + ", accountType=" + accountType
				+ ", txExternRefNo=" + txExternRefNo + ", externFileId=" + externFileId + ", laneId=" + laneId
				+ ", txSeqNumber=" + txSeqNumber + ", laneHealth=" + laneHealth + ", collectorId=" + collectorId
				+ ", entryDataSource=" + entryDataSource + ", entryLaneId=" + entryLaneId + ", laneTxStatus="
				+ laneTxStatus + ", lanetxType=" + lanetxType + ", actualAxles=" + actualAxles + ", collectorClass="
				+ collectorClass + ", collectorAxles=" + collectorAxles + ", preclassClass=" + preclassClass
				+ ", preclassAxles=" + preclassAxles + ", postclassClass=" + postclassClass + ", postclassAxles="
				+ postclassAxles + ", forwardAxles=" + forwardAxles + ", reverseAxles=" + reverseAxles
				+ ", discountedAmount=" + discountedAmount + ", collectedAmount=" + collectedAmount
				+ ", unrealizedAmount=" + unrealizedAmount + ", isDiscountable=" + isDiscountable + ", isMedianFare="
				+ isMedianFare + ", isPeak=" + isPeak + ", priceScheduleId=" + priceScheduleId + ", receiptIssued="
				+ receiptIssued + ", deviceNo=" + deviceNo + ", deviceAxles=" + deviceAxles + ", accountAgencyId="
				+ accountAgencyId + ", readAviAxles=" + readAviAxles + ", deviceProgramStatus=" + deviceProgramStatus
				+ ", bufferedReadFlag=" + bufferedReadFlag + ", postDeviceStatus=" + postDeviceStatus
				+ ", preTxnBalance=" + preTxnBalance + ", planTypeId=" + planTypeId + ", etcTxStatus=" + etcTxStatus
				+ ", imageTaken=" + imageTaken + ", plateCountry=" + plateCountry + ", revenueDate=" + revenueDate
				+ ", atpFileId=" + atpFileId + ", isReversed=" + isReversed + ", corrReasonId=" + corrReasonId
				+ ", reconStatusInd=" + reconStatusInd + ", reconSubCodeInd=" + reconSubCodeInd + ", mileage=" + mileage
				+ ", deviceReadCount=" + deviceReadCount + ", deviceWriteCount=" + deviceWriteCount
				+ ", entryDeviceReadCount=" + entryDeviceReadCount + ", entryDeviceWriteCount=" + entryDeviceWriteCount
				+ ", depositId=" + depositId + ", txDate=" + txDate + ", tollSystemType=" + tollSystemType
				+ ", prevViolTxStatus=" + prevViolTxStatus + ", laneMode=" + laneMode + ", tollRevenueType="
				+ tollRevenueType + ", exclusionStage=" + exclusionStage + ", etcAccountId=" + etcAccountId
				+ ", vehicleSpeed=" + vehicleSpeed + ", txTypeInd=" + txTypeInd + ", txSubtypeInd=" + txSubtypeInd
				+ ", entryVehicleSpeed=" + entryVehicleSpeed + ", laneState=" + laneState + ", speedViolFlag="
				+ speedViolFlag + ", accountTypeCd=" + accountTypeCd + ", currentTimestamp=" + currentTimestamp
				+ ", deviceAgencyClass=" + deviceAgencyClass + ", entryTimestamp=" + entryTimestamp
				+ ", entryTxSeqNumber=" + entryTxSeqNumber + ", event=" + event + ", externFileDate=" + externFileDate
				+ ", laneDeviceStatus=" + laneDeviceStatus + ", laneType=" + laneType + ", deviceCodedClass="
				+ deviceCodedClass + ", deviceIagClass=" + deviceIagClass + ", postedDate=" + postedDate
				+ ", readAviClass=" + readAviClass + ", reconDate=" + reconDate + ", tourSegmentId=" + tourSegmentId
				+ ", cDmvDate=" + cDmvDate + ", noticeTollAmount=" + noticeTollAmount + ", frontImgRejReason="
				+ frontImgRejReason + ", trailerPlate=" + trailerPlate + ", isReciprocityTxn=" + isReciprocityTxn
				+ ", expectedRevenueAmount=" + expectedRevenueAmount + ", videoFareAmount=" + videoFareAmount
				+ ", cashFareAmount=" + cashFareAmount + ", etcFareAmount=" + etcFareAmount + ", postedFareAmount="
				+ postedFareAmount + ", txStatus=" + txStatus + ", prevTxStatus=" + prevTxStatus
				+ ", discountedAmount2=" + discountedAmount2 + ", getTxTimestamp()=" + getTxTimestamp()
				+ ", getLaneTxId()=" + getLaneTxId() + ", getTxModSeq()=" + getTxModSeq() + ", getEventType()="
				+ getEventType() + ", getPrevEventType()=" + getPrevEventType() + ", getEventTimestamp()="
				+ getEventTimestamp() + ", getUpdateTs()=" + getUpdateTs() + ", getViolTxStatus()=" + getViolTxStatus()
				+ ", getFullFareAmount()=" + getFullFareAmount() + ", getPlazaAgencyId()=" + getPlazaAgencyId()
				+ ", getViolType()=" + getViolType() + ", getActualClass()=" + getActualClass()
				+ ", getReviewedClass()=" + getReviewedClass() + ", getActualExtraAxles()=" + getActualExtraAxles()
				+ ", getEntryPlazaId()=" + getEntryPlazaId() + ", getPlateNumber()=" + getPlateNumber()
				+ ", getPlateState()=" + getPlateState() + ", getDmvPlateType()=" + getDmvPlateType()
				+ ", getPlazaId()=" + getPlazaId() + ", getAetFlag()=" + getAetFlag() + ", getAccountType()="
				+ getAccountType() + ", getTxExternRefNo()=" + getTxExternRefNo() + ", getExternFileId()="
				+ getExternFileId() + ", getLaneId()=" + getLaneId() + ", getTxSeqNumber()=" + getTxSeqNumber()
				+ ", getLaneHealth()=" + getLaneHealth() + ", getCollectorId()=" + getCollectorId()
				+ ", getEntryDataSource()=" + getEntryDataSource() + ", getEntryLaneId()=" + getEntryLaneId()
				+ ", getLaneTxStatus()=" + getLaneTxStatus() + ", getLanetxType()=" + getLanetxType()
				+ ", getActualAxles()=" + getActualAxles() + ", getCollectorClass()=" + getCollectorClass()
				+ ", getCollectorAxles()=" + getCollectorAxles() + ", getPreclassClass()=" + getPreclassClass()
				+ ", getPreclassAxles()=" + getPreclassAxles() + ", getPostclassClass()=" + getPostclassClass()
				+ ", getPostclassAxles()=" + getPostclassAxles() + ", getForwardAxles()=" + getForwardAxles()
				+ ", getReverseAxles()=" + getReverseAxles() + ", getDiscountedAmount()=" + getDiscountedAmount()
				+ ", getCollectedAmount()=" + getCollectedAmount() + ", getUnrealizedAmount()=" + getUnrealizedAmount()
				+ ", getIsDiscountable()=" + getIsDiscountable() + ", getIsMedianFare()=" + getIsMedianFare()
				+ ", getIsPeak()=" + getIsPeak() + ", getPriceScheduleId()=" + getPriceScheduleId()
				+ ", getReceiptIssued()=" + getReceiptIssued() + ", getDeviceNo()=" + getDeviceNo()
				+ ", getDeviceAxles()=" + getDeviceAxles() + ", getAccountAgencyId()=" + getAccountAgencyId()
				+ ", getReadAviAxles()=" + getReadAviAxles() + ", getDeviceProgramStatus()=" + getDeviceProgramStatus()
				+ ", getBufferedReadFlag()=" + getBufferedReadFlag() + ", getPostDeviceStatus()="
				+ getPostDeviceStatus() + ", getPreTxnBalance()=" + getPreTxnBalance() + ", getPlanTypeId()="
				+ getPlanTypeId() + ", getEtcTxStatus()=" + getEtcTxStatus() + ", getImageTaken()=" + getImageTaken()
				+ ", getPlateCountry()=" + getPlateCountry() + ", getRevenueDate()=" + getRevenueDate()
				+ ", getAtpFileId()=" + getAtpFileId() + ", getIsReversed()=" + getIsReversed() + ", getCorrReasonId()="
				+ getCorrReasonId() + ", getReconStatusInd()=" + getReconStatusInd() + ", getReconSubCodeInd()="
				+ getReconSubCodeInd() + ", getMileage()=" + getMileage() + ", getDeviceReadCount()="
				+ getDeviceReadCount() + ", getDeviceWriteCount()=" + getDeviceWriteCount()
				+ ", getEntryDeviceReadCount()=" + getEntryDeviceReadCount() + ", getEntryDeviceWriteCount()="
				+ getEntryDeviceWriteCount() + ", getDepositId()=" + getDepositId() + ", getTxDate()=" + getTxDate()
				+ ", getTollSystemType()=" + getTollSystemType() + ", getPrevViolTxStatus()=" + getPrevViolTxStatus()
				+ ", getLaneMode()=" + getLaneMode() + ", getTollRevenueType()=" + getTollRevenueType()
				+ ", getExclusionStage()=" + getExclusionStage() + ", getEtcAccountId()=" + getEtcAccountId()
				+ ", getVehicleSpeed()=" + getVehicleSpeed() + ", getTxTypeInd()=" + getTxTypeInd()
				+ ", getTxSubtypeInd()=" + getTxSubtypeInd() + ", getEntryVehicleSpeed()=" + getEntryVehicleSpeed()
				+ ", getLaneState()=" + getLaneState() + ", getSpeedViolFlag()=" + getSpeedViolFlag()
				+ ", getAccountTypeCd()=" + getAccountTypeCd() + ", getCurrentTimestamp()=" + getCurrentTimestamp()
				+ ", getDeviceAgencyClass()=" + getDeviceAgencyClass() + ", getEntryTimestamp()=" + getEntryTimestamp()
				+ ", getEntryTxSeqNumber()=" + getEntryTxSeqNumber() + ", getEvent()=" + getEvent()
				+ ", getExternFileDate()=" + getExternFileDate() + ", getLaneDeviceStatus()=" + getLaneDeviceStatus()
				+ ", getLaneType()=" + getLaneType() + ", getDeviceCodedClass()=" + getDeviceCodedClass()
				+ ", getDeviceIagClass()=" + getDeviceIagClass() + ", getPostedDate()=" + getPostedDate()
				+ ", getReadAviClass()=" + getReadAviClass() + ", getReconDate()=" + getReconDate()
				+ ", getTourSegmentId()=" + getTourSegmentId() + ", getcDmvDate()=" + getcDmvDate()
				+ ", getNoticeTollAmount()=" + getNoticeTollAmount() + ", getFrontImgRejReason()="
				+ getFrontImgRejReason() + ", getTrailerPlate()=" + getTrailerPlate() + ", getIsReciprocityTxn()="
				+ getIsReciprocityTxn() + ", getExpectedRevenueAmount()=" + getExpectedRevenueAmount()
				+ ", getVideoFareAmount()=" + getVideoFareAmount() + ", getCashFareAmount()=" + getCashFareAmount()
				+ ", getEtcFareAmount()=" + getEtcFareAmount() + ", getPostedFareAmount()=" + getPostedFareAmount()
				+ ", getTxStatus()=" + getTxStatus() + ", getPrevTxStatus()=" + getPrevTxStatus()
				+ ", getDiscountedAmount2()=" + getDiscountedAmount2() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	
}