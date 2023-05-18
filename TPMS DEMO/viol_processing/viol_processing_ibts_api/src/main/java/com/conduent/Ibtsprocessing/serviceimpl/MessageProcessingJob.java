package com.conduent.Ibtsprocessing.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.conduent.Ibtsprocessing.constant.ConfigVariable;
import com.conduent.Ibtsprocessing.dao.IQueueDao;
import com.conduent.Ibtsprocessing.dto.IBTSViolDTO;
import com.conduent.Ibtsprocessing.dto.IbtsViolProcessDTO;
import com.conduent.Ibtsprocessing.dto.QueueMessage;
import com.conduent.Ibtsprocessing.service.IMessageProcessingJob;
import com.conduent.Ibtsprocessing.utility.AsyncProcessCall;
import com.conduent.Ibtsprocessing.utility.LocalDateAdapter;
import com.conduent.Ibtsprocessing.utility.LocalDateTimeAdapter;
import com.conduent.Ibtsprocessing.utility.MessagePublisherImpl;
import com.conduent.Ibtsprocessing.utility.OffsetDateTimeConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Service
public class MessageProcessingJob implements IMessageProcessingJob {

	@Autowired
	ConfigVariable configVariable;

	@Autowired
	private IQueueDao queueDao;

	@Autowired
	private MessagePublisherImpl messagePublisher;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	AsyncProcessCall asyncProcessCall;
	
	@Autowired
	Gson gson;


	@Value("${config.ibts.api.url}")
	private String urlPath;

	private static final Logger logger = LoggerFactory.getLogger(IMessageProcessingJob.class);

	/**
	 * Insert message to Queue table
	 */
	public void insert(IbtsViolProcessDTO object) {
		queueDao.insertInToQueue(object);

	}

	/**
	 * Push Message to Oracle Streaming Service
	 */
	public void pushMessage(IbtsViolProcessDTO object, String streamId) {
		logger.debug("Message push for Away Agency starts for account {}", object.getEtcAccountId());
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
				.excludeFieldsWithoutExposeAnnotation().create();
		byte[] msg = gson.toJson(object).getBytes();

		messagePublisher.publishMessage(String.valueOf(object.getLaneTxId()), msg, streamId);

		logger.debug("Message push for Away Agency ends for account {}", object.getEtcAccountId());
	}

	/**
	 * Send Message to IBTS
	 */
	public boolean sendToIBTS(IBTSViolDTO object) 
	{
//		Gson gson=null;
//		
//		gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
//				.registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeConverter(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
//				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
//				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
//
//		gson.toJson(object);
		
		logger.debug("Message push for Violation starts for account {}", object.getEtcAccountId());
		logger.info("Json send to IBTS {}", gson.toJson(object).toString());
		try 
		{
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(gson.toJson(object), headers);
			
//			ResponseEntity<String> responseEntity = restTemplate.postForEntity(urlPath, entity,
//					String.class);
//			
//			if (responseEntity.getStatusCodeValue() == 200) {
//				logger.info("Got response body {}", responseEntity.getBody());
//			} else {
//				logger.info("Got response status code {}", responseEntity.getStatusCodeValue());
//			}
			
			logger.debug("Message push for Violation ends for account {}", object.getEtcAccountId());
			return asyncProcessCall.callIBTSAPI(urlPath, entity);
			
		}
		catch (Exception e) {
			logger.error("Error: {}", e.getMessage());
			return false;
		}

	}

}