package com.conduent.tpms.recon.dao;

import java.io.File;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import com.conduent.tpms.recon.dto.FileCreationParameters;
import com.conduent.tpms.recon.dto.IncrTagStatusRecord;
import com.conduent.tpms.recon.dto.ReconciliationDto;
import com.conduent.tpms.recon.dto.XferFileInfoDto;
import com.conduent.tpms.recon.model.TAGDevice;

/**
 * Interface for DAO operations
 * 
 * @author taniyan
 *
 */
public interface ReconDao
{

	FileCreationParameters getMappingConfigurationDetails(FileCreationParameters fielDto);
	
	public List<TAGDevice> getDeviceInfofromTTagStatusAllSorted(int agencyId, String genType);
	
	public List<TAGDevice> getDevieListFromTTagAllSorted1(int agencyId, String genType, List<IncrTagStatusRecord> devicenoList);
	
	public List<IncrTagStatusRecord> getDeviceNoFromTInrTagStatusAllSorted(String genType);
	
	String getLastDwnldTS();
	
	
	public List<ReconciliationDto> getReconData();

	String getXferFileName(Long xferControlId);

	Integer getFileSequence();

	String getDevicePrefix(String accountAgencyId);

	void insertIntoDailyRTStatus(Long externalFileId, File file, long totalRecords,
			long intermidiateCount, long skipCount);

	void updateDeleteFlag(ReconciliationDto recon);


	public List<XferFileInfoDto> getXferFileInfo(LocalDateTime updateTs) throws ParseException;

	public List<ReconciliationDto> getReconciliationInfo(Long externalFileId, LocalDateTime updateTs) throws ParseException;

	public LocalDateTime getUpdateTs();

	
}
