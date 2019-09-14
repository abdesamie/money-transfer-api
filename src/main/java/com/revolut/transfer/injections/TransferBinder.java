package com.revolut.transfer.injections;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.revolut.transfer.services.ClientService;
import com.revolut.transfer.services.impl.ClientServiceImpl;

public class TransferBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(ClientService.class).to(ClientServiceImpl.class);

	}

}
