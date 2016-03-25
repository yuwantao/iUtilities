package com.java.miscellaneous;

import java.util.Comparator;

/**
 * Created by yuwt on 2016/3/21.
 */
public class MyComparator<T> implements Comparator<T> {
	public int compare(T o1, T o2) {
		return 0;
	}

	public static <T> Comparator<T> getComparator() {
		return new MyComparator<T>();
	}

	public static <T> Comparator<T> getAnotherComparator() {
		return new Comparator<T>() {
			public int compare(T o1, T o2) {
				return 0;
			}
		};
	}

	public static <T> Comparator<T> getOtherComparator() {
		return (c1, c2) -> c1.hashCode() - c2.hashCode();
	}
}
