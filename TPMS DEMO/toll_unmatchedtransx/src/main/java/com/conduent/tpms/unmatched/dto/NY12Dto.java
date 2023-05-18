package com.conduent.tpms.unmatched.dto;

public class NY12Dto {


	private Long laneTxId;
	private String txExternRefNo;
	private Integer txSeqNumber;
	private Long externFileId;
	private Integer laneId;
	private String txTimestamp;
	private String txTypeInd;
	private String txSubTypeInd;
	private String tollSystemType;
	private Integer laneMode;
	private Integer laneType;
	private Integer laneState;
	private Long laneHealth;
	private Integer plazaAgencyId;
	private Integer plazaId;
	private Long collectorId;
	private Long tourSegmentId;
	private String entryDataSource;
	private Integer entryLaneId;
	private Integer entryPlazaId;
	private String entryTimeStamp;
	private Integer entryTxSeqNumber;
	private Integer entryVehicleSpeed;
	private Integer laneTxStatus;
	private Integer laneTxType;
	private Integer tollRevenueType;
	private Integer actualClass;
	private Integer actualAxles;
	private Integer actualExtraAxles;
	private Integer collectorClass;
	private Integer collectorAxles;
	private Integer preclassClass;
	private Integer preclassAxles;
	private Integer postclassClass;
	private Integer postclassAxles;
	private Integer forwardAxles;
	private Integer reverseAxles;
	private Long discountedAmount;
	private Long collectedAmount;
	private Long unRealizedAmount;
	private Integer vehicleSpeed;
	private String deviceNo;
	private Integer accountType;
	private Integer deviceIagClass;
	private Integer deviceAxles;
	private Long etcAccountId;
	private Integer accountAgencyId;
	private Integer readAviClass;
	private Integer readAviAxles;
	private String deviceProgramStatus;
	private String bufferedReadFlag;
	private Integer laneDeviceStatus;
	private Integer postDeviceStatus;
	private Integer preTxnBalance;
	private String txStatus;
	private String speedViolFlag;
	private String imagetaken;
	private String plateCountry;
	private String plateState;
	private String plateNumber;
	private String revenueDate;
	private Long atpFileId;
	private String updateTs;
	private String matchedTxExternRefNo;
	private String txCompleteRefNo;
	private String txMatchStatus;
	private String externFileDate;
	private String txDate;
	private String event;
	private String hovFlag;
	private String isReciprocityTxn;
	private String cscLookUpKey;
	private double cashFareAmount;
	private double discountedAmount2;
	private double etcFareAmount;
	private double expectedRevenueAmount;
	private double postedFareAmount;
	private double videoFareAmount;
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
	public Integer getTxSeqNumber() {
		return txSeqNumber;
	}
	public void setTxSeqNumber(Integer txSeqNumber) {
		this.txSeqNumber = txSeqNumber;
	}
	public Long getExternFileId() {
		return externFileId;
	}
	public void setExternFileId(Long externFileId) {
		this.externFileId = externFileId;
	}
	public Integer getLaneId() {
		return laneId;
	}
	public void setLaneId(Integer laneId) {
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
	
	public Integer getLaneMode() {
		return laneMode;
	}
	public void setLaneMode(Integer laneMode) {
		this.laneMode = laneMode;
	}
	
	public Integer getLaneType() {
		return laneType;
	}
	public void setLaneType(Integer laneType) {
		this.laneType = laneType;
	}
	public Integer getLaneState() {
		return laneState;
	}
	public void setLaneState(Integer laneState) {
		this.laneState = laneState;
	}
	public Long getLaneHealth() {
		return laneHealth;
	}
	public void setLaneHealth(Long laneHealth) {
		this.laneHealth = laneHealth;
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
	public Integer getLaneTxType() {
		return laneTxType;
	}
	public void setLaneTxType(Integer laneTxType) {
		this.laneTxType = laneTxType;
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
	public Integer getPreclassClass() {
		return preclassClass;
	}
	public void setPreclassClass(Integer preclassClass) {
		this.preclassClass = preclassClass;
	}
	public Integer getPreclassAxles() {
		return preclassAxles;
	}
	public void setPreclassAxles(Integer preclassAxles) {
		this.preclassAxles = preclassAxles;
	}
	public Integer getPostclassClass() {
		return postclassClass;
	}
	public void setPostclassClass(Integer postclassClass) {
		this.postclassClass = postclassClass;
	}
	public Integer getPostclassAxles() {
		return postclassAxles;
	}
	public void setPostclassAxles(Integer postclassAxles) {
		this.postclassAxles = postclassAxles;
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
	
	public Long getDiscountedAmount() {
		return discountedAmount;
	}
	public void setDiscountedAmount(Long discountedAmount) {
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
	public Integer getVehicleSpeed() {
		return vehicleSpeed;
	}
	public void setVehicleSpeed(Integer vehicleSpeed) {
		this.vehicleSpeed = vehicleSpeed;
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
	public Integer getPreTxnBalance() {
		return preTxnBalance;
	}
	public void setPreTxnBalance(Integer preTxnBalance) {
		this.preTxnBalance = preTxnBalance;
	}
	public String getTxStatus() {
		return txStatus;
	}
	public void setTxStatus(String txStatus) {
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
	public String getRevenueDate() {
		return revenueDate;
	}
	public void setRevenueDate(String revenueDate) {
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
	public String getExternFileDate() {
		return externFileDate;
	}
	public void setExternFileDate(String externFileDate) {
		this.externFileDate = externFileDate;
	}
	public String getTxDate() {
		return txDate;
	}
	public void setTxDate(String txDate) {
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
	@Override
	public String toString() {
		return "NY12Dto [laneTxId=" + laneTxId + ", txExternRefNo=" + txExternRefNo + ", txSeqNumber=" + txSeqNumber
				+ ", externFileId=" + externFileId + ", laneId=" + laneId + ", txTimestamp=" + txTimestamp
				+ ", txTypeInd=" + txTypeInd + ", txSubTypeInd=" + txSubTypeInd + ", tollSystemType=" + tollSystemType
				+ ", laneMode=" + laneMode + ", laneType=" + laneType + ", laneState=" + laneState + ", laneHealth="
				+ laneHealth + ", plazaAgencyId=" + plazaAgencyId + ", plazaId=" + plazaId + ", collectorId="
				+ collectorId + ", tourSegmentId=" + tourSegmentId + ", entryDataSource=" + entryDataSource
				+ ", entryLaneId=" + entryLaneId + ", entryPlazaId=" + entryPlazaId + ", entryTimeStamp="
				+ entryTimeStamp + ", entryTxSeqNumber=" + entryTxSeqNumber + ", entryVehicleSpeed=" + entryVehicleSpeed
				+ ", laneTxStatus=" + laneTxStatus + ", laneTxType=" + laneTxType + ", tollRevenueType="
				+ tollRevenueType + ", actualClass=" + actualClass + ", actualAxles=" + actualAxles
				+ ", actualExtraAxles=" + actualExtraAxles + ", collectorClass=" + collectorClass + ", collectorAxles="
				+ collectorAxles + ", preclassClass=" + preclassClass + ", preclassAxles=" + preclassAxles
				+ ", postclassClass=" + postclassClass + ", postclassAxles=" + postclassAxles + ", forwardAxles="
				+ forwardAxles + ", reverseAxles=" + reverseAxles + ", discountedAmount=" + discountedAmount
				+ ", collectedAmount=" + collectedAmount + ", unRealizedAmount=" + unRealizedAmount + ", vehicleSpeed="
				+ vehicleSpeed + ", deviceNo=" + deviceNo + ", accountType=" + accountType + ", deviceIagClass="
				+ deviceIagClass + ", deviceAxles=" + deviceAxles + ", etcAccountId=" + etcAccountId
				+ ", accountAgencyId=" + accountAgencyId + ", readAviClass=" + readAviClass + ", readAviAxles="
				+ readAviAxles + ", deviceProgramStatus=" + deviceProgramStatus + ", bufferedReadFlag="
				+ bufferedReadFlag + ", laneDeviceStatus=" + laneDeviceStatus + ", postDeviceStatus=" + postDeviceStatus
				+ ", preTxnBalance=" + preTxnBalance + ", txStatus=" + txStatus + ", speedViolFlag=" + speedViolFlag
				+ ", imagetaken=" + imagetaken + ", plateCountry=" + plateCountry + ", plateState=" + plateState
				+ ", plateNumber=" + plateNumber + ", revenueDate=" + revenueDate + ", atpFileId=" + atpFileId
				+ ", updateTs=" + updateTs + ", matchedTxExternRefNo=" + matchedTxExternRefNo + ", txCompleteRefNo="
				+ txCompleteRefNo + ", txMatchStatus=" + txMatchStatus + ", externFileDate=" + externFileDate
				+ ", txDate=" + txDate + ", event=" + event + ", hovFlag=" + hovFlag + ", isReciprocityTxn="
				+ isReciprocityTxn + ", cscLookUpKey=" + cscLookUpKey + ", cashFareAmount=" + cashFareAmount
				+ ", discountedAmount2=" + discountedAmount2 + ", etcFareAmount=" + etcFareAmount
				+ ", expectedRevenueAmount=" + expectedRevenueAmount + ", postedFareAmount=" + postedFareAmount
				+ ", videoFareAmount=" + videoFareAmount + "]";
	}

	
	
}