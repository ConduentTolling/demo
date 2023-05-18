package com.conduent.tpms.NY12.dao;

import java.util.List;

import java.util.Map;

import com.conduent.tpms.NY12.dao.impl.NY12_ProcessDaoImpl;
//import com.conduent.tpms.NY12.model.Device;
import com.conduent.tpms.NY12.model.Plaza;
import com.conduent.tpms.NY12.vo.DeviceTransactionVO;
import com.conduent.tpms.NY12.vo.DistinctLaneTxPlazaVO;
import com.conduent.tpms.NY12.vo.LaneTxIdVO;
import com.conduent.tpms.NY12.vo.LastSectionTransactionVO;
import com.conduent.tpms.NY12.vo.MatchedTransactionsVO;
import com.conduent.tpms.NY12.vo.MessagesVO;
import com.conduent.tpms.NY12.vo.Ny12ProcessResponseVO;
import com.conduent.tpms.NY12.vo.SameSegmentTransactionVO;
import com.conduent.tpms.NY12.vo.TransactionVO;
import com.conduent.tpms.NY12.vo.VehicleClassNotEqualTo22TransactionVO;

public interface NY12_ProcessDao {
	
	
	public int getNextSequenceNumber();
	
	public boolean completeReferenceNumberAndUpdate(List<LaneTxIdVO> objectList);
	public boolean completeTransactionsToNy12(List<LaneTxIdVO> laneTxIdList);
	
	
	public List<MatchedTransactionsVO> getAllMatchedTransactions(String laneTxId);
	public boolean updateMatchedTxExternalReferenceNumber(List<MatchedTransactionsVO> matchedTransactionsVO,String laneTxId);
	public boolean insertCompleteTransactions(String laneTxId);
	public boolean updateBalancesAndPlazaInformation(String laneTxId);
	public boolean deleteTransactionsFromPendingQueue(String laneTxId);
	

	public List<VehicleClassNotEqualTo22TransactionVO> getAllTransactionsWhereVehicleClassNot22();
	
	
	public List<LastSectionTransactionVO> retriveAllTransactionsPertainingToLastSection();
	public List<LastSectionTransactionVO> getAllTransactionsOfLastSegment(String lastSectionId);
	
	public List<SameSegmentTransactionVO> getAllTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas();
	
	
	public int getProcessParameterForNY12_WaitDays(String paramname);
	
		
	public List<String> getAllDistinctDevices(String lastSectionId);
	public List<DeviceTransactionVO> getAllTransactionsByDevice(String device,String lastSectionId);
	
	
	
	public boolean completeReferenceNumberAndUpdateAccToSeq(List<String> objectList, int sequenceno);
	public boolean completeTransactionsToNy12AccToSeq(List<String> distinctlanetxid);
	
	public boolean updateEntryExit(String entry_id, String exit_id);

	public String getLastPlazaOfSegment(String sectionid);
	
	public int getTotalNumberOfErroredRecords();
	public int getTotalNumberOfRecordsProcessed();
	public int getTotalNumberOfRecordsToBeProcessed();
	

	
	
	
	
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	
	/***/
	public int getCountQuery();
	public boolean updateCompleteReferenceNumber(List<Object[]> objectList);
	
	public boolean startProcessingTransactionsForVehicleClassNot22();

	public boolean updateAllTrasactions(List<VehicleClassNotEqualTo22TransactionVO> objectList);
	public boolean loadCompleteTransaction(List<VehicleClassNotEqualTo22TransactionVO> objectList);
	public boolean doesPlazaExistOnSameSegment(String entryPlazaId,String exitPlazaId);
	public boolean doesPlazaBelongsToBoundarySegmentPlaza(String plazaId);
	public List<LastSectionTransactionVO> getAllTransactionsOfLastSegment(String entryPlazaId,String exitPlazaId);
	public boolean updateAllTransactionsOfLastSegment(List<LastSectionTransactionVO> objectList);
	public boolean loadCompleteTransactionsOfLastSegment(List<LastSectionTransactionVO> objectList);
	public boolean updateAllTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas(List<SameSegmentTransactionVO> objectList);
	public boolean loadCompleteTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas(List<SameSegmentTransactionVO> objectList);
	public List<String> retriveAllTransactionsPertainingToLastSectionForDevice();
	public boolean stitchingTransactions();
	//public List<LastSectionTransactionVO> getAllTransactionsOfLastSegment();
	public List<Plaza> getAllBoundaryPlazaOfSegment(String segmentId);
	
/////////////////////

	
	//public List<MessagesVO> getListOfMessagesVO();
	
	//public String getCurrentLaneTxId();
     public List<Ny12ProcessResponseVO> getNy12ProcessResponseVO_List();
     public void initializeMessagesVO_Objects();

	/***/
}
