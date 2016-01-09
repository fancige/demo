package com.fancige.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.fancige.bean.UserInfo;

public class MainFrameTest
{
	private static final String[] u1 = { "小红", "小明", "小樱", "小狼", "小李", "小小" };
	private static final String[] u2 = { "客服", "推销员", "维修员", "司机", "保安" };
	private static final String p1 = "我的好友";
	private static final String p2 = "临时会话";
	private static final List<UserInfo> l1 = new ArrayList<>();
	private static final List<UserInfo> l2 = new ArrayList<>();
	private static final List<Boolean> t1 = new ArrayList<>();
	private static final List<Boolean> t2 = new ArrayList<>();

	public static void showMainFrame()
	{
		final MainFrame mf = new MainFrame();
		mf.showFrame();
		mf.addParentLabel(p1);
		mf.addParentLabel(p2);

		for (int i = 0; i < u1.length; i++)
		{
			UserInfo user = new UserInfo();
			user.setName(u1[i]);
			user.setParent(p1);
			l1.add(user);
			t1.add(false);
		}

		for (int i = 0; i < u2.length; i++)
		{
			UserInfo user = new UserInfo();
			user.setName(u2[i]);
			user.setParent(p2);
			l2.add(user);
			t2.add(false);
		}

		new Thread()
		{
			public void run()
			{
				try
				{
					while (true)
					{
						sleep(2000);
						Random r = new Random();
						boolean b = r.nextBoolean();
						List<UserInfo> l = b ? l1 : l2;
						List<Boolean> t = b ? t1 : t2;
						int i = r.nextInt(l.size());
						if (t.get(i))
						{
							mf.removeChildLabel(l.get(i));
						}
						else
						{
							mf.addChildLabel(l.get(i));
						}
						t.set(i, !t.get(i));
					}
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			};
		}.start();
	}

	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
		showMainFrame();
	}
}
