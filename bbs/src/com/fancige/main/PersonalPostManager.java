package com.fancige.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.fancige.exception.PostNotFoundException;
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
public class PersonalPostManager
{
	private XMLManager postManager;
	private String userid;

	// private static final XMLManager classXm = new XMLManager(
	// FileManager.postlist);
	private static final List<PersonalPostManager> pms = new ArrayList<PersonalPostManager>();

	private PersonalPostManager(String userid)
	{
		this.userid = userid;
		postManager = new XMLManager(FileManager.getPostPath(userid));
	}

	public static PersonalPostManager newInstance(String userid)
	{
		synchronized (pms) 
		{
			for (Iterator<PersonalPostManager> it = pms.iterator(); it.hasNext();)
			{
				PersonalPostManager pm = it.next();
				if (pm.userid.equals(userid))
					return pm;
			}
			PersonalPostManager pm = new PersonalPostManager(userid);
			pms.add(pm);
			return pm;
		}
	}

	public synchronized void savePost(Post post)
	{
		if (!isPostid(post.getId()))
		{
			Document doc = postManager.readDocument();
			Element ePost = doc.createElement("post");
			Element eTitle = doc.createElement("title");
			Element eContent = doc.createElement("content");
			Element eTime = doc.createElement("time");

			eTitle.setTextContent(post.getTitle());
			eContent.setTextContent(post.getContent());
			eTime.setTextContent(post.getTime());

			ePost.setAttribute("id", StringHandler.transformId(post.getId()));
			ePost.appendChild(eTitle);
			ePost.appendChild(eContent);
			ePost.appendChild(eTime);

			doc.getDocumentElement().appendChild(ePost);
			postManager.writeDocumentWithDTD(doc);
			CommonPostManager.updatePostid(post.getId(), userid,
					CommonPostManager.NORMAL_POSTID);
		}
	}

	/**
	 * <p>
	 * If you want to edit a post, call {@link #getPostById(String)}, editing
	 * it, and then call this method. In other words, you just need to call this
	 * method to replace the old post.
	 * </p>
	 * <strong>Note that do not change the postid.</strong>
	 * 
	 * @param post
	 *            The post changed.
	 * @see #savePost(Post)
	 */
	public synchronized void replacePost(Post post)
	{
		if (isPostid(post.getId()))
		{
			deletePost(post.getId());
			savePost(post);
		}
	}

	public synchronized void deletePost(String postid)
	{
		if (isPostid(postid))
		{
			Document doc = postManager.readDocument();
			doc.getDocumentElement().removeChild(
					doc.getElementById(StringHandler.transformId(postid)));
			postManager.writeDocumentWithDTD(doc);
			CommonPostManager.updatePostid(postid, userid,
					CommonPostManager.DELETE_POSTID);
		}
	}

	public synchronized boolean isPostid(String postid)
	{
		Document doc = postManager.readDocument();
		return null == doc.getElementById(StringHandler.transformId(postid)) ? false
				: true;
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

	public List<Post> getAllPosts()
	{
		List<Post> ps = new ArrayList<Post>();
		Document doc = postManager.readDocument();
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
	 * @param postid
	 *            The id of the post you want to get.
	 * @return The post or null if it do not exist.
	 */
	public Post getPostById(String postid)
	{
		if (!isPostid(postid))
			throw new PostNotFoundException("The post do not exist");
		Document doc = postManager.readDocument();
		Element ePost = doc.getElementById(StringHandler.transformId(postid));
		if (null == ePost)
			throw new PostNotFoundException(
					"The post do not belong to the current user.");
		return transformPost(ePost);
	}
}
