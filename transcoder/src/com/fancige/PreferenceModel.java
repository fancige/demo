package com.fancige;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.Properties;
import java.util.prefs.Preferences;

/**
 * <p>
 * Provides three ways to read and store an application's preferences
 * <p>
 * <ul>
 * <li>use {@link java.util.prefs.Preferences}</li>
 * <li>use a properties file located in user home directory</li>
 * <li>use a properties file located in current directory</li>
 * </ul>
 * 
 * @author fancige
 * 
 */
public class PreferenceModel
{
	private Properties props;

	// property names
	private static final String KEY_SAVE_DIRECTORY = "KEY_SAVE_DIRECTORY";
	private static final String KEY_LAST_DIRECTORY = "KEY_LAST_DIRECTORY";
	private static final String KEY_TEXT_EXTENSIONS = "KEY_TEXT_EXTENSIONS";
	private static final String KEY_IS_COVER = "KEY_IS_COVER";

	private static File propsFile;
	private static String propsName = "文本编码转换器.properties";

	private static Preferences prefs;
	private static String prefsName = "/com/fancige";

	public PreferenceModel()
	{
		if (prefs == null && propsFile == null)
			init();
		load();
	}

	private static void init()
	{
		// determine which way to read preferences
		try
		{
			// use java.util.prefs.Preferences
			Preferences root = Preferences.userRoot();
			root.nodeExists(prefsName);
			prefs = root.node(prefsName);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			ThrowableController.displayException(e);

			// tell the user the first way can not be used
			String tip = "程序的配置文件不能正常读取，已使用备用方式读取.\n"
					+ "如果你在使用windows操作系统，请考虑将注册表项\n"
					+ "HKEY_CURRENT_USER\\Software\\JavaSoft\n"
					+ "HKEY_LOCAL_MACHINE\\SOFTWARE\\JavaSoft\n"
					+ "的当前用户权限设置为完全控制。";
			ThrowableController.display(tip);
			String targetDir;

			if (System.getProperty("user.home") != null)
			{
				// use the properties file located in user home directory
				targetDir = System.getProperty("user.home");
			}
			else
			{
				// use the properties file located in current directory
				targetDir = new File("").getAbsolutePath();
			}

			propsFile = new File(targetDir + File.separator + propsName);
		}
	}

	private void load()
	{
		props = new Properties();

		if (prefs != null)
		{
			String[] keys = getKeys();
			for (String key : keys)
			{
				props.put(key, prefs.get(key, getDefault(key)));
			}
		}
		else if (propsFile.exists())
		{
			try (FileInputStream in = new FileInputStream(propsFile))
			{
				props.load(in);
			}
			catch (IOException e1)
			{
				// tell the user loading the configuration information
				// failed, default will be used
				e1.printStackTrace();
				ThrowableController.displayException(e1);

				String tip = "配置文件读取失败，程序将使用默认设置启动。\n";
				ThrowableController.display(tip);
			}
		}

		validate();
	}

	private void validate()
	{
		String[] keys = getKeys();
		for (String key : keys)
		{
			if (!props.containsKey(key))
				props.put(key, getDefault(key));
		}
	}

	private static String[] getKeys()
	{
		Field[] fs = PreferenceModel.class.getDeclaredFields();
		LinkedList<String> list = new LinkedList<>();
		for (Field f : fs)
		{
			if (f.getName().startsWith("KEY"))
				list.add(f.getName());
		}
		return list.toArray(new String[] {});
	}

	private static String getDefault(String key)
	{
		if (KEY_SAVE_DIRECTORY == key || KEY_LAST_DIRECTORY == key)
		{
			return new File("").getAbsolutePath();
		}
		else if (KEY_TEXT_EXTENSIONS == key)
		{
			return "txt java lrc";
		}
		else if (KEY_IS_COVER == key)
		{
			return "false";
		}
		else
		{
			return "";
		}
	}

	public String getSaveDir()
	{
		return props.getProperty(KEY_SAVE_DIRECTORY);
	}

	public void setSaveDir(String dir)
	{
		props.setProperty(KEY_SAVE_DIRECTORY, dir);
	}

	public boolean isCover()
	{
		return "true".equals(props.getProperty(KEY_IS_COVER)) ? true : false;
	}

	public void setCover(boolean cover)
	{
		props.setProperty(KEY_IS_COVER, String.valueOf(cover));
	}

	public String[] getExtensions()
	{
		String extString = props.getProperty(KEY_TEXT_EXTENSIONS);
		// all control characters
		String[] exts = extString.split("[\\u0000-\\u0020|\\u007F]+");
		LinkedList<String> list = new LinkedList<>();
		for (String ext : exts)
		{
			if (!ext.isEmpty())
				list.add(ext);
		}
		return list.toArray(new String[] {});
	}

	public String getExtensionString()
	{
		return props.getProperty(KEY_TEXT_EXTENSIONS);
	}

	public void setExtensionString(String ext)
	{
		props.setProperty(KEY_TEXT_EXTENSIONS, ext);
	}

	public String getLastDir()
	{
		return props.getProperty(KEY_LAST_DIRECTORY);
	}

	public void setLastDir(String dir)
	{
		props.setProperty(KEY_LAST_DIRECTORY, dir);
	}

	public boolean store()
	{
		if (prefs != null)
		{
			String[] keys = getKeys();
			for (String key : keys)
			{
				prefs.put(key, props.getProperty(key));
			}
			return true;
		}
		else
		{
			try (FileOutputStream out = new FileOutputStream(propsFile))
			{
				props.store(out, null);
				return true;
			}
			catch (IOException e)
			{
				e.printStackTrace();
				ThrowableController.displayException(e);
				String tip = "配置文件写入失败！文件存储路径：" + propsFile.getAbsolutePath();
				ThrowableController.display(tip);
				return false;
			}
		}
	}

	/**
	 * Returns true if and only if the two Properties object maintained by the
	 * two models are equal.
	 */
	@Override
	public boolean equals(Object model)
	{
		if (model instanceof PreferenceModel)
		{
			Properties props = ((PreferenceModel) model).props;
			return this.props.equals(props);
		}
		return false;
	}
}
