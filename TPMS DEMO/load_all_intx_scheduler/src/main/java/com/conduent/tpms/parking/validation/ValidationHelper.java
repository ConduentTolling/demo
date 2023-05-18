package com.conduent.tpms.parking.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.conduent.tpms.parking.dto.FileNameDetailVO;
import com.conduent.tpms.parking.dto.IITCDetailInfoVO;
import com.conduent.tpms.parking.dto.IITCHeaderInfoVO;
import com.conduent.tpms.parking.dto.MappingInfoDto;
import com.conduent.tpms.parking.utility.GenericValidation;

public class ValidationHelper {
	@Autowired
	GenericValidation genericValidation;
	
	public void parseAndValidate(List<MappingInfoDto> mappingInfo,String fileName,
			FileNameDetailVO fileNameDTO, IITCHeaderInfoVO header) {
		String value = null;
		boolean isValid = true;
		IITCDetailInfoVO custAddressDetail = new IITCDetailInfoVO();
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

