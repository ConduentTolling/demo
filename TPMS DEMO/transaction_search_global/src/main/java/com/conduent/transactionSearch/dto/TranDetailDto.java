package com.conduent.transactionSearch.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class TranDetailDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long          laneTxId;
    private String        txExternRefNo;
    private String        txType;
    private String        txSubType;
    private String        tollSystemType;
    private Integer       tollRevenueType;
    private Integer       entryPlazaId;
    private Integer       entryLaneId;
    private Integer       entryTxSeqNumber;
    private String        entryDataSource;
    private Integer       entryVehicleSpeed;
    private Integer       plazaAgencyId;
    private Integer       plazaId;
    private Integer       laneId;
    private Date          txDate;
    private Integer       laneMode;
    private Integer       laneState;
    private Long          trxLaneSerial;
    private String        deviceNo;
    private Integer       avcClass;
    private Integer       tagIagClass;
    private Integer       tagClass;
    private Integer       tagAxles;
    private Integer       actualClass;
    private Integer       actualAxles;
    private Integer       extraAxles;
    private String        plateState;
    private String        plateNumber;
    private Integer       plateType;
    private String        plateCountry;
    private Integer       readPerf;
    private Integer       writePerf;
    private Integer       progStat;
    private String        collectorNumber;
    private String        imageCaptured;
    private Integer       vehicleSpeed;
    private Integer       speedViolation;
    private String        reciprocityTrx;
    private String        isViolation;
    private String        isLprEnabled;
    private Float         fullFareAmount;
    private Float         discountedAmount;
    private Float         unrealizedAmount;
    private Long          extFileId;
    private Date          receivedDate;
    private Integer       accountType;
    private Integer       acctAgencyId;
    private Long          etcAccountId;
    private Long          etcSubaccount;
    private String        dstFlag;
    private String        isPeak;
    private Integer       fareType;
    private LocalDateTime updateTs;
    private String        etcTxStatus;
    private String        depositId;
    private Date          postedDate;
    private String        laneDataSource;
    private Long          laneType;
    private Long          laneHealth;
    private Integer       avcAxles;
    private Long          tourSegmentId;
    private String        bufRead;
    private String        readerId;
    private Float         tollAmount;
    private String        debitCredit;
    private Integer       etcValidStatus;
    private Float         discountedAmount2;
    private Float         videoFareAmount;
    private Integer       planType;
    private String        reserved;
    private Long          atpFileId;
    private String        transactionInfo;
    private String        planCharged;
    private Float         etcFareAmount;
    private Float         postedFareAmount;
    private Float         expectedRevenueAmount;
    private Float         cashFareAmount;
    private Integer       txStatus;
    private String        aetFlag;
    private LocalDateTime entryTxTimestamp;
    private LocalDateTime txTimestamp;
	
    public TranDetailDto() {
		super();
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

	public Long getTrxLaneSerial() {
		return trxLaneSerial;
	}

	public void setTrxLaneSerial(Long trxLaneSerial) {
		this.trxLaneSerial = trxLaneSerial;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public Integer getAvcClass() {
		return avcClass;
	}

	public void setAvcClass(Integer avcClass) {
		this.avcClass = avcClass;
	}

	public Integer getTagIagClass() {
		return tagIagClass;
	}

	public void setTagIagClass(Integer tagIagClass) {
		this.tagIagClass = tagIagClass;
	}

	public Integer getTagClass() {
		return tagClass;
	}

	public void setTagClass(Integer tagClass) {
		this.tagClass = tagClass;
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

	public Float getFullFareAmount() {
		return fullFareAmount;
	}

	public void setFullFareAmount(Float fullFareAmount) {
		this.fullFareAmount = fullFareAmount;
	}

	public Float getDiscountedAmount() {
		return discountedAmount;
	}

	public void setDiscountedAmount(Float discountedAmount) {
		this.discountedAmount = discountedAmount;
	}

	public Float getUnrealizedAmount() {
		return unrealizedAmount;
	}

	public void setUnrealizedAmount(Float unrealizedAmount) {
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

	public Integer getAcctAgencyId() {
		return acctAgencyId;
	}

	public void setAcctAgencyId(Integer acctAgencyId) {
		this.acctAgencyId = acctAgencyId;
	}

	public Long getEtcAccountId() {
		return etcAccountId;
	}

	public void setEtcAccountId(Long etcAccountId) {
		this.etcAccountId = etcAccountId;
	}

	public Long getEtcSubaccount() {
		return etcSubaccount;
	}

	public void setEtcSubaccount(Long etcSubaccount) {
		this.etcSubaccount = etcSubaccount;
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

	public String getEtcTxStatus() {
		return etcTxStatus;
	}

	public void setEtcTxStatus(String etcTxStatus) {
		this.etcTxStatus = etcTxStatus;
	}

	public String getDepositId() {
		return depositId;
	}

	public void setDepositId(String depositId) {
		this.depositId = depositId;
	}

	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	public String getLaneDataSource() {
		return laneDataSource;
	}

	public void setLaneDataSource(String laneDataSource) {
		this.laneDataSource = laneDataSource;
	}

	public Long getLaneType() {
		return laneType;
	}

	public void setLaneType(Long laneType) {
		this.laneType = laneType;
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

	public Long getTourSegmentId() {
		return tourSegmentId;
	}

	public void setTourSegmentId(Long tourSegmentId) {
		this.tourSegmentId = tourSegmentId;
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

	public Float getTollAmount() {
		return tollAmount;
	}

	public void setTollAmount(Float tollAmount) {
		this.tollAmount = tollAmount;
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

	public Float getDiscountedAmount2() {
		return discountedAmount2;
	}

	public void setDiscountedAmount2(Float discountedAmount2) {
		this.discountedAmount2 = discountedAmount2;
	}

	public Float getVideoFareAmount() {
		return videoFareAmount;
	}

	public void setVideoFareAmount(Float videoFareAmount) {
		this.videoFareAmount = videoFareAmount;
	}

	public Integer getPlanType() {
		return planType;
	}

	public void setPlanType(Integer planType) {
		this.planType = planType;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	public Long getAtpFileId() {
		return atpFileId;
	}

	public void setAtpFileId(Long atpFileId) {
		this.atpFileId = atpFileId;
	}

	public String getTransactionInfo() {
		return transactionInfo;
	}

	public void setTransactionInfo(String transactionInfo) {
		this.transactionInfo = transactionInfo;
	}

	public String getPlanCharged() {
		return planCharged;
	}

	public void setPlanCharged(String planCharged) {
		this.planCharged = planCharged;
	}

	public Float getEtcFareAmount() {
		return etcFareAmount;
	}

	public void setEtcFareAmount(Float etcFareAmount) {
		this.etcFareAmount = etcFareAmount;
	}

	public Float getPostedFareAmount() {
		return postedFareAmount;
	}

	public void setPostedFareAmount(Float postedFareAmount) {
		this.postedFareAmount = postedFareAmount;
	}

	public Float getExpectedRevenueAmount() {
		return expectedRevenueAmount;
	}

	public void setExpectedRevenueAmount(Float expectedRevenueAmount) {
		this.expectedRevenueAmount = expectedRevenueAmount;
	}

	public Float getCashFareAmount() {
		return cashFareAmount;
	}

	public void setCashFareAmount(Float cashFareAmount) {
		this.cashFareAmount = cashFareAmount;
	}

	public Integer getTxStatus() {
		return txStatus;
	}

	public void setTxStatus(Integer txStatus) {
		this.txStatus = txStatus;
	}

	public String getAetFlag() {
		return aetFlag;
	}

	public void setAetFlag(String aetFlag) {
		this.aetFlag = aetFlag;
	}

	public LocalDateTime getEntryTxTimestamp() {
		return entryTxTimestamp;
	}

	public void setEntryTxTimestamp(LocalDateTime entryTxTimestamp) {
		this.entryTxTimestamp = entryTxTimestamp;
	}

	public LocalDateTime getTxTimestamp() {
		return txTimestamp;
	}

	public void setTxTimestamp(LocalDateTime txTimestamp) {
		this.txTimestamp = txTimestamp;
	}

	@Override
	public String toString() {
		return "TranDetailDto [laneTxId=" + laneTxId + ", txExternRefNo=" + txExternRefNo + ", txType=" + txType
				+ ", txSubType=" + txSubType + ", tollSystemType=" + tollSystemType + ", tollRevenueType="
				+ tollRevenueType + ", entryPlazaId=" + entryPlazaId + ", entryLaneId=" + entryLaneId
				+ ", entryTxSeqNumber=" + entryTxSeqNumber + ", entryDataSource=" + entryDataSource
				+ ", entryVehicleSpeed=" + entryVehicleSpeed + ", plazaAgencyId=" + plazaAgencyId + ", plazaId="
				+ plazaId + ", laneId=" + laneId + ", txDate=" + txDate + ", laneMode=" + laneMode + ", laneState="
				+ laneState + ", trxLaneSerial=" + trxLaneSerial + ", deviceNo=" + deviceNo + ", avcClass=" + avcClass
				+ ", tagIagClass=" + tagIagClass + ", tagClass=" + tagClass + ", tagAxles=" + tagAxles
				+ ", actualClass=" + actualClass + ", actualAxles=" + actualAxles + ", extraAxles=" + extraAxles
				+ ", plateState=" + plateState + ", plateNumber=" + plateNumber + ", plateType=" + plateType
				+ ", plateCountry=" + plateCountry + ", readPerf=" + readPerf + ", writePerf=" + writePerf
				+ ", progStat=" + progStat + ", collectorNumber=" + collectorNumber + ", imageCaptured=" + imageCaptured
				+ ", vehicleSpeed=" + vehicleSpeed + ", speedViolation=" + speedViolation + ", reciprocityTrx="
				+ reciprocityTrx + ", isViolation=" + isViolation + ", isLprEnabled=" + isLprEnabled
				+ ", fullFareAmount=" + fullFareAmount + ", discountedAmount=" + discountedAmount
				+ ", unrealizedAmount=" + unrealizedAmount + ", extFileId=" + extFileId + ", receivedDate="
				+ receivedDate + ", accountType=" + accountType + ", acctAgencyId=" + acctAgencyId + ", etcAccountId="
				+ etcAccountId + ", etcSubaccount=" + etcSubaccount + ", dstFlag=" + dstFlag + ", isPeak=" + isPeak
				+ ", fareType=" + fareType + ", updateTs=" + updateTs + ", etcTxStatus=" + etcTxStatus + ", depositId="
				+ depositId + ", postedDate=" + postedDate + ", laneDataSource=" + laneDataSource + ", laneType="
				+ laneType + ", laneHealth=" + laneHealth + ", avcAxles=" + avcAxles + ", tourSegmentId="
				+ tourSegmentId + ", bufRead=" + bufRead + ", readerId=" + readerId + ", tollAmount=" + tollAmount
				+ ", debitCredit=" + debitCredit + ", etcValidStatus=" + etcValidStatus + ", discountedAmount2="
				+ discountedAmount2 + ", videoFareAmount=" + videoFareAmount + ", planType=" + planType + ", reserved="
				+ reserved + ", atpFileId=" + atpFileId + ", transactionInfo=" + transactionInfo + ", planCharged="
				+ planCharged + ", etcFareAmount=" + etcFareAmount + ", postedFareAmount=" + postedFareAmount
				+ ", expectedRevenueAmount=" + expectedRevenueAmount + ", cashFareAmount=" + cashFareAmount
				+ ", txStatus=" + txStatus + ", aetFlag=" + aetFlag + ", entryTxTimestamp=" + entryTxTimestamp
				+ ", txTimestamp=" + txTimestamp + "]";
	}

    
    
}
