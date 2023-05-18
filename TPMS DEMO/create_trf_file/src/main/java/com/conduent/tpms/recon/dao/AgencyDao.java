package com.conduent.tpms.recon.dao;

import java.util.List;

import com.conduent.tpms.recon.model.Agency;


/**
 * 
 * Agency Dao interface
 * 
 * @author taniyan
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
