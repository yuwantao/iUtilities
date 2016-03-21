package com.test.junit;

/**
 * Created by yuwt on 2015/12/24.
 */
public class Largest {
    private String[] array;
    /**
     * Returns the largest element in a list.
     *
     * @param intList A list of integers
     * @return The largest number in the given list
     */
    public static int largest(int[] intList) {
        if (intList.length == 0) {
            throw new RuntimeException("Empty List");
        }

        int index, max = Integer.MIN_VALUE;
        for (index=0; index<intList.length; index++) {
            if (intList[index] > max) {
                max = intList[index];
            }
        }

        return max;
    }

    public String[] getArray() {
        return array;
    }

    public void setArray(String[] array) {
        this.array = array;
    }
}
