package com.conduent.tpms.intx.dao;

import java.util.List;
import java.util.Optional;

import com.conduent.tpms.intx.model.TollPostLimit;

/**
 * Toll Post limit Dao
 * 
 * @author deepeshb
 *
 */
public interface TollPostLimitDao {

	/**
	 *Get Toll Post limit based on agency id and etc tx status 
	 */
	Optional<List<TollPostLimit>> getTollPostLimitByAgencyIdAndEtcTxStatus(Integer etcTxStatus);

}
