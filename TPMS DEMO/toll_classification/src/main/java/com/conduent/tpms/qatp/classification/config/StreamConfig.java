package com.conduent.tpms.qatp.classification.config;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.classification.model.ConfigVariable;
import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.streaming.StreamAdminClient;
import com.oracle.bmc.streaming.StreamClient;
import com.oracle.bmc.streaming.model.Stream;
import com.oracle.bmc.streaming.requests.GetStreamRequest;

@Configuration
@ComponentScan("com.conduent.*")

public class StreamConfig {
	
	@Autowired
	ConfigVariable configVariable;

	@Bean("timeZoneConv")
	public TimeZoneConv getCurrentDateAndTime() {
		return new TimeZoneConv();
	}
	
	
	@Bean("streamClientHome")
	public StreamClient getStreamClientHome() throws IOException {

		File file = ResourceUtils.getFile(configVariable.getConfigfilepath());
		//File file = ResourceUtils.getFile("D:/Directory/config/config_dev.txt");
		final String configurationFilePath = file.toString();

		final ConfigFileReader.ConfigFile configFile = ConfigFileReader.parse(configurationFilePath);
		final AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);
		final StreamAdminClient adminClient = StreamAdminClient.builder().build(provider);

		Stream stream = adminClient
				.getStream(GetStreamRequest.builder().streamId(configVariable.getAwayAgencyStreamId()).build())
				//.getStream(GetStreamRequest.builder().streamId("ocid1.stream.oc1.iad.amaaaaaai6on7cyabc7l2s5nqecyck7mntgl2uw4xe2lemof4aho3tvlpsnq").build())
				.getStream();
		return StreamClient.builder().stream(stream).build(provider);

	}
	
	
	@Bean("streamClientVoilation")
	public StreamClient getStreamclientVoilation() throws IOException {

		File file = ResourceUtils.getFile(configVariable.getConfigfilepath());
		final String configurationFilePath = file.toString();

		final ConfigFileReader.ConfigFile configFile = ConfigFileReader.parse(configurationFilePath);
		final AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);
		final StreamAdminClient adminClient = StreamAdminClient.builder().build(provider);

		Stream stream = adminClient
				.getStream(GetStreamRequest.builder().streamId(configVariable.getViolationStreamId()).build())
				.getStream();
		return StreamClient.builder().stream(stream).build(provider);
	  
	  }
	
	 
	@Bean("classificationClient")
	public StreamClient getClassificationClient() throws IOException {

		File file = ResourceUtils.getFile(configVariable.getConfigfilepath());
		final String configurationFilePath = file.toString();

		final ConfigFileReader.ConfigFile configFile = ConfigFileReader.parse(configurationFilePath);
		final AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);
		final StreamAdminClient adminClient = StreamAdminClient.builder().build(provider);

		Stream stream = adminClient
				.getStream(GetStreamRequest.builder().streamId(configVariable.getStreamId()).build())
				.getStream();
		return StreamClient.builder().stream(stream).build(provider);
		

	}
	 
}
