package com.conduent.tollposting.daoimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.conduent.tollposting.constant.Constants;
import com.conduent.tollposting.dao.IIcrxReconDao;
import com.conduent.tollposting.model.IcrxRecon;

@Repository
public class IcrxReconDao implements IIcrxReconDao{

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Override
	public void saveIcrxRecon(IcrxRecon icrxRecon) 
	{
		String query="INSERT INTO TPMS.T_ICRX_RECON (PLAZA_AGENCY_ID, EXTER_FILE_ID, LANE_TX_ID, TX_EXTERN_REF_NO, TX_MOD_SEQ, ETC_TX_STATUS, ACCOUNT_AGENCY_ID, POSTED_DATE) values (:PLAZA_AGENCY_ID, :EXTER_FILE_ID, :LANE_TX_ID, :TX_EXTERN_REF_NO, :TX_MOD_SEQ,:TX_STATUS, :ACCOUNT_AGENCY_ID, :POSTED_DATE)";
		MapSqlParameterSource paramSource = null;
		paramSource = new MapSqlParameterSource();
		paramSource.addValue(Constants.PLAZA_AGENCY_ID,icrxRecon.getPlazaAgencyId());
		paramSource.addValue(Constants.EXTER_FILE_ID,icrxRecon.getExternFileId());
		paramSource.addValue(Constants.LANE_TX_ID, icrxRecon.getLaneTxId());
		paramSource.addValue(Constants.TX_EXTERN_REF_NO, icrxRecon.getTxExternRefNo());
		paramSource.addValue(Constants.TX_MOD_SEQ, icrxRecon.getTxModSeq());
		paramSource.addValue(Constants.TX_STATUS, icrxRecon.getTxStatus().getCode());
		paramSource.addValue(Constants.ACCOUNT_AGENCY_ID, icrxRecon.getAccountAgenyId());
		paramSource.addValue(Constants.POSTED_DATE, icrxRecon.getPostedDate());
		namedJdbcTemplate.update(query, paramSource);
	}
}