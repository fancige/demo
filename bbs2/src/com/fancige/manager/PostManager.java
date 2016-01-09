package com.fancige.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fancige.bean.Post;
import com.fancige.exception.PostException;
import com.fancige.util.StringHandler;

/**
 * <p>
 * Provides some attributes and operations related to posts.
 * </p>
 * <p>
 * This class maintains a connection to the bbs database and only accesses to
 * the post table in which all posts store.
 * </p>
 * 
 * @author fancige
 * 
 */
public class PostManager {
	public static final int TITLE_MIN_LENGTH = 1;
	public static final int TITLE_MAX_LENGTH = 50;
	public static final int CONTENT_MIN_LENGTH = 1;
	public static final int CONTENT_MAX_LENGTH = 10000;

	// private static String[] fields = { "postid", "userid", "title", "content"
	// };

	/**
	 * Tests whether the post which will be added to the database is valid. If
	 * the string is empty, which means no error messages contained, the post is
	 * valid.
	 */
	private static String verifyPost(Post post) {
		if (null == post)
			return "The post can not be null.";
		if (null == post.getTitle() || post.getTitle().isEmpty())
			return "The title of the post can not null.";
		else if (post.getTitle().length() > TITLE_MAX_LENGTH)
			return "The title of the post is too long: maxlength = " + TITLE_MAX_LENGTH;
		else if (null == post.getContent() || post.getContent().isEmpty())
			return "The content of the post can not null.";
		else if (post.getContent().length() > CONTENT_MAX_LENGTH)
			return "The content of the post is too long: maxlength = " + CONTENT_MAX_LENGTH;
		else
			return "";
	}

