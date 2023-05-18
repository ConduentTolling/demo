package com.conduent.tpms.ictx.service.impl;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.ictx.constants.IctxConstant;
import com.conduent.tpms.ictx.dao.AwayTransactionDao;
import com.conduent.tpms.ictx.dao.TransactionDetailDao;
import com.conduent.tpms.ictx.dto.MessageConversionDto;
import com.conduent.tpms.ictx.dto.RevenueDateStatisticsDto;
import com.conduent.tpms.ictx.model.Agency;
import com.conduent.tpms.ictx.model.AwayTransaction;
import com.conduent.tpms.ictx.model.ConfigVariable;
import com.conduent.tpms.ictx.model.Lane;
import com.conduent.tpms.ictx.model.Plaza;
import com.conduent.tpms.ictx.model.TollCalculationDto;
import com.conduent.tpms.ictx.model.TollCalculationResponseDto;
import com.conduent.tpms.ictx.model.VehicleClass;
import com.conduent.tpms.ictx.service.IctxConversionService;
import com.conduent.tpms.ictx.service.TollCalculationService;
import com.conduent.tpms.ictx.utility.LocalDateTimeUtility;
import com.conduent.tpms.ictx.utility.LocalTimeUtility;
import com.conduent.tpms.ictx.utility.StaticDataLoad;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * ICTX message conversion service
 * 
 * @author deepeshb
 *
 */
@Service
public class IctxConversionServiceImpl implements IctxConversionService {

	@Autowired
	private StaticDataLoad staticDataLoad;

	@Autowired
	private LocalDateTimeUtility localDateTimeUtility;

	@Autowired
	private LocalTimeUtility localTimeUtility;

	@Autowired
	private TollCalculationService tollCalculationService;

	@Autowired
	private AwayTransactionDao awayTransactionDao;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	ConfigVariable configVariable;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	
	@Autowired
	TransactionDetailDao transactionDetailDao;

	private static final Logger logger = LoggerFactory.getLogger(IctxConversionServiceImpl.class);

	/**
	 * Get Ictx message info
	 * 
	 * @param awayTransaction
	 * @return MessageConversionDto
	 */
	public MessageConversionDto getIctxTransaction(AwayTransaction awayTransaction,String fileType) {
		logger.info("Away Transaction in Ictx conversion service {}", awayTransaction);
		MessageConversionDto messageConversionDto = new MessageConversionDto();
		StringBuilder sb = new StringBuilder();
		String tollAmount;
		String creditDebit;
		
		if(fileType.equalsIgnoreCase(IctxConstant.FILE_EXTENSION_ICTX)) {
			creditDebit = getEtcDebitCredit(messageConversionDto);
			
		}else {
			sb.append(getCorrectionReason(awayTransaction, messageConversionDto));
			creditDebit = getEtcDebitCreditCorrection(awayTransaction, messageConversionDto);	
		}
		
		//Niranjan
		sb.append(getEtcTrxSerialNum(awayTransaction, messageConversionDto))
				.append(getEtcRevenueDate(awayTransaction, messageConversionDto))
				.append(getEtcFacAgency(awayTransaction, messageConversionDto))
				.append(getEtcTxType(awayTransaction, messageConversionDto))
				//.append(getEtcEntryDate(awayTransaction, messageConversionDto))
				//.append(getEtcEntryTime(awayTransaction, messageConversionDto))
				.append(getEtcEntryDateTime(awayTransaction, messageConversionDto))
				.append(getEtcEntryPlaza(awayTransaction, messageConversionDto))
				.append(getEtcEntryLane(awayTransaction, messageConversionDto))
				.append(getEtcTagAgency(awayTransaction, messageConversionDto))
				.append(getEtcTagSerialNum(awayTransaction, messageConversionDto))
				.append(getEtcReadPerformance(awayTransaction, messageConversionDto))
				.append(getEtcWritePerformance(awayTransaction, messageConversionDto))
				.append(getEtcTagPgmStatus(awayTransaction, messageConversionDto))
				.append(getEtcLaneMode(awayTransaction, messageConversionDto))
				.append(getEtcValidationStatus(awayTransaction, messageConversionDto))
				.append(getEtcLicState(awayTransaction, messageConversionDto))
				.append(getEtcLicNumber(awayTransaction, messageConversionDto))
				.append(getEtcLicType(awayTransaction, messageConversionDto))
				.append(getEtcClassCharged(awayTransaction, messageConversionDto))
				.append(getEtcActualAxles(awayTransaction, messageConversionDto))
				.append(getEtcExitSpeed(awayTransaction, messageConversionDto))
				.append(getEtcOverSpeed(awayTransaction, messageConversionDto))
				//.append(getEtcExitDate(awayTransaction, messageConversionDto))
				//.append(getEtcExitTime(awayTransaction, messageConversionDto))
				.append(getEtcExitDateTime(awayTransaction, messageConversionDto))
				.append(getEtcExitPlaza(awayTransaction, messageConversionDto))
				.append(getEtcExitLane(awayTransaction, messageConversionDto))
				.append(creditDebit)
				.append(getEtcTollAmount(awayTransaction, messageConversionDto)).append("\n");

		if(fileType.equalsIgnoreCase(IctxConstant.FILE_EXTENSION_ICTX)) {
			/// Update Full Fare AMount in T_AGENCY_TX_PENDING
			awayTransactionDao.updateExpectedRevenueAmtAndRevenueDate(awayTransaction);
		}
		logger.info("After update away Transaction {} :: ", awayTransaction);
		messageConversionDto.setTxMessage(sb.toString());
		if (!messageConversionDto.getIsValidLengthTx()) {
			logger.info("Bad Away Transaction with laneTxId:{} and ErrorMsg: {}", awayTransaction.getLaneTxId(),
					messageConversionDto.getErrorMsg());
		}

		return messageConversionDto;
	}

