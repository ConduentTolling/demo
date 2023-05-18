package com.conduent.tpms.unmatched.dto;

import java.time.OffsetDateTime;

/**
 * Common Classification Dto
 * 
 * 
 *
 */
public class TransactionDto {
	private Long laneTxId;
	private String txExternRefNo;
	private String txType;
	private String txSubType;
	private String tollSystemType;
	private Integer tollRevenueType;
	//private String entryTxTimeStamp;
	//private ZonedDateTime entryTxTimeStamp;
	private OffsetDateTime entryTxTimeStamp;
	private Integer entryPlazaId;
	private Integer entryLaneId;
	private Integer entryTxSeqNumber;
	private String entryDataSource;
	private Integer vehicleSpeed;
	private Integer plazaAgencyId;
	private Integer plazaId;
	private Integer laneId;
	private String externPlazaId;
	private String externLaneId;
	private String txDate;
	//private String txTimeStamp;
	//private ZonedDateTime txTimeStamp;
	private OffsetDateTime txTimeStamp;
	private Integer laneMode;
	private Integer laneState;
	private Long laneSn;
	private String deviceNo;
	private Integer deviceCodedClass;
	private Integer deviceIagClass;
	private Integer deviceAgencyClass;
	private Integer tagAxles;
	private Integer actualClass;
	private Integer actualAxles;
	private Integer extraAxles;
	private String plateState;
	private String plateNumber;
	private Integer plateType;
	private String plateCountry;
	private Integer readPerf;
	private Integer writePerf;
	private String progStat;
	private Long collectorNumber;
	private String imageCaptured;
	private String speedViolation;
	private String reciprocityTrx;
	private String isViolation;
	private String isLprEnabled;
	private Double unrealizedAmount;
	private Long extFileId;
	private String receivedDate;
	private Integer accountType;
	private Integer accAgencyId;
	private Long etcAccountId;
	private Long etcSubAccount;
	private String dstFlag;
	private String isPeak;
	private Integer fareType;
	private String updateTs;
	private String txStatus;
	private String aetFlag;
	private String reserved;
	private Integer avcClass;
	private Integer tagClass;
	private String transactionInfo;
	private String laneDataSource;
	private Long laneHealth;
	private Integer avcAxles;
	private Long tourSegment;
	private String bufRead;
	private String readerId;
	private Double discountedAmount2;
	private Double discountedAmount;
	private Long tagIagClass;
	private String planCharged;
	private Integer entryVehicleSpeed;
	private Integer tourSegmentId;
	private String debitCredit;
	private Integer etcValidStatus;
	private Long atpFileId;
	private Integer planType;
	private Double etcFareAmount;
	private Double postedFareAmount;
	private Double expectedRevenueAmount;
	private Double videoFareAmount;
	private Double cashFareAmount;
	
