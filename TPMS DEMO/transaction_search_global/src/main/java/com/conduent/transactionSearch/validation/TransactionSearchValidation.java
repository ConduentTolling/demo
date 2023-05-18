package com.conduent.transactionSearch.validation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.conduent.transactionSearch.constants.TransactionSearchConstants;
import com.conduent.transactionSearch.exception.TPMSGlobalException;
import com.conduent.transactionSearch.model.TransactionSearchFilter;
import com.conduent.transactionSearch.model.TransactionSearchGlobalRequest;
import com.google.common.collect.Iterators;


public class TransactionSearchValidation {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	
	
	@Autowired
	TransactionSearchConstants transactionSearchConstants;
	@Autowired
	TPMSGlobalException tPMSGlobalException;
	public boolean genericDateRangeCheck(String fromDate2, String toDate2,int rangeCheckInDays) throws TPMSGlobalException{
		String methodName = "genericDateRangeCheck";
		logger.info("Class Name : "+this.getClass()+" Method Name : genericDateRangeCheck "+ " Started...");

		if (fromDate2.isBlank())
		{
			return false;
		}

		if (toDate2.isBlank())
		{
			return true;
		}
		long numberOfDaysInBetween = findDifferenceInDates(fromDate2,toDate2,transactionSearchConstants.TRANSCATION_SEARCH_DATE_FORMAT);
		
		logger.info(" Number of Days In Between :"+numberOfDaysInBetween+" rangeCheckInDays : "+rangeCheckInDays)  ; 
		if (numberOfDaysInBetween > rangeCheckInDays)
		{
			TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=TransactionSearchConstants.RANGE_CHECK_MESSAGE+" Exceeds "+rangeCheckInDays+ " days.";
			throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
		}
		logger.info("Class Name : "+this.getClass()+" Method Name : genericDateRangeCheck "+ " Ended...");

		return true;
	}
	public long findDifferenceInDates(String fromDate2,String toDate2,String dateFormat)  throws TPMSGlobalException{
		String methodName = "findDifferenceInDates";
		long diff=0;
		try 
		{
		    Date firstDate = new Date();
		    Date secondDate = new Date();
		    //SimpleDateFormat sdf = new SimpleDateFormat(dateFormat,Locale.ENGLISH);
		    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			firstDate = sdf.parse(fromDate2);
			secondDate = sdf.parse(toDate2);
			long diffInMillis = Math.abs(secondDate.getTime()-firstDate.getTime());
			diff = TimeUnit.DAYS.convert(diffInMillis,TimeUnit.MILLISECONDS)+1;
			logger.info("date format :"+dateFormat);
			logger.info("DIFFERNECE IN DAYS INSIDE differenceInDates .."+diff+" FROM DATE : "+fromDate2 +" TO DATE : "+toDate2+" FIRST DATE :"+firstDate+" SECOND DATE "+secondDate+" diffInMills"+diffInMillis);
		
		} catch (ParseException pex) {
			TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="Error while parsing the dates"+pex.getMessage();
			logger.info("Error while parsing the dates : "+pex.getMessage());
			throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
		}
		return diff;

	}

	public boolean dateValidation(String fieldValue, String dateFormat) {
		DateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setLenient(false);
		try {
			sdf.parse(fieldValue);
			return true;
		} catch (ParseException ex) {
			return false;	
		}


	}

