package com.utils;

import java.io.File;
import java.io.IOException;

/**
 * Created by yuwt on 2016/5/12.
 */
public class FileUtils {
	/**
	 * 根据给定的文件路径生成文件.
	 *
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static File createFile(final String filePath) throws IOException {
		final File file = new File(filePath);
		if (file.isFile()) {
			return file;
		}
		if (!file.getParentFile().isDirectory()) {
			file.getParentFile().mkdirs();
		}
		if (!file.isFile()) {
			if (file.createNewFile()) {
				return file;
			}
		}
		return null;
	}
}
