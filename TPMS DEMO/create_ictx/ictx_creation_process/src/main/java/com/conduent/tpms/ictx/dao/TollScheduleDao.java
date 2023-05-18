package com.conduent.tpms.ictx.dao;

import com.conduent.tpms.ictx.dto.TollFareDto;
import com.conduent.tpms.ictx.model.TollSchedule;

/**
 * Toll Schedule Dao
 * 
 * @author deepeshb
 *
 */
public interface TollScheduleDao {

	/**
	 * Get Toll Amount
	 * 
	 * @param tollFareDto
	 * @return TollSchedule
	 */
	public TollSchedule getTollAmount(TollFareDto tollFareDto);
}
