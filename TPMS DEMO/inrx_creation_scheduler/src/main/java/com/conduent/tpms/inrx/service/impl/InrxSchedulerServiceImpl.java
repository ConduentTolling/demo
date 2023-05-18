package com.conduent.tpms.inrx.service.impl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.inrx.model.Agency;
import com.conduent.tpms.inrx.model.ConfigVariable;
import com.conduent.tpms.inrx.service.InrxSchedulerService;
import com.conduent.tpms.inrx.thread.InrxFileRequestThread;
import com.conduent.tpms.inrx.utility.StaticDataLoad;

/**
 * ICRX scheduler service implementation
 * 
 * @author petetid
 *
 */
@Service
public class InrxSchedulerServiceImpl implements InrxSchedulerService {

	private static final Logger log = LoggerFactory.getLogger(InrxSchedulerServiceImpl.class);

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
		log.info("Getting away agency list from T_Agency..");
		List<Agency> tempAwayAgencyList = staticDataLoad.getAwayAgency();

		if (!tempAwayAgencyList.isEmpty()) {
			log.info("Calling Executor Service for managing thread");
			ExecutorService tempExecutorService = Executors.newFixedThreadPool(configVariable.getThreadCount()); 
			log.info("Start processing agencies and execute in thread");
			for (int i = 0; i < tempAwayAgencyList.size(); i++) {
				tempExecutorService.execute(new InrxFileRequestThread(configVariable,
						tempAwayAgencyList.get(i).getAgencyId(), restTemplate, fileType));
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
}