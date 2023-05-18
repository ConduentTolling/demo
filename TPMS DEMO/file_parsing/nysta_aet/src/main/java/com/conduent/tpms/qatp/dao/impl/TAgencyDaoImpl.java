package com.conduent.tpms.qatp.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.qatp.config.LoadJpaQueries;
import com.conduent.tpms.qatp.constants.Constants;
import com.conduent.tpms.qatp.dao.TAgencyDao;
import com.conduent.tpms.qatp.dto.AgencyInfoVO;
import com.conduent.tpms.qatp.model.ProcessParameter;

@Repository
public class TAgencyDaoImpl implements TAgencyDao {

	private static final Logger dao_log = LoggerFactory.getLogger(TAgencyDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public List<AgencyInfoVO> getAgencyInfo() {
			
			String queryRules =	LoadJpaQueries.getQueryById("SELECT_INFO_FROM_T_AGENCY");
			
			dao_log.info("Agency info fetched from T_Agency table successfully.");
			
			return jdbcTemplate.query(queryRules, new BeanPropertyRowMapper<AgencyInfoVO>(AgencyInfoVO.class));
			
		}

	public String insertLane() 
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.param_name, Constants.INSERT_NEW_LANE);
		String queryToCheckFile = "Select param_value from MASTER.T_PROCESS_PARAMETERS where param_name =:param_name";

		String value = namedJdbcTemplate.queryForObject(queryToCheckFile, paramSource, String.class);
		if (value!=null) 
		{
			return value;
		}
		else
		{
			return null;
		}
	}

	
	
}
