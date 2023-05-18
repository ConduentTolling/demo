package com.conduent.tpms.intx.thread;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 * INTX thread to create request for each away agency
 * 
 * @author deepeshb
 *
 */
public class IntxFileRequestThread implements Runnable {
        
    private static final Logger logger = LoggerFactory.getLogger(IntxFileRequestThread.class);
        private String url;
        private String externFileId;
        private String awayAgencyId;
        private String fileType;
        private RestTemplate restTemplate;
        
    public IntxFileRequestThread() {
        super();
    }

    public IntxFileRequestThread(String url, String externFileId, String awayAgencyId, String fileType, RestTemplate restTemplate) {
        super();
        this.url = url;
        this.externFileId = externFileId;
        this.awayAgencyId = awayAgencyId;
        this.fileType = fileType;
        this.restTemplate = restTemplate;
    }

    @Override
    public void run() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        StringBuilder sb = new StringBuilder();
        //sb.append(configVariable.getIcrxServiceUrl()).append(awayAgencyId).append("/");
        //sb.append(configVariable.getIcrxServiceUrl()).append(fileType).append("/").append(awayAgencyId).append("/");
        sb.append(url).append("/").append(externFileId).append("/").append(awayAgencyId);
        System.out.println("URL built : "+sb.toString());
        logger.info("{} File Creation request url {}", fileType, url);
        String response = restTemplate.exchange(sb.toString(), HttpMethod.GET, entity, String.class).getBody();
        logger.info("{} File Creation Response for Away Agency {}: {}", fileType, awayAgencyId, response);
    }

}