	/**
	 * <p>
	 * Puts the new post into the database.
	 * </p>
	 * 
	 * <p>
	 * <b>Note:</b> the post must contain: userid, title, content, other
	 * properties will be ignore.
	 * </p>
	 * 
	 * @param post
	 *            The post you want to add.
	 * @return The postid.
	 * @throws PostException
	 *             If the post is invalid or other exceptions cause this
	 *             operation failed.
	 */
	public static String addPost(Post post) {
		// verify the post.
		String mess = verifyPost(post);
		if (!mess.isEmpty())
			throw new PostException(mess);

		// tests if the post contains a valid userid.
		if (!UserManager.isUserid(post.getUserid()))
			throw new PostException("The post does not contain a valid userid. ");

		String sql = "INSERT INTO post (userid, title, content, publish) VALUES (?, ?, ?, ?);";
		Connection con = DBManager.getConnection();
		try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, post.getUserid());
			stmt.setString(2, post.getTitle());
			stmt.setString(3, post.getContent());
			stmt.setTimestamp(4, new Timestamp(Calendar.getInstance().getTimeInMillis()));
			if (1 == stmt.executeUpdate()) {
				try (ResultSet rs = stmt.getGeneratedKeys()) {
					if (rs.next()) {
						String str = rs.getString(1);
						DBManager.close(con);
						return str;
					}
				}
			}
		} catch (SQLException e) {
			DBManager.close(con);
			throw new PostException("addPost failed", e);
		}
		DBManager.close(con);
		throw new PostException("addPost failed");
	}

	/**
	 * Deletes a post from the database.
	 * 
	 * @param postid
	 *            The postid of the post you want to delete.
	 * @return True if this operation succeed, false if not.
	 */
	public static boolean deletePost(String postid) {
		if (!StringHandler.isAllDigits(postid))
			return false;

		String sql = "DELETE FROM post WHERE postid =" + postid;
		Connection con = DBManager.getConnection();
		try {
			if (1 == con.createStatement().executeUpdate(sql)) {
				DBManager.close(con);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(con);
		}
		return false;
	}

	/**
	 * <p>
	 * Adds a modified post into the database.
	 * </p>
	 * <p>
	 * This method somewhat likes the addPost method, but there are some
	 * different points between them, one of them is that this modified post
	 * must contain a postid.
	 * </p>
	 * <p>
	 * <b>Note:</b> the post must contain: postid, title, content, other
	 * properties will be ignore.
	 * </p>
	 * 
	 * @param post
	 *            The modified post.
	 * @return @return True if this operation succeed, false if not.
	 */
	public static boolean modifyPost(Post post) {
		String mess = verifyPost(post);
		if (!mess.isEmpty())
			return false;

		String sql = "UPDATE post SET title = ?, content = ?, modify = ? WHERE postid = ?";
		Connection con = DBManager.getConnection();
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, post.getTitle());
			stmt.setString(2, post.getContent());
			stmt.setTimestamp(3, new Timestamp(Calendar.getInstance().getTimeInMillis()));
			stmt.setString(4, post.getPostid());
			if (1 == stmt.executeUpdate()) {
				DBManager.close(con);
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(con);
		}
		return false;
	}

	/**
	 * Gets a post by its id.
	 * 
	 * @param postid
	 *            an id of the post.
	 * @return The post or null if the postid does not exist.
	 */
	public static Post getPost(String postid) {
		if (null == postid || postid.isEmpty() || !StringHandler.isAllDigits(postid))
			return null;

		String sql = "SELECT * FROM post WHERE postid = ?;";
		Connection con = DBManager.getConnection();
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, postid);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Post post = setPost(rs);
					DBManager.close(con);
					return post;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(con);
		}
		return null;
	}

	/**
	 * Creates a <code>post<code> object by the <code>rs</code>. You should call
	 * rs.next() before this method to ensure the point of the <code>rs</code>
	 * currently points to a post.
	 * 
	 * @param rs
	 *            A ResultSet object.
	 * @return A post object or null if any exception occurs.
	 */
	private static Post setPost(ResultSet rs) {
		try {
			Post post = new Post();
			post.setPostid(rs.getString(1));
			post.setUserid(rs.getString(2));
			post.setTitle(rs.getString(3));
			post.setContent(rs.getString(4));
			post.setPublish(rs.getTimestamp(5));
			post.setModify(rs.getTimestamp(6));
			return post;
		} catch (SQLException e) {
			throw new PostException("setting up the post failed.", e);
		}
	}

	/**
	 * <p>
	 * Tests whether the <code>post</code> is valid or not. If true, it means
	 * the <code>post</code> must contain some necesary messages, such as
	 * postid, title, content, otherwise, it means the <code>post</code> may
	 * lack some of them.
	 * </p>
	 * 
	 * <p>
	 * <b>Note: </b>This method does not test the validity of the messages it
	 * contains.
	 * </p>
	 * 
	 * @param post
	 *            The post you want to test.
	 * @return true if the post is valid, false if not.
	 */
	public static boolean testPost(Post post) {
		if (null == post.getPostid() || null == post.getTitle() || null == post.getContent()
				|| null == post.getPublish())
			return false;
		else
			return true;
	}

	private static List<Post> queryPosts(String sql) {
		List<Post> posts = new LinkedList<>();
		Connection con = DBManager.getConnection();
		try (ResultSet rs = con.createStatement().executeQuery(sql)) {
			while (rs.next()) {
				Post post = setPost(rs);
				if (null != post)
					posts.add(post);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(con);
		}
		return posts;
	}

	private static String makeSql(Map<String, String> filter, boolean strictMode, int offset, int rowCount,
			String selectExpr) {
		String sql = "SELECT " + selectExpr + " FROM post";
		// String sql = "SELECT " + selectExpr + " FROM post WHERE";
		// if (filter == null)
		// filter = new HashMap<String, String>();
		//
		// String match = " LIKE ";
		// if (strictMode == true)
		// match = " = ";
		//
		// Set<String> keys = filter.keySet();
		// List<String> theKeys = Arrays.asList(fields);
		// for (String key : keys)
		// {
		// if (theKeys.contains(key))
		// {
		// String value = filter.get(key);
		// if (strictMode != true)
		// value = "%" + value + "%";
		// sql = sql + " " + key + match + "'" + value + "'" + " AND";
		// }
		// }
		//
		// if (sql.endsWith("AND"))
		// sql = sql.substring(0, sql.lastIndexOf(" AND"));
		// else if (sql.endsWith("WHERE"))
		// sql = sql.substring(0, sql.lastIndexOf(" WHERE"));

		if (offset >= 0 && rowCount >= 0)
			sql = sql + " LIMIT " + offset + ", " + rowCount;
		return sql;
	}

	public static List<Post> getPosts(Map<String, String> filter, boolean strictMode, int offset, int rowCount) {
		String sql = makeSql(filter, strictMode, offset, rowCount, "*");
		return queryPosts(sql);
	}

	public static List<Post> getPosts(Map<String, String> filter, boolean strictMode) {
		return getPosts(filter, strictMode, -1, -1);
	}

	/**
	 * Gets all posts.
	 * 
	 * @return
	 */
	public static List<Post> getPosts() {
		return getPosts(null, true, -1, -1);
	}

	/**
	 * Gets all posts, you can choose some of them by specifying the offset and
	 * rowCount parameters.
	 * 
	 * @param offset
	 *            The index of the first post, >=0
	 * @param rowCount
	 *            The number of the posts, >=0
	 * @return Some posts from all.
	 * 
	 */
	public static List<Post> getPosts(int offset, int rowCount) {
		return getPosts(null, true, offset, rowCount);
	}

	public static int countRows(Map<String, String> filter, boolean strictMode) {
		String sql = makeSql(filter, strictMode, -1, -1, "COUNT(*)");
		Connection con = DBManager.getConnection();
		try (ResultSet rs = con.createStatement().executeQuery(sql)) {
			if (rs.next()) {
				int i = rs.getInt(1);
				DBManager.close(con);
				return i;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(con);
		}
		return 0;
	}
}
