package com.conduent.tpms.qatp.dto;

import com.conduent.tpms.qatp.model.Transaction;
import com.conduent.tpms.qatp.utility.Convertor;

public class MtaTxDto {

	private String recordType;
	private String transType;
	private String tagAgencyId;
	private String tagSerialNumber;
	private String tcsId;
	private String tsClass;
	private String tagClass;
	private String vehicleClass;
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

	
}
