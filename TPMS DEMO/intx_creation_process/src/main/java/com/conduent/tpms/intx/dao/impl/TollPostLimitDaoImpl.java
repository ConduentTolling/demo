package com.conduent.tpms.intx.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.intx.config.LoadJpaQueries;
import com.conduent.tpms.intx.constants.IntxConstant;
import com.conduent.tpms.intx.dao.TollPostLimitDao;
import com.conduent.tpms.intx.model.TollPostLimit;

/**
 * Toll Post limit Dao
 * 
 * @author deepeshb
 *
 */
@Repository
public class TollPostLimitDaoImpl implements TollPostLimitDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Get Toll Post limit based on agency id and etc tx status
	 */
	@Override
	public Optional<List<TollPostLimit>> getTollPostLimitByAgencyIdAndEtcTxStatus(Integer etcTxStatus) {
		String query = LoadJpaQueries.getQueryById(IntxConstant.GET_T_TOLL_POST_LIMIT_BY_AGENCY_AND_ETC_TX_STATUS);
		List<TollPostLimit> list = jdbcTemplate.query(query,
				new BeanPropertyRowMapper<TollPostLimit>(TollPostLimit.class), etcTxStatus);
		if (!list.isEmpty()) {
			return Optional.of(list);
		}
		return Optional.empty();
	}
}
