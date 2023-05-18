package com.conduent.tpms.qatp.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.qatp.constants.Constants;
import com.conduent.tpms.qatp.dao.IAGDao;
import com.conduent.tpms.qatp.dto.TagStatusSortedRecord;
import com.conduent.tpms.qatp.service.BuildTagStatusService;

@Service
public class BuildTagStatusServiceImpl implements BuildTagStatusService {

	private static final Logger log = LoggerFactory.getLogger(BuildTagStatusServiceImpl.class);
	
	@Autowired
	IAGDao iagDao;

	public void buildTagStatus(TagStatusSortedRecord tagStatusSortedRecord) {
		buildRetailerStatus(tagStatusSortedRecord);
		buildItagStatus(tagStatusSortedRecord);
		buildNystaStatus(tagStatusSortedRecord);
		buildNysbaStatus(tagStatusSortedRecord);
		buildMtaStatus(tagStatusSortedRecord);
		buildMtaRioStatus(tagStatusSortedRecord);
		buildPaStatus(tagStatusSortedRecord);
		buildFtagStatus(tagStatusSortedRecord);
	}

	/**
	 * Building retailer status
	 * 
	 * @param tagStatusSortedRecord
	 * 
	 */
	public void buildRetailerStatus(TagStatusSortedRecord tagStatusSortedRecord) {
		int retailTagStatus = tagStatusSortedRecord.getRetailTagStatus();
		Long retailDays = null;
		LocalDate firstTollDate = tagStatusSortedRecord.getFirstTollDate() == null ? null
				: tagStatusSortedRecord.getFirstTollDate();

		if (firstTollDate != null) {
			LocalDate date = firstTollDate.plusDays(Constants.REG_TIME_LIMIT / 24);
			retailDays = Math.abs(ChronoUnit.DAYS.between(LocalDate.now(), date));
		}
		if ((retailTagStatus == Constants.UNREGISTERED) && retailDays == null) {
			tagStatusSortedRecord.setRetailerStatus(Constants.RETAILER_STATUS_U);
		} else if ((retailTagStatus == Constants.UNREGISTERED) && retailDays <= 0) {
			tagStatusSortedRecord.setRetailerStatus(Constants.RETAILER_STATUS_V);
		} else if ((retailTagStatus == Constants.UNREGISTERED) && retailDays > 0) {
			tagStatusSortedRecord.setRetailerStatus(Constants.RETAILER_STATUS_W);
		} else {
			// default value
			tagStatusSortedRecord.setRetailerStatus(Constants.RETAILER_STATUS_DEFAULT);
		}
		log.info("Retailer status set as: {}", tagStatusSortedRecord.getRetailerStatus());
	}

