package com.conduent.Ibtsprocessing.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.conduent.Ibtsprocessing.dto.IBTSViolDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class AsyncProcessCall {
	
	private static final Logger logger = LoggerFactory.getLogger(AsyncProcessCall.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	
	//@Async
	public boolean callIBTSAPI(String url, HttpEntity<String> entity) 
	{
		//boolean sucess=false;
		try 
		{
			
			ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity,String.class);
			
			if (responseEntity.getStatusCodeValue() == 200) 
			{
				logger.info("Got response body {}", responseEntity.getBody());
				return true;
			} 
			else 
			{
				logger.info("Got response status code {}", responseEntity.getStatusCodeValue());
				return false;
			}
		} 
		catch (RestClientException e) 
		{
			logger.info("Exception {}",e.getMessage());
			return false;
		}
		
	}

}
