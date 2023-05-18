package com.conduent.tpms.intx.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.intx.constants.IntxConstant;
import com.conduent.tpms.intx.dao.AwayTransactionDao;
import com.conduent.tpms.intx.dto.MessageConversionDto;
import com.conduent.tpms.intx.model.AwayTransaction;
import com.conduent.tpms.intx.service.IntxConversionService;

/**
 * INTX message conversion service
 * 
 * @author deepeshb
 *
 */
@Service
public class IntxConversionServiceImpl implements IntxConversionService {

//	@Autowired
//	private StaticDataLoad staticDataLoad;
//
//	@Autowired
//	private LocalDateTimeUtility localDateTimeUtility;
//
//	@Autowired
//	private LocalTimeUtility localTimeUtility;

	@Autowired
	private AwayTransactionDao awayTransactionDao;

	private static final Logger logger = LoggerFactory.getLogger(IntxConversionServiceImpl.class);

	/**
	 * Get Intx message info
	 * 
	 * @param awayTransaction
	 * @return MessageConversionDto
	 */
	public MessageConversionDto getIntxTransaction(AwayTransaction awayTransaction,String fileType) {
//		logger.info("Away Transaction in Intx conversion service {}", awayTransaction);
		logger.info("Away Transaction in Intx conversion service");
		MessageConversionDto messageConversionDto = new MessageConversionDto();
		StringBuilder sb = new StringBuilder();
//		String tollAmount;
//		String creditDebit;
		
//		if(fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_INTX)) {
//			creditDebit = getEtcDebitCredit(awayTransaction, messageConversionDto);
//		} else {
//			sb.append(getCorrectionReason(awayTransaction, messageConversionDto));
//			creditDebit = getEtcDebitCreditCorrection(awayTransaction, messageConversionDto);	
//		}
		
		if(fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_ITXN)) {
			sb.append(getCorrectionReason(awayTransaction, messageConversionDto));
		}
		
		sb.append(getEtcTrxSerialNum(awayTransaction, messageConversionDto))
				.append(getEtcRevenueDate(awayTransaction, messageConversionDto))
				.append(getEtcFacAgency(awayTransaction, messageConversionDto))
				.append(getEtcTrxType(awayTransaction, messageConversionDto))
				.append(getEtcEntryDate(awayTransaction, messageConversionDto))
				.append(getEtcEntryTime(awayTransaction, messageConversionDto))
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
				.append(getEtcClassCharged(awayTransaction, messageConversionDto))
				.append(getEtcActualAxles(awayTransaction, messageConversionDto))
				.append(getEtcExitSpeed(awayTransaction, messageConversionDto))
				.append(getEtcOverSpeed(awayTransaction, messageConversionDto))
				.append(getEtcExitDate(awayTransaction, messageConversionDto))
				.append(getEtcExitTime(awayTransaction, messageConversionDto))
				.append(getEtcExitPlaza(awayTransaction, messageConversionDto))
				.append(getEtcExitLane(awayTransaction, messageConversionDto))
				.append(getEtcDebitCredit(awayTransaction, messageConversionDto))
				.append(getEtcAmountDue(awayTransaction, messageConversionDto))
				.append("\n");

