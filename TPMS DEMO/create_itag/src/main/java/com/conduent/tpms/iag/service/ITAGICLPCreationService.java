package com.conduent.tpms.iag.service;

import java.io.File;
import java.io.FileWriter;

import com.conduent.tpms.iag.dto.ITAGICLPHeaderDto;
import com.conduent.tpms.iag.model.ITAGDevice;

public interface ITAGICLPCreationService {
	

	void writeContentToItagIclpFile(ITAGDevice iTAGDeviceObj, File itagiclpfile, FileWriter filewriterItagIclp);

	void overwriteFileHeader(ITAGICLPHeaderDto fileHeaderDto, File file);

}