	/**
	 * Get EtcTrxSerialNum
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return  String
	 */
	private String getEtcTrxSerialNum(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		String tempEtcTrxSerialNum = awayTransaction.getLaneTxId() != null
				? String.valueOf(awayTransaction.getLaneTxId())
				: null;
		if (tempEtcTrxSerialNum != null && tempEtcTrxSerialNum.length() <= IctxConstant.ETC_SERIAL_NO_LENGTH) {
			return StringUtils.leftPad(tempEtcTrxSerialNum, IctxConstant.ETC_SERIAL_NO_LENGTH, "0");
		} else if (tempEtcTrxSerialNum != null && tempEtcTrxSerialNum.length() > IctxConstant.ETC_SERIAL_NO_LENGTH) {
			messageConversionDto.setIsValidLengthTx(false);
			messageConversionDto.addErrorMsg("Length of Etc tx serial num is more than expected length");
			return StringUtils.leftPad(tempEtcTrxSerialNum, IctxConstant.ETC_SERIAL_NO_LENGTH, "0");
		} else {
			return StringUtils.leftPad(" ", IctxConstant.ETC_SERIAL_NO_LENGTH, " ");
		}

	}

	/**
	 * Get CorrectionReason
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return  String
	 */
	private String getCorrectionReason(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		String tempCorrectionReason = awayTransaction.getCorrReasonId() != null
				? String.valueOf(awayTransaction.getCorrReasonId())
				: null;
		
		//need to fetch these lengths from db
		if (tempCorrectionReason != null && tempCorrectionReason.length() <= IctxConstant.ETC_CORR_REASON_LENGTH) {
			return StringUtils.leftPad(tempCorrectionReason, IctxConstant.ETC_CORR_REASON_LENGTH, "0");
		} else if (tempCorrectionReason != null && tempCorrectionReason.length() > IctxConstant.ETC_CORR_REASON_LENGTH) {
			messageConversionDto.setIsValidLengthTx(false);
			messageConversionDto.addErrorMsg("Length of CorrectionReason is more than expected length");
			return StringUtils.leftPad(tempCorrectionReason, IctxConstant.ETC_CORR_REASON_LENGTH, "0");
		} else {
			return StringUtils.leftPad(" ", IctxConstant.ETC_CORR_REASON_LENGTH, " ");
		}

	}
	
	
	/**
	 * Get EtcRevenueDate
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcRevenueDate(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		RevenueDateStatisticsDto tempRevenueDateStatisticsDto = staticDataLoad
				.getRevenueStatusByPlaza(awayTransaction.getPlazaId());

		int plazaRevenueTimeSec = tempRevenueDateStatisticsDto.getRevenueSecondOfDay();
		if (null == awayTransaction.getTxTimestamp()) {
			return StringUtils.leftPad(" ", IctxConstant.ETC_REV_DATE_LENGTH, " ");
		} else if (0 == plazaRevenueTimeSec) {
			awayTransaction.setRevenueDate(awayTransaction.getTxTimestamp().toLocalDate());
			return localDateTimeUtility.getDateYYYYMMDD(awayTransaction.getTxTimestampLocalDateTime());

		} else if (plazaRevenueTimeSec > 43200) {
			if (awayTransaction.getTxTimestamp().toLocalTime().toSecondOfDay() > plazaRevenueTimeSec) {
				awayTransaction.setRevenueDate(awayTransaction.getTxTimestamp().plusDays(1).toLocalDate());
				return localDateTimeUtility.getDateYYYYMMDD(awayTransaction.getTxTimestampLocalDateTime().plusDays(1));
			} else {
				awayTransaction.setRevenueDate(awayTransaction.getTxTimestamp().toLocalDate());
				return localDateTimeUtility.getDateYYYYMMDD(awayTransaction.getTxTimestampLocalDateTime());
			}

		} else if (plazaRevenueTimeSec < 43200) {
			if (awayTransaction.getTxTimestamp().toLocalTime().toSecondOfDay() > plazaRevenueTimeSec) {
				awayTransaction.setRevenueDate(awayTransaction.getTxTimestamp().toLocalDate());
				return localDateTimeUtility.getDateYYYYMMDD(awayTransaction.getTxTimestampLocalDateTime());
			} else {
				awayTransaction.setRevenueDate(awayTransaction.getTxTimestamp().minusDays(1).toLocalDate());
				return localDateTimeUtility.getDateYYYYMMDD(awayTransaction.getTxTimestampLocalDateTime().minusDays(1));
			}
		}

		return StringUtils.leftPad(" ", IctxConstant.ETC_REV_DATE_LENGTH, " ");
	}

	/**
	 * Get EtcFacAgency
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcFacAgency(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		Integer tempEtcFacAgency = awayTransaction.getPlazaAgencyId() != null ? awayTransaction.getPlazaAgencyId()
				: null;
		Agency tempAgency = null;
		if (tempEtcFacAgency != null) {
			tempAgency = staticDataLoad.getFacHomeAgencyById(Long.valueOf(tempEtcFacAgency));
		}
		if (tempAgency != null) {
			if (tempAgency.getDevicePrefix().length() > IctxConstant.ETC_FAC_AGENCY_LENGTH) {
				messageConversionDto.setIsValidLengthTx(false);
				messageConversionDto.addErrorMsg("Length of Etc agency fac is more than expected length");
			}
			return StringUtils.leftPad(String.valueOf(tempAgency.getDevicePrefix()), IctxConstant.ETC_FAC_AGENCY_LENGTH, "0");
		}
		return StringUtils.leftPad(" ", IctxConstant.ETC_FAC_AGENCY_LENGTH, " ");
	}

	/**
	 * Get EtcTxType
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcTxType(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		String tempTollSystemType = awayTransaction.getTollSystemType() != null ? awayTransaction.getTollSystemType()
				: null;
		if (tempTollSystemType != null) {
			if (tempTollSystemType.length() > IctxConstant.ETC_TRX_TYPE_LENGTH) {
				messageConversionDto.setIsValidLengthTx(false);
				messageConversionDto.addErrorMsg("Length of Etc tx type is more than expected length");
			}
			return StringUtils.leftPad(awayTransaction.getTollSystemType(), IctxConstant.ETC_TRX_TYPE_LENGTH, " ");
		}
		return StringUtils.leftPad(" ", IctxConstant.ETC_TRX_TYPE_LENGTH, " ");
	}

	/**
	 * Get EtcEntryDate
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcEntryDate(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		String tempTollSystemType = awayTransaction.getTollSystemType() != null ? awayTransaction.getTollSystemType()
				: null;
		if (tempTollSystemType != null && (IctxConstant.TOLL_SYSTEM_TYPE_B.equalsIgnoreCase(tempTollSystemType)
				|| IctxConstant.TOLL_SYSTEM_TYPE_U.equalsIgnoreCase(tempTollSystemType) || IctxConstant.TOLL_SYSTEM_TYPE_E.equalsIgnoreCase(tempTollSystemType))) {
			return StringUtils.leftPad("*", 8, "*");
		}
		if (awayTransaction.getEntryLaneId() != null && awayTransaction.getEntryLaneId() != 0) {
			LocalDateTime etcEntryDateTime = awayTransaction.getEntryTimestampLocalDateTime() != null
					? awayTransaction.getEntryTimestampLocalDateTime()
					: null;
			if (etcEntryDateTime != null) {
				return localDateTimeUtility.getDateYYYYMMDD(etcEntryDateTime);
			}
		}
		return StringUtils.leftPad(" ", 8, " ");
	}
	
	/**
	 * Get EtcEntryDate
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcEntryDateTime(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		String tempTollSystemType = awayTransaction.getTollSystemType() != null ? awayTransaction.getTollSystemType()
				: null;
		if (tempTollSystemType != null && (IctxConstant.TOLL_SYSTEM_TYPE_B.equalsIgnoreCase(tempTollSystemType)
				|| IctxConstant.TOLL_SYSTEM_TYPE_U.equalsIgnoreCase(tempTollSystemType) || IctxConstant.TOLL_SYSTEM_TYPE_E.equalsIgnoreCase(tempTollSystemType))) {
			return StringUtils.leftPad("*", IctxConstant.ETC_ENTRY_DATE_TIME_LENGTH, "*");
		}
		if (awayTransaction.getEntryLaneId() != null && awayTransaction.getEntryLaneId() != 0) {
			LocalDateTime etcEntryDateTime = awayTransaction.getEntryTimestampLocalDateTime() != null
					? awayTransaction.getEntryTimestampLocalDateTime()
					: null;
			if (etcEntryDateTime != null) {

				String timeZoneTemp = timeZoneConv.zonedTxTimeOffset(etcEntryDateTime, Long.valueOf(awayTransaction.getPlazaId())).toString();
				String timezoneVal = "";
				if(timeZoneTemp.length()>25) {
					timezoneVal = timeZoneTemp.substring(0, 19)+timeZoneTemp.substring(23, 29);
				}else {
					timezoneVal = timeZoneTemp;
				}
				logger.info("substring::"+timezoneVal);
				return timezoneVal;
				//return etcDateTime.format(DateTimeFormatter.ofPattern("YYYY-MM-DD'T'hh:mm:ss±HH:MM"));
			
				//return timeZoneConv.zonedTxTimeOffset(etcEntryDateTime, Long.valueOf(awayTransaction.getPlazaId())).toString();
				//return etcEntryDateTime.format(DateTimeFormatter.ofPattern("YYYY-MM-DD'T'hh:mm:ss±HH:MM"));
			}
		}
		return StringUtils.leftPad(" ", IctxConstant.ETC_ENTRY_DATE_TIME_LENGTH, " ");
	}

	/**
	 * Get EtcEntryTime
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcEntryTime(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		String tempTollSystemType = awayTransaction.getTollSystemType() != null ? awayTransaction.getTollSystemType()
				: null;
		if (tempTollSystemType != null && (IctxConstant.TOLL_SYSTEM_TYPE_B.equalsIgnoreCase(tempTollSystemType)
				|| IctxConstant.TOLL_SYSTEM_TYPE_U.equalsIgnoreCase(tempTollSystemType) || IctxConstant.TOLL_SYSTEM_TYPE_E.equalsIgnoreCase(tempTollSystemType))) {
			return StringUtils.leftPad("*", 6, "*");
		}
		if (awayTransaction.getEntryLaneId() != null && awayTransaction.getEntryLaneId() != 0) {
			LocalDateTime etcEntryDateTime = awayTransaction.getEntryTimestampLocalDateTime() != null
					? awayTransaction.getEntryTimestampLocalDateTime()
					: null;
			if (etcEntryDateTime != null) {
				return localTimeUtility.getTimeHHMMSS(etcEntryDateTime);
			}

		}
		return StringUtils.leftPad(" ", 6, " ");
	}

	/**
	 * Get EtcEntryPlaza
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcEntryPlaza(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		String tempTollSystemType = awayTransaction.getTollSystemType() != null ? awayTransaction.getTollSystemType()
				: null;
		if (tempTollSystemType != null && (IctxConstant.TOLL_SYSTEM_TYPE_B.equalsIgnoreCase(tempTollSystemType)
				|| IctxConstant.TOLL_SYSTEM_TYPE_U.equalsIgnoreCase(tempTollSystemType) || IctxConstant.TOLL_SYSTEM_TYPE_E.equalsIgnoreCase(tempTollSystemType))) {
			return StringUtils.leftPad("*", IctxConstant.ETC_ENTRY_PLAZA_LENGTH, "*");
		}
		if (awayTransaction.getEntryLaneId() != null && awayTransaction.getEntryLaneId() != 0) {
			return setEntryPlaza(awayTransaction, messageConversionDto);
		}

		return StringUtils.leftPad(" ", IctxConstant.ETC_ENTRY_PLAZA_LENGTH, " ");
	}

	/**
	 * Set EntryPlaza
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String setEntryPlaza(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		Integer tempEtcEntryPlaza = awayTransaction.getEntryPlazaId() != null ? awayTransaction.getEntryPlazaId()
				: null;
		if (tempEtcEntryPlaza != null) {
			Plaza tempPlaza = staticDataLoad.getPlazaById(tempEtcEntryPlaza);
			String externPlazaId = tempPlaza != null ? tempPlaza.getExternPlazaId() : null;
			if (externPlazaId != null) {
				if (externPlazaId.length() > IctxConstant.ETC_ENTRY_PLAZA_LENGTH) {
					messageConversionDto.setIsValidLengthTx(false);
					messageConversionDto.addErrorMsg("Length of Etc entry plaza is more than expected length");
				}
				return StringUtils.rightPad(externPlazaId, IctxConstant.ETC_ENTRY_PLAZA_LENGTH, " ");
			}
		}
		return StringUtils.leftPad(" ", IctxConstant.ETC_ENTRY_PLAZA_LENGTH, " ");
	}

	/**
	 * Get EtcEntryLane
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcEntryLane(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		String tempTollSystemType = awayTransaction.getTollSystemType() != null ? awayTransaction.getTollSystemType()
				: null;
		if (tempTollSystemType != null && (IctxConstant.TOLL_SYSTEM_TYPE_B.equalsIgnoreCase(tempTollSystemType)
				|| IctxConstant.TOLL_SYSTEM_TYPE_U.equalsIgnoreCase(tempTollSystemType) ||IctxConstant.TOLL_SYSTEM_TYPE_E.equalsIgnoreCase(tempTollSystemType))) {
			return StringUtils.leftPad("*", IctxConstant.ETC_ENTRY_LANE_LENGTH, "*");
		}
		if (awayTransaction.getEntryLaneId() != null && awayTransaction.getEntryLaneId() != 0) {
			return setEtcEntryLane(awayTransaction, messageConversionDto);
		}
		return StringUtils.leftPad(" ", IctxConstant.ETC_ENTRY_LANE_LENGTH, " ");
	}

	/**
	 *Set Etc Entry lane
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String setEtcEntryLane(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		Integer tempEtcEntryLane = awayTransaction.getEntryLaneId() != null ? awayTransaction.getEntryLaneId() : null;
		if (tempEtcEntryLane != null) {
			Lane tempLane = staticDataLoad.getLaneById(tempEtcEntryLane);
			String externLaneId = tempLane != null ? tempLane.getExternLaneId() : null;
			if (externLaneId != null) {
				if (externLaneId.length() > IctxConstant.ETC_ENTRY_LANE_LENGTH) {
					messageConversionDto.setIsValidLengthTx(false);
					messageConversionDto.addErrorMsg("Length of Etc entry lane is more than expected length");
				}
				return StringUtils.rightPad(externLaneId, IctxConstant.ETC_ENTRY_LANE_LENGTH, " ");
			}
		}
		return StringUtils.leftPad(" ", IctxConstant.ETC_ENTRY_LANE_LENGTH, " ");
	}

	/**
	 * Get EtcTagAgency
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcTagAgency(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		String tempEtcTagAgency = awayTransaction.getDeviceNo() != null ? awayTransaction.getDeviceNo() : null;
		String tempEtcTagAgencyVal = "";
		//if 10 else 14 logic
		if(tempEtcTagAgency != null ) {
			if (tempEtcTagAgency.length() > 11) {
				tempEtcTagAgencyVal =  tempEtcTagAgency.substring(0, 4);
			}else {
				tempEtcTagAgencyVal =  tempEtcTagAgency.substring(0, 3);
			}
		}
		return StringUtils.leftPad(tempEtcTagAgencyVal, IctxConstant.ETC_TAG_AGENCY_LENGTH, "0");
	}

	/**
	 * Get EtcTagSerialNum
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcTagSerialNum(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		String tempEtcTagSerialNum = awayTransaction.getDeviceNo() != null ? awayTransaction.getDeviceNo() : null;
		String tempEtcTagSerialNumVal = "";
		if(tempEtcTagSerialNum != null) {
			if (tempEtcTagSerialNum.length() > 11) {
				tempEtcTagSerialNumVal = tempEtcTagSerialNum.substring(4, 14);
			}else {
				tempEtcTagSerialNumVal = tempEtcTagSerialNum.substring(3, 11);
			}
		}
		return StringUtils.leftPad(tempEtcTagSerialNumVal, IctxConstant.ETC_TAG_SERIAL_NO_LENGTH, "0");
	}

	/**
	 * Get EtcReadPerformance
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcReadPerformance(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		String tempEtcReadPerf = awayTransaction.getDeviceReadCount() != null
				&& awayTransaction.getDeviceReadCount() != 0 ? String.valueOf(awayTransaction.getDeviceReadCount())
						: "**";
		if (tempEtcReadPerf.length() > IctxConstant.ETC_READ_PERF_LENGTH) {
			messageConversionDto.setIsValidLengthTx(false);
			messageConversionDto.addErrorMsg("Length of Etc read performance is more than expected length");
		}
		return StringUtils.leftPad(tempEtcReadPerf, IctxConstant.ETC_READ_PERF_LENGTH, "0");
	}

	/**
	 * Get EtcWritePerformance
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcWritePerformance(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		String tempEtcWritePerf = awayTransaction.getDeviceWriteCount() != null
				&& awayTransaction.getDeviceWriteCount() != 0 ? String.valueOf(awayTransaction.getDeviceWriteCount())
						: "**";
		if (tempEtcWritePerf.length() > IctxConstant.ETC_WRITE_PERF_LENGTH) {
			messageConversionDto.setIsValidLengthTx(false);
			messageConversionDto.addErrorMsg("Length of Etc write performance is more than expected length");
		}
		return StringUtils.leftPad(tempEtcWritePerf, IctxConstant.ETC_WRITE_PERF_LENGTH, "0");
	}

	/**
	 * Get EtcTagPgmStatus
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcTagPgmStatus(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		String tempEtcTagPgmStatus = awayTransaction.getDeviceProgramStatus() != null
				? awayTransaction.getDeviceProgramStatus()
				: null;
		if (tempEtcTagPgmStatus != null) {
			if ("0".equalsIgnoreCase(tempEtcTagPgmStatus) || "1".equalsIgnoreCase(tempEtcTagPgmStatus)) {
				return "S";
			} else if ("4".equalsIgnoreCase(tempEtcTagPgmStatus) || "6".equalsIgnoreCase(tempEtcTagPgmStatus)) {
				return "F";
			}
		}
		return "*";
	}

	/**
	 * Get EtcLaneMode
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcLaneMode(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		Integer tempEtclaneMode = awayTransaction.getLaneMode() != null ? awayTransaction.getLaneMode() : null;
		if (tempEtclaneMode != null) {
			switch (tempEtclaneMode) {
			case 1:
				return IctxConstant.LANE_MODE_E;
			case 2:
				return IctxConstant.LANE_MODE_A;
			case 3:
				return IctxConstant.LANE_MODE_M;
			case 8:
				return IctxConstant.LANE_MODE_C;
			default:
				return IctxConstant.LANE_MODE_E; 
			}
		}
		return IctxConstant.LANE_MODE_E;
		//return StringUtils.leftPad(" ", 1, " ");
	}

	/**
	 * Get EtcValidationStatus
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcValidationStatus(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		String tempLaneDeviceStatus = awayTransaction.getLaneDeviceStatus() != null
				&& awayTransaction.getLaneDeviceStatus() != 0 ? String.valueOf(awayTransaction.getLaneDeviceStatus())
						: "*";
		if (tempLaneDeviceStatus.length() > IctxConstant.ETC_VALIDATION_STATUS_LENGTH) {
			messageConversionDto.setIsValidLengthTx(false);
			messageConversionDto.addErrorMsg("Length of Etc validation status is more than expected length");
		}
		return StringUtils.leftPad(tempLaneDeviceStatus, IctxConstant.ETC_VALIDATION_STATUS_LENGTH, " ");
	}

	/**
	 * Get EtcLicState
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcLicState(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		if (!StringUtils.isBlank(awayTransaction.getPlateState())) {
			if (awayTransaction.getPlateState().length() > IctxConstant.ETC_LIC_STATE_LENGTH) {
				messageConversionDto.setIsValidLengthTx(false);
				messageConversionDto.addErrorMsg("Length of Etc Lic state is more than expected length");
			}
			return StringUtils.rightPad(awayTransaction.getPlateState(), IctxConstant.ETC_LIC_STATE_LENGTH, " ");
		} else {
			return StringUtils.leftPad("*", IctxConstant.ETC_LIC_STATE_LENGTH, "*");
		}

	}
	

	/**
	 * Get EtcLicNumber
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcLicNumber(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		if (!StringUtils.isBlank(awayTransaction.getPlateNumber())) {
			if (awayTransaction.getPlateNumber().length() > IctxConstant.ETC_LIC_NUMBER_LENGTH) {
				messageConversionDto.setIsValidLengthTx(false);
				messageConversionDto.addErrorMsg("Length of Etc Lic number is more than expected length");
			}
			return StringUtils.rightPad(awayTransaction.getPlateNumber(), IctxConstant.ETC_LIC_NUMBER_LENGTH, " ");
		} else {
			return StringUtils.leftPad("*", IctxConstant.ETC_LIC_NUMBER_LENGTH, "*");
		}
	}

	/**
	 * Get EtcLicState
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcLicType(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		//code type need to fetch ;
		//awayTransaction.getPlateType()
		if(awayTransaction.getPlateType()!=null) {
			String licType = staticDataLoad.getPlateType(awayTransaction.getPlateType());
			//"LIC_TYPE_"+awayTransaction.getPlateState();
			if(!StringUtils.isBlank(licType)) {
				if (licType.length() > IctxConstant.ETC_LIC_TYPE_LENGTH) {
					messageConversionDto.setIsValidLengthTx(false);
					messageConversionDto.addErrorMsg("Length of Etc Lic type is more than expected length");
				}
				return StringUtils.rightPad(licType, IctxConstant.ETC_LIC_TYPE_LENGTH, " ");
			}else {
				return StringUtils.leftPad("*", IctxConstant.ETC_LIC_TYPE_LENGTH, "*");
			}
		}
		return StringUtils.leftPad("*", IctxConstant.ETC_LIC_TYPE_LENGTH, "*");

	}
	
	/**
	 * Get EtcClassCharged
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcClassCharged(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		Integer actualClass = awayTransaction.getActualClass() != null && awayTransaction.getActualClass() != 0
				? awayTransaction.getActualClass()
				: 0;
		VehicleClass vehClass = staticDataLoad.getVehClassByAgencyIdandActualClass(awayTransaction.getPlazaAgencyId(),
				actualClass);
		if (vehClass != null && vehClass.getExternAgencyClass() != null
				&& vehClass.getExternAgencyClass().length() > IctxConstant.ETC_CLASS_CHARGED_LENGTH) {
			messageConversionDto.setIsValidLengthTx(false);
			messageConversionDto.addErrorMsg("Length of Etc class charged is more than expected length");
		}

		return StringUtils.rightPad(vehClass != null ? String.valueOf(vehClass.getExternAgencyClass()) : " ", IctxConstant.ETC_CLASS_CHARGED_LENGTH, " ");

	}

	/**
	 * Get EtcActualAxles
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcActualAxles(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		Integer tempActualAxles = awayTransaction.getActualAxles() != null && awayTransaction.getActualAxles() != 0
				? awayTransaction.getActualAxles()
				: 0;
		if (String.valueOf(tempActualAxles).length() > IctxConstant.ETC_ACTUAL_AXLES_LENGTH) {
			messageConversionDto.setIsValidLengthTx(false);
			messageConversionDto.addErrorMsg("Length of Etc actual axles is more than expected length");
		}
		return StringUtils.rightPad(String.valueOf(tempActualAxles), IctxConstant.ETC_ACTUAL_AXLES_LENGTH, " ");
	}

	/**
	 * Get EtcExitSpeed
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcExitSpeed(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		Integer tempVehicleSpeed = awayTransaction.getVehicleSpeed() != null && awayTransaction.getVehicleSpeed() != 0
				? awayTransaction.getVehicleSpeed()
				: 0;
		if (String.valueOf(tempVehicleSpeed).length() > IctxConstant.ETC_EXIT_SPEED_LENGTH) {
			messageConversionDto.setIsValidLengthTx(false);
			messageConversionDto.addErrorMsg("Length of Etc exit speed is more than expected length");
		}
		return StringUtils.leftPad(String.valueOf(tempVehicleSpeed), IctxConstant.ETC_EXIT_SPEED_LENGTH, "0");
	}

	/**
	 * Get EtcOverSpeed
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */ 
	private String getEtcOverSpeed(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		String tempSpeedViolFlag = awayTransaction.getSpeedViolFlag() != null ? awayTransaction.getSpeedViolFlag()
				: null;
		if (tempSpeedViolFlag != null) {
			String overSpeed = "T".equalsIgnoreCase(tempSpeedViolFlag) || "Y".equalsIgnoreCase(tempSpeedViolFlag) ? "Y"
					: "N";
			return StringUtils.leftPad(overSpeed, IctxConstant.ETC_OVER_SPEED_LENGTH, " ");
		}
		return StringUtils.leftPad("N", IctxConstant.ETC_OVER_SPEED_LENGTH, " ");
	}

