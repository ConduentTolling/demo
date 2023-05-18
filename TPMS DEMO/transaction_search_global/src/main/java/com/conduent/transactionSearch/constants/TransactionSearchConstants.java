package com.conduent.transactionSearch.constants;

import java.util.ArrayList;
import java.util.List;

public class TransactionSearchConstants {

	// DATE_CONSTANT
	public static final String DATE_YYYYMMDD = "YYYYMMDD";
	public static final String DATE_FORMATTER_YYYYMMDD = "yyyyMMdd";

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

	public static final String DEFAULT_TIMESTAMP = "1900-01-01T00:00:00-05:00";

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
	public static final String AVC_CLASS = "AVC_CLASS";
	public static final String TAG_IAG_CLASS = "TAG_IAG_CLASS";
	public static final String TAG_CLASS = "TAG_CLASS";
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
	public static final String FULL_FARE_AMOUNT = "FULL_FARE_AMOUNT";
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

	public static final String POSTED_DATE = "POSTED_DATE";
	public static final String ETC_TX_STATUS = "ETC_TX_STATUS";
	public static final String ATP_FILE_ID = "ATP_FILE_ID";
	public static final String AET_FLAG = "AET_FLAG";
	public static final String TRANSPONDER_NUMBER = "DEVICE_NO";
	
	public static final String DATE = "DATE";
	public static final String TIME = "TIME";
	public static final String REGEX = "REGEX";
	public static final String CATEGORY = "CATEGORY";
    public static final String ACCOUNT_ID = "ACCOUNT_ID";
    
    public static final String ACCOUNT_NO ="ACCOUNT_NO";
    public static final String INVOICE_NUMBER = "INVOICE_NUMBER";
    public static final String AGENCY_NAME ="AGENCY_NAME";

    public static final String TYPE ="TX_TYPE";
    public static final String STATUS ="TX_STATUS";
    public static final String FROM_DATE="TX_DATE";
    public static final String TO_DATE="TX_DATE";
    
    public static final String INVOICE_NO="INVOICE_NO";
	public static final String ESCALATION_LEVEL="ESCALATION_LEVEL";
	
	public static final String EXIT_EXTERN_LANE_ID = "EXIT_EXTERN_LANE_ID";
	public static final String EXIT_EXTERN_PLAZA_ID = "EXIT_EXTERN_PLAZA_ID";
    
	public static final String[] VALID_FILTER_FIELD_NAMES = new String[] { "transactionFromDate", "transactionToDate",
			"postedFromDate", "postedToDate", "transponderNumber", "accountNumber", "plateNumber", "plateState",
			 "laneTxId", "extReferenceNo", "plazaId", "laneId","page","size","sortType","etcAccountId" };

	public static final String[][] VALID_FILTER_FIELD_NAMES_WITH_DATA_TYPES = new String[][] {
			{ "transactionFromDate", "Date" }, { "transactionToDate", "Date" }, { "postedFromDate", "Date" },
			{ "postedToDate", "Date" }, { "transponderNumber", "String" }, { "accountNumber", "String" },
			{ "plateNumber", "String" }, { "plateState", "String" }, { "transponderNumber", "String" },
			{ "laneTxId", "Number" }, { "extReferenceNo", "String" }, { "plazaId", "String" }, { "laneId", "String" } };
	
	public static final String[] VALID_GLOBAL_FIELD_NAMES = new String[] { "transactionFromDate", "transactionToDate",
					"postedFromDate", "postedToDate", "transponderNumber", "accountNumber", "plateNumber", "plateState",
					"transponderNumber", "laneTxId", "extReferenceNo", "plazaId", "laneId","category","agencyShortName","deviceNo","etcAccountId","txStatus",
					"sortType","page","size","status","type","escLevel","invoiceNo","fromDate","toDate","invoiceNo","escLevel"};
	public static final String[][] VALID_GLOBAL_FIELD_NAMES_WITH_DATA_TYPES = new String[][] {
		{ "transactionFromDate", "Date" }, { "transactionToDate", "Date" }, { "postedFromDate", "Date" },
		{ "postedToDate", "Date" }, { "transponderNumber", "String" }, { "accountNumber", "String" },
		{ "plateNumber", "String" }, { "plateState", "String" }, { "transponderNumber", "String" },
		{ "laneTxId", "Number" }, { "extReferenceNo", "String" }, { "plazaId", "String" }, { "laneId", "String" } ,{"category","String"},
		{"agencyShortName","String"},{"deviceNo","String"},{"etcAccountId","Number"},{"txStatus","String"},
		{"status","String"},{"type","String"},{"escLevel","String"},{"invoiceNo","String"},{"fromDate","Date"},{"toDate","Date"}};
	
/*
	 * Account Number (only with combination of Tx From/To or Posted From/To dates –
	 * date range can’t exceed 30 days) Plate Number and Plate State (Plate state
	 * optional) (only with combination of Tx From/To or Posted From/To dates – date
	 * range can’t exceed 30 days) Transponder Number (only with combination of Tx
	 * From/To or Posted From/To dates – date range can’t exceed 30 days) Plaza and
	 * Lane (Lane optional) (only with combination of Tx From/To or Posted From/To
	 * dates – date range can’t exceed 5 days)
	 * 
	 */

