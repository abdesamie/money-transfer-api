package com.revolut.transfer.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * Map exceptions raised due to a malformed body or request format
 * 
 * @author ABDESSAMIE
 *
 */
@Provider
public class MappingExceptionHandler implements ExceptionMapper<JsonMappingException> {

	@Override
	public Response toResponse(JsonMappingException jsonMappingException) {
		ErrorBody errorBody = new ErrorBody();
		errorBody.setMessage(jsonMappingException.getMessage());
		errorBody.setStatus(Status.BAD_REQUEST.getStatusCode());
		System.out.println(jsonMappingException);
		return Response.status(Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(errorBody).build();
	}

}
