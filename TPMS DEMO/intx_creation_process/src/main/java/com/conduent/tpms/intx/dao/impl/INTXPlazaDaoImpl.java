package com.conduent.tpms.intx.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.intx.config.LoadJpaQueries;
import com.conduent.tpms.intx.constants.IntxConstant;
import com.conduent.tpms.intx.dao.PlazaDao;
import com.conduent.tpms.intx.model.Plaza;

/**
 * Plaza Dao Implementation
 * 
 * @author deepeshb
 *
 */
@Repository
public class INTXPlazaDaoImpl implements PlazaDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 *Get Plaza Info
	 */
	@Override
	public List<Plaza> getPlaza() {
		String queryToCheckFile = LoadJpaQueries.getQueryById(IntxConstant.GET_T_PLAZA);

		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<Plaza>(Plaza.class));
	}

}
