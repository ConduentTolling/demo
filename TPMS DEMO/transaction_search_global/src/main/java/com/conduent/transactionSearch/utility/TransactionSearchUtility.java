package com.conduent.transactionSearch.utility;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.conduent.transactionSearch.exception.TPMSGlobalException;
import com.conduent.transactionSearch.constants.TransactionSearchConstants;
import com.conduent.transactionSearch.validation.TransactionSearchValidation;

public class TransactionSearchUtility {
	private Logger logger = LoggerFactory.getLogger(TransactionSearchUtility.class);
       String className = this.getClass().getName();
	   @Autowired
	   TransactionSearchConstants transactionSearchConstants;
	   
	   @Autowired
	   TransactionSearchValidation transactionSearchValidation;
	   
	   @Autowired
	   TPMSGlobalException tPMSGlobalException;
	   
	   public String findActualFieldNameInDatabase(String javaObjectFieldName) {
     	   logger.info("Class Name : "+this.getClass()+" Method Name : findActualFieldNameInDatabase "+ " Started..."+"Parameter :"+javaObjectFieldName);
		   String methodName = "findActualFieldNameInDatabase";	  
     	   
     	   if (javaObjectFieldName == null) {
			    	  return null;
			  }
			   if (!transactionSearchValidation.validateFilterFieldNames(javaObjectFieldName)) {
				   return null;
			   }      
		      Map<String,String> fieldNameMap = new HashMap<String,String>();
		      fieldNameMap.put("accountId",transactionSearchConstants.ETC_ACCOUNT_ID);
		      fieldNameMap.put("accountNumber",transactionSearchConstants.ETC_ACCOUNT_ID);
		      fieldNameMap.put("transponderNumber",transactionSearchConstants.TRANSPONDER_NUMBER);
		      fieldNameMap.put("deviceNo",transactionSearchConstants.TRANSPONDER_NUMBER);
		      fieldNameMap.put("transactionFromDate",transactionSearchConstants.TX_DATE);
		      fieldNameMap.put("transactionToDate",transactionSearchConstants.TX_DATE);
		      fieldNameMap.put("postedFromDate",transactionSearchConstants.POSTED_DATE);
		      fieldNameMap.put( "postedToDate",transactionSearchConstants.POSTED_DATE);
		      
		      fieldNameMap.put("transactionStartDate",transactionSearchConstants.TX_TIMESTAMP);
		      fieldNameMap.put("transactionEndDate",transactionSearchConstants.TX_TIMESTAMP);
		      fieldNameMap.put("postedStartDate",transactionSearchConstants.POSTED_DATE);
		      fieldNameMap.put( "postedEndDate",transactionSearchConstants.POSTED_DATE);
		      fieldNameMap.put("plateNumber",transactionSearchConstants.PLATE_NUMBER);
		      fieldNameMap.put( "plateState",transactionSearchConstants.PLATE_STATE);
		      fieldNameMap.put("transponderNumber",transactionSearchConstants.TRANSPONDER_NUMBER);
		      fieldNameMap.put( "laneTxId",transactionSearchConstants.LANE_TX_ID);
		      fieldNameMap.put( "extReferenceNo",transactionSearchConstants.TX_EXTERN_REF_NO);
		      fieldNameMap.put( "plazaId",transactionSearchConstants.PLAZA_ID);
		      fieldNameMap.put( "laneId",transactionSearchConstants.LANE_ID);
		      fieldNameMap.put( "category",transactionSearchConstants.CATEGORY);
		      fieldNameMap.put("etcAccountId",transactionSearchConstants.ETC_ACCOUNT_ID);
		      
		      if (fieldNameMap.get(javaObjectFieldName)!= null)
		      {
		    	  logger.info("Retrieved Field Name from database "+fieldNameMap.get(javaObjectFieldName));
			      logger.info("Class Name : "+this.getClass()+" Method Name b: findActualFieldNameInDatabase "+ " Ended...");
				
		    	  return fieldNameMap.get(javaObjectFieldName);
		      }
		      logger.info("Retrieved Field Name from database is null");
		      logger.info("Class Name : "+this.getClass()+" Method Name : findActualFieldNameInDatabase "+ " Ended...");
		      TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="No corresponding Database Column Name for field name :"+javaObjectFieldName;				
		      throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
				
//		      return "";
	   }
	   public String findActualFieldNameInDatabaseForGlobalQuery(String javaObjectFieldName) {
     	   logger.info("Class Name : "+this.getClass()+" Method Name : findActualFieldNameInDatabaseForGlobalQuery "+ " Started..."+"Parameter :"+javaObjectFieldName);
		   String methodName = "findActualFieldNameInDatabaseForGlobalQuery";
     	   if (javaObjectFieldName == null) {
			    	  return null;
			  }
			      
			   if (!transactionSearchValidation.validateGlobalFieldNames(javaObjectFieldName)) {
				   return null;
			   }      
		      Map<String,String> fieldNameMap = new HashMap<String,String>();
		      fieldNameMap.put("accountId",transactionSearchConstants.ETC_ACCOUNT_ID);
		      fieldNameMap.put("accountNumber",transactionSearchConstants.ACCOUNT_NO);
		      fieldNameMap.put("transponderNumber",transactionSearchConstants.TRANSPONDER_NUMBER);
		      fieldNameMap.put("deviceNo",transactionSearchConstants.TRANSPONDER_NUMBER);
		      fieldNameMap.put("transactionFromDate",transactionSearchConstants.TX_DATE);
		      fieldNameMap.put("transactionToDate",transactionSearchConstants.TX_DATE);
		      fieldNameMap.put("postedFromDate",transactionSearchConstants.POSTED_DATE);
		      fieldNameMap.put( "postedToDate",transactionSearchConstants.POSTED_DATE);
		      
		      fieldNameMap.put("transactionStartDate",transactionSearchConstants.TX_TIMESTAMP);
		      fieldNameMap.put("transactionEndDate",transactionSearchConstants.TX_TIMESTAMP);
		      fieldNameMap.put("postedStartDate",transactionSearchConstants.POSTED_DATE);
		      fieldNameMap.put("postedEndDate",transactionSearchConstants.POSTED_DATE);
		      fieldNameMap.put("plateNumber",transactionSearchConstants.PLATE_NUMBER);
		      fieldNameMap.put("plateState",transactionSearchConstants.PLATE_STATE);
		      fieldNameMap.put("transponderNumber",transactionSearchConstants.TRANSPONDER_NUMBER);
		      fieldNameMap.put("laneTxId",transactionSearchConstants.LANE_TX_ID);
		      fieldNameMap.put("extReferenceNo",transactionSearchConstants.TX_EXTERN_REF_NO);
		      fieldNameMap.put("plazaId",transactionSearchConstants.EXIT_EXTERN_PLAZA_ID);
		      fieldNameMap.put("laneId",transactionSearchConstants.EXIT_EXTERN_LANE_ID);
		      fieldNameMap.put("category",transactionSearchConstants.CATEGORY);
		      fieldNameMap.put("etcAccountId",transactionSearchConstants.ETC_ACCOUNT_ID);
		      fieldNameMap.put("txStatus",transactionSearchConstants.ETC_TX_STATUS);
		      fieldNameMap.put("status",transactionSearchConstants.STATUS);
		      fieldNameMap.put("type",transactionSearchConstants.TYPE);
		      fieldNameMap.put("escLevel",transactionSearchConstants.ESCALATION_LEVEL);
		      fieldNameMap.put("invoiceNo",transactionSearchConstants.INVOICE_NUMBER);
		      fieldNameMap.put("fromDate",transactionSearchConstants.FROM_DATE);
		      fieldNameMap.put("toDate",transactionSearchConstants.TO_DATE);
		      fieldNameMap.put("agencyName",transactionSearchConstants.AGENCY_NAME );
		      if (fieldNameMap.get(javaObjectFieldName)!= null)
		      {
		    	  logger.info("Retrieved Field Name from database "+fieldNameMap.get(javaObjectFieldName));
			      logger.info("Class Name : "+this.getClass()+" Method Name b: findActualFieldNameInDatabaseForGlobalQuery "+ " Ended...");
				
		    	  return fieldNameMap.get(javaObjectFieldName);
		      }
		      logger.info("Retrieved Field Name from database is null");
		      logger.info("Class Name : "+this.getClass()+" Method Name : findActualFieldNameInDatabaseForGlobalQuery "+ " Ended...");
              TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="No corresponding Database Column Name for field name :"+javaObjectFieldName;				
		      throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
              //            return "";
	   }

