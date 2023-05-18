package com.conduent.tpms.ibts.away.tx.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.conduent.tpms.ibts.away.tx.constants.AwayTransactionConstant;
import com.conduent.tpms.ibts.away.tx.validation.EntryLaneId;
import com.conduent.tpms.ibts.away.tx.validation.EntryPlazaId;
import com.conduent.tpms.ibts.away.tx.validation.EntryTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
/**
 * Away Transaction model
 * 
 * @author deepeshb
 *
 */
@EntryLaneId(message = AwayTransactionConstant.EXCEPTION_MSG_MANDATORY_ENTRY_LANE_ID)
@EntryPlazaId(message = AwayTransactionConstant.EXCEPTION_MSG_MANDATORY_ENTRY_PLAZA_ID)
@EntryTimestamp(message = AwayTransactionConstant.EXCEPTION_MSG_MANDATORY_ENTRY_TIMESTAMP)
public class AwayTransaction {

	@ApiModelProperty(required = true)
	@NotNull(message = AwayTransactionConstant.EXCEPTION_MSG_MANDATORY_LANE_TX_ID)
	private Long laneTxId;

	@ApiModelProperty(required = true)
	@NotBlank(message = AwayTransactionConstant.EXCEPTION_MSG_MANDATORY_TX_EXTERN_REF_NO)
	private String txExternRefNo;

	private Long txSeqNumber;

	@ApiModelProperty(required = true)
	@NotNull(message = AwayTransactionConstant.EXCEPTION_MSG_MANDATORY_EXTERN_FILE_ID)
	private Long externFileId;

	@ApiModelProperty(required = true)
	@NotNull(message = AwayTransactionConstant.EXCEPTION_MSG_MANDATORY_PLAZA_AGENCY_ID)
	private Integer plazaAgencyId;

	@ApiModelProperty(required = true)
	@NotNull(message = AwayTransactionConstant.EXCEPTION_MSG_MANDATORY_PLAZA_ID)
	private Integer plazaId;

	@ApiModelProperty(required = true)
	@NotNull(message = AwayTransactionConstant.EXCEPTION_MSG_MANDATORY_LANE_ID)
	private Integer laneId;

	@ApiModelProperty(required = true)
	@NotBlank(message = AwayTransactionConstant.EXCEPTION_MSG_MANDATORY_TX_TIMESTAMP)
	private String txTimestamp;

	private Integer txModSeq;

	@ApiModelProperty(required = true)
	@NotBlank(message = AwayTransactionConstant.EXCEPTION_MSG_MANDATORY_TX_TYPE)
	private String txType;

	@ApiModelProperty(required = true)
	@NotBlank(message = AwayTransactionConstant.EXCEPTION_MSG_MANDATORY_TX_SUB_TYPE)
	private String txSubType;
	
	private Integer txStatus;

	private Integer laneMode;

	private Long laneType;

	private Integer laneState;

	private Integer laneHealth;

	private Long collectorId;

	private Long tourSegmentId;

	private String entryDataSource;

	private Integer entryLaneId;

	private Integer entryPlazaId;

	private String entryTimestamp;

	private Integer entryTxSeqNumber;

	private Integer entryVehicleSpeed;

	private Integer laneTxStatus;

	private Integer lanetxType;

	@ApiModelProperty(required = true)
	@NotNull(message = AwayTransactionConstant.EXCEPTION_MSG_MANDATORY_TOLL_REVENUE_TYPE)
	private Integer tollRevenueType;

	@ApiModelProperty(required = true)
	@NotNull(message = AwayTransactionConstant.EXCEPTION_MSG_MANDATORY_ACTUAL_CLASS)
	private Integer actualClass;

	@ApiModelProperty(required = true)
	@NotNull(message = AwayTransactionConstant.EXCEPTION_MSG_MANDATORY_ACTUAL_AXLES)
	private Integer actualAxles;

	private Integer actualExtraAxles;

	private Integer collectorClass;

	private Integer collectorAxles;

	private Integer preClassClass;

	private Integer preClassAxles;

	private Integer postClassClass;

