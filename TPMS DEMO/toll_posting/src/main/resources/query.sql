create table TPMS.T_TOLL_POST_LIMIT
(
  plaza_agency_id NUMBER(3) not null,
  etc_tx_status   NUMBER(10) not null,
  allowed_days    NUMBER(10),
  update_ts       TIMESTAMP(3) WITH TIME ZONE,
  poaching_limit  VARCHAR2(8) default '00:05:00'
)


create table TPMS.T_AGENCY_HOLIDAY
(
  agency_id   NUMBER(3),
  holiday     DATE,
  days_ind    VARCHAR2(1),
  description VARCHAR2(60)
)
