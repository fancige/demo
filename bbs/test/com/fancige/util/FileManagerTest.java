package com.fancige.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class FileManagerTest
{
	@Parameters(name = "{index} : randomId = {0}")
	public static Iterable<Object[]> id()
	{
		List<Object[]> list = new ArrayList<Object[]>();

		for (int i = 0; i < 3; i++)
		{
			String[] s = new String[1];
			s[0] = String.valueOf(Math.random());
			list.add(s);
		}
		return list;
	}
	
	@Parameter(0)
	public String randomId;

	@Test
	public void test()
	{
		new FileManager();
	}
	
	@Test
	public void testGetPostPath()
	{
		String path = FileManager.getPostPath(randomId);
		assertNotNull(path);
		new File(path).delete();
	}
	
	@Test
	public void testDeletePost()
	{
		String path = FileManager.getPostPath(randomId);
		FileManager.deletePost(randomId);
		assertFalse(new File(path).exists());
	}
}
