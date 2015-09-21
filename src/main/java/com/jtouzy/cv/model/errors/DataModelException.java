package com.jtouzy.cv.model.errors;

public class DataModelException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public DataModelException() {
		super();
	}
	public DataModelException(String message, Throwable cause) {
		super(message, cause);
	}
	public DataModelException(String message) {
		super(message);
	}
	public DataModelException(Throwable cause) {
		super(cause);
	}
}
