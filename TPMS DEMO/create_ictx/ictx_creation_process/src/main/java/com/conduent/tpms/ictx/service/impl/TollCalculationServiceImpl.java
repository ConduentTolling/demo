package com.conduent.tpms.ictx.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.ictx.constants.IctxConstant;
import com.conduent.tpms.ictx.dao.TollScheduleDao;
import com.conduent.tpms.ictx.dto.TollFareDto;
import com.conduent.tpms.ictx.model.Agency;
import com.conduent.tpms.ictx.model.AwayTransaction;
import com.conduent.tpms.ictx.model.Lane;
import com.conduent.tpms.ictx.model.TollSchedule;
import com.conduent.tpms.ictx.service.TollCalculationService;
import com.conduent.tpms.ictx.utility.StaticDataLoad;

@Service
public class TollCalculationServiceImpl implements TollCalculationService {

	@Autowired
	StaticDataLoad staticDataLoad;

	@Autowired
	TollScheduleDao tollScheduleDao;

	/**
	 * Calculate Toll
	 */
	public Double calculateToll(AwayTransaction awayTransaction) {
		Double tollAmount;
		TollFareDto tempTollFareDto = new TollFareDto();

		tempTollFareDto.setPlanType(1);
		tempTollFareDto.setLaneMode(1);
		tempTollFareDto.setTollRevenueType(1);
		tempTollFareDto.setAccountType(1);
		tempTollFareDto.setPlazaAgencyId(awayTransaction.getPlazaAgencyId());
		tempTollFareDto.setPlazaId(awayTransaction.getPlazaId());
		tempTollFareDto.setActualClass(awayTransaction.getActualClass());

		setEntryPlazaId(awayTransaction, tempTollFareDto);

		tempTollFareDto.setTxDate(awayTransaction.getTxDate());
		tempTollFareDto.setFullFareAmount(0.0);
		tempTollFareDto.setDiscountAmount(0.0);
		tempTollFareDto.setExtraAxleCharge(0.0);
		tempTollFareDto.setExtraAxleChargeCash(0.0);
		tempTollFareDto.setTollRevenueType(awayTransaction.getTollRevenueType());

		LocalDateTime paCutOffDateTime = staticDataLoad
				.getCutOffDateForAgency(IctxConstant.CONSTANT_PANYNJ_TOLL_CUTOFF_DATE);

		Agency tempAgency = staticDataLoad.getAgencyById(Long.valueOf(awayTransaction.getPlazaAgencyId()));
		Lane tempLane = staticDataLoad.getLaneById(awayTransaction.getLaneId());
		Agency accountAgency = staticDataLoad.getAgency(awayTransaction.getDeviceNo().substring(0, 3));

		setPriceSchedule(tempTollFareDto, tempAgency);

		tollAmount = computeToll(awayTransaction, tempTollFareDto, paCutOffDateTime, tempAgency, tempLane,
				accountAgency);
		return tollAmount != null ? tollAmount : 0.0;
	}

	/**
	 * Compute toll
	 * 
	 * @param awayTransaction
	 * @param tempTollFareDto
	 * @param paCutOffDateTime
	 * @param tempAgency
	 * @param tempLane
	 * @param accountAgency
	 * @return Double
	 */
	private Double computeToll(AwayTransaction awayTransaction, TollFareDto tempTollFareDto,
			LocalDateTime paCutOffDateTime, Agency tempAgency, Lane tempLane, Agency accountAgency) {
		Double tollAmount;
		if (!(IctxConstant.AGENCY_SHORT_NAME_PA.equalsIgnoreCase(tempAgency.getAgencyShortName()))
				&& ("Y".equalsIgnoreCase(tempAgency.getCalculateTollAmount())
						|| "Y".equalsIgnoreCase(tempLane.getCalculateTollAmount()))) {
			if (IctxConstant.TOLL_SYSTEM_TYPE_X.equalsIgnoreCase(awayTransaction.getTollSystemType())
					&& Integer.valueOf(0).equals(awayTransaction.getEntryLaneId())) {
				computeForUnmatchedTx(awayTransaction, tempTollFareDto);
			} else {
				computeForMatchedTx(awayTransaction, tempTollFareDto, paCutOffDateTime, accountAgency);
			}

			tollAmount = tempTollFareDto.getDiscountAmount();
			return tollAmount;
		} else {
			if (IctxConstant.AGENCY_SHORT_NAME_PA.equalsIgnoreCase(tempAgency.getAgencyShortName())) {
				tollAmount = computeForPAAgency(awayTransaction, paCutOffDateTime, tempAgency);
			} else {
				tollAmount = awayTransaction.getExpectedRevenueAmount();
			}
		}
		return tollAmount;
	}

