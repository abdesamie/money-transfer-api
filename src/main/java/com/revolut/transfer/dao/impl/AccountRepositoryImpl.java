package com.revolut.transfer.dao.impl;

import static com.revolut.transfer.dao.tables.Account.ACCOUNT;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.SelectForUpdateOfStep;
import org.jooq.impl.DSL;

import com.revolut.transfer.dao.AccountRepository;
import com.revolut.transfer.dao.DataSource;
import com.revolut.transfer.model.AccountDto;
import com.revolut.transfer.utils.LockDecorator;

public class AccountRepositoryImpl implements AccountRepository {

	
	private DSLContext dslContext;


	@Inject
	public AccountRepositoryImpl(DataSource datasource) throws IOException {
		dslContext = DSL.using(datasource.getConnection());
	}

	@Override
	public List<AccountDto> findAll() {
		List<AccountDto> accounts = dslContext.select(ACCOUNT.ID, ACCOUNT.IBAN, ACCOUNT.AMOUNT).from(ACCOUNT).fetch()
				.stream().map(record -> new AccountDto(record.get(ACCOUNT.ID), record.get(ACCOUNT.IBAN),
						BigDecimal.valueOf(record.get(ACCOUNT.AMOUNT))))
				.collect(Collectors.toList());
		return accounts;
	}

	@Override
	public AccountDto findById(int idAccount) {
		AccountDto accountDto = null;
		Record accountRecord = dslContext.fetchOne(ACCOUNT, ACCOUNT.ID.eq(idAccount));
		if (accountRecord != null)
			accountDto = accountRecord.map(mapRecordToAccount());
		return accountDto;
	}

	private RecordMapper<Record, AccountDto> mapRecordToAccount() {
		return record -> new AccountDto(record.get(ACCOUNT.ID), record.get(ACCOUNT.IBAN),
				BigDecimal.valueOf(record.get(ACCOUNT.AMOUNT)));
	}

	@Override
	public void delete(AccountDto account) {
		dslContext.delete(ACCOUNT).where(ACCOUNT.ID.eq(account.getId())).execute();

	}

	@Override
	public int insert(AccountDto account) {
		return dslContext.insertInto(ACCOUNT, ACCOUNT.IBAN, ACCOUNT.AMOUNT)
				.values(account.getIban(), account.getAmount().doubleValue()).execute();
	}

	@Override
	public void update(AccountDto account) {
		dslContext.update(ACCOUNT).set(ACCOUNT.IBAN, account.getIban())
				.set(ACCOUNT.AMOUNT, account.getAmount().doubleValue()).where(ACCOUNT.ID.eq(account.getId())).execute();
	}

	@Override
	public LockDecorator<AccountDto> getForUpdate(int id) {
		SelectForUpdateOfStep<Record> forUpdate = dslContext.select().from(ACCOUNT).where(ACCOUNT.ID.eq(id))
				.forUpdate();
		LockDecorator<AccountDto> lockDecorator = new LockDecorator<AccountDto>(forUpdate, mapRecordToAccount());
		return lockDecorator;
	}
}
