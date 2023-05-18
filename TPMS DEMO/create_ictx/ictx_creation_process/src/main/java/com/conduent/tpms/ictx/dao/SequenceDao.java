package com.conduent.tpms.ictx.dao;

/**
 * File Sequence Dao
 * 
 * @author deepeshb
 *
 */
public interface SequenceDao {

	/**
	 * Get file sequence based on device prefix
	 * 
	 * @param devicePrefix
	 * @return Long
	 */
	Long getFileSequence(String devicePrefix);
	
	/**
	 * Get ATP file sequence based on device prefix
	 * 
	 * @param devicePrefix
	 * @return Long
	 */
	Long getAtpFileSequence();
}
