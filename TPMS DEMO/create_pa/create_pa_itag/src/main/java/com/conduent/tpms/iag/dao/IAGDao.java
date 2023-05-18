package com.conduent.tpms.iag.dao;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.conduent.tpms.iag.dto.FileCreationParameters;
import com.conduent.tpms.iag.dto.IncrTagStatusRecord;
import com.conduent.tpms.iag.model.TAGDevice;

/**
 * Interface for DAO operations
 * 
 * @author taniyan
 *
 */
public interface IAGDao
{

	FileCreationParameters getMappingConfigurationDetails(FileCreationParameters fielDto);
	
	public List<TAGDevice> getDeviceInfofromTTagStatusAllSorted(int agencyId, String genType);
	
	public List<TAGDevice> getDevieListFromTTagAllSorted1(int agencyId, String genType, List<IncrTagStatusRecord> devicenoList);
	
	public List<IncrTagStatusRecord> getDeviceNoFromTInrTagStatusAllSorted(String genType);
	
	
	String getLastDwnldTS();
}
