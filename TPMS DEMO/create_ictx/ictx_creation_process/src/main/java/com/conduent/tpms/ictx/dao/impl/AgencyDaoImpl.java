package com.conduent.tpms.ictx.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.ictx.config.LoadJpaQueries;
import com.conduent.tpms.ictx.constants.IctxConstant;
import com.conduent.tpms.ictx.dao.AgencyDao;
import com.conduent.tpms.ictx.model.Agency;

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
		String queryToCheckFile = LoadJpaQueries.getQueryById(IctxConstant.GET_T_AGENCY);
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<Agency>(Agency.class));
	}


}
