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
	public List<Agency> getAgency();
	public List<String> getHomeAgencyDevicePrefixList();
	public Agency getHomeAgency();
	public List<Agency> getAwayAgencyList();
	public List<String> getHomeAgencyList();
}
