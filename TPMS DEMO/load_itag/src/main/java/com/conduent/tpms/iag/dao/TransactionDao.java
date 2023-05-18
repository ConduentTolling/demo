package com.conduent.tpms.iag.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.conduent.tpms.iag.model.DeviceFileWrapper;

public interface TransactionDao {

	void uploadProcess(DeviceFileWrapper deviceFileWrapper) throws FileNotFoundException, IOException;
	void preparFileAndUploadToExtern(File newFile, File awaytagOldFile, int agencySequence);

}
