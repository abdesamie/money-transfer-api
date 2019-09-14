package com.revolut.transfer.exceptions;

public class ErrorBody {

	private int status;
	private String message;

	public ErrorBody(BusinessException businessException) {
		this.status = businessException.getStatus().getStatusCode();
		this.message = businessException.getMessage();
	}

	public ErrorBody() {
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
