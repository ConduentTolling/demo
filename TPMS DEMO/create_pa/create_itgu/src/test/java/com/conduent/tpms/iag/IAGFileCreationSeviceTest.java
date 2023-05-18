package com.conduent.tpms.iag;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tpms.iag.constants.Constants;

import com.conduent.tpms.iag.service.IAGFileCreationSevice;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class IAGFileCreationSeviceTest {
	
	
	@InjectMocks
	IAGFileCreationSevice iagFileCreationSevice;
	
	@Test
	void validateFileEntitiesFull() {
		String genType = Constants.FILE_GEN_TYPE_FULL;
		Assertions.assertDoesNotThrow(() -> 
			iagFileCreationSevice.validateFileEntities(genType));
	}
	
	@Test
	void validateFileEntitiesIncr() {
		String genType = Constants.FILE_GEN_TYPE_INCR;
		Assertions.assertDoesNotThrow(() -> 
			iagFileCreationSevice.validateFileEntities(genType));
	}
}
