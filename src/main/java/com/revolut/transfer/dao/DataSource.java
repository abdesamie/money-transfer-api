package com.revolut.transfer.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Named;

import com.revolut.transfer.utils.PropertiesUtility;
/**
 * It gives an abstraction of the underlying data source
 * permit to load the data source from properties file
 * @author ABDESSAMIE
 *
 */
public class DataSource {

	private static DataSource datasource;
	private final String url;
	private final String username;
	private final String password;
	private final String driver;

	@Inject
	public DataSource(@Named("DB_PROPERTIES_FILE") String propertiesFile) throws IOException {
		PropertiesUtility.loadProperties(propertiesFile);
		this.driver = PropertiesUtility.getValue("h2.driver");
		this.url = PropertiesUtility.getValue("h2.url");
		this.username = PropertiesUtility.getValue("h2.username");
		this.password = PropertiesUtility.getValue("h2.password");
	}

	/**
	 * The singleton scope now is handled by HK2
	 * @return a singleton
	 * @throws IOException if failed on loading properties file
	 */
	public static DataSource getInstance(String propertiesFile) throws IOException {
		if (datasource == null) {
			datasource = new DataSource(propertiesFile);
		}
		return datasource;
	}

	public Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

}
