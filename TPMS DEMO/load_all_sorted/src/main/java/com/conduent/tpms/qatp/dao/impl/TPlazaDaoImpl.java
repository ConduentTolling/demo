package com.conduent.tpms.qatp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.qatp.config.LoadJpaQueries;
import com.conduent.tpms.qatp.dao.TPlazaDao;
import com.conduent.tpms.qatp.model.TPlaza;

@Repository
public class TPlazaDaoImpl implements TPlazaDao {
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;


	@Override
	public List<TPlaza> getAll() {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById("GET_T_PLAZA");
		return namedJdbcTemplate.query(queryFile, paramSource, BeanPropertyRowMapper.newInstance(TPlaza.class));
	}
	
	

}
