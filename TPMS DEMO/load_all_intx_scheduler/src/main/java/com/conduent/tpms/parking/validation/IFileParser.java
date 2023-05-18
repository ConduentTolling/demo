package com.conduent.tpms.parking.validation;

import java.util.List;

import com.conduent.tpms.parking.dto.FileNameDetailVO;
import com.conduent.tpms.parking.dto.FileParserParameters;
import com.conduent.tpms.parking.dto.IITCDetailInfoVO;
import com.conduent.tpms.parking.dto.IITCHeaderInfoVO;
import com.conduent.tpms.parking.dto.MappingInfoDto;

public interface IFileParser {

	public void initialize();
	public Boolean validateFileName(List<MappingInfoDto> mappingInfo,String fileName,
			FileNameDetailVO fileNameDTO, IITCHeaderInfoVO header);
	public Boolean validateHeader(List<MappingInfoDto> mappingInfo,String fileName,
			FileNameDetailVO fileNameDTO, IITCHeaderInfoVO header);
	public Boolean validateFooter(List<MappingInfoDto> mappingInfo,String fileName,
			FileNameDetailVO fileNameDTO, IITCHeaderInfoVO header);
	public Boolean validateDetails(List<MappingInfoDto> mappingInfo,String fileName,
			FileNameDetailVO fileNameDTO, IITCHeaderInfoVO header);
	public void loadConfigurationMapping();
	
	public void doFileFieldMapping(String value, MappingInfoDto fMapping, FileNameDetailVO fileNameDTO,
			IITCHeaderInfoVO header, IITCDetailInfoVO custAddressDetail);
}
