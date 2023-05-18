package com.conduent.tpms.iag.constants;

/**
 * ITAG constants
 * 
 * @author MAYURIG1
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

	// FILE
	public static final String FILE_TYPE = "FILE_TYPE";
	public static final String FILE_FORMAT = "FILE_FORMAT";
	public static final String FILENAME = "FILENAME";
	public static final String FIXED = "FIXED";
	public static final String HEADER = "HEADER";
	public static final String DETAIL = "DETAIL";
	public static final String FILE_NAME = "FILE_NAME";

	// Validation
	public static final String FIXED_VALUE = "FIXED_VALUE";
	public static final String RANGE = "RANGE";
	public static final String LOV = "LOV";

	// OTHER
	public static final String AGENCY_ID = "AGENCY_ID";
	public static final String FIELD_TYPE = "FIELD_TYPE";
	public static final String DATE = "DATE";
	public static final String ACK_FILE_NAME = "ACK_FILE_NAME";
	public static final String ACK_FILE_DATE = "ACK_FILE_DATE";
	public static final String ACK_FILE_TIME = "ACK_FILE_TIME";
	public static final String ACK_FILE_TYPE = "ACK_FILE_TYPE";
	public static final String ACK_FILE_STATUS = "ACK_FILE_STATUS";
	public static final String TRX_FILE_NAME = "TRX_FILE_NAME";
	public static final String RECON_FILE_NAME="RECON_FILE_NAME";
	public static final String FROM_AGENCY="FROM_AGENCY";
	public static final String TO_AGENCY="TO_AGENCY";
	public static final String EXTERN_FILE_ID="EXTERN_FILE_ID";
	public static final String ATP_FILE_ID="ATP_FILE_ID";
	public static final String EXPECTED_REVENUE_AMOUNT="EXPECTED_REVENUE_AMOUNT";
	public static final String CASH_FARE_AMOUNT="CASH_FARE_AMOUNT";
	public static final String POSTED_FARE_AMOUNT="POSTED_FARE_AMOUNT";
	public static final String ETC_FARE_AMOUNT="ETC_FARE_AMOUNT";
	public static final String TRANSACTION_INFO="TRANSACTION_INFO";
	public static final String PLAN_CHARGED="PLAN_CHARGED";
	public static final String TX_STATUS="TX_STATUS";
	public static final String POSTED_DATE = "POSTED_DATE";
	public static final String DEPOSIT_ID = "DEPOSIT_ID";
	public static final long BATCH_RECORD_COUNT=5;
	
	
	
	//

	public static final String TXN_FILE_EXTENSION = "TXN";
	public static final String UNDER_SCORE_CHAR = "_";
	public static final String ACK_EXT = ".ACK";
	
	//ACK Status
	public static final String ACK_STATUS_07 = "07";
	public static final String ACK_STATUS_00 = "00";
	public static final String ACK_STATUS_01 = "01";
	
	
	//T_TRAN_DETAIL
	
	public static final String LANE_TX_ID          = "LANE_TX_ID";  
	public static final String TX_EXTERN_REF_NO    = "TX_EXTERN_REF_NO";
	public static final String TX_TYPE         = "TX_TYPE";
	public static final String TX_SUB_TYPE          = "TX_SUB_TYPE";
	public static final String TOLL_SYSTEM_TYPE    = "TOLL_SYSTEM_TYPE";
	public static final String TOLL_REVENUE_TYPE   = "TOLL_REVENUE_TYPE";
	public static final String ENTRY_TX_TIMESTAMP  = "ENTRY_TX_TIMESTAMP";
	public static final String ENTRY_PLAZA_ID      = "ENTRY_PLAZA_ID";
	public static final String ENTRY_LANE_ID       = "ENTRY_LANE_ID";
	public static final String ENTRY_TX_SEQ_NUMBER = "ENTRY_TX_SEQ_NUMBER";
	public static final String ENTRY_DATA_SOURCE   = "ENTRY_DATA_SOURCE";
	public static final String ENTRY_VEHICLE_SPEED = "ENTRY_VEHICLE_SPEED";
	public static final String PLAZA_AGENCY_ID     = "PLAZA_AGENCY_ID";
	public static final String PLAZA_ID            = "PLAZA_ID";
	public static final String LANE_ID             = "LANE_ID";
	public static final String TX_DATE             = "TX_DATE";
	public static final String TX_TIMESTAMP        = "TX_TIMESTAMP";
	public static final String LANE_MODE           = "LANE_MODE";
	public static final String LANE_STATE          = "LANE_STATE";
	public static final String TRX_LANE_SERIAL             = "TRX_LANE_SERIAL";
	public static final String DEVICE_NO           = "DEVICE_NO";
	public static final String AVC_CLASS  = "AVC_CLASS";
	public static final String TAG_IAG_CLASS    = "TAG_IAG_CLASS";
	public static final String TAG_CLASS = "TAG_CLASS";
	public static final String TAG_AXLES           = "TAG_AXLES";
	public static final String ACTUAL_CLASS        = "ACTUAL_CLASS";
	public static final String ACTUAL_AXLES        = "ACTUAL_AXLES";
	public static final String EXTRA_AXLES         = "EXTRA_AXLES";
	public static final String PLATE_STATE         = "PLATE_STATE";
	public static final String PLATE_NUMBER        = "PLATE_NUMBER";
	public static final String PLATE_TYPE          = "PLATE_TYPE";
	public static final String PLATE_COUNTRY       = "PLATE_COUNTRY";
	public static final String READ_PERF           = "READ_PERF";
	public static final String WRITE_PERF          = "WRITE_PERF";
	public static final String PROG_STAT           = "PROG_STAT";
	public static final String COLLECTOR_NUMBER    = "COLLECTOR_NUMBER";
	public static final String IMAGE_CAPTURED      = "IMAGE_CAPTURED";
	public static final String VEHICLE_SPEED       = "VEHICLE_SPEED";
	public static final String SPEED_VIOLATION     = "SPEED_VIOLATION";
	public static final String RECIPROCITY_TRX     = "RECIPROCITY_TRX";
	public static final String IS_VIOLATION        = "IS_VIOLATION";
	public static final String IS_LPR_ENABLED      = "IS_LPR_ENABLED";
	public static final String FULL_FARE_AMOUNT    = "FULL_FARE_AMOUNT";
	public static final String DISCOUNTED_AMOUNT   = "DISCOUNTED_AMOUNT";
	public static final String UNREALIZED_AMOUNT   = "UNREALIZED_AMOUNT";
	public static final String EXT_FILE_ID         = "EXT_FILE_ID"; 
	public static final String RECEIVED_DATE       = "RECEIVED_DATE";
	public static final String ACCOUNT_TYPE        = "ACCOUNT_TYPE";
	public static final String ACCT_AGENCY_ID      = "ACCT_AGENCY_ID";
	public static final String ETC_ACCOUNT_ID      = "ETC_ACCOUNT_ID";
	public static final String ETC_SUBACCOUNT      = "ETC_SUBACCOUNT";
	public static final String DST_FLAG            = "DST_FLAG";
	public static final String IS_PEAK             = "IS_PEAK";          
	public static final String FARE_TYPE           = "FARE_TYPE";         
	public static final String UPDATE_TS           = "UPDATE_TS";
	public static final String LANE_DATA_SOURCE = "LANE_DATA_SOURCE"; 
	public static final String LANE_TYPE = "LANE_TYPE";
	public static final String LANE_HEALTH = "LANE_HEALTH";
	public static final String AVC_AXLES = "AVC_AXLES";
	public static final String TOUR_SEGMENT_ID = "TOUR_SEGMENT_ID";
	public static final String BUF_READ = "BUF_READ";
	public static final String READER_ID = "READER_ID";
	public static final String TOLL_AMOUNT = "TOLL_AMOUNT";
	public static final String DEBIT_CREDIT = "DEBIT_CREDIT";
	public static final String ETC_VALID_STATUS = "ETC_VALID_STATUS";
	public static final String DISCOUNTED_AMOUNT_2 = "DISCOUNTED_AMOUNT_2";
	public static final String VIDEO_FARE_AMOUNT = "VIDEO_FARE_AMOUNT";
	public static final String PLAN_TYPE = "PLAN_TYPE";
	public static final String RESERVED= "RESERVED";
	
	//QATP STATS CONST
	public static final String INSERT_DATE = "INSERT_DATE";
	public static final String INSERT_TIME = "INSERT_TIME";
	public static final String RECORD_COUNT = "RECORD_COUNT";
	public static final String AMOUNT = "AMOUNT";
	public static final String IS_PROCESSED = "IS_PROCESSED";
	public static final String PROCESS_DATE = "PROCESS_DATE";
	public static final String PROCESS_TIME = "PROCESS_TIME";
	public static final String PROCESS_REC_COUNT = "PROCESS_REC_COUNT";
	public static final String XFER_CONTROL_ID = "XFER_CONTROL_ID";
	public static final String ID = "ID";
	public static final int ZERO = 0;
	public static final Object NO = "N";
	public static final String STATUS_ECTX = "ECTX";
	public static final String STATUS_REJC = "REJC";
	public static final Object YES = "Y";
		
	//Debit/Credit
	
	public static final String PLUS = "+";
	public static final String MINUS = "-";
	public static final String SPACE = " ";
	public static final String QCTOL_502 = "502";
	public static final String INSERT_IAG_FILE_STATS = "INSERT_IAG_FILE_STATS";
	public static final String ICTX_FILE_NAME = "ICTX_FILE_NAME";
	public static final String QUERY_PARAM_OUTPUT_FILE_NAME = "OUTPUT_FILE_NAME";
	public static final String INPUT_COUNT = "INPUT_COUNT";
	public static final String FILE_DATE = "FILE_DATE";
	public static final String FILE_SEQ_NUMBER = "FILE_SEQ_NUMBER";
	public static final String PROCESSED_FLAG = "PROCESSED_FLAG";
	//public static final int HEADER_LENGHT = 38;
	public static final int HEADER_LENGHT = 60;
	//public static final int DETAIL_RECORD_LENGHT = 109;
	public static final int DETAIL_RECORD_LENGHT = 201;
	//public static final int FILE_NAME_LENGHT = 27;
	public static final int FILE_NAME_LENGHT = 29;
	
	//ITXC
	public static final int ITXC_DETAIL_RECORD_LENGHT = 203;
	public static final String UPDATE_ATP_FILE_ID_IN_T_IA_FILE_STATS = "UPDATE_ATP_FILE_ID_IN_T_IA_FILE_STATS";
	public static final String ITXC = "ITXC";
	
	public static final String GET_ETC_ACCOUNT_1 = "GET_ETC_ACCOUNT_1";
	public static final String DEVICE_NO_14 = "DEVICE_NO_14";
	public static final String DEVICE_NO_11 = "DEVICE_NO_11";
	
	// TX_STATUS
	public static final String TAGB = "TAGB";
	public static final String ACCB = "ACCB";
	public static final String INSU = "INSU";
	public static final String NPST = "NPST";
	public static final String OLD1 = "OLD1";
	public static final String OLD2 = "OLD2";
	public static final String RINV = "RINV";
	
	public static final String GET_ETC_ACCOUNT_V_DEVICE = "GET_ETC_ACCOUNT_V_DEVICE";
	public static final String GET_ETC_ACCOUNT_V_H_DEVICE = "GET_ETC_ACCOUNT_V_H_DEVICE";
	public static final String GET_ETC_ACCOUNT_T_HA_DEVICES = "GET_ETC_ACCOUNT_T_HA_DEVICES";
	public static final String GET_V_ETC_ACCOUNT = "GET_V_ETC_ACCOUNT";
	public static final String GET_TOLL_POST_LIMITS = "GET_TOLL_POST_LIMITS";
	public static final String GET_MAX_LANE_ID = "GET_MAX_LANE_ID";
	public static final String GET_ALL_T_LANE = "GET_ALL_T_LANE";
	public static final String GET_ALL_T_PLAZA = "GET_ALL_T_PLAZA";
	public static final String GET_AWAY_AGENCY_EXT_LANE_PLAZA_INFO = "GET_AWAY_AGENCY_EXT_LANE_PLAZA_INFO";
	
	public static final String F = "F";
	public static final int ICTX_AGENCY_ID = 2;
}
