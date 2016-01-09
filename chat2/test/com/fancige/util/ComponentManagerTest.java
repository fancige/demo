package com.fancige.util;

import org.junit.Test;
import org.w3c.dom.Document;

public class ComponentManagerTest
{
	Document doc = DocumentManager.readDocument(PathManager.getPath("MainFrame.xml"));
	ComponentManager cm = new ComponentManager(doc, "rootPanel");
	@Test
	public void testGetSize()
	{
		System.out.println(cm.getBounds());
	}
}
