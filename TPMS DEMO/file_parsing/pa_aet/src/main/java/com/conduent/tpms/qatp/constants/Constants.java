package com.conduent.tpms.qatp.constants;

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
	public static final String PA_DATE_FORMAT = "yyyyMMdd";
	public static final String PA_TIME_FORMAT = "HHmmss";
	// TIME_CONSTANT
	public static final String TIME_HHMMSS = "HHMMSS";
	public static final String TIME_HHMMSSTT = "HHMMSSTT";

	// FILE
	public static final String FILE_TYPE = "FILE_TYPE";
	public static final String FILE_FORMAT = "FILE_FORMAT";
	public static final String FILENAME = "FILENAME";
	public static final String FIXED = "FIXED";
	public static final String HEADER = "HEADER";
	public static final String DETAIL = "DETAIL";

	// Validation
	public static final String FIXED_VALUE = "FIXED_VALUE";
	public static final String RANGE = "RANGE";
	public static final String LOV = "LOV";
	public static final String TIME = "TIME";
	public static final String REGEX = "REGEX";

	// OTHER
	public static final String AGENCY_ID = "AGENCY_ID";
	public static final String FIELD_TYPE = "FIELD_TYPE";
	public static final String DATE = "DATE";
	public static final String FILE_DATE = "FILE_DATE";
	public static final String ACK_FILE_NAME = "ACK_FILE_NAME";
	public static final String ACK_FILE_DATE = "ACK_FILE_DATE";
	public static final String ACK_FILE_TIME = "ACK_FILE_TIME";
	public static final String ACK_FILE_STATUS = "ACK_FILE_STATUS";
	public static final String TRX_FILE_NAME = "TRX_FILE_NAME";
	public static final String RECON_FILE_NAME = "RECON_FILE_NAME";
	public static final String FROM_AGENCY = "FROM_AGENCY";
	public static final String TO_AGENCY = "TO_AGENCY";
	public static final String EXTERN_FILE_ID = "EXTERN_FILE_ID";
	public static final long Record_Threshold = 2;
	public static final String ACK_FILE_TYPE = "ACK_FILE_TYPE";
	//public static final long BATCH_RECORD_COUNT = 100;
	public static final long BATCH_RECORD_COUNT = 5;

	//

	public static final String TXN_FILE_EXTENSION = "TXN";
	public static final String UNDER_SCORE_CHAR = "_";
	public static final String ACK_EXT = ".ACK";

	// ACK Status
	public static final String ACK_STATUS_07 = "07";
	public static final String ACK_STATUS_00 = "00";
	public static final String ACK_STATUS_01 = "01";
	
	public static final String DEFAULT_TIMESTAMP = "1900-01-01T00:00:00-05:00";
	public static final String DEFAULT_DATE = "19000101";
	public static final String DEFAULT_TIME = "00000000";

	

	// T_TRAN_DETAIL

	public static final String LANE_TX_ID = "LANE_TX_ID";
	public static final String TX_EXTERN_REF_NO = "TX_EXTERN_REF_NO";
	public static final String TX_TYPE = "TX_TYPE";
	public static final String TX_SUB_TYPE = "TX_SUB_TYPE";
	public static final String TOLL_SYSTEM_TYPE = "TOLL_SYSTEM_TYPE";
	public static final String TOLL_REVENUE_TYPE = "TOLL_REVENUE_TYPE";
	public static final String ENTRY_TX_TIMESTAMP = "ENTRY_TX_TIMESTAMP";
	public static final String ENTRY_PLAZA_ID = "ENTRY_PLAZA_ID";
	public static final String ENTRY_LANE_ID = "ENTRY_LANE_ID";
	public static final String ENTRY_TX_SEQ_NUMBER = "ENTRY_TX_SEQ_NUMBER";
	public static final String ENTRY_DATA_SOURCE = "ENTRY_DATA_SOURCE";
	public static final String ENTRY_VEHICLE_SPEED = "ENTRY_VEHICLE_SPEED";
	public static final String PLAZA_AGENCY_ID = "PLAZA_AGENCY_ID";
	public static final String PLAZA_ID = "PLAZA_ID";
	public static final String LANE_ID = "LANE_ID";
	public static final String TX_DATE = "TX_DATE";
	public static final String TX_TIMESTAMP = "TX_TIMESTAMP";
	public static final String LANE_MODE = "LANE_MODE";
	public static final String LANE_STATE = "LANE_STATE";
	public static final String TRX_LANE_SERIAL = "TRX_LANE_SERIAL";
	public static final String DEVICE_NO = "DEVICE_NO";
	public static final String DEVICE_CODED_CLASS = "DEVICE_CODED_CLASS";
	public static final String DEVICE_IAG_CLASS = "DEVICE_IAG_CLASS";
	public static final String DEVICE_AGENCY_CLASS = "DEVICE_AGENCY_CLASS";
	public static final String TAG_AXLES = "TAG_AXLES";
	public static final String ACTUAL_CLASS = "ACTUAL_CLASS";
	public static final String ACTUAL_AXLES = "ACTUAL_AXLES";
	public static final String EXTRA_AXLES = "EXTRA_AXLES";
	public static final String PLATE_STATE = "PLATE_STATE";
	public static final String PLATE_NUMBER = "PLATE_NUMBER";
	public static final String PLATE_TYPE = "PLATE_TYPE";
	public static final String PLATE_COUNTRY = "PLATE_COUNTRY";
	public static final String READ_PERF = "READ_PERF";
	public static final String WRITE_PERF = "WRITE_PERF";
	public static final String PROG_STAT = "PROG_STAT";
	public static final String COLLECTOR_NUMBER = "COLLECTOR_NUMBER";
	public static final String IMAGE_CAPTURED = "IMAGE_CAPTURED";
	public static final String VEHICLE_SPEED = "VEHICLE_SPEED";
	public static final String SPEED_VIOLATION = "SPEED_VIOLATION";
	public static final String RECIPROCITY_TRX = "RECIPROCITY_TRX";
	public static final String IS_VIOLATION = "IS_VIOLATION";
	public static final String IS_LPR_ENABLED = "IS_LPR_ENABLED";
	//public static final String FULL_FARE_AMOUNT = "FULL_FARE_AMOUNT";
	public static final String ETC_FARE_AMOUNT = "ETC_FARE_AMOUNT";
	public static final String EXPECTED_REVENUE_AMOUNT = "EXPECTED_REVENUE_AMOUNT";
	public static final String DISCOUNTED_AMOUNT = "DISCOUNTED_AMOUNT";
	public static final String UNREALIZED_AMOUNT = "UNREALIZED_AMOUNT";
	public static final String EXT_FILE_ID = "EXT_FILE_ID";
	public static final String RECEIVED_DATE = "RECEIVED_DATE";
	public static final String ACCOUNT_TYPE = "ACCOUNT_TYPE";
	public static final String ACCT_AGENCY_ID = "ACCT_AGENCY_ID";
	public static final String ETC_ACCOUNT_ID = "ETC_ACCOUNT_ID";
	public static final String ETC_SUBACCOUNT = "ETC_SUBACCOUNT";
	public static final String DST_FLAG = "DST_FLAG";
	public static final String IS_PEAK = "IS_PEAK";
	public static final String FARE_TYPE = "FARE_TYPE";
	public static final String UPDATE_TS = "UPDATE_TS";
	public static final String AVC_CLASS = "AVC_CLASS";
	public static final String TAG_IAG_CLASS = "TAG_IAG_CLASS";
	public static final String TAG_CLASS = "TAG_CLASS";
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
	public static final String RESERVED = "RESERVED";
	public static final String ATP_FILE_ID = "ATP_FILE_ID";
	public static final String PLAN_CHARGED = "PLAN_CHARGED";
	public static final String POSTED_FARE_AMOUNT = "POSTED_FARE_AMOUNT";
	public static final String CASH_FARE_AMOUNT = "CASH_FARE_AMOUNT";
	public static final String AET_FLAG         = "AET_FLAG"; 

	public static final Long ZERO 		= 0L;
	public static final String NO      	= "N";
	public static final String ATRN     = "ATRN"; 
	public static final String XFER_CONTROL_ID = "XFER_CONTROL_ID";
	public static final String F = "F";

}
