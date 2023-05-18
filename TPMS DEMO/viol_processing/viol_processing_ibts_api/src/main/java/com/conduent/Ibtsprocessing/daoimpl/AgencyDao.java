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
import com.conduent.Ibtsprocessing.dao.IAgencyDao;
import com.conduent.Ibtsprocessing.model.Agency;

@Repository
public class AgencyDao implements IAgencyDao {

	private static final Logger log = LoggerFactory.getLogger(AgencyDao.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	List<Agency> agencyMappingDetails = new ArrayList<Agency>();

	@Override
	public List<Agency> getAgency() {
		String queryRules = LoadJpaQueries.getQueryById("GET_T_AGENCY");
		log.info("Agency parameter fetched from T_AGENCY table successfully.");

		agencyMappingDetails = namedJdbcTemplate.query(queryRules, BeanPropertyRowMapper.newInstance(Agency.class));

		return agencyMappingDetails;
	}

}
