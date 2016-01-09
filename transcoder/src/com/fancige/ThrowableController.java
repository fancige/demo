package com.fancige;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ThrowableController
{
	private static JFrame frame;
	private static JTextArea screen;
	static
	{
		frame = new JFrame();
		frame.setTitle("异常信息显示框");
		screen = new JTextArea();
		screen.setEditable(false);
		JScrollPane panel = new JScrollPane(screen);
		panel.setPreferredSize(new Dimension(500, 300));
		frame.add(panel);
		frame.pack();
	}

	/**
	 * @see ThrowableController#displayException(Throwable, boolean)
	 */
	public static void displayException(Throwable th)
	{
		String mess = Calendar.getInstance().getTime().toString() + "\n";
		mess = mess + th.toString() + "\n";
		StackTraceElement[] es = th.getStackTrace();
		for (StackTraceElement e : es)
		{
			mess = mess + "\t" + e.toString() + "\n";
		}
		display(mess + "\n");
	}

	/**
	 * Prints all Throwable information on a same frame, you can decide to
	 * terminate the application when the user closed the frame.
	 */
	public static void displayException(Throwable th, boolean exit)
	{
		displayException(th);
		if (exit == true)
		{
			frame.addWindowListener(new WindowAdapter()
			{
				@Override
				public void windowClosing(WindowEvent e)
				{
					System.exit(0);
				}
			});
		}
	}

	public static void display(final String text)
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				screen.append(text + "\n");
				screen.setSelectionStart(screen.getText().length());
				frame.setVisible(true);
			}
		});
	}
}
