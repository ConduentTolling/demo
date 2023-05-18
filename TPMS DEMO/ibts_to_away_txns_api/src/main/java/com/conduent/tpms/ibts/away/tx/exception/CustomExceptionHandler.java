package com.conduent.tpms.ibts.away.tx.exception;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.conduent.tpms.ibts.away.tx.dto.ErrorResponseDto;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger customExceptionHandlerlogger = LoggerFactory.getLogger(CustomExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponseDto error = new ErrorResponseDto("Internal Server Error", details);
		customExceptionHandlerlogger.error("Exception message: {} ", ExceptionUtils.getStackTrace(ex));
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(DateFormatException.class)
	public final ResponseEntity<Object> handleAllExceptions(DateFormatException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.addAll(ex.getErrorMessages());
		ErrorResponseDto error = new ErrorResponseDto(ex.getMessage(), details);
		customExceptionHandlerlogger.error("Date format exception message: {} ", ExceptionUtils.getStackTrace(ex));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DateTimeFormatException.class)
	public final ResponseEntity<Object> handleAllExceptions(DateTimeFormatException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.addAll(ex.getErrorMessages());
		ErrorResponseDto error = new ErrorResponseDto(ex.getMessage(), details);
		customExceptionHandlerlogger.error("DateTime format exception message: {} ", ExceptionUtils.getStackTrace(ex));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DuplicateTransactionException.class)
	public final ResponseEntity<Object> handleAllExceptions(DuplicateTransactionException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getMessage());
		ErrorResponseDto error = new ErrorResponseDto("Duplicate Transaction Error", details);
		customExceptionHandlerlogger.error("Duplicate transaction exception message: {} ",
				ExceptionUtils.getStackTrace(ex));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> details = new ArrayList<>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			details.add(error.getDefaultMessage());
		}
		ErrorResponseDto error = new ErrorResponseDto("Mandatory Field Validation Error", details);
		customExceptionHandlerlogger.error("Mandatory field exception message: {} ", ExceptionUtils.getStackTrace(ex));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}
