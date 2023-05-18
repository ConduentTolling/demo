package com.conduent.tpms.iag.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.iag.config.LoadJpaQueries;
import com.conduent.tpms.iag.dao.TProcessParamterDao;
import com.conduent.tpms.iag.dto.TProcessParameter;


@Repository
public class TProcessParameterDaoImpl implements TProcessParamterDao {

	private static final Logger log = LoggerFactory.getLogger(TProcessParameterDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	List<TProcessParameter> nameMappingDetails=new ArrayList<TProcessParameter>();

	@Override
	public List<TProcessParameter> getProcessParameters() {
		
		String queryRules = LoadJpaQueries.getQueryById("T_PROCESS_PARAMETER");
		// paramSource.addValue(QatpConstants.PARAM_NAME, paramName);

		log.info("Process parameter fetched from T_PROCESS_PARAMETER table successfully.");

		nameMappingDetails = namedJdbcTemplate.query(queryRules,
				BeanPropertyRowMapper.newInstance(TProcessParameter.class));

		return nameMappingDetails;
	}

	@Override
	public List<TProcessParameter> getProcessParametersList(String paramName) {
		
				getProcessParameters();
				List<TProcessParameter> paramList = nameMappingDetails.stream().filter(e-> e.getParamName().equals(paramName)).collect(Collectors.toList());	
		return paramList;
	}
	

}
