package com.revolut.transfer.dao;

import java.util.List;

import com.revolut.transfer.model.TransferDto;

public interface TransferRepository {
	List<TransferDto> findAll();

	TransferDto insertTransfer(TransferDto transfer);
}
