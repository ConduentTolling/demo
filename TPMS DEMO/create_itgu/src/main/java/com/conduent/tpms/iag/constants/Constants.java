package com.conduent.tpms.iag.constants;

public class Constants {
	
	public static final String VERSION = "01.60.01";
	public final static String ITGU = "ITGU";
	public final static String GEN_TYPE = "GEN_TYPE";
	public static final String FILE_GEN_TYPE_FULL = "FULL";
	public static final String FILE_GEN_TYPE_INCR = "INCR";
	public static final String HOME_AGENCY_ID = "0008";
	public static final String SKIP = "SKIP";
	public static final String TAG_SORTED_ALL = "TAG_SORTED_ALL";
	public static final Object FIXED = "FIXED";
	public static final String FILE_FORMAT = "FILE_FORMAT";
	public static final String AGENCY_ID = "AGENCY_ID";
	public static final String FILE_TYPE = "FILE_TYPE";
	public static final String LOAD_T_AGENCY = "LOAD_T_AGENCY";
	public static final String SELECT_HOME_DEVICE_PREFIX = "SELECT_HOME_DEVICE_PREFIX";
	
	public static final String F_FROM_AGENCY_ID = "F_FROM_AGENCY_ID";
	public static final String F_FILE_DATE_TIME = "F_FILE_DATE_TIME";
	public static final String F_UNERSCORE = "F_UNERSCORE";
	public static final String F_FILE_EXTENSION = "F_FILE_EXTENSION";
	public static final String H_FILE_TYPE = "H_FILE_TYPE";
	public static final String H_FROM_AGENCY_ID = "H_FROM_AGENCY_ID";
	public static final String H_VERSION = "H_VERSION";
	public static final String H_PREV_FILE_DATE_TIME = "H_PREV_FILE_DATE_TIME";
	public static final String H_FILE_DATE_TIME = "H_FILE_DATE_TIME";
	public static final String H_FILE_TIME = "H_FILE_TIME";
	public static final String H_RECORD_COUNT = "H_RECORD_COUNT";
	public static final String H_COUNT_STAT1 = "H_COUNT_STAT1";
	public static final String H_COUNT_STAT2 = "H_COUNT_STAT2";
	public static final String H_COUNT_STAT3 = "H_COUNT_STAT3";
	public static final String H_COUNT_STAT4 = "H_COUNT_STAT4";
	public static final String D_TAG_AGENCY_ID = "D_TAG_AGENCY_ID";
	public static final String D_TAG_SERIAL_NUMBER_ID = "D_TAG_SERIAL_NUMBER_ID";
	public static final String D_TAG_STATUS = "D_TAG_STATUS";
	public static final String D_TAG_ACC_INFO = "D_TAG_ACC_INFO";
	public static final String D_TAG_HOME_AGENCY = "D_TAG_HOME_AGENCY";
	public static final String D_TAG_AC_TYPE_IND = "D_TAG_AC_TYPE_IND";
	public static final String D_TAG_ACCOUNT_NO = "D_TAG_ACCOUNT_NO";
	public static final String D_TAG_PROTOCOL = "D_TAG_PROTOCOL";
	public static final String D_TAG_TYPE = "D_TAG_TYPE";
	public static final String D_TAG_MOUNT = "D_TAG_MOUNT";
	public static final String D_TAG_CLASS = "D_TAG_CLASS";
	
	public static final String ITGU_FILES = "ITGU_FILES";
	public static final String UNDERSCORE = "_";
	public static final String DOT = ".";
	public static final String STAT_FILES = "STAT_FILES";
	public static final String STAT = "STAT";
	public static final String TOTAL="TOTAL";
	public static final String DEFAULT_COUNT = "0000000000";
	public static final String TAG_ACCOUNT_INFO = "000000";
	public static final int SIX = 6;
	public static final int ZERO = 0;
	public static final String ZERO_CHAR = "0";
	public static final String device_no = "device_no";
	
	public static final int TS_GOOD = 1;
	public static final int TS_LOW = 2;
	public static final int TS_INVALID = 3;
	public static final int TS_LOST = 4;
	
	public static final String GOOD = "GOOD";
	public static final String INVALID = "INVALID";
	public static final String LOW_BAL = "LOW BALANCE";
	public static final String LOST = "LOST";
	public static final String STOLEN = "STOLEN";
	
	// AGENCY ID
	public static final int MTA_AGENCY_ID = 2;
	public static final int NYSTA_AGENCY_ID = 1; //4;
	public static final int PA_AGENCY_ID = 3;
	
	// TAG record properties
	public static final int TAG_REC_LENGTH = 85;
	public static final int TAG_FILE_DATE_END = 19;
	public static final int TAG_FILE_DATE_START = 6;
	public static final int TAG_ACCOUNT_NO_LENGTH = 50;
	public static final int TAG_AGENCY_ID_LENGTH = 4;
	public static final int TAG_SERIAL_NO_LENGTH = 10;
	public static final int TAG_PROTOCOL = 3;
	public static final int TAG_CHAR_1 = 1;
	public static final int TAG_CLASS = 4;

}
