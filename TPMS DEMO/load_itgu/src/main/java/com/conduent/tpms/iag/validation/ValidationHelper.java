package com.conduent.tpms.iag.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.conduent.tpms.iag.dto.FileNameDetailVO;
import com.conduent.tpms.iag.dto.ITGUDetailInfoVO;
import com.conduent.tpms.iag.dto.ITGUHeaderInfoVO;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.utility.GenericValidation;

public class ValidationHelper {
	@Autowired
	GenericValidation genericValidation;
	
	public void parseAndValidate(List<MappingInfoDto> mappingInfo,String fileName,
			FileNameDetailVO fileNameDTO, ITGUHeaderInfoVO header) {
		String value = null;
		boolean isValid = true;
		ITGUDetailInfoVO itagDetail = new ITGUDetailInfoVO();
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

