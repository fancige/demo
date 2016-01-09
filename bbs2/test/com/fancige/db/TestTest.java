package com.fancige.db;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import org.junit.Test;

import static org.junit.Assert.*;

import com.fancige.bean.Post;

public class TestTest
{

	@Test
	public void testAddPost()
	{
		com.fancige.db.Test test = new com.fancige.db.Test();
		for(int i = 0; i < 5; i++)
		{
			Post post = new Post();
			post.setContent("content" + i);
			post.setTitle("title" + i);
			post.setPublish(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			post.setUserid("10000");
			try
			{
				test.addPost(post);
			}
			catch (IOException | SQLException e)
			{
				e.printStackTrace();
				fail();
			}
		}
	}

}
