package com.fancige.main;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fancige.util.FileManager;
import com.fancige.util.StringHandler;
import com.fancige.util.XMLManager;

public class CommonPostManager
{
	private static final XMLManager postlistManager = new XMLManager(
			FileManager.postlist);

	public static final int POSTID_LENGTH = 10;
	
	/**
	 * Calls this method to get an unique identification for a new post.
	 * 
	 * @return An unique identification(postid).
	 */
	public synchronized static String createPostid(String userid)
	{
		String postid;
		while (true)
		{
			postid = StringHandler.getRandomNumber(POSTID_LENGTH);
			if (-1 == testPostid(postid))
				break;
		}
		updatePostid(postid, userid, NORMAL_POSTID);
		return postid;
	}

	/**
	 * 
	 * @param postid
	 * @return <li>-1 : no this postid. <li>0 : <code>postid</code> is normal.
	 *         <li>1 : <code>postid</code> is deleted.
	 */
	public synchronized static int testPostid(String postid)
	{
		Document postlist = postlistManager.readDocument();
		NodeList postids = postlist.getElementsByTagName("postid");
		for (int i = 0; i < postids.getLength(); i++)
		{
			Node ePostid = postids.item(i);
			if (ePostid.getTextContent().equals(postid))
			{
				switch (ePostid.getParentNode().getNodeName())
				{
				case "normal":
					return 0;
				case "delete":
					return 1;
				}
			}
		}
		return -1;
	}

	public static final int NORMAL_POSTID = 0;
	public static final int DELETE_POSTID = 1;

	public synchronized static void updatePostid(String postid, String userid, int updateType)
	{
		Document postlist = postlistManager.readDocument();
		Element eUser = postlist.getElementById(StringHandler
				.transformId(userid));
		// init
		if (null == eUser)
		{
			eUser = postlist.createElement("user");
			eUser.setAttribute("id", StringHandler.transformId(userid));
			eUser.appendChild(postlist.createElement("normal"));
			eUser.appendChild(postlist.createElement("delete"));
			postlist.getDocumentElement().appendChild(eUser);
		}
		NodeList nodes = eUser.getChildNodes();
		switch (updateType)
		{
		case NORMAL_POSTID:
			Element ePostid = postlist.createElement("postid");
			ePostid.setTextContent(postid);
			nodes.item(0).appendChild(ePostid);
			break;

		case DELETE_POSTID:
			NodeList ePostids = nodes.item(0).getChildNodes();

			for (int i = 0; i < ePostids.getLength(); i++)
			{
				if (ePostids.item(i).getTextContent().equals(postid))
				{
					nodes.item(1).appendChild(ePostids.item(i));
					break;
				}
			}
			break;
		}
		postlistManager.writeDocumentWithDTD(postlist);
	}
}
