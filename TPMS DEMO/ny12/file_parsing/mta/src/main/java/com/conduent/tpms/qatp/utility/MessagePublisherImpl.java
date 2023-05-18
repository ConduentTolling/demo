package com.conduent.tpms.qatp.utility;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.conduent.tpms.qatp.model.ConfigVariable;
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

@Component
public class MessagePublisherImpl implements MessagePublisher {

	private static final Logger logger = LoggerFactory.getLogger(MessagePublisherImpl.class);
	
	@Autowired
	ConfigVariable configVariable;

	//private static StreamClient streamClient;
	
	@Autowired
	StreamClient streamClient;

	/**
	 * 
	 * Get stream client
	 * 
	 * @param streamId Stream ID of the stream
	 * @throws IOException
	 */
	public void getStreamclient(String streamId) throws IOException {

		File file = ResourceUtils.getFile(configVariable.getConfigfilepath());
		final String configurationFilePath = file.toString();

		final ConfigFileReader.ConfigFile configFile = ConfigFileReader.parse(configurationFilePath);
		final AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);
		final StreamAdminClient adminClient = StreamAdminClient.builder().build(provider);

		Stream stream = getStream(adminClient, streamId);
		streamClient = StreamClient.builder().stream(stream).build(provider);

	}

	/**
	 * Get stream 
	 * 
	 * @param adminClient StreamAdminClient
	 * @param streamId Stream ID of the stream
	 * @return Stream Stream
	 */
	private Stream getStream(StreamAdminClient adminClient, String streamId) {
		GetStreamResponse getResponse = adminClient.getStream(GetStreamRequest.builder().streamId(streamId).build());
		return getResponse.getStream();
	}

	
	/**
	 * Publish Message to stream
	 * 
	 * @param laneTxId  Key of the msg
	 * @param msg Value of the msg 
	 * @return boolean Message publsihed successfully or not
	 */
	public boolean publishMessage(String laneTxId, byte[] msg) {
		try {
			LocalDateTime from;
			LocalDateTime to;

			String streamId = configVariable.getStreamId();
			List<PutMessagesDetailsEntry> messages = new ArrayList<>();
			messages.add(PutMessagesDetailsEntry.builder().key(laneTxId.getBytes()).value(msg).build());

			logger.debug("Stream Client");
			from = LocalDateTime.now();
			if (streamClient == null) {
				getStreamclient(streamId);
			}
			to = LocalDateTime.now();

			logger.debug("Stream Client - Completed in {} ms", ChronoUnit.MILLIS.between(from, to));

			PutMessagesDetails messagesDetails = PutMessagesDetails.builder().messages(messages).build();
			PutMessagesRequest putRequest = PutMessagesRequest.builder().streamId(streamId)
					.putMessagesDetails(messagesDetails).build();

			logger.debug("Put message");
			from = LocalDateTime.now();
			PutMessagesResponse putResponse = streamClient.putMessages(putRequest);
			to = LocalDateTime.now();

			logger.debug("Put message - Published {} in {} ms", messages.size(), ChronoUnit.MILLIS.between(from, to));

			// the putResponse can contain some useful metadata for handling failures
			for (PutMessagesResultEntry entry : putResponse.getPutMessagesResult().getEntries()) {
				if (StringUtils.isNotBlank(entry.getError())) {
					logger.debug("Message with laneTxId {}, Error {}: {}", laneTxId, entry.getError(),
							entry.getErrorMessage());
					return false;
				} else {
					logger.debug("Message with laneTxId {} published to partition {}, offset {}.", laneTxId,
							entry.getPartition(), entry.getOffset());
					return true;
				}
			}
		} catch (IOException e) {
			logger.error("Fail to publish msg with laneTxId {}, {} Error: {}", laneTxId, msg, e.getMessage());
			return false;
		}
		return false;
	}

	
	/**
	 * Publish Messages to stream
	 * 
	 * @param Messages  
	 * @return long Number of messages published successfully
	 */
	public long publishMessages(List<PutMessagesDetailsEntry> messages) {
		long msgCounter = 0;

		LocalDateTime from;
		LocalDateTime to;

		String streamId = configVariable.getStreamId();

		logger.debug("Stream Client");
		from = LocalDateTime.now();
//			if (streamClient == null) {
//				getStreamclient(streamId);
//			}
		to = LocalDateTime.now();

		logger.debug("Stream Client - Completed in {} ms", ChronoUnit.MILLIS.between(from, to));

		PutMessagesDetails messagesDetails = PutMessagesDetails.builder().messages(messages).build();
		PutMessagesRequest putRequest = PutMessagesRequest.builder().streamId(streamId)
				.putMessagesDetails(messagesDetails).build();

		logger.debug("Put message");
		from = LocalDateTime.now();
		PutMessagesResponse putResponse = streamClient.putMessages(putRequest);
		to = LocalDateTime.now();

		logger.debug("Put message - Completed in {} ms", ChronoUnit.MILLIS.between(from, to));

		// the putResponse can contain some useful metadata for handling failures
		for (PutMessagesResultEntry entry : putResponse.getPutMessagesResult().getEntries()) {
			if (StringUtils.isNotBlank(entry.getError())) {

				logger.error("Message , Error {}: {}", entry.getError(), entry.getErrorMessage());
			} else {
				msgCounter++;
				logger.debug("Message  published to partition {}, offset {}.", entry.getPartition(),
						entry.getOffset());
			}
		}
		return msgCounter;

	}
	
	@PostConstruct
	public void test()
	{
		System.out.println("Hello");
		try {
			getStreamclient(configVariable.getStreamId());
			
			//publishMessage("1234","Hello OKE".getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
