package com.java.miscellaneous;

/**
 * Created by yuwt on 2015/12/21.
 */
public class SuperMan extends Man {
//    private SuperMan() {
//        super();
//    }
//    Gender initGender() {
//        return Gender.Female;
//    }

    public static void main(String... args) {
        System.out.print("Superman's gender: " + new SuperMan().gender);
    }
}

