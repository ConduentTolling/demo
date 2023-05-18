package com.conduent.tpms.NY12.service.impl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.tomcat.util.bcel.classfile.Constant;
//import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.conduent.tpms.NY12.config.LoadJpaQueries;
import com.conduent.tpms.NY12.constants.NY12_Constants;
import com.conduent.tpms.NY12.dao.NY12_ProcessDao;
import com.conduent.tpms.NY12.dao.impl.NY12_ProcessDaoImpl;
import com.conduent.tpms.NY12.model.Plaza;
import com.conduent.tpms.NY12.service.NY12_ProcessService;
import com.conduent.tpms.NY12.vo.DeviceTransactionVO;
import com.conduent.tpms.NY12.vo.LaneTxIdVO;
import com.conduent.tpms.NY12.vo.LastSectionTransactionVO;
import com.conduent.tpms.NY12.vo.MatchedTransactionsVO;
import com.conduent.tpms.NY12.vo.MessagesVO;
import com.conduent.tpms.NY12.vo.Ny12ProcessFinalResponseVO;
import com.conduent.tpms.NY12.vo.Ny12ProcessResponseVO;
import com.conduent.tpms.NY12.vo.SameSegmentTransactionVO;
import com.conduent.tpms.NY12.vo.TransactionVO;
import com.conduent.tpms.NY12.vo.VehicleClassNotEqualTo22TransactionVO;
import com.oracle.bmc.http.signing.internal.Constants;

/**
 * DAT Process Service Implementation
 * 
 * @author saurabh33
 *
 */

@Service
public class NY12_ProcessServiceImpl implements NY12_ProcessService {
	@Autowired
	private NY12_ProcessDao ny12_ProcessDao;    // = new NY12_ProcessDaoImpl();

	private static final Logger logger = LoggerFactory.getLogger(NY12_ProcessServiceImpl.class);

	private static final String String = null;
	
	private int totalNumberOfErroredRecords = 0;
	private int totalNumberOfRecordsProcessed = 0;
	private int totalNumberOfRecordsToBeProcessed = 0;
	
	//private int waitDays =0;
	
	
	private Ny12ProcessFinalResponseVO ny12ProcessFinalResponseVO = new Ny12ProcessFinalResponseVO();

	
	boolean success = false;
	
	//messageVO has string message and only those message are set in messagesVO variable , not used further after setting
	//MessagesVO messagesVO = new MessagesVO();
	//all the messagesVO list objects are added to messagesVO_List
	//List<MessagesVO> messagesVO_List = new ArrayList<>();
	
    //listoflist has list of  ny12responsevo objects
	//List<List<Ny12ProcessResponseVO>> ny12ProcessResponseVO_ListOfList;
	
	//Ny12ProcessResponseVO ny12ProcessResponseVO = new Ny12ProcessResponseVO();
	
	//check this
	List<Ny12ProcessResponseVO> ny12ProcessResponseVO_List = new ArrayList<>();
	
	
	public int getTotalNumberOfErroredRecords() {
		return totalNumberOfErroredRecords;
	}

	public void setTotalNumberOfErroredRecords(int totalNumberOfErroredRecords) {
		this.totalNumberOfErroredRecords = totalNumberOfErroredRecords;
	}

	public int getTotalNumberOfRecordsProcessed() {
		return totalNumberOfRecordsProcessed;
	}

	public void setTotalNumberOfRecordsProcessed(int totalNumberOfRecordsProcessed) {
		this.totalNumberOfRecordsProcessed = totalNumberOfRecordsProcessed;
	}

	public int getTotalNumberOfRecordsToBeProcessed() {
		return totalNumberOfRecordsToBeProcessed;
	}

	public void setTotalNumberOfRecordsToBeProcessed(int totalNumberOfRecordsToBeProcessed) {
		this.totalNumberOfRecordsToBeProcessed = totalNumberOfRecordsToBeProcessed;
	}


	public NY12_ProcessDao getNy12_ProcessDao() {
		return ny12_ProcessDao;
	}

	public void setNy12_ProcessDao(NY12_ProcessDao ny12_ProcessDao) {
		this.ny12_ProcessDao = ny12_ProcessDao;
	}

	

	public List<Ny12ProcessResponseVO> getNy12ProcessResponseVO_List() {
		return ny12ProcessResponseVO_List;
	}

	public void setNy12ProcessResponseVO_List(List<Ny12ProcessResponseVO> ny12ProcessResponseVO_List) {
		this.ny12ProcessResponseVO_List = ny12ProcessResponseVO_List;
	}

	
	
	/*
	 * public MessagesVO getMessagesVO() { return messagesVO; }
	 * 
	 * public void setMessagesVO(MessagesVO messagesVO) { this.messagesVO =
	 * messagesVO; }
	 * 
	 * public List<MessagesVO> getMessagesVO_List() { return messagesVO_List; }
	 * 
	 * public void setMessagesVO_List(List<MessagesVO> messagesVO_List) {
	 * this.messagesVO_List = messagesVO_List; }
	 * 
	 * public Ny12ProcessResponseVO getNy12ProcessResponseVO() { return
	 * ny12ProcessResponseVO; }
	 * 
	 * public void setNy12ProcessResponseVO(Ny12ProcessResponseVO
	 * ny12ProcessResponseVO) { this.ny12ProcessResponseVO = ny12ProcessResponseVO;
	 * }
	 */

	
		
		

