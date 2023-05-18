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
	public static final String ITAG = "ITAG";
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
	public static final String TAG_FROM_DATABASE="DBTAGs";
	
	//NYSTA RECORD CODES
	public static final String NYSBA_GOOD = "NYSBA_GOOD";
	public static final String NYSBA_LOW = "NYSBA_LOW";
	public static final String NYSBA_ZERO = "NYSBA_ZERO BALANCE";
	public static final String NYSBA_LOST = "NYSBA_LOST";
	
	
	//ITAG STATUS CODES
	public static final int TS_VALID = 1;
	public static final int TS_LOW = 2;
	//public static final int TS_INVALID = 3; //old 
	public static final int TAGREPLINISH = 3;
	public static final int TS_INVALID = 4;
	public static final int TS_LOST = 4;
	public static final int TS_STOLEN = 4;
	
	//DEVICE STATUS CODES
	public static final int DS_INVENTORY = 1;
	public static final String INVENTORY = "INVENTORY";
	public static final int STOLENSTATUS=9;
	public static final int LOSTSTATUS=8;
	public static final int DAMAGED=6;
	public static final int RETURNED=5;
	
	
	
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
	public static final int MTA_TS_AGENCY_ID=2;
	
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
	
	
	//Nysta tag status codes
	public static final int NTS_GOOD = 1;
	public static final int NTS_LOW = 2;
	public static final int NTS_ZERO = 3;
	public static final int NTS_LOST = 4;
	
	//Nysba tag status codes
	public static final int NBTS_GOOD = 1;
	public static final int NBTS_LOW = 2;
	public static final int NBTS_ZERO = 3;
	public static final int NBTS_LOST = 4;
	
	//Default constants
	public static final String YES = "Y";
	public static final String NO = "N";