	/**
	 * Building itag status
	 * 
	 * @param tagStatusSortedRecord
	 * 
	 */
	public void buildItagStatus(TagStatusSortedRecord tagStatusSortedRecord) {
		int deviceStatus = tagStatusSortedRecord.getDeviceStatus();
		String prevalidationFlag = tagStatusSortedRecord.getIsPrevalidated();
		String retailerStatus = tagStatusSortedRecord.getRetailerStatus();
		double currentBalance = tagStatusSortedRecord.getCurrentBalance() == null ? 0.00
				: tagStatusSortedRecord.getCurrentBalance();
		double rebillAmount = tagStatusSortedRecord.getRebillAmount();
		int devicePrefix = Integer.valueOf(tagStatusSortedRecord.getDeviceNo().substring(0, 3));
		int primaryRebillPayType = tagStatusSortedRecord.getPrimaryRebillPayType();
		int financial_status = tagStatusSortedRecord.getFinancialStatus();
		int rvkw_status = tagStatusSortedRecord.getAccountStatus();

		// log prevalidationFlag
		log.info("Prevalidation Flag...{}", prevalidationFlag);
		log.info("Financial Status...{}", financial_status);

		if (deviceStatus == Constants.DS_ACTIVE || prevalidationFlag.equalsIgnoreCase(Constants.YES)) // changed to
																										// active as
																										// told by PB
		{
			if (financial_status == Constants.FS_GOOD) {
				tagStatusSortedRecord.setItagTagStatus(Constants.TS_GOOD);
			} else if (financial_status == Constants.FS_LOW) {
				tagStatusSortedRecord.setItagTagStatus(Constants.TS_LOW);
			} else if (financial_status == Constants.FS_ZERO || rvkw_status == Constants.RVKW) // add account status =
																								// RVKW (PB)
			{
				tagStatusSortedRecord.setItagTagStatus(Constants.TS_INVALID);
			} else {
				tagStatusSortedRecord.setItagTagStatus(Constants.TS_LOST_STOLEN); // change to LOST_STOLEN (PB)
			}
		} else if ((retailerStatus.equals(Constants.RETAILER_STATUS_W))
				|| ((devicePrefix == Constants.MTA_AGENCY_ID || devicePrefix == Constants.NYSTA_AGENCY_ID)
						&& retailerStatus.equals(Constants.UNREGISTERED))
				|| (retailerStatus.equals(Constants.RETAILER_STATUS_V) && currentBalance < Constants.ZERO_THRESHOLD)) {
			tagStatusSortedRecord.setItagTagStatus(Constants.TS_INVALID);
		} else if (deviceStatus == Constants.DS_ACTIVE) {
			if ((rebillAmount > 0) || (primaryRebillPayType == Constants.RPT_ACHCPPT
					|| primaryRebillPayType == Constants.RPT_ACHSPPT)) {
				if (primaryRebillPayType == Constants.RPT_VISA || primaryRebillPayType == Constants.RPT_MASTERCARD
						|| primaryRebillPayType == Constants.RPT_AMEX || primaryRebillPayType == Constants.RPT_CASH
						|| primaryRebillPayType == Constants.RPT_DISC || primaryRebillPayType == Constants.RPT_SACH
						|| primaryRebillPayType == Constants.RPT_ACHSPPT
						|| primaryRebillPayType == Constants.RPT_ACHCPPT) {
					tagStatusSortedRecord.setItagTagStatus(Constants.TS_GOOD);
				} else {
					tagStatusSortedRecord.setItagTagStatus(Constants.TS_LOST_STOLEN);
				}
			} else {
				tagStatusSortedRecord.setItagTagStatus(Constants.TS_LOST_STOLEN);
			}
		} else if (tagStatusSortedRecord.getMtaNonRevCount() > 0
				|| tagStatusSortedRecord.getAllPlans() == tagStatusSortedRecord.getMtaNonRevCount()) {
			tagStatusSortedRecord.setItagTagStatus(Constants.TS_LOST_STOLEN);
		} else if (tagStatusSortedRecord.getPaNonRevCount() > 0
				|| tagStatusSortedRecord.getAllPlans() == tagStatusSortedRecord.getPaNonRevCount()) {
			tagStatusSortedRecord.setItagTagStatus(Constants.TS_INVALID);
		} else if (tagStatusSortedRecord.getNystaNonRevCount() > 0
				|| tagStatusSortedRecord.getAllPlans() == tagStatusSortedRecord.getNystaNonRevCount()) {
			tagStatusSortedRecord.setItagTagStatus(Constants.TS_LOST_STOLEN);
		} else {
			tagStatusSortedRecord.setItagTagStatus(Constants.TS_LOST_STOLEN);
		}

		log.info("ITAG status set as: {}", tagStatusSortedRecord.getItagTagStatus());
	}

