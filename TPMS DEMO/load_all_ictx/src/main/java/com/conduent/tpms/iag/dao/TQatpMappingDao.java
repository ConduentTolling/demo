package com.conduent.tpms.iag.dao;


import com.conduent.tpms.iag.dto.FileInfoDto;

public interface TQatpMappingDao {

	public FileInfoDto getMappingConfigurationDetails( String fileType);

}
