package com.conduent.Ibtsprocessing.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.Ibtsprocessing.constant.LoadJpaQueries;
import com.conduent.Ibtsprocessing.constant.QatpClassificationConstant;
import com.conduent.Ibtsprocessing.dao.IOaAddressDao;
import com.conduent.Ibtsprocessing.model.OaAddress;

@Repository
public class OaAddressDao implements IOaAddressDao{
	
	private static final Logger log = LoggerFactory.getLogger(OaAddressDao.class);
	
	List<OaAddress> addressMappingDetails=new ArrayList<OaAddress>();

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public List<OaAddress> getAddress(String deviceNo) {

        MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryRules = LoadJpaQueries.getQueryById("GET_OA_ADDRESS_DETAIL");
		paramSource.addValue(QatpClassificationConstant.DEVICE_NO, deviceNo);
		addressMappingDetails = namedJdbcTemplate.query(queryRules, paramSource,
				BeanPropertyRowMapper.newInstance(OaAddress.class));
		log.info("OA Address fetched from T_OA_ADDRESS table successfully.");
		return addressMappingDetails;
		
	}

}
