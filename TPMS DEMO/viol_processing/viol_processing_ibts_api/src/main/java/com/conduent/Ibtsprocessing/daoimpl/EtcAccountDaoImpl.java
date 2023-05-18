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
import com.conduent.Ibtsprocessing.dao.IEtcAccountDao;
import com.conduent.Ibtsprocessing.model.EtcAccount;

@Repository
public class EtcAccountDaoImpl implements IEtcAccountDao {

	private static final Logger log = LoggerFactory.getLogger(EtcAccountDaoImpl.class);

	List<EtcAccount> etcAccountMappingDetails = new ArrayList<EtcAccount>();

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public List<EtcAccount> getEtcAccount() {
		String queryRules = LoadJpaQueries.getQueryById("GET_ETC_ACCOUNT");
		etcAccountMappingDetails = namedJdbcTemplate.query(queryRules,
				BeanPropertyRowMapper.newInstance(EtcAccount.class));
		log.info("ETC Account fetched from T_ETC_ACCOUNT table successfully.");
		return etcAccountMappingDetails;
	}

}
