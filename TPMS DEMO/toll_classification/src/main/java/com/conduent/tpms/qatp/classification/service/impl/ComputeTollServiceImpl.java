package com.conduent.tpms.qatp.classification.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.qatp.classification.constants.QatpClassificationConstant;
import com.conduent.tpms.qatp.classification.dao.ComputeTollDao;
import com.conduent.tpms.qatp.classification.dao.TAgencyDao;
import com.conduent.tpms.qatp.classification.dto.AccountInfoDto;
import com.conduent.tpms.qatp.classification.dto.TollScheduleDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDetailDto;
import com.conduent.tpms.qatp.classification.dto.TransactionDto;
import com.conduent.tpms.qatp.classification.model.Agency;
import com.conduent.tpms.qatp.classification.model.TollPriceSchedule;
import com.conduent.tpms.qatp.classification.service.ComputeTollService;
import com.conduent.tpms.qatp.classification.utility.MasterDataCache;
import com.google.common.base.Stopwatch;

/**
 * Compute Toll Service Implementation
 * 
 * @author Sameer
 *
 */
@Service
public class ComputeTollServiceImpl implements ComputeTollService {

	private static List<Integer> actualClassList;
	private static List<String> tollSystemType;

	private static final Logger logger = LoggerFactory.getLogger(ComputeTollServiceImpl.class);

	@Autowired
	private TAgencyDao tAgencyDao;

	@Autowired
	private ComputeTollDao computeTollDAO;

	@Autowired
	private MasterDataCache masterDataCache;

	// getAgencyById
	static {
		actualClassList = new ArrayList<>();
		actualClassList.add(0);
		actualClassList.add(1);
		actualClassList.add(2);
		actualClassList.add(3);
		actualClassList.add(4);
		actualClassList.add(5);
		actualClassList.add(6);
		actualClassList.add(7);
		actualClassList.add(8);
		actualClassList.add(9);

		tollSystemType = new ArrayList<>();
		tollSystemType.add("B");
		tollSystemType.add("P");
		tollSystemType.add("X");
	}

