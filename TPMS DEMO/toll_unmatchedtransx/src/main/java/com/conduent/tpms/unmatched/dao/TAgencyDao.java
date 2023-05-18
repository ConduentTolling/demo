package com.conduent.tpms.unmatched.dao;

import java.util.List;

import com.conduent.tpms.unmatched.model.Agency;



/**
 * 
 * Agency Dao interface
 * 
 * @author deepeshb
 *
 */
public interface TAgencyDao {
	
	/**
	 *Get Agency Info
	 */
	public List<Agency> getAll();
	
	/**
	 *Get Schedule Pricing Info By Agency Id
	 */
	public Agency getAgencySchedulePricingById(Integer agencyId);

}