	public Ny12ProcessFinalResponseVO ny12StartProcess() {

		logger.info("Class Name :" + this.getClass() + " Method Name : ny12StartProcess - STARTED");
		//messagesVO.setMessage("NY12StartProcess - Started ");
		//messagesVO_List.add(messagesVO);
		
	

		try {
			 //check this
			 //ny12_ProcessDao.initializeMessagesVO_Objects();
			 // 3.1 select transactions where vehicle class in not equal to 22
			
			
			  List<VehicleClassNotEqualTo22TransactionVO> transactionVO_List=ny12_ProcessDao.getAllTransactionsWhereVehicleClassNot22(); // step 1 and// //step 2
			  
			  if (transactionVO_List != null && transactionVO_List.size() > 0) 
			  {
			  totalNumberOfRecordsToBeProcessed+=transactionVO_List.size(); 
			  success=callIndependentlyCompleteReferenceNumberUpdateAndCompleteTransactionsToNy12(transactionVO_List); 
			  //ny12ProcessResponseVO_ListOfList.add(ny12_ProcessDao.getNy12ProcessResponseVO_List()); 
			  } else 
			  { 
				  
			  logger.info("There are no transactions pertaining to Other than Vehicle class 22");
			  }
			 
			 
			 
			
			
			//ny12_ProcessDao.initializeMessagesVO_Objects();
			// 3.2 retrieve last section transactions
			
			  List<LastSectionTransactionVO> lastSectionTransactionVO_List =ny12_ProcessDao.retriveAllTransactionsPertainingToLastSection();
			  
			  
			  if (lastSectionTransactionVO_List != null && lastSectionTransactionVO_List.size() > 0) 
			  {
			  totalNumberOfRecordsToBeProcessed+=lastSectionTransactionVO_List.size();
			  logger.info("SECOND STEP : TOTAL NUMBER OF RECORDS TO BE PROCESSED IS lastSectionTransactionVO_List : "+lastSectionTransactionVO_List.size()); 
			  success=callIndependentlyCompleteReferenceNumberUpdateAndCompleteTransactionsToNy12(lastSectionTransactionVO_List);
			  //ny12ProcessResponseVO_ListOfList.add(ny12_ProcessDao.getNy12ProcessResponseVO_List()); 
			  } else 
			  {
			  logger.info("There are no transactions pertaining to Last Section "); 
			  }
			 
			
			
			//ny12_ProcessDao.initializeMessagesVO_Objects();
			// 3.3 other transactions like same segment and non boundary plaza
			
			  List<SameSegmentTransactionVO> transactionVO_SameSegment_List =ny12_ProcessDao.getAllTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas();

				
				  if (transactionVO_SameSegment_List != null && transactionVO_SameSegment_List.size() > 0) 
				  {
					  totalNumberOfRecordsToBeProcessed+=transactionVO_SameSegment_List.size();
				  success=callIndependentlyCompleteReferenceNumberUpdateAndCompleteTransactionsToNy12(transactionVO_SameSegment_List);
				  //ny12ProcessResponseVO_ListOfList.add(ny12_ProcessDao.getNy12ProcessResponseVO_List()); 
				  }else 
				  {
				  logger.info("There are no transactions pertaining to Same Segment and Non Boundary Plazas"); 
				  }
				 
			
			//ny12_ProcessDao.initializeMessagesVO_Objects();
			// 3.4 (step 4) & 3.5 (step 5)
			//getAllDistinctDevicesAndProcessTransactionsForEachDevice();
			
			boolean checkdevice= getAllDistinctDevices(NY12_Constants.LAST_SECTION_ID);
			if(checkdevice) 
			{
				logger.info("Distinctdevice trxn processed");
			}
			else {
				logger.info("distinct device trxn not processsed");
			}
			
			//System.out.println(checkdevice);
			
			//ny12ProcessResponseVO_ListOfList.add(ny12_ProcessDao.getNy12ProcessResponseVO_List());

			//ny12ProcessFinalResponseVO.setResponse(ny12ProcessResponseVO_ListOfList);

			logger.info("NY12 Processing successfully completed");
		} catch (Exception ex) {
			logger.error("Error occured failed in one of the methods starting_NYSTA_NY12_Plan / processingTransactionsForLastSegment / checkForAnyTransaction",ex);
            
		}

		//ny12ProcessResponseVO_List = ny12_ProcessDao.getNy12ProcessResponseVO_List();
		
		//messagesVO.setMessage("NY12StartProcess - ENDED ");
		logger.info("Class Name :" + this.getClass() + " Method Name : ny12StartProcess - ENDED");
		
		//ny12ProcessFinalResponseVO.setResponse(ny12ProcessResponseVO_ListOfList);
		
		ny12ProcessFinalResponseVO.setTotalNumberOfRecordsToBeProcessed(totalNumberOfRecordsToBeProcessed);
		ny12ProcessFinalResponseVO.setTotalNumberOfRecordsProcessed(ny12_ProcessDao.getTotalNumberOfRecordsProcessed());
		ny12ProcessFinalResponseVO.setTotalNumberOfErroredRecords(ny12_ProcessDao.getTotalNumberOfErroredRecords());
		return ny12ProcessFinalResponseVO;
	}

	
	
	public boolean callIndependentlyCompleteReferenceNumberUpdateAndCompleteTransactionsToNy12(List<?> commonObjectList) {
		logger.info("Inside callIndependentlyCompleteReferenceNumberUpdateAndCompleteTransactionsToNy12 Total Number of Objects "+commonObjectList.size());
		boolean success = false;
		String message = "";
		//List<Object[]> objectArrayList = new ArrayList<>();
		//Object[] objectArray = new Object[commonObjectList.size()];
		if (commonObjectList == null) return false;
		Object object = commonObjectList.get(0);

		LaneTxIdVO laneTxIdVO = new LaneTxIdVO();
		//List<LaneTxIdVO> distinctLaneTxIds = new ArrayList<LaneTxIdVO>();
		List<LaneTxIdVO> laneTxIdVOList = new ArrayList<>();
		
		if (object instanceof VehicleClassNotEqualTo22TransactionVO) {
			message =  "updating and loading complete transactions other than class 22";
			for (Object vo : commonObjectList) {
				laneTxIdVO = new LaneTxIdVO();
				laneTxIdVO.setDistinctLaneTransactionId(((VehicleClassNotEqualTo22TransactionVO) vo).getLaneTxId());
				laneTxIdVOList.add(laneTxIdVO);
				
			}
	            //ny12_ProcessDao.completeReferenceNumberAndUpdate(laneTxIdVOList);
	            //ny12_ProcessDao.completeTransactionsToNy12(laneTxIdVOList);
			success = true;		
		} else if (object instanceof LastSectionTransactionVO)
		{
			message =  "updating and loading complete of Last Section transactions ";
			for (Object vo : commonObjectList) {
				laneTxIdVO = new LaneTxIdVO();
				laneTxIdVO.setDistinctLaneTransactionId(((LastSectionTransactionVO) vo).getLaneTxId());
				laneTxIdVOList.add(laneTxIdVO);
			}
			    //ny12_ProcessDao.completeReferenceNumberAndUpdate(laneTxIdVOList);
			    //ny12_ProcessDao.completeTransactionsToNy12(laneTxIdVOList);
			success = true;		
		} else if (object instanceof SameSegmentTransactionVO) {
			message =  "updating and loading complete Same segment transactions ";
			for (Object vo : commonObjectList) {
				laneTxIdVO = new LaneTxIdVO();
				laneTxIdVO.setDistinctLaneTransactionId(((SameSegmentTransactionVO) vo).getLaneTxId());
				laneTxIdVOList.add(laneTxIdVO);
			}
			   //ny12_ProcessDao.completeReferenceNumberAndUpdate(laneTxIdVOList);
			   //ny12_ProcessDao.completeTransactionsToNy12(laneTxIdVOList);
			success = true;		
		}else if(  !(SameSegmentTransactionVO.class.isInstance(object) || VehicleClassNotEqualTo22TransactionVO.class.isInstance(object) || LastSectionTransactionVO.class.isInstance(object) ))
		{   message= "updating and loading for single transaction";
			for(Object l1: commonObjectList) {
				LaneTxIdVO l2= (LaneTxIdVO) l1;
				laneTxIdVOList.add(l2);
			}
			//ny12_ProcessDao.completeReferenceNumberAndUpdate(laneTxIdVOList);
			//ny12_ProcessDao.completeTransactionsToNy12(laneTxIdVOList);
			success=true;
			
		}

		
		  if (success) {
			  
		  // calling step 1 //logger.info("Step 1 Started..."); 
			  if (!ny12_ProcessDao.completeReferenceNumberAndUpdate(laneTxIdVOList)) 
			  { 
				  logger.info("STEP 1 : completeReferenceNumberAndUpdate : ERROR occured while "+message); 
			      success=false; 
		      }
		  
		  // calling step 2 //logger.info("Step 2 started");  
		     if (!ny12_ProcessDao.completeTransactionsToNy12(laneTxIdVOList)) 
			 { logger.info("Step 2 : completeTransactionsToNy12 : ERROR occured while "+message); 
				  success=false; 
			 } 
				  
			  
	       //logger.info("Step 2 Ended..."); 
		}
		 
		
		logger.info(message+" Operation successfully Completed");
		return success;
	}

	

