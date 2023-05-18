package com.conduent.tpms.intx.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.intx.config.LoadJpaQueries;
import com.conduent.tpms.intx.constants.IntxConstant;
import com.conduent.tpms.intx.dao.TCodeDao;
import com.conduent.tpms.intx.model.TCode;


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
		String queryFile = LoadJpaQueries.getQueryById(IntxConstant.GET_T_CODE);
		paramSource.addValue(IntxConstant.CODE_TYPE, IntxConstant.ETC_TX_STATUS);
		return namedJdbcTemplate.query(queryFile, paramSource, BeanPropertyRowMapper.newInstance(TCode.class));
	}

}
