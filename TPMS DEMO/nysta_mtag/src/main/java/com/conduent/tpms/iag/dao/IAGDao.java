package com.conduent.tpms.iag.dao;

import java.io.File;
import java.time.LocalDate;
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

	FileCreationParameters getMappingConfigurationDetails(FileCreationParameters fielDto);

	String getLastSuccessfullFile(Agency agency);

	void insertIntoInterAgencyFileXFERTable(InterAgencyFileXferDto interAgencyFileXferDto);

	void insertIntotTagstatusStatistics(ITAGHeaderInfoVO itagHeaderInfoVO, Map<String, String> recordCountMap, Map<String, String> recordCountMapITAGFile);
	
	public String checkIfFileAlreadyExists(File file);
	
	public void updateTSForExistingFileName(String fileName);

	public int checkIfStatisticsAlreadyExists(String fileName, LocalDate date, String itagFile);

}
