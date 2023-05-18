package com.conduent.tpms.icrx.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.icrx.config.LoadJpaQueries;
import com.conduent.tpms.icrx.constants.IcrxSchedulerConstant;
import com.conduent.tpms.icrx.dao.AgencyDao;
import com.conduent.tpms.icrx.model.Agency;

/**
 * Agency Dao Implementation
 * 
 * @author deepeshb
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
		String queryToCheckFile = LoadJpaQueries.getQueryById(IcrxSchedulerConstant.GET_T_AGENCY);
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<Agency>(Agency.class));
	}


}
