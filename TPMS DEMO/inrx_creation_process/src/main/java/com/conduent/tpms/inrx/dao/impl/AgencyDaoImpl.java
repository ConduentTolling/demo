package com.conduent.tpms.inrx.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.inrx.config.LoadJpaQueries;
import com.conduent.tpms.inrx.constants.Constants;
import com.conduent.tpms.inrx.dao.AgencyDao;
import com.conduent.tpms.inrx.model.Agency;

/**
 * Agency Dao Implementation
 * 
 * @author petetid
 *
 */
@Repository
public class AgencyDaoImpl implements AgencyDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Get Agency Info
	 */
	@Override
	public List<Agency> getAgency() {
		String queryToCheckFile = LoadJpaQueries.getQueryById(Constants.GET_T_AGENCY);
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<Agency>(Agency.class));
	}

}
