package com.revolut.transfer.exceptions;

import javax.ws.rs.core.Response.Status;

public class BusinessException extends RuntimeException {
	
	public BusinessException() {}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Status status;

	public BusinessException(String message, Status status) {
		super(message);
		this.status = status;
	}

	public Status getStatus() {
		return this.status;
	}

}
