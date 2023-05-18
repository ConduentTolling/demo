package com.conduent.Ibtsprocessing.daoimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.Ibtsprocessing.constant.LoadJpaQueries;
import com.conduent.Ibtsprocessing.dao.IProcessParameterDao;
import com.conduent.Ibtsprocessing.model.ProcessParameters;

@Repository
public class ProcessParameterDao implements IProcessParameterDao {

	private static final Logger log = LoggerFactory.getLogger(ProcessParameterDao.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	List<ProcessParameters> processMappingDetails = new ArrayList<ProcessParameters>();

	@Override
	public List<ProcessParameters> getProcessParameters() {

		String queryRules = LoadJpaQueries.getQueryById("T_PROCESS_PARAMETER");
		// paramSource.addValue(QatpConstants.PARAM_NAME, paramName);

		log.info("Process parameter fetched from T_PROCESS_PARAMETER table successfully.");

		processMappingDetails = namedJdbcTemplate.query(queryRules,
				BeanPropertyRowMapper.newInstance(ProcessParameters.class));

		return processMappingDetails;
	}

	@Override
	public List<ProcessParameters> getProcessParametersList(String paramName) {

		getProcessParameters();
		List<ProcessParameters> paramList = processMappingDetails.stream()
				.filter(e -> e.getParamName().equals(paramName)).collect(Collectors.toList());
		return paramList;
	}

}
