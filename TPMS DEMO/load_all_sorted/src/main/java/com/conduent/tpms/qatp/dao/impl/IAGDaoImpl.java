package com.conduent.tpms.qatp.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.config.LoadJpaQueries;
import com.conduent.tpms.qatp.constants.Constants;
import com.conduent.tpms.qatp.dao.IAGDao;
import com.conduent.tpms.qatp.dto.AccountApiInfoDto;
import com.conduent.tpms.qatp.dto.IncrTagStatusRecord;
import com.conduent.tpms.qatp.dto.PlanPolicyInfoDto;
import com.conduent.tpms.qatp.dto.TagStatusSortedRecord;
import com.conduent.tpms.qatp.model.VDeviceDto;
import com.conduent.tpms.qatp.model.VETCAccount;
import com.google.common.base.Stopwatch;


/**
 * Implementation class for IAGDao
 * 
 * @author Urvashi C
 *
 */
@Repository
public class IAGDaoImpl implements IAGDao {

	private static final Logger log = LoggerFactory.getLogger(IAGDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	TimeZoneConv timezoneConv;

	/**
	 * Get device information from V_DEVICE table
	 *
	 * @return List<VDeviceDto>
	 */
	@Override
	public List<TagStatusSortedRecord> getDeviceInformation(String startDeviceNo, String endDeviceNo, String loadType)
			throws SQLException {
		List<VDeviceDto> listOfObj = new ArrayList<VDeviceDto>();
		List<TagStatusSortedRecord> tagStatusSortedAllRecordlist = new ArrayList<TagStatusSortedRecord>();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();

		try {
			//need to fetch new columns here
			paramSource.addValue(Constants.START_DEVICE_NO, startDeviceNo);
			paramSource.addValue(Constants.END_DEVICE_NO, endDeviceNo);
			if (loadType.equalsIgnoreCase(Constants.F)) {

				String query = LoadJpaQueries.getQueryById("GET_DEVICE_INFO");

				listOfObj = namedJdbcTemplate.query(query, paramSource, BeanPropertyRowMapper.newInstance(VDeviceDto.class));
				log.info(listOfObj.toString());
			} else if (loadType.equalsIgnoreCase(Constants.I)) {

				String query = LoadJpaQueries.getQueryById("GET_DEVICE_INFO_INCR_NEW");


				listOfObj = namedJdbcTemplate.query(query, paramSource,
						BeanPropertyRowMapper.newInstance(VDeviceDto.class));
				log.info(listOfObj.toString());
			}
			for (VDeviceDto vDeviceDto : listOfObj) {
				// if(vDeviceDto.getDeviceStatus() == Constants.ACTIVE) { // change to active
				TagStatusSortedRecord tagStatusSortedRecord = new TagStatusSortedRecord();
				tagStatusSortedRecord.setEtcAccountId(vDeviceDto.getEtcAccountId());
				tagStatusSortedRecord.setDeviceNo(vDeviceDto.getDeviceNo());
				tagStatusSortedRecord.setRetailTagStatus(vDeviceDto.getRetailTagStatus());
				tagStatusSortedRecord.setDeviceTypeId(vDeviceDto.getDeviceTypeId());
				tagStatusSortedRecord.setDeviceStatus(vDeviceDto.getDeviceStatus());
				if (vDeviceDto.getIsPrevalidated() == null) {
					tagStatusSortedRecord.setIsPrevalidated(Constants.NO);
				} else {
					tagStatusSortedRecord.setIsPrevalidated(vDeviceDto.getIsPrevalidated());
				}
				tagStatusSortedRecord.setPrevalidationFlag(tagStatusSortedRecord.getIsPrevalidated());
				
				tagStatusSortedRecord.setTagClass(vDeviceDto.getModelClass());
				tagStatusSortedRecord.setTagType(vDeviceDto.getDeviceType());
				tagStatusSortedRecord.setTagProtocol(vDeviceDto.getTagProtocol());
				tagStatusSortedRecord.setTagMount(vDeviceDto.getMountType());


				tagStatusSortedAllRecordlist.add(tagStatusSortedRecord);
				// }
				log.info("Prevalidation flag...{}", tagStatusSortedRecord.getIsPrevalidated());
			}
			log.debug(tagStatusSortedAllRecordlist.toString());
		} catch (DataAccessException e) {
			log.info("Error while getting list of VDeviceDto : {}", e.getMessage());
			e.printStackTrace();
		}
		return tagStatusSortedAllRecordlist;
	}

	@Override
	public List<TagStatusSortedRecord> getDeviceInfo(String deviceNo) throws SQLException {

		List<TagStatusSortedRecord> listOfObj = new ArrayList<TagStatusSortedRecord>();
		List<TagStatusSortedRecord> tagStatusSortedAllRecordlist = new ArrayList<TagStatusSortedRecord>();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();

		paramSource.addValue(Constants.DEVICE_NO, deviceNo);

		try {
			String query = LoadJpaQueries.getQueryById("SELECT_INCR_FROM_T_TAG_ALLSORTED");

			listOfObj = namedJdbcTemplate.query(query, paramSource,
					BeanPropertyRowMapper.newInstance(TagStatusSortedRecord.class));

			for (TagStatusSortedRecord allsortedlist : listOfObj) {
				TagStatusSortedRecord tagStatusSortedRecord = new TagStatusSortedRecord();
				tagStatusSortedRecord.setDeviceNo(allsortedlist.getDeviceNo());
				tagStatusSortedRecord.setAccountNo(allsortedlist.getAccountNo());
				tagStatusSortedRecord.setAgencyId(allsortedlist.getAgencyId());
				tagStatusSortedRecord.setAccountStatus(allsortedlist.getAccountStatus());
				tagStatusSortedRecord.setFinancialStatus(allsortedlist.getFinancialStatus());
				tagStatusSortedRecord.setDeviceStatus(allsortedlist.getDeviceStatus());
				tagStatusSortedRecord.setRebillPayType(allsortedlist.getRebillPayType());
				tagStatusSortedRecord.setAllPlans(allsortedlist.getAllPlans());
				if (allsortedlist.getIsPrevalidated() == null) {
					tagStatusSortedRecord.setIsPrevalidated(Constants.NO);
				} else {
					tagStatusSortedRecord.setIsPrevalidated(allsortedlist.getIsPrevalidated());
				}
				tagStatusSortedRecord.setPrevalidationFlag(tagStatusSortedRecord.getIsPrevalidated());
				tagStatusSortedRecord.setOptInOutFlag(allsortedlist.getOptInOutFlag());
				tagStatusSortedRecord.setSpeedAgency(allsortedlist.getSpeedAgency());
				tagStatusSortedRecord.setRetailTagStatus(allsortedlist.getRetailTagStatus());
				tagStatusSortedRecord.setFirstTollDate(allsortedlist.getFirstTollDate());
				tagStatusSortedRecord.setPostPaidStatus(allsortedlist.getPostPaidStatus());
				tagStatusSortedRecord.setThwyAcct(allsortedlist.getThwyAcct());
				tagStatusSortedRecord.setRevPlanCount(allsortedlist.getRevPlanCount());
				tagStatusSortedRecord.setAcctSuspFlag(allsortedlist.getAcctSuspFlag());
				tagStatusSortedRecord.setRioCtrlStr(allsortedlist.getRioCtrlStr());
				tagStatusSortedRecord.setRioTagStatus(allsortedlist.getRioTagStatus());
				tagStatusSortedRecord.setMtaCtrlStr(allsortedlist.getMtaCtrlStr());
				tagStatusSortedRecord.setMtaTagStatus(allsortedlist.getMtaTagStatus());
				tagStatusSortedRecord.setTsCtrlStr(allsortedlist.getTsCtrlStr());
				tagStatusSortedRecord.setItagTagStatus(allsortedlist.getItagTagStatus());
				tagStatusSortedRecord.setPaTagStatus(allsortedlist.getPaTagStatus());
				tagStatusSortedRecord.setFtagTagStatus(allsortedlist.getFtagTagStatus());
				tagStatusSortedRecord.setCurrentBalance(allsortedlist.getCurrentBalance());
				tagStatusSortedRecord.setAllPlans(allsortedlist.getAllPlans());
				tagStatusSortedRecord.setTagProtocol(allsortedlist.getTagProtocol());
				tagStatusSortedRecord.setTagType(allsortedlist.getTagType());
				tagStatusSortedRecord.setTagMount(allsortedlist.getTagMount());
				tagStatusSortedRecord.setTagClass(allsortedlist.getTagClass());
				tagStatusSortedAllRecordlist.add(tagStatusSortedRecord);

				log.info("Agency id is..{}", tagStatusSortedRecord.getAgencyId());
			}
			log.debug(tagStatusSortedAllRecordlist.toString());
		}

		catch (DataAccessException e) {
			log.info("Error while getting list of VDeviceDto : {}", e.getMessage());
			e.printStackTrace();
		}
		return tagStatusSortedAllRecordlist;
	}

	/**
	 * Get account information from V_ETC_ACCOUNT table
	 *
	 * @return List<TagStatusSortedRecord>
	 */

	@Override
	public List<TagStatusSortedRecord> getETCAccountInfo(List<TagStatusSortedRecord> listOfObj,String startDeviceNo,String endDeviceNo) throws SQLException {

		List<TagStatusSortedRecord> tagStatusSortedActiveRecordlist = new ArrayList<TagStatusSortedRecord>();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		

		if (listOfObj.size() > 0) {
			
			
			paramSource.addValue(Constants.START_DEVICE_NO, startDeviceNo);
			paramSource.addValue(Constants.END_DEVICE_NO, endDeviceNo);

			String query = LoadJpaQueries.getQueryById("SELECT_ETC_ACC_INFO");
					
			List<VETCAccount> activeVEtcAccountList = namedJdbcTemplate.query(query, paramSource,
					BeanPropertyRowMapper.newInstance(VETCAccount.class));
			
			log.info("Active ETC ACOUNT List from V_ETC_ACOUNT Table:{}", activeVEtcAccountList);

			if (activeVEtcAccountList != null || activeVEtcAccountList.size() > 0) {
				for (VETCAccount vetcAccount : activeVEtcAccountList) {
					for (TagStatusSortedRecord tagStatusSortedRecord : listOfObj) {
						if (vetcAccount.getEtcAccountId() == tagStatusSortedRecord.getEtcAccountId()) {
							// if
							// (ifDeviceSpeedStatusIsValid(vetcAccount,tagStatusSortedRecord.getDeviceNo()))
							ifDeviceSpeedStatusIsValid(vetcAccount, tagStatusSortedRecord.getDeviceNo());
							// {
							if (vetcAccount.getAcctActStatus() == null) {
								tagStatusSortedRecord.setAccountStatus(Constants.ZERO);
							} else {
								tagStatusSortedRecord.setAccountStatus(vetcAccount.getAcctActStatus());
							}

							tagStatusSortedRecord.setAccountType(vetcAccount.getAccountType());
							tagStatusSortedRecord.setAccountTypeCd(vetcAccount.getAccountTypeCd());
							tagStatusSortedRecord.setAccountNo(vetcAccount.getAccountNo());
							tagStatusSortedRecord.setAgencyId(vetcAccount.getAgencyId());
							if (vetcAccount.getPostPaidStatus() == null) {
								tagStatusSortedRecord.setPostPaidStatus(Constants.ZERO);
							} else {
								tagStatusSortedRecord.setPostPaidStatus(vetcAccount.getPostPaidStatus());
							}
							if (vetcAccount.getRebillThreshold() == null) {
								tagStatusSortedRecord.setRebillThreshold(Constants.D_ZERO);
							} else {
								tagStatusSortedRecord.setRebillThreshold(vetcAccount.getRebillThreshold());
							}
							if (vetcAccount.getPrimaryRebillPayType() == null) {
								tagStatusSortedRecord.setRebillPayType(Constants.ZERO);
							} else {
								tagStatusSortedRecord.setRebillPayType(vetcAccount.getPrimaryRebillPayType());
							}
							if (vetcAccount.getAccountSuspended() == null) {
								tagStatusSortedRecord.setAcctSuspFlag(Constants.STR_N);
							} else {
								tagStatusSortedRecord.setAcctSuspFlag(vetcAccount.getAccountSuspended());
							}

							tagStatusSortedRecord.setIsCompanionTag(getCompanionTagStatus(
									tagStatusSortedRecord.getDeviceTypeId(), tagStatusSortedRecord.getAgencyId()));
							tagStatusSortedRecord.setTagOwningAgency(vetcAccount.getAgencyId());
							tagStatusSortedRecord.setThwyAcct(vetcAccount.getAgencyReference());
							// tagStatusSortedRecord.setSpeedAgency(vetcAccount.getAgencyId()); TBD with PB

							tagStatusSortedActiveRecordlist.add(tagStatusSortedRecord);
							// }
						}
					}
				}
			}
		}
		return tagStatusSortedActiveRecordlist;
	}

	/**
	 * Method used to check the device speed from V_SPEED_STATUS table
	 *
	 * @return boolean(true or false)
	 */
	private boolean ifDeviceSpeedStatusIsValid(VETCAccount accountApiInfoDto, String deviceNo) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		long etcAccountId = accountApiInfoDto.getEtcAccountId();
		int acctType = accountApiInfoDto.getAccountType();
		boolean isValidSpeedStatus = false;

		try {
			String query = "SELECT Count(*) FROM CRM.V_SPEED_STATUS WHERE  etc_account_id = " + etcAccountId
					+ " AND current_date BETWEEN start_date AND end_date AND ( speed_status LIKE 'REVOKED' OR speed_status LIKE 'SUSP%' ) AND Decode ('"
					+ acctType + "', 1, 'PRIVATE',device_no) = Decode ('" + acctType + "', 1, 'PRIVATE','" + deviceNo
					+ "')";
			int count = namedJdbcTemplate.queryForObject(query, paramSource, Integer.class);
			if (count > 0) {
				isValidSpeedStatus = true;
			} else {
				isValidSpeedStatus = false;
			}
		} catch (DataAccessException e) {
			// e.printStackTrace();
			isValidSpeedStatus = false;
		}
		log.info("Device no. {} with ETC account Id {} has valid speed? : {}", deviceNo, etcAccountId,
				isValidSpeedStatus);
		return isValidSpeedStatus;
	}

