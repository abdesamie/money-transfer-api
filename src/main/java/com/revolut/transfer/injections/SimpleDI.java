package com.revolut.transfer.injections;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.revolut.transfer.utils.PropertiesUtility;

/**
 * Dependecy injection is a must, since it was hard to establish it using HK2 I
 * created this utiliy for the purpose
 * 
 * @author ABDESSAMIE
 *
 */
public class SimpleDI {

	private static Map<Class, Class> correspondancies = new HashMap<Class, Class>();
	private static Map<Class, Object> instanciations = new HashMap<Class, Object>();
	private static SimpleDI instance;

	private SimpleDI() {
	}

	public static SimpleDI getInstance()
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (instance == null)
			instance = new SimpleDI();
		return instance;
	}

	public void loadDependencies(String propertiesFile)
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		loadCorrespondancies(propertiesFile);
		LoadInstances();
	}

	private static void loadCorrespondancies(String propertiesFile) throws IOException, ClassNotFoundException {
		Properties properties = PropertiesUtility.loadProperties(propertiesFile);
		Set<String> keySet = properties.stringPropertyNames();
		for (String property : keySet) {
			correspondancies.put(Class.forName(property), Class.forName(properties.getProperty(property)));
		}

	}

	public static <T> T getInstanceFor(Class<T> interfazz)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class clazz = correspondancies.get(interfazz);
		return (T) instanciations.get(clazz);
	}

	private static void LoadInstances() throws InstantiationException, IllegalAccessException {
		for (Map.Entry<Class, Class> entry : correspondancies.entrySet()) {
			Class clazz = entry.getValue();
			instanciations.put(clazz, clazz.newInstance());
		}

	}

	public static <T> T inject(Class<T> clazz) {

		return (T) instanciations.get(clazz);
	}

}
