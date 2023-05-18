package com.conduent.tpms.unmatched.dto;

public class ViolTxUpdateDto {
	
	private Long LANE_TX_ID;
	private Integer TX_STATUS;
	private String ENTRY_DATA_SOURCE;
	private Integer ENTRY_DEVICE_READ_COUNT;
	private Integer ENTRY_DEVICE_WRITE_COUNT;
	private Integer ENTRY_LANE_ID;
	private Long ENTRY_PLAZA_ID;
	private String ENTRY_TIMESTAMP;
	private Integer ENTRY_TX_SEQ_NUMBER;
	private Integer ENTRY_VEHICLE_SPEED;
	private String TX_SUBTYPE_IND;
	private String DEVICE_NO;
	private Double POSTED_FARE_AMOUNT;
	private Double VIDEO_FARE_AMOUNT;
	private Double ETC_FARE_AMOUNT;
	private Long ETC_ACCOUNT_ID;
	
	
	public Long getLANE_TX_ID() {
		return LANE_TX_ID;
	}
	public void setLANE_TX_ID(Long lANE_TX_ID) {
		LANE_TX_ID = lANE_TX_ID;
	}
	public Integer getTX_STATUS() {
		return TX_STATUS;
	}
	public void setTX_STATUS(Integer tX_STATUS) {
		TX_STATUS = tX_STATUS;
	}
	public String getENTRY_DATA_SOURCE() {
		return ENTRY_DATA_SOURCE;
	}
	public void setENTRY_DATA_SOURCE(String eNTRY_DATA_SOURCE) {
		ENTRY_DATA_SOURCE = eNTRY_DATA_SOURCE;
	}
	public Integer getENTRY_DEVICE_READ_COUNT() {
		return ENTRY_DEVICE_READ_COUNT;
	}
	public void setENTRY_DEVICE_READ_COUNT(Integer eNTRY_DEVICE_READ_COUNT) {
		ENTRY_DEVICE_READ_COUNT = eNTRY_DEVICE_READ_COUNT;
	}
	public Integer getENTRY_DEVICE_WRITE_COUNT() {
		return ENTRY_DEVICE_WRITE_COUNT;
	}
	public void setENTRY_DEVICE_WRITE_COUNT(Integer eNTRY_DEVICE_WRITE_COUNT) {
		ENTRY_DEVICE_WRITE_COUNT = eNTRY_DEVICE_WRITE_COUNT;
	}
	public Integer getENTRY_LANE_ID() {
		return ENTRY_LANE_ID;
	}
	public void setENTRY_LANE_ID(Integer eNTRY_LANE_ID) {
		ENTRY_LANE_ID = eNTRY_LANE_ID;
	}
	public Long getENTRY_PLAZA_ID() {
		return ENTRY_PLAZA_ID;
	}
	public void setENTRY_PLAZA_ID(Long eNTRY_PLAZA_ID) {
		ENTRY_PLAZA_ID = eNTRY_PLAZA_ID;
	}
	public String getENTRY_TIMESTAMP() {
		return ENTRY_TIMESTAMP;
	}
	public void setENTRY_TIMESTAMP(String eNTRY_TIMESTAMP) {
		ENTRY_TIMESTAMP = eNTRY_TIMESTAMP;
	}
	public Integer getENTRY_TX_SEQ_NUMBER() {
		return ENTRY_TX_SEQ_NUMBER;
	}
	public void setENTRY_TX_SEQ_NUMBER(Integer eNTRY_TX_SEQ_NUMBER) {
		ENTRY_TX_SEQ_NUMBER = eNTRY_TX_SEQ_NUMBER;
	}
	public Integer getENTRY_VEHICLE_SPEED() {
		return ENTRY_VEHICLE_SPEED;
	}
	public void setENTRY_VEHICLE_SPEED(Integer eNTRY_VEHICLE_SPEED) {
		ENTRY_VEHICLE_SPEED = eNTRY_VEHICLE_SPEED;
	}
	public String getTX_SUBTYPE_IND() {
		return TX_SUBTYPE_IND;
	}
	public void setTX_SUBTYPE_IND(String tX_SUBTYPE_IND) {
		TX_SUBTYPE_IND = tX_SUBTYPE_IND;
	}
	public String getDEVICE_NO() {
		return DEVICE_NO;
	}
	public void setDEVICE_NO(String dEVICE_NO) {
		DEVICE_NO = dEVICE_NO;
	}
	public Double getPOSTED_FARE_AMOUNT() {
		return POSTED_FARE_AMOUNT;
	}
	public void setPOSTED_FARE_AMOUNT(Double pOSTED_FARE_AMOUNT) {
		POSTED_FARE_AMOUNT = pOSTED_FARE_AMOUNT;
	}
	public Double getVIDEO_FARE_AMOUNT() {
		return VIDEO_FARE_AMOUNT;
	}
	public void setVIDEO_FARE_AMOUNT(Double vIDEO_FARE_AMOUNT) {
		VIDEO_FARE_AMOUNT = vIDEO_FARE_AMOUNT;
	}
	public Double getETC_FARE_AMOUNT() {
		return ETC_FARE_AMOUNT;
	}
	public void setETC_FARE_AMOUNT(Double eTC_FARE_AMOUNT) {
		ETC_FARE_AMOUNT = eTC_FARE_AMOUNT;
	}
	public Long getETC_ACCOUNT_ID() {
		return ETC_ACCOUNT_ID;
	}
	public void setETC_ACCOUNT_ID(Long eTC_ACCOUNT_ID) {
		ETC_ACCOUNT_ID = eTC_ACCOUNT_ID;
	}
	@Override
	public String toString() {
		return "ViolTxUpdateDto [LANE_TX_ID=" + LANE_TX_ID + ", TX_STATUS=" + TX_STATUS + ", ENTRY_DATA_SOURCE="
				+ ENTRY_DATA_SOURCE + ", ENTRY_DEVICE_READ_COUNT=" + ENTRY_DEVICE_READ_COUNT
				+ ", ENTRY_DEVICE_WRITE_COUNT=" + ENTRY_DEVICE_WRITE_COUNT + ", ENTRY_LANE_ID=" + ENTRY_LANE_ID
				+ ", ENTRY_PLAZA_ID=" + ENTRY_PLAZA_ID + ", ENTRY_TIMESTAMP=" + ENTRY_TIMESTAMP
				+ ", ENTRY_TX_SEQ_NUMBER=" + ENTRY_TX_SEQ_NUMBER + ", ENTRY_VEHICLE_SPEED=" + ENTRY_VEHICLE_SPEED
				+ ", TX_SUBTYPE_IND=" + TX_SUBTYPE_IND + ", DEVICE_NO=" + DEVICE_NO + ", POSTED_FARE_AMOUNT="
				+ POSTED_FARE_AMOUNT + ", VIDEO_FARE_AMOUNT=" + VIDEO_FARE_AMOUNT + ", ETC_FARE_AMOUNT="
				+ ETC_FARE_AMOUNT + ", ETC_ACCOUNT_ID=" + ETC_ACCOUNT_ID + "]";
	}
	
}