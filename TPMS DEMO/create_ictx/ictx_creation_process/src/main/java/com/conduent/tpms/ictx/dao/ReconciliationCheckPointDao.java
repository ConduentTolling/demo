package com.conduent.tpms.ictx.dao;

import com.conduent.tpms.ictx.model.ReconciliationCheckPoint;

/**
 * Reconciliation CheckPoint Dao
 * 
 * @author deepeshb
 *
 */
public interface ReconciliationCheckPointDao {

	/**
	 * Get file info
	 * 
	 * @param fromAgency
	 * @param toAgency
	 * @return ReconciliationCheckPoint
	 */
	public ReconciliationCheckPoint getFileInfo(String fromAgency, String toAgency, String fileType);

	/**
	 * Get file info by filename
	 * 
	 * @param fileName
	 * @return ReconciliationCheckPoint
	 */
	public ReconciliationCheckPoint getFileInfoByFileName(String fileName);

	/**
	 * Update Record
	 * 
	 * @param fileName
	 * @param recordCount
	 * @param fileStatusInd
	 */
	public void updateRecord(String fileName, Long recordCount, String fileStatusInd);

	/**
	 * Insert start entry
	 * 
	 * @param fileName
	 * @param recordCount
	 * @param fileStatusInd
	 */
	public void insertStartEntry(String fileName, Long recordCount, String fileStatusInd);

	/**
	 * Update status
	 * 
	 * @param fileName
	 * @param fileStatusInd
	 */
	public void updateStatus(String fileName, String fileStatusInd);

}
