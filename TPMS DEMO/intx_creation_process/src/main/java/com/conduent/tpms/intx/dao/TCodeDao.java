package com.conduent.tpms.intx.dao;

import java.util.List;

import com.conduent.tpms.intx.model.TCode;

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

}
