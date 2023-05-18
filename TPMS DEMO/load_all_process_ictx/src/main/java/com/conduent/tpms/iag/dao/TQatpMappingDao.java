package com.conduent.tpms.iag.dao;


import com.conduent.tpms.iag.dto.FileParserParameters;
import com.conduent.tpms.iag.exception.FileAlreadyProcessedException;
import com.conduent.tpms.iag.model.FileDetails;

public interface TQatpMappingDao {

	public FileParserParameters getMappingConfigurationDetails( String fileType);
	public FileDetails checkIfFileProcessedAlready(String fileName) throws FileAlreadyProcessedException;
	public void updateFileIntoCheckpoint(FileDetails fileDetails);
	public Long insertFileDetails(FileDetails fileDetails);

}
