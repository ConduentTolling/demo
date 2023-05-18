package com.conduent.tpms.intx.dao;

import java.util.List;
import java.util.Optional;

import com.conduent.tpms.intx.model.ProcessParameter;

/**
 * Process Parameter Dao
 * 
 * @author deepeshb
 *
 */
public interface ProcessParametersDao {

	/**
	 * Retrieve Cut-Off Day for Agency
	 * 
	 * @return Optional<List<ProcessParameter>>
	 */
	Optional<List<ProcessParameter>> getCutOffDateForAgency();

}
