package com.conduent.tpms.recon.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.recon.config.LoadJpaQueries;
import com.conduent.tpms.recon.dao.TCodeDao;
import com.conduent.tpms.recon.model.TCode;

/**
 * 
 * @author taniyan
 *
 */
@Repository
public class TCodeDaoImpl implements TCodeDao {
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;


	/**
	 *
	 * @return List<TCode>
	 */
	@Override
	public List<TCode> getFinStatus() {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById("SELECT_T_CODE");
		return namedJdbcTemplate.query(queryFile, paramSource, BeanPropertyRowMapper.newInstance(TCode.class));
	}
	
	

}
