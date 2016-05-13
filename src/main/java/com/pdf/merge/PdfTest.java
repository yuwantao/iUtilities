package com.pdf.merge;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

/**
 * Created by yuwt on 2016/5/13.
 */
public class PdfTest {
	public static void main(String[] args) {
		try {
//			byte[] bytes = Files.readAllBytes(Paths.get("e:\\download\\test.pdf"));
//			System.out.print(new String(Arrays.copyOf(bytes, 8)));
			String pdfFileHeader = "%PDF";
			BufferedInputStream in = new BufferedInputStream(new FileInputStream("e:\\download\\\\pdf_merge\\nginx.pdf"));
			byte[] buf = new byte[4];
			if (in.read(buf) != -1) {
				if (pdfFileHeader.equals(new String(buf))) {
					System.out.println("file is pdf.");
				} else {
					System.out.print("file is not pdf.");
				}
			} else {
				System.out.print("file is not pdf.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
