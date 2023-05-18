package com.conduent.transactionSearch.exception;

import com.conduent.transactionSearch.constants.TransactionSearchConstants;
import com.conduent.transactionSearch.model.TransactionSearchApiResponse;

public class TPMSGlobalException extends RuntimeException 
{
    private static final long serialVersionUID = 1L;
    
    private String nameOfClass;
    private String methodName;
    private String errorMessage;
    
    public TPMSGlobalException(String message, String nameOfClass, String methodName) 
    {
        super(message);
        this.nameOfClass = nameOfClass;
        this.methodName = methodName;
        this.errorMessage = message;
        TransactionSearchApiResponse transactionSearchApiResponse = new TransactionSearchApiResponse();
        transactionSearchApiResponse.setStatus(false);	
		transactionSearchApiResponse.setResult(null);
		transactionSearchApiResponse.setMessage(TransactionSearchConstants.RESPONSE_FAILED_MESSAGE);
		transactionSearchApiResponse.setErrors(TransactionSearchConstants.RESPONSE_ERROR_MESSAGE);
		transactionSearchApiResponse.setFieldErrors(TransactionSearchConstants.RESPONSE_FIELD_ERRORS);

    }
    
    
    public String getNameOfClass() {
        return nameOfClass;
    }
    public void setNameOfClass(String nameOfClass) {
        this.nameOfClass = nameOfClass;
    }
    public String getMethodName() {
        return methodName;
    }
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
}
