package com.conduent.tpms.iag.dao;

import java.util.List;

import com.conduent.tpms.iag.model.Agency;


/**
 * 
 * Agency Dao interface
 * 
 * @author urvashic
 *
 */
public interface AgencyDao {

	/**
	 * To get Agency Info
	 * 
	 * @return List<Agency>
	 */
	public List<Agency> getAgency();

	/**
	 * To get Home Device prefix list
	 * 
	 * @return
	 */
	List<String> getHomeAgencyList();


}
