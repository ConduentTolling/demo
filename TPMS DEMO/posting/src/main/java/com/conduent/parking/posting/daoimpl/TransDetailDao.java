package com.conduent.parking.posting.daoimpl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.parking.posting.dao.ITransDetailDao;
import com.conduent.parking.posting.model.TranDetail;

@Repository
public class TransDetailDao implements ITransDetailDao
{
	private static final Logger logger = LoggerFactory.getLogger(TransDetailDao.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	@Override
	public void updateTranDetail(TranDetail tranDetail) throws Exception
	{
				String updateQuery = "UPDATE T_TRAN_DETAIL SET POSTED_FARE_AMOUNT=?,DEPOSIT_ID=?,POSTED_DATE=?, TX_STATUS = ?,UPDATE_TS=?,PLAN_TYPE=?, ETC_ACCOUNT_ID=? WHERE LANE_TX_ID = ? and EXT_FILE_ID = ?";
				LocalDateTime fromTime = LocalDateTime.now();
				jdbcTemplate.update(updateQuery,tranDetail.getPostedFareAmount(),tranDetail.getDepositId(),tranDetail.getPostedDate(),tranDetail.getTxStatus(),LocalDateTime.now(),tranDetail.getPlanType(),tranDetail.getEtcAccountId(),tranDetail.getLaneTxId(), tranDetail.getExtFileId());
				logger.debug("##SQL Time Taken for thread {} HOSTNAME: {} in updateTranDetail: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime, LocalDateTime.now()));
		}
}