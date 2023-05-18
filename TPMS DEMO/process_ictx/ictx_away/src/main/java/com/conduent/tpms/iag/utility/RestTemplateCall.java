package com.conduent.tpms.iag.utility;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.dto.TLaneDto;
import com.conduent.tpms.iag.model.ConfigVariable;
import com.google.gson.Gson;

@Component
public class RestTemplateCall {
	
	private static final Logger logger = LoggerFactory.getLogger(RestTemplateCall.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ConfigVariable configVariable;
	
	public boolean insertInTLane(Integer newLaneId, String externLaneId, Integer plazaId, Optional<TLaneDto> existingLaneDetail, boolean existing)
	{
		try
		{
			TLaneDto laneDto = new TLaneDto();
			if(existing)
			{
				//existing values
				laneDto.setAvi(existingLaneDetail.get().getAvi());
				laneDto.setOperationalMode(existingLaneDetail.get().getOperationalMode());
				laneDto.setStatus(existingLaneDetail.get().getStatus());
				laneDto.setLprEnabled(existingLaneDetail.get().getLprEnabled());
				laneDto.setSectionId(existingLaneDetail.get().getSectionId());
				laneDto.setLaneId(newLaneId);
				laneDto.setExternLaneId(externLaneId);
				laneDto.setPlazaId(plazaId);
				laneDto.setCalculateTollAmount(Constants.F);
			}
			else
			{
				//default values
				laneDto.setAvi("Y");
				laneDto.setOperationalMode("1");
				laneDto.setStatus("1");
				laneDto.setLprEnabled(null);
				laneDto.setSectionId(null);
				laneDto.setLaneId(newLaneId);
				laneDto.setExternLaneId(externLaneId);
				laneDto.setPlazaId(plazaId);
				laneDto.setCalculateTollAmount(Constants.F);
			}
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			logger.info("Setting data for Inserting Lane: {}", laneDto.toString());
			Gson gson1 = new Gson();
			HttpEntity<String> entity = new HttpEntity<String>(gson1.toJson(laneDto), headers);
			
			ResponseEntity<String> result = restTemplate.postForEntity(configVariable.getInsertLane(), entity,
					String.class);
	
			logger.info("Insert Lane result: {}", result);
			
			if (result.getStatusCodeValue() == 200) 
			{
				//JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				//logger.info(jsonObject.toString());
				logger.info("Got lane response for externLaneId {} Response: {}", laneDto.getExternLaneId(),result.getBody());
				return true;
			} 
			else 
			{
				//JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				logger.info("Got response {}", result.getBody());
				return false;
			}
		}
		catch(Exception e)
		{
			logger.info("Exception while inserting new lane info {} ",e.getMessage());
			return false;
		}
		
	}

}
