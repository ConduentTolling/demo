package com.conduent.tpms.qeval.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.qeval.dao.QEvaluationFpmsDao;
import com.conduent.tpms.qeval.service.QevaluationService;

/**
 * Qevaluation Service Implementation to start the process
 * 
 * @author Urvashic
 *
 */

@Service
public class QevaluationServiceImpl implements QevaluationService
{
	private static final Logger log = LoggerFactory.getLogger(QevaluationServiceImpl.class);

	@Autowired
	protected QEvaluationFpmsDao qEvaluationFpmsDao;
	
	@Override
	public void process() {
		log.info("Proceess executed");
		String edate = qEvaluationFpmsDao.getLastEvalRunDate();
		System.out.println(edate);
		if(edate == null)
		{
			runFirstEvalForAccount();
		}
		System.out.println("Proceess executed");
		
	}

	private void runFirstEvalForAccount() {
		// TODO Auto-generated method stub
		
	}

}
