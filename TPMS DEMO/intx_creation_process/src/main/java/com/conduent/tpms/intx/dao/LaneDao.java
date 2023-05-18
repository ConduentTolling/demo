package com.conduent.tpms.intx.dao;

import java.util.List;

import com.conduent.tpms.intx.model.Lane;

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
