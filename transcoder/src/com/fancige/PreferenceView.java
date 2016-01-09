package com.fancige;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class PreferenceView
{
	private static final int AREAEXTS_WIDTH = 382;
	private static final int AREAEXTS_HEIGHT = 64;
	public JDialog dialog;

	public JRadioButton radioCover;

	public JRadioButton radioDir;

	public JTextField fieldDir;

	public JButton btnDir;

	public JTextArea areaExts;

	public JTextField fieldTip;

	public JButton btnOK;

	public JButton btnNo;

	public PreferenceView(Window parent)
	{
		initComponents(parent);
	}

	private void initComponents(Window parent)
	{
		dialog = new JDialog(parent);
		dialog.setLayout(new GridBagLayout());

		// panel1
		JPanel p1 = new JPanel(new GridBagLayout());
		p1.setBorder(new TitledBorder("保存目录"));

		// radio buttons
		radioCover = new JRadioButton("覆盖原文件");
		radioDir = new JRadioButton("选择目录");
		ButtonGroup group = new ButtonGroup();
		group.add(radioCover);
		group.add(radioDir);

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(3, 3, 3, 3);
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;

		// add radioCover
		c.gridx = 0;
		c.gridy = 0;
		p1.add(radioCover, c);

		// add radioDir
		c.gridy = 1;
		p1.add(radioDir, c);

		// add fieldDir
		c.weightx = 10000;
		c.gridx = 1;
		fieldDir = new JTextField();
		p1.add(fieldDir, c);
		c.weightx = 1;

		// add btnOpen
		c.gridx = 2;
		btnDir = new JButton("浏览");
		p1.add(btnDir, c);

		// panel2
		JPanel p2 = new JPanel(new GridBagLayout());
		p2.setBorder(new TitledBorder("扩展名选择"));

		// label
		c.gridx = 0;
		c.gridy = 0;
		JLabel lab = new JLabel("自定义文本文件扩展名，扩展名之间用空格隔开, 如，\"txt java lrc\"。");
		p2.add(lab, c);

		// textarea
		areaExts = new JTextArea();
		areaExts.setLineWrap(true);
		JScrollPane sp = new JScrollPane(areaExts);
		sp.setPreferredSize(new Dimension(AREAEXTS_WIDTH, AREAEXTS_HEIGHT));
		c.gridy = 1;
		p2.add(sp, c);

		// panel3
		JPanel p3 = new JPanel(new GridBagLayout());
		btnOK = new JButton("确定");
		btnNo = new JButton("取消");

		fieldTip = new JTextField();
		fieldTip.setOpaque(false);
		fieldTip.setBorder(null);
		fieldTip.setEditable(false);
		fieldTip.setForeground(Color.RED);

		// add fieldTip
		c.weightx = 10000;
		c.insets = new Insets(3, 10, 3, 3);
		c.gridx = 0;
		c.gridy = 0;
		p3.add(fieldTip, c);
		c.weightx = 1;
		c.insets = new Insets(3, 3, 3, 3);
		c.weightx = 1;

		// add btnOk
		c.gridx = 1;
		p3.add(btnOK, c);

		// add btnNo
		c.gridx = 2;
		p3.add(btnNo, c);

		// add panel1
		c.insets = new Insets(10, 3, 3, 3);
		c.gridx = 0;
		c.gridy = 0;
		dialog.getContentPane().add(p1, c);

		// add panel2
		c.insets = new Insets(20, 3, 3, 3);
		c.gridy = 1;
		dialog.getContentPane().add(p2, c);

		// add panel3
		c.insets = new Insets(3, 3, 3, 6);
		c.gridy = 2;
		dialog.getContentPane().add(p3, c);

		// add empty panel
		c.gridy = 3;
		c.weighty = 10000;
		dialog.getContentPane().add(new JPanel(), c);

		// set up dialog
		dialog.setTitle("设置");
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.pack();
		dialog.setMinimumSize(dialog.getSize());
		dialog.setLocationRelativeTo(parent);
	}
}
