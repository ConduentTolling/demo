package com.conduent.tpms.unmatched.serviceImpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.unmatched.constant.UnmatchedConstant;
import com.conduent.tpms.unmatched.dao.AgencyTxPendingDao;
import com.conduent.tpms.unmatched.dao.ComputeTollDao;
import com.conduent.tpms.unmatched.dto.CommonUnmatchedDto;
import com.conduent.tpms.unmatched.dto.TransactionDto;
import com.conduent.tpms.unmatched.model.ConfigVariable;
import com.conduent.tpms.unmatched.service.CommonNotificationService;
import com.conduent.tpms.unmatched.service.CommonUnmatchedService;
import com.conduent.tpms.unmatched.utility.TxStatusHelper;

/**
 * Common Notification Service Implementation
 * 
 * @author Sameer
 *
 */
@Service
public class CommonNotificationServiceImpl implements CommonNotificationService {

	private static final Logger logger = LoggerFactory.getLogger(CommonNotificationServiceImpl.class);

	@Autowired
	ConfigVariable configVariable;

	@Autowired
	private CommonUnmatchedService commonClassificationService;

	@Autowired
	private ComputeTollDao computeTollDao;

	@Autowired
	private TxStatusHelper txStatusHelper;

	@Autowired
	private AgencyTxPendingDao agencyTxPendingDao;
	

	
	/**
	 * Push Message to Home Stream or Away Stream or call to IBTS API Based on Tx
	 * Type
	 */
	
	Map<String,String> reportsMap=new HashMap<String,String>();
	public void pushMessage(TransactionDto txDTO) {
		
		Integer count =0;

		if (txDTO.getTxType().equals("V")) {
			txDTO.setIsViolation("1");
		}
		// get ATPFileID & update record count in qtap statitics
		// getAndUpdateQtpStatitics(txDTO);
		CommonUnmatchedDto commonDTO = getClassificationDTO(txDTO);

		if (commonDTO.getTxStatus() != null) {
			txDTO.setTxStatus(String.valueOf(commonDTO.getTxStatus()));
		}
		
			logger.info("CommonUnmatchedDTO: {}", commonDTO);
			if ("U".equalsIgnoreCase(txDTO.getTxType()) && "T".equalsIgnoreCase(txDTO.getTxSubType()))
			{
				// home & Posting
				txDTO.setTxStatus(UnmatchedConstant.UNMATCHED_POSTED);
				commonDTO.setTxStatus(UnmatchedConstant.UNMATCHED_POSTED_STATUS);
				
				commonClassificationService.insertToHomeEtcQueue(commonDTO);
				pushInStreamAndUpdateDB(txDTO, commonDTO, configVariable.getHomeAgencyStreamId());
				
				count++;
				reportsMap.put("Count of U/T transaction for ATP queue", count.toString());
			}
			else if ("U".equalsIgnoreCase(txDTO.getTxType()) && "X".equalsIgnoreCase(txDTO.getTxSubType()))
			{
				// home & Posting
				txDTO.setTxStatus(UnmatchedConstant.UNMATCHED_MAX_TOLL_STATUS);
				commonDTO.setTxStatus(UnmatchedConstant.EXPIRED_EXIT_STATUS);
				commonDTO.setIsMedianFare(UnmatchedConstant.X);
				commonDTO.setFareType(2);
				commonClassificationService.insertToHomeEtcQueue(commonDTO);
				pushInStreamAndUpdateDB(txDTO, commonDTO, configVariable.getHomeAgencyStreamId());
				
				count++;
				reportsMap.put("Count of U/X transaction for ATP queue", count.toString());
			}
			else if ("D".equalsIgnoreCase(txDTO.getTxType()))
			{
				// home & Posting
				// TxStatus will be 67 or 69
				commonClassificationService.insertToHomeEtcQueue(commonDTO);
				pushInStreamAndUpdateDB(txDTO, commonDTO, configVariable.getHomeAgencyStreamId());
				
				count++;
				reportsMap.put("Count of Discard transaction for ATP queue", count.toString());
			}	
			else if ("O".equalsIgnoreCase(txDTO.getTxType())) {
				// Away posting
				// computeTollDao.updateTTransDetail(txDTO);//commented
				agencyTxPendingDao.insert(commonDTO);
				
				count++;
				reportsMap.put("Count of Away transaction", count.toString());
			}
			else {
				logger.info("not maching txType in unmatched..");
			}
		}

	private void getAndUpdateQtpStatitics(TransactionDto transactionDto) {
		computeTollDao.getQtpStatitics(transactionDto);
		computeTollDao.updateQtpStatitics(transactionDto);
	}

	private void pushInStreamAndUpdateDB(TransactionDto txDTO, CommonUnmatchedDto commonDTO, String streamId) {
		// computeTollDao.updateTTransDetail(txDTO);
		commonClassificationService.pushMessage(commonDTO, streamId);
	}

	/**
	 * Get CommonUnmatchedDTO
	 * 
	 * @param TransactionDto
	 * @return CommonUnmatchedDTO
	 */
	private CommonUnmatchedDto getClassificationDTO(TransactionDto txDTO) {
		CommonUnmatchedDto commonDTO = new CommonUnmatchedDto();

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
		commonDTO.setEntryTimestamp(txDTO.getEntryTxTimeStamp() !=null ? txDTO.getEntryTxTimeStamp() : null);
		commonDTO.setEntryTxSeqNumber(txDTO.getEntryTxSeqNumber());
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
		if (txDTO.getTagIagClass() != null) {
			commonDTO.setDeviceIagClass((int) (long) txDTO.getTagIagClass());
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
		// removing default value '0' and adding the actual value.
		commonDTO.setPlanTypeId(txDTO.getPlanType());
		// commonDTO.setEtcTxStatus(etcTxStatusHelper.getEtcTxStatus(txDTO.getTxType(),
		// txDTO.getTxSubType(),
		// txDTO.getEtcAccountId(), txDTO.getLaneTxId()));
		// sakshi
		String status = txDTO.getTxStatus();
				Integer txStatus = Integer.valueOf(status);
		commonDTO.setTxStatus(txStatus);
		commonDTO.setAetFlag(txDTO.getAetFlag());
		// commonDTO.setEtcTxStatus(null);
		commonDTO.setSpeedViolFlag(txDTO.getSpeedViolation());
		commonDTO.setImageTaken(txDTO.getImageCaptured());
		commonDTO.setPlateCountry(txDTO.getPlateCountry());
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

}
