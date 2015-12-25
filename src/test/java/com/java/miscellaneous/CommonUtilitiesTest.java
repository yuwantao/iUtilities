package com.java.miscellaneous;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by yuwt on 2015/12/11.
 */
public class CommonUtilitiesTest {

    @org.junit.Before
    public void setUp() throws Exception {

    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void testGetCurrentWorkingDir() throws Exception {
        String curWorkingDir = System.getProperty("user.dir");
        assertEquals(curWorkingDir, CommonUtilities.getCurrentWorkingDir());
    }

    @Test
    public void testPrintClasspath() throws Exception {
        CommonUtilities.printClasspath();
    }
}