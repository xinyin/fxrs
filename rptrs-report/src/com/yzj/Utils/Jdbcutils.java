package com.yzj.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class Jdbcutils {
	public static String driver;
	public static String url;
	public static String username;
	public static String pwd;

	// public static Connection con=null;
	public static void main(String[] args) {
		getcon();
	}

	public static Connection getcon() {
		Properties pro = new Properties();

		try {
			//pro.load(new FileInputStream("src/db.properties"));
			String path = Jdbcutils.class.getClassLoader().getResource("db.properties").getPath();
			//System.out.println(path);
			FileInputStream in = new FileInputStream(path);
			
			pro.load(in);

			driver = pro.getProperty("jdbc.driver");
			url = pro.getProperty("jdbc.url");
			username = pro.getProperty("jdbc.username");
			pwd = pro.getProperty("jdbc.pwd");

			Class.forName(driver);
			

			return DriverManager.getConnection(url, username, pwd);
		} catch (Exception e) {
			
		}
		return null;

	}

}
