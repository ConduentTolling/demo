package com.conduent.tpms.ictx.constants;

import java.time.format.DateTimeFormatter;

/**
 * This class contains constant for this service.
 * 
 * @author deepeshb
 *
 */
public class IctxConstant {

	private IctxConstant() {
	}

	public static final String I = "I";
	public static final String V = "V";
	public static final String O = "O";
	public static final String T = "T";
	public static final String F = "F";
	public static final String Y = "Y";
	public static final String N = "N";
	public static final String R = "R";
	public static final String E = "E";
	public static final String ZERO = "0";
	public static final String G = "G";
	public static final String C = "C";
	public static final String U = "U";
	public static final String D = "D";
	public static final String W = "W";
	public static final String S = "S";
	public static final String Z = "Z";
	
	public static final int ZERO_VAL = 0;
	
	public static final Integer DEFAULT_PLAN_TYPE = 1;
	public static final Integer REVENUE_TYPE_1 = 1;
	public static final Integer PLAN_TYPE_217 = 217;
	public static final Integer REVENUE_TYPE_60 = 60;
	
	public static final String NJ_AGENCY = "NJ";

	public static final String GET_T_AGENCY = "GET_T_AGENCY";
	public static final String GET_T_LANE = "GET_T_LANE";
	public static final String GET_HOME_LANE_PLAZA_LIST = "GET_HOME_LANE_PLAZA_LIST";
	public static final String GET_T_VEHICLE_CLASS = "GET_T_VEHICLE_CLASS";
	public static final String GET_T_DISTANCE_DISCOUNT = "GET_T_DISTANCE_DISCOUNT";
	public static final String GET_T_AWAY_TRANSACTION = "GET_T_AWAY_TRANSACTION";
	public static final String GET_T_CORR_AWAY_TRANSACTION = "GET_T_CORR_AWAY_TRANSACTION";
	public static final String GET_T_CORR_AWAY_TRANSACTION_ALL_AGENCY = "GET_T_CORR_AWAY_TRANSACTION_ALL_AGENCY";
	public static final String GET_T_AWAY_TRANSACTION_ALL_AGENCY = "GET_T_AWAY_TRANSACTION_ALL_AGENCY";
	public static final String DEVICE_PREFIX = "DEVICE_PREFIX";
	public static final String UPDATE_T_TRANS_TABLE = "UPDATE_T_TRANS_TABLE";
	public static final String UPDATE_DST_ATP_FILE_ID = "UPDATE_DST_ATP_FILE_ID";
	public static final String DELETE_REJECTED_TX = "DELETE_REJECTED_TX";
	public static final String DELETE_REJECTED_TX_NEW = "DELETE_REJECTED_TX_NEW";
	public static final String DELETE_REJECTED_CORR = "DELETE_REJECTED_CORR";
	public static final String UPDATE_T_TRANS_TABLE_ETC_TX_STATUS = "UPDATE_T_TRANS_TABLE_ETC_TX_STATUS";
	public static final String GET_ITOL_AMOUNT_T_TRAN_DETAIL = "GET_ITOL_AMOUNT_T_TRAN_DETAIL";
	public static final String UPDATE_EXPECTED_REVENUE_AMOUNT_AND_REVENUE_DATE = "UPDATE_EXPECTED_REVENUE_AMOUNT_AND_REVENUE_DATE";
	public static final String GET_T_AWAY_TRANSACTION_DUPLICATE_CHECK = "GET_T_AWAY_TRANSACTION_DUPLICATE_CHECK";
	public static final String GET_T_AWAY_TRANSACTION_DUPLICATE_CHECK_NEW = "GET_T_AWAY_TRANSACTION_DUPLICATE_CHECK_NEW";
	public static final String GET_T_AWAY_TRANSACTION_DUPLICATE_CHECK_FRM_FPMS = "GET_T_AWAY_TRANSACTION_DUPLICATE_CHECK_FRM_FPMS";
	public static final String INSERT_IAG_FILE_STATISTICS = "INSERT_IAG_FILE_STATISTICS";
	public static final String INSERT_IA_FILE_STATS = "INSERT_IA_FILE_STATS";
	public static final String UPDATE_IA_FILE_STATS = "UPDATE_IA_FILE_STATS";
	public static final String UPDATE_IAG_FILE_STATISTICS = "UPDATE_IAG_FILE_STATISTICS";
	public static final String GET_IAG_FILE_STATISTICS = "GET_IAG_FILE_STATISTICS";
	public static final String GET_IAG_FILE_STATISTICS_BY_XFER_CONTROL_ID = "GET_IAG_FILE_STATISTICS_BY_XFER_CONTROL_ID";
	public static final String UPDATE_IAG_FILE_STATISTICS_VALUES = "UPDATE_IAG_FILE_STATISTICS_VALUES";
	public static final String GET_ATP_FILE_ID = "GET_ATP_FILE_ID";
	public static final String GET_T_PLAZA="GET_T_PLAZA";
	public static final String GET_CUT_OFF_DATE = "GET_CUT_OFF_DATE";
	public static final String GET_QATP_STATISTICS = "GET_QATP_STATISTICS";
	public static final String GET_QATP_STATISTICS_FROM_FILE_NAME = "GET_QATP_STATISTICS_FROM_FILE_NAME";
	public static final String GET_RECONCILIATION_CHECKPOINT_INFO = "GET_RECONCILIATION_CHECKPOINT_INFO";
	public static final String INSERT_INTIAL_ENTRY_RECONCILIATION_CHECKPOINT_INFO = "INSERT_INTIAL_ENTRY_RECONCILIATION_CHECKPOINT_INFO";
	public static final String UPDATE_RECONCILIATION_CHECKPOINT_INFO="UPDATE_RECONCILIATION_CHECKPOINT_INFO";
	public static final String UPDATE_RECONCILIATION_CHECKPOINT_STATUS = "UPDATE_RECONCILIATION_CHECKPOINT_STATUS";
	public static final String GET_ATP_FILE_SEQUENCE = "GET_ATP_FILE_SEQUENCE";
	public static final String GET_ATP_FILE_SEQUENCE_IAG = "GET_ATP_FILE_SEQUENCE_IAG";
	public static final String CHECK_ATP_FILE_ID_IN_IA_FILE_STATS = "CHECK_ATP_FILE_ID_IN_IA_FILE_STATS";
	public static final String GET_T_TOLL_POST_LIMIT_BY_AGENCY_AND_ETC_TX_STATUS = "GET_T_TOLL_POST_LIMIT_BY_AGENCY_AND_ETC_TX_STATUS";
	public static final String GET_TOLL_PRICES = "GET_TOLL_PRICES";
	public static final String ACCOUNT_AGENCY_ID = "ACCOUNT_AGENCY_ID";
	
	
	

