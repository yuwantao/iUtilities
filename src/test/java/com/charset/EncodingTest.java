package com.charset;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * Created by yuwt on 2016/4/27.
 */
public class EncodingTest {
	@Test
	public void testEncoding() throws UnsupportedEncodingException {
		String pre = "????????????.";
		String post = new String(pre.getBytes("iso-8859-1"), "gb2312");
		System.out.println(post);
	}
}
