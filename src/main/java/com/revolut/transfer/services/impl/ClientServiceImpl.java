package com.revolut.transfer.services.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import com.revolut.transfer.dao.ClientRepository;
import com.revolut.transfer.dao.impl.ClientRepositoryImpl;
import com.revolut.transfer.exceptions.BusinessException;
import com.revolut.transfer.model.ClientDto;
import com.revolut.transfer.services.ClientService;

public class ClientServiceImpl implements ClientService {

	private static final String ACCOUNT_NOT_FOUND = "Client not found";
	private ClientRepository clientRepository;

	public ClientServiceImpl() throws IOException {
		clientRepository = new ClientRepositoryImpl();
	}

	@Override
	public ClientDto findById(int id) {
		ClientDto clientDto = clientRepository.findById(id);
		assertClientFound(clientDto);
		return clientDto;
	}

	private void assertClientFound(ClientDto clientDto) {
		if (clientDto == null)
			throw new BusinessException(ACCOUNT_NOT_FOUND, Status.NOT_FOUND);
	}

	@Override
	public List<ClientDto> findAll() throws SQLException {
		return clientRepository.findAll();
	}

	@Override
	public void delete(int idClient) {
		ClientDto clientDto = clientRepository.findById(idClient);
		assertClientFound(clientDto);
		clientRepository.delete(idClient);
	}

	@Override
	public int insert(ClientDto client) {
		return clientRepository.insert(client);
	}

	@Override
	public ClientDto update(ClientDto client) {
		ClientDto clientDto = clientRepository.findById(client.getId());
		assertClientFound(clientDto);
		return clientRepository.update(client);
	}

}
