package com.conduent.tpms.iag.dao;

import java.time.LocalDate;
import java.util.List;

import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.FileParserParameters;
import com.conduent.tpms.iag.dto.ICLPDetailInfoVO;
import com.conduent.tpms.iag.model.AgencyInfoVO;
import com.conduent.tpms.iag.model.FileDetails;
import com.conduent.tpms.iag.model.LicPlateDetails;
import com.conduent.tpms.iag.model.XferControl;

public interface TQatpMappingDao {

	public FileParserParameters getMappingConfigurationDetails( String fileType);
	public XferControl checkFileDownloaded(String name);
	public FileDetails checkIfFileProcessedAlready(String fileName);
	public String getLastSuccesfulProcessedFile(String fromAgencyId2);
	public Long insertFileDetails(FileDetails fileDetails);
	public void updateFileIntoCheckpoint(FileDetails fileDetails);
	public void insertAckDetails(AckFileInfoDto ackFileInfoDto);
	public List<AgencyInfoVO> getAgencyDetails();
	public int[] insertPlateDetails(List<LicPlateDetails> licPlateDetailsList);
	//public List<ICLPDetailInfoVO> getDiffRecordsFromExtTables();
	public List<ICLPDetailInfoVO> getDiffRecordsFromExtTables_ICLP(String newAwaytagBucket, String oldAwaytagBucket);
	public List<ICLPDetailInfoVO> getDataFromNewExternTable(String newBucket);
	public List<ICLPDetailInfoVO> getDataFromOldExternTable(String oldBucket);
	//public List<ICLPDetailInfoVO> getDataFromExternTable(String externalTableName);
	public void updateLicPlateDetails(LicPlateDetails licPlateDetails);
	public LocalDate getIfDeviceExistForFuture(ICLPDetailInfoVO iclpDetailInfoVO);
	public void updateDuplicateLicPlateDetails(LicPlateDetails licPlateDetails, LocalDate endDate);
	public List<ICLPDetailInfoVO> getDataFromExternTable1(String newAwaytagBucket);
	public int getThresholdValue(String paramname);
	public List<String> getLicState();
	public String getAgencyid(String homeAgencyID);
    public void updateDuplicateLicPlateDetailsForOld(LicPlateDetails licPlateDetails, LocalDate endDate);
	
}
