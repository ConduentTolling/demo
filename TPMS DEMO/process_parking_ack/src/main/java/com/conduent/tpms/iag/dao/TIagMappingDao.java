package com.conduent.tpms.iag.dao;

import com.conduent.tpms.iag.dto.FileParserParameters;
import com.conduent.tpms.iag.model.XferControl;

public interface TIagMappingDao
{
	public FileParserParameters getMappingConfigurationDetails( FileParserParameters fielDto);
	public Long getAgencyId(String  filePrefix);
	public Long getLastProcessedRecordCount(Long agencyId);
	
}