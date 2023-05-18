package com.conduent.tpms.qatp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.qatp.config.LoadJpaQueries;
import com.conduent.tpms.qatp.dao.TPlazaDao;
import com.conduent.tpms.qatp.dto.Tplaza;
@Repository
public class TPlazaDaoImpl implements TPlazaDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Tplaza> getAll() {
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_T_PLAZA");

		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<Tplaza>(Tplaza.class));
	}

}
