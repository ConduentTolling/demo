package com.conduent.tpms.qatp.utility;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.qatp.dao.ITransDetailDao;
import com.conduent.tpms.qatp.dao.TQatpMappingDao;
import com.conduent.tpms.qatp.dto.OCRDto;
import com.conduent.tpms.qatp.model.ConfigVariable;
import com.conduent.tpms.qatp.model.ReconciliationCheckPoint;
import com.conduent.tpms.qatp.model.TranDetail;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

@Component
public class AsyncProcessCall {
	
	private static final Logger logger = LoggerFactory.getLogger(AsyncProcessCall.class);
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ConfigVariable configVariable;
	
	@Autowired
	protected TQatpMappingDao tQatpMappingDao;
	
	@Autowired
	protected ITransDetailDao transDetailDao;
	
	@Async
	public void calltoOCRAPI(List<OCRDto> list) 
	{
		LocalDateTime from = null;
		try 
		{
			from = LocalDateTime.now();
			ResponseEntity<String> result= restTemplate.postForEntity(configVariable.getOcrAPI(), list, String.class);
			logger.debug("OCR API for lane tx id : {} - Completed in {} ms", list.get(0).getLaneTxId(),ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
			
			if(result.getStatusCodeValue()==200)
			{
				JsonObject jsonObject = new Gson().fromJson(result.getBody(), JsonObject.class);
				logger.info("After successfully sending info to IBTS {}",jsonObject.toString());
			}
		} 
		catch (RestClientException e) 
		{
			logger.info("Rest Client Exception  : {}",e.getMessage());
		} 
		catch (JsonSyntaxException e) 
		{
			logger.info("Json Syntax Exception  : {}",e.getMessage());
		}
	}
	
	@Async
	public void updateCheckpointTable(ReconciliationCheckPoint reconciliationCheckPoint)
	{
		LocalDateTime from = null;
		from = LocalDateTime.now();
		tQatpMappingDao.updateRecordCount(reconciliationCheckPoint);
		logger.debug("Time Taken for updating Checkpoint Table : {} - Completed in {} ms", ChronoUnit.MILLIS.between(from, LocalDateTime.now()));
	}
	
	@Async
	public String callParallelProcess(String url, HttpEntity<String> entity) 
	{
		String info = null;
		try 
		{
			ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity,String.class);
			if(responseEntity!=null)
			{
				logger.info("Response: {}",responseEntity.getBody());
				info = responseEntity.getBody();
			}
			
			//return info;
		} 
		catch (RestClientException e) 
		{
			logger.info("Exception {}",e.getMessage());
		} 
		catch (JsonSyntaxException e) 
		{
			logger.info("Exception");
		}
		return info;
	}
}
