package com.conduent.tpms.recon.daoImpl;

import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.recon.config.LoadJpaQueries;
import com.conduent.tpms.recon.constants.Constants;
import com.conduent.tpms.recon.dao.ReconDao;
import com.conduent.tpms.recon.dto.FileCreationParameters;
import com.conduent.tpms.recon.dto.FileInfoDto;
import com.conduent.tpms.recon.dto.IncrTagStatusRecord;
import com.conduent.tpms.recon.dto.MappingInfoDto;
import com.conduent.tpms.recon.dto.ReconciliationDto;
import com.conduent.tpms.recon.dto.UpdateReconDto;
import com.conduent.tpms.recon.dto.XferFileInfoDto;
import com.conduent.tpms.recon.model.TAGDevice;

/**
 * Implementation class for IAGDao
 * 
 * @author shrikantm3
 *
 */	
@Repository
public class ReconDaoImpl implements ReconDao {
	
	private static final Logger log = LoggerFactory.getLogger(ReconDaoImpl.class);
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Autowired
	private JdbcTemplate  jdbcTemplate;
	
	@Autowired 
	private TimeZoneConv timeZoneConv;
	
	private List<TAGDevice> listOfDeviceInfo = new ArrayList<>();
	
	@Override
	public FileCreationParameters getMappingConfigurationDetails(FileCreationParameters fileDto) {

		log.info("Getting mapping configuration from table..");

		FileCreationParameters fileDetails = new FileCreationParameters();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryFile = LoadJpaQueries.getQueryById("GET_OUTPUT_FILE_CONFIGURATION_INFO");

		paramSource.addValue(Constants.FILE_TYPE, fileDto.getFileType());
		paramSource.addValue(Constants.AGENCY_ID, fileDto.getAgencyId());
		paramSource.addValue(Constants.FILE_FORMAT, Constants.FIXED);

		FileInfoDto info = namedJdbcTemplate.queryForObject(queryFile, paramSource,
				BeanPropertyRowMapper.newInstance(FileInfoDto.class));
		fileDetails.setFileInfoDto(info);
		System.out.println(fileDetails.getFileInfoDto().toString());
		System.out.println(info.toString());

		String queryRules = LoadJpaQueries.getQueryById("GET_OUTPUT_FIELD_VALIDATION_DETAILS");

		List<MappingInfoDto> nameMappingDetails = namedJdbcTemplate.query(queryRules, paramSource,
				BeanPropertyRowMapper.newInstance(MappingInfoDto.class));
		//getMinMaxListValues(nameMappingDetails);

		List<MappingInfoDto> fileNameMappingDetails = nameMappingDetails.stream()
				.filter(f -> f.getFieldType().equals("FILENAME")).collect(Collectors.toList());
		System.out.println(fileNameMappingDetails.toString());
		fileDetails.setFileNameMappingInfo(fileNameMappingDetails);

		List<MappingInfoDto> headerMappingDetails = nameMappingDetails.stream()
				.filter(f -> f.getFieldType().equals("HEADER")).collect(Collectors.toList());
		System.out.println(headerMappingDetails.toString());
		fileDetails.setHeaderMappingInfoList(headerMappingDetails);

		List<MappingInfoDto> detailMappingDetails = nameMappingDetails.stream()
				.filter(f -> f.getFieldType().equals("DETAIL")).collect(Collectors.toList());
		System.out.println(detailMappingDetails.toString());
		fileDetails.setDetailRecMappingInfo(detailMappingDetails);
		
		List<MappingInfoDto> trailerMappingDetails = nameMappingDetails.stream()
				.filter(f -> f.getFieldType().equals("TRAILER")).collect(Collectors.toList());
		System.out.println(trailerMappingDetails.toString());
		fileDetails.setTrailerMappingInfoDto(trailerMappingDetails);

		log.info("Collected mapping configuration from table..");
		
		return fileDetails;

	}

