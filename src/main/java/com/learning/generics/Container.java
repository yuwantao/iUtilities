package com.learning.generics;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by yuwt on 2016/3/23.
 */
public class Container<E> {
	private Collection<E> items = new HashSet<>();

	public void push(E item) {
		items.add(item);
	}
}
