package com.conduent.tpms.inserttable.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;


public class T25APendingQueueRequestModel implements Serializable{
	

	private static final long serialVersionUID = -6683213270280748046L;

@NotNull(message="LaneTxId shouldn't be null")
@ApiModelProperty(value = "LaneTxId", dataType = "Long", required = true, example = "20000145135")
	private  Long    laneTxId;
	private  String  txExternRefNo;
	private  Long txSeqNumber;
	private  Long externFileId;
	private  Long laneId;
	
	//@CreationTimestamp
	//@Temporal(TemporalType.TIMESTAMP)
	private  String  txTimestamp;
	
	@Size(min = 1, max = 1,message="TxTypeInd Max length is 1")
	private  String  txTypeInd;
	@Size(min = 1, max = 1,message="TxSubTypeInd Max length is 1")
	private  String  txSubTypeInd;
	@Size(min = 1, max = 1,message="TollSystemType Max length is 1")
	private  String  tollSystemType;
	
	private  Long laneMode;
	private  Long laneType;
	private  Long laneState;
	private  Long laneHealth;
	private  Long plazaAgencyId;
	private  Long plazaId;
	private  Long collectorId;
	private  Long tourSegmentId;
	
	@Size(min = 1, max = 1,message="EntryDataSource Max length is 1")
	private  String  entryDataSource;
	private  Long entryLaneId;
	private  Long entryPlazaId;
	
	//@CreationTimestamp
	//@Temporal(TemporalType.TIMESTAMP)
	private  String  entryTimeStamp;
	
	private  Long entryTxSeqNumber;
	
	//@Digits(integer = 5, fraction = 0, message = "Length should be <=5" )
	private  Long entryVehicleSpeed;
	
	private  Long laneTxStatus;
	private  Long laneTxType;
	private  Long tollRevenueType;
	private  Long actualClass;
	private  Long actualAxles;
	private  Long actualExtraAxles;
	private  Long collectorClass;
	private  Long collectorAxles;
	private  Long preclassClass;
	private  Long preclassAxles;
	private  Long postclassClass;
	private  Long postclassAxles;
	private  Long forwardAxles;
	private  Long reverseAxles;
	
	private  double discountedAmount;
	@Digits(integer = 12 /*precision*/, fraction = 2 /*scale*/,message="Value larger than precision allowed i.e. 12 for CollectedAmount")
	private  Long collectedAmount;
	@Digits(integer = 12 /*precision*/, fraction = 2 /*scale*/,message="Value larger than precision allowed i.e. 12 for UnRealizedAmount")
	private  Long unRealizedAmount;
	
	private  Long vehicleSpeed;
	

	@Size(max=14,message="Length of Device number should not exceed 14")
	private  String  deviceNo;
	private  Long accountType;
	private  Long deviceIagClass;
	private  Long deviceAxles;		 
	private  Long etcAccountId;	
	private  Long accountAgencyId;
	private  Long readAviClass;
	private  Long readAviAxles;
	
	@Size(min = 1, max = 1,message="DeviceProgramStatus Max length is 1")
	private  String  deviceProgramStatus;
	@Size(min = 1, max = 1,message="BufferedReadFlag Max length is 1")	
	private  String  bufferedReadFlag;
	
	private  Long laneDeviceStatus;
	private  Long postDeviceStatus;
	
	@Digits(integer = 12 /*precision*/, fraction = 2 /*scale*/,message="Value larger than precision allowed i.e. 12 for UnRealizedAmount")
	private  Long preTxnBalance;
	
	private  Long txStatus;
	
	@Size(min = 1, max = 1,message="SpeedViolFlag Max length is 1")		
	private  String  speedViolFlag;
	@Size(min = 1, max = 1,message="Imagetaken Max length is 1")		
	private  String  imagetaken;
	
	@Size(max = 4,message="PlateCountry Max length is 4")		
	private  String  plateCountry;
	@Size(max = 2,message="PlateState Max length is 2")			
	private  String  plateState;
	
	private  String  plateNumber;
	
	@JsonFormat(pattern = "yyyy/MM/dd")
	private  LocalDate  revenueDate;
	