	@Override
	public List<TAGDevice> getDeviceInfofromTTagStatusAllSorted(int agencyId, String genType) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.GEN_TYPE, genType);
		String queryRules = LoadJpaQueries.getQueryById("LOAD_DEVICES_FROM_T_TAG_STATUS_ALLSORTED");
		log.info("Agency info fetched from T_Agency table successfully.");
		
		return namedJdbcTemplate.query(queryRules, paramSource, BeanPropertyRowMapper.newInstance(TAGDevice.class));

	}
	
	@Override
	public String getLastDwnldTS() {
		String queryRules = LoadJpaQueries.getQueryById("SELECT_LAST_DOWNLOAD_TS");
		log.info("File Download TS fetched successfully.");
		String dwnldTS = (String) jdbcTemplate.queryForObject(queryRules, String.class);
		return dwnldTS;

	}

	@Override
	public List<TAGDevice> getDevieListFromTTagAllSorted1(int agencyId, String genType,List<IncrTagStatusRecord> devicenoList) 
	{
			TAGDevice deviceInfo = null;
			for(IncrTagStatusRecord deviceNoInfo :devicenoList) 
			{
				
				MapSqlParameterSource paramSource = new MapSqlParameterSource();
				paramSource.addValue(Constants.GEN_TYPE, genType);
				paramSource.addValue(Constants.device_no, deviceNoInfo.getDeviceNo());
				
				String queryRules = LoadJpaQueries.getQueryById("LOAD_DEVICES_FROM_T_TAG_STATUS_ALLSORTED");
				
				deviceInfo = namedJdbcTemplate.queryForObject(queryRules, paramSource, BeanPropertyRowMapper.newInstance(TAGDevice.class));
				log.info("Agency info fetched from T_TAG_STATUS_ALLSORTED table successfully..{}",deviceInfo);
				
				listOfDeviceInfo.add(deviceInfo);
			}
		return listOfDeviceInfo;
	}
	
	@Override
	public List<IncrTagStatusRecord> getDeviceNoFromTInrTagStatusAllSorted(String genType) {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.GEN_TYPE, genType);
		String queryRules = LoadJpaQueries.getQueryById("LOAD_DEVICE_NO_FROM_T_INCR_TAG_STATUS_ALLSORTED");
		log.info("Agency info fetched from T_INCR_TAG_STATUS_ALLSORTED table successfully.");
		
		return namedJdbcTemplate.query(queryRules, paramSource, BeanPropertyRowMapper.newInstance(IncrTagStatusRecord.class));

	}

	@Override
	public List<ReconciliationDto> getReconData() {
		
		String query = LoadJpaQueries.getQueryById("SELECT_T_RECONCILIATION");

		List<ReconciliationDto> list = jdbcTemplate.query(query,
				new BeanPropertyRowMapper<ReconciliationDto>(ReconciliationDto.class));
		
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public String getXferFileName(Long xferControlId) {

		String queryRules = LoadJpaQueries.getQueryById("GET_XFER_FILE_NAME");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("XFER_CONTROL_ID", xferControlId);
		String fileName = namedJdbcTemplate.queryForObject(queryRules,paramSource, String.class);
		return fileName;
	
	}

	@Override
	public Integer getFileSequence() {
		
		String queryRules = LoadJpaQueries.getQueryById("GET_FILE_SEQUENCE");
		Integer fileSequence =  jdbcTemplate.queryForObject(queryRules, Integer.class);
		return fileSequence;
	}

	@Override
	public String getDevicePrefix(String accountAgencyId) {

		String queryRules = LoadJpaQueries.getQueryById("GET_DEVICE_PREFIX");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("AGENCY_ID", accountAgencyId);
		String devicePrefix = namedJdbcTemplate.queryForObject(queryRules,paramSource, String.class);
		return devicePrefix;
	}

	@Override
	public void insertIntoDailyRTStatus(Long externalFileId, File file, long totalRecords,
			long intermidiateCount, long skipCount) {
		// TODO Auto-generated method stub
		
		try {
			String query = LoadJpaQueries.getQueryById("INSERT_T_DAILY_RT_STATS");
			MapSqlParameterSource paramSource = new MapSqlParameterSource();

			paramSource.addValue("RT_CREATION_DATE", new Date());
			paramSource.addValue("EXTERN_FILE_ID", externalFileId);
			paramSource.addValue("RT_FILE_NAME", file.getName());
			paramSource.addValue("FINAL_COUNT", totalRecords);
			paramSource.addValue("INTERIM_COUNT", intermidiateCount);
			paramSource.addValue("SKIPPED_COUNT", skipCount);
			paramSource.addValue("UPDATE_TS", LocalDateTime.now());

			namedJdbcTemplate.update(query, paramSource);
			log.info("Successfully data inserted into T_DAILY_RT_STATS Table for File Name {}", file);
		} catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
			log.info("Record not inserted into T_DAILY_RT_STATS Table properly" + ex.getMessage());
		}
		
	}

	@Override
	public void updateDeleteFlag(ReconciliationDto recon) {
		// TODO Auto-generated method stub
		String query = LoadJpaQueries.getQueryById("UPDATE_RECONCILIATION");
		UpdateReconDto updateReconDto = new UpdateReconDto();
		updateReconDto.setDeleteFlag("T");
		updateReconDto.setLaneTxId(recon.getLaneTxId());
		
		jdbcTemplate.update(query, updateReconDto.getDeleteFlag(), updateReconDto.getLaneTxId());
		
		log.info("##### Delete_Flag update in Reconciliation Table Successfully #####");
	}

	@Override
	public List<XferFileInfoDto> getXferFileInfo(LocalDateTime updateTs) throws ParseException {

		String query = LoadJpaQueries.getQueryById("GET_XFER_FILE_INFO");

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		Timestamp ts = Timestamp.valueOf(updateTs);
		paramSource.addValue("UPDATE_TS", convertToDateFormat(ts.toString()));
		List<XferFileInfoDto> list = namedJdbcTemplate.query(query,paramSource,
				BeanPropertyRowMapper.newInstance(XferFileInfoDto.class));
		
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public List<ReconciliationDto> getReconciliationInfo(Long externalFileId, LocalDateTime updateTs) throws ParseException {

		String query = LoadJpaQueries.getQueryById("GET_RECONCILIATION_INFO");

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		Timestamp ts = Timestamp.valueOf(updateTs);
		
		paramSource.addValue("EXTERN_FILE_ID", externalFileId);
		paramSource.addValue("UPDATE_TS", convertToDateFormat(ts.toString()));
		List<ReconciliationDto> list = namedJdbcTemplate.query(query,paramSource,
				new BeanPropertyRowMapper<ReconciliationDto>(ReconciliationDto.class));
		
		if (!list.isEmpty()) {
			return list;
		}
		return null;
	}

	private Object convertToDateFormat(String updateTs) throws ParseException {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SS");
        SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yy hh:mm:ss.SS");
        Date convertedDate = format1.parse(updateTs);
        String convertedDate2 = format2. format(convertedDate).toUpperCase();
        System. out. println(convertedDate2);
		return convertedDate2;
	}

	@Override
	public LocalDateTime getUpdateTs() {
		// TODO Auto-generated method stub
		String queryRules = LoadJpaQueries.getQueryById("GET_UPDATE_TS");
		LocalDateTime updateTs =  jdbcTemplate.queryForObject(queryRules, LocalDateTime.class);
		return updateTs;
	}
	

}

