package com.conduent.tpms.iag.dao.impl;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.iag.config.LoadJpaQueries;
import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.constants.ICTXConstants;
import com.conduent.tpms.iag.dao.TAgencyDao;
import com.conduent.tpms.iag.dto.AgencyInfoVO;
import com.conduent.tpms.iag.dto.TCodesVO;
import com.conduent.tpms.iag.model.AccountInfo;
import com.conduent.tpms.iag.model.LaneIdExtLaneInfo;
import com.conduent.tpms.iag.model.LaneTxReconPolicy;
import com.conduent.tpms.iag.model.TollPostLimitInfo;
import com.conduent.tpms.iag.model.TranDetail;
import com.google.common.base.Stopwatch;

@Repository
public class TAgencyDaoImpl implements TAgencyDao {

	private static final Logger dao_log = LoggerFactory.getLogger(TAgencyDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public List<AgencyInfoVO> getAgencyInfoList() {

		String queryRules = LoadJpaQueries.getQueryById("SELECT_INFO_FROM_T_AGENCY");

		dao_log.info("Agency info fetched from T_Agency table successfully.");

		return jdbcTemplate.query(queryRules, new BeanPropertyRowMapper<AgencyInfoVO>(AgencyInfoVO.class));

	}

	@Override
	public List<AgencyInfoVO> getHomeAgencyInfoList() {

		String queryRules = LoadJpaQueries.getQueryById("SELECT_HOME_AGENCY_LIST");
		dao_log.info("Home Agency info fetched from T_Agency table successfully.");
		return jdbcTemplate.query(queryRules, new BeanPropertyRowMapper<AgencyInfoVO>(AgencyInfoVO.class));
	}

	@Override
	public List<TCodesVO> getTCodesList() {

		String queryRules = LoadJpaQueries.getQueryById("GET_T_CODES");
		return jdbcTemplate.query(queryRules, new BeanPropertyRowMapper<TCodesVO>(TCodesVO.class));
	}

	@Override
	public AccountInfo getAccountInfoOne(String deviceNo14, String deviceNo11, OffsetDateTime txTimestamp) {
		dao_log.info("getAccountInfoOneList starting");
		// 14 digits deviceNo
		AccountInfo accountInfo = null;
		try {
			//String queryRules = LoadJpaQueries.getQueryById("ETC_ACCOUNT_1");
			String queryRules = LoadJpaQueries.getQueryById(Constants.GET_ETC_ACCOUNT_V_DEVICE);
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			
			paramSource.addValue(Constants.DEVICE_NO_14, deviceNo14);
			paramSource.addValue(Constants.DEVICE_NO_11, deviceNo11);
			paramSource.addValue(Constants.TX_TIMESTAMP, txTimestamp);

			accountInfo = namedJdbcTemplate.queryForObject(queryRules, paramSource,
					BeanPropertyRowMapper.newInstance(AccountInfo.class));
			dao_log.info("getAccountInfoOneList-accountId:{}", accountInfo.getEtcAccountId());
		} catch (EmptyResultDataAccessException e) {
			dao_log.info("no records found  in getAccountInfoOneList :{}", e.getMessage());
			return null;
		} catch (Exception e) {
			dao_log.info("Exception found in getAccountInfoOneList..");
			e.printStackTrace();
		}
		
		return accountInfo;
	}

	@Override
	public AccountInfo getAccountInfoTwo(String deviceNo14, String deviceNo11, OffsetDateTime txTimestamp) {
		AccountInfo accountInfo = null;
		try {
			String queryRules = LoadJpaQueries.getQueryById(Constants.GET_ETC_ACCOUNT_V_H_DEVICE);
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(Constants.DEVICE_NO_14, deviceNo14);
			paramSource.addValue(Constants.DEVICE_NO_11, deviceNo11);
			paramSource.addValue(Constants.TX_TIMESTAMP, txTimestamp);
			accountInfo = namedJdbcTemplate.queryForObject(queryRules, paramSource,
					BeanPropertyRowMapper.newInstance(AccountInfo.class));
		} catch (EmptyResultDataAccessException e) {
			dao_log.info("no records found in getAccountInfoTwoList :{}", e.getMessage());
			return null;
		} catch (Exception e) {
			dao_log.info("Exception found in getAccountInfoTwoList..");
			e.printStackTrace();
		}
		return accountInfo;

	}

	@Override
	public AccountInfo getAccountInfoThree(String deviceNo14, String deviceNo11, String txDate) {
		AccountInfo accountInfo = null;
		try {
			String queryRules = LoadJpaQueries.getQueryById(Constants.GET_ETC_ACCOUNT_T_HA_DEVICES);
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(Constants.DEVICE_NO_14, deviceNo14);
			paramSource.addValue(Constants.DEVICE_NO_11, deviceNo11);
			paramSource.addValue(Constants.TX_DATE, txDate);
			accountInfo = namedJdbcTemplate.queryForObject(queryRules, paramSource,
					BeanPropertyRowMapper.newInstance(AccountInfo.class));
		} catch (EmptyResultDataAccessException e) {
			dao_log.info("no records found in getAccountInfoThreeList :{}", e.getMessage());
			return null;
		} catch (Exception e) {
			dao_log.info("Exception found in getAccountInfoThreeList..");
			e.printStackTrace();
		}
		return accountInfo;

	}

	@Override
	public AccountInfo getAccountInfo(TranDetail tranDetail) {
		AccountInfo accountInfo = null;
		try {
			String queryRules = LoadJpaQueries.getQueryById(Constants.GET_V_ETC_ACCOUNT);
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(Constants.ETC_ACCOUNT_ID, tranDetail.getEtcAccountId());
			accountInfo = namedJdbcTemplate.queryForObject(queryRules, paramSource,
					BeanPropertyRowMapper.newInstance(AccountInfo.class));
		} catch (EmptyResultDataAccessException e) {
			dao_log.info("no records found in getAccountInfo ");
			return null;
		}catch (Exception e) {
			dao_log.info("Exception found in getAccountInfoList..");
			e.printStackTrace();
		}
		return accountInfo;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void getTxStatusCodeId(TranDetail tranDetail) {
		try {
			String queryRules = LoadJpaQueries.getQueryById("GET_TX_STATUS_CODE_ID");
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(Constants.DEVICE_NO, tranDetail.getDeviceNo());
			paramSource.addValue(Constants.TX_DATE, tranDetail.getTxDate());
			String result = (String) jdbcTemplate.queryForObject(queryRules, new Object[] {}, String.class);
			tranDetail.setTxStatus((result != null) ? result : null);
		} catch (EmptyResultDataAccessException e) {
			dao_log.info("no records found in getAccountInfoThreeList :{}", e);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public String getUnresgisteredStaus(TranDetail tranDetail) {
		String result = null;
		try {
			String queryRules = LoadJpaQueries.getQueryById("GET_RESGISTERED_STATUS");
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(Constants.DEVICE_NO, tranDetail.getDeviceNo());
			paramSource.addValue(Constants.TX_DATE, tranDetail.getTxDate());
			result = (String) jdbcTemplate.queryForObject(queryRules, new Object[] {}, String.class);
			tranDetail.setTxStatus((result != null) ? result : null);
		} catch (EmptyResultDataAccessException e) {
			dao_log.info("no records found in getAccountInfoThreeList :{}", e);
			return null;
		}
		return result;
	}

	@Override
	public List<LaneTxReconPolicy> getLaneTxReconPolicyList() {

		String queryRules = LoadJpaQueries.getQueryById("GET_V_RECON_POLICY");
		return jdbcTemplate.query(queryRules, new BeanPropertyRowMapper<LaneTxReconPolicy>(LaneTxReconPolicy.class));
	}

	@Override
	public List<LaneIdExtLaneInfo> getLaneIdExtLaneIdInfo() {

		String queryRules = LoadJpaQueries.getQueryById("GET_LANE_ID_EXT_LANE_INFO");
		List<LaneIdExtLaneInfo> laneIdExtLaneInfoList = new ArrayList<LaneIdExtLaneInfo>();
		// return jdbcTemplate.query(query, new
		// BeanPropertyRowMapper<String>(String.class));

		Stopwatch stopwatch = Stopwatch.createStarted();

		// optional

		List<LaneIdExtLaneInfo> laneIdExtLaneInfo = jdbcTemplate.query(queryRules,
				new BeanPropertyRowMapper<LaneIdExtLaneInfo>(LaneIdExtLaneInfo.class));

//		jdbcTemplate.query(queryRules, new RowCallbackHandler() {
//			public void processRow(ResultSet resultSet) throws SQLException {
//				while (resultSet.next()) {
//					LaneIdExtLaneInfo  laneIdExtLaneInfo=new LaneIdExtLaneInfo();
//					laneIdExtLaneInfo.setAgencyId(resultSet.getInt("AGENCY_ID")); ;
//					laneIdExtLaneInfo.setDefaultPlanId(resultSet.getInt("DEFAULT_PLAN_ID"));
//					laneIdExtLaneInfo.setEndPlazaInd(resultSet.getString("PLAZA_TYPE_IND"));laneIdExtLaneInfo.setExternLaneId(resultSet.getString("EXTERN_LANE_ID"));
//					laneIdExtLaneInfo.setExternPlazaId(resultSet.getString("EXTERN_PLAZA_ID"));laneIdExtLaneInfo.setLaneId(resultSet.getInt("LANE_ID"));
//					laneIdExtLaneInfo.setPlazaTypeInd(resultSet.getString("PLAZA_TYPE_IND"));
//					laneIdExtLaneInfo.setRevenueTime(resultSet.getString("REVENUE_TIME"));laneIdExtLaneInfo.setSectionId(resultSet.getString("SECTION_ID"));
//					// process it
//					
//					laneIdExtLaneInfoList.add(laneIdExtLaneInfo);
//				}
//			}
//		});
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		dao_log.info("Total time taken to execute getLaneIdExtLaneIdInfo query {}", millis);
//		 return laneIdExtLaneInfoList;
		return laneIdExtLaneInfo;

	}

	@Override
	public List<TollPostLimitInfo> getTollPostLimits() {
		List<TollPostLimitInfo> tollPostLimits = new ArrayList<TollPostLimitInfo>();
		try {
			String queryRules = LoadJpaQueries.getQueryById(Constants.GET_TOLL_POST_LIMITS);
			tollPostLimits = namedJdbcTemplate.query(queryRules, new BeanPropertyRowMapper<TollPostLimitInfo>(TollPostLimitInfo.class));
		} catch (EmptyResultDataAccessException e) {
			dao_log.info("no records found in MASTER.T_TOLL_POST_LIMIT ");
			return null;
		}catch (Exception e) {
			dao_log.info("Exception found in getTollPostLimits()");
			e.printStackTrace();
		}
		return tollPostLimits;
	}
	
	@Override
	public Integer getHaDevicesIagTagStatus(String deviceNo14, String deviceNo11, String txDate) {
		try {
			String queryRules = LoadJpaQueries.getQueryById(ICTXConstants.GET_HA_TAG_STATUS);
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue(Constants.DEVICE_NO_14, deviceNo14);
			paramSource.addValue(Constants.DEVICE_NO_11, deviceNo11);
			paramSource.addValue(Constants.TX_DATE, txDate);
			return namedJdbcTemplate.queryForObject(queryRules, paramSource, Integer.class);
		} catch (EmptyResultDataAccessException e) {
			dao_log.info("no records found in T_HA_DEVICES :{}", e.getMessage());
			return 0;
		} catch (Exception e) {
			dao_log.info("Exception found in getHaDevicesIagTagStatus..");
			e.printStackTrace();
			return 0;
		}
	}
}
