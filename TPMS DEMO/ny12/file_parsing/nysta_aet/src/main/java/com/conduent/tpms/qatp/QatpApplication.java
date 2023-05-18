package com.conduent.tpms.qatp;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.sql.DataSource;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.model.ConfigVariable;
import com.conduent.tpms.qatp.utility.LocalDateAdapter;
import com.conduent.tpms.qatp.utility.LocalDateTimeAdapter;
import com.conduent.tpms.qatp.utility.OffsetDateTimeConverter;
import com.google.common.util.concurrent.Uninterruptibles;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.streaming.StreamAdminClient;
import com.oracle.bmc.streaming.StreamClient;
import com.oracle.bmc.streaming.model.Stream;
import com.oracle.bmc.streaming.requests.GetStreamRequest;
import com.oracle.bmc.streaming.responses.GetStreamResponse;

@SpringBootApplication(scanBasePackages = {"com.conduent.*"} )
@EnableAsync
public class QatpApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(QatpApplication.class);
	
	@Autowired
	ConfigVariable configVariable;
	
	
	public static void main(String[] args) {
		SpringApplication.run(QatpApplication.class, args);
	}

//	@Bean
//	public RestTemplate restTemplate(RestTemplateBuilder builder)
//	{ 
//	    return builder
//	            .setConnectTimeout(Duration.ofMillis(100000))
//	            .setReadTimeout(Duration.ofMillis(100000))
//	            .build();
//	}

	@Bean(name = "restTemplate")
	public RestTemplate getRestTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

		SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy)
				.build();

		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

		requestFactory.setHttpClient(httpClient);
		return new RestTemplate(requestFactory);

	}
	@Bean("timeZoneConv")
	public TimeZoneConv getCurrentDateAndTime() {
		return new TimeZoneConv();
	}
	
	@Bean("streamClient")
	public StreamClient getStreamclient() throws IOException {

//		File file = ResourceUtils.getFile(configVariable.getConfigfilepath());
//		final String configurationFilePath = file.toString();
//
//		final ConfigFileReader.ConfigFile configFile = ConfigFileReader.parse(configurationFilePath);
//		final AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);
//		final StreamAdminClient adminClient = StreamAdminClient.builder().build(provider);
//
//		Stream stream = null;
//		try 
//		{
//			stream = adminClient.getStream(GetStreamRequest.builder().streamId(configVariable.getStreamId()).build()).getStream();
//			return StreamClient.builder().stream(stream).build(provider);
//		} 
//		catch (com.oracle.bmc.model.BmcException e) 
//		{
//			e.getMessage();
//			logger.error("*************GOT com.oracle.bmc.model.BmcException***********" + e.getMessage());
//			Uninterruptibles.sleepUninterruptibly(1, TimeUnit.MILLISECONDS);
//			stream = adminClient.getStream(GetStreamRequest.builder().streamId(configVariable.getStreamId()).build()).getStream();
//		}
//		return StreamClient.builder().stream(stream).build(provider);
//		
		return createStreamclient();
	}
	
	
	 private Stream getStream(StreamAdminClient adminClient, String streamId) 
	 {
	        GetStreamResponse getResponse = adminClient.getStream(GetStreamRequest.builder().streamId(streamId).build());
	        return getResponse.getStream();
	 }
	
	public StreamClient createStreamclient() throws IOException
	{
		File file = ResourceUtils.getFile(configVariable.getConfigfilepath());
		final String configurationFilePath = file.toString();

		final ConfigFileReader.ConfigFile configFile = ConfigFileReader.parse(configurationFilePath);
		final AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);
		
		final StreamAdminClient adminClient = StreamAdminClient.builder().build(provider);
		//adminClient.setEndpoint("https://streaming.us-ashburn-1.oci.oraclecloud.com");
		
		Stream stream=null;
		do
		{
			stream = getStream(adminClient, configVariable.getStreamId());
		
			try 
			{
				//stream = adminClient.getStream(GetStreamRequest.builder().streamId(configVariable.getStreamId()).build()).getStream();
				return StreamClient.builder().stream(stream).build(provider);
			} 
			catch (com.oracle.bmc.model.BmcException e) 
			{
				e.printStackTrace();
				logger.error("*************GOT com.oracle.bmc.model.BmcException***********" + e.getMessage());
				Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
				createStreamclient();
			}
		}while(stream==null);
			
		return null;
	
	}
	
	@Bean("gson")
	public Gson gsonCall()
	{
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeConverter(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.excludeFieldsWithoutExposeAnnotation().create();
		
		return gson;
	}
}
