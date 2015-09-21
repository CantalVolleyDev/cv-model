package com.jtouzy.cv.model.errors;

public class DataNotFoundException extends DataModelException {
	private static final long serialVersionUID = 1L;

	public DataNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataNotFoundException(String message) {
		super(message);
	}

	public DataNotFoundException(Throwable cause) {
		super(cause);
	}
}
