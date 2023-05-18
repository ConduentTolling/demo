package com.conduent.tpms.iag.constants;

/**
 * Constant class
 * 
 * @author Urvashi C
 *
 */
public class Constants {
	//ITAG file name params
	public static final String AGENCY_ID = "AGENCY_ID";
	public static final String TSI = "TSI";
	public static final String MTAG = "MTAG";
	public static final String NYSTA = "NYSTA";
	public static final String ITGU = "ITGU";
	public static final String ITAGICLP = "ITAGICLP";
	public static final String DEFAULT_COUNT = "00000000";
	public static final String DEFAULT_COUNT_TS = "0000000";
	public static final String TAG_ACCOUNT_INFO = "000000";
	public static final String DOT = ".";
	
	
	//TAG STATUS 
	public static final String GOOD = "GOOD";
	//public static final String LOW = "LOW";
	public static final String INVALID = "INVALID";
	public static final String LOW_BAL = "LOW BALANCE";
	public static final String LOST = "LOST";
	public static final String STOLEN = "STOLEN";
	public static final String VALID = "VALID";
	
	
	
	public static final String TOTAL = "TOTAL";
	public static final String TAG_SORTED_ALL = "TAG_SORTED_ALL";
	public static final String SKIP = "SKIP";
	
	//ITAG STATUS CODES
	public static final int TS_VALID = 1;
	public static final int TS_LOW = 2;
	public static final int TS_INVALID = 3;
	public static final int TS_LOST = 4;
	public static final int TS_STOLEN = 4;
	
	//DEVICE STATUS CODES
	public static final int DS_INVENTORY = 1;
	public static final String INVENTORY = "INVENTORY";
	
	
	//ACCT_FIN_STATUS
	public static final int FS_ZERO = 3;
	public static final int FS_LOW = 2;
	public static final String REPLENISH = "REPLENISH";
	public static final String MINIMUM = "MINIMUM";


	//AGENCY ID
	public static final String HOME_AGENCY_ID = "008";
	public static final int MTA_AGENCY_ID = 8;
	public static final int NYSTA_AGENCY_ID = 1; //4;
	public static final int PA_AGENCY_ID = 3;
	
	//ACCOUNT TYPES
	public static final int PRIVATE = 1;
	public static final int COMMERCIAL = 2;
	public static final int BUSINESS = 3;
	
	//QUERIES
	public static final String LOAD_T_AGENCY = "LOAD_T_AGENCY";
	public static final String SELECT_HOME_DEVICE_PREFIX = "SELECT_HOME_DEVICE_PREFIX";
	public static final String GEN_TYPE = "GEN_TYPE";
	public static final String UNDERSCORE = "_";
	public static final Object SUBACCOUNT_ID_DEFAULT = "************";
	public static final String STAT = "STAT";
	public static final String device_no = "device_no";
	
	
	//ITAG record properties
	public static final int ITAG_REC_LENGTH = 18;
	public static final int ITAG_FILE_DATE_END = 18;
	public static final int ITAG_FILE_DATE_START = 4;
	
	//MTAG
	public static final String MTAG_H_RECORD_TYPE_CODE = "H";
	public static final String MTAG_D_RECORD_TYPE_START = "[";
	public static final String MTAG_D_RECORD_TYPE_END = "]";
	public static final int MTAG_FILE_DATE_END = 18;
	public static final String MTAG_D_GROUP_ID = "065";
	public static final String MTAG_FILE_SERIAL_NO = "01";
	public static final String DEFAULT_ZERO ="0";
	public static final String DEFAULT_SPACE = " ";
	public static final String DEFAULT_PLAZA_VALUE = "   ";
	public static final String DEFAULT_THWY_VALUE = "         ";
	public static final int DEFAULT_lENGTH = 8;
	
	//Default constants
	public static final String YES = "Y";
	public static final String NO = "N";
	public static final int ZERO = 0;
	public static final int TWO = 2;
	public static final int THREE = 3;
	public static final int SEVEN = 7;
	public static final int EIGHT = 8;
	public static final int NINE = 9;
	