	// step iii - 5 - i
	public int getProcessParameterForNY12_WaitDays() {
		logger.info("Class Name :" + this.getClass() + " Method Name : getProcessParameterForNY12_WaitDays  - STARTED");
		int waitDays = ny12_ProcessDao.getProcessParameterForNY12_WaitDays(NY12_Constants.PARAM_NAME);
		logger.info("Class Name :" + this.getClass() + " Method Name : getProcessParameterForNY12_WaitDays - ENDED");

		return waitDays;
	}
	
	
	// step iii - 4
	public boolean getAllDistinctDevices(String lastSectionId) {
		logger.info("Class Name :" + this.getClass() + " Method Name : getAllDistinctDevices - STARTED");
		List<String> deviceList = new ArrayList<>();
		deviceList = ny12_ProcessDao.getAllDistinctDevices(lastSectionId);
		String entry_plaza_id=null;
		String exit_plaza_id=null;
		String queryForNY12_Condition =" ";
		
		int waitDays = getProcessParameterForNY12_WaitDays();
	    
	    LocalDateTime date1= LocalDateTime.now();
	    
	    MapSqlParameterSource paramSource = new MapSqlParameterSource();
	    
	    int updateCount;
		
		// step 5 --
		if (deviceList != null && deviceList.size()>0) {
			for (String device : deviceList) {
				List<DeviceTransactionVO> deviceTransactionVO_List= new ArrayList<>();
				deviceTransactionVO_List = getAllTransactionsByDevice(device, lastSectionId);
				
				//totalNumberOfRecordsToBeProcessed=deviceTransactionVO_List.size();

				DeviceTransactionVO d1= new DeviceTransactionVO();
				boolean withintime;
				boolean newsection;
				String lanetxid;
				int sequenceno;
				
				if(deviceTransactionVO_List != null && deviceTransactionVO_List.size()>0)
				{
					totalNumberOfRecordsToBeProcessed+=deviceTransactionVO_List.size();
				    
					Iterator<DeviceTransactionVO> devicetrxn = deviceTransactionVO_List.iterator();
					
					sequenceno=ny12_ProcessDao.getNextSequenceNumber();
					
					while(devicetrxn.hasNext()) {
						d1= devicetrxn.next();
						withintime= d1.isWithinTime();
						newsection=d1.isNewSection();
						lanetxid=d1.getLaneTxId();
						Timestamp t1= d1.getTxTimeStamp();
						//LocalDateTime d2 = t1.toLocalDateTime();
						String sectionid= d1.getSectionId();
						LocalDateTime d2 = t1.toLocalDateTime();
						
						List<LaneTxIdVO> lanetxidlist = new ArrayList<>();
						LaneTxIdVO l1= new LaneTxIdVO();
						l1.setDistinctLaneTransactionId(lanetxid);
						lanetxidlist.add(l1);
						
						List<String> lanetxidlist1= new ArrayList<>();
						lanetxidlist1.add(lanetxid);
						
						//seqno generate 
						//stitch -  entries have smae seq no
						
						
						
						if(withintime && newsection) 
						{
							//	boolean flag=false;
							
							if(entry_plaza_id==null) {
								entry_plaza_id= d1.getEntryPlazaId(); //1 
								exit_plaza_id=d1.getPlazaId();//2
								// what is the use of this entry and exit 
								
								
								
							}
							
						
			              
							if(  t1.toLocalDateTime().compareTo(date1.minusDays(waitDays)) <=0 ) {
								boolean success=callIndependentlyCompleteReferenceNumberUpdateAndCompleteTransactionsToNy12(lanetxidlist);
							}
							else {
								boolean  success= callIndependentlyCompleteReferenceNumberUpdateAndCompleteTransactionsToNy12Seq(lanetxidlist1,sequenceno);
								
								
							}
							
								
							//return success;
							
							//boolean f1= ny12_ProcessDao.updateEntryExit(entry_plaza_id,exit_plaza_id);
							
							
						}
						
						//lst txn 
						else { 
							
							if(exit_plaza_id==null) {
								exit_plaza_id= d1.getPlazaId(); // last txn exit will be set 
							}
							
							
							
							if(!withintime) {
								//2-3 // !withintime-> 2,3 or withintime-> 2,3
								 boolean  success= callIndependentlyCompleteReferenceNumberUpdateAndCompleteTransactionsToNy12Seq(lanetxidlist1,sequenceno);
							}
							else 
							{ //4-5
								boolean success=callIndependentlyCompleteReferenceNumberUpdateAndCompleteTransactionsToNy12(lanetxidlist);
							}
							
							 
							 
							if(sectionid==NY12_Constants.LAST_SECTION_ID || (t1.toLocalDateTime().compareTo(date1.minusDays(waitDays))<=0 ) ) {
								boolean success=callIndependentlyCompleteReferenceNumberUpdateAndCompleteTransactionsToNy12(lanetxidlist);
							}
							
							
						
							
						}
						
						
						  if ( ( t1.toLocalDateTime().compareTo(date1.minusDays(waitDays)) ) <= 0 ) { 
		
						  //d2 is earlier than date1-15 , thatis txn is older than max wait days 
						  
						 
						  boolean success =callIndependentlyCompleteReferenceNumberUpdateAndCompleteTransactionsToNy12(
						  lanetxidlist);
						  
						  }
						 
					}
					
					
				}else {
						logger.info("no transaction for selected device");
						return false;
				}
				// STEP 5 to be discussed
				//logger.info("Step iii - 4 has been successfully Completed ");
			}
		}
		else {
			logger.info("devices not found");
			return false;
		}
		logger.info("Class Name :" + this.getClass() + " Method Name : getAllDistinctDevices - ENDED");

		return true;
	}
	
	
			// step iii - 5
			public List<DeviceTransactionVO> getAllTransactionsByDevice(String device, String lastSectionId) {
				logger.info("Class Name :" + this.getClass() + " Method Name : getAllTransactionsByDevice  - STARTED");
				List<DeviceTransactionVO> deviceTransactionList = new ArrayList<>();
				deviceTransactionList = ny12_ProcessDao.getAllTransactionsByDevice(device, lastSectionId);
				//totalNumberOfRecordsToBeProcessed=deviceTransactionList.size();
				logger.info("Class Name :" + this.getClass() + " Method Name : getAllTransactionsByDevice - ENDED");

				return deviceTransactionList;
			}
			
	





	
	public boolean callIndependentlyCompleteReferenceNumberUpdateAndCompleteTransactionsToNy12Seq(List<String> commonObjectList, int sequenceno) {
		logger.info("Inside callIndependentlyCompleteReferenceNumberUpdateAndCompleteTransactionsToNy12Seq Total Number of Objects "+commonObjectList.size());
		boolean success = false;
		
		//List<Object[]> objectArrayList = new ArrayList<>();
		//Object[] objectArray = new Object[commonObjectList.size()];
		if (commonObjectList == null) return false;
		//Object object = commonObjectList.get(0);

		//LaneTxIdVO laneTxIdVO = new LaneTxIdVO();
		//List<LaneTxIdVO> distinctLaneTxIds = new ArrayList<LaneTxIdVO>();
		
		List<String> distinctLaneTxIdlist = new ArrayList<>();
		
		for (String vo : commonObjectList) {
			distinctLaneTxIdlist.add(vo);
			//laneTxIdVO = new LaneTxIdVO();
			//laneTxIdVO.setDistinctLaneTransactionId(((VehicleClassNotEqualTo22TransactionVO) vo).getLaneTxId());
			//distinctLaneTxIds.add(laneTxIdVO);
			
		}	
		
		boolean checks1=ny12_ProcessDao.completeReferenceNumberAndUpdateAccToSeq(distinctLaneTxIdlist, sequenceno);
			
		boolean checks2=ny12_ProcessDao.completeTransactionsToNy12AccToSeq(distinctLaneTxIdlist);
		
		if(checks1 & checks2) {
			success=true;
		}else {
			success=false;
		}
		

		/*
		 * if (success) { // calling step 1 logger.info("Step 1 Started..."); if
		 * (!ny12_ProcessDao.completeReferenceNumberAndUpdate(distinctLaneTxIds)) {
		 * logger.
		 * info("STEP 1 : completeReferenceNumberAndUpdate : ERROR occured while "
		 * +message); success=false; } logger.info("Step 2 started..."); // calling step
		 * 2 if (success) { if
		 * (!ny12_ProcessDao.completeTransactionsToNy12(distinctLaneTxIds)) {
		 * logger.info("Step 2 : completeTransactionsToNy12 : ERROR occured while "
		 * +message); success=false; } } logger.info("Step 2 Ended..."); }
		 * 
		 */
				
		
		logger.info(" Operation successfully Completed - callIndependentlyCompleteReferenceNumberUpdateAndCompleteTransactionsToNy12Seq");
		return success;
	}
	
	
	
	
	
	
	
	
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	

