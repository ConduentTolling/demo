package com.conduent.Ibtsprocessing.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.Ibtsprocessing.constant.LoadJpaQueries;
import com.conduent.Ibtsprocessing.dao.IPlazaDao;
import com.conduent.Ibtsprocessing.model.Lane;
import com.conduent.Ibtsprocessing.model.Plaza;

@Repository
public class PlazaDao implements IPlazaDao{

	
	private static final Logger log = LoggerFactory.getLogger(PlazaDao.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Override
	public List<Plaza> getPlaza() {
		
		List<Plaza> plazaMappingDetails = new ArrayList<Plaza>();

		String queryRules = LoadJpaQueries.getQueryById("GET_T_PLAZA");
		log.info("Plaza parameter fetched from T_PLAZA table successfully.");

		plazaMappingDetails = namedJdbcTemplate.query(queryRules, BeanPropertyRowMapper.newInstance(Plaza.class));

		return plazaMappingDetails;
	}

	

}
