package com.conduent.tpms.iag.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.iag.config.LoadJpaQueries;
import com.conduent.tpms.iag.dao.LaneTxReconPolicyDao;
import com.conduent.tpms.iag.model.LaneTxReconPolicy;
@Repository
public class LaneTxReconPolicyDaoImpl implements LaneTxReconPolicyDao {
	
	private static final Logger dao_log = LoggerFactory.getLogger(LaneTxReconPolicyDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<LaneTxReconPolicy> getLaneTxReconPolicy(){
		
		String queryRules =	LoadJpaQueries.getQueryById("SELECT_INFO_FROM_T_LANE_TX_RECON_POLICY");
		
		dao_log.info("Agency info fetched from T_LANE_TX_RECON_POLICY table successfully.");
		
		return jdbcTemplate.query(queryRules, new BeanPropertyRowMapper<LaneTxReconPolicy>(LaneTxReconPolicy.class));

	}
}
