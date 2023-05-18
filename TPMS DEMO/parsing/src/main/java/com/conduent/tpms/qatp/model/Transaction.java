package com.conduent.tpms.qatp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.constants.Constants;
import com.conduent.tpms.qatp.constants.FOConstants;
import com.conduent.tpms.qatp.dto.Plaza;
import com.conduent.tpms.qatp.parser.agency.FOTrnxFixlengthParser;
import com.conduent.tpms.qatp.utility.Convertor;
import com.conduent.tpms.qatp.utility.DateUtils;
import com.conduent.tpms.qatp.utility.MasterDataCache;

@Component
public class Transaction 
{
	
	private static final Logger logger = LoggerFactory.getLogger(Transaction.class);
	
	@Autowired
	protected MasterDataCache masterDataCacheInfo;
	
	private Long laneTxId;         
	private Long txExternRefNo;
	private String matchedTxExternRefNo;
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
	private Integer tagClass;
	private Integer tagAxles;
	private Integer tagIAGClass;
	private Integer AvcClass;
	private Integer avcAxles;
	private Long EtcValidation;
	private Integer readPerf;
	private Integer writePerf;
	private String progStat;
	private String imageCaptured;
	private Integer vehicleSpeed;
	private String speedViolation;
	private String reciprocityTrx;
	private String trxDate;
	private String trxTime;
	private Integer trxLaneId;
	private Long trxLaneSN;
	private String bufRead;
	private Long etcAccountId;
	private Long etcSubAccount;
	private String debitCredit;
	private Long tollAmount;
	private String licensePlate;
	private String licenseState;
	private String laneHealth;
	private String readerId;
	private String isPeak;
	private String fareType;
	private Long fullFareAmt;
	private String extHostRefNum;
	private Long extFileId;
	private String receivedDate;
	private Integer entryPlaza;
	private Integer entryLane;
	private Integer exitPlaza;
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
	private String plateCountry;
	private String plateState;
	private Integer plateType;
	private String plateNumber;
	private String txTimeStamp;
	private String entryTimeStamp;
	private String aetFlag;
	private double etcTollAmount;
	private Long atpFileId;
	private Integer fileNum;
	private Integer facAgency;
	
	
	
	public Integer getFacAgency() {
		return facAgency;
	}

	public void setFacAgency(Integer facAgency) {
		this.facAgency = facAgency;
	}

	public Integer getFileNum() {
		return fileNum;
	}

	public void setFileNum(Integer fileNum) {
		this.fileNum = fileNum;
	}
	
