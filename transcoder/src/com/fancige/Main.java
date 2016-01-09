package com.fancige;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JDialog;
import javax.swing.UIManager;

public class Main
{
	public static void main(String args[])
	{
		System.out.println("start.");

		// the application will start with a dialog telling the user it has been
		// started, do this because showing the main frame will take some time.

		final int width = 300, height = 200;
		final JDialog dialog = new JDialog()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void paint(Graphics g)
			{
				g.setColor(Color.GRAY);
				g.fillRect(0, 0, width, height);
				g.setColor(Color.WHITE);
				g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
				g.drawString("正在启动，请稍候...", 50, 110);
			}
		};
		dialog.setSize(width, height);
		dialog.setUndecorated(true);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);

		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			ThrowableController.displayException(e);
		}

		// all uncaught exceptions will display in a same dialog during the run
		// of application.
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
		{
			@Override
			public void uncaughtException(Thread t, Throwable e)
			{
				e.printStackTrace();
				ThrowableController.displayException(e, true);
			}
		});

		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				MainController main = new MainController();
				dialog.dispose();
				main.show();
			}
		});
	}
}
