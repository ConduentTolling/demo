package com.conduent.tpms.qatp.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.utility.Convertor;
import com.conduent.tpms.qatp.utility.DateUtils;
import com.conduent.tpms.qatp.utility.MasterDataCache;
import com.google.gson.annotations.Expose;

public class QatpBufferRecordType {

	
	private String plazaId;				
	private Integer  laneId; 				
	private String  laneConfig;         	
	private String onTime;         		
	private String collectorId;           
	private String  readerId;     			
	private String recordNumber;			
	private String tagId;   			
	private String agencyId;   			
	private String tranDetailSrno;
	private String  aviClass;   			
	private String tagClass;   			
	private String vehicleClass;   		
	private String avcProfile;   				
	private String avcAxles;   				
	private String  laneNumber;   			
	private String transitDate;   			
	private String  transitTime;   			
    private String  transactionInfo;
	private String vehicleSpeed;
	private String  fOcrPlateCountry;
	private String fOcrPlateState;
	private String fOcrPlateType;
	private String fOcrPlateNumber;
	private String fOcrConfidence;
	private String fOcrImageIndex;
	private String  fOcrImageColor;
	private String  rOcrPlateCountry;
	private String rOcrPlateState;
	private String rOcrPlateType;
	private String rOcrPlateNumber;
	private String rOcrConfidence;
	private String rOcrImageIndex;
	private String rOcrImageColor;
	private String  event;					
	private String HOV;					
	private String  presenceTime;		
	private String lostpresenceTime;	
	private String exceptionCode;
	private Long lcEtcaccountid,lcSubaccountid;
    private String lcDeviceno;
    private Integer accountType;
    private Integer lcAcctFinStatus;
    private Long balance;
    private Long lcIagclass;
    private Integer tagFlag;
    private String transitDateTime; 
    private Integer lcDeviceStatus;
    private Integer lcRebillType;
    private Integer acctAgencyId;
    private Integer isOtgUnreg;
    private Integer	OTGDeviceStatus;	
    private String trxType;
	private String trxSubtype;
	private String reciprocityTrx;
	private Integer plazaAgencyId;
	private String isLprEnabled;
	private String externPlazaId;
	private String aetFlag;
	private Integer tollRevenueType;


	public String getAetFlag() {
		return aetFlag;
	}
	public void setAetFlag(String aetFlag) {
		this.aetFlag = aetFlag;
	}

