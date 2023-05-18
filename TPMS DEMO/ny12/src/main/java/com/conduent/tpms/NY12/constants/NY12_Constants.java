package com.conduent.tpms.NY12.constants;

/**
 * This class is used to create service constants.
 * @author Saurabh
 * @version 1.0
 * @since 27-04-2021
 */
public class NY12_Constants {

	private NY12_Constants() {
	}
	public static final String LEFT_JUSTIFICATION = "L";
	public static final String RIGHT_JUSTIFICATION = "R";
	public static final String LAST_SECTION_ID="14";
	public static final String PARAM_NAME="TRAVEL-MIN_NY12";

	/*
	 * public String
	 * Query_Q1="select tpms.seq_ny12_complete_txn.nextval  into lv_complete_ref_no  from dual "
	 * ; public String Query_Q2="update t_ny12_pending_queue npq " +
	 * "           set npq.tx_complete_ref_no = lv_complete_ref_no " +
	 * "         where npq.lane_tx_id in (select regexp_substr(lv_lane_tx_id,'[^,]+', 1, level) from dual "
	 * +
	 * "                                  connect by regexp_substr(lv_lane_tx_id, '[^,]+', 1, level) is not null)"
	 * ; public String Query_Q3="select npq1.lane_tx_id,   npq1.tx_extern_ref_no, "
	 * + "                case when row_number() " +
	 * "                                      over (partition by npq1.tx_complete_ref_no order by npq1.tx_timestamp desc) = 1 "
	 * + "                then " +
	 * "                                first_value(npq1.tx_extern_ref_no) " +
	 * "                                over (partition by npq1.tx_complete_ref_no order by npq1.tx_timestamp) "
	 * + "                else " +
	 * "                                first_value(npq1.tx_extern_ref_no) " +
	 * "                                over (partition by npq1.tx_complete_ref_no order by npq1.tx_timestamp desc) "
	 * + "                end match_ref_no " +
	 * "                from  tpms.t_ny12_pending_queue npq1, " +
	 * "                                   tpms.t_ny12_pending_queue npq2 " +
	 * "                where npq1.tx_complete_ref_no = npq2.tx_complete_ref_no " +
	 * "                   and npq2.lane_tx_id = lv_lane_tx_id;"; public String
	 * Query_Q4="update tpms.t_ny12_pending_queue npq " +
	 * "    set npq.matched_tx_extern_ref_no = each_ltx.match_ref_no " +
	 * "    where npq.lane_tx_id = each_ltx.lane_tx_id;"; public String
	 * Query_Q5="insert into t_ny12_complete_txn nct  " +
	 * "                (LANE_TX_ID,TX_EXTERN_REF_NO, TX_COMPLETE_REF_NO, TX_MATCH_STATUS, TX_SEQ_NUMBER, EXTERN_FILE_ID, LANE_ID, TX_TIMESTAMP, TX_TYPE_IND, TX_SUBTYPE_IND, TOLL_SYSTEM_TYPE, LANE_MODE, LANE_TYPE, LANE_STATE, LANE_HEALTH, PLAZA_AGENCY_ID, PLAZA_ID, COLLECTOR_ID, TOUR_SEGMENT_ID, ENTRY_DATA_SOURCE, ENTRY_LANE_ID, ENTRY_PLAZA_ID, ENTRY_TIMESTAMP, ENTRY_TX_SEQ_NUMBER, ENTRY_VEHICLE_SPEED, LANE_TX_STATUS, LANE_TX_TYPE, TOLL_REVENUE_TYPE, ACTUAL_CLASS, ACTUAL_AXLES, ACTUAL_EXTRA_AXLES, COLLECTOR_CLASS, COLLECTOR_AXLES, PRECLASS_CLASS, PRECLASS_AXLES, POSTCLASS_CLASS, POSTCLASS_AXLES, FORWARD_AXLES, REVERSE_AXLES, DISCOUNTED_AMOUNT, COLLECTED_AMOUNT, UNREALIZED_AMOUNT, VEHICLE_SPEED, DEVICE_NO, ACCOUNT_TYPE, DEVICE_IAG_CLASS, DEVICE_AXLES, ETC_ACCOUNT_ID, ACCOUNT_AGENCY_ID, READ_AVI_CLASS, READ_AVI_AXLES, DEVICE_PROGRAM_STATUS, BUFFERED_READ_FLAG, LANE_DEVICE_STATUS, POST_DEVICE_STATUS, PRE_TXN_BALANCE, ETC_TX_STATUS, SPEED_VIOL_FLAG, IMAGE_TAKEN, PLATE_COUNTRY, PLATE_STATE, PLATE_NUMBER, REVENUE_DATE, ATP_FILE_ID, EXTERN_FILE_DATE, TX_DATE, CSC_LOOKUP_KEY, EVENT, HOV_FLAG, IS_RECIPROCITY_TXN, MATCHED_TX_EXTERN_REF_NO, STATUS_CD, PROCESSED_DATE, INSERT_DATE, UPDATE_DATE, SUMMARY_FLAG                                                                                                                                                                             ) "
	 * +
	 * "                select laNE_TX_ID,TX_EXTERN_REF_NO, TX_COMPLETE_REF_NO, TX_MATCH_STATUS, TX_SEQ_NUMBER, EXTERN_FILE_ID, LANE_ID, TX_TIMESTAMP, TX_TYPE_IND, TX_SUBTYPE_IND, TOLL_SYSTEM_TYPE, LANE_MODE, LANE_TYPE, LANE_STATE, LANE_HEALTH, PLAZA_AGENCY_ID, PLAZA_ID, COLLECTOR_ID, TOUR_SEGMENT_ID, ENTRY_DATA_SOURCE, ENTRY_LANE_ID, ENTRY_PLAZA_ID, ENTRY_TIMESTAMP, ENTRY_TX_SEQ_NUMBER, ENTRY_VEHICLE_SPEED, LANE_TX_STATUS, LANE_TX_TYPE, TOLL_REVENUE_TYPE, ACTUAL_CLASS, ACTUAL_AXLES, ACTUAL_EXTRA_AXLES, COLLECTOR_CLASS, COLLECTOR_AXLES, PRECLASS_CLASS, PRECLASS_AXLES, POSTCLASS_CLASS, POSTCLASS_AXLES, FORWARD_AXLES, REVERSE_AXLES, DISCOUNTED_AMOUNT, COLLECTED_AMOUNT, UNREALIZED_AMOUNT, VEHICLE_SPEED, DEVICE_NO, ACCOUNT_TYPE, DEVICE_IAG_CLASS, DEVICE_AXLES, ETC_ACCOUNT_ID, ACCOUNT_AGENCY_ID, READ_AVI_CLASS, READ_AVI_AXLES, DEVICE_PROGRAM_STATUS, BUFFERED_READ_FLAG, LANE_DEVICE_STATUS, POST_DEVICE_STATUS, PRE_TXN_BALANCE, ETC_TX_STATUS, SPEED_VIOL_FLAG, IMAGE_TAKEN, PLATE_COUNTRY, PLATE_STATE, PLATE_NUMBER, REVENUE_DATE, ATP_FILE_ID, EXTERN_FILE_DATE, TX_DATE, CSC_LOOKUP_KEY, EVENT, HOV_FLAG, IS_RECIPROCITY_TXN, MATCHED_TX_EXTERN_REF_NO, 'P'     status_cd, null    processed_date, sysdate insert_date, sysdate update_date, 'N' AS SUMMARY_FLAG "
	 * + "                from t_ny12_pending_queue npq " +
	 * "                where npq.lane_tx_id = lv_lane_tx_id; "; public String
	 * Query_Q6="update t_ny12_complete_txn nct " + "                set " +
	 * "                                nct.entry_plaza_id    = lv_entry_plaza, " +
	 * "                                nct.plaza_id          = lv_exit_plaza, " +
	 * "                                nct.entry_lane_id     = lv_entry_lane_id, "
	 * +
	 * "                                nct.entry_timestamp   = lv_entry_timestmp, "
	 * +
	 * "                                nct.discounted_amount = lv_discounted_amount, "
	 * +
	 * "                                nct.collected_amount  = lv_collected_amount, "
	 * +
	 * "                                nct.unrealized_amount = lv_unrealized_amount "
	 * + "                where nct.lane_tx_id = lv_lane_tx_id;"; public String
	 * Query_Q7="delete t_ny12_pending_queue npq " +
	 * "                where npq.tx_complete_ref_no = (select nct.tx_complete_ref_no "
	 * + "                                    from t_ny12_complete_txn nct " +
	 * "                                    where nct.lane_tx_id = lv_lane_tx_id); "
	 * ;
	 * 
	 * public String Query_Q8="select npq.lane_tx_id,  " +
	 * "                                npq.entry_plaza_id, " +
	 * "                                npq.plaza_id, " +
	 * "                                npq.entry_lane_id, " +
	 * "                                npq.entry_timestamp, " +
	 * "                                npq.discounted_amount, " +
	 * "                                npq.collected_amount, " +
	 * "                                npq.unrealized_amount, " +
	 * "                                npq.actual_class " +
	 * "   from t_ny12_pending_queue npq " + "where npq.actual_class <> 22; ";
	 * public String Query_Q9=" select npq.lane_tx_id, " +
	 * "                                  npq.entry_plaza_id, " +
	 * "                                  npq.plaza_id, " +
	 * "                                  npq.entry_lane_id, " +
	 * "                                  npq.entry_timestamp, " +
	 * "                                  npq.discounted_amount, " +
	 * "                                  npq.collected_amount, " +
	 * "                                  npq.unrealized_amount " +
	 * "                from t_ny12_pending_queue npq, " +
	 * "                                  t_lane tl " +
	 * "                where tl.plaza_id = npq.plaza_id " +
	 * "                  and tl.lane_id = npq.lane_id " +
	 * "                  and tl.section_id = lc_last_section_id; "; public String
	 * Query_Q10="select " + "                                npq.lane_tx_id, " +
	 * "                                npq.entry_plaza_id, " +
	 * "                                npq.plaza_id, " +
	 * "                                npq.entry_lane_id, " +
	 * "                                npq.entry_timestamp, " +
	 * "                                npq.discounted_amount, " +
	 * "                                npq.collected_amount, " +
	 * "                                npq.unrealized_amount " +
	 * "                  from " +
	 * "                                t_ny12_pending_queue npq, " +
	 * "                                t_plaza tp1, " +
	 * "                                t_plaza tp2 " +
	 * "                where tp1.plaza_id = npq.plaza_id " +
	 * "                   and tp2.plaza_id = npq.entry_plaza_id " +
	 * "                   and (tp1.time_limit_min is null or tp1.time_limit_min = 0) "
	 * +
	 * "                   and (tp2.time_limit_min is null or tp2.time_limit_min = 0)  ;  "
	 * ; public String Query_Q11="select npq.device_no " +
	 * "         from t_ny12_pending_queue npq " + "         join t_lane tl " +
	 * "           on (tl.plaza_id = npq.plaza_id and " +
	 * "               tl.lane_id = npq.lane_id) " +
	 * "          and tl.section_id <> lc_last_section_id   " +
	 * "        group by npq.device_no; "; public String
	 * Query_Q12="select outerq.*, " +
	 * "                 (case when min_diff < time_limit_min then 1 else 0 end) within_time, "
	 * +
	 * "                 (case when section_id != next_section_id then 1 else 0 end) new_section "
	 * + "            from (select mainq.*, " +
	 * "                         round((cast(next_entry_ts as date) - cast(tx_timestamp as date))* 24 * 60) as min_diff "
	 * + "                    from (select npq.lane_tx_id, " +
	 * "                                 npq.entry_plaza_id, " +
	 * "                                 npq.plaza_id, " +
	 * "                                 npq.entry_lane_id, " +
	 * "                                 tl.section_id, " +
	 * "                                 npq.entry_timestamp, " +
	 * "                                 npq.tx_timestamp, " +
	 * "                                 npq.discounted_amount, " +
	 * "                                 npq.collected_amount, " +
	 * "                                 npq.unrealized_amount, " +
	 * "                                 tp.time_limit_min, " +
	 * "                                 tpe.time_limit_min entry_time_limit, " +
	 * "                                 lead(entry_timestamp, 1, tx_timestamp) over (order by entry_timestamp) as next_entry_ts, "
	 * +
	 * "                                 lead(tl.section_id, 1, -1) over (order by entry_timestamp) as next_section_id "
	 * + "                            from t_ny12_pending_queue npq, " +
	 * "                                 t_plaza tp, " +
	 * "                                 t_plaza tpe, " +
	 * "                                 t_lane tl " +
	 * "                            where device_no = each_tag.device_no " +
	 * "                              and tp.plaza_id = npq.plaza_id " +
	 * "                              and tpe.plaza_id = npq.entry_plaza_id " +
	 * "                              and tl.lane_id = npq.lane_id " +
	 * "                              and tl.plaza_id = npq.plaza_id " +
	 * "                              and tl.section_id <> lc_last_section_id " +
	 * "                           order by device_no, entry_timestamp;"; public
	 * String[] QueryArray = new String[] {
	 * Query_Q1,Query_Q2,Query_Q3,Query_Q4,Query_Q5,Query_Q6,Query_Q7,Query_Q8,
	 * Query_Q9,Query_Q10,Query_Q11,Query_Q12};
	 */


}
