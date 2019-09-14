package com.revolut.transfer.dao.impl;

import static com.revolut.transfer.dao.tables.Client.CLIENT;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.RecordMapper;
import org.jooq.impl.DSL;

import com.revolut.transfer.dao.ClientRepository;
import com.revolut.transfer.dao.DataSource;
import com.revolut.transfer.model.ClientDto;

public class ClientRepositoryImpl implements ClientRepository {

	private DSLContext dslContext;

	public ClientRepositoryImpl() throws IOException {
		DataSource dataSource = DataSource.getInstance();
		dslContext = DSL.using(dataSource.getConnection());
	}

	@Override
	public List<ClientDto> findAll() throws SQLException {
		List<ClientDto> clients = dslContext.select(CLIENT.FIRST_NAME, CLIENT.LAST_NAME).from(CLIENT).fetch().stream()
				.map(mapPartialRecordToClientObject()).collect(Collectors.toList());
		return clients;
	}

	private Function<? super Record2<String, String>, ? extends ClientDto> mapPartialRecordToClientObject() {
		return record -> new ClientDto(record.get(CLIENT.FIRST_NAME), record.get(CLIENT.LAST_NAME));
	}

	@Override
	public ClientDto findById(int id) {
		ClientDto clientDto = null;
		Record clientRecord = dslContext.fetchOne(CLIENT, CLIENT.ID.eq(id));
		if (clientRecord != null)
			clientDto = clientRecord.map(mapFullRecordToClient());
		return clientDto;
	}

	private RecordMapper<Record, ClientDto> mapFullRecordToClient() {
		return record -> new ClientDto(record.get(CLIENT.ID), record.get(CLIENT.FIRST_NAME),
				record.get(CLIENT.LAST_NAME));
	}

	@Override
	public void delete(int idClient) {
		dslContext.delete(CLIENT).where(CLIENT.ID.eq(idClient)).execute();

	}

	@Override
	public int insert(ClientDto client) {
		return dslContext.insertInto(CLIENT, CLIENT.FIRST_NAME, CLIENT.LAST_NAME)
				.values(client.getFirstname(), client.getLastname()).execute();
	}

	@Override
	public ClientDto update(ClientDto client) {
		// TODO : exception when not found
		dslContext.update(CLIENT).set(CLIENT.FIRST_NAME, client.getFirstname())
				.set(CLIENT.LAST_NAME, client.getLastname()).where(CLIENT.ID.eq(client.getId())).execute();
		return findById(client.getId());
	}

}
