package com.conduent.tpms.intx.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.intx.config.LoadJpaQueries;
import com.conduent.tpms.intx.constants.IntxConstant;
import com.conduent.tpms.intx.dao.TransactionDetailDao;
import com.conduent.tpms.intx.model.AwayTransaction;

/**
 * Transaction Detail Dao
 * 
 * @author deepeshb
 *
 */
@Repository
public class TransactionDetailDaoImpl implements TransactionDetailDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Update Transaction Detail
	 * 
	 * @param AwayTransaction
	 */
	@Override
	public void updateTransactionDetail(AwayTransaction awayTx) {
		String query = LoadJpaQueries.getQueryById(IntxConstant.UPDATE_T_TRANS_TABLE);
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
		String query = LoadJpaQueries.getQueryById(IntxConstant.UPDATE_T_TRANS_TABLE_ETC_TX_STATUS);
		jdbcTemplate.update(query, awayTx.getEtcTxStatus(), awayTx.getLaneTxId());

	}

}
