package com.conduent.tpms.iag.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.conduent.tpms.iag.constants.ICLPConstants;
import com.conduent.tpms.iag.constants.IITCConstants;
import com.conduent.tpms.iag.dao.IAGDao;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.model.VAddressDto;
import com.conduent.tpms.iag.model.VVehicle;
import com.conduent.tpms.iag.service.IAGFileCreationServiceIITC;
import com.conduent.tpms.iag.utility.TextFileReader;

@Service
@Component
public class IITCFileCreationServiceImpl extends IAGFileCreationServiceIITC{
	private static final Logger log = LoggerFactory.getLogger(IITCFileCreationServiceImpl.class);

	@Autowired
	private TextFileReader textFileReader;
	
	@Autowired
	IAGDao iagDao;
	
	String deviceno;
	private int devicestatus;
	
	File file;
	String tagFileDest;
	String statFileDest;
	
	/**
	 * 
	 * @return File
	 */
	public File getFile() {
		return file;
	}
	/**
	 * 
	 * @param file
	 */
	public void setFile(File file) {
		this.file = file;
	}
	
	@Override
	public void initialize() {
		setIsHederPresentInFile(true);
		setIsDetailsPresentInFile(true);
		setIsTrailerPresentInFile(false);
	}

	
	public void createIITCFile(List<VAddressDto> info) throws IOException
	{
		try {
		tagFileDest = parentDest.concat("//").concat(IITCConstants.IITC_FILES); 
		statFileDest = parentDest.concat("//").concat(ICLPConstants.STAT_FILES);
		recordCountMap.put(ICLPConstants.TAG_SORTED_ALL, validationUtils.customFormatString(ICLPConstants.DEFAULT_COUNT, info.size()));
		if (!info.isEmpty()) {
			// Creating file 
			List<MappingInfoDto> nameProperties = getConfigurationMapping().getFileNameMappingInfo();
			fileName = buildFileSection(nameProperties, null);
			File file = new File(tagFileDest, fileName);
			FileWriter filewriter = new FileWriter(file);
			
			//Building TAG file header
			//After writing file
			List<MappingInfoDto> headerProperties = getConfigurationMapping().getHeaderMappingInfoList();
			String header = buildFileSection(headerProperties, null);

			log.info("Writing header to IITC file: {}",header);
			filewriter.write(header + "\n");

			//Build details
			List<MappingInfoDto> detailRecordProperties = getConfigurationMapping().getDetailRecMappingInfo();
			for (VAddressDto tagDevice : info) {
			boolean isValidRecord = validateTagRecord(tagDevice);	
			if(isValidRecord) {
				String record = buildFileSection(detailRecordProperties,tagDevice);	
				writeContentToFile(filewriter, record);
				totalRecords++;
				log.info("Records added to file is...{}",totalRecords);
				}
			else {
				skipCount++;
				log.info("Record not added to file: {}", tagDevice.toString());
			}
			
		}
				IITCheaderinfo.setRecordCount(String.valueOf(totalRecords));
				log.info("Total Record added ..{}",IITCheaderinfo.getRecordCount());
				filewriter.flush();
				filewriter.close();
			    //Overwriting header of TAG file
				overwriteFileHeader(file, headerProperties);
				populateRecordCountInMap();
				summaryFileCreationService.buildSummaryFileforIITC(recordCountMap, IITCConstants.IITC, statFileDest,fileName);
		}
	}finally {
		skipCount = 0;
		totalRecords = 0;
		info.clear();
		listofinfo.clear();	
		}
	}
	
