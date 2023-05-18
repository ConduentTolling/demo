package com.conduent.tpms.unmatched.dto;

/**
 * Transaction Detail Info contains account and transaction info of tx
 * 
 *
 */
public class TransactionDetailDto {

	private TransactionDto transactionDto;

	private AccountInfoDto accountInfoDto;

	public TransactionDetailDto() {
		super();
	}

	public TransactionDetailDto(TransactionDto transactionDto) {
		super();
		this.transactionDto = transactionDto;
	}

	public TransactionDetailDto(AccountInfoDto accountInfoDto) {
		super();
		this.accountInfoDto = accountInfoDto;
	}

	public TransactionDetailDto(TransactionDto transactionDto, AccountInfoDto accountInfoDto) {
		super();
		this.transactionDto = transactionDto;
		this.accountInfoDto = accountInfoDto;
	}

	public TransactionDto getTransactionDto() {
		return transactionDto;
	}

	public void setTransactionDto(TransactionDto transactionDto) {
		this.transactionDto = transactionDto;
	}

	public AccountInfoDto getAccountInfoDto() {
		return accountInfoDto;
	}

	public void setAccountInfoDto(AccountInfoDto accountInfoDto) {
		this.accountInfoDto = accountInfoDto;
	}

	@Override
	public String toString() {
		return "TransactionDetailDto [transactionDto=" + transactionDto + ", accountInfoDto=" + accountInfoDto + "]";
	}

}
