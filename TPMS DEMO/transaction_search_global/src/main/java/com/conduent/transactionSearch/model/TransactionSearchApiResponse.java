package com.conduent.transactionSearch.model;

import java.util.List;

public class TransactionSearchApiResponse {
    private boolean status;
    private String httpStatus = "OK";
    private String message;
    private TransactionResponse result;
    private String errors;
    private List<String> fieldErrors;
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public TransactionResponse getResult() {
		return result;
	}
	public void setResult(TransactionResponse result) {
		this.result = result;
	}
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	public List<String> getFieldErrors() {
		return fieldErrors;
	}
	public void setFieldErrors(List<String> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
    
}
