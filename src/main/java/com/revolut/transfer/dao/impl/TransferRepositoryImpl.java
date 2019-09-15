package com.revolut.transfer.dao.impl;

import static com.revolut.transfer.dao.tables.Account.ACCOUNT;
import static com.revolut.transfer.dao.tables.Transfer.TRANSFER;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record3;
import org.jooq.RecordMapper;
import org.jooq.impl.DSL;

import com.revolut.transfer.dao.DataSource;
import com.revolut.transfer.dao.TransferRepository;
import com.revolut.transfer.dao.tables.Account;
import com.revolut.transfer.model.TransferDto;

public class TransferRepositoryImpl implements TransferRepository {

	private static final String RECEIVER = "receiver";
	private static final String SENDER = "sender";
	private DSLContext dslContext;

	@Inject
	public TransferRepositoryImpl(DataSource dataSource) throws IOException {
		dslContext = DSL.using(dataSource.getConnection());
	}

	@Override
	public List<TransferDto> findAll() {
		Account senderAccount = ACCOUNT.as(SENDER);
		Account receiverAccount = ACCOUNT.as(RECEIVER);
		List<TransferDto> transfers = dslContext.select(senderAccount.ID, receiverAccount.ID, TRANSFER.AMOUNT)
				.from(TRANSFER).join(senderAccount).on(TRANSFER.ID_SENDER.eq(senderAccount.ID)).join(receiverAccount)
				.on(TRANSFER.ID_RECEPIENT.eq(receiverAccount.ID)).fetch().stream()
				.map(mapTransferDataToDto(senderAccount, receiverAccount)).collect(Collectors.toList());
		return transfers;
	}

	private Function<? super Record3<Integer, Integer, Double>, ? extends TransferDto> mapTransferDataToDto(
			Account senderAccount, Account receiverAccount) {
		return record -> new TransferDto(record.get(senderAccount.ID), record.get(receiverAccount.ID),
				BigDecimal.valueOf(record.get(TRANSFER.AMOUNT)));
	}

	@Override
	public TransferDto insertTransfer(TransferDto transfer) {
		int idTransfer = dslContext.insertInto(TRANSFER, TRANSFER.ID_SENDER, TRANSFER.ID_RECEPIENT, TRANSFER.AMOUNT)
				.values(transfer.getIdSender(), transfer.getIdReceiver(), transfer.getAmount().doubleValue())
				.returning(TRANSFER.ID).fetchOne().get(TRANSFER.ID);
		return findById(idTransfer);
	}

	public TransferDto findById(int id) {
		TransferDto transferDto = null;
		Record transferRecord = dslContext.fetchOne(TRANSFER, TRANSFER.ID.eq(id));
		if (transferRecord != null)
			transferDto = transferRecord.map(mapFullRecordToTransfer());
		return transferDto;
	}

	private RecordMapper<Record, TransferDto> mapFullRecordToTransfer() {
		return record -> new TransferDto.Builder(record.get(TRANSFER.ID_SENDER), record.get(TRANSFER.ID_RECEPIENT),
				BigDecimal.valueOf(record.get(TRANSFER.AMOUNT))).build();
	}

}
