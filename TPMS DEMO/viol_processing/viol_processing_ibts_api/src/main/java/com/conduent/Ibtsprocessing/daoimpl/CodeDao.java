package com.conduent.Ibtsprocessing.daoimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.Ibtsprocessing.constant.LoadJpaQueries;
import com.conduent.Ibtsprocessing.dao.ICodeDao;
import com.conduent.Ibtsprocessing.model.Codes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class CodeDao implements ICodeDao {

	private static final Logger log = LoggerFactory.getLogger(CodeDao.class);

	List<Codes> codesMappingDetails=new ArrayList<Codes>();

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;


	@Override
	public List<Codes> getCodes() {	

		String queryRules = LoadJpaQueries.getQueryById("GET_T_CODES");
		log.info("Process parameter fetched from T_CODES table successfully.");
		codesMappingDetails = namedJdbcTemplate.query(queryRules,
				BeanPropertyRowMapper.newInstance(Codes.class));
		return codesMappingDetails;

	}
	
	
	@Override
	public List<Codes> getCodesList(String codeType, String codeValue) {	
		List<Codes> codeList = codesMappingDetails.stream().filter(fixture -> fixture.getCodeType().equals(codeType) && fixture.getCodeValue().equals(codeValue)).collect(Collectors.toList());		
		return codeList;
	}
}
