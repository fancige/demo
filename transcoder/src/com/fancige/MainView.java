package com.fancige;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class MainView
{
	private static final int DEFAULT_WIDTH = 400;
	private static final int DEFAULT_HEIGHT_TOP = 200;
	private static final int DEFAULT_HEIGHT_BOTTOM = 100;

	/**
	 * The main frame.
	 */
	public JFrame frame;

	/**
	 * A display screen.
	 */
	public JTextArea screen;

	/**
	 * A comboBox containing the names of source charset.
	 */
	public JComboBox<String> boxFrom;

	/**
	 * A comboBox containing the names of target charset.
	 */
	public JComboBox<String> boxTo;

	/**
	 * A button used to open the preferences dialog.
	 */
	public JButton btnSet;

	/**
	 * A button used to remove all files.
	 */
	public JButton btnRemove;

	/**
	 * A button used to clear the display screen.
	 */
	public JButton btnClear;

	/**
	 * A button used to add files or directories.
	 */
	public JButton btnAdd;

	/**
	 * A button used to start the transcoding.
	 */
	public JButton btnStart;

	public MainView()
	{
		initComponents();
	}

	private void initComponents()
	{
		// main frame
		frame = new JFrame();
		frame.setLayout(new GridBagLayout());

		// display panel
		JPanel displayPanel = new JPanel(new BorderLayout());
		displayPanel.setPreferredSize(new Dimension(DEFAULT_WIDTH,
				DEFAULT_HEIGHT_TOP));
		screen = new JTextArea();
		screen.setEditable(false);
		JScrollPane pane = new JScrollPane(screen);
		displayPanel.add(pane);

		// control panel
		JPanel controlPanel = new JPanel(new GridBagLayout());
		controlPanel.setPreferredSize(new Dimension(DEFAULT_WIDTH,
				DEFAULT_HEIGHT_BOTTOM));
		controlPanel.setBorder(new TitledBorder("选择编码方式："));

		// charset choosers
		String[] charsets = { "GBK", "UTF-8", "Unicode" };
		boxFrom = new JComboBox<>(charsets);
		boxTo = new JComboBox<>(charsets);

		// text labels
		JLabel labFrom = new JLabel("从");
		JLabel labTo = new JLabel("到");

		// set button
		btnSet = new JButton("设置");

		// remove button
		btnRemove = new JButton("清空");
		btnRemove.setToolTipText("移除所有已添加的文件");

		// clear button
		btnClear = new JButton("清屏");
		btnClear.setToolTipText("清除屏幕上的所有内容");

		// add button
		btnAdd = new JButton("添加");
		btnAdd.setToolTipText("添加文件");

		// start button
		btnStart = new JButton("开始");
		btnStart.setToolTipText("开始转换");

		// lay out the control panel
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 1;

		// x = 0
		c.anchor = GridBagConstraints.EAST;

		c.gridx = 0;
		c.gridy = 0;
		controlPanel.add(labFrom, c);

		c.gridx = 0;
		c.gridy = 1;
		controlPanel.add(labTo, c);

		c.anchor = GridBagConstraints.CENTER;

		// x = 1
		c.gridx = 1;
		c.gridy = 0;
		controlPanel.add(boxFrom, c);

		c.gridy = 1;
		controlPanel.add(boxTo, c);

		// x = 2
		c.gridx = 2;
		c.gridy = 0;
		controlPanel.add(btnSet, c);

		c.gridy = 1;
		controlPanel.add(btnAdd, c);

		// x = 3
		c.gridx = 3;
		c.gridy = 0;
		controlPanel.add(btnRemove, c);

		c.gridy = 1;
		controlPanel.add(btnStart, c);

		// x = 4
		c.gridx = 4;
		c.gridy = 0;
		controlPanel.add(btnClear, c);

		// layout the main frame
		c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = 1000;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.BOTH;
		frame.getContentPane().add(displayPanel, c);

		c.insets = new Insets(5, 0, 0, 0);
		c.fill = GridBagConstraints.NONE;
		c.weighty = 1;
		frame.getContentPane().add(controlPanel, c);

		// set the main frame
		frame.setTitle("文本编码转换");
		frame.setIconImage(new ImageIcon(ThrowableController.class
				.getResource("/icon.jpg")).getImage());
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.pack();
		frame.setMinimumSize(frame.getSize());
		frame.setLocationRelativeTo(null);
	}
}
