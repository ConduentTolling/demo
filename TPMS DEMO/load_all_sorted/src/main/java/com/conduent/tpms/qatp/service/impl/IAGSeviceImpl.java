package com.conduent.tpms.qatp.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.qatp.constants.Constants;
import com.conduent.tpms.qatp.dao.IAGDao;
import com.conduent.tpms.qatp.dto.AccountApiInfoDto;
import com.conduent.tpms.qatp.dto.CustomerInfoDto;
import com.conduent.tpms.qatp.dto.PlanPolicyInfoDto;
import com.conduent.tpms.qatp.dto.TagStatusSortedRecord;
import com.conduent.tpms.qatp.model.ConfigVariable;
import com.conduent.tpms.qatp.model.TCode;
import com.conduent.tpms.qatp.service.BuildTagStatusService;
import com.conduent.tpms.qatp.service.IAGSevice;
import com.conduent.tpms.qatp.service.PlanStringService;
import com.conduent.tpms.qatp.utility.MasterDataCache;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Implementation class for IAGSevice
 * 
 * @author Urvashi C
 *
 */

@Service
public class IAGSeviceImpl implements IAGSevice {

	private static final Logger log = LoggerFactory.getLogger(IAGSeviceImpl.class);

	@Autowired
	IAGDao iagDao;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private ConfigVariable configVariable;

	@Autowired
	private MasterDataCache masterDataCache;

	@Autowired
	private PlanStringService planStringService;

	@Autowired
	private BuildTagStatusService buildTagStatusService;

	List<TCode> codeList;

	boolean updaterecord;
	boolean recordforPlazaId;
	// 2variables load type , hist flag

	/**
	 * Initialize basic properties properties
	 */
	public void initialize() {

	}

