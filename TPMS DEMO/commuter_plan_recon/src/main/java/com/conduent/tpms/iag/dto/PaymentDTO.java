package com.conduent.tpms.iag.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PaymentDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String paymentId;
	private String compositeTransactionId;
	private String payType;
	private String transType;
	private Double amount;
	private String emp;
	private Double availableAmount;
	private String etcAccountId;
	private String accountId;
	private String todId;
	private String status;
	private String checkNumber;
	private String bankAccountNumber;
	private String bankRoutingNumber;
	private String paymentReferenceNumber;
	private String merchantReferenceNumber;
	private String creditCardTokenNumber;
	private String expYear;
	private String expMonth;
	private String externalRefernceNumber;
	private boolean chargeBack;
	private boolean reversed;
	private String miSappliedAccountNumber;
	private boolean miSapplied;
	private String xrefTransactionId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private LocalDateTime bankFundedDate;
	private String orgTransactionId;
	private String ccResponse;
	private String confirmUser;
	private String confirmPassword;
	private boolean emvFlag;
	private boolean cardPresentFlag;
	private boolean debitIndicator;
	private String description;
	private String refundStatus;
	private String transactionId;
	private String digitalWallet;
	private String refundCheck;
	private String transactionDetails;
	private String blAddr;
	private String blAddrLane2;
	private String blCity;
	private String blCountry;
	private String blState;
	private String zipCode;
	private String ccAddressId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private LocalDateTime createdDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private LocalDateTime updatedDate;
	private String createdBy;
	private String updatedBy; 
	private String originalPaymentId;
	
	//20137 Enhancement - AVS_RESP_CODE and CVV_RESP_CODE column is not added in t_payment table.
	private String avsResp;
	private String cvvResp;
	
	private String merchantId;
	
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
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getEmp() {
		return emp;
	}
	public void setEmp(String emp) {
		this.emp = emp;
	}
	public Double getAvailableAmount() {
		return availableAmount;
	}
	public void setAvailableAmount(Double availableAmount) {
		this.availableAmount = availableAmount;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCheckNumber() {
		return checkNumber;
	}
	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	public String getBankRoutingNumber() {
		return bankRoutingNumber;
	}
	public void setBankRoutingNumber(String bankRoutingNumber) {
		this.bankRoutingNumber = bankRoutingNumber;
	}
	public String getPaymentReferenceNumber() {
		return paymentReferenceNumber;
	}
	public void setPaymentReferenceNumber(String paymentReferenceNumber) {
		this.paymentReferenceNumber = paymentReferenceNumber;
	}
	public String getMerchantReferenceNumber() {
		return merchantReferenceNumber;
	}
	public void setMerchantReferenceNumber(String merchantReferenceNumber) {
		this.merchantReferenceNumber = merchantReferenceNumber;
	}
	public String getCreditCardTokenNumber() {
		return creditCardTokenNumber;
	}
	public void setCreditCardTokenNumber(String creditCardTokenNumber) {
		this.creditCardTokenNumber = creditCardTokenNumber;
	}
	public String getExpYear() {
		return expYear;
	}
	public void setExpYear(String expYear) {
		this.expYear = expYear;
	}
	public String getExpMonth() {
		return expMonth;
	}
	public void setExpMonth(String expMonth) {
		this.expMonth = expMonth;
	}
	public String getExternalRefernceNumber() {
		return externalRefernceNumber;
	}
	public void setExternalRefernceNumber(String externalRefernceNumber) {
		this.externalRefernceNumber = externalRefernceNumber;
	}
	public boolean isChargeBack() {
		return chargeBack;
	}
	public void setChargeBack(boolean chargeBack) {
		this.chargeBack = chargeBack;
	}
	public boolean isReversed() {
		return reversed;
	}
	public void setReversed(boolean reversed) {
		this.reversed = reversed;
	}
	public String getMiSappliedAccountNumber() {
		return miSappliedAccountNumber;
	}
	public void setMiSappliedAccountNumber(String miSappliedAccountNumber) {
		this.miSappliedAccountNumber = miSappliedAccountNumber;
	}
	public boolean isMiSapplied() {
		return miSapplied;
	}
	public void setMiSapplied(boolean miSapplied) {
		this.miSapplied = miSapplied;
	}
	public String getXrefTransactionId() {
		return xrefTransactionId;
	}
	public void setXrefTransactionId(String xrefTransactionId) {
		this.xrefTransactionId = xrefTransactionId;
	}
	public LocalDateTime getBankFundedDate() {
		return bankFundedDate;
	}
	public void setBankFundedDate(LocalDateTime bankFundedDate) {
		this.bankFundedDate = bankFundedDate;
	}
	public String getOrgTransactionId() {
		return orgTransactionId;
	}
	public void setOrgTransactionId(String orgTransactionId) {
		this.orgTransactionId = orgTransactionId;
	}
	public String getCcResponse() {
		return ccResponse;
	}
	public void setCcResponse(String ccResponse) {
		this.ccResponse = ccResponse;
	}
	public String getConfirmUser() {
		return confirmUser;
	}
	public void setConfirmUser(String confirmUser) {
		this.confirmUser = confirmUser;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public boolean isEmvFlag() {
		return emvFlag;
	}
	public void setEmvFlag(boolean emvFlag) {
		this.emvFlag = emvFlag;
	}
	public boolean isCardPresentFlag() {
		return cardPresentFlag;
	}
	public void setCardPresentFlag(boolean cardPresentFlag) {
		this.cardPresentFlag = cardPresentFlag;
	}
	public boolean isDebitIndicator() {
		return debitIndicator;
	}
	public void setDebitIndicator(boolean debitIndicator) {
		this.debitIndicator = debitIndicator;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getDigitalWallet() {
		return digitalWallet;
	}
	public void setDigitalWallet(String digitalWallet) {
		this.digitalWallet = digitalWallet;
	}
	public String getRefundCheck() {
		return refundCheck;
	}
	public void setRefundCheck(String refundCheck) {
		this.refundCheck = refundCheck;
	}
	public String getTransactionDetails() {
		return transactionDetails;
	}
	public void setTransactionDetails(String transactionDetails) {
		this.transactionDetails = transactionDetails;
	}
	public String getBlAddr() {
		return blAddr;
	}
	public void setBlAddr(String blAddr) {
		this.blAddr = blAddr;
	}
	public String getBlAddrLane2() {
		return blAddrLane2;
	}
	public void setBlAddrLane2(String blAddrLane2) {
		this.blAddrLane2 = blAddrLane2;
	}
	public String getBlCity() {
		return blCity;
	}
	public void setBlCity(String blCity) {
		this.blCity = blCity;
	}
	public String getBlCountry() {
		return blCountry;
	}
	public void setBlCountry(String blCountry) {
		this.blCountry = blCountry;
	}
	public String getBlState() {
		return blState;
	}
	public void setBlState(String blState) {
		this.blState = blState;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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
	public String getCcAddressId() {
		return ccAddressId;
	}
	public void setCcAddressId(String ccAddressId) {
		this.ccAddressId = ccAddressId;
	}
	
	public String getAvsResp() {
		return avsResp;
	}
	public void setAvsResp(String avsResp) {
		this.avsResp = avsResp;
	}
	public String getCvvResp() {
		return cvvResp;
	}
	public void setCvvResp(String cvvResp) {
		this.cvvResp = cvvResp;
	}
	
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	public String getOriginalPaymentId() {
		return originalPaymentId;
	}
	public void setOriginalPaymentId(String originalPaymentId) {
		this.originalPaymentId = originalPaymentId;
	}
	@Override
	public String toString() {
		return "PaymentDTO [paymentId=" + paymentId + ", compositeTransactionId=" + compositeTransactionId
				+ ", payType=" + payType + ", transType=" + transType + ", amount=" + amount + ", emp=" + emp
				+ ", availableAmount=" + availableAmount + ", etcAccountId=" + etcAccountId + ", accountId=" + accountId
				+ ", todId=" + todId + ", status=" + status + ", checkNumber=" + checkNumber + ", bankAccountNumber="
				+ bankAccountNumber + ", bankRoutingNumber=" + bankRoutingNumber + ", paymentReferenceNumber="
				+ paymentReferenceNumber + ", merchantReferenceNumber=" + merchantReferenceNumber
				+ ", creditCardTokenNumber=" + creditCardTokenNumber + ", expYear=" + expYear + ", expMonth=" + expMonth
				+ ", externalRefernceNumber=" + externalRefernceNumber + ", chargeBack=" + chargeBack + ", reversed="
				+ reversed + ", miSappliedAccountNumber=" + miSappliedAccountNumber + ", miSapplied=" + miSapplied
				+ ", xrefTransactionId=" + xrefTransactionId + ", bankFundedDate=" + bankFundedDate
				+ ", orgTransactionId=" + orgTransactionId + ", ccResponse=" + ccResponse + ", confirmUser="
				+ confirmUser + ", confirmPassword=" + confirmPassword + ", emvFlag=" + emvFlag + ", cardPresentFlag="
				+ cardPresentFlag + ", debitIndicator=" + debitIndicator + ", description=" + description
				+ ", refundStatus=" + refundStatus + ", transactionId=" + transactionId + ", digitalWallet="
				+ digitalWallet + ", refundCheck=" + refundCheck + ", transactionDetails=" + transactionDetails
				+ ", blAddr=" + blAddr + ", blAddrLane2=" + blAddrLane2 + ", blCity=" + blCity + ", blCountry="
				+ blCountry + ", blState=" + blState + ", zipCode=" + zipCode + ", ccAddressId=" + ccAddressId
				+ ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", createdBy=" + createdBy
				+ ", updatedBy=" + updatedBy + ", avsResp=" + avsResp + ", cvvResp=" + cvvResp + ", merchantId="
				+ merchantId + "]";
	}
	
	
}