	//File specific constants
	public static final String FILE_GEN_TYPE_FULL = "FULL";
	public static final String FILE_GEN_TYPE_INCR = "INCR";
	public static final String FILE_TYPE = "FILE_TYPE";
	public static final Object FIXED = "FIXED";
	public static final String FILE_FORMAT = "FILE_FORMAT";
	public static final String STAT_FILES = "STAT_FILES";
	public static final String MTA_TSI_FILES = "MTA_TSI_FILES";
	public static final String ITAGICLP_FILES = "ITAGICLP_FILES";
	
	//Nysta MTAG mapping constants
	public static final String F_FILE_DATE = "F_FILE_DATE";
	public static final String F_FILE_SERIAL = "F_FILE_SERIAL";
	public static final String F_DOT = "F_DOT";
	public static final String F_FILE_EXTENSION = "F_FILE_EXTENSION";
	public static final String H_RECORD_TYPE_CODE = "H_RECORD_TYPE_CODE";
	public static final String H_RECORD_COUNT = "H_RECORD_COUNT";
	public static final String H_FILE_DATE_TIME = "H_FILE_DATE_TIME";
	public static final String H_FILE_SERIAL = "H_FILE_SERIAL";
	public static final String H_GOOD_BAL_TAG_COUNT = "H_GOOD_BAL_TAG_COUNT";
	public static final String H_LOW_BAL_TAG_COUNT = "H_LOW_BAL_TAG_COUNT";
	public static final String H_ZERO_BAL_TAG_COUNT = "H_ZERO_BAL_TAG_COUNT";
	public static final String H_LOST_TAG_COUNT = "H_LOST_TAG_COUNT";
	public static final String D_RECORD_TYPE_START = "D_RECORD_TYPE_START";
	public static final String D_GROUP_ID = "D_GROUP_ID";
	public static final String D_AGENCY_ID = "D_AGENCY_ID";
	public static final String D_SERIAL_NO = "D_SERIAL_NO";
	public static final String D_ACCOUNT_TYPE = "D_ACCOUNT_TYPE";
	public static final String D_FROM_PLAZA = "D_FROM_PLAZA";
	public static final String D_TO_PLAZA = "D_TO_PLAZA";
	public static final String D_THWY_ACCT = "D_THWY_ACCT";
	public static final String D_ETC_PLAN_CODES = "D_ETC_PLAN_CODES";
	public static final String D_RECORD_TYPE_END = "D_RECORD_TYPE_END";
	public static final String D_DEVICE_PREFIX = "D_DEVICE_PREFIX";
	public static final String LEFT = "L";
	public static final String RIGHT = "R";
	
	
	//MTA TS mapping constants
	public static final String TS = "TS";
	public static final String F_FILE_DATE_TIME = "F_FILE_DATE_TIME";
	public static final String H_SERVICE_CENTER = "H_SERVICE_CENTER";
	public static final String H_AGENCY_ID = "H_AGENCY_ID";
	public static final String H_PLAZA_ID = "H_PLAZA_ID";
	public static final String H_DOWNLOAD_TYPE = "H_DOWNLOAD_TYPE";
	public static final String H_LAST_DOWNLOAD_TS = "H_LAST_DOWNLOAD_TS";
	public static final String H_CURRENT_TS = "H_CURRENT_TS";
	public static final String H_STOLEN_TAG_COUNT = "H_STOLEN_TAG_COUNT";
	public static final String H_INVALID_TAG_COUNT = "H_INVALID_TAG_COUNT";
	public static final String H_INVENTORY_DEVICE_COUNT = "H_INVENTORY_DEVICE_COUNT";
	public static final String H_REPLENISH_TAG_COUNT = "H_REPLENISH_TAG_COUNT";
	public static final String H_MINUS_BAL_TAG_COUNT = "H_MINUS_BAL_TAG_COUNT";
	public static final String H_VALID_TAG_COUNT = "H_VALID_TAG_COUNT";
	public static final String D_RECORD_NUM = "D_RECORD_NUM";
	public static final String D_TAG_AGENCY_ID = "D_TAG_AGENCY_ID";
	public static final String D_TAG_IAG_CLASS = "D_TAG_IAG_CLASS";
	public static final String D_REV__TYPE = "D_REV__TYPE";
	public static final String D_CONTROL_INFORMATION = "D_CONTROL_INFORMATION";
	public static final String FILE_DWNL_COMPL = "C";
	public static final String FILE_DWNL_INCR = "I";
	public static final String T_RECORD_TYPE = "T_RECORD_TYPE";
	

}
