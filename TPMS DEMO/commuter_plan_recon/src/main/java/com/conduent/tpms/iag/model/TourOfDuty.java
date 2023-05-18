package com.conduent.tpms.iag.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * @author taniyan
 *
 */
public class TourOfDuty implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
	private String todId;
	
	private String userId;
	
	private String firstName;
	
	private String lastName;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private LocalDateTime actualStartDateTime;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate actualStartDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private LocalDateTime actualEndDateTime;
	
	/*
	 * @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS") private LocalDateTime
	 * lupDateTime;
	 */
	
	private Boolean financials;
	
	private Boolean depositMismatch;
	
	private String storeId;
	
	private String storeName;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private LocalDate  tourDate;
	
	private String status;
	
	private String loginMode;
	
	private String description;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private LocalDateTime  scheduleStartTime;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private LocalDateTime  scheduleEndTime;
	
	public String getTodId() {
		return todId;
	}
	public void setTodId(String todId) {
		this.todId = todId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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

	public Boolean getFinancials() {
		return financials;
	}
	public void setFinancials(Boolean financials) {
		this.financials = financials;
	}
	public Boolean getDepositMismatch() {
		return depositMismatch;
	}
	public void setDepositMismatch(Boolean depositMismatch) {
		this.depositMismatch = depositMismatch;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public LocalDate getTourDate() {
		return tourDate;
	}
	public void setTourDate(LocalDate tourDate) {
		this.tourDate = tourDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLoginMode() {
		return loginMode;
	}
	public void setLoginMode(String loginMode) {
		this.loginMode = loginMode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getActualStartDateTime() {
		return actualStartDateTime;
	}
	public void setActualStartDateTime(LocalDateTime actualStartDateTime) {
		this.actualStartDateTime = actualStartDateTime;
	}
	public LocalDateTime getActualEndDateTime() {
		return actualEndDateTime;
	}
	public void setActualEndDateTime(LocalDateTime actualEndDateTime) {
		this.actualEndDateTime = actualEndDateTime;
	}
	public LocalDateTime getScheduleStartTime() {
		return scheduleStartTime;
	}
	public void setScheduleStartTime(LocalDateTime scheduleStartTime) {
		this.scheduleStartTime = scheduleStartTime;
	}
	public LocalDateTime getScheduleEndTime() {
		return scheduleEndTime;
	}
	public void setScheduleEndTime(LocalDateTime scheduleEndTime) {
		this.scheduleEndTime = scheduleEndTime;
	}
	public LocalDate getActualStartDate() {
		return actualStartDate;
	}
	public void setActualStartDate(LocalDate actualStartDate) {
		this.actualStartDate = actualStartDate;
	}
	
	
	
	

	
	
}
