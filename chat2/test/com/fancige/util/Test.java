package com.fancige.util;

import org.w3c.dom.Document;

public class Test
{
	public static void main(String[] args)
	{
		Document doc = DocumentManager.readDocument(PathManager.getPath("MainFrame.xml"));
		ComponentManager cm = new ComponentManager(doc, "rootPanel");
		System.out.println(cm.getBgColor());
	}
}
