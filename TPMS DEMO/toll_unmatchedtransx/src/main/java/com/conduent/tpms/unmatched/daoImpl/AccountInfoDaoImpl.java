package com.conduent.tpms.unmatched.daoImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.unmatched.constant.LoadJpaQueries;
import com.conduent.tpms.unmatched.constant.UnmatchedConstant;
import com.conduent.tpms.unmatched.dao.AccountInfoDao;
import com.conduent.tpms.unmatched.model.AccountInfo;
import com.conduent.tpms.unmatched.model.AccountPlanDetail;
import com.conduent.tpms.unmatched.model.DeviceAway;
import com.conduent.tpms.unmatched.model.DeviceStatus;
import com.conduent.tpms.unmatched.model.ProcessParameter;

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
	public DeviceStatus getDeviceStatus(String deviceNumber, LocalDateTime txTimeStamp) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_DEVICE_STATUS);
		paramSource.addValue(UnmatchedConstant.DEVICE_NUMBER, deviceNumber);
		//DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
		Timestamp ts = Timestamp.valueOf(txTimeStamp);
		System.out.println("TimeStamp: ===> "+ ts);
		paramSource.addValue(UnmatchedConstant.TX_TIMESTAMP, ts);
		List<DeviceStatus> tempDeviceStatusList = namedJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(DeviceStatus.class));
		if (tempDeviceStatusList.isEmpty()) {
			return null;
		} else {
			return tempDeviceStatusList.get(0);
		}
	}

	/**
	 *Get Account Info
	 */
	@Override
	public AccountInfo getAccountInfo(Long etcAccountId,String txDate) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_ACCOUNT_INFO);
		paramSource.addValue(UnmatchedConstant.ETC_ACCOUNT_ID, etcAccountId);
		paramSource.addValue(UnmatchedConstant.TX_DATE, txDate);
		List<AccountInfo> tempAccountInfoList = namedJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(AccountInfo.class));
		if (tempAccountInfoList.isEmpty()) {
			return null;
		} else {
			return tempAccountInfoList.get(0);
		}
	}

	/**
	 *Get H Device Status Info
	 */
	@Override
	public DeviceStatus getHDeviceStatus(String deviceNumber, LocalDateTime txTimeStamp) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_H_DEVICE_STATUS);
		paramSource.addValue(UnmatchedConstant.DEVICE_NUMBER, deviceNumber);
		
		Timestamp ts = Timestamp.valueOf(txTimeStamp);
		paramSource.addValue(UnmatchedConstant.TX_TIMESTAMP, ts);
		List<DeviceStatus> tempDeviceStatusList = namedJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(DeviceStatus.class));
		if (tempDeviceStatusList.isEmpty()) {
			return null;
		} else {
			return tempDeviceStatusList.get(0);
		}
	}

	/**
	 *Get Away Device Info
	 */
	@Override
	public DeviceAway findDeviceAway(String deviceNumber, LocalDateTime txTimeStamp) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(UnmatchedConstant.FIND_DEVICE_AWAY);
		paramSource.addValue(UnmatchedConstant.DEVICE_NUMBER, deviceNumber);
		paramSource.addValue(UnmatchedConstant.TX_TIMESTAMP, txTimeStamp);
		List<DeviceAway> tempDeviceAwayList = namedJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(DeviceAway.class));
		if (tempDeviceAwayList.isEmpty()) {
			return null;
		} else {
			return tempDeviceAwayList.get(0);
		}
	}

	/**
	 *Get Process Parameter Info
	 */
	@Override
	public ProcessParameter getProcessParameter(String paramName, Long agencyId) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_PROCESS_PARAMETER);
		paramSource.addValue(UnmatchedConstant.PARAM_NAME, paramName);
		paramSource.addValue(UnmatchedConstant.AGENCY_ID, agencyId);
		List<ProcessParameter> tempProcessParameterList = namedJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(ProcessParameter.class));
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
	public DeviceStatus getHATagStatus(String deviceNumber, String txDate) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_HA_TAG_STATUS);
		paramSource.addValue(UnmatchedConstant.DEVICE_NUMBER, deviceNumber);
		paramSource.addValue(UnmatchedConstant.TX_DATE, txDate);
		List<DeviceStatus> tempDeviceStatusList = namedJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(DeviceStatus.class));
		if (tempDeviceStatusList.isEmpty()) {
			return null;
		} else {
			return tempDeviceStatusList.get(0);
		}
	}

	/**
	 *Get Device Special Plan Info
	 */
	@Override
	public AccountPlanDetail getDeviceSpecialPlan(Long etcAccountId, Integer planType, Integer agencyId,
			String deviceNumber, String txDate) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_DEVICE_SPECIAL_PLAN);
		paramSource.addValue(UnmatchedConstant.IN_ACCOUNT_ID, etcAccountId);
		paramSource.addValue(UnmatchedConstant.IN_TX_DATE, txDate);
		paramSource.addValue(UnmatchedConstant.IN_AGENCY_ID, agencyId);
		paramSource.addValue(UnmatchedConstant.IN_DEVICE_NO, deviceNumber);
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

}
