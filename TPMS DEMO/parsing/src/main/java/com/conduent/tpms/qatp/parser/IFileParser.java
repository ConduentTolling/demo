package com.conduent.tpms.qatp.parser;

import java.util.List;

import com.conduent.tpms.qatp.dto.FileParserParameters;
import com.conduent.tpms.qatp.dto.FOTrnxDetailInfoVo;
import com.conduent.tpms.qatp.dto.FileNameDetailVO;
import com.conduent.tpms.qatp.dto.NystaDetailInfoVO;
import com.conduent.tpms.qatp.dto.NystaHeaderInfoVO;
import com.conduent.tpms.qatp.dto.MappingInfoDto;

public interface IFileParser {

	public void initialize();
	public Boolean validateFileName(List<MappingInfoDto> mappingInfo,String fileName,
			FileNameDetailVO fileNameDTO, NystaHeaderInfoVO header);
	public Boolean validateHeader(List<MappingInfoDto> mappingInfo,String fileName,
			FileNameDetailVO fileNameDTO, NystaHeaderInfoVO header);
	public Boolean validateFooter(List<MappingInfoDto> mappingInfo,String fileName,
			FileNameDetailVO fileNameDTO, NystaHeaderInfoVO header);
	public Boolean validateDetails(List<MappingInfoDto> mappingInfo,String fileName,
			FileNameDetailVO fileNameDTO, NystaHeaderInfoVO header);
	public void loadConfigurationMapping();
	
	public void doFileFieldMapping(String value, MappingInfoDto fMapping, FileNameDetailVO fileNameDTO,
			NystaHeaderInfoVO header, FOTrnxDetailInfoVo itagDetail);
}
