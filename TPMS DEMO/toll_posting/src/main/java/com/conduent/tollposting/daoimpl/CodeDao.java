package com.conduent.tollposting.daoimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.conduent.tollposting.config.LoadJpaQueries;
import com.conduent.tollposting.dao.ICodeDao;
import com.conduent.tollposting.model.Codes;

@Repository
public class CodeDao implements ICodeDao 
{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Codes> getCodes()
	{

		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_T_CODES");

		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<Codes>(Codes.class));
	}

}
