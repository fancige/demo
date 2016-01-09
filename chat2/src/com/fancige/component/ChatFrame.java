package com.fancige.component;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ChatFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	public JScrollPane displaySP;
	public JTextArea display;
	public JScrollPane enterSP;
	public JTextArea enter;
	public JButton btnSend;
	public JButton btnClear;

	public ChatFrame()
	{
		init();
	}
	private void init()
	{
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();

		setLayout(gbl);
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 1;
		
		display = new JTextArea();
		display.setLineWrap(true);
		displaySP = new JScrollPane(display);
		gbc.weighty = 15;
		gbl.setConstraints(displaySP, gbc);
		add(displaySP);
		
		enter = new JTextArea();
		enter.setLineWrap(true);
		enterSP = new JScrollPane(enter);
		gbc.weighty = 5;
		gbl.setConstraints(enterSP, gbc);
		add(enterSP);
		
		btnSend = new JButton("发送");
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbl.setConstraints(btnSend, gbc);
		add(btnSend);
		
		btnClear = new JButton("清除");
		gbc.gridx = 5;
		gbc.anchor = GridBagConstraints.WEST;
		gbl.setConstraints(btnClear, gbc);
		add(btnClear);
		
		setSize(500, 400);
	}
	public static void main(String[] args)
	{
		new ChatFrame().setVisible(true);
	}
}
