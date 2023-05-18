package com.conduent.tpms.intx.dao.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.intx.config.LoadJpaQueries;
import com.conduent.tpms.intx.constants.IntxConstant;
import com.conduent.tpms.intx.dao.ProcessParametersDao;
import com.conduent.tpms.intx.model.ProcessParameter;

/**
 * Process Parameter Dao 
 * 
 * @author deepeshb
 *
 */
@Repository
public class ProcessParametersDaoImpl implements ProcessParametersDao {

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	/**
	 *Retrieve Cut-off Date for Agency
	 */
	@Override
	public Optional<List<ProcessParameter>> getCutOffDateForAgency() {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(IntxConstant.GET_CUT_OFF_DATE);
		paramSource.addValue(IntxConstant.QUERY_PARAM_PARAM_NAME,
				Arrays.asList(IntxConstant.QUERY_PARAM_MTA_TOLL_CUTOFF_DATE,
						IntxConstant.QUERY_PARAM_MDTA_TOLL_CUTOFF_DATE, IntxConstant.QUERY_PARAM_NYSTA_TOLL_CUTOFF_DATE,
						IntxConstant.QUERY_PARAM_PANYNJ_TOLL_CUTOFF_DATE));
		List<ProcessParameter> tempProcessParameterList = namedJdbcTemplate.query(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(ProcessParameter.class));
		if (tempProcessParameterList.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(tempProcessParameterList);
		}
	}

}
