package com.fancige.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.fancige.javabean.Post;
import com.fancige.util.FileManager;
import com.fancige.util.StringHandler;
import com.fancige.util.XMLManager;

/**
 * 
 * Handle any events of posts. Each post have a unique identification(postid).
 * Every user owns a file that contains his all posts, the file is named by
 * userid, you can get a Post object by its id.
 * 
 * @author fancige
 * 
 */
public class PostManager
{
	private static final int POSTID_LENGTH = 10;

	private XMLManager instanceXm;
	private String userid;

	private static final XMLManager classXm = new XMLManager(FileManager.postlist);
	private static final List<PostManager> pms = new ArrayList<PostManager>();

	private PostManager(String userid)
	{
		this.userid = userid;
		instanceXm = new XMLManager(FileManager.getPostPath(userid));
	}

	public static PostManager newInstance(String userid)
	{
		synchronized (pms)
		{
			for (Iterator<PostManager> it = pms.iterator(); it.hasNext();)
			{
				PostManager pm = it.next();
				if (pm.userid.equals(userid))
					return pm;
			}
			PostManager pm = new PostManager(userid);
			pms.add(pm);
			return pm;
		}
	}

	public synchronized void createPost(Post post)
	{
		Document doc = instanceXm.readDocument();
		Element ePost = doc.createElement("post");
		Element eTitle = doc.createElement("title");
		Element eContent = doc.createElement("content");
		Element eTime = doc.createElement("time");

		eTitle.setTextContent(post.getTitle());
		eContent.setTextContent(post.getContent());
		eTime.setTextContent(post.getTime());

		ePost.setAttribute("id", createPostid());
		ePost.appendChild(eTitle);
		ePost.appendChild(eContent);
		ePost.appendChild(eTime);

		doc.getDocumentElement().appendChild(ePost);
		instanceXm.writeDocumentWithDTD(doc);
	}

	private String createPostid()
	{
		String id;
		while (true)
		{
			id = StringHandler.getRandomNumber(POSTID_LENGTH);
			if (!isPostidExists(id))
				break;
		}
		return id;
	}

	private synchronized boolean isPostidExists(String postid)
	{
		Document postlist = classXm.readDocument();
		NodeList postids = postlist.getElementsByTagName("postid");
		for (int i = 0; i < postids.getLength(); i++)
		{
			if (postids.item(i).getTextContent().equals(postid))
				return true;
		}

		// update postlist.
		System.out.println(Thread.currentThread().getName() + ": " + postid);

		Element eUser = postlist.getElementById(StringHandler
				.transformId(userid));
		if (null == eUser)
		{
			eUser = postlist.createElement("user");
			eUser.setAttribute("id", StringHandler.transformId(userid));
			postlist.getDocumentElement().appendChild(eUser);
		}
		Element ePostid = postlist.createElement("postid");
		ePostid.setTextContent(postid);
		eUser.appendChild(ePostid);
		classXm.writeDocumentWithDTD(postlist);
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		return false;
	}


	/**
	 * <p>
	 * If you want to edit a post, call {@link #getPostById(String)}, editing
	 * it, and then call this method, in other word, you just need to call this
	 * method to save your post changed.
	 * </p>
	 * 
	 * @param post
	 *            The post changed.
	 * @see #addPost(Post)
	 */
	public synchronized void editPost(Post post)
	{
		deletePost(post.getId());
//		addPost(post);
	}

	public synchronized void deletePost(String id)
	{
		Document doc = instanceXm.readDocument();
		Element ePost = doc.getElementById(StringHandler.transformId(id));
		if (null != ePost)
		{
			doc.getDocumentElement().removeChild(ePost);
			instanceXm.writeDocumentWithDTD(doc);
		}
	}

	public synchronized void dedeleAllPosts()
	{
		FileManager.deletePost(userid);
	}

	/**
	 * From org.w3c.dom.Element to com.fancige.javabean.Post
	 */
	private Post transformPost(Element ePost)
	{
		Post post = new Post();

		post.setId(StringHandler.transformId(ePost.getAttribute("id")));

		NodeList nl = ePost.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++)
		{
			String name = nl.item(i).getNodeName();
			String text = nl.item(i).getTextContent();

			switch (name)
			{
			case "title":
				post.setTitle(text);
				break;

			case "content":
				post.setContent(text);
				break;

			case "time":
				post.setTime(text);
				break;
			}
		}

		return post;
	}

	public synchronized List<Post> getAllPosts()
	{
		List<Post> ps = new ArrayList<Post>();
		Document doc = instanceXm.readDocument();
		NodeList nl = doc.getElementsByTagName("post");
		for (int i = 0; i < nl.getLength(); i++)
		{
			ps.add(transformPost((Element) nl.item(i)));
		}
		return ps;
	}

	/**
	 * Gets the post by its id.
	 * 
	 * @param id
	 *            The id of the post you want to get.
	 * @return The post.
	 */
	public synchronized Post getPostById(String id)
	{
		Document doc = instanceXm.readDocument();
		Element ePost = doc.getElementById(StringHandler.transformId(id));

		if (null == ePost)
			return null;
		else
			return transformPost(ePost);
	}
}
