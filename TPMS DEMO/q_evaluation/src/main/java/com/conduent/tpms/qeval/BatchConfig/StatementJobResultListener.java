package com.conduent.tpms.qeval.BatchConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.qeval.BatchDao.BatchDao;
import com.conduent.tpms.qeval.BatchModel.StatisticsData;
import com.conduent.tpms.qeval.BatchService.BatchService;
import com.conduent.tpms.qeval.dao.impl.QEvaluationFpmsDaoImpl;


@Component
public class StatementJobResultListener implements JobExecutionListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StatementJobResultListener.class);

	
	private JobExecution active;

	@Autowired
	private JobOperator jobOperator;
	@Autowired
	public  BatchService batchService;
	@Autowired
	QEvaluationFpmsDaoImpl qEval;
	@Autowired
	BatchDao batchDao;
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		
		LOGGER.info("before job start ----------------------------");
		
		
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		LOGGER.info("After job----------------------------");
		batchDao.updateStmtRunDate();
	
	}
}
