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
import com.revolut.transfer.ressources.ClientResource;

public class ClientResourceTest extends JerseyTest {

	private static final String NOT_FOUND_ID = "99999999";
	private static final String ONE = "1";

	@Override
	protected Application configure() {
		return new ResourceConfig(ClientResource.class).register(new TransferBinder())
				.register(new BusinessExceptionHandler());
	}

	@Test
	public void shouldGetOneReturnSuccessfullResponse() {
		Response response = target("/api/v1/clients/" + ONE).request().get();

		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());
		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));

		String content = response.readEntity(String.class);
		assertEquals("Get one successfull", "{\"firstname\":\"abdessamie\",\"lastname\":\"sohail\",\"id\":1}", content);
	}

	@Test
	public void shouldGetAllReturnSuccessfullResponse() {
		Response response = target("/api/v1/clients").request().get();
		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());

		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));

	}

	@Test
	public void shouldGetOneReturnNotFoundResponse() {
		Response response = target("/api/v1/clients/" + NOT_FOUND_ID).request().get();
		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));

		String content = response.readEntity(String.class);
		assertEquals("", "{\"status\":404,\"message\":\"Client not found\"}", content);
	}

	@Test
	public void shouldPostReturnSuccessfullResponse() {
		Response response = target("/api/v1/clients").request()
				.post(Entity.json("{\"firstname\":\"abdessamie\",\"lastname\":\"sohail\"}"));
		assertEquals("Http Response should be 200: ", Status.CREATED.getStatusCode(), response.getStatus());
	}

	@Test
	public void shouldPutReturnSuccessfullResponse() {
		Response response = target("/api/v1/clients").request()
				.put(Entity.json("{\"firstname\":\"newFirstName\",\"lastname\":\"newLastName\",\"id\":2}"));
		assertEquals("Http Response should be 200: ", Status.OK.getStatusCode(), response.getStatus());

		String content = response.readEntity(String.class);
		assertEquals("Get one successfull", "{\"firstname\":\"newFirstName\",\"lastname\":\"newLastName\",\"id\":2}",
				content);
	}

	@Test
	public void shouldPutReturnNotFoundResponse() {
		Response response = target("/api/v1/clients").request()
				.put(Entity.json("{\"firstname\":\"newFirstName\",\"lastname\":\"newLastName\",\"id\":999999}"));
		assertEquals("Http Response should be 404: ", Status.NOT_FOUND.getStatusCode(), response.getStatus());

		String content = response.readEntity(String.class);
		assertEquals("Http Not Found", "{\"status\":404,\"message\":\"Client not found\"}", content);

	}

	@Test
	public void shouldDeleteReturnSuccessfullResponse() {
		Response response = target("/api/v1/clients/3").request().delete();
		assertEquals("Http Response should be 204: ", Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}

	@Test
	public void shouldDeleteReturnNotFoundResponse() {
		Response response = target("/api/v1/clients/" + NOT_FOUND_ID).request().delete();
		assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));

		String content = response.readEntity(String.class);
		assertEquals("Http Not Found", "{\"status\":404,\"message\":\"Client not found\"}", content);
	}

}
