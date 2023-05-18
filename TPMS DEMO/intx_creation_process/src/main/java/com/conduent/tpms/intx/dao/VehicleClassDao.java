package com.conduent.tpms.intx.dao;

import java.util.List;

import com.conduent.tpms.intx.model.VehicleClass;

/**
 * 
 * Vehicle Class Dao interface
 * 
 * @author deepeshb
 *
 */
public interface VehicleClassDao {

	/**
	 * Get Vehicle Class Info
	 */
	public List<VehicleClass> getVehicleClass();

}
