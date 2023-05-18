package com.conduent.tpms.ictx.dao;

import com.conduent.tpms.ictx.model.AwayTransaction;

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

	Double getTollAmount(Long laneTxId);
}
