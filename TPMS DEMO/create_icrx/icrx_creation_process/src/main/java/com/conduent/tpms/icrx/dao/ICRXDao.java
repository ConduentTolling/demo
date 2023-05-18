package com.conduent.tpms.icrx.dao;

import java.util.List;

import com.conduent.tpms.icrx.model.FileCreationParameters;
import com.conduent.tpms.icrx.model.FileStats;
import com.conduent.tpms.icrx.model.TxDetailRecord;


/**
 * Interface for DAO operations
 * 
 * @author urvashic
 *
 */
public interface ICRXDao
{
	public List<FileStats> getXferIdListForICTXFiles(String fileType, String string);

	public long getHCountForICTXFile(long xferControlId);
	
	public long getECountForICTXFile(long xferControlId);
	
	public long getECountForICTXFileFromICRXRecon(long xferControlId);
	
	public FileCreationParameters getMappingConfigurationDetails(FileCreationParameters fielDto);

	public List<TxDetailRecord> getTxRecordList(long xferControlId, long plazaAgencyId);
	
	public String getPostStatusForId(String txCodeId);

	public String getPlanNameForPlanId(String planTypeId);

	public void updateICRXDetailsIntoFileStats(FileStats fileStatsRecord);

	public String getDupExternRefForRJDPTxn(String transactionRowId);
	
}
