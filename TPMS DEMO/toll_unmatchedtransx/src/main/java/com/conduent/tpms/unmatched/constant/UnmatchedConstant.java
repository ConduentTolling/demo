package com.conduent.tpms.unmatched.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class contains constant for this service.
 * 
 * @author deepeshb
 *
 */
public class UnmatchedConstant {
	
	
	private UnmatchedConstant() {
	}
	
	
	public static final String VIOLATION_TYPE = "VIOLATION_TYPE";
	public static final Integer TRANSACTION_DAYS_GAP = 180;

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
	public static final String M = "M";
	public static final String D = "D";
	public static final String X = "X";
	public static final String W = "W";
	public static final String S = "S";
	public static final String Z = "Z";
	public static final String TOLL_SYSTEM_TYPE_B = "B";
	public static final String TOLL_SYSTEM_TYPE_P = "P";
	public static final String TOLL_SYSTEM_TYPE_C = "C";
	public static final String TOLL_SYSTEM_TYPE_T = "T";
	public static final String TOLL_SYSTEM_TYPE_E = "E";
	public static final String TOLL_SYSTEM_TYPE_X = "X";
	public static final String REBILL_TYPE_CC = "CC";
	public static final Integer ACCOUNT_STATUS_RVKW = 5;
	public static final Integer ACCOUNT_STATUS_RVKF = 6;
	public static final Integer VIOLATION_REVENUE_TYPE = 9;
	public static final Integer ACTIVE_DEVICE_STATUS = 3;
	public static final Integer ACTIVE_ACCOUNT_STATUS = 3;
	public static final Integer HOME_ACCOUNT_DEVICE = 1;
	public static final Integer DEFAULT_SPEED = 5000;
	public static final String DEFAULT_DEVICE_VALUE="***********";
	public static final String INSERT_INTO_T_AGENCY_TX_PENDING = "INSERT_INTO_T_AGENCY_TX_PENDING";
	
	public static final Integer SUSPENDED_POST_PAID_STATUS=2;
	public static final Integer ZERO_FIN_STATUS=3;
	public static final  List<Integer> SPEED_WARN_REVOKE_STATUS= Collections.unmodifiableList(Arrays.asList(2,6,7));
	public static final  List<Integer> IAG_INVALID_ZERO_STATUS= Collections.unmodifiableList(Arrays.asList(3,4));
	public static final  List<Integer> REBII_TYPE_CC= Collections.unmodifiableList(Arrays.asList(2,4,7,8));
	public static final  List<Integer> REBII_TYPE_CC_CASH_CHECK= Collections.unmodifiableList(Arrays.asList(1,6));
	public static final  List<Long> IAG_STATUS_FOR_AWAY_DEVICE= Collections.unmodifiableList(Arrays.asList(1l,2l));
	public static final  List<Long> IAG_STATUS_FOR_INVALID_AND_ZERO= Collections.unmodifiableList(Arrays.asList(3l,4l));
	