		if(fileType.equalsIgnoreCase(IntxConstant.FILE_EXTENSION_INTX)) {
			/// Update Full Fare AMount in T_AGENCY_TX_PENDING
			awayTransactionDao.updateExpectedRevenueAmtAndRevenueDate(awayTransaction);
		}
		
//		logger.info("After update away Transaction {} :: ", awayTransaction);
		messageConversionDto.setTxMessage(sb.toString());
		if (!messageConversionDto.getIsValidLengthTx()) {
			logger.error("Bad Away Transaction with laneTxId:{} and ErrorMsg: {}", awayTransaction.getLaneTxId(),
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
		if (tempEtcTrxSerialNum != null && tempEtcTrxSerialNum.length() <= 12) {
			return StringUtils.leftPad(tempEtcTrxSerialNum, 12, "0");
		} else if (tempEtcTrxSerialNum != null && tempEtcTrxSerialNum.length() > 12) {
			messageConversionDto.setIsValidLengthTx(false);
			messageConversionDto.addErrorMsg("Length of Etc tx serial num is more than expected length");
			return StringUtils.leftPad(tempEtcTrxSerialNum, 12, "0");
		} else {
			return StringUtils.leftPad(" ", 12, " ");
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
		String tempCorrectionReason = awayTransaction.getEtcCorrReasonId() != null
				? awayTransaction.getEtcCorrReasonId()
				: null;
		
		// need to fetch these lengths from db
		if (tempCorrectionReason != null && tempCorrectionReason.length() == 2) {
			// return StringUtils.leftPad(tempCorrectionReason, 2, "0");
			return tempCorrectionReason;
		} else if (tempCorrectionReason != null && tempCorrectionReason.length() > 2) {
			messageConversionDto.setIsValidLengthTx(false);
			messageConversionDto.addErrorMsg("Length of CorrectionReason is more than expected length");
			return StringUtils.leftPad(tempCorrectionReason, 2, "0");
		} else {
			return StringUtils.leftPad(" ", 2, " ");
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
//		RevenueDateStatisticsDto tempRevenueDateStatisticsDto = staticDataLoad
//				.getRevenueStatusByPlaza(awayTransaction.getPlazaId());
//
//		int plazaRevenueTimeSec = tempRevenueDateStatisticsDto.getRevenueSecondOfDay();
//		if (null == awayTransaction.getEtcTxTimestamp()) {
//			return StringUtils.leftPad(" ", 8, " ");
//		} else if (0 == plazaRevenueTimeSec) {
//			awayTransaction.setRevenueDate(awayTransaction.getEtcTxTimestamp().toLocalDate());
//			return localDateTimeUtility.getDateYYYYMMDD(awayTransaction.getEtcTxTimestamp());
//
//		} else if (plazaRevenueTimeSec > 43200) {
//			if (awayTransaction.getEtcTxTimestamp().toLocalTime().toSecondOfDay() > plazaRevenueTimeSec) {
//				awayTransaction.setRevenueDate(awayTransaction.getEtcTxTimestamp().plusDays(1).toLocalDate());
//				return localDateTimeUtility.getDateYYYYMMDD(awayTransaction.getEtcTxTimestamp().plusDays(1));
//			} else {
//				awayTransaction.setRevenueDate(awayTransaction.getEtcTxTimestamp().toLocalDate());
//				return localDateTimeUtility.getDateYYYYMMDD(awayTransaction.getEtcTxTimestamp());
//			}
//
//		} else if (plazaRevenueTimeSec < 43200) {
//			if (awayTransaction.getEtcTxTimestamp().toLocalTime().toSecondOfDay() > plazaRevenueTimeSec) {
//				awayTransaction.setRevenueDate(awayTransaction.getEtcTxTimestamp().toLocalDate());
//				return localDateTimeUtility.getDateYYYYMMDD(awayTransaction.getEtcTxTimestamp());
//			} else {
//				awayTransaction.setRevenueDate(awayTransaction.getEtcTxTimestamp().minusDays(1).toLocalDate());
//				return localDateTimeUtility.getDateYYYYMMDD(awayTransaction.getEtcTxTimestamp().minusDays(1));
//			}
//		}
		
		String tempEtcRevenueDate = awayTransaction.getEtcEntryDate();
		
		if (tempEtcRevenueDate != null && tempEtcRevenueDate.length() == 8) {
			return tempEtcRevenueDate;
		}

		return StringUtils.leftPad(" ", 8, " ");
	}

	/**
	 * Get EtcFacAgency
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcFacAgency(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
//		Integer tempEtcFacAgency = awayTransaction.getPlazaAgencyId() != null ? awayTransaction.getPlazaAgencyId()
//				: null;
//		Agency tempAgency = null;
//		if (tempEtcFacAgency != null) {
//			tempAgency = staticDataLoad.getFacHomeAgencyById(Long.valueOf(tempEtcFacAgency));
//		}
//		if (tempAgency != null) {
//			if (tempAgency.getDevicePrefix().length() > 3) {
//				messageConversionDto.setIsValidLengthTx(false);
//				messageConversionDto.addErrorMsg("Length of Etc agency fac is more than expected length");
//			}
//			return StringUtils.leftPad(String.valueOf(tempAgency.getDevicePrefix()), 3, "0");
//		}
		
		Integer tempEtcFacAgency = awayTransaction.getEtcFacAgency();
		
		if (tempEtcFacAgency != null) {
			return StringUtils.leftPad(String.valueOf(tempEtcFacAgency), 3, "0");
		}
		
		return StringUtils.leftPad(" ", 3, " ");
	}

	/**
	 * Get EtcTrxType
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcTrxType(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
//		String tempTollSystemType = awayTransaction.getTollSystemType() != null ? awayTransaction.getTollSystemType()
//				: null;
//		if (tempTollSystemType != null) {
//			if (tempTollSystemType.length() > 1) {
//				messageConversionDto.setIsValidLengthTx(false);
//				messageConversionDto.addErrorMsg("Length of Etc tx type is more than expected length");
//			}
//			return StringUtils.leftPad(awayTransaction.getTollSystemType(), 1, " ");
//		}
		
		String tempEtcTrxType = awayTransaction.getEtcTrxType();
		
		if (tempEtcTrxType != null && tempEtcTrxType.length() == 1) {
			return StringUtils.leftPad(tempEtcTrxType, 1, " ");
		}
		
		return StringUtils.leftPad(" ", 1, " ");
	}

	/**
	 * Get EtcEntryDate
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcEntryDate(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
//		String tempTollSystemType = awayTransaction.getTollSystemType() != null ? awayTransaction.getTollSystemType()
//				: null;
//		if (tempTollSystemType != null && (IntxConstant.TOLL_SYSTEM_TYPE_B.equalsIgnoreCase(tempTollSystemType)
//				|| IntxConstant.TOLL_SYSTEM_TYPE_U.equalsIgnoreCase(tempTollSystemType) || IntxConstant.TOLL_SYSTEM_TYPE_E.equalsIgnoreCase(tempTollSystemType))) {
//			return StringUtils.leftPad("*", 8, "*");
//		}
//		if (awayTransaction.getEntryLaneId() != null && awayTransaction.getEntryLaneId() != 0) {
//			LocalDateTime etcEntryDateTime = awayTransaction.getEntryTimestamp() != null
//					? awayTransaction.getEntryTimestamp()
//					: null;
//			if (etcEntryDateTime != null) {
//				return localDateTimeUtility.getDateYYYYMMDD(etcEntryDateTime);
//			}
//		}
		
		String tempEtcEntryDate = awayTransaction.getEtcEntryDate();
		
		if (tempEtcEntryDate != null && tempEtcEntryDate.length() == 8) {
			return tempEtcEntryDate;
		}
		
		return StringUtils.leftPad("*", 8, "*");
	}

	/**
	 * Get EtcEntryTime
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcEntryTime(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
//		String tempTollSystemType = awayTransaction.getTollSystemType() != null ? awayTransaction.getTollSystemType()
//				: null;
//		if (tempTollSystemType != null && (IntxConstant.TOLL_SYSTEM_TYPE_B.equalsIgnoreCase(tempTollSystemType)
//				|| IntxConstant.TOLL_SYSTEM_TYPE_U.equalsIgnoreCase(tempTollSystemType) || IntxConstant.TOLL_SYSTEM_TYPE_E.equalsIgnoreCase(tempTollSystemType))) {
//			return StringUtils.leftPad("*", 6, "*");
//		}
//		if (awayTransaction.getEntryLaneId() != null && awayTransaction.getEntryLaneId() != 0) {
//			LocalDateTime etcEntryDateTime = awayTransaction.getEntryTimestamp() != null
//					? awayTransaction.getEntryTimestamp()
//					: null;
//			if (etcEntryDateTime != null) {
//				return localTimeUtility.getTimeHHMMSS(etcEntryDateTime);
//			}
//
//		}
		
		String tempEtcEntryTime = awayTransaction.getEtcEntryTime();
		
		if (tempEtcEntryTime != null && tempEtcEntryTime.length() == 6) {
			return tempEtcEntryTime;
		}
		
		return StringUtils.leftPad("*", 6, "*");
		
	}

	/**
	 * Get EtcEntryPlaza
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcEntryPlaza(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
//		String tempTollSystemType = awayTransaction.getTollSystemType() != null ? awayTransaction.getTollSystemType()
//				: null;
//		if (tempTollSystemType != null && (IntxConstant.TOLL_SYSTEM_TYPE_B.equalsIgnoreCase(tempTollSystemType)
//				|| IntxConstant.TOLL_SYSTEM_TYPE_U.equalsIgnoreCase(tempTollSystemType) || IntxConstant.TOLL_SYSTEM_TYPE_E.equalsIgnoreCase(tempTollSystemType))) {
//			return StringUtils.leftPad("*", 3, "*");
//		}
//		if (awayTransaction.getEntryLaneId() != null && awayTransaction.getEntryLaneId() != 0) {
//			return setEntryPlaza(awayTransaction, messageConversionDto);
//		}
		
		String tempEtcEntryPlaza = awayTransaction.getEtcEntryPlaza();
		
		if (tempEtcEntryPlaza != null && tempEtcEntryPlaza.length() == 3) {
			return tempEtcEntryPlaza;
		}

		return StringUtils.leftPad("*", 3, "*");
	}

//	/**
//	 * Set EntryPlaza
//	 * 
//	 * @param awayTransaction
//	 * @param messageConversionDto
//	 * @return String
//	 */
//	private String setEntryPlaza(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
//		Integer tempEtcEntryPlaza = awayTransaction.getEntryPlazaId() != null ? awayTransaction.getEntryPlazaId()
//				: null;
//		if (tempEtcEntryPlaza != null) {
//			Plaza tempPlaza = staticDataLoad.getPlazaById(tempEtcEntryPlaza);
//			String externPlazaId = tempPlaza != null ? tempPlaza.getExternPlazaId() : null;
//			if (externPlazaId != null) {
//				if (externPlazaId.length() > 3) {
//					messageConversionDto.setIsValidLengthTx(false);
//					messageConversionDto.addErrorMsg("Length of Etc entry plaza is more than expected length");
//				}
//				return StringUtils.rightPad(externPlazaId, 3, " ");
//			}
//		}
//		return StringUtils.leftPad(" ", 3, " ");
//	}

	/**
	 * Get EtcEntryLane
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcEntryLane(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
//		String tempTollSystemType = awayTransaction.getTollSystemType() != null ? awayTransaction.getTollSystemType()
//				: null;
//		if (tempTollSystemType != null && (IntxConstant.TOLL_SYSTEM_TYPE_B.equalsIgnoreCase(tempTollSystemType)
//				|| IntxConstant.TOLL_SYSTEM_TYPE_U.equalsIgnoreCase(tempTollSystemType) ||IntxConstant.TOLL_SYSTEM_TYPE_E.equalsIgnoreCase(tempTollSystemType))) {
//			return StringUtils.leftPad("*", 3, "*");
//		}
//		if (awayTransaction.getEntryLaneId() != null && awayTransaction.getEntryLaneId() != 0) {
//			return setEtcEntryLane(awayTransaction, messageConversionDto);
//		}
		
		String tempEtcEntryLane = awayTransaction.getEtcEntryLane();
		
		if (tempEtcEntryLane != null && tempEtcEntryLane.length() == 3) {
			return tempEtcEntryLane;
		}

		return StringUtils.leftPad("*", 3, "*");
	}

//	/**
//	 *Set Etc Entry lane
//	 * 
//	 * @param awayTransaction
//	 * @param messageConversionDto
//	 * @return String
//	 */
//	private String setEtcEntryLane(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
//		Integer tempEtcEntryLane = awayTransaction.getEntryLaneId() != null ? awayTransaction.getEntryLaneId() : null;
//		if (tempEtcEntryLane != null) {
//			Lane tempLane = staticDataLoad.getLaneById(tempEtcEntryLane);
//			String externLaneId = tempLane != null ? tempLane.getExternLaneId() : null;
//			if (externLaneId != null) {
//				if (externLaneId.length() > 3) {
//					messageConversionDto.setIsValidLengthTx(false);
//					messageConversionDto.addErrorMsg("Length of Etc entry lane is more than expected length");
//				}
//				return StringUtils.rightPad(externLaneId, 3, " ");
//			}
//		}
//		return StringUtils.leftPad(" ", 3, " ");
//	}

	/**
	 * Get EtcTagAgency
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcTagAgency(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
//		String tempEtcTagAgency = awayTransaction.getDeviceNo() != null ? awayTransaction.getDeviceNo() : null;
//		if (tempEtcTagAgency != null && tempEtcTagAgency.length() > 3) {
//			return tempEtcTagAgency.substring(0, 3);
//		}
		
		String tempEtcTagAgency = awayTransaction.getEtcTagAgency();
		
		if (tempEtcTagAgency != null && tempEtcTagAgency.length() == 3) {
			return tempEtcTagAgency;
		}

		return StringUtils.leftPad(" ", 3, " ");
	}

	/**
	 * Get EtcTagSerialNum
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcTagSerialNum(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
//		String tempEtcTagSerialNum = awayTransaction.getDeviceNo() != null ? awayTransaction.getDeviceNo() : null;
//		if (tempEtcTagSerialNum != null && tempEtcTagSerialNum.length() == 11) {
//			return tempEtcTagSerialNum.substring(3, 11);
//		}
		
		Long tempEtcTagSerialNum = awayTransaction.getEtcTagSerialNumber();
		
		if (tempEtcTagSerialNum != null && tempEtcTagSerialNum >= 1 && tempEtcTagSerialNum <= 16777215) {
			return StringUtils.leftPad(String.valueOf(tempEtcTagSerialNum), 8, "0");
		}
		
		return StringUtils.leftPad(" ", 8, " ");
	}

	/**
	 * Get EtcReadPerformance
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcReadPerformance(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
//		String tempEtcReadPerf = awayTransaction.getDeviceReadCount() != null
//				&& awayTransaction.getDeviceReadCount() != 0 ? String.valueOf(awayTransaction.getDeviceReadCount())
//						: "**";
//		if (tempEtcReadPerf.length() > 2) {
//			messageConversionDto.setIsValidLengthTx(false);
//			messageConversionDto.addErrorMsg("Length of Etc read performance is more than expected length");
//		}
//		return StringUtils.leftPad(tempEtcReadPerf, 2, "0");
		
		String tempEtcReadPerf = awayTransaction.getEtcReadPerformance();
		
		if (tempEtcReadPerf != null && tempEtcReadPerf.length() != 0 && tempEtcReadPerf.length() < 3) {
			return StringUtils.leftPad(tempEtcReadPerf, 2, "0");
		}
		
		return StringUtils.leftPad("*", 2, "*");
	}

	/**
	 * Get EtcWritePerformance
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcWritePerformance(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
//		String tempEtcWritePerf = awayTransaction.getDeviceWriteCount() != null
//				&& awayTransaction.getDeviceWriteCount() != 0 ? String.valueOf(awayTransaction.getDeviceWriteCount())
//						: "**";
//		if (tempEtcWritePerf.length() > 2) {
//			messageConversionDto.setIsValidLengthTx(false);
//			messageConversionDto.addErrorMsg("Length of Etc write performance is more than expected length");
//		}
//		return StringUtils.leftPad(tempEtcWritePerf, 2, "0");
		
		String tempEtcWritePerf = awayTransaction.getEtcWritePerf();
		
		if (tempEtcWritePerf != null && tempEtcWritePerf.length() != 0 &&  tempEtcWritePerf.length() < 3) {
			return StringUtils.leftPad(tempEtcWritePerf, 2, "0");
		}
		
		return StringUtils.leftPad("*", 2, "*");
	}

	/**
	 * Get EtcTagPgmStatus
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcTagPgmStatus(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
//		String tempEtcTagPgmStatus = awayTransaction.getDeviceProgramStatus() != null
//				? awayTransaction.getDeviceProgramStatus()
//				: null;
//		if (tempEtcTagPgmStatus != null) {
//			if ("0".equalsIgnoreCase(tempEtcTagPgmStatus) || "1".equalsIgnoreCase(tempEtcTagPgmStatus)) {
//				return "S";
//			} else if ("4".equalsIgnoreCase(tempEtcTagPgmStatus) || "6".equalsIgnoreCase(tempEtcTagPgmStatus)) {
//				return "F";
//			}
//		}
		
		String tempEtcTagPgmStatus = awayTransaction.getEtcTagPgmStatus() != null
				? awayTransaction.getEtcTagPgmStatus()
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
//		Integer tempEtclaneMode = awayTransaction.getLaneMode() != null ? awayTransaction.getLaneMode() : null;
//		if (tempEtclaneMode != null) {
//			switch (tempEtclaneMode) {
//			case 1:
//				return IntxConstant.LANE_MODE_E;
//			case 2:
//				return IntxConstant.LANE_MODE_A;
//			case 3:
//				return IntxConstant.LANE_MODE_M;
//			case 8:
//				return IntxConstant.LANE_MODE_C;
//			default:
//				return IntxConstant.LANE_MODE_E; 
//			}
//		}
//		return IntxConstant.LANE_MODE_E;
		//return StringUtils.leftPad(" ", 1, " ");
		
		String tempEtcLaneMode = awayTransaction.getEtcLaneMode() != null ? awayTransaction.getEtcLaneMode() : null;
		if (tempEtcLaneMode != null) {
			switch (tempEtcLaneMode) {
			case "1":
				return IntxConstant.LANE_MODE_E;
			case "2":
				return IntxConstant.LANE_MODE_A;
			case "3":
				return IntxConstant.LANE_MODE_M;
			case "8":
				return IntxConstant.LANE_MODE_C;
			default:
				return IntxConstant.LANE_MODE_E; 
			}
		}
		return IntxConstant.LANE_MODE_E;
	}

	/**
	 * Get EtcValidationStatus
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcValidationStatus(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
//		String tempLaneDeviceStatus = awayTransaction.getLaneDeviceStatus() != null
//				&& awayTransaction.getLaneDeviceStatus() != 0 ? String.valueOf(awayTransaction.getLaneDeviceStatus())
//						: "*";
//		if (tempLaneDeviceStatus.length() > 1) {
//			messageConversionDto.setIsValidLengthTx(false);
//			messageConversionDto.addErrorMsg("Length of Etc validation status is more than expected length");
//		}
//		return StringUtils.leftPad(tempLaneDeviceStatus, 1, " ");
		
		String tempEtcValidationStatus = awayTransaction.getEtcValidationStatus() != null && awayTransaction.getEtcValidationStatus() != 0 
				? String.valueOf(awayTransaction.getEtcValidationStatus())
				: "*";
		if (tempEtcValidationStatus.length() > 1) {
			messageConversionDto.setIsValidLengthTx(false);
			messageConversionDto.addErrorMsg("Length of Etc validation status is more than expected length");
		}
		return StringUtils.leftPad(tempEtcValidationStatus, 1, " ");
	}

	/**
	 * Get EtcLicState
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcLicState(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
//		if (!StringUtils.isBlank(awayTransaction.getPlateState())) {
//			if (awayTransaction.getPlateState().length() > 2) {
//				messageConversionDto.setIsValidLengthTx(false);
//				messageConversionDto.addErrorMsg("Length of Etc Lic state is more than expected length");
//			}
//			return StringUtils.rightPad(awayTransaction.getPlateState(), 2, " ");
//		} else {
//			return StringUtils.leftPad("*", 2, "*");
//		}
		
		//Unused for Non-Toll transactions.
		return StringUtils.leftPad("*", 2, "*");
	}

	/**
	 * Get EtcLicNumber
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcLicNumber(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
//		if (!StringUtils.isBlank(awayTransaction.getPlateNumber())) {
//			if (awayTransaction.getPlateNumber().length() > 10) {
//				messageConversionDto.setIsValidLengthTx(false);
//				messageConversionDto.addErrorMsg("Length of Etc Lic number is more than expected length");
//			}
//			return StringUtils.rightPad(awayTransaction.getPlateNumber(), 10, " ");
//		} else {
//			return StringUtils.leftPad("*", 10, "*");
//		}
		
		//Unused for Non-Toll transactions.
		return StringUtils.leftPad("*", 10, "*");
	}

	/**
	 * Get EtcClassCharged
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcClassCharged(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
//		Integer actualClass = awayTransaction.getActualClass() != null && awayTransaction.getActualClass() != 0
//				? awayTransaction.getActualClass()
//				: 0;
//		VehicleClass vehClass = staticDataLoad.getVehClassByAgencyIdandActualClass(awayTransaction.getPlazaAgencyId(),
//				actualClass);
//		if (vehClass != null && vehClass.getExternAgencyClass() != null
//				&& vehClass.getExternAgencyClass().length() > 3) {
//			messageConversionDto.setIsValidLengthTx(false);
//			messageConversionDto.addErrorMsg("Length of Etc class charged is more than expected length");
//		}
//		return StringUtils.rightPad(vehClass != null ? String.valueOf(vehClass.getExternAgencyClass()) : " ", 3, " ");
		
		// Unused for Non-Toll transactions.
		return StringUtils.leftPad("*", 3, "*");
	}

	/**
	 * Get EtcActualAxles
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcActualAxles(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
//		Integer tempActualAxles = awayTransaction.getActualAxles() != null && awayTransaction.getActualAxles() != 0
//				? awayTransaction.getActualAxles()
//				: 0;
//		if (String.valueOf(tempActualAxles).length() > 2) {
//			messageConversionDto.setIsValidLengthTx(false);
//			messageConversionDto.addErrorMsg("Length of Etc actual axles is more than expected length");
//		}
//		return StringUtils.rightPad(String.valueOf(tempActualAxles), 2, " ");
		
		Integer tempEtcActualAxles = awayTransaction.getEtcActualAxles();
		if (String.valueOf(tempEtcActualAxles).length() > 2) {
			messageConversionDto.setIsValidLengthTx(false);
			messageConversionDto.addErrorMsg("Length of Etc actual axles is more than expected length");
		}
		return StringUtils.leftPad(String.valueOf(tempEtcActualAxles), 2, "0");
	}

	/**
	 * Get EtcExitSpeed
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcExitSpeed(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
//		Integer tempVehicleSpeed = awayTransaction.getVehicleSpeed() != null && awayTransaction.getVehicleSpeed() != 0
//				? awayTransaction.getVehicleSpeed()
//				: 0;
//		if (String.valueOf(tempVehicleSpeed).length() > 3) {
//			messageConversionDto.setIsValidLengthTx(false);
//			messageConversionDto.addErrorMsg("Length of Etc exit speed is more than expected length");
//		}
//		return StringUtils.leftPad(String.valueOf(tempVehicleSpeed), 3, "0");
		
		Integer tempEtcExitSpeed = awayTransaction.getEtcExitSpeed();
		if (String.valueOf(tempEtcExitSpeed).length() > 3) {
			messageConversionDto.setIsValidLengthTx(false);
			messageConversionDto.addErrorMsg("Length of Etc exit speed is more than expected length");
		}
		return StringUtils.leftPad(String.valueOf(tempEtcExitSpeed), 3, "0");
	}

	/**
	 * Get EtcOverSpeed
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */ 
	private String getEtcOverSpeed(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
//		String tempSpeedViolFlag = awayTransaction.getSpeedViolFlag() != null ? awayTransaction.getSpeedViolFlag()
//				: null;
//		if (tempSpeedViolFlag != null) {
//			String overSpeed = "T".equalsIgnoreCase(tempSpeedViolFlag) || "Y".equalsIgnoreCase(tempSpeedViolFlag) ? "Y"
//					: "N";
//			return StringUtils.leftPad(overSpeed, 1, " ");
//		}
//		return StringUtils.leftPad("N", 1, " ");
		
		String tempEtcOverSpeed = awayTransaction.getEtcOverSpeed() != null ? awayTransaction.getEtcOverSpeed()
				: null;
		if (tempEtcOverSpeed != null) {
			String overSpeed = "1".equalsIgnoreCase(tempEtcOverSpeed) || "Y".equalsIgnoreCase(tempEtcOverSpeed) ? "Y"
					: "N";
			return StringUtils.leftPad(overSpeed, 1, " ");
		}
		return StringUtils.leftPad("N", 1, " ");
	}

	/**
	 * Get EtcExitDate
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcExitDate(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
//		LocalDateTime etcDateTime = awayTransaction.getEtcTxTimestamp() != null ? awayTransaction.getEtcTxTimestamp() : null;
//		if (etcDateTime != null) {
//			return localDateTimeUtility.getDateYYYYMMDD(etcDateTime);
//		}
//
//		return StringUtils.leftPad(" ", 8, " ");
		
		String tempEtcExitDate = awayTransaction.getEtcExitDate() != null ? awayTransaction.getEtcExitDate() : null;
		
		if (tempEtcExitDate != null && tempEtcExitDate.length() == 8) {
			return tempEtcExitDate;
		}
		return StringUtils.leftPad(" ", 8, " ");
	}

	/**
	 * Get EtcExitTime
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcExitTime(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
//		LocalDateTime etcDateTime = awayTransaction.getEtcTxTimestamp() != null ? awayTransaction.getEtcTxTimestamp() : null;
//		if (etcDateTime != null) {
//			return localTimeUtility.getTimeHHMMSS(etcDateTime);
//		}
//		return StringUtils.leftPad(" ", 6, " ");
		
		String tempEtcExitTime = awayTransaction.getEtcExitTime() != null ? awayTransaction.getEtcExitTime() : null;
		
		if (tempEtcExitTime != null && tempEtcExitTime.length() == 6) {
			return tempEtcExitTime;
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
//		Integer tempEtcExitPlaza = awayTransaction.getPlazaId() != null ? awayTransaction.getPlazaId() : null;
//		if (tempEtcExitPlaza != null) {
//			Plaza tempPlaza = staticDataLoad.getPlazaById(tempEtcExitPlaza);
//			String externPlazaId = tempPlaza != null ? tempPlaza.getExternPlazaId() : null;
//			if (externPlazaId != null) {
//				if (externPlazaId.length() > 3) {
//					messageConversionDto.setIsValidLengthTx(false);
//					messageConversionDto.addErrorMsg("Length of Etc exit plaza is more than expected length");
//				}
//				return StringUtils.rightPad(externPlazaId, 3, " ");
//			}
//		}
//		return StringUtils.leftPad(" ", 3, " ");
		
		String tempEtcExitPlaza = awayTransaction.getEtcExitPlaza() != null ? awayTransaction.getEtcExitPlaza() : null;
		if (tempEtcExitPlaza != null) {
			if (tempEtcExitPlaza.length() > 3) {
				messageConversionDto.setIsValidLengthTx(false);
				messageConversionDto.addErrorMsg("Length of Etc exit plaza is more than expected length");
			}
			return StringUtils.rightPad(tempEtcExitPlaza, 3, " ");
		}
		return StringUtils.leftPad(" ", 3, " ");
	}

	/**
	 * Get EtcExitLane
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcExitLane(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
//		Integer tempEtcExitLane = awayTransaction.getLaneId() != null ? awayTransaction.getLaneId() : null;
//		if (tempEtcExitLane != null) {
//			Lane tempLane = staticDataLoad.getLaneById(tempEtcExitLane);
//			String externLaneId = tempLane != null ? tempLane.getExternLaneId() : null;
//			if (externLaneId != null) {
//				if (externLaneId.length() > 3) {
//					messageConversionDto.setIsValidLengthTx(false);
//					messageConversionDto.addErrorMsg("Length of Etc exit lane is more than expected length");
//				}
//				return StringUtils.rightPad(externLaneId, 3, " ");
//			}
//		}
//		return StringUtils.leftPad(" ", 3, " ");
		
		String tempEtcExitLane = awayTransaction.getEtcExitLane() != null ? awayTransaction.getEtcExitLane() : null;
		if (tempEtcExitLane != null) {
			if (tempEtcExitLane.length() > 3) {
				messageConversionDto.setIsValidLengthTx(false);
				messageConversionDto.addErrorMsg("Length of Etc exit lane is more than expected length");
			}
			return StringUtils.rightPad(tempEtcExitLane, 3, " ");
		}
		return StringUtils.leftPad(" ", 3, " ");
	}

	/**
	 * Get EtcDebitCredit
	 * 
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcDebitCredit(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
//		return StringUtils.leftPad("+", 1, " ");
		
		String tempEtcDebitCredit = awayTransaction.getEtcDebitCredit();
		
		if (tempEtcDebitCredit != null) {
			return tempEtcDebitCredit;
		}
		
		return "*";
	}
	
//	/**
//	 * Get EtcDebitCreditCorrection
//	 * 
//	 * @param messageConversionDto
//	 * @return String
//	 */
//	private String getEtcDebitCreditCorrection(AwayTransaction awayTransaction,MessageConversionDto messageConversionDto) {
//		
//		if(awayTransaction.getPostedFareAmount()>0) {
//			return StringUtils.leftPad("+", 1, " ");
//		}else {
//			return StringUtils.leftPad("-", 1, " ");
//		}
//	}

	/**
	 * Get EtcAmountOverdue
	 * 
	 * @param awayTransaction
	 * @param messageConversionDto
	 * @return String
	 */
	private String getEtcAmountDue(AwayTransaction awayTransaction, MessageConversionDto messageConversionDto) {
		//Double etcTollAmount = tollCalculationService.calculateToll(awayTransaction); 
		// Double etcTollAmount =  awayTransaction.getPostedFareAmount();
		Double etcAmountDue =  awayTransaction.getEtcAmountDue();
		
		if (etcAmountDue != null) {
			String tempAmount = String.valueOf((int)(etcAmountDue*100));
			return StringUtils.leftPad(tempAmount.replace(".", ""), 7, "0"); 
		}
		return StringUtils.leftPad("0", 7, "0");
	}

}