	public boolean validateAllFilterFieldNames(List<TransactionSearchFilter> transactionSearchFilterList) {
		String methodName = "validateAllFilterFieldNames";
		Iterator itr = transactionSearchFilterList.iterator();
		TransactionSearchFilter transactionSearchFilter;
		String iteratedFieldName = null;
		String iteratedFieldValue = null;
		logger.info("Class : "+ this.getClass()+"  Method Name : validateAllFilterFieldNames ....");
		List l = Arrays.asList(transactionSearchConstants.VALID_FILTER_FIELD_NAMES);
		boolean errorFlag = false;	
		while (itr.hasNext()) {
			transactionSearchFilter =  (TransactionSearchFilter) itr.next();	
			iteratedFieldName = transactionSearchFilter.getFieldName();
			iteratedFieldValue = transactionSearchFilter.getFieldValue();
			if (iteratedFieldName.equals("page") || iteratedFieldName.equals("size") || iteratedFieldName.equals("sortType")  )
			{
				continue;
			}	

			if (iteratedFieldName != null) { 
				if (!( l.contains(iteratedFieldName))) {
					TransactionSearchConstants.RESPONSE_FIELD_ERRORS.add(iteratedFieldName);
					errorFlag = true;
				}
			}
		}
		if (errorFlag) {	
			TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="Invalid Filter Field names specified";
		    throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
		}
		return errorFlag ? false :true;
	}
	public boolean validateAllGlobalFieldNames(TransactionSearchGlobalRequest transactionSearchGlobalRequest, Map inputRequestMap) throws TPMSGlobalException {
		logger.info("Class : "+ this.getClass()+" Method Name : validateAllGlobalFieldNames started");
		String methodName = "validateAllGlobalFieldNames";
		Set filterFieldNamesSet = new HashSet();
		Set validFilterFieldNamesSet = new HashSet();
		String iteratedValue = null;
		String iteratedFieldName = null;
		String iteratedFieldValue = null;
		List validFilterFieldNamesList=Arrays.asList(TransactionSearchConstants.VALID_GLOBAL_FIELD_NAMES);    
		String fieldName = null;
		String fieldValue = null;
		Object[] objArray = inputRequestMap.keySet().toArray();

		iteratedValue = null;
		int errorFieldsCounter = 0;
		boolean errorFlag = false;
		String errorFieldNames = null;
		for (int i =0;i<objArray.length;i++) {
			
			iteratedFieldName = objArray[i].toString();
			if (iteratedFieldName.equals("page") || iteratedFieldName.equals("size") || iteratedFieldName.equals("sortType")  )
			{
				continue;
			}	

			if (!(validFilterFieldNamesList.contains(iteratedFieldName))){
				errorFieldsCounter++;
				errorFlag = true;
				errorFieldNames += " "+iteratedFieldName+(errorFieldsCounter!= 1?",":"");
				TransactionSearchConstants.RESPONSE_FIELD_ERRORS.add(iteratedFieldName);
			}

		}
		if (errorFlag) {
			TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=TransactionSearchConstants.INVALID_FIELD_NAMES_SPECIFIED_IN_FILTER_CLAUSE+" Field Name :"+errorFieldNames;
			throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
		}

		logger.info("Class : "+ this.getClass()+" Method Name : validateAllGlobalFieldNames ....ended...");

		return errorFlag ? false:true;
	}
	public boolean validateFilterFieldNames(String javaObjectFieldName) {
		logger.info("Class : "+this.getClass()+" Method Name : validateFilterFieldNames started... Parameter passed is "+javaObjectFieldName);	
        String methodName = "validateFilterFieldNames";
		List l = Arrays.asList(transactionSearchConstants.VALID_FILTER_FIELD_NAMES);
		if ( l.contains(javaObjectFieldName)) {
			return true;
		}
		TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="Invalid Filter Field names specified";
        throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
		//return false;	
	}

	public boolean validateGlobalFieldNames(String javaObjectFieldName) {
		logger.info("Class : "+this.getClass()+" Method Name : validateFilterFieldNames started... Parameter passed is "+javaObjectFieldName);	
		String methodName = "validateGlobalFieldNames";
		
		List l = Arrays.asList(transactionSearchConstants.VALID_GLOBAL_FIELD_NAMES);
		if ( l.contains(javaObjectFieldName)) {
			return true;
		}
		logger.info("Class : "+this.getClass()+" Method Name : validateGlobalFieldNames : NOT ABLE TO FIND FIELD NAME ");	
		TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="Invalid Field names specified";
        throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
		//return false;	
	}
	public boolean validateSortFieldNames(String javaObjectFieldName) {
		String methodName = "validateSortFieldNames";
		List l = Arrays.asList(transactionSearchConstants.VALID_SORT_FIELD_NAMES);
		if ( l.contains(javaObjectFieldName)) {
			return true;
		}
		TransactionSearchConstants.RESPONSE_ERROR_MESSAGE="Invalid Sort Field Names specified";
		throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
		//return false;	
	}

