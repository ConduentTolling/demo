package com.conduent.tpms.qatp.classification.dao.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.qatp.classification.config.LoadJpaQueries;
import com.conduent.tpms.qatp.classification.constants.QatpClassificationConstant;
import com.conduent.tpms.qatp.classification.dao.TCodeDao;
import com.conduent.tpms.qatp.classification.model.TCode;
import com.google.common.base.Stopwatch;

/**
 * TCode Dao Implementation
 * 
 * @author deepeshb
 *
 */
@Repository
public class TCodeDaoImpl implements TCodeDao {
	
	private static final Logger logger = LoggerFactory.getLogger(TCodeDaoImpl.class);
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	/**
	 *Get Account Status Info
	 */
	@Override
	public List<TCode> getAccountStatus() {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(QatpClassificationConstant.GET_T_CODE);
		paramSource.addValue(QatpClassificationConstant.CODE_TYPE, QatpClassificationConstant.ACCT_ACT_STATUS);
		return namedJdbcTemplate.query(queryFile, paramSource, BeanPropertyRowMapper.newInstance(TCode.class));
	}

	/**
	 *Get Fin Status Info
	 */
	@Override
	public List<TCode> getFinStatus() {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(QatpClassificationConstant.GET_T_CODE);
		paramSource.addValue(QatpClassificationConstant.CODE_TYPE, QatpClassificationConstant.ACCT_FIN_STATUS);
		return namedJdbcTemplate.query(queryFile, paramSource, BeanPropertyRowMapper.newInstance(TCode.class));
	}

	/**
	 *Get Device Status Info
	 */
	@Override
	public List<TCode> getDeviceStatus() {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		Stopwatch stopwatch = Stopwatch.createStarted();
		String queryFile = LoadJpaQueries.getQueryById(QatpClassificationConstant.GET_T_CODE);
		paramSource.addValue(QatpClassificationConstant.CODE_TYPE, QatpClassificationConstant.DEVICE_STATUS);
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Time taken to select data from T_CODE table Shrikant ==> {}ms", millis);
		return namedJdbcTemplate.query(queryFile, paramSource, BeanPropertyRowMapper.newInstance(TCode.class));
	}

	
	/**
	 *Get Account Type Info
	 */
	@Override
	public List<TCode> getAccountType() {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(QatpClassificationConstant.GET_T_CODE);
		paramSource.addValue(QatpClassificationConstant.CODE_TYPE, QatpClassificationConstant.ACCOUNT_TYPE);
		return namedJdbcTemplate.query(queryFile, paramSource, BeanPropertyRowMapper.newInstance(TCode.class));
	}

	/**
	 *Get Account Speed Status Info
	 */
	@Override
	public TCode getAccountSpeedStatus(Long etcAccountId, String txDate) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(QatpClassificationConstant.GET_ACCOUNT_SPEED_STATUS);
		paramSource.addValue(QatpClassificationConstant.ETC_ACCOUNT_ID, etcAccountId);
		paramSource.addValue(QatpClassificationConstant.IN_TX_DATE, txDate);
		List<TCode> list = namedJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(TCode.class));
		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 *Get Device Speed Status Info
	 */
	@Override
	public TCode getDeviceSpeedStatus(Long etcAccountId, String deviceNo, String txDate) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(QatpClassificationConstant.GET_DEVICE_SPEED_STATUS);
		paramSource.addValue(QatpClassificationConstant.ETC_ACCOUNT_ID, etcAccountId);
		paramSource.addValue(QatpClassificationConstant.DEVICE_NO, deviceNo);
		paramSource.addValue(QatpClassificationConstant.IN_TX_DATE, txDate);
		List<TCode> list = namedJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(TCode.class));

		if ( !list.isEmpty()) {
			return list.get(0);
		}

		return null;
		
	}

	@Override
	public List<TCode> getTxStatus() {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(QatpClassificationConstant.GET_T_CODE);
		paramSource.addValue(QatpClassificationConstant.CODE_TYPE, QatpClassificationConstant.TX_STATUS);
		return namedJdbcTemplate.query(queryFile, paramSource, BeanPropertyRowMapper.newInstance(TCode.class));
	}

}
