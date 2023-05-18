package com.conduent.tpms.qatp.model;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.constants.Constants;
import com.conduent.tpms.qatp.constants.QATPConstants;
import com.conduent.tpms.qatp.dto.Tplaza;
import com.conduent.tpms.qatp.model.Transaction.ProgState;
import com.conduent.tpms.qatp.utility.Convertor;
import com.conduent.tpms.qatp.utility.DateUtils;
import com.conduent.tpms.qatp.utility.MasterDataCache;

public class Transaction {
	
	private static final Logger logger = LoggerFactory.getLogger(Transaction.class);
	
	public enum ProgState { STAR , S, U, F }
	
	private Long laneTxId;
	private Long txExternRefNo;
	private String trxType;
	private String trxSubtype;
	private String tollSystemType;
	private Integer tollRevenueType;
	private String accountType;
	private String entryDate;
	private String entryTime;
	private Integer entryLaneId;
	private Long entryLaneSN;
	private String entryDataSource;
	private Integer entryVehicleSpeed;
	private String deviceAgencyId;
	private String deviceNumber;
	private String laneType;
	private String laneMode;
	private String laneState;
	private Long collectorNumber;
	private Integer actualClass;
	private Integer actualAxles;
	private Integer extraAxles;
	private Long tagClass;
	private Integer tagAxles;
	private Long tagIAGClass;
	private Long AvcClass;
	private Integer AvcAxles;
	private Long EtcValidation;
	private Integer readPerf;
	private Integer writePerf;
	private String progStat;
	private String imageCaptured;
	private Integer vehicleSpeed;
	private Integer speedViolation;
	private String reciprocityTrx;
	private String trxDate;
	private String trxTime;
	private Integer trxLaneId;
	private Long trxLaneSN;
	private String bufRead;
	private Long etcAccountId;
	private Long etcSubAccount;
	private String debitCredit;
	private Double tollAmount;
	private String licensePlate;
	private String licenseState;
	private String laneHealth;
	private String readerId;
	private String isPeak;
	private String fareType;
	private Double fullFareAmt;
	private BigInteger extHostRefNum;
	private Long extFileId;
	private String receivedDate;
	private Integer entryPlaza;
	private Integer exitPlaza;
	private Integer entryLane;
	private Integer exitLane;
	private String externPlazaId;
	private String externLaneId;
	private String correction;
	private Long tourSegmentId;
	private String dstFlag;
	private String laneDataSource;
	private Integer plazaAgencyId;
	private Integer acctAgencyId;
	private String reserved;
	private Integer isViolation; /* was this a lane violation? (not in the ETOL fmt) */
	private Integer isLprEnabled;
	private String frontOcrPlateCountry;
	private String frontOcrPlateState;
	private String frontOcrPlateType;
	private String frontOcrPlateNumber;
	private String frontOcrConfidence;
	private String frontOcrImageIndex;
	private String frontImageColor;
	private String rearOcrPlateCountry;
	private String rearOcrPlateState;
	private String rearOcrPlateType;
	private String rearOcrPlateNumber;
	private String rearOcrConfidence;
	private String rearOcrImageIndex;
	private String rearImageColor;
	private String plateCountry;
	private String plateState;
	private Integer plateType;
	private String plateNumber;
	private String txTimeStamp;

	private String discountAmount1;
	private String discountAmount2;
	private String licPlateType;
	private String confidenceLevel;
	private Double discountedFareOne;
	private Double discountedFareTwo;
	private Double videoFareAmount;
	private String planCharged;
	private Long atrnPlanType;
	private String entryTimeStamp;
	private String aetFlag;
	private Double postedFareAmount;
	private Double expectedReveneueAmount;
	private Integer fileNum;
	private String agTrxType;
//	private Integer etcViolObserved;
	private String transType;
	private String direction;
	private String event;
	private String hovFlag;
	private String hovPresenceTime;
	private String hovLostPresenceTime;
	private String avcDiscrepancyFlag;
	private String degraded;
	private String uoCode;
	private String avcProfile;
	private String ti1TagReadStatus;
	private String ti2AdditionalTags;
	private String ti3ClassMismatchFlag;
	private String ti4TagStatus;
	private String ti5PaymentFlag;
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

	public String getHovPresenceTime() {
		return hovPresenceTime;
	}

	public void setHovPresenceTime(String hovPresenceTime) {
		this.hovPresenceTime = hovPresenceTime;
	}

