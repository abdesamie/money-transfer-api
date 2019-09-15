package com.revolut.transfer.configuration;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Defines the configuration of jersey application
 * @author ABDESSAMIE
 *
 */
@ApplicationPath("/")
public class ApplicationConfiguration extends ResourceConfig {

	
	@Inject
	public ApplicationConfiguration(ServiceLocator serviceLocator) {
		packages("com.gwidgets.resource");
		Injector injector = Guice.createInjector(new GuiceModule());
		initGuiceIntoHK2Bridge(serviceLocator, injector);
	}

	/**
	 * Create a bridge between HK2 native jersey DI and Guice DI
	 * @param serviceLocator
	 * @param injector
	 */
	private void initGuiceIntoHK2Bridge(ServiceLocator serviceLocator, Injector injector) {
		GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
		GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
		guiceBridge.bridgeGuiceInjector(injector);
	}
}