	public static final String QUERY_PARAM_EXPECTED_REVENUE_AMOUNT = "EXPECTED_REVENUE_AMOUNT";
	public static final String QUERY_PARAM_REVENUE_DATE = "REVENUE_DATE";
	public static final String QUERY_PARAM_LANE_TX_ID = "LANE_TX_ID";
	public static final String QUERY_PARAM_INPUT_FILE_NAME = "INPUT_FILE_NAME";
	public static final String QUERY_PARAM_ICTX_FILE_NAME = "ICTX_FILE_NAME" ;
	public static final String QUERY_PARAM_OUTPUT_FILE_NAME = "OUTPUT_FILE_NAME";
	public static final String QUERY_PARAM_XFER_CONTROL_ID = "XFER_CONTROL_ID";
	public static final String QUERY_PARAM_PROCESSED_FLAG = "PROCESSED_FLAG";
	public static final String QUERY_PARAM_ATP_FILE_ID = "ATP_FILE_ID";
	public static final String QUERY_PARAM_INPUT_REC_COUNT = "INPUT_REC_COUNT";
	public static final String QUERY_PARAM_OUTPUT_REC_COUNT = "OUTPUT_REC_COUNT";
	public static final String QUERY_PARAM_INPUT_COUNT = "INPUT_COUNT";
	public static final String QUERY_PARAM_OUTPUT_COUNT = "OUTPUT_COUNT";
	public static final String QUERY_PARAM_FROM_AGENCY = "FROM_AGENCY";
	public static final String QUERY_PARAM_TO_AGENCY = "TO_AGENCY";
	public static final String QUERY_PARAM_UPDATE_TS = "UPDATE_TS";
	public static final String QUERY_PARAM_MTA_TOLL_CUTOFF_DATE = "MTA_TOLL_CUTOFF_DATE";
	public static final String QUERY_PARAM_MDTA_TOLL_CUTOFF_DATE = "MDTA_TOLL_CUTOFF_DATE";
	public static final String QUERY_PARAM_NYSTA_TOLL_CUTOFF_DATE = "NYSTA_TOLL_CUTOFF_DATE";
	public static final String QUERY_PARAM_PANYNJ_TOLL_CUTOFF_DATE = "PANYNJ_TOLL_CUTOFF_DATE";
	public static final String QUERY_PARAM_PARAM_NAME = "PARAM_NAME";
	public static final String QUERY_PARAM_FILE_NAME= "FILE_NAME";
	public static final String QUERY_PARAM_FILE_STATUS_IND = "FILE_STATUS_IND";
	public static final String QUERY_PARAM_RECORD_COUNT = "RECORD_COUNT";
	public static final String FILE_TYPE ="FILE_TYPE";
	public static final String  FILE_EXTENSION_DOT_ICTX = ".ICTX";
	public static final String  FILE_EXTENSION_ICTX = "ICTX";
	public static final String  FILE_EXTENSION_TEMP="TEMP";
	public static final String  FILE_EXTENSION_DOT_ITXC = ".ITXC";
	public static final String  FILE_EXTENSION_ITXC = "ITXC";
	public static final String  HUBFILE = "HUBFILE";
	
