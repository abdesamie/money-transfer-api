package com.revolut.transfer.configuration;

import org.glassfish.jersey.server.internal.process.MappableException;
import org.glassfish.jersey.server.validation.internal.ValidationExceptionMapper;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.revolut.transfer.dao.AccountRepository;
import com.revolut.transfer.dao.ClientRepository;
import com.revolut.transfer.dao.TransferRepository;
import com.revolut.transfer.dao.impl.AccountRepositoryImpl;
import com.revolut.transfer.dao.impl.ClientRepositoryImpl;
import com.revolut.transfer.dao.impl.TransferRepositoryImpl;
import com.revolut.transfer.exceptions.BusinessException;
import com.revolut.transfer.exceptions.MappingExceptionHandler;
import com.revolut.transfer.exceptions.TechnicalExceptionHandler;
import com.revolut.transfer.services.AccountService;
import com.revolut.transfer.services.ClientService;
import com.revolut.transfer.services.TransferService;
import com.revolut.transfer.services.impl.AccountServiceImpl;
import com.revolut.transfer.services.impl.ClientServiceImpl;
import com.revolut.transfer.services.impl.TransferServiceImpl;

/**
 * This module is a guice module for dependencies definitions
 * which are known as bindings at Guice
 * Here we defines our dependencies between all our objects
 * @author ABDESSAMIE
 *
 */
public class GuiceModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(TransferService.class).to(TransferServiceImpl.class);
		bind(TransferRepository.class).to(TransferRepositoryImpl.class);
		bind(AccountService.class).to(AccountServiceImpl.class);
		bind(AccountRepository.class).to(AccountRepositoryImpl.class);
		bind(ClientRepository.class).to(ClientRepositoryImpl.class);
		bind(ClientService.class).to(ClientServiceImpl.class);
		bindConstant().annotatedWith(Names.named("DB_PROPERTIES_FILE")).to("h2.properties");
		
		/**PROVIDERS**/
		bind(BusinessException.class);
		bind(ValidationExceptionMapper.class);
		bind(TechnicalExceptionHandler.class);
		bind(MappingExceptionHandler.class);
	}
}