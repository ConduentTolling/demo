package com.conduent.parking.posting.service;

import com.conduent.parking.posting.dto.AccountInfoDTO;

public interface IAccountInfoService 
{
	public AccountInfoDTO getAccountInfo(Long etcAccountId,Integer accountType);
	public void getAccountBalance(AccountInfoDTO accountInfo);
}
