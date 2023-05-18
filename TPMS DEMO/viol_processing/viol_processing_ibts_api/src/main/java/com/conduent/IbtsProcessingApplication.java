package com.conduent;

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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import com.conduent.Ibtsprocessing.utility.LocalDateAdapter;
import com.conduent.Ibtsprocessing.utility.LocalDateTimeAdapter;
import com.conduent.Ibtsprocessing.utility.OffsetDateTimeConverter;
import com.conduent.app.timezone.utility.TimeZoneConv;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SpringBootApplication
@EnableAsync
@ComponentScan({"com.conduent.*"})
public class IbtsProcessingApplication {
	
//	@Bean("timeZoneConv")
//    public TimeZoneConv getCurrentDateAndTime() {
//        return new TimeZoneConv();
//    }
	
	public static void main(String[] args) 
	{
		SpringApplication.run(IbtsProcessingApplication.class, args);
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
		return new RestTemplate(requestFactory);

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
