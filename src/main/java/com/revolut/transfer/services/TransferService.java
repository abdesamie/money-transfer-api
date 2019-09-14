package com.revolut.transfer.services;

import java.util.List;

import com.revolut.transfer.model.TransferDto;

public interface TransferService {

	List<TransferDto> findAll();

	TransferDto executeTransfer(TransferDto transfer);

}
