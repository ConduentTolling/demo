package com.conduent.tpms.ictx;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.time.Duration;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

/**
 * Springboot Application for ICTX creation process.
 * 
 * @author deepeshb
 *
 */
@SpringBootApplication
@EnableTransactionManagement
//@EnableAsync
@ComponentScan({"com.conduent.*"})
public class IctxApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(IctxApplication.class, args);
	}

	@Override
	public void run(String... args) {
		// TODO: Start Process
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
	
	/*
	 * @Bean public RestTemplate restTemplate(RestTemplateBuilder builder) throws
	 * KeyStoreException, NoSuchAlgorithmException, KeyManagementException{
	 * TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String
	 * authType) -> true; SSLContext sslContext =
	 * org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null,
	 * acceptingTrustStrategy) .build(); SSLConnectionSocketFactory csf = new
	 * SSLConnectionSocketFactory(sslContext); CloseableHttpClient httpClient =
	 * HttpClients.custom().setSSLSocketFactory(csf).build();
	 * HttpComponentsClientHttpRequestFactory requestFactory = new
	 * HttpComponentsClientHttpRequestFactory();
	 * requestFactory.setHttpClient(httpClient);
	 * requestFactory.setConnectTimeout(30000);
	 * requestFactory.setReadTimeout(30000); return new
	 * RestTemplate(requestFactory); }
	 */
	
	
}