	/**
	 * Compute Toll for Transaction
	 */
	@Override
	public TransactionDto computeToll(TransactionDto transactionObject, AccountInfoDto accountObject) {
		logger.info("Compute toll service starts for lane tx id {}", transactionObject.getLaneTxId());
		Long planType;
		Integer actualClass = 0;
		List<String> tollRevTypeList = Arrays.asList(QatpClassificationConstant.REVENUE_TYPE_ETC_STRING,
				QatpClassificationConstant.REVENUE_TYPE_CASH_STRING,
				QatpClassificationConstant.REVENUE_TYPE_VIOLATION_STRING,
				QatpClassificationConstant.VIOLATION_AET_REVENUE_TYPE);
		String congesctionPricing = null;
		String dayInd = null;
		Integer lcPriceSchedule = 0;
		Map<Integer, TollScheduleDto> tempTollScheduleDtoMap = new HashMap<>();

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(QatpClassificationConstant.yyyy_MM_dd);
		String temptxDate = transactionObject.getTxDate();
		LocalDate txDate = null;
		if (temptxDate != null) {
			txDate = LocalDate.parse(temptxDate, dateFormatter);
		}

		Integer entryPlaza = transactionObject.getEntryPlazaId();
		Integer exitPlaza = transactionObject.getPlazaId();

		planType = getPlanType(accountObject);
		// adding code for inserting plan_type in transaction object
		transactionObject.setPlanType((int) (long) planType);
		logger.info("[Compute toll service] plan type set successfully for lane tx id {}",
				transactionObject.getLaneTxId());

		actualClass = getActualClass(transactionObject);

		logger.info("[Compute toll service] actual class set successfully for lane tx id {}",
				transactionObject.getLaneTxId());

		// Agency agency =
		// tAgencyDao.getAgencySchedulePricingById(transactionObject.getPlazaAgencyId());
		// // commented as per Manu suggestion
		Stopwatch stopwatch = Stopwatch.createStarted();
		Agency agency = masterDataCache.getAgencyById(transactionObject.getPlazaAgencyId().longValue()); // as per Manu
																											// suggestion
																											// taking
																											// data from
																											// Master
																											// cache
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Time taken to get data from CRM.T_AGENCY master cache ==> {} ms",millis);
		congesctionPricing = agency != null ? agency.getScheduledPricingFlag() : "N";

		// As suggested By PB, Adding plazaAgencyId to check for nysta.
		dayInd = getDayInd(congesctionPricing, dayInd, txDate, transactionObject.getPlazaAgencyId());

		logger.info("[Compute toll service] dayInd set successfully for lane tx id {}",
				transactionObject.getLaneTxId());

		logger.info("[Compute toll service] Get Price Schedule Query txDate {} dayInd {} Agency id {}", txDate, dayInd,
				transactionObject.getPlazaAgencyId());

		
		// working code commented as per Manu suggestion

		/*
		 * TollPriceSchedule tollPriceSchedule = computeTollDAO.getPriceSchedule(txDate,
		 * txDate, dayInd, transactionObject.getPlazaAgencyId());
		 */
		LocalDate date = LocalDate.of(1857, 01, 01);
		txDate = txDate != null ? txDate : date;
		Stopwatch stopwatch1 = Stopwatch.createStarted();
		// txDate = txDate?txDate:1857-01-01;
		TollPriceSchedule tollPriceSchedule = masterDataCache.getPriceSchedule(txDate, txDate, dayInd,
				transactionObject.getPlazaAgencyId());

		stopwatch1.stop();
		long millis1 = stopwatch1.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Time taken to select data from GET_PRICING_SCHEDULE master cache ==> ==> {} ms",millis1);
		Integer schedulePrice = tollPriceSchedule != null ? tollPriceSchedule.getPriceScheduleId() : null;

		logger.info("[Compute toll service] After Toll Price Schedule is {}", schedulePrice);

		lcPriceSchedule = getLcPriceSchedule(schedulePrice);

		logger.info("[Compute toll service] lcPriceSchedule value {}", lcPriceSchedule);

		// Condition for NYSTA and NYSTB
		// TODO: Will check later
		/*
		 * if (transactionObject.getPlazaAgencyId() !=null &&
		 * (transactionObject.getPlazaAgencyId() == 1 ||
		 * transactionObject.getPlazaAgencyId() == 4)) { lcPriceSchedule = 1; logger.
		 * info("[Compute toll service] lcPriceSchedule value changed for Agency condition {}"
		 * , lcPriceSchedule ); }
		 */

		logger.info("[Compute toll service] get toll amount call starts for lane tx id {}",
				transactionObject.getLaneTxId());

		logger.info(
				"Parameters Before Toll Calculation:laneTxId:{}, txDate: {}, txDate: {}, transactionObject.getPlazaId() : {},"
						+ "entryPlaza: {}, actualClass: {}, tollRevType: {}, planType: {}, lcPriceSchedule: {}",
				transactionObject.getLaneTxId(), txDate, txDate, transactionObject.getPlazaId(), entryPlaza,
				actualClass, tollRevTypeList, planType, lcPriceSchedule);
		

		// commented as per Manu suggestion

		Stopwatch stopwatch2 = Stopwatch.createStarted();
		List<TollScheduleDto> tollScheduleDTO = computeTollDAO.getTollAmountByRevenueType(txDate,
				transactionObject.getPlazaId(), entryPlaza, actualClass, tollRevTypeList, planType, lcPriceSchedule);
		 
		stopwatch2.stop();
		long millis3 = stopwatch2.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Time taken to select data from GET_TOLL_PRICES ==> {} ms",millis3);
		// new code as per Manu suggestion to get date from master cache

		
		/*
		 * LocalDate date1 = LocalDate.of(1857, 01, 01); txDate = txDate != null ?
		 * txDate :date1; Integer tempEntryPlazaId=entryPlaza!=null?entryPlaza:0;
		 * List<TollScheduleDto> tollScheduleDTO =
		 * masterDataCache.getTollAmountByRevenueType(txDate,
		 * transactionObject.getPlazaId(), tempEntryPlazaId, actualClass,
		 * tollRevTypeList, planType, lcPriceSchedule);
		 */
		 
		setTollScheduleForRevenueType(tempTollScheduleDtoMap, tollScheduleDTO);

		if (tempTollScheduleDtoMap.size() < 3 && tollSystemType.contains(transactionObject.getTollSystemType())) {
			// sakshi

			if (transactionObject.getPlazaAgencyId() != null && transactionObject.getPlazaAgencyId() != 2) {
				entryPlaza = exitPlaza;
			}
			tollScheduleDTO = computeTollDAO.getTollAmountByRevenueType(txDate, transactionObject.getPlazaId(),
					entryPlaza, actualClass, tollRevTypeList, planType, lcPriceSchedule);
			setTollScheduleForRevenueType(tempTollScheduleDtoMap, tollScheduleDTO);
		}

		if (tempTollScheduleDtoMap.size() < 3 && lcPriceSchedule > 1) {
			lcPriceSchedule = 1;
			tollScheduleDTO = computeTollDAO.getTollAmountByRevenueType(txDate, transactionObject.getPlazaId(),
					entryPlaza, actualClass, tollRevTypeList, planType, lcPriceSchedule);
			setTollScheduleForRevenueType(tempTollScheduleDtoMap, tollScheduleDTO);

		}
		logger.info("TollScheduleDto Parameter:laneTxId {}", tollScheduleDTO);
		logger.info("[Compute toll service] get toll amount call ends for lane tx id {}",
				transactionObject.getLaneTxId());

		return calculateToll(transactionObject, tempTollScheduleDtoMap, accountObject);
	}

