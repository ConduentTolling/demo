package com.conduent.tpms.NY12.vo;

import java.sql.Timestamp;
import java.util.Date;

public class TransactionVO {

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
	public String getTxExternRefNo() {
		return txExternRefNo;
	}
	public void setTxExternRefNo(String txExternRefNo) {
		this.txExternRefNo = txExternRefNo;
	}
	public String getMachedTxExternNo() {
		return machedTxExternNo;
	}
	public void setMachedTxExternNo(String machedTxExternNo) {
		this.machedTxExternNo = machedTxExternNo;
	}
	public String getTxCompleteRefNo() {
		return txCompleteRefNo;
	}
	public void setTxCompleteRefNo(String txCompleteRefNo) {
		this.txCompleteRefNo = txCompleteRefNo;
	}
	public String getTxMatchStatus() {
		return txMatchStatus;
	}
	public void setTxMatchStatus(String txMatchStatus) {
		this.txMatchStatus = txMatchStatus;
	}
	public long getTxSeqNumber() {
		return txSeqNumber;
	}
	public void setTxSeqNumber(long txSeqNumber) {
		this.txSeqNumber = txSeqNumber;
	}
	public double getExternFileId() {
		return externFileId;
	}
	public void setExternFileId(double externFileId) {
		this.externFileId = externFileId;
	}
	public double getAtpFileId() {
		return atpFileId;
	}
	public void setAtpFileId(double atpFileId) {
		this.atpFileId = atpFileId;
	}
	public Date getExternFileDate() {
		return externFileDate;
	}
	public void setExternFileDate(Date externFileDate) {
		this.externFileDate = externFileDate;
	}
	public Date getTxDate() {
		return txDate;
	}
	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}
	public String getTxTypeInd() {
		return txTypeInd;
	}
	public void setTxTypeInd(String txTypeInd) {
		this.txTypeInd = txTypeInd;
	}
	public String getTxSubtypeInd() {
		return txSubtypeInd;
	}
	public void setTxSubtypeInd(String txSubtypeInd) {
		this.txSubtypeInd = txSubtypeInd;
	}
	public String getTollSytemType() {
		return tollSytemType;
	}
	public void setTollSytemType(String tollSytemType) {
		this.tollSytemType = tollSytemType;
	}
	public long getPlazaAgencyId() {
		return plazaAgencyId;
	}
	public void setPlazaAgencyId(long plazaAgencyId) {
		this.plazaAgencyId = plazaAgencyId;
	}
	public long getPlazaId() {
		return plazaId;
	}
	public void setPlazaId(long plazaId) {
		this.plazaId = plazaId;
	}
	public long getLaneId() {
		return laneId;
	}
	public void setLaneId(long laneId) {
		this.laneId = laneId;
	}
	public long getLaneMode() {
		return laneMode;
	}
	public void setLaneMode(long laneMode) {
		this.laneMode = laneMode;
	}
	public long getLaneType() {
		return laneType;
	}
	public void setLaneType(long laneType) {
		this.laneType = laneType;
	}
	public long getLaneState() {
		return laneState;
	}
	public void setLaneState(long laneState) {
		this.laneState = laneState;
	}
	public long getLaneHealth() {
		return laneHealth;
	}
	public void setLaneHealth(long laneHealth) {
		this.laneHealth = laneHealth;
	}
	public String getEnrtyDataSource() {
		return enrtyDataSource;
	}
	public void setEnrtyDataSource(String enrtyDataSource) {
		this.enrtyDataSource = enrtyDataSource;
	}
	public long getEnrtyLaneId() {
		return enrtyLaneId;
	}
	public void setEnrtyLaneId(long enrtyLaneId) {
		this.enrtyLaneId = enrtyLaneId;
	}
	public long getEnrtyPlazaId() {
		return enrtyPlazaId;
	}
	public void setEnrtyPlazaId(long enrtyPlazaId) {
		this.enrtyPlazaId = enrtyPlazaId;
	}
	public long getEntryTxSeqNumber() {
		return entryTxSeqNumber;
	}
	public void setEntryTxSeqNumber(long entryTxSeqNumber) {
		this.entryTxSeqNumber = entryTxSeqNumber;
	}
	public int getEntryVehicleSpeed() {
		return entryVehicleSpeed;
	}
	public void setEntryVehicleSpeed(int entryVehicleSpeed) {
		this.entryVehicleSpeed = entryVehicleSpeed;
	}
	public long getLaneTxStetus() {
		return laneTxStetus;
	}
	public void setLaneTxStetus(long laneTxStetus) {
		this.laneTxStetus = laneTxStetus;
	}
	public long getLaneTxType() {
		return laneTxType;
	}
	public void setLaneTxType(long laneTxType) {
		this.laneTxType = laneTxType;
	}
	public long getTollRevenueType() {
		return tollRevenueType;
	}
	public void setTollRevenueType(long tollRevenueType) {
		this.tollRevenueType = tollRevenueType;
	}
	public int getActualClass() {
		return actualClass;
	}
	public void setActualClass(int actualClass) {
		this.actualClass = actualClass;
	}
	public int getActualAxles() {
		return actualAxles;
	}
	public void setActualAxles(int actualAxles) {
		this.actualAxles = actualAxles;
	}
	public int getActualExtraAxles() {
		return actualExtraAxles;
	}
	public void setActualExtraAxles(int actualExtraAxles) {
		this.actualExtraAxles = actualExtraAxles;
	}
	public int getCollectorClass() {
		return collectorClass;
	}
	public void setCollectorClass(int collectorClass) {
		this.collectorClass = collectorClass;
	}
	public int getCollectorAxles() {
		return collectorAxles;
	}
	public void setCollectorAxles(int collectorAxles) {
		this.collectorAxles = collectorAxles;
	}
	public int getPreclassClass() {
		return preclassClass;
	}
	public void setPreclassClass(int preclassClass) {
		this.preclassClass = preclassClass;
	}
	public int getPreclassAxles() {
		return preclassAxles;
	}
	public void setPreclassAxles(int preclassAxles) {
		this.preclassAxles = preclassAxles;
	}
	public long getPostclassClass() {
		return postclassClass;
	}
	public void setPostclassClass(long postclassClass) {
		this.postclassClass = postclassClass;
	}
	public long getPostclassAxles() {
		return postclassAxles;
	}
	public void setPostclassAxles(long postclassAxles) {
		this.postclassAxles = postclassAxles;
	}
	public long getForwardAxles() {
		return forwardAxles;
	}
	public void setForwardAxles(long forwardAxles) {
		this.forwardAxles = forwardAxles;
	}
	public long getReverseAxles() {
		return reverseAxles;
	}
	public void setReverseAxles(long reverseAxles) {
		this.reverseAxles = reverseAxles;
	}
	public float getDiscountedAmount() {
		return discountedAmount;
	}
	public void setDiscountedAmount(float discountedAmount) {
		this.discountedAmount = discountedAmount;
	}
	public float getCollectedAmount() {
		return collectedAmount;
	}
	public void setCollectedAmount(float collectedAmount) {
		this.collectedAmount = collectedAmount;
	}
	public float getUnrealizedAmount() {
		return unrealizedAmount;
	}
	public void setUnrealizedAmount(float unrealizedAmount) {
		this.unrealizedAmount = unrealizedAmount;
	}
	public int getCvehicleSpeed() {
		return cvehicleSpeed;
	}
	public void setCvehicleSpeed(int cvehicleSpeed) {
		this.cvehicleSpeed = cvehicleSpeed;
	}
	public String getDeviceNO() {
		return deviceNO;
	}
	public void setDeviceNO(String deviceNO) {
		this.deviceNO = deviceNO;
	}
	public int getAccountType() {
		return accountType;
	}
	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}
	public int getDeviceIagClass() {
		return deviceIagClass;
	}
	public void setDeviceIagClass(int deviceIagClass) {
		this.deviceIagClass = deviceIagClass;
	}
	public int getDeviceAxles() {
		return deviceAxles;
	}
	public void setDeviceAxles(int deviceAxles) {
		this.deviceAxles = deviceAxles;
	}
	public long getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(long etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	public int getAccounAgencyId() {
		return accounAgencyId;
	}
	public void setAccounAgencyId(int accounAgencyId) {
		this.accounAgencyId = accounAgencyId;
	}
	public int getReadAviClass() {
		return readAviClass;
	}
	public void setReadAviClass(int readAviClass) {
		this.readAviClass = readAviClass;
	}
	public int getReadAviAxles() {
		return readAviAxles;
	}
	public void setReadAviAxles(int readAviAxles) {
		this.readAviAxles = readAviAxles;
	}
	public String getDeviceProgramStatus() {
		return deviceProgramStatus;
	}
	public void setDeviceProgramStatus(String deviceProgramStatus) {
		this.deviceProgramStatus = deviceProgramStatus;
	}
	public String getBufferedReadFlag() {
		return bufferedReadFlag;
	}
	public void setBufferedReadFlag(String bufferedReadFlag) {
		this.bufferedReadFlag = bufferedReadFlag;
	}
	public long getLaneDeviceStatus() {
		return laneDeviceStatus;
	}
	public void setLaneDeviceStatus(long laneDeviceStatus) {
		this.laneDeviceStatus = laneDeviceStatus;
	}
	public long getPostDeviceStatus() {
		return postDeviceStatus;
	}
	public void setPostDeviceStatus(long postDeviceStatus) {
		this.postDeviceStatus = postDeviceStatus;
	}
	public float getPreTxnBalance() {
		return preTxnBalance;
	}
	public void setPreTxnBalance(float preTxnBalance) {
		this.preTxnBalance = preTxnBalance;
	}
	public long getTxStatus() {
		return txStatus;
	}
	public void setTxStatus(long txStatus) {
		this.txStatus = txStatus;
	}
	public long getCollectorId() {
		return collectorId;
	}
	public void setCollectorId(long collectorId) {
		this.collectorId = collectorId;
	}
	public long getTourSegmentId() {
		return tourSegmentId;
	}
	public void setTourSegmentId(long tourSegmentId) {
		this.tourSegmentId = tourSegmentId;
	}
	public String getSpeedVoilFlage() {
		return speedVoilFlage;
	}
	public void setSpeedVoilFlage(String speedVoilFlage) {
		this.speedVoilFlage = speedVoilFlage;
	}
	public String getImageTaken() {
		return imageTaken;
	}
	public void setImageTaken(String imageTaken) {
		this.imageTaken = imageTaken;
	}
	public String getPlateCounrty() {
		return plateCounrty;
	}
	public void setPlateCounrty(String plateCounrty) {
		this.plateCounrty = plateCounrty;
	}
	public String getPlateState() {
		return plateState;
	}
	public void setPlateState(String plateState) {
		this.plateState = plateState;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public Date getRevenueDate() {
		return revenueDate;
	}
	public void setRevenueDate(Date revenueDate) {
		this.revenueDate = revenueDate;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getHovFlag() {
		return hovFlag;
	}
	public void setHovFlag(String hovFlag) {
		this.hovFlag = hovFlag;
	}
	public String getIsRecprocityTxn() {
		return isRecprocityTxn;
	}
	public void setIsRecprocityTxn(String isRecprocityTxn) {
		this.isRecprocityTxn = isRecprocityTxn;
	}
	public String getCcsLookupKey() {
		return ccsLookupKey;
	}
	public void setCcsLookupKey(String ccsLookupKey) {
		this.ccsLookupKey = ccsLookupKey;
	}
	public Timestamp getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(Timestamp updateTs) {
		this.updateTs = updateTs;
	}
	public float getCashFareAmount() {
		return cashFareAmount;
	}
	public void setCashFareAmount(float cashFareAmount) {
		this.cashFareAmount = cashFareAmount;
	}
	public float getDiscountedAmount2() {
		return discountedAmount2;
	}
	public void setDiscountedAmount2(float discountedAmount2) {
		this.discountedAmount2 = discountedAmount2;
	}
	public float getEtcFareAmount() {
		return etcFareAmount;
	}
	public void setEtcFareAmount(float etcFareAmount) {
		this.etcFareAmount = etcFareAmount;
	}
	public float getExpectedRevenueAmount() {
		return expectedRevenueAmount;
	}
	public void setExpectedRevenueAmount(float expectedRevenueAmount) {
		this.expectedRevenueAmount = expectedRevenueAmount;
	}
	public float getPostedFareAmount() {
		return postedFareAmount;
	}
	public void setPostedFareAmount(float postedFareAmount) {
		this.postedFareAmount = postedFareAmount;
	}
	public float getVideoFareAmount() {
		return videoFareAmount;
	}
	public void setVideoFareAmount(float videoFareAmount) {
		this.videoFareAmount = videoFareAmount;
	}
	public Timestamp getEntryTimeStamp() {
		return entryTimeStamp;
	}
	public void setEntryTimeStamp(Timestamp entryTimeStamp) {
		this.entryTimeStamp = entryTimeStamp;
	}
	public Timestamp getTxTimeStamp() {
		return txTimeStamp;
	}
	public void setTxTimeStamp(Timestamp txTimeStamp) {
		this.txTimeStamp = txTimeStamp;
	}




}
