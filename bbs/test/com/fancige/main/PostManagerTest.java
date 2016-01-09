package com.fancige.main;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.fancige.javabean.Post;
import com.fancige.util.StringHandler;

@RunWith(Parameterized.class)
public class PostManagerTest
{
	@Parameters(name = "{index}: randomId1 = {0}, randomId2 = {1}")
	public static Iterable<Object[]> data()
	{
		List<Object[]> list = new ArrayList<Object[]>();

		for (int i = 0; i < 5; i++)
		{
			String[] s = new String[2];
			s[0] = String.valueOf(Math.random());
			s[1] = String.valueOf(Math.random());
			list.add(s);
		}
		return list;
	}

	@Parameter(0)
	public String randomId1;
	@Parameter(1)
	public String randomId2;

	String realUserid = "111111";

	String realPostid1 = "1234567890";
	String realPostid2 = "1234567891";
	String realPostid3 = "1234567892";

	PersonalPostManager realPM;
	PersonalPostManager virtualPM1;
	PersonalPostManager virtualPM2;
	PersonalPostManager virtualPM3;

	Post post;

	
	@Before
	public void setUp()
	{
		realPM = PersonalPostManager.newInstance(realUserid);
		virtualPM1 = PersonalPostManager.newInstance(randomId1);
		virtualPM2 = PersonalPostManager.newInstance(randomId1);
		virtualPM3 = PersonalPostManager.newInstance(randomId2);
		post = new Post(CommonPostManager.createPostid(randomId1), "no title", "no content", "2014-1-3");
	}

	@After
	public void tearDown()
	{
		virtualPM1.dedeleAllPosts();
		virtualPM2.dedeleAllPosts();
		virtualPM3.dedeleAllPosts();
	}

	@Test
	public void testNewInstance1()
	{
		assertTrue(virtualPM1 == virtualPM2);
	}

	@Test
	public void testNewInstance2()
	{
		assertFalse(virtualPM2 == virtualPM3);
	}

	@Test
	public void testGetPostById1()
	{
		assertNull(realPM.getPostById(randomId1));
	}

	@Test
	public void testGetPostById2()
	{
		assertNotNull(realPM.getPostById(realPostid1));
	}

	@Test
	public void testCreatePost()
	{
		String id = post.getId();
		assertNull(realPM.getPostById(id));
		realPM.savePost(post);
		assertNotNull(realPM.getPostById(id));
		realPM.deletePost(id);
	}

	@Test
	public void testDeletePost()
	{
		String id = post.getId();
		realPM.savePost(post);
		realPM.deletePost(id);
		assertNull(realPM.getPostById(id));
	}

	/*
	 * @BeforeClass public static void testGetallPosts() {
	 * 
	 * List<Post> list = PostManager.newInstance("111111").getAllPosts();
	 * for(Post p : list) { System.out.println(p); } }
	 */

	@Test
	public void testEditPost()
	{
		String id = post.getId();
		String oldContent = post.getContent();
		String newContent = "newContent";

		realPM.savePost(post);

		assertTrue(realPM.getPostById(id).getContent().equals(oldContent));

		post.setContent(newContent);
		realPM.replacePost(post);

		assertFalse(realPM.getPostById(id).getContent().equals(oldContent));

		realPM.deletePost(id);
	}

	public void testIsPostidExists()
	{
		final String s = StringHandler.getRandomNumber(10);
		for (int i = 0; i < 10; i++)
		{
			new Thread(String.valueOf(i))
			{
				public void run()
				{
					PersonalPostManager p = PersonalPostManager.newInstance(StringHandler.getRandomNumber(6));
					Method m;
					try
					{
						m = p.getClass().getDeclaredMethod("isPostidExists",
								String.class);
						m.setAccessible(true);
						boolean b = (Boolean) m.invoke(p, s);
						System.out.println(b);
						
					}
					catch (NoSuchMethodException | SecurityException
							| IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e)
					{
						e.printStackTrace();
					}
				};
			}.start();
		}
	}

	public static void main(String[] args)
	{
		new PostManagerTest().testIsPostidExists();
	}
}
