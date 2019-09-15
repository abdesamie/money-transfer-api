package com.revolut.transfer.integration;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.revolut.transfer.exceptions.BusinessExceptionHandler;
import com.revolut.transfer.helpers.TransferBinder;
import com.revolut.transfer.ressources.TransferRessource;

public class TransferResourceTest extends JerseyTest{

	@Override
	protected Application configure() {
		return new ResourceConfig(TransferRessource.class).register(new TransferBinder())
				.register(new BusinessExceptionHandler());
	}


	@Test
	public void shouldGetAllReturnSuccessfullResponse() {
		Response response = target("/api/v1/transfers").request().get();
		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());

		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));

	}
	
	@Test
	public void shouldExecuteTransferSuccessfullResponse() {
		Response response = target("/api/v1/transfers").request().post(Entity.json("{\"idSender\":2,\"idReceiver\":1,\"amount\":100.0}"));
		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());

		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));

	}
}
