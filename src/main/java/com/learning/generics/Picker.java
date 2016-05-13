package com.learning.generics;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yuwt on 2016/3/28.
 */
public class Picker {
	public static <T, S> T pick(S al, T a2) {
		al.hashCode();
		return a2;
	}

	void processStringList(List<String> stringList) {
		stringList.size();
	}

	public static void main(String[] args) {
//		ArrayList<String> arrayList = new ArrayList<>();
//		List<String> s = Picker.pick(new Object(), new ArrayList<>());
//		new Picker().processStringList(Collections.emptyList());
//		long a = Long.valueOf("20160331134739");
//		System.out.println(a);
//		Map<String, Object> map = Collections.singletonMap("aKey", new int[]{1,2,3});
//		System.out.println("map size:" + map.size());
//		System.out.println("map value:" + map.get("aKey"));
		Set<String> s = new HashSet<>();
		s.add("aa");
		s.add("aa");
		System.out.println(s.size());
	}
}
