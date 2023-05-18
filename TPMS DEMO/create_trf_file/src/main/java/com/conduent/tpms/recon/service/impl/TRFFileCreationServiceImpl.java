package com.conduent.tpms.recon.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.recon.constants.Constants;
import com.conduent.tpms.recon.dao.ReconDao;
import com.conduent.tpms.recon.dto.MappingInfoDto;
import com.conduent.tpms.recon.dto.ReconciliationDto;
import com.conduent.tpms.recon.dto.XferFileInfoDto;
import com.conduent.tpms.recon.model.TAGDevice;
import com.conduent.tpms.recon.service.TRFFileCreationSevice;

@Service
@Component
public class TRFFileCreationServiceImpl extends TRFFileCreationSevice {
	
	private static final Logger log = LoggerFactory.getLogger(TRFFileCreationServiceImpl.class);
	
	String tagFileDest;
	String statFileDest;
	
	
	@Autowired
	ReconDao reconDao;
	
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
		setIsTrailerPresentInFile(true);
	}
	
	
	//@Override
	public void createTRFFile(List<XferFileInfoDto> info, LocalDateTime updateTs) throws IOException, ParseException {
		try {
			tagFileDest = parentDest.concat("//").concat(Constants.TRF_FILES); 
			
			if (!info.isEmpty()) {
				// Creating file 
				List<MappingInfoDto> nameProperties = getConfigurationMapping().getFileNameMappingInfo();
				
				Long externalFileId = null;
				for (XferFileInfoDto fileInfo : info) {
			    externalFileId=	fileInfo.getXferControlId();
				//XferFileName = iagDao.getXferFileName(externalFileId);
				String XferFileName = fileInfo.getXferFileName();
				XferFileName = XferFileName.replace(".MTX", "_");
				
				
				fileName = buildFileSection1(nameProperties, null);
				File file = new File(tagFileDest, XferFileName+fileName);
				log.info("File Name : {}",file);
				log.info("#### EXTER_FILE_ID :=> {} for File_Name :=> {} ####",externalFileId, file.getName());
				FileWriter filewriter = new FileWriter(file);
				
				//Building TRF file header
				List<MappingInfoDto> headerProperties = getConfigurationMapping().getHeaderMappingInfoList();
				String header = buildFileSection1(headerProperties, null);

				log.info("Writing header to TRF file: {}",header);
				filewriter.write(header + "\n");

				//Build TRF file details
				List<MappingInfoDto> detailRecordProperties = getConfigurationMapping().getDetailRecMappingInfo();
				
				List<ReconciliationDto> reconList = reconDao.getReconciliationInfo(externalFileId, updateTs);
				log.info(" #### Reconcilition List: {} ####",reconList);
				if(reconList!=null)
				{
					for (ReconciliationDto reconciliation : reconList) {
						log.info(" #### LaneTxId for processing record: {} ####",reconciliation);
						boolean isValidRecord = validateDetailRecord(reconciliation);
						if (isValidRecord) {
							String record = buildFileSection1(detailRecordProperties, reconciliation);
							writeContentToFile(filewriter, record);
							// updateRecordCounter(reconciliation);
							if (reconciliation.getIsFinalState().equals("T")) {
								reconDao.updateDeleteFlag(reconciliation);
							}
							if (reconciliation.getIsFinalState().equals("F")) {
								intermidiateCount++;
							}
							totalRecords++;
							log.info("### Records added to file ### {}", totalRecords);
						} else {
							skipCount++;
							log.info("Record not added to file: {}", reconciliation.toString());
							log.info("### Skip Count ### {}", skipCount);
						}

					}
				}
				//Build TRF file trailer
				List<MappingInfoDto> trailerProperties = getConfigurationMapping().getTrailerMappingInfoDto();
				String trailer = buildFileSection1(trailerProperties, null);
				log.info("Writing trailer to TRF file: {}",trailer);
				filewriter.write(trailer + "\n");
				
					headerinfo.setRecordCount(String.valueOf(totalRecords));
					//headerinfo.setRecordCount(String.valueOf(info.size()));
					log.info("Total Record added ..{}",headerinfo.getRecordCount());
					filewriter.flush();
					filewriter.close();
				    //Overwriting header of TAG file
					overwriteFileHeader(file, headerProperties);
					populateRecordCountInMap();
					//summaryFileCreationService.buildSummaryFile(recordCountMap,Constants.ITGU, statFileDest,fileName);
					
					// insert into t_daily_rt_stats
					reconDao.insertIntoDailyRTStatus(externalFileId, file, totalRecords, intermidiateCount, skipCount);
					
					skipCount = 0;
					totalRecords = 0;
					intermidiateCount = 0;
				}
				
			}
		}finally {
			skipCount = 0;
			totalRecords = 0;
			intermidiateCount = 0;
			}
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
	public void updateRecordCounter(TAGDevice tagDeviceObj) {

		if (tagDeviceObj.getItagTagStatus() == Constants.TS_GOOD) {
			tsGood++;
		}
		if (tagDeviceObj.getItagTagStatus() == Constants.TS_LOW) {
			tsLow++;
		}
		if (tagDeviceObj.getItagTagStatus() == Constants.TS_INVALID) {
			tsInvalid++;
		}
		if (tagDeviceObj.getItagTagStatus() == Constants.TS_LOST) {
			tsLost++;
		}
		
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
		String newLine = buildFileSection1(headerProperties, null);
		
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
			log.info("Replacing TRF file {} header",file.getName());
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
	
	private boolean validateDetailRecord(ReconciliationDto reconciliation) {
		boolean isValidRecord = true;

			if (reconciliation.getCreateRt().equals("F")|| reconciliation.getReconTxStatus()==0 
					|| validateDevice(reconciliation.getTxExternRefNo())==true) {

				isValidRecord = false;
			}
		return isValidRecord;
	}
	
	public boolean validateDevice(String value)

	{
		String matcher_String = "\\s{" + value.length() + "}|[*|0]{" + value.length() + "}";
		if (value.matches(matcher_String))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

 	private void populateRecordCountInMap() {
		log.info("Setting counters to recordCountMap..");
		recordCountMap.put(Constants.TOTAL, headerinfo.getRecordCount()); 
		recordCountMap.put(Constants.SKIP, validationUtils.customFormatString(Constants.DEFAULT_COUNT,skipCount));
	}
}