	public static final String[] FILTER_FIELDS_ALLOWED_WITH_DATE_FIELDS = new String[] { "accountNumber",
			"transponderNumber", "plateNumber", "plateState", "plazaId", "laneId" ,"invoiceNo", "escLevel"};

	/*
	 * Tx From Date and To Date (only with combination of other fields) Posted From
	 * Date and To Date (only with combination of other fields)
	 */
	public static final String[] FILTER_DATE_FIELD_NAMES = new String[] { "transactionFromDate", "transactionToDate",
			"postedFromDate", "postedToDate" };

	/*
	 * Lane Tx ID Ext Reference ID
	 */
	public static final String[] FILTER_INDEPENDENT_FIELD_NAMES = new String[] { "laneTxId", "extReferenceNo" };

	public static final String[][] FILTER_DATE_FIELD_NAMES_PAIR = new String[][] {
			{ "transactionFromDate", "transactionToDate" }, { "transactionToDate", "transactionFromDate" },
			{ "postedFromDate", "postedToDate" }, { "postedToDate", "postedFromDate" } };

	public static final String[] FILTER_NON_DATE_FIELD_NAMES = new String[] { "transponderNumber", "accountNumber",
			"plateNumber", "plateState", "laneTxId", "extReferenceNo", "plazaId", "laneId","page","size","sortType","invoiceNo","escLevel" };

	public static final String[] FILTER_WITH_PLATE_NUMBER_AND_OPTIONAL_PLATE_STATE = new String[] { "plateNumber",
			"plateState" };

	public static final String[] FILTER_WITH_PLAZA_AND_OPTIONAL_LANE_ID = new String[] { "plazaId", "laneId" };

	public static final String[] SEARCH_RESPONSE_FIELD_NAMES = new String[] { "transponderNumber", "plateNumber",
			"postedDate", "transactionDateTime", "entryTransactionDateTime", "updateTimestamp", "accountNumber",
			"txStatus", "discFare", "exitPlazaDescription", "entryPlazaDescription", "exitLane", "entryLane",
			"agencyStatementName", "vehicleClass", "disputedFlag", "disputedAmount", "planName", "imageURL", "laneTxID",
			"extReferenceNo" ,"escLevel","invoiceNo"};

	public static final String[] VALID_SORT_FIELD_NAMES = new String[] { "transponderNumber", "plateNumber",
			"postedDate", "transactionDateTime"	};