	/**
	 * Compute for PA Agency
	 * 
	 * @param awayTransaction
	 * @param paCutOffDateTime
	 * @param tempAgency
	 * @return Double
	 */
	private Double computeForPAAgency(AwayTransaction awayTransaction, LocalDateTime paCutOffDateTime,
			Agency tempAgency) {
		Double tollAmount;
		if (paCutOffDateTime != null && awayTransaction.getTxTimestampLocalDateTime().isEqual(paCutOffDateTime)
				&& awayTransaction.getTxTimestampLocalDateTime().isAfter(paCutOffDateTime)) {
			// pa_cut_off_date
			tollAmount = (Integer.valueOf(5).equals(tempAgency.getParentAgencyId())
					? awayTransaction.getPostedFareAmount()
					: awayTransaction.getExpectedRevenueAmount());
		} else {
			tollAmount = awayTransaction.getPostedFareAmount();
		}
		return tollAmount;
	}

	/**
	 * Set Price Schedule
	 * 
	 * @param tempTollFareDto
	 * @param tempAgency
	 */
	private void setPriceSchedule(TollFareDto tempTollFareDto, Agency tempAgency) {
		tempTollFareDto.setSchedPrice(tempAgency != null ? tempAgency.getScheduledPricingFlag() : null);
	}

	/**
	 * Set Entry Plaza Id
	 * 
	 * @param awayTransaction
	 * @param tempTollFareDto
	 */
	private void setEntryPlazaId(AwayTransaction awayTransaction, TollFareDto tempTollFareDto) {
		if (!Arrays.asList(1, 53, 54).contains(awayTransaction.getPlazaAgencyId())) {
			tempTollFareDto.setEntryPlazaId(0);
		} else {
			if ((Arrays.asList(IctxConstant.TOLL_SYSTEM_TYPE_C, IctxConstant.TOLL_SYSTEM_TYPE_T,
					IctxConstant.TOLL_SYSTEM_TYPE_X).contains(awayTransaction.getTollSystemType()))
					|| (Integer.valueOf(0).equals(awayTransaction.getEntryLaneId()))) {
				tempTollFareDto.setEntryPlazaId(0);
			} else {
				tempTollFareDto.setEntryPlazaId(awayTransaction.getEntryPlazaId());
			}
		}
	}

	/**
	 * Compute toll for matched tx
	 * 
	 * @param awayTransaction
	 * @param tempTollFareDto
	 * @param paCutOffDateTime
	 * @param accountAgency
	 */
	private void computeForMatchedTx(AwayTransaction awayTransaction, TollFareDto tempTollFareDto,
			LocalDateTime paCutOffDateTime, Agency accountAgency) {
		TollSchedule tempTollSchedule;
		// Remove the Car pooling logic as informed
		tempTollSchedule = tollScheduleDao.getTollAmount(tempTollFareDto);

		if (tempTollSchedule != null) {
			tempTollFareDto.setDiscountAmount(tempTollSchedule.getDiscountFareAmount());
			tempTollFareDto.setFullFareAmount(tempTollSchedule.getFullFareAmount());
		}

		if (Arrays.asList(2, 4).contains(awayTransaction.getPlazaAgencyId())) {
			computeForMTAorNYSBAAgency(awayTransaction, tempTollFareDto);
		} else if (Integer.valueOf(3).equals(awayTransaction.getPlazaAgencyId())) {
			computeForPAAgency(awayTransaction, tempTollFareDto, paCutOffDateTime, accountAgency);
		} else if (Arrays.asList(8, 53, 54).contains(accountAgency.getParentAgencyId())) {
			computeForMTAandOtherAgency(awayTransaction, tempTollFareDto);
		} else if (Integer.valueOf(1).equals(awayTransaction.getPlazaAgencyId())) {
			computeForNYSTAAgency(awayTransaction, tempTollFareDto);
		}
	}

	/**
	 * Compute toll for unmatched tx
	 * 
	 * @param awayTransaction
	 * @param tempTollFareDto
	 */
	private void computeForUnmatchedTx(AwayTransaction awayTransaction, TollFareDto tempTollFareDto) {
		// TODO:get_max_toll_fare(fare_st) and set result tempTollFareDto
		// Implementation will be done in future
		Double tempTollAmt = 0.0;
		if (null == tempTollAmt) {
			// TODO:Bad record:Implementation will be done in future as we don't have
			// unmatched txn
		} else {
			tempTollAmt = tempTollFareDto.getDiscountAmount();
			if (Integer.valueOf(2).equals(awayTransaction.getPlazaAgencyId())) {
				tempTollAmt = tempTollFareDto.getFullFareAmount();
			}
		}
	}

