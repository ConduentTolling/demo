package com.conduent.tpms.qatp.classification;

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

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.qatp.classification.model.ConfigVariable;
import com.conduent.tpms.qatp.classification.service.QatpClassificationProcessService;
import com.conduent.tpms.qatp.classification.utility.LocalDateAdapter;
import com.conduent.tpms.qatp.classification.utility.LocalDateTimeAdapter;
import com.conduent.tpms.qatp.classification.utility.OffsetDateTimeConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.streaming.StreamAdminClient;
import com.oracle.bmc.streaming.StreamClient;
import com.oracle.bmc.streaming.model.Stream;
import com.oracle.bmc.streaming.requests.GetStreamRequest;

/**
 * This class starts the process of get account information and classification
 * of transaction.
 * 
 * @author deepeshb
 *
 */
@SpringBootApplication(scanBasePackages = {"com.conduent.*"} )
@EnableAsync
public class QatpClassificationApplication implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(QatpClassificationApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(QatpClassificationApplication.class, args);
	}

	@Autowired
	QatpClassificationProcessService qatpClassificationProcessService;
	
	@Autowired
	TimeZoneConv timeZoneConv;
	


	@Override
	public void run(String... args) 
	{
		log.info("Time as per timezone : {}",timeZoneConv.currentTime());
		qatpClassificationProcessService.process();
	}


	@Bean(name = "restTemplate")
	public RestTemplate getRestTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

		SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy)
				.build();

		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

		requestFactory.setHttpClient(httpClient);
		requestFactory.setConnectTimeout(30000);
		requestFactory.setReadTimeout(30000);
		return new RestTemplate(requestFactory);

	}
	
	/** Push messages to OSS **/
	@Bean("gson")
	public Gson gsonCall() {
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeConverter(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
		return gson;
	}
		 
}
