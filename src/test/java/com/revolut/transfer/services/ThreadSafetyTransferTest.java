package com.revolut.transfer.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.Test;

import com.revolut.transfer.helpers.TransferBinder;
import com.revolut.transfer.model.AccountDto;
import com.revolut.transfer.model.TransferDto;

public class ThreadSafetyTransferTest {

	private static final double RANDOM_AMOUNT = 13.7;
	private static final int THOUSAND = 1000;

	@Inject
	private AccountService accountService;
	@Inject
	private TransferService transferService;

	@Before
	public void setUp() {
		final ServiceLocator locator = ServiceLocatorUtilities.bind(new TransferBinder());
		locator.inject(this);
	}

	/**
	 * In order to ensure that issues are really related to race conditions and not
	 * to business viloation we should implement a not threaded scenaio to guarantee
	 * that failing Multi-threaded tests are not because of buisness or any other
	 * technical problems
	 */
	@Test
	public void shouldExecuteTransferOneThread() {
		AccountDto sender = accountService.findAccountById(1);
		AccountDto receiver = accountService.findAccountById(2);
		BigDecimal amountSender = sender.getAmount();
		BigDecimal amountReceiver = receiver.getAmount();
		receiver.getAmount();
		TransferDto transfer = new TransferDto.Builder(1, 2, BigDecimal.TEN).build();
		transferService.executeTransfer(transfer);
		sender = accountService.findAccountById(1);
		receiver = accountService.findAccountById(2);
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
		AccountDto sender = accountService.findAccountById(1);
		AccountDto receiver = accountService.findAccountById(2);
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
		sender = accountService.findAccountById(1);
		receiver = accountService.findAccountById(2);
		assertTrue(amountSender.subtract(round(THOUSAND * RANDOM_AMOUNT)).equals(sender.getAmount()));
		assertTrue(amountReceiver.add(round(THOUSAND * RANDOM_AMOUNT)).equals(receiver.getAmount()));
	}

	private BigDecimal round(double amountSender) {
		return BigDecimal.valueOf(amountSender).setScale(2, RoundingMode.HALF_DOWN);
	}

}
