package com.jtouzy.cv.model.errors;

public class MatchNotFoundException extends DataNotFoundException {
	private static final long serialVersionUID = 1L;
	
	public MatchNotFoundException(Integer id) {
		super("Le match " + id + " n'existe pas");
	}
	public MatchNotFoundException(Throwable cause) {
		super(cause);
	}
}
