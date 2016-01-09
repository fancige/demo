package com.fancige.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.util.HashMap;

public class LineLayoutManager implements LayoutManager
{
	private boolean sizeUnknown = true;
	@SuppressWarnings("unused")
	private int minWidth = 0;
	@SuppressWarnings("unused")
	private int minHeight = 0;
	private int preferedWidth = 0;
	private int preferedHeight = 0;

	private HashMap<Component, HashMap<String, Integer>> consMap = new HashMap<>();

	public LineLayoutManager()
	{

	}

	@Override
	public void addLayoutComponent(String name, Component comp)
	{

	}

	@Override
	public void removeLayoutComponent(Component comp)
	{

	}

	private void setSize(Container parent)
	{
		preferedWidth = 0;
		preferedHeight = 0;
		minWidth = 0;
		minHeight = 0;
		int nComps = parent.getComponentCount();
		for (int i = 0; i < nComps; i++)
		{
			Component c = parent.getComponent(i);
			if (c.isVisible())
			{
				preferedHeight += c.getPreferredSize().getHeight();
				preferedWidth = Math.max(preferedWidth, c.getWidth());
			}
		}
	}

	@Override
	public Dimension preferredLayoutSize(Container parent)
	{
		setSize(parent);
		Insets insets = parent.getInsets();
		preferedWidth = preferedWidth + insets.left + insets.right;
		preferedHeight = preferedHeight + insets.top + insets.bottom;
		sizeUnknown = false;
		return new Dimension(preferedWidth, preferedHeight);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent)
	{
		return new Dimension(0, 0);
	}

	@Override
	public void layoutContainer(Container parent)
	{
		if (sizeUnknown)
			setSize(parent);

		Insets insets = parent.getInsets();
		int maxWidth = parent.getWidth() - insets.left - insets.right;
		int maxHeight = parent.getHeight() - insets.top - insets.bottom;
		int x = 0;
		int y = 0;
		int width = 0;
		int height = 0;
		int emptyWidth = maxWidth;
		@SuppressWarnings("unused")
		int emptyHeight = maxHeight;

		int nComps = parent.getComponentCount();
		for (int i = 0; i < nComps; i++)
		{
			Component comp = parent.getComponent(i);
			if (comp.isVisible())
			{
				height = comp.getPreferredSize().height;
				width = comp.getPreferredSize().width;
				comp.setBounds(x, y, width, height);
				y += height;

				emptyWidth -= width;
				emptyHeight -= height;
			}
		}
		if (emptyWidth > 0)
		{
			for (int i = 0; i < nComps; i++)
			{
				Component comp = parent.getComponent(i);
				consMap.get(comp).get("weigth");
			}
		}
	}

	public void setConstraints(Component comp, LineLayoutConstraints cons)
	{
		HashMap<String, Integer> map = new HashMap<>();
		map.put("weightX", cons.weightX);
		map.put("weightY", cons.weightY);
		consMap.put(comp, map);
	}
}
