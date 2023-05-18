package com.conduent.tpms.qeval.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.qeval.config.LoadJpaQueries;
import com.conduent.tpms.qeval.dao.TProcessParamDao;
import com.conduent.tpms.qeval.model.TProcessParam;

@Repository
public class TProcessParamDaoImpl implements TProcessParamDao {
	private static final Logger log = LoggerFactory.getLogger(TProcessParamDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<TProcessParam> getProcessParamList() {
		try {
			log.info("Loading TProcessParam list");
			String queryFile = LoadJpaQueries.getQueryById("GET_ALL_AWAY_AGENCIES");
			return jdbcTemplate.query(queryFile, new BeanPropertyRowMapper<TProcessParam>(TProcessParam.class));
		} catch (Exception e) {
			log.info("Exception caught...{}", e);
		}
		return null;

	}

}
