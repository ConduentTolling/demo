package com.conduent.tpms.qatp.dao;

import com.conduent.tpms.qatp.dto.FileParserParameters;
import com.conduent.tpms.qatp.model.ReconciliationCheckPoint;
import com.conduent.tpms.qatp.model.TagDeviceDetails;
import com.conduent.tpms.qatp.model.XferControl;

public interface TQatpMappingDao
{
	public FileParserParameters getMappingConfigurationDetails( FileParserParameters fielDto);
	public Long getAgencyId(String  filePrefix);
	public boolean checkIfFileProcessedAlready(String fileName);
	public boolean checkIfTxExternRefNoIsAlreadyUsed(String txExternRefNo);
	public boolean checkIfTxExternRefNoIsAlreadyUsedWithSameExtFileId(String txExternRefNo, Long extFileId);
	public Long insertReconciliationCheckPoint(ReconciliationCheckPoint reconciliationCheckPoint);
	public Long getLastProcessedRecordCount(Long agencyId);
	public int insertTagDeviceDetails(TagDeviceDetails tagDetails);
	public XferControl checkFileDownloaded(String name);
	public ReconciliationCheckPoint getReconsilationCheckPoint(String fileName);
	public void updateRecordCount(ReconciliationCheckPoint reconciliationCheckPoint);
	public boolean checkForDuplicateFileNum(String value);
	public Integer getLastSuccesfulProcessedFileSeqNum();
	public long getPrevXferControlId(String name);
	public String getLastProcessedExternFileNumber(Long extFileId);
}