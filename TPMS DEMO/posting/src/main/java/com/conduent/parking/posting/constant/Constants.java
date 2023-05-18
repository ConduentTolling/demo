package com.conduent.parking.posting.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Constants 
{
	public static final String PLAN_TYPE_ID="PLAN_TYPE_ID";
	public static final String VEHICLE_CLASS="VEHICLE_CLASS";
	public static final String AGENCY_ID = "AGENCY_ID";
	public static final String DEVICE_NO = "DEVICE_NO";
	public static final String ETC_ACCOUNT_ID = "ETC_ACCOUNT_ID";
	public static final String TX_DATE = "TX_DATE";
	public static final String TX_TIME_STAMP = "TX_TIME_STAMP";
	public static final String TX_DATE_TIME = "TX_DATE_TIME";
	public static final String TX_STATUS ="TX_STATUS";
	public static final String POSTED_DATE ="POSTED_DATE";
	public static final String DEPOSITE_ID="DEPOSITE_ID";
	public static final String ROW_ID="ROW_ID";
	public static final String APD_ID="APD_ID";
	public static final Integer PA_AGENCY_ID=3;
	public static final String ENTRY_PLAZA_ID = "ENTRY_PLAZA_ID";
	public static final String EXIT_PLAZA_ID = "EXIT_PLAZA_ID";
	public static final String PLAZA_ID = "PLAZA_ID";
	public static final Double minAccountBal=100.0;
	public static final String dateFormator="yyyy-MM-dd";
	public static final String dateTimeFormator="yyyy-MM-dd HH:mm:ss.SSS";
	public static final String LANE_TX_ID="LANE_TX_ID";
	public static final String SUSP_DAY="SUSP_DAY";
	public static final String TRIP_START_DATE="TRIP_START_DATE";
	public static final String TRIP_END_DATE="TRIP_END_DATE";
	public static final String LATE_TRIPS="LATE_TRIPS";
	public static final String RECON_DATE="RECON_DATE";
	public static final String TRIPS_MADE="TRIPS_MADE";
	public static final String AMOUNT_CHARGED="AMOUNT_CHARGED";
	public static final String TRIPS_CHARGED="TRIPS_CHARGED";
	public static final String RECON_FLAG="RECON_FLAG";
	public static final String TRIPS_LEFT="TRIPS_LEFT";
	public static final String LAST_TX_DATE="LAST_TX_DATE";
	public static final String USAGE_AMOUNT="USAGE_AMOUNT";
	public static final String FULL_FARE_AMOUNT="FULL_FARE_AMOUNT";
	public static final String DISCOUNTED_AMOUNT="DISCOUNTED_AMOUNT";
	public static final String REVENUE_DATE ="REVENUE_DATE";
	
	//---ICRX
	public static final String PLAZA_AGENCY_ID="PLAZA_AGENCY_ID";
	public static final String EXTER_FILE_ID="EXTER_FILE_ID";
	public static final String TX_EXTERN_REF_NO="TX_EXTERN_REF_NO";
	public static final String TX_MOD_SEQ="TX_MOD_SEQ";
	public static final String ACCOUNT_AGENCY_ID="ACCOUNT_AGENCY_ID";
	
	//------ Agency
	public static final Integer AGENCY_NY=1;
	public static final Integer AGENCY_TB=2;
	public static final Integer AGENCY_PA=3;
	public static final Integer AGENCY_NB=4;
	public static final Integer AGENCY_DRJT=19;	
	public static final Integer AGENCY_MICC=53;
	public static final Integer AGENCY_METL=54;	
	public static final Integer AGENCY_NCTA=30;
	
	//---------Plaza
	public static final Integer PLAZA_MARINE_PARKWAY=225;
	public static final Integer PLAZA_CROSS_BAY_BR=226;
	public static final Integer PLAZA_VERRAZANO_NARROES_BR=230;
	
	public static final  List<Integer> VIDEO_FARE_PLAN_LIST= Collections.unmodifiableList(Arrays.asList(217,290));
	public static final String TX_DATETIME = "TX_DATETIME";
	public static final String EFFECTIVE_DATE = "EFFECTIVE_DATE";
	public static String DAYS_IND="DAYS_IND";
	public static String DEFAULT_DAYS_IND="D";
	
	//-------Account-Agency-Constants
	
	//-------Unmatched Constants
	public static String MAX_FARE_IND="X";
	public static String MEDIAN_FARE_IND="M";
	
	//-------Failover Constants
	public static final String PREPOSTING = "PREPOSTING";
	public static final String TOLLPOSTING = "TOLLPOSTING";
	public static final String AFTERPOSTING = "AFTERPOSTING";
	public static final String RECOVERABLE = "RECOVERABLE";
	public static final String NONRECOVERABLE = "NONRECOVERABLE";
	
}
