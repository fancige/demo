package com.fancige.main;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.fancige.util.StringHandler;

public class CommonPostManagerTest
{
	private String randomId1;
	private String randomId2;
	private String normalPostid = "1234567890";
	private String deletePostid = "1234567891";
	private String realUserid = "111111";

	@Before
	public void setUp()
	{
		randomId1 = StringHandler.getRandomNumber(15);
		randomId2 = StringHandler.getRandomNumber(15);
	}
	
	@Test
	public void testTestPostid()
	{
		assertEquals(0, CommonPostManager.testPostid(normalPostid));
		assertEquals(1, CommonPostManager.testPostid(deletePostid));
		assertEquals(-1, CommonPostManager.testPostid(randomId1));
	}
	
	@Test
	public void testUpdatePostid1()
	{
		assertEquals(-1, CommonPostManager.testPostid(randomId1));
		CommonPostManager.updatePostid(randomId1, realUserid, CommonPostManager.NORMAL_POSTID);
		assertEquals(0, CommonPostManager.testPostid(randomId1));
		CommonPostManager.updatePostid(randomId1, realUserid, CommonPostManager.DELETE_POSTID);
		assertEquals(1, CommonPostManager.testPostid(randomId1));
	}
	@Test
	public void testUpdatePostid2()
	{
		assertEquals(-1, CommonPostManager.testPostid(randomId1));
		CommonPostManager.updatePostid(randomId1, randomId2, CommonPostManager.NORMAL_POSTID);
		assertEquals(0, CommonPostManager.testPostid(randomId1));
		CommonPostManager.updatePostid(randomId1, randomId2, CommonPostManager.DELETE_POSTID);
		assertEquals(1, CommonPostManager.testPostid(randomId1));
	}
}
