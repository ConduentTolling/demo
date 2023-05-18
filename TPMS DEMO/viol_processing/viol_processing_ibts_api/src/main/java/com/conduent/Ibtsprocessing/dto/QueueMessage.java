package com.conduent.Ibtsprocessing.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import com.conduent.Ibtsprocessing.utility.LocalDateAdapter;
import com.conduent.Ibtsprocessing.utility.LocalDateTimeAdapter;
import com.conduent.Ibtsprocessing.utility.OffsetDateTimeConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class QueueMessage {
	private Long laneTxId;
	private String txExternRefNo;
	private long trxLaneSerial;
	private Long txSeqNumber;
	private Long externFileId;
	private Integer laneId;
	private String deviceNo;
	// private String txTimestamp;
	// private ZonedDateTime txTimestamp;
	// private OffsetDateTime txTimeStamp;
	private OffsetDateTime txTimestamp;
	private String txDate;
	private Integer txModSeq;
	// private String txType;
	private String txTypeInd;
	// private String txSubType;
	private String txSubtypeInd;
	private String tollSystemType;
	private Integer laneMode;
	private Integer laneType;
	private Integer laneState;
	private Integer laneHealth;
	private Integer plazaAgencyId;
	private Integer plazaId;
	private Integer collectorNumber;
	private Integer tourSegment;
	private String entryDataSource;
	private Integer entryLaneId;
	private Integer entryPlazaId;
	// private String entryTimestamp;
	// private ZonedDateTime entryTimestamp;
	//private OffsetDateTime entryTxTimeStamp;
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
	private Integer preClassClass;
	private Integer preClassAxles;
	private Integer postClassClass;
	private Integer postClassAxles;
	private Integer forwardAxles;
	private Integer reverseAxles;
	private Double fullFareAmount;
	private Double discountedAmount;
	private Double discountedAmount2;
	private Integer collectedAmount;
	private Double unrealizedAmount;
	private String isDiscountable;
	private String isMedianFare;
	private String isPeak;
	private Integer priceScheduleId;
	private Integer vehicleSpeed;
	private Integer receiptIssued;
	private Integer accountType;
	private Integer deviceCodedClass;
	private Integer deviceAgencyClass;
	private Integer deviceIagClass;
	private Integer deviceAxles;
	private Long etcAccountId;
	private Integer accountAgencyId;
	private Integer readAviClass;
	private Integer readAviAxles;
	private String progStat;
	// private String buffRead;
	private Integer etcValidStatus;
	private Integer postDeviceStatus;
	private Integer preTxnBalance;
	private Integer planTypeId;
	// private Integer etcTxStatus;
	private Integer txStatus;
	private String speedViolFlag;
	private String imageTaken;
	private String plateCountry;
	private String plateState;
	private String plateNumber;
	private String revenueDate;
	private String postedDate;
	private Integer atpFileId;
	private String updateTs;
	private String laneDataSource;
	private Integer corrReasonId;
	private String reconDate;
	private String externFileDate;
	private Integer mileage;
	private Integer internalAuditId;
	private Integer modifiedStatus;
	private Integer exceptionStatus;
	private String depositId;
	private String receivedDate;
	private Integer violType;
	private Integer reviewedVehicleType;
	private Integer reviewedClass;
	private Integer imageRvwClerkId;
	private Long imageBatchId;
	private Long imageBatchSeqNumber;
	private Integer imageIndex;
	private Long adjustedAmount;
	private Long noticeTollAmount;
	private Integer outputFileId;
	private String outputFileType;
	private String reciprocityTrx;
	private Integer makeId;
	private String eventTimestamp;
	//private LocalDateTime eventTimestamp;
	private String isLprEnabled;
	private Integer eventType;
	private Integer prevEventType;
	private Integer prevViolTxStatus;
	private Integer violTxStatus;
	private Integer DmvPlateType;
	private String isReversed;
	private Integer reconStatusInd;
	private Integer reconSubCodeInd;
	private Integer deviceReadCount;
	private Integer deviceWriteCount;
	private Integer entryDeviceReadCount;
	private Integer entryDeviceWriteCount;
	private String cscLookupKey;
	private String deviceProgramStatus;
	private String bufferReadFlag;
	private Integer laneDeviceStatus;
	private String tillSystemType;
	private String aetFlag;
	private Double tollAmount;
	private Double etcFareAmount;
	private Double postedFareAmount;
	private Double expectedRevenueAmount;
	private Double videoFareAmount;
	private Double cashFareAmount;
	private Double itolFareAmount;//additional field
	private String hovFlag;

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

	public long getTrxLaneSerial() {
		return trxLaneSerial;
	}

	public void setTrxLaneSerial(long trxLaneSerial) {
		this.trxLaneSerial = trxLaneSerial;
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

	public Integer getLaneId() {
		return laneId;
	}

	public void setLaneId(Integer laneId) {
		this.laneId = laneId;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public OffsetDateTime getTxTimestamp() {
		return txTimestamp;
	}

	public void setTxTimestamp(OffsetDateTime txTimestamp) {
		this.txTimestamp = txTimestamp;
	}

	public String getTxDate() {
		return txDate;
	}

	public void setTxDate(String txDate) {
		this.txDate = txDate;
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

	public String getTxSubtypeInd() {
		return txSubtypeInd;
	}

	public void setTxSubtypeInd(String txSubtypeInd) {
		this.txSubtypeInd = txSubtypeInd;
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

	public Integer getCollectorNumber() {
		return collectorNumber;
	}

	public void setCollectorNumber(Integer collectorNumber) {
		this.collectorNumber = collectorNumber;
	}

	public Integer getTourSegment() {
		return tourSegment;
	}

	public void setTourSegment(Integer tourSegment) {
		this.tourSegment = tourSegment;
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

	public Integer getPreClassClass() {
		return preClassClass;
	}

	public void setPreClassClass(Integer preClassClass) {
		this.preClassClass = preClassClass;
	}

	public Integer getPreClassAxles() {
		return preClassAxles;
	}

	public void setPreClassAxles(Integer preClassAxles) {
		this.preClassAxles = preClassAxles;
	}

	public Integer getPostClassClass() {
		return postClassClass;
	}

	public void setPostClassClass(Integer postClassClass) {
		this.postClassClass = postClassClass;
	}

	public Integer getPostClassAxles() {
		return postClassAxles;
	}

	public void setPostClassAxles(Integer postClassAxles) {
		this.postClassAxles = postClassAxles;
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

	public Double getFullFareAmount() {
		return fullFareAmount;
	}

	public void setFullFareAmount(Double fullFareAmount) {
		this.fullFareAmount = fullFareAmount;
	}

	public Double getDiscountedAmount() {
		return discountedAmount;
	}

	public void setDiscountedAmount(Double discountedAmount) {
		this.discountedAmount = discountedAmount;
	}

	public Double getDiscountedAmount2() {
		return discountedAmount2;
	}

	public void setDiscountedAmount2(Double discountedAmount2) {
		this.discountedAmount2 = discountedAmount2;
	}

	public Integer getCollectedAmount() {
		return collectedAmount;
	}

	public void setCollectedAmount(Integer collectedAmount) {
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

	public String getProgStat() {
		return progStat;
	}

	public void setProgStat(String progStat) {
		this.progStat = progStat;
	}

	public Integer getEtcValidStatus() {
		return etcValidStatus;
	}

	public void setEtcValidStatus(Integer etcValidStatus) {
		this.etcValidStatus = etcValidStatus;
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

	public Integer getTxStatus() {
		return txStatus;
	}

	public void setTxStatus(Integer txStatus) {
		this.txStatus = txStatus;
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

	public String getRevenueDate() {
		return revenueDate;
	}

	public void setRevenueDate(String revenueDate) {
		this.revenueDate = revenueDate;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(String postedDate) {
		this.postedDate = postedDate;
	}

	public Integer getAtpFileId() {
		return atpFileId;
	}

	public void setAtpFileId(Integer atpFileId) {
		this.atpFileId = atpFileId;
	}

	public String getUpdateTs() {
		return updateTs;
	}

	public void setUpdateTs(String updateTs) {
		this.updateTs = updateTs;
	}

	public String getLaneDataSource() {
		return laneDataSource;
	}

	public void setLaneDataSource(String laneDataSource) {
		this.laneDataSource = laneDataSource;
	}

	public Integer getCorrReasonId() {
		return corrReasonId;
	}

	public void setCorrReasonId(Integer corrReasonId) {
		this.corrReasonId = corrReasonId;
	}

	public String getReconDate() {
		return reconDate;
	}

	public void setReconDate(String reconDate) {
		this.reconDate = reconDate;
	}

	public String getExternFileDate() {
		return externFileDate;
	}

	public void setExternFileDate(String externFileDate) {
		this.externFileDate = externFileDate;
	}

	public Integer getMileage() {
		return mileage;
	}

	public void setMileage(Integer mileage) {
		this.mileage = mileage;
	}

	public Integer getInternalAuditId() {
		return internalAuditId;
	}

	public void setInternalAuditId(Integer internalAuditId) {
		this.internalAuditId = internalAuditId;
	}

	public Integer getModifiedStatus() {
		return modifiedStatus;
	}

	public void setModifiedStatus(Integer modifiedStatus) {
		this.modifiedStatus = modifiedStatus;
	}

	public Integer getExceptionStatus() {
		return exceptionStatus;
	}

	public void setExceptionStatus(Integer exceptionStatus) {
		this.exceptionStatus = exceptionStatus;
	}

	public String getDepositId() {
		return depositId;
	}

	public void setDepositId(String depositId) {
		this.depositId = depositId;
	}

	public String getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Integer getViolType() {
		return violType;
	}

	public void setViolType(Integer violType) {
		this.violType = violType;
	}

	public Integer getReviewedVehicleType() {
		return reviewedVehicleType;
	}

	public void setReviewedVehicleType(Integer reviewedVehicleType) {
		this.reviewedVehicleType = reviewedVehicleType;
	}

	public Integer getReviewedClass() {
		return reviewedClass;
	}

	public void setReviewedClass(Integer reviewedClass) {
		this.reviewedClass = reviewedClass;
	}

	public Integer getImageRvwClerkId() {
		return imageRvwClerkId;
	}

	public void setImageRvwClerkId(Integer imageRvwClerkId) {
		this.imageRvwClerkId = imageRvwClerkId;
	}

	public Long getImageBatchId() {
		return imageBatchId;
	}

	public void setImageBatchId(Long imageBatchId) {
		this.imageBatchId = imageBatchId;
	}

	public Long getImageBatchSeqNumber() {
		return imageBatchSeqNumber;
	}

	public void setImageBatchSeqNumber(Long imageBatchSeqNumber) {
		this.imageBatchSeqNumber = imageBatchSeqNumber;
	}

	public Integer getImageIndex() {
		return imageIndex;
	}

	public void setImageIndex(Integer imageIndex) {
		this.imageIndex = imageIndex;
	}

	public Long getAdjustedAmount() {
		return adjustedAmount;
	}

	public void setAdjustedAmount(Long adjustedAmount) {
		this.adjustedAmount = adjustedAmount;
	}

	public Long getNoticeTollAmount() {
		return noticeTollAmount;
	}

	public void setNoticeTollAmount(Long noticeTollAmount) {
		this.noticeTollAmount = noticeTollAmount;
	}

	public Integer getOutputFileId() {
		return outputFileId;
	}

	public void setOutputFileId(Integer outputFileId) {
		this.outputFileId = outputFileId;
	}

	public String getOutputFileType() {
		return outputFileType;
	}

	public void setOutputFileType(String outputFileType) {
		this.outputFileType = outputFileType;
	}

	public String getReciprocityTrx() {
		return reciprocityTrx;
	}

	public void setReciprocityTrx(String reciprocityTrx) {
		this.reciprocityTrx = reciprocityTrx;
	}

	public Integer getMakeId() {
		return makeId;
	}

	public void setMakeId(Integer makeId) {
		this.makeId = makeId;
	}

	public String getEventTimestamp() {
		return eventTimestamp;
	}

	public void setEventTimestamp(String eventTimestamp) {
		this.eventTimestamp = eventTimestamp;
	}

	public String getIsLprEnabled() {
		return isLprEnabled;
	}

	public void setIsLprEnabled(String isLprEnabled) {
		this.isLprEnabled = isLprEnabled;
	}

	public Integer getEventType() {
		return eventType;
	}

	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}

	public Integer getPrevEventType() {
		return prevEventType;
	}

	public void setPrevEventType(Integer prevEventType) {
		this.prevEventType = prevEventType;
	}

	public Integer getPrevViolTxStatus() {
		return prevViolTxStatus;
	}

	public void setPrevViolTxStatus(Integer prevViolTxStatus) {
		this.prevViolTxStatus = prevViolTxStatus;
	}

	public Integer getViolTxStatus() {
		return violTxStatus;
	}

	public void setViolTxStatus(Integer violTxStatus) {
		this.violTxStatus = violTxStatus;
	}

	public Integer getDmvPlateType() {
		return DmvPlateType;
	}

	public void setDmvPlateType(Integer dmvPlateType) {
		DmvPlateType = dmvPlateType;
	}

	public String getIsReversed() {
		return isReversed;
	}

	public void setIsReversed(String isReversed) {
		this.isReversed = isReversed;
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

	public String getCscLookupKey() {
		return cscLookupKey;
	}

	public void setCscLookupKey(String cscLookupKey) {
		this.cscLookupKey = cscLookupKey;
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

	public Integer getLaneDeviceStatus() {
		return laneDeviceStatus;
	}

	public void setLaneDeviceStatus(Integer laneDeviceStatus) {
		this.laneDeviceStatus = laneDeviceStatus;
	}

	public String getTillSystemType() {
		return tillSystemType;
	}

	public void setTillSystemType(String tillSystemType) {
		this.tillSystemType = tillSystemType;
	}

	public String getAetFlag() {
		return aetFlag;
	}

	public void setAetFlag(String aetFlag) {
		this.aetFlag = aetFlag;
	}

	public Double getTollAmount() {
		return tollAmount;
	}

	public void setTollAmount(Double tollAmount) {
		this.tollAmount = tollAmount;
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
	
	public Double getItolFareAmount() {
		return itolFareAmount;
	}

	public void setItolFareAmount(Double itolFareAmount) {
		this.itolFareAmount = itolFareAmount;
	}
	public String getHovFlag() {
		return hovFlag;
	}

	public void setHovFlag(String hovFlag) {
		this.hovFlag = hovFlag;
	}
	

	@Override
	public String toString() {
		return "QueueMessage [laneTxId=" + laneTxId + ", txExternRefNo=" + txExternRefNo + ", trxLaneSerial="
				+ trxLaneSerial + ", txSeqNumber=" + txSeqNumber + ", externFileId=" + externFileId + ", laneId="
				+ laneId + ", deviceNo=" + deviceNo + ", txTimestamp=" + txTimestamp + ", txDate=" + txDate
				+ ", txModSeq=" + txModSeq + ", txTypeInd=" + txTypeInd + ", txSubtypeInd=" + txSubtypeInd
				+ ", tollSystemType=" + tollSystemType + ", laneMode=" + laneMode + ", laneType=" + laneType
				+ ", laneState=" + laneState + ", laneHealth=" + laneHealth + ", plazaAgencyId=" + plazaAgencyId
				+ ", plazaId=" + plazaId + ", collectorNumber=" + collectorNumber + ", tourSegment=" + tourSegment
				+ ", entryDataSource=" + entryDataSource + ", entryLaneId=" + entryLaneId + ", entryPlazaId="
				+ entryPlazaId + ", entryTimestamp=" + entryTimestamp + ", entryTxSeqNumber=" + entryTxSeqNumber
				+ ", entryVehicleSpeed=" + entryVehicleSpeed + ", laneTxStatus=" + laneTxStatus + ", lanetxType="
				+ lanetxType + ", tollRevenueType=" + tollRevenueType + ", actualClass=" + actualClass
				+ ", actualAxles=" + actualAxles + ", actualExtraAxles=" + actualExtraAxles + ", collectorClass="
				+ collectorClass + ", collectorAxles=" + collectorAxles + ", preClassClass=" + preClassClass
				+ ", preClassAxles=" + preClassAxles + ", postClassClass=" + postClassClass + ", postClassAxles="
				+ postClassAxles + ", forwardAxles=" + forwardAxles + ", reverseAxles=" + reverseAxles
				+ ", fullFareAmount=" + fullFareAmount + ", discountedAmount=" + discountedAmount
				+ ", discountedAmount2=" + discountedAmount2 + ", collectedAmount=" + collectedAmount
				+ ", unrealizedAmount=" + unrealizedAmount + ", isDiscountable=" + isDiscountable + ", isMedianFare="
				+ isMedianFare + ", isPeak=" + isPeak + ", priceScheduleId=" + priceScheduleId + ", vehicleSpeed="
				+ vehicleSpeed + ", receiptIssued=" + receiptIssued + ", accountType=" + accountType
				+ ", deviceCodedClass=" + deviceCodedClass + ", deviceAgencyClass=" + deviceAgencyClass
				+ ", deviceIagClass=" + deviceIagClass + ", deviceAxles=" + deviceAxles + ", etcAccountId="
				+ etcAccountId + ", accountAgencyId=" + accountAgencyId + ", readAviClass=" + readAviClass
				+ ", readAviAxles=" + readAviAxles + ", progStat=" + progStat + ", etcValidStatus=" + etcValidStatus
				+ ", postDeviceStatus=" + postDeviceStatus + ", preTxnBalance=" + preTxnBalance + ", planTypeId="
				+ planTypeId + ", txStatus=" + txStatus + ", speedViolFlag=" + speedViolFlag + ", imageTaken="
				+ imageTaken + ", plateCountry=" + plateCountry + ", plateState=" + plateState + ", plateNumber="
				+ plateNumber + ", revenueDate=" + revenueDate + ", postedDate=" + postedDate + ", atpFileId="
				+ atpFileId + ", updateTs=" + updateTs + ", laneDataSource=" + laneDataSource + ", corrReasonId="
				+ corrReasonId + ", reconDate=" + reconDate + ", externFileDate=" + externFileDate + ", mileage="
				+ mileage + ", internalAuditId=" + internalAuditId + ", modifiedStatus=" + modifiedStatus
				+ ", exceptionStatus=" + exceptionStatus + ", depositId=" + depositId + ", receivedDate=" + receivedDate
				+ ", violType=" + violType + ", reviewedVehicleType=" + reviewedVehicleType + ", reviewedClass="
				+ reviewedClass + ", imageRvwClerkId=" + imageRvwClerkId + ", imageBatchId=" + imageBatchId
				+ ", imageBatchSeqNumber=" + imageBatchSeqNumber + ", imageIndex=" + imageIndex + ", adjustedAmount="
				+ adjustedAmount + ", noticeTollAmount=" + noticeTollAmount + ", outputFileId=" + outputFileId
				+ ", outputFileType=" + outputFileType + ", reciprocityTrx=" + reciprocityTrx + ", makeId=" + makeId
				+ ", eventTimestamp=" + eventTimestamp + ", isLprEnabled=" + isLprEnabled + ", eventType=" + eventType
				+ ", prevEventType=" + prevEventType + ", prevViolTxStatus=" + prevViolTxStatus + ", violTxStatus="
				+ violTxStatus + ", DmvPlateType=" + DmvPlateType + ", isReversed=" + isReversed + ", reconStatusInd="
				+ reconStatusInd + ", reconSubCodeInd=" + reconSubCodeInd + ", deviceReadCount=" + deviceReadCount
				+ ", deviceWriteCount=" + deviceWriteCount + ", entryDeviceReadCount=" + entryDeviceReadCount
				+ ", entryDeviceWriteCount=" + entryDeviceWriteCount + ", cscLookupKey=" + cscLookupKey
				+ ", deviceProgramStatus=" + deviceProgramStatus + ", bufferReadFlag=" + bufferReadFlag
				+ ", laneDeviceStatus=" + laneDeviceStatus + ", tillSystemType=" + tillSystemType + ", aetFlag="
				+ aetFlag + ", tollAmount=" + tollAmount + ", etcFareAmount=" + etcFareAmount + ", postedFareAmount="
				+ postedFareAmount + ", expectedRevenueAmount=" + expectedRevenueAmount + ", videoFareAmount="
				+ videoFareAmount + ", cashFareAmount=" + cashFareAmount + ", itolFareAmount=" + itolFareAmount
				+ ", hovFlag=" + hovFlag + "]";
	}

	public static QueueMessage dtoFromJson(String message) {
		QueueMessage queueMessageDTO = null;
		// gson = new Gson();
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.registerTypeAdapter(OffsetDateTime.class,
						new OffsetDateTimeConverter(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
		queueMessageDTO = gson.fromJson(message, QueueMessage.class);
		return queueMessageDTO;
	}

	
}