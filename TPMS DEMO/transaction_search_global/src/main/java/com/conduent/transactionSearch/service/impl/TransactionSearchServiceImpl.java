package com.conduent.transactionSearch.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
//import org.hibernate.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.conduent.transactionSearch.constants.TransactionSearchConstants;
import com.conduent.transactionSearch.dao.TranDetailDao;
import com.conduent.transactionSearch.exception.TPMSGlobalException;
import com.conduent.transactionSearch.model.PageBreakPOJO;
import com.conduent.transactionSearch.model.SortingAndPaging;
import com.conduent.transactionSearch.model.TranDetailClass;
import com.conduent.transactionSearch.model.TranQueryReturn;
import com.conduent.transactionSearch.model.TranQueryReturnFromView;
import com.conduent.transactionSearch.model.TransactionApiResponse;
import com.conduent.transactionSearch.model.TransactionResponse;
//import com.tollInfo.entity.TranDetail;
import com.conduent.transactionSearch.model.TransactionResponsePOJO;
import com.conduent.transactionSearch.model.TransactionSearchApiResponse;
import com.conduent.transactionSearch.model.TransactionSearchFilter;
import com.conduent.transactionSearch.model.TransactionSearchGlobalRequest;
import com.conduent.transactionSearch.model.TransactionSearchRequest;
//import com.tollInfo.repository.TransactionSearchRepository;
import com.conduent.transactionSearch.service.TransactionSearchService;
import com.conduent.transactionSearch.utility.TransactionSearchUtility;
import com.conduent.transactionSearch.validation.TransactionSearchValidation;

@Service
public class TransactionSearchServiceImpl implements TransactionSearchService {

	@Autowired
	TranDetailDao transDetailDao;

	@Autowired
	TranDetailClass tranDetailClass;

	@Autowired
	TransactionResponsePOJO transactionResponsePOJO;


	@Autowired
	TransactionSearchUtility transactionSearchUtility;

	@Autowired
	TransactionSearchConstants transactionSearchConstants;

	@Autowired
	TransactionSearchFilter transactionSearchFilter;

	@Autowired
	TransactionSearchValidation transactionSearchValidation;

	@Autowired
	PageBreakPOJO pageBreakPOJO;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private String fromDate;
	private String toDate;
	public String RESPONSE_MESSAGE="SUCCESS";
	String className = this.getClass().getName();
	@Override
	public List<TransactionResponsePOJO> buildTransactionSearchQuery(
			TransactionSearchRequest transactionSearchRequest) {
		logger.info("account Number : "+transactionSearchRequest.getAccountNumber());
		logger.info("Class Name :"+this.getClass()+" buildTransactionSearchQuery method started");
		String methodName = "buildTransactionSearchQuery";
		try {
			List<TransactionSearchFilter> filterList = transactionSearchRequest.getTransactionSearchFilter();

			logger.info("Class name :"+this.getClass()+"filterlist : "+filterList);   

			String selectClause ="SELECT ROWNUM,ETC_ACCOUNT_ID,DEVICE_NO,TX_TIMESTAMP,POSTED_DATE,PLATE_NUMBER,PLATE_STATE,LANE_TX_ID,TX_EXTERN_REF_NO,ENTRY_PLAZA_ID,LANE_ID,ENTRY_TX_TIMESTAMP, "+
					"UPDATE_TS,TX_STATUS,POSTED_FARE_AMOUNT,PLAZA_ID,ENTRY_PLAZA_ID,LANE_ID,ENTRY_LANE_ID,PLAZA_AGENCY_ID,ACTUAL_CLASS,DST_FLAG,UNREALIZED_AMOUNT,PLAN_TYPE,'' AS IMAGE_URL FROM t_TRAN_DETAIL ";
			//			"UPDATE_TS,ETC_TX_STATUS,DISCOUNTED_AMOUNT,PLAZA_ID,ENTRY_PLAZA_ID,LANE_ID,ENTRY_LANE_ID,PLAZA_AGENCY_ID,ACTUAL_CLASS,CASE WHEN UNREALIZED_AMOUNT > 0 THEN 'true' ELSE 'false' END AS DISPUTED_FLAG,UNREALIZED_AMOUNT,PLAN_TYPE,'' AS IMAGE_URL FROM t_TRAN_DETAIL ";
			String whereClause = "";
			String orderClause = "";
			SortingAndPaging sortingAndPagingObject = null;
			List<SortingAndPaging> sortingAndPagingList = transactionSearchRequest.getSortingAndPaging();
			Iterator sortingAndPagingElement = sortingAndPagingList.iterator();
			String sortFieldName = null;
			String actualSortFieldNameInDatabase = null;
			int sortingFieldCounter = 0;
			while (sortingAndPagingElement.hasNext()) {
				sortingAndPagingObject = (SortingAndPaging) sortingAndPagingElement.next();
				sortFieldName = sortingAndPagingObject.getSortFieldName();

				if (sortFieldName.isBlank()) {

				} else {
					actualSortFieldNameInDatabase = transactionSearchUtility.findActualSortFieldNameInDatabase(sortFieldName);
					if (!transactionSearchValidation.validateSortFieldNames(sortFieldName)){
						throw new TPMSGlobalException(TransactionSearchConstants.INVALID_SORT_FIELD_NAME_SPECIFIED,className,methodName);
					}
				}

				if (actualSortFieldNameInDatabase != null) {
					if (sortingFieldCounter==0) {
						orderClause = " ORDER BY "+actualSortFieldNameInDatabase;

					} else {
						orderClause += ","+ actualSortFieldNameInDatabase;
					}
					sortingFieldCounter++;
					if (sortingAndPagingObject.getSortType().equalsIgnoreCase("desc")) {
						orderClause += " DESC";
					} else {
						orderClause += " ASC";
					}
				}
			}

			StringBuilder whereClauseForQuery = finalWhereClauseBuild(filterList,transactionSearchRequest);
			int maximumNumberOfRecordsToBeRetrivedFromDatabase = 0;
			int totalNumberOfPages = 0, totalNumberOfRecordsInPage=0;
			StringBuilder finalQueryToBeExecuted = new StringBuilder();
			finalQueryToBeExecuted.append(selectClause);
			finalQueryToBeExecuted.append(" ");
			finalQueryToBeExecuted.append(whereClauseForQuery==null?" ":whereClauseForQuery);
			finalQueryToBeExecuted.append(" ");
			finalQueryToBeExecuted.append(orderClause);

			List<TranQueryReturn> tranQueryReturnList = transDetailDao.getAllRowsFromQuery(finalQueryToBeExecuted.toString(),0,0);   
			List<TransactionResponsePOJO> transactionResponsePOJOList = setResponseValues(tranQueryReturnList,transactionSearchRequest);

			logger.info("Generated Dynamic query for Transaction Search "+finalQueryToBeExecuted.toString());
			logger.info("Class Name :"+this.getClass()+" buildTransactionSearchQuery method ended");
			return transactionResponsePOJOList;	
		} catch (Exception ex) {
			logger.error("class : " + getClass() + " Error in buildTransactionSearchQuery \n" + ex.getMessage());

		}
		return null;
	}