	private  Long atpFileId;
	private  String  updateTs;
	private String matchedTxExternRefNo;
	private String txCompleteRefNo;
	private String txMatchStatus;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private  LocalDate externFileDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private  LocalDate txDate;
	
	@Size(max = 1,message="event Max length is 1")			
	private  String event;
	@Size(max = 1,message="HovFlag Max length is 1")		
	private  String hovFlag;
	@Size(max = 1,message="IsReciprocityTxn Max length is 1")			
	private  String isReciprocityTxn;
	
	private  String cscLookUpKey;
	
	@Digits(integer = 12 /*precision*/, fraction = 2 /*scale*/,message="Value larger than precision allowed i.e. 12 for UnRealizedAmount")
	private Long fullFareAmount;
	
	@Size(max = 1,message="StatusCd Max length is 1")
	private String statusCd;
	
	private String errorMessage;
	
	private double cashFareAmount;
	private double discountedAmount2;
	private double etcFareAmount;
	private double expectedRevenueAmount;
	private double postedFareAmount;
	private double videoFareAmount;
	
	@Override
	public String toString() {
		return "T25APendingQueueRequestModel [laneTxId=" + laneTxId + ", txExternRefNo=" + txExternRefNo
				+ ", txSeqNumber=" + txSeqNumber + ", externFileId=" + externFileId + ", laneId=" + laneId
				+ ", txTimestamp=" + txTimestamp + ", txTypeInd=" + txTypeInd + ", txSubTypeInd=" + txSubTypeInd
				+ ", tollSystemType=" + tollSystemType + ", laneMode=" + laneMode + ", laneType=" + laneType
				+ ", laneState=" + laneState + ", laneHealth=" + laneHealth + ", plazaAgencyId=" + plazaAgencyId
				+ ", plazaId=" + plazaId + ", collectorId=" + collectorId + ", tourSegmentId=" + tourSegmentId
				+ ", entryDataSource=" + entryDataSource + ", entryLaneId=" + entryLaneId + ", entryPlazaId="
				+ entryPlazaId + ", entryTimeStamp=" + entryTimeStamp + ", entryTxSeqNumber=" + entryTxSeqNumber
				+ ", entryVehicleSpeed=" + entryVehicleSpeed + ", laneTxStatus=" + laneTxStatus + ", laneTxType="
				+ laneTxType + ", tollRevenueType=" + tollRevenueType + ", actualClass=" + actualClass
				+ ", actualAxles=" + actualAxles + ", actualExtraAxles=" + actualExtraAxles + ", collectorClass="
				+ collectorClass + ", collectorAxles=" + collectorAxles + ", preclassClass=" + preclassClass
				+ ", preclassAxles=" + preclassAxles + ", postclassClass=" + postclassClass + ", postclassAxles="
				+ postclassAxles + ", forwardAxles=" + forwardAxles + ", reverseAxles=" + reverseAxles
				+ ", discountedAmount=" + discountedAmount + ", collectedAmount=" + collectedAmount
				+ ", unRealizedAmount=" + unRealizedAmount + ", vehicleSpeed=" + vehicleSpeed + ", deviceNo=" + deviceNo
				+ ", accountType=" + accountType + ", deviceIagClass=" + deviceIagClass + ", deviceAxles=" + deviceAxles
				+ ", etcAccountId=" + etcAccountId + ", accountAgencyId=" + accountAgencyId + ", readAviClass="
				+ readAviClass + ", readAviAxles=" + readAviAxles + ", deviceProgramStatus=" + deviceProgramStatus
				+ ", bufferedReadFlag=" + bufferedReadFlag + ", laneDeviceStatus=" + laneDeviceStatus
				+ ", postDeviceStatus=" + postDeviceStatus + ", preTxnBalance=" + preTxnBalance + ", txStatus="
				+ txStatus + ", speedViolFlag=" + speedViolFlag + ", imagetaken=" + imagetaken + ", plateCountry="
				+ plateCountry + ", plateState=" + plateState + ", plateNumber=" + plateNumber + ", revenueDate="
				+ revenueDate + ", atpFileId=" + atpFileId + ", updateTs=" + updateTs + ", matchedTxExternRefNo="
				+ matchedTxExternRefNo + ", txCompleteRefNo=" + txCompleteRefNo + ", txMatchStatus=" + txMatchStatus
				+ ", externFileDate=" + externFileDate + ", txDate=" + txDate + ", event=" + event + ", hovFlag="
				+ hovFlag + ", isReciprocityTxn=" + isReciprocityTxn + ", cscLookUpKey=" + cscLookUpKey
				+ ", fullFareAmount=" + fullFareAmount + ", statusCd=" + statusCd + ", errorMessage=" + errorMessage
				+ ", cashFareAmount=" + cashFareAmount + ", discountedAmount2=" + discountedAmount2 + ", etcFareAmount="
				+ etcFareAmount + ", expectedRevenueAmount=" + expectedRevenueAmount + ", postedFareAmount="
				+ postedFareAmount + ", videoFareAmount=" + videoFareAmount + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
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
		return externFileId;
	}
	public void setExternFileId(Long externFileId) {
		this.externFileId = externFileId;
	}
	public Long getLaneId() {
		return laneId;
	}
	public void setLaneId(Long laneId) {
		this.laneId = laneId;
	}
	
