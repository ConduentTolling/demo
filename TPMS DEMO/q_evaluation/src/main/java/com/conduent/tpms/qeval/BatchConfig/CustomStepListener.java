package com.conduent.tpms.qeval.BatchConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.qeval.BatchDao.BatchDao;
import com.conduent.tpms.qeval.BatchModel.StatisticsData;
@Component
public class CustomStepListener implements StepExecutionListener {
	private static final Logger log = LoggerFactory.getLogger(CustomStepListener.class);
	@Autowired
	private StatisticsData statisticsData;
	@Autowired
	private BatchDao batchDao;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		log.info("Before Step------------------");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		
		log.info("After Step start------------------");
		log.info("TotalRecProcessed "+statisticsData.getTotalRecProcessed());
		log.info("TotalSkipCount-"+statisticsData.getSkipRecCount());
		log.info("RebillUpCount-"+statisticsData.getRebilleUpCount());
		log.info("RebillDown "+statisticsData.getRebilleDownCount());
		batchDao.insertStatisticData(statisticsData);
		log.info("After Step completed------------------");
		return null;
		
	}

}