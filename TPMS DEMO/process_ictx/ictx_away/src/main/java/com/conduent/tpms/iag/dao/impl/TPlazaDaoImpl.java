package com.conduent.tpms.iag.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.conduent.tpms.iag.config.LoadJpaQueries;
import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.dao.TPlazaDao;
import com.conduent.tpms.iag.dto.Plaza;
@Repository
public class TPlazaDaoImpl implements TPlazaDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Plaza> getAll() {
		String queryToCheckFile = LoadJpaQueries.getQueryById(Constants.GET_ALL_T_PLAZA);
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<Plaza>(Plaza.class));
	}

}
