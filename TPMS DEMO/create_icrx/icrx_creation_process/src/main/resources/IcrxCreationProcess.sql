create table MASTER.T_DISTANCE_DISCOUNT
(
  agency_id       NUMBER(10) not null,
  plaza_id        NUMBER(10) not null,
  extern_plaza_id VARCHAR2(5),
  lane_id         NUMBER(10) not null,
  extern_lane_id  VARCHAR2(5),
  group_id        NUMBER(10) not null,
  time_ind        VARCHAR2(1),
  time_out_value  NUMBER(10),
  entry_exit_ind  VARCHAR2(1)
)


ALTER TABLE MASTER.T_LANE
ADD (CALCULATE_TOLL_AMOUNT CHAR(1 BYTE),
HOV_MAX_DELTA_TIME	NUMBER(5,0),
HOV_MIN_DELTA_TIME	NUMBER(5,0),
HOV_OFFSET	NUMBER(5,0),
IMAGE_RESOLUTION	VARCHAR2(20 BYTE),
IS_SHOULDER	VARCHAR2(1 BYTE),
LPR_ENABLED	VARCHAR2(1 BYTE));


create sequence TPMS.SEQ_ICTX_FILE_ID_016
minvalue 1
maxvalue 1000000000000000000000000000
start with 100
increment by 1
cache 20;