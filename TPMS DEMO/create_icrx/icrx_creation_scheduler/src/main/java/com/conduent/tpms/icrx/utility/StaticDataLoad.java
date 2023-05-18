package com.conduent.tpms.icrx.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.icrx.dao.AgencyDao;
import com.conduent.tpms.icrx.model.Agency;

/**
 * Load Data on startup
 * 
 * @author deepeshb
 *
 */
@Component
public class StaticDataLoad {

	private static final Logger logger = LoggerFactory.getLogger(StaticDataLoad.class);

	@Autowired
	private AgencyDao agencyDao;

	private List<Agency> agencyList;

	/**
	 * Get Agency Info
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
		return awayAgencyMap.getOrDefault("N", new ArrayList<Agency>());

	}

	/**
	 * Get Agency Info
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

	@PostConstruct
	public void init() {
		try {
			logger.info("Loading initial master data..");
			agencyList = agencyDao.getAgency();
			logger.info("Loaded agencyList data successfully");
		} catch (Exception ex) {
			logger.error("Failed to load initial master data: {}", ex.getMessage());

		}

	}
}
