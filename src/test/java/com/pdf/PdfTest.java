package com.pdf;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by yuwt on 2016/5/18.
 */
public class PdfTest {
	@Test
	public void calc_dpi() throws IOException {
		String path = "e:\\download\\tmp\\yilinxin\\";
		String fileName = "图解作文封面(1).pdf";
		File file = new File(path + File.separator + fileName);
		PDDocument document = PDDocument.load(file);
		PDPage page = document.getPage(0);
		PDRectangle cropBox = page.getCropBox();
		float width = cropBox.getWidth();
		float height = cropBox.getHeight();
		System.out.println("width: " + width);
		System.out.println("height: " + height);
	}

	@Test
	public void test_number_of_pages() throws IOException {
		String path = "e:\\download\\pr\\crawler-newspaper\\";
		String fileName = "BEIJING REVIEW_ENGLISH_20160908.pdf";
		File file = new File(path + fileName);
		PDDocument document = PDDocument.load(file);
		System.out.println(document);
	}

	@Test
	public void crop_page() throws IOException {
		String path = "e:\\download\\pr\\crawler-newspaper\\";
		String fileName = "THAT'S CHINA_ENGLISH_20160805 cover.pdf";
		File file = new File(path + File.separator + fileName);
		new File(file.getParent() + File.separator + "split").mkdir();
		PDDocument document = PDDocument.load(file);
		PDDocument newDoc = new PDDocument();
//		document.getPages().forEach((p) -> {
//			PDRectangle rectangle = new PDRectangle();
//			rectangle.setLowerLeftX(40);
//			rectangle.setLowerLeftY(40);
//			rectangle.setUpperRightX(674.65f - 40);
//			rectangle.setUpperRightY(870.23f - 40);
//			p.setCropBox(rectangle);
//
//		});
//		document.save(new File(path + File.separator + "modified.pdf"));
//
		PDPage page1;
		PDPage page2;
		PDPage page3;
		PDPage page4;
		PDPage firstPage = document.getPage(0);
		PDPage secondPage = document.getPage(1);
		PDRectangle cropBox = firstPage.getCropBox();
		float width = cropBox.getWidth();
		float height = cropBox.getHeight();
		System.out.println("width: " + width);
		System.out.println("height: " + height);

		PDRectangle rectangle = new PDRectangle();
		rectangle.setLowerLeftX(width/2 - 9);
		rectangle.setLowerLeftY(0);
		rectangle.setUpperRightX(width);
		rectangle.setUpperRightY(height);
		page1 = firstPage;
		page1.setCropBox(rectangle);
		newDoc.addPage(page1);
		newDoc.save(new File(file.getParent() + File.separator + "1.pdf"));
		newDoc.removePage(0);
		page3 = secondPage;
		page3.setCropBox(rectangle);
		newDoc.addPage(page3);
		newDoc.save(new File(file.getParent()  + File.separator + "3.pdf"));
		newDoc.removePage(0);

		rectangle.setLowerLeftX(0);
		rectangle.setLowerLeftY(0);
		rectangle.setUpperRightX(width / 2 - 9);
		rectangle.setUpperRightY(height);
		page4 = firstPage;
		page4.setCropBox(rectangle);
		newDoc.addPage(page4);
		newDoc.save(new File(path + File.separator + "4.pdf"));

		document.close();
		newDoc.close();

//
//		page.setCropBox(rectangle);
//		PDDocument document1 = new PDDocument();
//		document1.addPage(page);
//		document1.save(new File(path + File.separator + "封面.pdf"));

//		document.close();
//		document1.close();
	}

