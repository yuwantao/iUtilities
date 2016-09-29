package com;

import com.utils.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by yuwt on 2016/6/3.
 */
public class FileTest {
	@Test
	public void rename_file() {
		String path = "e:\\download\\zgjjdb\\中计报20#pdf\\001封面.pdf";
		File file = new File(path);
		System.out.println();
		file.renameTo(new File(file.getParent() + "\\001.pdf"));
	}

	@Test
	public void test_convert_image_to_pdf() throws IOException {
		String path = "e:\\download\\crawler-newspaper\\zgjsjb\\2016-05-16\\中计报第18#pdf\\";
		File dir = new File(path);
		for (File f : dir.listFiles()) {
			String fName = f.getName();
			if (fName.endsWith("jpg") || fName.endsWith("tif")) {
				PDDocument pdDocument = new PDDocument();
				BufferedImage image = ImageIO.read(f);
				int width = image.getWidth();
				int height = image.getHeight();
				PDPage pdPage = new PDPage(new PDRectangle(754, 924));
				pdDocument.addPage(pdPage);
				PDImageXObject pdImageXObject = LosslessFactory.createFromImage(pdDocument, image);
				PDPageContentStream contentStream = new PDPageContentStream(pdDocument, pdPage);
				contentStream.drawImage(pdImageXObject, 0, 0, 754, 924);
				contentStream.close();
				pdDocument.save(path + File.separator + fName.substring(0, fName.length() - 4) + ".pdf");
				pdDocument.close();
			}
		}
	}

	@Test
	public void windows_vs_linux() throws IOException {
		FileUtils.createFile("/data/abc.txt");
	}

	@Test
	public void test() throws FileNotFoundException {
		FileInputStream inputStream = new FileInputStream("/data/pr/pis/PIS-024 Professional Photography.xls");
		assert inputStream != null;
	}

	@Test
	public void compare_two_files() throws IOException {
		File file = new File("e://download//amazon_us_50w_books//elsevier.txt");
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		List<String> all = new ArrayList<>();
		String line;
		Collection c;
		while ((line = bufferedReader.readLine()) != null) {
			all.add(line);
		}

		file = new File("e://download//amazon_us_50w_books//crawled.txt");
		fileReader = new FileReader(file);
		bufferedReader = new BufferedReader(fileReader);
		List<String> crawled = new ArrayList<>();
		while ((line = bufferedReader.readLine()) != null) {
			crawled.add(line);
		}

		for (String isbn : all) {
			if (!crawled.contains(isbn)) {
				System.out.println(isbn);
			}
		}
	}

	@Test
	public void test_sort() {
		String path = "e:\\download\\crawler-newspaper\\zgjsjb\\2016-05-16\\中计报第18#pdf\\";
		File dir = new File(path);
		for (File f : dir.listFiles()) {
			String name = f.getName();
			String[] split = name.split("-");
			if (name.endsWith(".pdf") && split[0].length() ==1) {
				f.renameTo(new File(f.getParent() + File.separator + "0" + name));
			}
		}

		List<File> pdfs = new ArrayList<>();
		for (File f : dir.listFiles()) {
			if (f.getName().endsWith(".pdf")) {
				pdfs.add(f);
			}
		}

		Collections.sort(pdfs);
//		Arrays.sort(pdfs.toArray());
		for (File f : pdfs) {
			System.out.println(f.getName());
		}
	}
}
