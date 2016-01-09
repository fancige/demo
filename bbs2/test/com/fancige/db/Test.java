package com.fancige.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.Properties;

import com.fancige.bean.Post;
import com.fancige.manager.FileManager;

public class Test
{
	public Connection getConnection() throws IOException, SQLException
	{
		Properties pros = getProperties("connectDB.properties");
		String drivers = pros.getProperty("jdbc.drivers");
		System.setProperty("jdbc.drivers", drivers);
		String url = pros.getProperty("jdbc.url");
		String username = pros.getProperty("jdbc.username");
		String password = pros.getProperty("jdbc.password");
		return DriverManager.getConnection(url, username, password);
	}

	public Properties getProperties(String fileName) throws IOException
	{
		Properties pros = new Properties();
		try (InputStream in = Files.newInputStream(Paths.get(FileManager
				.getPath(fileName))))
		{
			pros.load(in);
		}
		return pros;
	}

	public void buildDB() throws IOException, SQLException
	{
		BufferedReader br = Files.newBufferedReader(
				Paths.get(FileManager.getPath("buildDB.txt")),
				Charset.forName("utf-8"));
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		String sql;
		while (null != (sql = br.readLine()))
		{
			System.out.println("execute: " + sql);
			stmt.execute(sql);
		}
		System.out.println("build finished.");
	}

	public void addPost(Post post) throws IOException, SQLException
	{
		String sql = "INSERT INTO post VALUES (?, ?, ?, ?, ?, ?);";
		PreparedStatement stmt = getConnection().prepareStatement(sql);
		stmt.setNull(1, Types.INTEGER);
		stmt.setString(2, post.getUserid());
		stmt.setString(3, post.getTitle());
		stmt.setString(4, post.getContent());
		stmt.setTimestamp(5, post.getPublish());
		stmt.setNull(6, Types.TIMESTAMP);
	}
	public void deletePost(int postid)
	{
		
	}

	public static void main(String[] args)
	{
		Test test = new Test();
		for(int i = 0; i < 2; i++)
		{
			Post p = new Post();
			p.setUserid("1000" + i);
			p.setTitle("title" + i);
			p.setContent("content" + i);
			p.setPublish(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			try
			{
				test.addPost(p);
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
	}
}
