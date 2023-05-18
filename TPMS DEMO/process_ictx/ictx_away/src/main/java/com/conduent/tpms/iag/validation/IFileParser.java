package com.conduent.tpms.iag.validation;

import java.util.List;

import com.conduent.tpms.iag.dto.FileParserParameters;
import com.conduent.tpms.iag.dto.IctxItxcNameVO;
import com.conduent.tpms.iag.dto.IctxItxcDetailInfoVO;
import com.conduent.tpms.iag.dto.IctxItxcHeaderInfoVO;
import com.conduent.tpms.iag.dto.MappingInfoDto;

public interface IFileParser {

	public void initialize();
	public Boolean validateFileName(List<MappingInfoDto> mappingInfo,String fileName,
			IctxItxcNameVO fileNameDTO, IctxItxcHeaderInfoVO header);
	public Boolean validateHeader(List<MappingInfoDto> mappingInfo,String fileName,
			IctxItxcNameVO fileNameDTO, IctxItxcHeaderInfoVO header);
	public Boolean validateFooter(List<MappingInfoDto> mappingInfo,String fileName,
			IctxItxcNameVO fileNameDTO, IctxItxcHeaderInfoVO header);
	public Boolean validateDetails(List<MappingInfoDto> mappingInfo,String fileName,
			IctxItxcNameVO fileNameDTO, IctxItxcHeaderInfoVO header);
	public void loadConfigurationMapping();
	
	public void doFileFieldMapping(String value, MappingInfoDto fMapping, IctxItxcNameVO fileNameDTO,
			IctxItxcHeaderInfoVO header, IctxItxcDetailInfoVO custAddressDetail);
}
