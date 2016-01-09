package com.fancige.javabean;


public class Post
{
	private String id;
	private String title;
	private String content;
	private String time;

	public Post()
	{
	}

	public Post(String id, String title, String content, String time)
	{
		this.id = id;
		this.title = title;
		this.content = content;
		this.time = time;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
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

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	@Override
	public String toString()
	{
		return "Post [id=" + id + ", title=" + title + ", content=" + content
				+ ", time=" + time + "]";
	}
}
