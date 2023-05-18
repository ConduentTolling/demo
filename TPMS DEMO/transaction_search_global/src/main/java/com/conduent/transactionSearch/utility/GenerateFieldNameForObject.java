package com.conduent.transactionSearch.utility;

import java.util.StringTokenizer;

public class GenerateFieldNameForObject {
	static String[] fieldNameArray = new String[] {
			"LANE_TX_ID",
			"TX_EXTERN_REF_NO",
			"TX_TYPE",
			"TX_SUB_TYPE",
			"TOLL_SYSTEM_TYPE",
			"TOLL_REVENUE_TYPE",
			"ENTRY_PLAZA_ID",
			"ENTRY_LANE_ID",
			"ENTRY_TX_SEQ_NUMBER",
			"ENTRY_DATA_SOURCE",
			"ENTRY_VEHICLE_SPEED",
			"PLAZA_AGENCY_ID",
			"PLAZA_ID",
			"LANE_ID",
			"TX_DATE",
			"LANE_MODE",
			"LANE_STATE",
			"TRX_LANE_SERIAL",
			"DEVICE_NO",
			"AVC_CLASS",
			"TAG_IAG_CLASS",
			"TAG_CLASS",
			"TAG_AXLES",
			"ACTUAL_CLASS",
			"ACTUAL_AXLES",
			"EXTRA_AXLES",
			"PLATE_STATE",
			"PLATE_NUMBER",
			"PLATE_TYPE",
			"PLATE_COUNTRY",
			"READ_PERF",
			"WRITE_PERF",
			"PROG_STAT",
			"COLLECTOR_NUMBER",
			"IMAGE_CAPTURED",
			"VEHICLE_SPEED",
			"SPEED_VIOLATION",
			"RECIPROCITY_TRX",
			"IS_VIOLATION",
			"IS_LPR_ENABLED",
			"FULL_FARE_AMOUNT",
			"DISCOUNTED_AMOUNT",
			"UNREALIZED_AMOUNT",
			"EXT_FILE_ID",
			"RECEIVED_DATE",
			"ACCOUNT_TYPE",
			"ACCT_AGENCY_ID",
			"ETC_ACCOUNT_ID",
			"ETC_SUBACCOUNT",
			"DST_FLAG",
			"IS_PEAK",
			"FARE_TYPE",
			"UPDATE_TS",
			"ETC_TX_STATUS",
			"DEPOSIT_ID",
			"POSTED_DATE",
			"LANE_DATA_SOURCE",
			"LANE_TYPE",
			"LANE_HEALTH",
			"AVC_AXLES",
			"TOUR_SEGMENT_ID",
			"BUF_READ",
			"READER_ID",
			"TOLL_AMOUNT",
			"DEBIT_CREDIT",
			"ETC_VALID_STATUS",
			"DISCOUNTED_AMOUNT_2",
			"VIDEO_FARE_AMOUNT",
			"PLAN_TYPE",
			"RESERVED",
			"ATP_FILE_ID",
			"TRANSACTION_INFO",
			"PLAN_CHARGED",
			"ETC_FARE_AMOUNT",
			"POSTED_FARE_AMOUNT",
			"EXPECTED_REVENUE_AMOUNT",
			"CASH_FARE_AMOUNT",
			"TX_STATUS",
			"AET_FLAG",
			"ENTRY_TX_TIMESTAMP",
			"TX_TIMESTAMP"

	};

	public static void main(String[] args) {
		String[] convertedFieldNameArray = new String[fieldNameArray.length];
		StringTokenizer st;
		String sb,tempString;
		int counter = 0;
		for (int i=0;i<fieldNameArray.length;i++) {
			st = new StringTokenizer(fieldNameArray[i],"_");
			counter=0;
			sb="";
			while (st.hasMoreTokens()) {
				if (counter++ ==0) {
					sb += st.nextToken().toLowerCase();
				} else {
					tempString = st.nextToken();
					sb+= tempString.substring(0, 0).toUpperCase()+tempString.substring(1,tempString.length()-1).toLowerCase();
				}

			}
			convertedFieldNameArray[i]=sb;        
		} 
		for(String str:convertedFieldNameArray) {
			System.out.println(str);
			
		}
	}
}
