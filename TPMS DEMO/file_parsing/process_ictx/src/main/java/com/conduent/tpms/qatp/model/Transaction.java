package com.conduent.tpms.qatp.model;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.constants.Constants;
import com.conduent.tpms.qatp.constants.QATPConstants;
import com.conduent.tpms.qatp.dto.Tplaza;
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
	private String plateType;
	private String plateNumber;
	private String txTimeStamp;

	private String discountAmount1;
	private String discountAmount2;
	private String licPlateType;
	private String confidenceLevel;
	private Double discountedFareOne;
	private Double discountedFareTwo;
	private Double videoFareAmount;
	private Double etcFareAmount;	
	
	private String planCharged;
	private Long atrnPlanType;
	private Timestamp entryTimeStamp;
	private String aetFlag;
	private Double postedFareAmount;
	private Double expectedReveneueAmount;
	private Integer fileNum;
	private String etcTrxType;
	private Integer etcViolObserved;
	private String etcRevenueDate;
	private String etcFacAgency;
	
	
	
	public String getEtcRevenueDate() {
		return etcRevenueDate;
	}

	public void setEtcRevenueDate(String etcRevenueDate) {
		this.etcRevenueDate = etcRevenueDate;
	}

	public String getEtcFacAgency() {
		return etcFacAgency;
	}

	public void setEtcFacAgency(String etcFacAgency) {
		this.etcFacAgency = etcFacAgency;
	}

	public Double getEtcFareAmount() {
		return etcFareAmount;
	}

	public void setEtcFareAmount(Double etcFareAmount) {
		this.etcFareAmount = etcFareAmount;
	}

	public String getEtcTrxType() {
		return etcTrxType;
	}
	
	public Integer getEtcViolObserved() {
		return etcViolObserved;
	}

	public void setEtcViolObserved(Integer etcViolObserved) {
		this.etcViolObserved = etcViolObserved;
	}

	public String getEtcTrxTypee() {
		return etcTrxType;
	}

	public void setEtcTrxType(String etcTrxType) {
		this.etcTrxType = etcTrxType;
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

	public String getPlateType() {
		return plateType;
	}

	public void setPlateType(String plateType) {
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
		
		//Amounts
		obj.setEtcFareAmount(etcFareAmount != null ? etcFareAmount/100 : 0d);
		obj.setExpectedRevenueAmount(expectedReveneueAmount != null ? expectedReveneueAmount/100 : 0d);
		obj.setPostedFareAmount(postedFareAmount != null ? postedFareAmount/100 : 0d);
		obj.setCashFareAmount(0d);
		obj.setDiscountedAmount(0d);
		obj.setDiscountedAmount2(0d);
		obj.setItolFareAmount(0d);
		obj.setVideoFareAmount(0d);
		obj.setUnrealizedAmount(0d);
		
		//Axles
		obj.setActualAxles(actualAxles != null ? actualAxles : 0);
		obj.setExtraAxles(0);
		obj.setTagAxles(0);
		obj.setAvcAxles(0);
		
		//Class
		obj.setActualClass(actualClass != null ? actualClass : 0);
		obj.setTagIagClass(0L);
		obj.setAvcClass(0L);
		obj.setTagClass(0L);
		obj.setDeviceAgencyClass(null);
		obj.setDeviceCodedClass(null);
		obj.setDeviceIagClass(null);
		
		//Date
		obj.setReceivedDate(DateUtils.parseLocalDate(receivedDate, "yyyyMMdd"));
		
		//Time
		obj.setUpdateTs(timeZoneConv.currentTime());
		
		//Entry Details
		obj.setEntryDataSource(null);
		obj.setEntryLaneId(0);
		obj.setEntryPlazaId(0);
		obj.setEntryTxSeqNumber(0);
		obj.setEntryTxTimeStamp(null);
		obj.setEntryVehicleSpeed(0);
		
		//Flag
		obj.setAetFlag(aetFlag.equals("*")?"F":aetFlag);
		obj.setDstFlag(dstFlag.equals("*")?"F":dstFlag);
	
		//Id
		obj.setExtFileId(extFileId != null ? extFileId : null);
		obj.setPlazaAgencyId(plazaAgencyId != null ? plazaAgencyId : null);
		obj.setPlazaId(exitPlaza != null? exitPlaza : null);
		obj.setExternPlazaId(externPlazaId != null ? externPlazaId : null);
		obj.setExternLaneId(externLaneId);
		obj.setAccAgencyId(null);
		obj.setDepositId(null);
		obj.setEtcAccountId(null);
		obj.setReaderId(null);
		obj.setTourSegment(0L);
		
		//Lane Info
		obj.setLaneDataSource(null);
		obj.setLaneHealth(null);
		obj.setLaneId(trxLaneId !=null ? trxLaneId : null);
		obj.setLaneMode(laneMode != null ? Integer.parseInt(laneMode) : 0);
		obj.setLaneState(null);
		obj.setLaneType(null);
		obj.setLaneSn(null);
		
		//Performance
		obj.setReadPerf(readPerf != null ? readPerf : 0);
		obj.setWritePerf(writePerf != null ? writePerf : 0);
		
		//Plan Info
		obj.setPlanCharged(null);
		obj.setPlanType(null);
		
		//PlateInfo
		obj.setPlateCountry(null);
		obj.setPlateType(plateType.equals("******************************") ? null : plateState);
		obj.setPlateState(plateState.equals("**") ? null : plateState);
		obj.setPlateNumber(plateNumber.equals("**********") ? null : plateNumber);
		
		//Speed
		obj.setSpeedViolation(speedViolation != null ? speedViolation : 0 );
		obj.setVehicleSpeed(vehicleSpeed != null ? vehicleSpeed : 0);
		
		//Tolling Info
		obj.setTollRevenueType(tollRevenueType != null ? tollRevenueType : null);
		obj.setTollSystemType(tollSystemType != null ? tollSystemType : null);
		
		//Transaction Info
		obj.setTxType(trxType);
		obj.setTxSubType(trxSubtype);
		
		if (extHostRefNum != null) 
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
				try {
					LocalDateTime temp = LocalDateTime.parse(txTimeStamp,DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
					
					if(exitPlaza != null) 
					{
						obj.setTxTimeStamp(timeZoneConv.zonedTxTimeOffset(temp,  Long.valueOf(exitPlaza)));
						obj.setTxDate(timeZoneConv.zonedTxTimeOffset(temp,  Long.valueOf(exitPlaza)).toLocalDate());
					}
					else
					{
						if(plazaAgencyId == Integer.valueOf(QATPConstants.PBA_AGENCY_ID)) 
						{
							Tplaza plazaInfo = masterDataCacheInfo.getPlazabyorder(19);
							obj.setTxTimeStamp(timeZoneConv.zonedTxTimeOffset(temp,  Long.valueOf(plazaInfo.getPlazaId())));
							obj.setTxDate(timeZoneConv.zonedTxTimeOffset(temp,  Long.valueOf(plazaInfo.getPlazaId())).toLocalDate());
						}
						else if (plazaAgencyId == Integer.valueOf(QATPConstants.TIBA_AGENCY_ID)) 
						{
							Tplaza plazaInfo = masterDataCacheInfo.getPlazabyorder(63);
							obj.setTxTimeStamp(timeZoneConv.zonedTxTimeOffset(temp,  Long.valueOf(plazaInfo.getPlazaId())));
							obj.setTxDate(timeZoneConv.zonedTxTimeOffset(temp,  Long.valueOf(plazaInfo.getPlazaId())).toLocalDate());
						}
					}
				}
				catch (Exception e) 
				{
					logger.info("Invalid value of minute of hour");
					OffsetDateTime DEFAULT_TIMESTAMP = OffsetDateTime.parse(Constants.DEFAULT_TIMESTAMP);
					obj.setTxTimeStamp(DEFAULT_TIMESTAMP);
					obj.setTxDate(DEFAULT_TIMESTAMP.toLocalDate());
					obj.setTxType("R");
					obj.setTxSubType("D");
				}
			}
		}
		
		//Type
		obj.setAccountType(null);
		obj.setFareType(null);
		obj.setEtcTrxType(etcTrxType != null ? etcTrxType : null);
		
		//Others	
		obj.setCollectorNumber(null);
		obj.setImageCaptured(null);
		obj.setIsPeak(null);
		obj.setIsViolation(String.valueOf(isViolation));
		obj.setBufRead(null);
		obj.setReserved(null);
		obj.setDeviceNo( (deviceNumber!=null && validate(deviceNumber))==true ? null : deviceNumber);
		obj.setDebitCredit(debitCredit != null ? debitCredit : null);
		obj.setReciprocityTrx(reciprocityTrx != null ? reciprocityTrx : null);	
		obj.setFileSeqNum(fileNum);		
		obj.setEtcViolObserved(etcViolObserved);
		
		if(progStat.equals("S")) 
		{
			obj.setProgStat(ProgState.valueOf("S").ordinal());
		}
		else if(progStat.equals("U"))
		{
			obj.setProgStat(ProgState.valueOf("U").ordinal());
		}
		else if(progStat.equals("F")) 
		{
			obj.setProgStat(ProgState.valueOf("F").ordinal());
		}
		else 
		{
			obj.setProgStat(ProgState.valueOf("STAR").ordinal());
		}
		
		//Extra Fields
		obj.setRevenueDate(DateUtils.parseLocalDate(etcRevenueDate, "yyyyMMdd"));
		obj.setFacAgency(Long.parseLong(etcFacAgency));
		obj.setEtcValidStatus(EtcValidation != null ? EtcValidation : null);
		
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

	public Timestamp getEntryTimeStamp() {
		return entryTimeStamp;
	}

	public void setEntryTimeStamp(Timestamp entryTimeStamp) {
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
				+ ", videoFareAmount=" + videoFareAmount + ", etcFareAmount=" + etcFareAmount + ", planCharged="
				+ planCharged + ", atrnPlanType=" + atrnPlanType + ", entryTimeStamp=" + entryTimeStamp + ", aetFlag="
				+ aetFlag + ", postedFareAmount=" + postedFareAmount + ", expectedReveneueAmount="
				+ expectedReveneueAmount + ", fileNum=" + fileNum + ", etcTrxType=" + etcTrxType + ", etcViolObserved="
				+ etcViolObserved + ", etcRevenueDate=" + etcRevenueDate + ", etcFacAgency=" + etcFacAgency + "]";
	}

	
	
	

}