	@Override
	//public List<TransactionResponsePOJO> buildTransactionSearchGlobalQuery(TransactionSearchGlobalRequest transactionSearchGlobalRequest) {
	public List<TranQueryReturn>  buildTransactionSearchGlobalQuery(TransactionSearchGlobalRequest transactionSearchGlobalRequest) {
		logger.info("Class Name :"+this.getClass()+" buildTransactionSearchGlobalQuery method started");
		String methodName = "buildTransactionSearchGlobalQuery";
		try {


			String selectClause ="";
			//isblank
			if (transactionSearchGlobalRequest.getAgencyShortName()==null || StringUtils.isBlank(transactionSearchGlobalRequest.getAgencyShortName())) {
				selectClause ="SELECT DISTINCT ETC_ACCOUNT_ID,DEVICE_NO,TX_TIMESTAMP,POSTED_DATE,PLATE_NUMBER,PLATE_STATE,LANE_TX_ID,TX_EXTERN_REF_NO,ENTRY_PLAZA_ID,LANE_ID,ENTRY_TX_TIMESTAMP, "+
						"UPDATE_TS,TX_STATUS,POSTED_FARE_AMOUNT,PLAZA_ID,ENTRY_PLAZA_ID,LANE_ID,ENTRY_LANE_ID,PLAZA_AGENCY_ID,ACTUAL_CLASS,DST_FLAG,UNREALIZED_AMOUNT,PLAN_TYPE,'' AS IMAGE_URL FROM T_TRAN_DETAIL ";
				//				"UPDATE_TS,ETC_TX_STATUS,DISCOUNTED_AMOUNT,PLAZA_ID,ENTRY_PLAZA_ID,LANE_ID,ENTRY_LANE_ID,PLAZA_AGENCY_ID,ACTUAL_CLASS,CASE WHEN UNREALIZED_AMOUNT > 0 THEN 'true' ELSE 'false' END AS DISPUTED_FLAG,UNREALIZED_AMOUNT,PLAN_TYPE,'' AS IMAGE_URL FROM T_TRAN_DETAIL ";
			} else {
				selectClause ="SELECT DISTINCT ETC_ACCOUNT_ID,DEVICE_NO,TX_TIMESTAMP,POSTED_DATE,PLATE_NUMBER,PLATE_STATE,LANE_TX_ID,TX_EXTERN_REF_NO,ENTRY_PLAZA_ID,LANE_ID,ENTRY_TX_TIMESTAMP, "+
						" T_TRAN_DETAIL.UPDATE_TS,TX_STATUS,POSTED_FARE_AMOUNT,PLAZA_ID,ENTRY_PLAZA_ID,LANE_ID,ENTRY_LANE_ID,PLAZA_AGENCY_ID,ACTUAL_CLASS,DST_FLAG,UNREALIZED_AMOUNT,PLAN_TYPE,'' AS IMAGE_URL FROM T_TRAN_DETAIL,CRM.V_AGENCY ";
				//				" T_TRAN_DETAIL.UPDATE_TS,ETC_TX_STATUS,DISCOUNTED_AMOUNT,PLAZA_ID,ENTRY_PLAZA_ID,LANE_ID,ENTRY_LANE_ID,PLAZA_AGENCY_ID,ACTUAL_CLASS,CASE WHEN UNREALIZED_AMOUNT > 0 THEN 'true' ELSE 'false' END AS DISPUTED_FLAG,UNREALIZED_AMOUNT,PLAN_TYPE,'' AS IMAGE_URL FROM T_TRAN_DETAIL,CRM.V_AGENCY ";

			}
			String orderClause = "";
			String actualSortFieldNameInDatabase =null;
			if (!StringUtils.isBlank(transactionSearchGlobalRequest.getSortType())) {

				if (!transactionSearchValidation.validateSortFieldNames(transactionSearchGlobalRequest.getSortType())){
					throw new TPMSGlobalException(TransactionSearchConstants.INVALID_SORT_FIELD_NAME_SPECIFIED,className,methodName);
				}else {
					actualSortFieldNameInDatabase = transactionSearchUtility.findActualSortFieldNameInDatabase(transactionSearchGlobalRequest.getSortType());

					if (transactionSearchGlobalRequest.getSortBy().equalsIgnoreCase("ASC")) {
						orderClause = " ORDER BY "+actualSortFieldNameInDatabase;
					} else if (transactionSearchGlobalRequest.getSortBy().equalsIgnoreCase("DESC")) {
						orderClause = " ORDER BY "+actualSortFieldNameInDatabase+" DESC";
					} 
				}
			}else {
				orderClause = " ORDER BY ETC_ACCOUNT_ID";
			}

			StringBuilder whereClauseForQuery = finalWhereClauseBuildForGlobalQuery(transactionSearchGlobalRequest);
			StringBuilder finalQueryToBeExecuted = new StringBuilder();
			finalQueryToBeExecuted.append(selectClause);
			finalQueryToBeExecuted.append(" ");
			//finalQueryToBeExecuted.append(whereClauseForQuery==null || !whereClauseForQuery.equals("WHERE")?" ":whereClauseForQuery);
			finalQueryToBeExecuted.append(whereClauseForQuery==null || whereClauseForQuery.equals("WHERE ")?" ":whereClauseForQuery);
			finalQueryToBeExecuted.append(" ");
			finalQueryToBeExecuted.append(orderClause);
			//finalQueryToBeExecuted.append(" OFFSET "+ transactionSearchGlobalRequest.getPage()*transactionSearchGlobalRequest.getSize()+" ROWS FETCH NEXT "+ transactionSearchGlobalRequest.getSize()+" ROWS ONLY");
			List<TranQueryReturn> tranQueryReturnList = transDetailDao.getAllRowsFromQuery(finalQueryToBeExecuted.toString(),transactionSearchGlobalRequest.getPage(),transactionSearchGlobalRequest.getSize());

			logger.info("Generated Dynamic query for Transaction Search "+finalQueryToBeExecuted.toString());
			logger.info("Class Name :"+this.getClass()+" buildTransactionSearchQuery method ended");
			//return transactionResponsePOJOList;	
			return tranQueryReturnList;	

		} catch (Exception ex) {
			logger.error("class : " + getClass() + " Error in buildTransactionSearchQuery \n" + ex.getMessage());

		}
		return null;
	}
	//
	@Override
	//public List<TransactionResponsePOJO> buildTransactionSearchGlobalQuery(TransactionSearchGlobalRequest transactionSearchGlobalRequest) {
	public List<TranQueryReturnFromView>  buildTransactionSearchGlobalQueryFromView(TransactionSearchGlobalRequest transactionSearchGlobalRequest) {
		logger.info("Class Name :"+this.getClass()+" buildTransactionSearchGlobalQueryFromView method started");
		String methodName = "buildTransactionSearchGlobalQueryFromView";
		try {

			//String selectClause ="SELECT  DISTINCT ACCOUNT_NO ,AGENCY_NAME ,DEVICE_NO ,DISCOUNTED_FARE ,DISPUTED_AMOUNT ,DISPUTED_FLAG ,ENTRY_EXTERN_LANE_ID ,ENTRY_EXTERN_PLAZA_ID ,ENTRY_LANE_ID ,ENTRY_PLAZA_DESCRIPTION ,ENTRY_PLAZA_ID ,ENTRY_TX_TIMESTAMP ,ESCALATION_LEVEL ,ETC_ACCOUNT_ID ,EXIT_EXTERN_LANE_ID ,EXIT_EXTERN_PLAZA_ID ,EXIT_LANE_ID ,EXIT_PLAZA_DESCRIPTION ,EXIT_PLAZA_ID ,IMAGE_URL ,INVOICE_NUMBER ,LANE_TX_ID,PLAN_NAME ,PLATE_NUMBER ,PLATE_STATE ,POSTED_DATE ,TX_DATE ,TX_EXTERN_REF_NO ,TX_STATUS ,TX_TIMESTAMP ,UPDATE_TS ,VEHICLE_CLASS, 'TOLL' AS CATEGORY, \n"
					
			String selectClause ="SELECT DISTINCT ACCOUNT_NO ,AGENCY_NAME ,DEVICE_NO ,DISCOUNTED_FARE ,DISPUTED_AMOUNT ,DISPUTED_FLAG ,ENTRY_EXTERN_LANE_ID ,ENTRY_EXTERN_PLAZA_ID ,ENTRY_LANE_ID ,ENTRY_PLAZA_DESCRIPTION ,ENTRY_PLAZA_ID ,ENTRY_TX_TIMESTAMP ,ESCALATION_LEVEL ,ETC_ACCOUNT_ID ,EXIT_EXTERN_LANE_ID ,EXIT_EXTERN_PLAZA_ID ,EXIT_LANE_ID ,EXIT_PLAZA_DESCRIPTION ,EXIT_PLAZA_ID ,IMAGE_URL ,INVOICE_NUMBER ,LANE_TX_ID,PLAN_NAME ,PLATE_NUMBER ,PLATE_STATE ,POSTED_DATE ,TX_DATE ,TX_EXTERN_REF_NO ,TX_STATUS ,TX_TIMESTAMP ,UPDATE_TS ,VEHICLE_CLASS, 'TOLL' AS CATEGORY, \n"
					+ "TRANSACTION_ROW_ID,ACTUAL_AXLES,DESCRIPTION,TRAN_CODE,POST_POSTPAID_BALANCE FROM TPMS.V_ALL_TRANSACTION ";
			if (!(transactionSearchGlobalRequest.getAgencyShortName()==null || StringUtils.isBlank(transactionSearchGlobalRequest.getAgencyShortName()))) {
			    selectClause+=" , CRM.V_AGENCY ";
			}


			String orderClause = "";
			String whereClause=" WHERE ";
			String actualSortFieldNameInDatabase =null;
			if (!StringUtils.isBlank(transactionSearchGlobalRequest.getSortType())) {

				if (!transactionSearchValidation.validateSortFieldNames(transactionSearchGlobalRequest.getSortType())){
					throw new TPMSGlobalException(TransactionSearchConstants.INVALID_SORT_FIELD_NAME_SPECIFIED,className,methodName);
				}else {
					actualSortFieldNameInDatabase = transactionSearchUtility.findActualSortFieldNameInDatabase(transactionSearchGlobalRequest.getSortType());

					if (transactionSearchGlobalRequest.getSortBy().equalsIgnoreCase("ASC")) {
						orderClause = " ORDER BY "+actualSortFieldNameInDatabase;
					} else if (transactionSearchGlobalRequest.getSortBy().equalsIgnoreCase("DESC")) {
						orderClause = " ORDER BY "+actualSortFieldNameInDatabase+" DESC";
					} 
				}
			}else {
				orderClause = " ORDER BY ACCOUNT_NO";
			}

			StringBuilder whereClauseForQuery = finalWhereClauseBuildForGlobalQueryFromView(transactionSearchGlobalRequest);
			StringBuilder finalQueryToBeExecuted = new StringBuilder();
			finalQueryToBeExecuted.append(selectClause);
			finalQueryToBeExecuted.append(" ");
			//finalQueryToBeExecuted.append(whereClauseForQuery==null || !whereClauseForQuery.equals("WHERE")?" ":whereClauseForQuery);
			finalQueryToBeExecuted.append(whereClauseForQuery==null || StringUtils.isEmpty(whereClauseForQuery)?" ":whereClause+ whereClauseForQuery);
			finalQueryToBeExecuted.append(" ");
			finalQueryToBeExecuted.append(orderClause);
			//finalQueryToBeExecuted.append(" OFFSET "+ transactionSearchGlobalRequest.getPage()*transactionSearchGlobalRequest.getSize()+" ROWS FETCH NEXT "+ transactionSearchGlobalRequest.getSize()+" ROWS ONLY");
			List<TranQueryReturnFromView> tranQueryReturnList = transDetailDao.getAllRowsFromQueryView(finalQueryToBeExecuted.toString(),transactionSearchGlobalRequest.getPage(),transactionSearchGlobalRequest.getSize());

			logger.info("Generated Dynamic query for Transaction Search "+finalQueryToBeExecuted.toString());
			logger.info("Class Name :"+this.getClass()+" buildTransactionSearchQueryFromView method ended");
			//return transactionResponsePOJOList;	
			return tranQueryReturnList;	

		} catch (Exception ex) {
			logger.error("class : " + getClass() + " Error in buildTransactionSearchQuery \n" + ex.getMessage());

		}
		return null;
	}


