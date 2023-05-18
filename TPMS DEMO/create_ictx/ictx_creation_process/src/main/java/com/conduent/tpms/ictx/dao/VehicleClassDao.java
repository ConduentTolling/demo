package com.conduent.tpms.ictx.dao;

import java.util.List;

import com.conduent.tpms.ictx.model.VehicleClass;

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
