package com.fancige.manager;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class FileManager {

	private String queryResult;
	public static final String ROOT_PATH = "E:/workspace/bbs2/data";
	public static final String DISPOSITION_PATH = ROOT_PATH + "/disposition/";
	public static final String LOCALE_PATH = ROOT_PATH + "/locale/";
	public static final String LOG_PATH = ROOT_PATH + "/log/";

	/**
	 * 
	 * @param file
	 *            The path starting to query.
	 * @param target
	 *            The name of the target file.
	 */
	private void query(File file, String target) {
		// If queryResult is not null, ok, finds it, stops the query.
		if (null == queryResult) {
			if (file.getName().equalsIgnoreCase(target)) {
				queryResult = file.getAbsolutePath();
				return;
			}
			File[] fs = file.listFiles();

			if (null != fs) {
				for (File f : fs) {
					query(f, target);
				}
			}
		}
	}

	/**
	 * Gets the path by the <code>filename</code>.
	 * 
	 * @param filename
	 *            The file name.
	 * @return The file absolute path or null if the file does not exist.
	 */
	public static String getPath(String filename) {
		FileManager fm = new FileManager();
		fm.query(new File(ROOT_PATH), filename);
		return fm.queryResult;
	}

	public static File[] listFiles() {
		File dir = new File(DISPOSITION_PATH);
		File[] files = dir.listFiles();
		Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File f1, File f2) {
				return f1.lastModified() > f2.lastModified() ? -1 : 1;
			}
		});
		return files;
	}

	public static boolean exist(String filename) {
		File dir = new File(DISPOSITION_PATH);
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.getName().equalsIgnoreCase(filename)) {
				return true;
			}
		}
		return false;
	}
}
