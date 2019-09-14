package com.revolut.transfer.services;

import java.sql.SQLException;
import java.util.List;

import com.revolut.transfer.model.ClientDto;

public interface ClientService {
	List<ClientDto> findAll() throws SQLException;

	ClientDto findById(int id);

	void delete(int idClient);

	int insert(ClientDto client);

	ClientDto update(ClientDto client);
}