	/**
	 * Building Nysta tag status
	 * 
	 * @param tagStatusSortedRecord
	 *
	 */
	public void buildNystaStatus(TagStatusSortedRecord tagStatusSortedRecord) {
		int itagStatus = tagStatusSortedRecord.getItagTagStatus();
		int nystaNonRevCount = tagStatusSortedRecord.getNystaNonRevCount();
		int nystaAnnualPlanCount = tagStatusSortedRecord.getNystaAnnualPlanCount();
		int postPaidStatus = tagStatusSortedRecord.getPostPaidStatus();
		int nystaPostPaidComlCount = tagStatusSortedRecord.getNystaPostPaidComlCount();
		int nystanysbacount;

		nystanysbacount = tagStatusSortedRecord.getNysbaNonRevCount() + tagStatusSortedRecord.getNystaNonRevCount();
		log.info("Nsyta and Nysba NonRev Count ....{}", nystanysbacount);

		// String devicePrefix = tagStatusSortedRecord.getDeviceNo().substring(0,3);
		// Default value
		// if(Constants.NYSTA_AGENCY_ID == tagStatusSortedRecord.getAgencyId())
		// //changed to NYSTA
		// {
		tagStatusSortedRecord.setNystaTagStatus(itagStatus);

		if (itagStatus != Constants.TS_INVALID) {
			if ((nystaNonRevCount > 0) || ((nystaAnnualPlanCount > 0) && (itagStatus == Constants.TS_GOOD))
					|| ((nystaPostPaidComlCount > 0) && (postPaidStatus == 1))) {
				tagStatusSortedRecord.setNystaTagStatus(Constants.TS_GOOD);
			} else if ((nystaPostPaidComlCount > 0) && (postPaidStatus == 2)) {
				tagStatusSortedRecord.setNystaTagStatus(Constants.TS_BAD);
			} else if ((nystaAnnualPlanCount > 0) && (itagStatus == Constants.TS_BAD)) {
				tagStatusSortedRecord.setNystaTagStatus(Constants.TS_GOOD);
			}
		}
		if ((tagStatusSortedRecord.getSpeedAgency() > 0)
				&& (tagStatusSortedRecord.getDeviceStatus() == Constants.DS_ACTIVE)) {
			tagStatusSortedRecord.setNystaTagStatus(Constants.FS_ZERO);
		}

		// Non rev conditions
		if (tagStatusSortedRecord.getMtaNonRevCount() > 0
				|| tagStatusSortedRecord.getAllPlans() == tagStatusSortedRecord.getMtaNonRevCount()) {
			tagStatusSortedRecord.setNystaTagStatus(Constants.TS_INVALID);
		} else if (nystanysbacount > 0
				|| tagStatusSortedRecord.getAllPlans() == tagStatusSortedRecord.getNystaNonRevCount()) {
			tagStatusSortedRecord.setNystaTagStatus(Constants.TS_GOOD);
		} else if (tagStatusSortedRecord.getPaNonRevCount() > 0
				|| tagStatusSortedRecord.getAllPlans() == tagStatusSortedRecord.getPaNonRevCount()) {
			tagStatusSortedRecord.setNystaTagStatus(Constants.TS_INVALID);
		}

		// }
		log.info("NYSTA tag status set as: {}", tagStatusSortedRecord.getNystaTagStatus());
	}

	/**
	 * Building Nysba tag status
	 * 
	 * @param tagStatusSortedRecord
	 * 
	 */
	public void buildNysbaStatus(TagStatusSortedRecord tagStatusSortedRecord) {
		int itagStatus = tagStatusSortedRecord.getItagTagStatus();
		int nysbaNonRevCount = tagStatusSortedRecord.getNysbaNonRevCount();
		if (itagStatus != Constants.TS_INVALID && nysbaNonRevCount > 0) {
			tagStatusSortedRecord.setNysbaTagStatus(Constants.TS_GOOD);
		} else {
			// default value
			tagStatusSortedRecord.setNysbaTagStatus(itagStatus);
		}
		log.info("NYSBA tag status set as: {}", tagStatusSortedRecord.getNysbaTagStatus());
	}

	/**
	 * Building Mta tag status
	 * 
	 * @param tagStatusSortedRecord
	 * 
	 */
	public void buildMtaStatus(TagStatusSortedRecord tagStatusSortedRecord) {
		int itagStatus = tagStatusSortedRecord.getItagTagStatus();
		int mtaNonRevCount = tagStatusSortedRecord.getMtaNonRevCount();
		int tbnrfpCnt = tagStatusSortedRecord.getTbnrfpCount();
		int rioNonRevCount = tagStatusSortedRecord.getRioNonRevCount();
		int mtafCount = tagStatusSortedRecord.getMtafCount();
		int govtCount = tagStatusSortedRecord.getGovtCount();
		int speedAgency = tagStatusSortedRecord.getSpeedAgency();
		int revPlanCount = tagStatusSortedRecord.getRevPlanCount();

		// default
		tagStatusSortedRecord.setMtaTagStatus(itagStatus);

		if ((speedAgency > 0) || (tbnrfpCnt > 0 && mtaNonRevCount == tbnrfpCnt) && (revPlanCount == 0)) {
			tagStatusSortedRecord.setMtaTagStatus(Constants.TS_INVALID);
		}
		if ((itagStatus != Constants.TS_INVALID)
				&& ((mtaNonRevCount - rioNonRevCount - tbnrfpCnt + mtafCount + govtCount) > 0)) {
			tagStatusSortedRecord.setMtaTagStatus(Constants.TS_GOOD);
		}
		log.info("MTA tag status set as: {}", tagStatusSortedRecord.getMtaTagStatus());
	}

