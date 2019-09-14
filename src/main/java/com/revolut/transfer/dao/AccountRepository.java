package com.revolut.transfer.dao;

import java.sql.SQLException;
import java.util.List;

import com.revolut.transfer.model.AccountDto;
import com.revolut.transfer.utils.LockDecorator;

public interface AccountRepository {

	List<AccountDto> findAll() throws SQLException;

	AccountDto findById(int id);

	void delete(AccountDto account);

	int insert(AccountDto account);

	void update(AccountDto account);

	LockDecorator<AccountDto> getForUpdate(int id);
}
