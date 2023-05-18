package com.conduent.tpms.qatp.classification.dao;

import java.util.List;

import com.conduent.tpms.qatp.classification.model.TCode;

/**
 * TCode Dao Interface.
 * 
 * @author deepeshb
 *
 */
public interface TCodeDao {

	/**
	 *Get Account Status Info
	 */
	List<TCode> getAccountStatus();

	/**
	 *Get Fin Status Info
	 */
	List<TCode> getFinStatus();

	/**
	 *Get Device Status Info
	 */
	List<TCode> getDeviceStatus();

	/**
	 *Get Account Type Info
	 */
	List<TCode> getAccountType();

	/**
	 *Get Account Speed Status Info
	 */
	TCode getAccountSpeedStatus(Long etcAccountId, String txDate);

	
	/**
	 *Get Device Speed Status Info
	 */
	TCode getDeviceSpeedStatus(Long etcAccountId, String deviceNo, String txDate);

	

	/**
	 *Get ETC TX Status Info
	 */
	List<TCode> getTxStatus();

}