	/**
	 * Building Mta Rio tag status
	 * 
	 * @param tagStatusSortedRecord
	 * 
	 */
	public void buildMtaRioStatus(TagStatusSortedRecord tagStatusSortedRecord) {
		int mtaSatus = tagStatusSortedRecord.getMtaTagStatus();
		int rioNonRevCount = tagStatusSortedRecord.getRioNonRevCount();

		int mtafCount = tagStatusSortedRecord.getMtafCount();
		int govtCount = tagStatusSortedRecord.getGovtCount();
		int mtaNonRevCount = tagStatusSortedRecord.getMtaNonRevCount();
		int tbnrfpCnt = tagStatusSortedRecord.getTbnrfpCount();

		if ((mtaSatus != Constants.TS_INVALID) && ((rioNonRevCount > 0)
				|| ((mtafCount + govtCount + mtaNonRevCount - tbnrfpCnt) > Constants.ZERO_VALUE))) {
			tagStatusSortedRecord.setRioTagStatus(Constants.TS_GOOD);
		} else {
			// default value
			tagStatusSortedRecord.setRioTagStatus(mtaSatus);
		}
		log.info("MTA RIO tag status set as: {}", tagStatusSortedRecord.getRioTagStatus());
	}

	/**
	 * Building PA tag status
	 * 
	 * @param tagStatusSortedRecord
	 */
	public void buildPaStatus(TagStatusSortedRecord tagStatusSortedRecord) {
		int itagStatus = tagStatusSortedRecord.getItagTagStatus();
		int speedAgency = tagStatusSortedRecord.getSpeedAgency();
		int paNonRevCount = tagStatusSortedRecord.getPaNonRevCount();

		// default value
		tagStatusSortedRecord.setPaTagStatus(itagStatus);

		if (speedAgency > 0) {
			tagStatusSortedRecord.setPaTagStatus(Constants.TS_INVALID);
		}
		if (itagStatus != Constants.TS_INVALID || paNonRevCount > 0) {
			tagStatusSortedRecord.setPaTagStatus(Constants.TS_GOOD);
		}

		else if (tagStatusSortedRecord.getAgencyId() == Constants.NYSTA_AGENCY_ID
				&& tagStatusSortedRecord.getNystaNonRevCount() > 0) {
			tagStatusSortedRecord.setPaTagStatus(Constants.FS_ZERO);
		}

		else if (tagStatusSortedRecord.getAgencyId() == Constants.MTA_AGENCY_ID
				&& tagStatusSortedRecord.getMtaNonRevCount() > 0) {
			tagStatusSortedRecord.setPaTagStatus(Constants.FS_ZERO);
		}

		log.info("PA tag status set as: {}", tagStatusSortedRecord.getPaTagStatus());
	}

