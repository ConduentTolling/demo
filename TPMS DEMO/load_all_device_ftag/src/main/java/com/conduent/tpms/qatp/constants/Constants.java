package com.conduent.tpms.qatp.constants;

/**
 * Constant class
 * 
 * @author Urvashi C
 *
 */
public class Constants {

	public static final String AGENCY_ID = "AGENCY_ID";
	public static final String START_DATE = "START_DATE";
	public static final String END_DATE = "END_DATE";
	public static final String lv_account_type = "lv_account_type";
	public static final String LV_ETC_ACCT_ID = "LV_ETC_ACCT_ID";
	public static final String START_DEVICE_NO = "START_DEVICE_NO";
	public static final String END_DEVICE_NO = "END_DEVICE_NO";
	public static final String LC_DEVICE_NO = "LC_DEVICE_NO";
	public static final String LC_ETC_ACCOUNT_ID = "LC_ETC_ACCOUNT_ID";
	public static final int UNREGISTERED = 1; //Y
	public static final int REGISTERED = 0;   //N
	public static final String YES = "Y";
	public static final String NO = "N";
	public static final int ZERO_VALUE = 0;
	public static final int RVKW = 5;
	public static final int RVKF = 6;
	public static final int ZERO = 0;
	public static final double D_ZERO = 0;
	public static final String MTA_TAG_STATUS = "MTA_TAG_STATUS";
	public static final String RIO_TAG_STATUS = "RIO_TAG_STATUS";
	public static final String ITAG_TAG_STATUS = "ITAG_TAG_STATUS";
	public static final String PA_TAG_STATUS = "PA_TAG_STATUS";
	public static final String TS_CTRL_STR = "TS_CTRL_STR";
	public static final String MTA_CTRL_STR = "MTA_CTRL_STR";
	public static final String RIO_CTRL_STR = "RIO_CTRL_STR";
	public static final String FINANCIAL_STATUS = "FINANCIAL_STATUS";
	public static final String UPDATE_TS = "UPDATE_TS";
	
	//Constants for MTA,RIO,ITAG Plaza
	public static final String PLAZA_ID = "PLAZA_ID";
	public static final int MTA_PLAZA_ID = 201;
	public static final int RIO_PLAZA_ID = 216;
	public static final int ITAG_PLAZA_ID = 1000;
	
	//For MTA
	public static final String OLD_MTAG_STATUS = "OLD_MTAG_STATUS";
	public static final String OLD_MTA_CONTROL_STRING = "OLD_MTA_CONTROL_STRING";	
	
	//For RIO
	public static final String OLD_RIO_STATUS = "OLD_RIO_STATUS";
	public static final String OLD_RIO_CONTROL_STRING = "OLD_RIO_CONTROL_STRING";
	
	//For PA
	public static final String OLD_PA_STATUS = "OLD_PA_STATUS";
	public static final String OLD_PA_CONTROL_STRING = "OLD_PA_CONTROL_STRING";
	
	//For UPDATION
	public static final String ACCOUNT_NO = "ACCOUNT_NO";
	public static final String ACCOUNT_STATUS = "ACCOUNT_STATUS";
	public static final String FIN_STATUS = "FIN_STATUS";
	public static final String DEVICE_STATUS = "DEVICE_STATUS";
	public static final String REBILL_PAY_TYPE = "REBILL_PAY_TYPE";
	public static final String ALL_PLANS = "ALL_PLANS";
	public static final String PREVALIDATION_FLAG = "PREVALIDATION_FLAG";
	public static final String SPEED_AGENCY = "SPEED_AGENCY";
	public static final String RETAIL_TAG_STATUS = "RETAIL_TAG_STATUS";
	public static final String POST_PAID_STATUS = "POST_PAID_STATUS";
	public static final String REV_PLAN_COUNT = "REV_PLAN_COUNT";
	public static final String MTAG_STATUS = "MTAG_STATUS";
	public static final String RIO_STATUS = "RIO_STATUS";
	public static final String ITAG_STATUS = "ITAG_STATUS";
	public static final String PA_STATUS = "PA_STATUS";
	public static final String PA_CTRL_STRING = "PA_CTRL_STRING";
	public static final String MTA_CTRL_STRING = "MTA_CTRL_STRING";
	public static final String RIO_CTRL_STRING = "RIO_CTRL_STRING";
	public static final String STR_N = "N";
	
	//GEN TYPE 
	public static final String F = "FULL";
	public static final String I = "INCREMENT";
	
	//HISTORY FLAG
	public static final String Y = "YES";
	public static final String N = "NO";
	
