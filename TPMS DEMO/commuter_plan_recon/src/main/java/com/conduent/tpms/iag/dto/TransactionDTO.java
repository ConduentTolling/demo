package com.conduent.tpms.iag.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TransactionDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String transactionRowId;
	private String paymentId;
	private String compositeTransactionId;
	private String transactionId;
	private String tranCode;
	private String category;
	private String subCategory;
	private Integer endorsingAgency;
	private Integer revenueAgency;
	private String tranType;
	private String payType;
	private String discription;
	private String statmentDiscription;
	private Long seqenceNumber;
	private String currentBalance;
	private String accountBalance;
	private String postTransactionBalance;
	private String preTransactionBalance;
	private Double amount;
	private String accountId;
	private String etcAccountId;
	private String todId;
	private String creditGl;
	private String debitGl;
	private String deviceRequest;
	private String invioce;
	private Long paymentSequenceNumber;
	private String transferReason;
	private String noticeNumber;
	private String issueType;
	private String salesOrderId;
	private Long balanceAffected;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private LocalDateTime createdDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private LocalDateTime updatedDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate postedDate;
	
	
	private String createdBy;
	private String updatedBy;
	private Double postPptlBalance;
	private Double postAccountBalance;
	private Double postViolBalance;
	private Double postPostPaidBalance;
	private Long laneTxId;
    private Double collectedAmount;
    private Double unrealizedAmount;
    private String unrealizedTrancode;
    private Double unitPrice;
    private int quantity;
    private String drCrInd;
    private String reasonCode;
    private String receivablesId;
	
    private String refInvId;
    private String referenceId;
    
    private String originalTransactionId;
    
	
	public String getRefInvId() {
		return refInvId;
	}
	public void setRefInvId(String refInvId) {
		this.refInvId = refInvId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getSalesOrderId() {
		return salesOrderId;
	}
	public void setSalesOrderId(String salesOrderId) {
		this.salesOrderId = salesOrderId;
	}
	
	public Long getBalanceAffected() {
		return balanceAffected;
	}
	public void setBalanceAffected(Long balanceAffected) {
		this.balanceAffected = balanceAffected;
	}
	public String getIssueType() {
		return issueType;
	}
	public void setIssueType(String issueType) {
		this.issueType = issueType;
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
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
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

	public Integer getEndorsingAgency() {
		return endorsingAgency;
	}
	public void setEndorsingAgency(Integer endorsingAgency) {
		this.endorsingAgency = endorsingAgency;
	}
	public Integer getRevenueAgency() {
		return revenueAgency;
	}
	public void setRevenueAgency(Integer revenueAgency) {
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
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	public String getStatmentDiscription() {
		return statmentDiscription;
	}
	public void setStatmentDiscription(String statmentDiscription) {
		this.statmentDiscription = statmentDiscription;
	}
	public Long getSeqenceNumber() {
		return seqenceNumber;
	}
	public void setSeqenceNumber(Long seqenceNumber) {
		this.seqenceNumber = seqenceNumber;
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
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(String etcAccountId) {
		this.etcAccountId = etcAccountId;
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
	public String getInvioce() {
		return invioce;
	}
	public void setInvioce(String invioce) {
		this.invioce = invioce;
	}
	public Long getPaymentSequenceNumber() {
		return paymentSequenceNumber;
	}
	public void setPaymentSequenceNumber(Long paymentSequenceNumber) {
		this.paymentSequenceNumber = paymentSequenceNumber;
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
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Double getPostPptlBalance() {
		return postPptlBalance;
	}
	public void setPostPptlBalance(Double postPptlBalance) {
		this.postPptlBalance = postPptlBalance;
	}
	public Double getPostAccountBalance() {
		return postAccountBalance;
	}
	public void setPostAccountBalance(Double postAccountBalance) {
		this.postAccountBalance = postAccountBalance;
	}
	public Double getPostViolBalance() {
		return postViolBalance;
	}
	public void setPostViolBalance(Double postViolBalance) {
		this.postViolBalance = postViolBalance;
	}
	public Double getPostPostPaidBalance() {
		return postPostPaidBalance;
	}
	public void setPostPostPaidBalance(Double postPostPaidBalance) {
		this.postPostPaidBalance = postPostPaidBalance;
	}
	public Long getLaneTxId() {
		return laneTxId;
	}
	public void setLaneTxId(Long laneTxId) {
		this.laneTxId = laneTxId;
	}
	public Double getCollectedAmount() {
		return collectedAmount;
	}
	public void setCollectedAmount(Double collectedAmount) {
		this.collectedAmount = collectedAmount;
	}
	public Double getUnrealizedAmount() {
		return unrealizedAmount;
	}
	public void setUnrealizedAmount(Double unrealizedAmount) {
		this.unrealizedAmount = unrealizedAmount;
	}
	public String getUnrealizedTrancode() {
		return unrealizedTrancode;
	}
	public void setUnrealizedTrancode(String unrealizedTrancode) {
		this.unrealizedTrancode = unrealizedTrancode;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getDrCrInd() {
		return drCrInd;
	}
	public void setDrCrInd(String drCrInd) {
		this.drCrInd = drCrInd;
	}
	
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getReceivablesId() {
		return receivablesId;
	}
	public void setReceivablesId(String receivablesId) {
		this.receivablesId = receivablesId;
	}
	
	
	public LocalDate getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(LocalDate postedDate) {
		this.postedDate = postedDate;
	}
	
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	
	
	public String getOriginalTransactionId() {
		return originalTransactionId;
	}
	public void setOriginalTransactionId(String originalTransactionId) {
		this.originalTransactionId = originalTransactionId;
	}
	@Override
	public String toString() {
		return "TransactionDTO [transactionRowId=" + transactionRowId + ", paymentId=" + paymentId
				+ ", compositeTransactionId=" + compositeTransactionId + ", transactionId=" + transactionId
				+ ", tranCode=" + tranCode + ", category=" + category + ", subCategory=" + subCategory
				+ ", endorsingAgency=" + endorsingAgency + ", revenueAgency=" + revenueAgency + ", tranType=" + tranType
				+ ", payType=" + payType + ", discription=" + discription + ", statmentDiscription="
				+ statmentDiscription + ", seqenceNumber=" + seqenceNumber + ", currentBalance=" + currentBalance
				+ ", accountBalance=" + accountBalance + ", postTransactionBalance=" + postTransactionBalance
				+ ", preTransactionBalance=" + preTransactionBalance + ", amount=" + amount + ", accountId=" + accountId
				+ ", etcAccountId=" + etcAccountId + ", todId=" + todId + ", creditGl=" + creditGl + ", debitGl="
				+ debitGl + ", deviceRequest=" + deviceRequest + ", invioce=" + invioce + ", paymentSequenceNumber="
				+ paymentSequenceNumber + ", transferReason=" + transferReason + ", noticeNumber=" + noticeNumber
				+ ", issueType=" + issueType + ", salesOrderId=" + salesOrderId + ", balanceAffected=" + balanceAffected
				+ ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", createdBy=" + createdBy
				+ ", updatedBy=" + updatedBy + ", postPptlBalance=" + postPptlBalance + ", postAccountBalance="
				+ postAccountBalance + ", postViolBalance=" + postViolBalance + ", postPostPaidBalance="
				+ postPostPaidBalance + ", laneTxId=" + laneTxId + ", collectedAmount=" + collectedAmount
				+ ", unrealizedAmount=" + unrealizedAmount + ", unrealizedTrancode=" + unrealizedTrancode
				+ ", unitPrice=" + unitPrice + ", quantity=" + quantity + ", drCrInd=" + drCrInd + ", reasonCode="
				+ reasonCode + ", receivablesId=" + receivablesId + ", referenceId="+ referenceId + "]";
	}
}
