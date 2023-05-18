package com.conduent.tpms.qatp.classification.dao.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.qatp.classification.config.LoadJpaQueries;
import com.conduent.tpms.qatp.classification.constants.QatpClassificationConstant;
import com.conduent.tpms.qatp.classification.dao.AccountInfoDao;
import com.conduent.tpms.qatp.classification.dto.AccountApiInfoDto;
import com.conduent.tpms.qatp.classification.model.AccountInfo;
import com.conduent.tpms.qatp.classification.model.AccountPlanDetail;
import com.conduent.tpms.qatp.classification.model.DeviceAway;
import com.conduent.tpms.qatp.classification.model.DeviceStatus;
import com.conduent.tpms.qatp.classification.model.PlanDetails;
import com.conduent.tpms.qatp.classification.model.ProcessParameter;
import com.conduent.tpms.qatp.classification.model.TollPriceSchedule;
import com.google.common.base.Stopwatch;

/**
 * Account Info Dao Implementation
 * 
 * @author deepeshb
 *
 */
@Repository
public class AccountInfoDaoImpl implements AccountInfoDao {

	private static final Logger logger = LoggerFactory.getLogger(AccountInfoDaoImpl.class);
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	/**
	 *Get DeviceStatus Info
	 */
	@Override
	public List<DeviceStatus> getDeviceStatus(String deviceNumber, String deviceNo11charecter, LocalDateTime txTimeStamp) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		Stopwatch stopwatch = Stopwatch.createStarted();
		String queryFile = LoadJpaQueries.getQueryById(QatpClassificationConstant.GET_DEVICE_STATUS);
		paramSource.addValue(QatpClassificationConstant.DEVICE_NUMBER, deviceNumber);
		paramSource.addValue(QatpClassificationConstant.DEVICE_NUMBER_11_CHAR, deviceNo11charecter);
		Timestamp ts = Timestamp.valueOf(txTimeStamp);
		System.out.println("TimeStamp: ===> "+ ts);
		paramSource.addValue(QatpClassificationConstant.TX_TIMESTAMP, ts);
		List<DeviceStatus> tempDeviceStatusList = namedJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(DeviceStatus.class));
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Time taken to select data from CRM.V_DEVICE  ==> {} ms",millis);
		//DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
		if (tempDeviceStatusList.isEmpty()) {
			return null;
		} else {
			return tempDeviceStatusList;
		}
	}

	/**
	 *Get Account Info
	 */
	@Override
	public AccountInfo getAccountInfo(Long etcAccountId,String txDate) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		Stopwatch stopwatch = Stopwatch.createStarted();
		String queryFile = LoadJpaQueries.getQueryById(QatpClassificationConstant.GET_ACCOUNT_INFO);
		paramSource.addValue(QatpClassificationConstant.ETC_ACCOUNT_ID, etcAccountId);
		paramSource.addValue(QatpClassificationConstant.TX_DATE, txDate);
		List<AccountInfo> tempAccountInfoList = namedJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(AccountInfo.class));
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Time taken to select data from  CRM.V_ETC_ACCOUNT ==> {}ms", millis);
		if (tempAccountInfoList.isEmpty()) {
			return null;
		} else {
			return tempAccountInfoList.get(0);
		}
	}
	
	@Override
	public AccountApiInfoDto getAccountInfoFromFPMS(String etcAccountId) {

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		Stopwatch stopwatch = Stopwatch.createStarted();
		String queryFile = LoadJpaQueries.getQueryById(QatpClassificationConstant.GET_ACCOUNT_INFO_DETAIL);
		paramSource.addValue(QatpClassificationConstant.ETC_ACCOUNT_ID, etcAccountId);
		List<AccountApiInfoDto> tempAccountApiInfoList = namedJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(AccountApiInfoDto.class));
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Time taken to get data from  CRM.V_ETC_ACCOUNT ==> {}ms", millis);
		if (tempAccountApiInfoList.isEmpty()) {
			return null;
		} else {
			return tempAccountApiInfoList.get(0);
		}
	}

	/**
	 *Get H Device Status Info
	 */
	@Override
	public List<DeviceStatus> getHDeviceStatus(String deviceNumber, String deviceNo11charecter, LocalDateTime txTimeStamp) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		Stopwatch stopwatch = Stopwatch.createStarted();
		String queryFile = LoadJpaQueries.getQueryById(QatpClassificationConstant.GET_H_DEVICE_STATUS);
		paramSource.addValue(QatpClassificationConstant.DEVICE_NUMBER, deviceNumber);
		paramSource.addValue(QatpClassificationConstant.DEVICE_NUMBER_11_CHAR, deviceNo11charecter);
		Timestamp ts = Timestamp.valueOf(txTimeStamp);
		paramSource.addValue(QatpClassificationConstant.TX_TIMESTAMP, ts);
		List<DeviceStatus> tempDeviceStatusList = namedJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(DeviceStatus.class));
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Time taken to select data from CRM.V_H_DEVICE ==> {} ms",millis);
		if (tempDeviceStatusList.isEmpty()) {
			return null;
		} else {
			return tempDeviceStatusList;
		}
	}

	/**
	 *Get Away Device Info
	 */
	@Override
	public List<DeviceAway> findDeviceAway(String deviceNumber, String deviceNo11charecter, LocalDateTime txTimeStamp) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		Stopwatch stopwatch = Stopwatch.createStarted();
		String queryFile = LoadJpaQueries.getQueryById(QatpClassificationConstant.FIND_DEVICE_AWAY);
		paramSource.addValue(QatpClassificationConstant.DEVICE_NUMBER, deviceNumber);
		paramSource.addValue(QatpClassificationConstant.DEVICE_NUMBER_11_CHAR, deviceNo11charecter);
		paramSource.addValue(QatpClassificationConstant.TX_TIMESTAMP, txTimeStamp);
		List<DeviceAway> tempDeviceAwayList = namedJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(DeviceAway.class));
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Time taken to select data from TPMS.T_OA_DEVICES ==> {} ms",millis);
		if (tempDeviceAwayList.isEmpty()) {
			return null;
		} else {
			return tempDeviceAwayList;
		}
	}

	/**
	 *Get Process Parameter Info
	 */
	@Override
	public ProcessParameter getProcessParameter(String paramName, Long agencyId) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		Stopwatch stopwatch = Stopwatch.createStarted();
		String queryFile = LoadJpaQueries.getQueryById(QatpClassificationConstant.GET_PROCESS_PARAMETER);
		paramSource.addValue(QatpClassificationConstant.PARAM_NAME, paramName);
		paramSource.addValue(QatpClassificationConstant.AGENCY_ID, agencyId);
		List<ProcessParameter> tempProcessParameterList = namedJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(ProcessParameter.class));
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Time taken to select data from MASTER.t_process_parameters ==> {} ms",millis);
		if (tempProcessParameterList.isEmpty()) {
			return null;
		} else {
			return tempProcessParameterList.get(0);
		}
	}

	/**
	 *Get HA Tag Status Info
	 */
	@Override
	public List<DeviceStatus> getHATagStatus(String deviceNumber, String deviceNo11charecter, String txDate) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		Stopwatch stopwatch = Stopwatch.createStarted();
		String queryFile = LoadJpaQueries.getQueryById(QatpClassificationConstant.GET_HA_TAG_STATUS);
		paramSource.addValue(QatpClassificationConstant.DEVICE_NUMBER, deviceNumber);
		paramSource.addValue(QatpClassificationConstant.DEVICE_NUMBER_11_CHAR, deviceNo11charecter);
		paramSource.addValue(QatpClassificationConstant.TX_DATE, txDate);	
		List<DeviceStatus> tempDeviceStatusList = namedJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(DeviceStatus.class));
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Time taken to select data from TPMS.T_HA_DEVICES ==> {} ms",millis);
		if (tempDeviceStatusList.isEmpty()) {
			return null;
		} else {
			return tempDeviceStatusList;
		}
	}

	/**
	 *Get Device Special Plan Info
	 */
	@Override
	public AccountPlanDetail getDeviceSpecialPlan(Long etcAccountId, Integer planType, Integer agencyId,
			String deviceNumber, String txDate) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(QatpClassificationConstant.GET_DEVICE_SPECIAL_PLAN);
		paramSource.addValue(QatpClassificationConstant.IN_ACCOUNT_ID, etcAccountId);
		paramSource.addValue(QatpClassificationConstant.IN_TX_DATE, txDate);
		paramSource.addValue(QatpClassificationConstant.IN_AGENCY_ID, agencyId);
		paramSource.addValue(QatpClassificationConstant.IN_DEVICE_NO, deviceNumber);
		List<AccountPlanDetail> tempDeviceStatusList = namedJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(AccountPlanDetail.class));

		if (tempDeviceStatusList.isEmpty()) {
			return null;
		} else {
			return tempDeviceStatusList.get(0);
		}
	}

	@Override
	public List<ProcessParameter> getAllProcessParameter() {
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_PROCESS_PARAMETER");
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<ProcessParameter>(ProcessParameter.class));
		
	}

	@Override
	public Long getAgency(Long etcAccountId) {

	String queryString = LoadJpaQueries.getQueryById(QatpClassificationConstant.GET_AGENCY_ID);
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("ETC_ACCOUNT_ID", etcAccountId);
		
		Long agencyId = namedJdbcTemplate.queryForObject(queryString, paramSource, Long.class);

		if (agencyId>0) {
			logger.info("Agency Id :" + agencyId);
			return agencyId;
		}else {
			return 0L;
		}
	}


	@Override
	public PlanDetails getPlanName(Long etcAccountId) {

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(QatpClassificationConstant.GET_ACCOUNT_PLAN);
		paramSource.addValue(QatpClassificationConstant.ETC_ACCOUNT_ID, etcAccountId);
		List<PlanDetails> planList = namedJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(PlanDetails.class));

		if (planList.isEmpty()) {
			logger.info("### Account Plan List is null ###");
			return null;
		} else {
			return planList.get(0);
		}
	}

}
