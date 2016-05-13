package com.java.miscellaneous;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yuwt on 2016/4/15.
 */
public class TestRegex {
	public static void main(String[] args) {
		String text = "{\"Header\":{\"Title\":\"Твердый переплет\",\"FullTitle\":\"Твердый переплет\",\"Order\":1,\"CountString\":\"1 издание\",\"PriceString\":\"598\",\"IsAbsent\":false},\"Editions\":[{\"IsActive\":true,\"Type\":\"Твердый переплет, суперобложка\",\"Publishers\":[{\"Href\":\"/context/detail/id/855962/\",\"Text\":\"АСТ\"},{\"Href\":\"/context/detail/id/21102722/\",\"Text\":\"Neoclassic\"}],\"Picture\":\"//static1.ozone.ru/multimedia/books_covers/c50/1010414632.jpg\",\"Format\":\"130х200 мм\",\"FormatTitle\":\"Средний формат\",\"ReleaseYear\":\"2014 г.\",\"PriceString\":\"598\",\"Id\":27940179}],\"IsShowНeader\":false,\"IsActive\":true},{\"Header\":{\"Title\":\"Цифровая книга\",\"FullTitle\":\"Цифровая книга\",\"Order\":3,\"CountString\":\"1 издание\",\"PriceString\":\"199,90\",\"IsAbsent\":false},\"Editions\":[{\"IsActive\":false,\"Type\":\"Цифровая книга\",\"Publishers\":[{\"Href\":\"/context/detail/id/33676712/\",\"Text\":\"АСТ\"}],\"Picture\":\"//static2.ozone.ru/multimedia/books_covers/c50/1013229197.jpg\",\"ReleaseYear\":\"2014 г.\",\"PriceString\":\"199,90\",\"Id\":28351024}],\"IsShowНeader\":false,\"IsActive\":false},{\"Header\":{\"Title\":\"Нет в продаже\",\"FullTitle\":\"Пока нет в продаже\",\"Order\":100,\"IsAbsent\":true},\"Editions\":[{\"IsActive\":false,\"Type\":\"Букинистическое издание\",\"Publishers\":[{\"Href\":\"/context/detail/id/855962/\",\"Text\":\"АСТ\"}],\"Picture\":\"//static1.ozone.ru/multimedia/books_covers/c50/1010029374.jpg\",\"Format\":\"130х200 мм\",\"FormatTitle\":\"Средний формат\",\"ReleaseYear\":\"2005 г.\",\"Id\":26349477}],\"IsShowНeader\":false,\"IsActive\":false}],";
		Pattern p = Pattern.compile("\\{\"Header\":\\{\"Title\":\"(\\p{L}+\\s+\\p{L}+)\",\"FullTitle.+ReleaseYear\":\"(\\d+\\s+г.)\",\"PriceString\":\"([\\d,]+)\"");


		text = "  <h3>Содержание</h3> " +
				"             <span>Великая шахматная доска.</span>" +
				"             <span>Выбор.</span>" +
				"             <span>Еще один шанс.</span>";
//		p = Pattern.compile("\"Type\":\"([\\p{L},\\s]*)");
		p = Pattern.compile("<h3>Содержание</h3>");
		Matcher matcher = p.matcher(text);
		while (matcher.find()) {
			System.out.println(matcher.group());
//			System.out.println(matcher.group(1));
//			System.out.println(matcher.group(2));
//			System.out.println(matcher.group(3));
		}
	}
}