	//DEVICE STATUS CODES
	public static final int DS_INVENTORY = 1;
	public static final int DS_ACTIVE = 3;
	public static final int DS_LOST = 8;
	public static final int DS_STOLEN = 9;
	
	//FIN STATUS INT
	public static final int FS_GOOD = 1;
	public static final int FS_LOW = 2;
	public static final int FS_ZERO = 3;
	public static final int FS_AUTOPAY = 4;
	
	//FIN STATUS String
	public static final String  FS_GOOD_BAL = "GOOD";
	public static final String FS_LOW_BAL = "LOW BAL";
	public static final String FS_LOW_ = "LOW";
	public static final String FS_ZERO_BAL = "ZERO";
	public static final String FS_AUTOPAY_BAL ="AUTOPAY";
		
	//TAG STATUS
	public static final int TS_GOOD = 1;
	public static final int TS_LOW = 2;
	public static final int TS_INVALID = 3;        //changed to 3 as PB told
	public static final int TS_BAD = 5;
	public static final int TS_LOST_STOLEN = 4;   //PB told to make it common
	//public static final int STOLEN = 4;
	
	//PRIMARY REBILL PAY TYPE
	public static final int RPT_CASH = 1;
	public static final int RPT_MASTERCARD = 2;
	public static final int RPT_VISA = 4;
	public static final int RPT_CHECK = 6;
	public static final int RPT_AMEX = 7;
	public static final int RPT_DISCOVER = 8;
	public static final int RPT_CHECKING = 9;
	public static final int RPT_SAVINGS = 10;
	public static final int RPT_RETAILER = 12;
	public static final int RPT_DISC = 3;
	public static final int RPT_SACH = 3;    // check n do it
	public static final int RPT_ACHCPPT = 15;
	public static final int RPT_ACHSPPT = 16;
	
	//Process parameters
	public static final int ZERO_THRESHOLD = 0;
	public static final int REG_TIME_LIMIT = 168;
	
	//RETAILER STATUS
	public static final String RETAILER_STATUS_U = "U";
	public static final String RETAILER_STATUS_V = "V";
	public static final String RETAILER_STATUS_W = "W";
	public static final String RETAILER_STATUS_DEFAULT = "Z";
	
	//AGENCY ID
	public static final int MTA_AGENCY_ID = 2;
	public static final int NYSTA_AGENCY_ID = 1;
	public static final int PA_AGENCY_ID = 3;
	
	public static final String MTA_AGENCY_PREFIX = "016";
	public static final String NYSTA_AGENCY_PREFIX = "004";
	public static final String PA_AGENCY_PREFIX = "005";
	public static final String HOME_AGENCY_PREFIX = "008";
	
	//ACCOUNT TYPES
	public static final int PRIVATE = 1;
	public static final int COMMERCIAL = 2;
	public static final int BUSINESS = 3;
	
	//Plan String
	public static final String PA_GOOD = "000V00";
	public static final String PA_LOW = "000M00";
	public static final String PA_ZERO = "000N00";
	public static final String PA_RVKF = "LI0R00";
	public static final String PA_DEFAULT = "II0R00";
	public static final String PA_DS_LOST = "LI0R00";
	public static final String PA_DS_STOLEN = "SI0R00";
	public static final String PA_DS_INVENTORY = "RI0000";
	public static final String PA_SPEED = "IW0S00";
	
	public static final String MTA_GOOD = "000V00";
	public static final String MTA_RIO_GOOD = "000V00";
	public static final String MTA_LOW = "000M00";
	public static final String MTA_RIO_LOW = "000M00";
	public static final String MTA_RVKF = "LW0R00";
	public static final String MTA_RIO_RVKF = "LW0R00";
	public static final String MTA_DEFAULT = "IW0R00";
	public static final String MTA_RIO_DEFAULT = "IW0R00";
	public static final String MTA_DS_LOST = "LW0R00";
	public static final String MTA_RIO_DS_LOST = "LW0R00";
	public static final String MTA_DS_STOLEN = "SW0R00";
	public static final String MTA_RIO_DS_STOLEN = "SW0R00";
	public static final String MTA_DS_INVENTORY = "R00000";
	public static final String MTA_RIO_DS_INVENTORY = "R00000";
	public static final String MTA_SPEED = "IW0R00";
	public static final String MTA_RIO_SPEED = "IW0R00";
	
	//FTAG related Added constants
	public static final String IN ="I";
	public static final String OUT ="O";
	public static final int RPT_ACHPPT=0;
	public static final int RPT_SCHSPPT=0;
	
}
