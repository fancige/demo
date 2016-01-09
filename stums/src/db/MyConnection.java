package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class MyConnection
{
	public static Connection getConnection() throws Exception
	{
		String username = "root";
		String password = "root";
		String url = "jdbc:mysql://localhost/stums";
		String driver = "com.mysql.jdbc.Driver";
		Class.forName(driver);
		return DriverManager.getConnection(url, username, password);
	}
}
