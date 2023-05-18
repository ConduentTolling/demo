package com.conduent.tollposting.model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.conduent.tollposting.constant.TxStatus;

public class TranDetail 
{
	private Long laneTxId;
	private String txExternRefNo;
	private String txType;
	private String txSubType;
	private String tollSystemType;
	private Integer tollRevenueType;
	private LocalDateTime entryTxTimeStamp;
	private Integer entryPlazaId;
	private Integer entryLaneId;
	private Integer entryTxSeqNumber;
	private String	entryDataSource;
	private Integer plazaAgencyId;
	private Integer plazaId;
	private Integer laneId;
	private Date txDate;
	private Timestamp txTimeStamp;
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
	private Integer progStat;
	private String collectorNumber;  
	private String imageCaptured;
	private Integer vehicleSpeed;
	private Integer speedViolation;
	private String reciprocityTrx;
	private String isViolation;
	private String isLprEnabled;
	private Double fullFareAmount;
	private Double discountedAmount;
	private Double unrealizedAmount;
	private Long extFileId;
	private Date receivedDate;
	private Integer accountType;
	private Integer accAgencyId;
	private Long etcAccountId;
	private Long etcSubAccount;
	private String dstFlag;
	private String isPeak;
	private Integer fareType;
	private LocalDateTime updateTs;
	private TxStatus etcTxStatus;
	private Integer txStatus;
	private LocalDate postedDate;   
	private String depositId;
	private String rowId;
	private Double postedFareAmount;
	private Integer planType;
	private Integer atpFileId; 
	
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

	/*
	 * public Integer getEntryVehicleSpeed() { return entryVehicleSpeed; } public
	 * void setEntryVehicleSpeed(Integer entryVehicleSpeed) { this.entryVehicleSpeed
	 * = entryVehicleSpeed; }
	 */
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
	
	public Timestamp getTxTimeStamp() {
		return txTimeStamp;
	}
	public void setTxTimeStamp(Timestamp txTimeStamp) {
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
	
	public TxStatus getEtcTxStatus() {
		return etcTxStatus;
	}
	public void setEtcTxStatus(TxStatus etcTxStatus) {
		this.etcTxStatus = etcTxStatus;
	}
	
	public LocalDate getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(LocalDate postedDate) {
		this.postedDate = postedDate;
	}
	public String getDepositId() {
		return depositId;
	}
	public void setDepositId(String depositId) {
		this.depositId = depositId;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public Integer getTxStatus() {
		return txStatus;
	}
	public void setTxStatus(Integer txStatus) {
		this.txStatus = txStatus;
	}
	public Double getPostedFareAmount() {
		return postedFareAmount;
	}
	public void setPostedFareAmount(Double postedFareAmount) {
		this.postedFareAmount = postedFareAmount;
	}
	public Integer getPlanType() {
		return planType;
	}
	public void setPlanType(Integer planType) {
		this.planType = planType;
	}
	public Integer getAtpFileId() {
		return atpFileId;
	}
	public void setAtpFileId(Integer atpFileId) {
		this.atpFileId = atpFileId;
	}
	
	@Override
	public String toString() {
		return "TranDetail [laneTxId=" + laneTxId + ", txExternRefNo=" + txExternRefNo + ", txType=" + txType
				+ ", txSubType=" + txSubType + ", tollSystemType=" + tollSystemType + ", tollRevenueType="
				+ tollRevenueType + ", entryTxTimeStamp=" + entryTxTimeStamp + ", entryPlazaId=" + entryPlazaId
				+ ", entryLaneId=" + entryLaneId + ", entryTxSeqNumber=" + entryTxSeqNumber + ", entryDataSource="
				+ entryDataSource + ", plazaAgencyId=" + plazaAgencyId + ", plazaId=" + plazaId + ", laneId=" + laneId
				+ ", txDate=" + txDate + ", txTimeStamp=" + txTimeStamp + ", laneMode=" + laneMode + ", laneState="
				+ laneState + ", laneSn=" + laneSn + ", deviceNo=" + deviceNo + ", deviceCodedClass=" + deviceCodedClass
				+ ", deviceIagClass=" + deviceIagClass + ", deviceAgencyClass=" + deviceAgencyClass + ", tagAxles="
				+ tagAxles + ", actualClass=" + actualClass + ", actualAxles=" + actualAxles + ", extraAxles="
				+ extraAxles + ", plateState=" + plateState + ", plateNumber=" + plateNumber + ", plateType="
				+ plateType + ", plateCountry=" + plateCountry + ", readPerf=" + readPerf + ", writePerf=" + writePerf
				+ ", progStat=" + progStat + ", collectorNumber=" + collectorNumber + ", imageCaptured=" + imageCaptured
				+ ", vehicleSpeed=" + vehicleSpeed + ", speedViolation=" + speedViolation + ", reciprocityTrx="
				+ reciprocityTrx + ", isViolation=" + isViolation + ", isLprEnabled=" + isLprEnabled
				+ ", fullFareAmount=" + fullFareAmount + ", discountedAmount=" + discountedAmount
				+ ", unrealizedAmount=" + unrealizedAmount + ", extFileId=" + extFileId + ", receivedDate="
				+ receivedDate + ", accountType=" + accountType + ", accAgencyId=" + accAgencyId + ", etcAccountId="
				+ etcAccountId + ", etcSubAccount=" + etcSubAccount + ", dstFlag=" + dstFlag + ", isPeak=" + isPeak
				+ ", fareType=" + fareType + ", updateTs=" + updateTs + ", etcTxStatus=" + etcTxStatus + ", txStatus="
				+ txStatus + ", postedDate=" + postedDate + ", depositId=" + depositId + ", rowId=" + rowId
				+ ", postedFareAmount=" + postedFareAmount + ", planType=" + planType + ", atpFileId=" + atpFileId
				+ "]";
	}
	
}