	/**
	 * Method used to get the companion tag status from V_DEVICE_MODEL table
	 *
	 * @return String(YES='Y'/NO='N')
	 */
	private String getCompanionTagStatus(int DEVICE_TYPE_ID, int AGENCY_ID) {
		String comp = "N";
		String queryForCompStatus = null;
		try {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			queryForCompStatus = "SELECT DECODE(device_type, 'OTHAGNTAG', 'Y', 'N') FROM CRM.V_DEVICE_MODEL WHERE DEVICE_TYPE_ID ="
					+ DEVICE_TYPE_ID + " AND AGENCY_ID =" + AGENCY_ID;
			comp = namedJdbcTemplate.queryForObject(queryForCompStatus, paramSource, String.class);
		} catch (DataAccessException e) {
			log.error("Error while executing SQL : {}", queryForCompStatus);
			return comp;
			// e.printStackTrace();
		}

		return comp;
	}

	/**
	 * Get plan policy info for device from V_ACCOUNT_PLAN_DETAIL and V_PLAN_POLICY
	 * tables
	 *
	 * @return PlanPolicyInfoDto
	 */
	@Override
	public PlanPolicyInfoDto getPlanPolicyInfo(long LC_ETC_ACCOUNT_ID, String LC_DEVICE_NO) {

		PlanPolicyInfoDto planPolicyInfoDto = new PlanPolicyInfoDto();
		try {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(Constants.LC_ETC_ACCOUNT_ID, LC_ETC_ACCOUNT_ID);
			paramSource.addValue(Constants.LC_DEVICE_NO, LC_DEVICE_NO);
			String query = LoadJpaQueries.getQueryById("SELECT_PLAN_POLICY");

			planPolicyInfoDto = namedJdbcTemplate.queryForObject(query, paramSource,
					BeanPropertyRowMapper.newInstance(PlanPolicyInfoDto.class));
		} catch (DataAccessException e) {
			return planPolicyInfoDto;
		}
		return planPolicyInfoDto;
	}

