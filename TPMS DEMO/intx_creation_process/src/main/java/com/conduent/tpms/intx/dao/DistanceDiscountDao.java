package com.conduent.tpms.intx.dao;

import java.util.List;

import com.conduent.tpms.intx.model.DistanceDiscount;

/**
 * 
 * Distance Discount Dao interface
 * 
 * @author deepeshb
 *
 */
public interface DistanceDiscountDao {

	/**
	 * Distance Discount Info
	 */
	public List<DistanceDiscount> getDistanceDiscount();

}
