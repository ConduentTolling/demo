package com.conduent.parking.posting.config;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.conduent.parking.posting.constant.ConfigVariable;
import com.conduent.parking.posting.oss.OssStreamClient;
import com.conduent.parking.posting.utility.LocalDateAdapter;
import com.conduent.parking.posting.utility.LocalDateTimeAdapter;
import com.conduent.parking.posting.utility.OffsetDateTimeConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Configuration
@ComponentScan("com.conduent.*")
public class BeanConfiguration {

	@Autowired
	protected ConfigVariable configVariable;
	
	@Bean("failureQueueClient")
	public OssStreamClient failureQueueClient() throws IOException {
			OssStreamClient	failureQueueClient = new OssStreamClient(configVariable, configVariable.getFailureQueue(),
					configVariable.getGroupName());
		return failureQueueClient;
	} 
	
	
	@Bean("parkingQueueClient")
	public OssStreamClient parkingQueueClient() throws IOException {
			OssStreamClient	parkingQueueClient = new OssStreamClient(configVariable, configVariable.getParkingQueue(),
					configVariable.getGroupName());
		return parkingQueueClient;
	} 
	
	@Bean("gson")
	public Gson gsonCall() {
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeConverter(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.serializeNulls()
				.excludeFieldsWithoutExposeAnnotation().create(); 
		return gson;
	}
	
	@Bean
	public RestTemplate getRestTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException 
	{
		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true; SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy)
				.build();
		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext); 
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(); 
		requestFactory.setHttpClient(httpClient);
		requestFactory.setConnectTimeout(30000);
		requestFactory.setReadTimeout(30000);
		return new RestTemplate(requestFactory);
	}
	
	@Bean
	public Map<String, String> myTollPostingContext() {
	    final Map<String, String> myPodMap = new ConcurrentHashMap<>();
	    myPodMap.put("podName", System.getenv("HOSTNAME")!= null?System.getenv("HOSTNAME"):System.getenv("COMPUTERNAME"));
	    
	    return myPodMap;
	}
	
}
