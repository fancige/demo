package com.fancige.bean;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Post
{
	private String postid;
	private String userid;
	private String title;
	private String content;
	private Timestamp publish;
	private Timestamp modify;
	private Date publishDate;
	private Time publishTime;
	
	public Post()
	{

	}

	public Post(String userid, String title, String content)
	{
		this.userid = userid;
		this.title = title;
		this.content = content;
	}

	public String getPostid()
	{
		return postid;
	}

	public void setPostid(String postid)
	{
		this.postid = postid;
	}

	public String getUserid()
	{
		return userid;
	}

	public void setUserid(String userid)
	{
		this.userid = userid;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public Timestamp getPublish()
	{
		return publish;
	}

	public void setPublish(Timestamp publish)
	{
		this.publish = publish;
		if(null != publish)
		{
			publishDate = new Date(publish.getTime());
			publishTime = new Time(publish.getTime());
		}
	}

	public Timestamp getModify()
	{
		return modify;
	}

	public void setModify(Timestamp modify)
	{
		this.modify = modify;
	}

	public Date getPublishDate()
	{
		return publishDate;
	}
	
	public Time getPublicTime()
	{
		return publishTime;
	}
	@Override
	public String toString()
	{
		return "Post [postid=" + postid + ", userid=" + userid + ", title="
				+ title + ", content=" + content + ", publish=" + publish
				+ ", modify=" + modify + "]";
	}
}
