package com.conduent.tpms.qatp.classification.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.classification.constants.QatpClassificationConstant;
import com.conduent.tpms.qatp.classification.dao.AgencyTxPendingDao;
import com.conduent.tpms.qatp.classification.dao.ComputeTollDao;
import com.conduent.tpms.qatp.classification.dto.CommonClassificationDto;
import com.conduent.tpms.qatp.classification.dto.NY12Dto;
import com.conduent.tpms.qatp.classification.dto.TUnmatchedTx;
import com.conduent.tpms.qatp.classification.dto.TransactionDto;
import com.conduent.tpms.qatp.classification.service.CommonClassificationService;
import com.conduent.tpms.qatp.classification.service.CommonNotificationService;
import com.conduent.tpms.qatp.classification.utility.AsyncProcessCall;
import com.conduent.tpms.qatp.classification.utility.DateUtils;
import com.conduent.tpms.qatp.classification.utility.MasterDataCache;
import com.conduent.tpms.qatp.classification.utility.TxStatusHelper;
import com.google.common.base.Stopwatch;

/**
 * Common Notification Service Implementation
 * 
 * @author Sameer
 *
 */
@Service
public class CommonNotificationServiceImpl implements CommonNotificationService {

	private static final Logger logger = LoggerFactory.getLogger(CommonNotificationServiceImpl.class);

	/*
	 * @Autowired ConfigVariable configVariable;
	 */

	@Autowired
	private CommonClassificationService commonClassificationService;

	@Autowired
	private ComputeTollDao computeTollDao;

	@Autowired
	private TxStatusHelper txStatusHelper;

	@Autowired
	private AgencyTxPendingDao agencyTxPendingDao;
	
	@Autowired
	private AsyncProcessCall asyncProcessCall;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	boolean flag=false;


	private void getAndUpdateQtpStatitics(TransactionDto transactionDto) {
		Stopwatch stopwatch = Stopwatch.createStarted();
		computeTollDao.getQtpStatitics(transactionDto);
		computeTollDao.updateQtpStatitics(transactionDto);
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Time taken to Select & UPDATE_T_QATP_STATISTICS Table ==> {} ms",millis);
	}
	private void pushInStreamAndUpdateDB(TransactionDto txDTO, CommonClassificationDto commonDTO, String streamId) 
	{
		Stopwatch stopwatch = Stopwatch.createStarted();	
		computeTollDao.updateTTransDetail(txDTO);
		commonDTO.setEventTimestamp(timeZoneConv.currentTime());
		logger.info("Event Timestamp set as {}",commonDTO.getEventTimestamp());
		logger.info("Message that will be pushed to IBTS is : {}",commonDTO.toString());
		asyncProcessCall.pushMessage(commonDTO, streamId);
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Time Taken for Publish Message & update in T_TRAN_DETAIL Table ==> {} ms",millis);

	}