	/**
	 * Get EtcExitDate
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcExitDate(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		LocalDateTime etcDateTime = awayTransaction.getTxTimestampLocalDateTime() != null ? awayTransaction.getTxTimestampLocalDateTime() : null;
		if (etcDateTime != null) {
			return localDateTimeUtility.getDateYYYYMMDD(etcDateTime);
		}

		return StringUtils.leftPad(" ", 8, " ");
	}
	
	/**
	 * Get EtcExitDate
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcExitDateTime(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		LocalDateTime etcDateTime = awayTransaction.getTxTimestampLocalDateTime() != null ? awayTransaction.getTxTimestampLocalDateTime() : null;
		if (etcDateTime != null) {
			String timeZoneTemp = timeZoneConv.zonedTxTimeOffset(etcDateTime, Long.valueOf(awayTransaction.getPlazaId())).toString();
			String timezoneVal = "";
			if(timeZoneTemp.length()>25) {
				timezoneVal = timeZoneTemp.substring(0, 19)+timeZoneTemp.substring(23, 29);
			}else {
				timezoneVal = timeZoneTemp;
			}
			logger.info("substring::"+timezoneVal);
			return timezoneVal;
			//return etcDateTime.format(DateTimeFormatter.ofPattern("YYYY-MM-DD'T'hh:mm:ss±HH:MM"));
		}

		return StringUtils.leftPad(" ", IctxConstant.ETC_EXIT_DATE_TIME_LENGTH, " ");
	}

	/**
	 * Get EtcExitTime
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcExitTime(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		LocalDateTime etcDateTime = awayTransaction.getTxTimestampLocalDateTime() != null ? awayTransaction.getTxTimestampLocalDateTime() : null;
		if (etcDateTime != null) {
			return localTimeUtility.getTimeHHMMSS(etcDateTime);
		}
		return StringUtils.leftPad(" ", 6, " ");
	}

	/**
	 * Get EtcExitPlaza
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcExitPlaza(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		Integer tempEtcExitPlaza = awayTransaction.getPlazaId() != null ? awayTransaction.getPlazaId() : null;
		if (tempEtcExitPlaza != null) {
			Plaza tempPlaza = staticDataLoad.getPlazaById(tempEtcExitPlaza);
			String externPlazaId = tempPlaza != null ? tempPlaza.getExternPlazaId() : null;
			if (externPlazaId != null) {
				if (externPlazaId.length() > IctxConstant.ETC_EXIT_PLAZA_LENGTH) {
					messageConversionDto.setIsValidLengthTx(false);
					messageConversionDto.addErrorMsg("Length of Etc exit plaza is more than expected length");
				}
				return StringUtils.rightPad(externPlazaId, IctxConstant.ETC_EXIT_PLAZA_LENGTH, " ");
			}
		}
		return StringUtils.leftPad(" ", IctxConstant.ETC_EXIT_PLAZA_LENGTH, " ");
	}

	/**
	 * Get EtcExitLane
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcExitLane(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		Integer tempEtcExitLane = awayTransaction.getLaneId() != null ? awayTransaction.getLaneId() : null;
		if (tempEtcExitLane != null) {
			Lane tempLane = staticDataLoad.getLaneById(tempEtcExitLane);
			String externLaneId = tempLane != null ? tempLane.getExternLaneId() : null;
			if (externLaneId != null) {
				if (externLaneId.length() > IctxConstant.ETC_EXIT_LANE_LENGTH) {
					messageConversionDto.setIsValidLengthTx(false);
					messageConversionDto.addErrorMsg("Length of Etc exit lane is more than expected length");
				}
				return StringUtils.rightPad(externLaneId, IctxConstant.ETC_EXIT_LANE_LENGTH, " ");
			}
		}

		return StringUtils.leftPad(" ", IctxConstant.ETC_EXIT_LANE_LENGTH, " ");
	}

	/**
	 * Get EtcDebitCredit
	 * 
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcDebitCredit(MessageConversionDto messageConversionDto) {
		return StringUtils.leftPad("+", IctxConstant.ETC_DEBIT_CREDIT_LENGTH, " ");
	}
	
	/**
	 * Get EtcDebitCreditCorrection
	 * 
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcDebitCreditCorrection(AwayTransaction awayTransaction,MessageConversionDto messageConversionDto) {
		
		if(awayTransaction.getPostedFareAmount()>0) {
			return StringUtils.leftPad("+", IctxConstant.ETC_DEBIT_CREDIT_LENGTH, " ");
		}else {
			return StringUtils.leftPad("-", IctxConstant.ETC_DEBIT_CREDIT_LENGTH, " ");
		}
	}

	/**
	 * Get EtcTollAmount
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcTollAmount(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		//Double etcTollAmount = tollCalculationService.calculateToll(awayTransaction); 
		//conditions over here
		logger.info("awayTransaction.getPlazaAgencyId():{}",awayTransaction.getPlazaAgencyId());
		Double etcTollAmount = null;
		
		/*
		join t_agency to  find type and as per ronnies mail
		TBM Full fare

		----------plaza agency

		||||Account agency
		Need to check for extra axel
		
		EZP = E-ZPass Discounted Rate  (Revenue_type_id = 1, T_TOLL_SCHEDULE.discounted_amount)
		TBM = Tolls by Mail Rate               (Revenue_type_id = 60, plan_type_id = 217, T_TOLL_SCHEDULE.discounted_amount)
		EZN = E-ZPass IAG Rate (NYSTA) – T_TOLL_SCHEDULE.FULL_FARE of Plan type 1 (Revenue_type_id 1)
		EZM = E-ZPass Mid-Tier Rate (MTA)  (Revenue_type_id = 60, T_TOLL_SCHEDULE.discounted_amount)
		*/
		