	//	
	@Override
	public List<TransactionResponsePOJO> transactionResponsePOJO(List<TranQueryReturn> tranQueryReturnList,TransactionSearchGlobalRequest transactionSearchGlobalRequest) {
		List<TransactionResponsePOJO> transactionResponsePOJOList = setResponseValues(tranQueryReturnList,transactionSearchGlobalRequest);
		return transactionResponsePOJOList;	
	}
	@Override
	public List<TransactionResponsePOJO> transactionResponsePOJOFromView(List<TranQueryReturnFromView> tranQueryReturnList,TransactionSearchGlobalRequest transactionSearchGlobalRequest) {
		List<TransactionResponsePOJO> transactionResponsePOJOList = setResponseValuesFromView(tranQueryReturnList,transactionSearchGlobalRequest);
		return transactionResponsePOJOList;	
	}
	public List<TransactionResponsePOJO> setResponseValues(List<TranQueryReturn> tranQueryReturnList)
	{
		logger.info("Class : "+this.getClass()+" Method Name : setResponseValues STARTED    Parameter"+tranQueryReturnList);

		List<TransactionResponsePOJO> transactionResponsePOJOList = new ArrayList<TransactionResponsePOJO>();
		Iterator<TranQueryReturn> itr = tranQueryReturnList.iterator();
		TranQueryReturn tranQueryReturn = null;
		TransactionResponsePOJO transactionResponsePOJO = null;
		while (itr.hasNext()) {
			tranQueryReturn = (TranQueryReturn) itr.next();
			transactionResponsePOJO = new TransactionResponsePOJO();

			transactionResponsePOJO.setAccountNumber(tranQueryReturn.getAccountNumber());
			transactionResponsePOJO.setAgencyStatementName(" ");
			transactionResponsePOJO.setTransponderNumber(tranQueryReturn.getTransponderNumber());		
			transactionResponsePOJO.setAgencyStatementName(tranQueryReturn.getAgencyStatementName());
			transactionResponsePOJO.setDiscFare(tranQueryReturn.getPostedFareAmount());
			transactionResponsePOJO.setDisputedAmount(tranQueryReturn.getDisputedAmount()) ;
			transactionResponsePOJO.setDisputedFlag(tranQueryReturn.isDisputedFlag());
			transactionResponsePOJO.setEntryLane(String.valueOf(tranQueryReturn.getEntryLane()));
			transactionResponsePOJO.setExitPlazaDescription(String.valueOf(tranQueryReturn.getExitPlazaDescription()));
			transactionResponsePOJO.setEntryPlazaDescription(String.valueOf(tranQueryReturn.getEntryPlazaDescription()));
			transactionResponsePOJO.setEntryTransactionDateTime(tranQueryReturn.getTransactionDateTime());
			transactionResponsePOJO.setExitLane(String.valueOf(tranQueryReturn.getLaneId()));
			transactionResponsePOJO.setExtReferenceNo(tranQueryReturn.getExtReferenceNo());
			transactionResponsePOJO.setImageURL(tranQueryReturn.getImageURL());
			transactionResponsePOJO.setLaneTxID(String.valueOf(tranQueryReturn.getLaneTxID()));
			transactionResponsePOJO.setPlanName(String.valueOf(tranQueryReturn.getPlanName()));
			transactionResponsePOJO.setPlateNumber(tranQueryReturn.getPlateNumber());
			transactionResponsePOJO.setPostedDate(tranQueryReturn.getPostedDate());
			transactionResponsePOJO.setTransactionDateTime(tranQueryReturn.getTransactionDateTime());
			transactionResponsePOJO.setTxStatus(tranQueryReturn.getTxStatus());
			transactionResponsePOJO.setUpdateTs(tranQueryReturn.getUpdateTs());
			transactionResponsePOJO.setVehicleClass(String.valueOf(tranQueryReturn.getVehicleClass()));
			//transactionResponsePOJO.setEntryExternLaneId();


			transactionResponsePOJOList.add(transactionResponsePOJO);
		}
		logger.info("Class : "+this.getClass()+" Method Name : setResponseValues ENDED    RETURNS :"+transactionResponsePOJOList);

		return transactionResponsePOJOList;
	}
	public List<TransactionResponsePOJO> setResponseValuesFromView(List<TranQueryReturnFromView> tranQueryReturnList, Object obj)
	{
		logger.info("Class : "+this.getClass()+" Method Name : setResponseValues STARTED    Parameter"+tranQueryReturnList);
		int startingRowNumber = 0;
		int endingRowNumber = 0;
		int page = 0;
		int size = 0;
		int currentRowNumber = 0;
		boolean skipFlag = true;

		if (obj instanceof TransactionSearchGlobalRequest)
		{
			TransactionSearchGlobalRequest transactionSearchGlobalRequest = (TransactionSearchGlobalRequest) obj;
						page = (transactionSearchGlobalRequest.getPage() != null && transactionSearchGlobalRequest.getPage() > 1)? transactionSearchGlobalRequest.getPage()-1:0 ;
						size = (transactionSearchGlobalRequest.getSize() != null )? transactionSearchGlobalRequest.getSize():1;
			
						startingRowNumber =  (page * size )+1; 
						endingRowNumber = (page * size )+1+ size;
						page = (transactionSearchGlobalRequest.getPage() != null && transactionSearchGlobalRequest.getPage() > 1)? transactionSearchGlobalRequest.getPage():1 ;
						
		} else 
			if (obj instanceof TransactionSearchRequest)
			{
				TransactionSearchRequest transactionSearchRequest = (TransactionSearchRequest) obj;
				page = (transactionSearchRequest.getPage() != null && transactionSearchRequest.getPage() > 1)? transactionSearchRequest.getPage()-1:0 ;
				size = (transactionSearchRequest.getSize() != null )? transactionSearchRequest.getSize():0;

				startingRowNumber =  page * size; 
				endingRowNumber = page * size + size;
			}	


		if (page == 0 && size == 0) skipFlag = false;
		List<TransactionResponsePOJO> transactionResponsePOJOList = new ArrayList<TransactionResponsePOJO>();
		Iterator<TranQueryReturnFromView> itr = tranQueryReturnList.iterator();
		TranQueryReturnFromView tranQueryReturn = null;
		TransactionResponsePOJO transactionResponsePOJO = null;
		int counter = 0;
		int currentPageNumber = page;
		while (itr.hasNext()) {
			tranQueryReturn = (TranQueryReturnFromView) itr.next();
			//			counter++;
			//			if (skipFlag)
			//			{
			//				if (counter < startingRowNumber) continue;
			//				if (counter > endingRowNumber) break;
			//			}
			transactionResponsePOJO = new TransactionResponsePOJO();

			transactionResponsePOJO.setAccountNumber(tranQueryReturn.getAccountNo());
			transactionResponsePOJO.setAgencyStatementName(tranQueryReturn.getAgencyName());
			transactionResponsePOJO.setTransponderNumber(tranQueryReturn.getDeviceNo());		
			transactionResponsePOJO.setDiscFare(tranQueryReturn.getDiscountedFare());
			transactionResponsePOJO.setDisputedAmount(tranQueryReturn.getDisputedAmount()) ;
			transactionResponsePOJO.setDisputedFlag(tranQueryReturn.isDisputedFlag());
			transactionResponsePOJO.setEntryLane(String.valueOf(tranQueryReturn.getEntryExternLaneId()));
			transactionResponsePOJO.setExitPlazaDescription(String.valueOf(tranQueryReturn.getExitPlazaDescription()));
			transactionResponsePOJO.setEntryPlazaDescription(String.valueOf(tranQueryReturn.getEntryPlazaDescription()));
			transactionResponsePOJO.setEntryTransactionDateTime(tranQueryReturn.getEntryTxTimestamp());
			transactionResponsePOJO.setExitLane(String.valueOf(tranQueryReturn.getExitExternLaneId()));
			transactionResponsePOJO.setExtReferenceNo(tranQueryReturn.getTxExternRefNo());
			transactionResponsePOJO.setImageURL(tranQueryReturn.getImageUrl());
			transactionResponsePOJO.setLaneTxID(String.valueOf(tranQueryReturn.getLaneTxId()));
			transactionResponsePOJO.setPlanName(String.valueOf(tranQueryReturn.getPlanName()));
			transactionResponsePOJO.setPlateNumber(tranQueryReturn.getPlateNumber());
			transactionResponsePOJO.setPlateState(tranQueryReturn.getPlateState());
			transactionResponsePOJO.setPostedDate(tranQueryReturn.getPostedDate());
			transactionResponsePOJO.setTransactionDateTime(tranQueryReturn.getTxTimestamp());
			transactionResponsePOJO.setTxStatus(tranQueryReturn.getTxStatus());
			transactionResponsePOJO.setUpdateTs(tranQueryReturn.getUpdateTs());
			transactionResponsePOJO.setVehicleClass(String.valueOf(tranQueryReturn.getVehicleClass()));

			transactionResponsePOJO.setInvoiceNumber(tranQueryReturn.getInvoiceNumber());
			transactionResponsePOJO.setEscalationLevel(tranQueryReturn.getEscalationLevel());
			transactionResponsePOJO.setEntryExternPlazaId(tranQueryReturn.getEntryExternPlazaId());
			transactionResponsePOJO.setExitExternPlazaId(tranQueryReturn.getExitExternPlazaId());
			transactionResponsePOJO.setEntryExternLaneId(tranQueryReturn.getEntryExternLaneId());
			transactionResponsePOJO.setExitExternLaneId(tranQueryReturn.getExitExternLaneId());
			transactionResponsePOJO.setCategory(tranQueryReturn.getCategory());
			transactionResponsePOJO.setActualAxles(tranQueryReturn.getActualAxles());
			transactionResponsePOJO.setDescription(tranQueryReturn.getDescription());
			transactionResponsePOJO.setTransactionRowId(tranQueryReturn.getTransactionRowId());
			transactionResponsePOJO.setTranType(tranQueryReturn.getTranType());
			transactionResponsePOJO.setPostTransactionBalance(tranQueryReturn.getPostTransactionBalance());
		    
			counter++;
			if (counter == size) {
				counter=0;
			    currentPageNumber++;	
			}

			transactionResponsePOJOList.add(transactionResponsePOJO);
		}
		logger.info("Class : "+this.getClass()+" Method Name : setResponseValues ENDED    RETURNS :"+transactionResponsePOJOList);

		return transactionResponsePOJOList;
	}

