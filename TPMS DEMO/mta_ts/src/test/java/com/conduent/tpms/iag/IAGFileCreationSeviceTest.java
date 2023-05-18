package com.conduent.tpms.iag;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.dto.FileCreationParameters;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.exception.InvalidFileFormatException;
import com.conduent.tpms.iag.exception.InvalidFileGenerationTypeException;
import com.conduent.tpms.iag.service.TSFileCreationService;
import com.conduent.tpms.iag.service.impl.TSFileCreationServiceImpl;

@ExtendWith(MockitoExtension.class)
public class IAGFileCreationSeviceTest {
	
	
	@InjectMocks
	TSFileCreationServiceImpl iagFileCreationSevice;
	
	@Mock
	TSFileCreationService iagFileCreationSeviceMock;
	
	@Mock
	FileCreationParameters fileCreationParameters;
	
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
	
	@Test
	void validateFileEntitiesExcp() {
		String genType = Constants.STOLEN;
		
		Assertions.assertThrows(InvalidFileGenerationTypeException.class, () -> {
			iagFileCreationSevice.validateFileEntities(genType);
		  });
	}
	
/*	@Test
	void processFileCreationExcp() throws InvalidFileGenerationTypeException, InvalidFileFormatException {
		TAGDevice tagDevice = new TAGDevice();
		List<TAGDevice> tagDeviceList = new ArrayList<>();
		tagDeviceList.add(tagDevice);
		
		Assertions.assertDoesNotThrow( () -> 
			iagFileCreationSevice.processFileCreation(tagDeviceList));
	}
	*/
	@Test
	void doPaddingLeft() throws InvalidFileGenerationTypeException, InvalidFileFormatException {
		String etcPlanCode = "32";
		String expectedEtcPlanCode = "00000032";
		MappingInfoDto fMapping = new MappingInfoDto();	
		fMapping.setFieldLength(8);
		fMapping.setJustification(Constants.LEFT);
		fMapping.setDefaultValue(etcPlanCode);
		fMapping.setPadCharacter("0");
		String value = iagFileCreationSevice.doPadding(fMapping, etcPlanCode);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedEtcPlanCode, value);
	}
	
	@Test
	void doPaddingRight() throws InvalidFileGenerationTypeException, InvalidFileFormatException {
		String etcPlanCode = "32";
		String expectedEtcPlanCode = "32000000";
		MappingInfoDto fMapping = new MappingInfoDto();	
		fMapping.setFieldLength(8);
		fMapping.setJustification(Constants.RIGHT);
		fMapping.setDefaultValue(etcPlanCode);
		fMapping.setPadCharacter("0");
		String value = iagFileCreationSevice.doPadding(fMapping, etcPlanCode);
		Assertions.assertNotNull(value);
		Assertions.assertEquals(expectedEtcPlanCode, value);
	}
}
