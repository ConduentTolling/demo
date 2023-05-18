package com.conduent.tpms.unmatched.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.unmatched.dao.ComputeTollDao;
import com.conduent.tpms.unmatched.dto.CommonUnmatchedDto;
import com.conduent.tpms.unmatched.model.ConfigVariable;
import com.conduent.tpms.unmatched.service.CommonUnmatchedService;
import com.conduent.tpms.unmatched.utility.LocalDateAdapter;
import com.conduent.tpms.unmatched.utility.LocalDateTimeAdapter;
import com.conduent.tpms.unmatched.utility.MessagePublisherImpl;
import com.conduent.tpms.unmatched.utility.OffsetDateTimeConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Common Classificaton Service Implementation
 * 
 * @author deepeshb
 *
 */
@Service
public class CommonUnmatchedServiceImpl implements CommonUnmatchedService {

	@Autowired
	ConfigVariable configVariable;

	@Autowired
	private ComputeTollDao computeTollDAO;

	@Autowired
	private MessagePublisherImpl messagePublisher;

	private static final Logger logger = LoggerFactory.getLogger(CommonUnmatchedService.class);

	/**
	 * Insert message to Home etc Queue table
	 */
	public void insertToHomeEtcQueue(CommonUnmatchedDto object) {
		computeTollDAO.insertInToHomeEtcQueue(object);
	}
	
	/**
	 * Insert message to Viol Queue table
	 */
	public void insertToViolQueue(CommonUnmatchedDto object) {
		computeTollDAO.insertInToViolQueue(object);
	}

	/**
	 * Push Message to Oracle Streaming Service
	 */
	public void pushMessage(CommonUnmatchedDto object, String streamId) {
		logger.info("Message push starts for lane tx id {}", object.getLaneTxId());
		/*
		 * Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new
		 * LocalDateAdapter()) .registerTypeAdapter(LocalDateTime.class, new
		 * LocalDateTimeAdapter()) .excludeFieldsWithoutExposeAnnotation().create();
		 */
		/**Push messages to OSS**/
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
		.registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeConverter(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
		.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
		.registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
		
		byte[] msg = gson.toJson(object).getBytes();
        logger.info("Message  Published: {}",gson.toJson(object).toString());
		messagePublisher.publishMessage(String.valueOf(object.getLaneTxId()), msg, streamId);

		logger.info("Message push  ends for lane tx id {}", object.getLaneTxId());
	}


}