	private Integer postClassAxles;

	private Integer forwardAxles;

	private Integer reverseAxles;

	

	private Double collectedAmount;

	private Double unrealizedAmount;

	private String isDiscountable;

	private String isMedianFare;

	private String isPeak;

	private Integer priceScheduleId;

	private Integer vehicleSpeed;

	private Integer receiptIssued;

	@ApiModelProperty(required = true)
	@NotBlank(message = AwayTransactionConstant.EXCEPTION_MSG_MANDATORY_DEVICE_NO)
	private String deviceNo;

	private Integer accountType;

	private Integer deviceCodedClass;

	private Integer deviceAgencyClass;

	private Integer deviceIagClass;

	private Integer deviceAxles;

	private Long etcAccountId;

	private Integer accountAgencyId;

	private Integer readAviClass;

	private Integer readAviAxles;

	private String deviceProgramStatus;

	private String bufferedReadFlag;

	private Long laneDeviceStatus;

	private Integer postDeviceStatus;

	private Double preTxnBalance;

	private Integer planTypeId;

	private Integer etcTxStatus;

	private String speedViolFlag;

	private String imageTaken;

	private String plateCountry;

	@ApiModelProperty(required = true)
	//@NotBlank(message = AwayTransactionConstant.EXCEPTION_MSG_MANDATORY_PLATE_STATE)
	private String plateState;

	@ApiModelProperty(required = true)
	//@NotBlank(message = AwayTransactionConstant.EXCEPTION_MSG_MANDATORY_PLATE_NUMBER)
	private String plateNumber;

	private String revenueDate;

	private String postedDate;

	@ApiModelProperty(required = true)
	@NotNull(message = AwayTransactionConstant.EXCEPTION_MSG_MANDATORY_ATP_FILE_ID)
	private Long atpFileId;

	private String isReversed;

	private Integer corrReasonId;

	private String reconDate;

	private Integer reconStatusInd;

	private Integer reconSubCodeInd;

	private String externFileDate;

	private Integer mileage;

	private Integer deviceReadCount;

	private Integer deviceWriteCount;

	private Integer entryDeviceReadCount;

	private Integer entryDeviceWriteCount;

	private String depositId;

	@ApiModelProperty(required = true)
	@NotBlank(message = AwayTransactionConstant.EXCEPTION_MSG_MANDATORY_TX_DATE)
	private String txDate;

	private String cscLookupKey;

	private String updateTs;

	@ApiModelProperty(required = true)
	@NotBlank(message = AwayTransactionConstant.EXCEPTION_MSG_MANDATORY_TOLL_SYSTEM_TYPE)
	private String tollSystemType;

	private String reciprocityTrx;

	private Long dstAtpFileId;

	private Double expectedRevenueAmount;

	private Double videoFareAmount;

	private Double cashFareAmount;
	
	private Double etcFareAmount;

	private Double postedFareAmount;

	@ApiModelProperty(required = true)
	@NotNull(message = AwayTransactionConstant.EXCEPTION_MSG_MANDATORY_SOURCE_SYSTEM)
	private Integer sourceSystem;

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
		return externFileId;
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

	public Integer getTxModSeq() {
		return txModSeq;
	}

