package com;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by yuwt on 2016/6/1.
 */
public class Tmp {
	public static void main(String[] args) throws IOException {
		final PDFMergerUtility ut = new PDFMergerUtility();
		String path = "e:\\download\\zgjjdb\\2891\\";
		File dir = new File(path);
		File[] files = dir.listFiles();
		Arrays.sort(files);
		for (File f : files) {
			ut.addSource(f);
		}

		ut.setDestinationFileName(path + "CHINA ECONOMIC HERALD_20160531.pdf");
		MemoryUsageSetting mus = MemoryUsageSetting.setupMixed(1024 * 1024 * 1024, 3 * 1024 * 1024 * 1024);
		mus.setTempDir(new File("e:\\download\\ftp"));
		ut.mergeDocuments(mus);
	}
}
