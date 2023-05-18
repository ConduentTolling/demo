package com.conduent.tpms.iag.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.iag.dto.AccountApiInfoDto;
import com.conduent.tpms.iag.dto.CustomerInfoDto;
import com.conduent.tpms.iag.model.ConfigVariable;
import com.conduent.tpms.iag.validation.IctxFileParserImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Component
public class FPMSAccountApi {

	private static final Logger log = LoggerFactory.getLogger(IctxFileParserImpl.class);

	@Autowired
	protected ConfigVariable configVariable;
	
	@Autowired
	public RestTemplate restTemplate;
	
	/**
	 * 
	 * Call FPMS Account Info API
	 *
	 * 
	 * 
	 * @param etcAccountId
	 * 
	 * @return AccountApiInfoDto
	 * 
	 */

	public AccountApiInfoDto callAccountApi(String etcAccountId) {

		try {

			CustomerInfoDto customerInfoDto = new CustomerInfoDto();

			customerInfoDto.setEtcAccountId(etcAccountId);

			System.out.println(configVariable.getAccountApiUri());

			log.info("Fetching FPMS api for URL:{} EtcAccountId:{} customerInfoDto:{}",configVariable.getAccountApiUri(),customerInfoDto.getEtcAccountId(),customerInfoDto);

			ResponseEntity<String> result = restTemplate.postForEntity(configVariable.getAccountApiUri(),
					customerInfoDto, String.class);

			log.info("result.getBody() " + result.getBody());

			if (result.getStatusCodeValue() == 200) {

				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);

			/*	Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
						.registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeConverter(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
						.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
						.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
						.excludeFieldsWithoutExposeAnnotation().create(); */
				
				Gson gson = new Gson();
				
				log.info("Converting json into an onject ");

				return gson.fromJson(jsonObject.getAsJsonObject("result"), AccountApiInfoDto.class);
			}
		} catch (Exception e) {
			log.info("Exception while get balance API call for ETC Account ID: {}",etcAccountId);
			e.printStackTrace();
			// return null;
		}
		return null;

	}

}
