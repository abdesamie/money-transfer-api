package com.revolut.transfer.helpers;



import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.revolut.transfer.dao.AccountRepository;
import com.revolut.transfer.dao.ClientRepository;
import com.revolut.transfer.dao.DataSource;
import com.revolut.transfer.dao.TransferRepository;
import com.revolut.transfer.dao.impl.AccountRepositoryImpl;
import com.revolut.transfer.dao.impl.ClientRepositoryImpl;
import com.revolut.transfer.dao.impl.TransferRepositoryImpl;
import com.revolut.transfer.services.AccountService;
import com.revolut.transfer.services.ClientService;
import com.revolut.transfer.services.TransferService;
import com.revolut.transfer.services.impl.AccountServiceImpl;
import com.revolut.transfer.services.impl.ClientServiceImpl;
import com.revolut.transfer.services.impl.TransferServiceImpl;
/**
 * Hk2 DI Binder, it defines for each contract what is the provided implementation
 * @author ABDESSAMIE
 *
 */
public class TransferBinder extends AbstractBinder {

	/**
	 * Define implementation of each contract
	 */
	@Override
	protected void configure() {
		bind(AccountRepositoryImpl.class).to(AccountRepository.class);
		bind(AccountServiceImpl.class).to(AccountService.class);
		bind(ClientServiceImpl.class).to(ClientService.class);
		bind(ClientRepositoryImpl.class).to(ClientRepository.class);
		bind(TransferServiceImpl.class).to(TransferService.class);
		bind(TransferRepositoryImpl.class).to(TransferRepository.class);
		bind(DataSource.class).to(DataSource.class).in(Singleton.class);
	    bind("h2.properties").to(String.class).named("DB_PROPERTIES_FILE");	
	}

}
