package com.jtouzy.cv.model.errors;

public class CalendarGenerationException extends Exception {
	private static final long serialVersionUID = 1L;

	public CalendarGenerationException(String message, Throwable cause) {
		super(message, cause);
	}

	public CalendarGenerationException(String message) {
		super(message);
	}

	public CalendarGenerationException(Throwable cause) {
		super(cause);
	}
}
