package com.conduent.tpms.qatp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import com.conduent.tpms.qatp.dto.OCRDto;
import com.conduent.tpms.qatp.utility.Convertor;
import com.conduent.tpms.qatp.utility.DateUtils;
import com.google.gson.annotations.Expose;

public class TranDetail 
{
	@Expose(serialize = true, deserialize = true)
	private Long laneTxId;
	@Expose(serialize = true, deserialize = true)
	private String txExternRefNo;
	@Expose(serialize = true, deserialize = true)
	private String matchedTxExternRefNo;
	@Expose(serialize = true, deserialize = true)
	private String txType;
	@Expose(serialize = true, deserialize = true)
	private String txSubType;
	@Expose(serialize = true, deserialize = true)
	private String tollSystemType;
	@Expose(serialize = true, deserialize = true)
	private Integer tollRevenueType;
	@Expose(serialize = true, deserialize = true)
	private OffsetDateTime entryTxTimeStamp;
	//private LocalDateTime entryTxTimeStamp;
	@Expose(serialize = true, deserialize = true)
	private Integer entryPlazaId;
	@Expose(serialize = true, deserialize = true)
	private Integer entryLaneId;
	@Expose(serialize = true, deserialize = true)
	private Integer entryTxSeqNumber;
	@Expose(serialize = true, deserialize = true)
	private String	entryDataSource;
	@Expose(serialize = true, deserialize = true)
	private Integer entryVehicleSpeed;
	@Expose(serialize = true, deserialize = true)
	private Integer plazaAgencyId;
	@Expose(serialize = true, deserialize = true)
	private Integer plazaId;
	@Expose(serialize = true, deserialize = true)
	private Integer laneId;
	@Expose(serialize = true, deserialize = true)
	private String externPlazaId;
	@Expose(serialize = true, deserialize = true)
	private String externLaneId;
	@Expose(serialize = true, deserialize = true)
	private LocalDate txDate;
	@Expose(serialize = true, deserialize = true)
	private OffsetDateTime txTimeStamp;
	//private LocalDateTime txTimeStamp;
	@Expose(serialize = true, deserialize = true)
	private Integer laneMode;
	@Expose(serialize = true, deserialize = true)
	private Integer laneState;
	@Expose(serialize = true, deserialize = true)
	private Long laneSn;
	@Expose(serialize = true, deserialize = true)
	private String deviceNo;
	@Expose(serialize = true, deserialize = true)
	private Integer deviceCodedClass;        
	@Expose(serialize = true, deserialize = true)
	private Long deviceIagClass;
	@Expose(serialize = true, deserialize = true)
	private Long deviceAgencyClass;
	@Expose(serialize = true, deserialize = true)
	private Integer tagAxles;
	@Expose(serialize = true, deserialize = true)
	private Integer actualClass;
	@Expose(serialize = true, deserialize = true)
	private Integer actualAxles;
	@Expose(serialize = true, deserialize = true)
	private Integer extraAxles;
	@Expose(serialize = true, deserialize = true)
	private String plateState;
	@Expose(serialize = true, deserialize = true)
	private String plateNumber;
	@Expose(serialize = true, deserialize = true)
	private Integer plateType;        
	@Expose(serialize = true, deserialize = true)
	private String plateCountry;
	@Expose(serialize = true, deserialize = true)
	private Integer readPerf;
	@Expose(serialize = true, deserialize = true)
	private Integer writePerf;
	@Expose(serialize = true, deserialize = true)
	private Integer progStat;
	@Expose(serialize = true, deserialize = true)
	private String collectorNumber;  
	@Expose(serialize = true, deserialize = true)
	private String imageCaptured;
	@Expose(serialize = true, deserialize = true)
	private Integer vehicleSpeed;
	@Expose(serialize = true, deserialize = true)
	private Integer speedViolation;
	@Expose(serialize = true, deserialize = true)
	private String reciprocityTrx;
	@Expose(serialize = true, deserialize = true)
	private String isViolation;
//	@Expose(serialize = true, deserialize = true)
//	private String isLprEnabled;
	@Expose(serialize = true, deserialize = true)
	private Double fullFareAmount;
	@Expose(serialize = true, deserialize = true)
	private Double discountedAmount;
	@Expose(serialize = true, deserialize = true)
	private Double unrealizedAmount;
	@Expose(serialize = true, deserialize = true)
	private Long extFileId;
	@Expose(serialize = true, deserialize = true)
	private LocalDate receivedDate;
	@Expose(serialize = true, deserialize = true)
	private Integer accountType;
	@Expose(serialize = true, deserialize = true)
	private Integer accAgencyId;
	@Expose(serialize = true, deserialize = true)
	private Long etcAccountId;
	@Expose(serialize = true, deserialize = true)
	private Long etcSubAccount;
	@Expose(serialize = true, deserialize = true)
	private String dstFlag;
	@Expose(serialize = true, deserialize = true)
	private String isPeak;
	@Expose(serialize = true, deserialize = true)
	private Integer fareType;
	
