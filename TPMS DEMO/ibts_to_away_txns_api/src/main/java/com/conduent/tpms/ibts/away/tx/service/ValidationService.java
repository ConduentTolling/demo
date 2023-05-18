package com.conduent.tpms.ibts.away.tx.service;

import com.conduent.tpms.ibts.away.tx.model.AwayTransaction;

/**
 * ICTX Process Service Interface to start the process
 * 
 * @author deepeshb
 *
 */
public interface ValidationService {

	boolean validate(AwayTransaction awayTransaction);
}
