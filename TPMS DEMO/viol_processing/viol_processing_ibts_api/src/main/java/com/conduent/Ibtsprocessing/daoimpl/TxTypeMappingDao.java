package com.conduent.Ibtsprocessing.daoimpl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.conduent.Ibtsprocessing.constant.LoadJpaQueries;
import com.conduent.Ibtsprocessing.dao.ITxTypeMappingDao;
import com.conduent.Ibtsprocessing.model.TxTypeMapping;

@Repository
public class TxTypeMappingDao implements ITxTypeMappingDao{
	
	private static final Logger logger = LoggerFactory.getLogger(TxTypeMappingDao.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	List<TxTypeMapping> TxTypeMappingDetails=new ArrayList<TxTypeMapping>();

	@Override
	public List<TxTypeMapping> getTxTypeMappings() {
		
		String queryRules =	 LoadJpaQueries.getQueryById("T_TX_TYPE_MAPPING");
		
		logger.info("Process parameter fetched from T_TX_TYPE_MAPPING table successfully.");
		
		TxTypeMappingDetails = namedJdbcTemplate.query(queryRules,
				BeanPropertyRowMapper.newInstance(TxTypeMapping.class));
		
		return TxTypeMappingDetails;
	}

}
