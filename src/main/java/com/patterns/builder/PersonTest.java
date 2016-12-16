package com.patterns.builder;

import org.apache.http.impl.client.HttpClients;

/**
 * Created by yuwt on 2016/12/13.
 */
public class PersonTest {
	public static void main(String[] args) {
		Person person = Person.custom("yu", "male")
				.height(1.70f)
				.weight(73.8f)
				.build();
		System.out.println(person.getName());
		HttpClients.createDefault();
//		new Person();
	}
}