	/****/
	
	public boolean updateCompleteReferenceNumber( List<Object[]> objectList) {
		logger.info("Class Name :" + this.getClass() + " Method Name : updateCompleteReferenceNumber - STARTED");

		boolean success = ny12_ProcessDao.updateCompleteReferenceNumber(objectList);
		logger.info("Class Name :" + this.getClass() + " Method Name : updateCompleteReferenceNumber - ENDED");

		return success;

	}
	
	public boolean startProcessingTransactionsForVehicleClassNot22() {
		logger.info("Class Name :" + this.getClass()
		+ " Method Name : startProcessingTransactionsForVehicleClassNot22 - STARTED");
		boolean success = ny12_ProcessDao.startProcessingTransactionsForVehicleClassNot22();
		logger.info("Class Name :" + this.getClass()
		+ " Method Name : startProcessingTransactionsForVehicleClassNot22  - ENDED");
		return success;
	}
	
		/////////////////////////// didnt get 
		public boolean updateAllTrasactions(List<VehicleClassNotEqualTo22TransactionVO> objectList) {
		logger.info("Class Name :" + this.getClass() + " Method Name : updateAllTrasactions - STARTED");
		boolean success = ny12_ProcessDao.updateAllTrasactions(objectList);
		logger.info("Class Name :" + this.getClass() + " Method Name : updateAllTrasactions - ENDED");
		
		return success;
		}


		public boolean loadCompleteTransaction(List<VehicleClassNotEqualTo22TransactionVO> objectList) {
		logger.info("Class Name :" + this.getClass() + " Method Name : loadCompleteTransaction - STARTED");
		boolean success = ny12_ProcessDao.loadCompleteTransaction(objectList);
		logger.info("Class Name :" + this.getClass() + " Method Name : loadCompleteTransaction - ENDED");
		
		return success;
		}
		 
		public boolean doesPlazaExistOnSameSegment(String entryPlazaId, String exitPlazaId) {
			logger.info("Class Name :" + this.getClass() + " Method Name : doesPlazaExistOnSameSegment  - STARTED");
			boolean success = ny12_ProcessDao.doesPlazaExistOnSameSegment(entryPlazaId, exitPlazaId);
			logger.info("Class Name :" + this.getClass() + " Method Name : doesPlazaExistOnSameSegment - ENDED");

			return success;
		}
		
		
		public boolean doesPlazaBelongsToBoundarySegmentPlaza(String plazaId) {
			logger.info(
					"Class Name :" + this.getClass() + " Method Name : doesPlazaBelongsToBoundarySegmentPlaza - STARTED");
			boolean success = ny12_ProcessDao.doesPlazaBelongsToBoundarySegmentPlaza(plazaId);
			logger.info("Class Name :" + this.getClass() + " Method Name :doesPlazaBelongsToBoundarySegmentPlaza  - ENDED");

			return success;
		}
		
		
		public List<Plaza> getAllBoundaryPlazaOfSegment(String segmentId) {
			logger.info("Class Name :" + this.getClass() + " Method Name :getAllBoundaryPlazaOfSegment  - STARTED");
			List<Plaza> plazaList = new ArrayList<>();
			plazaList = ny12_ProcessDao.getAllBoundaryPlazaOfSegment(segmentId);
			logger.info("Class Name :" + this.getClass() + " Method Name : getAllBoundaryPlazaOfSegment - ENDED");

			return plazaList;
		}
		
		
		public List<LastSectionTransactionVO> getAllTransactionsOfLastSegment(String entryPlazaId, String exitPlazaId) {
			logger.info("Class Name :" + this.getClass() + " Method Name :getAllTransactionsOfLastSegment  - STARTED");
			List<LastSectionTransactionVO> transactionVO_List = new ArrayList<>();
			transactionVO_List = ny12_ProcessDao.getAllTransactionsOfLastSegment(entryPlazaId, exitPlazaId);
			logger.info("Class Name :" + this.getClass() + " Method Name : getAllTransactionsOfLastSegment - ENDED");

			return transactionVO_List;
		}

		
		public List<LastSectionTransactionVO> getAllTransactionsOfLastSegment(String lastSectionId,String laneTxId,boolean flag) {
			logger.info("Class Name :" + this.getClass() + " Method Name :getAllTransactionsOfLastSegment  - STARTED");
			List<LastSectionTransactionVO> transactionVO_List = new ArrayList<>();
			transactionVO_List = ny12_ProcessDao.getAllTransactionsOfLastSegment(lastSectionId);
			logger.info("Class Name :" + this.getClass() + " Method Name : getAllTransactionsOfLastSegment - ENDED");

			return transactionVO_List;
		}

		
		public boolean updateAllTransactionsOfLastSegment(List<LastSectionTransactionVO> transactionVO) {
			logger.info("Class Name :" + this.getClass() + " Method Name : updateAllTransactionsOfLastSegment - STARTED");
			boolean success = ny12_ProcessDao.updateAllTransactionsOfLastSegment(transactionVO);
			logger.info("Class Name :" + this.getClass() + " Method Name : updateAllTransactionsOfLastSegment - ENDED");

			return success;
		}
		
		public boolean loadCompleteTransactionsOfLastSegment(List<LastSectionTransactionVO> transactionVO) {
			logger.info(
					"Class Name :" + this.getClass() + " Method Name : loadCompleteTransactionsOfLastSegment  - STARTED");
			boolean success = ny12_ProcessDao.loadCompleteTransactionsOfLastSegment(transactionVO);
			logger.info("Class Name :" + this.getClass() + " Method Name : loadCompleteTransactionsOfLastSegment - ENDED");

			return success;
		}
		
		
		
		
		
		public boolean updateAllTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas(
				List<SameSegmentTransactionVO> objectList) {
			logger.info("Class Name :" + this.getClass()
			+ " Method Name : updateAllTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas - STARTED");
			boolean success = ny12_ProcessDao.updateAllTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas(objectList);
			logger.info("Class Name :" + this.getClass()
			+ " Method Name : updateAllTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas  - ENDED");

			return success;
		}
		
		public boolean loadCompleteTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas(
				List<SameSegmentTransactionVO> objectList) {
			logger.info("Class Name :" + this.getClass()
			+ " Method Name : loadCompleteTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas  - STARTED");
			boolean success = ny12_ProcessDao.loadCompleteTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas(objectList);
			logger.info("Class Name :" + this.getClass()
			+ " Method Name : loadCompleteTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas  - ENDED");

			return success;
		}
		