	/**
	 * Get Classification Dto
	 * 
	 * @param TransactionDto
	 * @return CommonClassificationDto
	 */
	private CommonClassificationDto getClassificationDTO(TransactionDto txDTO) {
		CommonClassificationDto commonDTO = new CommonClassificationDto();

		commonDTO.setLaneTxId(txDTO.getLaneTxId());
		commonDTO.setTxExternRefNo(txDTO.getTxExternRefNo());
		commonDTO.setTxSeqNumber(txDTO.getLaneSn());
		commonDTO.setExternFileId(txDTO.getExtFileId());
		commonDTO.setPlazaAgencyId(txDTO.getPlazaAgencyId());
		commonDTO.setPlazaId(txDTO.getPlazaId());
		commonDTO.setLaneId(txDTO.getLaneId());
		commonDTO.setTxTimestamp(txDTO.getTxTimeStamp());
		commonDTO.setTxModSeq(0);
		commonDTO.setTxTypeInd(txDTO.getTxType());
		commonDTO.setTxSubtypeInd(txDTO.getTxSubType());
		commonDTO.setTollSystemType(txDTO.getTollSystemType());
		commonDTO.setLaneMode(txDTO.getLaneMode());
		// commonDTO.setLaneType(txDTO.getLan);
		commonDTO.setLaneState(txDTO.getLaneState());
		commonDTO.setLaneHealth(0);
		commonDTO.setCollectorId(txDTO.getCollectorNumber());
		// commonDTO.setTourSegmentId(txDTO.getTollSystemType());
		commonDTO.setEntryDataSource(txDTO.getEntryDataSource());
		commonDTO.setEntryLaneId(txDTO.getEntryLaneId());
		commonDTO.setEntryPlazaId(txDTO.getEntryPlazaId());
		// commonDTO.setEntryTimeStamp("");
		commonDTO.setEntryTimestamp(txDTO.getEntryTxTimeStamp());
		commonDTO.setEntryTxSeqNumber(txDTO.getEntryTxSeqNumber());
		commonDTO.setEventTimestamp(txDTO.getEventTimestamp());
		commonDTO.setEntryVehicleSpeed(txDTO.getVehicleSpeed());
		commonDTO.setLaneTxStatus(0);
		commonDTO.setLanetxType(0);
		commonDTO.setTollRevenueType(txDTO.getTollRevenueType());
		commonDTO.setActualClass(txDTO.getActualClass());
		commonDTO.setActualAxles(txDTO.getActualAxles());
		commonDTO.setActualExtraAxles(txDTO.getExtraAxles());
		commonDTO.setCollectorClass(0);
		commonDTO.setCollectorAxles(0);
		commonDTO.setPreClassAxles(0);
		commonDTO.setPreClassClass(0);
		commonDTO.setPostClassAxles(txDTO.getActualAxles());
		commonDTO.setPostClassClass(txDTO.getActualClass());
		commonDTO.setForwardAxles(0);
		commonDTO.setReverseAxles(0);

		commonDTO.setEtcFareAmount(txDTO.getEtcFareAmount());
		commonDTO.setPostedFareAmount(txDTO.getPostedFareAmount());
		commonDTO.setExpectedRevenueAmount(txDTO.getExpectedRevenueAmount());
		commonDTO.setVideoFareAmount(txDTO.getVideoFareAmount());
		commonDTO.setCashFareAmount(txDTO.getCashFareAmount());

		commonDTO.setUnrealizedAmount(0.0);
		commonDTO.setCollectedAmount(0.0);
		commonDTO.setIsDiscountable("N");
		commonDTO.setIsMedianFare("N");
		commonDTO.setIsPeak("N");
		commonDTO.setPriceScheduleId(0);
		commonDTO.setVehicleSpeed(txDTO.getVehicleSpeed());
		commonDTO.setReceiptIssued(0);
		commonDTO.setDeviceNo(txDTO.getDeviceNo());
		commonDTO.setAccountType(txDTO.getAccountType());
		// commonDTO.setDeviceCodedClass(txDTO.get);
		commonDTO.setDeviceAgencyClass(txDTO.getAvcClass());
		if(txDTO.getTagIagClass()!=null) {
		commonDTO.setDeviceIagClass((int)(long)txDTO.getTagIagClass());
		}
		commonDTO.setDeviceAxles(txDTO.getTagAxles());
		commonDTO.setEtcAccountId(txDTO.getEtcAccountId());
		commonDTO.setAccountAgencyId(txDTO.getAccAgencyId());
		commonDTO.setReadAviAxles(txDTO.getTagAxles());
		// commonDTO.setReadAviClass(txDTO.getTa);
		commonDTO.setDeviceProgramStatus(txDTO.getProgStat());
		commonDTO.setBufferReadFlag(txDTO.getBufRead());
		// commonDTO.setLaneDeviceStatus(txDTO.getVa);
		commonDTO.setPostDeviceStatus(0);
		commonDTO.setPreTxnBalance(0.0);
		//removing default value '0' and adding the actual value.
		commonDTO.setPlanTypeId(txDTO.getPlanType());
		commonDTO.setEtcViolObserved(txDTO.getEtcViolObserved());
//		commonDTO.setEtcTxStatus(etcTxStatusHelper.getEtcTxStatus(txDTO.getTxType(), txDTO.getTxSubType(),
//				txDTO.getEtcAccountId(), txDTO.getLaneTxId()));
		//sakshi
		commonDTO.setTxStatus(txStatusHelper.getTxStatus(txDTO.getTxType(), txDTO.getTxSubType(),
				txDTO.getEtcAccountId(), txDTO.getLaneTxId()));
		if(txDTO.getAgTrxType()!=null)
		{
		if(txDTO.getAgTrxType().equals("E") && txDTO.getTxType().equals("R") && txDTO.getTxSubType().equals("G")) {
			commonDTO.setTxStatus(34);
		}
		}
		if(txDTO.getEtcViolObserved()!=null && txDTO.getEtcViolObserved()==0 && txDTO.getTxStatus()!=null)
		{
		if(txDTO.getEtcViolObserved()==0 && txDTO.getPlazaAgencyId()==1 && 
		txDTO.getTxType().equals("E") && txDTO.getTxSubType().equals("Z") &&
		txDTO.getImageCaptured().equals("F") && txDTO.getTxStatus().equals("34") ){
			commonDTO.setTxStatus(34);
		}
		}
		commonDTO.setAetFlag(txDTO.getAetFlag());
		// commonDTO.setEtcTxStatus(null);
		commonDTO.setSpeedViolFlag(txDTO.getSpeedViolation());
		commonDTO.setImageTaken(txDTO.getImageCaptured());
		commonDTO.setPlateCountry("***");
		commonDTO.setPlateState(txDTO.getPlateState());
		commonDTO.setPlateNumber(txDTO.getPlateNumber());
		commonDTO.setRevenueDate(txDTO.getReceivedDate());
		commonDTO.setPostedDate(null);
		commonDTO.setAtpFileId(txDTO.getAtpFileId());
		commonDTO.setIsReversed("N");
		commonDTO.setCorrReasonId(0);
		commonDTO.setReconDate(null);
		commonDTO.setReconStatusInd(0);
		commonDTO.setReconSubCodeInd(0);
		commonDTO.setExternFileDate(null);
		commonDTO.setMileage(0);
		commonDTO.setDeviceReadCount(txDTO.getReadPerf());
		commonDTO.setDeviceWriteCount(txDTO.getWritePerf());
		commonDTO.setEntryDeviceReadCount(txDTO.getReadPerf());
		commonDTO.setEntryDeviceWriteCount(txDTO.getWritePerf());
		commonDTO.setTxDate(txDTO.getTxDate());
		commonDTO.setCscLookupKey(null);
		commonDTO.setUpdateTs(LocalDateTime.now().toString());
		commonDTO.setReciprocityTrx(txDTO.getReciprocityTrx());
		commonDTO.setDiscountedAmount(txDTO.getDiscountedAmount());
		commonDTO.setDiscountedAmount2(txDTO.getDiscountedAmount2());
		return commonDTO;
	}
	
