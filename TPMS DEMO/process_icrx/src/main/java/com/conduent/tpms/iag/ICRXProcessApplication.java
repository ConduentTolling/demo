package com.conduent.tpms.iag;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ICRXProcessApplication  {
	
	// @Autowired InvalidTagFileParserImpl invalidTagFileParserImpl;
	 

	public static void main(String[] args) {
		SpringApplication.run(ICRXProcessApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException
	{
	
		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true; 
		SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build(); 
		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext); 
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build(); 
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(); 
		requestFactory.setHttpClient(httpClient);
		return new RestTemplate(requestFactory);
	}
/*
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
	invalidTagFileParserImpl.fileParseTemplate();
	}
	
*/	

}
