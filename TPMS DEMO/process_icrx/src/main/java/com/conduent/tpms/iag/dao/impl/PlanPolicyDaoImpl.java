package com.conduent.tpms.iag.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.iag.config.LoadJpaQueries;
import com.conduent.tpms.iag.dao.PlanPolicyDao;
import com.conduent.tpms.iag.model.PlanPolicyVO;

@Repository
public class PlanPolicyDaoImpl implements PlanPolicyDao{

	private static final Logger dao_log = LoggerFactory.getLogger(PlanPolicyDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<PlanPolicyVO> getPlanPolicy() {
		
		String queryRules =	LoadJpaQueries.getQueryById("GET_V_POLICY");
		
		dao_log.info("Plan Policy info fetched from V_Policy table successfully.");
		
		return jdbcTemplate.query(queryRules, new BeanPropertyRowMapper<PlanPolicyVO>(PlanPolicyVO.class));
		
	}

}
