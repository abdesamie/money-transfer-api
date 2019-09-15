package com.revolut.transfer.ressources;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.revolut.transfer.model.TransferDto;
import com.revolut.transfer.services.TransferService;

@Path("/api/v1/transfers")
public class TransferRessource {

	@Inject
	private TransferService transferService;


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<TransferDto> findTransfers() throws SQLException {
		return transferService.findAll();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response executeTransfer(TransferDto transfer) {
		TransferDto returnedTransfer = transferService.executeTransfer(transfer);
		return Response.ok().status(Status.OK).entity(returnedTransfer).build();
	}

}