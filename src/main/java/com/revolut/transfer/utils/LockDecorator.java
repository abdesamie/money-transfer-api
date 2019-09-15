package com.revolut.transfer.utils;

import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.SelectForUpdateOfStep;

/**
 * This decorator aims to abstract the mecanism of locking a database record for update
 * it has been created to hide the underlying Data Access
 * @author ABDESSAMIE
 *
 * @param <T> the type of the locked record
 */
public class LockDecorator<T> {

	private SelectForUpdateOfStep<Record> lockForUpdate;
	private RecordMapper<Record, T> mapper;

	/**
	 * 
	 * @param forUpdate The select for update step
	 * @param mapper the mapper from record to type
	 */
	public LockDecorator(SelectForUpdateOfStep<Record> forUpdate, RecordMapper<Record, T> mapper) {
		this.lockForUpdate = forUpdate;
		this.mapper = mapper;
	}
	/**
	 * close the statement and release lock
	 */
	public void close() {
		lockForUpdate.close();
	}
	/**
	 * execute the lock and return the rentity locked
	 * @return the locked entity
	 */
	public T getEntity() {
		T entity = null;
		Record entityRecord = lockForUpdate.fetchOne();
		if (entityRecord != null)
			entity = entityRecord.map(mapper);
		return entity;
	}
	/**
	 * execute the statment
	 */
	public void execute() {
		lockForUpdate.execute();

	}

}
