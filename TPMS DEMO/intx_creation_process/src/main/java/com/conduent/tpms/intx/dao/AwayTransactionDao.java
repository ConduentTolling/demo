package com.conduent.tpms.intx.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.conduent.tpms.intx.model.AwayTransaction;

public interface AwayTransactionDao {

	/**
	 * List of Away Transaction based on External File Id and Away Agency Id
	 * 
	 * @param externalFileId
	 * @param awayAgencyId
	 * @return List<AwayTransaction>
	 */
	List<AwayTransaction> getAwayTransactionByExternalIdAndDevicePrefix(String externalFileId, String devicePrefix);
	
	/**
	 * List of Correction Away Transaction based on External File Id and Away Agency Id
	 * 
	 * @param externalFileId
	 * @param awayAgencyId
	 * @return List<AwayTransaction>
	 */
	List<AwayTransaction> getCorrAwayTransactionByExternalIdAndDevicePrefix(String externalFileId, String devicePrefix);
	
	/**
	 * List of Correction Away Transaction based on device prefix
	 * 
	 * @param devicePrefix
	 * @return List<AwayTransaction>
	 */
	List<AwayTransaction> getCorrAwayTransactionByDevicePrefix(String devicePrefix);
	
	/**
	 * Inserting List of Away Transaction in t_agency_tx_pending
	 * 
	 * @param AwayTransaction
	 */
	void insertIntxInAgencyPending(AwayTransaction awayTransaction);
	
	/**
	 * Update destination atp file Id
	 * 
	 * @param awayTransactionList
	 * @param dstAtpFileId
	 */
	void updateDstAtpFileId(List<AwayTransaction> awayTransactionList, Long dstAtpFileId);

	/**
	 * Delete Rejected Transaction
	 * 
	 * @param laneTxId
	 * @return Integer
	 */
	Integer deleteRejectedTx(Long laneTxId,String fileType);
	
	/**
	 * Delete Rejected Transaction IAG_Q
	 * 
	 * @param laneTxId
	 * @return Integer
	 */
	Integer deleteRejectedITXNQ(Long laneTxId,String fileType);

	
	/**
	 * Get Away Transaction
	 * 
	 * @param deviceNo
	 * @param plazaId
	 * @param laneId
	 * @param fromDate
	 * @param toDate
	 * @return Optional<List<AwayTransaction>>
	 */
	Optional<List<AwayTransaction>> getAwayTransaction(String deviceNo, Integer plazaId, Integer laneId,
			LocalDateTime fromDate, LocalDateTime toDate);

	/**
	 * 
	 * Get Away Transaction from FPMS
	 * @param deviceNo
	 * @param plazaId
	 * @param laneId
	 * @param fromDate
	 * @param toDate
	 * @return Optional<List<AwayTransaction>>
	 */
	Optional<List<AwayTransaction>> getAwayTransactionFpms(String deviceNo, Integer plazaId, Integer laneId,
			LocalDateTime fromDate, LocalDateTime toDate);

	/**
	 * Update expected revenue amount and revenue date
	 * 
	 * @param awayTransaction
	 */
	void updateExpectedRevenueAmtAndRevenueDate(AwayTransaction awayTransaction);

	/**
	 * List of Away Transaction based on device prefix
	 * 
	 * @param devicePrefix
	 * @return List<AwayTransaction>
	 */
	List<AwayTransaction> getAwayTransactionByDevicePrefix(String devicePrefix);

	void deleteFromIntxLogs(AwayTransaction awayTransaction);
	
	void deleteFromItxnCorrectionLogs(AwayTransaction awayTransaction);

}
