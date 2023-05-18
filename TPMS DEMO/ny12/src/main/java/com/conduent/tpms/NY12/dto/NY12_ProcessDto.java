package com.conduent.tpms.NY12.dto;

import java.io.Serializable;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

public class NY12_ProcessDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Expose(serialize = true, deserialize = true)
	private String recordType;
	@Expose(serialize = true, deserialize = true)
	private String statementType;
	@Expose(serialize = true, deserialize = true)
	private Long accountNumber;
	@Expose(serialize = true, deserialize = true)
	private Long etcAccountId;
	@Expose(serialize = true, deserialize = true)
	private String  subAccount;
	@Expose(serialize = true, deserialize = true)
	private String firstName;
	@Expose(serialize = true, deserialize = true)
	private String lastName;
	@Expose(serialize = true, deserialize = true)
	private String middleInitial;
	@Expose(serialize = true, deserialize = true)
	private String address1;
	@Expose(serialize = true, deserialize = true)
	private String address2;
	@Expose(serialize = true, deserialize = true)
	private String city;
	@Expose(serialize = true, deserialize = true)
	private String state;
	@Expose(serialize = true, deserialize = true)
	private String zip;
	@Expose(serialize = true, deserialize = true)
	private String zipplus4;
	@Expose(serialize = true, deserialize = true)
	private String country;
	@Expose(serialize = true, deserialize = true)
	private String email;
	@Expose(serialize = true, deserialize = true)
	private String language;
	@Expose(serialize = true, deserialize = true)
	private String replenishmentAmount;
	@Expose(serialize = true, deserialize = true)
	private String expirationDate;
	@Expose(serialize = true, deserialize = true)
	private String rebillThresholdAmount;
	@Expose(serialize = true, deserialize = true)
	private String primaryRebillPayType;
	@Expose(serialize = true, deserialize = true)
	private String companyName;
	@Expose(serialize = true, deserialize = true)
	private String devicePrefix;
	@Expose(serialize = true, deserialize = true)
	private String pinNumber;
	@Expose(serialize = true, deserialize = true)
	private String accountType;
	@Expose(serialize = true, deserialize = true)
	private String deliveryType;
	@Expose(serialize = true, deserialize = true)
	private Long agencyId;
	@Expose(serialize = true, deserialize = true)
	private String agencyShortName;
	@Expose(serialize = true, deserialize = true)
	private String nixieFlag;
	@Expose(serialize = true, deserialize = true)
	private String accountOpenDate;
	@Expose(serialize = true, deserialize = true)
	private String beginingBalance;
	@Expose(serialize = true, deserialize = true)
	private String endBalance;
	@Expose(serialize = true, deserialize = true)
	private String replenishmentMethod;
	@Expose(serialize = true, deserialize = true)
	private String expirationIndicator;
	@Expose(serialize = true, deserialize = true)
	private String tagDeposits;
	@Expose(serialize = true, deserialize = true)
	private String totalFees;
	@Expose(serialize = true, deserialize = true)
	private String totalReplenishment;
	@Expose(serialize = true, deserialize = true)
	private String statementBeginDate;
	@Expose(serialize = true, deserialize = true)
	private String statementEndDate;
	@Expose(serialize = true, deserialize = true)
	private String fileName;
	@Expose(serialize = true, deserialize = true)
	private String startDate;
	@Expose(serialize = true, deserialize = true)
	private String endDate;
	@Expose(serialize = true, deserialize = true)
	private String violationBalance;
	
	public String getRecordType() {
			return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getStatementType() {
		return statementType;
	}
	public void setStatementType(String statementType) {
		this.statementType = statementType;
	}
	public Long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Long getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(Long etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	
	public String getSubAccount() {
		return subAccount;
	}
	public void setSubAccount(String subAccount) {
		this.subAccount = subAccount;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleInitial() {
		return middleInitial;
	}
	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getZipplus4() {
		return zipplus4;
	}
	public void setZipplus4(String zipplus4) {
		this.zipplus4 = zipplus4;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getReplenishmentAmount() {
		return replenishmentAmount;
	}
	public void setReplenishmentAmount(String replenishmentAmount) {
		this.replenishmentAmount = replenishmentAmount;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getRebillThresholdAmount() {
		return rebillThresholdAmount;
	}
	public void setRebillThresholdAmount(String rebillThresholdAmount) {
		this.rebillThresholdAmount = rebillThresholdAmount;
	}
	public String getPrimaryRebillPayType() {
		return primaryRebillPayType;
	}
	public void setPrimaryRebillPayType(String primaryRebillPayType) {
		this.primaryRebillPayType = primaryRebillPayType;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getDevicePrefix() {
		return devicePrefix;
	}
	public void setDevicePrefix(String devicePrefix) {
		this.devicePrefix = devicePrefix;
	}
	public String getPinNumber() {
		return pinNumber;
	}
	public void setPinNumber(String pinNumber) {
		this.pinNumber = pinNumber;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public Long getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}
	public String getAgencyShortName() {
		return agencyShortName;
	}
	public void setAgencyShortName(String agencyShortName) {
		this.agencyShortName = agencyShortName;
	}
	public String getNixieFlag() {
		return nixieFlag;
	}
	public void setNixieFlag(String nixieFlag) {
		this.nixieFlag = nixieFlag;
	}
	public String getAccountOpenDate() {
		return accountOpenDate;
	}
	public void setAccountOpenDate(String accountOpenDate) {
		this.accountOpenDate = accountOpenDate;
	}
	public String getBeginingBalance() {
		return beginingBalance;
	}
	public void setBeginingBalance(String beginingBalance) {
		this.beginingBalance = beginingBalance;
	}
	public String getEndBalance() {
		return endBalance;
	}
	public void setEndBalance(String endBalance) {
		this.endBalance = endBalance;
	}
	public String getReplenishmentMethod() {
		return replenishmentMethod;
	}
	public void setReplenishmentMethod(String replenishmentMethod) {
		this.replenishmentMethod = replenishmentMethod;
	}
	public String getExpirationIndicator() {
		return expirationIndicator;
	}
	public void setExpirationIndicator(String expirationIndicator) {
		this.expirationIndicator = expirationIndicator;
	}
	public String getTagDeposits() {
		return tagDeposits;
	}
	public void setTagDeposits(String tagDeposits) {
		this.tagDeposits = tagDeposits;
	}
	public String getTotalFees() {
		return totalFees;
	}
	public void setTotalFees(String totalFees) {
		this.totalFees = totalFees;
	}
	public String getTotalReplenishment() {
		return totalReplenishment;
	}
	public void setTotalReplenishment(String totalReplenishment) {
		this.totalReplenishment = totalReplenishment;
	}
	public String getStatementBeginDate() {
		return statementBeginDate;
	}
	public void setStatementBeginDate(String statementBeginDate) {
		this.statementBeginDate = statementBeginDate;
	}
	public String getStatementEndDate() {
		return statementEndDate;
	}
	public void setStatementEndDate(String statementEndDate) {
		this.statementEndDate = statementEndDate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getViolationBalance() {
		return violationBalance;
	}
	public void setViolationBalance(String violationBalance) {
		this.violationBalance = violationBalance;
	}
	@Override
	public String toString() {
		return "InvoiceAccountHeaderDto [recordType=" + recordType + ", statementType=" + statementType
				+ ", accountNumber=" + accountNumber + ", etcAccountId=" + etcAccountId + ", subAccount=" + subAccount
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", middleInitial=" + middleInitial
				+ ", address1=" + address1 + ", address2=" + address2 + ", city=" + city + ", state=" + state + ", zip="
				+ zip + ", zipplus4=" + zipplus4 + ", country=" + country + ", email=" + email + ", language="
				+ language + ", replenishmentAmount=" + replenishmentAmount + ", expirationDate=" + expirationDate
				+ ", rebillThresholdAmount=" + rebillThresholdAmount + ", primaryRebillPayType=" + primaryRebillPayType
				+ ", companyName=" + companyName + ", devicePrefix=" + devicePrefix + ", pinNumber=" + pinNumber
				+ ", accountType=" + accountType + ", deliveryType=" + deliveryType + ", agencyId=" + agencyId
				+ ", agencyShortName=" + agencyShortName + ", nixieFlag=" + nixieFlag + ", accountOpenDate="
				+ accountOpenDate + ", beginingBalance=" + beginingBalance + ", endBalance=" + endBalance
				+ ", replenishmentMethod=" + replenishmentMethod + ", expirationIndicator=" + expirationIndicator
				+ ", tagDeposits=" + tagDeposits + ", totalFees=" + totalFees + ", totalReplenishment="
				+ totalReplenishment + ", statementBeginDate=" + statementBeginDate + ", statementEndDate="
				+ statementEndDate + ", fileName=" + fileName + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", violationBalance=" + violationBalance + "]";
	}
	
	
	
	



}
