package com.conduent.tpms.ictx.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.ictx.config.LoadJpaQueries;
import com.conduent.tpms.ictx.constants.IctxConstant;
import com.conduent.tpms.ictx.dao.PlazaDao;
import com.conduent.tpms.ictx.model.Plaza;

/**
 * Plaza Dao Implementation
 * 
 * @author deepeshb
 *
 */
@Repository
public class ICTXPlazaDaoImpl implements PlazaDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 *Get Plaza Info
	 */
	@Override
	public List<Plaza> getPlaza() {
		String queryToCheckFile = LoadJpaQueries.getQueryById(IctxConstant.GET_T_PLAZA);

		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<Plaza>(Plaza.class));
	}

}