	   public String findActualSortFieldNameInDatabase(String fieldName) {
		   logger.info("Class Name : "+this.getClass()+" Method Name : findActualSortFieldNameInDatabase "+ " Started...");
			String methodName = "findActualSortFieldNameInDatabase";
		    String[][] fieldNameMapArray = new String[][] {
		    	
		    	{ "etcAccountId"     , transactionSearchConstants.ETC_ACCOUNT_ID },
		    	{ "plateNumber"      , transactionSearchConstants.PLATE_NUMBER},
		    	{ "plateState"       , transactionSearchConstants.PLATE_STATE},
		    	{ "txTimeStamp"      , transactionSearchConstants.TX_TIMESTAMP},
		    	{ "entryTxTimeStamp" , transactionSearchConstants.ENTRY_TX_TIMESTAMP},
		    	{ "updateTs"         , transactionSearchConstants.UPDATE_TS},
		    	{ "postedDate"       , transactionSearchConstants.POSTED_DATE},
		    	{ "accountId"        , transactionSearchConstants.ACCOUNT_ID},
		    	{ "etcTxStatus"      , transactionSearchConstants.ETC_TX_STATUS},
		    	{ "laneTxId"         , transactionSearchConstants.LANE_TX_ID},
		    	{ "txExternRefNo"    , transactionSearchConstants.TX_EXTERN_REF_NO},
		    	{ "entryPlazaId"     , transactionSearchConstants.ENTRY_PLAZA_ID},
		    	{ "plazaId"          , transactionSearchConstants.PLAZA_ID},
		    	{ "laneId"           , transactionSearchConstants.LANE_ID},
		    	{ "entryLaneId"      , transactionSearchConstants.ENTRY_LANE_ID},
		    	{ "plazaAgencyId"    , transactionSearchConstants.PLAZA_AGENCY_ID},
		    	{ "actualClass"      , transactionSearchConstants.ACTUAL_CLASS},
		    	{ "unrealizedAmount" , transactionSearchConstants.UNREALIZED_AMOUNT},
		    	{ "discountedAmount" , transactionSearchConstants.DISCOUNTED_AMOUNT},
		    	{"transponderNumber" , transactionSearchConstants.TRANSPONDER_NUMBER},
		    	{"transactionDateTime",transactionSearchConstants.TX_TIMESTAMP},
		    	{"deviceNo" , transactionSearchConstants.TRANSPONDER_NUMBER},
		    	{"category",transactionSearchConstants.CATEGORY},
		    	{"invoicdNo",transactionSearchConstants.INVOICE_NUMBER},
		    	{"status",transactionSearchConstants.STATUS},
		    	{"type",transactionSearchConstants.TYPE},
		    	{"escLevel",transactionSearchConstants.ESCALATION_LEVEL},
		    	{"fromDate",transactionSearchConstants.FROM_DATE},
		    	{"toDate",transactionSearchConstants.TO_DATE},
			    	

		    } ;
		    
		    Map<String,String> fieldNameMap = new HashMap<String,String>();
		        
		    for (int i=0;i<fieldNameMapArray.length;i++) {
		    	  fieldNameMap.put(fieldNameMapArray[i][0],fieldNameMapArray[i][1]);
			}
		    
		    if (fieldNameMap.get(fieldName)!= null)
		    {
		    	logger.info("Retrieved Sort Field Name from database "+fieldNameMap.get(fieldName));
			    logger.info("Class Name : "+this.getClass()+" Method Name : findActualSortFieldNameInDatabase "+ " Ended...");
				
		    	  return fieldNameMap.get(fieldName);
		    }
		    logger.info("Retrieved Sort Field Name from database is null");
		    logger.info("Class Name : "+this.getClass()+" Method Name : findActualSortFieldNameInDatabase "+ " Ended...");
		    TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="No corresponding Database Column Name for sort field name :"+fieldName;				
		    throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);  	
		    //return "";
	   }
	   public String determineDataTypeFromFieldName(String fieldName) {
		   if (fieldName == null) {
				return "NULL";
			}
		   
		  // VALID_FILTER_FIELD_NAMES_WITH_DATA_TYPES
	        
		   for (int i=0;i<transactionSearchConstants.VALID_FILTER_FIELD_NAMES_WITH_DATA_TYPES.length;i++) {
			   if (transactionSearchConstants.VALID_FILTER_FIELD_NAMES_WITH_DATA_TYPES[i][0].equalsIgnoreCase(fieldName)) {
				   return transactionSearchConstants.VALID_FILTER_FIELD_NAMES_WITH_DATA_TYPES[i][1];
			   }   
		   }
		   TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="No Data Type specified for the field Name :"+fieldName;				
		     
	       return "";
		}
	   public String determineDataTypeFromGlobalFieldName(String fieldName) {
		   String methodName = "determineDataTypeFromGlobalFieldName";
		   if (fieldName == null) {
				return "NULL";
			}
		   
		  // VALID_FILTER_FIELD_NAMES_WITH_DATA_TYPES
	        
		   for (int i=0;i<transactionSearchConstants.VALID_GLOBAL_FIELD_NAMES_WITH_DATA_TYPES.length;i++) {
			   if (transactionSearchConstants.VALID_GLOBAL_FIELD_NAMES_WITH_DATA_TYPES[i][0].equalsIgnoreCase(fieldName)) {
				   return transactionSearchConstants.VALID_GLOBAL_FIELD_NAMES_WITH_DATA_TYPES[i][1];
			   }   
		   }
		   TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="No Data Type specified for the field Name :"+fieldName;	
		   throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
		}
	   public String determineDateFormatFromFieldValue(String fieldValue) {
		   String methodName = "determineDateFormatFromFieldValue";
		   boolean successfulDate ;
	        String[] dateFormatArray = new String[] {
	        		"DD-MM-YYYY","MM-DD-YYYY","YYYY-MM-DD","YYYY-DD-MM","DD/MM/YYYY","MM/DD/YYYY","YYYY/MM/DD","YYYY/DD/MM",
	        		"DD.MM.YYYY","MM.DD.YYYY","YYYY.MM.DD","YYYY.DD.MM","DDMMYYYY","MMDDYYYY","YYYYMMDD","YYYYDDMM"
	        };
	        for (int i=0;i<dateFormatArray.length;i++) {
	             successfulDate = transactionSearchValidation.dateValidation(fieldValue,dateFormatArray[i]);
		         if (successfulDate) return dateFormatArray[i];
	        }
		    
	        TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="No Matching Data Format for the field value :"+fieldValue;				
	        throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
		}


}
