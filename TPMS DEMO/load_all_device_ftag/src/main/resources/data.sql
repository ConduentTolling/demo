create table T_VIOL_TX_EXCLUSION
(
  agency_id         int(3),
  plaza_id          int(10),
  lane_id           int(10),
  lane_mode         int(10),  
  viol_type         int(10),
  toll_revenue_type int(10),
  exclusion_stage   int(5),
  etc_account_id    int(10),
  plate_state       VARCHAR2(2),
  plate_number      VARCHAR2(10),  
  vehicle_speed     int(5),
  tx_type_ind       VARCHAR2(1),
  tx_subtype_ind    VARCHAR2(1),
  start_date_time   TIMESTAMP,
  end_date_time     TIMESTAMP,
  emp_id            int,
  pel_name          VARCHAR2(50),
  csc_lookup_key    VARCHAR2(30),
  update_ts         TIMESTAMP(3) WITH TIME ZONE
);





/*CREATE LINKED TABLE LINK('', 'jdbc:oracle:thin:@oltpdev_high?TNS_ADMIN=C:/Users/anils16/Documents/Conduent/Wallet_OLTPDEV', 'Tolling_DEV', 'WElcome1234#', '(SELECT * FROM T_VIOL_TX_EXCLUSION)');
*/

