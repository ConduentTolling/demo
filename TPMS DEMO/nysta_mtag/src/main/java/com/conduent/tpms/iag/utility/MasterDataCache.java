package com.conduent.tpms.iag.utility;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.iag.dao.AgencyDao;
import com.conduent.tpms.iag.dao.TCodeDao;
import com.conduent.tpms.iag.model.Agency;
import com.conduent.tpms.iag.model.TCode;

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
	
	private List<Agency> awayAgencyList;
	
	private List<TCode> codeStatus;
	
	private Agency homeAgency;
	
	@Autowired
	private AgencyDao agencyDao;
	
	@Autowired
	private TCodeDao tCodeDao;
	
	
	
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
	 * Get Agency Info by agency id
	 * 
	 * @param devicePrefix
	 * @return Agency
	 */
	public TCode getCodeByDisplayVal(String displayVal) {
		Optional<TCode> code = codeStatus.stream().filter(e -> displayVal.equals(e.getDisplayValue())).findFirst();
		if (code.isPresent()) {
			return code.get();
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
	 * Initializes when the container starts
	 */
	@PostConstruct
	public void init() {
		try {
		/*	agencyList = agencyDao.getAgency();
			log.info("Master data loaded for T_Agency table ..{}",agencyList.toString());
			devicePrefixList = agencyDao.getHomeAgencyList();
			log.info("Master data loaded for Device Prefix list");
			*/
			agencyList = agencyDao.getAgency();
			log.info("Master data loaded for T_Agency table ..{}",agencyList.toString());
			
			devicePrefixList = agencyDao.getHomeAgencyDevicePrefixList();
			log.info("Master data loaded for Device Prefix list");
			
			homeAgency = agencyDao.getHomeAgency();
			log.info("Master data loaded for Home _Agency table ..{}",homeAgency.toString());
			
			awayAgencyList = agencyDao.getAwayAgencyList();
			log.info("Master data loaded for Away Agency table ..{}",awayAgencyList.toString());
			
			codeStatus = tCodeDao.getFinStatus();
			log.info("Master data loaded for t_codes table ..{}",codeStatus.toString());
			
		} catch (Exception ex) {
			log.error("Failed to load initial master date: {}", ex.getMessage());

		}

	}

	
}
