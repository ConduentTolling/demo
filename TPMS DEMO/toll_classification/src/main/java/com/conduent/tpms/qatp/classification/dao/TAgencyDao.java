package com.conduent.tpms.qatp.classification.dao;

import java.util.List;

import com.conduent.tpms.qatp.classification.model.Agency;

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
