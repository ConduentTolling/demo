package com.conduent.tollposting.daoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.conduent.tollposting.config.LoadJpaQueries;
import com.conduent.tollposting.dao.IPlazaDao;
import com.conduent.tollposting.model.Plaza;

@Repository
public class PlazaDao implements IPlazaDao
{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Plaza> getPlaza()
	{
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_T_PLAZA");

		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<Plaza>(Plaza.class));
	}
}
