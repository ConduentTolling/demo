package com.conduent.tpms.qatp.validation;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.conduent.tpms.qatp.dto.FileNameDetailVO;
import com.conduent.tpms.qatp.dto.NystaDetailInfoVO;
import com.conduent.tpms.qatp.dto.NystaHeaderInfoVO;
import com.conduent.tpms.qatp.dto.MappingInfoDto;
import com.conduent.tpms.qatp.utility.GenericValidation;

public class ValidationHelper
{
	@Autowired
	GenericValidation genericValidation;
	
	public void parseAndValidate(List<MappingInfoDto> mappingInfo,String fileName,
			FileNameDetailVO fileNameDTO, NystaHeaderInfoVO header) {
		String value = null;
		boolean isValid = true;
		NystaDetailInfoVO itagDetail = new NystaDetailInfoVO();
		for (MappingInfoDto fMapping : mappingInfo) {
			if (isValid) {

				value = fileName.substring(fMapping.getStartPosition().intValue(),
						fMapping.getEndPosition().intValue());
				isValid = genericValidation.doValidation(fMapping, value);
				
			}
		}
	}
	
	public void parseAndValidate(String line) {
		
	}
}