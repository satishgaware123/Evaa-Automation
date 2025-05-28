package com.evaa.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	private Properties properties;

	public ConfigReader() {
		try {
			FileInputStream file = new FileInputStream("src/test/resources/config.properties");
			properties = new Properties();
			properties.load(file);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load config.properties file: " + e.getMessage());
		}
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
}