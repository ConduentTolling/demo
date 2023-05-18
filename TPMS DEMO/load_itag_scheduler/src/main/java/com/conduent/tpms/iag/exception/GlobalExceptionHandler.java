package com.conduent.tpms.iag.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@EnableWebMvc
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler{

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleParseException(Exception e) {
		
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		
		LOGGER.error("Exception Occurred {}", e);
		return new ResponseEntity<>(e.getMessage(),
				httpStatus);
	}
}
