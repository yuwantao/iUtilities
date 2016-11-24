package com.java.core;

import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by yuwt on 2016/11/10.
 */
public class DateTest {
	public static void main(String[] args) throws ParseException {
		String dateStr = "2016-09-06";
		Date date = DateUtils.parseDate(dateStr, new String[]{"yyyy-MM-dd"});
		int i=0;
		while (i<10) {
			System.out.println(date.getTime());
			i++;
		}
	}
}
