package com.fancige.bean;

import java.sql.Date;
import java.sql.Timestamp;

public class User
{
	private int userid;
	private String username;
	private char sex;
	private Timestamp register;
	private Date birth;

	public int getUserid()
	{
		return userid;
	}

	public void setUserid(int userid)
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

	public char getSex()
	{
		return sex;
	}

	public void setSex(char sex)
	{
		this.sex = sex;
	}

	public Timestamp getRegister()
	{
		return register;
	}

	public void setRegister(Timestamp register)
	{
		this.register = register;
	}

	public Date getBirth()
	{
		return birth;
	}

	public void setBirth(Date birth)
	{
		this.birth = birth;
	}
}
