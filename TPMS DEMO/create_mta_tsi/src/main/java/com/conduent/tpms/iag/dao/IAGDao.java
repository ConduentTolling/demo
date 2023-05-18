package com.conduent.tpms.iag.dao;

import java.util.List;

import com.conduent.tpms.iag.dto.FileCreationParameters;
import com.conduent.tpms.iag.dto.IncrTagStatusRecord;
import com.conduent.tpms.iag.model.TAGDevice;


/**
 * Interface for DAO operations
 * 
 * @author Urvashi C
 *
 */
public interface IAGDao
{
	public List<TAGDevice> getDevieListFromTTagAllSorted(int agencyId, String genType);

	FileCreationParameters getMappingConfigurationDetails(FileCreationParameters fielDto);
	
	public TAGDevice getDevieListFromTTagAllSorted1(int agencyId, String genType,IncrTagStatusRecord deviceno);
	
	public List<IncrTagStatusRecord> getDeviceNoFromTInrTagStatusAllSorted(String genType);

	String getLastDwnldTS(String format, boolean isRFK);
}
