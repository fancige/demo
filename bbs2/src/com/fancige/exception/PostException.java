package com.fancige.exception;

public class PostException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PostException() {
		super();
	}

	public PostException(String message, Throwable cause) {
		super(message, cause);
	}

	public PostException(String message) {
		super(message);
	}

	public PostException(Throwable cause) {
		super(cause);
	}
}
