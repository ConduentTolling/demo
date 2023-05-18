package com.conduent.tpms.parking.dao;


import com.conduent.tpms.parking.dto.FileInfoDto;

public interface TQatpMappingDao {

	public FileInfoDto getMappingConfigurationDetails( String fileType);

}
