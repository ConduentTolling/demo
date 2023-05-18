package com.conduent.tpms.parking.dao;

import java.util.List;

import com.conduent.tpms.parking.dto.AckFileInfoDto;
import com.conduent.tpms.parking.dto.FileInfoDto;
import com.conduent.tpms.parking.dto.FileParserParameters;
import com.conduent.tpms.parking.model.AgencyInfoVO;
import com.conduent.tpms.parking.model.XferControl;
import com.conduent.tpms.parking.model.ZipCode;

public interface TAgencyDao {
	public FileInfoDto getMappingConfigurationDetails(String fileType);
	public void insertAckDetails(AckFileInfoDto ackFileInfoDto);
	public XferControl checkFileDownloaded(String fileName);
	public List<String> getAllAwayAgencies(String fileType);
}
