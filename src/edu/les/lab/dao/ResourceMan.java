package edu.les.lab.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ResourceMan {
		private static ResourceMan instance;
		private Connection conn;
		private String connectionURL = "jdbc:mariadb://localhost:3306/hotel?allowMultiQueries=true";
		private String user = "hotel";
		private String psswrd = "mdb52";

		private ResourceMan() {
			try {
				Class.forName("org.mariadb.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("Erro ao carregar driver mariaDB");
			}
		}

		public static ResourceMan getInstance() {
			if (instance == null) {
				instance = new ResourceMan();
			}
			return instance;
		}

		public Connection getConnection() throws SQLException {
			try {
				if (conn == null || conn.isClosed()) {
					conn = DriverManager.getConnection(connectionURL, user, psswrd);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return conn;
		}
}
