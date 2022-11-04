package app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlUtility {

	public static Connection getConnectionToMySql() {
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/data", "root", "Ford@#123");
			System.out.println("Connection to MySQL is successful");

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return connection;
	}
}
