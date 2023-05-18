package com.conduent.parking.posting.dao;

import com.conduent.parking.posting.model.FpmsAccount;

public interface IFpmsAccountDao {
	public FpmsAccount getFpmsAccount(Long etcAccountId);
}
