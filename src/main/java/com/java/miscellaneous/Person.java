package com.java.miscellaneous;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by yuwt on 2015/12/21.
 */
public abstract class Person {
    public static final String PERSON_S_NAME_CANNOT_BE_NULL = "Person's name cannot be null";
    private String name;
	HashMap a;

    public Person(String name) throws PersonException {
        if (name == null) {
            throw new PersonException(PERSON_S_NAME_CANNOT_BE_NULL);
        }
        this.name = name;
    }

	protected Person() {
	}

	public String getName() {
        return name;
    }

    public void setName(String name) throws PersonException {
        if (name == null) {
            throw new PersonException(PERSON_S_NAME_CANNOT_BE_NULL);
        }
        this.name = name;
    }

//    @Override
//    public abstract boolean equals(Object o);

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    protected enum Gender{Male, Female}

	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(1458633737 * 1000L);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = sdf.format(c.getTime());
		System.out.println(format);
	}
}
