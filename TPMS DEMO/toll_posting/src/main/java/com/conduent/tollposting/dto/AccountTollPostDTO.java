package com.conduent.tollposting.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.conduent.tollposting.constant.AccountFinStatus;
import com.conduent.tollposting.constant.AccountType;
import com.conduent.tollposting.constant.Constants;
import com.conduent.tollposting.constant.PayType;
import com.conduent.tollposting.constant.TxStatus;
import com.conduent.tollposting.model.Device;
import com.conduent.tollposting.serviceimpl.TollPostingJob;
import com.conduent.tollposting.utility.DateUtils;
import com.conduent.tollposting.utility.LocalDateAdapter;
import com.conduent.tollposting.utility.LocalDateTimeAdapter;
import com.conduent.tollposting.utility.OffsetDateTimeConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.annotations.Expose;

public class AccountTollPostDTO {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountTollPostDTO.class);

	@Expose(serialize = true, deserialize = true)
	private Long laneTxId;

	@Expose(serialize = true, deserialize = true)
	private Integer txStatus;

	@Expose(serialize = true, deserialize = true)
	private String txTypeInd;

	@Expose(serialize = true, deserialize = true)
	private String txSubtypeInd;

	@Expose(serialize = true, deserialize = true)
	private LocalDate txDate;

	@Expose(serialize = true, deserialize = true)
	private OffsetDateTime txTimestamp;

	@Expose(serialize = true, deserialize = true)
	private String txExternRefNo;

	@Expose(serialize = true, deserialize = true)
	private Long externFileId;

	@Expose(serialize = true, deserialize = true)
	private LocalDate externFileDate;

	@Expose(serialize = true, deserialize = true)
	private Integer txModSeq;

	@Expose(serialize = true, deserialize = true)
	private Long txSeqNumber;

	@Expose(serialize = true, deserialize = true)
	private Integer entryLaneId;

	@Expose(serialize = true, deserialize = true)
	private Integer entryPlazaId;

	@Expose(serialize = true, deserialize = true)
	private OffsetDateTime entryTimestamp;

	@Expose(serialize = true, deserialize = true)
	private Integer entryTxSeqNumber;

	@Expose(serialize = true, deserialize = true)
	private Integer entryVehicleSpeed;

	@Expose(serialize = true, deserialize = true)
	private String entryDataSource;

	@Expose(serialize = true, deserialize = true)
	private Integer entryDeviceReadCount;

	@Expose(serialize = true, deserialize = true)
	private Integer entryDeviceWriteCount;

	@Expose(serialize = true, deserialize = true)
	private Integer plazaAgencyId;

	@Expose(serialize = true, deserialize = true)
	private Integer plazaId;

	@Expose(serialize = true, deserialize = true)
	private Integer laneId;

	@Expose(serialize = true, deserialize = true)
	private Integer laneMode;

	@Expose(serialize = true, deserialize = true)
	private String laneType;

	@Expose(serialize = true, deserialize = true)
	private Integer laneState;

	@Expose(serialize = true, deserialize = true)
	private Integer laneHealth;

	@Expose(serialize = true, deserialize = true)
	private Integer laneTxStatus;

	@Expose(serialize = true, deserialize = true)
	private Integer lanetxType;

	@Expose(serialize = true, deserialize = true)
	private String laneDeviceStatus;

	@Expose(serialize = true, deserialize = true)
	private String collectorId;

	@Expose(serialize = true, deserialize = true)
	private Integer collectorClass;

	@Expose(serialize = true, deserialize = true)
	private Integer collectorAxles;

	@Expose(serialize = true, deserialize = true)
	private String tourSegmentId;

	@Expose(serialize = true, deserialize = true)
	private String tollSystemType;

	@Expose(serialize = true, deserialize = true)
	private Integer tollRevenueType;

	@Expose(serialize = true, deserialize = true)
	private Integer actualClass;

	@Expose(serialize = true, deserialize = true)
	private Integer actualAxles;

	@Expose(serialize = true, deserialize = true)
	private Integer actualExtraAxles;

	@Expose(serialize = true, deserialize = true)
	private Integer preclassClass;

	@Expose(serialize = true, deserialize = true)
	private Integer preclassAxles;

	@Expose(serialize = true, deserialize = true)
	private Integer postclassClass;

	@Expose(serialize = true, deserialize = true)
	private Integer postclassAxles;

	@Expose(serialize = true, deserialize = true)
	private Integer forwardAxles;

	@Expose(serialize = true, deserialize = true)
	private Integer reverseAxles;

	@Expose(serialize = true, deserialize = true)
	private Double videoFareAmount;

	@Expose(serialize = true, deserialize = true)
	private Double etcFareAmount;

	@Expose(serialize = true, deserialize = true)
	private Double cashFareAmount;

	@Expose(serialize = true, deserialize = true)
	private Double postedFareAmount;

	@Expose(serialize = true, deserialize = true)
	private Double collectedAmount;

	@Expose(serialize = true, deserialize = true)
	private Double unrealizedAmount;

	@Expose(serialize = true, deserialize = true)
	private Double expectedRevenueAmount;
	
	@Expose(serialize = true, deserialize = true)
	private Double correctionAmount;

	@Expose(serialize = true, deserialize = true)
	private Double preTxnBalance;

	@Expose(serialize = true, deserialize = true)
	private String isDiscountable;

	@Expose(serialize = true, deserialize = true)
	private String isMedianFare;

	@Expose(serialize = true, deserialize = true)
	private String isPeak;

	@Expose(serialize = true, deserialize = true)
	private Integer priceScheduleId;

	@Expose(serialize = true, deserialize = true)
	private Integer vehicleSpeed;

	@Expose(serialize = true, deserialize = true)
	private Integer receiptIssued;

	@Expose(serialize = true, deserialize = true)
	private String deviceNo;

	@Expose(serialize = true, deserialize = true)
	private Integer deviceClass;

	@Expose(serialize = true, deserialize = true)
	private Integer deviceCodedClass;

	@Expose(serialize = true, deserialize = true)
	private Integer deviceAgencyClass;

	@Expose(serialize = true, deserialize = true)
	private Integer deviceIagClass;

	@Expose(serialize = true, deserialize = true)
	private Integer deviceAxles;

	@Expose(serialize = true, deserialize = true)
	private Integer deviceProgramStatus;

	@Expose(serialize = true, deserialize = true)
	private Long etcAccountId;

	@Expose(serialize = true, deserialize = true)
	private Integer accountType;

	@Expose(serialize = true, deserialize = true)
	private Integer accountAgencyId;

	@Expose(serialize = true, deserialize = true)
	private Integer readAviClass;

	@Expose(serialize = true, deserialize = true)
	private Integer readAviAxles;

	@Expose(serialize = true, deserialize = true)
	private String bufferReadFlag;

	@Expose(serialize = true, deserialize = true)
	private Integer postDeviceStatus;

	@Expose(serialize = true, deserialize = true)
	private Integer planTypeId;

	@Expose(serialize = true, deserialize = true)
	private Integer speedViolFlag;

	@Expose(serialize = true, deserialize = true)
	private String imageTaken;

	@Expose(serialize = true, deserialize = true)
	private String plateCountry;

	@Expose(serialize = true, deserialize = true)
	private String plateState;

	@Expose(serialize = true, deserialize = true)
	private String plateNumber;

	@Expose(serialize = true, deserialize = true)
	private LocalDate revenueDate;

	@Expose(serialize = true, deserialize = true)
	private LocalDate postedDate;

	@Expose(serialize = true, deserialize = true)
	private String atpFileId;

	@Expose(serialize = true, deserialize = true)
	private String isReversed;

	@Expose(serialize = true, deserialize = true)
	private Integer corrReasonId;

	@Expose(serialize = true, deserialize = true)
	private LocalDate reconDate;

	@Expose(serialize = true, deserialize = true)
	private Integer reconStatusInd;

	@Expose(serialize = true, deserialize = true)
	private Integer reconSubCodeInd;

	@Expose(serialize = true, deserialize = true)
	private Integer mileage;

	@Expose(serialize = true, deserialize = true)
	private Integer deviceReadCount;

	@Expose(serialize = true, deserialize = true)
	private Integer deviceWriteCount;

	@Expose(serialize = true, deserialize = true)
	private String cscLookupKey;

	@Expose(serialize = true, deserialize = true)
	private LocalDateTime updateTs;

	@Expose(serialize = true, deserialize = true)
	private String aetFlag = "N";

	@Expose(serialize = true, deserialize = true)
	private OffsetDateTime eventTimestamp = OffsetDateTime.now();  //: TODO IBTS 

	@Expose(serialize = true, deserialize = true)
	private Integer eventType = 1;                                      //: TODO IBTS 

	@Expose(serialize = true, deserialize = true)
	private Integer prevViolTxStatus = 0;                             //: TODO IBTS 

	@Expose(serialize = true, deserialize = true)
	private Integer violTxStatus = 3;                                //: TODO IBTS 

	@Expose(serialize = true, deserialize = true)
	private Integer violType = 3;                                         //: TODO IBTS 

	@Expose(serialize = true, deserialize = true)
	private Integer fareTblId;                                         //: TODO IBTS 
	
	@Expose(serialize = true, deserialize = true)
	private String lprEnabled="N";
	
	@Expose(serialize = true, deserialize = true)
	private String postPaidFlag="F";
	
	@Expose(serialize = true, deserialize = true)
	private Integer agencyId;
	
	@Expose(serialize = true, deserialize = true)
	private String todId;
	
	@Expose(serialize = true, deserialize = true)
	private String debitCredit;
	
	private String transSchedPrice;
	
	private String rebillAccountNumber;
	
	private String typeOfTrx;
	
	private Device device;
	
	private AccountInfoDTO accountInfoDTO;
	
	public void setTxStatus(String accountFinStatus,Integer rebillPayType)
	{
		if (txTypeInd.equals("X") && txSubtypeInd.equals("I"))
		{
			this.setTxStatus(TxStatus.TX_STATUS_ITOL.getCode());
		}
		else if (txTypeInd.equals("V"))
		{
			if(txSubtypeInd.equals("T") || txSubtypeInd.equals("Q"))		//added txSubType Q condition for Classification
			{
				this.setTxStatus(TxStatus.TX_STATUS_VTOL.getCode());		
			}
			else if(txSubtypeInd.equals("I"))
			{
				if(rebillPayType!=null && (accountType==AccountType.PRIVATE.getCode() || accountType==AccountType.BUSINESS.getCode()
						|| accountType==AccountType.COMMERCIAL.getCode() || accountType==AccountType.NONREVENUE.getCode()) && rebillPayType.intValue()==PayType.MASTERCARD.getCode())
				{
					this.setTxStatus(TxStatus.TX_STATUS_ITOL.getCode());
				}
				else if((accountFinStatus!=null && rebillPayType!=null) && (accountType==AccountType.PRIVATE.getCode() || accountType==AccountType.BUSINESS.getCode()
						|| accountType==AccountType.COMMERCIAL.getCode() || accountType==AccountType.NONREVENUE.getCode()) && 
						(rebillPayType.intValue()==PayType.CASH.getCode() || rebillPayType.intValue()==PayType.CHECK.getCode()) &&
						(accountFinStatus.equals(AccountFinStatus.GOOD.toString()) || accountFinStatus.equals(AccountFinStatus.LOW.toString())))
				{
					this.setTxStatus(TxStatus.TX_STATUS_ITOL.getCode());
				}
				else if( (accountFinStatus!=null && rebillPayType!=null) && aetFlag.equals("Y") &&  (accountType==AccountType.PRIVATE.getCode() || accountType==AccountType.BUSINESS.getCode()
						|| accountType==AccountType.COMMERCIAL.getCode() || accountType==AccountType.NONREVENUE.getCode()) && 
						(rebillPayType.intValue()==PayType.CASH.getCode() || rebillPayType.intValue()==PayType.CHECK.getCode()) &&
						(accountFinStatus.equals(AccountFinStatus.ZERO.toString())))
				{
					this.setTxStatus(TxStatus.TX_STATUS_VPST.getCode());
				}
				else if(aetFlag.equals("Y") && accountType==AccountType.STVA.getCode())
				{
					this.setTxStatus(TxStatus.TX_STATUS_VPST.getCode());
				}
				else if(aetFlag.equals("Y") && (accountType==AccountType.PVIDEOUNREG.getCode() || accountType==AccountType.BVIDEOUNREG.getCode()))
				{
					this.setTxStatus(TxStatus.TX_STATUS_VPST.getCode());
				}
				else if((accountFinStatus!=null && rebillPayType!=null) && aetFlag.equals("N") &&  (accountType==AccountType.PRIVATE.getCode() || accountType==AccountType.BUSINESS.getCode()
						|| accountType==AccountType.COMMERCIAL.getCode() || accountType==AccountType.NONREVENUE.getCode()) && 
						(rebillPayType.intValue()==PayType.CASH.getCode() || rebillPayType.intValue()==PayType.CHECK.getCode()) &&
						(accountFinStatus.equals(AccountFinStatus.ZERO.toString())))
				{
					this.setTxStatus(TxStatus.TX_STATUS_VIOLTOLL.getCode());
				}
				else if(aetFlag.equals("N") && (accountType==AccountType.PVIOLATOR.getCode() || accountType==AccountType.CVIOLATOR.getCode() 
						|| accountType==AccountType.PVIDEOUNREG.getCode() || accountType==AccountType.BVIDEOUNREG.getCode()))
				{
					this.setTxStatus(TxStatus.TX_STATUS_VIOLTOLL.getCode());
				}
			}
		}
		else if (txTypeInd.equals("U")) //UXX expire -69, match - 67 
		{
			if ("IM".contains(txSubtypeInd))
			{
				this.setTxStatus(TxStatus.TX_STATUS_ITOL.getCode());
			}
			else //UX type
			{
				this.setTxStatus(TxStatus.TX_STATUS_UTOL.getCode());
			}
		}
		else if (txTypeInd.equals("I"))		//previously it was II - POST and IC - PPST but now changed due to US 12315
		{
			if (txSubtypeInd.equals("I"))
			{
				this.setTxStatus(TxStatus.TX_STATUS_PPST.getCode());			
			}
			else
			{
				this.setTxStatus(TxStatus.TX_STATUS_POST.getCode());
			}
		}
		else if (txTypeInd.equals("M"))
		{
			this.setTxStatus(TxStatus.TX_STATUS_MTOL.getCode());           
		}
		else if (txTypeInd.equals("P"))
		{
			this.setTxStatus(TxStatus.TX_STATUS_PSNT.getCode());
		}
		else if (txTypeInd.equals("E"))
		{
			this.setTxStatus(TxStatus.TX_STATUS_ETOL.getCode());
		}
		else if(getTypeOfTrx().equals("EPS_TRX"))
		{
			this.setTxStatus(TxStatus.TX_STATUS_PSNT.getCode());
		}
		else if(getTypeOfTrx().equals("EPS_CC_TRX"))
		{
			this.setTxStatus(TxStatus.TX_STATUS_CCREQ.getCode());
		}
		else if(getRebillAccountNumber()==null)
		{
			this.setTxStatus(TxStatus.TX_STATUS_NOCC.getCode());
		}
		else
		{
			this.setTxStatus(TxStatus.TX_STATUS_ETOL.getCode());
		}
	}
	
	public static void init(EtcTransaction etc,AccountTollPostDTO toll)
	{
		try
		{
			LocalDateTime fromn = LocalDateTime.now();
			BeanUtils.copyProperties(etc, toll);
			logger.info("##init Copy time: {} ms",ChronoUnit.MILLIS.between(fromn, LocalDateTime.now()));
			//toll.setExternFileDate(etc.getExternFileDate());
			//toll.mileage = plaza_lane_st[etc.trans_lane_id].mileage - plaza_lane_st[etc.entry_lane_id].mileage
			toll.setIsDiscountable("T");
			toll.setDeviceNo(etc.getDeviceNo()!=null?etc.getDeviceNo():"");
			toll.setExternFileDate(DateUtils.parseLocalDate(etc.getExternFileDate(), Constants.dateFormator));
			toll.setTxTimestamp(etc.getTxTimestamp());
			toll.setEntryTimestamp(etc.getEntryTimestamp());
			toll.setTxDate(DateUtils.parseLocalDate(etc.getTxDate(), Constants.dateFormator));
			toll.setEtcAccountId(etc.getEtcAccountId()!=null?etc.getEtcAccountId():0);
			toll.setDeviceClass(etc.getTagClass());
			toll.setDeviceAxles(etc.getTagAxles());
			toll.setPostclassClass(etc.getPostClassClass());
			toll.setPostclassAxles(etc.getPostClassAxles());
			toll.setPreclassClass(etc.getPreClassClass());
			toll.setPreclassAxles(etc.getPreClassAxles());
			toll.setPostedDate(LocalDate.now());
			//toll.setReconDate(LocalDate.now());
			toll.setReconDate(etc.getReconDate()==null?LocalDate.of(2058, 11, 9) : etc.getReconDate());
			toll.setPriceScheduleId(0);
			toll.setTxModSeq(1); //etc.getTxModSeq()!=null?etc.getTxModSeq():1
			toll.setExternFileId(etc.getExternFileId());
			//setTxStatus todo
			toll.setLprEnabled(etc.getLprEnabled()!=null?etc.getLprEnabled():"N");
			if( toll.getTxTypeInd().equals("I"))
			{
				toll.setRevenueDate( DateUtils.parseLocalDate(etc.getReceivedDate(),Constants.dateFormator));
			}
			else
			{
				toll.setRevenueDate(DateUtils.parseLocalDate(etc.getRevenueDate(),Constants.dateFormator));
			}
			if(etc.getPlanTypeId()==null )//:TODO || etc.getPlanTypeId().intValue()==888 || etc.getPlanTypeId().intValue()==999
			{
				toll.setPlanTypeId(0);
			}
			if(etc.getBuffer()!=null && (etc.getBuffer().equalsIgnoreCase("9999") || etc.getBuffer().equalsIgnoreCase("VFEE")))
			{
				toll.setPostDeviceStatus(1);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
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

	

	public String getTxTypeInd() {
		return txTypeInd;
	}

	public void setTxTypeInd(String txTypeInd) {
		this.txTypeInd = txTypeInd;
	}

	public String getTxSubtypeInd() {
		return txSubtypeInd;
	}

	public void setTxSubtypeInd(String txSubtypeInd) {
		this.txSubtypeInd = txSubtypeInd;
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

	public String getLaneType() {
		return laneType;
	}

	public void setLaneType(String laneType) {
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

	public String getCollectorId() {
		return collectorId;
	}

	public void setCollectorId(String collectorId) {
		this.collectorId = collectorId;
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

	public Double getVideoFareAmount() {
		return videoFareAmount;
	}

	public void setVideoFareAmount(Double videoFareAmount) {
		this.videoFareAmount = videoFareAmount;
	}

	public Double getEtcFareAmount() {
		return etcFareAmount;
	}

	public void setEtcFareAmount(Double etcFareAmount) {
		this.etcFareAmount = etcFareAmount;
	}

	public Double getCashFareAmount() {
		return cashFareAmount;
	}

	public void setCashFareAmount(Double cashFareAmount) {
		this.cashFareAmount = cashFareAmount;
	}

	public Double getPostedFareAmount() {
		return postedFareAmount;
	}

	public void setPostedFareAmount(Double postedFareAmount) {
		this.postedFareAmount = postedFareAmount;
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

	public Double getExpectedRevenueAmount() {
		return expectedRevenueAmount;
	}

	public void setExpectedRevenueAmount(Double expectedRevenueAmount) {
		this.expectedRevenueAmount = expectedRevenueAmount;
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

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
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

	public Integer getDeviceProgramStatus() {
		return deviceProgramStatus;
	}

	public void setDeviceProgramStatus(Integer deviceProgramStatus) {
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

	public Integer getSpeedViolFlag() {
		return speedViolFlag;
	}

	public void setSpeedViolFlag(Integer speedViolFlag) {
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

	public LocalDate getRevenueDate() {
		return revenueDate;
	}

	public void setRevenueDate(LocalDate revenueDate) {
		this.revenueDate = revenueDate;
	}

	public void setTxDate(LocalDate txDate) {
		this.txDate = txDate;
	}

	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}

	public void setEventTimestamp(OffsetDateTime eventTimestamp) {
		this.eventTimestamp = eventTimestamp;
	}

	public LocalDate getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(LocalDate postedDate) {
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

	public Integer getCorrReasonId() {
		return corrReasonId;
	}

	public void setCorrReasonId(Integer corrReasonId) {
		this.corrReasonId = corrReasonId;
	}

	public LocalDate getReconDate() {
		return reconDate;
	}

	public void setReconDate(LocalDate reconDate) {
		this.reconDate = reconDate;
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

	public Long getExternFileId() {
		return externFileId;
	}

	public void setExternFileId(Long externFileId) {
		this.externFileId = externFileId;
	}

	public LocalDate getExternFileDate() {
		return externFileDate;
	}

	public void setExternFileDate(LocalDate externFileDate) {
		this.externFileDate = externFileDate;
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

	public String getCscLookupKey() {
		return cscLookupKey;
	}

	public void setCscLookupKey(String cscLookupKey) {
		this.cscLookupKey = cscLookupKey;
	}

	public String getAetFlag() {
		return aetFlag;
	}

	public void setAetFlag(String aetFlag) {
		this.aetFlag = aetFlag;
	}


	public Integer getEventType() {
		return eventType;
	}

	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}

	public Integer getPrevViolTxStatus() {
		return prevViolTxStatus;
	}

	public void setPrevViolTxStatus(Integer prevViolTxStatus) {
		this.prevViolTxStatus = prevViolTxStatus;
	}

	public Integer getViolTxStatus() {
		return violTxStatus;
	}

	public void setViolTxStatus(Integer violTxStatus) {
		this.violTxStatus = violTxStatus;
	}

	public Integer getViolType() {
		return violType;
	}

	public void setViolType(Integer violType) {
		this.violType = violType;
	}

	public Integer getFareTblId() {
		return fareTblId;
	}

	public void setFareTblId(Integer fareTblId) {
		this.fareTblId = fareTblId;
	}

	public Integer getDeviceClass() {
		return deviceClass;
	}

	public void setDeviceClass(Integer deviceClass) {
		this.deviceClass = deviceClass;
	}

	public Integer getTxStatus() {
		return txStatus;
	}

	public void setTxStatus(Integer txStatus) {
		this.txStatus = txStatus;
	}

	public LocalDate getTxDate() {
		return txDate;
	}

	public LocalDateTime getUpdateTs() {
		return updateTs;
	}

	public OffsetDateTime getEventTimestamp() {
		return eventTimestamp;
	}

	public String getLprEnabled() {
		return lprEnabled;
	}

	public void setLprEnabled(String lprEnabled) {
		this.lprEnabled = lprEnabled;
	}

	public String getPostPaidFlag() {
		return postPaidFlag;
	}

	public void setPostPaidFlag(String postPaidFlag) {
		this.postPaidFlag = postPaidFlag;
	}

	public String getTransSchedPrice() {
		return transSchedPrice;
	}

	public void setTransSchedPrice(String transSchedPrice) {
		this.transSchedPrice = transSchedPrice;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public AccountInfoDTO getAccountInfoDTO() {
		return accountInfoDTO;
	}

	public void setAccountInfoDTO(AccountInfoDTO accountInfoDTO) {
		this.accountInfoDTO = accountInfoDTO;
	}

	public String getRebillAccountNumber() {
		return rebillAccountNumber;
	}

	public void setRebillAccountNumber(String rebillAccountNumber) {
		this.rebillAccountNumber = rebillAccountNumber;
	}

	public String getTypeOfTrx() {
		return typeOfTrx;
	}

	public void setTypeOfTrx(String typeOfTrx) {
		this.typeOfTrx = typeOfTrx;
	}

	public OffsetDateTime getTxTimestamp() {
		return txTimestamp;
	}

	public void setTxTimestamp(OffsetDateTime txTimestamp) {
		this.txTimestamp = txTimestamp;
	}

	public OffsetDateTime getEntryTimestamp() {
		return entryTimestamp;
	}

	public void setEntryTimestamp(OffsetDateTime entryTimestamp) {
		this.entryTimestamp = entryTimestamp;
	}

	public Double getCorrectionAmount() {
		return correctionAmount;
	}

	public void setCorrectionAmount(Double correctionAmount) {
		this.correctionAmount = correctionAmount;
	}

	public Integer getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}

	public String getTodId() {
		return todId;
	}

	public void setTodId(String todId) {
		this.todId = todId;
	}

	
	public String getDebitCredit() {
		return debitCredit;
	}

	public void setDebitCredit(String debitCredit) {
		this.debitCredit = debitCredit;
	}

	@Override
	public String toString() {
		return "AccountTollPostDTO [laneTxId=" + laneTxId + ", txStatus=" + txStatus + ", txTypeInd=" + txTypeInd
				+ ", txSubtypeInd=" + txSubtypeInd + ", txDate=" + txDate + ", txTimestamp=" + txTimestamp
				+ ", txExternRefNo=" + txExternRefNo + ", externFileId=" + externFileId + ", externFileDate="
				+ externFileDate + ", txModSeq=" + txModSeq + ", txSeqNumber=" + txSeqNumber + ", entryLaneId="
				+ entryLaneId + ", entryPlazaId=" + entryPlazaId + ", entryTimestamp=" + entryTimestamp
				+ ", entryTxSeqNumber=" + entryTxSeqNumber + ", entryVehicleSpeed=" + entryVehicleSpeed
				+ ", entryDataSource=" + entryDataSource + ", entryDeviceReadCount=" + entryDeviceReadCount
				+ ", entryDeviceWriteCount=" + entryDeviceWriteCount + ", plazaAgencyId=" + plazaAgencyId + ", plazaId="
				+ plazaId + ", laneId=" + laneId + ", laneMode=" + laneMode + ", laneType=" + laneType + ", laneState="
				+ laneState + ", laneHealth=" + laneHealth + ", laneTxStatus=" + laneTxStatus + ", lanetxType="
				+ lanetxType + ", laneDeviceStatus=" + laneDeviceStatus + ", collectorId=" + collectorId
				+ ", collectorClass=" + collectorClass + ", collectorAxles=" + collectorAxles + ", tourSegmentId="
				+ tourSegmentId + ", tollSystemType=" + tollSystemType + ", tollRevenueType=" + tollRevenueType
				+ ", actualClass=" + actualClass + ", actualAxles=" + actualAxles + ", actualExtraAxles="
				+ actualExtraAxles + ", preclassClass=" + preclassClass + ", preclassAxles=" + preclassAxles
				+ ", postclassClass=" + postclassClass + ", postclassAxles=" + postclassAxles + ", forwardAxles="
				+ forwardAxles + ", reverseAxles=" + reverseAxles + ", videoFareAmount=" + videoFareAmount
				+ ", etcFareAmount=" + etcFareAmount + ", cashFareAmount=" + cashFareAmount + ", postedFareAmount="
				+ postedFareAmount + ", collectedAmount=" + collectedAmount + ", unrealizedAmount=" + unrealizedAmount
				+ ", expectedRevenueAmount=" + expectedRevenueAmount + ", correctionAmount=" + correctionAmount
				+ ", preTxnBalance=" + preTxnBalance + ", isDiscountable=" + isDiscountable + ", isMedianFare="
				+ isMedianFare + ", isPeak=" + isPeak + ", priceScheduleId=" + priceScheduleId + ", vehicleSpeed="
				+ vehicleSpeed + ", receiptIssued=" + receiptIssued + ", deviceNo=" + deviceNo + ", deviceClass="
				+ deviceClass + ", deviceCodedClass=" + deviceCodedClass + ", deviceAgencyClass=" + deviceAgencyClass
				+ ", deviceIagClass=" + deviceIagClass + ", deviceAxles=" + deviceAxles + ", deviceProgramStatus="
				+ deviceProgramStatus + ", etcAccountId=" + etcAccountId + ", accountType=" + accountType
				+ ", accountAgencyId=" + accountAgencyId + ", readAviClass=" + readAviClass + ", readAviAxles="
				+ readAviAxles + ", bufferReadFlag=" + bufferReadFlag + ", postDeviceStatus=" + postDeviceStatus
				+ ", planTypeId=" + planTypeId + ", speedViolFlag=" + speedViolFlag + ", imageTaken=" + imageTaken
				+ ", plateCountry=" + plateCountry + ", plateState=" + plateState + ", plateNumber=" + plateNumber
				+ ", revenueDate=" + revenueDate + ", postedDate=" + postedDate + ", atpFileId=" + atpFileId
				+ ", isReversed=" + isReversed + ", corrReasonId=" + corrReasonId + ", reconDate=" + reconDate
				+ ", reconStatusInd=" + reconStatusInd + ", reconSubCodeInd=" + reconSubCodeInd + ", mileage=" + mileage
				+ ", deviceReadCount=" + deviceReadCount + ", deviceWriteCount=" + deviceWriteCount + ", cscLookupKey="
				+ cscLookupKey + ", updateTs=" + updateTs + ", aetFlag=" + aetFlag + ", eventTimestamp="
				+ eventTimestamp + ", eventType=" + eventType + ", prevViolTxStatus=" + prevViolTxStatus
				+ ", violTxStatus=" + violTxStatus + ", violType=" + violType + ", fareTblId=" + fareTblId
				+ ", lprEnabled=" + lprEnabled + ", postPaidFlag=" + postPaidFlag + ", agencyId=" + agencyId
				+ ", todId=" + todId + ", debitCredit=" + debitCredit + ", transSchedPrice=" + transSchedPrice
				+ ", rebillAccountNumber=" + rebillAccountNumber + ", typeOfTrx=" + typeOfTrx + ", device=" + device
				+ ", accountInfoDTO=" + accountInfoDTO + "]";
	}
	
	public static AccountTollPostDTO dtoFromJson(String msg)
	{
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeConverter(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.excludeFieldsWithoutExposeAnnotation()
				.registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) ->
			    LocalDate.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern(Constants.dateFormator)))
				.registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) ->
				LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern(Constants.dateTimeFormator)))
				.create();
		return gson.fromJson(msg, AccountTollPostDTO.class);
	}	

}
