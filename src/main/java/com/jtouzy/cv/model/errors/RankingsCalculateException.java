package com.jtouzy.cv.model.errors;

public class RankingsCalculateException extends Exception {
	private static final long serialVersionUID = 1L;

	public RankingsCalculateException(String message, Throwable cause) {
		super(message, cause);
	}

	public RankingsCalculateException(String message) {
		super(message);
	}

	public RankingsCalculateException(Throwable cause) {
		super(cause);
	}
}