	/**
	 * Process load tags
	 */
	@SuppressWarnings("null")
	@Override
	public String loadValidTagDetails(String startDeviceNo, String endDeviceNo, String loadType, String enableHistory) {
		String response = "Job Executed";
		try {

			log.info("Initialising...");
			initialize();

			/**
			 * Getting list of devices in range from V_DEVICE table.
			 */
			log.info("Getting list of devices in range from V_DEVICE table : {} and {}", startDeviceNo, endDeviceNo);
			List<TagStatusSortedRecord> tagStatusSortedAllRecordlist = iagDao.getDeviceInformation(startDeviceNo,
					endDeviceNo, loadType);

			/**
			 * Getting list of active tag accounts from V_ETC_ACCOUNT table.
			 */
			log.info("Getting list of active tag accounts from V_ETC_ACCOUNT table : {}", tagStatusSortedAllRecordlist);
			List<TagStatusSortedRecord> tagStatusSortedRecordList = iagDao
					.getETCAccountInfo(tagStatusSortedAllRecordlist, startDeviceNo, endDeviceNo);

			/**
			 * Iterate on tagStatusSortedRecordList to fetch ETC account details from FPMS
			 * API
			 */
			log.info("Iterate on tagStatusSortedRecordList to fetch ETC account details from FPMS : {}",
					tagStatusSortedRecordList);
			for (TagStatusSortedRecord tagStatusSortedRecord : tagStatusSortedRecordList) {
				log.info("Fetch {} account details from FPMS api..", tagStatusSortedRecord.getEtcAccountId());

				AccountApiInfoDto accountApiInfoDto = iagDao
						.getAccountInfoFromFPMS(String.valueOf(tagStatusSortedRecord.getEtcAccountId()));
				// callAccountApi(String.valueOf(tagStatusSortedRecord.getEtcAccountId()));

				if (accountApiInfoDto != null) {
					log.info(accountApiInfoDto.toString());
					tagStatusSortedRecord.setCurrentBalance(accountApiInfoDto.getAccountBalance());
					tagStatusSortedRecord.setRebillAmount(accountApiInfoDto.getRebillAmount());
					tagStatusSortedRecord.setRebillThreshold(accountApiInfoDto.getThresholdAmount());
					if (accountApiInfoDto.getFirstTollTimestamp() == null) {
						tagStatusSortedRecord.setFirstTollDate(null);
					} else {
						// future work
					}
					// tagStatusSortedRecord.setFirstTollDate(accountApiInfoDto.getFirstTollTimestamp()
					// != null? accountApiInfoDto.getFirstTollTimestamp().toLocalDate(): null);
					if (accountApiInfoDto.getAcctFinStatus() != null) {
						// get condition for low
						String finStatusStr = accountApiInfoDto.getAcctFinStatus();

						if (Constants.FS_GOOD_BAL.equals(finStatusStr)) {
							accountApiInfoDto.setAcctFinStatus("GOOD");
						} else if (Constants.FS_LOW_BAL.equals(finStatusStr)
								|| Constants.FS_LOW_.equals(finStatusStr)) {
							accountApiInfoDto.setAcctFinStatus("LOW");
						} else if (Constants.FS_ZERO_BAL.equals(finStatusStr)) {
							accountApiInfoDto.setAcctFinStatus("ZERO");
						} else if (Constants.FS_AUTOPAY_BAL.equals(finStatusStr)) {
							accountApiInfoDto.setAcctFinStatus("GOOD");// should go to 1
						}
						tagStatusSortedRecord.setFinancialStatus(masterDataCache.getTcodeIdForFinStatus(
								accountApiInfoDto.getAcctFinStatus(), Constants.ACCT_FIN_STATUS));
					}
					log.info("Financial Status...{}", tagStatusSortedRecord.getFinancialStatus());
				}

				log.info("Fetch {} policy plan details from policy_plan table..",
						tagStatusSortedRecord.getEtcAccountId());

				PlanPolicyInfoDto planPolicyInfoDto = iagDao.getPlanPolicyInfo(tagStatusSortedRecord.getEtcAccountId(),
						tagStatusSortedRecord.getDeviceNo());
				if (planPolicyInfoDto != null) {
					tagStatusSortedRecord.setAllPlans(planPolicyInfoDto.getLcAllPlans());
					tagStatusSortedRecord.setRevPlanCount(planPolicyInfoDto.getRevPlanCount());
					tagStatusSortedRecord.setItagInfo(planPolicyInfoDto.getItagInfo());
					tagStatusSortedRecord.setNystaNonRevCount(planPolicyInfoDto.getNystaNonRevCount());
					tagStatusSortedRecord.setNystaAnnualPlanCount(planPolicyInfoDto.getNystaAnnualPlanCount());
					tagStatusSortedRecord.setNystaPostPaidComlCount(planPolicyInfoDto.getNystaPostPaidComlCount());
					tagStatusSortedRecord.setNysbaNonRevCount(planPolicyInfoDto.getNysbaNonRevCount());
					tagStatusSortedRecord.setMtaNonRevCount(planPolicyInfoDto.getMtaNonRevCount());
					tagStatusSortedRecord.setPaNonRevCount(planPolicyInfoDto.getPaNonRevCount());
					tagStatusSortedRecord.setRioNonRevCount(planPolicyInfoDto.getRioNonRevCount());
					tagStatusSortedRecord.setPaSiBridgesCount(planPolicyInfoDto.getPaSiBridgesCount());
					tagStatusSortedRecord.setPaBridgesCount(planPolicyInfoDto.getPaBridgesCount());
					tagStatusSortedRecord.setPaCarpoolCount(planPolicyInfoDto.getPaCarpoolCount());

					tagStatusSortedRecord.setTbnrfpCount(planPolicyInfoDto.getTbnrfpCount());
					tagStatusSortedRecord.setMtafCount(planPolicyInfoDto.getMtafCount());
					tagStatusSortedRecord.setGovtCount(planPolicyInfoDto.getGovtCount());
					tagStatusSortedRecord.setSirCount(planPolicyInfoDto.getSirCount());
					tagStatusSortedRecord.setRrCount(planPolicyInfoDto.getRrCount());
					tagStatusSortedRecord.setBrCount(planPolicyInfoDto.getBrCount());
					tagStatusSortedRecord.setQrCount(planPolicyInfoDto.getQrCount());
				}

				String plansStr = iagDao.getPlanString(tagStatusSortedRecord.getEtcAccountId(),
						tagStatusSortedRecord.getDeviceNo());
				if (plansStr != null) {
					tagStatusSortedRecord.setPlansStr(plansStr);
					System.out.println(tagStatusSortedRecord.getPlansStr());
				}
				tagStatusSortedRecord = buildTagStatusService.buildTagStatus(tagStatusSortedRecord);
				log.info(tagStatusSortedRecord.toString());
				planStringService.buildAndSetPlanString(tagStatusSortedRecord);

				// setFTAGPlanInfo
				// int fTagPlanInfo=getFtagPlanInfo(tagStatusSortedRecord);
				tagStatusSortedRecord.setFtagPlanInfo(getFtagPlanInfo(tagStatusSortedRecord));
				log.info("FTAG PLAN INFO...{}", tagStatusSortedRecord.getFtagPlanInfo());

			}

			// Delete from Increment All Sorted Table
			if (loadType.equalsIgnoreCase(Constants.F) || loadType.equalsIgnoreCase(Constants.I)) {
				iagDao.delfromIncrAllsorted();
			}

			// Insertion in T_H_TAG_ALLSORTED Table
			if (enableHistory.equalsIgnoreCase(Constants.Y) && loadType.equalsIgnoreCase(Constants.F)) {
				insertintoTHTagAllSorted(tagStatusSortedAllRecordlist);
				log.info("Record inserted in T_H_TAG_STATUS_ALLSORTED....");
			}

			// Insertion in T_TAG_STATUS_ALLSORTED Table
			// if (enableHistory.equals(Constants.N) && loadType.equals(Constants.F)) {

			// if record already present print msg....else insert
			List<TagStatusSortedRecord> recordlist = new ArrayList<>();
			for (TagStatusSortedRecord record : tagStatusSortedRecordList) {
				String deviceno = record.getDeviceNo();
				TagStatusSortedRecord recordexists = iagDao.checkifrecordexists(deviceno);

				if (recordexists != null) {
					if (recordexists.getIsPrevalidated() == null) {
						recordexists.setIsPrevalidated(Constants.NO);
					}
					// check fields and update
					if (record.getAccountNo().equals(recordexists.getAccountNo())
							&& record.getAccountStatus().equals(recordexists.getAccountStatus())
							&& record.getFinancialStatus() == (recordexists.getFinancialStatus())
							&& record.getDeviceStatus() == (recordexists.getDeviceStatus())
							&& record.getRebillPayType().equals(recordexists.getRebillPayType())
							&& record.getAllPlans() == (recordexists.getAllPlans())
							&& record.getPrevalidationFlag().equals(recordexists.getPrevalidationFlag())
							&& record.getSpeedAgency() == (recordexists.getSpeedAgency())
							&& record.getRetailTagStatus() == (recordexists.getRetailTagStatus())
							&& record.getPostPaidStatus().equals(recordexists.getPostPaidStatus())
							&& record.getRevPlanCount() == (recordexists.getRevPlanCount())
							&& record.getMtaTagStatus() == (recordexists.getMtaTagStatus())
							&& record.getRioTagStatus() == (recordexists.getRioTagStatus())
							&& record.getItagTagStatus() == (recordexists.getItagTagStatus())
							&& record.getPaTagStatus() == (recordexists.getPaTagStatus())
							&& record.getTsCtrlStr().equals(recordexists.getTsCtrlStr())
							&& record.getMtaCtrlStr().equals(recordexists.getMtaCtrlStr())
							&& record.getRioCtrlStr().equals(recordexists.getRioCtrlStr())
							&& record.getCurrentBalance().equals(recordexists.getCurrentBalance())) {
						log.info("Record is not updated for device no..{}", deviceno);

					} else if (loadType.equalsIgnoreCase(Constants.F)) {
						log.info("Updating the existing record for device..{}", deviceno);
						iagDao.updaterecordinTTagAllSorted(record);
						
					}
				} else {
					
					recordlist.add(record);
					
				}
				
			}
			log.info("List of Records getting inserted :{}", recordlist);
			// log.info("Inserting record in T_TAG_ALL_SORTED table for device
			// no..{}",recordlist.indexOf(startDeviceNo));
			insertIntoTTagAllSorted(recordlist);

//			}

			if (loadType.equalsIgnoreCase(Constants.I)) {
				// Getting device Information from all sorted table
				for (TagStatusSortedRecord allsorted : tagStatusSortedRecordList) {

					int agency_id = allsorted.getAgencyId();
					String deviceNo = allsorted.getDeviceNo();
					log.info("Device No is...{}", deviceNo);

					List<TagStatusSortedRecord> deviceinfo = iagDao.getDeviceInfo(deviceNo);
					log.info("Device info for " + deviceNo + " from all sorted table....{}", deviceinfo);

					// checking if two list are same
					for (TagStatusSortedRecord sortedlist : deviceinfo) {
						if (allsorted.getAccountNo().equals(sortedlist.getAccountNo())
								&& allsorted.getAccountStatus().equals(sortedlist.getAccountStatus())
								&& allsorted.getDeviceNo().equals(sortedlist.getDeviceNo())
								&& allsorted.getFinancialStatus() == sortedlist.getFinancialStatus()
								&& allsorted.getDeviceStatus() == sortedlist.getDeviceStatus()
								&& allsorted.getRebillPayType().equals(sortedlist.getRebillPayType())
								&& allsorted.getAllPlans() == sortedlist.getAllPlans()
								&& allsorted.getPrevalidationFlag().equals(sortedlist.getPrevalidationFlag()) &&
								// allsorted.getOptInOutFlag().equals(sortedlist.getOptInOutFlag()) &&
								allsorted.getSpeedAgency() == sortedlist.getSpeedAgency()
								&& allsorted.getRetailTagStatus() == sortedlist.getRetailTagStatus() &&
								// allsorted.getThwyAcct().equals(sortedlist.getThwyAcct()) &&
								// allsorted.getFirstTollDate().equals(sortedlist.getFirstTollDate()) &&
								allsorted.getPostPaidStatus().equals(sortedlist.getPostPaidStatus())
								&& allsorted.getRevPlanCount() == sortedlist.getRevPlanCount()
								&& allsorted.getMtaTagStatus() == sortedlist.getMtaTagStatus()
								&& allsorted.getRioTagStatus() == sortedlist.getRioTagStatus()
								&& allsorted.getItagTagStatus() == sortedlist.getItagTagStatus()
								&& allsorted.getFtagTagStatus() == sortedlist.getFtagTagStatus()
								&& allsorted.getTsCtrlStr().equals(sortedlist.getTsCtrlStr())
								&& allsorted.getMtaCtrlStr().equals(sortedlist.getMtaCtrlStr())
								&& allsorted.getRioCtrlStr().equals(sortedlist.getRioCtrlStr())
								&& allsorted.getPaTagStatus() == sortedlist.getPaTagStatus()
								&& allsorted.getCurrentBalance().equals(sortedlist.getCurrentBalance())) {
							log.info("The two list are same...");
						} else {
							log.info("The two list are not same...");
							//iagDao.updateTTagStatusallsorted(deviceNo, tagStatusSortedRecordList);
							if (allsorted.getAccountNo().equals(sortedlist.getAccountNo())) {
								iagDao.updaterecordinTTagAllSorted(allsorted);
							}
							log.info("Record for device no {} got updated", deviceNo);
						}
						
						// check for mta
						if ((allsorted.getMtaTagStatus() != sortedlist.getMtaTagStatus()))
						//		|| (allsorted.getAgencyId() == Constants.MTA_AGENCY_ID)) // &&
						// ((sortedlist.getMtaTagStatus() == Constants.TS_INVALID)
						// ||(sortedlist.getMtaTagStatus() == Constants.TS_LOST_STOLEN)))
						{
							recordforPlazaId = iagDao.checkIfRecordExistsforPlazaID(Constants.MTA_PLAZA_ID, deviceNo);

							if (recordforPlazaId == true) {
								updaterecord = iagDao.updateTIncrALlSortedforMTA(deviceNo, agency_id, deviceinfo);
								log.info("Record for MTA device no {} got updated in T_INCR_TAG_STATUS_ALLSORTED...",
										deviceNo);
							} else {
								log.info("Inserting MTA Record in T_INCR_TAG_STATUS_ALLSORTED Table... ");
								iagDao.insertIntoTIncrTTagAllSortedforMTA(deviceinfo);
								log.info("Record for MTA got inserted in T_INCR_TAG_STATUS_ALLSORTED...");
							}
						}

						// check for rio
						if ((allsorted.getRioTagStatus() != sortedlist.getRioTagStatus()))
						//		|| (allsorted.getAgencyId() == Constants.MTA_AGENCY_ID))// &&
						// ((sortedlist.getRioTagStatus() == Constants.TS_INVALID)
						// ||(sortedlist.getRioTagStatus() == Constants.TS_LOST_STOLEN)))
						{
							recordforPlazaId = iagDao.checkIfRecordExistsforPlazaID(Constants.RIO_PLAZA_ID, deviceNo);
							if (recordforPlazaId == true) {
								updaterecord = iagDao.updateTIncrALlSortedforRIO(deviceNo, agency_id, deviceinfo);
								log.info("Record for RIO device no {} got updated in T_INCR_TAG_STATUS_ALLSORTED...",
										deviceNo);
							} else {
								log.info("Inserting RIO Record in T_INCR_TAG_STATUS_ALLSORTED Table... ");
								iagDao.insertIntoTIncrTTagAllSortedforRIO(deviceinfo);
								log.info("Record for RIO got inserted in T_INCR_TAG_STATUS_ALLSORTED...");
							}
						}

						// check for pa
						if ((allsorted.getPaTagStatus() != sortedlist.getPaTagStatus())
								|| (allsorted.getAllPlans() != sortedlist.getAllPlans()))
						//		|| (allsorted.getAgencyId() == Constants.PA_AGENCY_ID)) 
						{
							recordforPlazaId = iagDao.checkIfRecordExistsforPlazaID(Constants.ITAG_PLAZA_ID, deviceNo);

							if (recordforPlazaId == true) {
								updaterecord = iagDao.updateTIncrALlSortedforPA(deviceNo, agency_id, deviceinfo);
								log.info("Record for PA device no {} got updated in T_INCR_TAG_STATUS_ALLSORTED...",
										deviceNo);
							} else {
								log.info("Inserting PA Record in T_INCR_TAG_STATUS_ALLSORTED Table... ");
								iagDao.insertIntoTIncrTTagAllSortedforPA(deviceinfo);
								log.info("Record for PA got inserted in T_INCR_TAG_STATUS_ALLSORTED...");
							}
						}

//						// check for FTAG
//						if ((allsorted.getFtagTagStatus() != sortedlist.getFtagTagStatus()))
//						//		|| (allsorted.getAgencyId() == Constants.MTA_AGENCY_ID)) 
//						{
//							int plazaId = masterDataCache.getPlazabyorder(allsorted.getAgencyId());
//							recordforPlazaId = iagDao.checkIfRecordExistsforPlazaID(plazaId, deviceNo);
//
//							if (recordforPlazaId == true) {
//								updaterecord = iagDao.updateTIncrALlSortedforFTAG(deviceNo, agency_id, deviceinfo,
//										plazaId);
//								log.info("Record for FTAG device no {} got updated in T_INCR_TAG_STATUS_ALLSORTED...",
//										deviceNo);
//							} else {
//								log.info("Inserting FTAG Record in T_INCR_TAG_STATUS_ALLSORTED Table... ");
//								iagDao.insertIntoTIncrTTagAllSortedforFTAG(deviceinfo, plazaId);
//								log.info("Record for FTAG got inserted in T_INCR_TAG_STATUS_ALLSORTED...");
//							}
//						}

						/*
						 * else { iagDao.insertIntoTIncrTTagAllSortedforPA(deviceinfo);
						 * log.info("Record for PA got inserted in T_INCR_TAG_STATUS_ALLSORTED..."); }
						 */

					}
				}

			}
			// return response;
		} catch (Exception e) {
			log.info("Exception Occured:{}", e);
			e.printStackTrace();
		}
		return response;

	}

