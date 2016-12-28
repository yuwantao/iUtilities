package com.java.core;

import java.text.ParseException;

/**
 * Created by yuwt on 2016/11/10.
 */
public class DateTest {
	public static void main(String[] args) throws ParseException {
//		String dateStr = "2016-09-06";
//		Date date = DateUtils.parseDate(dateStr, new String[]{"yyyy-MM-dd"});
//		int i=0;
//		while (i<10) {
//			System.out.println(date.getTime());
//			i++;
//		}

//		Calendar c = Calendar.getInstance();
//		c.add(Calendar.HOUR_OF_DAY, 8);
//		int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
//		System.out.println(hourOfDay);

		String url = "http://book.jd.com/booktop/0-0-0.html?category=1713-0-0-0-10003-1#comfort";
		System.out.println(url.substring(0, url.length() - 9));
	}
}
