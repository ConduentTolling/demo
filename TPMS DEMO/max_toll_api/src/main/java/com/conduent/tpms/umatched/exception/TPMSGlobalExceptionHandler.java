package com.conduent.tpms.umatched.exception;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class TPMSGlobalExceptionHandler extends ResponseEntityExceptionHandler{
    private static final Logger logger = LoggerFactory.getLogger(TPMSGlobalExceptionHandler.class);

    @Autowired
    private TPMSExceptionUtil fPMSExceptionUtill;

    @ExceptionHandler(TPMSGlobalException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public TPMSGateway<?> fpmsBadRequest(TPMSGlobalException e) {
        fPMSExceptionUtill.fpmsGobalExceptionSave(e);
        logger.error("error occuers at ", e);
        return new TPMSGateway<>(HttpStatus.OK, e.getMessage());
    }

    
@Override
public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
		HttpStatus status, WebRequest request) {

	Map<String, Object> body = new LinkedHashMap<>();
	//body.put("timestamp", new Date());
	body.put("status", false);
	body.put("httpStatus", HttpStatus.OK);
	// Get all errors
	List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
			.collect(Collectors.toList());
	body.put("message", errors);
	body.put("maxTollAmount", null);
	body.put("errors", null);
	body.put("fieldErrors", null);

	return new ResponseEntity<>(body, headers, HttpStatus.OK);

}

 

}
