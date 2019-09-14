package com.revolut.transfer.dao;

import java.sql.SQLException;
import java.util.List;

import com.revolut.transfer.model.ClientDto;

public interface ClientRepository {

	List<ClientDto> findAll() throws SQLException;

	ClientDto findById(int id);

	void delete(int id);

	int insert(ClientDto client);

	ClientDto update(ClientDto client);
}