	public Integer getTollRevenueType() {
		return tollRevenueType;
	}
	public void setTollRevenueType(Integer tollRevenueType) {
		this.tollRevenueType = tollRevenueType;
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
	public void setLcIagclass(Long lcIagclass) {
		this.lcIagclass = lcIagclass;
	}

	private String externLaneId;
    public String getIsLprEnabled() {
		return isLprEnabled;
	}
	public void setIsLprEnabled(String isLprEnabled) {
		this.isLprEnabled = isLprEnabled;
	}
	public Integer getPlazaAgencyId() {
		return plazaAgencyId;
	}
	public void setPlazaAgencyId(Integer plazaAgencyId) {
		this.plazaAgencyId = plazaAgencyId;
	}
	public String getReciprocityTrx() {
		return reciprocityTrx;
	}
	public void setReciprocityTrx(String reciprocityTrx) {
		this.reciprocityTrx = reciprocityTrx;
	}
	public String getTransactionInfo() {
		return transactionInfo;
	}
	public void setTransactionInfo(String transactionInfo) {
		this.transactionInfo = transactionInfo;
	}

	public String getPlazaId() {
		return plazaId;
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
	public void setPlazaId(String plazaId) {
		this.plazaId = plazaId;
	}
	public Integer getLaneId() {
		return laneId;
	}
	public void setLaneId(Integer laneId) {
		this.laneId = laneId;
	}
	public String getLaneConfig() {
		return laneConfig;
	}
	public void setLaneConfig(String laneConfig) {
		this.laneConfig = laneConfig;
	}
	public String getOnTime() {
		return onTime;
	}
	public void setOnTime(String onTime) {
		this.onTime = onTime;
	}
	public String getCollectorId() {
		return collectorId;
	}
	public void setCollectorId(String collectorId) {
		this.collectorId = collectorId;
	}
	public String getReaderId() {
		return readerId;
	}
	public void setReaderId(String readerId) {
		this.readerId = readerId;
	}
	public String getRecordNumber() {
		return recordNumber;
	}
	public void setRecordNumber(String recordNumber) {
		this.recordNumber = recordNumber;
	}
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	public String getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}
	public String getTranDetailSrno() {
		return tranDetailSrno;
	}
	public void setTranDetailSrno(String tranDetailSrno) {
		this.tranDetailSrno = tranDetailSrno;
	}
	public String getAviClass() {
		return aviClass;
	}
	public void setAviClass(String aviClass) {
		this.aviClass = aviClass;
	}
	public String getTagClass() {
		return tagClass;
	}
	public void setTagClass(String tagClass) {
		this.tagClass = tagClass;
	}
	public String getVehicleClass() {
		return vehicleClass;
	}
	public void setVehicleClass(String vehicleClass) {
		this.vehicleClass = vehicleClass;
	}
	public String getAvcProfile() {
		return avcProfile;
	}
	public void setAvcProfile(String avcProfile) {
		this.avcProfile = avcProfile;
	}
	public String getAvcAxles() {
		return avcAxles;
	}
	public void setAvcAxles(String avcAxles) {
		this.avcAxles = avcAxles;
	}
	public String getLaneNumber() {
		return laneNumber;
	}
	public void setLaneNumber(String laneNumber) {
		this.laneNumber = laneNumber;
	}
	public String getTransitDate() {
		return transitDate;
	}
	public void setTransitDate(String transitDate) {
		this.transitDate = transitDate;
	}
	public String getTransitTime() {
		return transitTime;
	}
	public void setTransitTime(String transitTime) {
		this.transitTime = transitTime;
	}
	public String getVehicleSpeed() {
		return vehicleSpeed;
	}
	public void setVehicleSpeed(String vehicleSpeed) {
		this.vehicleSpeed = vehicleSpeed;
	}
	public String getfOcrPlateCountry() {
		return fOcrPlateCountry;
	}
	public void setfOcrPlateCountry(String fOcrPlateCountry) {
		this.fOcrPlateCountry = fOcrPlateCountry;
	}
	public String getfOcrPlateState() {
		return fOcrPlateState;
	}
	public void setfOcrPlateState(String fOcrPlateState) {
		this.fOcrPlateState = fOcrPlateState;
	}
	public String getfOcrPlateType() {
		return fOcrPlateType;
	}
	public void setfOcrPlateType(String fOcrPlateType) {
		this.fOcrPlateType = fOcrPlateType;
	}
	public String getfOcrPlateNumber() {
		return fOcrPlateNumber;
	}
	public void setfOcrPlateNumber(String fOcrPlateNumber) {
		this.fOcrPlateNumber = fOcrPlateNumber;
	}
	public String getfOcrConfidence() {
		return fOcrConfidence;
	}
	public void setfOcrConfidence(String fOcrConfidence) {
		this.fOcrConfidence = fOcrConfidence;
	}
	public String getfOcrImageIndex() {
		return fOcrImageIndex;
	}
	public void setfOcrImageIndex(String fOcrImageIndex) {
		this.fOcrImageIndex = fOcrImageIndex;
	}
	public String getfOcrImageColor() {
		return fOcrImageColor;
	}
	public void setfOcrImageColor(String fOcrImageColor) {
		this.fOcrImageColor = fOcrImageColor;
	}
	public String getrOcrPlateCountry() {
		return rOcrPlateCountry;
	}
	public void setrOcrPlateCountry(String rOcrPlateCountry) {
		this.rOcrPlateCountry = rOcrPlateCountry;
	}
	public String getrOcrPlateState() {
		return rOcrPlateState;
	}
	public void setrOcrPlateState(String rOcrPlateState) {
		this.rOcrPlateState = rOcrPlateState;
	}
	public String getrOcrPlateType() {
		return rOcrPlateType;
	}
	public void setrOcrPlateType(String rOcrPlateType) {
		this.rOcrPlateType = rOcrPlateType;
	}
	public String getrOcrPlateNumber() {
		return rOcrPlateNumber;
	}
	public void setrOcrPlateNumber(String rOcrPlateNumber) {
		this.rOcrPlateNumber = rOcrPlateNumber;
	}
	public String getrOcrConfidence() {
		return rOcrConfidence;
	}
	public void setrOcrConfidence(String rOcrConfidence) {
		this.rOcrConfidence = rOcrConfidence;
	}
	public String getrOcrImageIndex() {
		return rOcrImageIndex;
	}
	public void setrOcrImageIndex(String rOcrImageIndex) {
		this.rOcrImageIndex = rOcrImageIndex;
	}
	public String getrOcrImageColor() {
		return rOcrImageColor;
	}
	public void setrOcrImageColor(String rOcrImageColor) {
		this.rOcrImageColor = rOcrImageColor;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getHOV() {
		return HOV;
	}
	public void setHOV(String hOV) {
		HOV = hOV;
	}
	public String getPresenceTime() {
		return presenceTime;
	}
	public void setPresenceTime(String presenceTime) {
		this.presenceTime = presenceTime;
	}
	public String getLostpresenceTime() {
		return lostpresenceTime;
	}
	public void setLostpresenceTime(String lostpresenceTime) {
		this.lostpresenceTime = lostpresenceTime;
	}
	public String getExceptionCode() {
		return exceptionCode;
	}
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	public Long getLcEtcaccountid() {
		return lcEtcaccountid;
	}
	public void setLcEtcaccountid(Long lcEtcaccountid) {
		this.lcEtcaccountid = lcEtcaccountid;
	}
	public Long getLcSubaccountid() {
		return lcSubaccountid;
	}
	public void setLcSubaccountid(Long lcSubaccountid) {
		this.lcSubaccountid = lcSubaccountid;
	}
	public String getLcDeviceno() {
		return lcDeviceno;
	}
	public void setLcDeviceno(String lcDeviceno) {
		this.lcDeviceno = lcDeviceno;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public Integer getLcAcctFinStatus() {
		return lcAcctFinStatus;
	}
	public void setLcAcctFinStatus(Integer lcAcctFinStatus) {
		this.lcAcctFinStatus = lcAcctFinStatus;
	}
	public Long getBalance() {
		return balance;
	}
	public void setBalance(Long balance) {
		this.balance = balance;
	}
	public Long getLcIagclass() {
		return lcIagclass;
	}
	public void setIcIagclass(Long lcIagclass) {
		this.lcIagclass = lcIagclass;
	}
	public Integer getTagFlag() {
		return tagFlag;
	}
	public void setTagFlag(Integer tagFlag) {
		this.tagFlag = tagFlag;
	}
	public String getTransitDateTime() {
		return transitDateTime;
	}
	public void setTransitDateTime(String transitDateTime) {
		this.transitDateTime = transitDateTime;
	}
	public Integer getLcDeviceStatus() {
		return lcDeviceStatus;
	}
	public void setLcDeviceStatus(Integer lcDeviceStatus) {
		this.lcDeviceStatus = lcDeviceStatus;
	}
	public Integer getLcRebillType() {
		return lcRebillType;
	}
	public void setLcRebillType(Integer lcRebillType) {
		this.lcRebillType = lcRebillType;
	}
	public Integer getAcctAgencyId() {
		return acctAgencyId;
	}
	public void setAcctAgencyId(Integer acctAgencyId) {
		this.acctAgencyId = acctAgencyId;
	}
	public Integer getIsOtgUnreg() {
		return isOtgUnreg;
	}
	public void setIsOtgUnreg(Integer isOtgUnreg) {
		this.isOtgUnreg = isOtgUnreg;
	}
	public Integer getOTGDeviceStatus() {
		return OTGDeviceStatus;
	}
	public void setOTGDeviceStatus(Integer oTGDeviceStatus) {
		OTGDeviceStatus = oTGDeviceStatus;
	}
	
	public TranDetail getTranDetail(TimeZoneConv timeZoneConv) throws ParseException {
		TranDetail obj = new TranDetail();
		obj.setAccAgencyId(null);
		obj.setAccountType(null);
		obj.setActualAxles(Convertor.toInteger(avcAxles));
		obj.setActualClass(Convertor.toInteger(aviClass));
		obj.setCollectorNumber(collectorId);
		obj.setTagClass(Convertor.toInteger(tagClass)!=null?Convertor.toInteger(tagClass):0);
		obj.setAvcClass(Convertor.toInteger(aviClass)!=null?Convertor.toInteger(aviClass):0);
		obj.setTagIagClass(Convertor.toInteger(vehicleClass)!=null?Convertor.toInteger(vehicleClass):0);
		obj.setTxSubType(trxSubtype);
		obj.setTxType(trxType);
		if(lcDeviceno.equals("00000000000") || lcDeviceno.equals("***********") || lcDeviceno.equals("           "))
		{
			obj.setDeviceNo(null);
			obj.setTxType("V");
			obj.setTxSubType("F");
		}
		else
		{
			obj.setDeviceNo(lcDeviceno);
		}
		obj.setDiscountedAmount(0.0);
		obj.setDstFlag("F");
		obj.setEntryDataSource(null);
		obj.setEntryLaneId(null);
		obj.setEntryPlazaId(null);
		obj.setEntryTxSeqNumber(null);
		obj.setEntryTxTimeStamp(null);
		obj.setEntryVehicleSpeed(null);
		obj.setEtcAccountId(null);
		obj.setEtcSubAccount(null);
		obj.setExtFileId(lcIagclass);
		obj.setExtraAxles((long) 0);
		obj.setFareType(0);
		obj.setFullFareAmount(0.0);
		obj.setImageCaptured(laneConfig.charAt(3)=='1' ? "T":"F"); 
		obj.setIsLprEnabled(isLprEnabled!=null?isLprEnabled:"F");
		obj.setIsPeak("F");
		//obj.setIsViolation(transactionInfo.charAt(3) !='0' ? "Y":"N");
		obj.setIsViolation(transactionInfo.charAt(3) !='0' ? "1":"0");
		obj.setLaneId((laneId));
		obj.setLaneMode(Convertor.toInteger(laneConfig)!=null?Convertor.toInteger(laneConfig):0);  // need to ask PB
		obj.setTrxLaneSerial((long) 0);
		obj.setLaneState(0);
		obj.setLaneType(0);
		obj.setPlateCountry(null);
		obj.setPlateNumber(null);
		obj.setPlateState(null);
		obj.setPlateType(0);
		obj.setPlazaAgencyId(plazaAgencyId);
		obj.setPlazaId(Convertor.toInteger(plazaId));
		obj.setProgStat(null);
		obj.setReadPerf(null);
		if(tagFlag!=null && !(tagFlag.toString().trim().isEmpty())) {
		String datePart = tagFlag.toString().substring(0, tagFlag.toString().length() - 2);
		obj.setReceivedDate(DateUtils.parseLocalDate(datePart, "yyyyMMdd")); 
		}
		obj.setSpeedViolation(0);
		obj.setTagAxles(Convertor.toInteger(avcAxles));
		obj.setTollRevenueType(tollRevenueType!=null?tollRevenueType:1);
		obj.setTollSystemType("B");
		if(transitDate!=null && !(transitDate.trim().isEmpty())) {
			obj.setTxDate(DateUtils.parseLocalDate(transitDate.toString(), "yyyyMMdd"));
		}
		 if (tranDetailSrno != null) {
	            obj.setTxExternRefNo(StringUtils.leftPad(String.valueOf(tranDetailSrno), 16, "0"));
	        } else {
	            obj.setTxExternRefNo("0000000000000000");
	        } 
		if(transitDateTime!=null) 
		{
			
			if(DateUtils.parseLocalDate(transitDateTime.substring(0, 8),"yyyyMMdd") != null) 
			{
				LocalDateTime temp = LocalDateTime.parse(transitDateTime,DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS"));
				
				//obj.setTxTimeStamp(DateUtils.getTimeStamp(transitDateTime, "yyyyMMddHHmmssSS").toLocalDateTime());
				if(plazaId!=null)
				{
					obj.setTxTimeStamp(timeZoneConv.zonedTxTimeOffset(temp, Long.valueOf(plazaId)));
				}
				
				
			}
		}
		
		obj.setUnrealizedAmount(0.0);
		obj.setUpdateTs(LocalDateTime.now()); 
		obj.setVehicleSpeed(Convertor.toInteger(vehicleSpeed));
		obj.setWritePerf(null);
		
		obj.setReciprocityTrx(reciprocityTrx);
		obj.setTransactionInfo(transactionInfo);
		obj.setPlanCharged(null);
		obj.setEtcTxStatus("0");
		obj.setDepositId(null);
		obj.setPostedDate(null);
		obj.setLaneDataSource("0");
		obj.setLaneType(0);
		obj.setLaneHealth(0);
		obj.setAvcAxles(Convertor.toInteger(avcAxles));
		obj.setTourSegmentId(0);
		obj.setBufRead("F");
		obj.setReaderId(readerId.equals("****")?null:readerId);
		obj.setTollAmount(0);
		obj.setDebitCredit("+");
		obj.setEtcValidStatus(0);
		obj.setDiscountedAmount2(0);
		obj.setVideoFareAmount(0);
		obj.setPlanType(0);
		obj.setReserved(null);
		obj.setAtpFileId(0);
		
		//OCR DATA
		obj.setFrontOcrPlateCountry(fOcrPlateCountry);
		obj.setFrontOcrPlateState(fOcrPlateState);
		obj.setFrontOcrPlateType(fOcrPlateType);
		obj.setFrontOcrPlateNumber(fOcrPlateNumber);
		obj.setFrontOcrConfidence(fOcrConfidence);
		obj.setFrontOcrImageIndex(fOcrImageIndex);
		obj.setFrontImageColor(fOcrImageColor);

		obj.setRearOcrPlateCountry(rOcrPlateCountry);
		obj.setRearOcrPlateState(rOcrPlateState);
		obj.setRearOcrPlateType(rOcrPlateType);
		obj.setRearOcrPlateNumber(rOcrPlateNumber);
		obj.setRearOcrConfidence(rOcrConfidence);
		obj.setRearOcrImageIndex(rOcrImageIndex);
		obj.setRearImageColor(rOcrImageColor);
		obj.setExternLaneId(externLaneId);
		obj.setExternPlazaId(externPlazaId);
		//obj.setAetFlag(aetFlag!= null?aetFlag:"N");
		obj.setAetFlag(aetFlag);


		return obj;

	}

	
}
