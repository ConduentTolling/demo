package com.conduent.tpms.iag.utility;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.iag.dao.TAgencyDao;
import com.conduent.tpms.iag.dto.AgencyInfoVO;

@Component
public class MasterDataCache {


	@Autowired
	private TAgencyDao agencyDao;
	
	private List<AgencyInfoVO> agencyInfoVOList;
	public AgencyInfoVO getAgencyId(String agencyId) {
		if (agencyId == null || agencyInfoVOList == null) {
			return null;
		}
		Optional<AgencyInfoVO> agencyDto = agencyInfoVOList.stream().filter(e -> e.getFilePrefix().equals(agencyId))
				.findFirst();
		if (agencyDto.isPresent()) {
			return agencyDto.get();
		}
		return null;
	}	
	
	@PostConstruct
	public void init() {
		try {
			
			agencyInfoVOList = agencyDao.getAgencyInfo();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
