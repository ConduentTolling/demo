package com.conduent.tpms.iag.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.iag.constants.UUCTConstants;
import com.conduent.tpms.iag.dao.impl.TODCreationDaoImpl;
import com.conduent.tpms.iag.model.BatchUserInfo;
import com.conduent.tpms.iag.model.TODBatchUser;
import com.conduent.tpms.iag.model.TODOutput;
import com.conduent.tpms.iag.model.TodResponseUtill;
import com.conduent.tpms.iag.model.TourOfDuty;
import com.conduent.tpms.iag.service.TODCreationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Service
@Component
public class TODCreationServiceImpl extends TODCreationService {
	
	private static final Logger log = LoggerFactory.getLogger(TODCreationServiceImpl.class);
	
	@Autowired
	TODCreationDaoImpl todCreationdaoimpl;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${todService.connect.url}")
	private String todServiceUrl;
	
	public TODOutput buildTodId(String todID, @ RequestBody TODBatchUser todRequestVO)
	{
		TODOutput todOutput = null;
		TODBatchUser todbatchUser = new TODBatchUser();
		BatchUserInfo batchUserInfo = null;
		batchUserInfo = todCreationdaoimpl.batchUserDetail();
		log.info("Batch User...{}",batchUserInfo);
		
		List<TourOfDuty> listofTod = todCreationdaoimpl.listOfFinancialsNotClosed(todRequestVO.getUserId(),UUCTConstants.OPEN,true); 
		log.info("List of TOD with Financials Status not closed...{}",listofTod);
		
		TourOfDuty requestVO = new TourOfDuty();
		try {
			if (listofTod.size() > 0) {
				requestVO.setTodId(listofTod.get(0).getTodId());
				if ((listofTod.get(0).getStatus().equals("OPEN") || listofTod.get(0).getFinancials().equals(true))
						&& (!listofTod.get(0).getTourDate().equals(LocalDate.now()))) {
					log.info("TOD is invalid", "");
					throw new Exception("TOD is invalid");
				} else {

					todOutput = new TODOutput();
					todOutput.setTodId(listofTod.get(0).getTodId());
				}
				
			} else {
				todOutput = this.createTOD(todbatchUser, batchUserInfo);
			}
		}

		catch (Exception e) {
			log.info("TOD is invalid", "");
			e.getMessage();
			requestVO.setStatus(UUCTConstants.CLOSED);
			requestVO.setFinancials(false);
			todCreationdaoimpl.updateTOD(requestVO);

			todOutput = this.createTOD(todbatchUser, batchUserInfo);

		}
		
		return todOutput;
	}

	private TODOutput createTOD(TODBatchUser todbatchUser, BatchUserInfo batchUserInfo) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		TODOutput output = null;
		
		todbatchUser.setUserId(batchUserInfo.getUserId());
		todbatchUser.setStoreId(batchUserInfo.getStoreId());
		todbatchUser.setStoreName(batchUserInfo.getStoreName());
		todbatchUser.setLoginTime(LocalDateTime.now());
		todbatchUser.setLoginMode(batchUserInfo.getLoginMode());
		
		HttpEntity<TODBatchUser> entity = new HttpEntity<>(todbatchUser, headers);
		
		ResponseEntity<String> responseEntity = null;
		
		try {

			log.info("connecting to tod service-----{}");
			
			responseEntity = restTemplate.exchange(todServiceUrl, HttpMethod.POST, entity, String.class);
			
			System.out.println(responseEntity);
			
			if (null == responseEntity) 
			{
				log.info("Response Entity is null...{}",responseEntity);
			}
			TodResponseUtill responseUtill = objectMapper.readValue(responseEntity.getBody(), TodResponseUtill.class);
			output = responseUtill.getResult();
			
		}

		catch (ResourceAccessException e) 
		{
			e.getMessage();
			e.printStackTrace();

		} catch (JsonProcessingException e) 
		{
			e.getMessage();
			e.printStackTrace();
		} 

		return output;
	}

}
