package com.conduent.tpms.iag.model;

import java.security.Timestamp;
import java.time.LocalDateTime;

import com.conduent.tpms.iag.utility.Convertor;
import com.conduent.tpms.iag.utility.DateUtils;

public class QatpBufferRecordType {
	private String laneTxId;
	private String txExternRefNo;
	private String txSeqNumber;
    private String externFileId;
	private String  plazaAgencyId;
	private String  laneId;
	private String  PlazaId;
	private String  txTimestamp;
	private String  txModSeq;
	private String  txTypeInd;
	private String  txSubtype;
	private String  tillSystemType;
	private String  laneMode;
	private String  laneType;
	private String  laneState;
	private String  laneHealth;
	private String  tourSegmentId;
	private String  entryDataSource;
	private String  entryLaneId;
	private String  entryPlazaId;
	private String  entryTimestamp;
	private String  entryTxSeqNumber;
	private String  entryVehicleSpeed;
	private String  laneTxStatus;
	private String  lanetxType;
	private String  tollRevenueType;
	private String  actualClass;
	private String  actualAxles;
	private String  actualExtraAxles;
	private String  collectorClass;
	private String  collectorAxles;
	private String  preClassClass;
	private String  preClassAxles;
	private String  postClassClass;
	private String  postClassAxles;
	private String  forwardAxles;
	private String  reverseAxles;
	private String  fullFareAmount;
	private String  discountedAmount;
	private String  collectedAmount;
	private String  unrealizedAmount;
	private String  isDiscountable;
	private String  isMedianFare;
	private String  isPeak;
	private String  priceScheduleId;
	private String  vehicleSpeed;
	private String  receiptIssue;
	private String  deviceNo;
	private String  deviceCodedClass;
	private String  deviceAgencyClass;
	private String  deviceIagClass;
	private String  deviceAxles;
	private String  etcAccountId;
	private String  accountType;
	private String  accountAgencyId;
	private String  readAviClass;
	private String  readAviAxles;
	private String  deviceProgramStatus;
	private String  bufferReadFlag;
	private String  laneDeviceStatus;
	private String  postDeviceStatus;
	private String  preTxnBalance;
	private String  planTypeId;
	private String  etcTxStatus;
	private String  speedViolFlag;
	private String  imageTaken;
	private String  plateCountry;
	private String  plateState;
	private String  plateNumber;
	private String  revenueDate;
	private String  postedDate;
	private String  atpFileId;
	private String  isReversed;
	private String  corrReasonId;
	private String  reconDate;
	private String  reconStatusInd;
	private String  reconSubCodeInd;
	private String  externFileDate;
	private String  mileage;
	private String  deviceReadCount;
	private String  deviceWriteCount;
	private String  entryDeviceReadCount;
	private String  entryDeviceWriteCount;
	private String  depositId;
	private String  txDate;
	private String  cscLookupKey;
	private String  updateTs;
	private String  tollSystemType;
	private String  collectorID; 
	private String  receprocityTxns;
	private String  debitCreditInd;
	private String  tollAmount;
	private Timestamp entryTimeStamp;
	public String getLaneTxId() {
		return laneTxId;
	}
	public void setLaneTxId(String laneTxId) {
		this.laneTxId = laneTxId;
	}
	public String getTxExternRefNo() {
		return txExternRefNo;
	}
	public void setTxExternRefNo(String txExternRefNo) {
		this.txExternRefNo = txExternRefNo;
	}
	public String getTxSeqNumber() {
		return txSeqNumber;
	}
	public void setTxSeqNumber(String txSeqNumber) {
		this.txSeqNumber = txSeqNumber;
	}
	public String getExternFileId() {
		return externFileId;
	}
	public void setExternFileId(String externFileId) {
		this.externFileId = externFileId;
	}
	public String getPlazaAgencyId() {
		return plazaAgencyId;
	}
	public void setPlazaAgencyId(String plazaAgencyId) {
		this.plazaAgencyId = plazaAgencyId;
	}
	public String getLaneId() {
		return laneId;
	}
	public void setLaneId(String laneId) {
		this.laneId = laneId;
	}
	public String getPlazaId() {
		return PlazaId;
	}
	public void setPlazaId(String plazaId) {
		PlazaId = plazaId;
	}
	public String getTxTimestamp() {
		return txTimestamp;
	}
	public void setTxTimestamp(String txTimestamp) {
		this.txTimestamp = txTimestamp;
	}
	public String getTxModSeq() {
		return txModSeq;
	}
	public void setTxModSeq(String txModSeq) {
		this.txModSeq = txModSeq;
	}
	public String getTxTypeInd() {
		return txTypeInd;
	}
	public void setTxTypeInd(String txTypeInd) {
		this.txTypeInd = txTypeInd;
	}
	public String getTxSubtype() {
		return txSubtype;
	}
	public void setTxSubtypeInd(String txSubtypeInd) {
		this.txSubtype = txSubtypeInd;
	}
	public String getTillSystemType() {
		return tillSystemType;
	}
	public void setTillSystemType(String tillSystemType) {
		this.tillSystemType = tillSystemType;
	}
	public String getLaneMode() {
		return laneMode;
	}
	public void setLaneMode(String laneMode) {
		this.laneMode = laneMode;
	}
	public String getLaneType() {
		return laneType;
	}
	public void setLaneType(String laneType) {
		this.laneType = laneType;
	}
	public String getLaneState() {
		return laneState;
	}
	public void setLaneState(String laneState) {
		this.laneState = laneState;
	}
	public String getLaneHealth() {
		return laneHealth;
	}
	public void setLaneHealth(String laneHealth) {
		this.laneHealth = laneHealth;
	}
	public String getTourSegmentId() {
		return tourSegmentId;
	}
	public void setTourSegmentId(String tourSegmentId) {
		this.tourSegmentId = tourSegmentId;
	}
	public String getEntryDataSource() {
		return entryDataSource;
	}
	public void setEntryDataSource(String entryDataSource) {
		this.entryDataSource = entryDataSource;
	}
	public String getEntryLaneId() {
		return entryLaneId;
	}
	public void setEntryLaneId(String entryLaneId) {
		this.entryLaneId = entryLaneId;
	}
	public String getEntryPlazaId() {
		return entryPlazaId;
	}
	public void setEntryPlazaId(String entryPlazaId) {
		this.entryPlazaId = entryPlazaId;
	}
	public String getEntryTimestamp() {
		return entryTimestamp;
	}
	public void setEntryTimestamp(String entryTimestamp) {
		this.entryTimestamp = entryTimestamp;
	}
	public String getEntryTxSeqNumber() {
		return entryTxSeqNumber;
	}
	public void setEntryTxSeqNumber(String entryTxSeqNumber) {
		this.entryTxSeqNumber = entryTxSeqNumber;
	}
	public String getEntryVehicleSpeed() {
		return entryVehicleSpeed;
	}
	public void setEntryVehicleSpeed(String entryVehicleSpeed) {
		this.entryVehicleSpeed = entryVehicleSpeed;
	}
	public String getLaneTxStatus() {
		return laneTxStatus;
	}
	public void setLaneTxStatus(String laneTxStatus) {
		this.laneTxStatus = laneTxStatus;
	}
	public String getLanetxType() {
		return lanetxType;
	}
	public void setLanetxType(String lanetxType) {
		this.lanetxType = lanetxType;
	}
	public String getTollRevenueType() {
		return tollRevenueType;
	}
	public void setTollRevenueType(String tollRevenueType) {
		this.tollRevenueType = tollRevenueType;
	}
	public String getActualClass() {
		return actualClass;
	}
	public void setActualClass(String actualClass) {
		this.actualClass = actualClass;
	}
	public String getActualAxles() {
		return actualAxles;
	}
	public void setActualAxles(String actualAxles) {
		this.actualAxles = actualAxles;
	}
	public String getActualExtraAxles() {
		return actualExtraAxles;
	}
	public void setActualExtraAxles(String actualExtraAxles) {
		this.actualExtraAxles = actualExtraAxles;
	}
	public String getCollectorClass() {
		return collectorClass;
	}
	public void setCollectorClass(String collectorClass) {
		this.collectorClass = collectorClass;
	}
	public String getCollectorAxles() {
		return collectorAxles;
	}
	public void setCollectorAxles(String collectorAxles) {
		this.collectorAxles = collectorAxles;
	}
	public String getPreClassClass() {
		return preClassClass;
	}
	public void setPreClassClass(String preClassClass) {
		this.preClassClass = preClassClass;
	}
	public String getPreClassAxles() {
		return preClassAxles;
	}
	public void setPreClassAxles(String preClassAxles) {
		this.preClassAxles = preClassAxles;
	}
	public String getPostClassClass() {
		return postClassClass;
	}
	public void setPostClassClass(String postClassClass) {
		this.postClassClass = postClassClass;
	}
	public String getPostClassAxles() {
		return postClassAxles;
	}
	public void setPostClassAxles(String postClassAxles) {
		this.postClassAxles = postClassAxles;
	}
	public String getForwardAxles() {
		return forwardAxles;
	}
	public void setForwardAxles(String forwardAxles) {
		this.forwardAxles = forwardAxles;
	}
	public String getReverseAxles() {
		return reverseAxles;
	}
	public void setReverseAxles(String reverseAxles) {
		this.reverseAxles = reverseAxles;
	}
	public String getFullFareAmount() {
		return fullFareAmount;
	}
	public void setFullFareAmount(String fullFareAmount) {
		this.fullFareAmount = fullFareAmount;
	}
	public String getDiscountedAmount() {
		return discountedAmount;
	}
	public void setDiscountedAmount(String discountedAmount) {
		this.discountedAmount = discountedAmount;
	}
	public String getCollectedAmount() {
		return collectedAmount;
	}
	public void setCollectedAmount(String collectedAmount) {
		this.collectedAmount = collectedAmount;
	}
	public String getUnrealizedAmount() {
		return unrealizedAmount;
	}
	public void setUnrealizedAmount(String unrealizedAmount) {
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
	public String getPriceScheduleId() {
		return priceScheduleId;
	}
	public void setPriceScheduleId(String priceScheduleId) {
		this.priceScheduleId = priceScheduleId;
	}
	public String getVehicleSpeed() {
		return vehicleSpeed;
	}
	public void setVehicleSpeed(String vehicleSpeed) {
		this.vehicleSpeed = vehicleSpeed;
	}
	public String getReceiptIssue() {
		return receiptIssue;
	}
	public void setReceiptIssue(String receiptIssue) {
		this.receiptIssue = receiptIssue;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public String getDeviceCodedClass() {
		return deviceCodedClass;
	}
	public void setDeviceCodedClass(String deviceCodedClass) {
		this.deviceCodedClass = deviceCodedClass;
	}
	public String getDeviceAgencyClass() {
		return deviceAgencyClass;
	}
	public void setDeviceAgencyClass(String deviceAgencyClass) {
		this.deviceAgencyClass = deviceAgencyClass;
	}
	public String getDeviceIagClass() {
		return deviceIagClass;
	}
	public void setDeviceIagClass(String deviceIagClass) {
		this.deviceIagClass = deviceIagClass;
	}
	public String getDeviceAxles() {
		return deviceAxles;
	}
	public void setDeviceAxles(String deviceAxles) {
		this.deviceAxles = deviceAxles;
	}
	public String getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(String etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAccountAgencyId() {
		return accountAgencyId;
	}
	public void setAccountAgencyId(String accountAgencyId) {
		this.accountAgencyId = accountAgencyId;
	}
	public String getReadAviClass() {
		return readAviClass;
	}
	public void setReadAviClass(String readAviClass) {
		this.readAviClass = readAviClass;
	}
	public String getReadAviAxles() {
		return readAviAxles;
	}
	public void setReadAviAxles(String readAviAxles) {
		this.readAviAxles = readAviAxles;
	}
	public String getDeviceProgramStatus() {
		return deviceProgramStatus;
	}
	public void setDeviceProgramStatus(String deviceProgramStatus) {
		this.deviceProgramStatus = deviceProgramStatus;
	}
	public String getBufferReadFlag() {
		return bufferReadFlag;
	}
	public void setBufferReadFlag(String bufferReadFlag) {
		this.bufferReadFlag = bufferReadFlag;
	}
	public String getLaneDeviceStatus() {
		return laneDeviceStatus;
	}
	public void setLaneDeviceStatus(String laneDeviceStatus) {
		this.laneDeviceStatus = laneDeviceStatus;
	}
	public String getPostDeviceStatus() {
		return postDeviceStatus;
	}
	public void setPostDeviceStatus(String postDeviceStatus) {
		this.postDeviceStatus = postDeviceStatus;
	}
	public String getPreTxnBalance() {
		return preTxnBalance;
	}
	public void setPreTxnBalance(String preTxnBalance) {
		this.preTxnBalance = preTxnBalance;
	}
	public String getPlanTypeId() {
		return planTypeId;
	}
	public void setPlanTypeId(String planTypeId) {
		this.planTypeId = planTypeId;
	}
	public String getEtcTxStatus() {
		return etcTxStatus;
	}
	public void setEtcTxStatus(String etcTxStatus) {
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
	public String getAtpFileId() {
		return atpFileId;
	}
	public void setAtpFileId(String atpFileId) {
		this.atpFileId = atpFileId;
	}
	public String getIsReversed() {
		return isReversed;
	}
	public void setIsReversed(String isReversed) {
		this.isReversed = isReversed;
	}
	public String getCorrReasonId() {
		return corrReasonId;
	}
	public void setCorrReasonId(String corrReasonId) {
		this.corrReasonId = corrReasonId;
	}
	public String getReconDate() {
		return reconDate;
	}
	public void setReconDate(String reconDate) {
		this.reconDate = reconDate;
	}
	public String getReconStatusInd() {
		return reconStatusInd;
	}
	public void setReconStatusInd(String reconStatusInd) {
		this.reconStatusInd = reconStatusInd;
	}
	public String getReconSubCodeInd() {
		return reconSubCodeInd;
	}
	public void setReconSubCodeInd(String reconSubCodeInd) {
		this.reconSubCodeInd = reconSubCodeInd;
	}
	public String getExternFileDate() {
		return externFileDate;
	}
	public void setExternFileDate(String externFileDate) {
		this.externFileDate = externFileDate;
	}
	public String getMileage() {
		return mileage;
	}
	public void setMileage(String mileage) {
		this.mileage = mileage;
	}
	public String getDeviceReadCount() {
		return deviceReadCount;
	}
	public void setDeviceReadCount(String deviceReadCount) {
		this.deviceReadCount = deviceReadCount;
	}
	public String getDeviceWriteCount() {
		return deviceWriteCount;
	}
	public void setDeviceWriteCount(String deviceWriteCount) {
		this.deviceWriteCount = deviceWriteCount;
	}
	public String getEntryDeviceReadCount() {
		return entryDeviceReadCount;
	}
	public void setEntryDeviceReadCount(String entryDeviceReadCount) {
		this.entryDeviceReadCount = entryDeviceReadCount;
	}
	public String getEntryDeviceWriteCount() {
		return entryDeviceWriteCount;
	}
	public void setEntryDeviceWriteCount(String entryDeviceWriteCount) {
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
	public String getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(String updateTs) {
		this.updateTs = updateTs;
	}
	public String getTollSystemType() {
		return tollSystemType;
	}
	public void setTollSystemType(String tollSystemType) {
		this.tollSystemType = tollSystemType;
	}
	public String getCollectorID() {
		return collectorID;
	}
	public void setCollectorID(String collectorID) {
		this.collectorID = collectorID;
	}
	public String getReceprocityTxns() {
		return receprocityTxns;
	}
	public void setReceprocityTxns(String receprocityTxns) {
		this.receprocityTxns = receprocityTxns;
	}
	public String getDebitCreditInd() {
		return debitCreditInd;
	}
	public void setDebitCreditInd(String debitCreditInd) {
		this.debitCreditInd = debitCreditInd;
	}
	public String getTollAmount() {
		return tollAmount;
	}
	public void setTollAmount(String tollAmount) {
		this.tollAmount = tollAmount;
	}

	public TranDetail getTranDetail()
	{
		TranDetail obj=new TranDetail();
		obj.setAccountAgencyId(null);
		obj.setAccountType(null);
		//obj.setActualAxles(actualAxles);
		//obj.setActualClass(actualClass);
		//obj.setCollectorNumber(collectorNumber + "");
		obj.setDeviceAgencyClass(null);
		obj.setDeviceCodedClass(null);
		obj.setDeviceIagClass(null);
		//obj.setDeviceNo(deviceNumber);
		obj.setDiscountedAmount(null);
		//obj.setDstFlag(dstFlag);
		obj.setEntryDataSource(entryDataSource);
		//obj.setEntryLaneId(entryLaneId);
		//obj.setEntryPlazaId(entryPlaza);
		// obj.setEntryTxSeqNumber(entryTxSeqNumber); :TODO PB will share
		// obj.setEntryTxTimeStamp(entryTimeStamp.toLocalDateTime());
		//obj.setEntryVehicleSpeed(entryVehicleSpeed);
		obj.setEtcAccountId(null);
		obj.setEtcSubAccount(null);
		//obj.setExtFileId(extFileId);
		//obj.setExtraAxles(extraAxles);
		obj.setFareType(null);
		//obj.setImageCaptured(imageCaptured);
		//obj.setIsLprEnabled(isLprEnabled != null ? isLprEnabled + "" : "0");
		obj.setIsPeak(isPeak);
		//obj.setIsViolation(isViolation != null ? isViolation + "" : "0");
		//obj.setLaneId(trxLaneId);
		obj.setLaneMode(Convertor.toInteger(laneMode));
		//obj.setLaneSn(trxLaneSN);
		obj.setLaneState(Convertor.toInteger(laneState));
		obj.setPlateCountry(plateCountry);
		// obj.setPlateState(plateState);
		// obj.setPlateType(plateType);
		// obj.setPlateNumber(plateNumber);
		//obj.setPlateState(licenseState);
		//obj.setPlateType(Convertor.toInteger(licPlateType));// store as 0
		//obj.setPlateNumber(licensePlate);

		//obj.setVehicleSpeed(vehicleSpeed != null ? vehicleSpeed : 0);
		//obj.setPlazaAgencyId(plazaAgencyId);
		//obj.setPlazaId(exitPlaza);
		//obj.setProgStat(Convertor.toInteger(progStat));// string to int
		//obj.setReadPerf(readPerf);
		//obj.setReceivedDate(DateUtils.parseLocalDate(receivedDate, "yyyyMMdd"));
		obj.setReciprocityTrx(null);
		//obj.setSpeedViolation(speedViolation);
		//obj.setTagAxles(tagAxles);
		//obj.setTollRevenueType(tollRevenueType);
		obj.setTollSystemType(tollSystemType);
		//obj.setTxDate(DateUtils.parseLocalDate(trxDate, "yyyyMMdd"));
		// obj.setTxExternRefNo(StringUtils.leftPad(String.valueOf(trxLaneSN), 18,
		// "00"));
		//if (extHostRefNum != null) {
			//obj.setTxExternRefNo(StringUtils.leftPad(String.valueOf(extHostRefNum), 12, "0"));
		//} else {
			obj.setTxExternRefNo("000000000000");

		//}

		//obj.setTxType(trxType);
		//obj.setTxSubType(trxSubtype);
		//if (txTimeStamp != null) {
			//obj.setTxTimeStamp(txTimeStamp.toLocalDateTime());
		//}

		obj.setUpdateTs(LocalDateTime.now());
		//obj.setWritePerf(writePerf);
		//obj.setExternPlazaId(externPlazaId);
		//obj.setExternLaneId(externLaneId);
		//obj.setLaneDataSource(laneDataSource);
		//obj.setDstFlag(dstFlag);
		//obj.setBufRead(bufRead);
		//obj.setReaderId(readerId);
		//obj.setTourSegment(tourSegmentId);
		//obj.setAvcAxles(AvcAxles);
		//obj.setTagIagClass(tagIAGClass);
		//obj.setAvcClass(AvcClass);
		//obj.setTagClass(tagClass);
		obj.setLaneHealth(laneHealth == null || laneHealth.equals("****") ? 0 : Convertor.toLong(laneHealth));
		//obj.setFullFareAmount(fullFareAmt);
		//obj.setDiscountedAmount(discountedFareOne);
		//obj.setDiscountedAmount2(discountedFareTwo);
		//obj.setVideoFareAmount(videoFareAmount);
		//obj.setTollAmount(tollAmount);
		//obj.setPlanType(atrnPlanType==null?0:atrnPlanType);
		//obj.setPlanCharged(planCharged);
		return obj;

	}

	public Timestamp getEntryTimeStamp() {
		return entryTimeStamp;
	}
}
