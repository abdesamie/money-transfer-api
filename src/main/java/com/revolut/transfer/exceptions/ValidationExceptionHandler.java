package com.revolut.transfer.exceptions;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Parameters validation mapper -> Business validation rules of input parameters
 * 
 * @author ABDESSAMIE
 *
 */
@Provider
public class ValidationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

	@Override
	public Response toResponse(ConstraintViolationException constraintViolationException) {
		ErrorBody errorBody = constructErrorBody(constraintViolationException);
		return Response.status(Response.Status.BAD_REQUEST).entity(errorBody).type(MediaType.APPLICATION_JSON).build();
	}

	private ErrorBody constructErrorBody(ConstraintViolationException constraintViolationException) {
		ErrorBody errorBody = new ErrorBody();
		errorBody.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
		StringBuilder message = new StringBuilder();
		for (ConstraintViolation<?> cv : constraintViolationException.getConstraintViolations()) {
			message.append(cv.getPropertyPath() + " " + cv.getMessage() + "\n");
		}
		errorBody.setMessage(message.toString());
		return errorBody;
	}

}
