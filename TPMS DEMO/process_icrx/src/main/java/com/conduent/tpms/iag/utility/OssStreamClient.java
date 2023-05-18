
package com.conduent.tpms.iag.utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.ResourceUtils;

import com.conduent.tpms.iag.model.ConfigVariable;
import com.google.common.util.concurrent.Uninterruptibles;
import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.streaming.StreamAdminClient;
import com.oracle.bmc.streaming.StreamClient;
import com.oracle.bmc.streaming.model.CreateGroupCursorDetails;
import com.oracle.bmc.streaming.model.Group;
import com.oracle.bmc.streaming.model.PutMessagesDetails;
import com.oracle.bmc.streaming.model.PutMessagesDetailsEntry;
import com.oracle.bmc.streaming.model.PutMessagesResultEntry;
import com.oracle.bmc.streaming.model.Stream;
import com.oracle.bmc.streaming.requests.ConsumerHeartbeatRequest;
import com.oracle.bmc.streaming.requests.CreateGroupCursorRequest;
import com.oracle.bmc.streaming.requests.GetGroupRequest;
import com.oracle.bmc.streaming.requests.GetMessagesRequest;
import com.oracle.bmc.streaming.requests.GetStreamRequest;
import com.oracle.bmc.streaming.requests.PutMessagesRequest;
import com.oracle.bmc.streaming.responses.CreateGroupCursorResponse;
import com.oracle.bmc.streaming.responses.GetMessagesResponse;
import com.oracle.bmc.streaming.responses.GetStreamResponse;
import com.oracle.bmc.streaming.responses.PutMessagesResponse;

public class OssStreamClient {

	private static final Logger logger = LoggerFactory.getLogger(OssStreamClient.class);

	private ConfigVariable configVariable;

	private StreamClient streamClient;

	private String cursor;

	private String node = null;

	private String streamId = null;

	private String group;

	public OssStreamClient(ConfigVariable configVariable, String streamId, String group) throws IOException {
		this.configVariable = configVariable;
		this.streamId = streamId;
		this.group = group;
		init();
	}

	/*
	 * public void init() throws IOException {
	 * 
	 * streamClient = getStreamClient(streamId); node = "node" +
	 * Util.getRandomNumber(10); if (group != null) { cursor =
	 * getCursorByGroup(streamClient, streamId, group, node); }
	 * 
	 * }
	 */
	
	@PostConstruct
	public void init() {
		try {
			logger.info("Initializing node/cusror for lica process...");
			//Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
			if(null == streamClient ) {
				streamClient = getStreamClient(streamId);
			}
			
			if(null == node)
			{
				node = "node" + Util.getRandomNumber(10);
				//node = "licaNode-" + commanUtility.getRandomNumber(10);
			}else{
				logger.info("Node name already available as {} : ",node);
			}
			
			if (group != null) {
				cursor = getCursorByGroup(streamClient, streamId, group, node);
				//cursor = getCursorByGroup(streamClient, streamId, "licaProcessGroup", node);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("*************GOT IOException***********" + e.getMessage());
		}
		 catch (com.oracle.bmc.model.BmcException e) {
				e.printStackTrace();
				logger.error("*************GOT com.oracle.bmc.model.BmcException***********" + e.getMessage());
				Uninterruptibles.sleepUninterruptibly(1, TimeUnit.MILLISECONDS);
				init();
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("*************GOT Exception***********" + e.getMessage());
		}
		
	}

	public void initStreamClient() throws IOException {
		streamClient = getStreamClient(streamId);
	}

	private String getCursorByGroup(StreamClient streamClient, String streamId, String groupName, String instanceName) {
//logger.info("Creating a cursor for group {} instance {}", groupName, instanceName);

		this.streamId = streamId;
		CreateGroupCursorDetails cursorDetails = CreateGroupCursorDetails.builder().groupName(groupName)
				.instanceName(instanceName).type(CreateGroupCursorDetails.Type.TrimHorizon).commitOnGet(true).build();

		CreateGroupCursorRequest createCursorRequest = CreateGroupCursorRequest.builder().streamId(streamId)
				.createGroupCursorDetails(cursorDetails).build();

		CreateGroupCursorResponse groupCursorResponse = streamClient.createGroupCursor(createCursorRequest);
		return groupCursorResponse.getCursor().getValue();
	}

	public StreamClient getStreamClient(String streamId) throws IOException {

		File file = ResourceUtils.getFile(configVariable.getConfigFilePath());
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
	public void heartBeat() throws IOException {
		try {
			if (streamClient == null) {
				init();
			}
			ConsumerHeartbeatRequest consumerHeartbeatRequest = ConsumerHeartbeatRequest.builder().cursor(cursor)
					.streamId(streamId).build();
			streamClient.consumerHeartbeat(consumerHeartbeatRequest);
		} catch (Exception ex) {
			streamClient = null;
		}
	}

	public List<String> getMessage(int limit) {
		try {
			if (streamClient == null) {
				init();
			}
			GetMessagesRequest messagesRequest = GetMessagesRequest.builder().streamId(streamId).cursor(cursor)
					.limit(limit).build();
			GetMessagesResponse getMessagesResponse = streamClient.getMessages(messagesRequest);
			if (getMessagesResponse != null && getMessagesResponse.getItems() != null
					&& !getMessagesResponse.getItems().isEmpty()) {
				List<String> dataList = new ArrayList<>(getMessagesResponse.getItems().size());
				for (int i = 0; i < getMessagesResponse.getItems().size(); i++) {
					String message = new String(getMessagesResponse.getItems().get(i).getValue());
					logger.info("Got message from queue {}", message);
					dataList.add(message);
					cursor = getMessagesResponse.getOpcNextCursor();
				}
				return dataList;
			} else {
				Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
			}
//cursor = getMessagesResponse.getOpcNextCursor();
		} catch (Exception ex) {
//logger.info("Exception: {}",ex.getMessage());
			streamClient = null;
		}
		return Collections.emptyList();
	}

	public String getDetails() {
		GetGroupRequest getGrpRequest = GetGroupRequest.builder().groupName(group).streamId(streamId).build();
		Group gp = streamClient.getGroup(getGrpRequest).getGroup();
		return "Group Name:" + gp.getGroupName() + "Partition Reservation:" + gp.getReservations();
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public boolean publishMessage(String laneTxId, byte[] msg) {
		try {

			List<PutMessagesDetailsEntry> messages = new ArrayList<>();
			messages.add(PutMessagesDetailsEntry.builder().key(laneTxId.getBytes()).value(msg).build());

			if (streamClient == null) {
				initStreamClient();
			}

			PutMessagesDetails messagesDetails = PutMessagesDetails.builder().messages(messages).build();
			PutMessagesRequest putRequest = PutMessagesRequest.builder().streamId(streamId)
					.putMessagesDetails(messagesDetails).build();

			logger.debug("Put message");
			PutMessagesResponse putResponse = streamClient.putMessages(putRequest);

			// the putResponse can contain some useful metadata for handling failures
			for (PutMessagesResultEntry entry : putResponse.getPutMessagesResult().getEntries()) {
				if (StringUtils.isNotBlank(entry.getError())) {
					logger.debug("Message with laneTxId {}, Error {}: {}", laneTxId, entry.getError(),
							entry.getErrorMessage());
					return false;
				} else {
					logger.info("Message with laneTxId {} published to partition {}, offset {}.", laneTxId,
							entry.getPartition(), entry.getOffset());
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

}