	public void buildFtagStatus(TagStatusSortedRecord tagStatusSortedRecord) {

    	//tagStatusSortedRecord.setOptInOutFlag(iagDao.getOptInOutFlag(tagStatusSortedRecord));//Dependency on CRM table
		tagStatusSortedRecord.setOptInOutFlag(Constants.IN);
		//tagStatusSortedRecord.setOptInOutFlag(Constants.OUT);
		
		int primaryRebillPayType = tagStatusSortedRecord.getPrimaryRebillPayType();
		int rvkw_status = tagStatusSortedRecord.getAccountStatus();// AccountStatus
		int p_device_status = tagStatusSortedRecord.getDeviceStatus();
		if (tagStatusSortedRecord.getOptInOutFlag() == Constants.IN) {
			tagStatusSortedRecord.setFtagTagStatus(tagStatusSortedRecord.getItagTagStatus());
			if ((tagStatusSortedRecord.getItagTagStatus() != Constants.TS_INVALID)
				  || (primaryRebillPayType == Constants.RPT_VISA || primaryRebillPayType == Constants.RPT_AMEX
							|| primaryRebillPayType == Constants.RPT_DISC
							|| primaryRebillPayType == Constants.RPT_MASTERCARD
							|| primaryRebillPayType == Constants.RPT_ACHCPPT
							|| primaryRebillPayType == Constants.RPT_ACHSPPT)) 
			{
				tagStatusSortedRecord.setFtagTagStatus(Constants.TS_GOOD);
				p_device_status=Constants.TS_GOOD;
				//tagStatusSortedRecord.setDeviceStatus(p_device_status);
			} else if (tagStatusSortedRecord.getFtagTagStatus() == Constants.TS_GOOD || rvkw_status == 5 && tagStatusSortedRecord.getPlansStr()!=null) {
				if ((Integer
						.valueOf(tagStatusSortedRecord.getPlansStr().substring((int) (Math.floor(105 / 10) * 4 ), 44))
						& (int) Math.pow(2, Math.floorMod(105, 10))) > 0
						|| (Integer.valueOf(
								tagStatusSortedRecord.getPlansStr().substring((int) (Math.floor(104 / 10) * 4 ), 44))
								& (int) Math.pow(2, Math.floorMod(104, 10))) > 0
						|| (Integer.valueOf(
								tagStatusSortedRecord.getPlansStr().substring((int) (Math.floor(107 / 10) * 4 ), 44))
								& (int) Math.pow(2, Math.floorMod(107, 10))) > 0) {

					tagStatusSortedRecord.setFtagTagStatus(Constants.ZERO);
				} else
				{
 					tagStatusSortedRecord.setFtagTagStatus(Constants.TS_INVALID);
				}

			} else if ((primaryRebillPayType != Constants.RPT_VISA || primaryRebillPayType != Constants.RPT_AMEX
							|| primaryRebillPayType != Constants.RPT_DISC
							|| primaryRebillPayType != Constants.RPT_MASTERCARD
							|| primaryRebillPayType != Constants.RPT_ACHSPPT
							|| primaryRebillPayType != Constants.RPT_ACHSPPT)
					&& tagStatusSortedRecord.getFtagTagStatus() != Constants.ZERO || (rvkw_status != Constants.RVKW) && tagStatusSortedRecord.getPlansStr()!=null) {

				if ((Integer.valueOf(
						tagStatusSortedRecord.getPlansStr().substring((int) (Math.floor(105 / 10) * 4 ), 44))
						& (int) Math.pow(2, Math.floorMod(105, 10))) > 0
						|| (Integer.valueOf(
								tagStatusSortedRecord.getPlansStr().substring((int) (Math.floor(104 / 10) * 4 ), 44))
								& (int) Math.pow(2, Math.floorMod(104, 10))) > 0
						|| (Integer.valueOf(
								tagStatusSortedRecord.getPlansStr().substring((int) (Math.floor(107 / 10) * 4 ), 44))
								& (int) Math.pow(2, Math.floorMod(107, 10))) > 0) {

					tagStatusSortedRecord.setFtagTagStatus(Constants.ZERO);

				} else {
					tagStatusSortedRecord.setFtagTagStatus(Constants.TS_INVALID);
				}
			}
		}

			if (tagStatusSortedRecord.getOptInOutFlag() == Constants.OUT & p_device_status == Constants.DS_ACTIVE && tagStatusSortedRecord.getPlansStr()!=null) {

				if ((Integer
						.valueOf(tagStatusSortedRecord.getPlansStr().substring((int) (Math.floor(105 / 10) * 4 ), 44))
						& (int) Math.pow(2, Math.floorMod(105, 10))) > 0
						|| (Integer.valueOf(
								tagStatusSortedRecord.getPlansStr().substring((int) (Math.floor(104 / 10) * 4 ), 44))
								& (int) Math.pow(2, Math.floorMod(104, 10))) > 0
						|| (Integer.valueOf(
								tagStatusSortedRecord.getPlansStr().substring((int) (Math.floor(107 / 10) * 4 ), 44))
								& (int) Math.pow(2, Math.floorMod(107, 10))) > 0) {

					tagStatusSortedRecord.setFtagTagStatus(Constants.ZERO);

				} else {
					tagStatusSortedRecord.setFtagTagStatus(Constants.TS_INVALID);
				}

			}
			

			log.info("FTAG status set as: {}", tagStatusSortedRecord.getFtagTagStatus());
		

	}
}
