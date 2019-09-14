package com.revolut.transfer;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class Application {

	private static final String API_CONTROLLERS_PACKAGE = "com.revolut.transfer";

	public static void main(String[] args) throws Exception {
		startTheServer();
	}

	private static void startTheServer() {
		final Server server = new Server(8080);
		server.setHandler(getJerseyHandler());
		try {
			server.start();
			Runtime.getRuntime().addShutdownHook(new ShutDownThread());
			server.join();
		} catch (Exception exception) {
			// TODO : transform to log
			System.out.println("Error has been occured while starting the server!");
		} finally {
			server.destroy();
		}

	}

	private static Handler getJerseyHandler() {
		final ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
		handler.setContextPath("/");

		final ServletHolder servlet = handler.addServlet(ServletContainer.class, "/*");
		servlet.setInitParameter("jersey.config.server.provider.packages", API_CONTROLLERS_PACKAGE);
		return handler;
	}

}
