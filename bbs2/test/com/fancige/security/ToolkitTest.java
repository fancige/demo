package com.fancige.security;

import java.util.Random;

public class ToolkitTest
{
	public static void testRandom()
	{
		for(int i = 0; i < 10; i++)
		{
			System.out.println(Toolkit.random(1));
			System.out.println(Toolkit.random(new Random().nextInt(31) + 1));
		}
	}
	
	public static void main(String[] args)
	{
		ToolkitTest.testRandom();
	}
}