	/**
	 * code written for unmatched entry & exit transaction
	 * 
	 * @param txDTO
	 * @param commonDTO
	 * @return
	 */
	private TUnmatchedTx getUnmatchedTransaction(TransactionDto txDTO, CommonClassificationDto commonDTO) {
		TUnmatchedTx unMatchedTrx = new TUnmatchedTx();
		unMatchedTrx.setAccountAgencyId(txDTO.getAccAgencyId());
		unMatchedTrx.setAccountType(txDTO.getAccountType());
		unMatchedTrx.setActualClass(txDTO.getActualClass());
		unMatchedTrx.setActualAxles(txDTO.getActualAxles());
		unMatchedTrx.setActualExtraAxles(txDTO.getExtraAxles());
		unMatchedTrx.setAtpFileId(txDTO.getAtpFileId());
		unMatchedTrx.setBufferedRreadFlag(txDTO.getBufRead());
		unMatchedTrx.setCollectedAmount(commonDTO.getCollectedAmount());
		unMatchedTrx.setCollectorAxles(commonDTO.getCollectorAxles());
		unMatchedTrx.setCollectorClass(commonDTO.getCollectorClass());
		unMatchedTrx.setCollectorId(txDTO.getCollectorNumber());
		unMatchedTrx.setCorrReasonId(commonDTO.getCorrReasonId());
		unMatchedTrx.setDepositId(null);
		unMatchedTrx.setDeviceAgencyClass(txDTO.getAvcClass());
		unMatchedTrx.setDeviceAxles(txDTO.getTagAxles());
		unMatchedTrx.setDeviceCodedClass(txDTO.getDeviceCodedClass());
		if(txDTO.getTagIagClass()!=null) {
		unMatchedTrx.setDeviceIagClass((int)(long)txDTO.getTagIagClass());
		}
		unMatchedTrx.setDeviceNo(txDTO.getDeviceNo());
		unMatchedTrx.setDeviceProgramStatus(txDTO.getProgStat());
		unMatchedTrx.setDeviceReadCount(txDTO.getReadPerf());
		unMatchedTrx.setDeviceWriteCount(txDTO.getWritePerf());
		unMatchedTrx.setEntryDataSource(txDTO.getEntryDataSource());
		unMatchedTrx.setEntryDeviceReadCount(txDTO.getReadPerf());
		unMatchedTrx.setEntryDeviceWriteCount(txDTO.getWritePerf());
		unMatchedTrx.setEntryLaneId(txDTO.getEntryLaneId());
		unMatchedTrx.setEntryPlazaId(txDTO.getEntryPlazaId());
		unMatchedTrx.setEntryTimestamp(txDTO.getEntryTxTimeStamp());
		unMatchedTrx.setEntryTxSeqNumber(txDTO.getEntryTxSeqNumber());
		unMatchedTrx.setEntryVehicleSpeed(txDTO.getVehicleSpeed());
		unMatchedTrx.setEtcAccountId(txDTO.getEtcAccountId());
		unMatchedTrx.setExternFileDate(commonDTO.getExternFileDate());
		unMatchedTrx.setExternFileId(txDTO.getExtFileId());
		unMatchedTrx.setForwardAxles(commonDTO.getForwardAxles());
		unMatchedTrx.setImageTaken(txDTO.getImageCaptured());
		unMatchedTrx.setIsDiscountable(commonDTO.getIsDiscountable());
		unMatchedTrx.setIsMedianFare(commonDTO.getIsMedianFare());
		unMatchedTrx.setIsPeak(commonDTO.getIsPeak());
		unMatchedTrx.setIsReversed(commonDTO.getIsReversed());
		unMatchedTrx.setLaneDeviceStatus(null);
		unMatchedTrx.setLaneHealth(commonDTO.getLaneHealth());
		unMatchedTrx.setLaneId(txDTO.getLaneId());
		unMatchedTrx.setLaneMode(txDTO.getLaneMode());
		unMatchedTrx.setLaneState(txDTO.getLaneState());
		unMatchedTrx.setLaneTxId(txDTO.getLaneTxId());
		unMatchedTrx.setLaneTxStatus(commonDTO.getLaneTxStatus());
		unMatchedTrx.setLaneTxType(commonDTO.getLanetxType());
		unMatchedTrx.setLaneType(null);
		unMatchedTrx.setMileage(commonDTO.getMileage());
		unMatchedTrx.setPlanTypeId(txDTO.getPlanType());
		unMatchedTrx.setPlateCountry(commonDTO.getPlateCountry());
		unMatchedTrx.setPlateNumber(txDTO.getPlateNumber());
		unMatchedTrx.setPlateState(txDTO.getPlateState());
		unMatchedTrx.setPlazaAgencyId(txDTO.getPlazaAgencyId());
		unMatchedTrx.setPlazaId(txDTO.getPlazaId());
		unMatchedTrx.setPostclassAxles(txDTO.getActualAxles());
		unMatchedTrx.setPostclassClass(txDTO.getActualClass());
		unMatchedTrx.setPostedDate(commonDTO.getPostedDate());
		unMatchedTrx.setPostDeviceStatus(commonDTO.getPostDeviceStatus());
		unMatchedTrx.setPreclassAxles(commonDTO.getPreClassAxles());
		unMatchedTrx.setPreclassClass(commonDTO.getPreClassClass());
		unMatchedTrx.setPreTxnBalance(commonDTO.getPreTxnBalance());
		unMatchedTrx.setPriceScheduleId(commonDTO.getPriceScheduleId());
		unMatchedTrx.setReadAviAxles(txDTO.getTagAxles());
		unMatchedTrx.setReadAviClass(null);
		unMatchedTrx.setReceiptIssued(commonDTO.getReceiptIssued());
		unMatchedTrx.setReconDate(commonDTO.getReconDate());
		unMatchedTrx.setReconStatusInd(commonDTO.getReconStatusInd());
		unMatchedTrx.setReconSubCodeInd(commonDTO.getReconSubCodeInd());
		LocalDate revenueDate = LocalDate.parse(txDTO.getReceivedDate());
		unMatchedTrx.setRevenueDate(revenueDate);
		unMatchedTrx.setReverseAxles(commonDTO.getReverseAxles());
		unMatchedTrx.setSpeedViolFlag(txDTO.getSpeedViolation());
		unMatchedTrx.setTollRevenueType(txDTO.getTollRevenueType());
		unMatchedTrx.setTollSystemType(txDTO.getTollSystemType());
		unMatchedTrx.setTourSegmentId(txDTO.getTourSegmentId());
		LocalDate txDate = LocalDate.parse(txDTO.getTxDate());
		unMatchedTrx.setTxDate(txDate);
		unMatchedTrx.setTxExternRefNo(txDTO.getTxExternRefNo());
		unMatchedTrx.setTxModSeq(commonDTO.getTxModSeq());
		unMatchedTrx.setTxSeqNumber(txDTO.getLaneSn());
		unMatchedTrx.setTxStatus(commonDTO.getTxStatus());
		unMatchedTrx.setTxSubtypeInd(txDTO.getTxSubType());
		unMatchedTrx.setTxTimeStamp(txDTO.getTxTimeStamp());
		//unMatchedTrx.setTxTypeInd(QatpClassificationConstant.U);txDTO.getTxType()
		unMatchedTrx.setTxTypeInd(txDTO.getTxType());
		unMatchedTrx.setUnrealizedAmount(commonDTO.getUnrealizedAmount());
		LocalDateTime updateTs = LocalDateTime.parse(commonDTO.getUpdateTs());
		unMatchedTrx.setUpdateTs(updateTs);
		unMatchedTrx.setVehicleSpeed(txDTO.getVehicleSpeed());
		unMatchedTrx.setExpectedRevenueAmount(txDTO.getExpectedRevenueAmount());;
		unMatchedTrx.setCashFareAmount(txDTO.getCashFareAmount());
		unMatchedTrx.setVideoFareAmount(txDTO.getVideoFareAmount());
		unMatchedTrx.setEtcFareAmount(txDTO.getEtcFareAmount());
		unMatchedTrx.setPostedFareAmount(txDTO.getPostedFareAmount());
		
		return unMatchedTrx;
		
	}
	
	
	private NY12Dto getNY12Details(TransactionDto tempTxDto) {

		NY12Dto ny12 = new NY12Dto();

		ny12.setLaneTxId(tempTxDto.getLaneTxId());
		ny12.setTxExternRefNo(tempTxDto.getTxExternRefNo());
		ny12.setTxSeqNumber(tempTxDto.getEntryTxSeqNumber());
		ny12.setExternFileId(tempTxDto.getExtFileId());
		ny12.setLaneId(tempTxDto.getLaneId());
		ny12.setTxTimestamp(tempTxDto.getTxTimeStamp().toString());
		ny12.setTxTypeInd(tempTxDto.getTxType());
		ny12.setTxSubTypeInd(tempTxDto.getTxSubType());
		ny12.setTollSystemType(tempTxDto.getTollSystemType());
		ny12.setLaneMode(tempTxDto.getLaneMode());
		ny12.setLaneType(tempTxDto.getLaneTxType());
		ny12.setLaneState(tempTxDto.getLaneState());
		ny12.setLaneHealth(tempTxDto.getLaneHealth());
		ny12.setPlazaAgencyId(tempTxDto.getPlazaAgencyId());
		ny12.setPlazaId(tempTxDto.getPlazaId());
		ny12.setCollectorId(tempTxDto.getCollectorNumber());
		ny12.setTourSegmentId(tempTxDto.getTourSegment());
		ny12.setEntryDataSource(tempTxDto.getEntryDataSource());
		ny12.setEntryLaneId(tempTxDto.getEntryLaneId());
		ny12.setEntryPlazaId(tempTxDto.getEntryPlazaId() !=null ? tempTxDto.getEntryPlazaId() :0);
		ny12.setEntryTimeStamp(tempTxDto.getEntryTxTimeStamp() !=null ? tempTxDto.getEntryTxTimeStamp().toString(): null);
		ny12.setEntryTxSeqNumber(tempTxDto.getEntryTxSeqNumber());
		ny12.setEntryVehicleSpeed(tempTxDto.getEntryVehicleSpeed());
		ny12.setLaneTxStatus(tempTxDto.getLaneTxStatus());
		ny12.setLaneTxType(tempTxDto.getLaneTxType());
		ny12.setTollRevenueType(tempTxDto.getTollRevenueType());
		ny12.setActualClass(tempTxDto.getActualClass());
		ny12.setActualAxles(tempTxDto.getActualAxles());
		ny12.setActualExtraAxles(tempTxDto.getExtraAxles());
		ny12.setCollectorClass(tempTxDto.getCollectorClass());
		ny12.setCollectorAxles(tempTxDto.getCollectorAxles());
		ny12.setPreclassClass(tempTxDto.getPreClass());
		ny12.setPreclassAxles(tempTxDto.getPreClassAxles());
		ny12.setPostclassClass(tempTxDto.getPostClass());
		ny12.setPostclassAxles(tempTxDto.getPostClassAxles());
		ny12.setForwardAxles(tempTxDto.getForwordAxles());
		ny12.setReverseAxles(tempTxDto.getReverseAxles());
		ny12.setDiscountedAmount(tempTxDto.getDiscountedAmount()!=null ? tempTxDto.getDiscountedAmount().longValue() : 0);
		ny12.setCollectedAmount(tempTxDto.getCollectedAmount() !=null ? tempTxDto.getCollectedAmount().longValue() : 0);
		ny12.setUnRealizedAmount(tempTxDto.getUnrealizedAmount() !=null ? tempTxDto.getUnrealizedAmount().longValue() : 0);
		ny12.setVehicleSpeed(tempTxDto.getVehicleSpeed());
		ny12.setDeviceNo(tempTxDto.getDeviceNo());
		ny12.setAccountType(tempTxDto.getAccountType());
		ny12.setDeviceIagClass(tempTxDto.getDeviceIagClass());
		ny12.setDeviceAxles(tempTxDto.getDeviceAxles());
		ny12.setEtcAccountId(tempTxDto.getEtcAccountId());
		ny12.setAccountAgencyId(tempTxDto.getAccAgencyId());
		ny12.setReadAviClass(tempTxDto.getReadAVIClass());
		ny12.setReadAviAxles(tempTxDto.getReadAVIAxles());
		ny12.setDeviceProgramStatus(tempTxDto.getDeviceProgramStatus());
		ny12.setBufferedReadFlag(tempTxDto.getBufRead());
		ny12.setLaneDeviceStatus(tempTxDto.getLaneDeviceStatus());
		ny12.setPostDeviceStatus(tempTxDto.getPostDeviceStatus());
		ny12.setPreTxnBalance(tempTxDto.getPreTrxbalance());
		ny12.setTxStatus(tempTxDto.getTxStatus());
		ny12.setSpeedViolFlag(tempTxDto.getSpeedViolation());
		ny12.setImagetaken(tempTxDto.getImageCaptured());
		ny12.setPlateCountry(tempTxDto.getPlateCountry());
		ny12.setPlateState(tempTxDto.getPlateState());
		ny12.setPlateNumber(tempTxDto.getPlateNumber());
		ny12.setRevenueDate(tempTxDto.getRevenueDate() !=null ? tempTxDto.getRevenueDate() :null );
		ny12.setAtpFileId(tempTxDto.getAtpFileId());
		ny12.setMatchedTxExternRefNo(tempTxDto.getMatchedTxExternRefNo());
		ny12.setTxCompleteRefNo(tempTxDto.getTxCompleteRefNo());
		ny12.setTxMatchStatus(tempTxDto.getMatchedTxStatus());
		ny12.setExternFileDate(tempTxDto.getExternFileDate() !=null ? tempTxDto.getExternFileDate() :null );
		ny12.setTxDate(tempTxDto.getTxDate() !=null ? tempTxDto.getTxDate() :null );
		ny12.setEvent(tempTxDto.getEvent());
		ny12.setHovFlag(tempTxDto.getHovFlag());
		ny12.setIsReciprocityTxn(tempTxDto.getIsReciprocityTrx());
		ny12.setCscLookUpKey(tempTxDto.getCscLookupKey());
		ny12.setCashFareAmount(tempTxDto.getCashFareAmount());
		ny12.setDiscountedAmount2(tempTxDto.getDiscountedAmount2() !=null ? tempTxDto.getDiscountedAmount2() : 0);
		ny12.setEtcFareAmount(tempTxDto.getEtcFareAmount() !=null ? tempTxDto.getEtcFareAmount() :0);
		ny12.setExpectedRevenueAmount(tempTxDto.getExpectedRevenueAmount() !=null ? tempTxDto.getExpectedRevenueAmount() : 0);
		ny12.setPostedFareAmount(tempTxDto.getPostedFareAmount() !=null ? tempTxDto.getPostedFareAmount() : 0);
		ny12.setVideoFareAmount(tempTxDto.getVideoFareAmount() !=null ? tempTxDto.getVideoFareAmount() : 0);

		return ny12;
	}


