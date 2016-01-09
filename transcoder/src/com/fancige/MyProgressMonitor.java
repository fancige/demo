package com.fancige;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class MyProgressMonitor
{
	private JDialog dialog;
	private JLabel labMess;
	private JLabel labNote;
	private JProgressBar bar;
	private JButton btnCancel;
	private int max;

	private static final int DEFAULT_WIDTH = 400;
	private static final int DEFAULT_HEIGHT = 160;

	public MyProgressMonitor(Window parent, int min, int max)
	{
		this.max = max;

		// west
		JPanel west = new JPanel();
		west.add(new JLabel(UIManager.getIcon("OptionPane.informationIcon")));

		// center
		JPanel center = new JPanel(new GridBagLayout());
		center.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagConstraints c = new GridBagConstraints();

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(3, 3, 3, 3);
		c.weightx = 1;
		c.weighty = 1;

		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.WEST;
		labMess = new JLabel();
		center.add(labMess, c);

		labNote = new JLabel();
		center.add(labNote, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		bar = new JProgressBar(min, max);
		center.add(bar, c);

		// south
		JPanel south = new JPanel();
		btnCancel = new JButton(
				UIManager.getString("OptionPane.cancelButtonText"));
		south.add(btnCancel);

		dialog = new JDialog(parent);
		JPanel root = new JPanel(new BorderLayout());
		root.setBorder(new EmptyBorder(5, 5, 10, 5));
		root.add(west, BorderLayout.WEST);
		root.add(center, BorderLayout.CENTER);
		root.add(south, BorderLayout.SOUTH);
		dialog.getContentPane().add(root);

		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		dialog.setLocationRelativeTo(parent);
	}

	private boolean isShow = false;

	public void show()
	{
		if (!isShow)
			new Thread()
			{
				@Override
				public void run()
				{
					isShow = true;
					dialog.setModalityType(ModalityType.APPLICATION_MODAL);
					dialog.setVisible(true);
				}
			}.start();
	}

	public void setNote(String note)
	{
		labNote.setText(note);
	}

	public void setMessage(String message)
	{
		labMess.setText(message);
	}

	public void setProgress(int value)
	{
		if (value >= max)
			close();
		else
			bar.setValue(value);
	}

	public void addCancelListener(ActionListener listener)
	{
		btnCancel.addActionListener(listener);
	}

	public void close()
	{
		dialog.dispose();
	}
}
