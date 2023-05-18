package com.conduent.transactionSearch.dto;
import java.io.Serializable;


public class TransactionDto implements Serializable{
	private static final long serialVersionUID = 1L;
    private String transactionRowId;
    private String paymentId;
    private String compositeTransactionId;
    private String salesOrderId;
    private String tranCode;
    private String category;
    private String subCategory;
    private String endorsingAgency;
    private String revenueAgency;
    private String tranType;
    private String payType;
    private String description;
    private String statmentDescription;
    private Long   sequenceNumber;
    private String currentBalance;
    private String accountBalance;
    private String postTransactionBalance;
    private String preTransactionBalance;
    private Long   amount;
    private String etcAccountId;
    private String accountId;
    private String todId;
    private String creditGl;
    private String debitGl;
    private String deviceRequest;
    private String invoice;
    private Long   pmtSequenceNumber;
    private String transferReason;
    private String noticeNumber;
    private Long   balanceAffected;
    
	public TransactionDto() {
		super();
	}

	public String getTransactionRowId() {
		return transactionRowId;
	}

	public void setTransactionRowId(String transactionRowId) {
		this.transactionRowId = transactionRowId;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getCompositeTransactionId() {
		return compositeTransactionId;
	}

	public void setCompositeTransactionId(String compositeTransactionId) {
		this.compositeTransactionId = compositeTransactionId;
	}

	public String getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(String salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

	public String getTranCode() {
		return tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getEndorsingAgency() {
		return endorsingAgency;
	}

	public void setEndorsingAgency(String endorsingAgency) {
		this.endorsingAgency = endorsingAgency;
	}

	public String getRevenueAgency() {
		return revenueAgency;
	}

	public void setRevenueAgency(String revenueAgency) {
		this.revenueAgency = revenueAgency;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatmentDescription() {
		return statmentDescription;
	}

	public void setStatmentDescription(String statmentDescription) {
		this.statmentDescription = statmentDescription;
	}

	public Long getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(String currentBalance) {
		this.currentBalance = currentBalance;
	}

	public String getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getPostTransactionBalance() {
		return postTransactionBalance;
	}

	public void setPostTransactionBalance(String postTransactionBalance) {
		this.postTransactionBalance = postTransactionBalance;
	}

	public String getPreTransactionBalance() {
		return preTransactionBalance;
	}

	public void setPreTransactionBalance(String preTransactionBalance) {
		this.preTransactionBalance = preTransactionBalance;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getEtcAccountId() {
		return etcAccountId;
	}

	public void setEtcAccountId(String etcAccountId) {
		this.etcAccountId = etcAccountId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getTodId() {
		return todId;
	}

	public void setTodId(String todId) {
		this.todId = todId;
	}

	public String getCreditGl() {
		return creditGl;
	}

	public void setCreditGl(String creditGl) {
		this.creditGl = creditGl;
	}

	public String getDebitGl() {
		return debitGl;
	}

	public void setDebitGl(String debitGl) {
		this.debitGl = debitGl;
	}

	public String getDeviceRequest() {
		return deviceRequest;
	}

	public void setDeviceRequest(String deviceRequest) {
		this.deviceRequest = deviceRequest;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public Long getPmtSequenceNumber() {
		return pmtSequenceNumber;
	}

	public void setPmtSequenceNumber(Long pmtSequenceNumber) {
		this.pmtSequenceNumber = pmtSequenceNumber;
	}

	public String getTransferReason() {
		return transferReason;
	}

	public void setTransferReason(String transferReason) {
		this.transferReason = transferReason;
	}

	public String getNoticeNumber() {
		return noticeNumber;
	}

	public void setNoticeNumber(String noticeNumber) {
		this.noticeNumber = noticeNumber;
	}

	public Long getBalanceAffected() {
		return balanceAffected;
	}

	public void setBalanceAffected(Long balanceAffected) {
		this.balanceAffected = balanceAffected;
	}

	@Override
	public String toString() {
		return "TransactionDto [transactionRowId=" + transactionRowId + ", paymentId=" + paymentId
				+ ", compositeTransactionId=" + compositeTransactionId + ", salesOrderId=" + salesOrderId
				+ ", tranCode=" + tranCode + ", category=" + category + ", subCategory=" + subCategory
				+ ", endorsingAgency=" + endorsingAgency + ", revenueAgency=" + revenueAgency + ", tranType=" + tranType
				+ ", payType=" + payType + ", description=" + description + ", statmentDescription="
				+ statmentDescription + ", sequenceNumber=" + sequenceNumber + ", currentBalance=" + currentBalance
				+ ", accountBalance=" + accountBalance + ", postTransactionBalance=" + postTransactionBalance
				+ ", preTransactionBalance=" + preTransactionBalance + ", amount=" + amount + ", etcAccountId="
				+ etcAccountId + ", accountId=" + accountId + ", todId=" + todId + ", creditGl=" + creditGl
				+ ", debitGl=" + debitGl + ", deviceRequest=" + deviceRequest + ", invoice=" + invoice
				+ ", pmtSequenceNumber=" + pmtSequenceNumber + ", transferReason=" + transferReason + ", noticeNumber="
				+ noticeNumber + ", balanceAffected=" + balanceAffected + "]";
	}

	
    
}