//	public static final int ZERO = 0;
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
	public static final String TAG_FILES = "TAG_FILES";
	public static final String ITAGICLP_FILES = "ITAGICLP_FILES";
	
	//Nysta MTAG mapping constants
	public static final String F_FILE_DATE = "F_FILE_DATE";
	public static final String F_FILE_SERIAL = "F_FILE_SERIAL";
	public static final String F_DOT = "F_DOT";
	public static final String F_FILE_EXTENSION = "F_FILE_EXTENSION";
	public static final String H_RECORD_TYPE_CODE = "H_RECORD_TYPE_CODE";
	public static final String H_RECORD_COUNT = "H_RECORD_COUNT";
	public static final String H_PREV_FILE_NAME="H_PREV_FILE_NAME";
	public static final String H_FILE_DATE_TIME = "H_FILE_DATE_TIME";
	public static final String H_FILE_SERIAL = "H_FILE_SERIAL";
	public static final String H_GOOD_BAL_TAG_COUNT = "H_GOOD_BAL_TAG_COUNT";
	public static final String H_LOW_BAL_TAG_COUNT = "H_LOW_BAL_TAG_COUNT";
	public static final String H_ZERO_BAL_TAG_COUNT = "H_ZERO_BAL_TAG_COUNT";
	public static final String H_LOST_TAG_COUNT = "H_LOST_TAG_COUNT";
	public static final String H_GOOD_BAL_TAG_NYSBA_COUNT = "H_GOOD_BAL_TAG_NYSBA_COUNT";
	public static final String H_LOW_BAL_TAG_NYSBA_COUNT = "H_LOW_BAL_TAG_NYSBA_COUNT";
	public static final String H_ZERO_BAL_TAG_NYSBA_COUNT = "H_ZERO_BAL_TAG_NYSBA_COUNT";
	public static final String H_LOST_TAG_NYSBA_COUNT = "H_LOST_TAG_NYSBA_COUNT";
	public static final String D_RECORD_TYPE_START = "D_RECORD_TYPE_START";
	public static final String D_GROUP_ID = "D_GROUP_ID";
	public static final String D_AGENCY_ID = "D_AGENCY_ID";
	public static final String D_SERIAL_NO = "D_SERIAL_NO";
	public static final String D_ACCOUNT_TYPE = "D_ACCOUNT_TYPE";
	public static final String D_NYSTA_TAG_STATUS = "D_NYSTA_TAG_STATUS";
	public static final String D_FROM_PLAZA = "D_FROM_PLAZA";
	public static final String D_TO_PLAZA = "D_TO_PLAZA";
	public static final String D_THWY_ACCT = "D_THWY_ACCT";
	public static final String D_ETC_PLAN_CODES = "D_ETC_PLAN_CODES";
	public static final String D_NYSBA_TAG_STATUS = "D_NYSBA_TAG_STATUS";
	public static final String D_RECORD_TYPE_END = "D_RECORD_TYPE_END";
	public static final String D_DEVICE_PREFIX = "D_DEVICE_PREFIX";
	public static final String LEFT = "L";
	public static final String RIGHT = "R";
	
	
	//MTA TS mapping constants
	public static final String TS = "TS";
	public static final String F_FILE_DATE_TIME = "F_FILE_DATE_TIME";
	public static final String H_SERVICE_CENTER = "H_SERVICE_CENTER";
	public static final String H_HUB_ID_NAME="H_HUB_ID";
	public static final String H_HUB_ID="9003";
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
	public static final String D_TAG_TYPE="D_TAG_TYPE";
	public static final String D_CI1_TAG_STATUS="D_CI1_TAG_STATUS";
	public static final String D_CI2_VES ="D_CI2_VES";
	public static final String D_CI3_FUTURE_USE="D_CI3_FUTURE_USE";
	public static final String D_CI4_ACCOUNT_DEVICE_STATUS="D_CI4_ACCOUNT_DEVICE_STATUS";
	public static final String D_CI5_DISCOUNT_PLAN_FLAG_ORT="D_CI5_DISCOUNT_PLAN_FLAG_ORT";
	public static final String D_CI6_DISCOUNT_PLAN_FLAG_CBD="D_CI6_DISCOUNT_PLAN_FLAG_CBD";
	public static final String D_TAG_ACCOUNT_NO="D_TAG_ACCOUNT_NO";
	public static final String D_TAG_AC_TYPE_IND="D_TAG_AC_TYPE_IND";
	public static final String D_TAG_HOME_AGENCY="D_TAG_HOME_AGENCY";
	public static final String D_TAG_PROTOCOL="D_TAG_PROTOCOL";
	public static final String D_TAG_MOUNT="D_TAG_MOUNT";
	public static final String D_TAG_ACCT_INFO="D_TAG_ACCT_INFO";
	public static final String D_FILLER_FIELD_1="D_FILLER_FIELD1";
	public static final String D_FILLER_FIELD_2="D_FILLER_FIELD2";
	public static final String D_CONTROL_INFORMATION = "D_CONTROL_INFORMATION";
	public static final String FILE_DWNL_COMPL = "C";
	public static final String FILE_DWNL_INCR = "I";
	public static final String FILE_EXTENSION = "FILE_EXTENSION";
	public static final String FROM_DEVICE_PREFIX = "FROM_DEVICE_PREFIX";
	public static final String TO_DEVICE_PREFIX = "TO_DEVICE_PREFIX";
	public static final String FILE_DATE = "FILE_DATE";
	public static final String FILE_TIME_STRING = "FILE_TIME_STRING";
	public static final String RECORD_COUNT = "RECORD_COUNT";
	public static final String XFER_TYPE = "XFER_TYPE";
	public static final String PROCESS_STATUS = "PROCESS_STATUS";
	public static final String UPDATE_TS = "UPDATE_TS";
	public static final String FILE_NAME = "FILE_NAME";
	public static final String SELECT_AWAY_AGENCY_LIST = "SELECT_AWAY_AGENCY_LIST";
	public static final String SELECT_HOME_AGENCY = "SELECT_HOME_AGENCY";
	public static final String I = "I";
	public static final String CMPL = "CMPL";
	public static final String REC_TYPE="D";
	
	
	
	
	//INSERT_INTO_T_TAGSTATUS_STATISTICS params
	public static final String FILENAME = "FILENAME";
	public static final String FILEDATE = "FILEDATE";
	public static final String ITAG_MERGED = "ITAG_MERGED";
	public static final String DEVICE_PREFIX = "DEVICE_PREFIX";
	public static final String LBAL = "LBAL";
	public static final String VERSION = "VERSION";
	public static final String ZERO = "ZERO";
	public static final Object ITAG_MERGE_VERSION = "1.51";
	public static final String T_RECORD_TYPE = "T_RECORD_TYPE";
	public static final String T_REC_TYPE="E";
	
	
	public static final String FILE_Type="TS";

	public static final String isRFK="isRFK";
	public static final String RFK="R";
}
