package com.conduent.tpms.ictx.dao;

import java.util.List;

import com.conduent.tpms.ictx.model.DistanceDiscount;

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
