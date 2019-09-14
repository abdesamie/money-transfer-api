package com.revolut.transfer.services;

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
import com.revolut.transfer.dao.TransferRepository;
import com.revolut.transfer.exceptions.BusinessException;
import com.revolut.transfer.model.AccountDto;
import com.revolut.transfer.model.TransferDto;
import com.revolut.transfer.services.impl.TransferServiceImpl;

public class TransferServiceTest {

	private static final int TWO = 2;
	private static final int ONE = 1;
	@Mock
	private TransferRepository transferRepository;
	@Mock
	private AccountRepository accountRepository;

	@InjectMocks
	private TransferServiceImpl transferService;

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldTransferFailsWhenAccountNotFound() {
		when(accountRepository.findById(ONE)).thenReturn(null);
		when(accountRepository.findById(TWO)).thenReturn(new AccountDto());
		exceptionRule.expect(BusinessException.class);
		exceptionRule.expectMessage("Sender Account Not Found");
		transferService.executeTransfer(new TransferDto(ONE, TWO, BigDecimal.TEN));
		when(accountRepository.findById(ONE)).thenReturn(new AccountDto());
		when(accountRepository.findById(TWO)).thenReturn(null);
		exceptionRule.expect(BusinessException.class);
		exceptionRule.expectMessage("Receiver Account Not Found");
		transferService.executeTransfer(new TransferDto(ONE, TWO, BigDecimal.TEN));
	}

	@Test
	public void shouldTransferRejectedWhenInsufficientFunds() {
		AccountDto account1 = new AccountDto(ONE, "IBAN", BigDecimal.valueOf(500));
		AccountDto account2 = new AccountDto(TWO, "IBAN", BigDecimal.ZERO);
		when(accountRepository.findById(ONE)).thenReturn(account1);
		when(accountRepository.findById(TWO)).thenReturn(account2);
		exceptionRule.expect(BusinessException.class);
		exceptionRule.expectMessage("Insufficient Funds");
		transferService.executeTransfer(new TransferDto(ONE, TWO, BigDecimal.valueOf(500.01)));
	}

	@Test
	public void shouldTransferFailsWhenSameAccount() {
		AccountDto account1 = new AccountDto(ONE, "IBAN", BigDecimal.valueOf(5000));
		when(accountRepository.findById(ONE)).thenReturn(account1);
		when(accountRepository.findById(ONE)).thenReturn(account1);
		exceptionRule.expect(BusinessException.class);
		exceptionRule.expectMessage("Could not perform transfer to the same account");
		transferService.executeTransfer(new TransferDto(ONE, ONE, BigDecimal.valueOf(200)));
	}

	@Test
	public void shouldTransferRejectedWhenInvalidAmount() {
		AccountDto account1 = new AccountDto(ONE, "IBAN", BigDecimal.valueOf(1000));
		AccountDto account2 = new AccountDto(TWO, "IBAN", BigDecimal.ZERO);
		when(accountRepository.findById(ONE)).thenReturn(account1);
		when(accountRepository.findById(TWO)).thenReturn(account2);
		exceptionRule.expect(BusinessException.class);
		exceptionRule.expectMessage("Invalid Amount");
		transferService.executeTransfer(new TransferDto(ONE, TWO, BigDecimal.valueOf(-1)));
	}

}
