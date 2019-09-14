package com.revolut.transfer.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.revolut.transfer.validators.ValidIban;

public class AccountDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	@ValidIban
	private String iban;
	private BigDecimal amount;

	public AccountDto() {
	}

	public AccountDto(int id, String iban, BigDecimal amount) {
		this.id = id;
		this.iban = iban;
		this.amount = amount;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
