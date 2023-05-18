package com.conduent.tpms.inrx.dao;

import java.util.List;

import com.conduent.tpms.inrx.model.FileCreationParameters;
import com.conduent.tpms.inrx.model.FileStats;
import com.conduent.tpms.inrx.model.TxDetailRecord;


/**
 * Interface for DAO operations
 * 
 * @author petetid
 *
 */
public interface INRXDao
{
	public List<FileStats> getFileControlIdIdListForINTXFiles(String fileType, String string);

	public long getHCountForINTXFile(long fileControlId);
	
	public long getECountForINTXFile(long fileControlId);
	
	public FileCreationParameters getMappingConfigurationDetails(FileCreationParameters fielDto);

	public List<TxDetailRecord> getTxRecordList(long xferControlId, long plazaAgencyId);
	
	public String getPostStatusForId(String txCodeId);

	public String getPlanNameForPlanId(String planTypeId);

	public void updateINRXDetailsIntoFileStats(FileStats fileStatsRecord);

	
	
}
