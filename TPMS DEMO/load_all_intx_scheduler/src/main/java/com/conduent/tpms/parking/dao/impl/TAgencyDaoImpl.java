package com.conduent.tpms.parking.dao.impl;



import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.parking.config.LoadJpaQueries;
import com.conduent.tpms.parking.constants.Constants;
import com.conduent.tpms.parking.constants.IITCConstants;
import com.conduent.tpms.parking.dao.TAgencyDao;
import com.conduent.tpms.parking.dto.AckFileInfoDto;
import com.conduent.tpms.parking.dto.FileInfoDto;
import com.conduent.tpms.parking.model.XferControl;

@Repository
public class TAgencyDaoImpl implements TAgencyDao {

	private static final Logger dao_log = LoggerFactory.getLogger(TAgencyDaoImpl.class);


	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public FileInfoDto getMappingConfigurationDetails(String fileType) {

		FileInfoDto info =null;
		try 
		{
			dao_log.info("Getting mapping configuration from table for file type..{}",fileType);

			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			String queryFile = LoadJpaQueries.getQueryById("GET_FILE_INFO_CONFIGURATION");

			paramSource.addValue(IITCConstants.FILE_TYPE, fileType);
			paramSource.addValue(IITCConstants.AGENCY_ID, Constants.MTA_AGENCY_ID);
			paramSource.addValue(IITCConstants.FILE_FORMAT, Constants.FIXED);

			 info = namedJdbcTemplate.queryForObject(queryFile, paramSource,
					BeanPropertyRowMapper.newInstance(FileInfoDto.class));

			 dao_log.info("Mapping done for file Type..{}",fileType);
		}
		catch(Exception e) 
		{
			dao_log.info("Exception caught...{}",e);
		}
		return info;
	}
	
	@Override
	public void insertAckDetails(AckFileInfoDto ackFileInfoDto) {

		dao_log.info("Inserting Ack file details into ack table..");

		String query = LoadJpaQueries.getQueryById("INSERT_FILE_ACK_INTO_ACK_TABLE");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(IITCConstants.ACK_FILE_NAME, ackFileInfoDto.getAckFileName());
		paramSource.addValue(IITCConstants.ACK_FILE_DATE, ackFileInfoDto.getAckFileDate());
		paramSource.addValue(IITCConstants.ACK_FILE_TIME, ackFileInfoDto.getAckFileTime());
		paramSource.addValue(IITCConstants.ACK_FILE_STATUS, Integer.parseInt(ackFileInfoDto.getAckFileStatus()));
		paramSource.addValue(IITCConstants.TRX_FILE_NAME, ackFileInfoDto.getTrxFileName());
		paramSource.addValue(IITCConstants.RECON_FILE_NAME, ackFileInfoDto.getReconFileName());
		paramSource.addValue(IITCConstants.FILE_TYPE, ackFileInfoDto.getFileType());
		paramSource.addValue(IITCConstants.FROM_AGENCY, ackFileInfoDto.getToAgency());
		paramSource.addValue(IITCConstants.TO_AGENCY, ackFileInfoDto.getFromAgency());
		paramSource.addValue(IITCConstants.EXTERN_FILE_ID, ackFileInfoDto.getExternFileId());
		paramSource.addValue(IITCConstants.ATP_FILE_ID, ackFileInfoDto.getAtpFileId());
		int id = namedJdbcTemplate.update(query, paramSource);

		dao_log.info("Inserted Ack file {} details into ack table", ackFileInfoDto.getAckFileName());

	}
	
	@Override
	public XferControl checkFileDownloaded(String fileName) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String queryToCheckFile = "SELECT XFER_CONTROL_ID, XFER_FILE_ID, XFER_FILE_NAME, DATE_CREATED, TIME_CREATED, FILE_SIZE, FILE_CHECKSUM, NUM_RECS, HASH_TOTAL, XFER_PATH, XFER_XMIT_STATUS, XFER_RETRY, UPDATE_TS, FILE_TYPE  FROM T_XFER_CONTROL WHERE XFER_FILE_NAME='"+fileName+"' AND XFER_XMIT_STATUS ='C'";
		List<XferControl> listOfObj = namedJdbcTemplate.query(queryToCheckFile, paramSource,BeanPropertyRowMapper.newInstance(XferControl.class));
		if(listOfObj.size()>0) {
			dao_log.info("File present in XFER_CONTROL table : {}",listOfObj.get(0));
			return listOfObj.get(0);
		}
		return null;
	}
	
//	@Override
	public List<String> getAllAwayAgencies(String fileType) {

		List<String> info =null;
		List <String> info1 = new LinkedList<>();
		
		try 
		{
			dao_log.info("Getting away agencies list from table for file type..{}",fileType);

			String queryFile = LoadJpaQueries.getQueryById("GET_ALL_AWAY_AGENCIES");

			info1 =jdbcTemplate.queryForList(queryFile,String.class);
		}
		catch(Exception e) 
		{
			dao_log.info("Exception caught...{}",e);
			
		}
		return info1;
	}
	
}
