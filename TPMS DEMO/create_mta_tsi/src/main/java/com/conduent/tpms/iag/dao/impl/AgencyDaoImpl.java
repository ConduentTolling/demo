package com.conduent.tpms.iag.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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

}