		/*
		public boolean callIndependentlyCompleteReferenceNumberUpdateAndCompleteTransactionsToNy12(List<VehicleClassNotEqualTo22TransactionVO> objectList)
		{
			boolean success = false;
			String message = "";
			List<Object[]> objectArrayList = new ArrayList<>();
			Object[] objectArray = new Object[1];
			if (objectList instanceof VehicleClassNotEqualTo22TransactionVO)
			{
				message =  "updating and loading complete transactions other than class 22";
				for (Object vo : objectList) {
					objectArray[0] = ( (VehicleClassNotEqualTo22TransactionVO) vo).getLaneTxId();
					objectArrayList.add(objectArray);
				}
				success = true;		
			}		// to call step 1 and 2 
			if (!updateCompleteReferenceNumber(objectArrayList)) 
			{
				logger.info("ERROR occured while "+message);
				return success;
			}
			logger.info(message+" Operation successfully Completed");
			return success;
		}	
		public boolean callIndependentlyCompleteReferenceNumberUpdateAndCompleteTransactionsToNy12(List<LastSectionTransactionVO> objectList,int indicator)
		{
			boolean success = false;
			String message = "";
			List<Object[]> objectArrayList = new ArrayList<>();
			Object[] objectArray = new Object[1];
			if (objectList instanceof LastSectionTransactionVO)
			{
				message =  "updating and loading complete of Last Section transactions ";
				for (Object vo : objectList) {

					objectArray[0] = ( (LastSectionTransactionVO) vo).getLaneTxId();
					objectArrayList.add(objectArray);
				}
				success = true;		
			}		// to call step 1 and 2 again
			if (!updateCompleteReferenceNumber(objectArrayList)) 
			{
				logger.info("ERROR occured while "+message);
				return success;
			}
			logger.info(message+" Operation successfully Completed");
			return success;
		}	
		public boolean callIndependentlyCompleteReferenceNumberUpdateAndCompleteTransactionsToNy12(List<SameSegmentTransactionVO> objectList,String dummyString)
		{
			boolean success = false;
			String message = "";
			List<Object[]> objectArrayList = new ArrayList<>();
			Object[] objectArray = new Object[1];
			message =  "updating and loading complete Same segment transactions ";
			for (Object vo : objectList) {
				objectArray[0] = ( (SameSegmentTransactionVO) vo).getLaneTxId();
				objectArrayList.add(objectArray);
			}
			success = true;		
			// to call step 1 and 2 again
			if (!updateCompleteReferenceNumber(objectArrayList)) 
			{
				logger.info("ERROR occured while "+message);
				return success;
			}
			logger.info(message+" Operation successfully Completed");
			return success;
		}
		 */	

		
		public boolean findWhetherAnyRowsToBeProcessed() {
			logger.info("Class Name :" + this.getClass() + " Method Name : findWhetherAnyRowsToBeProcessed  - STARTED");

			totalNumberOfRecordsToBeProcessed = ny12_ProcessDao.getCountQuery();

			logger.info("Class Name :" + this.getClass() + " Method Name : findWhetherAnyRowsToBeProcessed - ENDED");

			return totalNumberOfRecordsToBeProcessed != 0 ? true : false;

		}
		
		
		// step iii - 1
		public boolean starting_NYSTA_NY12_Plan() {
			logger.info(
					"Class Name: " + this.getClass() + " Method Name : starting_NYSTA_NY12_Plan -  Step iii - 1 -STARTED");
			boolean success = false;
			List<VehicleClassNotEqualTo22TransactionVO> transactionVO_List = null;
			if (startProcessingTransactionsForVehicleClassNot22()) {
				transactionVO_List = getAllTransactionsWhereVehicleClassNot22();
				if (transactionVO_List != null) { // && updateAllTrasactions(transactionVO_List) &&
					// loadCompleteTransaction(transactionVO_List)) {
					logger.info("Step iii - 1 has been successfully Completed ");
					success = true;
				} else {
					logger.error(
							"either getAllTransactionsWhereVehicleClassNot22 or updateAllTransactions or loadCompleteTransaction failed...");
				}
			} else {
				logger.error("startProcessingTransactionsForVehicleClassNot22 failed...");
			}
			// executeStep_i_and_ii(transactionVO_List);
			logger.info(
					"Class Name: " + this.getClass() + " Method Name : starting_NYSTA_NY12_Plan -  Step iii - 1 -ENDED");
			return success;
		}

		// step iii - 2
		public boolean processingTransactionsForLastSegment() {
			logger.info("Class Name: " + this.getClass()
			+ " Method Name : processingTransactionsForLastSegment -  Step iii - 2 - STARTED");
			boolean success = false;
			List<LastSectionTransactionVO> transactionVO_List = retriveAllTransactionsPertainingToLastSection();
			// String lastSectionId = null;
			// transactionVO_List= getAllTransactionsOfLastSegment(lastSectionId);
			if (transactionVO_List != null) // && updateAllTransactionsOfLastSegment(transactionVO_List) &&
				// loadCompleteTransactionsOfLastSegment(transactionVO_List))
			{
				logger.info("Step iii - 2 has been successfully Completed ");
				success = true;
			} else {
				logger.error(
						"either getAllTransactionsOfLastSegment or loadCompleteTransactionsOfLastSegment or updateAllTransactionsOfLastSegment failed...");
			}

			// if (success) executeStep_i_and_ii(transactionVO_List);
			logger.info("Class Name: " + this.getClass()
			+ " Method Name : processingTransactionsForLastSegment - Step iii - 2 -ENDED");
			return success;
		}

		// step iii - 3
		public boolean checkForAnyTransaction() {
			logger.info("Class Name :" + this.getClass() + " Method Name : checkForAnyTransaction - Step iii - 3 STARTED");
			boolean success = true;

			//List<SameSegmentTransactionVO> transactionVO_SameSegment_List = getAllTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas();
			/*
			 * if (transactionVO_SameSegment_List != null &&
			 * updateAllTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas(
			 * transactionVO_SameSegment_List) &&
			 * loadCompleteTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas(/
			 * transactionVO_SameSegment_List)) { success = true;
			 * logger.info("Step iii - 3 has been successfully Completed "); } else {
			 * 
			 * logger.
			 * error("either getAllTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas or updateAllTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas or loadCompleteTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas failed..."
			 * ); }
			 */
			// if (success) executeStep_i_and_ii(transactionVO_SameSegment_List);
			logger.info("Class Name :" + this.getClass() + " Method Name : checkForAnyTransaction -  Step iii - 3 - ENDED");
			return success;
		}

		
		
		
		// step iii - 4 & 5
		public boolean getAllDistinctDevicesAndProcessTransactionsForEachDevice() {
			logger.info("Class Name :" + this.getClass() + " Method Name : getAllDistinctDevicesAndProcessTransactionsForEachDevice - STARTED");
			// step 4
			boolean success = false;
			String lastSectionId = null;
			List<String> lastSectionIdList = ny12_ProcessDao.retriveAllTransactionsPertainingToLastSectionForDevice();
			Set<String> lastSectionIdListUnique = lastSectionIdList.stream().collect(Collectors.toSet());
			Iterator lastSectionIdIterator = lastSectionIdListUnique.iterator();
			List<String> devicesList = null;
			Iterator<String> deviceIterator = null;
			String device = null;
			List<DeviceTransactionVO> deviceTransactionVO_List = new ArrayList<>();
			while (lastSectionIdIterator.hasNext())
			{
				lastSectionId = (String) lastSectionIdIterator.next();
				if (lastSectionId == null ) continue;
				logger.info("Class Name :" + this.getClass() + " Method Name : getAllDistinctDevicesAndProcessTransactionsForEachDevice - for Last Section id : "+lastSectionId +" STARTED");
				//devicesList = getAllDistinctDevices(lastSectionId);
				deviceIterator = devicesList.iterator();
				if (devicesList != null) {
					while (deviceIterator.hasNext()) {
						device = deviceIterator.next().toString();
						// CALLING STEP 5
						//deviceTransactionVO_List = getAllTransactionsByDevice(device, lastSectionId);
						success = true;
						break;
						// STEP 5 HAS TO BE CALLED
						/*
						 * if (deviceTransactionVO_List != null){ if
						 * (stitchingTransactions(deviceTransactionVO_List)) { // last step done
						 * logger.info("Step iii - 4 & 5 has been successfully Completed "); success =
						 * true; } else { logger.error(" stitching transactions failed..."); } } else {
						 * logger.error(" getAllTransactionsByDevice failed..."); }
						 */
					}
				} else {
					logger.error(" getAllDistinctDevices failed...for Last Section Id : "+lastSectionId);
				}

				logger.info("Class Name :" + this.getClass() + " Method Name : getAllDistinctDevicesAndProcessTransactionsForEachDevice - for Last Section id : "+lastSectionId +" ENDED");
			}
			logger.info("Class Name :" + this.getClass() + " Method Name : getAllDistinctDevicesAndProcessTransactionsForEachDevice - ENDED");

			return success;
		}





