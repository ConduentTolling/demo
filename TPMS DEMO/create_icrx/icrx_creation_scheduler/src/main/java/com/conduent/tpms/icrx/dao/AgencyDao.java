package com.conduent.tpms.icrx.dao;

import java.util.List;

import com.conduent.tpms.icrx.model.Agency;

/**
 * 
 * Agency Dao interface
 * 
 * @author deepeshb
 *
 */
public interface AgencyDao {

	/**
	 * Get Agency Info
	 */
	public List<Agency> getAgency();

}
