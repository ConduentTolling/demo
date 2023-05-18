package com.conduent.tpms.inrx.constants;

import java.time.format.DateTimeFormatter;

/**
 * This class contains constant for this service.
 * 
 * @author petetid
 *
 */
public class Constants {

	private Constants() {
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

	public static final String GET_T_AGENCY = "GET_T_AGENCY";
	public static final String GET_T_LANE = "GET_T_LANE";
	public static final String GET_T_VEHICLE_CLASS = "GET_T_VEHICLE_CLASS";
	public static final String GET_T_DISTANCE_DISCOUNT = "GET_T_DISTANCE_DISCOUNT";
	public static final String GET_T_AWAY_TRANSACTION = "GET_T_AWAY_TRANSACTION";
	public static final String DEVICE_PREFIX = "DEVICE_PREFIX";
	public static final String UPDATE_T_TRANS_TABLE = "UPDATE_T_TRANS_TABLE";
	public static final String UPDATE_DST_ATP_FILE_ID = "UPDATE_DST_ATP_FILE_ID";
	public static final String DELETE_REJECTED_TX = "DELETE_REJECTED_TX";
	public static final String UPDATE_T_TRANS_TABLE_ETC_TX_STATUS = "UPDATE_T_TRANS_TABLE_ETC_TX_STATUS";
	public static final String UPDATE_EXPECTED_REVENUE_AMOUNT_AND_REVENUE_DATE = "UPDATE_EXPECTED_REVENUE_AMOUNT_AND_REVENUE_DATE";
	public static final String GET_T_AWAY_TRANSACTION_DUPLICATE_CHECK = "GET_T_AWAY_TRANSACTION_DUPLICATE_CHECK";
	public static final String GET_T_AWAY_TRANSACTION_DUPLICATE_CHECK_FRM_FPMS = "GET_T_AWAY_TRANSACTION_DUPLICATE_CHECK_FRM_FPMS";
	public static final String INSERT_IAG_FILE_STATISTICS = "INSERT_IAG_FILE_STATISTICS";
	public static final String UPDATE_IAG_FILE_STATISTICS = "UPDATE_IAG_FILE_STATISTICS";
	public static final String GET_IAG_FILE_STATISTICS = "GET_IAG_FILE_STATISTICS";
	public static final String GET_ATP_FILE_ID = "GET_ATP_FILE_ID";
	public static final String GET_T_PLAZA="GET_T_PLAZA";
	public static final String GET_CUT_OFF_DATE = "GET_CUT_OFF_DATE";
	public static final String GET_QATP_STATISTICS = "GET_QATP_STATISTICS";
	public static final String GET_RECONCILIATION_CHECKPOINT_INFO = "GET_RECONCILIATION_CHECKPOINT_INFO";
	public static final String INSERT_INITIAL_ENTRY_RECONCILIATION_CHECKPOINT_INFO = "INSERT_INITIAL_ENTRY_RECONCILIATION_CHECKPOINT_INFO";
	public static final String UPDATE_RECONCILIATION_CHECKPOINT_INFO="UPDATE_RECONCILIATION_CHECKPOINT_INFO";
	public static final String UPDATE_RECONCILIATION_CHECKPOINT_STATUS = "UPDATE_RECONCILIATION_CHECKPOINT_STATUS";
	public static final String GET_ATP_FILE_SEQUENCE = "GET_ATP_FILE_SEQUENCE";
	public static final String GET_T_TOLL_POST_LIMIT_BY_AGENCY_AND_ETC_TX_STATUS = "GET_T_TOLL_POST_LIMIT_BY_AGENCY_AND_ETC_TX_STATUS";
	public static final String GET_TOLL_PRICES = "GET_TOLL_PRICES";
	

	public static final String QUERY_PARAM_EXPECTED_REVENUE_AMOUNT = "EXPECTED_REVENUE_AMOUNT";
	public static final String QUERY_PARAM_REVENUE_DATE = "REVENUE_DATE";
	public static final String QUERY_PARAM_LANE_TX_ID = "LANE_TX_ID";
	public static final String QUERY_PARAM_INPUT_FILE_NAME = "INPUT_FILE_NAME";
	public static final String QUERY_PARAM_OUTPUT_FILE_NAME = "OUTPUT_FILE_NAME";
	public static final String QUERY_PARAM_XFER_CONTROL_ID = "XFER_CONTROL_ID";
	public static final String QUERY_PARAM_ATP_FILE_ID = "ATP_FILE_ID";
	public static final String QUERY_PARAM_INPUT_REC_COUNT = "INPUT_REC_COUNT";
	public static final String QUERY_PARAM_OUTPUT_REC_COUNT = "OUTPUT_REC_COUNT";
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
	
	public static final String  FILE_EXTENSION_INTX = "INTX"; //".INTX";
	
	public static final String  CONSTANT_PERCENT = "%";
	public static final String  CONSTANT_UNDERSCORE = "_";
	
	public static final DateTimeFormatter DATEFORMATTER_yyyyMMdd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static final DateTimeFormatter DATEFORMATTER_yyyyMMddHHmmssSSS = DateTimeFormatter
			.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

	public static final String yyyyMMddHHmmssSSS = "yyyy-MM-dd HH:mm:ss.SSS";

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
	
	public static String AGENCY_ID = "AGENCY_ID";
	public static String GET_XFER_CONTROL_IDS = "GET_XFER_CONTROL_IDS";
	public static final String FILE_CONTROL_ID = "FILE_CONTROL_ID";
	public static final String F_FROM_AGENCY_ID = "F_FROM_AGENCY_ID";
	public static final String F_UNERSCORE = "F_UNERSCORE";
	public static final String F_TO_AGENCY_ID = "F_TO_AGENCY_ID";
	public static final String F_FILE_DATE_TIME = "F_FILE_DATE_TIME";
	public static final String F_DOT = "F_DOT";
	public static final String F_FILE_EXTENSION = "F_FILE_EXTENSION";
	
	public static final String FILE_TYPE = "FILE_TYPE";
	public static final Object FIXED = "FIXED";
	public static final String FILE_FORMAT = "FILE_FORMAT";
	public static final String INRX = "INRX";
	public static final int HOME_AGENCY_ID = 2;
	public static final String H_FILE_TYPE = "H_FILE_TYPE";
	public static final String H_FROM_AGENCY_ID = "H_FROM_AGENCY_ID";
	public static final String H_TO_AGENCY_ID = "H_TO_AGENCY_ID";
	public static final String H_FILE_DATE_TIME = "H_FILE_DATE_TIME";
	public static final String H_RECORD_COUNT = "H_RECORD_COUNT";
	public static final String H_INTX_FILE_NUM = "H_INTX_FILE_NUM";
	
	public static final String D_ETC_TRX_SERIAL_NUM = "D_ETC_TRX_SERIAL_NUM";
	public static final String D_ETC_POST_STATUS = "D_ETC_POST_STATUS";
	public static final String D_ETC_POST_PLAN = "D_ETC_POST_PLAN";
	public static final String D_ETC_DEBIT_CREDIT = "D_ETC_DEBIT_CREDIT";
	public static final String D_ETC_OWED_AMOUNT = "D_ETC_OWED_AMOUNT";
	public static final String PLAZA_AGENCY_ID = "PLAZA_AGENCY_ID";
	public static final String CODE_ID = "CODE_ID";
	public static final String FILENAME = "FILENAME";
	public static final String FILEDATE = "FILEDATE";
	public static final String PLAN_TYPE_ID = "PLAN_TYPE_ID";
	public static final String INRX_FILE_NAME = "INRX_FILE_NAME";
	public static final String PROCESSED_FLAG = "PROCESSED_FLAG";
	public static final String DEPOSIT_ID = "DEPOSIT_ID";
	public static final String FROM_AGENCY = "FROM_AGENCY";
	public static final String TO_AGENCY = "TO_AGENCY";
	public static final String FILE_SEQ_NUMBER = "FILE_SEQ_NUMBER";
	public static final String CREATE_INRX = "CREATE_INRX";
	public static final String CREATE_IRXN = "CREATE_IRXN";
	public static final String TEMP = "TEMP";
	public static final String IRXN = "IRXN";
	
}
