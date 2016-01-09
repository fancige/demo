package com.fancige.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBManager {
	
	public static Connection getConnection() {
		try {
			InitialContext cxt = new InitialContext();
			DataSource ds = (DataSource) cxt.lookup("java:comp/env/jdbc/mysql");
			return ds.getConnection();
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void close(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static int update(String sql, String... params) {
		int result = -1;
		Connection con = getConnection();
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			for (int i = 0; i < params.length; i++)
				stmt.setString(i + 1, params[i]);
			result = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(con);
		}
		return result;
	}

	public static String query(String sql, String... params) {
		String result = null;
		Connection con = DBManager.getConnection();
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			for (int i = 0; i < params.length; i++)
				stmt.setString(i + 1, params[i]);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					result = rs.getString(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(con);
		}
		return result;
	}
}
