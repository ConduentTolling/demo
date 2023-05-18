package com.conduent.tpms.iag.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.conduent.tpms.iag.exception.BucketFullException;
import com.conduent.tpms.iag.exception.EmptyLineException;
import com.conduent.tpms.iag.exception.InvalidFileNameException;
import com.conduent.tpms.iag.exception.InvalidRecordException;
import com.conduent.tpms.iag.exception.InvlidFileHeaderException;
import com.conduent.tpms.iag.model.DeviceFileWrapper;

public interface TransactionDao {

	void preparFileAndUploadToExtern(File newFile, File oldFile ,int agencySequence) throws InvalidFileNameException, InvlidFileHeaderException, EmptyLineException, InvalidRecordException;

	void preparFileAndUploadToExtern1(File newFile, File oldFile) throws BucketFullException, Exception;

	void uploadProcess(DeviceFileWrapper deviceFileWrapper) throws FileNotFoundException,IOException;

}
