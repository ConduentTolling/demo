package com.conduent.tollposting.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.conduent.tollposting.constant.Constants;
import com.conduent.tollposting.constant.TxStatus;
import com.conduent.tollposting.utility.DateUtils;
import com.conduent.tollposting.utility.LocalDateAdapter;
import com.conduent.tollposting.utility.LocalDateTimeAdapter;
import com.conduent.tollposting.utility.MasterCache;
import com.conduent.tollposting.utility.OffsetDateTimeConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class EtcTransaction
{
	private static final Logger logger = LoggerFactory.getLogger(EtcTransaction.class);

	/*
	 * @Autowired private MasterCache masterCache;
	 */
	
	@Expose(serialize = true, deserialize = true)
	private Long laneTxId;

	@Expose(serialize = true, deserialize = true)
	private String txExternRefNo;

	@Expose(serialize = true, deserialize = true)
	private Long txSeqNumber;

	@Expose(serialize = true, deserialize = true)
	private Long externFileId;

	@Expose(serialize = true, deserialize = true)
	private Integer plazaAgencyId;

	@Expose(serialize = true, deserialize = true)
	private Integer plazaId;

	@Expose(serialize = true, deserialize = true)
	private Integer laneId;

	@Expose(serialize = true, deserialize = true)
	private OffsetDateTime txTimestamp;

	@Expose(serialize = true, deserialize = true)
	private Integer txModSeq;

	@Expose(serialize = true, deserialize = true)
	private String txTypeInd;

	@Expose(serialize = true, deserialize = true)
	private String txSubtypeInd;

	@Expose(serialize = true, deserialize = true)
	private Integer laneMode;

	@Expose(serialize = true, deserialize = true)
	private String laneType;

	@Expose(serialize = true, deserialize = true)
	private Integer laneState;

	@Expose(serialize = true, deserialize = true)
	private Integer laneHealth;//

	@Expose(serialize = true, deserialize = true)
	private String collectorId;

	@Expose(serialize = true, deserialize = true)
	private String tourSegmentId;

	@Expose(serialize = true, deserialize = true)
	private String entryDataSource;

	@Expose(serialize = true, deserialize = true)
	private Integer entryLaneId;

	@Expose(serialize = true, deserialize = true)
	private Integer entryPlazaId;

	@Expose(serialize = true, deserialize = true)
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
	private Double videoFareAmount;

	@Expose(serialize = true, deserialize = true)
	private Double etcFareAmount;

	@Expose(serialize = true, deserialize = true)
	private Double cashFareAmount;

	@Expose(serialize = true, deserialize = true)
	private Double expectedRevenueAmount;

	@Expose(serialize = true, deserialize = true)
	private Double postedFareAmount;

	@Expose(serialize = true, deserialize = true)
	private Double tollAmount;

	@Expose(serialize = true, deserialize = true)
	private Double discountedAmount;

	/*@Expose(serialize = true, deserialize = true)
	private Double discountedAmount1;*/

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
	private String deviceNo;

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
	private Integer deviceProgramStatus;

	@Expose(serialize = true, deserialize = true)
	private String bufferReadFlag;

	@Expose(serialize = true, deserialize = true)
	private String laneDeviceStatus;

	@Expose(serialize = true, deserialize = true)
	private Integer postDeviceStatus;

	@Expose(serialize = true, deserialize = true)
	private Integer preTxnBalance;

	@Expose(serialize = true, deserialize = true)
	private Integer planTypeId;

	@Expose(serialize = true, deserialize = true)
	private Integer txStatus;

	@Expose(serialize = true, deserialize = true)
	private Integer speedViolFlag;

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
	private String atpFileId;

	@Expose(serialize = true, deserialize = true)
	private String isReversed;

	@Expose(serialize = true, deserialize = true)
	private Integer corrReasonId;

	@Expose(serialize = true, deserialize = true)
	private LocalDate reconDate;

	@Expose(serialize = true, deserialize = true)
	private Integer reconStatusInd;

	@Expose(serialize = true, deserialize = true)
	private Integer reconSubCodeInd;

	@Expose(serialize = true, deserialize = true)
	private String externFileDate;

	@Expose(serialize = true, deserialize = true)
	private Integer mileage;

	@Expose(serialize = true, deserialize = true)
	private Integer deviceReadCount;

	@Expose(serialize = true, deserialize = true)
	private Integer deviceWriteCount;

	@Expose(serialize = true, deserialize = true)
	private Integer entryDeviceReadCount;

	@Expose(serialize = true, deserialize = true)
	private Integer entryDeviceWriteCount;

	@Expose(serialize = true, deserialize = true)
	private String depositId;

	@Expose(serialize = true, deserialize = true)
	private String txDate;

	@Expose(serialize = true, deserialize = true)
	private String cscLookupKey;

	@Expose(serialize = true, deserialize = true)
	private String updateTs;

	@Expose(serialize = true, deserialize = true)
	private String tollSystemType;

	@Expose(serialize = true, deserialize = true)
	private String aetFlag = "Y";

	@Expose(serialize = true, deserialize = true)
	private String eventTimestamp;

	@Expose(serialize = true, deserialize = true)
	private Integer eventType = 1;

	@Expose(serialize = true, deserialize = true)
	private Integer prevViolTxStatus = 0;

	@Expose(serialize = true, deserialize = true)
	private Integer violTxStatus = 3;

	@Expose(serialize = true, deserialize = true)
	private Integer violType = 3;
	
	@Expose(serialize = true, deserialize = true)
	private String lprEnabled;
	
	@Expose(serialize = true, deserialize = true)
	private String postPaidFlag;

	private String typeOfTrx;

	@Expose(serialize = true, deserialize = true)
	private Integer sourceSystem=0;

	@Expose(serialize = true, deserialize = true)
	private String isHomeAgency;

	@Expose(serialize = true, deserialize = true)
	private String transSchedPrice;

	@Expose(serialize = true, deserialize = true)
	private Integer tagClass;

	@Expose(serialize = true, deserialize = true)
	private Integer tagAxles;

	@Expose(serialize = true, deserialize = true)
	private Integer tagIagClass;

	@Expose(serialize = true, deserialize = true)
	private Integer avcClass;

	@Expose(serialize = true, deserialize = true)
	private Integer avcAxles;

	@Expose(serialize = true, deserialize = true)
	private Integer avcPreClass;

	@Expose(serialize = true, deserialize = true)
	private Integer avcPreAxles;

	@Expose(serialize = true, deserialize = true)
	private Integer etcValidStatus;

	@Expose(serialize = true, deserialize = true)
	private String imageCaptured;

	@Expose(serialize = true, deserialize = true)
	private String reciprocityTrx;

	@Expose(serialize = true, deserialize = true)
	private String debitCredit;

	@Expose(serialize = true, deserialize = true)
	private String fareType;

	@Expose(serialize = true, deserialize = true)
	private String buffer;

	@Expose(serialize = true, deserialize = true)
	private String readPerf;

	@Expose(serialize = true, deserialize = true)
	private String writePerf;

	@Expose(serialize = true, deserialize = true)
	private String rebillAccountNumber;

	@Expose(serialize = true, deserialize = true)
	private Integer fareTblId;
	
	@Expose(serialize = true, deserialize = true)
	private String receivedDate;
	//remove
	@Expose(serialize = true, deserialize = true)
	private Long extFileId;
	
	@Expose(serialize = true, deserialize = true)
    private Double itolFareAmount;
	
	//new added fields for MTA
	@Expose(serialize = true, deserialize = true)
	private String direction;
	
	@Expose(serialize = true, deserialize = true)
	private String event;
	
	@Expose(serialize = true, deserialize = true)
	private String hovFlag;
	
	@Expose(serialize = true, deserialize = true)
	private OffsetDateTime hovPresenceTime;
	
	@Expose(serialize = true, deserialize = true)
	private OffsetDateTime hovLostPresenceTime;
	
	@Expose(serialize = true, deserialize = true)
	private String avcDiscrepancyFlag;
	
	@Expose(serialize = true, deserialize = true)
	private String degraded;
	
	@Expose(serialize = true, deserialize = true)
	private String uoCode;
	
	@Expose(serialize = true, deserialize = true)
	private String avcProfile;
	
	@Expose(serialize = true, deserialize = true)
	private String ti1TagReadStatus;
	
	@Expose(serialize = true, deserialize = true)
	private String ti2AdditionalTags;
	
	@Expose(serialize = true, deserialize = true)
	private String ti3ClassMismatchFlag;
	
	@Expose(serialize = true, deserialize = true)
	private String ti4TagStatus;
	
	@Expose(serialize = true, deserialize = true)
	private String ti5PaymentFlag;
	
	@Expose(serialize = true, deserialize = true)
	private String ti6RevenueFlag;
	
	

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getHovFlag() {
		return hovFlag;
	}

	public void setHovFlag(String hovFlag) {
		this.hovFlag = hovFlag;
	}

	public OffsetDateTime getHovPresenceTime() {
		return hovPresenceTime;
	}

	public void setHovPresenceTime(OffsetDateTime hovPresenceTime) {
		this.hovPresenceTime = hovPresenceTime;
	}

	public OffsetDateTime getHovLostPresenceTime() {
		return hovLostPresenceTime;
	}

	public void setHovLostPresenceTime(OffsetDateTime hovLostPresenceTime) {
		this.hovLostPresenceTime = hovLostPresenceTime;
	}

	public String getAvcDiscrepancyFlag() {
		return avcDiscrepancyFlag;
	}

	public void setAvcDiscrepancyFlag(String avcDiscrepancyFlag) {
		this.avcDiscrepancyFlag = avcDiscrepancyFlag;
	}

	public String getDegraded() {
		return degraded;
	}

	public void setDegraded(String degraded) {
		this.degraded = degraded;
	}

	public String getUoCode() {
		return uoCode;
	}

	public void setUoCode(String uoCode) {
		this.uoCode = uoCode;
	}

	public String getAvcProfile() {
		return avcProfile;
	}

	public void setAvcProfile(String avcProfile) {
		this.avcProfile = avcProfile;
	}

	public String getTi1TagReadStatus() {
		return ti1TagReadStatus;
	}

	public void setTi1TagReadStatus(String ti1TagReadStatus) {
		this.ti1TagReadStatus = ti1TagReadStatus;
	}

	public String getTi2AdditionalTags() {
		return ti2AdditionalTags;
	}

	public void setTi2AdditionalTags(String ti2AdditionalTags) {
		this.ti2AdditionalTags = ti2AdditionalTags;
	}

	public String getTi3ClassMismatchFlag() {
		return ti3ClassMismatchFlag;
	}

	public void setTi3ClassMismatchFlag(String ti3ClassMismatchFlag) {
		this.ti3ClassMismatchFlag = ti3ClassMismatchFlag;
	}

	public String getTi4TagStatus() {
		return ti4TagStatus;
	}

	public void setTi4TagStatus(String ti4TagStatus) {
		this.ti4TagStatus = ti4TagStatus;
	}

	public String getTi5PaymentFlag() {
		return ti5PaymentFlag;
	}

	public void setTi5PaymentFlag(String ti5PaymentFlag) {
		this.ti5PaymentFlag = ti5PaymentFlag;
	}

	public String getTi6RevenueFlag() {
		return ti6RevenueFlag;
	}

	public void setTi6RevenueFlag(String ti6RevenueFlag) {
		this.ti6RevenueFlag = ti6RevenueFlag;
	}

	public Double getItolFareAmount() {
		return itolFareAmount;
	}

	public void setItolFareAmount(Double itolFareAmount) {
		this.itolFareAmount = itolFareAmount;
	}

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
		return this.externFileId;
	}

	public void setExternFileId(Long externFileId) {
		this.externFileId = externFileId;
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

	

	public OffsetDateTime getTxTimestamp() {
		return txTimestamp;
	}

	public void setTxTimestamp(OffsetDateTime txTimestamp) {
		this.txTimestamp = txTimestamp;
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

	public Integer getLaneMode() {
		return laneMode;
	}

	public void setLaneMode(Integer laneMode) {
		this.laneMode = laneMode;
	}

	public String getLaneType() {
		return laneType;
	}

	public void setLaneType(String laneType) {
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

	public String getCollectorId() {
		return collectorId;
	}

	public void setCollectorId(String collectorId) {
		this.collectorId = collectorId;
	}

	public String getTourSegmentId() {
		return tourSegmentId;
	}

	public void setTourSegmentId(String tourSegmentId) {
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

	public Double getVideoFareAmount() {
		return videoFareAmount;
	}

	public void setVideoFareAmount(Double videoFareAmount) {
		this.videoFareAmount = videoFareAmount;
	}

	public Double getEtcFareAmount() {
		return etcFareAmount;
	}

	public void setEtcFareAmount(Double etcFareAmount) {
		this.etcFareAmount = etcFareAmount;
	}

	public Double getCashFareAmount() {
		return cashFareAmount;
	}

	public void setCashFareAmount(Double cashFareAmount) {
		this.cashFareAmount = cashFareAmount;
	}

	public Double getPostedFareAmount() {
		return postedFareAmount;
	}

	public void setPostedFareAmount(Double postedFareAmount) {
		this.postedFareAmount = postedFareAmount;
	}

/*	public Double getDiscountedAmount1() {
		return discountedAmount1;
	}

	public void setDiscountedAmount1(Double discountedAmount1) {
		this.discountedAmount1 = discountedAmount1;
	}*/

	public Double getDiscountedAmount2() {
		return discountedAmount2;
	}

	public void setDiscountedAmount2(Double discountedAmount2) {
		this.discountedAmount2 = discountedAmount2;
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

	public Integer getDeviceProgramStatus() {
		return deviceProgramStatus;
	}

	public void setDeviceProgramStatus(Integer deviceProgramStatus) {
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

	public Integer getSpeedViolFlag() {
		return speedViolFlag;
	}

	public void setSpeedViolFlag(Integer speedViolFlag) {
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

	public void setReconDate(LocalDate reconDate) {
		this.reconDate = reconDate;
	}

	public String getAtpFileId() {
		return atpFileId;
	}

	public void setAtpFileId(String atpFileId) {
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

	public LocalDate getReconDate() {
		return reconDate;
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

	public String getTxDate() {
		return txDate;
	}

	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}

	public String getCscLookupKey() {
		return cscLookupKey;
	}

	public void setCscLookupKey(String cscLookupKey) {
		this.cscLookupKey = cscLookupKey;
	}

	public String getUpdateTs() {
		return updateTs;
	}

	public void setUpdateTs(String updateTs) {
		this.updateTs = updateTs;
	}

	public String getTollSystemType() {
		return tollSystemType;
	}

	public void setTollSystemType(String tollSystemType) {
		this.tollSystemType = tollSystemType;
	}

	public String getAetFlag() {
		return aetFlag;
	}

	public void setAetFlag(String aetFlag) {
		this.aetFlag = aetFlag;
	}

	public String getEventTimestamp() {
		return eventTimestamp;
	}

	public void setEventTimestamp(String eventTimestamp) {
		this.eventTimestamp = eventTimestamp;
	}

	public Integer getEventType() {
		return eventType;
	}

	public void setEventType(Integer eventType) {
		this.eventType = eventType;
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

	public Integer getViolType() {
		return violType;
	}

	public void setViolType(Integer violType) {
		this.violType = violType;
	}

	public String getTypeOfTrx() {
		return typeOfTrx;
	}

	public void setTypeOfTrx(String typeOfTrx) {
		this.typeOfTrx = typeOfTrx;
	}

	public Integer getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(Integer sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public String getIsHomeAgency() {
		return isHomeAgency;
	}

	public void setIsHomeAgency(String isHomeAgency) {
		this.isHomeAgency = isHomeAgency;
	}

	public String getTransSchedPrice() {
		return transSchedPrice;
	}

	public void setTransSchedPrice(String transSchedPrice) {
		this.transSchedPrice = transSchedPrice;
	}

	public Double getExpectedRevenueAmount() {
		return expectedRevenueAmount;
	}

	public void setExpectedRevenueAmount(Double expectedRevenueAmount) {
		this.expectedRevenueAmount = expectedRevenueAmount;
	}

	public Integer getTagClass() {
		return tagClass;
	}

	public void setTagClass(Integer tagClass) {
		this.tagClass = tagClass;
	}

	public Integer getTagAxles() {
		return tagAxles;
	}

	public void setTagAxles(Integer tagAxles) {
		this.tagAxles = tagAxles;
	}

	public Integer getTagIagClass() {
		return tagIagClass;
	}

	public void setTagIagClass(Integer tagIagClass) {
		this.tagIagClass = tagIagClass;
	}

	public Integer getAvcClass() {
		return avcClass;
	}

	public void setAvcClass(Integer avcClass) {
		this.avcClass = avcClass;
	}

	public Integer getAvcAxles() {
		return avcAxles;
	}

	public void setAvcAxles(Integer avcAxles) {
		this.avcAxles = avcAxles;
	}

	public Integer getEtcValidStatus() {
		return etcValidStatus;
	}

	public void setEtcValidStatus(Integer etcValidStatus) {
		this.etcValidStatus = etcValidStatus;
	}

	public String getImageCaptured() {
		return imageCaptured;
	}

	public void setImageCaptured(String imageCaptured) {
		this.imageCaptured = imageCaptured;
	}

	public String getReciprocityTrx() {
		return reciprocityTrx;
	}

	public void setReciprocityTrx(String reciprocityTrx) {
		this.reciprocityTrx = reciprocityTrx;
	}

	public String getDebitCredit() {
		return debitCredit;
	}

	public void setDebitCredit(String debitCredit) {
		this.debitCredit = debitCredit;
	}

	public String getFareType() {
		return fareType;
	}

	public void setFareType(String fareType) {
		this.fareType = fareType;
	}

	public Integer getAvcPreClass() {
		return avcPreClass;
	}

	public void setAvcPreClass(Integer avcPreClass) {
		this.avcPreClass = avcPreClass;
	}

	public Integer getAvcPreAxles() {
		return avcPreAxles;
	}

	public void setAvcPreAxles(Integer avcPreAxles) {
		this.avcPreAxles = avcPreAxles;
	}

	public String getBuffer() {
		return buffer;
	}

	public void setBuffer(String buffer) {
		this.buffer = buffer;
	}

	public String getReadPerf() {
		return readPerf;
	}

	public void setReadPerf(String readPerf) {
		this.readPerf = readPerf;
	}

	public String getWritePerf() {
		return writePerf;
	}

	public void setWritePerf(String writePerf) {
		this.writePerf = writePerf;
	}

	public String getRebillAccountNumber() {
		return rebillAccountNumber;
	}

	public void setRebillAccountNumber(String rebillAccountNumber) {
		this.rebillAccountNumber = rebillAccountNumber;
	}

	public Integer getFareTblId() {
		return fareTblId;
	}

	public void setFareTblId(Integer fareTblId) {
		this.fareTblId = fareTblId;
	}

	public String getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Double getFullFareAmount() {
		return fullFareAmount;
	}

	public void setFullFareAmount(Double fullFareAmount) {
		this.fullFareAmount = fullFareAmount;
	}

	public Double getTollAmount() {
		return tollAmount;
	}

	public void setTollAmount(Double tollAmount) {
		this.tollAmount = tollAmount;
	}

	public Double getDiscountedAmount() {
		return discountedAmount;
	}

	public void setDiscountedAmount(Double discountedAmount) {
		this.discountedAmount = discountedAmount;
	}

	public String getLprEnabled() {
		return lprEnabled;
	}

	public void setLprEnabled(String lprEnabled) {
		this.lprEnabled = lprEnabled;
	}

	
	public String getPostPaidFlag() {
		return postPaidFlag;
	}

	public void setPostPaidFlag(String postPaidFlag) {
		this.postPaidFlag = postPaidFlag;
	}

	public Long getExtFileId() {
		return extFileId;
	}

	public void setExtFileId(Long extFileId) {
		this.extFileId = extFileId;
	}

	public static EtcTransaction dtoFromJson(String msg)
	{
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeConverter(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.excludeFieldsWithoutExposeAnnotation().create();
		return gson.fromJson(msg, EtcTransaction.class);
	}

	public boolean validateRejectionTransaction(EtcTransaction etcTransaction, MasterCache masterCache)
	{
		logger.info("Validating record for laneTxId {} ",etcTransaction.getLaneTxId());
		try
		{
			etcTransaction.setTypeOfTrx("TOLL_TXN");
			if(etcTransaction.getLaneTxId()==null || etcTransaction.getLaneTxId()<=0)
			{
				logger.info("laneTxId is not valid for for {} ",etcTransaction.getLaneTxId());
				return false;
			}
			if("D".contains(etcTransaction.getTxTypeInd()) && "E".contains(etcTransaction.getTxSubtypeInd()))
			{
				logger.info("TxType is expired/matching entry (DISCARDED) for laneTxId {}",etcTransaction.getLaneId());
				etcTransaction.setTollSystemType("E/X");
				return false;
			}
			if(etcTransaction.getTxTypeInd()==null || !"EVIPCMXUR".contains(etcTransaction.getTxTypeInd()))
			{
				logger.info("TxType is wrong for laneTxId {} calling exception API with txStatus {}",etcTransaction.getLaneId(),TxStatus.TX_STATUS_QINVTRX);
				etcTransaction.setTxStatus(TxStatus.TX_STATUS_QINVTRX.getCode());
				return false;
			}
			else
			{   logger.info("Setting txTypeInd:{}",etcTransaction.getTxTypeInd());
				etcTransaction.setTxTypeInd(etcTransaction.getTxTypeInd());
			}
			if(etcTransaction.getTxSubtypeInd()!=null && !"*0".contains(etcTransaction.getTxSubtypeInd()))
			{  logger.info("Setting txSubtypeInd:{}",etcTransaction.getTxSubtypeInd());
				etcTransaction.setTxSubtypeInd(etcTransaction.getTxSubtypeInd());
			}
			else
			{
				logger.info("TxSubType is wrong for laneTxId {} calling exception API ",etcTransaction.getLaneTxId());
				etcTransaction.setTxStatus(TxStatus.TX_STATUS_QINVTRX.getCode());
				return false;
			}
			if(etcTransaction.getTxTypeInd().equals("R"))
			{
				if(etcTransaction.getTxSubtypeInd().equals("D"))
				{
					if(etcTransaction.getTxTimestamp()==null)
					{
						//etcTransaction.setTxTimestamp("1858-11-17 00:00:00.000");
					}
					if(DateUtils.parseLocalDate(etcTransaction.getTxDate(), Constants.dateFormator)==null)
					{
						etcTransaction.setTxDate("1858-11-17");
					}
				}
				etcTransaction.setEtcAccountId(masterCache.getSystemAccount(etcTransaction.getPlazaAgencyId()));
				//etcTransaction.setAccountAgencyId(1);
				etcTransaction.setAccountAgencyId(etcTransaction.getPlazaAgencyId());		//changed due to bug 17031
				logger.info("TxType is R for laneTxId {} calling exception API...",etcTransaction.getLaneTxId());
				return false;
			}
			if(etcTransaction.getTollSystemType()==null || 	etcTransaction.getTollSystemType().equals("*"))
			{
				etcTransaction.setTollSystemType("ETC");
			}
			else
			{
				etcTransaction.setTollSystemType(etcTransaction.getTollSystemType());
			}
			etcTransaction.setTollRevenueType(etcTransaction.getTollRevenueType()==null?0:etcTransaction.getTollRevenueType());
			etcTransaction.setAccountType(etcTransaction.getAccountType()==null?0:etcTransaction.getAccountType());
			etcTransaction.setEntryLaneId(etcTransaction.getEntryLaneId()==null?0:etcTransaction.getEntryLaneId());
			etcTransaction.setEntryPlazaId(etcTransaction.getEntryPlazaId()==null?0:etcTransaction.getEntryPlazaId());
			
			//if(DateUtils.parseLocalDateTime(txTimestamp, Constants.dateTimeFormator)==null)
			if(etcTransaction.getTxTimestamp()==null)
			{
				logger.info("txTimestamp is not valid for laneTxId {} calling exception API with txStatus {}",etcTransaction.getLaneId(),TxStatus.TX_STATUS_QINVXDATE);
				etcTransaction.setTxStatus(TxStatus.TX_STATUS_QINVXDATE.getCode());
				return false;
			}
			if(DateUtils.parseLocalDate(etcTransaction.getTxDate(), Constants.dateFormator)==null)
			{
				logger.info("txDate is not valid for laneTxId {} calling exception API with txStatus {}",etcTransaction.getLaneId(),TxStatus.TX_STATUS_QINVXDATE);
				etcTransaction.setTxStatus(TxStatus.TX_STATUS_QINVXDATE.getCode());
				return false;
			}
			etcTransaction.setEntryVehicleSpeed(etcTransaction.getEntryVehicleSpeed()==null?0:etcTransaction.getEntryVehicleSpeed());
			etcTransaction.setLaneMode(etcTransaction.getLaneMode()==null?0:etcTransaction.getLaneMode());
			etcTransaction.setLaneType(etcTransaction.getLaneType()==null || etcTransaction.getLaneType().equals("*")?"0":etcTransaction.getLaneType());
			etcTransaction.setLaneState(etcTransaction.getLaneState()==null?0:etcTransaction.getLaneState());
			etcTransaction.setCollectorId(etcTransaction.getCollectorId()==null || etcTransaction.getCollectorId().equals("*")?"0":etcTransaction.getCollectorId());
			etcTransaction.setActualClass(etcTransaction.getActualClass()==null?0:etcTransaction.getActualClass());
			etcTransaction.setActualAxles(etcTransaction.getActualAxles()==null?0:etcTransaction.getActualAxles());
			etcTransaction.setActualExtraAxles(etcTransaction.getActualExtraAxles()==null?0:etcTransaction.getActualExtraAxles());
			etcTransaction.setTagClass(etcTransaction.getTagClass()==null?0:etcTransaction.getTagClass());
			etcTransaction.setTagAxles(etcTransaction.getTagAxles()==null?0:etcTransaction.getTagAxles());
			etcTransaction.setTagIagClass(etcTransaction.getTagIagClass()==null?0:etcTransaction.getTagIagClass());
			etcTransaction.setAvcClass(etcTransaction.getAvcClass()==null?0:etcTransaction.getAvcClass());
			etcTransaction.setAvcAxles(etcTransaction.getAvcAxles()==null?0:etcTransaction.getAvcAxles());
			etcTransaction.setAvcPreAxles(0);
			etcTransaction.setAvcPreClass(0);
			etcTransaction.setIsDiscountable("T");
			etcTransaction.setCorrReasonId(etcTransaction.getCorrReasonId()==null?0:etcTransaction.getCorrReasonId());
			etcTransaction.setEtcValidStatus(etcTransaction.getEtcValidStatus()==null?0:etcTransaction.getEtcValidStatus());
			etcTransaction.setReadPerf(etcTransaction.getReadPerf()==null || etcTransaction.getReadPerf().equals("*")?"0":etcTransaction.getReadPerf());
			etcTransaction.setWritePerf(etcTransaction.getWritePerf()==null || etcTransaction.getWritePerf().equals("*")?"0":etcTransaction.getWritePerf());
			etcTransaction.setDeviceProgramStatus(etcTransaction.getDeviceProgramStatus()==null?0:etcTransaction.getDeviceProgramStatus());	
			etcTransaction.setVehicleSpeed(etcTransaction.getVehicleSpeed()==null?0:etcTransaction.getVehicleSpeed());
			etcTransaction.setLaneId(etcTransaction.getLaneId()==null?0:etcTransaction.getLaneId());
			etcTransaction.setEtcAccountId(etcTransaction.getEtcAccountId()==null?0:etcTransaction.getEtcAccountId());
			etcTransaction.setExpectedRevenueAmount(etcTransaction.getExpectedRevenueAmount()==null?0:etcTransaction.getExpectedRevenueAmount());
			etcTransaction.setIsPeak((etcTransaction.getIsPeak() ==null || etcTransaction.getIsPeak().equals("*") || etcTransaction.getIsPeak().equals(" ")?"F":etcTransaction.getIsPeak()));
			etcTransaction.setIsMedianFare("N"); /**Normal fare*/
			if(etcTransaction.getFareType()!=null)
			{
				if(etcTransaction.getFareType().equals("0"))
				{
					etcTransaction.setIsMedianFare("N"); /**Normal fare*/
				}
				else if(etcTransaction.getFareType().equals("1"))
				{
					etcTransaction.setIsMedianFare("M"); /**Median fare*/
				}
				else if(etcTransaction.getFareType().equals("2"))
				{
					etcTransaction.setIsMedianFare("X"); /**Max fare*/
				}
			}
			etcTransaction.setVideoFareAmount(etcTransaction.getVideoFareAmount() ==null?0:etcTransaction.getVideoFareAmount());
			etcTransaction.setEtcFareAmount(etcTransaction.getEtcFareAmount()==null?0:etcTransaction.getEtcFareAmount());
			etcTransaction.setCashFareAmount(etcTransaction.getCashFareAmount()==null?0:etcTransaction.getCashFareAmount());
			etcTransaction.setTxExternRefNo((etcTransaction.getTxExternRefNo()==null || etcTransaction.getTxExternRefNo().equals("*"))?"0":etcTransaction.getTxExternRefNo());	   
			etcTransaction.setAtpFileId((etcTransaction.getAtpFileId()==null || etcTransaction.getAtpFileId().equals("*"))?"0":etcTransaction.getAtpFileId());
			etcTransaction.setExternFileId(etcTransaction.getExternFileId()==null?0:etcTransaction.getExternFileId());
			etcTransaction.setPlateNumber(etcTransaction.getPlateNumber()==null?"":etcTransaction.getPlateNumber());
			etcTransaction.setDiscountedAmount(etcTransaction.getDiscountedAmount()==null?0.0:etcTransaction.getDiscountedAmount());
			etcTransaction.setDiscountedAmount2(etcTransaction.getDiscountedAmount2()==null?0.0:etcTransaction.getDiscountedAmount2());
			if(etcTransaction.getTxTypeInd()!=null)
			{
				if(etcTransaction.getTxTypeInd().equalsIgnoreCase("C"))
				{
					etcTransaction.setTypeOfTrx("CLASS_MISMATCH_TXN");
				}
				else if(etcTransaction.getTxTypeInd().equalsIgnoreCase("P"))
				{
					etcTransaction.setTypeOfTrx("PARKING_TXN");
				}
				else
				{
					etcTransaction.setTypeOfTrx("TOLL_TXN");
				}
			}
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			logger.info("Exception while validating {}  for {} ",ex.getMessage(),etcTransaction);
		}
		return false;
	}

	@Override
	public String toString() {
		return "EtcTransaction [laneTxId=" + laneTxId + ", txExternRefNo=" + txExternRefNo + ", txSeqNumber="
				+ txSeqNumber + ", externFileId=" + externFileId + ", plazaAgencyId=" + plazaAgencyId + ", plazaId="
				+ plazaId + ", laneId=" + laneId + ", txTimestamp=" + txTimestamp + ", txModSeq=" + txModSeq
				+ ", txTypeInd=" + txTypeInd + ", txSubtypeInd=" + txSubtypeInd + ", laneMode=" + laneMode
				+ ", laneType=" + laneType + ", laneState=" + laneState + ", laneHealth=" + laneHealth
				+ ", collectorId=" + collectorId + ", tourSegmentId=" + tourSegmentId + ", entryDataSource="
				+ entryDataSource + ", entryLaneId=" + entryLaneId + ", entryPlazaId=" + entryPlazaId
				+ ", entryTimestamp=" + entryTimestamp + ", entryTxSeqNumber=" + entryTxSeqNumber
				+ ", entryVehicleSpeed=" + entryVehicleSpeed + ", laneTxStatus=" + laneTxStatus + ", lanetxType="
				+ lanetxType + ", tollRevenueType=" + tollRevenueType + ", actualClass=" + actualClass
				+ ", actualAxles=" + actualAxles + ", actualExtraAxles=" + actualExtraAxles + ", collectorClass="
				+ collectorClass + ", collectorAxles=" + collectorAxles + ", preClassClass=" + preClassClass
				+ ", preClassAxles=" + preClassAxles + ", postClassClass=" + postClassClass + ", postClassAxles="
				+ postClassAxles + ", forwardAxles=" + forwardAxles + ", reverseAxles=" + reverseAxles
				+ ", fullFareAmount=" + fullFareAmount + ", videoFareAmount=" + videoFareAmount + ", etcFareAmount="
				+ etcFareAmount + ", cashFareAmount=" + cashFareAmount + ", expectedRevenueAmount="
				+ expectedRevenueAmount + ", postedFareAmount=" + postedFareAmount + ", tollAmount=" + tollAmount
				+ ", discountedAmount=" + discountedAmount + ", discountedAmount2=" + discountedAmount2
				+ ", collectedAmount=" + collectedAmount + ", unrealizedAmount=" + unrealizedAmount
				+ ", isDiscountable=" + isDiscountable + ", isMedianFare=" + isMedianFare + ", isPeak=" + isPeak
				+ ", priceScheduleId=" + priceScheduleId + ", vehicleSpeed=" + vehicleSpeed + ", receiptIssued="
				+ receiptIssued + ", deviceNo=" + deviceNo + ", accountType=" + accountType + ", deviceCodedClass="
				+ deviceCodedClass + ", deviceAgencyClass=" + deviceAgencyClass + ", deviceIagClass=" + deviceIagClass
				+ ", deviceAxles=" + deviceAxles + ", etcAccountId=" + etcAccountId + ", accountAgencyId="
				+ accountAgencyId + ", readAviClass=" + readAviClass + ", readAviAxles=" + readAviAxles
				+ ", deviceProgramStatus=" + deviceProgramStatus + ", bufferReadFlag=" + bufferReadFlag
				+ ", laneDeviceStatus=" + laneDeviceStatus + ", postDeviceStatus=" + postDeviceStatus
				+ ", preTxnBalance=" + preTxnBalance + ", planTypeId=" + planTypeId + ", txStatus=" + txStatus
				+ ", speedViolFlag=" + speedViolFlag + ", imageTaken=" + imageTaken + ", plateCountry=" + plateCountry
				+ ", plateState=" + plateState + ", plateNumber=" + plateNumber + ", revenueDate=" + revenueDate
				+ ", postedDate=" + postedDate + ", atpFileId=" + atpFileId + ", isReversed=" + isReversed
				+ ", corrReasonId=" + corrReasonId + ", reconDate=" + reconDate + ", reconStatusInd=" + reconStatusInd
				+ ", reconSubCodeInd=" + reconSubCodeInd + ", externFileDate=" + externFileDate + ", mileage=" + mileage
				+ ", deviceReadCount=" + deviceReadCount + ", deviceWriteCount=" + deviceWriteCount
				+ ", entryDeviceReadCount=" + entryDeviceReadCount + ", entryDeviceWriteCount=" + entryDeviceWriteCount
				+ ", depositId=" + depositId + ", txDate=" + txDate + ", cscLookupKey=" + cscLookupKey + ", updateTs="
				+ updateTs + ", tollSystemType=" + tollSystemType + ", aetFlag=" + aetFlag + ", eventTimestamp="
				+ eventTimestamp + ", eventType=" + eventType + ", prevViolTxStatus=" + prevViolTxStatus
				+ ", violTxStatus=" + violTxStatus + ", violType=" + violType + ", lprEnabled=" + lprEnabled
				+ ", postPaidFlag=" + postPaidFlag + ", typeOfTrx=" + typeOfTrx + ", sourceSystem=" + sourceSystem
				+ ", isHomeAgency=" + isHomeAgency + ", transSchedPrice=" + transSchedPrice + ", tagClass=" + tagClass
				+ ", tagAxles=" + tagAxles + ", tagIagClass=" + tagIagClass + ", avcClass=" + avcClass + ", avcAxles="
				+ avcAxles + ", avcPreClass=" + avcPreClass + ", avcPreAxles=" + avcPreAxles + ", etcValidStatus="
				+ etcValidStatus + ", imageCaptured=" + imageCaptured + ", reciprocityTrx=" + reciprocityTrx
				+ ", debitCredit=" + debitCredit + ", fareType=" + fareType + ", buffer=" + buffer + ", readPerf="
				+ readPerf + ", writePerf=" + writePerf + ", rebillAccountNumber=" + rebillAccountNumber
				+ ", fareTblId=" + fareTblId + ", receivedDate=" + receivedDate + ", extFileId=" + extFileId
				+ ", itolFareAmount=" + itolFareAmount + ", direction=" + direction + ", event=" + event + ", hovFlag="
				+ hovFlag + ", hovPresenceTime=" + hovPresenceTime + ", hovLostPresenceTime=" + hovLostPresenceTime
				+ ", avcDiscrepancyFlag=" + avcDiscrepancyFlag + ", degraded=" + degraded + ", uoCode=" + uoCode
				+ ", avcProfile=" + avcProfile + ", ti1TagReadStatus=" + ti1TagReadStatus + ", ti2AdditionalTags="
				+ ti2AdditionalTags + ", ti3ClassMismatchFlag=" + ti3ClassMismatchFlag + ", ti4TagStatus="
				+ ti4TagStatus + ", ti5PaymentFlag=" + ti5PaymentFlag + ", ti6RevenueFlag=" + ti6RevenueFlag + "]";
	}

	
	
}

