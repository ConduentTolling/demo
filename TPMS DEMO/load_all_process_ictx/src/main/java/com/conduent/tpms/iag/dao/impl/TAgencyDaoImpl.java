package com.conduent.tpms.iag.dao.impl;



import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.iag.config.LoadJpaQueries;
import com.conduent.tpms.iag.constants.ICTXConstants;
import com.conduent.tpms.iag.dao.TAgencyDao;
import com.conduent.tpms.iag.dto.AckFileInfoDto;
import com.conduent.tpms.iag.dto.FileInfoDto;
import com.conduent.tpms.iag.model.AgencyInfoVO;
import com.conduent.tpms.iag.model.XferControl;

@Repository
public class TAgencyDaoImpl implements TAgencyDao {

	private static final Logger dao_log = LoggerFactory.getLogger(TAgencyDaoImpl.class);


	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public FileInfoDto getMappingConfigurationDetails(String fileType) {

		FileInfoDto info =null;
		try {
			dao_log.info("Getting mapping configuration from table..");

			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			String queryFile = LoadJpaQueries.getQueryById("GET_FILE_INFO_CONFIGURATION");

			paramSource.addValue(ICTXConstants.FILE_TYPE, fileType);
			paramSource.addValue(ICTXConstants.AGENCY_ID, ICTXConstants.HOME_AGENCY_ID);
			paramSource.addValue(ICTXConstants.FILE_FORMAT, ICTXConstants.FIXED);

			 info = namedJdbcTemplate.queryForObject(queryFile, paramSource,
					BeanPropertyRowMapper.newInstance(FileInfoDto.class));

}
		catch(Exception e) {
			
		}
		return info;
	}
	
	@Override
	public void insertAckDetails(AckFileInfoDto ackFileInfoDto) {

		dao_log.info("Inserting Ack file details into ack table..");

		String query = LoadJpaQueries.getQueryById("INSERT_FILE_ACK_INTO_ACK_TABLE");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue(ICTXConstants.ACK_FILE_NAME, ackFileInfoDto.getAckFileName());
		paramSource.addValue(ICTXConstants.ACK_FILE_DATE, ackFileInfoDto.getAckFileDate());
		paramSource.addValue(ICTXConstants.ACK_FILE_TIME, ackFileInfoDto.getAckFileTime());
		paramSource.addValue(ICTXConstants.ACK_FILE_STATUS, Integer.parseInt(ackFileInfoDto.getAckFileStatus()));
		paramSource.addValue(ICTXConstants.TRX_FILE_NAME, ackFileInfoDto.getTrxFileName());
		paramSource.addValue(ICTXConstants.RECON_FILE_NAME, ackFileInfoDto.getReconFileName());
		paramSource.addValue(ICTXConstants.FILE_TYPE, ackFileInfoDto.getFileType());
		paramSource.addValue(ICTXConstants.FROM_AGENCY, ackFileInfoDto.getToAgency());
		paramSource.addValue(ICTXConstants.TO_AGENCY, ackFileInfoDto.getFromAgency());
		paramSource.addValue(ICTXConstants.EXTERN_FILE_ID, ackFileInfoDto.getExternFileId());
		paramSource.addValue(ICTXConstants.ATP_FILE_ID, ackFileInfoDto.getAtpFileId());
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
	@Override
	public List<AgencyInfoVO> getAgencyInfoList() {
			
			String queryRules =	LoadJpaQueries.getQueryById("SELECT_INFO_FROM_T_AGENCY");
			
			dao_log.info("Agency info fetched from T_Agency table successfully.");
			
			return namedJdbcTemplate.query(queryRules, new BeanPropertyRowMapper<AgencyInfoVO>(AgencyInfoVO.class));
			
		}
}
