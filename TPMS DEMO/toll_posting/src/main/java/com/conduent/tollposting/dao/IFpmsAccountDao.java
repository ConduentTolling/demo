package com.conduent.tollposting.dao;

import com.conduent.tollposting.model.FpmsAccount;

public interface IFpmsAccountDao {
	public FpmsAccount getFpmsAccount(Long etcAccountId);
}