	public List<TransactionResponsePOJO> setResponseValues(List<TranQueryReturn> tranQueryReturnList, Object obj)
	{
		logger.info("Class : "+this.getClass()+" Method Name : setResponseValues STARTED    Parameter"+tranQueryReturnList);
		int startingRowNumber = 0;
		int endingRowNumber = 0;
		int page = 0;
		int size = 0;
		int currentRowNumber = 0;
		boolean skipFlag = true;

		if (obj instanceof TransactionSearchGlobalRequest)
		{
			TransactionSearchGlobalRequest transactionSearchGlobalRequest = (TransactionSearchGlobalRequest) obj;
			//			page = (transactionSearchGlobalRequest.getPage() != null && transactionSearchGlobalRequest.getPage() > 1)? transactionSearchGlobalRequest.getPage()-1:0 ;
			//			size = (transactionSearchGlobalRequest.getSize() != null )? transactionSearchGlobalRequest.getSize():0;
			//
			//			startingRowNumber =  page * size; 
			//			endingRowNumber = page * size + size;
		} else 
			if (obj instanceof TransactionSearchRequest)
			{
				TransactionSearchRequest transactionSearchRequest = (TransactionSearchRequest) obj;
				page = (transactionSearchRequest.getPage() != null && transactionSearchRequest.getPage() > 1)? transactionSearchRequest.getPage()-1:0 ;
				size = (transactionSearchRequest.getSize() != null )? transactionSearchRequest.getSize():0;

				startingRowNumber =  page * size; 
				endingRowNumber = page * size + size;
			}	


		if (page == 0 && size == 0) skipFlag = false;
		List<TransactionResponsePOJO> transactionResponsePOJOList = new ArrayList<TransactionResponsePOJO>();
		Iterator<TranQueryReturn> itr = tranQueryReturnList.iterator();
		TranQueryReturn tranQueryReturn = null;
		TransactionResponsePOJO transactionResponsePOJO = null;
		int counter = 0;
		while (itr.hasNext()) {
			tranQueryReturn = (TranQueryReturn) itr.next();
			//			counter++;
			//			if (skipFlag)
			//			{
			//				if (counter < startingRowNumber) continue;
			//				if (counter > endingRowNumber) break;
			//			}
			transactionResponsePOJO = new TransactionResponsePOJO();

			transactionResponsePOJO.setAccountNumber(tranQueryReturn.getAccountNumber());
			transactionResponsePOJO.setAgencyStatementName(" ");
			transactionResponsePOJO.setTransponderNumber(tranQueryReturn.getTransponderNumber());		
			transactionResponsePOJO.setAgencyStatementName(tranQueryReturn.getAgencyStatementName());
			transactionResponsePOJO.setDiscFare(tranQueryReturn.getPostedFareAmount());
			transactionResponsePOJO.setDisputedAmount(tranQueryReturn.getDisputedAmount()) ;
			transactionResponsePOJO.setDisputedFlag(tranQueryReturn.isDisputedFlag());
			transactionResponsePOJO.setEntryLane(String.valueOf(tranQueryReturn.getEntryLane()));
			transactionResponsePOJO.setExitPlazaDescription(String.valueOf(tranQueryReturn.getExitPlazaDescription()));
			transactionResponsePOJO.setEntryPlazaDescription(String.valueOf(tranQueryReturn.getEntryPlazaDescription()));
			transactionResponsePOJO.setEntryTransactionDateTime(tranQueryReturn.getEntryTransactionDateTime());
			transactionResponsePOJO.setExitLane(String.valueOf(tranQueryReturn.getLaneId()));
			transactionResponsePOJO.setExtReferenceNo(tranQueryReturn.getExtReferenceNo());
			transactionResponsePOJO.setImageURL(tranQueryReturn.getImageURL());
			transactionResponsePOJO.setLaneTxID(String.valueOf(tranQueryReturn.getLaneTxID()));
			transactionResponsePOJO.setPlanName(String.valueOf(tranQueryReturn.getPlanName()));
			transactionResponsePOJO.setPlateNumber(tranQueryReturn.getPlateNumber());
			transactionResponsePOJO.setPostedDate(tranQueryReturn.getPostedDate());
			transactionResponsePOJO.setTransactionDateTime(tranQueryReturn.getTransactionDateTime());
			transactionResponsePOJO.setTxStatus(tranQueryReturn.getTxStatus());
			transactionResponsePOJO.setUpdateTs(tranQueryReturn.getUpdateTs());
			transactionResponsePOJO.setVehicleClass(String.valueOf(tranQueryReturn.getVehicleClass()));


			transactionResponsePOJOList.add(transactionResponsePOJO);
		}
		logger.info("Class : "+this.getClass()+" Method Name : setResponseValues ENDED    RETURNS :"+transactionResponsePOJOList);

		return transactionResponsePOJOList;
	}

	public StringBuilder finalWhereClauseBuild(List<TransactionSearchFilter> transactionSearchFilterList, TransactionSearchRequest transactionSearchRequest) throws TPMSGlobalException{
		logger.info("Class Name : "+this.getClass()+" Method Name : finalWhereClauseBuild "+ " Started...");
		String methodName = "finalWhereClauseBuild";
		String fieldName = "";
		String fieldValue = "";
		//String whereClauseForQuery = "WHERE LEFT(ETC_ACCOUNT_ID,LENGTH(ETC_ACCOUNT_ID)-1) = "+transactionSearchRequest.getAccountNumber();	
		StringBuilder whereClauseForQuery = new StringBuilder();
		if (transactionSearchRequest.getAccountNumber() == null) {
			TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="Account Number is Mandatory ";
			throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,this.getClass().getName(),methodName);
		}
		whereClauseForQuery.append("WHERE ETC_ACCOUNT_ID="+transactionSearchRequest.getAccountNumber());	
		int startingWhereClauseCounter = 0;
		TransactionSearchFilter transactionSearchFilter;
		String dataTypeFromValue="";
		String greaterThanOrEqualSign = " >=";
		String lessThanOrEqualSign = " <=";
		String actualSignToBeIncorporatedInTheQuery ="=";
		String inOperator = " IN ";
		// building the where clause for the query based on the request 

		String transactionFromDate =" ";
		String transactionToDate =" ";
		String postedFromDate =" ";
		String postedToDate =" ";
		String actualFieldNameInDatabase=" ";
		String dateFormat =TransactionSearchConstants.TRANSACTION_SEARCH_DATE_FORMAT_IN_SQL;
		List<String> filterPlazaAndLaneList = new ArrayList<String>();
		filterPlazaAndLaneList=Arrays.asList(TransactionSearchConstants.FILTER_WITH_PLAZA_AND_OPTIONAL_LANE_ID);
		boolean plazaOrLaneExistsFlag = false;
		boolean dateFieldExistsFlag = false;
		boolean valueContainsMultipleValue = false;
		String[]   stringValues = null;
		Long[]     longValues = null;
		String[]   dateValues = null;
		int totalNumberOfMultipleValues = 0;
		StringTokenizer multipleValueTokenizer = null;
		StringBuilder inQueryString=null;

