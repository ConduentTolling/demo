package com.conduent.tpms.ictx.service;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.dao.TAgencyDao;
import com.conduent.tpms.iag.dto.FileInfoDto;
import com.conduent.tpms.iag.exception.InvalidFileTypeException;
import com.conduent.tpms.iag.model.ConfigVariable;
import com.conduent.tpms.iag.validation.FileUtil;
import com.google.common.collect.Lists;

@Service
public class LoadAllICTXService {

	@Autowired
	protected TAgencyDao tAgencyDao;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ConfigVariable config;

	@Autowired
	private Environment env;

	private FileInfoDto fileInfoDto;
	private LinkedList<List<String>> list= new LinkedList<List<String>>();

	private static final Logger logger = LoggerFactory.getLogger(LoadAllICTXService.class);
	private FileUtil fileUtil = new FileUtil();

	public void loadAllFiles(String fileType) throws IOException, InterruptedException 
	{
		fileInfoDto = loadConfigurationMapping(fileType);
		List<String> awayAgenceList = getAllAwayAgencies(fileType);
		
		for (String agencyName : awayAgenceList) 
		{
			List<File> filesList = fileUtil.getAllfilesFromSrcDirectory(fileInfoDto.getSrcDir(), agencyName,
						fileType);
			logger.info("List of files : {}, list of files Size : {}", filesList.toString(),filesList.size() );
			if (!CollectionUtils.isEmpty(filesList)) 
			{
				processFile(filesList, fileType, 1);
			} 
			else 
			{
				logger.info("There are no files for agency prefix : {}", agencyName);
			}
		}
		logger.info("End of load all file...");
	}

	private void processItagFile(List<String> awayAgenceList, String fileType) throws InterruptedException {
		List<List<String>> agesublist = Lists.partition(awayAgenceList, config.getSublistsize());
		for (List<String> list : agesublist) {
			list.stream().forEach(name -> {
				try 
				{
					List<File> filesList = fileUtil.getAllfilesFromSrcDirectory(loadConfigurationMapping(fileType).getSrcDir(), name,fileType);
					logger.info("List of files : {}, list of files Size: {} ", filesList.toString(),filesList.size() );

					if (!CollectionUtils.isEmpty(filesList)) 
					{
						int agencySequence = list.indexOf(name) + 1;
						logger.info("Sent file for agency {} with sequence: {}", name, agencySequence);
						
						processFile(filesList, fileType, agencySequence);
					} 
					else 
					{
						logger.info("There are no files for agency prefix : {}", name);
					}
				} catch (IOException e) 
				{
					e.printStackTrace();
				}
			});
			
			Thread.sleep(config.getThreadsleepcount());
		}
	}

	
	public void processFile(List<File> filesList, String fileType, int agencySequence) throws IOException {
		
		String url = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		for (File file : filesList) {
			HttpEntity<File> entity = new HttpEntity<File>(file, headers);
			
			url = env.getProperty(fileType.toLowerCase().concat(Constants.DOT).concat(Constants.destinationUrl)).concat("?agencySequence=" + agencySequence);

			logger.info("Url to be used is...{}", url);
			restTemplate.exchange(url, HttpMethod.POST, entity,String.class); 
		}
	}
	

	public List<String> getAllAwayAgencies(String fileType) 
	{
		return tAgencyDao.getAllAwayAgencies(fileType);
	}

	public FileInfoDto loadConfigurationMapping(String fileType) 
	{
		return tAgencyDao.getMappingConfigurationDetails(fileType);
	}
	
	public void loadITAGICLPFile(String fileType) throws IOException, InterruptedException, InvalidFileTypeException 
	{
		validatefileType(fileType);
		//fileInfoDto = loadConfigurationMapping(fileType);
		List<String> awayAgenceList = getAllAwayAgencies(fileType);
		
		processItagFile(awayAgenceList, fileType);
	}
	
	public void loadHomeITAGFile(String fileType) throws IOException, InterruptedException, InvalidFileTypeException 
	{
		validatefileTypeforHome(fileType);
		fileInfoDto = loadConfigurationMapping(fileType);
		List<String> homeAgenceList = tAgencyDao.getAllHomeAgencies();
			
		processHomeITAGFile(homeAgenceList, fileType);
	}

	public void processHomeITAGFile(List<String> homeAgenceList, String fileType) throws IOException 
	{
		for (String agencyName : homeAgenceList) 
		{
			List<File> filesList = fileUtil.getAllfilesFromSrcDirectory(fileInfoDto.getSrcDir(), agencyName,fileType);
			logger.info("List of files : {}, list of files Size : {}", filesList.toString(),filesList.size() );

			if (!CollectionUtils.isEmpty(filesList)) 
			{
				processFile(filesList, fileType, 1);
			} 
			else 
			{
				logger.info("There are no files for agency prefix : {}", agencyName);
			}
		}
	}

	public void validatefileTypeforHome(String fileType) throws InvalidFileTypeException 
	{
		if(fileType.equals(Constants.ITAG))
		{
			logger.info("File Type {} is valid...",fileType);
		}
		else
		{
			logger.info("File Type {} is invalid..",fileType);
			throw new InvalidFileTypeException("Invalid File Type");
		}
		
	}

	public void validatefileType(String fileType) throws InvalidFileTypeException 
	{
		if(fileType.equals(Constants.ITAG) || fileType.equals(Constants.ICLP))
		{
			logger.info("File Type {} is valid...",fileType);
		}
		else
		{
			logger.info("File Type {} is invalid..",fileType);
			throw new InvalidFileTypeException("Invalid File Type");
		}
		
	}

}
