package com.conduent.tpms.ictx.dao;

import java.util.List;

import com.conduent.tpms.ictx.model.TCode;

/**
 * TCode Dao Interface.
 * 
 * @author deepeshb
 *
 */
public interface TCodeDao {

	
	/**
	 * Get ETC TX Status Info
	 */
	List<TCode> getEtcTxStatus();

	/**
	 *Get tx status
	 */
	List<TCode> getTxStatus();

}
