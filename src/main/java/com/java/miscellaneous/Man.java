package com.java.miscellaneous;

/**
 * Created by yuwt on 2015/12/21.
 */
public class Man extends Person{
    protected Gender gender;

    Man() {
        this.gender = Gender.Female;
        this.gender = initGender();
    }

    final Gender initGender() {
        return Gender.Male;
    }
}
