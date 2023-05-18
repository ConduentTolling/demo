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
import com.conduent.Ibtsprocessing.dao.ILaneDao;
import com.conduent.Ibtsprocessing.model.Agency;
import com.conduent.Ibtsprocessing.model.Lane;

@Repository
public class LaneDao implements ILaneDao {

	private static final Logger log = LoggerFactory.getLogger(LaneDao.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public List<Lane> getLane() {
		List<Lane> laneMappingDetails = new ArrayList<Lane>();

		String queryRules = LoadJpaQueries.getQueryById("GET_T_LANE");
		log.info("Lane parameter fetched from T_Lane table successfully.");

		laneMappingDetails = namedJdbcTemplate.query(queryRules, BeanPropertyRowMapper.newInstance(Lane.class));

		return laneMappingDetails;
	}

}