		// step iii - 5 - ii

		public boolean stitchingTransactions(List<TransactionVO> deviceTransactionVO_List) {

			logger.info("Class Name :" + this.getClass() + " Method Name : stitchingTransactions - STARTED");
			boolean success = true;
			// boolean success =
			// ny12_ProcessDao.stitchingTransactions(deviceTransactionVO_List);
			// step iii - 5 - i
			int maxWaitDays = getProcessParameterForNY12_WaitDays();
			// step iii - 5 - ii
			// ????boolean success = step_iii_5_ii(deviceTransactionVO_List);
			logger.info("FINAL STEP  has been successfully Completed ");

			logger.info("Class Name :" + this.getClass() + " Method Name : stitchingTransactions  - ENDED");
			return success;
		}

		/*   public boolean executeStep_i_and_ii(List<Object[]> transactionVO_List) {
			boolean success = false;
			if (completeReferenceNumberAndUpdate(new ArrayList<Object[]>())) {

				// * String laneTxId = null; //???? if
				// * (insertCompleteTransactionToCompleteTxn(transactionVO_List,laneTxId)) {
				// * success = true; } else {
				// * logger.error("step 2 failed in executeStep1And2 method"); }
				 //
			} else {
				logger.error("step 1 failed in executeStep1And2 method");

			}
			return success;
		}
		 */
		/*
		public boolean executeStep_i_and_ii(TransactionVO transactionVO) {
			boolean success = false;
			List<TransactionVO> transactionVO_SingleRecord = new ArrayList<>();
			transactionVO_SingleRecord.add(transactionVO);
			//	int sequenceNumber = getNextSequenceNumber();

			if (completeReferenceNumberAndUpdate(new ArrayList<Object[]>())) {


				// * String laneTxId = null; //???? if
				// * (insertCompleteTransactionToCompleteTxn(transactionVO_SingleRecord,laneTxId))
				// * { success = true; } else {
				// * logger.error("step 2 failed in executeStep1And2 method"); }
				//
			} else {
				logger.error("step 1 failed in executeStep1And2 method");

			}
			return success;
		}
		 */
		
		
		public int timeAllowedToReachThisPlazaFromPreviousPlaza(String startingPlazaId, String endingPlazaId) {
			logger.info("Class Name :" + this.getClass()
			+ " Method Name : loadCompleteTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas  - STARTED");

			// int i = Integer.parseInt(startingPlazaId);
			// int j = Integer.parseInt(endingPlazaId);
			// getTimeAllowed ?????
			logger.info("Class Name :" + this.getClass()
			+ " Method Name : loadCompleteTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas  - ENDED");
			return 100;
		}

		public boolean timeLimitWithinTime(String startingPlazaId, String endingPlazaId,
				List<TransactionVO> transactionVO_List) {
			logger.info("Class Name :" + this.getClass() + " Method Name : timeLimitWithinTime  - STARTED");
			// retrieve time from t_highway_section
			// compare the total time starting from first journeyed plaza to last plaza
			Date startingJourneyDateTime = findFirstTransaction(transactionVO_List).getEntryTimeStamp();
			Date endingJourneyDateTime = findLastTransaction(transactionVO_List).getEntryTimeStamp();

			long differenceTimeInSeconds = (startingJourneyDateTime.getTime() - endingJourneyDateTime.getTime()) / 10000;
			long maximumAllowedTimeToReachThisPlazaFromPreviousPlaza = timeAllowedToReachThisPlazaFromPreviousPlaza(
					startingPlazaId, endingPlazaId);
			if (differenceTimeInSeconds <= maximumAllowedTimeToReachThisPlazaFromPreviousPlaza) {
				logger.info("Class Name :" + this.getClass() + " Method Name : timeLimitWithinTime - Returns true - ENDED");
				return true;
			}
			logger.info("Class Name :" + this.getClass() + " Method Name : timeLimitWithinTime  -Returns false -  ENDED");
			return false;
		}

		public boolean setCurrentTransactionAsNewTransaction(TransactionVO transactionVO) {
			logger.info(
					"Class Name :" + this.getClass() + " Method Name : setCurrentTransactionAsNewTransaction  - STARTED");
			// ????
			return true;
		}

		public boolean isPlazaIdsAreInSequence(String entryPlazaId, String exitPlazaId) {
			logger.info("Class Name :" + this.getClass() + " Method Name : isPlazaIdsAreInSequence  - STARTED");
			if (Math.abs(Integer.parseInt(exitPlazaId) - Integer.parseInt(entryPlazaId)) == 1) {
				return true;
			}
			logger.info("Class Name :" + this.getClass() + " Method Name : isPlazaIdsAreInSequence  - ENDED");
			return false;
		}

		public boolean doesTransactionExceededMaxWaitDays(String startingPreviousPlazaStartDateAndTime,
				String endingStartDateAndTime) {
			logger.info("Class Name :" + this.getClass() + " Method Name : doesTransactionExceededMaxWaitDays  - STARTED");
			return true;
		}

		public int resetMaxWaitDays() {
			logger.info("Class Name :" + this.getClass() + " Method Name : doesTransactionExceededMaxWaitDays  - ENDED");
			return 0;
		}

		public boolean transactionDateOlderThanMaxWaitDays(Date currentTransactionDate,
				Date previousPlazaStartingTransactionDate) {
			logger.info("Class Name :" + this.getClass() + " Method Name : transactionDateOlderThanMaxWaitDays  - STARTED");
			long differenceInTime = currentTransactionDate.getTime() - previousPlazaStartingTransactionDate.getTime();
			if (differenceInTime > getProcessParameterForNY12_WaitDays()) {
				return true;
			} else {
				logger.info(
						"Class Name :" + this.getClass() + " Method Name : transactionDateOlderThanMaxWaitDays  - ENDED");
				return false;
			}

		}

