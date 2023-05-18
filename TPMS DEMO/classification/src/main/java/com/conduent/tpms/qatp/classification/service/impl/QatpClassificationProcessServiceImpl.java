package com.conduent.tpms.qatp.classification.service.impl;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.classification.dto.TransactionDto;
import com.conduent.tpms.qatp.classification.factory.QatpClassificationFactory;
import com.conduent.tpms.qatp.classification.service.MessageReaderService;
import com.conduent.tpms.qatp.classification.service.QatpClassificationProcessService;
import com.conduent.tpms.qatp.classification.service.QatpClassificationService;
import com.conduent.tpms.qatp.classification.utility.AsyncProcessCall;
import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.Uninterruptibles;

@Service
public class QatpClassificationProcessServiceImpl implements QatpClassificationProcessService {

	private static final Logger logger = LoggerFactory.getLogger(QatpClassificationProcessServiceImpl.class);

	@Autowired
	private MessageReaderService messageReaderService;

	@Autowired
	private QatpClassificationFactory qatpClassificationFactory;
	
	@Autowired
	AsyncProcessCall asyncProcessCall;
	
	@Autowired
	TimeZoneConv timeZoneConv;

	/**
	 * To start the processing of transaction messsage
	 */
	@Override
	public void process() {
		while (true) {
			try {
				
				List<TransactionDto> tempTxDtoList = messageReaderService.readMessages();
//				ObjectMapper obj =new ObjectMapper();
//				List<TransactionDto> participantJsonList = obj.readValue(jsonString, new TypeReference<List<TransactionDto>>(){});
				if (tempTxDtoList != null) {
					
					if(tempTxDtoList.size()>0)
					{
					MDC.put("logFileName", "CLASSIFICATION_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
					logger.info("logFileName: {}",MDC.get("logFileName"));
					
					//Optional<String> podName = Optional.ofNullable(System.getenv("HOSTNAME"));
				//	logger.debug("Pod Name is {}",podName);
					Stopwatch stopwatch = Stopwatch.createStarted();	
					for (TransactionDto tempTxDto : tempTxDtoList) 
					{
						processTxBasedOnPlazaAgencyId(tempTxDto); // 100ms x 20 --> async
						//asyncProcessCall.processTxBasedOnPlazaAgencyId(tempTxDto);
					}
					
					Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
					
					
				stopwatch.stop();
				long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
				//logger.info("Time taken to process {} records ==> {}ms",tempTxDtoList.size(),millis);
					}
				}
			} catch (Exception e) {
				logger.error("Exception message: {}", ExceptionUtils.getStackTrace(e));
			}
		}

	}

	/**
	 * Process tx based on plaza agency id
	 * 
	 * @param tempTxDto
	 */
	private void processTxBasedOnPlazaAgencyId(TransactionDto tempTxDto) {
		if (tempTxDto != null) {
			try {
				logger.info("Recived Tx Msg: {}", tempTxDto);
				logger.info("Lane Tx Id process {}", tempTxDto.getLaneTxId());
				// tempPlazaAgencyId is null then execute the default implementation
				Integer tempPlazaAgencyId = tempTxDto.getPlazaAgencyId() != null ? tempTxDto.getPlazaAgencyId() : 0;
				QatpClassificationService qatpClassificationService = qatpClassificationFactory
						.getQatpClassificationObject(tempPlazaAgencyId);
				qatpClassificationService.processTxMsg(tempTxDto);
			} catch (Exception e) {
				logger.error("Exception in Tx Msg with lane tx id: {}, message: {}", tempTxDto.getLaneTxId(),
						ExceptionUtils.getStackTrace(e));
			}
		}
	}

}
