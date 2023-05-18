package com.conduent.Ibtsprocessing.dto;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.Expose;

public class IbtsViolProcessDTO {

	@Expose(serialize = true, deserialize = true)
	private Long laneTxId;
	@Expose(serialize = true, deserialize = true)
	private String txExternRefNo;
	@Expose(serialize = true, deserialize = true)
	private long trxLaneSerial;
	@Expose(serialize = true, deserialize = true)
	private Long txSeqNumber;
	@Expose(serialize = true, deserialize = true)
	private Long externFileId;
	@Expose(serialize = true, deserialize = true)
	private Integer laneId;
	@Expose(serialize = true, deserialize = true)
	private String deviceNo;
	@Expose(serialize = true, deserialize = true)
	//private Timestamp txTimestamp;
	//private ZonedDateTime txTimestamp;
	private OffsetDateTime txTimeStamp;
	@Expose(serialize = true, deserialize = true)
	private String txDate;
	@Expose(serialize = true, deserialize = true)
	private Integer txModSeq;
	@Expose(serialize = true, deserialize = true)
	private String txType;
	@Expose(serialize = true, deserialize = true)
	private String txSubtype;
	@Expose(serialize = true, deserialize = true)
	private String tollSystemType;
	@Expose(serialize = true, deserialize = true)
	private Integer laneMode;
	@Expose(serialize = true, deserialize = true)
	private Integer laneType;
	@Expose(serialize = true, deserialize = true)
	private Integer laneState;
	@Expose(serialize = true, deserialize = true)
	private Integer laneHealth;
	@Expose(serialize = true, deserialize = true)
	private Integer plazaAgencyId;
	@Expose(serialize = true, deserialize = true)
	private Integer plazaId;
	@Expose(serialize = true, deserialize = true)
	private Integer collectorNumber;
	@Expose(serialize = true, deserialize = true)
	private Integer tourSegment;
	@Expose(serialize = true, deserialize = true)
	private String entryDataSource;
	@Expose(serialize = true, deserialize = true)
	private Integer entryLaneId;
	@Expose(serialize = true, deserialize = true)
	private Integer entryPlazaId;
	@Expose(serialize = true, deserialize = true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	//private String entryTimeStamp;
	//private ZonedDateTime entryTimeStamp;
	//private OffsetDateTime entryTxTimeStamp;
	private OffsetDateTime entryTimestamp;
	@Expose(serialize = true, deserialize = true)
	private Integer entryTxSeqNumber;
	@Expose(serialize = true, deserialize = true)
	private Integer entryVehicleSpeed;
	@Expose(serialize = true, deserialize = true)
	private Integer laneTxStatus;
	@Expose(serialize = true, deserialize = true)
	private Integer lanetxType;
	@Expose(serialize = true, deserialize = true)
	private Integer tollRevenueType;
	@Expose(serialize = true, deserialize = true)
	private Integer actualClass;
	@Expose(serialize = true, deserialize = true)
	private Integer actualAxles;
	@Expose(serialize = true, deserialize = true)
	private Integer actualExtraAxles;
	@Expose(serialize = true, deserialize = true)
	private Integer collectorClass;
	@Expose(serialize = true, deserialize = true)
	private Integer collectorAxles;
	@Expose(serialize = true, deserialize = true)
	private Integer preClassClass;
	@Expose(serialize = true, deserialize = true)
	private Integer preClassAxles;
	@Expose(serialize = true, deserialize = true)
	private Integer postClassClass;
	@Expose(serialize = true, deserialize = true)
	private Integer postClassAxles;
	@Expose(serialize = true, deserialize = true)
	private Integer forwardAxles;
	@Expose(serialize = true, deserialize = true)
	private Integer reverseAxles;
	@Expose(serialize = true, deserialize = true)
	private Double fullFareAmount;
	@Expose(serialize = true, deserialize = true)
	private Double discountedAmount;
	@Expose(serialize = true, deserialize = true)
	private Double discountedAmount2;
	@Expose(serialize = true, deserialize = true)
	private Double collectedAmount;
	@Expose(serialize = true, deserialize = true)
	private Double unrealizedAmount;
	@Expose(serialize = true, deserialize = true)
	private String isDiscountable;
	@Expose(serialize = true, deserialize = true)
	private String isMedianFare;
	@Expose(serialize = true, deserialize = true)
	private String isPeak;
	@Expose(serialize = true, deserialize = true)
	private Integer priceScheduleId;
	@Expose(serialize = true, deserialize = true)
	private Integer vehicleSpeed;
	@Expose(serialize = true, deserialize = true)
	private Integer receiptIssued;
	@Expose(serialize = true, deserialize = true)
	private Integer accountType;
	@Expose(serialize = true, deserialize = true)
	private Integer deviceCodedClass;
	@Expose(serialize = true, deserialize = true)
	private Integer deviceAgencyClass;
	@Expose(serialize = true, deserialize = true)
	private Integer deviceIagClass;
	@Expose(serialize = true, deserialize = true)
	private Integer deviceAxles;
	@Expose(serialize = true, deserialize = true)
	private Long etcAccountId;
	@Expose(serialize = true, deserialize = true)
	private Integer accountAgencyId;
	@Expose(serialize = true, deserialize = true)
	private Integer readAviClass;
	@Expose(serialize = true, deserialize = true)
	private Integer readAviAxles;
	@Expose(serialize = true, deserialize = true)
	private String progStat;
	@Expose(serialize = true, deserialize = true)
	private String buffRead;
	@Expose(serialize = true, deserialize = true)
	private Integer etcValidStatus;
	@Expose(serialize = true, deserialize = true)
	private Integer postDeviceStatus;
	@Expose(serialize = true, deserialize = true)
	private Integer preTxnBalance;
	@Expose(serialize = true, deserialize = true)
	private Integer planTypeId;
	@Expose(serialize = true, deserialize = true)
	//private Integer etcTxStatus;
	private Integer txStatus;
	@Expose(serialize = true, deserialize = true)
	private String speedViolFlag;
	@Expose(serialize = true, deserialize = true)
	private String imageTaken;
	@Expose(serialize = true, deserialize = true)
	private String plateCountry;
	@Expose(serialize = true, deserialize = true)
	private String plateState;
	@Expose(serialize = true, deserialize = true)
	private String plateNumber;
	@Expose(serialize = true, deserialize = true)
	private String revenueDate;
	@Expose(serialize = true, deserialize = true)
	private String postedDate;
	@Expose(serialize = true, deserialize = true)
	private Integer atpFileId;
	@Expose(serialize = true, deserialize = true)
	private String updateTs;
	@Expose(serialize = true, deserialize = true)
	private String laneDataSource;
	@Expose(serialize = true, deserialize = true)
	private Integer corrReasonId;
	@Expose(serialize = true, deserialize = true)
	private String reconDate;
	@Expose(serialize = true, deserialize = true)
	private String externFileDate;
	@Expose(serialize = true, deserialize = true)
	private Integer mileage;
	@Expose(serialize = true, deserialize = true)
	private Integer internalAuditId;
	@Expose(serialize = true, deserialize = true)
	private Integer modifiedStatus;
	@Expose(serialize = true, deserialize = true)
	private Integer exceptionStatus;
	@Expose(serialize = true, deserialize = true)
	private String depositId;
	@Expose(serialize = true, deserialize = true)
	private String receivedDate;
	@Expose(serialize = true, deserialize = true)
	private Integer violType;
	@Expose(serialize = true, deserialize = true)
	private Integer reviewedVehicleType;
	@Expose(serialize = true, deserialize = true)
	private Integer reviewedClass;
	@Expose(serialize = true, deserialize = true)
	private Integer imageRvwClerkId;
	@Expose(serialize = true, deserialize = true)
	private Long imageBatchId;
	@Expose(serialize = true, deserialize = true)
	private Long imageBatchSeqNumber;
	@Expose(serialize = true, deserialize = true)
	private Integer imageIndex;
	@Expose(serialize = true, deserialize = true)
	private Long adjustedAmount;
	@Expose(serialize = true, deserialize = true)
	private Long noticeTollAmount;
	@Expose(serialize = true, deserialize = true)
	private Integer outputFileId;
	@Expose(serialize = true, deserialize = true)
	private String outputFileType;
	@Expose(serialize = true, deserialize = true)
	private String reciprocityTrx;
	@Expose(serialize = true, deserialize = true)
	private Integer makeId;
	@Expose(serialize = true, deserialize = true)
	private String eventTimestamp;
	@Expose(serialize = true, deserialize = true)
	private String isLprEnabled;
	@Expose(serialize = true, deserialize = true)
	private Integer eventType;
	@Expose(serialize = true, deserialize = true)
	private Integer preeventType;
	@Expose(serialize = true, deserialize = true)
	private Integer prevViolTxStatus;
	@Expose(serialize = true, deserialize = true)
	private Integer violTxStatus;
	@Expose(serialize = true, deserialize = true)
	private Integer DmvPlateType;
	@Expose(serialize = true, deserialize = true)
	private String isReversed;
	@Expose(serialize = true, deserialize = true)
	private Integer reconStatusInd;
	@Expose(serialize = true, deserialize = true)
	private Integer reconSubCodeInd;
	@Expose(serialize = true, deserialize = true)
	private Integer deviceReadCount;
	@Expose(serialize = true, deserialize = true)
	private Integer deviceWriteCount;
	@Expose(serialize = true, deserialize = true)
	private Integer entryDeviceReadCount;
	@Expose(serialize = true, deserialize = true)
	private Integer entryDeviceWriteCount;
	@Expose(serialize = true, deserialize = true)
	private String cscLookupKey;
	@Expose(serialize = true, deserialize = true)
	private String deviceProgramStatus;
	@Expose(serialize = true, deserialize = true)
	private String bufferReadFlag;
	@Expose(serialize = true, deserialize = true)
	private Integer laneDeviceStatus;
	@Expose(serialize = true, deserialize = true)
	private String tillSystemType;
	@Expose(serialize = true, deserialize = true)
	private String aetFlag = "Y";
	@Expose(serialize = true, deserialize = true)
	private Double tollAmount;
	
	@Expose(serialize = true, deserialize = true)
	private Double itolFareAmount;
	
	
	public Double getItolFareAmount() {
		return itolFareAmount;
	}
	public void setItolFareAmount(Double itolFareAmount) {
		this.itolFareAmount = itolFareAmount;
	}
	
	public Double getTollAmount() {
		return tollAmount;
	}

	public void setTollAmount(Double tollAmount) {
		this.tollAmount = tollAmount;
	}

	public IbtsViolProcessDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getLaneTxId() {
		return laneTxId;
	}

	public String getTxExternRefNo() {
		return txExternRefNo;
	}

	public long getTrxLaneSerial() {
		return trxLaneSerial;
	}

	public Long getTxSeqNumber() {
		return txSeqNumber;
	}

	public Long getExternFileId() {
		return externFileId;
	}

	public Integer getLaneId() {
		return laneId;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public OffsetDateTime getTxTimeStamp() {
		return txTimeStamp;
	}

	public String getTxDate() {
		return txDate;
	}

	public Integer getTxModSeq() {
		return txModSeq;
	}

	public String getTxType() {
		return txType;
	}

	public String getTxSubtype() {
		return txSubtype;
	}

	public String getTollSystemType() {
		return tollSystemType;
	}

	public Integer getLaneMode() {
		return laneMode;
	}

	public Integer getLaneType() {
		return laneType;
	}

	public Integer getLaneState() {
		return laneState;
	}

	public Integer getLaneHealth() {
		return laneHealth;
	}

	public Integer getPlazaAgencyId() {
		return plazaAgencyId;
	}

	public Integer getPlazaId() {
		return plazaId;
	}

	public Integer getcollectorNumber() {
		return collectorNumber;
	}

	public Integer getTourSegment() {
		return tourSegment;
	}

	public String getEntryDataSource() {
		return entryDataSource;
	}

	public Integer getEntryLaneId() {
		return entryLaneId;
	}

	public Integer getEntryPlazaId() {
		return entryPlazaId;
	}

	public Integer getEntryTxSeqNumber() {
		return entryTxSeqNumber;
	}

	public Integer getEntryVehicleSpeed() {
		return entryVehicleSpeed;
	}

	public Integer getLaneTxStatus() {
		return laneTxStatus;
	}

	public Integer getLanetxType() {
		return lanetxType;
	}

	public Integer getTollRevenueType() {
		return tollRevenueType;
	}

	public Integer getActualClass() {
		return actualClass;
	}

	public Integer getActualAxles() {
		return actualAxles;
	}

	public Integer getActualExtraAxles() {
		return actualExtraAxles;
	}

	public Integer getCollectorClass() {
		return collectorClass;
	}

	public Integer getCollectorAxles() {
		return collectorAxles;
	}

	public Integer getPreClassClass() {
		return preClassClass;
	}

	public Integer getPreClassAxles() {
		return preClassAxles;
	}

	public Integer getPostClassClass() {
		return postClassClass;
	}

	public Integer getPostClassAxles() {
		return postClassAxles;
	}

	public Integer getForwardAxles() {
		return forwardAxles;
	}

	public Integer getReverseAxles() {
		return reverseAxles;
	}

	public Double getFullFareAmount() {
		return fullFareAmount;
	}

	public Double getDiscountedAmount() {
		return discountedAmount;
	}

	public Double getCollectedAmount() {
		return collectedAmount;
	}

	public Double getUnrealizedAmount() {
		return unrealizedAmount;
	}

	public String getIsDiscountable() {
		return isDiscountable;
	}

	public String getIsMedianFare() {
		return isMedianFare;
	}

	public String getIsPeak() {
		return isPeak;
	}

	public Integer getPriceScheduleId() {
		return priceScheduleId;
	}

	public Integer getVehicleSpeed() {
		return vehicleSpeed;
	}

	public Integer getReceiptIssued() {
		return receiptIssued;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public Integer getDeviceCodedClass() {
		return deviceCodedClass;
	}

	public Integer getDeviceAgencyClass() {
		return deviceAgencyClass;
	}

	public Integer getDeviceIagClass() {
		return deviceIagClass;
	}

	public Integer getDeviceAxles() {
		return deviceAxles;
	}

	public Long getEtcAccountId() {
		return etcAccountId;
	}

	public Integer getAccountAgencyId() {
		return accountAgencyId;
	}

	public Integer getReadAviClass() {
		return readAviClass;
	}

	public Integer getReadAviAxles() {
		return readAviAxles;
	}

	public String getProgStat() {
		return progStat;
	}

	public String getBuffRead() {
		return buffRead;
	}

	public Integer getEtcValidStatus() {
		return etcValidStatus;
	}

	public Integer getPostDeviceStatus() {
		return postDeviceStatus;
	}

	public Integer getPreTxnBalance() {
		return preTxnBalance;
	}

	public Integer getPlanTypeId() {
		return planTypeId;
	}

//	public Integer getEtcTxStatus() {
//		return etcTxStatus;
//	}

	
	public String getPlateCountry() {
		return plateCountry;
	}

	public String getPlateState() {
		return plateState;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public String getRevenueDate() {
		return revenueDate;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public Integer getAtpFileId() {
		return atpFileId;
	}

	public String getUpdateTs() {
		return updateTs;
	}

	public String getLaneDataSource() {
		return laneDataSource;
	}

	public Integer getCorrReasonId() {
		return corrReasonId;
	}

	public String getReconDate() {
		return reconDate;
	}

	public String getExternFileDate() {
		return externFileDate;
	}

	public Integer getMileage() {
		return mileage;
	}

	public Integer getInternalAuditId() {
		return internalAuditId;
	}

	public Integer getModifiedStatus() {
		return modifiedStatus;
	}

	public Integer getExceptionStatus() {
		return exceptionStatus;
	}

	public String getDepositId() {
		return depositId;
	}

	public String getReceivedDate() {
		return receivedDate;
	}

	public Integer getViolType() {
		return violType;
	}

	public Integer getReviewedVehicleType() {
		return reviewedVehicleType;
	}

	public Integer getReviewedClass() {
		return reviewedClass;
	}

	public Integer getImageRvwClerkId() {
		return imageRvwClerkId;
	}

	public Long getImageBatchId() {
		return imageBatchId;
	}

	public Long getImageBatchSeqNumber() {
		return imageBatchSeqNumber;
	}

	public Integer getImageIndex() {
		return imageIndex;
	}

	public Long getAdjustedAmount() {
		return adjustedAmount;
	}

	public Long getNoticeTollAmount() {
		return noticeTollAmount;
	}

	public Integer getOutputFileId() {
		return outputFileId;
	}

	public String getOutputFileType() {
		return outputFileType;
	}

	public String getReciprocityTrx() {
		return reciprocityTrx;
	}

	public Integer getMakeId() {
		return makeId;
	}

	public String getEventTimestamp() {
		return eventTimestamp;
	}

	public String getIsLprEnabled() {
		return isLprEnabled;
	}

	public Integer getEventType() {
		return eventType;
	}

	public Integer getPreeventType() {
		return preeventType;
	}

	public Integer getPrevViolTxStatus() {
		return prevViolTxStatus;
	}

	public Integer getViolTxStatus() {
		return violTxStatus;
	}

	public Integer getDmvPlateType() {
		return DmvPlateType;
	}

	public String getIsReversed() {
		return isReversed;
	}

	public Integer getReconStatusInd() {
		return reconStatusInd;
	}

	public Integer getReconSubCodeInd() {
		return reconSubCodeInd;
	}

	public Integer getDeviceReadCount() {
		return deviceReadCount;
	}

	public Integer getDeviceWriteCount() {
		return deviceWriteCount;
	}

	public Integer getEntryDeviceReadCount() {
		return entryDeviceReadCount;
	}

	public Integer getEntryDeviceWriteCount() {
		return entryDeviceWriteCount;
	}

	public String getCscLookupKey() {
		return cscLookupKey;
	}

	public String getDeviceProgramStatus() {
		return deviceProgramStatus;
	}

	public String getBufferReadFlag() {
		return bufferReadFlag;
	}

	public Integer getLaneDeviceStatus() {
		return laneDeviceStatus;
	}

	public String getTillSystemType() {
		return tillSystemType;
	}

	public String getAetFlag() {
		return aetFlag;
	}

	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
	}

	public void setTxExternRefNo(String txExternRefNo) {
		this.txExternRefNo = txExternRefNo;
	}

	public void setTrxLaneSerial(long trxLaneSerial) {
		this.trxLaneSerial = trxLaneSerial;
	}

	public void setTxSeqNumber(Long txSeqNumber) {
		this.txSeqNumber = txSeqNumber;
	}

	public void setExternFileId(Long externFileId) {
		this.externFileId = externFileId;
	}

	public void setLaneId(Integer laneId) {
		this.laneId = laneId;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public void setTxTimeStamp(OffsetDateTime txTimeStamp) {
		this.txTimeStamp = txTimeStamp;
	}

	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}

	public void setTxModSeq(Integer txModSeq) {
		this.txModSeq = txModSeq;
	}

	public void setTxType(String txType) {
		this.txType = txType;
	}

	public void setTxSubtype(String txSubtype) {
		this.txSubtype = txSubtype;
	}

	public void setTollSystemType(String tollSystemType) {
		this.tollSystemType = tollSystemType;
	}

	public void setLaneMode(Integer laneMode) {
		this.laneMode = laneMode;
	}

	public void setLaneType(Integer laneType) {
		this.laneType = laneType;
	}

	public void setLaneState(Integer laneState) {
		this.laneState = laneState;
	}

	public void setLaneHealth(Integer laneHealth) {
		this.laneHealth = laneHealth;
	}

	public void setPlazaAgencyId(Integer plazaAgencyId) {
		this.plazaAgencyId = plazaAgencyId;
	}

	public void setPlazaId(Integer plazaId) {
		this.plazaId = plazaId;
	}

	public void setcollectorNumber(Integer collectorNumber) {
		this.collectorNumber = collectorNumber;
	}

	public void setTourSegment(Integer tourSegment) {
		this.tourSegment = tourSegment;
	}

	public void setEntryDataSource(String entryDataSource) {
		this.entryDataSource = entryDataSource;
	}

	public void setEntryLaneId(Integer entryLaneId) {
		this.entryLaneId = entryLaneId;
	}

	public void setEntryPlazaId(Integer entryPlazaId) {
		this.entryPlazaId = entryPlazaId;
	}

	public void setEntryTxSeqNumber(Integer entryTxSeqNumber) {
		this.entryTxSeqNumber = entryTxSeqNumber;
	}

	public void setEntryVehicleSpeed(Integer entryVehicleSpeed) {
		this.entryVehicleSpeed = entryVehicleSpeed;
	}

	public void setLaneTxStatus(Integer laneTxStatus) {
		this.laneTxStatus = laneTxStatus;
	}

	public void setLanetxType(Integer lanetxType) {
		this.lanetxType = lanetxType;
	}

	public void setTollRevenueType(Integer tollRevenueType) {
		this.tollRevenueType = tollRevenueType;
	}

	public void setActualClass(Integer actualClass) {
		this.actualClass = actualClass;
	}

	public void setActualAxles(Integer actualAxles) {
		this.actualAxles = actualAxles;
	}

	public void setActualExtraAxles(Integer actualExtraAxles) {
		this.actualExtraAxles = actualExtraAxles;
	}

	public void setCollectorClass(Integer collectorClass) {
		this.collectorClass = collectorClass;
	}

	public void setCollectorAxles(Integer collectorAxles) {
		this.collectorAxles = collectorAxles;
	}

	public void setPreClassClass(Integer preClassClass) {
		this.preClassClass = preClassClass;
	}

	public void setPreClassAxles(Integer preClassAxles) {
		this.preClassAxles = preClassAxles;
	}

	public void setPostClassClass(Integer postClassClass) {
		this.postClassClass = postClassClass;
	}

	public void setPostClassAxles(Integer postClassAxles) {
		this.postClassAxles = postClassAxles;
	}

	public void setForwardAxles(Integer forwardAxles) {
		this.forwardAxles = forwardAxles;
	}

	public void setReverseAxles(Integer reverseAxles) {
		this.reverseAxles = reverseAxles;
	}

	public void setFullFareAmount(Double fullFareAmount) {
		this.fullFareAmount = fullFareAmount;
	}

	public void setDiscountedAmount(Double discountedAmount) {
		this.discountedAmount = discountedAmount;
	}

	public void setCollectedAmount(Double collectedAmount) {
		this.collectedAmount = collectedAmount;
	}

	public void setUnrealizedAmount(Double unrealizedAmount) {
		this.unrealizedAmount = unrealizedAmount;
	}

	public void setIsDiscountable(String isDiscountable) {
		this.isDiscountable = isDiscountable;
	}

	public void setIsMedianFare(String isMedianFare) {
		this.isMedianFare = isMedianFare;
	}

	public void setIsPeak(String isPeak) {
		this.isPeak = isPeak;
	}

	public void setPriceScheduleId(Integer priceScheduleId) {
		this.priceScheduleId = priceScheduleId;
	}

	public void setVehicleSpeed(Integer vehicleSpeed) {
		this.vehicleSpeed = vehicleSpeed;
	}

	public void setReceiptIssued(Integer receiptIssued) {
		this.receiptIssued = receiptIssued;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public void setDeviceCodedClass(Integer deviceCodedClass) {
		this.deviceCodedClass = deviceCodedClass;
	}

	public void setDeviceAgencyClass(Integer deviceAgencyClass) {
		this.deviceAgencyClass = deviceAgencyClass;
	}

	public void setDeviceIagClass(Integer deviceIagClass) {
		this.deviceIagClass = deviceIagClass;
	}

	public void setDeviceAxles(Integer deviceAxles) {
		this.deviceAxles = deviceAxles;
	}

	public void setEtcAccountId(Long etcAccountId) {
		this.etcAccountId = etcAccountId;
	}

	public void setAccountAgencyId(Integer accountAgencyId) {
		this.accountAgencyId = accountAgencyId;
	}

	public void setReadAviClass(Integer readAviClass) {
		this.readAviClass = readAviClass;
	}

	public void setReadAviAxles(Integer readAviAxles) {
		this.readAviAxles = readAviAxles;
	}

	public void setProgStat(String progStat) {
		this.progStat = progStat;
	}

	public void setBuffRead(String buffRead) {
		this.buffRead = buffRead;
	}

	public void setEtcValidStatus(Integer etcValidStatus) {
		this.etcValidStatus = etcValidStatus;
	}

	public void setPostDeviceStatus(Integer postDeviceStatus) {
		this.postDeviceStatus = postDeviceStatus;
	}

	public void setPreTxnBalance(Integer preTxnBalance) {
		this.preTxnBalance = preTxnBalance;
	}

	public void setPlanTypeId(Integer planTypeId) {
		this.planTypeId = planTypeId;
	}

//	public void setEtcTxStatus(Integer etcTxStatus) {
//		this.etcTxStatus = etcTxStatus;
//	}


	public String getSpeedViolFlag() {
		return speedViolFlag;
	}

	public Integer getTxStatus() {
		return txStatus;
	}

	public void setTxStatus(Integer txStatus) {
		this.txStatus = txStatus;
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

	public void setPlateCountry(String plateCountry) {
		this.plateCountry = plateCountry;
	}

	public void setPlateState(String plateState) {
		this.plateState = plateState;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public void setRevenueDate(String revenueDate) {
		this.revenueDate = revenueDate;
	}

	public void setPostedDate(String postedDate) {
		this.postedDate = postedDate;
	}

	public void setAtpFileId(Integer atpFileId) {
		this.atpFileId = atpFileId;
	}

	public void setUpdateTs(String updateTs) {
		this.updateTs = updateTs;
	}

	public void setLaneDataSource(String laneDataSource) {
		this.laneDataSource = laneDataSource;
	}

	public void setCorrReasonId(Integer corrReasonId) {
		this.corrReasonId = corrReasonId;
	}

	public void setReconDate(String reconDate) {
		this.reconDate = reconDate;
	}

	public void setExternFileDate(String externFileDate) {
		this.externFileDate = externFileDate;
	}

	public void setMileage(Integer mileage) {
		this.mileage = mileage;
	}

	public void setInternalAuditId(Integer internalAuditId) {
		this.internalAuditId = internalAuditId;
	}

	public void setModifiedStatus(Integer modifiedStatus) {
		this.modifiedStatus = modifiedStatus;
	}

	public void setExceptionStatus(Integer exceptionStatus) {
		this.exceptionStatus = exceptionStatus;
	}

	public void setDepositId(String depositId) {
		this.depositId = depositId;
	}

	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}

	public void setViolType(Integer violType) {
		this.violType = violType;
	}

	public void setReviewedVehicleType(Integer reviewedVehicleType) {
		this.reviewedVehicleType = reviewedVehicleType;
	}

	public void setReviewedClass(Integer reviewedClass) {
		this.reviewedClass = reviewedClass;
	}

	public void setImageRvwClerkId(Integer imageRvwClerkId) {
		this.imageRvwClerkId = imageRvwClerkId;
	}

	public void setImageBatchId(Long imageBatchId) {
		this.imageBatchId = imageBatchId;
	}

	public void setImageBatchSeqNumber(Long imageBatchSeqNumber) {
		this.imageBatchSeqNumber = imageBatchSeqNumber;
	}

	public void setImageIndex(Integer imageIndex) {
		this.imageIndex = imageIndex;
	}

	public void setAdjustedAmount(Long adjustedAmount) {
		this.adjustedAmount = adjustedAmount;
	}

	public void setNoticeTollAmount(Long noticeTollAmount) {
		this.noticeTollAmount = noticeTollAmount;
	}

	public void setOutputFileId(Integer outputFileId) {
		this.outputFileId = outputFileId;
	}

	public void setOutputFileType(String outputFileType) {
		this.outputFileType = outputFileType;
	}

	public void setReciprocityTrx(String reciprocityTrx) {
		this.reciprocityTrx = reciprocityTrx;
	}

	public void setMakeId(Integer makeId) {
		this.makeId = makeId;
	}

	public void setEventTimestamp(String eventTimestamp) {
		this.eventTimestamp = eventTimestamp;
	}

	public void setIsLprEnabled(String isLprEnabled) {
		this.isLprEnabled = isLprEnabled;
	}

	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}

	public void setPreeventType(Integer preeventType) {
		this.preeventType = preeventType;
	}

	public void setPrevViolTxStatus(Integer prevViolTxStatus) {
		this.prevViolTxStatus = prevViolTxStatus;
	}

	public void setViolTxStatus(Integer violTxStatus) {
		this.violTxStatus = violTxStatus;
	}

	public void setDmvPlateType(Integer dmvPlateType) {
		DmvPlateType = dmvPlateType;
	}

	public void setIsReversed(String isReversed) {
		this.isReversed = isReversed;
	}

	public void setReconStatusInd(Integer reconStatusInd) {
		this.reconStatusInd = reconStatusInd;
	}

	public void setReconSubCodeInd(Integer reconSubCodeInd) {
		this.reconSubCodeInd = reconSubCodeInd;
	}

	public void setDeviceReadCount(Integer deviceReadCount) {
		this.deviceReadCount = deviceReadCount;
	}

	public void setDeviceWriteCount(Integer deviceWriteCount) {
		this.deviceWriteCount = deviceWriteCount;
	}

	public void setEntryDeviceReadCount(Integer entryDeviceReadCount) {
		this.entryDeviceReadCount = entryDeviceReadCount;
	}

	public void setEntryDeviceWriteCount(Integer entryDeviceWriteCount) {
		this.entryDeviceWriteCount = entryDeviceWriteCount;
	}

	public void setCscLookupKey(String cscLookupKey) {
		this.cscLookupKey = cscLookupKey;
	}

	public void setDeviceProgramStatus(String deviceProgramStatus) {
		this.deviceProgramStatus = deviceProgramStatus;
	}

	public void setBufferReadFlag(String bufferReadFlag) {
		this.bufferReadFlag = bufferReadFlag;
	}

	public void setLaneDeviceStatus(Integer laneDeviceStatus) {
		this.laneDeviceStatus = laneDeviceStatus;
	}

	public void setTillSystemType(String tillSystemType) {
		this.tillSystemType = tillSystemType;
	}

	public void setAetFlag(String aetFlag) {
		this.aetFlag = aetFlag;
	}

	public Double getDiscountedAmount2() {
		return discountedAmount2;
	}

	public void setDiscountedAmount2(Double discountedAmount2) {
		this.discountedAmount2 = discountedAmount2;
	}

	public Integer getCollectorNumber() {
		return collectorNumber;
	}

	public void setCollectorNumber(Integer collectorNumber) {
		this.collectorNumber = collectorNumber;
	}

	public OffsetDateTime getEntryTimestamp() {
		return entryTimestamp;
	}

	public void setEntryTimestamp(OffsetDateTime entryTimestamp) {
		this.entryTimestamp = entryTimestamp;
	}

	@Override
	public String toString() {
		return "IbtsViolProcessDTO [laneTxId=" + laneTxId + ", txExternRefNo=" + txExternRefNo + ", trxLaneSerial="
				+ trxLaneSerial + ", txSeqNumber=" + txSeqNumber + ", externFileId=" + externFileId + ", laneId="
				+ laneId + ", deviceNo=" + deviceNo + ", txTimeStamp=" + txTimeStamp + ", txDate=" + txDate
				+ ", txModSeq=" + txModSeq + ", txType=" + txType + ", txSubtype=" + txSubtype + ", tollSystemType="
				+ tollSystemType + ", laneMode=" + laneMode + ", laneType=" + laneType + ", laneState=" + laneState
				+ ", laneHealth=" + laneHealth + ", plazaAgencyId=" + plazaAgencyId + ", plazaId=" + plazaId
				+ ", collectorNumber=" + collectorNumber + ", tourSegment=" + tourSegment + ", entryDataSource="
				+ entryDataSource + ", entryLaneId=" + entryLaneId + ", entryPlazaId=" + entryPlazaId
				+ ", entryTimestamp=" + entryTimestamp + ", entryTxSeqNumber=" + entryTxSeqNumber
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
				+ ", readAviAxles=" + readAviAxles + ", progStat=" + progStat + ", buffRead=" + buffRead
				+ ", etcValidStatus=" + etcValidStatus + ", postDeviceStatus=" + postDeviceStatus + ", preTxnBalance="
				+ preTxnBalance + ", planTypeId=" + planTypeId + ", txStatus=" + txStatus + ", speedViolFlag="
				+ speedViolFlag + ", imageTaken=" + imageTaken + ", plateCountry=" + plateCountry + ", plateState="
				+ plateState + ", plateNumber=" + plateNumber + ", revenueDate=" + revenueDate + ", postedDate="
				+ postedDate + ", atpFileId=" + atpFileId + ", updateTs=" + updateTs + ", laneDataSource="
				+ laneDataSource + ", corrReasonId=" + corrReasonId + ", reconDate=" + reconDate + ", externFileDate="
				+ externFileDate + ", mileage=" + mileage + ", internalAuditId=" + internalAuditId + ", modifiedStatus="
				+ modifiedStatus + ", exceptionStatus=" + exceptionStatus + ", depositId=" + depositId
				+ ", receivedDate=" + receivedDate + ", violType=" + violType + ", reviewedVehicleType="
				+ reviewedVehicleType + ", reviewedClass=" + reviewedClass + ", imageRvwClerkId=" + imageRvwClerkId
				+ ", imageBatchId=" + imageBatchId + ", imageBatchSeqNumber=" + imageBatchSeqNumber + ", imageIndex="
				+ imageIndex + ", adjustedAmount=" + adjustedAmount + ", noticeTollAmount=" + noticeTollAmount
				+ ", outputFileId=" + outputFileId + ", outputFileType=" + outputFileType + ", reciprocityTrx="
				+ reciprocityTrx + ", makeId=" + makeId + ", eventTimestamp=" + eventTimestamp + ", isLprEnabled="
				+ isLprEnabled + ", eventType=" + eventType + ", preeventType=" + preeventType + ", prevViolTxStatus="
				+ prevViolTxStatus + ", violTxStatus=" + violTxStatus + ", DmvPlateType=" + DmvPlateType
				+ ", isReversed=" + isReversed + ", reconStatusInd=" + reconStatusInd + ", reconSubCodeInd="
				+ reconSubCodeInd + ", deviceReadCount=" + deviceReadCount + ", deviceWriteCount=" + deviceWriteCount
				+ ", entryDeviceReadCount=" + entryDeviceReadCount + ", entryDeviceWriteCount=" + entryDeviceWriteCount
				+ ", cscLookupKey=" + cscLookupKey + ", deviceProgramStatus=" + deviceProgramStatus
				+ ", bufferReadFlag=" + bufferReadFlag + ", laneDeviceStatus=" + laneDeviceStatus + ", tillSystemType="
				+ tillSystemType + ", aetFlag=" + aetFlag + ", tollAmount=" + tollAmount + ", itolFareAmount=" + itolFareAmount + "]";
	}

	
}