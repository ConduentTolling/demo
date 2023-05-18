package com.conduent.tpms.inrx.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.conduent.tpms.inrx.dto.FileNameDetailVO;
import com.conduent.tpms.inrx.dto.INRXHeaderInfoVO;
import com.conduent.tpms.inrx.dto.INRXDetailInfoVO;
import com.conduent.tpms.inrx.dto.MappingInfoDto;
import com.conduent.tpms.inrx.utility.GenericValidation;

public class ValidationHelper {
	@Autowired
	GenericValidation genericValidation;
	
	public void parseAndValidate(List<MappingInfoDto> mappingInfo,String fileName,
			FileNameDetailVO fileNameDTO, INRXHeaderInfoVO header) {
		String value = null;
		boolean isValid = true;
		INRXDetailInfoVO custAddressDetail = new INRXDetailInfoVO();
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
