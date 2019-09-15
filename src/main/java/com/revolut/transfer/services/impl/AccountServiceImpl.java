package com.revolut.transfer.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import com.revolut.transfer.dao.AccountRepository;
import com.revolut.transfer.exceptions.BusinessException;
import com.revolut.transfer.model.AccountDto;
import com.revolut.transfer.services.AccountService;

@Provider
public class AccountServiceImpl implements AccountService {

	private static final String ACCOUNT_NOT_FOUND = "Account not found";
	@Inject
	private AccountRepository accountRepository;

	@Override
	public AccountDto findAccountById(int id) {
		AccountDto accountDto = getAccountOrException(id);
		return accountDto;
	}

	private AccountDto getAccountOrException(int id) {
		AccountDto accountDto = accountRepository.findById(id);
		assertAccountFound(accountDto);
		return accountDto;
	}

	private void assertAccountFound(AccountDto accountDto) {
		if (accountDto == null)
			throw new BusinessException(ACCOUNT_NOT_FOUND, Status.NOT_FOUND);
	}

	@Override
	public List<AccountDto> findAll() {
		return accountRepository.findAll();
	}

	@Override
	public void delete(int idAccount) {
		AccountDto account = getAccountOrException(idAccount);
		accountRepository.delete(account);
	}

	@Override
	public int insert(AccountDto account) {
		return accountRepository.insert(account);
	}

	@Override
	public void update(AccountDto account) {
		AccountDto storedAccount = accountRepository.findById(account.getId());
		assertAccountFound(storedAccount);
		accountRepository.update(account);
	}

	@Override
	public synchronized AccountDto deposit(int idAccount, double amount) {
		if (amount <= 0)
			throw new BusinessException("Invalid Amount", Status.NOT_ACCEPTABLE);
		AccountDto account = findAccountById(idAccount);
		BigDecimal accountAmount = account.getAmount();
		BigDecimal sum = accountAmount.add(BigDecimal.valueOf(amount)).setScale(2, RoundingMode.HALF_DOWN);
		account.setAmount(sum);
		accountRepository.update(account);
		return account;
	}

}
