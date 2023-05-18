package com.conduent.tpms.qeval.BatchConfig;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.conduent.tpms.qeval.BatchModel.AccountInfo;


public class CustomerRowMapper implements RowMapper<AccountInfo> {

	@Override
	public AccountInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		AccountInfo account = new AccountInfo();
		
		//account.setEtcAccountId(rs.getLong("ETC_ACCOUNT_ID"));
		//account.setAccountType(rs.getInt("ACCOUNT_TYPE"));
		//account.setReBillAmount(rs.getLong("REBILL_AMOUNT"));
		//System.out.println("---------------in RowMapper---------------");
    return account;
    }
	


	
}
