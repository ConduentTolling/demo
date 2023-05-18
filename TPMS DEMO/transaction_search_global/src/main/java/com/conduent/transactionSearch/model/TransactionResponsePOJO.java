/**
 * 
 */
package com.conduent.transactionSearch.model;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * @author saurabhs33
 *
 */

public class TransactionResponsePOJO {

	private String transponderNumber;
	private String plateNumber;
	private String plateState;
	private Date postedDate;
	private Timestamp transactionDateTime;
	private Timestamp entryTransactionDateTime;
	private Timestamp updateTs;
	private String accountNumber;
	private String txStatus;
	private double discFare;
	private String entryExternPlazaId;
	private String exitExternPlazaId;
	private String entryPlazaDescription;
	private String exitPlazaDescription;
	private String exitLane;
	private String entryLane;
	private String agencyStatementName;
	private String vehicleClass;
	private boolean disputedFlag;
	private double disputedAmount;
	private String planName;
	private String imageURL;
	private String laneTxID;
	private String extReferenceNo;
	private String invoiceNumber;
	private String escalationLevel;
	private String entryExternLaneId;
	private String exitExternLaneId;
	private String category;
	private String actualAxles;
	private String transactionRowId;
	private String description;
	private double postTransactionBalance;
	private String tranType;

	/*public int page;
	public int size;
	public int recordNumber;
	public boolean paged;
	public boolean unpaged;
	*/
	

	public TransactionResponsePOJO() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	

	

	





	public TransactionResponsePOJO(String transponderNumber, String plateNumber, String plateState, Date postedDate,
			Timestamp transactionDateTime, Timestamp entryTransactionDateTime, Timestamp updateTs, String accountNumber,
			String txStatus, double discFare, String entryExternPlazaId, String exitExternPlazaId,
			String entryPlazaDescription, String exitPlazaDescription, String exitLane, String entryLane,
			String agencyStatementName, String vehicleClass, boolean disputedFlag, double disputedAmount,
			String planName, String imageURL, String laneTxID, String extReferenceNo, String invoiceNumber,
			String escalationLevel, String entryExternLaneId, String exitExternLaneId, String category,
			String actualAxles, String transactionRowId, String description, double postTransactionBalance,
			String tranType) {
		super();
		this.transponderNumber = transponderNumber;
		this.plateNumber = plateNumber;
		this.plateState = plateState;
		this.postedDate = postedDate;
		this.transactionDateTime = transactionDateTime;
		this.entryTransactionDateTime = entryTransactionDateTime;
		this.updateTs = updateTs;
		this.accountNumber = accountNumber;
		this.txStatus = txStatus;
		this.discFare = discFare;
		this.entryExternPlazaId = entryExternPlazaId;
		this.exitExternPlazaId = exitExternPlazaId;
		this.entryPlazaDescription = entryPlazaDescription;
		this.exitPlazaDescription = exitPlazaDescription;
		this.exitLane = exitLane;
		this.entryLane = entryLane;
		this.agencyStatementName = agencyStatementName;
		this.vehicleClass = vehicleClass;
		this.disputedFlag = disputedFlag;
		this.disputedAmount = disputedAmount;
		this.planName = planName;
		this.imageURL = imageURL;
		this.laneTxID = laneTxID;
		this.extReferenceNo = extReferenceNo;
		this.invoiceNumber = invoiceNumber;
		this.escalationLevel = escalationLevel;
		this.entryExternLaneId = entryExternLaneId;
		this.exitExternLaneId = exitExternLaneId;
		this.category = category;
		this.actualAxles = actualAxles;
		this.transactionRowId = transactionRowId;
		this.description = description;
		this.postTransactionBalance = postTransactionBalance;
		this.tranType = tranType;
	}












	public String getTransponderNumber() {
		return transponderNumber;
	}

	public void setTransponderNumber(String transponderNumber) {
		this.transponderNumber = transponderNumber;
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

	public Timestamp getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(Timestamp transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
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

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getTxStatus() {
		return txStatus;
	}

	public void setTxStatus(String txStatus) {
		this.txStatus = txStatus;
	}

	public double getDiscFare() {
		return discFare;
	}

	public void setDiscFare(double discFare) {
		this.discFare = discFare;
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

	public String getEntryExternPlazaId() {
		return entryExternPlazaId;
	}

	public void setEntryExternPlazaId(String entryExternPlazaId) {
		this.entryExternPlazaId = entryExternPlazaId;
	}

	public String getExitExternPlazaId() {
		return exitExternPlazaId;
	}

	public void setExitExternPlazaId(String exitExternPlazaId) {
		this.exitExternPlazaId = exitExternPlazaId;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getEscalationLevel() {
		return escalationLevel;
	}

	public void setEscalationLevel(String escalationLevel) {
		this.escalationLevel = escalationLevel;
	}

	public String getEntryExternLaneId() {
		return entryExternLaneId;
	}

	public void setEntryExternLaneId(String entryExternLaneId) {
		this.entryExternLaneId = entryExternLaneId;
	}

	public String getExitExternLaneId() {
		return exitExternLaneId;
	}

	public void setExitExternLaneId(String exitExternLaneId) {
		this.exitExternLaneId = exitExternLaneId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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












	@Override
	public String toString() {
		return "TransactionResponsePOJO [transponderNumber=" + transponderNumber + ", plateNumber=" + plateNumber
				+ ", plateState=" + plateState + ", postedDate=" + postedDate + ", transactionDateTime="
				+ transactionDateTime + ", entryTransactionDateTime=" + entryTransactionDateTime + ", updateTs="
				+ updateTs + ", accountNumber=" + accountNumber + ", txStatus=" + txStatus + ", discFare=" + discFare
				+ ", entryExternPlazaId=" + entryExternPlazaId + ", exitExternPlazaId=" + exitExternPlazaId
				+ ", entryPlazaDescription=" + entryPlazaDescription + ", exitPlazaDescription=" + exitPlazaDescription
				+ ", exitLane=" + exitLane + ", entryLane=" + entryLane + ", agencyStatementName=" + agencyStatementName
				+ ", vehicleClass=" + vehicleClass + ", disputedFlag=" + disputedFlag + ", disputedAmount="
				+ disputedAmount + ", planName=" + planName + ", imageURL=" + imageURL + ", laneTxID=" + laneTxID
				+ ", extReferenceNo=" + extReferenceNo + ", invoiceNumber=" + invoiceNumber + ", escalationLevel="
				+ escalationLevel + ", entryExternLaneId=" + entryExternLaneId + ", exitExternLaneId="
				+ exitExternLaneId + ", category=" + category + ", actualAxles=" + actualAxles + ", transactionRowId="
				+ transactionRowId + ", description=" + description + ", postTransactionBalance="
				+ postTransactionBalance + ", tranType=" + tranType + "]";
	}

	
	
}