	public boolean validateAllRequestMemberName(List<TransactionSearchFilter> transactionSearchFilterList) throws TPMSGlobalException {
		logger.info("Class : "+ this.getClass()+" Method Name : validateAllRequestMemberName started");
		String methodName = "validateAllRequestMemberName";
		Map filterListMap = new HashMap();
		Set filterFieldNamesSet = new HashSet();
		TransactionSearchFilter transactionSearchFilter = null;
		Set validFilterFieldNamesSet = new HashSet();
		String iteratedValue = null;
		String iteratedFieldName = null;
		String iteratedFieldValue = null;


		boolean dateFieldsExistFlag             = false;
		boolean otherThanDateFieldsExistFlag    = false;
		boolean independentFieldNamesExistFlag  = false;
		boolean fieldsAllowedWithDateFieldsFlag = false;
		boolean plateNumberExistsFlag = false;
		boolean plateStateExistsFlag  = false;
		boolean plazaExistsFlag = false;
		boolean laneExistsFlag = false;
        boolean transponderNumberExistsFlag = false;
		int dateFieldCounter = 0;
		int dateFieldIndex = -1;
		int otherThanDateFieldCounter = 0;
		int independentFieldNamesCounter = 0;
		int fieldsAllowedWithDateFieldCounter = 1;

		String[] dateFieldsPresentInFilter = new String[4];
		String[] dateFieldsPresentInFilter_FieldValue = new String[4];
		Object keyValue = null;
		String keyValueInStr = null;

		List filterDateFieldNamesList = new ArrayList();
		filterDateFieldNamesList=Arrays.asList(transactionSearchConstants.FILTER_DATE_FIELD_NAMES);

		List filterNonDateFieldNamesList = new ArrayList();
		filterNonDateFieldNamesList=Arrays.asList(transactionSearchConstants.FILTER_NON_DATE_FIELD_NAMES);

		List filterIndependentFieldNamesList = new ArrayList();
		filterIndependentFieldNamesList=Arrays.asList(transactionSearchConstants.FILTER_INDEPENDENT_FIELD_NAMES);

		List filterPlateNumberAndPlateStateList = new ArrayList();
		filterPlateNumberAndPlateStateList=Arrays.asList(transactionSearchConstants.FILTER_WITH_PLATE_NUMBER_AND_OPTIONAL_PLATE_STATE);

		List filterPlazaAndLaneList = new ArrayList();
		filterPlazaAndLaneList=Arrays.asList(transactionSearchConstants.FILTER_WITH_PLAZA_AND_OPTIONAL_LANE_ID);

		Iterator itr = transactionSearchFilterList.iterator();
		transactionSearchFilterList.forEach(c -> filterListMap.put(c.getFieldName(), c.getFieldValue()));
		
		List validFilterFieldNamesList=Arrays.asList(TransactionSearchConstants.VALID_FILTER_FIELD_NAMES);    
		validFilterFieldNamesList.forEach(c -> validFilterFieldNamesSet.add(c));

		transactionSearchFilterList.forEach(c -> filterListMap.put(c.getFieldName(), c.getFieldValue()));

		itr = transactionSearchFilterList.iterator();
		iteratedValue = null;

		itr = transactionSearchFilterList.iterator();
		logger.info("Class : "+ this.getClass()+"  Method Name : validateAllRequestMemberName ....STEP 1");
	
		while (itr.hasNext()) {
			
			transactionSearchFilter =  (TransactionSearchFilter) itr.next();	
			iteratedFieldName = transactionSearchFilter.getFieldName();
			iteratedFieldValue = transactionSearchFilter.getFieldValue();
			
			if (iteratedFieldValue != null) { 
				if (filterDateFieldNamesList.contains(iteratedFieldName)) {	
					dateFieldsExistFlag = true;
					dateFieldCounter++;
					dateFieldIndex++;
					dateFieldsPresentInFilter[dateFieldIndex]=(String) iteratedFieldName;
					dateFieldsPresentInFilter_FieldValue[dateFieldIndex] = (String) iteratedFieldValue;
				}    
			}

			logger.info("iteratedFieldName : "+iteratedFieldName+ " "+ "itereatedFieldValue : " +iteratedFieldValue);
			if (!(validFilterFieldNamesSet.contains(iteratedFieldName))){
				TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=TransactionSearchConstants.INVALID_FIELD_NAMES_SPECIFIED_IN_FILTER_CLAUSE+" Field Name :"+iteratedFieldValue;
				throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
			}

			if (iteratedFieldValue == null) {
				TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=TransactionSearchConstants.NO_VALUE_SPECIFIED_FOR_FIELD_NAME_IN_FILTER_CLAUSE+" Field Name :"+iteratedFieldName;
				throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
			}
			if (filterFieldNamesSet.contains(iteratedFieldName)){
				TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=TransactionSearchConstants.DUPLICATE_FIELD_NAMES_SPECIFIED_IN_FILTER_CLAUSE+" Field Name :"+iteratedFieldName;
				throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
			} else {
				filterFieldNamesSet.add(iteratedFieldName);
			}
		}
		List filterFieldsAllowedWithDateFieldsList = new ArrayList();
		filterFieldsAllowedWithDateFieldsList=Arrays.asList(transactionSearchConstants.FILTER_FIELDS_ALLOWED_WITH_DATE_FIELDS);


		Iterator iterator = filterFieldNamesSet.iterator();

		while (iterator.hasNext()) {
			keyValue = iterator.next();
			if (keyValue != null) {
				if (filterNonDateFieldNamesList.contains(keyValue)) {
					otherThanDateFieldsExistFlag    = true;
					otherThanDateFieldCounter++;
				}
				if (filterIndependentFieldNamesList.contains(keyValue)) {
					independentFieldNamesExistFlag  = true;
					independentFieldNamesCounter++;
				}
				if (filterFieldsAllowedWithDateFieldsList.contains(keyValue)) {
					fieldsAllowedWithDateFieldsFlag = true;
					fieldsAllowedWithDateFieldCounter++;
				} 
				keyValueInStr=keyValue.toString();
				switch (keyValueInStr)
				{
				case "transponderNumber" : transponderNumberExistsFlag = true;
				break;
				case "plateNumber" : plateNumberExistsFlag = true;
				break;
				case "plateState"  : plateStateExistsFlag = true;
				break;
				case "plazaId"     : plazaExistsFlag = true;
				break;
				case "laneId"      : laneExistsFlag = true;
				break;
				}
			}
		}

		logger.info("dateFieldsExistFlag             : "+dateFieldsExistFlag);
		logger.info("Number of date fields           : "+dateFieldCounter);
		logger.info("otherThanDateFieldsExistFlag    : "+otherThanDateFieldsExistFlag);
		logger.info("independentFieldNamesExistFlag  : "+independentFieldNamesExistFlag);
		logger.info("fieldsAllowedWithDateFieldsFlag : "+fieldsAllowedWithDateFieldsFlag);
		logger.info("plateNumberExistsFlag           : "+plateNumberExistsFlag);
		logger.info("plateStateExistsFlag            : "+plateStateExistsFlag);
		logger.info("plazaExistsFlag                 : "+plazaExistsFlag);
		logger.info("laneExistsFlag                  : "+laneExistsFlag);

		/*if (dateFieldCounter >2) {
			TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.EITHER_TRANSACTION_DATE_OR_POSTED_DATE_ALLOWED;
			throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);

		}*/
		boolean datePairedFlag = false;
		if (dateFieldCounter > 0) {
			for (int j=1;j<dateFieldCounter;j++) {
				if (dateFieldsPresentInFilter[0]!= null && dateFieldsPresentInFilter[j]!= null ) {
					if (dateFieldsPresentInFilter[0].substring(0, 4).equals(dateFieldsPresentInFilter[j].substring(0, 4)))
					{
						datePairedFlag = true; 	
					}
				}
			}
		}
		if (dateFieldCounter > 1 && (! datePairedFlag)) {
			TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.CANNOT_GIVE_BOTH_TRANSACTION_DATE_POSTED_DATE_IN_FILTER_CLAUSE;
			throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
		}

		if (independentFieldNamesExistFlag)
		{
			if (dateFieldsExistFlag || fieldsAllowedWithDateFieldsFlag) {
				TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.CANNOT_CLUB_LANE_TX_ID_AND_EXT_REFERENCE_ID_WITH_OTHER_FIELDS;
				throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
			}
		}
		if (independentFieldNamesCounter>1) {
			TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.EITHER_LANE_TX_ID_OR_EXT_REFERENCE_NO_CAN_BE_SPECIFIED;
			throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
		}
		int counter =0;
		if (dateFieldsExistFlag ) {
			if (transponderNumberExistsFlag) counter++;
			if (plateStateExistsFlag || plateNumberExistsFlag) counter++;
			if ( laneExistsFlag || plazaExistsFlag)  counter++;
			if (counter > 1)
			{
				TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.CANNOT_CLUB_TRANSPONDER_NUMBER_PLATENUMBER_PLATESTATE_PLAZA_LANEID_INVOICENO_ESCLEVEL_IN_ONE_GO;	
				throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
			}
			if (independentFieldNamesExistFlag) {
				TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.CANNOT_SPECIFY_INDEPENDENT_FIELD_NAME_ALONG_WITH_DATE_FIELDS;	
				throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
			}
		}
		if (plateNumberExistsFlag || plateStateExistsFlag)
		{
			if (independentFieldNamesExistFlag) {
				TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.CAN_CLUB_PLATE_STATE_WITH_PLATE_NUMBER_ONLY;	
				throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
			}
		}
		if (plateStateExistsFlag && !plateNumberExistsFlag) {
			TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.CAN_CLUB_PLATE_STATE_WITH_PLATE_NUMBER_ONLY;	
			throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
		}
		if (laneExistsFlag && !plazaExistsFlag) {
			TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.CAN_CLUB_LANE_ID_WITH_PLAZA_ONLY;	
			throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);

		}
		if (plazaExistsFlag || laneExistsFlag) {
			if (independentFieldNamesExistFlag) {
				TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.CAN_CLUB_LANE_ID_WITH_PLAZA_ONLY;	
				throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
			}
		}