		public boolean transactionDateOlderThanMaxWaitDays(List<TransactionVO> transactionVO_List) {
			logger.info("Class Name :" + this.getClass() + " Method Name : transactionDateOlderThanMaxWaitDays  - STARTED");
			boolean success = false;
			Iterator<TransactionVO> transactionVO_iterator = transactionVO_List.iterator();
			TransactionVO transactionVO;
			Date[] dateArray = new Date[2];
			int counter = 0;
			while (transactionVO_iterator.hasNext()) {
				transactionVO = (TransactionVO) transactionVO_iterator.next();
				if (counter == 0) {
					dateArray[1] = transactionVO.getTxTimeStamp();
					counter = 1;
				} else {
					dateArray[counter - 1] = dateArray[counter];
					dateArray[counter] = transactionVO.getTxTimeStamp();
				}
			}
			long differenceInTime = dateArray[1].getTime() - dateArray[0].getTime();
			if (differenceInTime > getProcessParameterForNY12_WaitDays()) {
				success = true;
			} else {
				logger.info(
						"Class Name :" + this.getClass() + " Method Name : transactionDateOlderThanMaxWaitDays  - ENDED");

				success = false;
			}
			return success;
		}

		public boolean findWhetherTransactionsAreInTheNextTransaction(List<TransactionVO> transactionVO_List) {
			logger.info("Class Name :" + this.getClass()
			+ " Method Name : findWhetherTransactionsAreInTheNextTransaction  - STARTED");
			boolean nextTransactionExistsFlag = false;
			Iterator<TransactionVO> transactionVO_iterator = transactionVO_List.iterator();
			TransactionVO transactionVO;
			int counter = 0;
			String sameTransaction = null;
			while (transactionVO_iterator.hasNext()) {
				transactionVO = (TransactionVO) transactionVO_iterator.next();
				if (counter++ == 0) {
					sameTransaction = transactionVO.getMachedTxExternNo();
				} else {
					// ???? getExternalRefNo

					if (sameTransaction.equals(transactionVO.getMachedTxExternNo())) {
						nextTransactionExistsFlag = true;
					}
				}

			}
			logger.info("Class Name :" + this.getClass()
			+ " Method Name : findWhetherTransactionsAreInTheNextTransaction  - ENDED");
			return nextTransactionExistsFlag;
		}

		public TransactionVO findFirstTransaction(List<TransactionVO> transactionVO_List) {
			logger.info("Class Name :" + this.getClass()
			+ " Method Name : findWhetherTransactionsAreInTheNextTransaction  - STARTED");
			TransactionVO transactionVO = transactionVO_List.get(0);
			logger.info("Class Name :" + this.getClass()
			+ " Method Name : findWhetherTransactionsAreInTheNextTransaction  - ENDED");
			return transactionVO;
		}

		public TransactionVO findPreviousTransaction(List<TransactionVO> transactionVO_List) {
			logger.info("Class Name :" + this.getClass() + " Method Name : findPreviousTransaction  - STARTED");
			TransactionVO transactionVO = transactionVO_List.get(transactionVO_List.size() - 2);
			logger.info("Class Name :" + this.getClass() + " Method Name : findPreviousTransaction  - ENDED");
			return transactionVO;
		}

		public TransactionVO findLastTransaction(List<TransactionVO> transactionVO_List) {
			logger.info("Class Name :" + this.getClass() + " Method Name : findLastTransaction  - STARTED");
			TransactionVO transactionVO = transactionVO_List.get(transactionVO_List.size() - 1);
			logger.info("Class Name :" + this.getClass() + " Method Name : findLastTransaction  - ENDED");
			return transactionVO;
		}

		public long computeWaitTimeOfTheTransaction(Date lastPlazaTransactionDateTime) {
			logger.info("Class Name :" + this.getClass() + " Method Name : computeWaitTimeOfTheTransaction - STARTED");
			// return waitTime in Seconds
			long waitTime = (lastPlazaTransactionDateTime.getTime() - System.currentTimeMillis()) / 1000;
			logger.info("Class Name :" + this.getClass() + " Method Name : computeWaitTimeOfTheTransaction - SENDED");
			return waitTime;
		}

		public String[] findEntryPlazaIdAndExitPlazaId(List<TransactionVO> transactionVO_List) {
			logger.info("Class Name :" + this.getClass() + " Method Name : findEntryPlazaIdAndExitPlazaId - STARTED");
			String[] plazaIdArray = new String[4];
			Iterator<TransactionVO> transactionVO_iterator = transactionVO_List.iterator();
			TransactionVO transactionVO;
			int counter = 0;
			while (transactionVO_iterator.hasNext()) {
				transactionVO = (TransactionVO) transactionVO_iterator.next();
				if (counter++ == 0) {
					plazaIdArray[0] = String.valueOf(transactionVO.getEnrtyPlazaId());
					plazaIdArray[1] = String.valueOf(transactionVO.getTxTimeStamp());
				} else {
					plazaIdArray[2] = String.valueOf(transactionVO.getEnrtyPlazaId());
					plazaIdArray[3] = String.valueOf(transactionVO.getTxTimeStamp());

				}
			}
			logger.info("Class Name :" + this.getClass() + " Method Name : findEntryPlazaIdAndExitPlazaId - STARTED");
			return plazaIdArray;

		}
		
		
		
		public boolean step_iii_5_ii(List<TransactionVO> transactionVO_List) {
			logger.info("Class Name :" + this.getClass() + " Method Name : step_iii_5_ii - STARTED");
			boolean success = false;
			int currentWaitTime = 0;
			String entryPlazaId = null;
			String exitPlazaId = null;
			Date tempDate = new Date();
			Date currentTransactionDate = new Date();
			Date previousPlazaStartingTransactionDate = new Date();
			TransactionVO transactionVO = new TransactionVO();
			int maxWaitDays = getProcessParameterForNY12_WaitDays();
			String entryTimeStamp = null;
			String exitTimeStamp = null;
			boolean maxWaitDaysCrossedFlag = transactionDateOlderThanMaxWaitDays(currentTransactionDate,
					previousPlazaStartingTransactionDate);
			String currentPlazaId = null;
			String[] plazaIdArray = findEntryPlazaIdAndExitPlazaId(transactionVO_List);
			entryPlazaId = plazaIdArray[0];
			entryTimeStamp = plazaIdArray[1];
			exitPlazaId = plazaIdArray[2];
			exitTimeStamp = plazaIdArray[3];

			Iterator<TransactionVO> transactionVO_iterator = transactionVO_List.iterator();
			while (transactionVO_iterator.hasNext()) {
				transactionVO = (TransactionVO) transactionVO_iterator.next();
				// current transactionVO.getEntryTimeStamp();
				// transactionVO.getE
				if (timeLimitWithinTime(entryPlazaId, exitPlazaId, transactionVO_List)
						&& !doesPlazaExistOnSameSegment(entryPlazaId, exitPlazaId)) {
					if (entryPlazaId == null) // no entry
					{
						setCurrentTransactionAsNewTransaction(transactionVO);
					} else {
						// plaza_ids are not in sequence and exceeded max_wait_days then make a
						// complete transaction update & load the complete txns (step 1 & 2)
						if (!isPlazaIdsAreInSequence(entryPlazaId, exitPlazaId)
								&& doesTransactionExceededMaxWaitDays(entryTimeStamp, exitTimeStamp)) {
							//if (executeStep_i_and_ii(transactionVO)) {
							success = true;
							//} else {
							//	success = false;
							//}
						}
					}
					// stitch the transaction
				} else { // means last transactions to stitch
					if (entryPlazaId == null) // entry_plaza_id is null
						setCurrentTransactionAsNewTransaction(transactionVO); // set current txns is the first txns
					// ????
					if (findWhetherTransactionsAreInTheNextTransaction(transactionVO_List))// the txns are in the next txns
					{
						if (maxWaitDaysCrossedFlag) {// txns_date older than the max_wait_days
							//executeStep_i_and_ii(transactionVO); // update & load the complete txns (step 1 & 2)
							currentWaitTime = 0;// reset the variable for next txns
						}
					}
					// stitch the txns
					if (!doesPlazaBelongsToBoundarySegmentPlaza(currentPlazaId) || maxWaitDaysCrossedFlag)// (not last plaza
						// in segment or
						// past max wait
						// days)
					{
						//if (executeStep_i_and_ii(transactionVO)) // update & load the complete txns (step 1 & 2)
						//	{
						success = true;
						//	} else {
						//		success = false;
						//	}
					}
				}
				if (maxWaitDaysCrossedFlag) // txns older than the max wait days)
				{
					//	if (executeStep_i_and_ii(transactionVO)) // complete the txns (step 1 & 2)
					//		{
					success = true;
					//	} else {
					//		success = false;
					//	}
				}
			}
			logger.info("Class Name :" + this.getClass() + " Method Name : step_iii_5_ii - ENDED");
			return success;
		}
		
		
		public boolean processing_each_trx(List<TransactionVO> transaction) {
			
			boolean flag=false;
			if(flag) {
				
			}else {
				
			}
			
			
			
			return success;
			
		}