		if (transactionSearchFilterList != null) {

			if (!(transactionSearchValidation.validateAllFilterFieldNames(transactionSearchFilterList))) {
				TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="Transaction Search Invalid Field Names specified";
				throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
			}

			if (!(transactionSearchValidation.validateAllRequestMemberName(transactionSearchFilterList))){
				logger.info("Class Name : "+this.getClass()+" Method Name : finalWhereClauseBuild "+ " VALIDATION FAILED...");
				TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="Transaction Search Validation Failed";
				throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
			}
			logger.info("Class Name : "+this.getClass()+" Method Name : finalWhereClauseBuild "+ " Started...STEP 1");
			Iterator<TransactionSearchFilter> iterator = transactionSearchFilterList.iterator();
			while (iterator.hasNext()) {
				transactionSearchFilter = (TransactionSearchFilter) iterator.next();
				fieldName = transactionSearchFilter.getFieldName();
				if (fieldName.equals("page") || fieldName.equals("size") || fieldName.equals("sortType")  ) continue;
				fieldValue = transactionSearchFilter.getFieldValue();
				dataTypeFromValue=transactionSearchUtility.determineDataTypeFromFieldName(fieldName);
				valueContainsMultipleValue=false;
				inQueryString = new StringBuilder();		
				if (fieldValue != null) {

					if (fieldValue.startsWith("[")) {
						inQueryString.append(inOperator);
						inQueryString.append(" ( ");
						inQueryString.append(fieldValue.substring(1,fieldValue.length()-1));
						inQueryString.append(" ) ");

						valueContainsMultipleValue=true;

					}
				}
				if (fieldValue != null && fieldValue.length()>0) {
					whereClauseForQuery.append(" AND ");
					if (filterPlazaAndLaneList.contains(fieldName)) {
						plazaOrLaneExistsFlag = true;
					}
					startingWhereClauseCounter++;


					actualFieldNameInDatabase = transactionSearchUtility.findActualFieldNameInDatabase(fieldName);

					logger.info("Class : "+ this.getClass()+" MethodName : finalWhereClauseBuild "+" fieldName : "+fieldName+" fieldValue : "+fieldValue+" actualFieldNameInDatabase : "+actualFieldNameInDatabase);


					actualSignToBeIncorporatedInTheQuery = "="; 
					if (fieldName.equals("transactionFromDate")) {
						actualSignToBeIncorporatedInTheQuery = greaterThanOrEqualSign;	 
						transactionFromDate = fieldValue;
						setFromDate(fieldValue);
						dateFieldExistsFlag=true;
					} else if (fieldName.equals("transactionToDate")) {
						actualSignToBeIncorporatedInTheQuery = lessThanOrEqualSign;	
						transactionToDate = fieldValue;
						setToDate(fieldValue);
						dateFieldExistsFlag=true;
					} else if (fieldName.equals("postedFromDate")) {
						actualSignToBeIncorporatedInTheQuery = greaterThanOrEqualSign;	 
						postedFromDate = fieldValue;
						setFromDate(fieldValue);
						dateFieldExistsFlag=true;
					} else if (fieldName.equals("postedToDate")) {
						actualSignToBeIncorporatedInTheQuery = lessThanOrEqualSign;	
						postedToDate = fieldValue;
						setToDate(fieldValue);
						dateFieldExistsFlag=true;

					}

					if (valueContainsMultipleValue)
					{
						whereClauseForQuery.append(actualFieldNameInDatabase) ;
						whereClauseForQuery.append(inQueryString);
						continue;
					}

					if (dataTypeFromValue.equalsIgnoreCase("String"))
					{	 
						whereClauseForQuery.append(actualFieldNameInDatabase+"="+"'"+fieldValue+"'");
					} else if (dataTypeFromValue.equalsIgnoreCase("Number")) {
						whereClauseForQuery.append(actualFieldNameInDatabase+"="+Long.parseLong(fieldValue));
					} else if (dataTypeFromValue.equalsIgnoreCase("Date")) {
						whereClauseForQuery.append(actualFieldNameInDatabase+ actualSignToBeIncorporatedInTheQuery+" TO_DATE('"+fieldValue+"','"+dateFormat+"')");
					} 
				}
			}

			if (dateFieldExistsFlag && fromDate != null && toDate != null) {
				if (plazaOrLaneExistsFlag) {
					whereClauseForQuery.append(" AND TO_DATE('"+toDate+"','DD-MM-YYYY')-TO_DATE('"+fromDate+"','DD-MM-YYYY') < "+TransactionSearchConstants.DATE_RANCE_FOR_PLAZA_LANE_SEARCH);
				} else {
					whereClauseForQuery.append(" AND TO_DATE('"+toDate+"','DD-MM-YYYY')-TO_DATE('"+fromDate+"','DD-MM-YYYY') < "+TransactionSearchConstants.DATE_RANGE_FOR_OTHER_FIELDS_OTHER_THAN_PLAZA_LANE);
				}
			}
			logger.info("Class Name : "+this.getClass()+" Method Name : finalWhereClauseBuild "+ " Started...STEP 2");


			if (startingWhereClauseCounter > 1) {
				//	whereClauseForQuery += ";";
			}

			logger.info(" whereClauseQuery = "+whereClauseForQuery);
			logger.info("Class Name : "+this.getClass()+" Method Name : finalWhereClauseBuild "+ " Ended...");

			return whereClauseForQuery;  
		}
		logger.info(" whereClauseQuery = "+whereClauseForQuery);
		logger.info("Class Name : "+this.getClass()+" Method Name : finalWhereClauseBuild "+ " Ended...");
		return null;
	}


	public StringBuilder finalWhereClauseBuildForGlobalQuery(TransactionSearchGlobalRequest transactionSearchGlobalRequest) throws TPMSGlobalException{
		logger.info("Class Name : "+this.getClass()+" Method Name : finalWhereClauseBuildForBuildQuery "+ " Started...");
		String methodName = "finalWhereClauseBuildForGlobalQuery";
		String fieldName = "";
		String fieldValue = "";
		//String whereClauseForQuery = "WHERE LEFT(ETC_ACCOUNT_ID,LENGTH(ETC_ACCOUNT_ID)-1) = "+transactionSearchRequest.getAccountNumber();	
		StringBuilder whereClauseForQuery = new StringBuilder();
		whereClauseForQuery.append("WHERE ");	
		if (transactionSearchGlobalRequest.getAgencyShortName()!= null && (!transactionSearchGlobalRequest.getAgencyShortName().isBlank())) {
			whereClauseForQuery.append( " PLAZA_AGENCY_ID = CRM.V_AGENCY.AGENCY_ID AND ");
			whereClauseForQuery.append(" CRM.V_AGENCY.AGENCY_SHORT_NAME = '"+transactionSearchGlobalRequest.getAgencyShortName()+"' AND ");
		}		

		int startingWhereClauseCounter = 0;
		TransactionSearchFilter transactionSearchFilter;
		String dataTypeFromValue="";
		String equalSign = " = ";
		String greaterThanOrEqualSign = " >=";
		String lessThanOrEqualSign = " <=";
		String actualSignToBeIncorporatedInTheQuery ="=";
		String inOperator = " IN ";

		String transactionFromDate =" ";
		String transactionToDate =" ";
		String postedFromDate =" ";
		String postedToDate =" ";
		String actualFieldNameInDatabase=" ";
		String dateFormat =transactionSearchConstants.TRANSCATION_SEARCH_DATE_FORMAT;

		boolean plazaOrLaneExistsFlag = false;
		boolean dateFieldExistsFlag = false;
		boolean valueContainsMultipleValue = false;
		String[]   stringValues = null;
		Long[]     longValues = null;
		String[]   dateValues = null;
		int totalNumberOfMultipleValues = 0;
		StringTokenizer multipleValueTokenizer = null;
		StringBuilder inQueryString=null;

		logger.info("Class Name : "+this.getClass()+" Method Name : finalWhereClauseBuildForBuildQuery "+ " Started...STEP 1");

		Map<String,String> inputRequestMap = new HashMap<String,String>();
		if (transactionSearchGlobalRequest.getEtcAccountId() != null) {
			inputRequestMap.put("etcAccountId",transactionSearchGlobalRequest.getEtcAccountId());
		}
		//		if (transactionSearchGlobalRequest.getDeviceNo() != null) {
		//			inputRequestMap.put("deviceNo",transactionSearchGlobalRequest.getDeviceNo());
		//
		//		}
		/*if (transactionSearchGlobalRequest.getCategory() != null) {
			inputRequestMap.put("category",transactionSearchGlobalRequest.getCategory());
		}
		 */
		if (transactionSearchGlobalRequest.getPlateNumber() != null) {
			inputRequestMap.put("plateNumber",transactionSearchGlobalRequest.getPlateNumber());
		}
		if (transactionSearchGlobalRequest.getTransactionFromDate() != null) {
			inputRequestMap.put("transactionFromDate",transactionSearchGlobalRequest.getTransactionFromDate());
		}
		if (transactionSearchGlobalRequest.getTransactionToDate() != null) {
			inputRequestMap.put("transactionToDate",transactionSearchGlobalRequest.getTransactionToDate());
		}
		if (transactionSearchGlobalRequest.getPostedFromDate() != null) {
			inputRequestMap.put("postedFromDate",transactionSearchGlobalRequest.getPostedFromDate());
		}
		if (transactionSearchGlobalRequest.getPostedToDate() != null) {
			inputRequestMap.put("postedToDate",transactionSearchGlobalRequest.getPostedToDate());
		}
		if (transactionSearchGlobalRequest.getPlazaId() != null) {
			inputRequestMap.put("plazaId",transactionSearchGlobalRequest.getPlazaId());
		}
		if (transactionSearchGlobalRequest.getAgencyShortName() != null) {
			inputRequestMap.put("agencyShortName",transactionSearchGlobalRequest.getAgencyShortName());
		}

		if (transactionSearchGlobalRequest.getStatus() != null) {
			inputRequestMap.put("status",transactionSearchGlobalRequest.getStatus());
		}
		if (transactionSearchGlobalRequest.getType() != null) {
			inputRequestMap.put("type",transactionSearchGlobalRequest.getType());
		}
		/*	if (transactionSearchGlobalRequest.getEscLevel() != null) {
			inputRequestMap.put("escLevel",transactionSearchGlobalRequest.getEscLevel());
		}
		if (transactionSearchGlobalRequest.getInvoiceNo() != null) {
			inputRequestMap.put("invoiceNo",transactionSearchGlobalRequest.getInvoiceNo());
		}
		 */	if (transactionSearchGlobalRequest.getFromDate() != null) {
			 inputRequestMap.put("fromDate",transactionSearchGlobalRequest.getFromDate());
		 }
		 if (transactionSearchGlobalRequest.getToDate() != null) {
			 inputRequestMap.put("toDate",transactionSearchGlobalRequest.getToDate());
		 }
		 //Added by Leena
		 if(transactionSearchGlobalRequest.getLaneId() !=null) {
			 inputRequestMap.put("laneId",transactionSearchGlobalRequest.getLaneId());
		 }
		 if(transactionSearchGlobalRequest.getLaneTxId() !=null) {
			 inputRequestMap.put("laneTxId",transactionSearchGlobalRequest.getLaneTxId());
		 }
		 if(transactionSearchGlobalRequest.getPlateState() !=null) {
			 inputRequestMap.put("plateState",transactionSearchGlobalRequest.getPlateState());
		 }
		 if(transactionSearchGlobalRequest.getExtReferenceNo() !=null) {
			 inputRequestMap.put("extReferenceNo",transactionSearchGlobalRequest.getExtReferenceNo());
		 }
		 if(transactionSearchGlobalRequest.getTransponderNumber() !=null) {
			 inputRequestMap.put("transponderNumber",transactionSearchGlobalRequest.getTransponderNumber());
		 }

		 if (transactionSearchGlobalRequest.getPage()!= null) {
			 if (transactionSearchGlobalRequest.getPage()<0) {
				 TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="Page Number should be positive";
				 throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
			 }
		 }
		 if (transactionSearchGlobalRequest.getSize()!= null) {
			 if (transactionSearchGlobalRequest.getSize()<0) {
				 TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="Size should be positive";
				 throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
			 }
		 }
		 if (transactionSearchGlobalRequest.getSortBy()!= null) {
			 if (!(transactionSearchGlobalRequest.getSortBy().equals("ASC") || 
					 transactionSearchGlobalRequest.getSortBy().equals("DESC"))) {
				 TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="Sort By should be either ASC or DESC "+transactionSearchGlobalRequest.getSortBy();
				 throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
			 } 	
		 }
		 if (!(transactionSearchValidation.validateAllGlobalFieldNames(transactionSearchGlobalRequest,inputRequestMap))) {
			 TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="Transaction Search Invalid Field Names specified";
			 throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
		 }

		 if (!(transactionSearchValidation.validateAllRequestMemberNameGlobal(null,inputRequestMap))){
			 logger.info("Class Name : "+this.getClass()+" Method Name : finalWhereClauseBuildForBuildQuery "+ " VALIDATION FAILED...");
			 TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="Transaction Search Validation Failed";
			 throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
		 }

		 Object[] objArray = inputRequestMap.keySet().toArray();
		 for (int i =0;i<objArray.length;i++) {
			 fieldName = (String) objArray[i];
			 fieldValue = (String) inputRequestMap.get(fieldName);
			 if (fieldName.equals("agencyShortName") || fieldName.equals("page") || fieldName.equals("size")|| fieldName.equals("sortType")) {
				 continue;
			 }
			 dataTypeFromValue=transactionSearchUtility.determineDataTypeFromGlobalFieldName(fieldName);
			 valueContainsMultipleValue=false;
			 inQueryString = new StringBuilder();		
			 if (fieldValue != null) {

				 if (fieldValue.startsWith("[")) {
					 inQueryString.append(inOperator);
					 inQueryString.append(" ( ");
					 inQueryString.append(fieldValue.substring(1,fieldValue.length()-1));
					 inQueryString.append(" ) ");
					 valueContainsMultipleValue=true;
				 }
			 }
			 if (fieldValue != null && fieldValue.length()>0) {
				 if (startingWhereClauseCounter != 0) whereClauseForQuery.append(" AND ");
				 startingWhereClauseCounter++;
				 actualFieldNameInDatabase = transactionSearchUtility.findActualFieldNameInDatabaseForGlobalQuery(fieldName);
				 logger.info("Class : "+ this.getClass()+" MethodName : finalWhereClauseBuildForBuildQuery "+" fieldName : "+fieldName+" fieldValue : "+fieldValue+" actualFieldNameInDatabase : "+actualFieldNameInDatabase);
				 actualSignToBeIncorporatedInTheQuery = "="; 
				 if (fieldName.equals("transactionFromDate")) {
					 actualSignToBeIncorporatedInTheQuery = greaterThanOrEqualSign;	 
					 transactionFromDate = fieldValue;
					 setFromDate(fieldValue);
					 dateFieldExistsFlag=true;
				 } else if (fieldName.equals("transactionToDate")) {
					 actualSignToBeIncorporatedInTheQuery = lessThanOrEqualSign;	
					 transactionToDate = fieldValue;
					 setToDate(fieldValue);
					 dateFieldExistsFlag=true;
				 } else if (fieldName.equals("postedFromDate")) {
					 actualSignToBeIncorporatedInTheQuery = greaterThanOrEqualSign;	 
					 postedFromDate = fieldValue;
					 setFromDate(fieldValue);
					 dateFieldExistsFlag=true;
				 } else if (fieldName.equals("postedToDate")) {
					 actualSignToBeIncorporatedInTheQuery = lessThanOrEqualSign;	
					 postedToDate = fieldValue;
					 setToDate(fieldValue);
					 dateFieldExistsFlag=true;
				 }else if (fieldName.equals("fromDate")) {
					 actualSignToBeIncorporatedInTheQuery = greaterThanOrEqualSign;	 
					 transactionFromDate = fieldValue;
					 setFromDate(fieldValue);
					 dateFieldExistsFlag=true;
				 } else if (fieldName.equals("toDate")) {
					 actualSignToBeIncorporatedInTheQuery = lessThanOrEqualSign;	
					 transactionToDate = fieldValue;
					 setToDate(fieldValue);
					 dateFieldExistsFlag=true;
				 }
				 if (valueContainsMultipleValue)
				 {
					 whereClauseForQuery.append(actualFieldNameInDatabase) ;
					 whereClauseForQuery.append(inQueryString);
					 continue;
				 }
				 if (dataTypeFromValue.equalsIgnoreCase("String"))
				 {	 
					 whereClauseForQuery.append(actualFieldNameInDatabase+"="+"'"+fieldValue+"'");
				 } else if (dataTypeFromValue.equalsIgnoreCase("Number")) {
					 whereClauseForQuery.append(actualFieldNameInDatabase+"="+Long.parseLong(fieldValue));

				 } else if (dataTypeFromValue.equalsIgnoreCase("Date")) {
					 whereClauseForQuery.append(actualFieldNameInDatabase+ actualSignToBeIncorporatedInTheQuery+" TO_DATE('"+fieldValue+"','"+dateFormat+"')");
				 } 
			 }
		 }

		 if (dateFieldExistsFlag && fromDate != null && toDate != null) {
			 if (plazaOrLaneExistsFlag) {
				 whereClauseForQuery.append(" AND TO_DATE('"+toDate+"','DD-MM-YYYY')-TO_DATE('"+fromDate+"','DD-MM-YYYY') < "+TransactionSearchConstants.DATE_RANCE_FOR_PLAZA_LANE_SEARCH);
				 //whereClauseForQuery = whereClauseForQuery+" AND TO_DATE('"+toDate+"','DD-MM-YYYY')-TO_DATE('"+fromDate+"','DD-MM-YYYY') <= "+TransactionSearchConstants.DATE_RANCE_FOR_PLAZA_LANE_SEARCH;
			 } else {
				 whereClauseForQuery.append(" AND TO_DATE('"+toDate+"','DD-MM-YYYY')-TO_DATE('"+fromDate+"','DD-MM-YYYY') < "+TransactionSearchConstants.DATE_RANGE_FOR_OTHER_FIELDS_OTHER_THAN_PLAZA_LANE);
				 //whereClauseForQuery = whereClauseForQuery+" AND TO_DATE('"+toDate+"','DD-MM-YYYY')-TO_DATE('"+fromDate+"','DD-MM-YYYY') <= "+TransactionSearchConstants.DATE_RANGE_FOR_OTHER_FIELDS_OTHER_THAN_PLAZA_LANE;
			 }
		 }
		 logger.info("Class Name : "+this.getClass()+" Method Name : finalWhereClauseBuildForBuildQuery "+ " Started...STEP 2");


		 if (startingWhereClauseCounter > 1) {
			 //	whereClauseForQuery += ";";
		 }

		 logger.info(" whereClauseQuery = "+whereClauseForQuery);
		 logger.info("Class Name : "+this.getClass()+" Method Name : finalWhereClauseBuildForBuildQuery "+ " Ended...");

		 return whereClauseForQuery;  

	}

	// 26.07.2022 started
	public StringBuilder finalWhereClauseBuildForGlobalQueryFromView(TransactionSearchGlobalRequest transactionSearchGlobalRequest) throws TPMSGlobalException{
		logger.info("Class Name : "+this.getClass()+" Method Name : finalWhereClauseBuildForBuildQuery "+ " Started...");
		String methodName = "finalWhereClauseBuildForGlobalQuery";
		String fieldName = "";
		String fieldValue = "";
		//String whereClauseForQuery = "WHERE LEFT(ETC_ACCOUNT_ID,LENGTH(ETC_ACCOUNT_ID)-1) = "+transactionSearchRequest.getAccountNumber();	
		StringBuilder whereClauseForQuery = new StringBuilder();
//		whereClauseForQuery.append("WHERE ");	
		if (transactionSearchGlobalRequest.getAgencyShortName()!= null && (!transactionSearchGlobalRequest.getAgencyShortName().isBlank())) {
			whereClauseForQuery.append( " PLAZA_AGENCY_ID = CRM.V_AGENCY.AGENCY_ID AND ");
			whereClauseForQuery.append(" CRM.V_AGENCY.AGENCY_SHORT_NAME = '"+transactionSearchGlobalRequest.getAgencyShortName()+"' AND ");
		}		

		int startingWhereClauseCounter = 0;
		TransactionSearchFilter transactionSearchFilter;
		String dataTypeFromValue="";
		String equalSign = " = ";
		String greaterThanOrEqualSign = " >=";
		String lessThanOrEqualSign = " <=";
		String actualSignToBeIncorporatedInTheQuery ="=";
		String inOperator = " IN ";

		String transactionFromDate =" ";
		String transactionToDate =" ";
		String postedFromDate =" ";
		String postedToDate =" ";
		String actualFieldNameInDatabase=" ";
		String dateFormat =transactionSearchConstants.TRANSCATION_SEARCH_DATE_FORMAT;

		boolean plazaOrLaneExistsFlag = false;
		boolean dateFieldExistsFlag = false;
		boolean valueContainsMultipleValue = false;
		String[]   stringValues = null;
		Long[]     longValues = null;
		String[]   dateValues = null;
		int totalNumberOfMultipleValues = 0;
		StringTokenizer multipleValueTokenizer = null;
		StringBuilder inQueryString=null;

		logger.info("Class Name : "+this.getClass()+" Method Name : finalWhereClauseBuildForBuildQuery "+ " Started...STEP 1");

		Map<String,String> inputRequestMap = new HashMap<String,String>();
		if (transactionSearchGlobalRequest.getEtcAccountId() != null) {
			inputRequestMap.put("etcAccountId",transactionSearchGlobalRequest.getEtcAccountId());
		}
		if (transactionSearchGlobalRequest.getAccountNumber() != null) {
			inputRequestMap.put("accountNumber",transactionSearchGlobalRequest.getAccountNumber());

		}
		if (transactionSearchGlobalRequest.getCategory() != null) {
			inputRequestMap.put("category",transactionSearchGlobalRequest.getCategory());
		}
		 
		if (transactionSearchGlobalRequest.getPlateNumber() != null) {
			inputRequestMap.put("plateNumber",transactionSearchGlobalRequest.getPlateNumber());
		}
		if (transactionSearchGlobalRequest.getTransactionFromDate() != null) {
			inputRequestMap.put("transactionFromDate",transactionSearchGlobalRequest.getTransactionFromDate());
		}
		if (transactionSearchGlobalRequest.getTransactionToDate() != null) {
			inputRequestMap.put("transactionToDate",transactionSearchGlobalRequest.getTransactionToDate());
		}
		if (transactionSearchGlobalRequest.getPostedFromDate() != null) {
			inputRequestMap.put("postedFromDate",transactionSearchGlobalRequest.getPostedFromDate());
		}
		if (transactionSearchGlobalRequest.getPostedToDate() != null) {
			inputRequestMap.put("postedToDate",transactionSearchGlobalRequest.getPostedToDate());
		}
		if (transactionSearchGlobalRequest.getPlazaId() != null) {
			inputRequestMap.put("plazaId",transactionSearchGlobalRequest.getPlazaId());
		}
		if (transactionSearchGlobalRequest.getAgencyShortName() != null) {
			inputRequestMap.put("agencyShortName",transactionSearchGlobalRequest.getAgencyShortName());
		}

		if (transactionSearchGlobalRequest.getStatus() != null) {
			inputRequestMap.put("status",transactionSearchGlobalRequest.getStatus());
		}
		if (transactionSearchGlobalRequest.getType() != null) {
			inputRequestMap.put("type",transactionSearchGlobalRequest.getType());
		}
		if (transactionSearchGlobalRequest.getEscLevel() != null) {
			inputRequestMap.put("escLevel",transactionSearchGlobalRequest.getEscLevel());
		}
		if (transactionSearchGlobalRequest.getInvoiceNo() != null) {
			inputRequestMap.put("invoiceNo",transactionSearchGlobalRequest.getInvoiceNo());
		}
		 if (transactionSearchGlobalRequest.getFromDate() != null) {
			 inputRequestMap.put("fromDate",transactionSearchGlobalRequest.getFromDate());
		 }
		 if (transactionSearchGlobalRequest.getToDate() != null) {
			 inputRequestMap.put("toDate",transactionSearchGlobalRequest.getToDate());
		 }
		 //Added by Leena
		 if(transactionSearchGlobalRequest.getLaneId() !=null) {
			 inputRequestMap.put("laneId",transactionSearchGlobalRequest.getLaneId());
		 }
		 if(transactionSearchGlobalRequest.getLaneTxId() !=null) {
			 inputRequestMap.put("laneTxId",transactionSearchGlobalRequest.getLaneTxId());
		 }
		 if(transactionSearchGlobalRequest.getPlateState() !=null) {
			 inputRequestMap.put("plateState",transactionSearchGlobalRequest.getPlateState());
		 }
		 if(transactionSearchGlobalRequest.getExtReferenceNo() !=null) {
			 inputRequestMap.put("extReferenceNo",transactionSearchGlobalRequest.getExtReferenceNo());
		 }
		 if(transactionSearchGlobalRequest.getTransponderNumber() !=null) {
			 inputRequestMap.put("transponderNumber",transactionSearchGlobalRequest.getTransponderNumber());
		 }

		 if (transactionSearchGlobalRequest.getPage()!= null) {
			 if (transactionSearchGlobalRequest.getPage()<0 || transactionSearchGlobalRequest.getPage()==0) {
				 TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="Page Number should be positive and non-zero.";
				 throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
			 }
		 }
		 if (transactionSearchGlobalRequest.getSize()!= null) {
			 if (transactionSearchGlobalRequest.getSize()<0) {
				 TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="Size should be positive";
				 throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
			 }
		 }
		 if (transactionSearchGlobalRequest.getSortBy()!= null) {
			 if (!(transactionSearchGlobalRequest.getSortBy().equals("ASC") || 
				   transactionSearchGlobalRequest.getSortBy().equals("DESC"))) {
				 TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="Sort By should be either ASC or DESC "+transactionSearchGlobalRequest.getSortBy();
				 throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
			 } 	
		 }
		 if (!(transactionSearchValidation.validateAllGlobalFieldNames(transactionSearchGlobalRequest,inputRequestMap))) {
			 TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="Transaction Search Invalid Field Names specified";
			 throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
		 }

		 if (!(transactionSearchValidation.validateAllRequestMemberNameGlobal(null,inputRequestMap))){
			 logger.info("Class Name : "+this.getClass()+" Method Name : finalWhereClauseBuildForBuildQuery "+ " VALIDATION FAILED...");
			 TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="Transaction Search Validation Failed";
			 throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
		 }

		 Object[] objArray = inputRequestMap.keySet().toArray();
		 for (int i =0;i<objArray.length;i++) {
			 fieldName = (String) objArray[i];
			 fieldValue = (String) inputRequestMap.get(fieldName);
			 if (fieldName.equals("agencyShortName") || fieldName.equals("page") || fieldName.equals("size")|| fieldName.equals("sortType")) {
				 continue;
			 }
			 dataTypeFromValue=transactionSearchUtility.determineDataTypeFromGlobalFieldName(fieldName);
			 valueContainsMultipleValue=false;
			 inQueryString = new StringBuilder();		
			 if (fieldValue != null) {

				 if (fieldValue.startsWith("[")) {
					 inQueryString.append(inOperator);
					 inQueryString.append(" ( ");
					 inQueryString.append(fieldValue.substring(1,fieldValue.length()-1));
					 inQueryString.append(" ) ");
					 valueContainsMultipleValue=true;
				 }
			 }
			 if (fieldValue != null && fieldValue.length()>0) {
				 if (startingWhereClauseCounter != 0) whereClauseForQuery.append(" AND ");
				 startingWhereClauseCounter++;
				 actualFieldNameInDatabase = transactionSearchUtility.findActualFieldNameInDatabaseForGlobalQuery(fieldName);
				 logger.info("Class : "+ this.getClass()+" MethodName : finalWhereClauseBuildForBuildQuery "+" fieldName : "+fieldName+" fieldValue : "+fieldValue+" actualFieldNameInDatabase : "+actualFieldNameInDatabase);
				 actualSignToBeIncorporatedInTheQuery = "="; 
				 if (fieldName.equals("transactionFromDate")) {
					 actualSignToBeIncorporatedInTheQuery = greaterThanOrEqualSign;	 
					 transactionFromDate = fieldValue;
					 setFromDate(fieldValue);
					 dateFieldExistsFlag=true;
				 } else if (fieldName.equals("transactionToDate")) {
					 actualSignToBeIncorporatedInTheQuery = lessThanOrEqualSign;	
					 transactionToDate = fieldValue;
					 setToDate(fieldValue);
					 dateFieldExistsFlag=true;
				 } else if (fieldName.equals("postedFromDate")) {
					 actualSignToBeIncorporatedInTheQuery = greaterThanOrEqualSign;	 
					 postedFromDate = fieldValue;
					 setFromDate(fieldValue);
					 dateFieldExistsFlag=true;
				 } else if (fieldName.equals("postedToDate")) {
					 actualSignToBeIncorporatedInTheQuery = lessThanOrEqualSign;	
					 postedToDate = fieldValue;
					 setToDate(fieldValue);
					 dateFieldExistsFlag=true;
				 }else if (fieldName.equals("fromDate")) {
					 actualSignToBeIncorporatedInTheQuery = greaterThanOrEqualSign;	 
					 transactionFromDate = fieldValue;
					 setFromDate(fieldValue);
					 dateFieldExistsFlag=true;
				 } else if (fieldName.equals("toDate")) {
					 actualSignToBeIncorporatedInTheQuery = lessThanOrEqualSign;	
					 transactionToDate = fieldValue;
					 setToDate(fieldValue);
					 dateFieldExistsFlag=true;
				 }
				 if (valueContainsMultipleValue)
				 {
					 whereClauseForQuery.append(actualFieldNameInDatabase) ;
					 whereClauseForQuery.append(inQueryString);
					 continue;
				 }
				 if (dataTypeFromValue.equalsIgnoreCase("String"))
				 {	 
					 whereClauseForQuery.append(actualFieldNameInDatabase+"="+"'"+fieldValue+"'");
				 } else if (dataTypeFromValue.equalsIgnoreCase("Number")) {
					 whereClauseForQuery.append(actualFieldNameInDatabase+"="+Long.parseLong(fieldValue));

				 } else if (dataTypeFromValue.equalsIgnoreCase("Date")) {
					 whereClauseForQuery.append(actualFieldNameInDatabase+ actualSignToBeIncorporatedInTheQuery+" TO_DATE('"+fieldValue+"','"+dateFormat+"')");
				 } 
			 }
		 }

		 if (dateFieldExistsFlag && fromDate != null && toDate != null) {
			 if (plazaOrLaneExistsFlag) {
				 whereClauseForQuery.append(" AND TO_DATE('"+toDate+"','DD-MM-YYYY')-TO_DATE('"+fromDate+"','DD-MM-YYYY') < "+TransactionSearchConstants.DATE_RANCE_FOR_PLAZA_LANE_SEARCH);
				 //whereClauseForQuery = whereClauseForQuery+" AND TO_DATE('"+toDate+"','DD-MM-YYYY')-TO_DATE('"+fromDate+"','DD-MM-YYYY') <= "+TransactionSearchConstants.DATE_RANCE_FOR_PLAZA_LANE_SEARCH;
			 } else {
				 whereClauseForQuery.append(" AND TO_DATE('"+toDate+"','DD-MM-YYYY')-TO_DATE('"+fromDate+"','DD-MM-YYYY') < "+TransactionSearchConstants.DATE_RANGE_FOR_OTHER_FIELDS_OTHER_THAN_PLAZA_LANE);
				 //whereClauseForQuery = whereClauseForQuery+" AND TO_DATE('"+toDate+"','DD-MM-YYYY')-TO_DATE('"+fromDate+"','DD-MM-YYYY') <= "+TransactionSearchConstants.DATE_RANGE_FOR_OTHER_FIELDS_OTHER_THAN_PLAZA_LANE;
			 }
		 }
		 logger.info("Class Name : "+this.getClass()+" Method Name : finalWhereClauseBuildForBuildQuery "+ " Started...STEP 2");


		 if (startingWhereClauseCounter > 1) {
			 //	whereClauseForQuery += ";";
		 }

		 logger.info(" whereClauseQuery = "+whereClauseForQuery);
		 logger.info("Class Name : "+this.getClass()+" Method Name : finalWhereClauseBuildForBuildQuery "+ " Ended...");

		 return whereClauseForQuery;  

	}


	// 26.07.2022 ended



	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getFromDate() {
		return this.fromDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getToDate() {
		return this.toDate;
	}
	public TransactionApiResponse buildFinalResponse(List<TransactionResponsePOJO> transactionReponsePOJOList,Object requestObject) {
		TransactionApiResponse transactionApiResponse = new TransactionApiResponse();

		TransactionResponsePOJO transactionResponsePOJO;
		List<TransactionResponsePOJO> pageWiseTransactionReponsePOJOList = null;
		List<PageBreakPOJO> pageWisePageBreakPOJOList = new ArrayList<PageBreakPOJO>();
		int totalNumberOfRecordsInPage=0;
		TransactionSearchRequest transactionSearchRequest;
		TransactionSearchGlobalRequest transactionSearchGlobalRequest;
		if (requestObject instanceof TransactionSearchRequest){
			transactionSearchRequest = (TransactionSearchRequest) requestObject;
		} else if (requestObject instanceof TransactionSearchGlobalRequest) {
			transactionSearchGlobalRequest = (TransactionSearchGlobalRequest) requestObject;   
		}

		int currentRecordCounter = 1, pageCounter=0;
		boolean pageObjectWrittenFlag = false;
		for (int i = 0;i<transactionReponsePOJOList.size();i++) {
			if (currentRecordCounter == 1) {
				pageObjectWrittenFlag = false;
				pageCounter++;
				pageBreakPOJO = new PageBreakPOJO();
				pageWiseTransactionReponsePOJOList = new ArrayList<TransactionResponsePOJO>();
			}
			transactionResponsePOJO = (TransactionResponsePOJO) transactionReponsePOJOList.get(i);
			pageWiseTransactionReponsePOJOList.add(transactionResponsePOJO);
			pageObjectWrittenFlag = false;
			currentRecordCounter++;
			if (currentRecordCounter > totalNumberOfRecordsInPage) {
				pageBreakPOJO.setPageNo(pageCounter);
				pageBreakPOJO.setResponseList(pageWiseTransactionReponsePOJOList);
				pageWisePageBreakPOJOList.add(pageBreakPOJO);
				currentRecordCounter = 1;
				pageObjectWrittenFlag = true;
			}
		}
		if (!pageObjectWrittenFlag)
		{ 
			pageBreakPOJO.setPageNo(pageCounter);
			pageBreakPOJO.setResponseList(pageWiseTransactionReponsePOJOList);
			pageWisePageBreakPOJOList.add(pageBreakPOJO);

		}
		transactionApiResponse.setResponseList(pageWisePageBreakPOJOList);
		transactionApiResponse.setTotalNumberOfRecords(transactionReponsePOJOList!=null ?transactionReponsePOJOList.size():0);
		transactionApiResponse.setTotalPageNumbers(pageCounter);
		logger.info("Class Name : "+this.getClass()+" Method Name : startProcess successfully ended");


		return transactionApiResponse;
	}
	public TransactionSearchApiResponse buildResponseAsPerCRM(List<TransactionResponsePOJO> transactionReponsePOJOList,Object requestObject) {
		String methodName = "buildResponseAsPerCRM";
		logger.info("Class Name : "+this.getClass()+" Method Name : "+ methodName +" successfully started");

		TransactionSearchApiResponse transactionSearchApiResponse = new TransactionSearchApiResponse();
		List<TransactionResponse> transactionResponses = new ArrayList<TransactionResponse>();
		TransactionResponse transactionResponse = new TransactionResponse();
		transactionResponse.setContent(transactionReponsePOJOList);


		if (transactionReponsePOJOList != null) {
			transactionSearchApiResponse.setStatus(true);
			transactionSearchApiResponse.setResult(transactionResponse);
			transactionSearchApiResponse.setMessage(TransactionSearchConstants.RESPONSE_SUCCESSED_MESSAGE);
			transactionSearchApiResponse.setErrors(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE);
			transactionSearchApiResponse.setFieldErrors(TransactionSearchConstants.RESPONSE_FIELD_ERRORS);
		} else {
			transactionSearchApiResponse.setStatus(false);
			transactionSearchApiResponse.setResult(null);
			transactionSearchApiResponse.setMessage(TransactionSearchConstants.RESPONSE_FAILED_MESSAGE);
			transactionSearchApiResponse.setErrors(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE);
			transactionSearchApiResponse.setFieldErrors(TransactionSearchConstants.RESPONSE_FIELD_ERRORS);

		}
		logger.info("Class Name : "+this.getClass()+" Method Name : "+ methodName +" successfully ended");
		return transactionSearchApiResponse;
	}

	public void setExceptionResponse(TransactionSearchApiResponse transactionSearchApiResponse) {
		String methodName = "setExceptionResponse";
		logger.info("Class Name : "+this.getClass()+" Method Name : "+ methodName +" successfully started");
		transactionSearchApiResponse.setStatus(false);	
		transactionSearchApiResponse.setResult(null);
		transactionSearchApiResponse.setMessage(TransactionSearchConstants.RESPONSE_FAILED_MESSAGE);
		transactionSearchApiResponse.setErrors(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE);
		transactionSearchApiResponse.setFieldErrors(TransactionSearchConstants.RESPONSE_FIELD_ERRORS);
		logger.info("Class Name : "+this.getClass()+" Method Name : "+ methodName +" successfully ended");

	}
	public void setRequestBodyMapToRequestValues(TransactionSearchGlobalRequest transactionSearchGlobalRequest,Map<String,String> allParamsMap) {
		String methodName = "setRequestBodyMapToRequestValues";
		logger.info("Class Name :"+this.getClass()+" Method Name : "+ methodName + "successfully started");

		Set allKeys = allParamsMap.keySet();
		Iterator itr = allKeys.iterator();

		String currentParameter="";
		String currentValue="";
		//StringBuilder fieldErrors = new StringBuilder();
		boolean fieldErrorFlag = false;
		List <String> fieldErrorList = new ArrayList<String>();
		while (itr.hasNext()) {
			currentParameter=(String) itr.next();
			logger.info("TRANSACTION SEARCH GLOBAL REQUEST : Request Parameter : "+currentParameter);
			currentValue = allParamsMap.get(currentParameter);
			switch(currentParameter) {
			case "etcAccountId": if (currentValue != null) transactionSearchGlobalRequest.setEtcAccountId(currentValue); break;
			case "category": if (currentValue != null) transactionSearchGlobalRequest.setCategory(currentValue); break;
			case "plateNumber": if (currentValue != null) transactionSearchGlobalRequest.setPlateNumber(currentValue); break;
			case "transponderNumber": if (currentValue != null) transactionSearchGlobalRequest.setTransponderNumber(currentValue); break;
			case "postedFromDate": if (currentValue != null) transactionSearchGlobalRequest.setPostedFromDate(currentValue); break;
			case "postedToDate": if (currentValue != null) transactionSearchGlobalRequest.setPostedToDate(currentValue); break;
			case "transactionFromDate": if (currentValue != null) transactionSearchGlobalRequest.setTransactionFromDate(currentValue); break;
			case "transactionToDate": if (currentValue != null) transactionSearchGlobalRequest.setTransactionToDate(currentValue); break;
			case "plazaId": if (currentValue != null) transactionSearchGlobalRequest.setPlazaId(currentValue); break;
			case "agencyShortName":if (currentValue != null)  transactionSearchGlobalRequest.setAgencyShortName(currentValue); break;
			case "sortType": if (currentValue != null) transactionSearchGlobalRequest.setSortType(currentValue); break;
			case "page" :if (currentValue != null) transactionSearchGlobalRequest.setPage(Integer.parseInt(currentValue)); break;
			case "size" :if (currentValue != null) transactionSearchGlobalRequest.setSize(Integer.parseInt(currentValue)); break;
			case "invoiceNo": if (currentValue != null) transactionSearchGlobalRequest.setInvoiceNo(currentValue); break;
			case "status": if (currentValue != null) transactionSearchGlobalRequest.setStatus(currentValue); break;
			case "type": if (currentValue != null) transactionSearchGlobalRequest.setType(currentValue); break;
			case "escLevel": if (currentValue != null) transactionSearchGlobalRequest.setEscLevel(currentValue); break;
			case "fromDate": if (currentValue != null) transactionSearchGlobalRequest.setFromDate(currentValue); break;
			case "toDate": if (currentValue != null) transactionSearchGlobalRequest.setToDate(currentValue); break;
			//New Parameters
			case "accountNumber": if(currentValue!=null) transactionSearchGlobalRequest.setAccountNumber(currentValue);break;
			case "plateState": if(currentValue!=null) transactionSearchGlobalRequest.setPlateState(currentValue);break;
			case "laneTxId": if(currentValue!=null) transactionSearchGlobalRequest.setLaneTxId(currentValue);break;
			case "extReferenceNo": if(currentValue!=null) transactionSearchGlobalRequest.setExtReferenceNo(currentValue);break;
			case "laneId": if(currentValue!=null) transactionSearchGlobalRequest.setLaneId(currentValue);break;
			case "sortBy": if(currentValue!=null) transactionSearchGlobalRequest.setSortBy(currentValue);break;

			default : fieldErrorFlag=true;
			fieldErrorList.add(currentParameter);
			logger.info("NO SUCH ELEMENT "+currentParameter);
			}
		}
		if (fieldErrorFlag) {
			TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="Invalid Field Names Specified: ";
			TransactionSearchConstants.RESPONSE_FIELD_ERRORS= fieldErrorList;
			logger.info("FIELD ERRORS : "+fieldErrorList);
			throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE+TransactionSearchConstants.RESPONSE_FIELD_ERRORS,this.getClass().getName(),methodName);
		}
		logger.info("Class Name :"+this.getClass()+" Method Name : "+ methodName + "successfully ended");

	}

}
