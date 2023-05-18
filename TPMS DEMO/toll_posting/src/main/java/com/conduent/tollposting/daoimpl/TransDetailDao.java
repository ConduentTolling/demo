package com.conduent.tollposting.daoimpl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.app.timezone.utility.TimeZoneConv;
import com.conduent.tollposting.dao.ITransDetailDao;
import com.conduent.tollposting.model.TranDetail;

@Repository
public class TransDetailDao implements ITransDetailDao
{
	private static final Logger logger = LoggerFactory.getLogger(TransDetailDao.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private TimeZoneConv timeZoneConv;
	

	@Override
	public void updateTranDetail(TranDetail tranDetail) throws Exception
	{
			if(null!=tranDetail.getTxType() && tranDetail.getTxType().equals("V")) {
				String updateQuery = "UPDATE T_TRAN_DETAIL SET POSTED_FARE_AMOUNT=?,DEPOSIT_ID=?,POSTED_DATE=?, TX_STATUS = ?,UPDATE_TS=?,PLAN_TYPE=?,PLAN_CHARGED=?,ETC_ACCOUNT_ID=?, ACCOUNT_TYPE=?, ACCT_AGENCY_ID=?, PLATE_STATE=?, PLATE_NUMBER=?,UNREALIZED_AMOUNT = ? WHERE LANE_TX_ID = ? and EXT_FILE_ID = ?";
				LocalDateTime fromTime = LocalDateTime.now();
				jdbcTemplate.update(updateQuery,tranDetail.getPostedFareAmount(),tranDetail.getDepositId(),tranDetail.getPostedDate(),tranDetail.getTxStatus(),timeZoneConv.currentTime(),tranDetail.getPlanType(),tranDetail.getPlanType(),tranDetail.getEtcAccountId(),tranDetail.getAccountType(),tranDetail.getAccAgencyId(),tranDetail.getPlateState(),tranDetail.getPlateNumber(),tranDetail.getUnrealizedAmount(),tranDetail.getLaneTxId(), tranDetail.getExtFileId());
				logger.debug("##SQL Time Taken for thread {} HOSTNAME: {} in updateTranDetail: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime, LocalDateTime.now()));

			}else {
				String updateQuery = "UPDATE T_TRAN_DETAIL SET POSTED_FARE_AMOUNT=?,DEPOSIT_ID=?,POSTED_DATE=?, TX_STATUS = ?,UPDATE_TS=?,PLAN_TYPE=?,PLAN_CHARGED=?,ETC_ACCOUNT_ID=?,UNREALIZED_AMOUNT = ?,ACCT_AGENCY_ID=? WHERE LANE_TX_ID = ? and EXT_FILE_ID = ?";
				LocalDateTime fromTime = LocalDateTime.now();
				jdbcTemplate.update(updateQuery,tranDetail.getPostedFareAmount(),tranDetail.getDepositId(),tranDetail.getPostedDate(),tranDetail.getTxStatus(),timeZoneConv.currentTime(),tranDetail.getPlanType(),tranDetail.getPlanType(),tranDetail.getEtcAccountId(),tranDetail.getUnrealizedAmount(),tranDetail.getAccAgencyId(),tranDetail.getLaneTxId(), tranDetail.getExtFileId());
				logger.debug("##SQL Time Taken for thread {} HOSTNAME: {} in updateTranDetail: {} ms", Thread.currentThread().getName(),System.getenv("HOSTNAME"), ChronoUnit.MILLIS.between(fromTime, LocalDateTime.now()));
				}
	}
}