		String plazaAgencyShortName = staticDataLoad.getAgencyById(Long.valueOf(awayTransaction.getPlazaAgencyId())).getAgencyShortName();
		//String accAgencyShortName = staticDataLoad.getAgencyById(Long.valueOf(awayTransaction.getAccountAgencyId())).getAgencyShortName();
		
		logger.info("plazaAgencyShortName {}",plazaAgencyShortName);
		etcTollAmount = awayTransaction.getPostedFareAmount();//Default value before calculation 
		TollCalculationResponseDto tollCalculationResponseDto = new TollCalculationResponseDto();
		if(plazaAgencyShortName.equalsIgnoreCase("PA")) {
			String agencyShortName = staticDataLoad.getAgencyById(Long.valueOf(awayTransaction.getAccountAgencyId())).getAgencyShortName();
			
			if((awayTransaction.getPlateState()!= null && awayTransaction.getPlateState().equalsIgnoreCase("NJ"))||(agencyShortName.equalsIgnoreCase(IctxConstant.NJ_AGENCY))) {
				//EZP FOR NJ Plate and TAG Transactions
				logger.info("NJ Transaction");
				//etcTollAmount = awayTransaction.getDiscountedAmount();
				etcTollAmount = transactionDetailDao.getTollAmount(awayTransaction.getLaneTxId());
				//new query to fetch data from t_account_toll above
				awayTransaction.setFullFareAmount(etcTollAmount);
			
			}else {
				logger.info("Non NJ Transaction");
				//etcTollAmount =  awayTransaction.getPostedFareAmount();
				etcTollAmount = awayTransaction.getVideoFareAmount();
			}
		}else if(plazaAgencyShortName.equalsIgnoreCase("NY")) {
			if(awayTransaction.getPlateNumber()!=null && !awayTransaction.getPlateNumber().isBlank() &&
					!awayTransaction.getPlateNumber().equalsIgnoreCase("**********")) {
				tollCalculationResponseDto = getTollCalculationForPlate(awayTransaction, IctxConstant.PLAN_TYPE_217, IctxConstant.REVENUE_TYPE_60);
				if(tollCalculationResponseDto!=null) {
					//TBM  TAG Transaction
					etcTollAmount = tollCalculationResponseDto.getFullFare();
					Double extraAxelCharge = 0.0;
					if(awayTransaction.getActualExtraAxles()!=null && awayTransaction.getActualExtraAxles()>0) {
						extraAxelCharge = awayTransaction.getActualExtraAxles()*tollCalculationResponseDto.getExtraAxleChargeCash();
						etcTollAmount = etcTollAmount+extraAxelCharge;
					}
				}
			}else {
				tollCalculationResponseDto = getTollCalculationForPlate(awayTransaction, IctxConstant.DEFAULT_PLAN_TYPE, IctxConstant.REVENUE_TYPE_1);
				if(tollCalculationResponseDto!=null) {
					//EZN  Plate Transaction
					etcTollAmount = tollCalculationResponseDto.getFullFare();
					Double extraAxelCharge = 0.0;
					if(awayTransaction.getActualExtraAxles()!=null && awayTransaction.getActualExtraAxles()>0) {
						extraAxelCharge = awayTransaction.getActualExtraAxles()*tollCalculationResponseDto.getExtraAxleChargeCash();
						etcTollAmount = etcTollAmount+extraAxelCharge;
					}
				}

			}
		}else if(plazaAgencyShortName.equalsIgnoreCase("NB")) {
			if(awayTransaction.getPlateNumber()!=null && !awayTransaction.getPlateNumber().isBlank() &&
					!awayTransaction.getPlateNumber().equalsIgnoreCase("**********")) {

				tollCalculationResponseDto = getTollCalculationForPlate(awayTransaction, IctxConstant.PLAN_TYPE_217, IctxConstant.REVENUE_TYPE_60);
				if(tollCalculationResponseDto!=null) {
					//TBM Plate
					etcTollAmount = tollCalculationResponseDto.getFullFare();
					Double extraAxelCharge = 0.0;
					if(awayTransaction.getActualExtraAxles()!=null && awayTransaction.getActualExtraAxles()>0) {
						extraAxelCharge = awayTransaction.getActualExtraAxles()*tollCalculationResponseDto.getExtraAxleChargeCash();
						etcTollAmount = etcTollAmount+extraAxelCharge;
					}
				}

			}else {
				tollCalculationResponseDto = getTollCalculationForPlate(awayTransaction, IctxConstant.DEFAULT_PLAN_TYPE, IctxConstant.REVENUE_TYPE_1);
				if(tollCalculationResponseDto!=null) {
					//EZP
					etcTollAmount = tollCalculationResponseDto.getDiscountFare();
					Double extraAxelCharge = 0.0;
					if(awayTransaction.getActualExtraAxles()!=null && awayTransaction.getActualExtraAxles()>0) {
						extraAxelCharge = awayTransaction.getActualExtraAxles()*tollCalculationResponseDto.getExtraAxleChargeCash();
						etcTollAmount = etcTollAmount+extraAxelCharge;
					}
				}
			}
		}else if(plazaAgencyShortName.equalsIgnoreCase("MdTA")) {
			tollCalculationResponseDto = getTollCalculationForPlate(awayTransaction, IctxConstant.PLAN_TYPE_217, IctxConstant.REVENUE_TYPE_60);
			if(tollCalculationResponseDto!=null) {
			//TBM
				etcTollAmount = tollCalculationResponseDto.getFullFare();
				Double extraAxelCharge = 0.0;
				if(awayTransaction.getActualExtraAxles()!=null && awayTransaction.getActualExtraAxles()>0) {
					extraAxelCharge = awayTransaction.getActualExtraAxles()*tollCalculationResponseDto.getExtraAxleChargeCash();
					etcTollAmount = etcTollAmount+extraAxelCharge;
				}
			}
		
		}else {
			etcTollAmount =  awayTransaction.getPostedFareAmount();
		}

