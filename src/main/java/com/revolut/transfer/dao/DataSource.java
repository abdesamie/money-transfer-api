package com.revolut.transfer.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.revolut.transfer.utils.PropertiesUtility;

public class DataSource {

	private static DataSource datasource;
	private final String url;
	private final String username;
	private final String password;
	private final String driver;

	private DataSource() throws IOException {
		PropertiesUtility.loadProperties("h2.properties");
		this.driver = PropertiesUtility.getValue("h2.driver");
		this.url = PropertiesUtility.getValue("h2.url");
		this.username = PropertiesUtility.getValue("h2.username");
		this.password = PropertiesUtility.getValue("h2.password");
	}

	public static DataSource getInstance() throws IOException {
		if (datasource == null) {
			datasource = new DataSource();
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
