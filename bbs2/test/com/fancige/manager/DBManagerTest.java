package com.fancige.manager;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import com.fancige.bean.Post;
import com.fancige.manager.DBManager;
import com.fancige.manager.PostManager;

public class DBManagerTest
{
	@Test
	public void testGetConection()
	{
		try
		{
			assertTrue(DBManager.getConnection().isValid(1));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void testCharset()
	{
		Post post = new Post();
		post.setContent("你好");
		post.setTitle("我们");
		post.setUserid("1");
		String postid = PostManager.addPost(post);
		post = PostManager.getPost(postid);
		System.out.println(post);
	}
	
	public void queryCharset()
	{
		String sql = "SELECT @@character_set_server";
		try (Statement stmt = DBManager.getConnection().createStatement())
		{
			try (ResultSet rs = stmt.executeQuery(sql))
			{
				if (rs.next())
					System.out.println(rs.getString(1));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void testQuery()
	{
		String sql = "SELECT ? FROM userlist WHERE userid = ?";
		System.out.println(DBManager.query(sql, "username", "10000"));
	}

	public static void main(String[] args)
	{
		DBManagerTest.testQuery();
	}
}
