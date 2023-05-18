package com.conduent.tpms.intx.constants;

import java.time.format.DateTimeFormatter;

public class IntxConstant {
	
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
	public static final String GET_HOME_LANE_PLAZA_LIST = "GET_HOME_LANE_PLAZA_LIST";
	public static final String GET_T_VEHICLE_CLASS = "GET_T_VEHICLE_CLASS";
	public static final String GET_T_DISTANCE_DISCOUNT = "GET_T_DISTANCE_DISCOUNT";
	public static final String GET_T_AWAY_TRANSACTION = "GET_T_AWAY_TRANSACTION";
	public static final String GET_T_CORR_AWAY_TRANSACTION = "GET_T_CORR_AWAY_TRANSACTION";
	public static final String DEVICE_PREFIX = "DEVICE_PREFIX";
	public static final String UPDATE_T_TRANS_TABLE = "UPDATE_T_TRANS_TABLE";
	public static final String UPDATE_DST_ATP_FILE_ID = "UPDATE_DST_ATP_FILE_ID";
	public static final String DELETE_REJECTED_TX = "DELETE_REJECTED_TX";
	public static final String DELETE_REJECTED_CORR = "DELETE_REJECTED_CORR";
	public static final String UPDATE_T_TRANS_TABLE_ETC_TX_STATUS = "UPDATE_T_TRANS_TABLE_ETC_TX_STATUS";
	public static final String UPDATE_EXPECTED_REVENUE_AMOUNT_AND_REVENUE_DATE = "UPDATE_EXPECTED_REVENUE_AMOUNT_AND_REVENUE_DATE";
	public static final String GET_T_AWAY_TRANSACTION_DUPLICATE_CHECK = "GET_T_AWAY_TRANSACTION_DUPLICATE_CHECK";
	public static final String GET_T_AWAY_TRANSACTION_DUPLICATE_CHECK_FRM_FPMS = "GET_T_AWAY_TRANSACTION_DUPLICATE_CHECK_FRM_FPMS";
	public static final String INSERT_IAG_FILE_STATISTICS = "INSERT_IAG_FILE_STATISTICS";
	public static final String INSERT_IA_FILE_STATS = "INSERT_IA_FILE_STATS";
	public static final String UPDATE_IA_FILE_STATS = "UPDATE_IA_FILE_STATS";
	public static final String UPDATE_IAG_FILE_STATISTICS = "UPDATE_IAG_FILE_STATISTICS";
	public static final String GET_IAG_FILE_STATISTICS = "GET_IAG_FILE_STATISTICS";
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
	public static final String INSERT_INTO_T_AGENCY_TX_PENDING = "INSERT_INTO_T_AGENCY_TX_PENDING";
	public static final String INSERT_INTO_T_FO_RECON_FILE_STATS = "INSERT_INTO_T_FO_RECON_FILE_STATS";
	public static final String INSERT_INTO_T_FO_FILE_STATS = "INSERT_INTO_T_FO_FILE_STATS";
	public static final String DELETE_FROM_T_EPS_INTX_LOG = "DELETE_FROM_T_EPS_INTX_LOG";
	public static final String DELETE_FROM_T_EPS_ITXN_CORRECTION_LOG = "DELETE_FROM_T_EPS_ITXN_CORRECTION_LOG";
	
	public static final String QUERY_PARAM_EXPECTED_REVENUE_AMOUNT = "EXPECTED_REVENUE_AMOUNT";
	public static final String QUERY_PARAM_REVENUE_DATE = "REVENUE_DATE";
	public static final String QUERY_PARAM_LANE_TX_ID = "LANE_TX_ID";
	public static final String QUERY_PARAM_INPUT_FILE_NAME = "INPUT_FILE_NAME";
	public static final String QUERY_PARAM_INTX_FILE_NAME = "INTX_FILE_NAME" ;
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
	public static final String  FILE_EXTENSION_DOT_INTX = ".INTX";
	public static final String  FILE_EXTENSION_INTX = "INTX";
	public static final String  FILE_EXTENSION_TEMP="TEMP";
	public static final String  FILE_EXTENSION_DOT_ITXN = ".ITXN";
	public static final String  FILE_EXTENSION_ITXN = "ITXN";
	
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
	public static final String  CONSTANT_PARENT_AGENCY_ID_008 = "008";
	public static final String  CONSTANT_PANYNJ_TOLL_CUTOFF_DATE = "PANYNJ_TOLL_CUTOFF_DATE";
	public static final String  CONSTANT_NYSTA_TOLL_CUTOFF_DATE = "NYSTA_TOLL_CUTOFF_DATE";
	
	
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
	
	public static final DateTimeFormatter LOCALDATEFORMATTER_yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
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
	public static final String FILE_NAME = "FILE_NAME";
	public static final String STATUS_ECTX = "ECTX";
	public static final String STATUS_REJC = "REJC";
	public static final int ZERO_INT = 0;
	public static final String GET_T_FILE_STATISTICS_BY_FILEID = "GET_T_FILE_STATISTICS_BY_FILEID";
	public static final String XFER_CONTROL_ID = "XFER_CONTROL_ID";
	public static final String UPDATE_RECORD_COUNT_IN_IA_FILE_STATS = "UPDATE_RECORD_COUNT_IN_IA_FILE_STATS";
	public static final String UPDATE_RECORD_COUNT_IN_IAG_FILE_STATISTICS = "UPDATE_RECORD_COUNT_IN_IAG_FILE_STATISTICS";
	public static final String EXTERNAL_FILE_ID = "EXTERNAL_FILE_ID";

}
