package com.fancige.util;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class MyResource
{
	public static ImageIcon getIcon(String path)
	{
		return new ImageIcon(MyResource.getResource(path));
	}

	public static Image getImage(String path)
	{
		try (InputStream in = MyResource.class.getResourceAsStream(path))
		{
			return ImageIO.read(in);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] getResource(String path)
	{
		List<byte[]> list = new ArrayList<>();
		int unit = 10240;
		try (InputStream in = MyResource.class.getResourceAsStream(path))
		{
			int len = 0;
			int tmpLen = 0;
			byte[] tmp = new byte[unit];
			while (-1 != (tmpLen = in.read(tmp)))
			{
				list.add(tmp);
				len = tmpLen;
				tmp = new byte[unit];
			}
			byte[] result = new byte[(list.size() - 1) * unit + len];
			int k = 0;
			for (int i = 0; i < list.size(); i++)
			{
				for (int j = 0; j < unit && k < result.length; j++, k++)
				{
					result[k] = list.get(i)[j];
				}
			}
			return result;

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
