package com.conduent.tpms.qatp.classification.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.conduent.tpms.qatp.classification.dao.ComputeTollDao;
import com.conduent.tpms.qatp.classification.dto.CommonClassificationDto;
import com.conduent.tpms.qatp.classification.dto.TUnmatchedTx;
import com.conduent.tpms.qatp.classification.model.ConfigVariable;
import com.conduent.tpms.qatp.classification.service.CommonClassificationService;
import com.conduent.tpms.qatp.classification.utility.AsyncProcessCall;
import com.conduent.tpms.qatp.classification.utility.LocalDateAdapter;
import com.conduent.tpms.qatp.classification.utility.LocalDateTimeAdapter;
import com.conduent.tpms.qatp.classification.utility.MessagePublisherImpl;
import com.conduent.tpms.qatp.classification.utility.OffsetDateTimeConverter;
import com.google.common.base.Stopwatch;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Common Classificaton Service Implementation
 * 
 * @author deepeshb
 *
 */
@Service
public class CommonClassificationServiceImpl implements CommonClassificationService {

	/*
	 * @Autowired ConfigVariable configVariable;
	 */

	@Autowired
	private ComputeTollDao computeTollDAO;

	@Autowired
	private MessagePublisherImpl messagePublisher;
	
	@Autowired
	Gson gson;
	
	@Autowired
	AsyncProcessCall asyncProcessCall;

	private static final Logger logger = LoggerFactory.getLogger(CommonClassificationService.class);

	/**
	 * Insert message to Home etc Queue table
	 */
	public void insertToHomeEtcQueue(CommonClassificationDto object) {
		computeTollDAO.insertInToHomeEtcQueue(object);
	}
	
	/**
	 * Insert message to Viol Queue table
	 */
	public void insertToViolQueue(CommonClassificationDto object) {
		computeTollDAO.insertInToViolQueue(object);
	}

	/**
	 * Push Message to Oracle Streaming Service
	 */
	//Async call
	
	public void pushMessage(CommonClassificationDto object, String streamId) {
		logger.info("Message push starts for lane tx id {}", object.getLaneTxId());
		/*
		 * Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new
		 * LocalDateAdapter()) .registerTypeAdapter(LocalDateTime.class, new
		 * LocalDateTimeAdapter()) .excludeFieldsWithoutExposeAnnotation().create();
		 */
		/**Push messages to OSS**/
		/*Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
		.registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeConverter(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
		.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
		.registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();*/
		Stopwatch stopwatch = Stopwatch.createStarted();
		byte[] msg = gson.toJson(object).getBytes();
        logger.info("Message  Published: {}",gson.toJson(object).toString());   
		//messagePublisher.publishMessage(String.valueOf(object.getLaneTxId()), msg, streamId);
       
        
        messagePublisher.publishMessage(String.valueOf(object.getLaneTxId()), msg, streamId);
        stopwatch.stop();
		long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		logger.info("Time taken to Publish Message Shrikant ==> {} ms",millis);
		logger.info("Message push  ends for lane tx id {}", object.getLaneTxId());
	}

	@Override
	public void insertToTUnmatchedEntryTx(TUnmatchedTx unMatchedTx) {
		// TODO Auto-generated method stub
		computeTollDAO.insertIntoTUnmatchedEntryTx(unMatchedTx);
	}

	@Override
	public void insertToTUnmatchedExitTx(TUnmatchedTx unMatchedTx) {
		// TODO Auto-generated method stub
		computeTollDAO.insertIntoTUnmatchedExitTx(unMatchedTx);
	}
	
	



}
