package com.fancige.main;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.fancige.javabean.User;
import com.fancige.util.FileManager;
import com.fancige.util.StringHandler;
import com.fancige.util.XMLManager;

public class UserManager
{
	private static Document userlist;

	public static final int USERNAME = 0;
	public static final int USERID = 1;

	public static final int USERNAME_LENGTH_MAX = 10;
	public static final int USERNAME_LENGTH_MIN = 3;
	public static final int PASSWORD_LENGTH_MAX = 15;
	public static final int PASSWORD_LENGTH_MIN = 6;
	public static final int USERID_LENGTH = 6;

	public static final String USERNAME_LEGAL_CHARS = "[0-9[\u4E00-\u9FA5][a-zA-Z]]";
	public static final String PASSWORD_LEGAL_CHARS = "[0-9[a-zA-Z]]";

	private static XMLManager xm;

	static
	{
		xm = new XMLManager(FileManager.userlist);
		userlist = xm.readDocument();
	}

	public static synchronized void write()
	{
		xm.writeDocumentWithDTD(userlist);
	}

	/**
	 * Creates a new userid for a new account by system at random. You should
	 * call the method isExist to check whether the userid the system generated
	 * exists, if exists, you should call this method again until you get a
	 * userid which is different from others.
	 * 
	 * @return the new userid.
	 */
	public static String createUserid()
	{
		return StringHandler.getRandomNumber(USERID_LENGTH);
	}

	/**
	 * <p>
	 * Checks if the <code>value</code> matching the <code>type</code> exists.
	 * <p>
	 * 
	 * <p>
	 * You should use this method to check if the username and the userid exists
	 * in the database before you create a new account.
	 * </p>
	 * 
	 * @param type
	 *            UserManager.USERNAME or UserManager.USERID.
	 * @param value
	 *            the value matching this type.
	 * @return true if the value exists, or false if not.
	 * @throws IllegalArgumentException
	 *             If the type is not defined in this class.
	 */
	public static boolean isExist(int type, String value)
	{
		NodeList nodes;
		switch (type)
		{
		case USERNAME:
			nodes = userlist.getElementsByTagName("username");
			break;

		case USERID:
			nodes = userlist.getElementsByTagName("userid");
			break;

		default:
			throw new IllegalArgumentException("No this type.");
		}

		for (int i = 0; i < nodes.getLength(); i++)
		{
			if (value.equalsIgnoreCase(nodes.item(i).getTextContent()))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the username by the userid.
	 * 
	 * @param userid
	 * 
	 * @return username. If no this userid, null will be returned.
	 */
	public static String getUserName(String userid)
	{
		NodeList ids = userlist.getElementsByTagName("userid");
		for (int i = 0; i < ids.getLength(); i++)
		{
			if (ids.item(i).getTextContent().equals(userid))
			{
				return userlist.getElementsByTagName("username").item(i)
						.getTextContent();
			}
		}
		return null;
	}

	/**
	 * <p>
	 * Creates a new account.
	 * </p>
	 * <p>
	 * you should call {@link #isExist(int, String)} before creating a new
	 * account to make sure the new username and the userid don't exsit in the
	 * userlist.
	 * </p>
	 * 
	 * @param newUser
	 *            The user you want to create.
	 */

	public static synchronized void createUser(User newUser)
	{
		Element user = userlist.createElement("user");
		Element userid = userlist.createElement("userid");
		Element username = userlist.createElement("username");
		Element password = userlist.createElement("password");

		user.appendChild(userid);
		user.appendChild(username);
		user.appendChild(password);

		userid.setTextContent(newUser.getUserid());
		username.setTextContent(newUser.getUsername());
		password.setTextContent(newUser.getPassword());

		userlist.getDocumentElement().appendChild(user);
		write();
	}

	/**
	 * Checks if the password matches the username or id.
	 * 
	 * @param nameOrId
	 *            username or userid.
	 * @param password
	 *            password.
	 * @return true if it matches, or false if not.
	 */
	public static boolean isMatches(String nameOrId, String password)
	{
		NodeList names = userlist.getElementsByTagName("username");
		NodeList ids = userlist.getElementsByTagName("userid");
		for (int i = 0; i < names.getLength(); i++)
		{
			if (nameOrId.equals(names.item(i).getTextContent())
					|| nameOrId.equals(ids.item(i).getTextContent()))
			{
				if (password.equals(userlist.getElementsByTagName("password")
						.item(i).getTextContent()))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		return false;
	}
}
