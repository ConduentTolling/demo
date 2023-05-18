package com.conduent.Ibtsprocessing.utility;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.conduent.Ibtsprocessing.constant.ConfigVariable;
import com.google.common.util.concurrent.Uninterruptibles;
import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.streaming.StreamAdminClient;
import com.oracle.bmc.streaming.StreamClient;
import com.oracle.bmc.streaming.model.CreateGroupCursorDetails;
import com.oracle.bmc.streaming.model.Stream;
import com.oracle.bmc.streaming.requests.ConsumerHeartbeatRequest;
import com.oracle.bmc.streaming.requests.CreateGroupCursorRequest;
import com.oracle.bmc.streaming.requests.GetMessagesRequest;
import com.oracle.bmc.streaming.requests.GetStreamRequest;
import com.oracle.bmc.streaming.responses.CreateGroupCursorResponse;
import com.oracle.bmc.streaming.responses.GetMessagesResponse;
import com.oracle.bmc.streaming.responses.GetStreamResponse;

/**
 * Utility class to read message from Oracle Streaming Service
 * 
 * @author deepeshb
 *
 */
@Component
public class MessageReader {

	private static final Logger logger = LoggerFactory.getLogger(MessageReader.class);

	@Autowired
	private ConfigVariable configVariable;

	private StreamClient streamClient;

	private String cursor;



	private String streamId = null;

	@PostConstruct
	public void init() throws IOException {
		String node;
		streamClient = getStreamClient(configVariable.getStreamId());
		node = "node" + RandomNumberGenerator.getRandomNumber(10);
		cursor = getCursorByGroup(streamClient, configVariable.getStreamId(), configVariable.getGroupName(), node);

	}

	private String getCursorByGroup(StreamClient streamClient, String streamId, String groupName, String instanceName) {
		logger.info("Creating a cursor for group {} instance {}", groupName, instanceName);

		this.streamId = streamId;
		CreateGroupCursorDetails cursorDetails = CreateGroupCursorDetails.builder().groupName(groupName)
				.instanceName(instanceName).type(CreateGroupCursorDetails.Type.TrimHorizon).commitOnGet(true).build();

		CreateGroupCursorRequest createCursorRequest = CreateGroupCursorRequest.builder().streamId(streamId)
				.createGroupCursorDetails(cursorDetails).build();

		CreateGroupCursorResponse groupCursorResponse = streamClient.createGroupCursor(createCursorRequest);
		return groupCursorResponse.getCursor().getValue();
	}

	private StreamClient getStreamClient(String streamId) throws IOException {
		
		File file = ResourceUtils.getFile(configVariable.getConfigfilepath());
		final String configurationFilePath = file.toString();

		final ConfigFileReader.ConfigFile configFile = ConfigFileReader.parse(configurationFilePath);
		final AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);
		final StreamAdminClient adminClient = StreamAdminClient.builder().build(provider);

		Stream stream = getStream(adminClient, streamId);
		return StreamClient.builder().stream(stream).build(provider);

	}

	private Stream getStream(StreamAdminClient adminClient, String streamId) {
		GetStreamResponse getResponse = adminClient.getStream(GetStreamRequest.builder().streamId(streamId).build());
		return getResponse.getStream();
	}

	@Scheduled(fixedRate = 20000)
	private void heartBeat() throws IOException {
		try {
			ConsumerHeartbeatRequest consumerHeartbeatRequest = ConsumerHeartbeatRequest.builder().cursor(cursor)
					.streamId(streamId).build();
			streamClient.consumerHeartbeat(consumerHeartbeatRequest);
		} catch (Exception ex) {
			logger.info("Exception in heartbeat: {}", ex.getMessage());
			init();
		}
	}

	public GetMessagesResponse getMessage() throws IOException {
		GetMessagesResponse getMessagesResponse = null;
		try {
			if (streamClient == null) {
				init();
			}
			GetMessagesRequest messagesRequest = GetMessagesRequest.builder().streamId(configVariable.getStreamId())
					.cursor(cursor).limit(Integer.valueOf(configVariable.getMessageLimit())).build();
			getMessagesResponse = streamClient.getMessages(messagesRequest);
			if (getMessagesResponse != null && getMessagesResponse.getItems() != null
					&& !getMessagesResponse.getItems().isEmpty()) {
				cursor = getMessagesResponse.getOpcNextCursor();
				return getMessagesResponse;
			} else {
				// getMessages is a throttled method; clients should retrieve sufficiently large
				// message
				// batches, as to avoid too many http requests.
				Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
			}
		} catch (Exception ex) {
			logger.info("Exception while getting message from Queue: {}", ExceptionUtils.getStackTrace(ex));
			init();
		}
		return getMessagesResponse;
	}
	
	public GetMessagesResponse getMessage(String streamId,Integer messageLimit) throws IOException {
		GetMessagesResponse getMessagesResponse;
		try {
			if (streamClient == null) {
				init();
			}
			GetMessagesRequest messagesRequest = GetMessagesRequest.builder().streamId(streamId)
					.cursor(cursor).limit(Integer.valueOf(messageLimit)).build();
			getMessagesResponse = streamClient.getMessages(messagesRequest);
			if (getMessagesResponse != null && getMessagesResponse.getItems() != null
					&& !getMessagesResponse.getItems().isEmpty()) {
				cursor = getMessagesResponse.getOpcNextCursor();
				return getMessagesResponse;
			} else {
				// getMessages is a throttled method; clients should retrieve sufficiently large
				// message
				// batches, as to avoid too many http requests.
				Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
				
			}
			return getMessagesResponse;

		} 
		catch (Exception ex) {
			logger.info("Exception while getting message from Queue: {}", ExceptionUtils.getStackTrace(ex));
			init();
			return null;
		}
	}

}