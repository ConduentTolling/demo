package com.conduent.Ibtsprocessing.serviceimpl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.conduent.Ibtsprocessing.dao.IXferControlDao;
import com.conduent.Ibtsprocessing.dto.IBTSViolDTO;
import com.conduent.Ibtsprocessing.dto.IbtsViolProcessDTO;
import com.conduent.Ibtsprocessing.dto.QueueMessage;
import com.conduent.Ibtsprocessing.model.XferControl;
import com.conduent.Ibtsprocessing.service.IMessageProcessingJob;
import com.conduent.Ibtsprocessing.service.IPushMessageService;
import com.conduent.Ibtsprocessing.utility.MasterCache;
import com.conduent.app.timezone.utility.TimeZoneConv;

@Service
public class PushMessageServiceImpl implements IPushMessageService {

	private static final Logger logger = LoggerFactory.getLogger(PushMessageServiceImpl.class);

	@Value("${config.away.streamid}")
	private String awayAgencyStreamId;

	@Autowired
	private IMessageProcessingJob messageProcessingJob;

	@Autowired
	MasterCache masterCache;
	
	@Autowired
	IXferControlDao xferControlDao;
	
	@Autowired
	TimeZoneConv timeZoneConv;

	/**
	 * Push Message to Home Stream or Away Stream or call to IBTS API Based on Tx
	 * Type
	 */
	public void pushMessageToAway(QueueMessage queueObj) {
		IbtsViolProcessDTO commonDTO = getClassificationDTO(queueObj);
		String streamId = null;
		logger.info("CommonClassificationDTO: {}", commonDTO);
		streamId = awayAgencyStreamId;
		messageProcessingJob.pushMessage(commonDTO, streamId);
		// commonClassificationService.insert(commonDTO);

	}

	public boolean pushMessageToViol(QueueMessage queueObj) throws ParseException {
		
		boolean sucessFlag= false;
		logger.info("Entering in Puush Message Method");
		IBTSViolDTO commonIbtsDTo = getIbtsDTO(queueObj);	
		logger.info("Getting commonIbtsDTo attribute values");
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		if (queueObj.getTxTimestamp() != null) {
			//commonIbtsDTo.setTxTimestamp((LocalDateTime.parse(queueObj.getTxTimestamp(), format)).toString());
			commonIbtsDTo.setTxTimestamp(queueObj.getTxTimestamp().toString());
		}
		if (queueObj.getEntryTimestamp() != null) {
			//commonIbtsDTo.setEntryTimestamp((LocalDateTime.parse(queueObj.getTxTimestamp(), format)).toString());
			commonIbtsDTo.setEntryTimestamp(queueObj.getEntryTimestamp().toString());
		}
		if (queueObj.getEventTimestamp()!= null) {
			//commonIbtsDTo.setEntryTimestamp((LocalDateTime.parse(queueObj.getTxTimestamp(), format)).toString());
			commonIbtsDTo.setEventTimestamp(timeZoneConv.currentTime());
		}
		logger.info("Before send to IBTS method.. commonIBTSDto {}",commonIbtsDTo);
		sucessFlag = messageProcessingJob.sendToIBTS(commonIbtsDTo);
		IbtsViolProcessDTO commonDTO = getClassificationDTO(queueObj);
		messageProcessingJob.insert(commonDTO);
		
		return sucessFlag;
	}