	private LocalDateTime updateTs;
	@Expose(serialize = true, deserialize = true)
	private String laneDataSource;
	@Expose(serialize = true, deserialize = true)
	private Long laneType;    
	@Expose(serialize = true, deserialize = true)
	private Long laneHealth;
	@Expose(serialize = true, deserialize = true)
	private Integer avcAxles;
	@Expose(serialize = true, deserialize = true)
	private Long tourSegment;
	@Expose(serialize = true, deserialize = true)
	private String bufRead;
	@Expose(serialize = true, deserialize = true)
	private String readerId;
	@Expose(serialize = true, deserialize = true)
	private Double tollAmount;
	@Expose(serialize = true, deserialize = true)
	private String debitCredit;
	@Expose(serialize = true, deserialize = true)
	private Long etcValidStatus;
	@Expose(serialize = true, deserialize = true)
	private Double discountedAmount2;
	@Expose(serialize = true, deserialize = true)
	private Double videoFareAmount;
	@Expose(serialize = true, deserialize = true)
	private Long planType;     
	@Expose(serialize = true, deserialize = true)
	private String reserved;
	@Expose(serialize = true, deserialize = true)
	private Long atpFileId; 
	
	@Expose(serialize = true, deserialize = true)
	private Integer avcClass;
	@Expose(serialize = true, deserialize = true)
	private Integer tagIagClass;
	@Expose(serialize = true, deserialize = true)
	private Integer tagClass;
	
	@Expose(serialize = true, deserialize = true)
	private String aetFlag;
	@Expose(serialize = true, deserialize = true)
	private Double etcFareAmount;
	@Expose(serialize = true, deserialize = true)
	private Double postedFareAmount;
	@Expose(serialize = true, deserialize = true)
	private Double expectedRevenueAmount;
	@Expose(serialize = true, deserialize = true)
	private Double cashFareAmount;
	@Expose(serialize = true, deserialize = true)
	private Integer txStatus;
	@Expose(serialize = true, deserialize = true)
	private Integer fileSeqNum;
	@Expose(serialize = true, deserialize = true)
	private Integer facAgency;
	
	

	public Integer getFacAgency() {
		return facAgency;
	}

	public void setFacAgency(Integer facAgency) {
		this.facAgency = facAgency;
	}

	public Integer getFileSeqNum() {
		return fileSeqNum;
	}

