package com.conduent.tpms.ictx.service;

import com.conduent.tpms.ictx.model.AwayTransaction;

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
