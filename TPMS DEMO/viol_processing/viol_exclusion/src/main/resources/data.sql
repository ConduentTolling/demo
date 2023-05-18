create table T_VIOL_TX_EXCLUSION
(
  "agency_id"        NUMBER(3,0),
  "plaza_id "        NUMBER(10,0),
  "lane_id "         NUMBER(10,0),
  "lane_mode"         NUMBER(10,0),  
  "viol_type"         NUMBER(10,0),
  "toll_revenue_type" NUMBER(10,0),
  "exclusion_stage"  NUMBER(5,0),
  "etc_account_id"    NUMBER(10,0),
  "plate_state"      VARCHAR2(2),
  "plate_number"     VARCHAR2(10),  
  "vehicle_speed"     NUMBER(5,0),
  "tx_type_ind"       VARCHAR2(1),
  "tx_subtype_ind"   VARCHAR2(1),
  "start_date_time"  TIMESTAMP,
  "end_date_time"     TIMESTAMP,
  "emp_id"           NUMBER(3,0),
  "pel_name"          VARCHAR2(50),
  "csc_lookup_key"    VARCHAR2(30),
  "update_ts"        TIMESTAMP(3) WITH TIME ZONE
);





/*CREATE LINKED TABLE LINK('', 'jdbc:oracle:thin:@oltpdev_high?TNS_ADMIN=C:/Users/anils16/Documents/Conduent/Wallet_OLTPDEV', 'Tolling_DEV', 'WElcome1234#', '(SELECT * FROM T_VIOL_TX_EXCLUSION)');
*/