	public void setFileSeqNum(Integer fileSeqNum) {
		this.fileSeqNum = fileSeqNum;
	}
	
	
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
	

	
	public Integer getTxStatus() {
		return txStatus;
	}
	public void setTxStatus(Integer txStatus) {
		this.txStatus = txStatus;
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
	public Double getCashFareAmount() {
		return cashFareAmount;
	}
	public void setCashFareAmount(Double cashFareAmount) {
		this.cashFareAmount = cashFareAmount;
	}
	public String getMatchedTxExternRefNo() {
		return matchedTxExternRefNo;
	}
	public void setMatchedTxExternRefNo(String matchedTxExternRefNo) {
		this.matchedTxExternRefNo = matchedTxExternRefNo;
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
	public Integer getEntryTxSeqNumber() {
		return entryTxSeqNumber;
	}
	public void setEntryTxSeqNumber(Integer entryTxSeqNumber) {
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
	public Integer getLaneId() {
		return laneId;
	}
	public void setLaneId(Integer laneId) {
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
	public LocalDate getTxDate() {
		return txDate;
	}
	public void setTxDate(LocalDate txDate) {
		this.txDate = txDate;
	}
	
	public OffsetDateTime getEntryTxTimeStamp() {
		return entryTxTimeStamp;
	}
	public void setEntryTxTimeStamp(OffsetDateTime entryTxTimeStamp) {
		this.entryTxTimeStamp = entryTxTimeStamp;
	}
	public OffsetDateTime getTxTimeStamp() {
		return txTimeStamp;
	}
	public void setTxTimeStamp(OffsetDateTime txTimeStamp) {
		this.txTimeStamp = txTimeStamp;
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
	public Integer getPlateType() {
		return plateType;
	}
	public void setPlateType(Integer plateType) {
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
//	public String getIsLprEnabled() {
//		return isLprEnabled;
//	}
//	public void setIsLprEnabled(String isLprEnabled) {
//		this.isLprEnabled = isLprEnabled;
//	}
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
	public Long getExtFileId() {
		return extFileId;
	}
	public void setExtFileId(Long extFileId) {
		this.extFileId = extFileId;
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
	public Integer getAccAgencyId() {
		return accAgencyId;
	}
	public void setAccAgencyId(Integer accAgencyId) {
		this.accAgencyId = accAgencyId;
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
	
	public Integer getAvcClass() {
		return avcClass;
	}
	public void setAvcClass(Integer avcClass) {
		this.avcClass = avcClass;
	}
	public Integer getTagIagClass() {
		return tagIagClass;
	}
	public void setTagIagClass(Integer tagIagClass) {
		this.tagIagClass = tagIagClass;
	}
	public Integer getTagClass() {
		return tagClass;
	}
	public void setTagClass(Integer tagClass) {
		this.tagClass = tagClass;
	}
	public String getAetFlag() {
		return aetFlag;
	}
	public void setAetFlag(String aetFlag) {
		this.aetFlag = aetFlag;
	}
	
	
	@Override
	public String toString() {
		return "TranDetail [laneTxId=" + laneTxId + ", txExternRefNo=" + txExternRefNo + ", matchedTxExternRefNo="
				+ matchedTxExternRefNo + ", txType=" + txType + ", txSubType=" + txSubType + ", tollSystemType="
				+ tollSystemType + ", tollRevenueType=" + tollRevenueType + ", entryTxTimeStamp=" + entryTxTimeStamp
				+ ", entryPlazaId=" + entryPlazaId + ", entryLaneId=" + entryLaneId + ", entryTxSeqNumber="
				+ entryTxSeqNumber + ", entryDataSource=" + entryDataSource + ", entryVehicleSpeed=" + entryVehicleSpeed
				+ ", plazaAgencyId=" + plazaAgencyId + ", plazaId=" + plazaId + ", laneId=" + laneId
				+ ", externPlazaId=" + externPlazaId + ", externLaneId=" + externLaneId + ", txDate=" + txDate
				+ ", txTimeStamp=" + txTimeStamp + ", laneMode=" + laneMode + ", laneState=" + laneState + ", laneSn="
				+ laneSn + ", deviceNo=" + deviceNo + ", deviceCodedClass=" + deviceCodedClass + ", deviceIagClass="
				+ deviceIagClass + ", deviceAgencyClass=" + deviceAgencyClass + ", tagAxles=" + tagAxles
				+ ", actualClass=" + actualClass + ", actualAxles=" + actualAxles + ", extraAxles=" + extraAxles
				+ ", plateState=" + plateState + ", plateNumber=" + plateNumber + ", plateType=" + plateType
				+ ", plateCountry=" + plateCountry + ", readPerf=" + readPerf + ", writePerf=" + writePerf
				+ ", progStat=" + progStat + ", collectorNumber=" + collectorNumber + ", imageCaptured=" + imageCaptured
				+ ", vehicleSpeed=" + vehicleSpeed + ", speedViolation=" + speedViolation + ", reciprocityTrx="
				+ reciprocityTrx + ", isViolation=" + isViolation + ", fullFareAmount=" + fullFareAmount
				+ ", discountedAmount=" + discountedAmount + ", unrealizedAmount=" + unrealizedAmount + ", extFileId="
				+ extFileId + ", receivedDate=" + receivedDate + ", accountType=" + accountType + ", accAgencyId="
				+ accAgencyId + ", etcAccountId=" + etcAccountId + ", etcSubAccount=" + etcSubAccount + ", dstFlag="
				+ dstFlag + ", isPeak=" + isPeak + ", fareType=" + fareType + ", updateTs=" + updateTs
				+ ", laneDataSource=" + laneDataSource + ", laneType=" + laneType + ", laneHealth=" + laneHealth
				+ ", avcAxles=" + avcAxles + ", tourSegment=" + tourSegment + ", bufRead=" + bufRead + ", readerId="
				+ readerId + ", tollAmount=" + tollAmount + ", debitCredit=" + debitCredit + ", etcValidStatus="
				+ etcValidStatus + ", discountedAmount2=" + discountedAmount2 + ", videoFareAmount=" + videoFareAmount
				+ ", planType=" + planType + ", reserved=" + reserved + ", atpFileId=" + atpFileId + ", avcClass="
				+ avcClass + ", tagIagClass=" + tagIagClass + ", tagClass=" + tagClass + ", aetFlag=" + aetFlag
				+ ", frontOcrPlateCountry=" + frontOcrPlateCountry + ", frontOcrPlateState=" + frontOcrPlateState
				+ ", frontOcrPlateType=" + frontOcrPlateType + ", frontOcrPlateNumber=" + frontOcrPlateNumber
				+ ", frontOcrConfidence=" + frontOcrConfidence + ", frontOcrImageIndex=" + frontOcrImageIndex
				+ ", frontImageColor=" + frontImageColor + ", rearOcrPlateCountry=" + rearOcrPlateCountry
				+ ", rearOcrPlateState=" + rearOcrPlateState + ", rearOcrPlateType=" + rearOcrPlateType
				+ ", rearOcrPlateNumber=" + rearOcrPlateNumber + ", rearOcrConfidence=" + rearOcrConfidence
				+ ", rearOcrImageIndex=" + rearOcrImageIndex + ", rearImageColor=" + rearImageColor + "]";
	}
	public OCRDto getOCRDto()
	{
		OCRDto obj=new OCRDto();
		obj.setfOcrConfidence(Convertor.toInteger(frontOcrConfidence));
		obj.setfOcrImageColor(frontImageColor);
		obj.setfOcrImageIndex(Convertor.toInteger(frontOcrImageIndex));
		obj.setfOcrPlateCountry(frontOcrPlateCountry);
		obj.setfOcrPlateNumber(frontOcrPlateNumber);
		obj.setfOcrPlateState(frontOcrPlateState);
		obj.setfOcrPlateType(frontOcrPlateType);
		obj.setLaneTxId(laneTxId);
		obj.setrImageColor(rearImageColor);
		obj.setrOcrConfidence(Convertor.toInteger(rearOcrConfidence));
		obj.setrOcrImageIndex(Convertor.toInteger(rearOcrImageIndex));
		obj.setrOcrPlateCountry(rearOcrPlateCountry);
		obj.setrOcrPlateNumber(rearOcrPlateNumber);
		obj.setrOcrPlateState(rearOcrPlateState);
		obj.setrOcrPlateType(rearOcrPlateType);
		obj.setTxDate(DateUtils.parseLocalDate(txDate, "yyyy-MM-dd"));
		return obj;
	}
}