	@Override
	public void pushMessagetoParkingQueue(TransactionDto transactionDto,MasterDataCache masterDataCache) 
	{
		getAndUpdateQtpStatitics(transactionDto);
		
		if(transactionDto.getTxType().equals(QatpClassificationConstant.P) && 
				transactionDto.getTxSubType().equals(QatpClassificationConstant.T))
		{
			logger.info("If TX_TYPE = P & SUB_TYPE = T");
			Stopwatch stopwatch = Stopwatch.createStarted();	
			computeTollDao.updateTTransDetail(transactionDto);
			stopwatch.stop();
			long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
			logger.info("Time taken to updateTTransDetail Table ==> {} ms",millis);
			
			//insert in T_EPS_INTX_LOG
			computeTollDao.insertToTEPSINTXLog(transactionDto,masterDataCache);
			
			//insert to T_EPS_FTOL_LOG
			computeTollDao.insertToTEPSFTOLLog(transactionDto);
		}
		else
		{
			//pushing to ATP QUEUE
			logger.info("Pushing to ATP Queue");
			CommonClassificationDto commonDTO = getClassificationDTO(transactionDto);	
			logger.info("CommonClassificationDTO: {}", commonDTO);
			
			if (commonDTO.getTxStatus() != null) 
			{
				transactionDto.setTxStatus(String.valueOf(commonDTO.getTxStatus()));
			}
			
			logger.info("Calling ATP Queue");
			pushInStreamAndUpdateDB(transactionDto, commonDTO, "parking");
			
			//insert to T_EPS_FTOL_LOG
			computeTollDao.insertToTEPSFTOLLog(transactionDto);
		}
	}


}
