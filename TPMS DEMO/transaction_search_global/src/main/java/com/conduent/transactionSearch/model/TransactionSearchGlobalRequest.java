package com.conduent.transactionSearch.model;

import io.swagger.annotations.ApiModelProperty;

public class TransactionSearchGlobalRequest {
	
	@ApiModelProperty(value = "ETC account Id", dataType = "Integer", required = true, example = "2")
	private String  etcAccountId;
	private String  category;
	private String  plateNumber;
	//private String  deviceNo;
	private String  postedFromDate;
	private String  postedToDate;
	private String  transactionFromDate;
	private String  transactionToDate;
	private String  plazaId;
	private String  agencyShortName;
	private String  sortType;
	private Integer page;
	private Integer size;
	private String  status;
	private String  type;
	private String  invoiceNo;
	private String  escLevel;
	private String  fromDate;
	private String  toDate;
	private String accountNumber;
	private String plateState;
	private String laneTxId;
	private String extReferenceNo;
	private String laneId;
	private String transponderNumber;
	private String sortBy;
	
	

	
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public String getTransponderNumber() {
		return transponderNumber;
	}
	public void setTransponderNumber(String transponderNumber) {
		this.transponderNumber = transponderNumber;
	}
	public String getEtcAccountId() {
		return etcAccountId;
	}
	public void setEtcAccountId(String etcAccountId) {
		this.etcAccountId = etcAccountId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
//	public String getDeviceNo() {
//		return deviceNo;
//	}
//	public void setDeviceNo(String deviceNo) {
//		this.deviceNo = deviceNo;
//	}
	public String getPostedFromDate() {
		return postedFromDate;
	}
	public void setPostedFromDate(String postedFromDate) {
		this.postedFromDate = postedFromDate;
	}
	public String getPostedToDate() {
		return postedToDate;
	}
	public void setPostedToDate(String postedToDate) {
		this.postedToDate = postedToDate;
	}
	public String getTransactionFromDate() {
		return transactionFromDate;
	}
	public void setTransactionFromDate(String transactionFromDate) {
		this.transactionFromDate = transactionFromDate;
	}
	public String getTransactionToDate() {
		return transactionToDate;
	}
	public void setTransactionToDate(String transactionToDate) {
		this.transactionToDate = transactionToDate;
	}
	public String getPlazaId() {
		return plazaId;
	}
	public void setPlazaId(String plazaId) {
		this.plazaId = plazaId;
	}
	public String getAgencyShortName() {
		return agencyShortName;
	}
	public void setAgencyShortName(String agencyShortName) {
		this.agencyShortName = agencyShortName;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getEscLevel() {
		return escLevel;
	}
	public void setEscLevel(String escLevel) {
		this.escLevel = escLevel;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getPlateState() {
		return plateState;
	}
	public void setPlateState(String plateState) {
		this.plateState = plateState;
	}
	public String getLaneTxId() {
		return laneTxId;
	}
	public void setLaneTxId(String laneTxId) {
		this.laneTxId = laneTxId;
	}
	public String getExtReferenceNo() {
		return extReferenceNo;
	}
	public void setExtReferenceNo(String extReferenceNo) {
		this.extReferenceNo = extReferenceNo;
	}
	public String getLaneId() {
		return laneId;
	}
	public void setLaneId(String laneId) {
		this.laneId = laneId;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	
	}
