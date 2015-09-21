package com.jtouzy.cv.model.errors;

public class UserNotFoundException extends DataNotFoundException {
	private static final long serialVersionUID = 1L;
	
	public UserNotFoundException(Integer id) {
		super("L'utilisateur " + id + " n'existe pas");
	}
	public UserNotFoundException(String mail) {
		super("Aucun utilisateur n'existe avec l'adresse e-mail " + mail);
	}
	public UserNotFoundException(Throwable cause) {
		super(cause);
	}
}
