package com.conduent.tpms.ictx.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.ictx.config.LoadJpaQueries;
import com.conduent.tpms.ictx.constants.IctxConstant;
import com.conduent.tpms.ictx.dao.TransactionDetailDao;
import com.conduent.tpms.ictx.model.AwayTransaction;

/**
 * Transaction Detail Dao
 * 
 * @author deepeshb
 *
 */
@Repository
public class TransactionDetailDaoImpl implements TransactionDetailDao {

	private static final Logger logger = LoggerFactory.getLogger(TransactionDetailDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	/**
	 * Update Transaction Detail
	 * 
	 * @param AwayTransaction
	 */
	@Override
	public void updateTransactionDetail(AwayTransaction awayTx) {
		String query = LoadJpaQueries.getQueryById(IctxConstant.UPDATE_T_TRANS_TABLE);
		jdbcTemplate.update(query, awayTx.getTxType(), awayTx.getTxSubType(), awayTx.getEtcTxStatus(),
				awayTx.getLaneTxId());
	}

	/**
	 * Update Etc tx status
	 * 
	 * @param AwayTransaction
	 */
	@Override
	public void updateEtcTxStatus(AwayTransaction awayTx) {
		String query = LoadJpaQueries.getQueryById(IctxConstant.UPDATE_T_TRANS_TABLE_ETC_TX_STATUS);
		jdbcTemplate.update(query, awayTx.getEtcTxStatus(), awayTx.getLaneTxId());

	}

	@Override
	public Double getTollAmount(Long laneTxId) {
		// TODO Auto-generated method stub
		try {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById(IctxConstant.GET_ITOL_AMOUNT_T_TRAN_DETAIL);
		queryFile = queryFile+laneTxId;
		logger.info("in TransactionDetailDaoImpl.getTollAmount..queryFile::{}",queryFile);
		//paramSource.addValue(IctxConstant.QUERY_PARAM_LANE_TX_ID, laneTxId);
		//System.out.println("queryFile "+ queryFile);
		//List<Double> tempAwayTxList = namedParameterJdbcTemplate.query(queryFile, paramSource,BeanPropertyRowMapper.newInstance(Double.class));
		return jdbcTemplate.queryForObject(queryFile, Double.class);
		
		}catch(Exception e){
			logger.info("Exception in TransactionDetailDaoImpl.getTollAmount..{}",e.getMessage());
			return 0.0;
		}
		
	}

}
