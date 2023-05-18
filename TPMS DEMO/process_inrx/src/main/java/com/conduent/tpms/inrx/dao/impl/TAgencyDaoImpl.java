package com.conduent.tpms.inrx.dao.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.inrx.config.LoadJpaQueries;
import com.conduent.tpms.inrx.dao.TAgencyDao;
import com.conduent.tpms.inrx.model.AgencyInfoVO;
import com.conduent.tpms.inrx.model.ZipCode;

@Repository
public class TAgencyDaoImpl implements TAgencyDao {

	private static final Logger dao_log = LoggerFactory.getLogger(TAgencyDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<AgencyInfoVO> getAgencyInfoList() {
			
			String queryRules =	LoadJpaQueries.getQueryById("SELECT_INFO_FROM_T_AGENCY");
			
			dao_log.info("Agency info fetched from T_Agency table successfully.");
			
			return jdbcTemplate.query(queryRules, new BeanPropertyRowMapper<AgencyInfoVO>(AgencyInfoVO.class));
			
		}

	
	@Override
	public List<ZipCode> getZipCodeList() {
		
		String query = "select ROW_ID , ZIPCODE , CITY , STATE_PROV , COUNTRY from CRM.V_ZIPCODE";
		
		//return jdbcTemplate.query(query, new BeanPropertyRowMapper<String>(String.class));
		Stream<ZipCode> stream = jdbcTemplate.queryForStream(query, (rs, rowNum) ->
		  new ZipCode(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
		 
		 List<ZipCode> list = stream.collect(Collectors.toList());
		return list;
		
        
	}
}
