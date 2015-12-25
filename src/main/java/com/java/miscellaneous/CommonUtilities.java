package com.java.miscellaneous;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by yuwt on 2015/12/11.
 *
 * @author yuwt
 */
public class CommonUtilities {
    /**
     * Return the current working directory.
     *
     * @return the current working directory
     */
    public static String getCurrentWorkingDir() {
        return System.getProperty("user.dir");
    }

    /**
     * Print classpath on standard output.
     */
    public static void printClasspath() {
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        URL[] urLs = ((URLClassLoader) systemClassLoader).getURLs();
        for (URL url : urLs) {
//            System.out.println(url.getFile());
        }
    }
}
