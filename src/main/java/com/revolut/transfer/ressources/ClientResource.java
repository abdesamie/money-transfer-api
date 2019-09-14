package com.revolut.transfer.ressources;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.revolut.transfer.model.ClientDto;
import com.revolut.transfer.services.ClientService;
import com.revolut.transfer.services.impl.ClientServiceImpl;

@Path("/api/v1/clients")
public class ClientResource {

	private ClientService clientService;

	public ClientResource() throws IOException {
		this.clientService = new ClientServiceImpl();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ClientDto> findClients() throws SQLException {
		return clientService.findAll();
	}

	@GET
	@Path("/{clientId}")
	@Produces(MediaType.APPLICATION_JSON)
	public ClientDto findClientById(@PathParam("clientId") int clientId) {
		return clientService.findById(clientId);
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateClient(ClientDto client) {
		ClientDto clientDto = clientService.update(client);
		return Response.ok().status(Status.OK).entity(clientDto).build();
	}

	@POST
	public Response createClient(ClientDto client) {
		clientService.insert(client);
		return Response.ok().status(Status.CREATED).build();
	}

	@DELETE
	@Path("/{clientId}")
	public Response deleteClient(@PathParam("clientId") int clientId) {
		clientService.delete(clientId);
		return Response.ok().status(Status.NO_CONTENT).build();
	}
}