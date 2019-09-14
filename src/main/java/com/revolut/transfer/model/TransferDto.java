package com.revolut.transfer.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransferDto {

	private int idSender;
	private int idReceiver;
	@JsonProperty
	private BigDecimal amount;

	public TransferDto() {
	}

	public TransferDto(int sender, int receiver, BigDecimal amount) {
		this.idSender = sender;
		this.idReceiver = receiver;
		this.amount = amount;
	}

	public int getIdSender() {
		return idSender;
	}

	public void setIdSender(int sender) {
		this.idSender = sender;
	}

	public int getIdReceiver() {
		return idReceiver;
	}

	public void setIdReceiver(int receiver) {
		this.idReceiver = receiver;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public static class Builder {

		private final int idReceiver;
		private final int idSender;
		private final BigDecimal amount;

		public Builder(int idSender, int idReceiver, BigDecimal amount) {
			this.idReceiver = idReceiver;
			this.idSender = idSender;
			this.amount = amount;
		}

		public TransferDto build() {
			return new TransferDto(this);
		}
	}

	private TransferDto(Builder builder) {
		this.idReceiver = builder.idReceiver;
		this.idSender = builder.idSender;
		this.amount = builder.amount;
	}

}
