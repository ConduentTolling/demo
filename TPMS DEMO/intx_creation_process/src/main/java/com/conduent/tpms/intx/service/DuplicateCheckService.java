package com.conduent.tpms.intx.service;

import com.conduent.tpms.intx.model.AwayTransaction;

/**
 * 
 * Duplicate Check Service 
 * 
 * @author deepeshb
 *
 */
public interface DuplicateCheckService {

	/**
	 * Duplicate Validation on given transaction
	 * 
	 * @param awayTx
	 * @return boolean
	 */
	boolean validateDuplicateTx(AwayTransaction awayTx);
}
