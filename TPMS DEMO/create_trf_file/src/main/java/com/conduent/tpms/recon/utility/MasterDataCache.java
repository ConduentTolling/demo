package com.conduent.tpms.recon.utility;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.recon.dao.AgencyDao;
import com.conduent.tpms.recon.model.Agency;

/**
 * 
 * @author taniyan
 *
 */
@Component
public class MasterDataCache {

	private static final Logger log = LoggerFactory.getLogger(MasterDataCache.class);

	private List<Agency> agencyList;
	
	private List<String> devicePrefixList;
	
	@Autowired
	private AgencyDao agencyDao;
	
	
	/**
	 * Get Agency Info by agency id
	 * 
	 * @param devicePrefix
	 * @return Agency
	 */
	public Agency getAgencyById(Long agencyId) {
		Optional<Agency> agency = agencyList.stream().filter(e -> agencyId.equals(e.getAgencyId())).findFirst();
		if (agency.isPresent()) {
			return agency.get();
		}
		return null;
	}
	
	/**
	 * Get device prefix Info by agency id
	 * 
	 * @param devicePrefix
	 * @return Agency
	 */
	public boolean validateHomeDevice(String devicePrefix) {
		boolean isHome = false;
		Optional<String> homeDevicePrefix = devicePrefixList.stream().filter(e -> devicePrefix.equals(e)).findFirst();
		if(homeDevicePrefix.isPresent()) {
			isHome = true;
		}
		return isHome;
	}

	/**
	 * Initializes when the container starts
	 */
	@PostConstruct
	public void init() {
		try {
			//agencyList = agencyDao.getAgency();
			//log.info("Master data loaded for T_Agency table ..{}",agencyList.toString());
			//devicePrefixList = agencyDao.getHomeAgencyList();
			log.info("Master data loaded ...");
		} catch (Exception ex) {
			log.error("Failed to load initial master date: {}", ex.getMessage());

		}

	}

	
}
