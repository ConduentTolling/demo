package com.conduent.Ibtsprocessing.serviceimpl;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.Ibtsprocessing.service.MessageReaderService;
import com.conduent.Ibtsprocessing.utility.MessageReader;
import com.conduent.app.timezone.utility.TimeZoneConv;
import com.oracle.bmc.streaming.responses.GetMessagesResponse;

/**
 * Read Transaction Message Service Implementation from Oracle Streaming Service
 * 
 * @author deepeshb
 *
 */
@Service
public class MessageReaderServiceImpl implements MessageReaderService {

	private static final Logger logger = LoggerFactory.getLogger(MessageReaderServiceImpl.class);

	@Autowired
	private MessageReader messageReader;

	@Autowired
	TimeZoneConv timeZoneConv;
	/**
	 * Read Transaction Messages from Oracle Streaming Service
	 * 
	 * @return List<TransactionDto>
	 * @throws IOException
	 */
	@Override
	public List<String> readMessages(String streamId, Integer messageLimit) throws IOException {
		List<String> dataList = null;
		GetMessagesResponse getMessagesResponse = messageReader.getMessage(streamId, messageLimit);
		if (getMessagesResponse != null && getMessagesResponse.getItems().size()>0) {
			
			MDC.put("logFileName", "IBTS_VIOL_PROCESSING_".concat(timeZoneConv.currentTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
	        logger.info("logFileName: {}",MDC.get("logFileName"));
			
			dataList = new ArrayList<>(getMessagesResponse.getItems().size());
			for (int i = 0; i < getMessagesResponse.getItems().size(); i++) {
				// String message = new
				// String(getMessagesResponse.getItems().get(i).getValue());
				// TransactionDto t = TransactionDto.dtoFromJson(message);
				String message = new String(getMessagesResponse.getItems().get(i).getValue());
				logger.info("Got message from queue {}", message);
				dataList.add(message);
				// dataList.add(t);
			}
		} else {
			logger.info("No Messages are found in Classifaction Queue");
		}
		return dataList;
	}

}
