package com.conduent.tpms.iag.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.iag.constants.ICLPConstants;
import com.conduent.tpms.iag.dao.IAGDao;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.model.VVehicle;
import com.conduent.tpms.iag.service.IAGFileCreationSevice;
import com.conduent.tpms.iag.utility.TextFileReader;

@Service
public class ICLPFileCreationServiceImpl extends IAGFileCreationSevice {
	private static final Logger log = LoggerFactory.getLogger(ICLPFileCreationServiceImpl.class);

	String tagFileDest;
	String statFileDest;
	
	
	@Autowired
	IAGDao iagDao;
	
	@Autowired
	private TextFileReader textFileReader;
	
	String deviceno;
	
	String accountno;
	
	VVehicle vdeviceno;

	List<String> listofdeviceno = new ArrayList<String>();
	
	
	
	List<String> taginfo = new ArrayList<String>();
	
	private int devicestatus;
	
	File file;

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
	
	//@Override
	public void createICLPFile(List<VVehicle> info) throws IOException {
		try {
			tagFileDest = parentDest.concat("//").concat(ICLPConstants.ICLP_FILES); 
			statFileDest = parentDest.concat("//").concat(ICLPConstants.STAT_FILES);
			//tagFileDest = "D:\\Directory\\ParentDirectory\\ICLP_FILES";//for local DO NOT COMMIT
			//statFileDest = "D:\\Directory\\ParentDirectory\\STAT_FILES";//for local DO NOT COMMIT
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

				log.info("Writing header to ICLP file: {}",header);
				filewriter.write(header + "\n");

				//Build details
				List<MappingInfoDto> detailRecordProperties = getConfigurationMapping().getDetailRecMappingInfo();
				for (VVehicle tagDevice : info) {
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
					ICLPheaderinfo.setRecordCount(String.valueOf(totalRecords));
					log.info("Total Record added ..{}",ICLPheaderinfo.getRecordCount());
					filewriter.flush();
					filewriter.close();
				    //Overwriting header of TAG file
					overwriteFileHeader(file, headerProperties);
					populateRecordCountInMap();
					summaryFileCreationService.buildSummaryFile(recordCountMap, ICLPConstants.ICLP, statFileDest,fileName);
			}
		}finally {
			skipCount = 0;
			totalRecords = 0;
			plateinfolist.clear();
			info.clear();
		}
	}
	
