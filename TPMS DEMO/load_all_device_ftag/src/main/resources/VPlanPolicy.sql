--------------------------------------------------------
--  File created - Tuesday-June-15-2021   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table V_PLAN_POLICY
--------------------------------------------------------

  CREATE TABLE "CRM"."V_PLAN_POLICY" 
   (	"PLAN_TYPE" NUMBER(10,0), 
	"PLAN_NAME" VARCHAR2(100 BYTE) COLLATE "USING_NLS_COMP", 
	"IS_COMMUTE" CHAR(1 BYTE) COLLATE "USING_NLS_COMP", 
	"IS_DEVICE_SPECIFIC" CHAR(1 BYTE) COLLATE "USING_NLS_COMP", 
	"IS_REVENUE_PLAN" CHAR(1 BYTE) COLLATE "USING_NLS_COMP", 
	"IS_PRORATE_FEE" CHAR(1 BYTE) COLLATE "USING_NLS_COMP", 
	"IS_CHARGE_FEE" CHAR(1 BYTE) COLLATE "USING_NLS_COMP", 
	"IS_ANNUAL_PLAN" CHAR(1 BYTE) COLLATE "USING_NLS_COMP", 
	"AGENCY_ID" NUMBER(10,0), 
	"MAP_AGENCY_ID" NUMBER(10,0), 
	"CHARGE_PER_DEVICE" CHAR(1 BYTE) COLLATE "USING_NLS_COMP", 
	"PLAN_BIT" NUMBER(10,0), 
	"IS_ZERO_DEPOSIT_PLAN" CHAR(1 BYTE) COLLATE "USING_NLS_COMP", 
	"IS_POST_PAID" CHAR(1 BYTE) COLLATE "USING_NLS_COMP", 
	"EXPIRATION_MONTH" NUMBER(10,0), 
	"EXPIRATION_DAY" NUMBER(10,0), 
	"MIN_DAYS_FOR_RENEWAL" NUMBER(10,0), 
	"PLAN_DAYS" NUMBER(10,0), 
	"MAX_TRIPS" NUMBER(10,0), 
	"MIN_TRIPS" NUMBER(10,0), 
	"RESIDENT_LIMITED" CHAR(1 BYTE) COLLATE "USING_NLS_COMP", 
	"PLAZA_LIMITED" CHAR(1 BYTE) COLLATE "USING_NLS_COMP", 
	"CHARGE_UNUSED_TRIPS" CHAR(1 BYTE) COLLATE "USING_NLS_COMP", 
	"MANUAL_SUSPENSION" CHAR(1 BYTE) COLLATE "USING_NLS_COMP", 
	"AUTOMATIC_REACTIVATION" CHAR(1 BYTE) COLLATE "USING_NLS_COMP", 
	"PLAN_AGENCY_PRIORITY" NUMBER(10,0), 
	"CHARGE_PLAN_BALANCE" CHAR(1 BYTE) COLLATE "USING_NLS_COMP", 
	"RENEW_REQ_TRIPS" NUMBER(10,0), 
	"RENEW_REQ_DAYS" NUMBER(10,0), 
	"MIN_SUSPENSION_DAYS" NUMBER(10,0), 
	"RENEW_LIMIT_IND" CHAR(1 BYTE) COLLATE "USING_NLS_COMP", 
	"MONTHLY_PLAN" CHAR(1 BYTE) COLLATE "USING_NLS_COMP", 
	"NUMBER_OF_PERIODS" NUMBER(10,0), 
	"APPLY_USAGE_CREDIT" CHAR(1 BYTE) COLLATE "USING_NLS_COMP", 
	"RECON_LAG_PERIOD" NUMBER(10,0), 
	"IS_ACCOUNT_LEVEL_DISCOUNT" CHAR(1 BYTE) COLLATE "USING_NLS_COMP", 
	"IS_TRIP_LIMITED" CHAR(1 BYTE) COLLATE "USING_NLS_COMP", 
	"IS_SERVICE_FEE_EXEMPT" CHAR(1 BYTE) COLLATE "USING_NLS_COMP", 
	"IS_STMT_FEE_EXEMPT" CHAR(1 BYTE) COLLATE "USING_NLS_COMP", 
	"IS_CALENDAR_PERIOD" CHAR(1 BYTE) COLLATE "USING_NLS_COMP", 
	"IS_HYBRID_PLAN" CHAR(1 BYTE) COLLATE "USING_NLS_COMP", 
	"IS_GOVT_AGENCY" CHAR(1 BYTE) COLLATE "USING_NLS_COMP"
   )  DEFAULT COLLATION "USING_NLS_COMP" SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 10 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "DATA" ;
