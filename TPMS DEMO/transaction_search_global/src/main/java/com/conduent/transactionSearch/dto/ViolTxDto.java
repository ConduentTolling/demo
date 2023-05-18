package com.conduent.transactionSearch.dto;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class ViolTxDto implements Serializable {
	private static final long serialVersionUID = 1L;
    private Long    accountAgencyId;
    private String  accountDeviceAgency;
    private String  accountDeviceNo;
    private Integer accountType;
    private Integer actualAxles;
    private Integer actualClass;
    private Integer actualExtraAxles;
    private String  addressSource;
    private Float   adjustedAmount;
    private String  aetFlag;
    private Float   amountPaid;
    private Long    atpFileId;
    private String  badImgIndexes;
    private String  basefilename;
    private String  bufferedReadFlag;
    private Integer citationLevel;
    private Integer citationStatus;
    private Integer classAdjType;
    private Integer collStatus;
    private Float   collectedAmount;
    private Integer collectorAxles;
    private Long    collectorClass;
    private Long    collectorId;
    private Long    corrReasonId;
    private String  cscLookupKey;
    private String        depositId;
    private Integer       deviceAgencyClass;
    private Integer       deviceAxles;
    private Integer       deviceCodedClass;
    private Integer       deviceIagClass;
    private String        deviceNo;
    private String        deviceProgramStatus;
    private Integer       deviceReadCount;
    private Integer       deviceWriteCount;
    private Float         discountedAmount;
    private Long          disputedEtcAcctId;
    private String        dmvMakeId;
    private Long          dmvPlateType;
    private Integer       dmvRetry;
    private Date          dmvReturnDate;
    private String        entryDataSource;
    private Integer       entryDeviceReadCount;
    private Integer       entryDeviceWriteCount;
    private Long          entryLaneId;
    private Long          entryPlazaId;
    private LocalDateTime entryTimestamp;
    private Long          entryTxSeqNumber;
    private Integer       entryVehicleSpeed;
    private Long          etcAccountId;
    private Long          etcTxStatus;
    private LocalDateTime eventTimestamp;
    private Long          eventType;
    private Long          exceptionStatus;
    private Date          externFileDate;
    private Long          externFileId;
    private String        facilityId;
    private Long          fareTblId;
    private LocalDateTime firstCollmailDatetime;
    private Date          firstDmvResponseDate;
    private Date          firstPostedDate;
    private Integer       forwardAxles;
    private Float         fullFareAmount;
    private String        gmtTxTime;
    private Long          imageBatchId;
    private Long          imageBatchSeqNumber;
    private Integer       imageIndex;
    private Integer       imagePickupAttemptCount;
    private Date          imagePickupAttemptDate;
    private Date          imageReceiveDate;
    private LocalDateTime imageReviewTimestamp;
    private Long          imageRvwClerkId;
    private String        imageTaken;
    private String        imdocProcessed;
    private String        imgFileIndex;
    private Long          internalAuditId;
    private String        isAnonymous;
    private String        isClosed;
    private String        isDiscountable;
    private String        isMedianFare;
    private String        isPeak;
    private String        isReciprocityTxn;
    private String        isReversed;
    private Long          laneDeviceStatus;
    private Long          laneHealth;
    private Long          laneId;
    private Long          laneMode;
    private Long          laneSeqNo;
    private Long          laneState;
    private Long          laneTxId;
    private Long          laneTxStatus;
    private Long          laneTxType;
    private Long          laneType;
    private LocalDateTime lastCommDatetime;
    private String        lastCommMode;
    private Integer       limousine;
    private Date          loadDate;
    private String        location;
    private String        lostStolenDeviceFlag;
    private Long          makeId;
    private Integer       mileage;
    private Long          modifiedStatus;
    private String        newAcct;
    private Float         noticeFeeAmount;
    private Float         noticeTollAmount;
    private Float         origDiscountedAmount;
    private Float         origFullFareAmount;
    private Long          origLaneTxId;
    private Integer       outputFileId;
    private String        outputFileType;
    private Long          parLaneTxId;
    private Integer       planTypeId;
    private String        plateCountry;
    private String        plateNumber;
    private String        plateState;
    private Long          plazaAgencyId;
    private Long          plazaId;
    private Long          postDeviceStatus;
    private Integer       postclassAxles;
    private Integer       postclassClass;
    private Date          postedDate;
    private Integer       postingType;
    private Float         preTxnBalance;
    private Integer       preclassAxles;
    private Integer       preclassClass;
    private Integer       prevEventType;
    private Integer       prevViolTxStatus;
    private Integer       priceScheduleId;
    private String        processStatus;
    private Integer       readAviAxles;
    private Integer       readAviClass;
    private Integer       receiptIssued;
    private Date          reconDate;
    private Long          reconPlanId;
    private Integer       reconStatusInd;
    private Integer       reconSubCodeInd;
    private Long          reconSummaryId;
    private Date          reconTimestamp;
    private String        reconTranCode;
    private Long          recvSummaryId;
    private String        recvTranCode;
    private String        rentalCompanyId;
    private Date          revenueDate;
    private Integer       reverseAxles;
    private Long          reviewedClass;
    private Date          reviewedDate;
    private Long          reviewedSegmentId;
    private Long          reviewedVehicleType;
    private String        speedViolFlag;
    private Integer       taxi;
    private Double        taxiLimo;
    private Long          tollRevenueType;
    private String        tollSystemType;
    private Long          tourSegmentId;
    private Date          txDate;
    private String        txExternRefNo;
    private Long          txModSeq;
    private Long          txSeqNumber;
    private String        txSubtypeInd;
    private LocalDateTime txTimestamp;
    private String        txTypeInd;
    private Integer       txnDisputed;
    private Float         unrealizedAmount;
    private LocalDateTime updateTs;
    private Integer       vehicleSpeed;
    private Long          violTxStatus;
    private Long          violType;
    private Long          event;
    private Float         frontImgRejReason;
    private String        hovFlag;
    private String        invoicePaid;
    private Long          trailerPlate;

    public ViolTxDto() {
		super();
	}

	public Long getAccountAgencyId() {
		return accountAgencyId;
	}

	public void setAccountAgencyId(Long accountAgencyId) {
		this.accountAgencyId = accountAgencyId;
	}

	public String getAccountDeviceAgency() {
		return accountDeviceAgency;
	}

	public void setAccountDeviceAgency(String accountDeviceAgency) {
		this.accountDeviceAgency = accountDeviceAgency;
	}

	public String getAccountDeviceNo() {
		return accountDeviceNo;
	}

	public void setAccountDeviceNo(String accountDeviceNo) {
		this.accountDeviceNo = accountDeviceNo;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public Integer getActualAxles() {
		return actualAxles;
	}

	public void setActualAxles(Integer actualAxles) {
		this.actualAxles = actualAxles;
	}

	public Integer getActualClass() {
		return actualClass;
	}

	public void setActualClass(Integer actualClass) {
		this.actualClass = actualClass;
	}

	public Integer getActualExtraAxles() {
		return actualExtraAxles;
	}

	public void setActualExtraAxles(Integer actualExtraAxles) {
		this.actualExtraAxles = actualExtraAxles;
	}

	public String getAddressSource() {
		return addressSource;
	}

	public void setAddressSource(String addressSource) {
		this.addressSource = addressSource;
	}

	public Float getAdjustedAmount() {
		return adjustedAmount;
	}

	public void setAdjustedAmount(Float adjustedAmount) {
		this.adjustedAmount = adjustedAmount;
	}

	public String getAetFlag() {
		return aetFlag;
	}

	public void setAetFlag(String aetFlag) {
		this.aetFlag = aetFlag;
	}

	public Float getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(Float amountPaid) {
		this.amountPaid = amountPaid;
	}

	public Long getAtpFileId() {
		return atpFileId;
	}

	public void setAtpFileId(Long atpFileId) {
		this.atpFileId = atpFileId;
	}

	public String getBadImgIndexes() {
		return badImgIndexes;
	}

	public void setBadImgIndexes(String badImgIndexes) {
		this.badImgIndexes = badImgIndexes;
	}

	public String getBasefilename() {
		return basefilename;
	}

	public void setBasefilename(String basefilename) {
		this.basefilename = basefilename;
	}

	public String getBufferedReadFlag() {
		return bufferedReadFlag;
	}

	public void setBufferedReadFlag(String bufferedReadFlag) {
		this.bufferedReadFlag = bufferedReadFlag;
	}

	public Integer getCitationLevel() {
		return citationLevel;
	}

	public void setCitationLevel(Integer citationLevel) {
		this.citationLevel = citationLevel;
	}

	public Integer getCitationStatus() {
		return citationStatus;
	}

	public void setCitationStatus(Integer citationStatus) {
		this.citationStatus = citationStatus;
	}

	public Integer getClassAdjType() {
		return classAdjType;
	}

	public void setClassAdjType(Integer classAdjType) {
		this.classAdjType = classAdjType;
	}

	public Integer getCollStatus() {
		return collStatus;
	}

	public void setCollStatus(Integer collStatus) {
		this.collStatus = collStatus;
	}

	public Float getCollectedAmount() {
		return collectedAmount;
	}

	public void setCollectedAmount(Float collectedAmount) {
		this.collectedAmount = collectedAmount;
	}

	public Integer getCollectorAxles() {
		return collectorAxles;
	}

	public void setCollectorAxles(Integer collectorAxles) {
		this.collectorAxles = collectorAxles;
	}

	public Long getCollectorClass() {
		return collectorClass;
	}

	public void setCollectorClass(Long collectorClass) {
		this.collectorClass = collectorClass;
	}

	public Long getCollectorId() {
		return collectorId;
	}

	public void setCollectorId(Long collectorId) {
		this.collectorId = collectorId;
	}

	public Long getCorrReasonId() {
		return corrReasonId;
	}

	public void setCorrReasonId(Long corrReasonId) {
		this.corrReasonId = corrReasonId;
	}

	public String getCscLookupKey() {
		return cscLookupKey;
	}

	public void setCscLookupKey(String cscLookupKey) {
		this.cscLookupKey = cscLookupKey;
	}

	public String getDepositId() {
		return depositId;
	}

	public void setDepositId(String depositId) {
		this.depositId = depositId;
	}

	public Integer getDeviceAgencyClass() {
		return deviceAgencyClass;
	}

	public void setDeviceAgencyClass(Integer deviceAgencyClass) {
		this.deviceAgencyClass = deviceAgencyClass;
	}

	public Integer getDeviceAxles() {
		return deviceAxles;
	}

	public void setDeviceAxles(Integer deviceAxles) {
		this.deviceAxles = deviceAxles;
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

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public String getDeviceProgramStatus() {
		return deviceProgramStatus;
	}

	public void setDeviceProgramStatus(String deviceProgramStatus) {
		this.deviceProgramStatus = deviceProgramStatus;
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

	public Float getDiscountedAmount() {
		return discountedAmount;
	}

	public void setDiscountedAmount(Float discountedAmount) {
		this.discountedAmount = discountedAmount;
	}

	public Long getDisputedEtcAcctId() {
		return disputedEtcAcctId;
	}

	public void setDisputedEtcAcctId(Long disputedEtcAcctId) {
		this.disputedEtcAcctId = disputedEtcAcctId;
	}

	public String getDmvMakeId() {
		return dmvMakeId;
	}

	public void setDmvMakeId(String dmvMakeId) {
		this.dmvMakeId = dmvMakeId;
	}

	public Long getDmvPlateType() {
		return dmvPlateType;
	}

	public void setDmvPlateType(Long dmvPlateType) {
		this.dmvPlateType = dmvPlateType;
	}

	public Integer getDmvRetry() {
		return dmvRetry;
	}

	public void setDmvRetry(Integer dmvRetry) {
		this.dmvRetry = dmvRetry;
	}

	public Date getDmvReturnDate() {
		return dmvReturnDate;
	}

	public void setDmvReturnDate(Date dmvReturnDate) {
		this.dmvReturnDate = dmvReturnDate;
	}

	public String getEntryDataSource() {
		return entryDataSource;
	}

	public void setEntryDataSource(String entryDataSource) {
		this.entryDataSource = entryDataSource;
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

	public LocalDateTime getEntryTimestamp() {
		return entryTimestamp;
	}

	public void setEntryTimestamp(LocalDateTime entryTimestamp) {
		this.entryTimestamp = entryTimestamp;
	}

	public Long getEntryTxSeqNumber() {
		return entryTxSeqNumber;
	}

	public void setEntryTxSeqNumber(Long entryTxSeqNumber) {
		this.entryTxSeqNumber = entryTxSeqNumber;
	}

	public Integer getEntryVehicleSpeed() {
		return entryVehicleSpeed;
	}

	public void setEntryVehicleSpeed(Integer entryVehicleSpeed) {
		this.entryVehicleSpeed = entryVehicleSpeed;
	}

	public Long getEtcAccountId() {
		return etcAccountId;
	}

	public void setEtcAccountId(Long etcAccountId) {
		this.etcAccountId = etcAccountId;
	}

	public Long getEtcTxStatus() {
		return etcTxStatus;
	}

	public void setEtcTxStatus(Long etcTxStatus) {
		this.etcTxStatus = etcTxStatus;
	}

	public LocalDateTime getEventTimestamp() {
		return eventTimestamp;
	}

	public void setEventTimestamp(LocalDateTime eventTimestamp) {
		this.eventTimestamp = eventTimestamp;
	}

	public Long getEventType() {
		return eventType;
	}

	public void setEventType(Long eventType) {
		this.eventType = eventType;
	}

	public Long getExceptionStatus() {
		return exceptionStatus;
	}

	public void setExceptionStatus(Long exceptionStatus) {
		this.exceptionStatus = exceptionStatus;
	}

	public Date getExternFileDate() {
		return externFileDate;
	}

	public void setExternFileDate(Date externFileDate) {
		this.externFileDate = externFileDate;
	}

	public Long getExternFileId() {
		return externFileId;
	}

	public void setExternFileId(Long externFileId) {
		this.externFileId = externFileId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public Long getFareTblId() {
		return fareTblId;
	}

	public void setFareTblId(Long fareTblId) {
		this.fareTblId = fareTblId;
	}

	public LocalDateTime getFirstCollmailDatetime() {
		return firstCollmailDatetime;
	}

	public void setFirstCollmailDatetime(LocalDateTime firstCollmailDatetime) {
		this.firstCollmailDatetime = firstCollmailDatetime;
	}

	public Date getFirstDmvResponseDate() {
		return firstDmvResponseDate;
	}

	public void setFirstDmvResponseDate(Date firstDmvResponseDate) {
		this.firstDmvResponseDate = firstDmvResponseDate;
	}

	public Date getFirstPostedDate() {
		return firstPostedDate;
	}

	public void setFirstPostedDate(Date firstPostedDate) {
		this.firstPostedDate = firstPostedDate;
	}

	public Integer getForwardAxles() {
		return forwardAxles;
	}

	public void setForwardAxles(Integer forwardAxles) {
		this.forwardAxles = forwardAxles;
	}

	public Float getFullFareAmount() {
		return fullFareAmount;
	}

	public void setFullFareAmount(Float fullFareAmount) {
		this.fullFareAmount = fullFareAmount;
	}

	public String getGmtTxTime() {
		return gmtTxTime;
	}

	public void setGmtTxTime(String gmtTxTime) {
		this.gmtTxTime = gmtTxTime;
	}

	public Long getImageBatchId() {
		return imageBatchId;
	}

	public void setImageBatchId(Long imageBatchId) {
		this.imageBatchId = imageBatchId;
	}

	public Long getImageBatchSeqNumber() {
		return imageBatchSeqNumber;
	}

	public void setImageBatchSeqNumber(Long imageBatchSeqNumber) {
		this.imageBatchSeqNumber = imageBatchSeqNumber;
	}

	public Integer getImageIndex() {
		return imageIndex;
	}

	public void setImageIndex(Integer imageIndex) {
		this.imageIndex = imageIndex;
	}

	public Integer getImagePickupAttemptCount() {
		return imagePickupAttemptCount;
	}

	public void setImagePickupAttemptCount(Integer imagePickupAttemptCount) {
		this.imagePickupAttemptCount = imagePickupAttemptCount;
	}

	public Date getImagePickupAttemptDate() {
		return imagePickupAttemptDate;
	}

	public void setImagePickupAttemptDate(Date imagePickupAttemptDate) {
		this.imagePickupAttemptDate = imagePickupAttemptDate;
	}

	public Date getImageReceiveDate() {
		return imageReceiveDate;
	}

	public void setImageReceiveDate(Date imageReceiveDate) {
		this.imageReceiveDate = imageReceiveDate;
	}

	public LocalDateTime getImageReviewTimestamp() {
		return imageReviewTimestamp;
	}

	public void setImageReviewTimestamp(LocalDateTime imageReviewTimestamp) {
		this.imageReviewTimestamp = imageReviewTimestamp;
	}

	public Long getImageRvwClerkId() {
		return imageRvwClerkId;
	}

	public void setImageRvwClerkId(Long imageRvwClerkId) {
		this.imageRvwClerkId = imageRvwClerkId;
	}

	public String getImageTaken() {
		return imageTaken;
	}

	public void setImageTaken(String imageTaken) {
		this.imageTaken = imageTaken;
	}

	public String getImdocProcessed() {
		return imdocProcessed;
	}

	public void setImdocProcessed(String imdocProcessed) {
		this.imdocProcessed = imdocProcessed;
	}

	public String getImgFileIndex() {
		return imgFileIndex;
	}

	public void setImgFileIndex(String imgFileIndex) {
		this.imgFileIndex = imgFileIndex;
	}

	public Long getInternalAuditId() {
		return internalAuditId;
	}

	public void setInternalAuditId(Long internalAuditId) {
		this.internalAuditId = internalAuditId;
	}

	public String getIsAnonymous() {
		return isAnonymous;
	}

	public void setIsAnonymous(String isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

	public String getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(String isClosed) {
		this.isClosed = isClosed;
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

	public String getIsReciprocityTxn() {
		return isReciprocityTxn;
	}

	public void setIsReciprocityTxn(String isReciprocityTxn) {
		this.isReciprocityTxn = isReciprocityTxn;
	}

	public String getIsReversed() {
		return isReversed;
	}

	public void setIsReversed(String isReversed) {
		this.isReversed = isReversed;
	}

	public Long getLaneDeviceStatus() {
		return laneDeviceStatus;
	}

	public void setLaneDeviceStatus(Long laneDeviceStatus) {
		this.laneDeviceStatus = laneDeviceStatus;
	}

	public Long getLaneHealth() {
		return laneHealth;
	}

	public void setLaneHealth(Long laneHealth) {
		this.laneHealth = laneHealth;
	}

	public Long getLaneId() {
		return laneId;
	}

	public void setLaneId(Long laneId) {
		this.laneId = laneId;
	}

	public Long getLaneMode() {
		return laneMode;
	}

	public void setLaneMode(Long laneMode) {
		this.laneMode = laneMode;
	}

	public Long getLaneSeqNo() {
		return laneSeqNo;
	}

	public void setLaneSeqNo(Long laneSeqNo) {
		this.laneSeqNo = laneSeqNo;
	}

	public Long getLaneState() {
		return laneState;
	}

	public void setLaneState(Long laneState) {
		this.laneState = laneState;
	}

	public Long getLaneTxId() {
		return laneTxId;
	}

	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
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

	public Long getLaneType() {
		return laneType;
	}

	public void setLaneType(Long laneType) {
		this.laneType = laneType;
	}

	public LocalDateTime getLastCommDatetime() {
		return lastCommDatetime;
	}

	public void setLastCommDatetime(LocalDateTime lastCommDatetime) {
		this.lastCommDatetime = lastCommDatetime;
	}

	public String getLastCommMode() {
		return lastCommMode;
	}

	public void setLastCommMode(String lastCommMode) {
		this.lastCommMode = lastCommMode;
	}

	public Integer getLimousine() {
		return limousine;
	}

	public void setLimousine(Integer limousine) {
		this.limousine = limousine;
	}

	public Date getLoadDate() {
		return loadDate;
	}

	public void setLoadDate(Date loadDate) {
		this.loadDate = loadDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLostStolenDeviceFlag() {
		return lostStolenDeviceFlag;
	}

	public void setLostStolenDeviceFlag(String lostStolenDeviceFlag) {
		this.lostStolenDeviceFlag = lostStolenDeviceFlag;
	}

	public Long getMakeId() {
		return makeId;
	}

	public void setMakeId(Long makeId) {
		this.makeId = makeId;
	}

	public Integer getMileage() {
		return mileage;
	}

	public void setMileage(Integer mileage) {
		this.mileage = mileage;
	}

	public Long getModifiedStatus() {
		return modifiedStatus;
	}

	public void setModifiedStatus(Long modifiedStatus) {
		this.modifiedStatus = modifiedStatus;
	}

	public String getNewAcct() {
		return newAcct;
	}

	public void setNewAcct(String newAcct) {
		this.newAcct = newAcct;
	}

	public Float getNoticeFeeAmount() {
		return noticeFeeAmount;
	}

	public void setNoticeFeeAmount(Float noticeFeeAmount) {
		this.noticeFeeAmount = noticeFeeAmount;
	}

	public Float getNoticeTollAmount() {
		return noticeTollAmount;
	}

	public void setNoticeTollAmount(Float noticeTollAmount) {
		this.noticeTollAmount = noticeTollAmount;
	}

	public Float getOrigDiscountedAmount() {
		return origDiscountedAmount;
	}

	public void setOrigDiscountedAmount(Float origDiscountedAmount) {
		this.origDiscountedAmount = origDiscountedAmount;
	}

	public Float getOrigFullFareAmount() {
		return origFullFareAmount;
	}

	public void setOrigFullFareAmount(Float origFullFareAmount) {
		this.origFullFareAmount = origFullFareAmount;
	}

	public Long getOrigLaneTxId() {
		return origLaneTxId;
	}

	public void setOrigLaneTxId(Long origLaneTxId) {
		this.origLaneTxId = origLaneTxId;
	}

	public Integer getOutputFileId() {
		return outputFileId;
	}

	public void setOutputFileId(Integer outputFileId) {
		this.outputFileId = outputFileId;
	}

	public String getOutputFileType() {
		return outputFileType;
	}

	public void setOutputFileType(String outputFileType) {
		this.outputFileType = outputFileType;
	}

	public Long getParLaneTxId() {
		return parLaneTxId;
	}

	public void setParLaneTxId(Long parLaneTxId) {
		this.parLaneTxId = parLaneTxId;
	}

	public Integer getPlanTypeId() {
		return planTypeId;
	}

	public void setPlanTypeId(Integer planTypeId) {
		this.planTypeId = planTypeId;
	}

	public String getPlateCountry() {
		return plateCountry;
	}

	public void setPlateCountry(String plateCountry) {
		this.plateCountry = plateCountry;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getPlateState() {
		return plateState;
	}

	public void setPlateState(String plateState) {
		this.plateState = plateState;
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

	public Long getPostDeviceStatus() {
		return postDeviceStatus;
	}

	public void setPostDeviceStatus(Long postDeviceStatus) {
		this.postDeviceStatus = postDeviceStatus;
	}

	public Integer getPostclassAxles() {
		return postclassAxles;
	}

	public void setPostclassAxles(Integer postclassAxles) {
		this.postclassAxles = postclassAxles;
	}

	public Integer getPostclassClass() {
		return postclassClass;
	}

	public void setPostclassClass(Integer postclassClass) {
		this.postclassClass = postclassClass;
	}

	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	public Integer getPostingType() {
		return postingType;
	}

	public void setPostingType(Integer postingType) {
		this.postingType = postingType;
	}

	public Float getPreTxnBalance() {
		return preTxnBalance;
	}

	public void setPreTxnBalance(Float preTxnBalance) {
		this.preTxnBalance = preTxnBalance;
	}

	public Integer getPreclassAxles() {
		return preclassAxles;
	}

	public void setPreclassAxles(Integer preclassAxles) {
		this.preclassAxles = preclassAxles;
	}

	public Integer getPreclassClass() {
		return preclassClass;
	}

	public void setPreclassClass(Integer preclassClass) {
		this.preclassClass = preclassClass;
	}

	public Integer getPrevEventType() {
		return prevEventType;
	}

	public void setPrevEventType(Integer prevEventType) {
		this.prevEventType = prevEventType;
	}

	public Integer getPrevViolTxStatus() {
		return prevViolTxStatus;
	}

	public void setPrevViolTxStatus(Integer prevViolTxStatus) {
		this.prevViolTxStatus = prevViolTxStatus;
	}

	public Integer getPriceScheduleId() {
		return priceScheduleId;
	}

	public void setPriceScheduleId(Integer priceScheduleId) {
		this.priceScheduleId = priceScheduleId;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public Integer getReadAviAxles() {
		return readAviAxles;
	}

	public void setReadAviAxles(Integer readAviAxles) {
		this.readAviAxles = readAviAxles;
	}

	public Integer getReadAviClass() {
		return readAviClass;
	}

	public void setReadAviClass(Integer readAviClass) {
		this.readAviClass = readAviClass;
	}

	public Integer getReceiptIssued() {
		return receiptIssued;
	}

	public void setReceiptIssued(Integer receiptIssued) {
		this.receiptIssued = receiptIssued;
	}

	public Date getReconDate() {
		return reconDate;
	}

	public void setReconDate(Date reconDate) {
		this.reconDate = reconDate;
	}

	public Long getReconPlanId() {
		return reconPlanId;
	}

	public void setReconPlanId(Long reconPlanId) {
		this.reconPlanId = reconPlanId;
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

	public Long getReconSummaryId() {
		return reconSummaryId;
	}

	public void setReconSummaryId(Long reconSummaryId) {
		this.reconSummaryId = reconSummaryId;
	}

	public Date getReconTimestamp() {
		return reconTimestamp;
	}

	public void setReconTimestamp(Date reconTimestamp) {
		this.reconTimestamp = reconTimestamp;
	}

	public String getReconTranCode() {
		return reconTranCode;
	}

	public void setReconTranCode(String reconTranCode) {
		this.reconTranCode = reconTranCode;
	}

	public Long getRecvSummaryId() {
		return recvSummaryId;
	}

	public void setRecvSummaryId(Long recvSummaryId) {
		this.recvSummaryId = recvSummaryId;
	}

	public String getRecvTranCode() {
		return recvTranCode;
	}

	public void setRecvTranCode(String recvTranCode) {
		this.recvTranCode = recvTranCode;
	}

	public String getRentalCompanyId() {
		return rentalCompanyId;
	}

	public void setRentalCompanyId(String rentalCompanyId) {
		this.rentalCompanyId = rentalCompanyId;
	}

	public Date getRevenueDate() {
		return revenueDate;
	}

	public void setRevenueDate(Date revenueDate) {
		this.revenueDate = revenueDate;
	}

	public Integer getReverseAxles() {
		return reverseAxles;
	}

	public void setReverseAxles(Integer reverseAxles) {
		this.reverseAxles = reverseAxles;
	}

	public Long getReviewedClass() {
		return reviewedClass;
	}

	public void setReviewedClass(Long reviewedClass) {
		this.reviewedClass = reviewedClass;
	}

	public Date getReviewedDate() {
		return reviewedDate;
	}

	public void setReviewedDate(Date reviewedDate) {
		this.reviewedDate = reviewedDate;
	}

	public Long getReviewedSegmentId() {
		return reviewedSegmentId;
	}

	public void setReviewedSegmentId(Long reviewedSegmentId) {
		this.reviewedSegmentId = reviewedSegmentId;
	}

	public Long getReviewedVehicleType() {
		return reviewedVehicleType;
	}

	public void setReviewedVehicleType(Long reviewedVehicleType) {
		this.reviewedVehicleType = reviewedVehicleType;
	}

	public String getSpeedViolFlag() {
		return speedViolFlag;
	}

	public void setSpeedViolFlag(String speedViolFlag) {
		this.speedViolFlag = speedViolFlag;
	}

	public Integer getTaxi() {
		return taxi;
	}

	public void setTaxi(Integer taxi) {
		this.taxi = taxi;
	}

	public Double getTaxiLimo() {
		return taxiLimo;
	}

	public void setTaxiLimo(Double taxiLimo) {
		this.taxiLimo = taxiLimo;
	}

	public Long getTollRevenueType() {
		return tollRevenueType;
	}

	public void setTollRevenueType(Long tollRevenueType) {
		this.tollRevenueType = tollRevenueType;
	}

	public String getTollSystemType() {
		return tollSystemType;
	}

	public void setTollSystemType(String tollSystemType) {
		this.tollSystemType = tollSystemType;
	}

	public Long getTourSegmentId() {
		return tourSegmentId;
	}

	public void setTourSegmentId(Long tourSegmentId) {
		this.tourSegmentId = tourSegmentId;
	}

	public Date getTxDate() {
		return txDate;
	}

	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}

	public String getTxExternRefNo() {
		return txExternRefNo;
	}

	public void setTxExternRefNo(String txExternRefNo) {
		this.txExternRefNo = txExternRefNo;
	}

	public Long getTxModSeq() {
		return txModSeq;
	}

	public void setTxModSeq(Long txModSeq) {
		this.txModSeq = txModSeq;
	}

	public Long getTxSeqNumber() {
		return txSeqNumber;
	}

	public void setTxSeqNumber(Long txSeqNumber) {
		this.txSeqNumber = txSeqNumber;
	}

	public String getTxSubtypeInd() {
		return txSubtypeInd;
	}

	public void setTxSubtypeInd(String txSubtypeInd) {
		this.txSubtypeInd = txSubtypeInd;
	}

	public LocalDateTime getTxTimestamp() {
		return txTimestamp;
	}

	public void setTxTimestamp(LocalDateTime txTimestamp) {
		this.txTimestamp = txTimestamp;
	}

	public String getTxTypeInd() {
		return txTypeInd;
	}

	public void setTxTypeInd(String txTypeInd) {
		this.txTypeInd = txTypeInd;
	}

	public Integer getTxnDisputed() {
		return txnDisputed;
	}

	public void setTxnDisputed(Integer txnDisputed) {
		this.txnDisputed = txnDisputed;
	}

	public Float getUnrealizedAmount() {
		return unrealizedAmount;
	}

	public void setUnrealizedAmount(Float unrealizedAmount) {
		this.unrealizedAmount = unrealizedAmount;
	}

	public LocalDateTime getUpdateTs() {
		return updateTs;
	}

	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}

	public Integer getVehicleSpeed() {
		return vehicleSpeed;
	}

	public void setVehicleSpeed(Integer vehicleSpeed) {
		this.vehicleSpeed = vehicleSpeed;
	}

	public Long getViolTxStatus() {
		return violTxStatus;
	}

	public void setViolTxStatus(Long violTxStatus) {
		this.violTxStatus = violTxStatus;
	}

	public Long getViolType() {
		return violType;
	}

	public void setViolType(Long violType) {
		this.violType = violType;
	}

	public Long getEvent() {
		return event;
	}

	public void setEvent(Long event) {
		this.event = event;
	}

	public Float getFrontImgRejReason() {
		return frontImgRejReason;
	}

	public void setFrontImgRejReason(Float frontImgRejReason) {
		this.frontImgRejReason = frontImgRejReason;
	}

	public String getHovFlag() {
		return hovFlag;
	}

	public void setHovFlag(String hovFlag) {
		this.hovFlag = hovFlag;
	}

	public String getInvoicePaid() {
		return invoicePaid;
	}

	public void setInvoicePaid(String invoicePaid) {
		this.invoicePaid = invoicePaid;
	}

	public Long getTrailerPlate() {
		return trailerPlate;
	}

	public void setTrailerPlate(Long trailerPlate) {
		this.trailerPlate = trailerPlate;
	}

	@Override
	public String toString() {
		return "ViolTxDto [accountAgencyId=" + accountAgencyId + ", accountDeviceAgency=" + accountDeviceAgency
				+ ", accountDeviceNo=" + accountDeviceNo + ", accountType=" + accountType + ", actualAxles="
				+ actualAxles + ", actualClass=" + actualClass + ", actualExtraAxles=" + actualExtraAxles
				+ ", addressSource=" + addressSource + ", adjustedAmount=" + adjustedAmount + ", aetFlag=" + aetFlag
				+ ", amountPaid=" + amountPaid + ", atpFileId=" + atpFileId + ", badImgIndexes=" + badImgIndexes
				+ ", basefilename=" + basefilename + ", bufferedReadFlag=" + bufferedReadFlag + ", citationLevel="
				+ citationLevel + ", citationStatus=" + citationStatus + ", classAdjType=" + classAdjType
				+ ", collStatus=" + collStatus + ", collectedAmount=" + collectedAmount + ", collectorAxles="
				+ collectorAxles + ", collectorClass=" + collectorClass + ", collectorId=" + collectorId
				+ ", corrReasonId=" + corrReasonId + ", cscLookupKey=" + cscLookupKey + ", depositId=" + depositId
				+ ", deviceAgencyClass=" + deviceAgencyClass + ", deviceAxles=" + deviceAxles + ", deviceCodedClass="
				+ deviceCodedClass + ", deviceIagClass=" + deviceIagClass + ", deviceNo=" + deviceNo
				+ ", deviceProgramStatus=" + deviceProgramStatus + ", deviceReadCount=" + deviceReadCount
				+ ", deviceWriteCount=" + deviceWriteCount + ", discountedAmount=" + discountedAmount
				+ ", disputedEtcAcctId=" + disputedEtcAcctId + ", dmvMakeId=" + dmvMakeId + ", dmvPlateType="
				+ dmvPlateType + ", dmvRetry=" + dmvRetry + ", dmvReturnDate=" + dmvReturnDate + ", entryDataSource="
				+ entryDataSource + ", entryDeviceReadCount=" + entryDeviceReadCount + ", entryDeviceWriteCount="
				+ entryDeviceWriteCount + ", entryLaneId=" + entryLaneId + ", entryPlazaId=" + entryPlazaId
				+ ", entryTimestamp=" + entryTimestamp + ", entryTxSeqNumber=" + entryTxSeqNumber
				+ ", entryVehicleSpeed=" + entryVehicleSpeed + ", etcAccountId=" + etcAccountId + ", etcTxStatus="
				+ etcTxStatus + ", eventTimestamp=" + eventTimestamp + ", eventType=" + eventType + ", exceptionStatus="
				+ exceptionStatus + ", externFileDate=" + externFileDate + ", externFileId=" + externFileId
				+ ", facilityId=" + facilityId + ", fareTblId=" + fareTblId + ", firstCollmailDatetime="
				+ firstCollmailDatetime + ", firstDmvResponseDate=" + firstDmvResponseDate + ", firstPostedDate="
				+ firstPostedDate + ", forwardAxles=" + forwardAxles + ", fullFareAmount=" + fullFareAmount
				+ ", gmtTxTime=" + gmtTxTime + ", imageBatchId=" + imageBatchId + ", imageBatchSeqNumber="
				+ imageBatchSeqNumber + ", imageIndex=" + imageIndex + ", imagePickupAttemptCount="
				+ imagePickupAttemptCount + ", imagePickupAttemptDate=" + imagePickupAttemptDate + ", imageReceiveDate="
				+ imageReceiveDate + ", imageReviewTimestamp=" + imageReviewTimestamp + ", imageRvwClerkId="
				+ imageRvwClerkId + ", imageTaken=" + imageTaken + ", imdocProcessed=" + imdocProcessed
				+ ", imgFileIndex=" + imgFileIndex + ", internalAuditId=" + internalAuditId + ", isAnonymous="
				+ isAnonymous + ", isClosed=" + isClosed + ", isDiscountable=" + isDiscountable + ", isMedianFare="
				+ isMedianFare + ", isPeak=" + isPeak + ", isReciprocityTxn=" + isReciprocityTxn + ", isReversed="
				+ isReversed + ", laneDeviceStatus=" + laneDeviceStatus + ", laneHealth=" + laneHealth + ", laneId="
				+ laneId + ", laneMode=" + laneMode + ", laneSeqNo=" + laneSeqNo + ", laneState=" + laneState
				+ ", laneTxId=" + laneTxId + ", laneTxStatus=" + laneTxStatus + ", laneTxType=" + laneTxType
				+ ", laneType=" + laneType + ", lastCommDatetime=" + lastCommDatetime + ", lastCommMode=" + lastCommMode
				+ ", limousine=" + limousine + ", loadDate=" + loadDate + ", location=" + location
				+ ", lostStolenDeviceFlag=" + lostStolenDeviceFlag + ", makeId=" + makeId + ", mileage=" + mileage
				+ ", modifiedStatus=" + modifiedStatus + ", newAcct=" + newAcct + ", noticeFeeAmount=" + noticeFeeAmount
				+ ", noticeTollAmount=" + noticeTollAmount + ", origDiscountedAmount=" + origDiscountedAmount
				+ ", origFullFareAmount=" + origFullFareAmount + ", origLaneTxId=" + origLaneTxId + ", outputFileId="
				+ outputFileId + ", outputFileType=" + outputFileType + ", parLaneTxId=" + parLaneTxId + ", planTypeId="
				+ planTypeId + ", plateCountry=" + plateCountry + ", plateNumber=" + plateNumber + ", plateState="
				+ plateState + ", plazaAgencyId=" + plazaAgencyId + ", plazaId=" + plazaId + ", postDeviceStatus="
				+ postDeviceStatus + ", postclassAxles=" + postclassAxles + ", postclassClass=" + postclassClass
				+ ", postedDate=" + postedDate + ", postingType=" + postingType + ", preTxnBalance=" + preTxnBalance
				+ ", preclassAxles=" + preclassAxles + ", preclassClass=" + preclassClass + ", prevEventType="
				+ prevEventType + ", prevViolTxStatus=" + prevViolTxStatus + ", priceScheduleId=" + priceScheduleId
				+ ", processStatus=" + processStatus + ", readAviAxles=" + readAviAxles + ", readAviClass="
				+ readAviClass + ", receiptIssued=" + receiptIssued + ", reconDate=" + reconDate + ", reconPlanId="
				+ reconPlanId + ", reconStatusInd=" + reconStatusInd + ", reconSubCodeInd=" + reconSubCodeInd
				+ ", reconSummaryId=" + reconSummaryId + ", reconTimestamp=" + reconTimestamp + ", reconTranCode="
				+ reconTranCode + ", recvSummaryId=" + recvSummaryId + ", recvTranCode=" + recvTranCode
				+ ", rentalCompanyId=" + rentalCompanyId + ", revenueDate=" + revenueDate + ", reverseAxles="
				+ reverseAxles + ", reviewedClass=" + reviewedClass + ", reviewedDate=" + reviewedDate
				+ ", reviewedSegmentId=" + reviewedSegmentId + ", reviewedVehicleType=" + reviewedVehicleType
				+ ", speedViolFlag=" + speedViolFlag + ", taxi=" + taxi + ", taxiLimo=" + taxiLimo
				+ ", tollRevenueType=" + tollRevenueType + ", tollSystemType=" + tollSystemType + ", tourSegmentId="
				+ tourSegmentId + ", txDate=" + txDate + ", txExternRefNo=" + txExternRefNo + ", txModSeq=" + txModSeq
				+ ", txSeqNumber=" + txSeqNumber + ", txSubtypeInd=" + txSubtypeInd + ", txTimestamp=" + txTimestamp
				+ ", txTypeInd=" + txTypeInd + ", txnDisputed=" + txnDisputed + ", unrealizedAmount=" + unrealizedAmount
				+ ", updateTs=" + updateTs + ", vehicleSpeed=" + vehicleSpeed + ", violTxStatus=" + violTxStatus
				+ ", violType=" + violType + ", event=" + event + ", frontImgRejReason=" + frontImgRejReason
				+ ", hovFlag=" + hovFlag + ", invoicePaid=" + invoicePaid + ", trailerPlate=" + trailerPlate + "]";
	}

    
}
