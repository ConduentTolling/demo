package com.conduent.tpms.qatp.classification.utility;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.qatp.classification.constants.QatpClassificationConstant;
import com.conduent.tpms.qatp.classification.dto.CommonClassificationDto;
import com.conduent.tpms.qatp.classification.dto.NY12Dto;
import com.conduent.tpms.qatp.classification.dto.TransactionDto;
import com.conduent.tpms.qatp.classification.factory.QatpClassificationFactory;
import com.conduent.tpms.qatp.classification.model.ConfigVariable;
import com.conduent.tpms.qatp.classification.service.QatpClassificationService;
import com.google.common.base.Stopwatch;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Component
public class AsyncProcessCall {
	private static final Logger logger = LoggerFactory.getLogger(AsyncProcessCall.class);
	
	@Autowired
	private MessagePublisherImpl messagePublisher;
	
	@Autowired
	Gson gson;
	
	@Autowired
	private QatpClassificationFactory qatpClassificationFactory;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	ConfigVariable configVariable;

	@Async
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
		logger.info("Time Taken for Publish Async Message ==> {} ms",millis);
		logger.info("Message push  ends for lane tx id {}", object.getLaneTxId());
	}

	/*
	 * @Autowired // @Qualifier("streamClientHome") StreamClient streamClientHome;
	 * 
	 * @Autowired StreamClient streamClientVoilation;
	 * 
	 * @Autowired ConfigVariable configVariable;
	 */

	/*
	 * @Async public boolean asyncPublishMessage (String laneTxId, byte[] msg,
	 * String streamId) {
	 * 
	 * 
	 * LocalDateTime from; LocalDateTime to;
	 * 
	 * List<PutMessagesDetailsEntry> messages = new ArrayList<>();
	 * messages.add(PutMessagesDetailsEntry.builder().key(laneTxId.getBytes()).value
	 * (msg).build());
	 * 
	 * logger.debug("Stream Client"); from = LocalDateTime.now();
	 * 
	 * if (streamClient == null) { getStreamClient(streamId); }
	 * 
	 * to = LocalDateTime.now();
	 * 
	 * logger.debug("Stream Client - Completed in {} ms",
	 * ChronoUnit.MILLIS.between(from, to));
	 * logger.debug("Stream ID :=> "+streamId); PutMessagesDetails messagesDetails =
	 * PutMessagesDetails.builder().messages(messages).build();
	 * 
	 * PutMessagesRequest putRequest =
	 * PutMessagesRequest.builder().streamId(getStreamId(streamId))
	 * .putMessagesDetails(messagesDetails).build();
	 * 
	 * logger.debug("Put message"); from = LocalDateTime.now();
	 * 
	 * PutMessagesResponse putResponse =
	 * getStreamClient(streamId).putMessages(putRequest); to = LocalDateTime.now();
	 * 
	 * logger.debug("Put message - Published {} in {} ms", messages.size(),
	 * ChronoUnit.MILLIS.between(from, to));
	 * 
	 * // the putResponse can contain some useful metadata for handling failures for
	 * (PutMessagesResultEntry entry :
	 * putResponse.getPutMessagesResult().getEntries()) { if
	 * (StringUtils.isNotBlank(entry.getError())) {
	 * logger.debug("Message with laneTxId {}, Error {}: {}", laneTxId,
	 * entry.getError(), entry.getErrorMessage()); return false; } else {
	 * logger.debug("Message with laneTxId {} published to partition {}, offset {}."
	 * , laneTxId, entry.getPartition(), entry.getOffset()); return true; } } return
	 * false;
	 * 
	 * }
	 * 
	 * private StreamClient getStreamClient(String streamName) { if
	 * (streamName.equalsIgnoreCase("home")) { return streamClientHome; } else {
	 * return streamClientVoilation; } }
	 * 
	 * private String getStreamId(String streamName) { if
	 * (streamName.equalsIgnoreCase("home")) { return
	 * configVariable.getHomeAgencyStreamId(); } else { return
	 * configVariable.getViolationStreamId(); } }
	 */

	@Async
	public void processTxBasedOnPlazaAgencyId(TransactionDto tempTxDto)
	{
		if (tempTxDto != null) {
			try {
				logger.info("Recived Tx Msg: {}", tempTxDto);
				logger.info("Lane Tx Id {}", tempTxDto.getLaneTxId());
				
				Optional<String> podName = Optional.ofNullable(System.getenv("HOSTNAME"));
				logger.info("1# Process trnx with lane_tx_id {} by thread {} on pod {} ",
						tempTxDto.getLaneTxId(),Thread.currentThread().getName(),podName);
				
				// tempPlazaAgencyId is null then execute the default implementation
				Integer tempPlazaAgencyId = tempTxDto.getPlazaAgencyId() != null ? tempTxDto.getPlazaAgencyId() : 0;
				QatpClassificationService qatpClassificationService = qatpClassificationFactory.getQatpClassificationObject(tempPlazaAgencyId);
				qatpClassificationService.processTxMsg(tempTxDto);
			} catch (Exception e) {
				logger.error("Exception in Tx Msg with lane tx id: {}, message: {}", tempTxDto.getLaneTxId(),
						ExceptionUtils.getStackTrace(e));
			}
		}
	
	}
	
	@Async
	public void callInsertAPI(NY12Dto ny12Dto, String apiName) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Gson gson1 = new Gson();
		try {
			HttpEntity<String> entity = new HttpEntity<String>(gson1.toJson(ny12Dto), headers);
			
			System.out.println(gson1.toJson(ny12Dto));
			
			logger.info("JSON: {}",gson1.toJson(ny12Dto));

			if (apiName.equals(QatpClassificationConstant.NY12)) {
				ResponseEntity<String> result = restTemplate.postForEntity(configVariable.getUrlPathNY12(), entity,
						String.class);

				if (result.getStatusCodeValue() == 200) {
					JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
					logger.info("Got NY12 API response {}", jsonObject);
				} else {
					logger.info("Got NY12 API response status code {}", result.getStatusCodeValue());
				}
			} else if (apiName.equals(QatpClassificationConstant.UN_25A)) {

				ResponseEntity<String> result = restTemplate.postForEntity(configVariable.getUrlPath25a(), entity,
						String.class);

				if (result.getStatusCodeValue() == 200) {
					JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
					logger.info("Got 25A API response {}", jsonObject);
				} else {
					logger.info("Got 25A API response status code {}", result.getStatusCodeValue());
				}

			} else {
				logger.info("Both NY12 & 25A API not calling.......");
			}

		} catch (Exception e) {
			logger.error("Error: {}", e.getMessage());
		}
	}
	
}
