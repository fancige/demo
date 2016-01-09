package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyOperator
{
	public static String login(String username, String password)
	{
		String sql = "SELECT authority FROM userlist WHERE username = '"
				+ username + "' AND password = '" + password + "';";
		try (Connection con = MyConnection.getConnection())
		{
			try (Statement stmt = con.createStatement())
			{
				try (ResultSet rs = stmt.executeQuery(sql))
				{
					if (rs.next())
						return rs.getString(1);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	private static String toJsonArray(String[] array, boolean quot)
	{
		String str = "[";
		for (int i = 0; i < array.length; i++)
		{
			str += quot ? "\"" + array[i] + "\"" : array[i];
			if (i != array.length - 1)
				str += ",";
		}
		str += "]";
		return str;
	}

	private static String toJsonArray(int[] array, boolean quot)
	{
		String str = "[";
		for (int i = 0; i < array.length; i++)
		{
			str += quot ? "\"" + array[i] + "\"" : array[i];
			if (i != array.length - 1)
				str += ",";
		}
		str += "]";
		return str;
	}

	private static boolean isEmpty(String... args)
	{
		for (int i = 0; i < args.length; i++)
		{
			if (args[i] == null || args[i].isEmpty())
				return true;
		}
		return false;
	}

	private static final String[] tableNames = { "student", "teacher",
			"course", "dormitory", "department", "class", "sc", "userlist" };

	private static final String[] firstColNames = { "stuId", "tId", "courId",
			"dormId", "departId", "classId", "stuId", "username" };

	private static final String[][] columnAliases = {
			{ "学号", "姓名", "性别", "年龄", "专业", "辅修专业", "政治面貌", "家庭住址", "入学时间",
					"所属院编号", "宿舍编号", "班级编号" },
			{ "教师编号", "教师姓名", "联系电话", "职称" },
			{ "课程编号", "课程名称", "课程类型", "学分", "课程总分" },
			{ "宿舍号", "宿舍地址", "可入住人数", "已入住人数" }, { "系编号", "系名称", "系主任" },
			{ "班级编号", "专业名称", "班级人数" }, { "学号", "课程名", "获得的学分", "获得的成绩" },
			{ "用户名", "密码", "权限" } };

	private static final String[][] columnNames = { { "stuId", "stuName",
			"stuSex", "stuAge", "major", "minor", "politicsStatus",
			"stuAddress", "enterTime", "deptId", "dormId", "classId" } };

	private static final String[] enums = { "{'3':['男','女']}", "{}", "{}",
			"{}", "{}", "{}", "{}", "{'3':['student','teacher','admin']}" };

	private static final int[][] columnCharLengths = {
			{ 15, 30, 1, 7, 30, 30, 30, 50, 30, 15, 15, 15 },
			{ 15, 30, 50, 50 }, { 15, 30, 30, 7, 7 }, { 7, 50, 7, 7 },
			{ 15, 50, 50 }, { 15, 50, 9 }, { 15, 30, 7, 7 }, { 15, 30, 10 } };

	private static final int[][] columnNotNulls = {
			{ 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1 }, { 1, 1, 0, 1 },
			{ 1, 1, 0, 1, 0 }, { 1, 0, 0, 0 }, { 1, 0, 1 }, { 1, 1, 0 },
			{ 1, 1, 1, 1 }, { 1, 1, 1 } };

	private static final boolean[] unique = { true, true, true, true, true,
			true, false, true };

	private static String aliasToName(String tableName, String columnAlias)
	{
		int index = getTableIndex(tableName);
		if (index != -1)
		{
			String[] names = columnNames[index];
			String[] aliases = columnAliases[index];
			for (int i = 0; i < names.length; i++)
			{
				if (aliases[i].equals(columnAlias))
					return names[i];
			}
		}
		return "";
	}

	public static String getTableInfo(String tableName)
	{
		int index = getTableIndex(tableName);
		if (index != -1)
		{
			String aliases = toJsonArray(columnAliases[index], true);
			String lengths = toJsonArray(columnCharLengths[index], false);
			String notNulls = toJsonArray(columnNotNulls[index], false);
			String obj = "{'tableName':'" + tableNames[index]
					+ "','columnCharLengths':" + lengths + ",'columnAliases':"
					+ aliases + ",'columnNotNulls':" + notNulls + ",'enums':"
					+ enums[index] + "}";
			return obj.replaceAll("'", "\"");
		}
		return "";
	}

	private static int getTableIndex(String tableName)
	{
		for (int i = 0; i < tableNames.length; i++)
		{
			if (tableNames[i].equalsIgnoreCase(tableName))
			{
				return i;
			}
		}
		return -1;
	}

	public static String getTableNames()
	{
		return toJsonArray(tableNames, true);
	}

	public static String getGrade(String stuId, String courName)
	{
		String sql = "SELECT * FROM sc WHERE stuId = '" + stuId + "' AND "
				+ "courId LIKE '%" + courName + "%'";
		return executeQuery("sc", sql);
	}

	private static String executeQuery(String tableName, String sql)
	{
		int tableIndex = getTableIndex(tableName);
		if (tableIndex == -1)
			return "[]";

		try (Connection con = MyConnection.getConnection())
		{
			try (Statement stmt = con.createStatement())
			{
				int length = columnAliases[tableIndex].length;
				try (ResultSet rs = stmt.executeQuery(sql))
				{
					String result = "[";
					while (rs.next())
					{
						if (!result.equals("["))
							result += ",";
						result += "[";
						for (int i = 1; i <= length; i++)
						{
							result += "\"" + rs.getString(i) + "\"";
							if (i != length)
								result += ",";
						}
						result += "]";
					}
					result += "]";
					return result;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "[]";
	}

	public static String query(String tableName, String columnAlias,
			String columnValue, boolean like)
	{
		String clause = "";
		if (!isEmpty(tableName, columnAlias, columnValue))
		{
			String val = like ? " LIKE '%" + columnValue + "%'" : " = '"
					+ columnValue + "'";
			String columnName = aliasToName(tableName, columnAlias);
			clause = " WHERE " + columnName + val;
			if (columnName.isEmpty())
				return "[]";
		}
		String sql = "SELECT * FROM " + tableName + clause;
		return executeQuery(tableName, sql);
	}

	public static int delete(String tableName, String keys)
	{
		int index = getTableIndex(tableName);
		if (index != -1 && keys != null)
		{
			String[] ks = keys.split(",");
			String name = firstColNames[index];
			String sql = "DELETE FROM " + tableNames[index] + " WHERE ";

			for (int i = 0; i < ks.length; i++)
			{
				sql += name + " = '" + ks[i] + "'";
				if (i < ks.length - 1)
					sql += " || ";
			}
			try (Connection con = MyConnection.getConnection())
			{
				try (Statement stmt = con.createStatement())
				{
					int count = stmt.executeUpdate(sql);
					if ("student".equals(tableName)
							|| "teacher".equals(tableName))
					{
						delete("userlist", keys);
					}
					return count;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return 0;
	}

	public static int update(String tableName, String data)
	{
		int tableIndex = getTableIndex(tableName);
		if (tableIndex != -1 && data != null && !data.isEmpty())
		{
			data += "end";
			String[] values = data.split(",");
			int columnLength = columnAliases[tableIndex].length;
			String insertSql = "INSERT INTO " + tableName + " VALUES (?";
			for (int i = 1; i < columnLength; i++)
			{
				insertSql += ",?";
			}
			insertSql += ");";

			String deleteSql = "DELETE FROM " + tableName + " WHERE "
					+ firstColNames[tableIndex] + " = ?";

			try (Connection con = MyConnection.getConnection())
			{
				con.setAutoCommit(false);
				try (PreparedStatement insertStmt = con
						.prepareStatement(insertSql))
				{
					try (PreparedStatement deleteStmt = con
							.prepareStatement(deleteSql))
					{
						int count = 0;
						for (int i = 0; i < values.length - 1; i++)
						{
							String value = values[i];
							insertStmt.setString(i % columnLength + 1, value);
							if (i % columnLength == 0)
							{
								deleteStmt.setString(1, values[i]);
							}
							if (i != 0 && i % columnLength == columnLength - 1)
							{
								try
								{
									if (unique[tableIndex])
										deleteStmt.executeUpdate();
									insertStmt.executeUpdate();
									con.commit();
									count++;
									if ("student".equals(tableName)
											|| "teacher".equals(tableName))
									{
										String username = values[i
												- columnLength + 1];
										String authority = tableName;
										String password = username;
										update("userlist", username + ","
												+ password + "," + authority
												+ ",");
									}
								}
								catch (SQLException e)
								{
									con.rollback();
									e.printStackTrace();
								}
							}
						}
						return count;
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return 0;
	}
}
