package com.conduent.tpms.intx.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.intx.constant.IntxSchedulerConstant;
import com.conduent.tpms.intx.dao.ExternalFileDao;
import com.conduent.tpms.intx.model.Agency;
import com.conduent.tpms.intx.model.ConfigVariable;
import com.conduent.tpms.intx.model.ExternalFile;
import com.conduent.tpms.intx.service.IntxSchedulerService;
import com.conduent.tpms.intx.utility.StaticDataLoad;

@Service
public class IntxSchedulerServiceImpl implements IntxSchedulerService {

	private static final Logger logger = LoggerFactory.getLogger(IntxSchedulerServiceImpl.class);

	@Autowired
	StaticDataLoad staticDataLoad;

	@Autowired
	ConfigVariable configVariable;

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	ExternalFileDao externalFileDao;

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

	private void processCall(List<Agency> tempAwayAgencyList,String fileType) {
		String urlValue = null;
		
		if (fileType.equalsIgnoreCase(IntxSchedulerConstant.FILE_TYPE_INTX)) {
		    urlValue = configVariable.getIntxServiceUrl();
        } else if (fileType.equalsIgnoreCase(IntxSchedulerConstant.FILE_TYPE_ITXN)) {
            urlValue = configVariable.getItxnServiceUrl();
        }

		for (Agency agency : tempAwayAgencyList) {
		    List<ExternalFile> externalFileList = new ArrayList<>();
		    
		    if (fileType.equalsIgnoreCase(IntxSchedulerConstant.FILE_TYPE_INTX)) {
	            externalFileList = externalFileDao.getExternalFilesByAgency(agency.getDevicePrefix());
	        } else if (fileType.equalsIgnoreCase(IntxSchedulerConstant.FILE_TYPE_ITXN)) {
	            externalFileList = externalFileDao.getCorrExternalFilesByAgency(agency.getDevicePrefix());
	        }
		    
		    if (!externalFileList.isEmpty()) {
                for (ExternalFile externalFile: externalFileList) {
                    try {
                        String url = ("".concat(urlValue).concat("/").concat(externalFile.getFileId().toString()).concat("/").concat(agency.getAgencyId().toString()));
                        logger.info("{} File Creation request url {}", fileType, url);
                        String response = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(), String.class).getBody();
                        logger.info("{} File Creation Response for Away Agency {}: {}", fileType, agency.getAgencyId(), response);
                        Thread.sleep(4000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private HttpEntity<String> getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        return entity;
    }
    
}
