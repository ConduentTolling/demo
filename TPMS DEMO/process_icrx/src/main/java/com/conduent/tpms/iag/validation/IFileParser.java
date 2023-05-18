package com.conduent.tpms.iag.validation;

import java.util.List;

import com.conduent.tpms.iag.dto.FileParserParameters;
import com.conduent.tpms.iag.dto.FileNameDetailVO;
import com.conduent.tpms.iag.dto.ICRXDetailInfoVO;
import com.conduent.tpms.iag.dto.ICRXHeaderInfoVO;
import com.conduent.tpms.iag.dto.MappingInfoDto;

public interface IFileParser {

	public void initialize();
	public Boolean validateFileName(List<MappingInfoDto> mappingInfo,String fileName,
			FileNameDetailVO fileNameDTO, ICRXHeaderInfoVO header);
	public Boolean validateHeader(List<MappingInfoDto> mappingInfo,String fileName,
			FileNameDetailVO fileNameDTO, ICRXHeaderInfoVO header);
	public Boolean validateFooter(List<MappingInfoDto> mappingInfo,String fileName,
			FileNameDetailVO fileNameDTO, ICRXHeaderInfoVO header);
	public Boolean validateDetails(List<MappingInfoDto> mappingInfo,String fileName,
			FileNameDetailVO fileNameDTO, ICRXHeaderInfoVO header);
	public void loadConfigurationMapping();
	
	public void doFileFieldMapping(String value, MappingInfoDto fMapping, FileNameDetailVO fileNameDTO,
			ICRXHeaderInfoVO header, ICRXDetailInfoVO custAddressDetail);
}
