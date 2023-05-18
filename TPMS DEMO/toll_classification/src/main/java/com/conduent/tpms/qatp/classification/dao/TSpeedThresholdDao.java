package com.conduent.tpms.qatp.classification.dao;

import com.conduent.tpms.qatp.classification.model.SpeedThreshold;

/**
 * Speed Threshold Dao Interface
 * 
 * @author deepeshb
 *
 */
public interface TSpeedThresholdDao {

	/**
	 * Get Speed Limit for Lane
	 */
	SpeedThreshold getSpeedLimitForLane(Integer agencyId, Integer laneId, Integer accountType);

	/**
	 * Get Speed Limit for Agency
	 */
	SpeedThreshold getSpeedLimitForAgency(Integer agencyId, Integer accountType);

}