	public String getTxTypeInd() {
		return txTypeInd;
	}
	public void setTxTypeInd(String txTypeInd) {
		this.txTypeInd = txTypeInd;
	}
	public String getTxSubTypeInd() {
		return txSubTypeInd;
	}
	public void setTxSubTypeInd(String txSubTypeInd) {
		this.txSubTypeInd = txSubTypeInd;
	}
	public String getTollSystemType() {
		return tollSystemType;
	}
	public void setTollSystemType(String tollSystemType) {
		this.tollSystemType = tollSystemType;
	}
	public Long getLaneMode() {
		return laneMode;
	}
	public void setLaneMode(Long laneMode) {
		this.laneMode = laneMode;
	}
	public Long getLaneType() {
		return laneType;
	}
	public void setLaneType(Long laneType) {
		this.laneType = laneType;
	}
	public Long getLaneState() {
		return laneState;
	}
	public void setLaneState(Long laneState) {
		this.laneState = laneState;
	}
	public Long getLaneHealth() {
		return laneHealth;
	}
	public void setLaneHealth(Long laneHealth) {
		this.laneHealth = laneHealth;
	}
	public Long getPlazaAgencyId() {
		return plazaAgencyId;
	}
	public void setPlazaAgencyId(Long plazaAgencyId) {
		this.plazaAgencyId = plazaAgencyId;
	}
	public Long getPlazaId() {
		return plazaId;
	}
	public void setPlazaId(Long plazaId) {
		this.plazaId = plazaId;
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
	public Long getEntryLaneId() {
		return entryLaneId;
	}
	public void setEntryLaneId(Long entryLaneId) {
		this.entryLaneId = entryLaneId;
	}
	public Long getEntryPlazaId() {
		return entryPlazaId;
	}
	public void setEntryPlazaId(Long entryPlazaId) {
		this.entryPlazaId = entryPlazaId;
	}
	
	public String getTxTimestamp() {
		return txTimestamp;
	}
	public void setTxTimestamp(String txTimestamp) {
		this.txTimestamp = txTimestamp;
	}
	public String getEntryTimeStamp() {
		return entryTimeStamp;
	}
	public void setEntryTimeStamp(String entryTimeStamp) {
		this.entryTimeStamp = entryTimeStamp;
	}
	public Long getEntryTxSeqNumber() {
		return entryTxSeqNumber;
	}
	public void setEntryTxSeqNumber(Long entryTxSeqNumber) {
		this.entryTxSeqNumber = entryTxSeqNumber;
	}
	public Long getEntryVehicleSpeed() {
		return entryVehicleSpeed;
	}
	public void setEntryVehicleSpeed(Long entryVehicleSpeed) {
		this.entryVehicleSpeed = entryVehicleSpeed;
	}
	public Long getLaneTxStatus() {
		return laneTxStatus;
	}
	public void setLaneTxStatus(Long laneTxStatus) {
		this.laneTxStatus = laneTxStatus;
	}
	public Long getLaneTxType() {
		return laneTxType;
	}
	public void setLaneTxType(Long laneTxType) {
		this.laneTxType = laneTxType;
	}
	public Long getTollRevenueType() {
		return tollRevenueType;
	}
	public void setTollRevenueType(Long tollRevenueType) {
		this.tollRevenueType = tollRevenueType;
	}
	public Long getActualClass() {
		return actualClass;
	}
	public void setActualClass(Long actualClass) {
		this.actualClass = actualClass;
	}
	public Long getActualAxles() {
		return actualAxles;
	}
	public void setActualAxles(Long actualAxles) {
		this.actualAxles = actualAxles;
	}
	public Long getActualExtraAxles() {
		return actualExtraAxles;
	}
	public void setActualExtraAxles(Long actualExtraAxles) {
		this.actualExtraAxles = actualExtraAxles;
	}
	public Long getCollectorClass() {
		return collectorClass;
	}
	public void setCollectorClass(Long collectorClass) {
		this.collectorClass = collectorClass;
	}
	public Long getCollectorAxles() {
		return collectorAxles;
	}
	public void setCollectorAxles(Long collectorAxles) {
		this.collectorAxles = collectorAxles;
	}
	public Long getPreclassClass() {
		return preclassClass;
	}
	public void setPreclassClass(Long preclassClass) {
		this.preclassClass = preclassClass;
	}
	public Long getPreclassAxles() {
		return preclassAxles;
	}
	public void setPreclassAxles(Long preclassAxles) {
		this.preclassAxles = preclassAxles;
	}
	public Long getPostclassClass() {
		return postclassClass;
	}
	public void setPostclassClass(Long postclassClass) {
		this.postclassClass = postclassClass;
	}
	public Long getPostclassAxles() {
		return postclassAxles;
	}
	public void setPostclassAxles(Long postclassAxles) {
		this.postclassAxles = postclassAxles;
	}
	public Long getForwardAxles() {
		return forwardAxles;
	}
	public void setForwardAxles(Long forwardAxles) {
		this.forwardAxles = forwardAxles;
	}
	public Long getReverseAxles() {
		return reverseAxles;
	}
	public void setReverseAxles(Long reverseAxles) {
		this.reverseAxles = reverseAxles;
	}
	public double getDiscountedAmount() {
		return discountedAmount;
	}
	public void setDiscountedAmount(double discountedAmount) {
		this.discountedAmount = discountedAmount;
	}
	public Long getCollectedAmount() {
		return collectedAmount;
	}
	public void setCollectedAmount(Long collectedAmount) {
		this.collectedAmount = collectedAmount;
	}
	public Long getUnRealizedAmount() {
		return unRealizedAmount;
	}
	public void setUnRealizedAmount(Long unRealizedAmount) {
		this.unRealizedAmount = unRealizedAmount;
	}
	public Long getVehicleSpeed() {
		return vehicleSpeed;
	}
	public void setVehicleSpeed(Long vehicleSpeed) {
		this.vehicleSpeed = vehicleSpeed;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public Long getAccountType() {
		return accountType;
	}
	public void setAccountType(Long accountType) {
		this.accountType = accountType;
	}
	public Long getDeviceIagClass() {
		return deviceIagClass;
	}
	public void setDeviceIagClass(Long deviceIagClass) {
		this.deviceIagClass = deviceIagClass;
	}
	public Long getDeviceAxles() {
		return deviceAxles;
	}
	public void setDeviceAxles(Long deviceAxles) {
		this.deviceAxles = deviceAxles;
	}
	public Long getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(Long etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	public Long getAccountAgencyId() {
		return accountAgencyId;
	}
	public void setAccountAgencyId(Long accountAgencyId) {
		this.accountAgencyId = accountAgencyId;
	}
	public Long getReadAviClass() {
		return readAviClass;
	}
	public void setReadAviClass(Long readAviClass) {
		this.readAviClass = readAviClass;
	}
	public Long getReadAviAxles() {
		return readAviAxles;
	}
	public void setReadAviAxles(Long readAviAxles) {
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
	public Long getPostDeviceStatus() {
		return postDeviceStatus;
	}
	public void setPostDeviceStatus(Long postDeviceStatus) {
		this.postDeviceStatus = postDeviceStatus;
	}
	public Long getPreTxnBalance() {
		return preTxnBalance;
	}
	public void setPreTxnBalance(Long preTxnBalance) {
		this.preTxnBalance = preTxnBalance;
	}
	public Long getTxStatus() {
		return txStatus;
	}
	public void setTxStatus(Long txStatus) {
		this.txStatus = txStatus;
	}
	public String getSpeedViolFlag() {
		return speedViolFlag;
	}
	public void setSpeedViolFlag(String speedViolFlag) {
		this.speedViolFlag = speedViolFlag;
	}
	public String getImagetaken() {
		return imagetaken;
	}
	public void setImagetaken(String imagetaken) {
		this.imagetaken = imagetaken;
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
	public LocalDate getRevenueDate() {
		return revenueDate;
	}
	public void setRevenueDate(LocalDate revenueDate) {
		this.revenueDate = revenueDate;
	}
	public Long getAtpFileId() {
		return atpFileId;
	}
	public void setAtpFileId(Long atpFileId) {
		this.atpFileId = atpFileId;
	}
	public String getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(String updateTs) {
		this.updateTs = updateTs;
	}
	public String getMatchedTxExternRefNo() {
		return matchedTxExternRefNo;
	}
	public void setMatchedTxExternRefNo(String matchedTxExternRefNo) {
		this.matchedTxExternRefNo = matchedTxExternRefNo;
	}
	public String getTxCompleteRefNo() {
		return txCompleteRefNo;
	}
	public void setTxCompleteRefNo(String txCompleteRefNo) {
		this.txCompleteRefNo = txCompleteRefNo;
	}
	public String getTxMatchStatus() {
		return txMatchStatus;
	}
	public void setTxMatchStatus(String txMatchStatus) {
		this.txMatchStatus = txMatchStatus;
	}
	public LocalDate getExternFileDate() {
		return externFileDate;
	}
	public void setExternFileDate(LocalDate externFileDate) {
		this.externFileDate = externFileDate;
	}
	public LocalDate getTxDate() {
		return txDate;
	}
	public void setTxDate(LocalDate txDate) {
		this.txDate = txDate;
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
	public String getIsReciprocityTxn() {
		return isReciprocityTxn;
	}
	public void setIsReciprocityTxn(String isReciprocityTxn) {
		this.isReciprocityTxn = isReciprocityTxn;
	}
	public String getCscLookUpKey() {
		return cscLookUpKey;
	}
	public void setCscLookUpKey(String cscLookUpKey) {
		this.cscLookUpKey = cscLookUpKey;
	}
	public Long getFullFareAmount() {
		return fullFareAmount;
	}
	public void setFullFareAmount(Long fullFareAmount) {
		this.fullFareAmount = fullFareAmount;
	}
	public String getStatusCd() {
		return statusCd;
	}
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public double getCashFareAmount() {
		return cashFareAmount;
	}
	public void setCashFareAmount(double cashFareAmount) {
		this.cashFareAmount = cashFareAmount;
	}
	public double getDiscountedAmount2() {
		return discountedAmount2;
	}
	public void setDiscountedAmount2(double discountedAmount2) {
		this.discountedAmount2 = discountedAmount2;
	}
	public double getEtcFareAmount() {
		return etcFareAmount;
	}
	public void setEtcFareAmount(double etcFareAmount) {
		this.etcFareAmount = etcFareAmount;
	}
	public double getExpectedRevenueAmount() {
		return expectedRevenueAmount;
	}
	public void setExpectedRevenueAmount(double expectedRevenueAmount) {
		this.expectedRevenueAmount = expectedRevenueAmount;
	}
	public double getPostedFareAmount() {
		return postedFareAmount;
	}
	public void setPostedFareAmount(double postedFareAmount) {
		this.postedFareAmount = postedFareAmount;
	}
	public double getVideoFareAmount() {
		return videoFareAmount;
	}
	public void setVideoFareAmount(double videoFareAmount) {
		this.videoFareAmount = videoFareAmount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

	
	
	
	/*public TollUnmatchedTxRequestModel() {
		super();
		// TODO Auto-generated constructor stub
	}*/
	
	

	

	
	

}