	private void setTollScheduleForRevenueType(Map<Integer, TollScheduleDto> tempTollScheduleDtoMap,
			List<TollScheduleDto> tollScheduleDTO) {
		if (tollScheduleDTO != null) {
			Integer tempRevenueType;
			for (int i = 0; i < tollScheduleDTO.size(); i++) {
				tempRevenueType = tollScheduleDTO.get(i).getRevenueTypeId();
				if ((tempRevenueType != null)
						&& (tempRevenueType.equals(QatpClassificationConstant.REVENUE_TYPE_ETC)
								|| tempRevenueType.equals(QatpClassificationConstant.REVENUE_TYPE_VIOLATION)
								|| tempRevenueType.equals(QatpClassificationConstant.REVENUE_TYPE_CASH)
								|| tempRevenueType.equals(QatpClassificationConstant.AET_VIOLATION_REVENUE_TYPE))
						&& (tempTollScheduleDtoMap.get(tempRevenueType) == null)) {
					tempTollScheduleDtoMap.put(tempRevenueType, tollScheduleDTO.get(i));
				}
			}
		}
	}

	private Integer getLcPriceSchedule(Integer schedulePrice) {
		Integer lcPriceSchedule;
		if (schedulePrice == null) {
			lcPriceSchedule = 1;
			logger.info("[Compute toll service] Toll Price Schedule INSIDE IF CONDITION");
		} else {
			lcPriceSchedule = schedulePrice;
			logger.info("[Compute toll service] Toll Price Schedule INSIDE ELSE CONDITION and Value {}",
					lcPriceSchedule);
		}
		return lcPriceSchedule;
	}

	private String getDayInd(String congesctionPricing, String dayInd, LocalDate txDate, Integer plazaAgencyId) {
		// As suggested By PB, Adding plazaAgencyId to check for nysta with W and D and
		// for others it will Default 'D'.
		// This is temporary check, will remove in Future.
		if (congesctionPricing != null && congesctionPricing.equals("Y") && plazaAgencyId == 1) {
			if (txDate != null && (txDate.getDayOfWeek().getValue() == 6 || txDate.getDayOfWeek().getValue() == 7)) {
				dayInd = QatpClassificationConstant.W;
			} else {
				dayInd = QatpClassificationConstant.D;
			}
		} else {
			dayInd = QatpClassificationConstant.D;
		}
		return dayInd;
	}

	private Integer getActualClass(TransactionDto transactionObject) {
		Integer actualClass;
		// sakshi
		if (actualClassList.contains(transactionObject.getActualClass()) && transactionObject.getPlazaAgencyId() != null
				&& transactionObject.getPlazaAgencyId() != 2) {
			actualClass = 22;
		} else {
			actualClass = transactionObject.getActualClass();
		}
		return actualClass;
	}

	private Long getPlanType(AccountInfoDto accountObject) {
		Long planType;
		// changing plan_type default to private 1 and 2 for commercial , business and
		// BVideos
		if (accountObject.getAcctType() != null && (accountObject.getAcctType() == 2 || accountObject.getAcctType() == 3
				|| accountObject.getAcctType() == 12)) {
			planType = 2l;
		} else {
			planType = 1l;
		}
		return planType;
	}

