package com.fancige.main;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserManagerTest
{
	@Test(expected = IllegalArgumentException.class)
	public void testIsExist() 
	{
		UserManager.isExist(3, "111111");
	}
	
	@Test
	public void testIsExist2()
	{
		try
		{
			assertTrue(UserManager.isExist(UserManager.USERNAME, "fancige"));
		}
		catch (Exception e)
		{
			fail();
			e.printStackTrace();
		}
	}
		
	@Test
	public void testIsExist3() 
	{
		try
		{
			assertFalse(UserManager.isExist(UserManager.USERID, "111"));
		}
		catch (Exception e)
		{
			fail();
			e.printStackTrace();
		}
	}
	
	@Test
	public void testIsExist4() 
	{
		try
		{
			assertTrue(UserManager.isExist(UserManager.USERNAME, "fancige"));
		}
		catch (Exception e)
		{
			fail();
			e.printStackTrace();
		}
	}
	
	@Test
	public void testIsMatches()
	{
		assertTrue(UserManager.isMatches("fancige", "123456"));
	}
	
	@Test
	public void testGetUsername()
	{
		assertEquals("fancige", UserManager.getUserName("111111"));
	}
	
	@Test
	public void testGetUsername1()
	{
		assertNotEquals("fanc", UserManager.getUserName("111111"));
	}
}