	@Override
	public boolean overwriteFileHeader(File file, List<MappingInfoDto> headerProperties) {
		boolean headerUpdatedInFile = false;
				
		//Building new header with latest record count in file
		String newLine = buildFileSection(headerProperties, null);
		
		try {
			String filePath = file.getAbsolutePath();
			Scanner sc = new Scanner(new File(filePath));
			StringBuffer buffer = new StringBuffer();
			String oldLine = null;
			if (sc.hasNextLine() != false) {
				oldLine = sc.nextLine();
			}
			Scanner sc1 = new Scanner(new File(filePath));
			while (sc1.hasNextLine()) {
				buffer.append(sc1.nextLine() + "\n");
			}
	
			String fileContents = buffer.toString();
			log.debug("Contents of the file: " + fileContents);
			
			sc.close();
			//String newLine = header.toString();
			log.info("Replacing MTAG file {} header",file.getName());
			log.info("Old header: {}",oldLine);
			log.info("New header: {}",newLine);
			fileContents = fileContents.replaceAll(oldLine, newLine);
			FileWriter writer = new FileWriter(filePath);
			log.debug("new data: {}", fileContents);
			writer.append(fileContents);
			writer.flush();
			writer.close();
			headerUpdatedInFile = true;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return headerUpdatedInFile;
	}

	private void writeContentToFile(FileWriter filewriter, String record ) {
		
		try {
			filewriter.write(record + "\r");
			//error recovery
		} catch (IOException e) {
			log.error("Exception occurred while writing record {}",record);
			e.printStackTrace();
		}
		log.info("Record added to file: {}", record);
		
	}
	
	@Override
 	public void readExistingFileforIITC(File file) throws FileNotFoundException,IOException,Exception
	{
		String Filename = file.getName();
		log.info("File taken for process..{}",Filename);
		
		textFileReader.openFile(file);
		
		String currentline = textFileReader.readLine();	
		
		while ((currentline = textFileReader.readLine()) != null)
		{
			deviceno = currentline.substring(0,11);
			devicestatus = Integer.parseInt(currentline.substring(11,12));
			log.info("Device status taken from file is..{}",devicestatus);
			
			if(devicestatus == IITCConstants.DS_INVALID)
			{
				log.info("Device no taken from file is..{}",deviceno);
				
				int etc_account_id = iagDao.getetcaccountid(deviceno);
				log.info("ETC_ACCOUNT_ID for Device No {} is {}",deviceno,etc_account_id);
				
				customernameinfo = iagDao.getCustomerName(etc_account_id);
				log.info("Customer Name Info for etc_account_id {} is {}",etc_account_id,customernameinfo);
				
				VAddressDto addressdto = iagDao.getAddressInfo(etc_account_id);
				log.info("Address Info for etc_account_id {} is {}",etc_account_id,addressinfo);
				
				companyName = iagDao.getCompanyName(etc_account_id);
				log.info("Company Name for etc account id {} is taken as..{}",etc_account_id,companyName.getCompanyName());
				
				//VAddressDto addressdto = new VAddressDto();
			/*	addressdto.setStreet1(addressdto.getStreet1());
				addressdto.setStreet2(addressdto.getStreet2());
				addressdto.setCity(addressdto.getCity());
				addressdto.setState(addressdto.getState()); */
				
				if(addressdto.getStreet1() == null) 
				{
					addressdto.setStreet1(" ");
				}
				
				if(addressdto.getStreet2() == null) 
				{
					addressdto.setStreet2(" ");
				}
				
				if(customernameinfo.getLastName() == null) 
				{
					addressdto.setLastName(" ");
				}
				else
				{
					addressdto.setLastName(customernameinfo.getLastName());
				}
				
				if(customernameinfo.getFirstName() == null) 
				{
					addressdto.setFirstName(" ");
				}
				else
				{
					addressdto.setFirstName(customernameinfo.getFirstName());
				}
				
				if(customernameinfo.getMiddleInitial() == null) 
				{
					addressdto.setMiddleInitial(" ");
				}
				else
				{
					addressdto.setMiddleInitial(customernameinfo.getMiddleInitial());
				}
				
				if(addressdto.getZipcodeplus() != null) 
				{
					addressdto.setZipcode(addressdto.getZipcode().concat(addressdto.getZipcodeplus()));
				}
				else
				{
					addressdto.setZipcode(addressdto.getZipcode());
				}
				
				addressdto.setDeviceNo(deviceno);
				addressdto.setCompanyName(companyName.getCompanyName());
				
				log.info("Zipcode taken as :{}",addressdto.getZipcode());
				
				if(addressdto!= null) 
				{	
					boolean recordvalid = validaterecord(addressdto);
					
					if(recordvalid == true)
					{
						listofinfo.add(addressdto);
						log.info("List of information for creating IITC file...{}",listofinfo);
					}
					else
					{
						log.info("Validation Fail for device no {}...",deviceno);
					}
				}
			}
			else
			{
				skipCount++;
				log.info("Record skipped for device no...{}",deviceno);
			}
		}
	}
	
	public boolean validaterecord(VAddressDto recordinfo) {
		
		boolean recordvalid = true;
		
		//FN & LN null and CN not null
		if(recordinfo.getFirstName().equals(" ") && recordinfo.getLastName().equals(" ") && recordinfo.getCompanyName() != null)
		{
			recordinfo.setLastName(recordinfo.getCompanyName());
			//listofinfo.add(addressdto);
			recordvalid = true;
		}
		
		//LN is null
		else if(recordinfo.getLastName().equals(" ")) 
		{
			skipCount++;
			log.info("Record skipped as Last Name is null for device no...{}",recordinfo.getDeviceNo());
			recordvalid = false;
		}
		
		//Addr 1 and Addr 2 are null
		else if(recordinfo.getStreet1().equals(" ") && recordinfo.getStreet2().equals(" ")) 
		{
			skipCount++;
			log.info("Record skipped as Street 1 and Street 2 are null for device no...{}",recordinfo.getDeviceNo());
			recordvalid = false;
		}
		
		//Zipcode null
		else if(recordinfo.getZipcode() == null) 
		{
			skipCount++;
			log.info("Record skipped as zipcode is null for device no...{}",recordinfo.getDeviceNo());
			recordvalid = false;
		}
		
		return recordvalid;
	}
	private boolean validateTagRecord(VAddressDto info) {
		boolean isValidRecord = false;
		// Validate device prefix
		if(validationUtils.validateFirstName(info.getFirstName())) {
			if (validationUtils.validateMiddleInitial(info.getMiddleInitial())) {
				if(validationUtils.validateCompanyName(info.getCompanyName())) {
					if(validationUtils.validateAddress1(info.getStreet1())) {
						if(validationUtils.validateAddress2(info.getStreet2())) {
						if(validationUtils.validateCity(info.getCity())) {
							if(validationUtils.validateState(info.getState())) {
								if(validationUtils.validateZipcode(info.getZipcode())) {
										isValidRecord = true;
									}
								}
							}
						}
					}
				}
			}
		}
		
		return isValidRecord;
	}
	
	private void populateRecordCountInMap() {
		log.info("Setting counters to recordCountMap..");
		recordCountMap.put(ICLPConstants.TOTAL, IITCheaderinfo.getRecordCount()); 
		recordCountMap.put(ICLPConstants.SKIP, validationUtils.customFormatString(ICLPConstants.DEFAULT_COUNT,skipCount));
	}
}
