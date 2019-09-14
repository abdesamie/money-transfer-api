package com.revolut.transfer.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtility {

	private static Properties properties;

	private PropertiesUtility() {
	}

	public static Properties loadProperties(String resourceFileName) throws IOException {
		properties = new Properties();
		InputStream inputStream = PropertiesUtility.class.getClassLoader().getResourceAsStream(resourceFileName);
		properties.load(inputStream);
		inputStream.close();
		return properties;
	}

	public static String getValue(String key) {
		return properties.getProperty(key);
	}

	public static int getIntegerValue(String key) {
		return Integer.valueOf(properties.getProperty(key));
	}
}
