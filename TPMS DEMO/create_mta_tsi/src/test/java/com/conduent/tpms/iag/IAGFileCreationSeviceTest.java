package com.conduent.tpms.iag;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.dto.FileCreationParameters;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.exception.InvalidFileFormatException;
import com.conduent.tpms.iag.exception.InvalidFileGenerationTypeException;
import com.conduent.tpms.iag.model.TAGDevice;
import com.conduent.tpms.iag.service.IAGFileCreationSevice;

@ExtendWith(MockitoExtension.class)
public class IAGFileCreationSeviceTest {
	
	
	@InjectMocks
	IAGFileCreationSevice iagFileCreationSevice;
	
	@Mock
	IAGFileCreationSevice iagFileCreationSeviceMock;
	
	@Mock
	FileCreationParameters fileCreationParameters;
	
	@Test
	void validateFileEntitiesIncr() {
		int agencyId = 2;
		String fileType = Constants.TSI;
		String genType = Constants.FILE_GEN_TYPE_INCR;
		Assertions.assertDoesNotThrow(() -> 
			iagFileCreationSevice.validateFileEntities(agencyId, fileType, genType));
	}
	
	@Test
	void validateFileEntitiesExcp() {
		int agencyId = 2;
		String fileType = Constants.TSI;
		String genType = Constants.FILE_GEN_TYPE_FULL;
		Assertions.assertThrows(InvalidFileGenerationTypeException.class, () -> {
			iagFileCreationSevice.validateFileEntities(agencyId, fileType, genType);
		  });
	}
	
	@Test
	void processFileCreationExcp() throws InvalidFileGenerationTypeException, InvalidFileFormatException {
		TAGDevice tagDevice = new TAGDevice();
		List<TAGDevice> tagDeviceList = new ArrayList<>();
		tagDeviceList.add(tagDevice);
		
		Assertions.assertDoesNotThrow( () -> 
			iagFileCreationSevice.processFileCreation(tagDeviceList, false));
	}
	
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