	@Override
	public boolean updateTIncrALlSortedforMTA(String deviceno, int agency_id, List<TagStatusSortedRecord> list) {

		boolean updatestatus = false;
		int tagstatusrecord = 0;

		try {
			for (TagStatusSortedRecord record : list) {
				MapSqlParameterSource paramSource = new MapSqlParameterSource();
				paramSource.addValue(Constants.DEVICE_NO, deviceno);
				paramSource.addValue(Constants.AGENCY_ID, agency_id);
				String query = LoadJpaQueries.getQueryById("UPDATE_INCR_ALL_SORTED_BY_DEVICENO_FORMTA");

				paramSource.addValue(Constants.OLD_TAG_STATUS, record.getMtaTagStatus());
				paramSource.addValue(Constants.OLD_CONTROL_STRING, record.getMtaCtrlStr());
				paramSource.addValue(Constants.PLAZA_ID, Constants.MTA_PLAZA_ID);
				paramSource.addValue(Constants.UPDATE_TS, Timestamp.valueOf(timezoneConv.currentTime()));

				tagstatusrecord = namedJdbcTemplate.update(query, paramSource);
				log.info("Number of rows updated in T_INCR_TAG_STATUS_ALLSORTED for MTA: {}", tagstatusrecord);
			}
			if (tagstatusrecord == 0) {
				updatestatus = false;
				log.info("Updated status is {}", tagstatusrecord);
			} else {
				updatestatus = true;
			}
		} catch (DataAccessException e) {
			log.info("Error while getting list of VDeviceDto : {}", e.getMessage());
			e.printStackTrace();
		}

		return updatestatus;
	}

	@Override
	public boolean updateTIncrALlSortedforRIO(String deviceno, int agency_id, List<TagStatusSortedRecord> list) {

		boolean updatestatus = false;
		int tagstatusrecord = 0;

		try {
			for (TagStatusSortedRecord record : list) {
				MapSqlParameterSource paramSource = new MapSqlParameterSource();
				paramSource.addValue(Constants.DEVICE_NO, deviceno);
				paramSource.addValue(Constants.AGENCY_ID, agency_id);
				String query = LoadJpaQueries.getQueryById("UPDATE_INCR_ALL_SORTED_BY_DEVICENO_FORRIO");

				paramSource.addValue(Constants.OLD_TAG_STATUS, record.getRioTagStatus());
				paramSource.addValue(Constants.OLD_CONTROL_STRING, record.getRioCtrlStr());
				paramSource.addValue(Constants.PLAZA_ID, Constants.RIO_PLAZA_ID);
				paramSource.addValue(Constants.UPDATE_TS, Timestamp.valueOf(timezoneConv.currentTime()));

				tagstatusrecord = namedJdbcTemplate.update(query, paramSource);
				log.info("Number of rows updated in T_INCR_TAG_STATUS_ALLSORTED for RIO: {}", tagstatusrecord);
			}
			if (tagstatusrecord == 0) {
				updatestatus = false;
				log.info("Updated status is {}", tagstatusrecord);
			} else {
				updatestatus = true;
			}
		} catch (DataAccessException e) {
			log.info("Error while getting list of VDeviceDto : {}", e.getMessage());
			e.printStackTrace();
		}
		return updatestatus;
	}

	@Override
	public boolean updateTIncrALlSortedforPA(String deviceno, int agency_id, List<TagStatusSortedRecord> list) {

		boolean updatestatus = false;
		int tagstatusrecord = 0;

		try {
			for (TagStatusSortedRecord record : list) {
				MapSqlParameterSource paramSource = new MapSqlParameterSource();
				paramSource.addValue(Constants.DEVICE_NO, deviceno);
				paramSource.addValue(Constants.AGENCY_ID, agency_id);
				String query = LoadJpaQueries.getQueryById("UPDATE_INCR_ALL_SORTED_BY_DEVICENO_FORPA");

				paramSource.addValue(Constants.OLD_TAG_STATUS, record.getPaTagStatus());
				paramSource.addValue(Constants.OLD_CONTROL_STRING, null);
				paramSource.addValue(Constants.PLAZA_ID, Constants.ITAG_PLAZA_ID);
				paramSource.addValue(Constants.UPDATE_TS, Timestamp.valueOf(timezoneConv.currentTime()));

				tagstatusrecord = namedJdbcTemplate.update(query, paramSource);
				log.info("Number of rows updated in T_INCR_TAG_STATUS_ALLSORTED for PA: {}", tagstatusrecord);
			}
			if (tagstatusrecord == 0) {
				updatestatus = false;
				log.info("Updated status is {}", tagstatusrecord);
			} else {
				updatestatus = true;
			}
		} catch (DataAccessException e) {
			log.info("Error while getting list of VDeviceDto : {}", e.getMessage());
			e.printStackTrace();
		}
		return updatestatus;
	}

//	//For FTAG
//	@Override
//	public boolean updateTIncrALlSortedforFTAG(String deviceno, int agency_id, List<TagStatusSortedRecord> list, int plazaId) {
//
//		boolean updatestatus = false;
//		int tagstatusrecord = 0;
//
//		try {
//			for (TagStatusSortedRecord record : list) {
//				MapSqlParameterSource paramSource = new MapSqlParameterSource();
//				paramSource.addValue(Constants.DEVICE_NO, deviceno);
//				paramSource.addValue(Constants.AGENCY_ID, agency_id);
//				String query = LoadJpaQueries.getQueryById("UPDATE_INCR_ALL_SORTED_BY_DEVICENO_FOR_FTAG");
//
//				paramSource.addValue(Constants.OLD_TAG_STATUS, record.getFtagTagStatus());
//				paramSource.addValue(Constants.OLD_CONTROL_STRING, null);//ftag ctrl str
//				paramSource.addValue(Constants.PLAZA_ID, plazaId);
//				paramSource.addValue(Constants.UPDATE_TS, Timestamp.valueOf(timezoneConv.currentTime()));
//
//				tagstatusrecord = namedJdbcTemplate.update(query, paramSource);
//				log.info("Number of rows updated in T_INCR_TAG_STATUS_ALLSORTED for FTAG: {}", tagstatusrecord);
//			}
//			if (tagstatusrecord == 0) {
//				updatestatus = false;
//				log.info("Updated status is {}", tagstatusrecord);
//			} else {
//				updatestatus = true;
//			}
//		} catch (DataAccessException e) {
//			log.info("Error while getting list of VDeviceDto : {}", e.getMessage());
//			e.printStackTrace();
//		}
//
//		return updatestatus;
//	}
	
