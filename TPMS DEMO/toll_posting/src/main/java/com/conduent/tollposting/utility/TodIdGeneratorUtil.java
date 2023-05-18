package com.conduent.tollposting.utility;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.conduent.tollposting.constant.ConfigVariable;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Component
public class TodIdGeneratorUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(TodIdGeneratorUtil.class);
	
	@Autowired
	private ConfigVariable configVariable;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@SuppressWarnings("static-access")
	public String todId() throws IOException {
		try
		{
			JsonObject jsonObject = new JsonObject();
			 jsonObject.addProperty("userId", "urvashic");
			 jsonObject.addProperty("storeId", "BS1");
			 jsonObject.addProperty("storeName", "BirlaSoft");
			 jsonObject.addProperty("loginMode", "web");
			 
			 LocalDateTime time = LocalDateTime.now();
			 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
			 String formattedDate = time.format(dtf);
			
			 jsonObject.addProperty("loginTime", formattedDate);
			 
			 	HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(),headers);
				logger.info("Generating todId for payload: {}",entity);
				ResponseEntity<String> result = restTemplate.postForEntity(configVariable.getTodId(), entity, String.class);
				if(result.getStatusCodeValue()==200)
				{
					JsonObject json = new Gson().fromJson(result.getBody(), JsonObject.class);
					JsonObject element = json.get("result").getAsJsonObject();
					return element.get("todId").getAsString();
				}
		}
		catch (Exception ex)
		{
		}
		return null;
	}
	
	
}
