package com.revolut.transfer.model;

import java.io.Serializable;

public class ClientDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String firstname;
	private String lastname;
	private int id;

	public ClientDto() {
	}

	public ClientDto(String firstname, String lastname) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public ClientDto(int id, String firstname, String lastname) {
		this(firstname, lastname);
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastme) {
		this.lastname = lastme;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
