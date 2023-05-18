package com.conduent.Ibtsprocessing.dto;

import java.time.LocalDateTime;

import com.google.gson.annotations.Expose;

public class IBTSViolDTO {

	@Expose(serialize = true, deserialize = true)
	private Integer accountAgencyId;
	@Expose(serialize = true, deserialize = true)
	private Integer accountType;
	@Expose(serialize = true, deserialize = true)
	private Integer actualClass;
	@Expose(serialize = true, deserialize = true)
	private Integer actualAxles;
	@Expose(serialize = true, deserialize = true)
	private Integer actualExtraAxles;
	@Expose(serialize = true, deserialize = true)
	private String aetFlag = "Y";
	@Expose(serialize = true, deserialize = true)
	private Integer atpFileId;
	@Expose(serialize = true, deserialize = true)
	private String basefilename;
	@Expose(serialize = true, deserialize = true)
	private String bufferedReadFlag;
	@Expose(serialize = true, deserialize = true)
	private String classAdjType;
	@Expose(serialize = true, deserialize = true)
	private Integer collectorClass;
	@Expose(serialize = true, deserialize = true)
	private Integer collectorAxles;
	@Expose(serialize = true, deserialize = true)
	private Integer collectorNumber;
	@Expose(serialize = true, deserialize = true)
	private String depositId;
	@Expose(serialize = true, deserialize = true)
	private Integer deviceCodedClass;
	@Expose(serialize = true, deserialize = true)
	private Integer deviceAgencyClass;
	@Expose(serialize = true, deserialize = true)
	private Integer deviceIagClass;
	@Expose(serialize = true, deserialize = true)
	private Integer deviceAxles;
	@Expose(serialize = true, deserialize = true)
	private String deviceNo;
	@Expose(serialize = true, deserialize = true)
	private String deviceProgramStatus;
	@Expose(serialize = true, deserialize = true)
	private Integer deviceReadCount;
	@Expose(serialize = true, deserialize = true)
	private Integer deviceWriteCount;
	@Expose(serialize = true, deserialize = true)
	private Double discountedAmount;
	@Expose(serialize = true, deserialize = true)
	private Double discountedAmount2;
	@Expose(serialize = true, deserialize = true)
	private String entryDataSource;
	@Expose(serialize = true, deserialize = true)
	private Integer entryDeviceReadCount;
	@Expose(serialize = true, deserialize = true)
	private Integer entryDeviceWriteCount;
	@Expose(serialize = true, deserialize = true)
	private Integer entryLaneId;
	@Expose(serialize = true, deserialize = true)
	private Integer entryPlazaId;
	@Expose(serialize = true, deserialize = true)
	private String entryTimestamp;
	//private ZonedDateTime entryTimestamp;
	//private OffsetDateTime entryTimestamp;
	@Expose(serialize = true, deserialize = true)
	private Integer entryTxSeqNumber;
	@Expose(serialize = true, deserialize = true)
	private Long etcAccountId;
	@Expose(serialize = true, deserialize = true)
	private Integer etcTxStatus;
	@Expose(serialize = true, deserialize = true)
	//private String eventTimestamp;
	private LocalDateTime eventTimestamp;
	@Expose(serialize = true, deserialize = true)
	private Integer eventType;
	@Expose(serialize = true, deserialize = true)
	private String externFileDate;
	@Expose(serialize = true, deserialize = true)
	private Long externFileId;
	@Expose(serialize = true, deserialize = true)
	private Integer forwardAxles;
	/*
	 * @Expose(serialize = true, deserialize = true) private Double fullFareAmount;
	 */
	@Expose(serialize = true, deserialize = true)
	private String imageTaken;
	@Expose(serialize = true, deserialize = true)
	private String isDiscountable;
	@Expose(serialize = true, deserialize = true)
	private String isMedianFare;
	@Expose(serialize = true, deserialize = true)
	private String isPeak;
	@Expose(serialize = true, deserialize = true)
	private String isReciprocityTxn;
	@Expose(serialize = true, deserialize = true)
	private String isReversed;
	@Expose(serialize = true, deserialize = true)
	private Integer laneHealth;
	@Expose(serialize = true, deserialize = true)
	private Integer laneId;
	@Expose(serialize = true, deserialize = true)
	private Integer laneMode;
	@Expose(serialize = true, deserialize = true)
	private Integer laneState;
	@Expose(serialize = true, deserialize = true)
	private Long laneTxId;
	@Expose(serialize = true, deserialize = true)
	private Integer laneTxStatus;
	@Expose(serialize = true, deserialize = true)
	private Integer laneTxType;
	@Expose(serialize = true, deserialize = true)
	private Integer laneType;
	@Expose(serialize = true, deserialize = true)
	private Integer planTypeId;
	@Expose(serialize = true, deserialize = true)
	private String plateCountry;
	@Expose(serialize = true, deserialize = true)
	private String plateNumber;
	@Expose(serialize = true, deserialize = true)
	private String plateState;
	@Expose(serialize = true, deserialize = true)
	private Integer plazaAgencyId;
	@Expose(serialize = true, deserialize = true)
	private Integer plazaId;
	@Expose(serialize = true, deserialize = true)
	private Integer postDeviceStatus;
	@Expose(serialize = true, deserialize = true)
	private Integer postClassAxles;
	@Expose(serialize = true, deserialize = true)
	private Integer postClassClass;
	@Expose(serialize = true, deserialize = true)
	private Integer preTxnBalance;
	@Expose(serialize = true, deserialize = true)
	private Integer prevEventType;
	@Expose(serialize = true, deserialize = true)
	private Integer prevViolTxStatus;
	@Expose(serialize = true, deserialize = true)
	private Integer priceScheduleId;
	@Expose(serialize = true, deserialize = true)
	private Integer readAviAxles;
	@Expose(serialize = true, deserialize = true)
	private String speedViolFlag;
	@Expose(serialize = true, deserialize = true)
	private Integer tollRevenueType;
	@Expose(serialize = true, deserialize = true)
	private String tollSystemType;
	@Expose(serialize = true, deserialize = true)
	private String txDate;
	@Expose(serialize = true, deserialize = true)
	private String txExternRefNo;
	@Expose(serialize = true, deserialize = true)
	private Integer txModSeq;
	@Expose(serialize = true, deserialize = true)
	private Long txSeqNumber;
	@Expose(serialize = true, deserialize = true)
	private String txSubtypeInd;
	@Expose(serialize = true, deserialize = true)
	private String txTimestamp;
	//private ZonedDateTime txTimestamp;
	//private OffsetDateTime txTimestamp;
	@Expose(serialize = true, deserialize = true)
	private String txTypeInd;
	@Expose(serialize = true, deserialize = true)
	private Double unrealizedAmount;
	@Expose(serialize = true, deserialize = true)
	private Integer vehicleSpeed;
	@Expose(serialize = true, deserialize = true)
	private Integer violTxStatus;
	@Expose(serialize = true, deserialize = true)
	private Integer violType;
	@Expose(serialize = true, deserialize = true)
	private String hovFlag;
	@Expose(serialize = true, deserialize = true)
	private double videoFareAmount;
	@Expose(serialize = true, deserialize = true)
	private double etcFareAmount;
	@Expose(serialize = true, deserialize = true)
	private double cashFareAmount;
	@Expose(serialize = true, deserialize = true)
	private double expectedRevenueAmount;
	@Expose(serialize = true, deserialize = true)
	private double postedFareAmount;
	@Expose(serialize = true, deserialize = true)
	private Integer txStatus;
	
