package com.conduent.tpms.qeval.BatchModel;

import java.sql.Date;

public class StatementMessageData {
	
	private Long statementMessageType;
	private Date applicableDate;
	private Date expiryDate;
	private String paramvalue;
	private String message;
	private Date updateTs;
	private String message1;
	private String message2;
	
	
	public Long getStatementMessageType() {
		return statementMessageType;
	}
	public void setStatementMessageType(Long statementMessageType) {
		this.statementMessageType = statementMessageType;
	}
	public Date getApplicableDate() {
		return applicableDate;
	}
	public void setApplicableDate(Date applicableDate) {
		this.applicableDate = applicableDate;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getParamvalue() {
		return paramvalue;
	}
	public void setParamvalue(String paramvalue) {
		this.paramvalue = paramvalue;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(Date updateTs) {
		this.updateTs = updateTs;
	}
	public String getMessage1() {
		return message1;
	}
	public void setMessage1(String message1) {
		this.message1 = message1;
	}
	public String getMessage2() {
		return message2;
	}
	public void setMessage2(String message2) {
		this.message2 = message2;
	}
	

}