	public static final String yyyy_MM_dd= "yyyy-MM-dd";
	public static final String GET_DEVICE_SPECIAL_PLAN="GET_DEVICE_SPECIAL_PLAN";
	public static final String GET_ACCOUNT_SPEED_STATUS = "GET_ACCOUNT_SPEED_STATUS";
	public static final String GET_T_AGENCY_SCHEDULE_PRICING_BY_ID = "GET_T_AGENCY_SCHEDULE_PRICING_BY_ID";
	public static final String GET_T_QATP_STATISTICS="GET_T_QATP_STATISTICS";
	public static final String UPDATE_T_QATP_STATISTICS="UPDATE_T_QATP_STATISTICS";
	public static final String INSERT_INTO_QUEUE="INSERT_INTO_QUEUE";
	public static final String GET_TOLL_PRICES = "GET_TOLL_PRICES";
	public static final String GET_PRICING_SCHEDULE = "GET_PRICING_SCHEDULE";
	public static final String GET_HA_TAG_STATUS = "GET_HA_TAG_STATUS";
	public static final String GET_PROCESS_PARAMETER = "GET_PROCESS_PARAMETER";
	public static final String GET_DEVICE_STATUS = "GET_DEVICE_STATUS";
	public static final String GET_ACCOUNT_INFO = "GET_ACCOUNT_INFO";
	public static final String GET_H_DEVICE_STATUS = "GET_H_DEVICE_STATUS";
	public static final String FIND_DEVICE_AWAY = "FIND_DEVICE_AWAY";
	public static final String DEVICE_NUMBER = "DEVICE_NUMBER";
	public static final String TX_TIMESTAMP = "TX_TIMESTAMP";
	public static final String ETC_ACCOUNT_ID = "ETC_ACCOUNT_ID";
	public static final String DEVICE_NO = "DEVICE_NO";
	public static final String PARAM_NAME = "PARAM_NAME";
	public static final String AGENCY_ID = "AGENCY_ID";
	public static final String GET_T_AGENCY = "GET_T_AGENCY";
	public static final String CODE_TYPE = "CODE_TYPE";
	public static final String  GET_T_CODE = "GET_T_CODE";
	public static final String ACCT_ACT_STATUS = "ACCT_ACT_STATUS";
	public static final String ACCT_FIN_STATUS = "ACCT_FIN_STATUS";
	public static final String DEVICE_STATUS = "DEVICE_STATUS";
	public static final String ACCOUNT_TYPE = "ACCOUNT_TYPE";
	public static final String TX_STATUS = "TX_STATUS";
	public static final String QATP_RETAIL_TAG_CHECK = "QATP_RETAIL_TAG_CHECK";
	public static final Integer RET_TAG_UNREG = 1;
	public static final Long RET_TAG_UNREG_1 = 10L;
	public static final Integer PRIVATE = 1;
	public static final Integer COMMERCIAL = 2;
	public static final String IN_TX_DATE = "IN_TX_DATE";
	public static final String TX_DATE = "TX_DATE";
	public static final String GET_DEVICE_SPEED_STATUS = "GET_DEVICE_SPEED_STATUS";
	public static final String IN_ACCT_TYPE = "IN_ACCT_TYPE";
	public static final String IN_LANE_ID = "IN_LANE_ID";
	public static final String IN_AGENCY_ID = "IN_AGENCY_ID";
	public static final String GET_SPEED_THRESHOLD_FOR_LANE = "GET_SPEED_THRESHOLD_FOR_LANE";
	public static final String GET_SPEED_THRESHOLD_FOR_AGENCY = "GET_SPEED_THRESHOLD_FOR_AGENCY";
	public static final Integer DEFAULT_SPEED_LIMIT_THRESHOLD = 5000;
	public static final String IN_ACCOUNT_ID = "in_account_id";
	public static final String IN_DEVICE_NO = "in_device_no";
	public static final String INSERT_INTO_T_HOME_ETC_QUEUE = "INSERT_INTO_T_HOME_ETC_QUEUE";
	public static final String INSERT_INTO_T_VIOL_QUEUE = "INSERT_INTO_T_VIOL_QUEUE";
	
	
	//Compute Toll and Classification Constant
	public static final String BEYOND ="BEYOND";
	public static final String QINVAGENCY ="QINVAGENCY";
	public static final String QINVCLASS ="QINVCLASS";
	public static final String QINVEDATE ="QINVEDATE";
	public static final String QINVMODE ="QINVMODE";
	public static final String QINVPLAZA ="QINVPLAZA";
	public static final String QINVRECLEN ="QINVRECLEN";
	public static final String QINVTAGGRP ="QINVTAGGRP";
	public static final String QINVTRX ="QINVTRX";
	public static final String QINVXDATE ="QINVXDATE";
	public static final String QNONNUMVAL ="QNONNUMVAL";
	public static final String QNONVTRX ="QNONVTRX";
	public static final String QECTX ="QECTX";
	public static final String QITXC ="QITXC";
	public static final String QXTOL ="QXTOL";
	public static final String QDISC ="QDISC";
	public static final String QETOL ="QETOL";
	public static final String QFTOL ="QFTOL";
	public static final String QMTOL ="QMTOL";
	public static final String QUNMA ="QUNMA";
	public static final String QCTOL ="QCTOL";
	public static final String QVIOL  ="QVIOL";
	
	public static final String REVENUE_TYPE_ETC_STRING = "1";
	public static final String REVENUE_TYPE_VIOLATION_STRING = "9";
	public static final String REVENUE_TYPE_CASH_STRING = "3";
	public static final String VIOLATION_AET_REVENUE_TYPE = "60";
	
	public static final Integer REVENUE_TYPE_ETC = 1;
	public static final Integer REVENUE_TYPE_VIOLATION = 9;
	public static final Integer REVENUE_TYPE_CASH = 3;
	public static final Integer AET_VIOLATION_REVENUE_TYPE = 60;
	
	
	
	public static final Integer HOME_AGENCY_DEVICE = 1;
	public static final String VIOL_TX_STATUS = null;
	public static final String UPDATE_T_TRANS_TABLE = null;
	
	public static final String UPDATE_MATCHED_DEVICE_NUMBER_TX_ENTRY="UPDATE_MATCHED_DEVICE_NUMBER_TX_ENTRY";
	public static final String UPDATE_MATCHED_DEVICE_NUMBER_TX_EXIT="UPDATE_MATCHED_DEVICE_NUMBER_TX_EXIT";
	
	
	public final static String UNMATCHED_START_PROCESS_DATE = "UNMATCHED_START_PROCESS_DATE";
	public final static String UNMATCHED_END_PROCESS_DATE = "UNMATCHED_END_PROCESS_DATE";
	public final static String UNMATCHED_TIME_WINDOWS = "UNMATCHED_TIME_WINDOWS";
	public final static String IMAGE_TXN_START_PROCESS_DATE = "IMAGE_TXN_START_PROCESS_DATE";
	public final static String IMAGE_TXN_END_PROCESS_DATE = "IMAGE_TXN_END_PROCESS_DATE";
	public final static String IMAGE_PROCESSING_THRESHOLD = "IMAGE_PROCESSING_THRESHOLD";
	public final static String UNMATCHED_CROSSMATCHED_ALLOWED = "UNMATCHED_CROSSMATCHED_ALLOWED";
	