	public static final String  UPDATE_POSTED_TOLL_AMOUNT = "UPDATE_POSTED_TOLL_AMOUNT";
	public static final String  POSTED_FARE_AMOUNT = "POSTED_FARE_AMOUNT";
	public static final String  DISCOUNTED_AMOUNT = "DISCOUNTED_AMOUNT";
	public static final String  FULL_FARE_AMOUNT = "FULL_FARE_AMOUNT";
	public static final String  TX_EXTERN_REF_NO = "TX_EXTERN_REF_NO";
	
	public static final String  FILE_STATUS_COMPLETE="C";
	public static final String  FILE_STATUS_START="S";
	public static final String  FILE_STATUS_IN_PROGRESS="P";
	public static final String  PROCESSED_FLAG_VALUE="N";
	public static final String  FILE_DATE="FILE_DATE";
	public static final String  DEPOSIT_ID="DEPOSIT_ID";
	public static final String FILE_SEQ_NUMBER ="FILE_SEQ_NUMBER";
	
	
	public static final String  CONSTANT_PERCENT = "%";
	public static final String  CONSTANT_UNDERSCORE = "_";
	public static final String  CONSTANT_DOT = ".";
	public static final String  CONSTANT_DUPL = "DUPL";
	public static final String  CONSTANT_PARENT_AGENCY_ID_0008 = "0008";
	public static final String  CONSTANT_PARENT_HUB_9003 = "9003";
	public static final String  CONSTANT_PANYNJ_TOLL_CUTOFF_DATE = "PANYNJ_TOLL_CUTOFF_DATE";
	public static final String  CONSTANT_NYSTA_TOLL_CUTOFF_DATE = "NYSTA_TOLL_CUTOFF_DATE";
	public static final String  CONSTANT_VERSION = "01.60.02";
	
	
	public static final String  TOLL_SYSTEM_TYPE_C = "C";
	public static final String  TOLL_SYSTEM_TYPE_T = "T";
	public static final String  TOLL_SYSTEM_TYPE_X = "X";
	public static final String  TOLL_SYSTEM_TYPE_B = "B";
	public static final String  TOLL_SYSTEM_TYPE_U = "U";
	public static final String  TOLL_SYSTEM_TYPE_E = "E";
	
	
	public static final String  LANE_MODE_E = "E";
	public static final String  LANE_MODE_A = "A";
	public static final String  LANE_MODE_M = "M";
	public static final String  LANE_MODE_C = "C";
	
	public static final String  AGENCY_SHORT_NAME_PA= "PA";
	
	public static final DateTimeFormatter DATEFORMATTER_yyyyMMdd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static final DateTimeFormatter DATEFORMATTER_yyyyMMddHHmmssSSS = DateTimeFormatter
			.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

	public static final String FORMAT_yyyyMMddHHmmssSSS = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String FORMAT_yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_HHmmss ="HHmmss";
	public static final String FORMAT_yyyyMMdd ="yyyyMMdd";
	public static final String FORMAT_yyyy_MM_dd = "yyyy-MM-dd";

