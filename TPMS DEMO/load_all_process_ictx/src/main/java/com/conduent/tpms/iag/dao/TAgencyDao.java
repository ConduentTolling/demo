package com.conduent.tpms.iag.dao;

import java.util.List;

import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.FileInfoDto;
import com.conduent.tpms.iag.dto.FileParserParameters;
import com.conduent.tpms.iag.model.AgencyInfoVO;
import com.conduent.tpms.iag.model.XferControl;
import com.conduent.tpms.iag.model.ZipCode;

public interface TAgencyDao {
	public FileInfoDto getMappingConfigurationDetails(String fileType);
	public void insertAckDetails(AckFileInfoDto ackFileInfoDto);
	public XferControl checkFileDownloaded(String fileName);
	public List<AgencyInfoVO> getAgencyInfoList();
}
