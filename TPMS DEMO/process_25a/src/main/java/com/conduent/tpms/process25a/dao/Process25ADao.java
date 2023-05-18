package com.conduent.tpms.process25a.dao;

import java.util.List;

import com.conduent.tpms.process25a.model.Pending25ATransactions;

public interface Process25ADao 
{
	public List<Pending25ATransactions> getPending25ATolls( Long entryPlaza, Long exitPlaza);
	public List<Pending25ATransactions> getPending25ATollsExit26( Long entryPlaza, Long exitPlaza);
	public List<Pending25ATransactions> getAllRelatedTransactions(Long entryPlazaId, Long exitPlazaId, String tableName, String deviceNo, String plateNo);
	public List<Pending25ATransactions> getAllRelatedTransactionsTAccountToll(Long plazaId, Long plazaId2,
			String string, Long agencyId, String deviceNo, String plateNo);
	public int getCompleteSeq();
	public int updatePendingQueue(String laneTxId, int seqNo);
	public List<Pending25ATransactions> getMatchedRecords(String laneTxId, int seqNo);
	public int updateMatchedRecords(String laneTxId, int seqNo);
	public void insertComplleteTransaction(Pending25ATransactions transaction);
	public void deletePendingTransaction(Pending25ATransactions transaction);

}
