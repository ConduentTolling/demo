package com.conduent.tpms.iag.dao;

import java.util.List;

import com.conduent.tpms.iag.dto.FileCreationParameters;
import com.conduent.tpms.iag.model.FTAGDevice;


public interface FTAGDao
{
	public List<FTAGDevice> getDeviceListFromTTagAllSorted(int agencyId, String genType);
	FileCreationParameters getMappingConfigurationDetails(FileCreationParameters fielDto);
}
