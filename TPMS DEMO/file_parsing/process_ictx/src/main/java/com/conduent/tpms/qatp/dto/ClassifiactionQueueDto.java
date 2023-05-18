package com.conduent.tpms.qatp.dto;

import java.time.LocalDateTime;
import java.util.Date;

import com.google.gson.annotations.Expose;

public class ClassifiactionQueueDto 
{
	@Expose(serialize = true, deserialize = true)
	private Long laneTxId;
	@Expose(serialize = true, deserialize = true)
	private Long txExternRefNo;
	@Expose(serialize = true, deserialize = true)
	private String txTypeInd;
	@Expose(serialize = true, deserialize = true)
	private String txSubType;
	@Expose(serialize = true, deserialize = true)
	private String tollSystemType;
	@Expose(serialize = true, deserialize = true)
	private Integer tollRevenueType;
	@Expose(serialize = true, deserialize = true)
	private LocalDateTime entryTxTimeStamp;
	@Expose(serialize = true, deserialize = true)
	private Integer entryPlazaId;
	@Expose(serialize = true, deserialize = true)
	private Integer entryLaneId;
	@Expose(serialize = true, deserialize = true)
	private Integer entryTxSeqNumber;
	@Expose(serialize = true, deserialize = true)
	private String	entryDataSource;
	@Expose(serialize = true, deserialize = true)
	private Integer entryVehicleSpeed;
	@Expose(serialize = true, deserialize = true)
	private Integer plazaAgencyId;
	@Expose(serialize = true, deserialize = true)
	private Integer plazaId;
	@Expose(serialize = true, deserialize = true)
	private Integer laneId;
	@Expose(serialize = true, deserialize = true)
	private Date txDate;
	@Expose(serialize = true, deserialize = true)
	private LocalDateTime txTimeStamp;
	@Expose(serialize = true, deserialize = true)
	private Integer laneMode;
	@Expose(serialize = true, deserialize = true)
	private Integer laneState;
	@Expose(serialize = true, deserialize = true)
	private Long laneSn;
	@Expose(serialize = true, deserialize = true)
	private String deviceNo;
	@Expose(serialize = true, deserialize = true)
	private Integer deviceCodedClass;        
	@Expose(serialize = true, deserialize = true)
	private Integer deviceIagClass;
	@Expose(serialize = true, deserialize = true)
	private Integer deviceAgencyClass;
	@Expose(serialize = true, deserialize = true)
	private Integer tagAxles;
	@Expose(serialize = true, deserialize = true)
	private Integer actualClass;
	@Expose(serialize = true, deserialize = true)
	private Integer actualAxles;
	@Expose(serialize = true, deserialize = true)
	private Integer extraAxles;
	@Expose(serialize = true, deserialize = true)
	private String plateState;
	@Expose(serialize = true, deserialize = true)
	private String plateNumber;
	@Expose(serialize = true, deserialize = true)
	private Integer plateType;        
	@Expose(serialize = true, deserialize = true)
	private String plateCountry;
	@Expose(serialize = true, deserialize = true)
	private Integer readPerf;
	@Expose(serialize = true, deserialize = true)
	private Integer writePerf;
	@Expose(serialize = true, deserialize = true)
	private Integer progStat;
	@Expose(serialize = true, deserialize = true)
	private String collectorNumber;  
	@Expose(serialize = true, deserialize = true)
	private String imageCaptured;
	@Expose(serialize = true, deserialize = true)
	private Integer vehicleSpeed;
	@Expose(serialize = true, deserialize = true)
	private Integer speedViolation;
	@Expose(serialize = true, deserialize = true)
	private String reciprocityTrx;
	@Expose(serialize = true, deserialize = true)
	private String isViolation;
	@Expose(serialize = true, deserialize = true)
	private String isLprEnabled;
	@Expose(serialize = true, deserialize = true)
	private Double fullFareAmount;
	@Expose(serialize = true, deserialize = true)
	private Double discountedAmount;
	@Expose(serialize = true, deserialize = true)
	private Double unrealizedAmount;
	@Expose(serialize = true, deserialize = true)
	private Long extFileId;
	@Expose(serialize = true, deserialize = true)
	private Date receivedDate;
	@Expose(serialize = true, deserialize = true)
	private Integer accountType;
	@Expose(serialize = true, deserialize = true)
	private Integer accAgencyId;
	@Expose(serialize = true, deserialize = true)
	private Long etcAccountId;
	@Expose(serialize = true, deserialize = true)
	private Long etcSubAccount;
	@Expose(serialize = true, deserialize = true)
	private String dstFlag;
	@Expose(serialize = true, deserialize = true)
	private String isPeak;
	@Expose(serialize = true, deserialize = true)
	private Integer fareType;
	@Expose(serialize = true, deserialize = true)
	private LocalDateTime updateTs;
	public Long getLaneTxId() {
		return laneTxId;
	}
	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
	}
	public Long getTxExternRefNo() {
		return txExternRefNo;
	}
	public void setTxExternRefNo(Long txExternRefNo) {
		this.txExternRefNo = txExternRefNo;
	}
	public String getTxTypeInd() {
		return txTypeInd;
	}
	public void setTxTypeInd(String txTypeInd) {
		this.txTypeInd = txTypeInd;
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
	public LocalDateTime getEntryTxTimeStamp() {
		return entryTxTimeStamp;
	}
	public void setEntryTxTimeStamp(LocalDateTime entryTxTimeStamp) {
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
	public Integer getEntryVehicleSpeed() {
		return entryVehicleSpeed;
	}
	public void setEntryVehicleSpeed(Integer entryVehicleSpeed) {
		this.entryVehicleSpeed = entryVehicleSpeed;
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
	public Date getTxDate() {
		return txDate;
	}
	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}
	public LocalDateTime getTxTimeStamp() {
		return txTimeStamp;
	}
	public void setTxTimeStamp(LocalDateTime txTimeStamp) {
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
	public Integer getProgStat() {
		return progStat;
	}
	public void setProgStat(Integer progStat) {
		this.progStat = progStat;
	}
	public String getCollectorNumber() {
		return collectorNumber;
	}
	public void setCollectorNumber(String collectorNumber) {
		this.collectorNumber = collectorNumber;
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
	public Double getFullFareAmount() {
		return fullFareAmount;
	}
	public void setFullFareAmount(Double fullFareAmount) {
		this.fullFareAmount = fullFareAmount;
	}
	public Double getDiscountedAmount() {
		return discountedAmount;
	}
	public void setDiscountedAmount(Double discountedAmount) {
		this.discountedAmount = discountedAmount;
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
	public Date getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(Date receivedDate) {
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
	public LocalDateTime getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}
}
