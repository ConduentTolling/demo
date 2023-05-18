package com.conduent.tpms.ictx.dao;

import java.util.List;

import com.conduent.tpms.ictx.model.Lane;

/**
 * 
 * Lane Dao interface
 * 
 * @author deepeshb
 *
 */
public interface LaneDao {

	/**
	 * Get Lane Info
	 */
	public List<Lane> getLane();

	public List<Lane> getHomeLanePlazaList();

}
