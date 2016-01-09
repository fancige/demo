package com.fancige.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class StringHandlerTest
{
	@Test
	public void testIsAllNumber()
	{
		assertFalse(StringHandler.isAllDigits("az1234567890"));
		assertTrue(StringHandler.isAllDigits("1234567890"));
		assertFalse(StringHandler.isAllDigits(null));
	}

	@Test
	public void testToMap()
	{
		Map<String, String> m = new HashMap<>();
		String k1 = "fjow }{}/fjw", k2 = "foewi fe", k3 = "3i  /of", k4 = "f 、jfe、eo3/。jjof3w";
		m.put(k1, k2);
		m.put(k3, k4);
		assertTrue(m.equals(StringHandler.toMap(m.toString())));
		assertTrue(m.equals(StringHandler.toMap(k1 + "  = " + k2 + "  ," + k3
				+ " =  " + k4 + " } ")));
		assertEquals(0, StringHandler.toMap(null).size());
		assertEquals(0, StringHandler.toMap("jof[./3f3").size());
	}
}