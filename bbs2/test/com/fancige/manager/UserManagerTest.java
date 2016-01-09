package com.fancige.manager;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fancige.manager.UserManager.Column;
import com.fancige.security.Toolkit;

public class UserManagerTest
{
	private static String userid;
	private static String username;
	private static String password;
	private static String loginID;

	private static String random()
	{
		return String.valueOf(Math.random()).substring(2, 12);
	}

	public static void removeUser(String userid)
	{
		String sql = "DELETE FROM userlist WHERE userid = ?";
		DBManager.update(sql, userid);
	}

	@BeforeClass
	public static void init()
	{
		username = random();
		password = random();
		userid = UserManager.register(password);
		UserManager.set(userid, Column.username, username);
		loginID = UserManager.get(userid, Column.loginID);
	}

	@AfterClass
	public static void destroy()
	{
		removeUser(userid);
	}

	@Test
	public void testIsUserid()
	{
		assertFalse(UserManager.isUserid(random()));
		assertFalse(UserManager.isUserid(null));
		assertFalse(UserManager.isUserid(""));
		assertTrue(UserManager.isUserid(userid));
	}

	@Test
	public void testRegister()
	{
		String id = UserManager.register("password");
		removeUser(id);
	}

	@Test
	public void testLogin()
	{
		assertTrue(UserManager.login(userid, password));
		assertFalse(UserManager.login(random(), random()));
		assertFalse(UserManager.login(null, null));
	}

	@Test
	public void testGetUnique()
	{
		Column[] keys = { Column.userid, Column.username, Column.loginID };
		String[] vals = { userid, username, loginID };
		for (int i = 0; i < keys.length; i++)
		{
			for (String val : vals)
			{
				assertEquals(vals[i], UserManager.getUnique(keys[i], val));
			}
		}
	}

	@Test
	public void testSetGet()
	{
		String r = Toolkit.random(32);
		assertTrue(UserManager.set(userid, Column.loginKey, r));
		assertEquals(r, UserManager.get(userid, Column.loginKey));
		assertTrue(UserManager.set(userid, Column.loginKey, null));
		assertNull(UserManager.get(userid, Column.loginKey));
		assertFalse(UserManager.set(null, null, null));
		assertFalse(UserManager.set("", null, ""));
	}

	@Test
	public void testAutoLogin()
	{
		String key = Toolkit.random(32);
		UserManager.set(userid, Column.loginKey, key);
		assertTrue(UserManager.autoLogin(
				UserManager.get(userid, Column.loginID), key));
		UserManager.set(userid, Column.loginKey, null);
		assertFalse(UserManager.autoLogin(
				UserManager.get(userid, Column.loginID), key));
	}
}
