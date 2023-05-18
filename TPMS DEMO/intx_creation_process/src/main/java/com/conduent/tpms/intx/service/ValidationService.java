package com.conduent.tpms.intx.service;

import com.conduent.tpms.intx.model.AwayTransaction;

public interface ValidationService {

	/**
	 * Validate transaction
	 * 
	 * @param tempAwayTx
	 * @return boolean
	 */
	public boolean validateTx(AwayTransaction tempAwayTx);
	
	/**
	 * Validate entry info
	 * 
	 * @param tempAwayTx
	 * @return Boolean
	 */
	public Boolean validateEntryInfo(AwayTransaction tempAwayTx);
	
	/**
	 * Validate exit info
	 * 
	 * @param tempAwayTx
	 * @return Boolean
	 */
	public Boolean validateExitInfo(AwayTransaction tempAwayTx);
	
	public Boolean validateHomePlazaLaneInfo(AwayTransaction tempAwayTx);
	
	public boolean validateHomeLaneId(AwayTransaction tempAwayTx);
	
	public boolean validateHomePlazaId(AwayTransaction tempAwayTx);
	
	/**
	 * Validate tx timestamp
	 * 
	 * @param tempAwayTx
	 * @return boolean
	 */
	public boolean validateTxTimestamp(AwayTransaction tempAwayTx);
	
	/**
	 * Validate entry lane id
	 * 
	 * @param tempAwayTx
	 * @return boolean
	 */
	public boolean validateEntryLaneId(AwayTransaction tempAwayTx);
	
	/**
	 * Validate entry plaza id
	 * 
	 * @param tempAwayTx
	 * @return boolean
	 */
	public boolean validateEntryPlazaId(AwayTransaction tempAwayTx);
	
	/**
	 * Validate entry timestamp
	 * 
	 * @param tempAwayTx
	 * @return
	 */
	public boolean validateEntryTimestamp(AwayTransaction tempAwayTx);
	
	/**
	 * Validate tx date
	 * 
	 * @param tempAwayTx
	 * @return boolean
	 */
	public boolean validateTxDate(AwayTransaction tempAwayTx);
	
	/**
	 * Validate exit lane id
	 * 
	 * @param tempAwayTx
	 * @return boolean
	 */
	public boolean validateExitLaneId(AwayTransaction tempAwayTx);
	
	/**
	 * Validate exit plaza id
	 * 
	 * @param tempAwayTx
	 * @return boolean
	 */
	public boolean validateExitPlazaId(AwayTransaction tempAwayTx);
	
}
