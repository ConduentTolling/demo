package com.conduent.tpms.iag.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.iag.config.LoadJpaQueries;
import com.conduent.tpms.iag.dao.TCodesDao;
import com.conduent.tpms.iag.model.AgencyInfoVO;
import com.conduent.tpms.iag.model.TCodesVO;

@Repository
public class TCodeDaoImpl implements TCodesDao{
	
	private static final Logger dao_log = LoggerFactory.getLogger(TCodeDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@Override
	public List<TCodesVO> getTCodes() {

		String queryRules =	LoadJpaQueries.getQueryById("GET_T_CODES");
		
		dao_log.info("T_CODES info fetched from T_Codes table successfully.");
		
		return jdbcTemplate.query(queryRules, new BeanPropertyRowMapper<TCodesVO>(TCodesVO.class));
	}


}
