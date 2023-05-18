package com.conduent.tpms.icrx.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.icrx.constants.Constants;
import com.conduent.tpms.icrx.dao.AgencyDao;
import com.conduent.tpms.icrx.model.Agency;

/**
 * Load Data on startup
 * 
 * @author urvashic
 *
 */
@Component
public class StaticDataLoad {

	private static final Logger logger = LoggerFactory.getLogger(StaticDataLoad.class);

	@Autowired
	private AgencyDao agencyDao;

	private List<Agency> agencyList;
	
	Map<String, String> planCodeMap = new HashMap<>();
	
	

	/**
	 * Get Agency Info By device prefix
	 * 
	 * @param devicePrefix
	 * @return Agency
	 */
	public Agency getAgency(String devicePrefix) {
		Optional<Agency> agency = agencyList.stream().filter(e -> devicePrefix.equalsIgnoreCase(e.getDevicePrefix()))
				.findFirst();
		if (agency.isPresent()) {
			return agency.get();
		}
		return null;
	}

	/**
	 * Get Away Agency Info
	 * 
	 * @param devicePrefix
	 * @return Agency
	 */
	public List<Agency> getAwayAgency() {
		Map<String, List<Agency>> awayAgencyMap = agencyList.stream()
				.collect(Collectors.groupingBy(Agency::getIsHomeAgency));
		return awayAgencyMap.getOrDefault(Constants.N, new ArrayList<Agency>());
	}

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
		}else {
		return null;
		}
	}
	
	private void populatePlanMap() {
		planCodeMap.put("00002", "PASI");
		planCodeMap.put("00003", "PAAB");
		planCodeMap.put("00004", "PACP");
		planCodeMap.put("00005", "PANRPA");
		planCodeMap.put("00006", "NYSBA");
		planCodeMap.put("00007", "NY12");
		planCodeMap.put("00008", "TZC");
		planCodeMap.put("00009", "TZPL");
		planCodeMap.put("00010", "RR");
		planCodeMap.put("00011", "SIR");
		// planCodeMap.put("00012", ""); // FOR NJ IGNORE HERE
		// planCodeMap.put("00013", ""); // FOR NJ IGNORE HERE
		// planCodeMap.put("00014", ""); // FOR NJ IGNORE HERE
		// planCodeMap.put("00023", ""); // FOR FL IGNORE HERE
	}

	public String getPlanCode(String planTypeId) {
		String planCode="     ";
		for (Entry<String, String> entry : planCodeMap.entrySet()) {
            if (entry.getValue().equals(planTypeId)) {
            	planCode = entry.getKey();
            }
        }
	return planCode;
	}
	
	@PostConstruct
	public void init() {
		try {
			populatePlanMap();
			logger.info("Plan Map initialized successfully");
			logger.info("Loading initial master data..");
			agencyList = agencyDao.getAgency();
			logger.info("Loaded agencyList data successfully");
		} catch (Exception ex) {
			logger.error("Failed to load initial master data: {}", ExceptionUtils.getStackTrace(ex));
		}
	}

}
