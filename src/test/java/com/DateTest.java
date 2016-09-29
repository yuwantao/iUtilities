package com;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

import java.util.Date;

/**
 * Created by yuwt on 2016/8/3.
 */
public class DateTest {
	@Test
	public void test_add_days() {
		Date now = new Date();
		Date yesterday = DateUtils.addDays(now, -1);
		String format = DateFormatUtils.format(yesterday, "yyyy-MM-dd");
		System.out.println(format);
	}
}
