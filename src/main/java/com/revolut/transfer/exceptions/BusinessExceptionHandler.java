package com.revolut.transfer.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.google.inject.Singleton;

/**
 * Translator of any kind of business rules violations of any exception
 * unrelated to technical issues it maps the exception to human readble response
 * with HTTP status
 * 
 * @author ABDESSAMIE
 *
 */
@Provider
@Singleton
public class BusinessExceptionHandler implements ExceptionMapper<BusinessException> {
	
	

	@Override
	public Response toResponse(BusinessException businessException) {

		System.out.println(businessException);
		return Response.status(businessException.getStatus()).entity(new ErrorBody(businessException))
				.type(MediaType.APPLICATION_JSON).build();
	}

}
