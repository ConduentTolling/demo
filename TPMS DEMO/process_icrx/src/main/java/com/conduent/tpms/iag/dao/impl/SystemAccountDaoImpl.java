package com.conduent.tpms.iag.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.iag.config.LoadJpaQueries;
import com.conduent.tpms.iag.dao.SystemAccountDao;
import com.conduent.tpms.iag.model.SystemAccountVO;
import com.conduent.tpms.iag.model.TCodesVO;

@Repository
public class SystemAccountDaoImpl implements SystemAccountDao{

	private static final Logger dao_log = LoggerFactory.getLogger(TCodeDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<SystemAccountVO> getAccount() {


		String queryRules =	LoadJpaQueries.getQueryById("GET_SYS_ACCOUNT");
		
		dao_log.info("SystemAccount info fetched from t_process_parameters table successfully.");
		
		return jdbcTemplate.query(queryRules, new BeanPropertyRowMapper<SystemAccountVO>(SystemAccountVO.class));
	}

}
