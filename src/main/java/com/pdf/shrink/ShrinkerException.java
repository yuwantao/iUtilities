package com.pdf.shrink;

/**
 * Created by yuwt on 2016/8/22.
 */
public class ShrinkerException extends Exception {
	public ShrinkerException() {
	}

	public ShrinkerException(String msg) {
		super(msg);
	}

	public ShrinkerException(Throwable cause) {
		super(cause);
	}

	public ShrinkerException(String message, Throwable cause) {
		super(message, cause);
	}
}
