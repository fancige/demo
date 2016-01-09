package com.fancige.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.fancige.util.StringHandler;

public class UserManager {
	
	public static final int PASSWORD_LENGTH_MAX = 20;
	public static final int PASSWORD_LENGTH_MIN = 6;
	public static final int USERID_LENGTH_MIN = 5;
	public static final int USERID_LENGTH_MAX = 9;
	public static final int USERNAME_LENGTH_MAX = 10;
	public static final int USERNAME_LENGTH_MIN = 1;
	public static final String PASSWORD_LEGAL_CHARS = "[0-9[a-zA-z]]";

	public enum Column {
		userid, username, password, loginID, loginKey;
	}

	public static boolean isUserid(String value) {
		if (!StringHandler.isAllDigits(value))
			return false;

		String sql = "SELECT userid FROM userlist WHERE userid = ?;";
		Connection con = DBManager.getConnection();
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, value);
			if (stmt.executeQuery().next()) {
				DBManager.close(con);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(con);
		}
		return false;
	}

	private static void initTable(String userid) {
		String sql = "INSERT INTO user (userid) VALUE (?)";
		DBManager.update(sql, userid);
		sql = "UPDATE userlist SET loginID = MD5(userid), username = userid WHERE userid = ?";
		DBManager.update(sql, userid);
	}

	public static String register(String password) {
		String sql = "INSERT INTO userlist (password) VALUES ( ? );";
		Connection con = DBManager.getConnection();
		try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, password);
			stmt.executeUpdate();
			try (ResultSet rs = stmt.getGeneratedKeys()) {
				if (rs.next()) {
					String userid = rs.getString(1);
					initTable(userid);
					return userid;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(con);
		}
		throw new RuntimeException("register failed");
	}

	public static boolean login(String userkey, String password) {
		if (null == userkey || userkey.isEmpty() || null == password
				|| password.length() < UserManager.PASSWORD_LENGTH_MIN
				|| password.length() > UserManager.PASSWORD_LENGTH_MAX
				|| StringHandler.containsIllegalChar(password, UserManager.PASSWORD_LEGAL_CHARS)) {
			return false;
		}
		String sql = "SELECT * FROM userlist WHERE password = ? AND (userid = ? OR username = ?);";
		Connection con = DBManager.getConnection();
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, password);
			for (int i = 2; i < 4; i++)
				stmt.setString(i, userkey);
			if (stmt.executeQuery().next()) {
				DBManager.close(con);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(con);
		}
		return false;
	}

	public static String getUnique(Column name, String otherUnique) {
		String sql = "SELECT " + name + " FROM userlist WHERE userid = ? OR username = ? OR loginID = ?";
		return DBManager.query(sql, otherUnique, otherUnique, otherUnique);
	}

	public static boolean set(String userid, Column name, String value) {
		if (name == null || Column.userid == name || Column.loginID == name)
			return false;

		if (Column.loginKey == name && value != null && value.length() != 32)
			return false;

		String sql = "UPDATE userlist SET " + name + " = ? WHERE userid = ?";
		return DBManager.update(sql, value, userid) == 1 ? true : false;
	}

	public static String get(String userid, Column name) {
		if (name == null)
			return null;

		String sql = "SELECT " + name + " FROM userlist WHERE userid = ?";
		return DBManager.query(sql, userid);
	}

	public static boolean autoLogin(String loginID, String loginKey) {
		String sql = "SELECT userid FROM userlist WHERE loginID = ? AND loginKey = ?";
		return DBManager.query(sql, loginID, loginKey) != null;
	}
}