	public void setTxModSeq(Integer txModSeq) {
		this.txModSeq = txModSeq;
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

	public Integer getLaneMode() {
		return laneMode;
	}

	public void setLaneMode(Integer laneMode) {
		this.laneMode = laneMode;
	}

	public Long getLaneType() {
		return laneType;
	}

	public void setLaneType(Long laneType) {
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

	public Long getCollectorId() {
		return collectorId;
	}

	public void setCollectorId(Long collectorId) {
		this.collectorId = collectorId;
	}

	public Long getTourSegmentId() {
		return tourSegmentId;
	}

	public void setTourSegmentId(Long tourSegmentId) {
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

	public String getDeviceProgramStatus() {
		return deviceProgramStatus;
	}

	public void setDeviceProgramStatus(String deviceProgramStatus) {
		this.deviceProgramStatus = deviceProgramStatus;
	}

	public String getBufferedReadFlag() {
		return bufferedReadFlag;
	}

	public void setBufferedReadFlag(String bufferedReadFlag) {
		this.bufferedReadFlag = bufferedReadFlag;
	}

	public Long getLaneDeviceStatus() {
		return laneDeviceStatus;
	}

	public void setLaneDeviceStatus(Long laneDeviceStatus) {
		this.laneDeviceStatus = laneDeviceStatus;
	}

	public Integer getPostDeviceStatus() {
		return postDeviceStatus;
	}

	public void setPostDeviceStatus(Integer postDeviceStatus) {
		this.postDeviceStatus = postDeviceStatus;
	}

	public Double getPreTxnBalance() {
		return preTxnBalance;
	}

	public void setPreTxnBalance(Double preTxnBalance) {
		this.preTxnBalance = preTxnBalance;
	}

	public Integer getPlanTypeId() {
		return planTypeId;
	}

	public void setPlanTypeId(Integer planTypeId) {
		this.planTypeId = planTypeId;
	}

	public Integer getEtcTxStatus() {
		return etcTxStatus;
	}

	public void setEtcTxStatus(Integer etcTxStatus) {
		this.etcTxStatus = etcTxStatus;
	}

	public String getSpeedViolFlag() {
		return speedViolFlag;
	}

	public void setSpeedViolFlag(String speedViolFlag) {
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

	public Long getAtpFileId() {
		return atpFileId;
	}

	public void setAtpFileId(Long atpFileId) {
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

	public String getTollSystemType() {
		return tollSystemType;
	}

	public void setTollSystemType(String tollSystemType) {
		this.tollSystemType = tollSystemType;
	}

	public String getReciprocityTrx() {
		return reciprocityTrx;
	}

	public void setReciprocityTrx(String reciprocityTrx) {
		this.reciprocityTrx = reciprocityTrx;
	}

	public Long getDstAtpFileId() {
		return dstAtpFileId;
	}

	public void setDstAtpFileId(Long dstAtpFileId) {
		this.dstAtpFileId = dstAtpFileId;
	}

	public String getTxTimestamp() {
		return txTimestamp;
	}

	public void setTxTimestamp(String txTimestamp) {
		this.txTimestamp = txTimestamp;
	}

	public String getEntryTimestamp() {
		return entryTimestamp;
	}

	public void setEntryTimestamp(String entryTimestamp) {
		this.entryTimestamp = entryTimestamp;
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

	public String getReconDate() {
		return reconDate;
	}

	public void setReconDate(String reconDate) {
		this.reconDate = reconDate;
	}

	public String getExternFileDate() {
		return externFileDate;
	}

	public void setExternFileDate(String externFileDate) {
		this.externFileDate = externFileDate;
	}

	public String getUpdateTs() {
		return updateTs;
	}

	public void setUpdateTs(String updateTs) {
		this.updateTs = updateTs;
	}

	public Integer getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(Integer sourceSystem) {
		this.sourceSystem = sourceSystem;
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

	public Double getVideoFareAmount() {
		return videoFareAmount;
	}

	public void setVideoFareAmount(Double videoFareAmount) {
		this.videoFareAmount = videoFareAmount;
	}

	public Double getCashFareAmount() {
		return cashFareAmount;
	}

	public void setCashFareAmount(Double cashFareAmount) {
		this.cashFareAmount = cashFareAmount;
	}

	public Integer getTxStatus() {
		return txStatus;
	}

	public void setTxStatus(Integer txStatus) {
		this.txStatus = txStatus;
	}

	@Override
	public String toString() {
		return "AwayTransaction [laneTxId=" + laneTxId + ", txExternRefNo=" + txExternRefNo + ", txSeqNumber="
				+ txSeqNumber + ", externFileId=" + externFileId + ", plazaAgencyId=" + plazaAgencyId + ", plazaId="
				+ plazaId + ", laneId=" + laneId + ", txTimestamp=" + txTimestamp + ", txModSeq=" + txModSeq
				+ ", txType=" + txType + ", txSubType=" + txSubType + ", laneMode=" + laneMode + ", laneType="
				+ laneType + ", laneState=" + laneState + ", laneHealth=" + laneHealth + ", collectorId=" + collectorId
				+ ", tourSegmentId=" + tourSegmentId + ", entryDataSource=" + entryDataSource + ", entryLaneId="
				+ entryLaneId + ", entryPlazaId=" + entryPlazaId + ", entryTimestamp=" + entryTimestamp
				+ ", entryTxSeqNumber=" + entryTxSeqNumber + ", entryVehicleSpeed=" + entryVehicleSpeed
				+ ", laneTxStatus=" + laneTxStatus + ", lanetxType=" + lanetxType + ", tollRevenueType="
				+ tollRevenueType + ", actualClass=" + actualClass + ", actualAxles=" + actualAxles
				+ ", actualExtraAxles=" + actualExtraAxles + ", collectorClass=" + collectorClass + ", collectorAxles="
				+ collectorAxles + ", preClassClass=" + preClassClass + ", preClassAxles=" + preClassAxles
				+ ", postClassClass=" + postClassClass + ", postClassAxles=" + postClassAxles + ", forwardAxles="
				+ forwardAxles + ", reverseAxles=" + reverseAxles + ", etcFareAmount=" + etcFareAmount
				+ ", postedFareAmount=" + postedFareAmount + ", collectedAmount=" + collectedAmount
				+ ", unrealizedAmount=" + unrealizedAmount + ", isDiscountable=" + isDiscountable + ", isMedianFare="
				+ isMedianFare + ", isPeak=" + isPeak + ", priceScheduleId=" + priceScheduleId + ", vehicleSpeed="
				+ vehicleSpeed + ", receiptIssued=" + receiptIssued + ", deviceNo=" + deviceNo + ", accountType="
				+ accountType + ", deviceCodedClass=" + deviceCodedClass + ", deviceAgencyClass=" + deviceAgencyClass
				+ ", deviceIagClass=" + deviceIagClass + ", deviceAxles=" + deviceAxles + ", etcAccountId="
				+ etcAccountId + ", accountAgencyId=" + accountAgencyId + ", readAviClass=" + readAviClass
				+ ", readAviAxles=" + readAviAxles + ", deviceProgramStatus=" + deviceProgramStatus
				+ ", bufferedReadFlag=" + bufferedReadFlag + ", laneDeviceStatus=" + laneDeviceStatus
				+ ", postDeviceStatus=" + postDeviceStatus + ", preTxnBalance=" + preTxnBalance + ", planTypeId="
				+ planTypeId + ", etcTxStatus=" + etcTxStatus + ", speedViolFlag=" + speedViolFlag + ", imageTaken="
				+ imageTaken + ", plateCountry=" + plateCountry + ", plateState=" + plateState + ", plateNumber="
				+ plateNumber + ", revenueDate=" + revenueDate + ", postedDate=" + postedDate + ", atpFileId="
				+ atpFileId + ", isReversed=" + isReversed + ", corrReasonId=" + corrReasonId + ", reconDate="
				+ reconDate + ", reconStatusInd=" + reconStatusInd + ", reconSubCodeInd=" + reconSubCodeInd
				+ ", externFileDate=" + externFileDate + ", mileage=" + mileage + ", deviceReadCount=" + deviceReadCount
				+ ", deviceWriteCount=" + deviceWriteCount + ", entryDeviceReadCount=" + entryDeviceReadCount
				+ ", entryDeviceWriteCount=" + entryDeviceWriteCount + ", depositId=" + depositId + ", txDate=" + txDate
				+ ", cscLookupKey=" + cscLookupKey + ", updateTs=" + updateTs + ", tollSystemType=" + tollSystemType
				+ ", reciprocityTrx=" + reciprocityTrx + ", dstAtpFileId=" + dstAtpFileId + ", expectedRevenueAmount="
				+ expectedRevenueAmount + ", videoFareAmount=" + videoFareAmount + ", cashFareAmount=" + cashFareAmount
				+ ", sourceSystem=" + sourceSystem + ", txStatus=" + txStatus + "]";
	}

}
