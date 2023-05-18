package com.conduent.tpms.iag.parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conduent.tpms.iag.constants.Constants;
import com.conduent.tpms.iag.dao.TIagDao;
import com.conduent.tpms.iag.dto.FileDetailsDto;
import com.conduent.tpms.iag.dto.MappingInfoDto;
import com.conduent.tpms.iag.utility.GenericValidation;

@Component
public class Parser {

	@Autowired
	private TIagDao qatpDao;

	@Autowired
	private GenericValidation genericValidation;

	private FileDetailsDto fileDetailsDto;

	public void intialize() {
		// TODO Auto-generated method stub
	}

	public List<File> retrieveFile(FileDetailsDto fileDetailsDto) throws IOException {
		// Implementation-Retrieve List of Filename from the directory
		//Add Null check
		//Check exception scenario
		try (Stream<Path> paths = Files.list(Paths.get(fileDetailsDto.getFileInfoDto().getSrcDir()));) {
			return paths.map(Path::toFile).collect(Collectors.toList());
		}
	}

	public void process() throws IOException {
		// Note: All the var in base class and it will be intialize by dervied class

		boolean isValid = false;

		// intialize
		intialize();

		// loading Mapping Info
		FileDetailsDto fileDetailsDto = loadMappingInfo(getFileDetailsDto());

		// Retrieve file info from the src directory
		Optional<List<File>> tempFileList = Optional.ofNullable(retrieveFile(fileDetailsDto));

		if (tempFileList.isPresent()) {

			// For each file
			for (File file : tempFileList.get()) {

				// Validate Filename
				FileDetailsDto tempfileDetailsDto = validateFileName(fileDetailsDto, file.getName());
				isValid = tempfileDetailsDto.getIsFileNameValid();

				if (isValid) {
					// Validate Download Status
					/*
					 * tempfileDetailsDto = checkFileStatus(fileDetailsDto, file.getName()); isValid
					 * = tempfileDetailsDto.getIsFileDownloaded();
					 */

					/*
					 * if (isValid) { // Common-validateHeader-If valid then process else create ACK
					 * file tempfileDetailsDto = validateHeader(fileDetailsDto); isValid =
					 * tempfileDetailsDto.getIsHeaderValid(); if (isValid) { // for each line-Shoudl
					 * we read line here or inside the method // Why??To avoid twice iteration of
					 * single line/tx from the file // Specific-ParseAndValidateDetailRecord
					 * 
					 * } else { // createAck-HeaderInvalid } }
					 * 
					 */
					transferFile(file, tempfileDetailsDto.getFileInfoDto().getProcDir());
				}

			} // for each Loop-end
		}
		// tearDown
		tearDown();
	}

	public FileDetailsDto loadMappingInfo(FileDetailsDto fileDetailsDto) {
		// Loading mapping info
		return qatpDao.getMappingConfigurationDetails(fileDetailsDto);
	}

	public FileDetailsDto validateFileName(FileDetailsDto fileDetailsDto, String fileName) {
		boolean isValid = true;
		fileDetailsDto.setFileName(fileName);
		List<MappingInfoDto> fileNameMappingInfo = fileDetailsDto.getFileNameMappingInfo();
		String value = null;
		if (fileDetailsDto.getFileNameLength() < fileName.length()) {
			fileDetailsDto.setIsFileNameValid(false);
			return fileDetailsDto;
		}
		for (MappingInfoDto fMapping : fileNameMappingInfo) {
			// TODO: Inform Mayuri
			if (isValid) {
				//
				value = fileName.substring(fMapping.getStartPosition().intValue(),
						fMapping.getEndPosition().intValue());
				try {
					isValid = genericValidation.doValidation(fMapping, value);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if ("Y".equals(fMapping.getIsPojoValue())) {
					// Check if we require to set in the POJO
					// Inform to Mayuri
				}
			} else {
				break;
			}
		}
		fileDetailsDto.setIsFileNameValid(isValid);
		return fileDetailsDto;
	}

	public FileDetailsDto validateHeader(FileDetailsDto fileDetailsDto) {
		// TODO Implementation
		return fileDetailsDto;
	}

	public FileDetailsDto checkFileStatus(FileDetailsDto fileDetailsDto, String fileName) {
		// TODO Implementation
		return fileDetailsDto;
	}

	public FileDetailsDto getFileDetailsDto() {
		return fileDetailsDto;
	}

	public void setFileDetailsDto(FileDetailsDto fileDetailsDto) {
		this.fileDetailsDto = fileDetailsDto;
	}

	// Check with Team
	public void parseAndValidateDetailRecord() {
		// TODO Implementation
	}

	public static Boolean transferFile(File file, String targetDir) {
		try {
			Files.move(Paths.get(file.getAbsolutePath()), Paths.get(targetDir + "\\" + file.getName()));
		} catch (IOException e) {
			// File transfer fail
			return false;
		}
		return true;
	}

	public void loadStaticData() {
		// TODO Implementation:Priority
		
	}

	public void tearDown() {
		// TODO Auto-generated method stub
	}

}
