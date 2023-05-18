package com.conduent.tpms.iag.dao;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.FileParserParameters;
import com.conduent.tpms.iag.dto.ITGUDetailInfoVO;
import com.conduent.tpms.iag.dto.InterAgencyFileXferDto;
import com.conduent.tpms.iag.model.AgencyInfoVO;
import com.conduent.tpms.iag.model.FileDetails;
import com.conduent.tpms.iag.model.TagDeviceDetails;
import com.conduent.tpms.iag.model.XferControl;

public interface TQatpMappingDao {

	public FileParserParameters getMappingConfigurationDetails(String fileType);
	public String getAgencyId(String  filePrefix);
	public FileDetails checkIfFileProcessedAlready(String fileName);
	public Long insertFileDetails(FileDetails fileDetails);
	public Long getLastProcessedRecordCount(Long agencyId);
	public XferControl checkFileDownloaded(String name);
	public void updateFileIntoCheckpoint(FileDetails fileDetails);
	public void insertAckDetails(AckFileInfoDto ackFileInfoDto);
	public List<AgencyInfoVO> getAgencyDetails();
	public void updateTagDeviceDetails(TagDeviceDetails tagDeviceDetails);
	public int[] insertTagDeviceDetailsforAway(List<TagDeviceDetails> tagDeviceDetailsList);
	public TagDeviceDetails checkDeviceInfoInToaDeviceTable(String deviceNo);
	public int updateToaDeviceTable(ITGUDetailInfoVO deviceNo, Long xferControlId);
	public InterAgencyFileXferDto getFullFileDetails();
	public LocalDate getenddate(String deviceNo);
	public int updateenddate(TagDeviceDetails itguDetail,LocalDate prevEndDate);
}
