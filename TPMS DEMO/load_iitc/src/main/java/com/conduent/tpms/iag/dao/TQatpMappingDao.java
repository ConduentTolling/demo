package com.conduent.tpms.iag.dao;


import java.util.List;

import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.FileParserParameters;
import com.conduent.tpms.iag.exception.FileAlreadyProcessedException;
import com.conduent.tpms.iag.model.CustomerAddressRecord;
import com.conduent.tpms.iag.model.FileDetails;
import com.conduent.tpms.iag.model.TagDeviceDetails;
import com.conduent.tpms.iag.model.XferControl;
import com.conduent.tpms.iag.model.ZipCode;

public interface TQatpMappingDao {

	public FileParserParameters getMappingConfigurationDetails( String fileType);
	public FileDetails checkIfFileProcessedAlready(String fileName) throws FileAlreadyProcessedException;
	public Long insertFileDetails(FileDetails fileDetails);
	public XferControl checkFileDownloaded(String name);
	public String getLastSuccesfulProcessedFile(String fromAgencyId2);
	public void updateFileIntoCheckpoint(FileDetails fileDetails);
	public void insertAckDetails(AckFileInfoDto ackFileInfoDto);
	public int[] insertInvalidCustomerRecord(List<CustomerAddressRecord> customerAddressRecordList);
	public List<CustomerAddressRecord> checkifrecordexists();
	public void updateInvalidAddressIdinDevices(String device_no, int address_id);
	public TagDeviceDetails checkRecordExistInDevice(String device_no);
	public void insertaddressinaddresstable(int address_id);
	public List<String> getZipCodeList();
	public CustomerAddressRecord checkifrecordexists(CustomerAddressRecord record);
}
