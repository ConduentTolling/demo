package com.conduent.tpms.iag.pub.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.conduent.tpms.iag.model.ConfigVariable;
import com.conduent.tpms.iag.pub.MessagePublisher;
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

	private static StreamClient streamClient;

	//private static long counter;

	public void getStreamclient(String streamId) throws IOException {

		File file = ResourceUtils.getFile(configVariable.getConfigfilepath());
		final String configurationFilePath = file.toString();

		final ConfigFileReader.ConfigFile configFile = ConfigFileReader.parse(configurationFilePath);
		final AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);
		final StreamAdminClient adminClient = StreamAdminClient.builder().build(provider);

		Stream stream = getStream(adminClient, streamId);
		streamClient = StreamClient.builder().stream(stream).build(provider);

	}

	private Stream getStream(StreamAdminClient adminClient, String streamId) {
		GetStreamResponse getResponse = adminClient.getStream(GetStreamRequest.builder().streamId(streamId).build());
		return getResponse.getStream();
	}

	public boolean publishMessage(String laneTxId, byte[] msg) {
		try {
			
			String streamId = configVariable.getStreamId();
			List<PutMessagesDetailsEntry> messages = new ArrayList<>();
			messages.add(PutMessagesDetailsEntry.builder().key(laneTxId.getBytes()).value(msg).build());
		
			// logger.info(String.format("Publishing %s messages to stream %s.",
			// messages.size(), streamId));
			if (streamClient == null) {
				getStreamclient(streamId);
			}

			PutMessagesDetails messagesDetails = PutMessagesDetails.builder().messages(messages).build();
			PutMessagesRequest putRequest = PutMessagesRequest.builder().streamId(streamId)
					.putMessagesDetails(messagesDetails).build();
			PutMessagesResponse putResponse = streamClient.putMessages(putRequest);

			//the putResponse can contain some useful metadata for handling failures
			for (PutMessagesResultEntry entry : putResponse.getPutMessagesResult().getEntries()) {
				if (StringUtils.isNotBlank(entry.getError())) {
					logger.info("Error {}: {}", entry.getError(), entry.getErrorMessage());
					return false;
				} else {
					//counter++;
					//logger.info("Number of Record Published: {}", counter);
					logger.info("Message published to partition {}, offset {}.", entry.getPartition(),
							entry.getOffset());
					return true;
				}
			}
		} catch (IOException e) {
			logger.error("Fail to publish msg {} Error: {}", msg, e.getMessage());
			return false;
		}
		return false;
	}
}
