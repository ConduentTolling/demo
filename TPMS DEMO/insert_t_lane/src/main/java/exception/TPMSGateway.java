package exception;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

public class TPMSGateway<T> {

	private Boolean status;
	private HttpStatus httpStatus;
	private String message;
	private T result;
	private List<String> errors;
	private List<FieldError> fieldErrors;

	public TPMSGateway() {
		super();
	}

	public TPMSGateway(Boolean status, final HttpStatus httpStatus, final T result) {
		super();
		this.status = status;
		this.httpStatus = httpStatus;
		this.result = result;
	}

	public TPMSGateway(Boolean status, final HttpStatus httpStatus, final String message, final List<String> errors) {
		super();
		this.status = status;
		this.httpStatus = httpStatus;
		this.message = message;
		this.errors = errors;
	}

	public TPMSGateway(String message, final List<String> errors) {
		super();
		this.status = false;
		this.httpStatus = HttpStatus.OK;
		this.message = message;
		this.errors = errors;

	}
	
	//newly implemented
	public TPMSGateway(Boolean status, String message) {
		super();
		this.status = false;
		this.httpStatus = HttpStatus.NOT_MODIFIED;
		this.message = message;

	}

	public TPMSGateway(Boolean status, final HttpStatus httpStatus, final String message, final String error) {
		super();
		this.status = status;
		this.httpStatus = httpStatus;
		this.message = message;
		if (!StringUtils.isEmpty(error)) {
			errors = Arrays.asList(error);
		}

	}

	public TPMSGateway(final HttpStatus httpStatus, final String message) {
		super();
		this.status = Boolean.FALSE;
		this.httpStatus = httpStatus;
		this.message = message;

	}

	public TPMSGateway(final HttpStatus httpStatus, final T result, final String message) {
		super();
		this.httpStatus = httpStatus;
		this.message = message;
		this.result = result;
	}

	public TPMSGateway(Boolean status, final HttpStatus httpStatus, final String message, final T result) {
		super();
		this.status = status;
		this.httpStatus = httpStatus;
		this.message = message;
		this.result = result;
	}

	public TPMSGateway(Boolean status, final HttpStatus httpStatus, final List<String> errors) {
		super();
		this.status = status;
		this.httpStatus = httpStatus;
		this.message = errors.toString();
		this.errors = errors;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

}