package com.conduent.transactionSearch.model;

import java.sql.Timestamp;
import java.util.Date;

public class TranQueryReturn {
	
	private int       rowNumber;
	private String    accountNumber;
	private String    transponderNumber;
	private Timestamp transactionDateTime;
	private Date      postedDate;
	private String    plateNumber;
	private String    plateState;
	private String    laneTxID;
	private String    extReferenceNo;
    private String    plazaId;
    private String    laneId;
	private Timestamp entryTransactionDateTime;
	private Timestamp updateTs;
	private String      txStatus;
	private double    postedFareAmount;
	private String    exitPlazaDescription;
	private String    entryPlazaDescription;
	private String    exitLane;
	private String    entryLane;
	private String    agencyStatementName;
	private String    vehicleClass;
	private boolean   disputedFlag;
	private double    disputedAmount;
	private String    planName;
	private String    imageURL;
	
	private int totalElements;
	
	
	public int getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}
	public int getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getTransponderNumber() {
		return transponderNumber;
	}
	public void setTransponderNumber(String transponderNumber) {
		this.transponderNumber = transponderNumber;
	}
	public Timestamp getTransactionDateTime() {
		return transactionDateTime;
	}
	public void setTransactionDateTime(Timestamp transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}
	public Date getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getPlateState() {
		return plateState;
	}
	public void setPlateState(String plateState) {
		this.plateState = plateState;
	}
	public String getLaneTxID() {
		return laneTxID;
	}
	public void setLaneTxID(String laneTxID) {
		this.laneTxID = laneTxID;
	}
	public String getExtReferenceNo() {
		return extReferenceNo;
	}
	public void setExtReferenceNo(String extReferenceNo) {
		this.extReferenceNo = extReferenceNo;
	}
	public String getPlazaId() {
		return plazaId;
	}
	public void setPlazaId(String plazaId) {
		this.plazaId = plazaId;
	}
	public String getLaneId() {
		return laneId;
	}
	public void setLaneId(String laneId) {
		this.laneId = laneId;
	}
	public Timestamp getEntryTransactionDateTime() {
		return entryTransactionDateTime;
	}
	public void setEntryTransactionDateTime(Timestamp entryTransactionDateTime) {
		this.entryTransactionDateTime = entryTransactionDateTime;
	}
	public Timestamp getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(Timestamp updateTs) {
		this.updateTs = updateTs;
	}
	public String getTxStatus() {
		return txStatus;
	}
	public void setTxStatus(String txStatus) {
		this.txStatus = txStatus;
	}
	public double getPostedFareAmount() {
		return postedFareAmount;
	}
	public void setPostedFareAmount(double postedFareAmount) {
		this.postedFareAmount = postedFareAmount;
	}
	public String getExitPlazaDescription() {
		return exitPlazaDescription;
	}
	public void setExitPlazaDescription(String exitPlazaDescription) {
		this.exitPlazaDescription = exitPlazaDescription;
	}
	public String getEntryPlazaDescription() {
		return entryPlazaDescription;
	}
	public void setEntryPlazaDescription(String entryPlazaDescription) {
		this.entryPlazaDescription = entryPlazaDescription;
	}
	public String getExitLane() {
		return exitLane;
	}
	public void setExitLane(String exitLane) {
		this.exitLane = exitLane;
	}
	public String getEntryLane() {
		return entryLane;
	}
	public void setEntryLane(String entryLane) {
		this.entryLane = entryLane;
	}
	public String getAgencyStatementName() {
		return agencyStatementName;
	}
	public void setAgencyStatementName(String agencyStatementName) {
		this.agencyStatementName = agencyStatementName;
	}
	public String getVehicleClass() {
		return vehicleClass;
	}
	public void setVehicleClass(String vehicleClass) {
		this.vehicleClass = vehicleClass;
	}
	public boolean isDisputedFlag() {
		return disputedFlag;
	}
	public void setDisputedFlag(boolean disputedFlag) {
		this.disputedFlag = disputedFlag;
	}
	public double getDisputedAmount() {
		return disputedAmount;
	}
	public void setDisputedAmount(double disputedAmount) {
		this.disputedAmount = disputedAmount;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	
	
	}
