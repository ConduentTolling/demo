package com.conduent.tollposting;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import com.conduent.app.timezone.utility.TimeZoneConv;

@SpringBootApplication
@ComponentScan(basePackages = "com.conduent.*")
@EnableAutoConfiguration(exclude = { ErrorMvcAutoConfiguration.class})
@EnableAsync
@EnableTransactionManagement
public class TollPostingApplication 
{
	public static void main(String[] args) 
	{
		SpringApplication.run(TollPostingApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException 
	{
		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true; 
		SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy)
				.build();
		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext); 
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(); 
		requestFactory.setHttpClient(httpClient);
		requestFactory.setConnectTimeout(120000);
		requestFactory.setReadTimeout(60000);
		return new RestTemplate(requestFactory);
	}
	
	
	  @Bean public Map<String, String> myTollPostingContext() { final Map<String,
	  String> myPodMap = new ConcurrentHashMap<>(); 
	  myPodMap.put("podName",
	  System.getenv("HOSTNAME")!=null?System.getenv("HOSTNAME"):System.getenv("COMPUTERNAME"));
	  
	  return myPodMap;
	  }
	 
	  
	  @Bean("timeZoneConv")
		public TimeZoneConv getCurrentDateAndTime() {
			return new TimeZoneConv();
		}

	
	
}
