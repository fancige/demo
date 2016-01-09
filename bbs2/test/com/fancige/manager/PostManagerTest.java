package com.fancige.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.fancige.bean.Post;
import com.fancige.manager.PostManager;

public class PostManagerTest
{

	private Post createPost()
	{
		Post post = new Post();
		post.setUserid("10000");
		post.setTitle("title");
		post.setContent("content");
		return PostManager.getPost(PostManager.addPost(post));
	}

	@Test
	public void testAddPost1()
	{
		Post post = createPost();
		assertNotEquals(null, post.getPostid());
		PostManager.deletePost(post.getPostid());
	}

	@Test(expected = RuntimeException.class)
	public void testAddPost2()
	{
		Post post = new Post();
		PostManager.addPost(post);
	}

	@Test
	public void testDeletePost1()
	{
		assertFalse(PostManager.deletePost("jij"));
	}

	@Test
	public void testDeletePost2()
	{
		assertTrue(PostManager.deletePost(createPost().getPostid()));
	}

	@Test
	public void testGetPost1()
	{
		assertNull(PostManager.getPost("a"));
	}

	@Test
	public void testGetPost2()
	{
		String postid = createPost().getPostid();
		assertNotNull(PostManager.getPost(postid));
		PostManager.deletePost(postid);
	}

	@Test
	public void testModifyPost1()
	{
		String postid = createPost().getPostid();
		Post post = new Post();
		post.setPostid(postid);
		post.setTitle("title");
		post.setContent("content");
		assertTrue(PostManager.modifyPost(post));
		PostManager.deletePost(postid);
	}

	@Test
	public void testModifyPost2()
	{
		assertFalse(PostManager.modifyPost(new Post()));
	}

	@Test
	public void testGetAllPost1()
	{
		int[][] data = { { 1, 3, 1 }, { 3, 1, 3 }, { 2, 5, 2 }, { 0, 100, 3 } };
		for (int i = 0; i < data.length; i++)
		{
			assertEquals(data[i][0],
					PostManager.getPosts(data[i][1], data[i][2]).size());
		}
	}

	public static void testGetPosts()
	{
		Map<String, String> map = new HashMap<>();
		map.put("content", "%架构师%");
		// System.out.println(PostManager.getPosts(map, true, -1, -1));
		System.out.println(PostManager.getPosts(null, true, 1, 3));
	}

	public static void testCountRows()
	{
		Map<String, String> map = new HashMap<>();
		// map.put("content", "%架构师%");
		System.out.println(PostManager.countRows(map, false));
		System.out.println(PostManager.countRows(null, true));
		System.out.println(PostManager.getPosts(map, true).size());
	}

	public static void main(String[] args)
	{
		// PostManagerTest.testGetPosts();
		PostManagerTest.testCountRows();
	}
}
