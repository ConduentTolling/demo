package com.conduent.tpms.ibts.away.tx.dao;

import com.conduent.tpms.ibts.away.tx.model.AwayTransaction;

/**
 * Away Transaction Dao
 * 
 * @author deepeshb
 *
 */
public interface AwayTransactionDao {

	void insert(AwayTransaction awayTransaction);

	AwayTransaction checkLaneTxId(Long laneTxId);

}
