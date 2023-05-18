package com.conduent.tpms.iag.dao;


import java.util.List;

import com.conduent.tpms.iag.constants.FileProcessStatus;
import com.conduent.tpms.iag.dto.AccountTollPostDTO;
import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.AgencyTxPendingDto;
import com.conduent.tpms.iag.dto.AwayAgencyDto;
import com.conduent.tpms.iag.dto.FileParserParameters;
import com.conduent.tpms.iag.exception.TransactionFileDataMismatch;
import com.conduent.tpms.iag.model.AgencyInfoVO;
import com.conduent.tpms.iag.model.FileDetails;
import com.conduent.tpms.iag.model.QatpStatistics;
import com.conduent.tpms.iag.model.TagDeviceDetails;
import com.conduent.tpms.iag.model.TranDetail;
import com.conduent.tpms.iag.model.XferControl;

public interface TQatpMappingDao {

	public FileParserParameters getMappingConfigurationDetails( String fileType);
	public FileParserParameters getIRXCMappingConfigurationDetails( FileParserParameters fielDto);
	public FileDetails checkIfFileProcessedAlready(String fileName);
	public AgencyTxPendingDto getlaneTxIdFromAgencyTxPending(long laneTxId);
	public Long insertFileDetails(FileDetails fileDetails);
	public XferControl checkFileDownloaded(String name);
	public String getLastSuccesfulProcessedFile(String fromAgencyId2);
	public void updateFileIntoCheckpoint(FileDetails fileDetails);
	public void insertAckDetails(AckFileInfoDto ackFileInfoDto);
	public void updateInvalidAddressIdinDevices(String device_no, int address_id);
	public TagDeviceDetails checkRecordExistInDevice(String device_no);
	public void insertaddressinaddresstable(int address_id);
	public void updateaddressidindevicetable(int address_id);
	public List<String> getZipCodeList();	
	public void insertQatpStatistics(QatpStatistics qatpStatistics);
	List<AgencyInfoVO> getAgencyDetails();
	public void updateTranDetail(TranDetail tranDetail);
	public void updateTranDetailPostedAmt(TranDetail tranDetail);
	public void updateTranDetailWithFields(TranDetail tranDetail, AccountTollPostDTO toll);
	public void insertAwayAgency(AwayAgencyDto awayAgencyDto);
	public void updateAwayAgency(AwayAgencyDto awayAgencyDto);
	public void deleteAgencyTxPending(long laneTxId);
	public String getAccountAgencyId(long etcAccountIdParsing);
	public void updateICTXFileDetails(String ictxFileNum, FileDetails fileDetails) throws TransactionFileDataMismatch;
	public Long getATPFileId(String ictxFileNum);
	public String getICTXFileName(String ictxFileNum);
	public int getICTXRecordCountId(String ictxFileNum);
	public void updateFileIntoCheckpointLaneTxId(FileDetails fileDetails);
	public Boolean checkIfFileProcessedAlreadyBy(String value, FileProcessStatus complete);
}
