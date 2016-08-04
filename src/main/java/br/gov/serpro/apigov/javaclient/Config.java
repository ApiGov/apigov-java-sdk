package br.gov.serpro.apigov.javaclient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	
	public Config() {
		load();
	}
	
	public String tokenAPI = null;
	public String consumerKey = null;
	public String consumerSecret = null;
	
		
	private void load() {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = getClass().getClassLoader().getResourceAsStream("config.properties");			
			prop.load(input);
			tokenAPI = prop.getProperty("token.uri");
			consumerKey = prop.getProperty("consumerKey");
			consumerSecret = prop.getProperty("consumerSecret");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
