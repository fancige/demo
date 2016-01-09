package com.fancige;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Transcoder
{
	private boolean isCancel = false;

	public void cancel()
	{
		isCancel = true;
	}

	/**
	 * Converts a file from a charset to another charset.
	 * 
	 * @param srcFile
	 *            The source file to transcode.
	 * @param targetDir
	 *            The target directory used to store the target file.<br>
	 *            null is allowed that indicates the source file will be
	 *            covered, alternatively, you can specify the target directory
	 *            which is same as the the source directory in which the source
	 *            file is.
	 * 
	 * @param srcCode
	 *            the charset name of the source file
	 * @param targetCode
	 *            the charset name of the target file
	 * @return <p>
	 *         true if this conversion succeeded, false if not.
	 *         <p>
	 *         <b> Note that </b> this will be false if the source charset is
	 *         same as the target charset or the source file and the target
	 *         directory are invalid (e.g. do not exist) or any exceptions occur
	 *         during the conversion.
	 */
	public boolean convert(File srcFile, File targetDir, String srcCode,
			String targetCode)
	{
		Charset srcSet;
		Charset tagSet;

		// Tests whether the charset names are valid
		try
		{
			srcSet = Charset.forName(srcCode);
			tagSet = Charset.forName(targetCode);

			if (srcSet.name().equals(tagSet.name()))
				return false;
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
			ThrowableController.displayException(e);
			return false;
		}

		// Tests whether the source file and target directory are valid
		if (!srcFile.isFile()
				|| (targetDir != null && !targetDir.isDirectory()))
			return false;

		// null indicate that the target file should cover the source
		// file
		if (targetDir == null)
			targetDir = srcFile.getParentFile();

		File tagFile = new File(targetDir.getPath() + File.separator
				+ srcFile.getName());

		// test whether the source file should be covered,
		// if true, the source file will be renamed as a temp file,
		// after the conversion, it will be deleted

		boolean isCover = false;
		String oldName = srcFile.getAbsolutePath();
		if (tagFile.equals(srcFile))
		{
			isCover = true;
			int count = 0;
			while (true)
			{
				File f = new File(srcFile.getParent() + File.separator
						+ Math.random() + srcFile.getName());
				if (srcFile.renameTo(f))
				{
					srcFile = f;
					break;
				}
				count++;
				if (count > 10)
				{
					throw new RuntimeException("覆盖文件时需要将源文件重命名，重命名失败，转换失败。");
				}
			}
		}

		try (FileInputStream fin = new FileInputStream(srcFile))
		{
			String utf8 = Charset.forName("utf-8").name();
			if (utf8.equals(srcSet.name()))
			{
				// Tests whether there is a byte-order mark
				try (FileInputStream fin2 = new FileInputStream(srcFile))
				{
					byte[] bs = new byte[3];
					fin2.read(bs);
					// EF BB BF(byte-order mark)
					if (-17 == bs[0] && -69 == bs[1] && -65 == bs[2])
					{
						// Removes the byte-order mark
						fin.read(bs);
					}
				}
			}
			try (InputStreamReader in = new InputStreamReader(fin, srcSet))
			{

				// start to write
				try (FileOutputStream out = new FileOutputStream(tagFile))
				{
					// Adds the byte-order mark(EF BB BF) to the head of the
					// target file when the target charset is utf-8
					byte[] mark = { -17, -69, -65 };
					if (utf8.equals(tagSet.name()))
					{
						out.write(mark);
					}

					// read 0.5MB each time
					int large = 512 * 512;
					char[] cs = new char[large];
					int length;
					while (-1 != (length = in.read(cs)))
					{
						if(isCancel)
						{
							fin.close();
							out.close();
							tagFile.delete();
							if(isCover)
								srcFile.renameTo(new File(oldName));
							return false;
						}
						out.write(new String(cs, 0, length).getBytes(tagSet));
						cs = new char[large];
					}
					out.flush();
				}

				// delete the source file if isCover is true
				if (isCover)
				{
					fin.close();
					Files.delete(Paths.get(srcFile.getAbsolutePath()));
				}
				return true;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			ThrowableController.displayException(e);
		}
		return false;
	}
}
