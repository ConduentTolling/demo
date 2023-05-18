package com.conduent.tpms.ictx.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.ictx.config.LoadJpaQueries;
import com.conduent.tpms.ictx.constants.IctxConstant;
import com.conduent.tpms.ictx.dao.TCodeDao;
import com.conduent.tpms.ictx.model.TCode;


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
	 *Get Etc tx status
	 */
	@Override
	public List<TCode> getEtcTxStatus() {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(IctxConstant.GET_T_CODE);
		paramSource.addValue(IctxConstant.CODE_TYPE, IctxConstant.ETC_TX_STATUS);
		return namedJdbcTemplate.query(queryFile, paramSource, BeanPropertyRowMapper.newInstance(TCode.class));
	}
	
	/**
	 *Get tx status
	 */
	@Override
	public List<TCode> getTxStatus() {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(IctxConstant.GET_T_CODE);
		paramSource.addValue(IctxConstant.CODE_TYPE, IctxConstant.TX_STATUS);
		return namedJdbcTemplate.query(queryFile, paramSource, BeanPropertyRowMapper.newInstance(TCode.class));
	}

}
