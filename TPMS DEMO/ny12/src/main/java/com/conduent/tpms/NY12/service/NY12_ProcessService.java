package com.conduent.tpms.NY12.service;

import java.util.List;




import com.conduent.tpms.NY12.model.Ny12ProcessRequest;
import com.conduent.tpms.NY12.model.Plaza;
//import com.conduent.tpms.NY12.model.ResponseWrapperVO;
import com.conduent.tpms.NY12.vo.DeviceTransactionVO;
import com.conduent.tpms.NY12.vo.LaneTxIdVO;
import com.conduent.tpms.NY12.vo.LastSectionTransactionVO;
import com.conduent.tpms.NY12.vo.MatchedTransactionsVO;
import com.conduent.tpms.NY12.vo.Ny12ProcessFinalResponseVO;
import com.conduent.tpms.NY12.vo.Ny12ProcessResponseVO;
//import com.conduent.tpms.NY12.vo.PlazaSegmentVO;
import com.conduent.tpms.NY12.vo.SameSegmentTransactionVO;
import com.conduent.tpms.NY12.vo.TransactionVO;
import com.conduent.tpms.NY12.vo.VehicleClassNotEqualTo22TransactionVO;
//import com.conduent.tpms.NY12.model.Device;

/**
 * Dat Process Service Interface to start the process
 * 
 * @author saurabhsingh3
 *
 */
public interface NY12_ProcessService {

	/*** To start the process of the service*/
	Ny12ProcessFinalResponseVO ny12StartProcess();
	

	public boolean completeReferenceNumberAndUpdate(List<LaneTxIdVO> laneTxIdList) ;// correct 
	

	public boolean completeTransactionsToNy12(List<LaneTxIdVO> laneTxIdList);
	
	
	
	public int getProcessParameterForNY12_WaitDays();

	public boolean getAllDistinctDevices(String lastSectionId);
	public List<DeviceTransactionVO> getAllTransactionsByDevice(String device, String lastSectionId);
	
	
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/****/
	public boolean updateCompleteReferenceNumber(List<Object[]> laneTxIdList);// this also correct , further step 2 method also implemented here 
	public boolean startProcessingTransactionsForVehicleClassNot22(); // not code in this
	public boolean updateAllTrasactions(List<VehicleClassNotEqualTo22TransactionVO> objectList);
	public boolean loadCompleteTransaction(List<VehicleClassNotEqualTo22TransactionVO> objectList);
	
	public boolean doesPlazaExistOnSameSegment(String entryPlazaId,String exitPlazaId);

	public boolean doesPlazaBelongsToBoundarySegmentPlaza(String plazaId);
	
	public List<Plaza> getAllBoundaryPlazaOfSegment(String segmentId);
	
	//public List<LastSectionTransactionVO> getAllTransactionsOfLastSegment();
	public List<LastSectionTransactionVO> getAllTransactionsOfLastSegment(String entryPlazaId,String exitPlazaId);
	
	public List<LastSectionTransactionVO> getAllTransactionsOfLastSegment(String lastSectionId,String laneTxId,boolean flag);
	
	public boolean updateAllTransactionsOfLastSegment(List<LastSectionTransactionVO> objectList);

	public boolean loadCompleteTransactionsOfLastSegment(List<LastSectionTransactionVO> objectList);
	
	public boolean updateAllTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas(List<SameSegmentTransactionVO> transactionVO);
	
	public boolean loadCompleteTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas(List<SameSegmentTransactionVO> transactionVO);
	public boolean stitchingTransactions(List<TransactionVO> deviceTransactionVO_List);
	/****/
	
///////////////////////////////////////////////	
	
	/***/
	public int getNextSequenceNumber();
	
	public List<MatchedTransactionsVO> getAllMatchedTransactions(String laneTxId);
	public boolean updateMatchedTxExternalReferenceNumber(List<MatchedTransactionsVO> matchedTransactionsVO,String laneTxId,int sequenceNumber);
	public boolean insertCompleteTransactions(String laneTxId);
	public boolean updateBalancesAndPlazaInformation(String laneTxId);
	public boolean deleteTransactionsFromPendingQueue(String laneTxId);
	
    public List<VehicleClassNotEqualTo22TransactionVO> getAllTransactionsWhereVehicleClassNot22();
	

	public List<SameSegmentTransactionVO> getAllTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas();
	/***/
	
}