	@Test
	public void extract_text() {
		String path = "e:\\download\\crawler-newspaper\\zgjjdb\\2889";
		File al = new File(path + File.separator + "A1.pdf");
		try {
			PDDocument pdDocument = PDDocument.load(al);
			COSDocument cosDocument = pdDocument.getDocument();
			PDPage page = pdDocument.getPage(0);
			PDFTextStripper pdfTextStripper = new PDFTextStripper();
			String text = pdfTextStripper.getText(pdDocument);
			System.out.println(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void extract_pages_from_pdf() {
		String sourceDir = "E:\\download\\pdf_merge\\data\\merge.pdf";
		String destDir = "E:\\download\\pdf_merge\\data\\cover.pdf";
		try {
			PDDocument document = PDDocument.load(new File(sourceDir));
			PDPage coverPage = document.getPage(0);
			PDDocument coverDoc = new PDDocument();
			coverDoc.addPage(coverPage);
			coverDoc.save(new File(destDir));
			document.close();
			coverDoc.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void covert_pdf_to_jpg() {
		String sourceDir = "E:\\download\\今古传奇\\2.pdf";
		String destDir = "E:\\download\\今古传奇\\2.jpg";
		try {
			PDDocument document = PDDocument.load(new File(sourceDir));
			PDFRenderer pdfRenderer = new PDFRenderer(document);
			BufferedImage bufferedImage = pdfRenderer.renderImage(0, 5);
			ImageIO.write(bufferedImage, "jpg", new File(destDir));
			document.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void create_from_image() {
//		CCITTFactory.createFromFile();
		PDDocument pdDocument = new PDDocument();
		String path = "e:\\download\\crawler-newspaper\\china information world\\2016-08-02\\封底－康普.jpg";
//		String path = "e:\\data\\pr\\marriage-family-documentary\\2016-07-01\\MARRIAGE AND FAMILY_DOCUMENTARY_20160701_封面.jpg";
		BufferedImage image;
		try {
			image = ImageIO.read(new File(path));
			int width = image.getWidth();
			int height = image.getHeight();
			PDPage pdPage = new PDPage(new PDRectangle(width, height));
			pdDocument.addPage(pdPage);
			PDImageXObject pdImageXObject = LosslessFactory.createFromImage(pdDocument, image);
			PDPageContentStream contentStream = new PDPageContentStream(pdDocument, pdPage);
			contentStream.drawImage(pdImageXObject, 0, 0);
			contentStream.close();
			pdDocument.save("e:\\download\\crawler-newspaper\\china information world\\2016-08-02\\1.pdf");
//			pdDocument.save("e:\\data\\pr\\marriage-family-documentary\\2016-07-01\\1.pdf");
			pdDocument.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		PDImageXObject.createFromFile(path,pdDocument);
	}

	@Test
	public void create_from_tiff() {
		PDDocument pdDocument = new PDDocument();
		String path = "e:\\download\\zgjjdb\\1.tif";
		BufferedImage image;
		try {
			image = ImageIO.read(new File(path));
			int width = image.getWidth();
			int height = image.getHeight();
			PDPage pdPage = new PDPage(new PDRectangle(width, height));
			pdDocument.addPage(pdPage);
			PDImageXObject pdImageXObject = LosslessFactory.createFromImage(pdDocument, image);
			PDPageContentStream contentStream = new PDPageContentStream(pdDocument, pdPage);
			contentStream.drawImage(pdImageXObject, 0, 0);
			contentStream.close();
			pdDocument.save("e:\\download\\zgjjdb\\tif.pdf");
			pdDocument.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test_merge() throws IOException {
		String path = "e:\\download\\pr\\crawler-newspaper\\销售与市场\\9期\\9期管理\\PDF\\21-96\\";
		File dir = new File(path);
		File[] files = dir.listFiles();
		Arrays.sort(files);
		final PDFMergerUtility ut = new PDFMergerUtility();
		for (File f : files) {
			ut.addSource(f);
		}
//		Files.walk(Paths.get(path)).forEach(filePath -> {
//			if (Files.isRegularFile(filePath)) {
//				try {
//					ut.addSource(filePath.toFile());
//				} catch (FileNotFoundException e) {
//					e.printStackTrace();
//				}
//			}
//		});

		ut.setDestinationFileName(path + "merge.pdf");
		MemoryUsageSetting mus = MemoryUsageSetting.setupTempFileOnly();
		mus.setTempDir(new File("c:\\tmp"));
		ut.mergeDocuments(mus);
	}

	@Test
	public void merge_china_information_world() throws IOException {
		String srcPath = "e:\\download\\crawler-newspaper\\china information world\\2016-08-02\\";
		File dir = new File(srcPath);
		for (File f : dir.listFiles()) {
			String fName = f.getName();
			if (fName.endsWith("jpg") || fName.endsWith("tif")) {
				PDDocument pdDocument = new PDDocument();
				BufferedImage image = ImageIO.read(f);
				int width = 754;
				int height = 924;
				PDPage pdPage = new PDPage(new PDRectangle(width, height));
				pdDocument.addPage(pdPage);
				PDImageXObject pdImageXObject = LosslessFactory.createFromImage(pdDocument, image);
				PDPageContentStream contentStream = new PDPageContentStream(pdDocument, pdPage);
				contentStream.drawImage(pdImageXObject, 0, 0, width, height);
				contentStream.close();
				pdDocument.save(srcPath + fName.substring(0, fName.length() - 4) + ".pdf");
				pdDocument.close();
			}
		}

		for (File f : dir.listFiles()) {
			String name = f.getName();
			if (!name.endsWith(".pdf")) {
				continue;
			}

			if (name.startsWith("封面")) {
				f.renameTo(new File(f.getParent() + File.separator + "0001" + name));
				continue;
			}
			if (name.startsWith("封二")) {
				f.renameTo(new File(f.getParent() + File.separator + "001" + name));
				continue;
			}
			if (name.startsWith("封三")) {
				f.renameTo(new File(f.getParent() + File.separator + "9999" + name));
				continue;
			}
		}

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

	PDFMergerUtility ut = new PDFMergerUtility();
		Collections.sort(pdfs);
		for (File f : pdfs) {
		ut.addSource(f);
	}

		ut.setDestinationFileName(srcPath + "CHINA INFORMATION WORLD_20160801.pdf");
	MemoryUsageSetting mus = MemoryUsageSetting.setupTempFileOnly();
		mus.setTempDir(new File("c:\\tmp"));
		ut.mergeDocuments(mus);
	}
}
