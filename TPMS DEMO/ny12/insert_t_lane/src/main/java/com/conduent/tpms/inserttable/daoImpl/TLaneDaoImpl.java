package com.conduent.tpms.inserttable.daoImpl;

import java.text.ParseException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tpms.inserttable.constant.LoadJpaQueries;
import com.conduent.tpms.inserttable.dao.TLaneDao;
import com.conduent.tpms.inserttable.model.TLaneRequestModel;

@Repository
public class TLaneDaoImpl implements TLaneDao{
	
	

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	final static Logger logger = LoggerFactory.getLogger(TLaneDaoImpl.class);

	

	MapSqlParameterSource paramSource = new MapSqlParameterSource();
	
	@Override
	public Integer insertTLaneDao(TLaneRequestModel tLaneRequestModel) throws ParseException {
		logger.info("Entering insertTLaneDao method");
		String queryCountString = LoadJpaQueries.getQueryById("GET_COUNT_T_LANE");
		
		paramSource.addValue("LANE_ID", tLaneRequestModel.getLaneId());
		Integer id2=jdbcTemplate.queryForObject(queryCountString,paramSource,Integer.class);
		
		Integer id=0;
		
		
		
		logger.info("Pojo Object {}", tLaneRequestModel);

        if (id2 == 0) {
			logger.info("No existing plan found for laneId {}", tLaneRequestModel.getLaneId());
			paramSource.addValue("LANE_ID",tLaneRequestModel.getLaneId());
			paramSource.addValue("EXTERN_LANE_ID",tLaneRequestModel.getExternLaneId());
			paramSource.addValue("AVI",tLaneRequestModel.getAvi());
			paramSource.addValue("OPERATIONAL_MODE",tLaneRequestModel.getOperationalMode());
			paramSource.addValue("STATUS",tLaneRequestModel.getStatus());
			paramSource.addValue("PLAZA_ID",tLaneRequestModel.getPlazaId());
			paramSource.addValue("LANE_IDX",tLaneRequestModel.getLaneIDX());
			paramSource.addValue("LANE_MASK",tLaneRequestModel.getLaneMask());
			paramSource.addValue("DIRECTION",tLaneRequestModel.getDirection());
			paramSource.addValue("LANE_IP",tLaneRequestModel.getLaneIp());
			paramSource.addValue("PORT_NO",tLaneRequestModel.getPortNo());
			paramSource.addValue("HOST_QUEUE_NAME",tLaneRequestModel.getHostQueueName());
			paramSource.addValue("LOCAL_PORT_NO",tLaneRequestModel.getLocalPortNo());
			paramSource.addValue("LANE_TYPE",tLaneRequestModel.getLaneType());
			paramSource.addValue("CALCULATE_TOLL_AMOUNT",tLaneRequestModel.getCalculateTollAmount());
			paramSource.addValue("HOV_MAX_DELTA_TIME",tLaneRequestModel.getHovMaxDeltaTime());
			paramSource.addValue("HOV_MIN_DELTA_TIME",tLaneRequestModel.getHovMinDeltaTime());
			paramSource.addValue("HOV_OFFSET",tLaneRequestModel.getHovOffset());
			paramSource.addValue("IMAGE_RESOLUTION",tLaneRequestModel.getImageResolution());
			paramSource.addValue("IS_SHOULDER",tLaneRequestModel.getIsShoulder());
			paramSource.addValue("LPR_ENABLED",tLaneRequestModel.getLprEnabled());
			paramSource.addValue("SECTION_ID",tLaneRequestModel.getSectionId());
			paramSource.addValue("UPDATE_TS",isValid(tLaneRequestModel.getUpdateTs()));
		
			

			
			String queryString = LoadJpaQueries.getQueryById("INSERT_INTO_T_LANE");
			id = jdbcTemplate.update(queryString, paramSource);
			
		}
		
		
		/*
		 * if (id2 == 0) { paramSource.addValue("LANE_TX_ID",
		 * t25APendingQueueRequest.getLaneTxId());
		 * 
		 * String queryString =
		 * LoadJpaQueries.getQueryById("INSERT_INTO_T_25A_PENDING_QUEUE"); id =
		 * jdbcTemplate.update(queryString, paramSource);
		 * 
		 * }
		 */
        return id;
	}	
	public static OffsetDateTime isValid(String s) {
		if (s != null) {
			return OffsetDateTime.parse(s, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		} else {
			return null;
		}
	}
}
