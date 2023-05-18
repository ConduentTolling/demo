package com.conduent.tpms.iag.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import com.google.gson.annotations.Expose;

public class TranDetail 
{	
	@Expose(serialize = true, deserialize = true)
	private Long laneTxId =0l;
	@Expose(serialize = true, deserialize = true)
	private String txExternRefNo=null;
	@Expose(serialize = true, deserialize = true)
	private String txType=null;
	@Expose(serialize = true, deserialize = true)
	private String txSubType=null;
	@Expose(serialize = true, deserialize = true)
	private String txTypeInd=null;
	@Expose(serialize = true, deserialize = true)
	private String txSubtypeInd=null;
	@Expose(serialize = true, deserialize = true)
	private String tollSystemType=null;
	@Expose(serialize = true, deserialize = true)
	private Integer tollRevenueType=0;
	@Expose(serialize = true, deserialize = true)
	private OffsetDateTime entryTxTimeStamp;
	@Expose(serialize = true, deserialize = true)
	private Integer entryPlazaId=0;
	@Expose(serialize = true, deserialize = true)
	private Integer entryLaneId=0;
	@Expose(serialize = true, deserialize = true)
	private Long entryTxSeqNumber=0l;
	@Expose(serialize = true, deserialize = true)
	private String	entryDataSource=null;;
	@Expose(serialize = true, deserialize = true)
	private Integer entryVehicleSpeed=0;
	@Expose(serialize = true, deserialize = true)
	private Integer plazaAgencyId=0;
	@Expose(serialize = true, deserialize = true)
	private Integer plazaId=0;
	@Expose(serialize = true, deserialize = true)
	private Long laneId=0l;
	@Expose(serialize = true, deserialize = true)
	private String externPlazaId=null;
	@Expose(serialize = true, deserialize = true)
	private String externLaneId=null;
	@Expose(serialize = true, deserialize = true)
	private String txDate=null;
	@Expose(serialize = true, deserialize = true)
	private OffsetDateTime txTimestamp;
	@Expose(serialize = true, deserialize = true)
	private Integer laneMode=0;
	@Expose(serialize = true, deserialize = true)
	private Integer laneState=0;
	@Expose(serialize = true, deserialize = true)
	private Long laneSn=0l;
	@Expose(serialize = true, deserialize = true)
	private String deviceNo=null;
	@Expose(serialize = true, deserialize = true)
	private Integer deviceCodedClass=0;        
	@Expose(serialize = true, deserialize = true)
	private Long deviceIagClass=0l;
	@Expose(serialize = true, deserialize = true)
	private Long deviceAgencyClass=0l;
	@Expose(serialize = true, deserialize = true)
	private Integer tagAxles=0;
	@Expose(serialize = true, deserialize = true)
	private Integer actualClass=0;
	@Expose(serialize = true, deserialize = true)
	private Integer actualAxles=0;
	@Expose(serialize = true, deserialize = true)
	private Integer extraAxles=0;
	@Expose(serialize = true, deserialize = true)
	private String plateState=null;
	@Expose(serialize = true, deserialize = true)
	private String plateNumber=null;
	@Expose(serialize = true, deserialize = true)
	private String plateType=null;        
	@Expose(serialize = true, deserialize = true)
	private String plateCountry=null;
	@Expose(serialize = true, deserialize = true)
	private Integer readPerf=0;
	@Expose(serialize = true, deserialize = true)
	private Integer writePerf=0;
	@Expose(serialize = true, deserialize = true)
	private Integer progStat;
	@Expose(serialize = true, deserialize = true)
	private String collectorNumber=null;  
	@Expose(serialize = true, deserialize = true)
	private String imageCaptured=null;
	@Expose(serialize = true, deserialize = true)
	private Integer vehicleSpeed=0;
	@Expose(serialize = true, deserialize = true)
	private Integer speedViolation=0;
	@Expose(serialize = true, deserialize = true)
	private String reciprocityTrx=null;
	@Expose(serialize = true, deserialize = true)
	private String isViolation=null;
	@Expose(serialize = true, deserialize = true)
	private String isLprEnabled=null;
	@Expose(serialize = true, deserialize = true)
	private Double fullFareAmount=0.00;
	@Expose(serialize = true, deserialize = true)
	private Double discountedAmount=0.00;
	@Expose(serialize = true, deserialize = true)
	private Double unrealizedAmount=0.00;
	@Expose(serialize = true, deserialize = true)
	private Long externFileId=0l;
	@Expose(serialize = true, deserialize = true)
	private LocalDate receivedDate;
	@Expose(serialize = true, deserialize = true)
	private Integer accountType=0;
	@Expose(serialize = true, deserialize = true)
	private Integer accountAgencyId=0;
	@Expose(serialize = true, deserialize = true)
	private Long etcAccountId=0l;
	@Expose(serialize = true, deserialize = true)
	private Long etcSubAccount=0l;
	@Expose(serialize = true, deserialize = true)
	private String dstFlag=null;
	@Expose(serialize = true, deserialize = true)
	private String isPeak=null;
	@Expose(serialize = true, deserialize = true)
	private Integer fareType=0;
	@Expose(serialize = true, deserialize = true)
	private LocalDateTime updateTs;
	@Expose(serialize = true, deserialize = true)
	private String laneDataSource=null;
	@Expose(serialize = true, deserialize = true)
	private Long laneType=0l;    
	@Expose(serialize = true, deserialize = true)
	private Long laneHealth=0l;
	@Expose(serialize = true, deserialize = true)
	private Integer avcAxles=0;
	@Expose(serialize = true, deserialize = true)
	private Long tourSegment=0l;
	@Expose(serialize = true, deserialize = true)
	private String bufRead=null;
	@Expose(serialize = true, deserialize = true)
	private String readerId=null;
	@Expose(serialize = true, deserialize = true)
	private Double tollAmount=0.00;
	@Expose(serialize = true, deserialize = true)
	private String debitCredit=null;
	@Expose(serialize = true, deserialize = true)
	private Long etcValidStatus=0l;
	@Expose(serialize = true, deserialize = true)
	private Double discountedAmount2=0.00;
	@Expose(serialize = true, deserialize = true)
	private Double videoFareAmount=0.00;
	@Expose(serialize = true, deserialize = true)
	private Long planType=0l;     
	@Expose(serialize = true, deserialize = true)
	private String reserved=null;
	@Expose(serialize = true, deserialize = true)
	private Long atpFileId=0l; 
	@Expose(serialize = true, deserialize = true)
	private Double expectedRevenueAmount=0.00;
	@Expose(serialize = true, deserialize = true)
	private Double cashFareAmount=0.00;
	@Expose(serialize = true, deserialize = true)
	private Double postedFareAmount=0.00;
	@Expose(serialize = true, deserialize = true)
	private Double etcFareAmount=0.00;
	@Expose(serialize = true, deserialize = true)
	private String corrReasonId=null;
	@Expose(serialize = true, deserialize = true)
	private String transactionInfo=null;
	@Expose(serialize = true, deserialize = true)
	private String planCharged=null;
	@Expose(serialize = true, deserialize = true)
	private String txStatus=null;
	@Expose(serialize = true, deserialize = true)
	private LocalDate postedDate=null;
	@Expose(serialize = true, deserialize = true)
	private String depositId=null;
	private String  frontOcrPlateCountry;
	private String  frontOcrPlateState;
	private String  frontOcrPlateType;
	private String  frontOcrPlateNumber;
	private String  frontOcrConfidence;
	private String  frontOcrImageIndex;
	private String  frontImageColor;
	private String  rearOcrPlateCountry;
	private String  rearOcrPlateState;
	private String  rearOcrPlateType;
	private String  rearOcrPlateNumber;
	private String  rearOcrConfidence;
	private String  rearOcrImageIndex;
	private String  rearImageColor;
	private String deviceNoFromDb;
	
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
	public String getTxType() {
		return txType;
	}
	public void setTxType(String txType) {
		this.txType = txType;
	}
	public String getTxSubType() {
		return txSubType;
	}
	public void setTxSubType(String txSubType) {
		this.txSubType = txSubType;
	}
	public String getTollSystemType() {
		return tollSystemType;
	}
	public void setTollSystemType(String tollSystemType) {
		this.tollSystemType = tollSystemType;
	}
	public Integer getTollRevenueType() {
		return tollRevenueType;
	}
	public void setTollRevenueType(Integer tollRevenueType) {
		this.tollRevenueType = tollRevenueType;
	}
	public OffsetDateTime getEntryTxTimeStamp() {
		return entryTxTimeStamp;
	}
	public void setEntryTxTimeStamp(OffsetDateTime entryTxTimeStamp) {
		this.entryTxTimeStamp = entryTxTimeStamp;
	}
	public Integer getEntryPlazaId() {
		return entryPlazaId;
	}
	public void setEntryPlazaId(Integer entryPlazaId) {
		this.entryPlazaId = entryPlazaId;
	}
	public Integer getEntryLaneId() {
		return entryLaneId;
	}
	public void setEntryLaneId(Integer entryLaneId) {
		this.entryLaneId = entryLaneId;
	}
	public Long getEntryTxSeqNumber() {
		return entryTxSeqNumber;
	}
	public void setEntryTxSeqNumber(Long entryTxSeqNumber) {
		this.entryTxSeqNumber = entryTxSeqNumber;
	}
	public String getEntryDataSource() {
		return entryDataSource;
	}
	public void setEntryDataSource(String entryDataSource) {
		this.entryDataSource = entryDataSource;
	}
	public Integer getEntryVehicleSpeed() {
		return entryVehicleSpeed;
	}
	public void setEntryVehicleSpeed(Integer entryVehicleSpeed) {
		this.entryVehicleSpeed = entryVehicleSpeed;
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
	public Long getLaneId() {
		return laneId;
	}
	public void setLaneId(Long laneId) {
		this.laneId = laneId;
	}
	public String getExternPlazaId() {
		return externPlazaId;
	}
	public void setExternPlazaId(String externPlazaId) {
		this.externPlazaId = externPlazaId;
	}
	public String getExternLaneId() {
		return externLaneId;
	}
	public void setExternLaneId(String externLaneId) {
		this.externLaneId = externLaneId;
	}
	public String getTxDate() {
		return txDate;
	}
	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}
	public OffsetDateTime getTxTimestamp() {
		return txTimestamp;
	}
	public void setTxTimestamp(OffsetDateTime txTimeStamp) {
		this.txTimestamp = txTimeStamp;
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
	public Long getLaneSn() {
		return laneSn;
	}
	public void setLaneSn(Long laneSn) {
		this.laneSn = laneSn;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public Integer getDeviceCodedClass() {
		return deviceCodedClass;
	}
	public void setDeviceCodedClass(Integer deviceCodedClass) {
		this.deviceCodedClass = deviceCodedClass;
	}
	public Long getDeviceIagClass() {
		return deviceIagClass;
	}
	public void setDeviceIagClass(Long deviceIagClass) {
		this.deviceIagClass = deviceIagClass;
	}
	public Long getDeviceAgencyClass() {
		return deviceAgencyClass;
	}
	public void setDeviceAgencyClass(Long deviceAgencyClass) {
		this.deviceAgencyClass = deviceAgencyClass;
	}
	public Integer getTagAxles() {
		return tagAxles;
	}
	public void setTagAxles(Integer tagAxles) {
		this.tagAxles = tagAxles;
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
	public Integer getExtraAxles() {
		return extraAxles;
	}
	public void setExtraAxles(Integer extraAxles) {
		this.extraAxles = extraAxles;
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
	public String getPlateType() {
		return plateType;
	}
	public void setPlateType(String plateType) {
		this.plateType = plateType;
	}
	public String getPlateCountry() {
		return plateCountry;
	}
	public void setPlateCountry(String plateCountry) {
		this.plateCountry = plateCountry;
	}
	public Integer getReadPerf() {
		return readPerf;
	}
	public void setReadPerf(Integer readPerf) {
		this.readPerf = readPerf;
	}
	public Integer getWritePerf() {
		return writePerf;
	}
	public void setWritePerf(Integer writePerf) {
		this.writePerf = writePerf;
	}
	public Integer getProgStat() {
		return progStat;
	}
	public void setProgStat(Integer progStat) {
		this.progStat = progStat;
	}
	public String getCollectorNumber() {
		return collectorNumber;
	}
	public void setCollectorNumber(String collectorNumber) {
		this.collectorNumber = collectorNumber;
	}
	public String getImageCaptured() {
		return imageCaptured;
	}
	public void setImageCaptured(String imageCaptured) {
		this.imageCaptured = imageCaptured;
	}
	public Integer getVehicleSpeed() {
		return vehicleSpeed;
	}
	public void setVehicleSpeed(Integer vehicleSpeed) {
		this.vehicleSpeed = vehicleSpeed;
	}
	public Integer getSpeedViolation() {
		return speedViolation;
	}
	public void setSpeedViolation(Integer speedViolation) {
		this.speedViolation = speedViolation;
	}
	public String getReciprocityTrx() {
		return reciprocityTrx;
	}
	public void setReciprocityTrx(String reciprocityTrx) {
		this.reciprocityTrx = reciprocityTrx;
	}
	public String getIsViolation() {
		return isViolation;
	}
	public void setIsViolation(String isViolation) {
		this.isViolation = isViolation;
	}
	public String getIsLprEnabled() {
		return isLprEnabled;
	}
	public void setIsLprEnabled(String isLprEnabled) {
		this.isLprEnabled = isLprEnabled;
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
	public Double getUnrealizedAmount() {
		return unrealizedAmount;
	}
	public void setUnrealizedAmount(Double unrealizedAmount) {
		this.unrealizedAmount = unrealizedAmount;
	}
	public Long getExternFileId() {
		return externFileId;
	}
	public void setExternFileId(Long externFileId) {
		this.externFileId = externFileId;
	}
	
	public LocalDate getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(LocalDate receivedDate) {
		this.receivedDate = receivedDate;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public Integer getAccountAgencyId() {
		return accountAgencyId;
	}
	public void setAccountAgencyId(Integer accountAgencyId) {
		this.accountAgencyId = accountAgencyId;
	}
	public Long getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(Long etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	public Long getEtcSubAccount() {
		return etcSubAccount;
	}
	public void setEtcSubAccount(Long etcSubAccount) {
		this.etcSubAccount = etcSubAccount;
	}
	public String getDstFlag() {
		return dstFlag;
	}
	public void setDstFlag(String dstFlag) {
		this.dstFlag = dstFlag;
	}
	public String getIsPeak() {
		return isPeak;
	}
	public void setIsPeak(String isPeak) {
		this.isPeak = isPeak;
	}
	public Integer getFareType() {
		return fareType;
	}
	public void setFareType(Integer fareType) {
		this.fareType = fareType;
	}
	public LocalDateTime getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}
	
	public String getFrontOcrPlateCountry() {
		return frontOcrPlateCountry;
	}
	public void setFrontOcrPlateCountry(String frontOcrPlateCountry) {
		this.frontOcrPlateCountry = frontOcrPlateCountry;
	}
	public String getFrontOcrPlateState() {
		return frontOcrPlateState;
	}
	public void setFrontOcrPlateState(String frontOcrPlateState) {
		this.frontOcrPlateState = frontOcrPlateState;
	}
	public String getFrontOcrPlateType() {
		return frontOcrPlateType;
	}
	public void setFrontOcrPlateType(String frontOcrPlateType) {
		this.frontOcrPlateType = frontOcrPlateType;
	}
	public String getFrontOcrPlateNumber() {
		return frontOcrPlateNumber;
	}
	public void setFrontOcrPlateNumber(String frontOcrPlateNumber) {
		this.frontOcrPlateNumber = frontOcrPlateNumber;
	}
	public String getFrontOcrConfidence() {
		return frontOcrConfidence;
	}
	public void setFrontOcrConfidence(String frontOcrConfidence) {
		this.frontOcrConfidence = frontOcrConfidence;
	}
	public String getFrontOcrImageIndex() {
		return frontOcrImageIndex;
	}
	public void setFrontOcrImageIndex(String frontOcrImageIndex) {
		this.frontOcrImageIndex = frontOcrImageIndex;
	}
	public String getFrontImageColor() {
		return frontImageColor;
	}
	public void setFrontImageColor(String frontImageColor) {
		this.frontImageColor = frontImageColor;
	}
	public String getRearOcrPlateCountry() {
		return rearOcrPlateCountry;
	}
	public void setRearOcrPlateCountry(String rearOcrPlateCountry) {
		this.rearOcrPlateCountry = rearOcrPlateCountry;
	}
	public String getRearOcrPlateState() {
		return rearOcrPlateState;
	}
	public void setRearOcrPlateState(String rearOcrPlateState) {
		this.rearOcrPlateState = rearOcrPlateState;
	}
	public String getRearOcrPlateType() {
		return rearOcrPlateType;
	}
	public void setRearOcrPlateType(String rearOcrPlateType) {
		this.rearOcrPlateType = rearOcrPlateType;
	}
	public String getRearOcrPlateNumber() {
		return rearOcrPlateNumber;
	}
	public void setRearOcrPlateNumber(String rearOcrPlateNumber) {
		this.rearOcrPlateNumber = rearOcrPlateNumber;
	}
	public String getRearOcrConfidence() {
		return rearOcrConfidence;
	}
	public void setRearOcrConfidence(String rearOcrConfidence) {
		this.rearOcrConfidence = rearOcrConfidence;
	}
	public String getRearOcrImageIndex() {
		return rearOcrImageIndex;
	}
	public void setRearOcrImageIndex(String rearOcrImageIndex) {
		this.rearOcrImageIndex = rearOcrImageIndex;
	}
	public String getRearImageColor() {
		return rearImageColor;
	}
	public void setRearImageColor(String rearImageColor) {
		this.rearImageColor = rearImageColor;
	}
	
	public String getLaneDataSource() {
		return laneDataSource;
	}
	public void setLaneDataSource(String laneDataSource) {
		this.laneDataSource = laneDataSource;
	}
	public Long getLaneType() {
		return laneType;
	}
	public void setLaneType(Long laneType) {
		this.laneType = laneType;
	}
	public Long getLaneHealth() {
		return laneHealth;
	}
	public void setLaneHealth(Long laneHealth) {
		this.laneHealth = laneHealth;
	}
	
	public Integer getAvcAxles() {
		return avcAxles;
	}
	public void setAvcAxles(Integer avcAxles) {
		this.avcAxles = avcAxles;
	}
	public Long getTourSegment() {
		return tourSegment;
	}
	public void setTourSegment(Long tourSegment) {
		this.tourSegment = tourSegment;
	}
	public String getBufRead() {
		return bufRead;
	}
	public void setBufRead(String bufRead) {
		this.bufRead = bufRead;
	}
	public String getReaderId() {
		return readerId;
	}
	public void setReaderId(String readerId) {
		this.readerId = readerId;
	}
	public Double getTollAmount() {
		return tollAmount;
	}
	public void setTollAmount(Double tollAmount) {
		this.tollAmount = tollAmount;
	}
	public String getDebitCredit() {
		return debitCredit;
	}
	public void setDebitCredit(String debitCredit) {
		this.debitCredit = debitCredit;
	}
	public Long getEtcValidStatus() {
		return etcValidStatus;
	}
	public void setEtcValidStatus(Long etcValidStatus) {
		this.etcValidStatus = etcValidStatus;
	}
	public Double getDiscountedAmount2() {
		return discountedAmount2;
	}
	public void setDiscountedAmount2(Double discountedAmount2) {
		this.discountedAmount2 = discountedAmount2;
	}
	public Double getVideoFareAmount() {
		return videoFareAmount;
	}
	public void setVideoFareAmount(Double videoFareAmount) {
		this.videoFareAmount = videoFareAmount;
	}
	public Long getPlanType() {
		return planType;
	}
	public void setPlanType(Long planType) {
		this.planType = planType;
	}
	public String getReserved() {
		return reserved;
	}
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	public Long getAtpFileId() {
		return atpFileId;
	}
	public void setAtpFileId(Long atpFileId) {
		this.atpFileId = atpFileId;
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
	public void setTxSubtypeInd(String txSubTypeInd) {
		this.txSubtypeInd = txSubTypeInd;
	}

	public Double getExpectedRevenueAmount() {
		return expectedRevenueAmount;
	}
	public void setExpectedRevenueAmount(Double expectedRevenueAmount) {
		this.expectedRevenueAmount = expectedRevenueAmount;
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
	public Double getEtcFareAmount() {
		return etcFareAmount;
	}
	public void setEtcFareAmount(Double etcFareAmount) {
		this.etcFareAmount = etcFareAmount;
	}
	public String getCorrReasonId() {
		return corrReasonId;
	}
	public void setCorrReasonId(String corrReasonId) {
		this.corrReasonId = corrReasonId;
	}
	public String getTransactionInfo() {
		return transactionInfo;
	}
	public void setTransactionInfo(String transactionInfo) {
		this.transactionInfo = transactionInfo;
	}
	public String getPlanCharged() {
		return planCharged;
	}
	public void setPlanCharged(String planCharged) {
		this.planCharged = planCharged;
	}
	public String getTxStatus() {
		return txStatus;
	}
	public void setTxStatus(String txStatus) {
		this.txStatus = txStatus;
	}
	public LocalDate getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(LocalDate postedDate) {
		this.postedDate = postedDate;
	}
	public String getDepositId() {
		return depositId;
	}
	public void setDepositId(String depositId) {
		this.depositId = depositId;
	}
	public String getDeviceNoFromDb() {
		return deviceNoFromDb;
	}
	public void setDeviceNoFromDb(String deviceNoFromDb) {
		this.deviceNoFromDb = deviceNoFromDb;
	}
	@Override
	public String toString() {
		return "TranDetail [laneTxId=" + laneTxId + ", txExternRefNo=" + txExternRefNo + ", txType=" + txType
				+ ", txSubType=" + txSubType + ", txTypeInd=" + txTypeInd + ", txSubtypeInd=" + txSubtypeInd
				+ ", tollSystemType=" + tollSystemType + ", tollRevenueType=" + tollRevenueType + ", entryTxTimeStamp="
				+ entryTxTimeStamp + ", entryPlazaId=" + entryPlazaId + ", entryLaneId=" + entryLaneId
				+ ", entryTxSeqNumber=" + entryTxSeqNumber + ", entryDataSource=" + entryDataSource
				+ ", entryVehicleSpeed=" + entryVehicleSpeed + ", plazaAgencyId=" + plazaAgencyId + ", plazaId="
				+ plazaId + ", laneId=" + laneId + ", externPlazaId=" + externPlazaId + ", externLaneId=" + externLaneId
				+ ", txDate=" + txDate + ", txTimestamp=" + txTimestamp + ", laneMode=" + laneMode + ", laneState="
				+ laneState + ", laneSn=" + laneSn + ", deviceNo=" + deviceNo + ", deviceCodedClass=" + deviceCodedClass
				+ ", deviceIagClass=" + deviceIagClass + ", deviceAgencyClass=" + deviceAgencyClass + ", tagAxles="
				+ tagAxles + ", actualClass=" + actualClass + ", actualAxles=" + actualAxles + ", extraAxles="
				+ extraAxles + ", plateState=" + plateState + ", plateNumber=" + plateNumber + ", plateType="
				+ plateType + ", plateCountry=" + plateCountry + ", readPerf=" + readPerf + ", writePerf=" + writePerf
				+ ", progStat=" + progStat + ", collectorNumber=" + collectorNumber + ", imageCaptured=" + imageCaptured
				+ ", vehicleSpeed=" + vehicleSpeed + ", speedViolation=" + speedViolation + ", reciprocityTrx="
				+ reciprocityTrx + ", isViolation=" + isViolation + ", isLprEnabled=" + isLprEnabled
				+ ", fullFareAmount=" + fullFareAmount + ", discountedAmount=" + discountedAmount
				+ ", unrealizedAmount=" + unrealizedAmount + ", externFileId=" + externFileId + ", receivedDate="
				+ receivedDate + ", accountType=" + accountType + ", accountAgencyId=" + accountAgencyId
				+ ", etcAccountId=" + etcAccountId + ", etcSubAccount=" + etcSubAccount + ", dstFlag=" + dstFlag
				+ ", isPeak=" + isPeak + ", fareType=" + fareType + ", updateTs=" + updateTs + ", laneDataSource="
				+ laneDataSource + ", laneType=" + laneType + ", laneHealth=" + laneHealth + ", avcAxles=" + avcAxles
				+ ", tourSegment=" + tourSegment + ", bufRead=" + bufRead + ", readerId=" + readerId + ", tollAmount="
				+ tollAmount + ", debitCredit=" + debitCredit + ", etcValidStatus=" + etcValidStatus
				+ ", discountedAmount2=" + discountedAmount2 + ", videoFareAmount=" + videoFareAmount + ", planType="
				+ planType + ", reserved=" + reserved + ", atpFileId=" + atpFileId + ", expectedRevenueAmount="
				+ expectedRevenueAmount + ", cashFareAmount=" + cashFareAmount + ", postedFareAmount="
				+ postedFareAmount + ", etcFareAmount=" + etcFareAmount + ", corrReasonId=" + corrReasonId
				+ ", transactionInfo=" + transactionInfo + ", planCharged=" + planCharged + ", txStatus=" + txStatus
				+ ", postedDate=" + postedDate + ", depositId=" + depositId + ", frontOcrPlateCountry="
				+ frontOcrPlateCountry + ", frontOcrPlateState=" + frontOcrPlateState + ", frontOcrPlateType="
				+ frontOcrPlateType + ", frontOcrPlateNumber=" + frontOcrPlateNumber + ", frontOcrConfidence="
				+ frontOcrConfidence + ", frontOcrImageIndex=" + frontOcrImageIndex + ", frontImageColor="
				+ frontImageColor + ", rearOcrPlateCountry=" + rearOcrPlateCountry + ", rearOcrPlateState="
				+ rearOcrPlateState + ", rearOcrPlateType=" + rearOcrPlateType + ", rearOcrPlateNumber="
				+ rearOcrPlateNumber + ", rearOcrConfidence=" + rearOcrConfidence + ", rearOcrImageIndex="
				+ rearOcrImageIndex + ", rearImageColor=" + rearImageColor + ", deviceNoFromDb=" + deviceNoFromDb + "]";
	}
	
}