	public String getHovLostPresenceTime() {
		return hovLostPresenceTime;
	}

	public void setHovLostPresenceTime(String hovLostPresenceTime) {
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
	
	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

//	public Integer getEtcViolObserved() {
//		return etcViolObserved;
//	}
//
//	public void setEtcViolObserved(Integer etcViolObserved) {
//		this.etcViolObserved = etcViolObserved;
//	}

	public String getAgTrxType() {
		return agTrxType;
	}

	public void setAgTrxType(String agTrxType) {
		this.agTrxType = agTrxType;
	}

	public Integer getFileNum() {
		return fileNum;
	}

	public void setFileNum(Integer fileNum) {
		this.fileNum = fileNum;
	}
	
	public Double getPostedFareAmount() {
		return postedFareAmount;
	}

	public void setPostedFareAmount(Double postedFareAmount) {
		this.postedFareAmount = postedFareAmount;
	}

	public Double getExpectedReveneueAmount() {
		return expectedReveneueAmount;
	}

	public void setExpectedReveneueAmount(Double expectedReveneueAmount) {
		this.expectedReveneueAmount = expectedReveneueAmount;
	}

	public String getAetFlag() {
		return aetFlag;
	}

	public void setAetFlag(String aetFlag) {
		this.aetFlag = aetFlag;
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

	public String getDiscountAmount1() {
		return discountAmount1;
	}

	public void setDiscountAmount1(String discountAmount1) {
		this.discountAmount1 = discountAmount1;
	}

	public String getDiscountAmount2() {
		return discountAmount2;
	}

	public void setDiscountAmount2(String discountAmount2) {
		this.discountAmount2 = discountAmount2;
	}

	public Long getLaneTxId() {
		return laneTxId;
	}

	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
	}

	public String getTrxType() {
		return trxType;
	}

	public void setTrxType(String trxType) {
		this.trxType = trxType;
	}

	public String getTrxSubtype() {
		return trxSubtype;
	}

	public void setTrxSubtype(String trxSubtype) {
		this.trxSubtype = trxSubtype;
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

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	public String getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}

	public Integer getEntryLaneId() {
		return entryLaneId;
	}

	public void setEntryLaneId(Integer entryLaneId) {
		this.entryLaneId = entryLaneId;
	}

	public Long getEntryLaneSN() {
		return entryLaneSN;
	}

	public void setEntryLaneSN(Long entryLaneSN) {
		this.entryLaneSN = entryLaneSN;
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

	public String getDeviceAgencyId() {
		return deviceAgencyId;
	}

	public void setDeviceAgencyId(String deviceAgencyId) {
		this.deviceAgencyId = deviceAgencyId;
	}

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public String getLaneType() {
		return laneType;
	}

	public void setLaneType(String laneType) {
		this.laneType = laneType;
	}

	public String getLaneMode() {
		return laneMode;
	}

	public void setLaneMode(String laneMode) {
		this.laneMode = laneMode;
	}

	public String getLaneState() {
		return laneState;
	}

	public void setLaneState(String laneState) {
		this.laneState = laneState;
	}

	public Long getCollectorNumber() {
		return collectorNumber;
	}

	public void setCollectorNumber(Long collectorNumber) {
		this.collectorNumber = collectorNumber;
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

	public Long getTagClass() {
		return tagClass;
	}

	public void setTagClass(Long tagClass) {
		this.tagClass = tagClass;
	}

	public Integer getTagAxles() {
		return tagAxles;
	}

	public void setTagAxles(Integer tagAxles) {
		this.tagAxles = tagAxles;
	}

	public Long getTagIAGClass() {
		return tagIAGClass;
	}

	public void setTagIAGClass(Long tagIAGClass) {
		this.tagIAGClass = tagIAGClass;
	}

	public Long getAvcClass() {
		return AvcClass;
	}

	public void setAvcClass(Long avcClass) {
		AvcClass = avcClass;
	}

	public Integer getAvcAxles() {
		return AvcAxles;
	}

	public void setAvcAxles(Integer avcAxles) {
		AvcAxles = avcAxles;
	}

	public Long getEtcValidation() {
		return EtcValidation;
	}

	public void setEtcValidation(Long etcValidation) {
		EtcValidation = etcValidation;
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

	public String getProgStat() {
		return progStat;
	}

	public void setProgStat(String progStat) {
		this.progStat = progStat;
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

	public String getTrxDate() {
		return trxDate;
	}

	public void setTrxDate(String trxDate) {
		this.trxDate = trxDate;
	}

	public String getTrxTime() {
		return trxTime;
	}

	public void setTrxTime(String trxTime) {
		this.trxTime = trxTime;
	}

	public Integer getTrxLaneId() {
		return trxLaneId;
	}

	public void setTrxLaneId(Integer trxLaneId) {
		this.trxLaneId = trxLaneId;
	}

	public Long getTrxLaneSN() {
		return trxLaneSN;
	}

	public void setTrxLaneSN(Long trxLaneSN) {
		this.trxLaneSN = trxLaneSN;
	}

	public String getBufRead() {
		return bufRead;
	}

	public void setBufRead(String bufRead) {
		this.bufRead = bufRead;
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

	public String getDebitCredit() {
		return debitCredit;
	}

	public void setDebitCredit(String debitCredit) {
		this.debitCredit = debitCredit;
	}

	public Double getTollAmount() {
		return tollAmount;
	}

	public void setTollAmount(Double tollAmount) {
		this.tollAmount = tollAmount;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getLicenseState() {
		return licenseState;
	}

	public void setLicenseState(String licenseState) {
		this.licenseState = licenseState;
	}

	public String getLaneHealth() {
		return laneHealth;
	}

	public void setLaneHealth(String laneHealth) {
		this.laneHealth = laneHealth;
	}

	public String getReaderId() {
		return readerId;
	}

	public void setReaderId(String readerId) {
		this.readerId = readerId;
	}

	public String getIsPeak() {
		return isPeak;
	}

	public void setIsPeak(String isPeak) {
		this.isPeak = isPeak;
	}

	public String getFareType() {
		return fareType;
	}

	public void setFareType(String fareType) {
		this.fareType = fareType;
	}

	public Double getFullFareAmt() {
		return fullFareAmt;
	}

	public void setFullFareAmt(Double fullFareAmt) {
		this.fullFareAmt = fullFareAmt;
	}

	public BigInteger getExtHostRefNum() {
		return extHostRefNum;
	}

	public void setExtHostRefNum(BigInteger extHostRefNum) {
		this.extHostRefNum = extHostRefNum;
	}

	public Long getExtFileId() {
		return extFileId;
	}

	public void setExtFileId(Long extFileId) {
		this.extFileId = extFileId;
	}

	public String getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Integer getEntryPlaza() {
		return entryPlaza;
	}

	public void setEntryPlaza(Integer entryPlaza) {
		this.entryPlaza = entryPlaza;
	}

	public Integer getExitPlaza() {
		return exitPlaza;
	}

	public void setExitPlaza(Integer exitPlaza) {
		this.exitPlaza = exitPlaza;
	}

	public String getCorrection() {
		return correction;
	}

	public void setCorrection(String correction) {
		this.correction = correction;
	}

	public Long getTourSegmentId() {
		return tourSegmentId;
	}

	public void setTourSegmentId(Long tourSegmentId) {
		this.tourSegmentId = tourSegmentId;
	}

	public String getDstFlag() {
		return dstFlag;
	}

	public void setDstFlag(String dstFlag) {
		this.dstFlag = dstFlag;
	}

	public String getLaneDataSource() {
		return laneDataSource;
	}

	public void setLaneDataSource(String laneDataSource) {
		this.laneDataSource = laneDataSource;
	}

	public Integer getPlazaAgencyId() {
		return plazaAgencyId;
	}

	public void setPlazaAgencyId(Integer plazaAgencyId) {
		this.plazaAgencyId = plazaAgencyId;
	}

	public Integer getAcctAgencyId() {
		return acctAgencyId;
	}

	public void setAcctAgencyId(Integer acctAgencyId) {
		this.acctAgencyId = acctAgencyId;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	public Integer getIsViolation() {
		return isViolation;
	}

	public void setIsViolation(Integer isViolation) {
		this.isViolation = isViolation;
	}

	public Integer getIsLprEnabled() {
		return isLprEnabled;
	}

	public void setIsLprEnabled(Integer isLprEnabled) {
		this.isLprEnabled = isLprEnabled;
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

	public Long getTxExternRefNo() {
		return txExternRefNo;
	}

	public void setTxExternRefNo(Long txExternRefNo) {
		this.txExternRefNo = txExternRefNo;
	}

	public Integer getEntryLane() {
		return entryLane;
	}

	public void setEntryLane(Integer entryLane) {
		this.entryLane = entryLane;
	}

	public Integer getExitLane() {
		return exitLane;
	}

	public void setExitLane(Integer exitLane) {
		this.exitLane = exitLane;
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

	public Integer getPlateType() {
		return plateType;
	}

	public void setPlateType(Integer plateType) {
		this.plateType = plateType;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public TranDetail getTranDetail(TimeZoneConv timeZoneConv, MasterDataCache masterDataCacheInfo) 
	{
		TranDetail obj = new TranDetail();
		
		obj.setAccAgencyId(null);
		obj.setAccountType(1);
		obj.setActualAxles(actualAxles);
		obj.setActualClass(actualClass);
		obj.setCollectorNumber(null);
		obj.setDeviceAgencyClass(null);
		obj.setDeviceCodedClass(null);
		obj.setDeviceIagClass(null);
		
		if(deviceNumber == null || validate(deviceNumber))
		{
			obj.setDeviceNo(null);
		}
		else
		{
			obj.setDeviceNo(deviceNumber);
		}
		
		obj.setDiscountedAmount(0.0);
		obj.setDstFlag(dstFlag.equals("*")?"F":dstFlag);
		obj.setEntryDataSource(null);
		obj.setEntryLaneId(null);
		obj.setEntryPlazaId(null);
		obj.setEntryTxSeqNumber(null);
		obj.setEntryTxTimeStamp(null);
		obj.setEntryVehicleSpeed(0);
		obj.setEtcAccountId(null);
		obj.setEtcSubAccount(null);
		obj.setExtFileId(extFileId);
		obj.setExtraAxles(extraAxles);
		obj.setExternPlazaId(externPlazaId);
		obj.setExternLaneId(externLaneId);
		obj.setFareType(0);
		obj.setImageCaptured(imageCaptured);
		obj.setIsPeak(isPeak != null ? isPeak + "" : "F");
		obj.setIsViolation(isViolation != null ? isViolation + "" : "0");
		obj.setLaneId(trxLaneId);
		obj.setLaneMode(null);
		obj.setLaneSn(null);
		obj.setLaneState(null);
		obj.setPlateCountry(plateCountry.equals("***")?null:plateCountry);
		obj.setPlateState(plateState.equals("**" )?null:plateState);
		obj.setPlateType(plateType!=null?plateType:0);
		obj.setPlateNumber(plateNumber.equals("**********")?null:plateNumber);
		obj.setVehicleSpeed(vehicleSpeed != null ? vehicleSpeed : 0);
		obj.setPlazaAgencyId(plazaAgencyId);
		obj.setPlazaId(exitPlaza);	
		obj.setProgStat(null);	
		obj.setReadPerf(null);
		
		if (receivedDate != null) 
		{		
			if(DateUtils.parseLocalDate(receivedDate.substring(0, 8),"yyyyMMdd") != null) 
			{
				try 
				{
					LocalDateTime temp = LocalDateTime.parse(receivedDate,DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
					ZonedDateTime zoned_temp = timeZoneConv.zonedTxTimeUTCOffset(temp);
					
					obj.setReceivedDate(zoned_temp.toOffsetDateTime().toLocalDate());
					
				} 
				catch (Exception e) 
				{
					logger.info("Invalid value of minute of hour");
					OffsetDateTime DEFAULT_TIMESTAMP = OffsetDateTime.parse(Constants.DEFAULT_TIMESTAMP);
					obj.setReceivedDate(DEFAULT_TIMESTAMP.toLocalDate());
				}
			}
		}
		
		//obj.setReceivedDate(DateUtils.parseLocalDate(receivedDate, "yyyyMMdd"));
			
		obj.setReciprocityTrx(reciprocityTrx != null ? reciprocityTrx : "F");	
		obj.setSpeedViolation(speedViolation != null ? speedViolation : 0);
		obj.setTagAxles(tagAxles != null ? tagAxles : 0);
		obj.setTollRevenueType(1);
		obj.setTollSystemType("B");
		//obj.setTxDate(DateUtils.parseLocalDate(trxDate, "yyyyMMdd"));
		obj.setTransType(transType);
		
		obj.setTxType(trxType);
		obj.setTxSubType(trxSubtype);
		
		if(extHostRefNum != null) 
		{					
			obj.setTxExternRefNo(StringUtils.leftPad(String.valueOf(extHostRefNum), 20, "0"));			
		}
		else 
		{
			obj.setTxExternRefNo("00000000000000000000");
		}
				
		if (txTimeStamp != null) 
		{		
			if(DateUtils.parseLocalDate(txTimeStamp.substring(0, 8),"yyyyMMdd") != null) 
			{
				try 
				{
					LocalDateTime temp = LocalDateTime.parse(txTimeStamp,DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS"));
					ZonedDateTime offset_temp = timeZoneConv.zonedTxTimeUTCOffset(temp);
					
					obj.setTxTimeStamp(offset_temp.toOffsetDateTime());
					obj.setTxDate(offset_temp.toOffsetDateTime().toLocalDate());
					
				} 
				catch (Exception e) 
				{
					logger.info("Invalid value of minute of hour");
					OffsetDateTime DEFAULT_TIMESTAMP = OffsetDateTime.parse(Constants.DEFAULT_TIMESTAMP);
					obj.setTxTimeStamp(DEFAULT_TIMESTAMP);
					obj.setTxType("R");
					obj.setTxSubType("D");
				}
			}
		}

		obj.setEtcValidStatus(EtcValidation !=null ? EtcValidation : 0);
		obj.setUpdateTs(timeZoneConv.currentTime());
		obj.setWritePerf(writePerf != null ? writePerf : null);
		obj.setLaneDataSource(laneDataSource != null ? laneDataSource : null);
		obj.setBufRead(bufRead!=null?bufRead:"F");
		obj.setReaderId(null);
		obj.setTourSegment(null);
		obj.setAvcAxles(AvcAxles !=null ? AvcAxles : 0);
		obj.setTagIagClass(tagIAGClass!=null?tagIAGClass:0);
		obj.setAvcClass(AvcClass!=null?AvcClass:0);
		obj.setTagClass(tagClass!=null?tagClass:0);
		obj.setLaneHealth(laneHealth == null || laneHealth.equals("****") ? 0 : Convertor.toLong(laneHealth));
		obj.setPlanType(null);
		obj.setPlanCharged(null);
			
		obj.setCashFareAmount(0.0);
		obj.setEtcFareAmount(fullFareAmt!=null?fullFareAmt/100:0.0);
		obj.setDiscountedAmount(discountedFareOne!=null?discountedFareOne/100:0.0);
		obj.setDiscountedAmount2(discountedFareTwo!=null?discountedFareTwo/100:0.0);
		obj.setVideoFareAmount(videoFareAmount!=null?videoFareAmount/100:0.0);
		obj.setExpectedRevenueAmount(tollAmount!=null?tollAmount/100:0.0);
		obj.setItolFareAmount(fullFareAmt!=null?fullFareAmt/100:0.0);
		
		obj.setUnrealizedAmount(0.0);
		
		obj.setLaneType(null);
		obj.setDebitCredit(null);
		obj.setAetFlag(aetFlag);
		obj.setFileSeqNum(null);
		//obj.setEtcViolObserved(etcViolObserved);
		
		obj.setDirection(direction);
		obj.setEvent(event);
		obj.setHovFlag(hovFlag);
			
		Tplaza plazaInfo = masterDataCacheInfo.getPlazabyorder(2);
		
		if(hovPresenceTime != null) 
		{
			try {
				LocalDateTime hpt = LocalDateTime.parse(hovPresenceTime,DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS"));
				obj.setHovPresenceTime( exitPlaza!=null ? timeZoneConv.zonedTxTimeOffset(hpt,  Long.valueOf(exitPlaza)): 
					timeZoneConv.zonedTxTimeOffset(hpt, Long.valueOf(plazaInfo.getPlazaId())));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info("Invalid value of minute of hour");
				OffsetDateTime DEFAULT_TIMESTAMP = OffsetDateTime.parse(Constants.DEFAULT_TIMESTAMP);
				obj.setTxTimeStamp(DEFAULT_TIMESTAMP);
			}
		}
		
		if(hovLostPresenceTime != null) 
		{
			try {
				LocalDateTime hlpt = LocalDateTime.parse(hovLostPresenceTime,DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS"));
				obj.setHovLostPresenceTime(exitPlaza!=null ? timeZoneConv.zonedTxTimeOffset(hlpt,  Long.valueOf(exitPlaza)): 
					timeZoneConv.zonedTxTimeOffset(hlpt, Long.valueOf(plazaInfo.getPlazaId())));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info("Invalid value of minute of hour");
				OffsetDateTime DEFAULT_TIMESTAMP = OffsetDateTime.parse(Constants.DEFAULT_TIMESTAMP);
				obj.setTxTimeStamp(DEFAULT_TIMESTAMP);
			}
		}
		
		
		obj.setAvcDiscrepancyFlag(avcDiscrepancyFlag);
		obj.setDegraded(degraded);
		obj.setUoCode(uoCode);
		obj.setAvcProfile(avcProfile);
		
		//Transaction Info
		obj.setTi1TagReadStatus(ti1TagReadStatus);
		obj.setTi2AdditionalTags(ti2AdditionalTags);
		obj.setTi3ClassMismatchFlag(ti3ClassMismatchFlag);
		obj.setTi4TagStatus(ti4TagStatus);
		obj.setTi5PaymentFlag(ti5PaymentFlag);
		obj.setTi6RevenueFlag(ti6RevenueFlag);
		
		return obj;

	}

	private boolean validate(String value) 
	{
		String matcher_String = "\\s{"+value.length()+"}|[*|0| ]{"+value.length()+"}";
        if(value.matches(matcher_String)) 
        {
            return true;
        }
        else
        {
            return false;
        }
	}

	public String getEntryTimeStamp() {
		return entryTimeStamp;
	}

	public void setEntryTimeStamp(String entryTimeStamp) {
		this.entryTimeStamp = entryTimeStamp;
	}

	public String getLicPlateType() {
		return licPlateType;
	}

	public void setLicPlateType(String licPlateType) {
		this.licPlateType = licPlateType;
	}

	public String getConfidenceLevel() {
		return confidenceLevel;
	}

	public void setConfidenceLevel(String confidenceLevel) {
		this.confidenceLevel = confidenceLevel;
	}

	public Double getDiscountedFareOne() {
		return discountedFareOne;
	}

	public void setDiscountedFareOne(Double discountedFareOne) {
		this.discountedFareOne = discountedFareOne;
	}

	public Double getDiscountedFareTwo() {
		return discountedFareTwo;
	}

	public void setDiscountedFareTwo(Double discountedFareTwo) {
		this.discountedFareTwo = discountedFareTwo;
	}

	public Double getVideoFareAmount() {
		return videoFareAmount;
	}

	public void setVideoFareAmount(Double videoFareAmount) {
		this.videoFareAmount = videoFareAmount;
	}

	public String getPlanCharged() {
		return planCharged;
	}

	public void setPlanCharged(String planCharged) {
		this.planCharged = planCharged;
	}

	public Long getAtrnPlanType() {
		return atrnPlanType;
	}

	public void setAtrnPlanType(Long atrnPlanType) {
		this.atrnPlanType = atrnPlanType;
	}

	public String getTxTimeStamp() {
		return txTimeStamp;
	}

	public void setTxTimeStamp(String txTimeStamp) {
		this.txTimeStamp = txTimeStamp;
	}

	@Override
	public String toString() {
		return "Transaction [laneTxId=" + laneTxId + ", txExternRefNo=" + txExternRefNo + ", trxType=" + trxType
				+ ", trxSubtype=" + trxSubtype + ", tollSystemType=" + tollSystemType + ", tollRevenueType="
				+ tollRevenueType + ", accountType=" + accountType + ", entryDate=" + entryDate + ", entryTime="
				+ entryTime + ", entryLaneId=" + entryLaneId + ", entryLaneSN=" + entryLaneSN + ", entryDataSource="
				+ entryDataSource + ", entryVehicleSpeed=" + entryVehicleSpeed + ", deviceAgencyId=" + deviceAgencyId
				+ ", deviceNumber=" + deviceNumber + ", laneType=" + laneType + ", laneMode=" + laneMode
				+ ", laneState=" + laneState + ", collectorNumber=" + collectorNumber + ", actualClass=" + actualClass
				+ ", actualAxles=" + actualAxles + ", extraAxles=" + extraAxles + ", tagClass=" + tagClass
				+ ", tagAxles=" + tagAxles + ", tagIAGClass=" + tagIAGClass + ", AvcClass=" + AvcClass + ", AvcAxles="
				+ AvcAxles + ", EtcValidation=" + EtcValidation + ", readPerf=" + readPerf + ", writePerf=" + writePerf
				+ ", progStat=" + progStat + ", imageCaptured=" + imageCaptured + ", vehicleSpeed=" + vehicleSpeed
				+ ", speedViolation=" + speedViolation + ", reciprocityTrx=" + reciprocityTrx + ", trxDate=" + trxDate
				+ ", trxTime=" + trxTime + ", trxLaneId=" + trxLaneId + ", trxLaneSN=" + trxLaneSN + ", bufRead="
				+ bufRead + ", etcAccountId=" + etcAccountId + ", etcSubAccount=" + etcSubAccount + ", debitCredit="
				+ debitCredit + ", tollAmount=" + tollAmount + ", licensePlate=" + licensePlate + ", licenseState="
				+ licenseState + ", laneHealth=" + laneHealth + ", readerId=" + readerId + ", isPeak=" + isPeak
				+ ", fareType=" + fareType + ", fullFareAmt=" + fullFareAmt + ", extHostRefNum=" + extHostRefNum
				+ ", extFileId=" + extFileId + ", receivedDate=" + receivedDate + ", entryPlaza=" + entryPlaza
				+ ", exitPlaza=" + exitPlaza + ", entryLane=" + entryLane + ", exitLane=" + exitLane
				+ ", externPlazaId=" + externPlazaId + ", externLaneId=" + externLaneId + ", correction=" + correction
				+ ", tourSegmentId=" + tourSegmentId + ", dstFlag=" + dstFlag + ", laneDataSource=" + laneDataSource
				+ ", plazaAgencyId=" + plazaAgencyId + ", acctAgencyId=" + acctAgencyId + ", reserved=" + reserved
				+ ", isViolation=" + isViolation + ", isLprEnabled=" + isLprEnabled + ", frontOcrPlateCountry="
				+ frontOcrPlateCountry + ", frontOcrPlateState=" + frontOcrPlateState + ", frontOcrPlateType="
				+ frontOcrPlateType + ", frontOcrPlateNumber=" + frontOcrPlateNumber + ", frontOcrConfidence="
				+ frontOcrConfidence + ", frontOcrImageIndex=" + frontOcrImageIndex + ", frontImageColor="
				+ frontImageColor + ", rearOcrPlateCountry=" + rearOcrPlateCountry + ", rearOcrPlateState="
				+ rearOcrPlateState + ", rearOcrPlateType=" + rearOcrPlateType + ", rearOcrPlateNumber="
				+ rearOcrPlateNumber + ", rearOcrConfidence=" + rearOcrConfidence + ", rearOcrImageIndex="
				+ rearOcrImageIndex + ", rearImageColor=" + rearImageColor + ", plateCountry=" + plateCountry
				+ ", plateState=" + plateState + ", plateType=" + plateType + ", plateNumber=" + plateNumber
				+ ", txTimeStamp=" + txTimeStamp + ", discountAmount1=" + discountAmount1 + ", discountAmount2="
				+ discountAmount2 + ", licPlateType=" + licPlateType + ", confidenceLevel=" + confidenceLevel
				+ ", discountedFareOne=" + discountedFareOne + ", discountedFareTwo=" + discountedFareTwo
				+ ", videoFareAmount=" + videoFareAmount + ", planCharged=" + planCharged + ", atrnPlanType="
				+ atrnPlanType + ", entryTimeStamp=" + entryTimeStamp + ", aetFlag=" + aetFlag + ", postedFareAmount="
				+ postedFareAmount + ", expectedReveneueAmount=" + expectedReveneueAmount + ", fileNum=" + fileNum
				+ ", agTrxType=" + agTrxType + ", transType=" + transType + ", direction=" + direction + ", event="
				+ event + ", hovFlag=" + hovFlag + ", hovPresenceTime=" + hovPresenceTime + ", hovLostPresenceTime="
				+ hovLostPresenceTime + ", avcDiscrepancyFlag=" + avcDiscrepancyFlag + ", degraded=" + degraded
				+ ", uoCode=" + uoCode + ", avcProfile=" + avcProfile + ", ti1TagReadStatus=" + ti1TagReadStatus
				+ ", ti2AdditionalTags=" + ti2AdditionalTags + ", ti3ClassMismatchFlag=" + ti3ClassMismatchFlag
				+ ", ti4TagStatus=" + ti4TagStatus + ", ti5PaymentFlag=" + ti5PaymentFlag + ", ti6RevenueFlag="
				+ ti6RevenueFlag + "]";
	}

	
	
	

}