	public Long getAtpFileId() {
		return atpFileId;
	}
	public void setAtpFileId(Long atpFileId) {
		this.atpFileId = atpFileId;
	}
	public double getEtcTollAmount() {
		return etcTollAmount;
	}
	public void setEtcTollAmount(double etcTollAmount) {
		this.etcTollAmount = etcTollAmount;
	}
	public String getMatchedTxExternRefNo() {
		return matchedTxExternRefNo;
	}
	public void setMatchedTxExternRefNo(String matchedTxExternRefNo) {
		this.matchedTxExternRefNo = matchedTxExternRefNo;
	}
	public String getAetFlag() {
		return aetFlag;
	}
	public void setAetFlag(String aetFlag) {
		this.aetFlag = aetFlag;
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
	public Integer getTagIAGClass() {
		return tagIAGClass;
	}
	public void setTagIAGClass(Integer tagIAGClass) {
		this.tagIAGClass = tagIAGClass;
	}

	public Integer getAvcClass() {
		return AvcClass;
	}
	public void setAvcClass(Integer avcClass) {
		AvcClass = avcClass;
	}

	public Integer getAvcAxles() {
		return avcAxles;
	}
	public void setAvcAxles(Integer avcAxles) {
		this.avcAxles = avcAxles;
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
	public String getSpeedViolation() {
		return speedViolation;
	}
	public void setSpeedViolation(String speedViolation) {
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
	public Long getTollAmount() {
		return tollAmount;
	}
	public void setTollAmount(Long tollAmount) {
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

	public Long getFullFareAmt() {
		return fullFareAmt;
	}
	public void setFullFareAmt(Long fullFareAmt) {
		this.fullFareAmt = fullFareAmt;
	}

	public String getExtHostRefNum() {
		return extHostRefNum;
	}
	public void setExtHostRefNum(String extHostRefNum) {
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


	public String getTxTimeStamp() {
		return txTimeStamp;
	}
	public void setTxTimeStamp(String txTimeStamp) {
		this.txTimeStamp = txTimeStamp;
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
	public String getEntryTimeStamp() {
		return entryTimeStamp;
	}
	public void setEntryTimeStamp(String entryTimeStamp) {
		this.entryTimeStamp = entryTimeStamp;
	}
	public TranDetail getTranDetail(TimeZoneConv timeZoneConv,MasterDataCache masterDataCacheInfo)
	{
		TranDetail obj=new TranDetail();
		
		obj.setAccAgencyId(0); 
		
		obj.setAccountType(0);
		
		obj.setActualAxles(actualAxles);
		
		obj.setActualClass(0);
		
		obj.setAetFlag(aetFlag);
		
		//obj.setAtpFileId(atpFileId);
		
		obj.setDeviceNo(deviceNumber);
		
		if(extHostRefNum!=null)
		{
			obj.setTxExternRefNo(StringUtils.leftPad(String.valueOf(extHostRefNum), 16, "0"));
		}
		else
		{
			obj.setTxExternRefNo("0000000000000000");
		}
		
		obj.setExtFileId(extFileId);
		
		obj.setLaneId(exitLane);
		
		obj.setPlazaId(exitPlaza);
		
		obj.setPlazaAgencyId(plazaAgencyId);
		
		obj.setTxType(trxType); 
		
		obj.setTxSubType(trxSubtype);
		
		LocalDate date = DateUtils.parseLocalDateRecieved(receivedDate, "yyyy-MM-dd");
		if(date!=null)
		{
			obj.setReceivedDate(date);
		}
//		else
//		{
//			logger.info("Invalid recieved date");
//			obj.setTxType("R");
//			obj.setTxSubType("R");
//		}
		
		obj.setTxDate(DateUtils.parseLocalDate(trxDate, "yyyyMMdd"));
		
		if(txTimeStamp!=null)
		{	
			if(DateUtils.parseLocalDate(txTimeStamp.substring(0, 8),"yyyyMMdd") != null) {
				try 
				{
					LocalDateTime temp=LocalDateTime.parse(txTimeStamp,DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
					
					if(exitPlaza != null ) 
					{
						obj.setTxTimeStamp(timeZoneConv.zonedTxTimeOffset(temp,  Long.valueOf(exitPlaza)));
					}
					else
					{
						Plaza plazaInfo = masterDataCacheInfo.getPlazabyorder(1);
						obj.setTxTimeStamp(timeZoneConv.zonedTxTimeOffset(temp,  Long.valueOf(plazaInfo.getPlazaId())));
					}
				} catch (Exception e) 
				{
					logger.info("Invalid value of minute of hour");
					OffsetDateTime DEFAULT_TIMESTAMP = OffsetDateTime.parse(Constants.DEFAULT_TIMESTAMP);
					obj.setTxTimeStamp(DEFAULT_TIMESTAMP);
					obj.setTxType("R");
					obj.setTxSubType("D");
				}
			}
		}
		
		obj.setLaneMode(Convertor.toInteger(laneMode));
		
		obj.setProgStat(Convertor.toInteger(progStat));
		
		obj.setVehicleSpeed(vehicleSpeed);
		
		obj.setSpeedViolation(Convertor.toInteger(speedViolation));
		
		obj.setEtcValidStatus(EtcValidation);
		
		obj.setEntryLaneId(entryLaneId);
		
		obj.setEntryPlazaId(entryPlaza);
		
		if(entryTimeStamp!=null && !entryTimeStamp.equals("****************"))
		{
			if(DateUtils.parseLocalDate(entryTimeStamp.substring(0, 8),"yyyyMMdd") != null) 
			{
				try {
					LocalDateTime txTimeStamp=LocalDateTime.parse(entryTimeStamp,DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

					if(exitPlaza != null) 
					{
						obj.setEntryTxTimeStamp(timeZoneConv.zonedTxTimeOffset(txTimeStamp,  Long.valueOf(exitPlaza)));
						System.out.println(timeZoneConv.zonedTxTimeOffset(txTimeStamp,  Long.valueOf(exitPlaza)));
					}
					else
					{
						Plaza plazaInfo = masterDataCacheInfo.getPlazabyorder(1);
						obj.setEntryTxTimeStamp(timeZoneConv.zonedTxTimeOffset(txTimeStamp,Long.valueOf(plazaInfo.getPlazaId())));
					}
				} catch (Exception e) 
				{
					logger.info("Invalid value of minute of hour");
					OffsetDateTime DEFAULT_TIMESTAMP = OffsetDateTime.parse(Constants.DEFAULT_TIMESTAMP);
					obj.setEntryTxTimeStamp(DEFAULT_TIMESTAMP);
					obj.setTxType("R");
					obj.setTxSubType("D");
				}
			}
		}
		else
		{
			logger.info("Invalid Entry Timestamp {}",entryTimeStamp);
		}
		
		obj.setTollRevenueType(tollRevenueType);
		
		obj.setExpectedRevenueAmount(Convertor.toDouble(etcTollAmount/100));
		
		obj.setPostedFareAmount(Convertor.toDouble(etcTollAmount/100));
		
		obj.setEtcFareAmount(Convertor.toDouble(etcTollAmount/100));
		
		obj.setDiscountedAmount2(0D);
		
		obj.setVideoFareAmount(0D);
		
		obj.setDiscountedAmount(0D);
		
		obj.setUnrealizedAmount(0D);
		
		obj.setCashFareAmount(0D);
		
		obj.setVideoFareAmount(0D);
		
		obj.setDeviceCodedClass(0);
		
		obj.setEtcAccountId(null);
		
		obj.setEtcSubAccount(null);
		
		obj.setTxStatus(FOConstants.QFTOL);
		
		obj.setPlateState(plateState);
		
		obj.setPlateNumber(plateNumber);
		
		obj.setPlateCountry("***");
		
		obj.setReadPerf(readPerf);
		
		//obj.setReceivedDate(DateUtils.parseLocalDate(receivedDate, "yyyyMMdd"));
		
		obj.setUpdateTs(LocalDateTime.now());
		
		obj.setWritePerf(writePerf);
		
		obj.setDebitCredit(debitCredit);
		
		obj.setTollSystemType(tollSystemType);
		
		obj.setFileSeqNum(fileNum);
		
		obj.setExternPlazaId(externPlazaId);
		
		obj.setExternLaneId(externLaneId);
		
		obj.setTollAmount(Convertor.toDouble(etcTollAmount/100));
		
		obj.setFacAgency(facAgency);
		
		System.out.println(obj);
		
		return obj;
	}
	
	@Override
	public String toString() {
		return "Transaction [masterDataCacheInfo=" + masterDataCacheInfo + ", laneTxId=" + laneTxId + ", txExternRefNo="
				+ txExternRefNo + ", matchedTxExternRefNo=" + matchedTxExternRefNo + ", trxType=" + trxType
				+ ", trxSubtype=" + trxSubtype + ", tollSystemType=" + tollSystemType + ", tollRevenueType="
				+ tollRevenueType + ", accountType=" + accountType + ", entryDate=" + entryDate + ", entryTime="
				+ entryTime + ", entryLaneId=" + entryLaneId + ", entryLaneSN=" + entryLaneSN + ", entryDataSource="
				+ entryDataSource + ", entryVehicleSpeed=" + entryVehicleSpeed + ", deviceAgencyId=" + deviceAgencyId
				+ ", deviceNumber=" + deviceNumber + ", laneType=" + laneType + ", laneMode=" + laneMode
				+ ", laneState=" + laneState + ", collectorNumber=" + collectorNumber + ", actualClass=" + actualClass
				+ ", actualAxles=" + actualAxles + ", extraAxles=" + extraAxles + ", tagClass=" + tagClass
				+ ", tagAxles=" + tagAxles + ", tagIAGClass=" + tagIAGClass + ", AvcClass=" + AvcClass + ", avcAxles="
				+ avcAxles + ", EtcValidation=" + EtcValidation + ", readPerf=" + readPerf + ", writePerf=" + writePerf
				+ ", progStat=" + progStat + ", imageCaptured=" + imageCaptured + ", vehicleSpeed=" + vehicleSpeed
				+ ", speedViolation=" + speedViolation + ", reciprocityTrx=" + reciprocityTrx + ", trxDate=" + trxDate
				+ ", trxTime=" + trxTime + ", trxLaneId=" + trxLaneId + ", trxLaneSN=" + trxLaneSN + ", bufRead="
				+ bufRead + ", etcAccountId=" + etcAccountId + ", etcSubAccount=" + etcSubAccount + ", debitCredit="
				+ debitCredit + ", tollAmount=" + tollAmount + ", licensePlate=" + licensePlate + ", licenseState="
				+ licenseState + ", laneHealth=" + laneHealth + ", readerId=" + readerId + ", isPeak=" + isPeak
				+ ", fareType=" + fareType + ", fullFareAmt=" + fullFareAmt + ", extHostRefNum=" + extHostRefNum
				+ ", extFileId=" + extFileId + ", receivedDate=" + receivedDate + ", entryPlaza=" + entryPlaza
				+ ", entryLane=" + entryLane + ", exitPlaza=" + exitPlaza + ", exitLane=" + exitLane
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
				+ ", txTimeStamp=" + txTimeStamp + ", entryTimeStamp=" + entryTimeStamp + ", aetFlag=" + aetFlag
				+ ", etcTollAmount=" + etcTollAmount + "]";
	}
	
	
	
}
