package com.conduent.tollposting.config;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.conduent.tollposting.constant.ConfigVariable;
import com.conduent.tollposting.oss.OssStreamClient;
import com.conduent.tollposting.utility.LocalDateAdapter;
import com.conduent.tollposting.utility.LocalDateTimeAdapter;
import com.conduent.tollposting.utility.OffsetDateTimeConverter;
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
	
	@Bean("ibtsQueueClient")
	public OssStreamClient ibtsQueueClient() throws IOException {
			OssStreamClient	ibtsQueueClient = new OssStreamClient(configVariable, configVariable.getIbtsQueue(),
					configVariable.getGroupName());
		return ibtsQueueClient;
	} 
	
	
	@Bean("atpQueueClient")
	public OssStreamClient atpQueueClient() throws IOException {
			OssStreamClient	atpQueueClient = new OssStreamClient(configVariable, configVariable.getAtpQueue(),
					configVariable.getGroupName());
		return atpQueueClient;
	} 
	
	@Bean("gson")
	public Gson gsonCall() {
				Gson gson = new GsonBuilder()
				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeConverter(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
				.serializeNulls()
				.excludeFieldsWithoutExposeAnnotation().create(); 
		return gson;
	}
}
