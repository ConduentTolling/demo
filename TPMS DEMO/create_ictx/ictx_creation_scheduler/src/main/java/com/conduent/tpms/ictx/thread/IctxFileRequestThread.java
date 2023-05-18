package com.conduent.tpms.ictx.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.ictx.model.ConfigVariable;

/**
 * ICTX thread to create request for each away agency
 * 
 * @author deepeshb
 *
 */
public class IctxFileRequestThread  {

	private static final Logger logger = LoggerFactory.getLogger(IctxFileRequestThread.class);

	private ConfigVariable configVariable;

	private Long awayAgencyId;

	private RestTemplate restTemplate;

	public IctxFileRequestThread() {
		super();
	}

	public IctxFileRequestThread(ConfigVariable configVariable, Long awayAgencyId, RestTemplate restTemplate) {
		super();
		this.configVariable = configVariable;
		this.awayAgencyId = awayAgencyId;
		this.restTemplate = restTemplate;
	}

/*	@Override
	public void run() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		StringBuilder sb = new StringBuilder();
		sb.append(configVariable.getIctxServiceUrl()).append(awayAgencyId).append("/");
		logger.info("Ictx File Creation Request initiated for Away Agency {}: {}", awayAgencyId,sb.toString());
		String response = restTemplate.exchange(sb.toString(), HttpMethod.GET, entity, String.class).getBody();
		logger.info("Ictx File Creation Response for Away Agency {}: {}", awayAgencyId, response);
	}
*/
}