	@Override
	public void updateTTagStatusallsorted(String deviceno, List<TagStatusSortedRecord> list) {

		int tagstatusrecord = 0;

		try {
			for (TagStatusSortedRecord record : list) {
				MapSqlParameterSource paramSource = new MapSqlParameterSource();
				paramSource.addValue(Constants.DEVICE_NO, deviceno);
				String query = LoadJpaQueries.getQueryById("UPDATE_ALL_SORTED_BY_DEVICENO");

				// do we have to add all fields?
				paramSource.addValue(Constants.ACCOUNT_NO, record.getAccountNo());
				paramSource.addValue(Constants.ACCOUNT_STATUS, record.getAccountStatus());
				//paramSource.addValue(Constants.LC_DEVICE_NO, record.getDeviceNo());
				paramSource.addValue(Constants.FINANCIAL_STATUS, record.getFinancialStatus());
				paramSource.addValue(Constants.DEVICE_STATUS, record.getDeviceStatus());
				paramSource.addValue(Constants.REBILL_PAY_TYPE, record.getRebillPayType());
				paramSource.addValue(Constants.ALL_PLANS, record.getAllPlans());
				paramSource.addValue(Constants.PREVALIDATION_FLAG, record.getPrevalidationFlag());
				paramSource.addValue(Constants.SPEED_AGENCY, record.getSpeedAgency());
				paramSource.addValue(Constants.RETAIL_TAG_STATUS, record.getRetailTagStatus());
				paramSource.addValue(Constants.POST_PAID_STATUS, record.getPostPaidStatus());
				paramSource.addValue(Constants.REV_PLAN_COUNT, record.getRevPlanCount());
				paramSource.addValue(Constants.MTA_TAG_STATUS, record.getMtaTagStatus());
				paramSource.addValue(Constants.RIO_TAG_STATUS, record.getRioTagStatus());
				paramSource.addValue(Constants.ITAG_TAG_STATUS, record.getItagTagStatus());
				paramSource.addValue(Constants.PA_TAG_STATUS, record.getPaTagStatus());
				paramSource.addValue(Constants.FTAG_TAG_STATUS, record.getFtagTagStatus());
				paramSource.addValue(Constants.TS_CTRL_STR, record.getTsCtrlStr());
				paramSource.addValue(Constants.MTA_CTRL_STR, record.getMtaCtrlStr());
				paramSource.addValue(Constants.RIO_CTRL_STR, record.getRioCtrlStr());
				paramSource.addValue(Constants.CURRENT_BALANCE, record.getCurrentBalance());
				paramSource.addValue(Constants.UPDATE_TS, Timestamp.valueOf(timezoneConv.currentTime()));

				tagstatusrecord = namedJdbcTemplate.update(query, paramSource);
				log.info("Number of rows updated in T_TAG_STATUS_ALLSORTED : {}", tagstatusrecord);
			}
		} catch (DataAccessException e) {
			log.info("Error while getting list of VDeviceDto : {}", e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public int delfromIncrAllsorted() {
		int result;
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String query = LoadJpaQueries.getQueryById("DEL_FROM_INCR_ALLSORTED");
		result = namedJdbcTemplate.update(query, paramSource);
		log.info("Number of records deleted from T_INCR_TAG_STATUS_ALLSORTED : {}", result);
		return result;
	}

	@Override
	public String getPlanString(long LC_ETC_ACCOUNT_ID, String LC_DEVICE_NO) {

		String planString = null;
		String query = null;
		try {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(Constants.LC_ETC_ACCOUNT_ID, LC_ETC_ACCOUNT_ID);
			paramSource.addValue(Constants.LC_DEVICE_NO, LC_DEVICE_NO);
			query = LoadJpaQueries.getQueryById("SELECT_PLAN_STRING");
			planString = namedJdbcTemplate.queryForObject(query, paramSource, String.class);
			log.info("Plan String: {} ", planString);
		} catch (DataAccessException e) {
			log.info("Exception while executing sql: {}", query);
		}
		return planString;
	}

	/**
	 * Insert active device information into T_TAG_STATUS_ALLSORTED table
	 *
	 */
	@Override
	public void insertIntoTTagAllSorted(List<TagStatusSortedRecord> recordlist) throws SQLException{
		//MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		String insertQuery = LoadJpaQueries.getQueryById("INSERT_INTO_T_TAG_STATUS_ALLSORTED");
//		int updateCounts = jdbcTemplate.update(insertQuery, paramSource);
	//	log.info("UpdateCount is : {}",updateCounts);
		
		int[] updateCounts = jdbcTemplate.batchUpdate(insertQuery, new BatchPreparedStatementSetter() {
		
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setObject(1, recordlist.get(i).getAccountNo());
				//paramSource.addValue("ETC_ACCOUNT_ID",record.getEtcAccountId());
				ps.setObject(2, recordlist.get(i).getEtcAccountId());
				ps.setObject(3, recordlist.get(i).getAccountStatus());
				ps.setObject(4, recordlist.get(i).getAccountType());
				ps.setObject(5, recordlist.get(i).getAccountTypeCd());
				ps.setObject(6, recordlist.get(i).getAgencyId());
				ps.setObject(7, recordlist.get(i).getDeviceNo());
				ps.setObject(8, recordlist.get(i).getDeviceStatus());
				ps.setObject(9, recordlist.get(i).getFinancialStatus());
				ps.setObject(10,recordlist.get(i).getIagCodedClass());
				ps.setObject(11, recordlist.get(i).getCurrentBalance());
				ps.setObject(12, recordlist.get(i).getRebillPayType());
				ps.setObject(13, recordlist.get(i).getRebillAmount());
				ps.setObject(14, recordlist.get(i).getRebillThreshold());
				ps.setObject(15, recordlist.get(i).getPostPaidStatus());
				ps.setObject(16, recordlist.get(i).getTagOwningAgency());
				ps.setObject(17, recordlist.get(i).getThwyAcct());
				ps.setObject(18, recordlist.get(i).getOptInOutFlag());
				ps.setObject(19, recordlist.get(i).getAcctSuspFlag());
				ps.setObject(20, recordlist.get(i).getPostPaidFlag());
				ps.setObject(21, recordlist.get(i).getPostPaidPlanCnt());
				ps.setObject(22, recordlist.get(i).getIsPrevalidated());
				ps.setObject(23, recordlist.get(i).getIsCompanionTag());
				ps.setObject(24,
						recordlist.get(i).getFirstTollDate() == null ? null : Date.valueOf(recordlist.get(i).getFirstTollDate()));
				ps.setObject(25, recordlist.get(i).getFromPlaza());
				ps.setObject(26, recordlist.get(i).getToPlaza());
				ps.setObject(27, recordlist.get(i).getRetailTagStatus());
				ps.setObject(28, recordlist.get(i).getSpeedAgency());
				ps.setObject(29, recordlist.get(i).getItagTagStatus());
				ps.setObject(30, recordlist.get(i).getItagInfo());
				ps.setObject(31, recordlist.get(i).getAllPlans());
				ps.setObject(32, recordlist.get(i).getRevPlanCount());
				ps.setObject(33, recordlist.get(i).getNystaNonRevCount());

				ps.setObject(34, recordlist.get(i).getNystaAnnualPlanCount());
				ps.setObject(35, recordlist.get(i).getNystaPostPaidComlCount());
				ps.setObject(36, recordlist.get(i).getNystaTagStatus());
				ps.setObject(37, recordlist.get(i).getNysbaNonRevCount());
				ps.setObject(38, recordlist.get(i).getNysbaTagStatus());
				ps.setObject(39, recordlist.get(i).getMtaNonRevCount());
				ps.setObject(40, recordlist.get(i).getMtaSpecialPlanCount());
				ps.setObject(41, recordlist.get(i).getMtaTagStatus());
				ps.setObject(42, recordlist.get(i).getMtagPlanStr());
				ps.setObject(43, recordlist.get(i).getMtaCtrlStr());
				ps.setObject(44, recordlist.get(i).getPaNonRevCount());
				ps.setObject(45, recordlist.get(i).getPaBridgesCount());
				ps.setObject(46, recordlist.get(i).getPaSiBridgesCount());
				ps.setObject(47, recordlist.get(i).getPaCarpoolCount());

				ps.setObject(48, recordlist.get(i).getPaTagStatus());
				ps.setObject(49, recordlist.get(i).getRioNonRevCount());
				ps.setObject(50, recordlist.get(i).getRioTagStatus());
				ps.setObject(51, recordlist.get(i).getRioCtrlStr());
				ps.setObject(52, recordlist.get(i).getPlzLimitedCount());
				ps.setObject(53, recordlist.get(i).getFtagTagStatus());
				ps.setObject(54, recordlist.get(i).getFtagPlanInfo());

				ps.setObject(55, recordlist.get(i).getTsCtrlStr());
				ps.setObject(56, recordlist.get(i).getPlansStr());
				ps.setObject(57, recordlist.get(i).getCscLookupKey());
				ps.setObject(58, Timestamp.valueOf(timezoneConv.currentTime()));
				//new fields
				ps.setObject(59, recordlist.get(i).getTagProtocol());//TAG_PROTOCOL
				ps.setObject(60, recordlist.get(i).getTagType());//TAG_TYPE
				ps.setObject(61, recordlist.get(i).getTagMount());//TAG_MOUNT
				ps.setObject(62, recordlist.get(i).getTagClass());//TAG_CLASS


			}
			
		

			public int getBatchSize() {
				return recordlist.size();
			}
		});
//		paramSource.addValue("ACCOUNT_NO",record.getAccountNo());
//		paramSource.addValue("ETC_ACCOUNT_ID",record.getEtcAccountId());
//		paramSource.addValue("ACCOUNT_STATUS",record.getAccountStatus());
//		paramSource.addValue("ACCOUNT_TYPE",record.getAccountType());
//		paramSource.addValue("AGENCY_ID",record.getAgencyId());
//		paramSource.addValue("DEVICE_NO",record.getDeviceNo());
//		paramSource.addValue("DEVICE_STATUS",record.getDeviceStatus());
//		paramSource.addValue("FINANCIAL_STATUS",record.getFinancialStatus());
//		paramSource.addValue("IAG_CODED_CLASS",record.getIagCodedClass());
//		paramSource.addValue("CURRENT_BALANCE",record.getCurrentBalance());
//		paramSource.addValue("REBILL_PAY_TYPE",record.getRebillPayType());
//		paramSource.addValue("REBILL_AMOUNT",record.getRebillAmount());
//		paramSource.addValue("REBILL_THRESHOLD",record.getRebillThreshold());
//		paramSource.addValue("POST_PAID_STATUS",record.getPostPaidStatus());
//		paramSource.addValue("TAG_OWNING_AGENCY",record.getTagOwningAgency());
//		paramSource.addValue("THWY_ACCT",record.getThwyAcct());
//		paramSource.addValue("OPT_IN_OUT_FLAG",record.getOptInOutFlag());
//		paramSource.addValue("ACCT_SUSP_FLAG",record.getAcctSuspFlag());
//		paramSource.addValue("POST_PAID_FLAG",record.getPostPaidFlag());
//		paramSource.addValue("POST_PAID_PLAN_CNT",record.getPostPaidPlanCnt());
//		paramSource.addValue("PREVALIDATION_FLAG",record.getPrevalidationFlag());
//		paramSource.addValue("IS_COMPANION_TAG",record.getIsCompanionTag());
//		paramSource.addValue("FIRST_TOLL_DATE",record.getFirstTollDate());
//		paramSource.addValue("FROM_PLAZA",record.getFromPlaza());
//		paramSource.addValue("TO_PLAZA",record.getToPlaza());
//		paramSource.addValue("RETAIL_TAG_STATUS",record.getRetailTagStatus());
//		paramSource.addValue("SPEED_AGENCY",record.getSpeedAgency());
//		paramSource.addValue("ITAG_TAG_STATUS",record.getItagTagStatus());
//		paramSource.addValue("ITAG_INFO",record.getItagInfo());
//		paramSource.addValue("ALL_PLANS",record.getAllPlans());
//		paramSource.addValue("REV_PLAN_COUNT",record.getRevPlanCount());
//		paramSource.addValue("NYSTA_NON_REV_COUNT",record.getNystaNonRevCount());
//		paramSource.addValue("NYSTA_ANNUAL_PLAN_COUNT",record.getNystaAnnualPlanCount());
//		paramSource.addValue("NYSTA_POST_PAID_COML_COUNT",record.getNystaPostPaidComlCount());
//		paramSource.addValue("NYSTA_TAG_STATUS",record.getNystaTagStatus());
//		paramSource.addValue("NYSBA_NON_REV_COUNT",record.getNysbaNonRevCount());
//		paramSource.addValue("NYSBA_TAG_STATUS",record.getNysbaTagStatus());
//		paramSource.addValue("MTA_NON_REV_COUNT",record.getMtaNonRevCount());
//		paramSource.addValue("MTA_SPECIAL_PLAN_COUNT",record.getMtaSpecialPlanCount());
//		paramSource.addValue("MTA_TAG_STATUS",record.getMtaTagStatus());
//		paramSource.addValue("MTAG_PLAN_STR",record.getMtagPlanStr());
//		paramSource.addValue("MTA_CTRL_STR",record.getMtaCtrlStr());
//		paramSource.addValue("PA_NON_REV_COUNT",record.getPaNonRevCount());
//		paramSource.addValue("PA_BRIDGES_COUNT",record.getPaBridgesCount());
//		paramSource.addValue("PA_SI_BRIDGES_COUNT",record.getPaSiBridgesCount());
//		paramSource.addValue("PA_CARPOOL_COUNT",record.getPaCarpoolCount());
//		paramSource.addValue("PA_TAG_STATUS",record.getPaTagStatus());
//		paramSource.addValue("RIO_NON_REV_COUNT",record.getRioNonRevCount());
//		paramSource.addValue("RIO_TAG_STATUS",record.getRioTagStatus());
//		paramSource.addValue("RIO_CTRL_STR",record.getRioCtrlStr());
//		paramSource.addValue("PLZ_LIMITED_COUNT",record.getPlzLimitedCount());
//		paramSource.addValue("FTAG_TAG_STATUS",record.getFtagTagStatus());
//		paramSource.addValue("FTAG_PLAN_INFO",record.getFtagPlanInfo());
//		paramSource.addValue("TS_CTRL_STR",record.getTsCtrlStr());
//		paramSource.addValue("PLANS_STR",record.getPlansStr());
//		paramSource.addValue("CSC_LOOKUP_KEY",record.getCscLookupKey());
//		paramSource.addValue("UPDATE_TS",record.getUpdateTs());
//		
//		String insertQuery = LoadJpaQueries.getQueryById("INSERT_INTO_T_TAG_STATUS_ALLSORTED");
//		int updateCounts = jdbcTemplate.update(insertQuery, paramSource);
//		log.info("UpdateCount is : {}",updateCounts);
//		
		
		
		
		log.debug("No. of tag devices inserted in T_TAG_STATUS_ALLSORTED batch {}", updateCounts);

	}

	@Override
	public void insertintoTHTagAllSorted(List<TagStatusSortedRecord> list) {

		String insertQuery = LoadJpaQueries.getQueryById("INSERT_INTO_T_H_TAG_STATUS_ALLSORTED");
		int[] updateCounts = jdbcTemplate.batchUpdate(insertQuery, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setObject(1, list.get(i).getAccountNo());
				ps.setObject(2, list.get(i).getEtcAccountId());
				ps.setObject(3, list.get(i).getAccountStatus());
				ps.setObject(4, list.get(i).getAccountType());
				ps.setObject(5, list.get(i).getAccountTypeCd());
				ps.setObject(6,  list.get(i).getAgencyId());
				ps.setObject(7, list.get(i).getFinancialStatus());
				ps.setObject(8, list.get(i).getRevPlanCount());
				ps.setObject(9, list.get(i).getMtaNonRevCount());
				ps.setObject(10,list.get(i).getNysbaNonRevCount());
				ps.setObject(11, list.get(i).getPaNonRevCount());
				ps.setObject(12, list.get(i).getRioNonRevCount());
				ps.setObject(13, list.get(i).getPlzLimitedCount());
				ps.setObject(14, list.get(i).getMtaSpecialPlanCount());
				ps.setObject(15, list.get(i).getPaBridgesCount());
				ps.setObject(16, list.get(i).getPaSiBridgesCount());
				ps.setObject(17, list.get(i).getPaCarpoolCount());
				ps.setObject(18, list.get(i).getDeviceNo());
				ps.setObject(19, list.get(i).getDeviceStatus());
				ps.setObject(20, list.get(i).getRebillPayType());
				ps.setObject(21, list.get(i).getPostPaidStatus());
				ps.setObject(22, list.get(i).getCurrentBalance());
				ps.setObject(23, list.get(i).getRebillAmount());
				ps.setObject(24, list.get(i).getRebillThreshold());
				ps.setObject(25, list.get(i).getTagOwningAgency());
				ps.setObject(26, list.get(i).getThwyAcct());
				ps.setObject(27, list.get(i).getIagCodedClass());
				ps.setObject(28, list.get(i).getOptInOutFlag());
				ps.setObject(29, list.get(i).getPrevalidationFlag());
				ps.setObject(30, list.get(i).getIsCompanionTag());
				ps.setObject(31, Timestamp.valueOf(timezoneConv.currentTime()));
				ps.setObject(32, list.get(i).getItagTagStatus());
				ps.setObject(33, list.get(i).getMtaTagStatus());
				ps.setObject(34, list.get(i).getPaTagStatus());
				ps.setObject(35, list.get(i).getRioTagStatus());
				ps.setObject(36, list.get(i).getFtagTagStatus());
				ps.setObject(37, list.get(i).getFromPlaza());
				ps.setObject(38, list.get(i).getToPlaza());
				ps.setObject(39, list.get(i).getTsCtrlStr());
				ps.setObject(40, list.get(i).getItagInfo());
				ps.setObject(41,
						list.get(i).getFirstTollDate() == null ? null : Date.valueOf(list.get(i).getFirstTollDate()));
				ps.setObject(42, list.get(i).getRetailTagStatus());
				ps.setObject(43, list.get(i).getSpeedAgency());
				ps.setObject(44, list.get(i).getAllPlans());
				ps.setObject(45, list.get(i).getCscLookupKey());
				ps.setObject(46, list.get(i).getPlansStr());
				ps.setObject(47, list.get(i).getMtagPlanStr());
				ps.setObject(48, list.get(i).getMtaCtrlStr());
				ps.setObject(49, list.get(i).getRioCtrlStr());
				ps.setObject(50, list.get(i).getFtagPlanInfo());
				ps.setObject(51, list.get(i).getNysbaTagStatus());
				ps.setObject(52, list.get(i).getAcctSuspFlag());
				//new fields
				ps.setObject(53, list.get(i).getTagProtocol());//TAG_PROTOCOL
				ps.setObject(54, list.get(i).getTagType());//TAG_TYPE
				ps.setObject(55, list.get(i).getTagMount());//TAG_MOUNT
				ps.setObject(56, list.get(i).getTagClass());//TAG_CLASS

			}

			public int getBatchSize() {
				return list.size();
			}
		});
		log.debug("No. of tag devices inserted in History table in batch {}", updateCounts);

	}