	@Expose(serialize = true, deserialize = true)
	private Double itolFareAmount;
	
	
	public Double getItolFareAmount() {
		return itolFareAmount;
	}
	public void setItolFareAmount(Double itolFareAmount) {
		this.itolFareAmount = itolFareAmount;
	}
	public Integer getAccountAgencyId() {
		return accountAgencyId;
	}
	public void setAccountAgencyId(Integer accountAgencyId) {
		this.accountAgencyId = accountAgencyId;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
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
	public String getAetFlag() {
		return aetFlag;
	}
	public void setAetFlag(String aetFlag) {
		this.aetFlag = aetFlag;
	}
	public Integer getAtpFileId() {
		return atpFileId;
	}
	public void setAtpFileId(Integer atpFileId) {
		this.atpFileId = atpFileId;
	}
	public String getBasefilename() {
		return basefilename;
	}
	public void setBasefilename(String basefilename) {
		this.basefilename = basefilename;
	}
	public String getBufferedReadFlag() {
		return bufferedReadFlag;
	}
	public void setBufferedReadFlag(String bufferedReadFlag) {
		this.bufferedReadFlag = bufferedReadFlag;
	}
	public String getClassAdjType() {
		return classAdjType;
	}
	public void setClassAdjType(String classAdjType) {
		this.classAdjType = classAdjType;
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
	public Integer getcollectorNumber() {
		return collectorNumber;
	}
	public void setcollectorNumber(Integer collectorNumber) {
		this.collectorNumber = collectorNumber;
	}
	public String getDepositId() {
		return depositId;
	}
	public void setDepositId(String depositId) {
		this.depositId = depositId;
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
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public String getDeviceProgramStatus() {
		return deviceProgramStatus;
	}
	public void setDeviceProgramStatus(String deviceProgramStatus) {
		this.deviceProgramStatus = deviceProgramStatus;
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
	public Double getDiscountedAmount() {
		return discountedAmount;
	}
	public void setDiscountedAmount(Double discountedAmount) {
		this.discountedAmount = discountedAmount;
	}
	public String getEntryDataSource() {
		return entryDataSource;
	}
	public void setEntryDataSource(String entryDataSource) {
		this.entryDataSource = entryDataSource;
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
//	public OffsetDateTime getEntryTimestamp() {
//		return entryTimestamp;
//	}
//	public void setEntryTimestamp(OffsetDateTime entryTimestamp) {
//		this.entryTimestamp = entryTimestamp;
//	}
	public Integer getEntryTxSeqNumber() {
		return entryTxSeqNumber;
	}
	public void setEntryTxSeqNumber(Integer entryTxSeqNumber) {
		this.entryTxSeqNumber = entryTxSeqNumber;
	}
	public Long getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(Long etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	public Integer getEtcTxStatus() {
		return etcTxStatus;
	}
	public void setEtcTxStatus(Integer etcTxStatus) {
		this.etcTxStatus = etcTxStatus;
	}
	public LocalDateTime getEventTimestamp() {
		return eventTimestamp;
	}
	public void setEventTimestamp(LocalDateTime eventTimestamp) {
		this.eventTimestamp = eventTimestamp;
	}
	public Integer getEventType() {
		return eventType;
	}
	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}
	public String getExternFileDate() {
		return externFileDate;
	}
	public void setExternFileDate(String externFileDate) {
		this.externFileDate = externFileDate;
	}
	public Long getExternFileId() {
		return externFileId;
	}
	public void setExternFileId(Long externFileId) {
		this.externFileId = externFileId;
	}
	public Integer getForwardAxles() {
		return forwardAxles;
	}
	public void setForwardAxles(Integer forwardAxles) {
		this.forwardAxles = forwardAxles;
	}
	public String getImageTaken() {
		return imageTaken;
	}
	public void setImageTaken(String imageTaken) {
		this.imageTaken = imageTaken;
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
	public String getIsReciprocityTxn() {
		return isReciprocityTxn;
	}
	public void setIsReciprocityTxn(String isReciprocityTxn) {
		this.isReciprocityTxn = isReciprocityTxn;
	}
	public String getIsReversed() {
		return isReversed;
	}
	public void setIsReversed(String isReversed) {
		this.isReversed = isReversed;
	}
	public Integer getLaneHealth() {
		return laneHealth;
	}
	public void setLaneHealth(Integer laneHealth) {
		this.laneHealth = laneHealth;
	}
	public Integer getLaneId() {
		return laneId;
	}
	public void setLaneId(Integer laneId) {
		this.laneId = laneId;
	}
	public Integer getLaneMode() {
		return laneMode;
	}
	public void setLaneMode(Integer laneMode) {
		this.laneMode = laneMode;
	}
	public Integer getLaneState() {
		return laneState;
	}
	public void setLaneState(Integer laneState) {
		this.laneState = laneState;
	}
	public Long getLaneTxId() {
		return laneTxId;
	}
	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
	}
	public Integer getLaneTxStatus() {
		return laneTxStatus;
	}
	public void setLaneTxStatus(Integer laneTxStatus) {
		this.laneTxStatus = laneTxStatus;
	}
	public Integer getLaneTxType() {
		return laneTxType;
	}
	public void setLaneTxType(Integer laneTxType) {
		this.laneTxType = laneTxType;
	}
	public Integer getLaneType() {
		return laneType;
	}
	public void setLaneType(Integer laneType) {
		this.laneType = laneType;
	}
	public Integer getPlanTypeId() {
		return planTypeId;
	}
	public void setPlanTypeId(Integer planTypeId) {
		this.planTypeId = planTypeId;
	}
	public String getPlateCountry() {
		return plateCountry;
	}
	public void setPlateCountry(String plateCountry) {
		this.plateCountry = plateCountry;
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
	public Integer getPostDeviceStatus() {
		return postDeviceStatus;
	}
	public void setPostDeviceStatus(Integer postDeviceStatus) {
		this.postDeviceStatus = postDeviceStatus;
	}
	public Integer getPostClassAxles() {
		return postClassAxles;
	}
	public void setPostClassAxles(Integer postClassAxles) {
		this.postClassAxles = postClassAxles;
	}
	public Integer getPostClassClass() {
		return postClassClass;
	}
	public void setPostClassClass(Integer postClassClass) {
		this.postClassClass = postClassClass;
	}
	public Integer getPreTxnBalance() {
		return preTxnBalance;
	}
	public void setPreTxnBalance(Integer preTxnBalance) {
		this.preTxnBalance = preTxnBalance;
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
	public Integer getPriceScheduleId() {
		return priceScheduleId;
	}
	public void setPriceScheduleId(Integer priceScheduleId) {
		this.priceScheduleId = priceScheduleId;
	}
	public Integer getReadAviAxles() {
		return readAviAxles;
	}
	public void setReadAviAxles(Integer readAviAxles) {
		this.readAviAxles = readAviAxles;
	}
	public String getSpeedViolFlag() {
		return speedViolFlag;
	}
	public void setSpeedViolFlag(String speedViolFlag) {
		this.speedViolFlag = speedViolFlag;
	}
	public Integer getTollRevenueType() {
		return tollRevenueType;
	}
	public void setTollRevenueType(Integer tollRevenueType) {
		this.tollRevenueType = tollRevenueType;
	}
	public String getTollSystemType() {
		return tollSystemType;
	}
	public void setTollSystemType(String tollSystemType) {
		this.tollSystemType = tollSystemType;
	}
	public String getTxDate() {
		return txDate;
	}
	public void setTxDate(String txDate) {
		this.txDate = txDate;
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
	public Long getTxSeqNumber() {
		return txSeqNumber;
	}
	public void setTxSeqNumber(Long txSeqNumber) {
		this.txSeqNumber = txSeqNumber;
	}
   
	//	public OffsetDateTime getTxTimestamp() {
//		return txTimestamp;
//	}
//	public void setTxTimestamp(OffsetDateTime txTimestamp) {
//		this.txTimestamp = txTimestamp;
//	}
	
	
	
	public Integer getCollectorNumber() {
		return collectorNumber;
	}
	public void setCollectorNumber(Integer collectorNumber) {
		this.collectorNumber = collectorNumber;
	}
	
	
	public String getEntryTimestamp() {
		return entryTimestamp;
	}
	public void setEntryTimestamp(String entryTimestamp) {
		this.entryTimestamp = entryTimestamp;
	}
	public String getTxTimestamp() {
		return txTimestamp;
	}
	public void setTxTimestamp(String txTimestamp) {
		this.txTimestamp = txTimestamp;
	}
	public String getTxTypeInd() {
		return txTypeInd;
	}
	public void setTxTypeInd(String txTypeInd) {
		this.txTypeInd = txTypeInd;
	}
	public Double getUnrealizedAmount() {
		return unrealizedAmount;
	}
	public void setUnrealizedAmount(Double unrealizedAmount) {
		this.unrealizedAmount = unrealizedAmount;
	}
	public Integer getVehicleSpeed() {
		return vehicleSpeed;
	}
	public void setVehicleSpeed(Integer vehicleSpeed) {
		this.vehicleSpeed = vehicleSpeed;
	}
	public Integer getViolTxStatus() {
		return violTxStatus;
	}
	public void setViolTxStatus(Integer violTxStatus) {
		this.violTxStatus = violTxStatus;
	}
	public Integer getViolType() {
		return violType;
	}
	public void setViolType(Integer violType) {
		this.violType = violType;
	}
	public String getHovFlag() {
		return hovFlag;
	}
	public void setHovFlag(String hovFlag) {
		this.hovFlag = hovFlag;
	}
	public double getVideoFareAmount() {
		return videoFareAmount;
	}
	public void setVideoFareAmount(double videoFareAmount) {
		this.videoFareAmount = videoFareAmount;
	}
	public double getEtcFareAmount() {
		return etcFareAmount;
	}
	public void setEtcFareAmount(double etcFareAmount) {
		this.etcFareAmount = etcFareAmount;
	}
	public double getCashFareAmount() {
		return cashFareAmount;
	}
	public void setCashFareAmount(double cashFareAmount) {
		this.cashFareAmount = cashFareAmount;
	}
	public double getExpectedRevenueAmount() {
		return expectedRevenueAmount;
	}
	public void setExpectedRevenueAmount(double expectedRevenueAmount) {
		this.expectedRevenueAmount = expectedRevenueAmount;
	}
	public double getPostedFareAmount() {
		return postedFareAmount;
	}
	public void setPostedFareAmount(double postedFareAmount) {
		this.postedFareAmount = postedFareAmount;
	}
	public Integer getTxStatus() {
		return txStatus;
	}
	public void setTxStatus(Integer txStatus) {
		this.txStatus = txStatus;
	}
	public Double getDiscountedAmount2() {
		return discountedAmount2;
	}
	public void setDiscountedAmount2(Double discountedAmount2) {
		this.discountedAmount2 = discountedAmount2;
	}

	
	public String getTxSubtypeInd() {
		return txSubtypeInd;
	}
	public void setTxSubtypeInd(String txSubtypeInd) {
		this.txSubtypeInd = txSubtypeInd;
	}
	@Override
	public String toString() {
		return "IBTSViolDTO [accountAgencyId=" + accountAgencyId + ", accountType=" + accountType + ", actualClass="
				+ actualClass + ", actualAxles=" + actualAxles + ", actualExtraAxles=" + actualExtraAxles + ", aetFlag="
				+ aetFlag + ", atpFileId=" + atpFileId + ", basefilename=" + basefilename + ", bufferedReadFlag="
				+ bufferedReadFlag + ", classAdjType=" + classAdjType + ", collectorClass=" + collectorClass
				+ ", collectorAxles=" + collectorAxles + ", collectorNumber=" + collectorNumber + ", depositId="
				+ depositId + ", deviceCodedClass=" + deviceCodedClass + ", deviceAgencyClass=" + deviceAgencyClass
				+ ", deviceIagClass=" + deviceIagClass + ", deviceAxles=" + deviceAxles + ", deviceNo=" + deviceNo
				+ ", deviceProgramStatus=" + deviceProgramStatus + ", deviceReadCount=" + deviceReadCount
				+ ", deviceWriteCount=" + deviceWriteCount + ", discountedAmount=" + discountedAmount
				+ ", discountedAmount2=" + discountedAmount2 + ", entryDataSource=" + entryDataSource
				+ ", entryDeviceReadCount=" + entryDeviceReadCount + ", entryDeviceWriteCount=" + entryDeviceWriteCount
				+ ", entryLaneId=" + entryLaneId + ", entryPlazaId=" + entryPlazaId + ", entryTimestamp="
				+ entryTimestamp + ", entryTxSeqNumber=" + entryTxSeqNumber + ", etcAccountId=" + etcAccountId
				+ ", etcTxStatus=" + etcTxStatus + ", eventTimestamp=" + eventTimestamp + ", eventType=" + eventType
				+ ", externFileDate=" + externFileDate + ", externFileId=" + externFileId + ", forwardAxles="
				+ forwardAxles + ", imageTaken=" + imageTaken + ", isDiscountable=" + isDiscountable + ", isMedianFare="
				+ isMedianFare + ", isPeak=" + isPeak + ", isReciprocityTxn=" + isReciprocityTxn + ", isReversed="
				+ isReversed + ", laneHealth=" + laneHealth + ", laneId=" + laneId + ", laneMode=" + laneMode
				+ ", laneState=" + laneState + ", laneTxId=" + laneTxId + ", laneTxStatus=" + laneTxStatus
				+ ", laneTxType=" + laneTxType + ", laneType=" + laneType + ", planTypeId=" + planTypeId
				+ ", plateCountry=" + plateCountry + ", plateNumber=" + plateNumber + ", plateState=" + plateState
				+ ", plazaAgencyId=" + plazaAgencyId + ", plazaId=" + plazaId + ", postDeviceStatus=" + postDeviceStatus
				+ ", postClassAxles=" + postClassAxles + ", postClassClass=" + postClassClass + ", preTxnBalance="
				+ preTxnBalance + ", prevEventType=" + prevEventType + ", prevViolTxStatus=" + prevViolTxStatus
				+ ", priceScheduleId=" + priceScheduleId + ", readAviAxles=" + readAviAxles + ", speedViolFlag="
				+ speedViolFlag + ", tollRevenueType=" + tollRevenueType + ", tollSystemType=" + tollSystemType
				+ ", txDate=" + txDate + ", txExternRefNo=" + txExternRefNo + ", txModSeq=" + txModSeq
				+ ", txSeqNumber=" + txSeqNumber + ", txSubtypeInd=" + txSubtypeInd + ", txTimestamp=" + txTimestamp
				+ ", txTypeInd=" + txTypeInd + ", unrealizedAmount=" + unrealizedAmount + ", vehicleSpeed="
				+ vehicleSpeed + ", violTxStatus=" + violTxStatus + ", violType=" + violType + ", hovFlag=" + hovFlag
				+ ", videoFareAmount=" + videoFareAmount + ", etcFareAmount=" + etcFareAmount + ", cashFareAmount="
				+ cashFareAmount + ", expectedRevenueAmount=" + expectedRevenueAmount + ", postedFareAmount="
				+ postedFareAmount + ", txStatus=" + txStatus + ", itolFareAmount=" + itolFareAmount + "]";
	}
	
	
}