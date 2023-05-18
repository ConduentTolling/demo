package com.conduent.tpms.iag.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import com.conduent.tpms.iag.constants.FileProcessStatus;
import com.conduent.tpms.iag.dao.impl.TQatpMappingDaoImpl;
import com.conduent.tpms.iag.dto.ITGUHeaderInfoVO;
import com.conduent.tpms.iag.exception.InvalidFileNameException;
import com.conduent.tpms.iag.exception.InvlidFileHeaderException;
import com.conduent.tpms.iag.model.AgencyInfoVO;
import com.conduent.tpms.iag.model.FileDetails;

@ExtendWith(MockitoExtension.class)
public class GenericITagFileParserImplTest {

	@InjectMocks
	GenericITguFileParserImpl genericITagFileParserImpl;

	@InjectMocks
	TQatpMappingDaoImpl tQatpMappingDaoImpl;

	@Mock
	JdbcTemplate jdbcTemplate;

	@Mock
	ITGUHeaderInfoVO headerVO;

	ClassLoader classLoader = getClass().getClassLoader();
	File file = new File(classLoader.getResource("009_20210310230438.ITAG").getFile());
	

	static FileDetails fileDetails;
	static List<AgencyInfoVO> aglistVo;
	static AgencyInfoVO agencyInfoVO;
	String fileName = file.getName();

	@BeforeAll
	public static void init() {

		fileDetails = new FileDetails();
		fileDetails.setFileName("009_20210310230438");
		fileDetails.setFileType("ITAG");
		fileDetails.setProcessName(null);
		fileDetails.setProcessStatus(FileProcessStatus.START);

		aglistVo = new ArrayList<AgencyInfoVO>();
		agencyInfoVO = new AgencyInfoVO();
		agencyInfoVO.setAgencyId(9);
		agencyInfoVO.setAgencyName("NEW JERSEY REGIONAL CONSORTIUM");
		agencyInfoVO.setAgencyShortName("NJ");
		agencyInfoVO.setConsortium("IAG");
		agencyInfoVO.setDevicePrefix("022");
		agencyInfoVO.setFilePrefix("022");

		aglistVo.add(agencyInfoVO);

	}

	@Test
	public void checkIfFileProcessedAlreadyTest() {

		fileDetails = tQatpMappingDaoImpl.checkIfFileProcessedAlready(fileName);
		assertThat(fileDetails != null);
	}
	/*		
	
	@Test
	public void checkIfFileNotProcessedAlreadyTest() {

		Mockito.when(tQatpMappingDaoImpl.checkIfFileProcessedAlready(fileName)).thenReturn(null);
		fileDetails = tQatpMappingDaoImpl.checkIfFileProcessedAlready(fileName);
		assertThat(fileDetails == null);
	}


	@Test
	public void validateThresholdLimit_ValidTest() throws InvalidFileNameException, IOException, InvlidFileHeaderException {

		when(headerVO.getRecordCount()).thenReturn("10");
		genericITagFileParserImpl.validateThresholdLimit(file);
		assertTrue(file.exists());
	}

	@Test
	public void validateThresholdLimit_ExceptionTest() throws InvalidFileNameException, IOException, InvlidFileHeaderException {

		when(headerVO.getRecordCount()).thenReturn("20");
		Assertions.assertThrows(InvlidFileHeaderException.class,
				() -> genericITagFileParserImpl.validateThresholdLimit(file));
	}
*/
}
