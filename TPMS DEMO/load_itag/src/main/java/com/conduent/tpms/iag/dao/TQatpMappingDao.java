package com.conduent.tpms.iag.dao;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.FileParserParameters;
import com.conduent.tpms.iag.dto.ITAGDetailInfoVO;
import com.conduent.tpms.iag.model.AgencyInfoVO;
import com.conduent.tpms.iag.model.FileDetails;
import com.conduent.tpms.iag.model.TagDeviceDetails;
import com.conduent.tpms.iag.model.XferControl;

public interface TQatpMappingDao {

	public FileParserParameters getMappingConfigurationDetails(String fileType);
	public Long getAgencyId(String  filePrefix);
	public FileDetails checkIfFileProcessedAlready(String fileName);
	public Long insertFileDetails(FileDetails fileDetails);
	public Long getLastProcessedRecordCount(Long agencyId);
	public XferControl checkFileDownloaded(String name);
	public String getLastSuccesfulProcessedFile(String fromAgencyId2);
	public void updateFileIntoCheckpoint(FileDetails fileDetails);
	public void insertAckDetails(AckFileInfoDto ackFileInfoDto);
	public List<AgencyInfoVO> getAgencyDetails();
	//public List<ITAGDetailInfoVO> getDiffRecordsFromExtTables();
	public void updateTagDeviceDetails(TagDeviceDetails tagDeviceDetails);
	public void updateTagDeviceDetailsforHome(TagDeviceDetails tagDeviceDetails);
	public List<String> getDataFromNewExternTable(String newBucket);
	public List<String> getDataFromOldExternTable(String oldBucket);
	public LocalDate getIfDeviceExistForFuture(String deviceNo);
	public LocalDate getIfDeviceExistForFutureforHome(String deviceNo);
	public void updateDuplicateTagDeviceDetails(TagDeviceDetails tagDeviceDetails, LocalDate prevEndDate);
	public void updateDuplicateTagDeviceDetailsforHome(TagDeviceDetails tagDeviceDetails, LocalDate prevEndDate);
	public int[] insertTagDeviceDetailsforAway(List<TagDeviceDetails> tagDeviceDetailsList);
	public int[] insertTagDeviceDetailsforHome(List<TagDeviceDetails> tagDeviceDetailsList);
	public List<ITAGDetailInfoVO> getDiffRecordsFromExtTables_ITAG(String newAwaytagBucket, String oldAwaytagBucket);
	public List<String> getDataFromExternTable_ITAG(String externalTableName);
	public int getThresholdValue(String paramname);
	public String getAgencyid(String fileprefix);
	public void updateDuplicateTagDeviceDateForOld(TagDeviceDetails tagDeviceDetails, LocalDate prevEndDate);
    public String getAgencyidHome(String fileprefix);
}
