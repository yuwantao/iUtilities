package com.pdf.merge;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuwt on 2016/5/11.
 */
public class MergePdf {
	public static void main(String[] args) {
		List<File> sources = getSources();
		PDFMergerUtility ut = new PDFMergerUtility();
		sources.forEach(s -> {
			try {
				ut.addSource(s);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		});
		try {
			ut.setDestinationFileName("E:\\download\\pdf_merge\\merge.pdf");
//			MemoryUsageSetting mus = MemoryUsageSetting.setupMixed(1024 * 1024 * 1024, 2 * 1024 * 1024 * 1024);
			MemoryUsageSetting mus = MemoryUsageSetting.setupTempFileOnly();
			mus.setTempDir(new File("E:\\download\\pdf_merge\\tmp"));
//			MemoryUsageSetting mus = MemoryUsageSetting.setupMainMemoryOnly();
			ut.mergeDocuments(mus);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static List<File> getSources() {
		List<File> files = new ArrayList<>();
		try {
			Files.walk(Paths.get("e:\\download\\crawler-newspaper\\human-nature\\test\\")).forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					files.add(filePath.toFile());
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return files;
	}
}