	@Override
	public void insertIntoTIncrTTagAllSortedforMTA(List<TagStatusSortedRecord> incrlist) {

		String insertQuery = LoadJpaQueries.getQueryById("INSERT_INTO_T_INCR_TAG_STATUS_ALLSORTED");
		int[] updateCounts = jdbcTemplate.batchUpdate(insertQuery, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setObject(1, incrlist.get(i).getDeviceNo());
				ps.setObject(2, incrlist.get(i).getAgencyId());
				ps.setObject(3, Constants.MTA_PLAZA_ID);
				ps.setObject(4, incrlist.get(i).getMtaTagStatus());
				ps.setObject(5, incrlist.get(i).getMtaCtrlStr());
				ps.setObject(6, Timestamp.valueOf(timezoneConv.currentTime()));
				//new fields
				ps.setObject(7, incrlist.get(i).getTagProtocol());//TAG_PROTOCOL
				ps.setObject(8, incrlist.get(i).getTagType());//TAG_TYPE
				ps.setObject(9, incrlist.get(i).getTagMount());//TAG_MOUNT
				ps.setObject(10, incrlist.get(i).getTagClass());//TAG_CLASS
			}

			public int getBatchSize() {
				return incrlist.size();
			}
		});
		log.info("No. of tag devices inserted in T_INCR_TAG_STATUS_ALLSORTED for MTA table in batch {}", updateCounts);
	}

	@Override
	public void insertIntoTIncrTTagAllSortedforRIO(List<TagStatusSortedRecord> incrlist) {

		String insertQuery = LoadJpaQueries.getQueryById("INSERT_INTO_T_INCR_TAG_STATUS_ALLSORTED");
		int[] updateCounts = jdbcTemplate.batchUpdate(insertQuery, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setObject(1, incrlist.get(i).getDeviceNo());
				ps.setObject(2, incrlist.get(i).getAgencyId());
				ps.setObject(3, Constants.RIO_PLAZA_ID);
				ps.setObject(4, incrlist.get(i).getRioTagStatus());
				ps.setObject(5, incrlist.get(i).getRioCtrlStr());
				ps.setObject(6, Timestamp.valueOf(timezoneConv.currentTime()));

				//new fields
				ps.setObject(7, incrlist.get(i).getTagProtocol());//TAG_PROTOCOL
				ps.setObject(8, incrlist.get(i).getTagType());//TAG_TYPE
				ps.setObject(9, incrlist.get(i).getTagMount());//TAG_MOUNT
				ps.setObject(10, incrlist.get(i).getTagClass());//TAG_CLASS


			}

			public int getBatchSize() {
				return incrlist.size();
			}
		});
		log.info("No. of tag devices inserted in T_INCR_TAG_STATUS_ALLSORTED for RIO table in batch {}", updateCounts);
	}

	@Override
	public void insertIntoTIncrTTagAllSortedforPA(List<TagStatusSortedRecord> incrlist) {

		String insertQuery = LoadJpaQueries.getQueryById("INSERT_INTO_T_INCR_TAG_STATUS_ALLSORTED");
		int[] updateCounts = jdbcTemplate.batchUpdate(insertQuery, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setObject(1, incrlist.get(i).getDeviceNo());
				ps.setObject(2, incrlist.get(i).getAgencyId());
				ps.setObject(3, Constants.ITAG_PLAZA_ID);
				ps.setObject(4, incrlist.get(i).getPaTagStatus());
				ps.setObject(5, incrlist.get(i).getTsCtrlStr());
				ps.setObject(6, Timestamp.valueOf(timezoneConv.currentTime()));
				//new fields
				ps.setObject(7, incrlist.get(i).getTagProtocol());//TAG_PROTOCOL
				ps.setObject(8, incrlist.get(i).getTagType());//TAG_TYPE
				ps.setObject(9, incrlist.get(i).getTagMount());//TAG_MOUNT
				ps.setObject(10, incrlist.get(i).getTagClass());//TAG_CLASS
			}

			public int getBatchSize() {
				return incrlist.size();
			}
		});
		log.info("No. of tag devices inserted in T_INCR_TAG_STATUS_ALLSORTED for PA table in batch {}", updateCounts);
	}
	
