package com.conduent.tpms.iag.dao;


import java.util.List;

import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.FileParserParameters;
import com.conduent.tpms.iag.model.FileDetails;
import com.conduent.tpms.iag.model.TagDeviceDetails;
import com.conduent.tpms.iag.model.XferControl;

public interface TQatpMappingDao {

	public FileParserParameters getMappingConfigurationDetails( String fileType);
	public FileDetails checkIfFileProcessedAlready(String fileName);
	public Long insertFileDetails(FileDetails fileDetails);
	public XferControl checkFileDownloaded(String name);
	public String getLastSuccesfulProcessedFile(String fromAgencyId2);
	public void updateFileIntoCheckpoint(FileDetails fileDetails);
	public void insertAckDetails(AckFileInfoDto ackFileInfoDto);
	public void updateInvalidAddressIdinDevices(String device_no, int address_id);
	public TagDeviceDetails checkRecordExistInDevice(String device_no);
	//public void insertaddressinaddresstable(int address_id);
	public void updateaddressidindevicetable(int address_id);
	public List<String> getZipCodeList();	
	//public void insertQatpStatistics(QatpStatistics qatpStatistics);
	public FileParserParameters getMappingConfigurationDetailsITXC(String fileType);
	boolean checkForDuplicateFileNum(String value);
	long getLastSuccesfulProcessedFileSeqNum();
}
