package com.fancige.bean;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class HistoryTest
{
	@Test
	public void testAdd()
	{
		String url = Math.random() + "";
		History his = new History();
		his.add(url);
		assertEquals(url, his.get());
	}

	@Test
	public void testGet()
	{
		int size1 = 20;
		int size2 = 25;
		History his = new History(size1);
		List<String> list = new ArrayList<>(size2);
		for (int i = 0; i < size2; i++)
		{
			list.add(Math.random() + "");
		}

		for (int i = 0; i < list.size(); i++)
		{
			his.add(list.get(i));
		}

		for (int i = 0, j = list.size() - 1; i < his.size(); i++, j--)
		{
			assertEquals(list.get(j), his.get(i));
		}
	}
	
	@Test
	public void testClear()
	{
		History his = new History();
		his.add("ijfe");
		his.clear();
		assertNull(his.get());
	}
}