	/**
	 * Calculate Toll
	 * 
	 * @param transactionObject
	 * @param tollScheduleDTO
	 * @param accountObject
	 * @return TransactionDto
	 */
	private TransactionDto calculateToll(TransactionDto transactionObject,
			Map<Integer, TollScheduleDto> tempTollScheduleDtoMap, AccountInfoDto accountObject) {

		setEtcFareAmount(transactionObject, tempTollScheduleDtoMap, accountObject);
		setVideoFareAmount(transactionObject, tempTollScheduleDtoMap, accountObject);
		setCashFareAmount(transactionObject, tempTollScheduleDtoMap, accountObject);
		Double extraAxleAmount = setExtraAxleAmount(transactionObject, tempTollScheduleDtoMap);
		setExpectedRevenueAmount(transactionObject, extraAxleAmount);
		transactionObject.setPostedFareAmount(transactionObject.getExpectedRevenueAmount());
		logger.info("[Compute toll service] Toll amount calculated are : ETC_FARE_AMOUNT {} , VIDEO_FARE_AMOUNT {} , "
				+ "CASH_FARE_AMOUNT {} , EXPECTED_REVENUE_AMOUNT {}",
				transactionObject.getEtcFareAmount(),transactionObject.getVideoFareAmount() , transactionObject.getCashFareAmount(),
				transactionObject.getExpectedRevenueAmount());
		logger.info("[Compute toll service] Toll amount calculation ends for account id {}",
				transactionObject.getEtcAccountId());
		return transactionObject;
	}

	private Double setExtraAxleAmount(TransactionDto transactionObject,
			Map<Integer, TollScheduleDto> tempTollScheduleDtoMap) {
		Double extraAxleAmount;
		if (tempTollScheduleDtoMap.get(transactionObject.getTollRevenueType()) != null
				&& transactionObject.getExtraAxles() != null
				&& tempTollScheduleDtoMap.get(transactionObject.getTollRevenueType()).getExtraAxleCharge() != null) {
			extraAxleAmount = transactionObject.getExtraAxles()
					* tempTollScheduleDtoMap.get(transactionObject.getTollRevenueType()).getExtraAxleCharge();
		} else {
			extraAxleAmount = 0.0;
		}
		return extraAxleAmount;
	}

	private void setExpectedRevenueAmount(TransactionDto transactionObject, Double extraAxleAmount) {
		if (QatpClassificationConstant.REVENUE_TYPE_ETC.equals(transactionObject.getTollRevenueType())) {
			transactionObject.setExpectedRevenueAmount(transactionObject.getEtcFareAmount());
		} else if (QatpClassificationConstant.REVENUE_TYPE_VIOLATION.equals(transactionObject.getTollRevenueType())) {
			transactionObject.setExpectedRevenueAmount(transactionObject.getVideoFareAmount());
		} else if (QatpClassificationConstant.REVENUE_TYPE_CASH.equals(transactionObject.getTollRevenueType())) {
			transactionObject.setExpectedRevenueAmount(transactionObject.getCashFareAmount());
		} else if (QatpClassificationConstant.AET_VIOLATION_REVENUE_TYPE
				.equals(transactionObject.getTollRevenueType())) {
			transactionObject.setExpectedRevenueAmount(transactionObject.getVideoFareAmount());
		}
	}

	private void setCashFareAmount(TransactionDto transactionObject,
			Map<Integer, TollScheduleDto> tempTollScheduleDtoMap, AccountInfoDto accountObject) {
		double extraAxlesAmount = 0;

		if (tempTollScheduleDtoMap.get(QatpClassificationConstant.REVENUE_TYPE_CASH) != null) {
			if (tempTollScheduleDtoMap.get(transactionObject.getTollRevenueType()) != null
					&& transactionObject.getExtraAxles() != null && tempTollScheduleDtoMap
							.get(transactionObject.getTollRevenueType()).getExtraAxleCharge() != null) {
				extraAxlesAmount = tempTollScheduleDtoMap.get(3).getExtraAxleCharge()
						* transactionObject.getExtraAxles();
			}
			transactionObject.setCashFareAmount(
					getFareAmount(transactionObject, accountObject, tempTollScheduleDtoMap.get(3)) + extraAxlesAmount);
		} else {
			transactionObject.setCashFareAmount(0.0);
		}
	}

