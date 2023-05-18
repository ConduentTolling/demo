package com.conduent.tpms.iag.service;

import java.io.File;

import com.conduent.tpms.iag.model.FileDetails;

public interface IAGLoadFileService {

	public boolean validateHeaderLineUsingICDRule(String headerIAGLine);
	public boolean validateFileDetailLineUsingICDRule(String detailsIAGLine);
	public boolean validateFileName(File file, FileDetails fileDetailsVO); 
	public void createACKFile(String fileName) throws Exception;
	public boolean insertFileCheckppointDetails(FileDetails fileDetailsVO);
}
