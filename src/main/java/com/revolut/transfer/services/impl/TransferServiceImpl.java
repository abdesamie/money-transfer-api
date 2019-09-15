package com.revolut.transfer.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;

import com.revolut.transfer.dao.AccountRepository;
import com.revolut.transfer.dao.TransferRepository;
import com.revolut.transfer.exceptions.BusinessException;
import com.revolut.transfer.model.AccountDto;
import com.revolut.transfer.model.TransferDto;
import com.revolut.transfer.services.TransferService;

public class TransferServiceImpl implements TransferService {

	@Inject
	private TransferRepository transferRepository;
	@Inject
	private AccountRepository accountRepository;

	@Override
	public List<TransferDto> findAll() {
		return transferRepository.findAll();
	}

	@Override
	public synchronized TransferDto executeTransfer(TransferDto transfer) {
		assertCorrectAmount(transfer);
		ensureNotSameAccount(transfer);
		AccountDto senderAccount = getAccountOrThrowException(transfer.getIdSender(), "Sender Account Not Found");
		AccountDto receiverAccount = getAccountOrThrowException(transfer.getIdReceiver(), "Receiver Account Not Found");
		BigDecimal senderAccountAmount = senderAccount.getAmount();
		BigDecimal transferAmount = transfer.getAmount();
		ensureSufficientFunds(senderAccountAmount, transferAmount);
		updateBalances(senderAccount, receiverAccount, senderAccountAmount, transferAmount);
		TransferDto executeTransfer = transferRepository.insertTransfer(transfer);
		return executeTransfer;
	}

	private void assertCorrectAmount(TransferDto transfer) {
		if (transfer.getAmount().doubleValue() <= 0)
			throw new BusinessException("Invalid Amount", Status.NOT_ACCEPTABLE);
	}

	private void ensureNotSameAccount(TransferDto transfer) {
		if (transfer.getIdSender() == transfer.getIdReceiver())
			throw new BusinessException("Could not perform transfer to the same account", Status.NOT_ACCEPTABLE);
	}

	private void updateBalances(AccountDto senderAccount, AccountDto receiverAccount, BigDecimal senderAccountAmount,
			BigDecimal transferAmount) {
		BigDecimal senderNewAmount = senderAccountAmount.subtract(transferAmount).setScale(2, RoundingMode.HALF_DOWN);
		senderAccount.setAmount(senderNewAmount);
		BigDecimal receiverNewAmount = receiverAccount.getAmount().add(transferAmount).setScale(2,
				RoundingMode.HALF_DOWN);
		receiverAccount.setAmount(receiverNewAmount);
		accountRepository.update(senderAccount);
		accountRepository.update(receiverAccount);
	}

	private void ensureSufficientFunds(BigDecimal senderAccountAmount, BigDecimal transferAmount) {
		if (transferAmount.compareTo(senderAccountAmount) > 0)
			throw new BusinessException("Insufficient Funds", Status.NOT_ACCEPTABLE);
	}

	private AccountDto getAccountOrThrowException(int idAccount, String message) {
		AccountDto account = accountRepository.findById(idAccount);
		if (account == null)
			throw new BusinessException(message, Status.NOT_FOUND);
		return account;
	}

}
