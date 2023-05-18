package com.conduent.tpms.unmatched.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.unmatched.constant.LoadJpaQueries;
import com.conduent.tpms.unmatched.constant.UnmatchedConstant;
import com.conduent.tpms.unmatched.dao.TCodeDao;
import com.conduent.tpms.unmatched.model.TCode;



/**
 * TCode Dao Implementation
 * 
 * @author deepeshb
 *
 */
@Repository
public class TCodeDaoImpl implements TCodeDao {
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	/**
	 *Get Account Status Info
	 */
	@Override
	public List<TCode> getAccountStatus() {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_T_CODE);
		paramSource.addValue(UnmatchedConstant.CODE_TYPE, UnmatchedConstant.ACCT_ACT_STATUS);
		return namedJdbcTemplate.query(queryFile, paramSource, BeanPropertyRowMapper.newInstance(TCode.class));
	}

	/**
	 *Get Fin Status Info
	 */
	@Override
	public List<TCode> getFinStatus() {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_T_CODE);
		paramSource.addValue(UnmatchedConstant.CODE_TYPE, UnmatchedConstant.ACCT_FIN_STATUS);
		return namedJdbcTemplate.query(queryFile, paramSource, BeanPropertyRowMapper.newInstance(TCode.class));
	}

	/**
	 *Get Device Status Info
	 */
	@Override
	public List<TCode> getDeviceStatus() {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_T_CODE);
		paramSource.addValue(UnmatchedConstant.CODE_TYPE, UnmatchedConstant.DEVICE_STATUS);
		return namedJdbcTemplate.query(queryFile, paramSource, BeanPropertyRowMapper.newInstance(TCode.class));
	}

	
	/**
	 *Get Account Type Info
	 */
	@Override
	public List<TCode> getAccountType() {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_T_CODE);
		paramSource.addValue(UnmatchedConstant.CODE_TYPE, UnmatchedConstant.ACCOUNT_TYPE);
		return namedJdbcTemplate.query(queryFile, paramSource, BeanPropertyRowMapper.newInstance(TCode.class));
	}

	/**
	 *Get Account Speed Status Info
	 */
	@Override
	public TCode getAccountSpeedStatus(Long etcAccountId, String txDate) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_ACCOUNT_SPEED_STATUS);
		paramSource.addValue(UnmatchedConstant.ETC_ACCOUNT_ID, etcAccountId);
		paramSource.addValue(UnmatchedConstant.IN_TX_DATE, txDate);
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
		String queryFile = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_DEVICE_SPEED_STATUS);
		paramSource.addValue(UnmatchedConstant.ETC_ACCOUNT_ID, etcAccountId);
		paramSource.addValue(UnmatchedConstant.DEVICE_NO, deviceNo);
		paramSource.addValue(UnmatchedConstant.IN_TX_DATE, txDate);
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
		String queryFile = LoadJpaQueries.getQueryById(UnmatchedConstant.GET_T_CODE);
		paramSource.addValue(UnmatchedConstant.CODE_TYPE, UnmatchedConstant.TX_STATUS);
		return namedJdbcTemplate.query(queryFile, paramSource, BeanPropertyRowMapper.newInstance(TCode.class));
	}

}
