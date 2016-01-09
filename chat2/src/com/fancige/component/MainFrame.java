package com.fancige.component;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.w3c.dom.Document;

import com.fancige.bean.UserInfo;
import com.fancige.util.ComponentManager;
import com.fancige.util.DocumentManager;
import com.fancige.util.PathManager;

public class MainFrame
{
	private JFrame frame;
	private JPanel rootPanel;
	private JPanel topPanel;
	private JScrollPane middlePane;
	private JPanel bottomPanel;

	private HashMap<String, ArrayList<UserInfo>> map = new HashMap<>();
	private HashMap<String, Boolean> vis = new HashMap<>();

	public MainFrame()
	{
		init();
	}

	private ComponentManager getManager(String varName)
	{
		Document doc = DocumentManager.readDocument(PathManager
				.getPath("MainFrame.xml"));
		return new ComponentManager(doc, varName);
	}

	private void init()
	{

		frame = new JFrame();
		// rootPanel

		ComponentManager cm = getManager("rootPanel");

		rootPanel = new JPanel(new GridBagLayout());
		rootPanel.setBackground(cm.getBgColor());
		rootPanel.setPreferredSize(cm.getBounds().getSize());
		frame.getContentPane().add(rootPanel);

		GridBagConstraints c = new GridBagConstraints();

		// topPanel

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		cm = getManager("topPanel");
		topPanel = new JPanel();
		topPanel.setPreferredSize(cm.getBounds().getSize());
		topPanel.setBackground(cm.getBgColor());
		rootPanel.add(topPanel, c);

		// middlePane

		cm = getManager("middlePane");
		middlePane = new JScrollPane();
		middlePane.setPreferredSize(cm.getBounds().getSize());
		c.weighty = 1;
		rootPanel.add(middlePane, c);

		// bottomPanel

		bottomPanel = new JPanel();
		cm = getManager("bottomPanel");
		bottomPanel.setBackground(cm.getBgColor());
		bottomPanel.setPreferredSize(cm.getBounds().getSize());
		c.weighty = 0;
		rootPanel.add(bottomPanel, c);

		frame.pack();
	}
	
	public void showFrame()
	{
		frame.setVisible(true);
	}

	private boolean isChildVisible(String parent)
	{
		return vis.get(parent);
	}

	private void changeChildVisible(String parent)
	{
		vis.put(parent, !isChildVisible(parent));
		updateList();
	}

	/**
	 * Creates a label based on its ComponentManager.
	 */
	private JLabel makeLabel(ComponentManager cm)
	{
		JLabel label = new JLabel();
		label.setPreferredSize(cm.getBounds().getSize());
		label.setBackground(cm.getBgColor());
		label.setBorder(new EmptyBorder(cm.getInsets()));
		label.setOpaque(true);
		return label;
	}

	private JLabel createParentLabel(String name)
	{
		final JLabel label = makeLabel(getManager("parentLabel"));

		label.setName(name);

		if (0 == map.get(name).size())
			label.setText("- " + name);
		else if (isChildVisible(name))
			label.setText("- " + name);
		else
			label.setText("+ " + name);

		label.addMouseListener(new MouseAdapter()
		{
			Color preBg = label.getBackground();

			@Override
			public void mouseClicked(MouseEvent e)
			{
				Point p = middlePane.getViewport().getViewPosition();
				changeChildVisible(label.getName());
				middlePane.getViewport().setViewPosition(p);
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				preBg = label.getBackground();
				label.setBackground(new Color(207, 215, 241));
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				label.setBackground(preBg);
			}
		});
		return label;
	}

	private JLabel createChildLabel(UserInfo info)
	{
		final JLabel label = makeLabel(getManager("childLabel"));
		label.setText(info.getName());
		label.setName(info.getName());
		label.addMouseListener(new MouseAdapter()
		{
			Color preColor = label.getBackground();

			@Override
			public void mouseClicked(MouseEvent e)
			{
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				preColor = label.getBackground();
				label.setBackground(new Color(237, 240, 220));
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				label.setBackground(preColor);
			}

		});
		return label;
	}
	
	public JFrame getMainFrame()
	{
		return frame;
	}

	public void addParentLabel(String name)
	{
		if (!map.containsKey(name))
		{
			map.put(name, new ArrayList<UserInfo>());
			vis.put(name, Boolean.FALSE);
			updateList();
		}
	}

	public void removeParentLabel(String name)
	{
		if (map.containsKey(name))
		{
			map.remove(name);
			vis.remove(name);
			updateList();
		}
	}

	public void addChildLabel(UserInfo info)
	{
		String parent = info.getParent();
		if (map.containsKey(parent))
		{
			map.get(parent).add(info);
			updateList();
		}
	}

	public void removeChildLabel(UserInfo info)
	{
		String parent = info.getParent();
		if (map.containsKey(parent))
		{
			map.get(parent).remove(info);
			updateList();
		}
	}

	private void updateList()
	{
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(Color.WHITE);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;

		Set<String> parents = map.keySet();
		for (String parent : parents)
		{
			panel.add(createParentLabel(parent), c);
			if (isChildVisible(parent))
			{
				List<UserInfo> infos = map.get(parent);
				for (UserInfo info : infos)
				{
					panel.add(createChildLabel(info), c);
				}
			}
		}

		// just fills the panel.
		c.weighty = 1;
		panel.add(new JLabel(), c);
		middlePane.setViewportView(panel);
	}
}