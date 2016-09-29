package com.java.core;

/**
 * Created by yuwt on 2016/7/22.
 */
public class StringTest {
	public static void main(String[] args) {
		String s1 = new String("cibtc");
		String s2 = "cibtc";
		String s3 = "ci" + "btc";
		System.out.println(s1 == s2);
		System.out.println(s2 == s3);
	}
}