	private void setVideoFareAmount(TransactionDto transactionObject,
			Map<Integer, TollScheduleDto> tempTollScheduleDtoMap, AccountInfoDto accountObject) {
		double extraAxlesAmount = 0;

		String aetFlag = transactionObject.getAetFlag();

		if (aetFlag != null && aetFlag.equals("N")) {
			if (tempTollScheduleDtoMap.get(QatpClassificationConstant.REVENUE_TYPE_VIOLATION) != null) {
				if (tempTollScheduleDtoMap.get(transactionObject.getTollRevenueType()) != null
						&& transactionObject.getExtraAxles() != null && tempTollScheduleDtoMap
								.get(transactionObject.getTollRevenueType()).getExtraAxleCharge() != null) {
					extraAxlesAmount = tempTollScheduleDtoMap.get(9).getExtraAxleCharge()
							* transactionObject.getExtraAxles();
				}
				transactionObject.setVideoFareAmount(
						getFareAmount(transactionObject, accountObject, tempTollScheduleDtoMap.get(9))
								+ extraAxlesAmount);
			}
		} else if (aetFlag != null && aetFlag.equals("Y")) {
			if (tempTollScheduleDtoMap.get(QatpClassificationConstant.AET_VIOLATION_REVENUE_TYPE) != null) {
				if (tempTollScheduleDtoMap.get(transactionObject.getTollRevenueType()) != null
						&& transactionObject.getExtraAxles() != null && tempTollScheduleDtoMap
								.get(transactionObject.getTollRevenueType()).getExtraAxleCharge() != null) {
					extraAxlesAmount = tempTollScheduleDtoMap.get(60).getExtraAxleCharge()
							* transactionObject.getExtraAxles();
				}
				transactionObject.setVideoFareAmount(
						getFareAmount(transactionObject, accountObject, tempTollScheduleDtoMap.get(60))
								+ extraAxlesAmount);
			}
		} else {
			transactionObject.setVideoFareAmount(0.0);
		}
	}

	private void setEtcFareAmount(TransactionDto transactionObject,
			Map<Integer, TollScheduleDto> tempTollScheduleDtoMap, AccountInfoDto accountObject) {
		double extraAxlesAmount = 0;
		if (tempTollScheduleDtoMap.get(QatpClassificationConstant.REVENUE_TYPE_ETC) != null) {
			if (tempTollScheduleDtoMap.get(transactionObject.getTollRevenueType()) != null
					&& transactionObject.getExtraAxles() != null && tempTollScheduleDtoMap
							.get(transactionObject.getTollRevenueType()).getExtraAxleCharge() != null) {
				extraAxlesAmount = tempTollScheduleDtoMap.get(1).getExtraAxleCharge()
						* transactionObject.getExtraAxles();
			}
			transactionObject.setEtcFareAmount(
					getFareAmount(transactionObject, accountObject, tempTollScheduleDtoMap.get(1)) + extraAxlesAmount);
		} else {
			transactionObject.setEtcFareAmount(0.0);
		}
	}

	private Double getFareAmount(TransactionDto transactionObject, AccountInfoDto accountObject,
			TollScheduleDto tollScheduleDTO) {
		if (tollScheduleDTO == null) {
			return 0.0;
		}

		logger.info("[Compute toll service] Toll amount calculation starts for account id {}",
				transactionObject.getEtcAccountId());
		if (QatpClassificationConstant.HOME_AGENCY_DEVICE.equals(accountObject.getHomeAgencyDev())
				|| (QatpClassificationConstant.V.equalsIgnoreCase(transactionObject.getTxType())
						&& QatpClassificationConstant.F.equalsIgnoreCase(transactionObject.getTxSubType()))) {
			return tollScheduleDTO.getFullFareAmount() != null ? tollScheduleDTO.getFullFareAmount() : 0.0;
		} else {
			//return tollScheduleDTO.getDiscountFareAmount() != null ? tollScheduleDTO.getDiscountFareAmount() : 0.0;
			return tollScheduleDTO.getFullFareAmount() != null ? tollScheduleDTO.getFullFareAmount() : 0.0;
		}
	}

	/**
	 * Compute toll of PA agency transaction
	 * 
	 * @param transactionDetailDto
	 * @return TransactionDetailDto
	 */
	public TransactionDetailDto computePAToll(TransactionDetailDto transactionDetailDto) {
		// PA Compute toll
		TransactionDto transactionDto = transactionDetailDto.getTransactionDto();
		if (QatpClassificationConstant.E.equalsIgnoreCase(transactionDto.getTxType())
				|| QatpClassificationConstant.O.equalsIgnoreCase(transactionDto.getTxType())
				|| QatpClassificationConstant.R.equalsIgnoreCase(transactionDto.getTxType())) {
			transactionDto.setExpectedRevenueAmount(transactionDto.getEtcFareAmount());
		} else {
			// In case of "V" txtype
			transactionDto.setExpectedRevenueAmount(transactionDto.getVideoFareAmount());
		}
		transactionDto.setPostedFareAmount(transactionDto.getExpectedRevenueAmount());
		//transactionDto.setCashFareAmount(0.0);
		transactionDetailDto.setTransactionDto(transactionDto);
		return transactionDetailDto;
	}
}
