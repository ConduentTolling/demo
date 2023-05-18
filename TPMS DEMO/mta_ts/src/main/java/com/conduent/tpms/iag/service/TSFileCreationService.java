package com.conduent.tpms.iag.service;

import java.io.IOException;
import java.util.List;

import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.exception.InvalidFileGenerationTypeException;
import com.conduent.tpms.iag.model.TAGDevice;

public interface TSFileCreationService {

	public void createTagFile(List<TAGDevice> tagDeviceList,boolean isRFK) throws IOException, Exception;
	public String doFieldMapping(MappingInfoDto fMapping, TAGDevice tagDevice);
	public void updateRecordCounter(TAGDevice tagDeviceObj);
	public String fileCreationTemplate(int agencyId, String fileType, String genType, boolean isRFK);
	public void validateFileEntities(String genType) throws InvalidFileGenerationTypeException;
	public String doPadding(MappingInfoDto fMapping, String etcPlanCode);
	public void validateAgencyId(int agencyId) throws InvalidFileGenerationTypeException;
	public void validateFileType(String fileType) throws InvalidFileGenerationTypeException;
}
