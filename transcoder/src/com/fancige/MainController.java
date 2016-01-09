package com.fancige;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainController
{
	private LinkedList<File> list;
	private String[] charsets = { "GBK", "UTF-8", "Unicode" };

	private int boxFromIndex = 0;
	private int boxToIndex = 0;
	private JFileChooser chooser;

	private FileFilter textFilter;
	private File saveDir;

	private MainView view;

	public MainController()
	{
		initData();
		initComponents();
		initView();
		initPreferences();
	}

	private class ThreadWorker extends Thread
	{
		int finish = 0;
		int success = 0;
		boolean isCancel = false;
		Transcoder coder;

		@Override
		public void run()
		{
			display("\n任务已开始...");
			final MyProgressMonitor monitor = new MyProgressMonitor(view.frame,
					0, list.size());
			monitor.addCancelListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if (coder != null)
						coder.cancel();
					isCancel = true;
					monitor.close();
				}
			});
			monitor.setMessage("任务进行中，请稍候...");
			monitor.show();
			for (int i = 0; i < list.size() && !isCancel; i++)
			{
				coder = new Transcoder();
				monitor.setNote("正在转换：" + list.get(i).getName());
				if (coder.convert(list.get(i), saveDir, charsets[boxFromIndex],
						charsets[boxToIndex]))
				{
					success++;
				}
				finish++;
				monitor.setProgress(finish);
			}

			String mess;
			if (isCancel)
			{
				mess = "任务已取消！";
			}
			else
			{
				mess = "任务已完成！";
			}

			display(mess + "共转换：" + finish + " 成功：" + success + " 失败："
					+ (finish - success));

			list.clear();
			view.frame.setEnabled(true);
			view.frame.toFront();
		}
	}

	private void initData()
	{
		list = new LinkedList<File>();
	}

	private void initPreferences()
	{
		PreferenceModel pm = new PreferenceModel();

		if (pm.isCover())
			saveDir = null;
		else
			saveDir = new File(pm.getSaveDir());

		chooser.removeChoosableFileFilter(textFilter);
		textFilter = new FileNameExtensionFilter("文本文件", pm.getExtensions());
		chooser.setFileFilter(textFilter);
		File dir = new File(pm.getLastDir());
		if (dir.isDirectory())
			chooser.setCurrentDirectory(dir);
	}

	private void initComponents()
	{
		chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(true);
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	}

	// add listeners for each needed component of MainView.
	private void initView()
	{
		view = new MainView();

		view.boxFrom.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				boxFromIndex = view.boxFrom.getSelectedIndex();
			}
		});

		view.boxTo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				boxToIndex = view.boxTo.getSelectedIndex();
			}
		});

		view.btnSet.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				PreferenceController pc = new PreferenceController(view.frame);

				// the preferences of the running application should be
				// reinitialized if the user made a change
				if (pc.isChanged())
					initPreferences();
			}

		});

		view.btnClear.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				EventQueue.invokeLater(new Runnable()
				{
					@Override
					public void run()
					{
						view.screen.setText("");
					}
				});
			}
		});

		view.btnRemove.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!list.isEmpty())
				{
					int option = JOptionPane.showConfirmDialog(view.frame,
							"确定要移除所有已添加的文件吗？", "注意", JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE);
					if (0 == option)
					{
						list.clear();
						display("清空完成！");
					}
				}
				else
				{
					display("列表文件为空！");
				}

			}
		});

		view.btnAdd.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int returnVal = chooser.showOpenDialog(view.frame);
				if (JFileChooser.APPROVE_OPTION == returnVal)
				{
					int size = list.size();
					addFiles(true, chooser.getSelectedFiles());
					display("增加：" + (list.size() - size) + "个文件");
				}
			}
		});

		view.btnStart.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (list.isEmpty())
				{
					JOptionPane.showMessageDialog(view.frame, "未添加任何文件！");
				}
				else if (boxFromIndex == boxToIndex)
				{
					JOptionPane.showMessageDialog(view.frame, "两种编码方式不能相同！");
				}
				else if (saveDir != null && !saveDir.isDirectory())
				{
					JOptionPane.showMessageDialog(view.frame,
							"保存目录无效，请在设置中修改！");
				}
				else
				{
					view.frame.setEnabled(false);
					new ThreadWorker().start();
				}
			}
		});

		view.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		view.frame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				if (!list.isEmpty())
				{
					int val = JOptionPane.showConfirmDialog(view.frame,
							"列表中还有文件没有转换，确定退出？");
					if (0 == val)
					{
						exit();
					}
				}
				else
				{
					exit();
				}
			}
		});
	}

	/**
	 * Terminates the application, the current directory of the {@link #chooser}
	 * will be saved.
	 */
	private void exit()
	{
		PreferenceModel model = new PreferenceModel();
		model.setLastDir(chooser.getCurrentDirectory().getAbsolutePath());
		model.store();
		System.exit(0);
	}

	/**
	 * Appends messages to the MainView.screen (a JTextArea)
	 */
	private void display(final String mess)
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				view.screen.append(mess + "\n");
				view.screen.setSelectionStart(view.screen.getText().length());
			}
		});
	}

	/**
	 * Adds files to the list from a set of files and directories.
	 * 
	 * @param enterDir
	 *            Determines if it should add the files of a directory. Note
	 *            that if the File is a directory, this method will call itself
	 *            again to add its all files(not directories) it contains but
	 *            just one time, which means all of files and directories of its
	 *            subdirectory will be ignored.
	 * @param files
	 *            A set of files and directories.
	 */
	private void addFiles(boolean enterDir, File... files)
	{
		for (int i = 0; i < files.length; i++)
		{
			File f = files[i];
			if (f.isDirectory())
			{
				if (enterDir)
					addFiles(false, f.listFiles());
			}
			else if (!list.contains(f))
			{
				// accept all files or only text files
				if (!textFilter.equals(chooser.getFileFilter())
						|| textFilter.accept(f))
				{
					list.add(f);
					display("已添加：" + f.getPath());
				}
			}
		}
	}

	/**
	 * Makes the main frame display
	 */
	public void show()
	{
		view.frame.setVisible(true);
	}
}
