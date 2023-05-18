package com.conduent.tpms.process25a.dao.impl;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tpms.process25a.dao.IXferControlDao;
import com.conduent.tpms.process25a.dao.TQatpMappingDao;
import com.conduent.tpms.process25a.model.AgencyInfoVO;
import com.conduent.tpms.process25a.model.TagDeviceDetails;
import com.conduent.tpms.process25a.model.TranDetail;


@Repository
public class TQatpMappingDaoImpl implements TQatpMappingDao {

	private static final Logger dao_log = LoggerFactory.getLogger(TQatpMappingDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	IXferControlDao xferControlDao;

	@Autowired
	TimeZoneConv timeZoneConv;



	@Override
	public TagDeviceDetails checkRecordExistInDevice(String device_no) {

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		String querytocheckrecord = "select * from T_QATP_STATISTICS";
		List<TagDeviceDetails> devicedetail = namedJdbcTemplate.query(querytocheckrecord, paramSource,BeanPropertyRowMapper.newInstance(TagDeviceDetails.class));
		if(devicedetail.size()>0) {
			return devicedetail.get(0);
		}
		return null;
	}

	

	

	


	/*@Override
	public List<AgencyInfoVO> getAgencyDetails() {

		String queryRules =	LoadJpaQueries.getQueryById("SELECT_INFO_FROM_T_AGENCY");
		dao_log.info("Agency info fetched from T_Agency table successfully.");
		List<AgencyInfoVO> list= jdbcTemplate.query(queryRules, new BeanPropertyRowMapper<AgencyInfoVO>(AgencyInfoVO.class));
		return list;
	}*/

	

	@Override
	public List<String> getZipCodeList() {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public void insertaddressinaddresstable(int address_id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<AgencyInfoVO> getAgencyDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateTranDetail(TranDetail tranDetail) {
		String updateQuery = "UPDATE T_TRAN_DETAIL SET DEPOSIT_ID=?,POSTED_DATE=?, TX_STATUS = ?,UPDATE_TS=? WHERE LANE_TX_ID = ? ";
		jdbcTemplate.update(updateQuery,tranDetail.getDepositId(),new Timestamp(new Date().getTime()),tranDetail.getEtcTxStatus().getCode(),timeZoneConv.currentTime(), tranDetail.getLaneTxId());
		dao_log.info("Update TS converted as per TimeZone in T_TRAN Table succesfully."+timeZoneConv.currentTime());
		dao_log.info("Data Update in T_TRAN Table succesfully.");
	}
	
	@Override
	public void updateTranDetailPostedAmt(TranDetail tranDetail) {
		dao_log.info("Update T_TRAN_DETAIL updateTranDetailPostedAmt {} for Lane TX Id {}",tranDetail.getPostedFareAmount(), tranDetail.getLaneTxId());
		String updateQuery = "UPDATE T_TRAN_DETAIL SET DEPOSIT_ID=?,POSTED_DATE=?, TX_STATUS = ?,UPDATE_TS=?,POSTED_FARE_AMOUNT=? WHERE LANE_TX_ID = ? ";
		jdbcTemplate.update(updateQuery,tranDetail.getDepositId(),new Timestamp(new Date().getTime()),tranDetail.getEtcTxStatus().getCode(),timeZoneConv.currentTime(),tranDetail.getPostedFareAmount(), tranDetail.getLaneTxId());
		
		dao_log.info("Update TS converted as per TimeZone in T_TRAN Table succesfully."+timeZoneConv.currentTime());
		dao_log.info("Data Update in T_TRAN Table succesfully.");
	}
	

	

	

	

}