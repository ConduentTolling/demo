package com.conduent.transactionSearch.model;

import java.sql.Timestamp;
import java.util.Date;

public class TranQueryReturnFromView {
    private int       rowNumber;
    private String accountNo;
    private String agencyName;
    private String deviceNo;
    private double discountedFare;
    private double disputedAmount;
    private boolean disputedFlag;
    private String entryExternLaneId;
    private String entryExternPlazaId;
    private String entryLaneId;
    private String entryPlazaDescription;
    private String entryPlazaId;
    private Timestamp entryTxTimestamp;
    private String escalationLevel;
    private String etcAccountId;
    private String exitExternLaneId;
    private String exitExternPlazaId;
    private String exitLaneId;
    private String exitPlazaDescription;
    private String exitPlazaId;
    private String imageUrl;
    private String invoiceNumber;
    private String laneTxId;
    private String planName;
    private String plateNumber;
    private String plateState;
    private Date   postedDate;
    private Date   txDate;
    private String txExternRefNo;
    private String txStatus;
    private Timestamp txTimestamp;
    private Timestamp updateTs;
    private String vehicleClass;
    private String category;
    private int    totalElements;
    private String actualAxles;
	private String transactionRowId;
	private String description;
	private double postTransactionBalance;
	private String tranType;

    
	public int getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public double getDiscountedFare() {
		return discountedFare;
	}
	public void setDiscountedFare(double discountedFare) {
		this.discountedFare = discountedFare;
	}
	public double getDisputedAmount() {
		return disputedAmount;
	}
	public void setDisputedAmount(double disputedAmount) {
		this.disputedAmount = disputedAmount;
	}
	public boolean isDisputedFlag() {
		return disputedFlag;
	}
	public void setDisputedFlag(boolean disputedFlag) {
		this.disputedFlag = disputedFlag;
	}
	public String getEntryExternLaneId() {
		return entryExternLaneId;
	}
	public void setEntryExternLaneId(String entryExternLaneId) {
		this.entryExternLaneId = entryExternLaneId;
	}
	public String getEntryExternPlazaId() {
		return entryExternPlazaId;
	}
	public void setEntryExternPlazaId(String entryExternPlazaId) {
		this.entryExternPlazaId = entryExternPlazaId;
	}
	public String getEntryLaneId() {
		return entryLaneId;
	}
	public void setEntryLaneId(String entryLaneId) {
		this.entryLaneId = entryLaneId;
	}
	public String getEntryPlazaDescription() {
		return entryPlazaDescription;
	}
	public void setEntryPlazaDescription(String entryPlazaDescription) {
		this.entryPlazaDescription = entryPlazaDescription;
	}
	public String getEntryPlazaId() {
		return entryPlazaId;
	}
	public void setEntryPlazaId(String entryPlazaId) {
		this.entryPlazaId = entryPlazaId;
	}
	public Timestamp getEntryTxTimestamp() {
		return entryTxTimestamp;
	}
	public void setEntryTxTimestamp(Timestamp entryTxTimestamp) {
		this.entryTxTimestamp = entryTxTimestamp;
	}
	public String getEscalationLevel() {
		return escalationLevel;
	}
	public void setEscalationLevel(String escalationLevel) {
		this.escalationLevel = escalationLevel;
	}
	public String getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(String etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	public String getExitExternLaneId() {
		return exitExternLaneId;
	}
	public void setExitExternLaneId(String exitExternLaneId) {
		this.exitExternLaneId = exitExternLaneId;
	}
	public String getExitExternPlazaId() {
		return exitExternPlazaId;
	}
	public void setExitExternPlazaId(String exitExternPlazaId) {
		this.exitExternPlazaId = exitExternPlazaId;
	}
	public String getExitLaneId() {
		return exitLaneId;
	}
	public void setExitLaneId(String exitLaneId) {
		this.exitLaneId = exitLaneId;
	}
	public String getExitPlazaDescription() {
		return exitPlazaDescription;
	}
	public void setExitPlazaDescription(String exitPlazaDescription) {
		this.exitPlazaDescription = exitPlazaDescription;
	}
	public String getExitPlazaId() {
		return exitPlazaId;
	}
	public void setExitPlazaId(String exitPlazaId) {
		this.exitPlazaId = exitPlazaId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getLaneTxId() {
		return laneTxId;
	}
	public void setLaneTxId(String laneTxId) {
		this.laneTxId = laneTxId;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
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
	public Date getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}
	public Date getTxDate() {
		return txDate;
	}
	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}
	public String getTxExternRefNo() {
		return txExternRefNo;
	}
	public void setTxExternRefNo(String txExternRefNo) {
		this.txExternRefNo = txExternRefNo;
	}
	public String getTxStatus() {
		return txStatus;
	}
	public void setTxStatus(String txStatus) {
		this.txStatus = txStatus;
	}
	public Timestamp getTxTimestamp() {
		return txTimestamp;
	}
	public void setTxTimestamp(Timestamp txTimestamp) {
		this.txTimestamp = txTimestamp;
	}
	public Timestamp getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(Timestamp updateTs) {
		this.updateTs = updateTs;
	}
	public String getVehicleClass() {
		return vehicleClass;
	}
	public void setVehicleClass(String vehicleClass) {
		this.vehicleClass = vehicleClass;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}
	public String getActualAxles() {
		return actualAxles;
	}
	public void setActualAxles(String actualAxles) {
		this.actualAxles = actualAxles;
	}
	public String getTransactionRowId() {
		return transactionRowId;
	}
	public void setTransactionRowId(String transactionRowId) {
		this.transactionRowId = transactionRowId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPostTransactionBalance() {
		return postTransactionBalance;
	}
	public void setPostTransactionBalance(double postTransactionBalance) {
		this.postTransactionBalance = postTransactionBalance;
	}
	public String getTranType() {
		return tranType;
	}
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
	
    
		
	
	}
