package com.conduent.tpms.qatp.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.conduent.tpms.qatp.constants.QATPConstants;
import com.conduent.tpms.qatp.dao.TQatpMappingDao;
import com.conduent.tpms.qatp.model.Transaction;
import com.conduent.tpms.qatp.model.VehicleClass;
import com.conduent.tpms.qatp.model.XferControl;
import com.conduent.tpms.qatp.utility.Convertor;
import com.conduent.tpms.qatp.utility.GenericValidation;
import com.conduent.tpms.qatp.utility.MasterDataCache;

@Service
public class MtaDetailInfoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(MtaDetailInfoVO.class);
		
	private String recordType;
	private String transType;
	private String tagAgencyId;
	private String tagSerialNumber;
	private String tcsId;
	private String tsClass;
	private String tagClass;
	private String dvehicleClass;
	private String avcProfile;
	private String avcAxles;
	private String exitLaneNumber;
	private String exitTransitDate;
	private String exitTransitTime;
	private String exitPlaza;
	private String entryLaneNumber;
	private String entryTransitDate;
	private String entryTransitTime;
	private String entryPlaza;
	private String direction;
	private String ti1TagReadStatus;
	private String ti2AdditionalTags;
	private String ti3ClassMismatchFlag;
	private String ti4TagStatus;
	private String ti5PaymentFlag;
	private String ti6RevenueFlag;
	private String vehicleSpeed;
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
	private String bestImage;
	private String event;
	private String hovFlag;
	private String hovpresenceTime;
	private String hovlostPresenceTime;
	private String longitude;
	private String latitude;
	private String vmt;
	private String duration;
	private String vehicleType;
	private String vehicleSubType;
	private String discountPlanFlagCbd;
	private String cbdPostingPlan;
	private String avcDiscrepancyFlag;
	private String degraded;
	private String expectedToll;
	private String appliedToll;
	private String expectedAccountType;
	private String homeEzpassRate;
	private String origTcsId;
	private String uoCode;
	private String uvid;
	private String displayDate;
	private String displayTime;
	private String displayPlaza;
	private String payeeId;
	private String homeMidTierRate;
	private String awayEzpassRate;
	private String tbmRate;
	private String tripType;
	private String tripQuantityLimit;
	private String tripAmountLimit;
	private String tripChargeableTrxReason;
	private String tripQuantityReason;
	private String appliedTollReason;
	private String tripUpchargeFlag;
	private String manualFlag;
	private String tagHomeAgency;
	
	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getTagAgencyId() {
		return tagAgencyId;
	}

	public void setTagAgencyId(String tagAgencyId) {
		this.tagAgencyId = tagAgencyId;
	}

	public String getTagSerialNumber() {
		return tagSerialNumber;
	}

	public void setTagSerialNumber(String tagSerialNumber) {
		this.tagSerialNumber = tagSerialNumber;
	}

	public String getTcsId() {
		return tcsId;
	}

	public void setTcsId(String tcsId) {
		this.tcsId = tcsId;
	}

	public String getTsClass() {
		return tsClass;
	}

	public void setTsClass(String tsClass) {
		this.tsClass = tsClass;
	}

	public String getTagClass() {
		return tagClass;
	}

	public void setTagClass(String tagClass) {
		this.tagClass = tagClass;
	}

	public String getVehicleClass() {
		return dvehicleClass;
	}

	public void setVehicleClass(String vehicleClass) {
		this.dvehicleClass = vehicleClass;
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

	public String getExitLaneNumber() {
		return exitLaneNumber;
	}

	public void setExitLaneNumber(String exitLaneNumber) {
		this.exitLaneNumber = exitLaneNumber;
	}

	public String getExitTransitDate() {
		return exitTransitDate;
	}

	public void setExitTransitDate(String exitTransitDate) {
		this.exitTransitDate = exitTransitDate;
	}

	public String getExitTransitTime() {
		return exitTransitTime;
	}

	public void setExitTransitTime(String exitTransitTime) {
		this.exitTransitTime = exitTransitTime;
	}

	public String getExitPlaza() {
		return exitPlaza;
	}

	public void setExitPlaza(String exitPlaza) {
		this.exitPlaza = exitPlaza;
	}

	public String getEntryLaneNumber() {
		return entryLaneNumber;
	}

	public void setEntryLaneNumber(String entryLaneNumber) {
		this.entryLaneNumber = entryLaneNumber;
	}

	public String getEntryTransitDate() {
		return entryTransitDate;
	}

	public void setEntryTransitDate(String entryTransitDate) {
		this.entryTransitDate = entryTransitDate;
	}

	public String getEntryTransitTime() {
		return entryTransitTime;
	}

	public void setEntryTransitTime(String entryTransitTime) {
		this.entryTransitTime = entryTransitTime;
	}

	public String getEntryPlaza() {
		return entryPlaza;
	}

	public void setEntryPlaza(String entryPlaza) {
		this.entryPlaza = entryPlaza;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
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

	public String getVehicleSpeed() {
		return vehicleSpeed;
	}

	public void setVehicleSpeed(String vehicleSpeed) {
		this.vehicleSpeed = vehicleSpeed;
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

	public String getBestImage() {
		return bestImage;
	}

	public void setBestImage(String bestImage) {
		this.bestImage = bestImage;
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

	public String getHovpresenceTime() {
		return hovpresenceTime;
	}

	public void setHovpresenceTime(String hovpresenceTime) {
		this.hovpresenceTime = hovpresenceTime;
	}

	public String getHovlostPresenceTime() {
		return hovlostPresenceTime;
	}

	public void setHovlostPresenceTime(String hovlostPresenceTime) {
		this.hovlostPresenceTime = hovlostPresenceTime;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getVmt() {
		return vmt;
	}

	public void setVmt(String vmt) {
		this.vmt = vmt;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getVehicleSubType() {
		return vehicleSubType;
	}

	public void setVehicleSubType(String vehicleSubType) {
		this.vehicleSubType = vehicleSubType;
	}

	public String getDiscountPlanFlagCbd() {
		return discountPlanFlagCbd;
	}

	public void setDiscountPlanFlagCbd(String discountPlanFlagCbd) {
		this.discountPlanFlagCbd = discountPlanFlagCbd;
	}

	public String getCbdPostingPlan() {
		return cbdPostingPlan;
	}

	public void setCbdPostingPlan(String cbdPostingPlan) {
		this.cbdPostingPlan = cbdPostingPlan;
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

	public String getExpectedToll() {
		return expectedToll;
	}

	public void setExpectedToll(String expectedToll) {
		this.expectedToll = expectedToll;
	}

	public String getAppliedToll() {
		return appliedToll;
	}

	public void setAppliedToll(String appliedToll) {
		this.appliedToll = appliedToll;
	}

	public String getExpectedAccountType() {
		return expectedAccountType;
	}

	public void setExpectedAccountType(String expectedAccountType) {
		this.expectedAccountType = expectedAccountType;
	}

	public String getHomeEzpassRate() {
		return homeEzpassRate;
	}

	public void setHomeEzpassRate(String homeEzpassRate) {
		this.homeEzpassRate = homeEzpassRate;
	}

	public String getOrigTcsId() {
		return origTcsId;
	}

	public void setOrigTcsId(String origTcsId) {
		this.origTcsId = origTcsId;
	}

	public String getUoCode() {
		return uoCode;
	}

	public void setUoCode(String uoCode) {
		this.uoCode = uoCode;
	}

	public String getUvid() {
		return uvid;
	}

	public void setUvid(String uvid) {
		this.uvid = uvid;
	}

	public String getDisplayDate() {
		return displayDate;
	}

	public void setDisplayDate(String displayDate) {
		this.displayDate = displayDate;
	}

	public String getDisplayTime() {
		return displayTime;
	}

	public void setDisplayTime(String displayTime) {
		this.displayTime = displayTime;
	}

	public String getDisplayPlaza() {
		return displayPlaza;
	}

	public void setDisplayPlaza(String displayPlaza) {
		this.displayPlaza = displayPlaza;
	}

	public String getPayeeId() {
		return payeeId;
	}

	public void setPayeeId(String payeeId) {
		this.payeeId = payeeId;
	}

	public String getHomeMidTierRate() {
		return homeMidTierRate;
	}

	public void setHomeMidTierRate(String homeMidTierRate) {
		this.homeMidTierRate = homeMidTierRate;
	}

	public String getAwayEzpassRate() {
		return awayEzpassRate;
	}

	public void setAwayEzpassRate(String awayEzpassRate) {
		this.awayEzpassRate = awayEzpassRate;
	}

	public String getTbmRate() {
		return tbmRate;
	}

	public void setTbmRate(String tbmRate) {
		this.tbmRate = tbmRate;
	}

	public String getTripType() {
		return tripType;
	}

	public void setTripType(String tripType) {
		this.tripType = tripType;
	}

	public String getTripQuantityLimit() {
		return tripQuantityLimit;
	}

	public void setTripQuantityLimit(String tripQuantityLimit) {
		this.tripQuantityLimit = tripQuantityLimit;
	}

	public String getTripAmountLimit() {
		return tripAmountLimit;
	}

	public void setTripAmountLimit(String tripAmountLimit) {
		this.tripAmountLimit = tripAmountLimit;
	}

	public String getTripChargeableTrxReason() {
		return tripChargeableTrxReason;
	}

	public void setTripChargeableTrxReason(String tripChargeableTrxReason) {
		this.tripChargeableTrxReason = tripChargeableTrxReason;
	}

	public String getTripQuantityReason() {
		return tripQuantityReason;
	}

	public void setTripQuantityReason(String tripQuantityReason) {
		this.tripQuantityReason = tripQuantityReason;
	}

	public String getAppliedTollReason() {
		return appliedTollReason;
	}

	public void setAppliedTollReason(String appliedTollReason) {
		this.appliedTollReason = appliedTollReason;
	}

	public String getTripUpchargeFlag() {
		return tripUpchargeFlag;
	}

	public void setTripUpchargeFlag(String tripUpchargeFlag) {
		this.tripUpchargeFlag = tripUpchargeFlag;
	}

	public String getManualFlag() {
		return manualFlag;
	}

	public void setManualFlag(String manualFlag) {
		this.manualFlag = manualFlag;
	}

	public String getTagHomeAgency() {
		return tagHomeAgency;
	}

	public void setTagHomeAgency(String tagHomeAgency) {
		this.tagHomeAgency = tagHomeAgency;
	}

	public Transaction getTransaction(MasterDataCache masterDataCache, XferControl xferControl, MtaHeaderinfoVO headerVO, TQatpMappingDao tQatpMappingDao) 
	{
		AgencyInfoVO agencyInfoVO = masterDataCache.getAgency(tagAgencyId != null ? tagAgencyId : null);
		Tplaza plaza = masterDataCache.getPlaza(exitPlaza != null ? StringUtils.leftPad(String.valueOf(exitPlaza), 2, "0") : null,
				Convertor.toInteger(QATPConstants.MTA_AGENCY_ID));
		Transaction obj = new Transaction();

		GenericValidation validation = new GenericValidation();
	
		obj.setTrxType("E");
		obj.setTrxSubtype("Z");
		
		// transType - R/X
		if (transType == null) 
		{
			obj.setTransType(null);
		}
		else if (transType!=null &&  transType.equals("1") && isDeviceValid(tagAgencyId + tagSerialNumber)) 
		{
//			obj.setTrxType("E");
//			obj.setTrxSubtype("Z");
//			obj.setEtcViolObserved(0);
			obj.setTransType(transType);
		} 
		else if (transType!=null && (transType.equals("2") || transType.equals("3")) ) 
		{
//			obj.setTrxType("V");
//			obj.setTrxSubtype("F");			
//			obj.setEtcViolObserved(1);
			obj.setTransType(transType);
		}
		else 
		{
			logger.info("R/X - Invalid TRANS_TYPE : {}", transType);
			obj.setTrxType("R");
			obj.setTrxSubtype("X");			
			obj.setTransType(transType);
		}
				
		// Device Number - R/T 
		if(tagAgencyId == null || tagSerialNumber == null || validate(tagAgencyId) || validate(tagSerialNumber)
				|| validate(tagAgencyId+tagSerialNumber)) 
		{
			obj.setTrxType("V");
			obj.setTrxSubtype("F");
		}
		else
		{
			if(tagAgencyId.matches(".*[a-zA-Z].*") || tagSerialNumber.matches(".*[a-zA-Z].*"))
			{
				if(!obj.getTrxType().equals("R")) 
				{				
					logger.info("R/T - Null TAG_AGENCY_ID or TAG_SERIAL_NUMBER");
					obj.setTrxType("R");
					obj.setTrxSubtype("T");
				}
				if (tagAgencyId != null && tagSerialNumber != null && (tagAgencyId.matches(".*[a-zA-Z].*") || tagSerialNumber.matches(".*[a-zA-Z].*")))
					obj.setDeviceNumber(tagAgencyId+tagSerialNumber);
			} 
			else 
			{
				if (agencyInfoVO != null) 
				{
					String dnumber = ((agencyInfoVO.getDevicePrefix()) + (tagSerialNumber != null ? tagSerialNumber : null));
					obj.setDeviceNumber(dnumber);
				} 
				else  
				{					
					if(!obj.getTrxType().equals("R"))
					{
						logger.info("R/T - Invalid TAG_AGENCY_ID : {}", tagAgencyId);
						obj.setTrxType("R");
						obj.setTrxSubtype("T");
					}					
					obj.setDeviceNumber(tagAgencyId+tagSerialNumber);				
				}
			}
		}
					
		// Extern Reference Number - R/X
		if (tcsId == null) 
		{
			obj.setExtHostRefNum(null);
		}
		else if ( tcsId.length() != 20 || tcsId.matches(".*[a-zA-Z].*") || tcsId.equals("                    ") || tcsId.contains(" ") ) //Bug 12012 ^[a-zA-Z0-9_@./#&+-]*$     
		{		
			if(!obj.getTrxType().equals("R")) 
			{
				logger.info("R/X - Invalid TCS_ID : {}", tcsId);
				obj.setTrxType("R");
				obj.setTrxSubtype("X");
			}			
		}
		else
		{				
			obj.setExtHostRefNum(new BigInteger(tcsId));
		}
		
		//TagClass - R/C
		Integer tgClass = tagClass!=null ? Convertor.toInteger(tagClass) : null;
		
		if(tagClass == null) 
		{
			obj.setTagClass(null);
		}
		else if(tagClass.matches(".*[a-zA-Z].*") || tagClass.equals("    ") || tagClass.contains(" ") || tgClass < 0 || tgClass > 9999 || tagClass.equals("****"))
		{
			if(!obj.getTrxType().equals("R")) 
			{			
				logger.info("R/C - Invalid TAG_CLASS : {}", tagClass);
				obj.setTrxType("R");
				obj.setTrxSubtype("C");
			}
		}
		else 
		{
			obj.setTagClass(Convertor.toLong(tagClass));
		}
			
		
		//TsClass - R/C	
		Integer tssClass = tsClass != null ? Convertor.toInteger(tsClass) : null;
		
		if(tsClass == null) 
		{
			obj.setTagIAGClass(null);
		}
		else if(tsClass.matches(".*[a-zA-Z].*") || tsClass.equals("    ") || tsClass.contains(" ") || tssClass < 0 || tssClass > 9999 || tsClass.equals("****"))				 
		{
			if(!obj.getTrxType().equals("R"))
			{
				logger.info("R/C - Invalid TS_CLASS : {}", tsClass);
				obj.setTrxType("R");
				obj.setTrxSubtype("C");
			}
		}
		else 
		{
			obj.setTagIAGClass(Convertor.toLong(tsClass));
		}
	
		// Avc Axles - R/X
		if(avcAxles == null) 
		{
			obj.setAvcAxles(null);
			obj.setActualAxles(null);
		}
		else if(avcAxles.matches("[a-fA-F0-9]")) 
		{
			obj.setAvcAxles(Integer.parseInt(avcAxles,16));
			obj.setActualAxles(Integer.parseInt(avcAxles,16));
		}
		else 
		{				
			if(!obj.getTrxType().equals("R"))
			{
				logger.info("R/X - Invalid AVC_AXLES : {}", avcAxles);
				obj.setTrxType("R");
				obj.setTrxSubtype("X");				
			}
		}
		
		// Exit PlazaId - R/L
		Tplaza tplaza = masterDataCache.getPlaza(exitPlaza != null ? StringUtils.leftPad(String.valueOf(exitPlaza), 2, "0") : null, 
				Convertor.toInteger(QATPConstants.MTA_AGENCY_ID));
	
		if (tplaza == null) 
		{
			if(!obj.getTrxType().equals("R")) 
			{
				logger.info("R/L - Invalid EXIT_PLAZA : {}", exitPlaza);
				obj.setTrxType("R");
				obj.setTrxSubtype("L");
			}
		} 
		else
		{
			obj.setExitPlaza(tplaza.getPlazaId());
			obj.setPlazaAgencyId(tplaza.getAgencyId());
			obj.setExternPlazaId(String.valueOf(tplaza.getExternPlazaId()));
			obj.setExitLane( masterDataCache.getLane(exitLaneNumber, plaza.getPlazaId()) != null ? masterDataCache.getLane(exitLaneNumber,plaza.getPlazaId()).getLaneId() : null);
		}
		
		// Exit Lane - R/L
		logger.debug("Checking for lane id present in master.T_LANE Table");
		int laneDto = masterDataCache.getLaneId(exitLaneNumber != null ? exitLaneNumber : null,
					plaza != null ? plaza.getPlazaId() : null);

		if (laneDto == 0) 
		{
			if(!obj.getTrxType().equals("R")) 
			{
				logger.info("R/L - Invalid EXIT_LANE_NUMBER : {}", exitLaneNumber);
				obj.setTrxType("R");
				obj.setTrxSubtype("L");
			}
		} 
		else
		{
			obj.setTrxLaneId(laneDto);
		}
			
		// Vehicle Class - R/C 
		VehicleClass vehicleClass = masterDataCache.getAgencyIdandExternalAgencyClass(plaza != null ? plaza.getAgencyId() : null, 
				dvehicleClass != null ? String.valueOf(Convertor.toInteger(dvehicleClass)) : null);
				
		if(vehicleClass==null)
		{
			if(!obj.getTrxType().equals("R")) 
			{
				logger.info("R/C - Invalid VEHICLE_CLASS : {}", dvehicleClass);
				obj.setTrxType("R");
				obj.setTrxSubtype("C");
			}
		}
		else
		{
			obj.setActualClass(vehicleClass.getAgencyClass());
					
			if(avcAxles!=null && avcAxles.matches("[a-fA-F0-9]")) 
			{
				Integer extraAxles = Integer.parseInt(avcAxles,16) - vehicleClass.getBaseAxleCount();			
				
				if(extraAxles >= 0) 
				{
					obj.setExtraAxles(extraAxles);
				}
				else if (extraAxles < 0)
				{
					if(!obj.getTrxType().equals("R")) 
					{
						logger.info("R/X - Negative EXTRA_AXLES : {}", extraAxles);
						obj.setTrxType("R");
						obj.setTrxSubtype("X");
					}
					obj.setExtraAxles(null);
				}
			}		
		}	
		
		// Exit Date and Time - R/D
		if(exitTransitDate == null) 
		{
			obj.setTrxDate("19000101");
		}
		else if(!validation.dateValidation(exitTransitDate, "YYYYMMDD2")) 
		{
			if(!obj.getTrxType().equals("R")) 
			{
				logger.info("R/D - Invalid EXIT_TRANSIT_DATE : {}", exitTransitDate);
				obj.setTrxType("R");
				obj.setTrxSubtype("D");
			}
			obj.setTrxDate("19000101");
		}
		else 
		{
			obj.setTrxDate(exitTransitDate); 
		}
	
		if(exitTransitTime == null) 
		{
			obj.setTrxTime("05000000");
		}
		else if(!validation.timeValidation(exitTransitTime, "HHMMSSTT")) 
		{
			if(!obj.getTrxType().equals("R")) 
			{
				logger.info("R/D - Invalid EXIT_TRANSIT_TIME : {}", exitTransitTime);
				obj.setTrxType("R");
				obj.setTrxSubtype("D");
			}
			obj.setTrxTime("05000000");
		}
		else
		{
			obj.setTrxTime(exitTransitTime);
		}
		
		String date = validation.dateValidation(exitTransitDate,"YYYYMMDD2") == true ? exitTransitDate : "19000101";
		String time = validation.timeValidation(exitTransitTime,"HHMMSSTT") == true ? exitTransitTime : "05000000";
		
		String txTimeStamp = date + time;
		obj.setTxTimeStamp(txTimeStamp);
		
		// Vehicle Speed - R/N
		if(vehicleSpeed == null) 
		{
			obj.setVehicleSpeed(null);
		}
		else if(vehicleSpeed.matches(".*[a-zA-Z].*") || vehicleSpeed.equals("   ") || vehicleSpeed.contains(" ") 
				|| Convertor.toInteger(vehicleSpeed) < 0 || Convertor.toInteger(vehicleSpeed) > 999 ) 
		{
			if(!obj.getTrxType().equals("R")) 
			{
				logger.info("R/N - Invalid VEHICLE_SPEED : {}", vehicleSpeed);
				obj.setTrxType("R");	
				obj.setTrxSubtype("N");
			}
		}
		else 
		{
			obj.setVehicleSpeed(Convertor.toInteger(vehicleSpeed));
		}
		
		
				
		if (obj.getDeviceNumber() != null) 
		{
			AgencyInfoVO checkAwayAgency = masterDataCache.checkAwayAgency(obj.getDeviceNumber().substring(0, 3));
			if (checkAwayAgency != null && checkAwayAgency.getIsHomeAgency() != null) {

				obj.setReciprocityTrx(checkAwayAgency.getIsHomeAgency().equals("N") ? "T" : "F");
			} else {
				obj.setReciprocityTrx("F");
			}
		}
		
		if(transType == null) 
		{
			obj.setImageCaptured(null);
			obj.setIsViolation(null);
		}
		else if(transType.equals("1")) 
		{
			obj.setImageCaptured("N");
			obj.setIsViolation(0);
		}			
		else if (transType.equals("2"))
		{
			obj.setImageCaptured("Y");
			obj.setIsViolation(1);
		}
		else
		{
			obj.setImageCaptured(null);
			obj.setIsViolation(null);
		}
				
		//New Fields		
		if(direction!=null && "01".contains(direction)) 
		{
			obj.setDirection(direction);
		}
		
		if(event!=null && "YN".contains(event)) 		
		{
			obj.setEvent(event);
		}
		
		if(hovFlag!=null && "YN".contains(hovFlag)) 
		{
			obj.setHovFlag(hovFlag);
		}
				
		if(avcDiscrepancyFlag!=null && "YN".contains(avcDiscrepancyFlag)) 
		{
			obj.setAvcDiscrepancyFlag(avcDiscrepancyFlag);
		}
		
		if(degraded!=null && "ACDIMNZ".contains(degraded))
		{
			obj.setDegraded(degraded);
		}
		
		if(uoCode!=null && "YN".contains(String.valueOf(uoCode.charAt(0))) && 
				"YN".contains(String.valueOf(uoCode.charAt(1))) &&
				"IVNUE".contains(String.valueOf(uoCode.charAt(2))))  
		{
			obj.setUoCode(uoCode);
		}
		
		if(avcProfile!=null && "HO".contains(avcProfile)) 
		{
			obj.setAvcProfile(avcProfile);
		}
		
		obj.setHovPresenceTime(hovpresenceTime);
		obj.setHovLostPresenceTime(hovlostPresenceTime);
		
		
		//Transaction Info
		if(ti1TagReadStatus!=null && "YN".contains(ti1TagReadStatus)) 
		{
			obj.setTi1TagReadStatus(ti1TagReadStatus);
		}
		
		if(ti2AdditionalTags!=null && "YN".contains(ti2AdditionalTags)) 
		{
			obj.setTi2AdditionalTags(ti2AdditionalTags);
		}
		
		if(ti3ClassMismatchFlag!=null && "YN".contains(ti3ClassMismatchFlag)) 
		{
			obj.setTi3ClassMismatchFlag(ti3ClassMismatchFlag);
		}
		
		if(ti4TagStatus!=null && "0LSIRU".contains(ti4TagStatus)) 
		{
			obj.setTi4TagStatus(ti4TagStatus);
		}
		
		if(ti5PaymentFlag!=null && "V12".contains(ti5PaymentFlag))
		{
			obj.setTi5PaymentFlag(ti5PaymentFlag);
		}
		
		if(ti6RevenueFlag!=null && "YN".contains(ti6RevenueFlag)) 
		{
			obj.setTi6RevenueFlag(ti6RevenueFlag);
		}
		
		
		// R/X with Flags for Classification
		if ( ti1TagReadStatus!=null && ti1TagReadStatus.equals("N") && ( ti2AdditionalTags!=null 
				|| ti3ClassMismatchFlag!=null || ti4TagStatus!=null || ti5PaymentFlag!=null || ti6RevenueFlag!=null )) 
		{
			logger.info("R/X - Invalid for Classification");
			obj.setTrxType("R");
			obj.setTrxSubtype("X");		
			obj.setTransType(transType);
		}
		
		// R/V with Flags for Classification
		if (ti1TagReadStatus!=null && ti1TagReadStatus.equals("Y") && (ti5PaymentFlag == null || !ti5PaymentFlag.equals("V")) )
		{
			if(!obj.getTrxType().equals("R")) 
			{
				logger.info("R/V - Invalid for Classification");
				obj.setTrxType("R");
				obj.setTrxSubtype("V");
			}
		}
		
		obj.setTollRevenueType(60);
		obj.setTollSystemType("B");
			
		
		
		obj.setReceivedDate(headerVO.getCurrentTs().substring(0, 14));	
				
		obj.setExtFileId(xferControl.getXferControlId());	
		
		obj.setPlateCountry("***");
		obj.setPlateState("**");
		obj.setPlateType(0);
		obj.setPlateNumber("**********");
		
		//String aetFlag = getAetFlag(obj, masterDataCache);
		obj.setAetFlag("Y");

		obj.setDstFlag("N");
		
		return obj;		
	

	}
	
	public boolean validate(String value) 
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

	private boolean isDeviceValid(String agTagSerialNumber) 
	{
		if(agTagSerialNumber.equals("**************") || agTagSerialNumber.equals("              ") || agTagSerialNumber.equals("00000000000000"))
		{
			return false;
		}
		return true;
	}

//	private String getAetFlag(Transaction obj, MasterDataCache masterDataCache) 
//	{
//		String aetFlag = null;
//
//		Tplaza plaza = masterDataCache.getPlaza(this.getEntryPlaza(),
//				Convertor.toInteger(QATPConstants.MTA_AGENCY_ID));
//
//		TLaneDto lane = masterDataCache.getLane(this.getEntryLaneNumber(),
//				plaza != null ? plaza.getPlazaId() : null);
//		
//		if(lane!=null)
//		{
//			//obj.setExternLaneId(String.valueOf(lane.getLaneId()));
//			obj.setExternLaneId(String.valueOf(lane.getExternLaneId()));
//		}
//
//		if (lane != null && lane.getLprEnabled() != null) 
//		{
//			if (lane.getLprEnabled().equals("Y") || lane.getLprEnabled().equals("T")) 
//			{
//				aetFlag = "Y";
//			}
//			else 
//			{
//				aetFlag = "N";
//			}
//		} 
//		else if (plaza != null && plaza.getLprEnabled() != null) 
//		{
//			if (plaza.getLprEnabled().equals("Y") || plaza.getLprEnabled().equals("T")) 
//			{
//				aetFlag = "Y";
//			} 
//			else 
//			{
//				aetFlag = "N";
//			}
//
//		} 
//		else 
//		{ 
//			aetFlag = "N";
//		}
//		return aetFlag;
//	}
}
