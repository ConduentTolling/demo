package com.conduent.tpms.ictx.service.impl;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.ictx.model.Agency;
import com.conduent.tpms.ictx.model.ConfigVariable;
import com.conduent.tpms.ictx.service.IctxSchedulerService;
import com.conduent.tpms.ictx.utility.StaticDataLoad;

/**
 * ICTX scheduler service implementation
 * 
 * @author deepeshb
 *
 */
@Service
public class IctxSchedulerServiceImpl implements IctxSchedulerService {

	private static final Logger logger = LoggerFactory.getLogger(IctxSchedulerServiceImpl.class);

	@Autowired
	StaticDataLoad staticDataLoad;

	@Autowired
	ConfigVariable configVariable;

	@Autowired
	RestTemplate restTemplate;

	/**
	 * To start the processing of transaction message
	 */
	@Override
	public void process(String fileType) {
		// Get awayAgencyList from T_AGENCY
		List<Agency> tempAwayAgencyList = staticDataLoad.getAwayAgency();

		if (!tempAwayAgencyList.isEmpty()) {
			processCall(tempAwayAgencyList, fileType);
		}

	}
	
	/**
	 * To start the processing of transaction message
	 */
	@Override
	public void processHubFile(String fileType) {
		
		String urlValue = "";
		if(fileType.equalsIgnoreCase("ICTX")) {
			urlValue = configVariable.getIctxServiceUrl();
		}else {
			urlValue = configVariable.getItxcServiceUrl();
		}

		String url = ("".concat(urlValue).concat("HUBFILE").concat("/").toString());
		logger.info("{} File Creation request url {}", fileType, url);
		String response = restTemplate.exchange(url, HttpMethod.GET, getHTTPEntity(), String.class).getBody();
		logger.info("Ictx File Creation Response for HUBFILE: {}", response);

	}
	



	@Async
	private void processCall(List<Agency> tempAwayAgencyList, String fileType) {
		
		String urlValue = "";
		if(fileType.equalsIgnoreCase("ICTX")) {
			urlValue = configVariable.getIctxServiceUrl();
		}else {
			urlValue = configVariable.getItxcServiceUrl();
		}
		
	for (Agency agency : tempAwayAgencyList) {
		String url = ("".concat(urlValue).concat(agency.getAgencyId().toString()).concat("/").toString());
		logger.info("{} File Creation request url {}", fileType, url);
		String response = restTemplate.exchange(url, HttpMethod.GET, getHTTPEntity(), String.class).getBody();
		logger.info("Ictx File Creation Response for Away Agency {}: {}", agency.getAgencyId(), response);

		}
	}
	
	private HttpEntity<String> getHTTPEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		return entity;
		
	}
}
