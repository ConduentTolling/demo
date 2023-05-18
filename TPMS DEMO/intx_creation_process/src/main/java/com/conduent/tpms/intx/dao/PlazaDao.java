package com.conduent.tpms.intx.dao;

import java.util.List;

import com.conduent.tpms.intx.model.Plaza;

/**
 * Plaza Dao Interface
 * 
 * @author deepeshb
 *
 */
public interface PlazaDao {
	/**
	 * Get Plaza Info
	 * 
	 * @return List<Plaza>
	 */
	public List<Plaza> getPlaza();

}
