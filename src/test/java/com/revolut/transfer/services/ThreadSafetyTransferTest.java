package com.revolut.transfer.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import com.revolut.transfer.model.AccountDto;
import com.revolut.transfer.model.TransferDto;
import com.revolut.transfer.services.impl.AccountServiceImpl;
import com.revolut.transfer.services.impl.TransferServiceImpl;

public class ThreadSafetyTransferTest {

	private static final double RANDOM_AMOUNT = 13.7;
	private static final int THOUSAND = 1000;
	private TransferService transferService;
	private AccountService accountService;

	@Before
	public void setUp() throws IOException {
		transferService = new TransferServiceImpl();
		accountService = new AccountServiceImpl();
	}

	/**
	 * In order to ensure that issues are really related to race conditions and not
	 * to business viloation we should implement a not threaded scenaio to guarantee
	 * that failing Multi-threaded tests are not because of buisness or any other
	 * technical problems
	 */
	@Test
	public void shouldExecuteTransferOneThread() {
		AccountDto sender = accountService.findById(1);
		AccountDto receiver = accountService.findById(2);
		BigDecimal amountSender = sender.getAmount();
		BigDecimal amountReceiver = receiver.getAmount();
		receiver.getAmount();
		TransferDto transfer = new TransferDto.Builder(1, 2, BigDecimal.TEN).build();
		transferService.executeTransfer(transfer);
		sender = accountService.findById(1);
		receiver = accountService.findById(2);
		assertEquals(amountSender.doubleValue() - 10, sender.getAmount().doubleValue(), 0);
		assertEquals(amountReceiver.doubleValue() + 10, receiver.getAmount().doubleValue(), 0);
	}

	/**
	 * test multi threaded case, where multiple clients attempts to make a
	 * transaction concurrently
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void shouldExecuteTransferMultiThreaded() throws InterruptedException {
		AccountDto sender = accountService.findById(1);
		AccountDto receiver = accountService.findById(2);
		BigDecimal amountSender = sender.getAmount();
		BigDecimal amountReceiver = receiver.getAmount();
		Runnable transfertRunner = new Runnable() {
			@Override
			public void run() {
				TransferDto transfer = new TransferDto.Builder(1, 2, BigDecimal.valueOf(RANDOM_AMOUNT)).build();
				transferService.executeTransfer(transfer);
			}
		};
		ExecutorService executors = Executors.newCachedThreadPool();
		for (int i = 0; i < THOUSAND; i++) {
			executors.execute(transfertRunner);
		}
		executors.shutdown();
		executors.awaitTermination(30, TimeUnit.SECONDS);
		sender = accountService.findById(1);
		receiver = accountService.findById(2);
		assertTrue(amountSender.subtract(round(THOUSAND * RANDOM_AMOUNT)).equals(sender.getAmount()));
		assertTrue(amountReceiver.add(round(THOUSAND * RANDOM_AMOUNT)).equals(receiver.getAmount()));
	}

	private BigDecimal round(double amountSender) {
		return BigDecimal.valueOf(amountSender).setScale(2, RoundingMode.HALF_DOWN);
	}

}
