package com.conduent.tpms.ictx.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.conduent.tpms.ictx.model.AwayTransaction;

/**
 * Away Transaction Dao
 * 
 * @author deepeshb
 *
 */
public interface AwayTransactionDao {

	/**
	 * List of Away Transaction based on awayAgencyId
	 * 
	 * @param devicePrefix
	 * @return List<AwayTransaction>
	 */
	List<AwayTransaction> getAwayTransactionByAgencyId(Long awayAgencyId);
	
	/**
	 * List of Correction Away Transaction based on device prefix
	 * 
	 * @param devicePrefix
	 * @return List<AwayTransaction>
	 */
	List<AwayTransaction> getCorrAwayTransactionByDevicePrefix(String devicePrefix);
	
	/**
	 * Inserting List of Correction Away Transaction in t_agency_tx_pending
	 * 
	 * @param AwayTransaction
	 */
	void insertITXCInAgencyPending(AwayTransaction awayTransaction);

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
	Integer deleteRejectedITXCQ(Long laneTxId,String fileType);

	
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

	List<AwayTransaction> getCorrAwayTransactionHub();

	List<AwayTransaction> getAwayTransactionHub();

	void updatePostedFareAmount(AwayTransaction awayTransaction, Double etcTollAmount);

	Optional<List<AwayTransaction>> getAwayTransactionNew(Long laneTxId);

	/**
	 * Delete Rejected Transaction
	 * 
	 * @param laneTxId
	 * @return Integer
	 */
	Integer deleteRejectedTxNew(Long laneTxId, String fileType, String txExtRefNo);

}