	public IBTSViolDTO getIbtsDTO(QueueMessage queueObj) throws ParseException {
		IBTSViolDTO violDTO = new IBTSViolDTO();

		violDTO.setAccountAgencyId(queueObj.getAccountAgencyId()!=null?queueObj.getAccountAgencyId():null);
		violDTO.setAccountType(queueObj.getAccountType()!=null?queueObj.getAccountType():null);
		violDTO.setActualAxles(queueObj.getActualAxles()!=null?queueObj.getActualAxles():0);
		violDTO.setActualClass(queueObj.getActualClass()!=null?queueObj.getActualClass():0);
		violDTO.setActualExtraAxles(queueObj.getActualExtraAxles()!=null?queueObj.getActualExtraAxles():0);

		violDTO.setAetFlag(queueObj.getAetFlag());
		violDTO.setAtpFileId(queueObj.getAtpFileId());
		violDTO.setBasefilename(null);
		if (queueObj.getBufferReadFlag() != null) {
			violDTO.setBufferedReadFlag(queueObj.getBufferReadFlag());
		}
		
		violDTO.setClassAdjType(null);
		violDTO.setCollectorAxles(queueObj.getCollectorAxles()!=null?queueObj.getCollectorAxles():0);
		violDTO.setCollectorClass(queueObj.getCollectorClass()!=null?queueObj.getCollectorClass():0);
		violDTO.setcollectorNumber(queueObj.getCollectorNumber()!=null?queueObj.getCollectorNumber():0);
		violDTO.setDepositId(null);
		
		violDTO.setDeviceAgencyClass(queueObj.getDeviceAgencyClass());
		violDTO.setDeviceAxles(queueObj.getDeviceAxles());
		violDTO.setDeviceCodedClass(queueObj.getDeviceIagClass());
		violDTO.setDeviceIagClass(queueObj.getDeviceIagClass());
		violDTO.setDeviceNo(queueObj.getDeviceNo());
		
		if (queueObj.getDeviceProgramStatus() != null) {
			violDTO.setDeviceProgramStatus(queueObj.getDeviceProgramStatus());
		}
		violDTO.setDeviceReadCount(queueObj.getDeviceReadCount());
		violDTO.setDeviceWriteCount(queueObj.getDeviceWriteCount());
		violDTO.setDiscountedAmount(queueObj.getDiscountedAmount());
		violDTO.setDiscountedAmount2(queueObj.getDiscountedAmount2());
		if (queueObj.getEntryTimestamp() != null) {

			violDTO.setEntryTimestamp(queueObj.getEntryTimestamp().toString());
		}
		
		violDTO.setEntryDataSource(queueObj.getEntryDataSource());
		violDTO.setEntryDeviceReadCount(queueObj.getEntryDeviceReadCount());
		violDTO.setEntryDeviceWriteCount(queueObj.getEntryDeviceWriteCount());
		violDTO.setEntryLaneId(queueObj.getEntryLaneId()!=null?queueObj.getEntryLaneId():0);
		violDTO.setEntryPlazaId(queueObj.getEntryPlazaId()!=null?queueObj.getEntryPlazaId():0);
		violDTO.setEntryTxSeqNumber(queueObj.getEntryTxSeqNumber()!=null?queueObj.getEntryTxSeqNumber():0);
		
		violDTO.setEtcAccountId(queueObj.getEtcAccountId());
		//violDTO.setEtcTxStatus(queueObj.getEtcTxStatus()); //this is now renamed to TxStatus Temporary change
//		if (queueObj.getEventTimestamp() != null) {
//			violDTO.setEventTimestamp(queueObj.getEventTimestamp());
//		}
		violDTO.setEventType(queueObj.getEventType());
		
		if(queueObj.getExternFileId()!=null) {
			
		violDTO.setExternFileId(queueObj.getExternFileId());
		XferControl getFileDateinfo = xferControlDao.getXferControlDate(queueObj.getExternFileId());
		if(getFileDateinfo!=null && getFileDateinfo.getDateCreated()!=null) {
			violDTO.setExternFileDate(getFileDateinfo.getDateCreated().toString());
		}
		}
		
		violDTO.setForwardAxles(queueObj.getForwardAxles());
	//	violDTO.setFullFareAmount(queueObj.getFullFareAmount());
		violDTO.setIsDiscountable(queueObj.getIsDiscountable()!=null?queueObj.getIsDiscountable():"F");
		violDTO.setIsMedianFare(queueObj.getIsMedianFare());
		violDTO.setIsPeak(queueObj.getIsPeak()!=null?queueObj.getIsPeak():"F");
		violDTO.setIsReciprocityTxn(queueObj.getReciprocityTrx()!=null?queueObj.getReciprocityTrx():"F");
	
		violDTO.setIsReversed(queueObj.getIsReversed());
		violDTO.setLaneId(queueObj.getLaneId());
		violDTO.setLaneMode(queueObj.getLaneMode()!=null?queueObj.getLaneMode():0);
		violDTO.setLaneState(queueObj.getLaneState()!=null?queueObj.getLaneState():0);
		violDTO.setLaneHealth(queueObj.getLaneHealth());
		
		if (queueObj.getLaneTxId() != null) {
			violDTO.setLaneTxId(queueObj.getLaneTxId());
		}
		violDTO.setImageTaken(queueObj.getImageTaken());
		violDTO.setLaneTxStatus(queueObj.getLaneTxStatus());
		violDTO.setLaneTxType(queueObj.getLanetxType());
		
		violDTO.setLaneType(queueObj.getLaneType()!=null?queueObj.getLaneType():0);
		violDTO.setPlanTypeId(queueObj.getPlanTypeId());
		violDTO.setPlateCountry(queueObj.getPlateCountry());
		violDTO.setPlateNumber(queueObj.getPlateNumber());
		violDTO.setPlateState(queueObj.getPlateState());
		violDTO.setPlazaAgencyId(queueObj.getPlazaAgencyId());
		
		violDTO.setPlazaId(queueObj.getPlazaId());
		violDTO.setPostClassAxles(queueObj.getPostClassAxles());
		violDTO.setPostClassClass(queueObj.getPostClassClass());
		violDTO.setPostDeviceStatus(queueObj.getPostDeviceStatus());
		violDTO.setPreTxnBalance(queueObj.getPreTxnBalance());
		violDTO.setPrevEventType(queueObj.getPrevEventType()!=null?queueObj.getPrevEventType():0);
		
		violDTO.setPrevViolTxStatus(queueObj.getPrevViolTxStatus());
		violDTO.setPriceScheduleId(queueObj.getPriceScheduleId());
		violDTO.setReadAviAxles(queueObj.getReadAviAxles());
		if (queueObj.getSpeedViolFlag() != null) {
			violDTO.setSpeedViolFlag(queueObj.getSpeedViolFlag());
		}
		violDTO.setTollRevenueType(queueObj.getTollRevenueType());
		
		violDTO.setTollSystemType(queueObj.getTollSystemType());
		violDTO.setTxDate(queueObj.getTxDate());
		violDTO.setTxExternRefNo(queueObj.getTxExternRefNo());
		violDTO.setTxModSeq(queueObj.getTxModSeq());
	
		if (queueObj.getTxSeqNumber() != null) {
			violDTO.setTxSeqNumber(queueObj.getTxSeqNumber());
		}else {
			violDTO.setTxSeqNumber((long)0);
		}
		//violDTO.setTxSubType(queueObj.getTxSubType());
		violDTO.setTxSubtypeInd(queueObj.getTxSubtypeInd());
		if(queueObj.getTxTimestamp()!=null) {
		violDTO.setTxTimestamp(queueObj.getTxTimestamp().toString());
		}
		violDTO.setTxTypeInd(queueObj.getTxTypeInd());
		//violDTO.setTxType(queueObj.getTxType()!= null?queueObj.getTxType() : " " );

		violDTO.setUnrealizedAmount(queueObj.getUnrealizedAmount());
		violDTO.setVehicleSpeed(queueObj.getVehicleSpeed());
		violDTO.setViolTxStatus(queueObj.getViolTxStatus()); //Temporary change for IBTS issue
		violDTO.setViolType(queueObj.getViolType());
		//violDTO.setHovFlag("N");
		violDTO.setHovFlag(queueObj.getHovFlag()!=null?queueObj.getHovFlag():"N");
		
		
		violDTO.setVideoFareAmount(queueObj.getVideoFareAmount());
		
		//commented as suggested by PB
		/*
		 * violDTO.setEtcFareAmount(queueObj.getVideoFareAmount());
		 * violDTO.setCashFareAmount(queueObj.getFullFareAmount());
		 * violDTO.setExpectedRevenueAmount(queueObj.getTollAmount());
		 * violDTO.setPostedFareAmount(queueObj.getTollAmount());
		 */
		
		violDTO.setEtcFareAmount(queueObj.getEtcFareAmount());
		violDTO.setCashFareAmount(queueObj.getCashFareAmount());
		violDTO.setExpectedRevenueAmount(queueObj.getExpectedRevenueAmount());
		violDTO.setPostedFareAmount(queueObj.getPostedFareAmount());
		violDTO.setItolFareAmount(queueObj.getItolFareAmount());
		
		
		violDTO.setTxStatus(queueObj.getTxStatus());
		logger.info("Entering in Puush Message Method ----------end and returning");
		return violDTO;

	}

