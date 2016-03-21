package com.java.miscellaneous;

/**
 * Created by yuwt on 2015/12/21.
 */
public abstract class Person {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        return !(name != null ? !name.equals(person.name) : person.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    protected enum Gender{Male, Female}
}
