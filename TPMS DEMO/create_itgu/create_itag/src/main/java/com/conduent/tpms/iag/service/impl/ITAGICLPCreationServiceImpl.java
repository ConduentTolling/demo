package com.conduent.tpms.iag.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.dto.ITAGICLPHeaderDto;
import com.conduent.tpms.iag.model.ITAGDevice;
import com.conduent.tpms.iag.service.ITAGICLPCreationService;
import com.conduent.tpms.iag.utility.FileWriteOperation;
import com.conduent.tpms.iag.utility.IAGValidationUtils;

@Service
public class ITAGICLPCreationServiceImpl implements ITAGICLPCreationService {
	
	private static final Logger log = LoggerFactory.getLogger(ITAGICLPCreationServiceImpl.class);
	@Autowired
	public IAGValidationUtils validationUtils;
	
	@Autowired
	FileWriteOperation fileWriteOperation;
	/**
	 * 
	 * @param iTAGDeviceObj
	 * @param itagiclpfile
	 * @param filewriterItagIclp
	 */
	@Override
	public void writeContentToItagIclpFile(ITAGDevice iTAGDeviceObj, File itagiclpfile,
			FileWriter filewriterItagIclp) {

		StringBuilder row = new StringBuilder();
		try {
			row.append(iTAGDeviceObj.getDeviceNo()).append(iTAGDeviceObj.getItagTagStatus())
					.append(Constants.SUBACCOUNT_ID_DEFAULT);
			filewriterItagIclp.write(row.toString() + "\n");
			log.info("Record added to ITAGICLP file: {}", row.toString());
		} catch (IOException e) {
			log.info("Exception caught while adding record to ITAGICLP file: {}", row.toString());
			e.printStackTrace();
		}
	}
	
	@Override
	public void overwriteFileHeader(ITAGICLPHeaderDto fileHeaderDto, File file) {

		long recordCount = fileWriteOperation.getRecordCountFromFile(file.getName(), file.getAbsolutePath());
		String tmpHeader = Constants.ITAGICLP.concat(Constants.HOME_AGENCY_ID).concat(validationUtils.customFormatString(Constants.DEFAULT_COUNT, recordCount)).concat(fileHeaderDto.getFileDateTime());
				

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
			System.out.println("Contents of the file: " + fileContents);
			
			sc.close();
			String newLine = tmpHeader.toString();

			fileContents = fileContents.replaceAll(oldLine, newLine);
			FileWriter writer = new FileWriter(filePath);
			log.info("new data: {}", fileContents);
			writer.append(fileContents);
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
}
