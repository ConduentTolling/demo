package com.conduent.tpms.parking.intxService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.parking.constants.Constants;
import com.conduent.tpms.parking.dao.TAgencyDao;
import com.conduent.tpms.parking.dto.FileInfoDto;
import com.conduent.tpms.parking.model.ConfigVariable;
import com.conduent.tpms.parking.validation.FileUtil;
import com.google.common.base.Stopwatch;

@Service
public class LoadAllINTXService {

	@Autowired
	protected TAgencyDao tAgencyDao;

	@Autowired 
	private RestTemplate restTemplate;
	

	@Autowired
	private Environment env;
	
	@Autowired
	ApplicationContext applicationContext;

	private CopyOnWriteArrayList<List<String>> list= new CopyOnWriteArrayList<List<String>>();
	
	private static final Logger logger = LoggerFactory.getLogger(LoadAllINTXService.class);
	private FileUtil fileUtil = new FileUtil();
	
	public void loadAllFiles(String fileType) throws IOException, InterruptedException {
		try 
		{
			list.add((list.size()-1)+1, getAllAwayAgencies(fileType));	
			logger.info("list after away call...{},fileType: {}",list,fileType);
			Stopwatch stopwatch = Stopwatch.createStarted();
			for (List<String> agencyNameList : list) 
			{
			  logger.info("Agency list: {} for file type: {}",list,fileType);
			  for (String agencyName :agencyNameList) 
			  {
				  
				  logger.info("Agency Name: {} for file type: {}",agencyName,fileType);
				  List<File> filesList  = fileUtil.getAllfilesFromSrcDirectory(loadConfigurationMapping(fileType).getSrcDir(), agencyName, fileType);
				  logger.info("List of files : {}, list of files Size : {}, File Type: {}",filesList.toString(),filesList.size(),fileType);
			  
				  if (!CollectionUtils.isEmpty(filesList)) 
				  { 
					  processFile(filesList, fileType,agencyName); 
				  } 
				  else 
				  { 
					  logger.info("There are no files for agency prefix : {}",agencyName);
					  
				  } 
				 
			 }
		 }
			 
			logger.info("End of load all file...");
			list.clear();
			stopwatch.stop();
			long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
			logger.info("Total time taken to process all the files in direcory {}",millis);
		} catch (IOException e) 
		{
			logger.info("exception caught {} for filetype {}",e,fileType);
			e.printStackTrace();
		}
	}

	@Async
	public void processFile(List<File> filesList, String fileType,String agencySequence) throws IOException {
		//String urlParam =null;
		try {
			String url = null;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			url = env.getProperty(fileType.toLowerCase().concat(Constants.DOT).concat(Constants.destinationUrl));
		for (File file : filesList) {
			HttpEntity<File> entity = new HttpEntity<File>(file, headers);
			logger.info("Url to be used: {}", url);
			restTemplate.exchange(url, HttpMethod.POST, entity,String.class);
		}
		} catch (BeansException e) 
		{
			logger.info("BeansException Caught: ");
			e.printStackTrace();
		} catch (RestClientException e) 
		{
			logger.info("RestClientException Caught: ");
			e.printStackTrace();
		}
	}
	
	
	public synchronized List<String> getAllAwayAgencies(String fileType) {
		return tAgencyDao.getAllAwayAgencies(fileType);
	}

	public FileInfoDto loadConfigurationMapping(String fileType) {
		return tAgencyDao.getMappingConfigurationDetails(fileType);
	}
	
	
}
