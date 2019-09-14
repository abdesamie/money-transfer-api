package com.revolut.transfer.ressources;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.revolut.transfer.model.TransferDto;
import com.revolut.transfer.services.TransferService;
import com.revolut.transfer.services.impl.TransferServiceImpl;

@Path("/api/v1/transfers")
public class TransferRessource {

	private TransferService transferService;

	public TransferRessource() throws IOException {
		this.transferService = new TransferServiceImpl();
	}

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