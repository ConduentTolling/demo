package com.conduent.tpms.inrx.dao;

import java.util.List;

import com.conduent.tpms.inrx.model.Agency;

/**
 * 
 * Agency Dao interface
 * 
 * @author petetid
 *
 */
public interface AgencyDao {

	/**
	 * Get Agency Info
	 */
	public List<Agency> getAgency();

}
