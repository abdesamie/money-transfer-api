package com.revolut.transfer.services;

import java.util.List;

import com.revolut.transfer.model.AccountDto;

public interface AccountService {
	List<AccountDto> findAll();

	AccountDto findAccountById(int idAccount);

	void delete(int idClient);

	int insert(AccountDto account);

	void update(AccountDto account);

	/**
	 * Deposit an amount on an account
	 * @param idAccount the account to deposit on
	 * @param amount the amount
	 * @return the updated account
	 */
	AccountDto deposit(int idAccount, double amount);
}
