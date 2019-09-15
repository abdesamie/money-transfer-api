 package com.revolut.transfer.ressources;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
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

import com.revolut.transfer.model.AccountDto;
import com.revolut.transfer.services.AccountService;

/**
 * Endpoints of all account( {@link AccountDto}) related requests
 * 
 * @author ABDESSAMIE
 *
 */
@Path("/api/v1/accounts")
public class AccountResource {
	@Inject
	private AccountService accountservice;


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<AccountDto> findAccounts() {
		return accountservice.findAll();
	}

	@GET
	@Path("/{accountId}")
	@Produces(MediaType.APPLICATION_JSON)
	public AccountDto findAccountById(@PathParam("accountId") int accountId) {
		return accountservice.findAccountById(accountId);
	}

	@PUT
	@Path("/{accountId}/deposit/{amount}")
	@Produces(MediaType.APPLICATION_JSON)
	public AccountDto deposit(@PathParam("accountId") int accountId, @PathParam("amount") double amount) {
		return accountservice.deposit(accountId, amount);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateAccount(@Valid AccountDto account) {
		accountservice.update(account);
		return Response.ok().status(Status.OK).entity(account).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createAccount(@Valid AccountDto account) {
		accountservice.insert(account);
		return Response.ok().status(Status.CREATED).build();
	}

	@DELETE
	@Path("/{accountId}")
	public Response deleteClient(@PathParam("accountId") int accountId) {
		accountservice.delete(accountId);
		return Response.ok().status(Status.NO_CONTENT).build();
	}
}