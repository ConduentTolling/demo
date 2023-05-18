package com.conduent.Ibtsprocessing.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.Ibtsprocessing.constant.LoadJpaQueries;
import com.conduent.Ibtsprocessing.dto.Exclusion;
import com.conduent.Ibtsprocessing.service.IViolTxExclusionService;

@Repository
public class ViolTxExclusionService implements IViolTxExclusionService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Exclusion> getAllViolTxExclusion() {
		String queryToCheckFile = LoadJpaQueries.getQueryById("GET_ALL_T_VIOL_TX_EXCLUSION");
		return jdbcTemplate.query(queryToCheckFile, new BeanPropertyRowMapper<Exclusion>(Exclusion.class));
	}

}
