package com.conduent.tpms.iag.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.iag.config.LoadJpaQueries;
import com.conduent.tpms.iag.dao.TAgencyDao;
import com.conduent.tpms.iag.model.AgencyInfoVO;

@Repository
public class TAgencyDaoImpl implements TAgencyDao {

	private static final Logger dao_log = LoggerFactory.getLogger(TAgencyDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<AgencyInfoVO> getAgencyInfoListforAway() {
			
			String queryRules =	LoadJpaQueries.getQueryById("SELECT_INFO_FROM_T_AGENCY_AWAY");
			
			dao_log.info("Agency info fetched from T_Agency table successfully.");
			
			return jdbcTemplate.query(queryRules, new BeanPropertyRowMapper<AgencyInfoVO>(AgencyInfoVO.class));
			
		}
	
	@Override
	public List<AgencyInfoVO> getAgencyInfoListforHome() {
			
			String queryRules =	LoadJpaQueries.getQueryById("SELECT_INFO_FROM_T_AGENCY_HOME");
			
			dao_log.info("Agency info fetched from T_Agency table successfully.");
			
			return jdbcTemplate.query(queryRules, new BeanPropertyRowMapper<AgencyInfoVO>(AgencyInfoVO.class));
			
		}
}
