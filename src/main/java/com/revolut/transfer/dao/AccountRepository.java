package com.revolut.transfer.dao;

import java.util.List;

import com.revolut.transfer.model.AccountDto;
import com.revolut.transfer.utils.LockDecorator;

public interface AccountRepository {

	List<AccountDto> findAll();

	AccountDto findById(int id);

	void delete(AccountDto account);

	int insert(AccountDto account);

	void update(AccountDto account);

	LockDecorator<AccountDto> getForUpdate(int id);
}