		String fromDate = null;
		String toDate = null;
		if (dateFieldsExistFlag && dateFieldsPresentInFilter != null) {
			for (int i=0;i<2;i++)
			{
				if (dateFieldsPresentInFilter[i] != null)
				{	   
					if (dateFieldsPresentInFilter[i].contains("FromDate")) {
						fromDate = dateFieldsPresentInFilter_FieldValue[i];
					} else {
						toDate = dateFieldsPresentInFilter_FieldValue[i];
					}
				}

			}
			logger.info(this.getClass()+"  Method Name : validateAllRequestMemberName : From Date : "+fromDate +" To Date : "+toDate);
		}
		// Date Range check validation        
		if (dateFieldsExistFlag) {
			if (plazaExistsFlag || laneExistsFlag) {
				if (genericDateRangeCheck(fromDate,toDate,TransactionSearchConstants.DATE_RANCE_FOR_PLAZA_LANE_SEARCH)){
					logger.info("Valid date range");
				} else {
					TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.DATE_RANGE_EXCEEDS_FOR_PLAZA_SEARCH+" "+TransactionSearchConstants.DATE_RANCE_FOR_PLAZA_LANE_SEARCH+ " days.";	
					throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
				}
			} else {
				if (genericDateRangeCheck(fromDate,toDate,TransactionSearchConstants.DATE_RANGE_FOR_OTHER_FIELDS_OTHER_THAN_PLAZA_LANE)){
					logger.info("Valid date range");
				} else {
					TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.DATE_RANGE_EXCEEDS_FOR_OTHER_FIELDS_SEARCH+" "+TransactionSearchConstants.DATE_RANGE_FOR_OTHER_FIELDS_OTHER_THAN_PLAZA_LANE+" days.";
					throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
				}
			}
		}
		logger.info("Class : "+ this.getClass()+" Method Name : validateAllRequestMemberName ....ended...");
		return true;
	}

	public boolean validateAllRequestMemberNameGlobal(TransactionSearchGlobalRequest transactionSearchGlobalRequest, Map<String,String> inputRequestMap) throws TPMSGlobalException {
		logger.info("Class : "+ this.getClass()+" Method Name : validateAllRequestMemberNameGlobal started");
		String methodName = "validateAllRequestMemberNameGlobal";
		Set<String> filterFieldNamesSet = new HashSet<String>();
	    String iteratedFieldName = null;
	    String iteratedFieldValue = null;
		boolean dateFieldsExistFlag             = false;
		boolean otherThanDateFieldsExistFlag    = false;
		boolean independentFieldNamesExistFlag  = false;
		boolean fieldsAllowedWithDateFieldsFlag = false;
		boolean plateNumberExistsFlag = false;
		boolean plateStateExistsFlag  = false;
		boolean plazaExistsFlag = false;
		boolean laneExistsFlag = false;
        boolean transponderNumberExistsFlag = false;
        boolean accountNumberExistsFlag = false;
        boolean invoiceNumberExistsFlag = false;
        boolean escalationLevelExistsFlag = false;
		int dateFieldCounter = 0;
		int dateFieldIndex = -1;
		int otherThanDateFieldCounter = 0;
		int independentFieldNamesCounter = 0;
		int fieldsAllowedWithDateFieldCounter = 1;
		List<String> validFilterFieldNamesList=Arrays.asList(TransactionSearchConstants.VALID_GLOBAL_FIELD_NAMES);    
		
		List filterDateFieldNamesList = new ArrayList();
		filterDateFieldNamesList=Arrays.asList(transactionSearchConstants.FILTER_DATE_FIELD_NAMES);

		List filterNonDateFieldNamesList = new ArrayList();
		filterNonDateFieldNamesList=Arrays.asList(transactionSearchConstants.FILTER_NON_DATE_FIELD_NAMES);

		List filterIndependentFieldNamesList = new ArrayList();
		filterIndependentFieldNamesList=Arrays.asList(transactionSearchConstants.FILTER_INDEPENDENT_FIELD_NAMES);

		List filterPlateNumberAndPlateStateList = new ArrayList();
		filterPlateNumberAndPlateStateList=Arrays.asList(transactionSearchConstants.FILTER_WITH_PLATE_NUMBER_AND_OPTIONAL_PLATE_STATE);

		List filterPlazaAndLaneList = new ArrayList();
		filterPlazaAndLaneList=Arrays.asList(transactionSearchConstants.FILTER_WITH_PLAZA_AND_OPTIONAL_LANE_ID);

		List filterFieldsAllowedWithDateFieldsList = new ArrayList();
		filterFieldsAllowedWithDateFieldsList=Arrays.asList(transactionSearchConstants.FILTER_FIELDS_ALLOWED_WITH_DATE_FIELDS);

		
		String[] dateFieldsPresentInFilter = new String[4];
		String[] dateFieldsPresentInFilter_FieldValue= new String[4];
		List omitValidationList = new ArrayList();
		omitValidationList.add("page");
		omitValidationList.add("size");
		omitValidationList.add("sortType");
	    omitValidationList.add("sortBy");	
	    //omitValidationList.add("accountNumber");	
		
		inputRequestMap.values().removeIf(x -> x=="");
		Object[] objArray = inputRequestMap.keySet().toArray();
		Iterator itr = Iterators.forArray(objArray);
        int otherFieldsCounter=0;
		while (itr.hasNext()) {
    	   iteratedFieldName =  (String) itr.next();
    	   iteratedFieldValue = (String) inputRequestMap.get(iteratedFieldName);
            
			if (omitValidationList.contains(iteratedFieldName)) continue;
			
			if (iteratedFieldName.indexOf("Date")>0) {
			} else {
				if (iteratedFieldName.contains("invoiceNo") || iteratedFieldName.contains("escLevel") )
				{
					
				} else {
					otherFieldsCounter++;
				}
			}
		
			if (!(validFilterFieldNamesList.contains(iteratedFieldName))){
				TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=TransactionSearchConstants.INVALID_FIELD_NAMES_SPECIFIED_IN_FILTER_CLAUSE+" Field Name :"+iteratedFieldName;
				throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
			}
			if (filterFieldNamesSet.contains(iteratedFieldName)){
				TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=TransactionSearchConstants.DUPLICATE_FIELD_NAMES_SPECIFIED_IN_FILTER_CLAUSE+" Field Name :"+iteratedFieldName; 
				throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
			} else {
				filterFieldNamesSet.add(iteratedFieldName);
			}
			
			if (iteratedFieldValue != null) { 
				if (filterDateFieldNamesList.contains(iteratedFieldName)) {	
					dateFieldsExistFlag = true;
					dateFieldCounter++;
					dateFieldIndex++;
					dateFieldsPresentInFilter[dateFieldIndex]=(String) iteratedFieldName;
					dateFieldsPresentInFilter_FieldValue[dateFieldIndex] = (String) iteratedFieldValue;
				}    
			}
			
			if (filterNonDateFieldNamesList.contains(iteratedFieldName)) {
				otherThanDateFieldsExistFlag    = true;
				otherThanDateFieldCounter++;
			}
			if (filterIndependentFieldNamesList.contains(iteratedFieldName)) {
				independentFieldNamesExistFlag  = true;
				independentFieldNamesCounter++;
			}
			if (filterFieldsAllowedWithDateFieldsList.contains(iteratedFieldName)) {
				fieldsAllowedWithDateFieldsFlag = true;
				fieldsAllowedWithDateFieldCounter++;
			}
			
			switch (iteratedFieldName)
			{
			case "transponderNumber" : transponderNumberExistsFlag = true;
			break;
			case "plateNumber" : plateNumberExistsFlag = true;
			break;
			case "plateState"  : plateStateExistsFlag = true;
			break;
			case "plazaId"     : plazaExistsFlag = true;
			break;
			case "laneId"      : laneExistsFlag = true;
			break;
			case "accountNumber" : accountNumberExistsFlag=true;
			break;
			case "invoiceNo" : invoiceNumberExistsFlag=true;
			break;
			case "escLevel" : escalationLevelExistsFlag=true;
			break;
			}
			continue;
			}
       boolean datePairedFlag = false;
		if (dateFieldCounter > 0) {
			for (int j=1;j<dateFieldCounter;j++) {
				if (dateFieldsPresentInFilter[0]!= null && dateFieldsPresentInFilter[j]!= null ) {
					if (dateFieldsPresentInFilter[0].substring(0, 4).equals(dateFieldsPresentInFilter[j].substring(0, 4)))
					{
						datePairedFlag = true; 	
					}
				}
			}
		}
			/*if (dateFieldCounter >2) {
				TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.EITHER_TRANSACTION_DATE_OR_POSTED_DATE_ALLOWED;
				throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);

			}*/
			if (dateFieldCounter > 2) {
				TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.CANNOT_GIVE_BOTH_TRANSACTION_DATE_POSTED_DATE_IN_FILTER_CLAUSE;
				throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
			}
			if (invoiceNumberExistsFlag || escalationLevelExistsFlag )
			{
				if (otherFieldsCounter > 0) {
					TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.INVOICE_NUMBER_AND_ESCALATION_LEVEL_CANNOT_BE_COMBINED_WITH_OTHER_FIELDS;
					throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
					
				}
			}
			if (invoiceNumberExistsFlag && escalationLevelExistsFlag) {
				TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.INVOICE_NUMBER_AND_ESCALATION_LEVEL_CANNOT_BE_CLUBBED;
				throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
			
			}

			if (independentFieldNamesExistFlag)
			{
				if (independentFieldNamesCounter >1 || fieldsAllowedWithDateFieldCounter> 1 || otherThanDateFieldCounter>1) {
					TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.CANNOT_CLUB_LANE_TX_ID_AND_EXT_REFERENCE_ID_WITH_OTHER_FIELDS;
					throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
				}
			}
			if (independentFieldNamesCounter>1) {
				TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.EITHER_LANE_TX_ID_OR_EXT_REFERENCE_NO_CAN_BE_SPECIFIED;
				throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
			}
			int counter =0;
			if (dateFieldsExistFlag ) {
				if (transponderNumberExistsFlag) counter++;
				if (plateStateExistsFlag || plateNumberExistsFlag) counter++;
				if ( laneExistsFlag || plazaExistsFlag)  counter++;
				if (invoiceNumberExistsFlag)counter++;
				if (escalationLevelExistsFlag) counter++;
				
//				if (counter > 1 && accountNumberExistsFlag)
//				{
//					//TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.CANNOT_CLUB_TRANSPONDER_NUMBER_PLATENUMBER_PLATESTATE_PLAZA_LANEID_IN_ONE_GO;	
//					TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.CANNOT_CLUB_TRANSPONDER_NUMBER_PLATENUMBER_PLATESTATE_PLAZA_LANEID_INVOICENO_ESCLEVEL_IN_ONE_GO;	
//					throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
//				}
				if (independentFieldNamesExistFlag && dateFieldsExistFlag) {
					TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.CANNOT_SPECIFY_INDEPENDENT_FIELD_NAME_ALONG_WITH_DATE_FIELDS;	
					throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
				}
			}
			
			if (plateNumberExistsFlag || plateStateExistsFlag)
			{
				if (independentFieldNamesExistFlag) {
					TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.CAN_CLUB_PLATE_STATE_WITH_PLATE_NUMBER_ONLY;	
					throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
				}
			}
			if (plateStateExistsFlag && !plateNumberExistsFlag) {
				TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.CAN_CLUB_PLATE_STATE_WITH_PLATE_NUMBER_ONLY;	
				throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
			}
			if (laneExistsFlag && !plazaExistsFlag) {
				TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.CAN_CLUB_LANE_ID_WITH_PLAZA_ONLY;	
				throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);

			}
			if (plazaExistsFlag || laneExistsFlag) {
				if (independentFieldNamesExistFlag) {
					TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.CAN_CLUB_LANE_ID_WITH_PLAZA_ONLY;	
					throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
				}
			}

			String fromDate = null;
			String toDate = null;
			if (dateFieldsExistFlag && dateFieldsPresentInFilter != null) {
				for (int i=0;i<2;i++)
				{
					if (dateFieldsPresentInFilter[i] != null)
					{	   
						if (dateFieldsPresentInFilter[i].contains("FromDate")) {
							fromDate = dateFieldsPresentInFilter_FieldValue[i];
						} else if(dateFieldsPresentInFilter[i].contains("ToDate")){
							toDate = dateFieldsPresentInFilter_FieldValue[i];
						}
					}

				}
				logger.info(this.getClass()+"  Method Name : validateAllRequestMemberName : From Date : "+fromDate +" To Date : "+toDate);
			}