	public static final int DEFAULT_NUMBER_OF_RECORDS = 20;
	public static final int DEFAULT_PAGE_NO = 0; // Starting page Number
	public static final int DATE_RANGE_FOR_OTHER_FIELDS_OTHER_THAN_PLAZA_LANE = 30;
	public static final int DATE_RANCE_FOR_PLAZA_LANE_SEARCH = 5;
	public static final int MAXIMUM_NUMBER_OF_ROWS_TO_BE_RETRIEVED_FROM_DATABASE = 200;
	public static final String TRANSCATION_SEARCH_DATE_FORMAT = "dd-MM-yyyy";
	public static final String TRANSACTION_SEARCH_DATE_FORMAT_IN_SQL = "DD-MM-YYYY";
	public static final String RANGE_CHECK_MESSAGE = "Date Range Specified ";
	public static final String INVALID_FIELD_NAMES_SPECIFIED_IN_FILTER_CLAUSE = "Invalid Field Names specified in filter request";
	public static final String DUPLICATE_FIELD_NAMES_SPECIFIED_IN_FILTER_CLAUSE = "Duplicate Field Names specified in filter request";
	public static final String NO_VALUE_SPECIFIED_FOR_FIELD_NAME_IN_FILTER_CLAUSE = "No Value Specified for Field Names specified in filter request";
	public static final String INVALID_TRANSACTION_DATE_RANGE_SPECIFIED_IN_FILTER_CLAUSE = "Invalid Date Range Specified for Transaction Date in filter request";
	public static final String INVALID_POSTED_DATE_RANGE_SPECIFIED_IN_FILTER_CLAUSE = "Invalid Date Range Specified for Posted Date in filter request";
	public static final String EITHER_TRANSACTION_DATE_OR_POSTED_DATE_ALLOWED = "Either Transaction Date or Posted Date Range can be specified in filter request";
	public static final String CANNOT_CLUB_LANE_TX_ID_AND_EXT_REFERENCE_ID_WITH_OTHER_FIELDS = "Can't club Lane Transaction Id and External Reference Number With Other Fields";
	public static final String CANNOT_CLUB_DATE_WITH_MORE_THAN_ONE_GENERIC_FIELD = "When you specify date range you are allowed to specify atleast one account number or transponder number or plate number or plate state ";
	public static final String CAN_CLUB_PLATE_STATE_WITH_PLATE_NUMBER_ONLY = "Plate State can be specified only with Plate Number";
	public static final String CAN_CLUB_LANE_ID_WITH_PLAZA_ONLY = "Lane Id can only be specifed with Plaza Id";
	public static final String DATE_RANGE_EXCEEDS_FOR_PLAZA_SEARCH = "Date Range for plaza / Lane search exceeds ";
	public static final String DATE_RANGE_EXCEEDS_FOR_OTHER_FIELDS_SEARCH = "Date Range for search fields exceeds ";
	public static final String INVALID_SORT_FIELD_NAME_SPECIFIED = "Invalid Sort Field Name Specified in the request";
	public static final String EITHER_LANE_TX_ID_OR_EXT_REFERENCE_NO_CAN_BE_SPECIFIED = "Either Lane Tx Id or External Reference Number can be specified";
	public static final String CANNOT_GIVE_BOTH_TRANSACTION_DATE_POSTED_DATE_IN_FILTER_CLAUSE="Both Transaction and Posted Date can't be given in the filter request";
    public static final String RESPONSE_SUCCESSED_MESSAGE = "Transaction Search  Successful";
    public static final String RESPONSE_FAILED_MESSAGE = "Transaction Search  Failed";
    public static String RESPONSE_ERROR_MESSAGE = "";
    public static List<String> RESPONSE_FIELD_ERRORS = new ArrayList<String>();
    public static final String CANNOT_CLUB_TRANSPONDER_NUMBER_PLATENUMBER_PLATESTATE_PLAZA_LANEID_INVOICENO_ESCLEVEL_IN_ONE_GO="Transponder Number or Plate Number / PlateState or PlazaId/LaneId or Invoice Number or Esc Level cannot club together. Transponder Number can be specified or Combination of Plate Number with optional Plate State can be specified or Combination of Plaza Id with optional Lane Id can be specified ";
    public static final String CANNOT_SPECIFY_INDEPENDENT_FIELD_NAME_ALONG_WITH_DATE_FIELDS="Lane Tx Id or External Reference Number cannot be specified along with date field criteria";
	public static final String EITHER_TRANSACTION_DATE_OR_POSTED_DATE_ALLOWED_FOR_DATE_ALLOWED_FIELD="Either Transaction Date or Posted Date Range can be specified in filter request";
	public static final String CANNOT_CLUB_ACCOUNT_NO_WITH_OTHER_FIELD="Account Number can be specified only with Transaction/Posted date field.";
	public static final String INVOICE_NUMBER_AND_ESCALATION_LEVEL_CANNOT_BE_COMBINED_WITH_OTHER_FIELDS="Invoice Number or Escalation level cannot be combined with other fields";
	public static final String INVOICE_NUMBER_AND_ESCALATION_LEVEL_CANNOT_BE_CLUBBED="Invoice Number cannot be clubbed with Escalation Level";    
	public static final String DATE_FIELDS_CANNOT_BE_SEARCHED_INDEPENDENTLY="Date Fields Cannot be searched Independently";
}
