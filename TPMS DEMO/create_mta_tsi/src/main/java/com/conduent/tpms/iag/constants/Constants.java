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
	
	public static final String isRFK = "isRFK";
	public static final String R = "R";
	
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
	public static final int TS_GOOD = 1;
	public static final int TS_LOW = 2;
	public static final int TS_ZERO = 3;
	public static final int TS_AUTOPAY = 4;
	//public static final int TS_INVALID = 3;
	//public static final int TS_LOST = 4;
	//public static final int TS_STOLEN = 4;
	
	//DEVICE STATUS CODES
	public static final int DS_INVENTORY = 1;
	public static final int DS_RETURNED = 5;
	public static final int DS_DAMAGED = 6;
	public static final int DS_LOST = 8;
	public static final int DS_STOLEN = 9;
	public static final String INVENTORY = "INVENTORY";
	
	
	//ACCT_FIN_STATUS
	public static final int FS_ZERO = 3;
	public static final int FS_LOW = 2;
	public static final int FS_GOOD = 1;
	public static final String REPLENISH = "REPLENISH";
	public static final String MINIMUM = "MINIMUM";
	public static final String MINBAL = "MINBAL";
	

	//AGENCY ID
	public static final String HOME_AGENCY_ID = "0008";
	public static final int MTA_AGENCY_ID = 2; //8;
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
	public static final int SIX = 6;
	public static final int SEVEN = 7;
	public static final int EIGHT = 8;
	public static final int NINE = 9;
	public static final int TEN = 10;
	
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
	//public static final String F_DOT = "F_DOT";
	//public static final String F_FILE_EXTENSION = "F_FILE_EXTENSION";
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
	
	
	// MTA TS mapping constants
	public static final String TS = "TS";
	public static final String F_FROM_AGENCY_ID = "F_FROM_AGENCY_ID";
	public static final String F_FILE_DATE_TIME = "F_FILE_DATE_TIME";
	public static final String F_DOT = "F_DOT";
	public static final String F_FILE_EXTENSION = "F_FILE_EXTENSION";
	public static final String H_REC_TYPE = "H_REC_TYPE";
	public static final String H_SERVICE_CENTER = "H_SERVICE_CENTER";
	public static final String H_HUB_ID = "H_HUB_ID";
	public static final String H_AGENCY_ID = "H_AGENCY_ID";
	//public static final String H_PLAZA_ID = "H_PLAZA_ID";
	public static final String H_DWNLD_TYPE = "H_DWNLD_TYPE";
	public static final String H_LAST_TS = "H_LAST_TS";
	public static final String H_CURRENT_TS = "H_CURRENT_TS";
	public static final String H_STOLEN = "H_STOLEN";
	public static final String H_LOST = "H_LOST";
	public static final String H_INVALID = "H_INVALID";
	public static final String H_INVENTORY = "H_INVENTORY";
	public static final String H_REPLENISH = "H_REPLENISH";
	public static final String H_MINBAL = "H_MINBAL";
	public static final String H_VALID = "H_VALID";
	public static final String H_TOTAL = "H_TOTAL";
	public static final String H_PREV_FILE_NAME = "H_PREV_FILE_NAME";
	public static final String D_REC_TYPE = "D_REC_TYPE";
	public static final String D_REC_NUM = "D_REC_NUM";
	public static final String D_TAG_AGENCY_ID = "D_TAG_AGENCY_ID";
	public static final String D_TAG_SERIAL_NUM = "D_TAG_SERIAL_NUM";
	public static final String D_TAG_CLASS = "D_TAG_CLASS";
	public static final String D_REV_TYPE = "D_REV_TYPE";
	public static final String D_TAG_TYPE = "D_TAG_TYPE";
	public static final String D_CI1_TAG_STATUS = "D_CI1_TAG_STATUS";
	public static final String D_CI2_VES = "D_CI2_VES";
	public static final String D_CI3_FUTURE_USE = "D_CI3_FUTURE_USE";
	public static final String D_CI4_ACCOUNT_DEVICE_STATUS = "D_CI4_ACCOUNT_DEVICE_STATUS";
	public static final String D_CI5_DISCOUNT_PLAN_FLAG_ORT = "D_CI5_DISCOUNT_PLAN_FLAG_ORT";
	public static final String D_CI6_DISCOUNT_PLAN_FLAG_CBD = "D_CI6_DISCOUNT_PLAN_FLAG_CBD";
	public static final String D_TAG_ACCOUNT_NO = "D_TAG_ACCOUNT_NO";
	public static final String D_TAG_AC_TYPE_IND = "D_TAG_AC_TYPE_IND";
	public static final String D_TAG_HOME_AGENCY = "D_TAG_HOME_AGENCY";
	public static final String D_TAG_PROTOCOL = "D_TAG_PROTOCOL";
	public static final String D_TAG_MOUNT = "D_TAG_MOUNT";
	public static final String D_TAG_ACCT_INFO = "D_TAG_ACCT_INFO";
	public static final String D_FILLER_FIELD1 = "D_FILLER_FIELD1";
	public static final String D_FILLER_FIELD2 = "D_FILLER_FIELD2";
	public static final String T_REC_TYPE = "T_REC_TYPE";
	
	public static final String FILE_DWNL_COMPL = "C";
	public static final String FILE_DWNL_INCR = "I";
	public static final String PREV_FILE_NAME = "PREV_FILE_NAME";

}