	private void writeContentToFile(FileWriter filewriter, String record ) {
		//log.info("LOST:{}, ZERO:{}, LOW_BAL:{}, GOOD:{}, SKIP:{}",lostCountNysta, zeroCountNysta, lowCountNysta, goodCountNysta, skipCount);
		//log.info("NYSBA_GOOD:{}, NYSBA_LOW:{}, NYSBA_ZERO:{}, NYSBA_LOST:{}",goodCountNysba, lowCountNysba, zeroCountNysba, lostCountNysba);
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
	public void updateRecordCounter(VVehicle tagDeviceObj) {
/*
		if (tagDeviceObj.getNystaTagStatus() == Constants.NTS_GOOD) {
			goodCountNysta++;
		}
		if (tagDeviceObj.getNystaTagStatus() == Constants.NTS_LOW) {
			lowCountNysta++;
		}
		if (tagDeviceObj.getNystaTagStatus() == Constants.NTS_ZERO) {
			zeroCountNysta++;
		}
		if (tagDeviceObj.getNystaTagStatus() == Constants.NTS_LOST) {
			lostCountNysta++;
		}
		if(tagDeviceObj.getNysbaTagStatus() == Constants.NBTS_GOOD) {
			goodCountNysba++;
		}
		if(tagDeviceObj.getNysbaTagStatus() == Constants.NBTS_LOW) {
			lowCountNysba++;
			
		}if(tagDeviceObj.getNysbaTagStatus() == Constants.NBTS_ZERO) {
			zeroCountNysba++;
			
		}if(tagDeviceObj.getNysbaTagStatus() == Constants.NBTS_LOST) {
			lostCountNysba++;
		}
		*/
		totalRecords++;
	}

	/**
	 * 
	 * @param fileHeaderDto
	 * @param file
	 * @param headerProperties 
	 * @return
	 */
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
	
	/**
	 * 
	 * @param TAGDeviceObj
	 * @return true/false
	 */
	
	private boolean validateTagRecord(VVehicle info) {
		boolean isValidRecord = false;
		// Validate device prefix
		
		if (validationUtils.validatePlateNumber(info.getPlate_number())) {
			if (validationUtils.validatePlateState(info.getPlate_state())) {
				//if(validationUtils.validatePlateType(info.getPlate_type())) {
						isValidRecord = true;
					//}
			}
		}
		return isValidRecord;
	}
	

 	private void populateRecordCountInMap() {
		log.info("Setting counters to recordCountMap..");
		recordCountMap.put(ICLPConstants.TOTAL, ICLPheaderinfo.getRecordCount()); 
		recordCountMap.put(ICLPConstants.SKIP, validationUtils.customFormatString(ICLPConstants.DEFAULT_COUNT,skipCount));
	}
	
	@Override
 	public void readExistingFileforICLP(File file) throws FileNotFoundException,IOException,Exception {
		
		String Filename = file.getName();
		log.info("File taken for process..{}",Filename);
		
		textFileReader.openFile(file);
		
		String currentline = textFileReader.readLine();	
		List<Integer> accIdList = new ArrayList<Integer>();
		while ((currentline = textFileReader.readLine()) != null)
		{
			if(currentline.length()==ICLPConstants.DATA_LENGTH_MIN) {//Get correct val Niranjan
			
			deviceno = currentline.substring(ICLPConstants.DEVICE_NUM_START,ICLPConstants.DEVICE_NUM_END);
			devicestatus = Integer.parseInt(currentline.substring(ICLPConstants.DEVICE_STATUS_START,ICLPConstants.DEVICE_STATUS_END));
			log.info("Device status taken from file is..{}",devicestatus);

			accountno = currentline.substring(ICLPConstants.DEVICE_ACC_NO_START,ICLPConstants.DEVICE_ACC_NO_END);
			String regex = "^0+(?!$)";
			accountno = accountno.replaceAll(regex, "");
			log.info("accountno taken from file is..{}",accountno);
		
			
			if(devicestatus == ICLPConstants.DS_GOOD || devicestatus == ICLPConstants.DS_LOW || (devicestatus == ICLPConstants.TS_INVALID && tpmsStatusFlag)) 
			{
				log.info("Device no taken from file is..{}",deviceno);

				int etc_account_id = iagDao.getetcaccountid(deviceno);

				if(!accIdList.contains(etc_account_id)){

					accIdList.add(etc_account_id);
					log.info("ETC_ACCOUNT_ID for Device No {} is {}",deviceno,etc_account_id);

					String licHomeAgency = masterDataCache.getAgencyById(iagDao.getAgencyId(etc_account_id)).getFilePrefix();
					log.info("licHomeAgency for ETC_ACCOUNT_ID {} is {}",etc_account_id,licHomeAgency);

					vehicleinfolist = iagDao.getPlateInfo(etc_account_id);
					log.info("Plate Info for etc_account_id {} is {}",etc_account_id,vehicleinfolist);
					//masterDataCache.getAgencyById(ITAGDeviceObj.getTagOwningAgency()).getFilePrefix();

					for(VVehicle vehicleinfo : vehicleinfolist)
					{
						vehicleinfo.setDeviceno(deviceno);
						vehicleinfo.setDevicePrefix(deviceno.substring(ICLPConstants.DEVICE_PREFIX_START,ICLPConstants.DEVICE_PREFIX_END));
						vehicleinfo.setSerialNo(deviceno.substring(ICLPConstants.DEVICE_SERIAL_START,ICLPConstants.DEVICE_SERIAL_END));
						vehicleinfo.setAccountNo(accountno);
						vehicleinfo.setLicHomeAgency(licHomeAgency);

						plateinfolist.add(vehicleinfo);	
					}

					log.info("Plate Info added in list for etc_account_id {} is {}",etc_account_id,plateinfolist);
				}

			}
			else 
			{
				skipCount++;
				log.info("Record skipped for device no...{}",deviceno);
			}
			}else {
				log.info("Line size is greater than expected...{}",currentline);
			}
		
		}
		textFileReader.closeFile();
 		
 	}
	
	
}
