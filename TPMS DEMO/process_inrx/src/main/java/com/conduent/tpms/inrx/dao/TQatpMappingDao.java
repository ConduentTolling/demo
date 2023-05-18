package com.conduent.tpms.inrx.dao;


import java.util.List;

import com.conduent.tpms.inrx.dto.AckFileInfoDto;
import com.conduent.tpms.inrx.dto.AgencyTxPendingDto;
import com.conduent.tpms.inrx.dto.AwayAgencyDto;
import com.conduent.tpms.inrx.dto.FileParserParameters;
import com.conduent.tpms.inrx.model.AgencyInfoVO;
import com.conduent.tpms.inrx.model.FileDetails;
import com.conduent.tpms.inrx.model.QatpStatistics;
import com.conduent.tpms.inrx.model.TagDeviceDetails;
import com.conduent.tpms.inrx.model.TranDetail;
import com.conduent.tpms.inrx.model.XferControl;

public interface TQatpMappingDao {

	public FileParserParameters getMappingConfigurationDetails( String fileType);
	public FileParserParameters getIRXNMappingConfigurationDetails( FileParserParameters fielDto);
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
	public void insertAwayAgency(AwayAgencyDto awayAgencyDto);
	public void updateAwayAgency(AwayAgencyDto awayAgencyDto);
	public void deleteAgencyTxPending(long laneTxId);
	public String getAccountAgencyId(long etcAccountIdParsing);
	public void updateINTXFileDetails(String intxFileNum, FileDetails fileDetails);
	public Long getATPFileId(String intxFileNum);
}
