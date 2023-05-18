package com.conduent.tpms.iag.service;

import java.util.Map;

public interface SummaryFileCreationService {

	public void buildSummaryFile(Map<String,String> recordCountMap, String inputFileType, String destPath,String filename);

}