		/*
		if(awayTransaction.getPlateNumber()!=null && !awayTransaction.getPlateNumber().isBlank() &&
				!awayTransaction.getPlateNumber().equalsIgnoreCase("**********")) {
			//NJ => discounted amt toll calculation api toll revenue type = 1 take discounted amt
			TollCalculationResponseDto tollCalculationResponseDto = new TollCalculationResponseDto();
			tollCalculationResponseDto = getTollCalculationForPlate(awayTransaction, IctxConstant.DEFAULT_PLAN_TYPE, IctxConstant.REVENUE_TYPE_1);
			

			if(tollCalculationResponseDto!=null) {
			if(awayTransaction.getPlateState().equalsIgnoreCase("NJ")) {
				
				etcTollAmount = tollCalculationResponseDto.getDiscountFare();
			}
			else{
				etcTollAmount = tollCalculationResponseDto.getFullFare();
			}
			}else {
				logger.info("Toll Calculation failed");
				etcTollAmount =  awayTransaction.getPostedFareAmount();
			}
			//etcTollAmount = awayTransaction.getVideoFareAmount();
		}else {
			etcTollAmount =  awayTransaction.getPostedFareAmount();
		}
		*/
		if (etcTollAmount != null) {
			logger.info("etcTollAmount is not null : {}",etcTollAmount);
			awayTransactionDao.updatePostedFareAmount(awayTransaction,etcTollAmount);
			String tempAmount = String.valueOf((int)(etcTollAmount*100));
			return StringUtils.leftPad(tempAmount.replace(".", ""), IctxConstant.ETC_TOLL_AMOUNT_LENGTH, "0"); 
		}
		return StringUtils.leftPad("0", IctxConstant.ETC_TOLL_AMOUNT_LENGTH, "0");
	}

	private TollCalculationResponseDto getTollCalculationForPlate(AwayTransaction awayTransaction, int planType, int tollRevenueType) {

		TollCalculationDto tollCalculationDto = new TollCalculationDto();

		tollCalculationDto.setEntryPlazaId(awayTransaction.getEntryPlazaId()!= null ? awayTransaction.getEntryPlazaId() : 0);
		tollCalculationDto.setExitPlazaId(awayTransaction.getPlazaId());
		//tollCalculationDto.setPlanType(violTxDto1.getPlanTypeId());
		tollCalculationDto.setPlanType(planType);
		tollCalculationDto.setTollRevenueType(tollRevenueType);
		tollCalculationDto.setTxDate(awayTransaction.getTxDate().toString());
		tollCalculationDto.setTxTimestamp(awayTransaction.getTxTimestamp().toString().replace("T", " "));
		tollCalculationDto.setAgencyId(awayTransaction.getPlazaAgencyId());
		tollCalculationDto.setActualClass(awayTransaction.getActualClass());
		tollCalculationDto.setAccountType(awayTransaction.getAccountType()!= null ? awayTransaction.getAccountType() : 1);
		tollCalculationDto.setTollSystemType(awayTransaction.getTollSystemType());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		logger.info("Setting data for TollCalculation for Plate : {}",tollCalculationDto);
		Gson gson = new Gson();
		try {
			HttpEntity<String> entity = new HttpEntity<String>(gson.toJson(tollCalculationDto), headers);

			ResponseEntity<String> result = restTemplate.postForEntity(configVariable.getTollCalculationUri(), entity,String.class);
			logger.info("Toll Calculation result for plate: {}", result);
			if (result.getStatusCodeValue() == 200) {

				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				logger.info("Got tollcalculation response for plate : {}", jsonObject);

				// JsonElement element = jsonObject.get("amounts");
				return gson.fromJson(jsonObject.getAsJsonObject("amounts"), TollCalculationResponseDto.class);

			} else {
				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				logger.info("Got tollcalculation response for Plate #### : {}", jsonObject);
			}

		} catch (Exception e) {
			logger.info("Error: Exception {} in Toll Calculation API for Plate",e.getMessage());
		}
		return null;
	}
	
}
