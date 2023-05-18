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
	public static final String TAG_ACCOUNT_INFO = "000000";
	public static final String DOT = ".";
	public static final String VERSION = "01.60.01";

	
	//TAG STATUS 
	public static final String GOOD = "GOOD";
	//public static final String LOW = "LOW";
	public static final String INVALID = "INVALID";
	public static final String LOW_BAL = "LOW BALANCE";
	public static final String LOST = "LOST";
	public static final String STOLEN = "STOLEN";
	
	public static final String TOTAL = "TOTAL";
	public static final String TAG_SORTED_ALL = "TAG_SORTED_ALL";
	public static final String SKIP = "SKIP";
	
	//ITAG STATUS CODES
	public static final int TS_VALID = 1;
	public static final int TS_LOW = 2;
	public static final int TS_INVALID = 3;
	public static final int TS_LOST = 4;
	
	
	
	//public static final String DELIMITER = "LF";
	public static final String YES = "Y";
	public static final String NO = "N";
	public static final int ZERO = 0;
	

	//AGENCY ID
	public static final String HOME_AGENCY_ID = "0008";
	public static final int MTA_AGENCY_ID = 2;
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
	
	
	//ITAG record properties
	public static final int ITAG_REC_LENGTH = 85;
	public static final int ITAG_FILE_DATE_END = 19;
	public static final int ITAG_FILE_DATE_START = 6;
	
	public static final int ITAG_ACCOUNT_NO_LENGTH = 50;
	public static final int ITAG_AGENCY_ID_LENGTH = 4;
	public static final int ITAG_SERIAL_NO_LENGTH = 10;
	public static final int ITAG_PROTOCOL = 3;
	public static final int ITAG_CHAR_1 = 1;
	public static final int ITAG_CLASS = 4;


	//MTAG
	public static final String MTAG_H_RECORD_TYPE_CODE = "H";
	public static final String MTAG_D_RECORD_TYPE_START = "[";
	public static final String MTAG_D_RECORD_TYPE_END = "]";
	public static final int MTAG_FILE_DATE_END = 18;
	public static final String MTAG_D_ETC_TAG_ID_NO = "065";
	public static final String MTAG_FILE_SERIAL_NO = "01";
	
	
	public static final int NTS_NORMAL = 1;
	public static final int NTS_LOW = 2;
	public static final int NTS_ZERO = 3;
	public static final int NTS_LOST = 4;
	
	public static final int NBTS_GOOD = 1;
	public static final int NBTS_LOW = 2;
	public static final int NBTS_ZERO = 3;
	public static final int NBTS_LOST = 4;
	
	public static final String FILE_GEN_TYPE_FULL = "FULL";
	public static final String FILE_GEN_TYPE_INCR = "INCR";
	

}