	/****/
	
///////////////////////////////////////
		
		/***/
		public int getNextSequenceNumber() {
			logger.info("Class Name :" + this.getClass() + " Method Name : getNextSequenceNumber - STARTED");
			int sequenceNumber = 0;
			try {
				sequenceNumber = ny12_ProcessDao.getNextSequenceNumber();
			} catch (Exception ex) {
				ex.printStackTrace();
				sequenceNumber = 0;
			}
			logger.info("Class Name : " + this.getClass() + " Method Name : getNextSequenceNumber - ENDED");
			return sequenceNumber;
		}
		
		
		
		
		// step 1
		public boolean completeReferenceNumberAndUpdate( List<LaneTxIdVO> objectList) {
			logger.info("Class Name :" + this.getClass() + " Method Name : completeReferenceNumberAndUpdate - STARTED");
			boolean success = ny12_ProcessDao.completeReferenceNumberAndUpdate(objectList);
			if (success) {
				logger.info("Step 1  has been successfully Completed ");
			} else {
				logger.error("Step 1 : completeReferenceNumberAndUpdate failed...");
				return false;
			}
			logger.info("Class Name :" + this.getClass() + " Method Name : completeReferenceNumberAndUpdate - ENDED");
			return true;
		}
		
		public List<MatchedTransactionsVO> getAllMatchedTransactions(String laneTxId) {
			logger.info("Class Name :" + this.getClass() + " Method Name : getAllMatchedTransactions - STARTED");
			List<MatchedTransactionsVO> matchedTransactionsVO = ny12_ProcessDao.getAllMatchedTransactions(laneTxId);
			logger.info("Class Name :" + this.getClass() + " Method Name : getAllMatchedTransactions - ENDED");
			return matchedTransactionsVO;

		}
		
		public boolean updateMatchedTxExternalReferenceNumber(List<MatchedTransactionsVO> matchedTransactionsVO,
				String laneTxId, int sequenceNumber) {
			logger.info(
					"Class Name :" + this.getClass() + " Method Name : updateMatchedTxExternalReferenceNumber - STARTED");
			boolean success = ny12_ProcessDao.updateMatchedTxExternalReferenceNumber(matchedTransactionsVO, laneTxId
					);
			logger.info("Class Name :" + this.getClass() + " Method Name : updateMatchedTxExternalReferenceNumber - ENDED");
			return success;
		}
		
		public boolean insertCompleteTransactions(String laneTxId) {
			logger.info("Class Name :" + this.getClass() + " Method Name : insertCompleteTransactions - STARTED");
			boolean success = ny12_ProcessDao.insertCompleteTransactions(laneTxId);
			logger.info("Class Name :" + this.getClass() + " Method Name : insertCompleteTransactions - ENDED");
			return success;
		}
		
		public boolean updateBalancesAndPlazaInformation(String laneTxId) {
			logger.info("Class Name :" + this.getClass() + " Method Name : updateBalancesAndPlazaInformation - STARTED");

			boolean success = ny12_ProcessDao.updateBalancesAndPlazaInformation(laneTxId);
			logger.info("Class Name :" + this.getClass() + " Method Name : updateBalancesAndPlazaInformation - ENDED");
			return success;
		}
		
		public boolean deleteTransactionsFromPendingQueue(String laneTxId) {
			logger.info("Class Name :" + this.getClass() + " Method Name :  deleteTransactionsFromPendingQueue- STARTED");
			boolean success = ny12_ProcessDao.deleteTransactionsFromPendingQueue(laneTxId);
			logger.info("Class Name :" + this.getClass() + " Method Name : deleteTransactionsFromPendingQueue - ENDED");
			return success;
		}
		
		// step 2
		public boolean completeTransactionsToNy12(List<LaneTxIdVO> objectList) {
			logger.info("Class Name :" + this.getClass() + " Method Name : completeTransactionsToNy12 - STARTED");
			boolean success = ny12_ProcessDao.completeTransactionsToNy12(objectList);
			if (success) {
				logger.info("Step 2 has been successfully Completed ");
			} else {
				logger.error("Step 2 : completeTransactionsToNy12 failed...");
				return false;
			}
			logger.info("Class Name :" + this.getClass() + " Method Name : completeTransactionsToNy12 - ENDED");

			return true;
		}

		
		public List<VehicleClassNotEqualTo22TransactionVO> getAllTransactionsWhereVehicleClassNot22() {
			logger.info(
					"Class Name :" + this.getClass() + " Method Name : getAllTransactionsWhereVehicleClassNot22 - STARTED");

			List<VehicleClassNotEqualTo22TransactionVO> objectList = new ArrayList<>();
			objectList = ny12_ProcessDao.getAllTransactionsWhereVehicleClassNot22();
			logger.info(
					"Class Name :" + this.getClass() + " Method Name : getAllTransactionsWhereVehicleClassNot22 - ENDED");

			return objectList;
		}

		public List<LastSectionTransactionVO> retriveAllTransactionsPertainingToLastSection() {
			logger.info("Class Name :" + this.getClass() + " Method Name : getAllTransactionsOfLastSegment - STARTED");
			List<LastSectionTransactionVO> transactionVO_List = new ArrayList<>();
			transactionVO_List = ny12_ProcessDao.retriveAllTransactionsPertainingToLastSection();
			logger.info("Class Name :" + this.getClass() + " Method Name : getAllTransactionsOfLastSegment - ENDED");

			return transactionVO_List;
		}

		
		
		public List<SameSegmentTransactionVO> getAllTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas() {
			logger.info("Class Name :" + this.getClass()
			+ " Method Name :getAllTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas  - STARTED");
			List<SameSegmentTransactionVO> transactionVO_List = new ArrayList<>();
			transactionVO_List = ny12_ProcessDao.getAllTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas();

			logger.info("Class Name :" + this.getClass()
			+ " Method Name : getAllTransactionsOfSameSegmentPlazasAndNonBoundaryPlazas - ENDED");

			return transactionVO_List;
		}
		
		/***/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
