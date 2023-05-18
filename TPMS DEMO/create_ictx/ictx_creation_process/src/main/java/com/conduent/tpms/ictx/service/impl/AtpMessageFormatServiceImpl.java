package com.conduent.tpms.ictx.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.ictx.constants.IctxConstant;
import com.conduent.tpms.ictx.dto.AtpMessageDto;
import com.conduent.tpms.ictx.model.AwayTransaction;
import com.conduent.tpms.ictx.service.AtpMessageFormatService;
import com.conduent.tpms.ictx.utility.EtcTxStatusHelper;
import com.conduent.tpms.ictx.utility.LocalDateTimeUtility;

/**
 * ATP Message format service
 * 
 * @author deepeshb
 *
 */
@Service
public class AtpMessageFormatServiceImpl implements AtpMessageFormatService {

	@Autowired
	private EtcTxStatusHelper etcTxStatusHelper;

	@Autowired
	private LocalDateTimeUtility localDateTimeUtility;
	
	@Autowired
	TimeZoneConv timeZoneConv;

	/**
	 * Get ATP message
	 * 
	 * @param AwayTransaction
	 * @return AtpMessageDto
	 */
	public AtpMessageDto getAtpMessage(AwayTransaction txDto,String fileType) {

		AtpMessageDto commonDTO = new AtpMessageDto();

		if(fileType.equalsIgnoreCase(IctxConstant.FILE_EXTENSION_ITXC)) {
			//commonDTO.setCorrectionReason(txDto.getCorrectionReason());
			commonDTO.setCorrectionReason(Long.valueOf(txDto.getCorrReasonId()));
		}
			
		commonDTO.setLaneTxId(txDto.getLaneTxId());
		commonDTO.setTxExternRefNo(txDto.getTxExternRefNo());

		commonDTO.setExternFileId(txDto.getExternFileId());
		commonDTO.setPlazaAgencyId(txDto.getPlazaAgencyId());
		commonDTO.setPlazaId(txDto.getPlazaId());
		commonDTO.setLaneId(txDto.getLaneId());

		
		LocalDateTime etcDateTime = txDto.getTxTimestampLocalDateTime() != null ? txDto.getTxTimestampLocalDateTime() : null;
		String timezoneVal = "";
		if (etcDateTime != null && timeZoneConv.zonedTxTimeOffset(etcDateTime, Long.valueOf(txDto.getPlazaId())) != null) {
			String timeZoneTemp = timeZoneConv.zonedTxTimeOffset(etcDateTime, Long.valueOf(txDto.getPlazaId())).toString();
			if(timeZoneTemp.length()>25) {
				timezoneVal = timeZoneTemp.substring(0, 19)+timeZoneTemp.substring(23, 29);
			}else {
				timezoneVal = timeZoneTemp;
			}
		}

		commonDTO.setTxTimestamp(timezoneVal);
		
		commonDTO.setTxModSeq(0);
		commonDTO.setTxTypeInd(txDto.getTxType());
		commonDTO.setTxSubtypeInd(txDto.getTxSubType());
		commonDTO.setTollSystemType(txDto.getTollSystemType());
		commonDTO.setLaneMode(txDto.getLaneMode());
		commonDTO.setLaneState(txDto.getLaneState());
		commonDTO.setLaneHealth(0);
		commonDTO.setCollectorId(txDto.getCollectorId());
		commonDTO.setEntryDataSource(txDto.getEntryDataSource());
		commonDTO.setEntryLaneId(txDto.getEntryLaneId());
		commonDTO.setEntryPlazaId(txDto.getEntryPlazaId());
		
		LocalDateTime etcEntryDateTime = txDto.getEntryTimestampLocalDateTime() != null ? txDto.getEntryTimestampLocalDateTime() : null;
		String entryTimezoneVal = "";
		if (etcEntryDateTime != null && timeZoneConv.zonedTxTimeOffset(etcEntryDateTime, Long.valueOf(txDto.getPlazaId())) != null) {
			String entryTimeZoneTemp = timeZoneConv.zonedTxTimeOffset(etcEntryDateTime, Long.valueOf(txDto.getPlazaId())).toString();
			if(entryTimeZoneTemp.length()>25) {
				entryTimezoneVal = entryTimeZoneTemp.substring(0, 19)+entryTimeZoneTemp.substring(23, 29);
			}else {
				entryTimezoneVal = entryTimeZoneTemp;
			}
		}
		commonDTO.setEntryTimestamp(entryTimezoneVal);
		
		
		commonDTO.setEntryTxSeqNumber(txDto.getEntryTxSeqNumber());
		commonDTO.setEntryVehicleSpeed(txDto.getVehicleSpeed());
		commonDTO.setLaneTxStatus(0);
		commonDTO.setLanetxType(0);
		commonDTO.setTollRevenueType(txDto.getTollRevenueType());
		commonDTO.setActualClass(txDto.getActualClass());
		commonDTO.setActualAxles(txDto.getActualAxles());

		commonDTO.setCollectorClass(0);
		commonDTO.setCollectorAxles(0);
		commonDTO.setPreClassAxles(0);
		commonDTO.setPreClassClass(0);
		commonDTO.setPostClassAxles(txDto.getActualAxles());
		commonDTO.setPostClassClass(txDto.getActualClass());
		commonDTO.setForwardAxles(0);
		commonDTO.setReverseAxles(0);
		commonDTO.setFullFareAmount(txDto.getFullFareAmount());
		commonDTO.setDiscountedAmount(txDto.getDiscountedAmount());
		commonDTO.setUnrealizedAmount(0.0);
		commonDTO.setCollectedAmount(0.0);

		commonDTO.setIsDiscountable("N");
		commonDTO.setIsMedianFare("N");
		commonDTO.setIsPeak("N");
		commonDTO.setPriceScheduleId(0);
		commonDTO.setVehicleSpeed(txDto.getVehicleSpeed());
		commonDTO.setReceiptIssued(0);
		commonDTO.setDeviceNo(txDto.getDeviceNo());
		commonDTO.setAccountType(txDto.getAccountType());

		commonDTO.setEtcAccountId(txDto.getEtcAccountId());
		commonDTO.setAccountAgencyId(txDto.getAccountAgencyId());

		commonDTO.setPostDeviceStatus(0);
		commonDTO.setPreTxnBalance(txDto.getPreTxnBalance());
		commonDTO.setPlanTypeId(0);
		commonDTO.setEtcTxStatus(etcTxStatusHelper.getEtcTxStatus(txDto.getTxType(), txDto.getTxSubType(),
				txDto.getEtcAccountId(), txDto.getLaneTxId()));

		commonDTO.setSpeedViolFlag(txDto.getSpeedViolFlag());

		commonDTO.setPlateCountry("***");
		commonDTO.setPlateState(txDto.getPlateState());
		commonDTO.setPlateNumber(txDto.getPlateNumber());

		commonDTO.setPostedDate(null);
		commonDTO.setAtpFileId(txDto.getAtpFileId());
		commonDTO.setIsReversed("N");
		commonDTO.setCorrReasonId(0);
		commonDTO.setReconDate(null);
		commonDTO.setReconStatusInd(0);
		commonDTO.setReconSubCodeInd(0);
		commonDTO.setExternFileDate(null);
		commonDTO.setMileage(0);

		commonDTO.setTxDate(String.valueOf(txDto.getTxDate()));
		commonDTO.setCscLookupKey(null);
		commonDTO.setUpdateTs(LocalDateTime.now().toString());
		commonDTO.setReciprocityTrx(txDto.getReciprocityTrx());
		commonDTO.setTxStatus(txDto.getTxStatus());

		txDto.setEtcTxStatus(commonDTO.getEtcTxStatus());
		
		return commonDTO;

	}

}