	private String matchedTxExternRefNo;
	private String txCompleteRefNo;
	private String matchedTxStatus;
	private String externFileDate;
	private Integer laneTxStatus;
	private Integer laneTxType;
	private Integer collectorClass;
	private Integer collectorAxles;
	private Integer preClass;
	private Integer preClassAxles;
	private Integer postClass;
	private Integer postClassAxles;
	private Integer forwordAxles;
	private Integer reverseAxles;
	private Integer deviceAxles;
	private Integer readAVIClass;
	private Integer readAVIAxles;
	private String deviceProgramStatus;
	private Double collectedAmount;
	private Integer laneDeviceStatus;
	private Integer postDeviceStatus;
	private Integer preTrxbalance;
	private String revenueDate;
	private String event;
	private String hovFlag;
	private String isReciprocityTrx;
	private String cscLookupKey;
	
	
	
	
	
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
	public String getMatchedTxStatus() {
		return matchedTxStatus;
	}
	public void setMatchedTxStatus(String matchedTxStatus) {
		this.matchedTxStatus = matchedTxStatus;
	}
	public String getExternFileDate() {
		return externFileDate;
	}
	public void setExternFileDate(String externFileDate) {
		this.externFileDate = externFileDate;
	}
	public Integer getLaneTxStatus() {
		return laneTxStatus;
	}
	public void setLaneTxStatus(Integer laneTxStatus) {
		this.laneTxStatus = laneTxStatus;
	}
	public Integer getLaneTxType() {
		return laneTxType;
	}
	public void setLaneTxType(Integer laneTxType) {
		this.laneTxType = laneTxType;
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
	public Integer getPreClass() {
		return preClass;
	}
	public void setPreClass(Integer preClass) {
		this.preClass = preClass;
	}
	public Integer getPreClassAxles() {
		return preClassAxles;
	}
	public void setPreClassAxles(Integer preClassAxles) {
		this.preClassAxles = preClassAxles;
	}
	public Integer getPostClass() {
		return postClass;
	}
	public void setPostClass(Integer postClass) {
		this.postClass = postClass;
	}
	public Integer getPostClassAxles() {
		return postClassAxles;
	}
	public void setPostClassAxles(Integer postClassAxles) {
		this.postClassAxles = postClassAxles;
	}
	public Integer getForwordAxles() {
		return forwordAxles;
	}
	public void setForwordAxles(Integer forwordAxles) {
		this.forwordAxles = forwordAxles;
	}
	public Integer getReverseAxles() {
		return reverseAxles;
	}
	public void setReverseAxles(Integer reverseAxles) {
		this.reverseAxles = reverseAxles;
	}
	public Integer getDeviceAxles() {
		return deviceAxles;
	}
	public void setDeviceAxles(Integer deviceAxles) {
		this.deviceAxles = deviceAxles;
	}
	public Integer getReadAVIClass() {
		return readAVIClass;
	}
	public void setReadAVIClass(Integer readAVIClass) {
		this.readAVIClass = readAVIClass;
	}
	public Integer getReadAVIAxles() {
		return readAVIAxles;
	}
	public void setReadAVIAxles(Integer readAVIAxles) {
		this.readAVIAxles = readAVIAxles;
	}
	public String getDeviceProgramStatus() {
		return deviceProgramStatus;
	}
	public void setDeviceProgramStatus(String deviceProgramStatus) {
		this.deviceProgramStatus = deviceProgramStatus;
	}
	public Double getCollectedAmount() {
		return collectedAmount;
	}
	public void setCollectedAmount(Double collectedAmount) {
		this.collectedAmount = collectedAmount;
	}
	public Integer getLaneDeviceStatus() {
		return laneDeviceStatus;
	}
	public void setLaneDeviceStatus(Integer laneDeviceStatus) {
		this.laneDeviceStatus = laneDeviceStatus;
	}
	public Integer getPostDeviceStatus() {
		return postDeviceStatus;
	}
	public void setPostDeviceStatus(Integer postDeviceStatus) {
		this.postDeviceStatus = postDeviceStatus;
	}
	public Integer getPreTrxbalance() {
		return preTrxbalance;
	}
	public void setPreTrxbalance(Integer preTrxbalance) {
		this.preTrxbalance = preTrxbalance;
	}
	public String getRevenueDate() {
		return revenueDate;
	}
	public void setRevenueDate(String revenueDate) {
		this.revenueDate = revenueDate;
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
	public String getIsReciprocityTrx() {
		return isReciprocityTrx;
	}
	public void setIsReciprocityTrx(String isReciprocityTrx) {
		this.isReciprocityTrx = isReciprocityTrx;
	}
	public String getCscLookupKey() {
		return cscLookupKey;
	}
	public void setCscLookupKey(String cscLookupKey) {
		this.cscLookupKey = cscLookupKey;
	}
	private boolean isSpeedViolation = false;
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
	public OffsetDateTime getEntryTxTimeStamp() {
		return entryTxTimeStamp;
	}
	public void setEntryTxTimeStamp(OffsetDateTime entryTxTimeStamp) {
		this.entryTxTimeStamp = entryTxTimeStamp;
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
	public Integer getVehicleSpeed() {
		return vehicleSpeed;
	}
	public void setVehicleSpeed(Integer vehicleSpeed) {
		this.vehicleSpeed = vehicleSpeed;
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
	public String getTxDate() {
		return txDate;
	}
	public void setTxDate(String txDate) {
		this.txDate = txDate;
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
	public Integer getDeviceIagClass() {
		return deviceIagClass;
	}
	public void setDeviceIagClass(Integer deviceIagClass) {
		this.deviceIagClass = deviceIagClass;
	}
	public Integer getDeviceAgencyClass() {
		return deviceAgencyClass;
	}
	public void setDeviceAgencyClass(Integer deviceAgencyClass) {
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
	public String getProgStat() {
		return progStat;
	}
	public void setProgStat(String progStat) {
		this.progStat = progStat;
	}
	public Long getCollectorNumber() {
		return collectorNumber;
	}
	public void setCollectorNumber(Long collectorNumber) {
		this.collectorNumber = collectorNumber;
	}
	public String getImageCaptured() {
		return imageCaptured;
	}
	public void setImageCaptured(String imageCaptured) {
		this.imageCaptured = imageCaptured;
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
	public String getIsViolation() {
		return isViolation;
	}
	public void setIsViolation(String isViolation) {
		this.isViolation = isViolation;
	}
	public String getIsLprEnabled() {
		return isLprEnabled;
	}
	public void setIsLprEnabled(String isLprEnabled) {
		this.isLprEnabled = isLprEnabled;
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
	public String getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(String receivedDate) {
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
	public String getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(String updateTs) {
		this.updateTs = updateTs;
	}
	public String getTxStatus() {
		return txStatus;
	}
	public void setTxStatus(String txStatus) {
		this.txStatus = txStatus;
	}
	public String getAetFlag() {
		return aetFlag;
	}
	public void setAetFlag(String aetFlag) {
		this.aetFlag = aetFlag;
	}
	public String getReserved() {
		return reserved;
	}
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	public Integer getAvcClass() {
		return avcClass;
	}
	public void setAvcClass(Integer avcClass) {
		this.avcClass = avcClass;
	}
	public Integer getTagClass() {
		return tagClass;
	}
	public void setTagClass(Integer tagClass) {
		this.tagClass = tagClass;
	}
	public String getTransactionInfo() {
		return transactionInfo;
	}
	public void setTransactionInfo(String transactionInfo) {
		this.transactionInfo = transactionInfo;
	}
	public String getLaneDataSource() {
		return laneDataSource;
	}
	public void setLaneDataSource(String laneDataSource) {
		this.laneDataSource = laneDataSource;
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
	public Double getDiscountedAmount2() {
		return discountedAmount2;
	}
	public void setDiscountedAmount2(Double discountedAmount2) {
		this.discountedAmount2 = discountedAmount2;
	}
	public Double getDiscountedAmount() {
		return discountedAmount;
	}
	public void setDiscountedAmount(Double discountedAmount) {
		this.discountedAmount = discountedAmount;
	}
	public Long getTagIagClass() {
		return tagIagClass;
	}
	public void setTagIagClass(Long tagIagClass) {
		this.tagIagClass = tagIagClass;
	}
	public String getPlanCharged() {
		return planCharged;
	}
	public void setPlanCharged(String planCharged) {
		this.planCharged = planCharged;
	}
	public Integer getEntryVehicleSpeed() {
		return entryVehicleSpeed;
	}
	public void setEntryVehicleSpeed(Integer entryVehicleSpeed) {
		this.entryVehicleSpeed = entryVehicleSpeed;
	}
	public Integer getTourSegmentId() {
		return tourSegmentId;
	}
	public void setTourSegmentId(Integer tourSegmentId) {
		this.tourSegmentId = tourSegmentId;
	}
	public String getDebitCredit() {
		return debitCredit;
	}
	public void setDebitCredit(String debitCredit) {
		this.debitCredit = debitCredit;
	}
	public Integer getEtcValidStatus() {
		return etcValidStatus;
	}
	public void setEtcValidStatus(Integer etcValidStatus) {
		this.etcValidStatus = etcValidStatus;
	}
	public Long getAtpFileId() {
		return atpFileId;
	}
	public void setAtpFileId(Long atpFileId) {
		this.atpFileId = atpFileId;
	}
	public Integer getPlanType() {
		return planType;
	}
	public void setPlanType(Integer planType) {
		this.planType = planType;
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
	public boolean isSpeedViolation() {
		return isSpeedViolation;
	}
	public void setSpeedViolation(boolean isSpeedViolation) {
		this.isSpeedViolation = isSpeedViolation;
	}
	@Override
	public String toString() {
		return "TransactionDto [laneTxId=" + laneTxId + ", txExternRefNo=" + txExternRefNo + ", txType=" + txType
				+ ", txSubType=" + txSubType + ", tollSystemType=" + tollSystemType + ", tollRevenueType="
				+ tollRevenueType + ", entryTxTimeStamp=" + entryTxTimeStamp + ", entryPlazaId=" + entryPlazaId
				+ ", entryLaneId=" + entryLaneId + ", entryTxSeqNumber=" + entryTxSeqNumber + ", entryDataSource="
				+ entryDataSource + ", vehicleSpeed=" + vehicleSpeed + ", plazaAgencyId=" + plazaAgencyId + ", plazaId="
				+ plazaId + ", laneId=" + laneId + ", externPlazaId=" + externPlazaId + ", externLaneId=" + externLaneId
				+ ", txDate=" + txDate + ", txTimeStamp=" + txTimeStamp + ", laneMode=" + laneMode + ", laneState="
				+ laneState + ", laneSn=" + laneSn + ", deviceNo=" + deviceNo + ", deviceCodedClass=" + deviceCodedClass
				+ ", deviceIagClass=" + deviceIagClass + ", deviceAgencyClass=" + deviceAgencyClass + ", tagAxles="
				+ tagAxles + ", actualClass=" + actualClass + ", actualAxles=" + actualAxles + ", extraAxles="
				+ extraAxles + ", plateState=" + plateState + ", plateNumber=" + plateNumber + ", plateType="
				+ plateType + ", plateCountry=" + plateCountry + ", readPerf=" + readPerf + ", writePerf=" + writePerf
				+ ", progStat=" + progStat + ", collectorNumber=" + collectorNumber + ", imageCaptured=" + imageCaptured
				+ ", speedViolation=" + speedViolation + ", reciprocityTrx=" + reciprocityTrx + ", isViolation="
				+ isViolation + ", isLprEnabled=" + isLprEnabled + ", unrealizedAmount=" + unrealizedAmount
				+ ", extFileId=" + extFileId + ", receivedDate=" + receivedDate + ", accountType=" + accountType
				+ ", accAgencyId=" + accAgencyId + ", etcAccountId=" + etcAccountId + ", etcSubAccount=" + etcSubAccount
				+ ", dstFlag=" + dstFlag + ", isPeak=" + isPeak + ", fareType=" + fareType + ", updateTs=" + updateTs
				+ ", txStatus=" + txStatus + ", aetFlag=" + aetFlag + ", reserved=" + reserved + ", avcClass="
				+ avcClass + ", tagClass=" + tagClass + ", transactionInfo=" + transactionInfo + ", laneDataSource="
				+ laneDataSource + ", laneHealth=" + laneHealth + ", avcAxles=" + avcAxles + ", tourSegment="
				+ tourSegment + ", bufRead=" + bufRead + ", readerId=" + readerId + ", discountedAmount2="
				+ discountedAmount2 + ", discountedAmount=" + discountedAmount + ", tagIagClass=" + tagIagClass
				+ ", planCharged=" + planCharged + ", entryVehicleSpeed=" + entryVehicleSpeed + ", tourSegmentId="
				+ tourSegmentId + ", debitCredit=" + debitCredit + ", etcValidStatus=" + etcValidStatus + ", atpFileId="
				+ atpFileId + ", planType=" + planType + ", etcFareAmount=" + etcFareAmount + ", postedFareAmount="
				+ postedFareAmount + ", expectedRevenueAmount=" + expectedRevenueAmount + ", videoFareAmount="
				+ videoFareAmount + ", cashFareAmount=" + cashFareAmount + ", matchedTxExternRefNo="
				+ matchedTxExternRefNo + ", txCompleteRefNo=" + txCompleteRefNo + ", matchedTxStatus=" + matchedTxStatus
				+ ", externFileDate=" + externFileDate + ", laneTxStatus=" + laneTxStatus + ", laneTxType=" + laneTxType
				+ ", collectorClass=" + collectorClass + ", collectorAxles=" + collectorAxles + ", preClass=" + preClass
				+ ", preClassAxles=" + preClassAxles + ", postClass=" + postClass + ", postClassAxles=" + postClassAxles
				+ ", forwordAxles=" + forwordAxles + ", reverseAxles=" + reverseAxles + ", deviceAxles=" + deviceAxles
				+ ", readAVIClass=" + readAVIClass + ", readAVIAxles=" + readAVIAxles + ", deviceProgramStatus="
				+ deviceProgramStatus + ", collectedAmount=" + collectedAmount + ", laneDeviceStatus="
				+ laneDeviceStatus + ", postDeviceStatus=" + postDeviceStatus + ", preTrxbalance=" + preTrxbalance
				+ ", revenueDate=" + revenueDate + ", event=" + event + ", hovFlag=" + hovFlag + ", isReciprocityTrx="
				+ isReciprocityTrx + ", cscLookupKey=" + cscLookupKey + ", isSpeedViolation=" + isSpeedViolation + "]";
	}
	
	
}
