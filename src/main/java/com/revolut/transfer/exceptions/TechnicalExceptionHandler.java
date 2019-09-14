package com.revolut.transfer.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TechnicalExceptionHandler implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable exception) {
		ErrorBody errorBody = new ErrorBody();
		errorBody.setMessage("Technical Error");
		errorBody.setStatus(500);
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorBody).type(MediaType.APPLICATION_JSON).build();
	}

}
