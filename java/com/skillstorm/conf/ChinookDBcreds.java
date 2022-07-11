package com.skillstorm.conf;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ChinookDBcreds {
	
	private static ChinookDBcreds instance;
	private String url;
	private String username;
	private String password;
	
	private ChinookDBcreds() {
		try {
			// Load into memory immediately
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// Read the properties (key/value) from application.properties
			try (InputStream input = ChinookDBcreds.class.getClassLoader()
					.getResourceAsStream("application.properties")) {
				Properties props = new Properties();
				props.load(input); // Load in the file we opened
				// Grab the keys we want
				this.url = props.getProperty("db.url");
				this.username = props.getProperty("db.username");
				this.password = props.getProperty("db.password");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ChinookDBcreds getInstance() {
		if (instance == null)
			instance = new ChinookDBcreds();
		return instance;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
}