//			if(accountNumberExistsFlag && (plateNumberExistsFlag || plazaExistsFlag || transponderNumberExistsFlag || invoiceNumberExistsFlag || escalationLevelExistsFlag)) {
//				TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.CANNOT_CLUB_ACCOUNT_NO_WITH_OTHER_FIELD;	
//				throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
//			}
			// Date Range check validation 
			   if (fieldsAllowedWithDateFieldsFlag  && (! datePairedFlag)) {
				  TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.EITHER_TRANSACTION_DATE_OR_POSTED_DATE_ALLOWED_FOR_DATE_ALLOWED_FIELD;
				  throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
			   }

			if (dateFieldsExistFlag) {
				if (plazaExistsFlag || laneExistsFlag) {
					if (genericDateRangeCheck(fromDate,toDate,TransactionSearchConstants.DATE_RANCE_FOR_PLAZA_LANE_SEARCH)){
						logger.info("Valid date range");
					} else {
						TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.DATE_RANGE_EXCEEDS_FOR_PLAZA_SEARCH+" "+TransactionSearchConstants.DATE_RANCE_FOR_PLAZA_LANE_SEARCH+ " days.";	
						throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
					}
				} else {
					if (genericDateRangeCheck(fromDate,toDate,TransactionSearchConstants.DATE_RANGE_FOR_OTHER_FIELDS_OTHER_THAN_PLAZA_LANE)){
						logger.info("Valid date range");
					} else {
						TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.DATE_RANGE_EXCEEDS_FOR_OTHER_FIELDS_SEARCH+" "+TransactionSearchConstants.DATE_RANGE_FOR_OTHER_FIELDS_OTHER_THAN_PLAZA_LANE+" days.";
						throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
					}
				}
			}
			
			if (dateFieldsExistFlag) {
				if(accountNumberExistsFlag || plateNumberExistsFlag || plateStateExistsFlag || transponderNumberExistsFlag || plazaExistsFlag || laneExistsFlag) {
					
				} else {
					TransactionSearchConstants.RESPONSE_ERROR_MESSAGE=transactionSearchConstants.DATE_FIELDS_CANNOT_BE_SEARCHED_INDEPENDENTLY;
					throw new TPMSGlobalException(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE,className,methodName);
				
				}
			}
			logger.info("Class : "+ this.getClass()+" Method Name : validateAllRequestMemberName ....ended...");
		
		return true;
	
	}
	
	
}
