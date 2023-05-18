package com.conduent.tpms.iag;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.exception.InvalidFileGenerationTypeException;
import com.conduent.tpms.iag.service.IAGFileCreationSevice;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class IAGFileCreationSeviceTest {
	
	
	@InjectMocks
	IAGFileCreationSevice iagFileCreationSevice;
	
	@Test
	void validateFileEntities() {
		int agencyId = 3;
		String fileType = Constants.ATAG;
		String genType = Constants.FILE_GEN_TYPE_FULL;
		Assertions.assertDoesNotThrow(() -> 
			iagFileCreationSevice.validateFileEntities(agencyId, fileType, genType));
	}
	
	@Test
	void validateFileEntitiesExcp1() {
		int agencyId = 1;
		String fileType = Constants.ATAG;
		String genType = Constants.FILE_GEN_TYPE_FULL;	
		Assertions.assertThrows(InvalidFileGenerationTypeException.class, () -> {
			iagFileCreationSevice.validateFileEntities(agencyId, fileType, genType);
		  });
	}
	
	@Test
	void validateFileEntitiesExcp2() {
		int agencyId = 3;
		String fileType = "ATAF";
		String genType = Constants.FILE_GEN_TYPE_FULL;
		Assertions.assertThrows(InvalidFileGenerationTypeException.class, () -> {
			iagFileCreationSevice.validateFileEntities(agencyId, fileType, genType);
		  });
	}
	
	@Test
	void validateFileEntitiesExcp3() {
		int agencyId = 3;
		String fileType = Constants.ATAG;
		String genType = "FULR";	
		Assertions.assertThrows(InvalidFileGenerationTypeException.class, () -> {
			iagFileCreationSevice.validateFileEntities(agencyId, fileType,genType);
		  });
	}
}
