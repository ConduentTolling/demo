package com.conduent.tpms.iag.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.iag.config.LoadJpaQueries;
import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.dao.AgencyDao;
import com.conduent.tpms.iag.model.Agency;

/**
 * 
 * Agency Dao Implementation
 * 
 * @author urvashic
 *
 */
@Repository
public class AgencyDaoImpl implements AgencyDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	/**
	 * 
	 * To get Agency Info
	 * 
	 * @return List<Agency>
	 */
	@Override
	public List<Agency> getAgency() {
		String queryToCheckFile = LoadJpaQueries.getQueryById(Constants.LOAD_T_AGENCY);
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<Agency>(Agency.class));
	}
	
	/**
	 * to get Home Device prefix list
	 * 
	 * @return
	 */
	@Override
	public List<String> getHomeAgencyList() {
		String queryToCheckFile = LoadJpaQueries.getQueryById(Constants.SELECT_HOME_DEVICE_PREFIX);
		return jdbcTemplate.queryForList(queryToCheckFile, String.class);
	}
	
	/**
	 * @return Agency
	 */
	@Override
	public Agency getHomeAgency() {
		String queryToCheckFile = LoadJpaQueries.getQueryById(Constants.SELECT_HOME_AGENCY);
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		Agency ageInfo = namedJdbcTemplate.queryForObject(queryToCheckFile, paramSource,
				BeanPropertyRowMapper.newInstance(Agency.class));
		return ageInfo;
	}

	
	/**
	 * to get Home Device prefix list
	 * 
	 * @return
	 */
	@Override
	public List<String> getHomeAgencyDevicePrefixList() {
		String queryToCheckFile = LoadJpaQueries.getQueryById(Constants.SELECT_HOME_DEVICE_PREFIX);
		return jdbcTemplate.queryForList(queryToCheckFile, String.class);
	}

	/**
	 * @return List<Agency>
	 */
	@Override
	public List<Agency> getAwayAgencyList() {
		String queryToCheckFile = LoadJpaQueries.getQueryById(Constants.SELECT_AWAY_AGENCY_LIST);
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<Agency>(Agency.class));
	}
	
}