	public final static String UPDATE_DISCARD_TX_ENTRY = "UPDATE_DISCARD_TX_ENTRY";
	public static final String PARAM_GROUP ="PARAM_GROUP";
	public static final String UNMATCHED ="UNMATCHED";
	public static final String IMAGE_TXN ="IMAGE_TXN";
	public static final String UNMATCHED_CROSSMATCHED = "UNMATCHED_CROSSMATCHED";
	public static final String GET_UNMATCHED_EXIT="GET_UNMATCHED_EXIT";
	public static final String PROCESS_START_DATE ="PROCESS_START_DATE";
	public static final String PROCESS_END_DATE ="PROCESS_END_DATE";
	public static final Integer MATCHED_STATUS = 62;
	public static final Integer DISCARD_STATUS = 67;
	public static final Integer MATCHED_PLATE_STATUS = 64;
	
	public static final Integer EXPIRED_ENTRY_DISCARD_STATUS = 69;
	public static final Integer EXPIRED_EXIT_STATUS = 68;
	public static final Integer MATCHED_VIOL_STATUS = 653;
	public static final Integer NO_MATCHED_FOUND_VIOL_STATUS = 659;
	public static final Integer UNMATCHED_POSTED_STATUS = 66;
	
	public static final String UNMATCHED_POSTED = "66";
	
	public static final String UNMATCHED_MAX_TOLL_STATUS = "68";
	
	public static final String IMAGE_TXN_TIME_WINDOWS = "IMAGE_TXN_TIME_WINDOWS";
	public static final String GET_IMAGE_TRX_PROCESS ="GET_IMAGE_TRX_PROCESS";
	public static final String GET_MATCHED_STATUS_VOIL_TX ="GET_MATCHED_STATUS_VOIL_TX";
	public static final String UPDATE_DISCARD_TX_ENTRY_FOR_PLATE = "UPDATE_DISCARD_TX_ENTRY_FOR_PLATE";
	public static final String UPDATE_MATCHED_PLATE_NUMBER_TX_ENTRY="UPDATE_MATCHED_PLATE_NUMBER_TX_ENTRY";
	public static final String UPDATE_MATCHED_PLATE_NUMBER_VIOL_TX="UPDATE_MATCHED_PLATE_NUMBER_VIOL_TX";
	public static final String NY_PLAN_COUNT = "NY_PLAN_COUNT";
	public static final String GET_ETC_ACCOUNT_ID = "GET_ETC_ACCOUNT_ID";
	public static final String TPMS_UNMATCHED = "TPMS_UNMATCHED";
	public static final String GET_DEVICE_NUMBER = "GET_DEVICE_NUMBER";
	public static final String GET_ETC_ACC_FROM_PLATE = "GET_ETC_ACC_FROM_PLATE";
	public static final String GET_MCHING_DEVICE_USING_PLATE = "GET_MCHING_DEVICE_USING_PLATE";
	
	public static final String GET_PLATE_INFO = "GET_PLATE_INFO";
	public static final String GET_EXPIRE_ENTRY = "GET_EXPIRE_ENTRY";
	public static final String GET_EXPIRE_EXIT = "GET_EXPIRE_EXIT";
	public static final String GET_EXPIRED_VIOL_DATA = "GET_EXPIRED_VIOL_DATA";
	
	public static final String  GET_MAX_END_DATE = "GET_MAX_END_DATE";
	
	public static final String UPDATE_MATCHED_TRAN = "UPDATE_MATCHED_TRAN";
	
	public static final String UPDATE_EXPIRED_TRAN = "UPDATE_EXPIRED_TRAN";
	
	public static final String UPDATE_EXPIRED_EXIT = "UPDATE_EXPIRED_EXIT";
	
	public static final String UPDATE_EXPIRED_EXIT_TRAN = "UPDATE_EXPIRED_EXIT_TRAN";
	
	public static final String UPDATE_DISCARD_TRAN_FOR_REF = "UPDATE_DISCARD_TRAN_FOR_REF";
	
	
	public static final String GET_MCHING_PLATE_FROM_DEVICE = "GET_MCHING_PLATE_FROM_DEVICE";
	public static final String UPDATE_MATCHED_PLATE_NUMBER_TX_EXIT = "UPDATE_MATCHED_PLATE_NUMBER_TX_EXIT";
	
	public static final Integer EXTERN_PLAZA_25 = 21;
	public static final Integer EXTERN_PLAZA_25A = 22;
	public static final Integer DEFAULT_PLAN_TYPE = 1;
	public static final Integer REVENUE_TYPE_1 = 1;
	
	public static final String TPMS = "TPMS";
	
}
