package com.conduent.tpms.intx.dao;

import com.conduent.tpms.intx.dto.TollFareDto;
import com.conduent.tpms.intx.model.TollSchedule;

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