	public IbtsViolProcessDTO getClassificationDTO(QueueMessage queueObj) {
		IbtsViolProcessDTO violDTO = new IbtsViolProcessDTO();

		violDTO.setLaneTxId(queueObj.getLaneTxId());
		violDTO.setTxExternRefNo(queueObj.getTxExternRefNo());
		violDTO.setTxSeqNumber(queueObj.getTxSeqNumber());
		violDTO.setExternFileId(queueObj.getExternFileId());
		violDTO.setLaneId(queueObj.getLaneId());
		violDTO.setDeviceNo(queueObj.getDeviceNo());
		if(queueObj.getTxTimestamp()!=null) {
		violDTO.setTxTimeStamp(queueObj.getTxTimestamp());
		}
		if(queueObj.getTxDate()!=null) {
		violDTO.setTxDate(queueObj.getTxDate().toString());
		}
		violDTO.setTxModSeq(0);
		violDTO.setTxType(queueObj.getTxTypeInd());
		//violDTO.setTxType(queueObj.getTxTypeInd()!= null?queueObj.getTxTypeInd() : " ");
		violDTO.setTxSubtype(queueObj.getTxSubtypeInd());
		violDTO.setTollSystemType(queueObj.getTollSystemType());
		if (queueObj.getLaneMode() != null) {
			violDTO.setLaneMode(queueObj.getLaneMode());
		}
		violDTO.setLaneType(queueObj.getLaneType());
		violDTO.setLaneState(queueObj.getLaneState());
		violDTO.setLaneHealth(queueObj.getLaneHealth());
		violDTO.setPlazaAgencyId(queueObj.getPlazaAgencyId());
		violDTO.setPlazaId(queueObj.getPlazaId());
		violDTO.setcollectorNumber(queueObj.getCollectorNumber());
		violDTO.setTourSegment(queueObj.getTourSegment());
		violDTO.setEntryDataSource(queueObj.getEntryDataSource());
		violDTO.setEntryLaneId(queueObj.getEntryLaneId());
		violDTO.setEntryPlazaId(queueObj.getEntryPlazaId());
		if(queueObj.getEntryTimestamp()!=null) {
		violDTO.setEntryTimestamp(queueObj.getEntryTimestamp());
		}
		violDTO.setEntryTxSeqNumber(queueObj.getEntryTxSeqNumber());
		violDTO.setEntryVehicleSpeed(queueObj.getVehicleSpeed());
		violDTO.setLaneTxStatus(queueObj.getLaneTxStatus()); // check
		violDTO.setLanetxType(queueObj.getIsPeak()==null?1:queueObj.getIsPeak()=="Y"?1:0);
		violDTO.setTollRevenueType(queueObj.getTollRevenueType());
		violDTO.setActualClass(queueObj.getActualClass());
		violDTO.setActualAxles(queueObj.getActualAxles());
		violDTO.setActualExtraAxles(queueObj.getActualExtraAxles());
		violDTO.setCollectorClass(queueObj.getCollectorClass());
		violDTO.setCollectorAxles(queueObj.getCollectorAxles());
		violDTO.setPreClassAxles(queueObj.getPreClassAxles());
		violDTO.setPreClassClass(queueObj.getPreClassClass());
		violDTO.setPostClassAxles(queueObj.getPostClassAxles());
		violDTO.setPostClassClass(queueObj.getPostClassClass());
		violDTO.setForwardAxles(queueObj.getForwardAxles());
		violDTO.setReverseAxles(queueObj.getReverseAxles());
		if(queueObj.getFullFareAmount()!=null) {
		violDTO.setFullFareAmount(queueObj.getFullFareAmount() == '*' ? 0 : queueObj.getFullFareAmount());
		}
		violDTO.setDiscountedAmount(queueObj.getDiscountedAmount()); 
		violDTO.setDiscountedAmount2(queueObj.getDiscountedAmount2());
		violDTO.setUnrealizedAmount(queueObj.getUnrealizedAmount()); 
		if(queueObj.getCollectedAmount()!=null) {
		violDTO.setCollectedAmount((double)queueObj.getCollectedAmount());
		}
		violDTO.setIsDiscountable("N");
		violDTO.setIsMedianFare("N"); // fare_type
		violDTO.setIsPeak(queueObj.getIsPeak());
		violDTO.setPriceScheduleId(queueObj.getPriceScheduleId());
		violDTO.setVehicleSpeed(queueObj.getVehicleSpeed());
		violDTO.setReceiptIssued(0); // dst_flag
		violDTO.setAccountType(queueObj.getAccountType());
		violDTO.setDeviceCodedClass(queueObj.getDeviceCodedClass());
		violDTO.setDeviceAgencyClass(queueObj.getDeviceAgencyClass());
		violDTO.setDeviceIagClass(queueObj.getDeviceIagClass());
		violDTO.setDeviceAxles(queueObj.getDeviceAxles());
		violDTO.setEtcAccountId(queueObj.getEtcAccountId());
		violDTO.setAccountAgencyId(queueObj.getAccountAgencyId());
		violDTO.setReadAviAxles(queueObj.getDeviceAxles());
		violDTO.setReadAviClass(queueObj.getDeviceAgencyClass());
		violDTO.setProgStat(queueObj.getProgStat());
	//	violDTO.setBuffRead(queueObj.getBuffRead());
		if (queueObj.getBufferReadFlag() != null) {
			violDTO.setBufferReadFlag(queueObj.getBufferReadFlag());
		}
		if (queueObj.getDeviceProgramStatus() != null) {
			violDTO.setDeviceProgramStatus(queueObj.getDeviceProgramStatus());
		}
		violDTO.setTollAmount(queueObj.getTollAmount());
		violDTO.setEtcValidStatus(queueObj.getEtcValidStatus());
		violDTO.setPostDeviceStatus(1);
		violDTO.setPreTxnBalance(0);
		if(queueObj.getPlanTypeId()!=null) {
		violDTO.setPlanTypeId(queueObj.getPlanTypeId() == '*' ? 0 : queueObj.getPlanTypeId());
		}
		//violDTO.setEtcTxStatus(masterCache.getCodeID("ETC_TX_STATUS", "QVIOL").getCodeId());
		violDTO.setTxStatus(masterCache.getCodeID("TX_STATUS", "QVIOL").getCodeId());
		violDTO.setImageTaken(queueObj.getImageTaken());
		violDTO.setPlateCountry("****");
		violDTO.setPlateState(queueObj.getPlateState());
		violDTO.setPlateNumber(queueObj.getPlateNumber());
		violDTO.setRevenueDate(queueObj.getRevenueDate());
		violDTO.setPostedDate(queueObj.getPostedDate());
		violDTO.setAtpFileId(queueObj.getAtpFileId());
		violDTO.setUpdateTs(LocalDateTime.now().toString());
		violDTO.setLaneDataSource("N");
		violDTO.setCorrReasonId(queueObj.getCorrReasonId());
		violDTO.setReconDate(queueObj.getReconDate());
		violDTO.setExternFileDate(queueObj.getExternFileDate());
		violDTO.setMileage(0);
		violDTO.setInternalAuditId(0);
		violDTO.setModifiedStatus(0);
		violDTO.setExceptionStatus(0);
		violDTO.setDepositId(queueObj.getDepositId());
		violDTO.setReceivedDate(queueObj.getReceivedDate());
		violDTO.setViolType(queueObj.getViolType());
		violDTO.setReviewedVehicleType(0);
		violDTO.setReviewedClass(0);
		violDTO.setImageBatchId((long) 0);
		violDTO.setImageRvwClerkId(0);
		violDTO.setImageIndex(1);
		violDTO.setImageBatchSeqNumber((long) 0);
		violDTO.setAdjustedAmount((long) 0);
		violDTO.setNoticeTollAmount((long) 0);
		violDTO.setOutputFileId(0);
		violDTO.setOutputFileType(null);
		violDTO.setReciprocityTrx(queueObj.getReciprocityTrx());
		violDTO.setMakeId(0);
		violDTO.setEventTimestamp(LocalDateTime.now().toString());
		violDTO.setIsLprEnabled(queueObj.getIsLprEnabled());
		violDTO.setEventType(masterCache.getCodeID("EVENT_TYPE", "TXNINSERT").getCodeId());
		violDTO.setPreeventType(0);
		violDTO.setViolTxStatus(queueObj.getViolTxStatus());
		violDTO.setPrevViolTxStatus(0);
		violDTO.setDmvPlateType(1);
		violDTO.setPostDeviceStatus(0);
		violDTO.setCscLookupKey(queueObj.getCscLookupKey());
		violDTO.setEntryDeviceWriteCount(queueObj.getEntryDeviceWriteCount());
		violDTO.setEntryDeviceReadCount(queueObj.getEntryDeviceReadCount());
		violDTO.setDeviceReadCount(queueObj.getDeviceReadCount());
		violDTO.setDeviceWriteCount(queueObj.getDeviceWriteCount());
		violDTO.setReconSubCodeInd(queueObj.getReconSubCodeInd());
		violDTO.setReconStatusInd(queueObj.getReconStatusInd());
		violDTO.setIsReversed(queueObj.getIsReversed());
		violDTO.setAtpFileId(queueObj.getAtpFileId());
		violDTO.setSpeedViolFlag(queueObj.getSpeedViolFlag());
		violDTO.setItolFareAmount(queueObj.getItolFareAmount());
		return violDTO;
	}

}
