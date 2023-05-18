package com.conduent.tpms.inrx.utility;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.conduent.tpms.inrx.model.ConfigVariable;
import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.streaming.StreamAdminClient;
import com.oracle.bmc.streaming.StreamClient;
import com.oracle.bmc.streaming.model.PutMessagesDetails;
import com.oracle.bmc.streaming.model.PutMessagesDetailsEntry;
import com.oracle.bmc.streaming.model.PutMessagesResultEntry;
import com.oracle.bmc.streaming.model.Stream;
import com.oracle.bmc.streaming.requests.GetStreamRequest;
import com.oracle.bmc.streaming.requests.PutMessagesRequest;
import com.oracle.bmc.streaming.responses.GetStreamResponse;
import com.oracle.bmc.streaming.responses.PutMessagesResponse;

/**
 * This MessagePublisher implementation provides method to publish message into
 * TPMS-Classification stream. .
 * 
 * @version 1.0
 * @since 17-05-2021
 */
@Component
public class MessagePublisherImpl  {

	private static final Logger logger = LoggerFactory.getLogger(MessagePublisherImpl.class);

	@Autowired
	ConfigVariable configVariable;

	private StreamClient streamClient;

	/**
	 * 
	 * Get stream client
	 * 
	 * @param streamId Stream ID of the stream
	 * @throws IOException
	 */
	public void getStreamclient(String streamId) throws IOException {

		File file = ResourceUtils.getFile(configVariable.getConfigFilePath());
		
		final String configurationFilePath = file.toString();
		
		final ConfigFileReader.ConfigFile configFile = ConfigFileReader.parse(configurationFilePath);
		final AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);
		logger.info("stream configurationFilePath {}", configurationFilePath);
		final StreamAdminClient adminClient = StreamAdminClient.builder().build(provider);
		logger.info("stream id {}",streamId);
		Stream stream = getStream(adminClient, streamId);
		streamClient = StreamClient.builder().stream(stream).build(provider);
		
	}

	/**
	 * Get stream
	 * 
	 * @param adminClient StreamAdminClient
	 * @param streamId    Stream ID of the stream
	 * @return Stream Stream
	 */
	private Stream getStream(StreamAdminClient adminClient, String streamId) {
		GetStreamResponse getResponse = adminClient.getStream(GetStreamRequest.builder().streamId(streamId).build());
		return getResponse.getStream();
	}


	/**
	 * Publish Messages to stream
	 * 
	 * @param Messages
	 * @return long Number of messages published successfully
	 */
	public long publishMessages(List<PutMessagesDetailsEntry> messages,String streamId) {
		long msgCounter = 0;

		try {
			//getStreamclient(configVariable.getPostingIntoATPQueueStreamId());
			getStreamclient(streamId);
			LocalDateTime from;
			LocalDateTime to;

			//String streamId = configVariable.getPostingIntoATPQueueStreamId();

			logger.info("Stream Client");
			from = LocalDateTime.now();
			if (streamClient == null) {
				getStreamclient(streamId);
			}
			to = LocalDateTime.now();

			logger.info("Stream Client - Completed in {} ms", ChronoUnit.MILLIS.between(from, to));

			PutMessagesDetails messagesDetails = PutMessagesDetails.builder().messages(messages).build();
			PutMessagesRequest putRequest = PutMessagesRequest.builder().streamId(streamId)
					.putMessagesDetails(messagesDetails).build();

			logger.info("Put message unzip");
			from = LocalDateTime.now();
			PutMessagesResponse putResponse = streamClient.putMessages(putRequest);
			to = LocalDateTime.now();

			logger.info("Put message - Completed in {} ms", ChronoUnit.MILLIS.between(from, to));

			// the putResponse can contain some useful metadata for handling failures
			for (PutMessagesResultEntry entry : putResponse.getPutMessagesResult().getEntries()) {
				if (StringUtils.isNotBlank(entry.getError())) {

					logger.error("Message , Error {}: {}", entry.getError(), entry.getErrorMessage());
				} else {
					msgCounter++;
					logger.info("Message  published to partition {}, offset {}.", entry.getPartition(),
							entry.getOffset());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("Fail to publish: Error: {}", e.getMessage());
			return msgCounter;
		}
		return msgCounter;

	}

	
	
}