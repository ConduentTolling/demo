package com.conduent.tpms.iag.utility;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.iag.dao.AgencyDao;
import com.conduent.tpms.iag.model.Agency;

/**
 * 
 * @author urvashic
 *
 */
@Component
public class MasterDataCache {

	private static final Logger log = LoggerFactory.getLogger(MasterDataCache.class);

	private List<Agency> agencyList;
	
	private List<String> devicePrefixList;
	
	private Agency homeAgency;
	
	private List<Agency> awayAgencyList;
	
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
	
	
	public Agency getHomeAgency() {
		if (homeAgency != null) {
			return homeAgency;
		}else {
			log.info("Home agency is null");
			return null;
		}
	}
	
	/**
	 * 
	 * @return List<Agency>
	 */
	public List<Agency> getAwayAgencyList() {
		if (awayAgencyList != null || !awayAgencyList.isEmpty()) {
			return awayAgencyList;
		}else {
			log.info("Away agency list is null");
			return null;
		}
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
			agencyList = agencyDao.getAgency();
			log.info("Master data loaded for T_Agency table ..{}",agencyList.toString());
			
			devicePrefixList = agencyDao.getHomeAgencyDevicePrefixList();
			log.info("Master data loaded for Device Prefix list");
			
			homeAgency = agencyDao.getHomeAgency();
			log.info("Master data loaded for Home _Agency table ..{}",homeAgency.toString());
			
			awayAgencyList = agencyDao.getAwayAgencyList();
			log.info("Master data loaded for Away Agency table ..{}",awayAgencyList.toString());
		} catch (Exception ex) {
			log.error("Failed to load initial master date: {}", ex.getMessage());

		}

	}

	
}
