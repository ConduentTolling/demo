package com.conduent.tpms.iag.dao;

import java.util.List;

import com.conduent.tpms.iag.dto.FileCreationParameters;
import com.conduent.tpms.iag.dto.IncrTagStatusRecord;
import com.conduent.tpms.iag.model.FTAGDevice;


public interface FTAGIncrementDao
{
	public List<FTAGDevice> getDeviceListFromTTagAllSorted(int agencyId, String genType,List<IncrTagStatusRecord> deviceNoList);
	FileCreationParameters getMappingConfigurationDetails(FileCreationParameters fielDto);
	public List<IncrTagStatusRecord> getDeviceNoFromTInrTagStatusAllSorted(String genType);
	public String getLastDwnldTS();
}
