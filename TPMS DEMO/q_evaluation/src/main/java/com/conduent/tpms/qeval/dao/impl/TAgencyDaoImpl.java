package com.conduent.tpms.qeval.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.qeval.config.LoadJpaQueries;
import com.conduent.tpms.qeval.dao.TAgencyDao;
import com.conduent.tpms.qeval.dto.AgencyInfoVO;
import com.conduent.tpms.qeval.model.TranDetail;

@Repository
public class TAgencyDaoImpl implements TAgencyDao {

	private static final Logger dao_log = LoggerFactory.getLogger(TAgencyDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<AgencyInfoVO> getAgencyInfoList() {

		String queryRules = LoadJpaQueries.getQueryById("SELECT_INFO_FROM_T_AGENCY");

		dao_log.info("Agency info fetched from T_Agency table successfully.");

		return jdbcTemplate.query(queryRules, new BeanPropertyRowMapper<AgencyInfoVO>(AgencyInfoVO.class));

	}

	//remove
	
	@SuppressWarnings("deprecation")
	@Override
	public String getUnresgisteredStaus(TranDetail tranDetail) {
		String result = null;
		try {
			String queryRules = LoadJpaQueries.getQueryById("GET_RESGISTERED_STATUS");
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
	//		paramSource.addValue(Constants.DEVICE_NO, tranDetail.getDeviceNo());
	//		paramSource.addValue(Constants.TX_DATE, tranDetail.getTxDate());
			result = (String) jdbcTemplate.queryForObject(queryRules, new Object[] {}, String.class);
			tranDetail.setTxStatus((result != null) ? result : null);
		} catch (EmptyResultDataAccessException e) {
			dao_log.info("no records found in getAccountInfoThreeList :{}", e);
			return null;
		}
		return result;
	}


/*
		Stopwatch stopwatch = Stopwatch.createStarted();
		stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		dao_log.info("Total time taken to execute getLaneIdExtLaneIdInfo query {}", millis);

	} */
}
