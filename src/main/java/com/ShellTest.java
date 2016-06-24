package com;

import java.io.IOException;

/**
 * Created by yuwt on 2016/6/3.
 */
public class ShellTest {
	public static void main(String[] args) throws IOException, InterruptedException {
		Runtime rt = Runtime.getRuntime();
		Process p = rt.exec("unrar e /data/doc/*.rar /data/doc/test/");
		p.waitFor();
		System.out.println("process exit code: " + p.exitValue());
	}
}
