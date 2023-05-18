package com.conduent.parking.posting.dao;

import com.conduent.parking.posting.dto.AccountInfoDTO;

public interface IAccountInfoDao 
{
	public AccountInfoDTO getAccountInfo(Long etcAccountId,Integer accountType);
	public void getAccountBalance(AccountInfoDTO accountInfo);
}
