package com.conduent.tpms.icrx.thread;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.icrx.model.ConfigVariable;

/**
 * ICRX thread to create request for each away agency
 * 
 * @author deepeshb
 *
 */

public class IcrxFileRequestThread implements Runnable {
		
	private static final Logger logger = LoggerFactory.getLogger(IcrxFileRequestThread.class);
		private ConfigVariable configVariable;
		private Long awayAgencyId;
		private String fileType;
		private RestTemplate restTemplate;
		
	public IcrxFileRequestThread() {
		super();
	}

	public IcrxFileRequestThread(ConfigVariable configVariable, Long awayAgencyId, RestTemplate restTemplate, String fileType) {
		super();
		this.configVariable = configVariable;
		this.awayAgencyId = awayAgencyId;
		this.restTemplate = restTemplate;
		this.fileType = fileType;
	}

	@Override
	public void run() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		StringBuilder sb = new StringBuilder();
		//sb.append(configVariable.getIcrxServiceUrl()).append(awayAgencyId).append("/");
		sb.append(configVariable.getIcrxServiceUrl()).append(fileType).append("/").append(awayAgencyId);
		System.out.println("URL built : "+sb.toString());
		logger.info("Icrx File Creation Request initiated for Away Agency {}: {}", awayAgencyId, sb.toString());
		String response = restTemplate.exchange(sb.toString(), HttpMethod.GET, entity, String.class).getBody();
		logger.info("Icrx File Creation Response for Away Agency {}: {}", awayAgencyId, response);
	}

}
