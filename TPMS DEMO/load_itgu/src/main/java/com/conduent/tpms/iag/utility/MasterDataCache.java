
package com.conduent.tpms.iag.utility;

import java.io.File;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.iag.dao.TAgencyDao;
import com.conduent.tpms.iag.model.AgencyInfoVO;

@Component
public class MasterDataCache {
	@Autowired
	private TAgencyDao tAgencyDao;

	List<AgencyInfoVO> agencyInfoVOList;
	
	List<AgencyInfoVO> agencyInfoVOListHome;

	protected List<File> missingFilesList;

	public boolean isValidAgency(Integer agencyId) {
		if (agencyId == null) {
			return false;
		}
		Optional<AgencyInfoVO> agencyInfoVO = agencyInfoVOList.stream().filter(e -> e.getAgencyId() == agencyId)
				.findFirst();
		if (agencyInfoVO.isPresent()) {
			agencyInfoVO.get().setFileProcessingStatus("N");
			return true;
		}
		return false;
	}

	public List<AgencyInfoVO> getagencyInfoVOListFromMasterCache() {
		return agencyInfoVOList;
	}
	
	public List<AgencyInfoVO> getAgencyInfoVOListHome() {
		return agencyInfoVOListHome;
	}

	public void setAgencyInfoVOListHome(List<AgencyInfoVO> agencyInfoVOListHome) {
		this.agencyInfoVOListHome = agencyInfoVOListHome;
	}

	public Integer getXtraAxlesFromVehicleClassStatics(String etcIndVehClass) {
		return null;
	}

	public Long getAVCAxlesFromVehicleAxleStatics(String etcIndVehClass) {
		return null;
	}

	public boolean isHomeAgency(String agencyId) {
		return true;
	}
	
	@PostConstruct
	public void init() {
		try {
			agencyInfoVOListHome = tAgencyDao.getAgencyInfoListforHome();
			agencyInfoVOList = tAgencyDao.getAgencyInfoListforAway();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	
}
