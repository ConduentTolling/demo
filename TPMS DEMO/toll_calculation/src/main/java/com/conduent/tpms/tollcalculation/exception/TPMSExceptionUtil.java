package com.conduent.tpms.tollcalculation.exception;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.tollcalculation.dto.ExceptionStoreDto;

@Component
public class TPMSExceptionUtil {
	
	 

	    public void fpmsGobalExceptionSave(TPMSGlobalException e) {
	    
	    	ExceptionStoreDto dto = new ExceptionStoreDto();
	        String linNo=String.valueOf(e.getStackTrace()[0].getLineNumber());
	        dto.setLineNo(linNo);
	        dto.setMethodName(e.getMethodName());
	        dto.setClassName(e.getNameOfClass());
	        dto.setLocalizedMsg(e.getLocalizedMessage().toString());
	        dto.setMessage(e.getMessage().toString());
	        dto.setCreatedDate(LocalDateTime.now());

	    }

	 

	    public void fpmsGobalExceptionSave(Exception e) {

	 

	        ExceptionStoreDto dto = new ExceptionStoreDto();
	        String linNo=String.valueOf(e.getStackTrace()[0].getLineNumber());
	        dto.setLineNo(linNo);
	        dto.setClassName(e.getClass().getName().toString());
	        dto.setMessage(e.getMessage().toString());
	        dto.setCreatedDate(LocalDateTime.now());

	 

	    }
	    
}
