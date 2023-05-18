package com.conduent.tpms.iag.constants;
public class Constants {
	//FTAG file name params
	public static final String AGENCY_ID = "AGENCY_ID";
	public static final String FTAG = "FTAG";
	
	public static final String DEFAULT_COUNT = "00000000";
	public static final String TAG_ACCOUNT_INFO = "000000";
	public static final String DOT = ".";
	
	//TAG STATUS 
	public static final String GOOD = "GOOD";
	//public static final String LOW = "LOW";
	public static final String INVALID = "INVALID";
	public static final String LOW_BAL = "LOW BALANCE";
	public static final String LOST = "LOST";
	
	
	public static final String TOTAL = "TOTAL";
	public static final String TAG_SORTED_ALL = "TAG_SORTED_ALL";
	public static final String SKIP = "SKIP";
	
	//DEVICE STATUS CODES
	public static final int TS_VALID = 1;
	public static final int TS_LOW = 2;
	public static final int TS_INVALID = 3;
	public static final int TS_LOST = 4;
	
	
	//public static final String DELIMITER = "LF";
	public static final String YES = "Y";
	public static final String NO = "N";
	public static final int ZERO = 0;
	

	//AGENCY ID
	public static final String HOME_AGENCY_ID = "008";
	public static final int MTA_AGENCY_ID = 2;
	
	
	//QUERIES
	public static final String LOAD_T_AGENCY = "LOAD_T_AGENCY";
	public static final String SELECT_HOME_DEVICE_PREFIX = "SELECT_HOME_DEVICE_PREFIX";
	public static final String GEN_TYPE = "GEN_TYPE";
	public static final String UNDERSCORE = "_";
	public static final String STAT = "STAT";
	
	
	//FTAG record properties
	public static final int FTAG_REC_LENGTH = 18;
	public static final int FTAG_FILE_DATE_END = 18;
	public static final int FTAG_FILE_DATE_START = 4;
	
	public static final String FILE_GEN_TYPE_FULL = "FULL";
	//public static final String FILE_GEN_TYPE_INCR = "INCR";
	
	public static final String FTAG_FILES = "FTAG_FILES";
	public static final String FTAG_STAT_FILES = "FTAG_STAT_FILES";
	
	public static final String FILE_TYPE = "FILE_TYPE";
	public static final String FILE_FORMAT = "FILE_FORMAT";
	public static final String FULL = "FULL";
	
}
