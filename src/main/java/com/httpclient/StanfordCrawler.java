package com.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yuwt on 2016/12/29.
 */
public class StanfordCrawler {
	public static void main(String[] args) throws Exception{
		CloseableHttpClient client = MyHttpClientUtils.getHttpClient();

		for (int i=11;i<=22;i++) {
			crawlOnePage(client, i);
		}
	}

	private static void crawlOnePage(CloseableHttpClient client, int pageNum) throws IOException {
		String url = "https://searchworks.stanford.edu/?page=" + pageNum +
				"&per_page=100&q=%E4%B8%AD%E5%9B%BD%E7%A4%BE%E4%BC%9A%E7%A7%91%E5%AD%A6%E6%96%87%E7%8C%AE%E5%87%BA%E7%89%88%E7%A4%BE";
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = client.execute(httpGet);
		HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity);
		EntityUtils.consume(entity);
		response.close();

		Document doc = Jsoup.parse(content);
		Elements elements = doc.select("a[data-context-href]");
		System.out.println(elements.size());

		File file = new File("e:\\tmp\\stanford-result.xls");
		FileInputStream in = new FileInputStream(file);
		HSSFWorkbook wb = new HSSFWorkbook(in);
		in.close();
		Sheet sheet = wb.getSheetAt(0);

		for (Element e : elements) {
			String title = "";
			String subTitle = "";
			String isbn = "";
			String date = "";

			String detailRelativeUrl = e.attr("href");
			String detailUrl = "https://searchworks.stanford.edu" + detailRelativeUrl;
			System.out.println(detailUrl);

			httpGet = new HttpGet(detailUrl);
			response = client.execute(httpGet);
			entity = response.getEntity();
			content = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
			response.close();

			Document detailDoc = Jsoup.parse(content);
			title = detailDoc.select("span[itemprop]").text();
			subTitle = detailDoc.select("p.vernacular-title").text();

			Element isbnEle = detailDoc.select("dt[title=ISBN]").first();
			while (true) {
				if (isbnEle == null) {
					break;
				}
				isbnEle = isbnEle.nextElementSibling();
				if (isbnEle == null || !isbnEle.tagName().equals("dd")) {
					break;
				}
				isbn = isbn + isbnEle.text() + "   ";
			}
			isbn = isbn.trim();

			String dateRelativeUrl = detailDoc.select("a#librarianLink").attr("href");
			String dateUrl = "https://searchworks.stanford.edu" + dateRelativeUrl;

			httpGet = new HttpGet(dateUrl);
			response = client.execute(httpGet);
			entity = response.getEntity();
			content = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
			response.close();

			Document dateDoc = Jsoup.parse(content);
			Elements dateElements = dateDoc.select("span.tag");
			for (Element e1 : dateElements) {
				if (e1.text().trim().equals("008")) {
					String dateStr = e1.nextElementSibling().text();
					date = dateStr.trim().substring(0, 6);
					break;
				}
			}

			System.out.println("crawl one detail succeed.");
			//write to excel


			int lastRowNum = sheet.getLastRowNum();
			Row row = sheet.createRow(lastRowNum + 1);

			Cell cell = row.createCell(0);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(isbn);

			cell = row.createCell(1);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(title);

			cell = row.createCell(2);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(subTitle);

			cell = row.createCell(3);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(date);
		}

		FileOutputStream out = new FileOutputStream("e:\\tmp\\stanford-result.xls");
		wb.write(out);
		out.flush();
		out.close();
	}
}
