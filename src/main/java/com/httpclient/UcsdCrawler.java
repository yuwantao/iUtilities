package com.httpclient;

import com.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
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

import java.io.*;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yuwt on 2016/12/29.
 */
public class UcsdCrawler {
	public static void main(String[] args) throws Exception {
		CloseableHttpClient client = MyHttpClientUtils.getHttpClient();

//		crawlList(client);

		HSSFWorkbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet();

		Row headerRow = sheet.createRow(0);

		Cell headerCell = headerRow.createCell(0);
		headerCell.setCellType(Cell.CELL_TYPE_STRING);
		headerCell.setCellValue("ISBN");

		headerCell = headerRow.createCell(1);
		headerCell.setCellType(Cell.CELL_TYPE_STRING);
		headerCell.setCellValue("书名");

		headerCell = headerRow.createCell(2);
		headerCell.setCellType(Cell.CELL_TYPE_STRING);
		headerCell.setCellValue("中文书名");

		headerCell = headerRow.createCell(3);
		headerCell.setCellType(Cell.CELL_TYPE_STRING);
		headerCell.setCellValue("MARC日期");

		String filename = "e:\\tmp\\ucsd-list.txt";
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line = reader.readLine();
		while (line != null && StringUtils.isNotEmpty(line)) {
//			System.out.println("s:" + line);
			HttpGet httpGet = new HttpGet(line);
			CloseableHttpResponse response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
			response.close();

			Document doc = Jsoup.parse(content);
			Elements titleElements = doc.select("strong");
			Element titleEle = titleElements.get(1);
			Element subTitleEle = titleElements.get(2);
			String title = titleEle.text();
			String subTitle = subTitleEle.text();
			String isbn = "";
			String date = "";

			Elements isbnElemnts = doc.select("td");
			Iterator<Element> iterator = isbnElemnts.iterator();
			while (iterator.hasNext()) {
				Element next = iterator.next();
				String text = next.text();
				if (text != null && text.equals("ISBN")) {
					Element next1 = iterator.next();
					isbn = next1.text();
				}
			}

			String marcRelativeUrl = doc.select("img[alt='MARC Display']").first().parent().attr("href");
			String marcUrl = "http://roger.ucsd.edu" + marcRelativeUrl;
			response = client.execute(new HttpGet(marcUrl));
			entity = response.getEntity();
			content = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
			response.close();

			Document marcDoc = Jsoup.parse(content);
			String marcStr = marcDoc.select("pre").text();
			String regex = "008\\s+(\\d{6})";
			Pattern pattern = Pattern.compile(regex);
			Matcher m = pattern.matcher(marcStr);
			if (m.find()) {
				date = m.group(1);
			}

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

			line = reader.readLine();
		}

		File resultFile = FileUtils.createFile("e:\\tmp\\uscd-result.xls");
		assert resultFile != null;
		FileOutputStream out = new FileOutputStream(resultFile);
		wb.write(out);
		out.flush();
		out.close();
	}

	private static void crawlList(CloseableHttpClient client) throws IOException {
		String filename = "e:\\tmp\\ucsd-list.txt";
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		for (int i=0;i<=11;i++) {
			int num = 1 + i * 50;
			String url = "http://roger.ucsd.edu/search~S9?/X{u4E2D}{u56FD}{u793E}{u4F1A}{u79D1}{u5B66}{u6587}{u732E}{u51FA}{u7248}{u793E}&searchscope=9&SORT=D/X{u4E2D}{u56FD}{u793E}{u4F1A}{u79D1}{u5B66}{u6587}{u732E}{u51FA}{u7248}{u793E}&searchscope=9&SORT=D&SUBKEY=%E4%B8%AD%E5%9B%BD%E7%A4%BE%E4%BC%9A%E7%A7%91%E5%AD%A6%E6%96%87%E7%8C%AE%E5%87%BA%E7%89%88%E7%A4%BE/" + num +
					"%2C578%2C578%2CE/2browse";
			url = url.replaceAll("\\{", "%7B").replaceAll("}", "%7D");
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
			response.close();

			Document doc = Jsoup.parse(content);
			Elements elements = doc.select("div.briefcitTitle > a");
			for (Element e : elements) {
				String relativeUrl = e.attr("href");
				String detailUrl = "http://roger.ucsd.edu/" + relativeUrl;

				writer.write(detailUrl);
				writer.newLine();
			}
		}
		writer.close();
	}
}
