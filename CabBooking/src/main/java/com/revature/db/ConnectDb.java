package com.revature.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectDb {
	public static Connection getConnection() throws SQLException, java.lang.ClassNotFoundException {
		// 1 register driver
				Class.forName("com.mysql.cj.jdbc.Driver");//"com.mysql.jdbc.Driver"

				//2 creating connection

				Connection con=
				DriverManager.getConnection("jdbc:mysql://localhost:3306/friends_details", "root", "12345");
				return con;
	}

}
