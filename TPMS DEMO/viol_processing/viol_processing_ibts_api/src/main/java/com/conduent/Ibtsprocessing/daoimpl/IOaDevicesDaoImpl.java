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
import com.conduent.Ibtsprocessing.dao.IOaDevicesDao;
import com.conduent.Ibtsprocessing.model.OADevices;

@Repository
public class IOaDevicesDaoImpl implements IOaDevicesDao{

	private static final Logger log = LoggerFactory.getLogger(IOaDevicesDaoImpl.class);

	List<OADevices> oaDevicesMappingDetails=new ArrayList<OADevices>();

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	
	@Override
	public List<OADevices> getOaDevices() {
		String queryRules = LoadJpaQueries.getQueryById("GET_T_O_A_DEVICES");
		log.info("O A Devices fetched from T_CODES table successfully.");
		oaDevicesMappingDetails = namedJdbcTemplate.query(queryRules,
				BeanPropertyRowMapper.newInstance(OADevices.class));
		return oaDevicesMappingDetails;

	}

}
