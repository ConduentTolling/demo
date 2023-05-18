package com.conduent.tpms.qeval.BatchConfig;


import java.util.Map;

//package com.example.batchprocessing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.conduent.tpms.qeval.BatchModel.AccountInfo;
import com.conduent.tpms.qeval.BatchServiceImpl.BatchServiceImpl;


public class PersonItemProcessor implements ItemProcessor<AccountInfo, AccountInfo> {
	@Autowired
	protected BatchServiceImpl batchServiceImpl;


	private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);

	@Override
	public AccountInfo process( AccountInfo account) throws Exception {

		log.info("Processor Start");
		Map<String, Object> processContext = null ; //buildProcesssContextSomewhere();

		return batchServiceImpl.processAccount(account, processContext);
		

	}



}