	// Compute Toll and Classification Constant
	public static final String BEYOND = "BEYOND";
	public static final String QINVAGENCY = "QINVAGENCY";
	public static final String QINVCLASS = "QINVCLASS";
	public static final String QINVEDATE = "QINVEDATE";
	public static final String QINVMODE = "QINVMODE";
	public static final String QINVPLAZA = "QINVPLAZA";
	public static final String QINVRECLEN = "QINVRECLEN";
	public static final String QINVTAGGRP = "QINVTAGGRP";
	public static final String QINVTRX = "QINVTRX";
	public static final String QINVXDATE = "QINVXDATE";
	public static final String QNONNUMVAL = "QNONNUMVAL";
	public static final String QNONVTRX = "QNONVTRX";
	public static final String QECTX = "QECTX";
	public static final String QITXC = "QITXC";
	public static final String QXTOL = "QXTOL";
	public static final String QDISC = "QDISC";
	public static final String QETOL = "QETOL";
	public static final String QFTOL = "QFTOL";
	public static final String QMTOL = "QMTOL";
	public static final String QUNMA = "QUNMA";
	public static final String QCTOL = "QCTOL";
	public static final String QVIOL = "QVIOL";

	public static final String GET_T_CODE = "GET_T_CODE";
	public static final String CODE_TYPE = "CODE_TYPE";
	public static final String ETC_TX_STATUS = "ETC_TX_STATUS";
	public static final String TX_STATUS = "TX_STATUS";
	public static final String FILE_NAME = "FILE_NAME";
	public static final String STATUS_ECTX = "ECTX";
	public static final String STATUS_REJC = "REJC";
	public static final int ZERO_INT = 0;
	public static final String GET_T_FILE_STATISTICS_BY_FILEID = "GET_T_FILE_STATISTICS_BY_FILEID";
	public static final String XFER_CONTROL_ID = "XFER_CONTROL_ID";
	public static final String UPDATE_RECORD_COUNT_IN_IA_FILE_STATS = "UPDATE_RECORD_COUNT_IN_IA_FILE_STATS";
	public static final String UPDATE_RECORD_COUNT_IN_IAG_FILE_STATISTICS = "UPDATE_RECORD_COUNT_IN_IAG_FILE_STATISTICS";
	
	//Data builder constants
	public static final Integer ETC_SERIAL_NO_LENGTH = 20;
	public static final int ETC_CORR_REASON_LENGTH = 2;
	public static final int ETC_REV_DATE_LENGTH = 8;
	public static final int ETC_FAC_AGENCY_LENGTH = 4;
	public static final int ETC_TRX_TYPE_LENGTH = 1;
	public static final int ETC_ENTRY_DATE_TIME_LENGTH = 25;
	public static final int ETC_ENTRY_PLAZA_LENGTH = 15;
	public static final int ETC_ENTRY_LANE_LENGTH = 3;
	public static final int ETC_TAG_AGENCY_LENGTH = 4;
	public static final int ETC_TAG_SERIAL_NO_LENGTH = 10;
	public static final int ETC_READ_PERF_LENGTH = 2;
	public static final int ETC_WRITE_PERF_LENGTH = 2;
	public static final int ETC_TAG_PGM_STATUS_LENGTH = 1;
	public static final int ETC_LANE_MODE_LENGTH = 1;
	public static final int ETC_VALIDATION_STATUS_LENGTH = 1;
	public static final int ETC_LIC_STATE_LENGTH = 2;
	public static final int ETC_LIC_NUMBER_LENGTH = 10;
	public static final int ETC_LIC_TYPE_LENGTH = 30;
	public static final int ETC_CLASS_CHARGED_LENGTH = 3;
	public static final int ETC_ACTUAL_AXLES_LENGTH = 2;
	public static final int ETC_EXIT_SPEED_LENGTH = 3;
	public static final int ETC_OVER_SPEED_LENGTH = 1;
	public static final int ETC_EXIT_DATE_TIME_LENGTH = 25;
	public static final int ETC_EXIT_PLAZA_LENGTH = 15;
	public static final int ETC_EXIT_LANE_LENGTH = 3;
	public static final int ETC_DEBIT_CREDIT_LENGTH = 1;
	public static final int ETC_TOLL_AMOUNT_LENGTH = 9;
	
	public static final int RECORD_LENGTH = 60;
	public static final int HEADER_LENGTH = 201;
	public static final long TX_STATUS_QICTX = 503;
	public static final int RECORD_COUNT_LOCATION_ICTX = 40;
	public static final int RECORD_COUNT_LENGTH_ICTX = 8;
}
