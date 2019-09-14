package com.revolut.transfer.services;

import java.sql.SQLException;
import java.util.List;

import com.revolut.transfer.model.AccountDto;

public interface AccountService {
	List<AccountDto> findAll() throws SQLException;

	AccountDto findById(int idAccount);

	void delete(int idClient);

	int insert(AccountDto account);

	void update(AccountDto account);

	AccountDto deposit(int idAccount, double amount2);
}
