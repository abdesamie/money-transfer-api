package com.revolut.transfer.services;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revolut.transfer.dao.AccountRepository;
import com.revolut.transfer.exceptions.BusinessException;
import com.revolut.transfer.model.AccountDto;
import com.revolut.transfer.services.impl.AccountServiceImpl;

public class AccountServiceTest {
	@Mock
	private AccountRepository accountRepository;

	@InjectMocks
	private AccountServiceImpl accountService;

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	private static final int ONE = 1;

	@Test
	public void shouldDepositFailInvalidAmount() {
		exceptionRule.expect(BusinessException.class);
		exceptionRule.expectMessage("Invalid Amount");
		accountService.deposit(ONE, -573.25);
	}

	@Test
	public void shouldDepositUpdateBalance() {
		AccountDto account = new AccountDto(ONE, "IBAN", BigDecimal.valueOf(5000));

		when(accountRepository.findById(ONE)).thenReturn(account);
		doNothing().when(accountRepository).update(account);

		AccountDto updatedAccount = accountService.deposit(ONE, 573.25);
		assertTrue(BigDecimal.valueOf(5573.25).equals(updatedAccount.getAmount()));
	}
}