//	//for FTAG
//	@Override
//	public void insertIntoTIncrTTagAllSortedforFTAG(List<TagStatusSortedRecord> incrlist, int plazaId) {
//
//		String insertQuery = LoadJpaQueries.getQueryById("INSERT_INTO_T_INCR_TAG_STATUS_ALLSORTED");
//		int[] updateCounts = jdbcTemplate.batchUpdate(insertQuery, new BatchPreparedStatementSetter() {
//			public void setValues(PreparedStatement ps, int i) throws SQLException {
//				ps.setObject(1, incrlist.get(i).getDeviceNo());
//				ps.setObject(2, incrlist.get(i).getAgencyId());
//				ps.setObject(3,plazaId);
//				ps.setObject(4, incrlist.get(i).getFtagTagStatus());
//				ps.setObject(5, null);//ctrl str
//				ps.setObject(6, Timestamp.valueOf(timezoneConv.currentTime()));
//				//new fields
//				ps.setObject(7, incrlist.get(i).getTagProtocol());//TAG_PROTOCOL
//				ps.setObject(8, incrlist.get(i).getTagType());//TAG_TYPE
//				ps.setObject(9, incrlist.get(i).getTagMount());//TAG_MOUNT
//				ps.setObject(10, incrlist.get(i).getTagClass());//TAG_CLASS
//			}
//
//			public int getBatchSize() {
//				return incrlist.size();
//			}
//		});
//		log.info("No. of tag devices inserted in T_INCR_TAG_STATUS_ALLSORTED for FTAG table in batch {}", updateCounts);
//	}

	@Override
	public TagStatusSortedRecord checkifrecordexists(String deviceNo) {
		try {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(Constants.LC_DEVICE_NO, deviceNo);
			String query = LoadJpaQueries.getQueryById("CHECK_IF_RECORD_EXISTS");

			TagStatusSortedRecord record = namedJdbcTemplate.queryForObject(query, paramSource,
					BeanPropertyRowMapper.newInstance(TagStatusSortedRecord.class));
			
			

			if (record != null) {
				log.info("Record exists with device no....{}", deviceNo);
				return record;
			}
		} catch (DataAccessException e) {
			e.getMessage();
			log.info("Record does not exists with device no....{}", deviceNo);
			//return null;
		}
		return null;
		
	}

	@Override
	public boolean checkIfRecordExistsforPlazaID(int plaza_id, String deviceNo) {
		try {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(Constants.PLAZA_ID, plaza_id);
			paramSource.addValue(Constants.DEVICE_NO, deviceNo);
			String query = LoadJpaQueries.getQueryById("CHECK_IF_RECORD_EXISTS_FOR_PLAZA_ID");

			IncrTagStatusRecord record = namedJdbcTemplate.queryForObject(query, paramSource,
					BeanPropertyRowMapper.newInstance(IncrTagStatusRecord.class));

			if (record != null) {
				log.info("Record exists with plaza id....{}", plaza_id);
				return true;
			}
		} catch (DataAccessException e) {
			e.getMessage();
			log.info("Record does not exists with plaza id....{}", plaza_id);
		}

		return false;
	}

	@Override
	public void updaterecordinTTagAllSorted(TagStatusSortedRecord record) {

		int tagstatusrecord = 0;

		try {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(Constants.LC_DEVICE_NO, record.getDeviceNo());
			String query = LoadJpaQueries.getQueryById("UPDATE_T_TAG_ALL_SORTED_FOR_RECORD");

			// do we have to add all fields?
			paramSource.addValue(Constants.ACCOUNT_NO, record.getAccountNo());
			paramSource.addValue(Constants.ACCOUNT_STATUS, record.getAccountStatus());
			paramSource.addValue(Constants.LC_DEVICE_NO, record.getDeviceNo());
			paramSource.addValue(Constants.FINANCIAL_STATUS, record.getFinancialStatus());
			paramSource.addValue(Constants.DEVICE_STATUS, record.getDeviceStatus());
			paramSource.addValue(Constants.REBILL_PAY_TYPE, record.getRebillPayType());
			paramSource.addValue(Constants.ALL_PLANS, record.getAllPlans());
			paramSource.addValue(Constants.PREVALIDATION_FLAG, record.getPrevalidationFlag());
			paramSource.addValue(Constants.SPEED_AGENCY, record.getSpeedAgency());
			paramSource.addValue(Constants.RETAIL_TAG_STATUS, record.getRetailTagStatus());
			paramSource.addValue(Constants.POST_PAID_STATUS, record.getPostPaidStatus());
			paramSource.addValue(Constants.REV_PLAN_COUNT, record.getRevPlanCount());
			paramSource.addValue(Constants.MTA_TAG_STATUS, record.getMtaTagStatus());
			paramSource.addValue(Constants.RIO_TAG_STATUS, record.getRioTagStatus());
			paramSource.addValue(Constants.ITAG_TAG_STATUS, record.getItagTagStatus());
			paramSource.addValue(Constants.PA_TAG_STATUS, record.getPaTagStatus());
			paramSource.addValue(Constants.FTAG_TAG_STATUS, record.getFtagTagStatus());
			paramSource.addValue(Constants.TS_CTRL_STR, record.getTsCtrlStr());
			paramSource.addValue(Constants.MTA_CTRL_STR, record.getMtaCtrlStr());
			paramSource.addValue(Constants.RIO_CTRL_STR, record.getRioCtrlStr());
			paramSource.addValue(Constants.CURRENT_BALANCE, record.getCurrentBalance());
			paramSource.addValue(Constants.UPDATE_TS, Timestamp.valueOf(timezoneConv.currentTime()));

			tagstatusrecord = namedJdbcTemplate.update(query, paramSource);
			log.info("Number of rows updated in T_TAG_STATUS_ALLSORTED: {}", tagstatusrecord);
		} catch (DataAccessException e) {
			log.info("Error while updating record in T_TAG_STATUS_ALLSORTED: {}", e.getMessage());
			e.printStackTrace();
		}

	}


	@Override
    public AccountApiInfoDto getAccountInfoFromFPMS(String etcAccountId) {

		log.info("getAccountInfoFromFPMS etcAccountId ==> {}",etcAccountId);

       MapSqlParameterSource paramSource = new MapSqlParameterSource();
        String queryFile = LoadJpaQueries.getQueryById("GET_ACCOUNT_INFO_DETAIL");
        paramSource.addValue("ETC_ACCOUNT_ID", etcAccountId);
        List<AccountApiInfoDto> tempAccountApiInfoList = namedJdbcTemplate.query(queryFile, paramSource,
                BeanPropertyRowMapper.newInstance(AccountApiInfoDto.class));
        log.info("tempAccountApiInfoList ==> {}",tempAccountApiInfoList);
        if (tempAccountApiInfoList.isEmpty()) {
            return null;
        } else {
            return tempAccountApiInfoList.get(0);
        }
    }
	/*
	 * @Override public TagStatusSortedRecord
	 * checkifrecordexists1(TagStatusSortedRecord record) {
	 * 
	 * String query =
	 * "SELECT AGENCY_ID,ACCOUNT_NO,ETC_ACCOUNT_ID,ACCOUNT_TYPE,ACCOUNT_STATUS,DEVICE_NO,FINANCIAL_STATUS,TAG_OWNING_AGENCY"
	 * +
	 * " THWY_ACCT,CURRENT_BALANCE,REBILL_PAY_TYPE,REBILL_AMOUNT,REBILL_THRESHOLD,POST_PAID_STATUS,ACCT_SUSP_FLAG,POST_PAID_FLAG"
	 * +
	 * "	FIRST_TOLL_DATE,RETAIL_TAG_STATUS,SPEED_AGENCY,ALL_PLANS,REV_PLAN_COUNT,POST_PAID_PLAN_CNT,NYSTA_NON_REV_COUNT,NYSTA_ANNUAL_PLAN_COUNT"
	 * +
	 * "	NYSTA_POST_PAID_COML_COUNT ,NYSTA_TAG_STATUS,NYSBA_NON_REV_COUNT,MTA_NON_REV_COUNT,MTA_SPECIAL_PLAN_COUNT,MTA_TAG_STATUS,PA_NON_REV_COUNT"
	 * +
	 * " PA_BRIDGES_COUNT,PA_SI_BRIDGES_COUNT,PA_CARPOOL_COUNT,RIO_NON_REV_COUNT,PLZ_LIMITED_COUNT,PA_TAG_STATUS,RIO_TAG_STATUS,FTAG_TAG_STATUS"
	 * +
	 * "	IAG_CODED_CLASS,OPT_IN_OUT_FLAG,PREVALIDATION_FLAG,IS_COMPANION_TAG,ITAG_INFO,ITAG_TAG_STATUS,FROM_PLAZA,TO_PLAZA,TS_CTRL_STR,PLANS_STR"
	 * +
	 * "	MTAG_PLAN_STR,MTA_CTRL_STR,RIO_CTRL_STR,FTAG_PLAN_INFO,NYSBA_TAG_STATUS,CSC_LOOKUP_KEY,DEVICE_STATUS"
	 * + "	FROM TPMS.T_TAG_STATUS_ALLSORTED WHERE "
	 * +" 	AGENCY_ID = '"+record.getAgencyId()+"' AND" +
	 * "	ACCOUNT_NO = '"+record.getAccountNo()+"' AND" +
	 * " ETC_ACCOUNT_ID = '"+record.getEtcAccountId()+"' AND" +
	 * " ACCOUNT_TYPE = '"+record.getAccountType()+"' AND" +
	 * " ACCOUNT_STATUS = '"+record.getAccountStatus()+"' AND" +
	 * " DEVICE_NO = '"+record.getDeviceNo()+"' AND" +
	 * " FINANCIAL_STATUS= '"+record.getFinancialStatus()+"' AND" +
	 * " TAG_OWNING_AGENCY = '"+record.getAgencyId()+"' AND" +
	 * " THWY_ACCT = '"+record.getThwyAcct()+"' AND" +
	 * " CURRENT_BALANCE = '"+record.getCurrentBalance()+"' AND" +
	 * " REBILL_PAY_TYPE ='"+record.getRebillPayType()+"' AND" +
	 * " REBILL_AMOUNT = '"+record.getRebillAmount()+"' AND" +
	 * " REBILL_THRESHOLD = '"+record.getRebillThreshold()+"' AND" +
	 * " POST_PAID_STATUS = '"+record.getPostPaidStatus()+"' AND" +
	 * " ACCT_SUSP_FLAG = '"+record.getAcctSuspFlag()+"' AND" +
	 * " POST_PAID_FLAG = '"+record.getPostPaidFlag()+"' AND" +
	 * " FIRST_TOLL_DATE = '"+record.getFirstTollDate()+"' AND" +
	 * " RETAIL_TAG_STATUS = '"+record.getRetailerStatus()+"' AND" +
	 * " SPEED_AGENCY = '"+record.getSpeedAgency()+"' AND" +
	 * " ALL_PLANS = '"+record.getAllPlans()+"' AND" +
	 * " REV_PLAN_COUNT = '"+record.getRevPlanCount()+"' AND" +
	 * " POST_PAID_PLAN_CNT = '"+record.getPostPaidPlanCnt()+"' AND" +
	 * " NYSTA_NON_REV_COUNT = '"+record.getNystaNonRevCount()+"' AND" +
	 * " NYSTA_ANNUAL_PLAN_COUNT = '"+record.getNystaAnnualPlanCount()+"' AND" +
	 * " NYSTA_POST_PAID_COML_COUNT = '"+record.getNystaPostPaidComlCount()+"' AND"
	 * + " NYSTA_TAG_STATUS = '"+record.getNysbaNonRevCount()+"' AND" +
	 * " NYSBA_NON_REV_COUNT = '"+record.getNysbaNonRevCount()+"' AND" +
	 * " MTA_NON_REV_COUNT = '"+record.getMtaNonRevCount()+"' AND" +
	 * " MTA_SPECIAL_PLAN_COUNT = '"+record.getMtaSpecialPlanCount()+"' AND" +
	 * " MTA_TAG_STATUS = '"+record.getMtaTagStatus()+"' AND" +
	 * " PA_NON_REV_COUNT = '"+record.getPaNonRevCount()+"' AND" +
	 * " PA_BRIDGES_COUNT = '"+record.getPaBridgesCount()+"' AND" +
	 * " PA_SI_BRIDGES_COUNT = '"+record.getPaSiBridgesCount()+"' AND" +
	 * " PA_CARPOOL_COUNT = '"+record.getPaCarpoolCount()+"' AND" +
	 * " RIO_NON_REV_COUNT ='"+record.getRioNonRevCount()+"' AND" +
	 * " PLZ_LIMITED_COUNT = '"+record.getPlzLimitedCount()+"' AND" +
	 * " PA_TAG_STATUS = '"+record.getPaTagStatus()+"' AND" +
	 * " RIO_TAG_STATUS = '"+record.getRioTagStatus()+"' AND" +
	 * " FTAG_TAG_STATUS = '"+record.getFtagTagStatus()+"' AND" +
	 * " IAG_CODED_CLASS = '"+record.getIagCodedClass()+"' AND" +
	 * " OPT_IN_OUT_FLAG = '"+record.getOptInOutFlag()+"' AND" +
	 * " PREVALIDATION_FLAG = '"+record.getPrevalidationFlag()+"' AND" +
	 * " IS_COMPANION_TAG = '"+record.getIsCompanionTag()+"' AND" +
	 * " ITAG_INFO = '"+record.getItagInfo()+"' AND" +
	 * " ITAG_TAG_STATUS = '"+record.getItagTagStatus()+"' AND" +
	 * " FROM_PLAZA = '"+record.getFromPlaza()+"' AND" +
	 * " TO_PLAZA = '"+record.getToPlaza()+"' AND" +
	 * " TS_CTRL_STR ='"+record.getTsCtrlStr()+"' AND" +
	 * " PLANS_STR = '"+record.getPlansStr()+"' AND" +
	 * " MTAG_PLAN_STR = '"+record.getMtagPlanStr()+"' AND" +
	 * " MTA_CTRL_STR = '"+record.getMtaCtrlStr()+"' AND" +
	 * " RIO_CTRL_STR = '"+record.getRioCtrlStr()+"' AND" +
	 * " FTAG_PLAN_INFO = '"+record.getFtagPlanInfo()+"' AND" +
	 * " NYSBA_TAG_STATUS = '"+record.getNysbaTagStatus()+"' AND" +
	 * " CSC_LOOKUP_KEY = '"+record.getCscLookupKey()+"' AND" +
	 * " DEVICE_STATUS = '"+record.getDeviceStatus()+"' ";
	 * 
	 * List<Map<String, Object>> listofrecord = jdbcTemplate.queryForList(query);
	 * 
	 * if(listofrecord.size() > 0) { return (TagStatusSortedRecord)
	 * listofrecord.get(0); } else { return null; }
	 * 
	 * 
	 * Stream<TagStatusSortedRecord> stream =
	 * jdbcTemplate.queryForStream(query,(rs,rownum) -> new
	 * TagStatusSortedRecord(rs.getString(1) , rs.getInt(2) ,rs.getInt(3)
	 * ,rs.getInt(4) ,rs.getInt(5) ,rs.getString(6) , rs.getInt(7) ,rs.getInt(8)
	 * ,rs.getInt(9) ,rs.getInt(10) ,rs.getInt(11) ,rs.getInt(12) ,rs.getInt(13)
	 * ,rs.getInt(14) , rs.getInt(15) ,rs.getString(16) ,rs.getString(17)
	 * ,rs.getString(18) ,rs.getString(19) ,rs.getInt(20) ,rs.getString(21) ,
	 * rs.getString(22) ,rs.getDate(23) ,rs.getString(24) ,rs.getString(25)
	 * ,rs.getInt(26) ,rs.getInt(27) ,rs.getInt(28) ,rs.getInt(29) , rs.getInt(30)
	 * ,rs.getInt(31) ,rs.getInt(32) ,rs.getInt(33) ,rs.getInt(34) ,rs.getInt(35)
	 * ,rs.getInt(36) ,rs.getInt(37) ,rs.getInt(38) , rs.getInt(39) ,rs.getInt(40)
	 * ,rs.getInt(41) ,rs.getString(43) ,rs.getInt(44) ,rs.getInt(45) ,rs.getInt(46)
	 * ,rs.getInt(47) , rs.getInt(48) ,rs.getInt(49) ,rs.getString(50)
	 * ,rs.getInt(51) ,rs.getInt(52) ,rs.getInt(53) ,rs.getString(54) ,
	 * rs.getString(55) ,rs.getString(56)));
	 * 
	 * 
	 * }
	 */

	@Override
	public String getOptInOutFlag(TagStatusSortedRecord tagStatusSortedRecord) {
		// TODO Auto-generated method stub
		
		if(checkifrecordexists1(tagStatusSortedRecord)) {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			//paramSource.addValue(Constants.lv_account_type, tagStatusSortedRecord.getAccountType());
			paramSource.addValue(Constants.ETC_ACCOUNT_ID, tagStatusSortedRecord.getEtcAccountId());
			
			//String optInOutFlag=LoadJpaQueries.getQueryById("SELECT_OPT_IN_OUT_STATUS");
			String query=LoadJpaQueries.getQueryById("SELECT_OPT_IN_OUT_STATUS");
			
			String optInOutFlag= namedJdbcTemplate.queryForObject(query,paramSource,String.class);
			
			if(optInOutFlag.equals(Constants.TRUE)) {
				return Constants.IN;
			}else {
				return Constants.OUT;
			}
			//return optInOutFlag;
		}else {
			
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(Constants.lv_account_type, tagStatusSortedRecord.getAccountType());
			
			
			//String optInOutFlag=LoadJpaQueries.getQueryById("SELECT_NO_RECORD_OPT_IN_OUT_STATUS");
			String query=LoadJpaQueries.getQueryById("SELECT_NO_RECORD_OPT_IN_OUT_STATUS");
			
			String optInOutFlag = namedJdbcTemplate.queryForObject(query, paramSource, String.class);
//			if(optInOutFlag.equals(Constants.TRUE)) {
//				return Constants.I;
//			}else {
//				return Constants.OUT;
//			}
			
			
			return optInOutFlag;
		}
		
		
		//return null;
	}
	
	/*public String getOptInOutFlag(TagStatusSortedRecord tagStatusSortedRecord) {
		if(checkifrecordexists1(tagStatusSortedRecord.getDeviceNo())) {
			String optInOutFlag=LoadJpaQueries.getQueryById("SELECT_OPT_IN_OUT_STATUS");
			return optInOutFlag;
		}else {
			String optInOutFlag=LoadJpaQueries.getQueryById("SELECT_NO_RECORD_OPT_IN_OUT_STATUS");
			return optInOutFlag;
		}
		
	}*/
	
	@Override
	public boolean checkifrecordexists1(TagStatusSortedRecord tagstatussortedrecord) {
		try {
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(Constants.ETC_ACCOUNT_ID, tagstatussortedrecord.getEtcAccountId());
			//paramSource.addValue(Constants.LC_DEVICE_NO, deviceNo);
			//String query = LoadJpaQueries.getQueryById("CHECK_IF_RECORD_EXISTS");
			
			String query =LoadJpaQueries.getQueryById("SELECT_OPT_IN_OUT_STATUS");

			/*
			 * TagStatusSortedRecord record = namedJdbcTemplate.queryForObject(query,
			 * paramSource, BeanPropertyRowMapper.newInstance(TagStatusSortedRecord.class));
			 */
			
			/*
			 * List<TagStatusSortedRecord> record = namedJdbcTemplate.query(query,
			 * paramSource, BeanPropertyRowMapper.newInstance(TagStatusSortedRecord.class));
			 */
			
			String vector_aprking= namedJdbcTemplate.queryForObject(query, paramSource, String.class);
			
			
//record != null //record.size()>0
			if (vector_aprking !=null) {
				log.info("Record exists with etc....{}", tagstatussortedrecord.getEtcAccountId());
				return true;
			}
		} catch (DataAccessException e) {
			e.getMessage();
			log.info("Record does not exists with etc....{}", tagstatussortedrecord.getEtcAccountId());
		}
		return false;
	}//CRM table needs to be created
}
