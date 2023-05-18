package com.conduent.tpms.ictx.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.ictx.dto.AtpMessageDto;
import com.conduent.tpms.ictx.model.ConfigVariable;
import com.conduent.tpms.ictx.service.MessagePublisherService;
import com.conduent.tpms.ictx.utility.LocalDateAdapter;
import com.conduent.tpms.ictx.utility.LocalDateTimeAdapter;
import com.conduent.tpms.ictx.utility.MessagePublisher;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oracle.bmc.streaming.model.PutMessagesDetailsEntry;

/**
 * Message publishing service
 * 
 * @author deepeshb
 *
 */
@Service
public class MessagePublisherServiceImpl implements MessagePublisherService {
	@Autowired
	ConfigVariable configVariable;

	@Autowired
	private MessagePublisher messagePublisher;

	private static final Logger logger = LoggerFactory.getLogger(MessagePublisherServiceImpl.class);

	/**
	 * Publish message to stream
	 * 
	 * @param tempAtpMessageDto
	 * @return long
	 */
	public long publishMessageToStream(List<AtpMessageDto> tempAtpMessageDto) {
		// Added for logging:
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
				.excludeFieldsWithoutExposeAnnotation().create();
		long msgCounter = 0;
		AtpMessageDto tempTx;
		List<PutMessagesDetailsEntry> messages = new LinkedList<>();
		for (int i = 0; i < tempAtpMessageDto.size(); i++) {
			tempTx = tempAtpMessageDto.get(i);
			logger.info("Publish Message {}",tempTx);
			byte[] msg = gson.toJson(tempTx).getBytes();
			messages.add(PutMessagesDetailsEntry.builder().key(tempTx.getLaneTxId().toString().trim().getBytes())
					.value(msg).build());
		}
		int msgsSize = messages.size();
		if (msgsSize != 0) {
			msgCounter = messagePublisher.publishMessages(messages, configVariable.getStreamId());
			if (msgCounter == msgsSize) {
				logger.debug("Messages Published Successfully: Expectd : {},Published : {} ", msgsSize, msgCounter);
			} else if (msgCounter == 0) {
				logger.info("Failed to publish Messages: Expectd : {},Published : {}", msgsSize, msgCounter);
			} else {
				logger.info("Messages Published Partially: Expectd : {},Published : {} ", msgsSize, msgCounter);
			}
		}
		return msgCounter;

	}
}
