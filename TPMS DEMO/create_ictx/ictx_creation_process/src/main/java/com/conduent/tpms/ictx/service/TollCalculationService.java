package com.conduent.tpms.ictx.service;

import com.conduent.tpms.ictx.model.AwayTransaction;

/**
 * Toll Calculation Logic
 * 
 * @author deepeshb
 *
 */
public interface TollCalculationService {

	/**
	 * Calculate toll
	 * 
	 * @param awayTransaction
	 * @return Double
	 */
	public Double calculateToll(AwayTransaction awayTransaction);

}