	private void insertIntoTTagAllSorted(List<TagStatusSortedRecord> recordlist) throws SQLException {
		iagDao.insertIntoTTagAllSorted(recordlist);
	}

	private void insertintoTHTagAllSorted(List<TagStatusSortedRecord> activeTagsList) {
		iagDao.insertintoTHTagAllSorted(activeTagsList);
	}

	/**
	 * Call FPMS Account Info API
	 * 
	 * @param etcAccountId
	 * @return AccountApiInfoDto
	 */
	public AccountApiInfoDto callAccountApi(String etcAccountId) {
		try {
			CustomerInfoDto customerInfoDto = new CustomerInfoDto();
			customerInfoDto.setEtcAccountId(etcAccountId);
			System.out.println(configVariable.getAccountApiUri());
			System.out.println(customerInfoDto.toString());
			ResponseEntity<String> result = restTemplate.postForEntity(configVariable.getAccountApiUri(),
					customerInfoDto, String.class);
			log.info("result.getBody() " + result.getBody());
			if (result.getStatusCodeValue() == 200) {
				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				Gson gson = new Gson();
				return gson.fromJson(jsonObject.getAsJsonObject("result"), AccountApiInfoDto.class);
			}
		} catch (Exception e) {
			log.info("Exception: {} while get balance API call for ETC Account ID: {}", e.getMessage(), etcAccountId);
			return null;
		}
		return null;
	}

	public int getFtagPlanInfo(TagStatusSortedRecord tagStatusSortedRecord) {

		int ftag_plan_info = tagStatusSortedRecord.getFtagPlanInfo();
		if (tagStatusSortedRecord.getPlansStr() != null) {
			try {

				if ((Integer
						.valueOf(tagStatusSortedRecord.getPlansStr().substring((int) (Math.floor(105 / 10) * 4), 44))
						& (int) Math.pow(2, Math.floorMod(105, 10))) > 0) {
					ftag_plan_info = ftag_plan_info + 1;
				} else if ((Integer
						.valueOf(tagStatusSortedRecord.getPlansStr().substring((int) (Math.floor(104 / 10) * 4), 44))
						& (int) Math.pow(2, Math.floorMod(104, 10))) > 0) {
					ftag_plan_info = ftag_plan_info + 2;
				} else if ((Integer
						.valueOf(tagStatusSortedRecord.getPlansStr().substring((int) (Math.floor(107 / 10) * 4), 44))
						& (int) Math.pow(2, Math.floorMod(107, 10))) > 0) {
					ftag_plan_info = ftag_plan_info + 4;
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// return null;
			}
		}
		return ftag_plan_info;

	}

}
