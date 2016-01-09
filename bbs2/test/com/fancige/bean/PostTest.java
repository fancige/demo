package com.fancige.bean;

import org.junit.Test;

import com.fancige.manager.PostManager;

public class PostTest
{

	@Test
	public void testGetDate()
	{
		Post post = PostManager.getPost("10000");
		if(null != post)
		{
			System.out.println(post.getPublishDate());
			System.out.println(post.getPublicTime());
		}
	}
}
