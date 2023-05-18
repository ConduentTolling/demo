package com.conduent.tpms.qeval.constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Constants
 * 
 * @author Urvashic
 *
 */

public class Constants {

	// DATE_CONSTANT
	public static final String DATE_YYYYMMDD = "YYYYMMDD";
	public static final String DATE_FORMATTER_YYYYMMDD = "yyyyMMdd";
	
	public static final String NYSTA_DATE_FORMAT = "yyyyMMdd";
	public static final String NYSTA_TIME_FORMAT = "HHmmssSS";
	public static final String dateFormator="yyyy-MM-dd";

	// TIME_CONSTANT
	public static final String TIME_HHMMSS = "HHMMSS";
	public static final String TIME_HHMMSSTT = "HHMMSSTT";
	public static final String dateTimeFormator="yyyy-MM-dd HH:mm:ss.SSS";

	// Validation
	public static final String FIXED_VALUE = "FIXED_VALUE";
	public static final String RANGE = "RANGE";
	public static final String LOV = "LOV";

	// OTHER
	public static final String AGENCY_ID = "AGENCY_ID";
	public static final String FIELD_TYPE = "FIELD_TYPE";
	public static final String DATE = "DATE";
	public static final String TRX_FILE_NAME = "TRX_FILE_NAME";
	public static final String RECON_FILE_NAME="RECON_FILE_NAME";
	public static final String EXTERN_FILE_ID="EXTERN_FILE_ID";
	public static final String ATP_FILE_ID="ATP_FILE_ID";
	public static final String EXPECTED_REVENUE_AMOUNT="EXPECTED_REVENUE_AMOUNT";
	public static final String CASH_FARE_AMOUNT="CASH_FARE_AMOUNT";
	public static final String POSTED_FARE_AMOUNT="POSTED_FARE_AMOUNT";
	public static final String ETC_FARE_AMOUNT="ETC_FARE_AMOUNT";
	public static final String TRANSACTION_INFO="TRANSACTION_INFO";
	public static final String PLAN_CHARGED="PLAN_CHARGED";
	public static final String TX_STATUS="TX_STATUS";
	

	public static final String TXN_FILE_EXTENSION = "TXN";
	public static final String UNDER_SCORE_CHAR = "_";
	public static final String ACK_EXT = ".ACK";
	
	
	//T_TRAN_DETAIL
	
	//QATP STATS CONST
	public static final String INSERT_DATE = "INSERT_DATE";
	public static final String INSERT_TIME = "INSERT_TIME";
	public static final String RECORD_COUNT = "RECORD_COUNT";
	public static final String AMOUNT = "AMOUNT";
	public static final String IS_PROCESSED = "IS_PROCESSED";
	public static final String PROCESS_DATE = "PROCESS_DATE";
	public static final String PROCESS_TIME = "PROCESS_TIME";
	public static final String ID = "ID";
	public static final int ZERO = 0;
	public static final Object NO = "N";
	public static final Object YES = "Y";
		
	
	//ITXC
	public static final String REGEX = "REGEX";
	public static final String TIME = "TIME";
	////////////
	public static final String ETC_ACCOUNT_ID = "ETC_ACCOUNT_ID";
	public static final String ANNIVERSAY_DOM = "ANNIVERSAY_DOM";
	
	public static final String ACCT_ACT_STATUS_VALUE ="3";
	public static final String ACCT_ACT_STATUS= "ACCT_ACT_STATUS";
	
	//public static final List<Integer> ACCOUNT_TYPE_VALUE = new List<Integer>{1,2,3,5,7,9,13};
	//public static final Object ACCOUNT_TYPE_VALUE_LIST="1,2,3,5,7,9,13";
	public static final Object ACCOUNT_TYPE_VALUE_LIST=Arrays.asList(1,2,3,5,7,9,13);
	public static final String ACCOUNT_TYPE= "ACCOUNT_TYPE";
	
	//public static final List<Integer> ACCOUNT_TYPE_VALUE_LIST= Collections.unmodifiableList(Arrays.asList(1,2,3,5,7,9,13));
	public static final int PRIVATE=1;
	public static final int BUSINESS=2;
	public static final int COMMERCIAL=3;
	public static final int PVIDEOREG=5;
	public static final int BVIDEOREG=7;
	public static final int REGVIDEO=9;
	
	
	
	
	
	public static final int STATUS_VALUE=25;
	public static final String STATUS= "STATUS";
	
	public static final String POSTED_START_DATE="POSTED_START_DATE";
	public static final String POSTED_END_DATE="POSTED_END_DATE";
	
	public static final int POSTED_DATE_INTERVAL_VALUE=10;
	public static final String POSTED_DATE_INTERVAL= "POSTED_DATE_INTERVAL";
	
	public static final String EVAL_START_DATE= "EVAL_START_DATE";
	public static final String REBILL_AMOUNT= "REBILL_AMOUNT";
	
	public static String lastRunDate; 
	
	public static final String MESSAGE1="MESSAGE1";
	public static final String MESSAGE1_VALUE="On evaluation of toll usage, your replenishment has been changed from ";
	public static final String MESSAGE2="MESSAGE2";
	public static final String MESSAGE2_VALUE="$50.00";
	
	public static final String STMTRUNID="STMTRUNID";
	public static final int STMTRUNID_VALUE=401;
	public static final String TOTALRECPROCESSED="TOTALRECPROCESSED";
	public static final String REBILLUPCOUNT="REBILLUPCOUNT";
	public static final String REBILLDOWNCOUNT="REBILLDOWNCOUNT";
	public static final String SKIPRECCOUNT="SKIPRECCOUNT";
	
	public static final String ADDRESS_TYPE="ADDRESS_TYPE";
	public static final String ADDRESS_TYPE_VALUE="MAILING";
	
	public static final int QEVAL_PERIOD=90;
	public static final String QEVAL_PROCESS_PARAM="QEVAL_PROCESS";
	public static final String QEVAL_PARAM="QEVAL";
	public static final String QEVAL_PROCESS_PARAM_FREQUENCY="QEVAL_FREQUENCY";
	public static final String QEVAL_ROUND_FREQUENCY="QEVAL_ROUND_FREQUENCY";
	
	
	
	
	
}
