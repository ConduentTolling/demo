package com.conduent.tpms.intx.dao;

import com.conduent.tpms.intx.model.AwayTransaction;

/**
 * Transaction Detail Dao
 * 
 * @author deepeshb
 *
 */
public interface TransactionDetailDao {

	/**
	 * Update Transaction Detail
	 * 
	 * @param AwayTransaction
	 */
	void updateTransactionDetail(AwayTransaction awayTx);
	
	/**
	 * Update Etc tx status
	 * 
	 * @param AwayTransaction
	 */
	void updateEtcTxStatus(AwayTransaction awayTx);
}