	/**
	 * Compute toll for NYSTA agency
	 * 
	 * @param awayTransaction
	 * @param tempTollFareDto
	 */
	private void computeForNYSTAAgency(AwayTransaction awayTransaction, TollFareDto tempTollFareDto) {
		// nysta_cutoff_dt
		LocalDateTime nystaCutOffDateTime = staticDataLoad
				.getCutOffDateForAgency(IctxConstant.CONSTANT_NYSTA_TOLL_CUTOFF_DATE);
		tempTollFareDto.setDiscountAmount(
				(nystaCutOffDateTime != null && awayTransaction.getTxTimestampLocalDateTime().isAfter(nystaCutOffDateTime)
						&& awayTransaction.getTxTimestampLocalDateTime().isEqual(nystaCutOffDateTime))
								? tempTollFareDto.getDiscountAmount()
								: tempTollFareDto.getFullFareAmount());
	}

	/**
	 * Compute toll for MTA and other Agency
	 * 
	 * @param awayTransaction
	 * @param tempTollFareDto
	 */
	private void computeForMTAandOtherAgency(AwayTransaction awayTransaction, TollFareDto tempTollFareDto) {
		// Check with PB When we will receive plaza agency id as 8, 53 ,54
		Double extraAxleCharge = tempTollFareDto.getExtraAxleCharge() * awayTransaction.getActualExtraAxles();
		tempTollFareDto.setDiscountAmount((Integer.valueOf(8).equals(tempTollFareDto.getActualClass()))
				? tempTollFareDto.getDiscountAmount() + extraAxleCharge
				: tempTollFareDto.getFullFareAmount());
	}

	/**
	 * Compute toll for PA Agency
	 * 
	 * @param awayTransaction
	 * @param tempTollFareDto
	 * @param paCutOffDateTime
	 * @param accountAgency
	 */
	private void computeForPAAgency(AwayTransaction awayTransaction, TollFareDto tempTollFareDto,
			LocalDateTime paCutOffDateTime, Agency accountAgency) {
		if (Arrays.asList(6, 7, 9).contains(awayTransaction.getActualClass())) {
			Double extraAxleCharge = tempTollFareDto.getExtraAxleCharge() * awayTransaction.getActualExtraAxles();
			if (paCutOffDateTime != null && awayTransaction.getTxTimestampLocalDateTime().isAfter(paCutOffDateTime)
					&& awayTransaction.getTxTimestampLocalDateTime().isEqual(paCutOffDateTime)) {
				// Check pa_cut_off_date

				tempTollFareDto.setDiscountAmount(((Integer.valueOf(5).equals(accountAgency.getParentAgencyId()))
						? tempTollFareDto.getDiscountAmount()
						: tempTollFareDto.getFullFareAmount()) + extraAxleCharge);

			} else {
				tempTollFareDto
						.setDiscountAmount(tempTollFareDto.getDiscountAmount() + tempTollFareDto.getExtraAxleCharge());
			}
		} else {
			if (paCutOffDateTime != null && awayTransaction.getTxTimestampLocalDateTime().isAfter(paCutOffDateTime)
					&& awayTransaction.getTxTimestampLocalDateTime().isEqual(paCutOffDateTime)) {
				// Check pa_cut_off_date
				tempTollFareDto.setDiscountAmount(((Integer.valueOf(5).equals(accountAgency.getParentAgencyId()))
						? tempTollFareDto.getDiscountAmount()
						: tempTollFareDto.getFullFareAmount()));
			}
		}
	}

	/**
	 * Compute toll for MTA or NYSBA
	 * 
	 * @param awayTransaction
	 * @param tempTollFareDto
	 */
	private void computeForMTAorNYSBAAgency(AwayTransaction awayTransaction, TollFareDto tempTollFareDto) {
		Double extraAxleCharge = tempTollFareDto.getExtraAxleCharge() * awayTransaction.getActualExtraAxles();
		tempTollFareDto.setDiscountAmount(
				((Integer.valueOf(2).equals(awayTransaction.getPlazaAgencyId())) ? tempTollFareDto.getFullFareAmount()
						: tempTollFareDto.getDiscountAmount()) + extraAxleCharge);
	}

}
