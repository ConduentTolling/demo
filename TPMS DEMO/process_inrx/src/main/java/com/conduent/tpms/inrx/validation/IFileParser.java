package com.conduent.tpms.inrx.validation;

import java.util.List;

import com.conduent.tpms.inrx.dto.FileParserParameters;
import com.conduent.tpms.inrx.dto.FileNameDetailVO;
import com.conduent.tpms.inrx.dto.INRXHeaderInfoVO;
import com.conduent.tpms.inrx.dto.INRXDetailInfoVO;
import com.conduent.tpms.inrx.dto.MappingInfoDto;

public interface IFileParser {

	public void initialize();
	public Boolean validateFileName(List<MappingInfoDto> mappingInfo,String fileName,
			FileNameDetailVO fileNameDTO, INRXHeaderInfoVO header);
	public Boolean validateHeader(List<MappingInfoDto> mappingInfo,String fileName,
			FileNameDetailVO fileNameDTO, INRXHeaderInfoVO header);
	public Boolean validateFooter(List<MappingInfoDto> mappingInfo,String fileName,
			FileNameDetailVO fileNameDTO, INRXHeaderInfoVO header);
	public Boolean validateDetails(List<MappingInfoDto> mappingInfo,String fileName,
			FileNameDetailVO fileNameDTO, INRXHeaderInfoVO header);
	public void loadConfigurationMapping();
	
	public void doFileFieldMapping(String value, MappingInfoDto fMapping, FileNameDetailVO fileNameDTO,
			INRXHeaderInfoVO header, INRXDetailInfoVO custAddressDetail);
}
