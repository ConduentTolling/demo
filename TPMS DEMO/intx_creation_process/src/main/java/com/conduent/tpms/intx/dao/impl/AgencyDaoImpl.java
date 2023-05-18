package com.conduent.tpms.intx.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.intx.config.LoadJpaQueries;
import com.conduent.tpms.intx.constants.IntxConstant;
import com.conduent.tpms.intx.dao.AgencyDao;
import com.conduent.tpms.intx.model.Agency;

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
		String queryToCheckFile = LoadJpaQueries.getQueryById(IntxConstant.GET_T_AGENCY);
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<Agency>(Agency.class));
	}


}
