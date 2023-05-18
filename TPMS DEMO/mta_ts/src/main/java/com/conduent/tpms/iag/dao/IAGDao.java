package com.conduent.tpms.iag.dao;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.conduent.tpms.iag.dto.FileCreationParameters;
import com.conduent.tpms.iag.dto.ITAGHeaderInfoVO;
import com.conduent.tpms.iag.dto.InterAgencyFileXferDto;
import com.conduent.tpms.iag.model.Agency;
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

	public FileCreationParameters getMappingConfigurationDetails(FileCreationParameters fielDto);

	public String getLastDwnldTS();
	
	public String getLastDwnldTSRFK();

	public String getLastSuccessfullFile(Agency agency);
	
	public void insertIntoInterAgencyFileXFERTable(InterAgencyFileXferDto interAgencyFileXferDto);

	public void insertIntotTagstatusStatistics(ITAGHeaderInfoVO itagHeaderInfoVO, Map<String, String> recordCountMap);
	
	public String checkIfFileAlreadyExists(File file);
	
	public void updateTSForExistingFileName(String fileName);

	public String getPrevfile();
	
	public String getPrevfileRFK();
}
