package com.conduent.tpms.NY12.vo;

import java.sql.Timestamp;
import java.util.Date;

public class CompleteTransactionVO {
	private String   txExternRefNo;         //     TX_EXTERN_REF_NO               VARCHAR2(22)  
	private String   machedTxExternNo;      // MATCHED_TX_EXTERN_REF_NO       VARCHAR2(22)                
	private String   txCompleteRefNo;       // TX_COMPLETE_REF_NO             VARCHAR2(22)                
	private String   txMatchStatus;         //private longTX_MATCH_STATUS                VARCHAR2(50)                
	private long     txSeqNumber;            //        TX_SEQ_NUMBER                  NUMBER(10)                  
	private double   externFileId;           //        EXTERN_FILE_ID                 NUMBER(22)                  
	private double   atpFileId;              //               ATP_FILE_ID                    NUMBER(22)                  
	private Date     externFileDate;         //                                     EXTERN_FILE_DATE               DATE                        
	private Date     txDate;                 //                   TX_DATE                        DATE                        
	private String   txTypeInd;              //   TX_TYPE_IND                    VARCHAR2(1)                 
	private String   txSubtypeInd;           //TX_SUBTYPE_IND                 VARCHAR2(1)                 
	private String   tollSytemType;          //   TOLL_SYSTEM_TYPE               VARCHAR2(1)                 
	private long     plazaAgencyId;          //     PLAZA_AGENCY_ID
	private long     plazaId;                //      PLAZA_ID                       NUMBER(10)                  
	private long     laneId;                 //              LANE_ID                        NUMBER(10)                  
	private long     laneMode;               //      LANE_MODE                      NUMBER(10)                  
	private long     laneType;                //    LANE_TYPE                      NUMBER(10)                  
	private long     laneState;              //  LANE_STATE                     NUMBER(10)                  
	private long     laneHealth;             // LANE_HEALTH                    NUMBER(10)                  
	private String   enrtyDataSource;        // ENTRY_DATA_SOURCE              VARCHAR2(1)                 
	private long     enrtyLaneId;            //  ENTRY_LANE_ID                  NUMBER(10)                  
	private long     enrtyPlazaId;           //        ENTRY_PLAZA_ID                 NUMBER(10)                  
	private long     entryTxSeqNumber;       //  ENTRY_TX_SEQ_NUMBER            NUMBER(10)                  
	private int      entryVehicleSpeed;      //        ENTRY_VEHICLE_SPEED            NUMBER(5)                   
	private long     laneTxStetus;           //     LANE_TX_STATUS                 NUMBER(10)                  
	private long      laneTxType;              //      LANE_TX_TYPE                   NUMBER(10)                  
	private long      tollRevenueType;       //    TOLL_REVENUE_TYPE              NUMBER(10)                  
	private int       actualClass;           //           ACTUAL_CLASS                   NUMBER(3)                   
	private int       actualAxles;           //     ACTUAL_AXLES                   NUMBER(3)                   
	private int       actualExtraAxles;      //     ACTUAL_EXTRA_AXLES             NUMBER(3)                   
	private int       collectorClass;        //           COLLECTOR_CLASS                NUMBER(3)                   
	private int       collectorAxles;        //            COLLECTOR_AXLES                NUMBER(3)                   
	private int       preclassClass;         //    PRECLASS_CLASS                 NUMBER(3)                   
	private int       preclassAxles;         //  PRECLASS_AXLES                 NUMBER(3)                   
	private  long     postclassClass;        //   POSTCLASS_CLASS                NUMBER(3)                   
	private  long     postclassAxles;        // POSTCLASS_AXLES                NUMBER(3)                   
	private  long     forwardAxles;          // FORWARD_AXLES                  NUMBER(3)                   
	private  long     reverseAxles;          //             REVERSE_AXLES                  NUMBER(3)                   
	private  float    discountedAmount;      //               DISCOUNTED_AMOUNT              NUMBER(12,2)                
	private  float    collectedAmount;       //              COLLECTED_AMOUNT               NUMBER(12,2)                
	private  float    unrealizedAmount;      //          UNREALIZED_AMOUNT              NUMBER(12,2)                
	private  int      cvehicleSpeed;         //                 VEHICLE_SPEED                  NUMBER(5)                   
	private String    deviceNO;              //            DEVICE_NO                      VARCHAR2(12)                
	private int       accountType;           //            ACCOUNT_TYPE                   NUMBER(5)                   
	private int       deviceIagClass;         //          DEVICE_IAG_CLASS               NUMBER(5)                   
	private int       deviceAxles;            //                DEVICE_AXLES                   NUMBER(4)                   
	private long      etcAccountId;            //             ETC_ACCOUNT_ID                 NUMBER(10)                  
	private int       accounAgencyId ;        //               tACCOUNT_AGENCY_ID              NUMBER(10)                  
	private int       readAviClass;           //             READ_AVI_CLASS                 NUMBER(3)                   
	private int       readAviAxles;           //              READ_AVI_AXLES                 NUMBER(3)                   
	private String    deviceProgramStatus;    //         DEVICE_PROGRAM_STATUS          VARCHAR2(1)                 
	private String    bufferedReadFlag;       //               BUFFERED_READ_FLAG             VARCHAR2(1)                 
	private long      laneDeviceStatus;       //            LANE_DEVICE_STATUS             NUMBER(10)                  
	private long      postDeviceStatus;       //             POST_DEVICE_STATUS             NUMBER(10)                  
	private float     preTxnBalance;          //              PRE_TXN_BALANCE                NUMBER(12,2)                
	private long      txStatus;               //                 TX_STATUS                      NUMBER(10)                  
	private long      collectorId;            //                   COLLECTOR_ID                   NUMBER(10)                  
	private long      tourSegmentId;         //                     TOUR_SEGMENT_ID                NUMBER(10)                  
	private String    speedVoilFlage;         //                 SPEED_VIOL_FLAG                VARCHAR2(1)                 
	private String    imageTaken;             //               IMAGE_TAKEN                    VARCHAR2(1)                 
	private String    plateCounrty;           //             PLATE_COUNTRY                  VARCHAR2(4)                 
	private String    plateState;             //               PLATE_STATE                    VARCHAR2(2)                 
	private String    plateNumber;           //             PLATE_NUMBER                   VARCHAR2(10)                
	private Date      revenueDate;           //            //  REVENUE_DATE                            DATE                        
	private String    event;                 //            //    EVENT                          VARCHAR2(1)                 
	private String    hovFlag;               //            // HOV_FLAG                       VARCHAR2(1)                 
	private String    isRecprocityTxn;       //            //     IS_RECIPROCITY_TXN             VARCHAR2(1)                 
	private String    ccsLookupKey;          //            //    CSC_LOOKUP_KEY                 VARCHAR2(30)                
	private Timestamp updateTs;              //            //UPDATE_TS                      TIMESTAMP(3)                
	private float     cashFareAmount;                   //CASH_FARE_AMOUNT               FLOAT(126)                  
	private float     discountedAmount2;                //DISCOUNTED_AMOUNT_2            FLOAT(126)                  
	private float     etcFareAmount;                    //ETC_FARE_AMOUNT                FLOAT(126)                  
	private float     expectedRevenueAmount;            //EXPECTED_REVENUE_AMOUNT        FLOAT(126)                  
	private float     postedFareAmount;                 //POSTED_FARE_AMOUNT             FLOAT(126)                  
	private float     videoFareAmount;                 //VIDEO_FARE_AMOUNT              FLOAT(126)                  
	private Timestamp entryTimeStamp;                  //ENTRY_TIMESTAMP                TIMESTAMP(6) WITH TIME ZONE 
	private Timestamp txTimeStamp;                     //TX_TIMESTAMP                   TIMESTAMP(6) WITH TIME ZONE 
	private Date insertDate;
	private Date processedDate;
	private String statusCd;
	private String summaryFlag;

	
	
	
}
