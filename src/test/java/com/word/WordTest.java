package com.word;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by yuwt on 2016/10/9.
 */
public class WordTest {

	@Test
	public void test() {
		String filepath = "e:\\download\\羌塘动物-出刊\\羌塘动物正文-出刊.docx";
		try {
			XWPFDocument document = new XWPFDocument(new FileInputStream(filepath));
			List<XWPFParagraph> paragraphs = document.getParagraphs();
			paragraphs.forEach(p -> {
				String text = p.getText();
				System.out.println("p:" + text);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
