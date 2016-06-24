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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by yuwt on 2016/5/18.
 */
public class PdfTest {
	@Test
	public void crop_page() throws IOException {
		String path = "e:\\download\\human-nature\\6";
		String fileName = "2016年6月封面（电子版）（2016.5.25).pdf";
		File file = new File(path + File.separator + fileName);
		PDDocument document = PDDocument.load(file);
		PDPage page = document.getPage(0);
		PDRectangle cropBox = page.getCropBox();
		float width = cropBox.getWidth();
		float height = cropBox.getHeight();
		System.out.println("width: " + width);
		System.out.println("height: " + height);

		PDRectangle rectangle = new PDRectangle();
		rectangle.setLowerLeftX(0);
		rectangle.setLowerLeftY(0);
		rectangle.setUpperRightX(width / 2 -12);
		rectangle.setUpperRightY(height);

		rectangle.setLowerLeftX(width/2 - 12);
		rectangle.setLowerLeftY(0);
		rectangle.setUpperRightX(width);
		rectangle.setUpperRightY(height);

		page.setCropBox(rectangle);
		PDDocument document1 = new PDDocument();
		document1.addPage(page);
		document1.save(new File(path + File.separator + "封面.pdf"));

		document.close();
		document1.close();
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
		String path = "e:\\download\\zgjjdb\\1.jpg";
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
			pdDocument.save("e:\\download\\zgjjdb\\1.pdf");
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
		String path = "e:\\download\\zgjjdb\\2016-05-28\\";
		final PDFMergerUtility ut = new PDFMergerUtility();
		Files.walk(Paths.get(path)).forEach(filePath -> {
			if (Files.isRegularFile(filePath)) {
				try {
					ut.addSource(filePath.toFile());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

		ut.setDestinationFileName(path + "merge.pdf");
		MemoryUsageSetting mus = MemoryUsageSetting.setupMixed(1024 * 1024 * 1024, 3 * 1024 * 1024 * 1024);
		mus.setTempDir(new File(path + "tmp"));
		ut.mergeDocuments(mus);
	}
}
