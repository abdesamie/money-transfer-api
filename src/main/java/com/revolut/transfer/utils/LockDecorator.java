package com.revolut.transfer.utils;

import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.SelectForUpdateOfStep;

public class LockDecorator<T> {

	private SelectForUpdateOfStep<Record> lockForUpdate;
	private RecordMapper<Record, T> mapper;

	public LockDecorator(SelectForUpdateOfStep<Record> forUpdate, RecordMapper<Record, T> mapper) {
		this.lockForUpdate = forUpdate;
		this.mapper = mapper;
	}

	public void close() {
		lockForUpdate.close();
	}

	public T getEntity() {
		T entity = null;
		Record entityRecord = lockForUpdate.fetchOne();
		if (entityRecord != null)
			entity = entityRecord.map(mapper);
		return entity;
	}

	public void execute() {
		lockForUpdate.execute();

	}

}
