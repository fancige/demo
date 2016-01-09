package com.fancige.util;

import java.awt.Color;
import java.awt.Insets;
import java.awt.Rectangle;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ComponentManager
{
	private Node component;
	private String varName;
	private Document doc;

	public ComponentManager(Document doc, String varName)
	{
		this.doc = doc;
		this.varName = varName;
		init();
	}

	private void init()
	{
		NodeList names = doc.getElementsByTagName("var");
		for (int i = 0; i < names.getLength(); i++)
		{
			if (names.item(i).getTextContent().equals(varName))
			{
				component = names.item(i).getParentNode();
				return;
			}
		}
		throw new RuntimeException(varName + " : no this component!");
	}

	public Node getAttribute(String attrName)
	{
		return getAttribute(component, attrName);
	}

	private Node getAttribute(Node parent, String attrName)
	{
		Node n = parent.getFirstChild();
		while (null != n)
		{
			if (n.getNodeName().equals(attrName))
			{
				return n;
			}
			n = n.getNextSibling();
		}
		throw new RuntimeException(attrName + " : no this attribute!");
	}

	private int getInt(Node node)
	{
		return Integer.parseInt(node.getTextContent());
	}
	private Color getColor(String type)
	{
		Node bgColor = getAttribute(getAttribute("color"), type);
		if (bgColor == null)
			return Color.WHITE;
		int r = getInt(getAttribute(bgColor, "red"));
		int g = getInt(getAttribute(bgColor, "green"));
		int b = getInt(getAttribute(bgColor, "blue"));
		return new Color(r, g, b);
	}
	public Color getBgColor()
	{
		return getColor("background");
	}
	public Color getFocusColor()
	{
		return getColor("focus");
	}

	public Rectangle getBounds()
	{
		Node rectangle = getAttribute("rectangle");
		int x = getInt(getAttribute(rectangle, "x"));
		int y = getInt(getAttribute(rectangle, "y"));
		int width = getInt(getAttribute(rectangle, "width"));
		int height = getInt(getAttribute(rectangle, "height"));
		return new Rectangle(x, y, width, height);
	}
	public Insets getInsets()
	{
		Node insets = getAttribute("insets");
		int top = getInt(getAttribute(insets, "top"));
		int left = getInt(getAttribute(insets, "left"));
		int bottom = getInt(getAttribute(insets, "bottom"));
		int right = getInt(getAttribute(insets, "right"));
		return new Insets(top, left, bottom, right);
	}
}
