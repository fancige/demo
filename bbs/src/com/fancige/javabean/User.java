package com.fancige.javabean;

/**
 * it contains the basic infomation of a new user to create,
 * including userid, username and password.
 * 
 * @author fancige
 *
 */
public class User 
{
	private String userid;
	private String username;
	private String password;
	
	public User(String userid, String username, String password)
	{
		this.userid = userid;
		this.username = username;
		this.password = password;
	}
	public String getUserid()
	{
		return userid;
	}
	public void setUserid(String userid)
	{
		this.userid = userid;
	}
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}

}
