package com.csv;

/**
 * Created by yuwt on 2016/9/30.
 */
public class MathTest {
	public static void main(String[] args) {
		int offset = 101000;
		int base = 100000;
		int quotient = offset / base;
		int modulo = offset%base;
		System.out.println("quotient:" + quotient);
		System.out.println("modulo:" + modulo);
	}
}
